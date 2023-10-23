//package com.yangy.user.handler;
//
//import com.yangy.user.service.EncryptService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * @Author: Yangy
// * @Date: 2023/7/25 15:15
// * @Description
// */
//@Slf4j
////加上如下2个注解，即可对指定的类型进行类型转换处理，如下配置就会对String字符串类型的字段，都加解密
////@MappedTypes({EncryptTypeHandler.class})
////@MappedJdbcTypes(JdbcType.VARCHAR)
//@Component
//public class EncryptTypeHandler extends BaseTypeHandler<Object> {
//	
//	@Resource
//	private EncryptService encryptService;
//	
//	@Override
//	public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object t, JdbcType jdbcType) throws SQLException {
//		try {
//			if(t instanceof String){
//				preparedStatement.setString(i,encryptService.encrypt(t.toString()));
//			}else {
//				preparedStatement.setObject(i,t);	
//			}
//		} catch (Exception e) {
//			preparedStatement.setString(i,t.toString());
//			log.error("{}",e.getMessage(),e);
//		}
//	}
//
//	@Override
//	public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
//		String columnValue = resultSet.getString(s);
//		try {
//			return StringUtils.isBlank(columnValue) ? columnValue : encryptService.decrypt(columnValue);
//		} catch (Exception e) {
//			log.error("{}",e.getMessage(),e);	
//			return columnValue;
//		}
//	}
//
//	@Override
//	public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
//		String columnValue = resultSet.getString(i);
//		try {
//			return StringUtils.isBlank(columnValue) ? columnValue : encryptService.decrypt(columnValue);
//		} catch (Exception e) {
//			log.error("{}",e.getMessage(),e);	
//			return columnValue;
//		}
//	}
//
//	@Override
//	public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
//		String columnValue = callableStatement.getString(i);
//		try {
//			return StringUtils.isBlank(columnValue) ? columnValue : encryptService.decrypt(columnValue);
//		} catch (Exception e) {
//			log.error("{}",e.getMessage(),e);	
//			return columnValue;
//		}
//	}
//}
