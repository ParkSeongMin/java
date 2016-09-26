package com.seongmin.test.dbcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionManager {

//	// Load the JDBC driver
//	// String driverName = "org.gjt.mm.mysql.Driver"; // MySQL MM JDBC driver
//	static String	driverName	= "oracle.jdbc.driver.OracleDriver";

	
    private static final String DBCP_CONNECT_NAME_PREFIX = "jdbc:apache:commons:dbcp:";
    
    // ********** Define Initial Parameter Names
     static final String JNDI_NAME = "test";
     // db connection info parameter names
     static final String DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
     static final String URL = "jdbc:oracle:thin:@ip:1521:orcl";
     static final String USERNAME = "id";
     static final String PASSWORD = "pw";
     static final int MAX_ACTIVE = 30;
     static final int MAX_IDLE = 30;
     static final int MAX_WAIT = 30000;
     static final String VALIDATION_QUERY = "SELECT 1 FROM DUAL";
     // abandoned setting을 위한 parameter names
     // ********** Define Initial Parameter Names End
	
	static {
		// jdbc driver lodding
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// db connection pool로 사용할 GenericObjectPool을 생성하고 설정한다.
		GenericObjectPool connPool = new GenericObjectPool();
		connPool.setMaxActive(MAX_ACTIVE);
		connPool.setMaxIdle(MAX_IDLE);
		connPool.setMaxWait(MAX_WAIT);

		// Object Pool에서 DB Connection을 생성하기 위한 DriverManager Factory를 생성한다.
		ConnectionFactory connFactory = new DriverManagerConnectionFactory(URL, USERNAME, PASSWORD);

		// ConnectionFactory의 래퍼 클래스 생성
		PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, connPool, null,
				VALIDATION_QUERY, false, true);

		// DBCP에서 Abandoned 설정 정보를 사용하려면 주석을 푼다. dbcp-1.2.2에서 Abandoned에 대한 모든
		// 객체가 Deprecated 되었다.
		/*
		 * boolean removeAbandoned = (initParamValue =
		 * config.getInitParameter(REMOVE_ABANDONED)) != null ?
		 * Boolean.parseBoolean(initParamValue) : false; int
		 * removeAbandonedTimeout = (initParamValue =
		 * config.getInitParameter(REMOVE_ABANDONED_TIMEOUT)) != null ?
		 * Integer.parseInt(initParamValue) : 300; boolean logAbandoned =
		 * (initParamValue = config.getInitParameter(LOG_ABANDONED)) != null ?
		 * Boolean.parseBoolean(initParamValue) : false;
		 * 
		 * AbandonedConfig abandonedConfig = new AbandonedConfig();
		 * abandonedConfig.setLogAbandoned(true);
		 * abandonedConfig.setRemoveAbandoned(true);
		 * abandonedConfig.setRemoveAbandonedTimeout(120);
		 * 
		 * AbandonedObjectPool abandonedObjectPool = new
		 * AbandonedObjectPool(poolableConnFactory, abandonedConfig);
		 */

		// PoolingDriver를 로딩하여 DB Connection Pool을 등록한다.
		try {
			Class.forName("org.apache.commons.dbcp.PoolingDriver");
			PoolingDriver poolingDriver = (PoolingDriver) DriverManager.getDriver(DBCP_CONNECT_NAME_PREFIX);
			poolingDriver.registerPool(JNDI_NAME, connPool);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() throws SQLException {
		 return DriverManager.getConnection(DBCP_CONNECT_NAME_PREFIX + JNDI_NAME);
	}

}
