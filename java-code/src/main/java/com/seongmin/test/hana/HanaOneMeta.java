package com.seongmin.test.hana;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
  
public class HanaOneMeta {
 
    public static void main(String[] args) throws Exception{
 
        Connection conn = HanaJdbcHelper.getConnection();
        
        ResultSet rs = null;
        String schemaPattern = null;
        String tableNamePattern = null;
        
//        String call = "call \"RESEARCH\".\"PC_FIND_PUBLISHER\"(pubid => 11, publisher => null)";
//        String call = "{call \"RESEARCH\".\"PC_FIND_PUBLISHER\" ( ?, ?)}";
//        CallableStatement prepareCall = conn.prepareCall(call);
//        prepareCall.setInt(1, 1);
//        prepareCall.execute();
//        rs = prepareCall.getResultSet();
//        HanaJdbcHelper.print(rs, "call procedure");
        
//        String sql = "select \"RESEARCH\".\"FUNC_MUL\"(?) from dummy";
//        CallableStatement prepareCall = conn.prepareCall(sql);
//        prepareCall.setInt(1, 1);
//        prepareCall.registerOutParameter(0, 4);
//        prepareCall.execute();
//        
//        int int1 = prepareCall.getInt(1);
//        System.out.println("inti="+int1);
//        rs = prepareCall.getResultSet();
//        HanaJdbcHelper.print(rs, "call procedure");
        
//        PreparedStatement prepareCall = conn.prepareStatement("call \"RESEARCH\".\"PREMIUM_PRICE\"(11, null)");
//      CallableStatement prepareCall = conn.prepareCall("select \"RESEARCH\".\"FUNC_MUL\"(10) FROM DUMMY");
       
        
//        PreparedStatement prepareCall = conn.prepareStatement("select \"RESEARCH\".\"FUNC_MUL\"(10) FROM DUMMY");
//        CallableStatement prepareCall = conn.prepareCall("select \"RESEARCH\".\"FUNC_MUL\"(10) FROM DUMMY");
//        rs = prepareCall.executeQuery();
//        HanaJdbcHelper.print(rs, "call procedure");
        
//        CallableStatement prepareCall = conn.prepareCall("{?=call \"RESEARCH\".\"FUNC_MUL\"(10)}");
//        CallableStatement prepareCall = conn.prepareCall("call \"RESEARCH\".\"FUNC_MUL\"(10, ?)");
//        CallableStatement prepareCall = conn.prepareCall("call FUNC_MUL(10)");
//        CallableStatement prepareCall = conn.prepareCall("call \"RESEARCH\".\"FUNC_MUL\"(INPUT1=>1, OUTPUT1=>null)");
////        prepareCall.setInt(1, 100);
//        rs = prepareCall.executeQuery();
//        HanaJdbcHelper.print(rs, "call procedure");
        
        DatabaseMetaData dbmd = conn.getMetaData();
        System.out.println("CatalogSeparator : "+dbmd.getCatalogSeparator());
        System.out.println("CatalogTerm : "+dbmd.getCatalogTerm());
        System.out.println("DatabaseProductName : "+dbmd.getDatabaseProductName());
        System.out.println("DatabaseProductVersion : "+dbmd.getDatabaseProductVersion());
        System.out.println("DatabaseMajorVersion : "+dbmd.getDatabaseMajorVersion());
        System.out.println("DatabaseMinorVersion : "+dbmd.getDatabaseMinorVersion());
        System.out.println("DefaultTransactionIsolation : "+dbmd.getDefaultTransactionIsolation());
        System.out.println("DriverMajorVersion : "+dbmd.getDriverMajorVersion());
        System.out.println("DriverMinorVersion : "+dbmd.getDriverMinorVersion());
        System.out.println("DriverName : "+dbmd.getDriverName());
        System.out.println("DriverVersion : "+dbmd.getDriverVersion());
        System.out.println("ExtraNameCharacters : "+dbmd.getExtraNameCharacters());
        System.out.println("IdentifierQuoteString : "+dbmd.getIdentifierQuoteString());
        System.out.println("JDBCMajorVersion : "+dbmd.getJDBCMajorVersion());
        System.out.println("JDBCMinorVersion : "+dbmd.getJDBCMinorVersion());
        System.out.println("MaxBinaryLiteralLength : "+dbmd.getMaxBinaryLiteralLength());
        System.out.println("MaxCatalogNameLength : "+dbmd.getMaxCatalogNameLength());
        System.out.println("MaxCharLiteralLength : "+dbmd.getMaxCharLiteralLength());
        System.out.println("MaxColumnNameLength : "+dbmd.getMaxColumnNameLength());
        System.out.println("MaxColumnsInGroupBy : "+dbmd.getMaxColumnsInGroupBy());
        System.out.println("MaxColumnsInIndex : "+dbmd.getMaxColumnsInIndex());
        System.out.println("MaxColumnsInOrderBy : "+dbmd.getMaxColumnsInOrderBy());
        System.out.println("MaxColumnsInSelect : "+dbmd.getMaxColumnsInSelect());
        System.out.println("MaxColumnsInTable : "+dbmd.getMaxColumnsInTable());
        System.out.println("MaxConnections : "+dbmd.getMaxConnections());
        System.out.println("MaxCursorNameLength : "+dbmd.getMaxCursorNameLength());
        System.out.println("MaxIndexLength : "+dbmd.getMaxIndexLength());
        System.out.println("MaxProcedureNameLength : "+dbmd.getMaxProcedureNameLength());
        System.out.println("MaxRowSize : "+dbmd.getMaxRowSize());
        System.out.println("MaxSchemaNameLength : "+dbmd.getMaxSchemaNameLength());
        System.out.println("MaxStatementLength : "+dbmd.getMaxStatementLength());
        System.out.println("MaxStatements : "+dbmd.getMaxStatements());
        System.out.println("MaxTableNameLength : "+dbmd.getMaxTableNameLength());
        System.out.println("MaxTablesInSelect : "+dbmd.getMaxTablesInSelect());
        System.out.println("MaxUserNameLength : "+dbmd.getMaxUserNameLength());
        System.out.println("NumericFunctions : "+dbmd.getNumericFunctions());
        System.out.println("ProcedureTerm : "+dbmd.getProcedureTerm());
        System.out.println("ResultSetHoldability : "+dbmd.getResultSetHoldability());
        System.out.println("RowIdLifetime : "+dbmd.getRowIdLifetime());
        System.out.println("SchemaTerm : "+dbmd.getSchemaTerm());
        System.out.println("SearchStringEscape : "+dbmd.getSearchStringEscape());
        System.out.println("SQLKeywords : "+dbmd.getSQLKeywords());
        System.out.println("SQLStateType : "+dbmd.getSQLStateType());
        System.out.println("StringFunctions : "+dbmd.getStringFunctions());
        System.out.println("SystemFunctions : "+dbmd.getSystemFunctions());
//        System.out.println("Schemas : "+dbmd.getSchemas());
//        System.out.println("TimeDateFunctions : "+dbmd.getTimeDateFunctions());
//        System.out.println("URL : "+dbmd.getURL());
//        System.out.println("UserName : "+dbmd.getUserName());
         
       
        
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        schemaPattern = "RESEARCH";
        tableNamePattern = "DONG_EXAM";
        rs = dbmd.getColumns(null, schemaPattern, tableNamePattern, null);
        HanaJdbcHelper.print(rs, "getColumns=RESEARCH, DONG_EXAM");
        
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        schemaPattern = "SYS";
        tableNamePattern = "ACCESSIBLE_VIEWS";
        rs = dbmd.getColumns(null, schemaPattern, tableNamePattern, null);
        HanaJdbcHelper.print(rs, "getColumns=SYS, ACCESSIBLE_VIEWS");
        
        
        
//        System.out.println("######################################################################");
//        System.out.println("######################################################################");
//        rs = dbmd.getFunctions(null, "TEST", "%");
//        HanaJdbcHelper.print(rs, "getFunctions");
//        
//        System.out.println("######################################################################");
//        System.out.println("######################################################################");
//        Class[] parameterTypes = new Class[]{String.class, String.class, String.class};
//        Method method = dbmd.getClass().getMethod("getFunctions", parameterTypes);
//        rs = (ResultSet) method.invoke(dbmd, null, null, "%");
//        HanaJdbcHelper.print(rs, "getFunctionsForReflections");
//        
//        
//        
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        rs = dbmd.getProcedures(null, null, "%");
        HanaJdbcHelper.print(rs, "getProcedures");
        
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        String stringFunctions = dbmd.getStringFunctions();
        String numericFunctions = dbmd.getNumericFunctions();
        String systemFunctions = dbmd.getSystemFunctions();

        System.out.println("stringFunctions="+stringFunctions);
        System.out.println("numericFunctions="+stringFunctions);
        System.out.println("systemFunctions="+systemFunctions);
//        
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        rs = dbmd.getProcedureColumns(null, null, "PC_FIND_PUBLISHER", null);
        HanaJdbcHelper.print(rs, "getProcedureColumns=PC_FIND_PUBLISHER");
//        
//        
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        rs = dbmd.getProcedureColumns(null, null, "PREMIUM_PRICE", null);
        HanaJdbcHelper.print(rs, "getProcedureColumns=PREMIUM_PRICE");
//        
//        System.out.println("######################################################################");
//        System.out.println("######################################################################");
//        rs = dbmd.getFunctionColumns(null, null, "FUNC_ADD_MUL", null);
//        HanaJdbcHelper.print(rs, "getFunctionColumns=FUNC_ADD_MUL");
//        
        System.out.println("######################################################################");
        System.out.println("######################################################################");
        rs = dbmd.getFunctionColumns(null, null, "FUNC_MUL", null);
        HanaJdbcHelper.print(rs, "getFunctionColumns=FUNC_MUL");
//        
//        System.out.println("######################################################################");
//        System.out.println("######################################################################");
//        rs = dbmd.getFunctionColumns(null, null, "PREMIUM_PRICE", null);
//        HanaJdbcHelper.print(rs, "getFunctionColumns=PREMIUM_PRICE");
//     
//        System.out.println("######################################################################");
//        System.out.println("######################################################################");   
//        rs = dbmd.getSchemas();
//        HanaJdbcHelper.print(rs, "getSchemas");
//        
//        
//        System.out.println("######################################################################");
//        System.out.println("######################################################################");   
//        rs = dbmd.getTables(null, null, null, null);
//        HanaJdbcHelper.print(rs, "getTables");
        
    }
}