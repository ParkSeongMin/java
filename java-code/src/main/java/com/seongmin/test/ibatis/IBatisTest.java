package com.seongmin.test.ibatis;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IBatisTest {

	public static void main(String[] args) throws Exception {
		
		String resource = "ibatis/SqlMapConfig.xml";
		
		Reader reader = Resources.getResourceAsReader(resource);
		
	
		SqlMapClient sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		

		
		
//		List idColRow = new ArrayList();
//		idColRow.add("1");
//		idColRow.add("2");
//		idColRow.add("3");
		
		
//		Map idColRow = new HashMap();
//		idColRow.put("idColRow", "test");
		
//		List nameColRow = new ArrayList();
//		nameColRow.add("홍길동1");
//		nameColRow.add("홍길동2");
//		nameColRow.add("홍길동3");
//		Map nameCol = new HashMap();
//		nameCol.put("name", "1");
		
//		String[] str = new String[]{"id", "name"};
		
//		List ds1 = new ArrayList();
//		ds1.add(idColRow);
//		ds1.add(nameCol);
		
		
		List idColRow = new ArrayList();
		idColRow.add("1");
		idColRow.add("2");
		idColRow.add("3");
		
//		Map map = new HashMap();
//		map.put("name", "홍길동3");
//		map.put("ds1*idColRow", idColRow);
//		map.put("ds1_idColName", idColRow);
		
		
		List<XaTest> xaList = sqlMap.queryForList("getSelect", idColRow);
		
		XaTest xa = null;
		int size = xaList.size();
		for(int i=0; i<size; i++) {
			xa = xaList.get(i);
			
			System.out.println("id : " + xa.getId()+ ", name : "+ xa.getName() + ", age : " + xa.getAge() + ", createData : " + xa.getCreateDate());
			
			
		}
		
		
	}

	/*
	
	 <select id="getSelect" parameterClass="java.util.Map"
		resultMap="xaList">
		select ID, NAME, AGE, CREATE_DATE 
		from XA_TEST
		where 1=1
		<dynamic>
			<isNotNull prepend="and" property="DS1">
				<iterate property="dsAddr">
					age in
					<iterate property="dsAddr[].age" open="(" close=")"
						conjunction=",">
						#dsAddr[].age#

						<iterate property="dsAddrDetail">
							<iterate property="dsAddr[].age"
								conjunction=",">

							</iterate>
						</iterate>
					</iterate>

					address1 in

					<iterate property="dsAddr[].address1" open="("
						close=")" conjunction=",">
						#dsAddr[].address1#
					</iterate>

				</iterate>
			</isNotNull>
		</dynamic>

	</select>
	 
	 
	
	 */
	

}
