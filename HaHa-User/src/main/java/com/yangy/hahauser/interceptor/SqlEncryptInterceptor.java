package com.yangy.hahauser.interceptor;

import com.yangy.hahauser.annotation.EncryptEntity;
import com.yangy.hahauser.annotation.EncryptField;
import com.yangy.hahauser.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

/**
 * @Author: Yangy
 * @Date: 2023/7/27 10:37
 * @Description
 */
@Component
@Intercepts({
//        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, String.class}),
        @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
@Slf4j
public class SqlEncryptInterceptor implements Interceptor {

	@Resource
    private EncryptService encryptService;
	
	private static final StopWatch stopWatch = new StopWatch();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
    	Object target = invocation.getTarget();
		
        //拦截sql结果处理器
        if (target instanceof ResultSetHandler) {
            return resultDecrypt(invocation);
        }

        if (target instanceof ParameterHandler) {
            return parameterEncrypt(invocation);
        }

//        if (target instanceof StatementHandler) {
//            return replaceSql(invocation);
//        }
        return invocation.proceed();
    }

    /**
     * 对mybatis映射结果进行字段解密
     *
     * @param invocation 参数
     * @return 结果
     * @throws Throwable 异常
     */
    private Object resultDecrypt(Invocation invocation) throws Throwable {
        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        //基于selectList
        if (resultObject instanceof ArrayList) {
            ArrayList resultList = (ArrayList) resultObject;
            if (!CollectionUtils.isEmpty(resultList) && needToDecrypt(resultList.get(0))) {
                stopWatch.start("解密耗时s统计");
            	for (Object result : resultList) {
                    //逐一解密
                    decryptProcessor(result);
                }
                stopWatch.stop();
            	log.info("解密s耗时：{}",stopWatch.getTotalTimeSeconds());
            }
        //基于selectOne
        } else {
            if (needToDecrypt(resultObject)) {
            	stopWatch.start("解密耗时统计");
                decryptProcessor(resultObject);
                log.info("解密耗时：{}",stopWatch.getTotalTimeSeconds());
            }
        }
        return resultObject;
    }

    public <T> T decryptProcessor(T result) throws Exception {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            //取出所有被EncryptDecryptField注解的字段
            EncryptField encryptField = field.getAnnotation(EncryptField.class);
            if (!Objects.isNull(encryptField)) {
                field.setAccessible(true);
                Object object = field.get(result);
                //只支持String的解密
                if (object instanceof String) {
                    String value = (String) object;
                    //对注解的字段进行逐一解密
                    field.set(result, encryptService.decrypt(value));
                }
            }
        }
        return result;
    }

    /**
     * mybatis映射参数进行加密
     *
     * @param invocation 参数
     * @return 结果
     * @throws Throwable 异常
     */
    private Object parameterEncrypt(Invocation invocation) throws Throwable {
        //@Signature 指定了 type= parameterHandler 后，这里的 invocation.getTarget() 便是parameterHandler 
        //若指定ResultSetHandler ，这里则能强转为ResultSetHandler
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        // 获取参数对像，即 mapper 中 paramsType 的实例
        Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
        parameterField.setAccessible(true);
        //取出实例
        Object parameterObject = parameterField.get(parameterHandler);
        if (parameterObject != null) {
            Class<?> parameterObjectClass = parameterObject.getClass();
            //校验该实例的类是否被@SensitiveData所注解
            EncryptEntity sensitiveData = AnnotationUtils.findAnnotation(parameterObjectClass, EncryptEntity.class);
            if (Objects.nonNull(sensitiveData)) {
            	stopWatch.start("加密耗时统计");
                //取出当前当前类所有字段，传入加密方法
                Field[] declaredFields = parameterObjectClass.getDeclaredFields();
                for (Field field : declaredFields) {
					//取出所有被EncryptDecryptField注解的字段
					EncryptField sensitiveField = field.getAnnotation(EncryptField.class);
					if (!Objects.isNull(sensitiveField)) {
						field.setAccessible(true);
						Object object = field.get(parameterObject);
						//暂时只实现String类型的加密
						if (object instanceof String) {
							String value = (String) object;
							//加密  这里我使用自定义的AES加密工具
							field.set(parameterObject, encryptService.encrypt(value));
						}
					}
				}
				stopWatch.stop();
                log.info("加密耗时：{}",stopWatch.getTotalTimeSeconds());
            }
        }
        return invocation.proceed();
    }

    /**
     * 替换mybatis Sql中的加密Key
     *
     * @param invocation 参数
     * @return 结果
     * @throws Throwable 异常
     */
    private Object replaceSql(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        //获取到原始sql语句
        String sql = boundSql.getSql();
        if (null == sql){
            return invocation.proceed();
        }
        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, sql);
        return invocation.proceed();
    }

    /**
     * 判断是否包含需要加解密对象
     *
     * @param object 参数
     * @return 结果
     */
    private boolean needToDecrypt(Object object) {
        if(Objects.isNull(object)){
            return false;
        }
        Class<?> objectClass = object.getClass();
        Class<?> parentClass = objectClass.getSuperclass();
        EncryptEntity encryptEntity = AnnotationUtils.findAnnotation(objectClass, EncryptEntity.class);
        EncryptEntity parentEncryptEntity = AnnotationUtils.findAnnotation(parentClass, EncryptEntity.class);

        return Objects.nonNull(encryptEntity) || Objects.nonNull(parentEncryptEntity);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
