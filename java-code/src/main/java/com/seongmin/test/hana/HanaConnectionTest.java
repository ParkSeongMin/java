package com.seongmin.test.hana;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HanaConnectionTest {

	public static void main(String[] args) {
		test();
		test2();
	}

	public static void test() {

		Connection conn = null;
		Statement stmt = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = HanaJdbcHelper.getConnection();
			
//			String selectSql = "select distinct PUB_ID, NAME, TITLE, sum(PRICE), avg(PRICE), max(PRICE), mid(PRICE), min(PRICE), count(PRICE), mode(PRICE) from \"_SYS_BIC\".\"com.research/AN_PUB_BOOKS\" group by PUB_ID, NAME, TITLE";
//			String selectSql = "select distinct PUB_ID, NAME, TITLE, sum(PRICE), avg(PRICE), max(PRICE), min(PRICE), count(PRICE) from \"_SYS_BIC\".\"com.research/AN_PUB_BOOKS\" group by PUB_ID, NAME, TITLE";
			
			// 전체 list
			String selectSql = "select * from \"_SYS_REPO\".\"ACTIVE_OBJECT\"";
//			String selectSql = "SELECT * FROM \"PUBLIC\".\"VIEW_COLUMNS\" where SCHEMA_NAME = '_SYS_BIC' AND VIEW_NAME='com.research/AT_FIRST'";
			
//			String selectSql = "select TOP 0 * from \"_SYS_BIC\".\"com.research.PSM/AT_FIRST\"";
			
			// 해당 view schema
//			String selectSql = "select * from \"_SYS_REPO\".\"ACTIVE_OBJECT\" where PACKAGE_ID = 'com.research' and OBJECT_NAME = 'AT_BOOKS'";
//			String selectSql = "select * from \"_SYS_REPO\".\"ACTIVE_OBJECT\" where PACKAGE_ID = 'com.research' and OBJECT_NAME = 'CA_SALES_REPORT'";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSql); 

			
			DatabaseMetaData metaData = conn.getMetaData();
			HanaJdbcHelper.pringMetaData(metaData);
			HanaJdbcHelper.print(rs, selectSql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {

					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public static void test2() {

		Connection conn = null;
		Statement stmt = null; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = HanaJdbcHelper.getConnection();
			
//			String selectSql = "select distinct PUB_ID, NAME, TITLE, sum(PRICE), avg(PRICE), max(PRICE), mid(PRICE), min(PRICE), count(PRICE), mode(PRICE) from \"_SYS_BIC\".\"com.research/AN_PUB_BOOKS\" group by PUB_ID, NAME, TITLE";
//			String selectSql = "select distinct PUB_ID, NAME, TITLE, sum(PRICE), avg(PRICE), max(PRICE), min(PRICE), count(PRICE) from \"_SYS_BIC\".\"com.research/AN_PUB_BOOKS\" group by PUB_ID, NAME, TITLE";
			
			// 전체 list
			String selectSql = "select * from \"SYSTEM\".\"PUBLISHER_5463296A0F5711C9E10000000AA00064\"";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectSql); 

			
			DatabaseMetaData metaData = conn.getMetaData();
			HanaJdbcHelper.pringMetaData(metaData);
			HanaJdbcHelper.print(rs, selectSql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {

					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
	
	
}
