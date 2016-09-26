package com.seongmin.test.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class EmpIBatisTest {

	public static void main(String[] args) throws IOException, SQLException {
		
		
		String resource = "ibatis/SqlMapConfig.xml";
		
		Reader reader = Resources.getResourceAsReader(resource);
		
	
		SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		Object in = 900;
		
		Map map = new HashMap();
		map.put("inParam", in);
		
		List salList1 = new ArrayList();
		salList1.add(2000);
		salList1.add(800);
		
		List salList2 = new ArrayList();
		salList2.add(2850);
		salList2.add(2450);
		map.put("salList1", salList1);
		map.put("salList2", salList2);
		
		
		List<emp> empList = sqlMap.queryForList("getEmp", map);
		
		emp emp = null;
		int size = empList.size();
		for(int i=0; i<size; i++) {
			emp = empList.get(i);
			
			System.out.println("id : " + emp.getEmpno()+ ", name : "+ emp.getEname() + ", job : " + emp.getJob() + ", sal : " + emp.getSal());
			
			
		}
		
		
	}
	
}
