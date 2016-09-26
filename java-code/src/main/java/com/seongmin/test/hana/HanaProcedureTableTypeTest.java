package com.seongmin.test.hana;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HanaProcedureTableTypeTest {

	public static void main(String[] args) {
		test();
	}

	public static void test() {

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = HanaJdbcHelper.getConnection();

			// publisher cursor
			String call = "call \"RESEARCH\".PC_FIND_PUBLISHER(pubid => ?, publisher => null)";
//			String call = "call \"RESEARCH\".PC_FIND_PUBLISHER(?, ?)";

			CallableStatement prepareCall = conn.prepareCall(call);

			ParameterMetaData parameterMetaData = prepareCall.getParameterMetaData();

			int parameterCount = parameterMetaData.getParameterCount();
			String result = null;
			result += "Got " + parameterMetaData.getParameterCount() + " unbound parameters\n";

			for (int i = 0; i < parameterCount; i++) {
				result += (i + 1) + ": " + parameterMetaData.getParameterType(i + 1) + "-"
						+ parameterMetaData.getParameterTypeName(i + 1) + "\n";
			}

			System.out.println(result);

			prepareCall.setInt(1, 4);
			// SAP DBTech JDBC: Internal JDBC error: output parameter at index 2 was not expected.
//			prepareCall.registerOutParameter(2, 1003);

//			rs = prepareCall.executeQuery();
//			HanaJdbcHelper.print(rs, call);
			
			// SAP DBTech JDBC: No output parameter data delivered by call statement.
			Object object = prepareCall.getObject(1);
			System.out.println(object);
			
			if (prepareCall.execute()) {
//				result += "total_input_rows: " + prepareCall.getInt(1) + "\n";

				do {
					rs = prepareCall.getResultSet();
					result += "=========\n";
					result += "ResultSet has: " + rs.getMetaData().getColumnCount() + " column(s)\n";
					// result += rs.getMetaData().getColumnTypeName(1);
					while (rs.next()) {
						for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
							result += rs.getString(i+1) + "\t|\t";
						}
						result += "\n";
					}
					result += "=========\n\n";
				} while (prepareCall.getMoreResults()); // get next resultset
														// from stored procedure
			} else {
				result += "Failed to execute procedure";
			}

			System.out.println(result);
			
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
