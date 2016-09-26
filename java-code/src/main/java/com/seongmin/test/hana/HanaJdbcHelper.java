package com.seongmin.test.hana;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public abstract class HanaJdbcHelper {

	static {
		try {
			Class.forName("com.sap.db.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager
				.getConnection("jdbc:sap://test.vm.cld.sr:30015", "SYSTEM", "manager");
		
		return conn;
	}
	
	protected static void pringMetaData(DatabaseMetaData metaData) throws SQLException
	{
		
		
		/*
		copy from DatabaseMetaData.getColumns()

		TABLE_CAT String => table catalog (may be null) 
		TABLE_SCHEM String => table schema (may be null) 
		TABLE_NAME String => table name 
		COLUMN_NAME String => column name 
		DATA_TYPE int => SQL type from java.sql.Types 
		TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified 
		COLUMN_SIZE int => column size. For char or date types this is the maximum number of characters, for numeric or decimal types this is precision. 
		BUFFER_LENGTH is not used. 
		DECIMAL_DIGITS int => the number of fractional digits 
		NUM_PREC_RADIX int => Radix (typically either 10 or 2) 
		NULLABLE int => is NULL allowed. 
			columnNoNulls - might not allow NULL values 
			columnNullable - definitely allows NULL values 
			columnNullableUnknown - nullability unknown 
		REMARKS String => comment describing column (may be null) 
		COLUMN_DEF String => default value (may be null) 
		SQL_DATA_TYPE int => unused 
		SQL_DATETIME_SUB int => unused 
		CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column 
		ORDINAL_POSITION int => index of column in table (starting at 1) 
		IS_NULLABLE String => "NO" means column definitely does not allow NULL values; "YES" means the column might allow NULL values. An empty string means nobody knows. 
		SCOPE_CATLOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF) 
		SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF) 
		SCOPE_TABLE String => table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF) 
		SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF) 		
		 */

		/*
		오라클에서는 테이블 병 앞에 schema이름이 붙는다.
		예를들어 "TOBE0.USER_INFO", "TOBE1.USER_INFO".
		이런 경우 이름은 같지만 다른 테이블이다.
		요 앞의 값은 resultSet.getString("TABLE_SCHEM")으로 구할 수 있고,
		metaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern)를 호출할 때의 schemaPattern의 값이다.
		 * 		
		 */
		String catalog= null;
		String schemaPattern = null;
//		String tableNamePattern = null;
//		String schemaPattern = "_SYS_BIC";
		String tableNamePattern = "com..research/AT_FIRST";
		String columnNamePattern = null;
		ResultSet resultSet = metaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
		while(resultSet.next()) {

			// oracle에서의 특이현상으로 "COLUMN_DEF"를 가장먼저 읽어야 한다.
			// 그렇지 않을 경우"스트림이 이미 종료되었습니다"라는 SQLException이 발생한다. 
			String defaultValue = resultSet.getString("COLUMN_DEF");
			String columnName = resultSet.getString("COLUMN_NAME");
			String vendorsTypeName = resultSet.getString("TYPE_NAME");
			int columnType = resultSet.getInt("DATA_TYPE");
			int columnSize = resultSet.getInt("COLUMN_SIZE");
			String decimalDigit = resultSet.getString("DECIMAL_DIGITS");

			System.out.println(columnName+", "+resultSet.getString("TYPE_NAME")+", "+columnType);
		}

		resultSet = metaData.getPrimaryKeys(catalog, schemaPattern, tableNamePattern);
		while(resultSet.next()) {
			String columnName = resultSet.getString("COLUMN_NAME");
			System.out.println("pk"+columnName);
		}


	}
	
	
	public static void print(ResultSet rs, String sql) throws SQLException {
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		
//		for (int i = 1; i <= columnsNumber; i++) {
//			if (i > 1)
//				System.out.print(",  ");
////			String columnValue = rs.getString(i);
//			System.out.print("getColumnName=" + rsmd.getColumnName(i));
//			System.out.print(", getColumnType="+rsmd.getColumnType(i));
//			System.out.print(", getColumnTypeName="+rsmd.getColumnTypeName(i));
//			System.out.print(", getPrecision="+ rsmd.getPrecision(i));
//			System.out.print(", getScale=" + rsmd.getScale(i));
//			System.out.print(", getColumnDisplaySize="+rsmd.getColumnDisplaySize(i));
//			System.out.println();
//		}
		
		
		System.out.println("querying=" + sql);
		int index = 0;
		
		while (rs.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnValue = rs.getString(i);
				System.out.print(columnValue + " " + rsmd.getColumnName(i));
			}
			System.out.println("");
			index++;
		}
		
		System.out.println("count = " + index);
	}
	
}
