package com.yangy.hahauser.handler;

import com.yangy.hahauser.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.annotation.Resource;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Yangy
 * @Date: 2023/7/25 15:15
 * @Description
 */
@Slf4j
//@MappedTypes({String.class})
//@MappedJdbcTypes(JdbcType.VARCHAR)
//@Component
public class EncryptTypeHandler extends BaseTypeHandler<String> {
	
	@Resource
	private EncryptService encryptService;
	
	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, String t, JdbcType jdbcType) throws SQLException {
		try {
			preparedStatement.setString(i,encryptService.encrypt(t));
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);
		}
	}

	@Override
	public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
		String columnValue = resultSet.getString(s);
		try {
			return StringUtils.isBlank(columnValue) ? columnValue : encryptService.decrypt(columnValue);
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);	
			return columnValue;
		}
	}

	@Override
	public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
		String columnValue = resultSet.getString(i);
		try {
			return StringUtils.isBlank(columnValue) ? columnValue : encryptService.decrypt(columnValue);
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);	
			return columnValue;
		}
	}

	@Override
	public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		String columnValue = callableStatement.getString(i);
		try {
			return StringUtils.isBlank(columnValue) ? columnValue : encryptService.decrypt(columnValue);
		} catch (Exception e) {
			log.error("{}",e.getMessage(),e);	
			return columnValue;
		}
	}
}
