package com.seongmin.test.ibatis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.seongmin.test.ParameterSet;
import com.seongmin.test.utils.DevUtil;
import com.seongmin.test.utils.xml.XmlParseException;
import com.seongmin.test.utils.xml.XmlUtil;
import com.tobesoft.xplatform.data.ColumnHeader;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.datatype.PlatformDataType;


public class IterateElementTest {

	public static ParameterSet	parameterSet	= new ParameterSet();

	public static void main(String[] args) throws IOException, TransformerException, TransformerException, XmlParseException {

		String sqlDir = "test/ibatis/iterate.xml";
		String xmlString = DevUtil.getResourceAsString(IterateElementTest.class, sqlDir, "utf-8");

		// Document doc = DevUtil.getResourceAsDocument(sqlDir, MashupDataSet.UTF8);
		Node rootNode = XmlUtil.buildXmlNode(xmlString);
		Node sqlMapNode = rootNode.getNextSibling();

		String str = XmlUtil.transformDOM2String(rootNode);
		Node dttt = XmlUtil.transformString2Document(str);
		System.out.println(XmlUtil.transformDOM2String(dttt));
		DataSet ds = new DataSet("ds");
		ds.addColumn(new ColumnHeader("id", PlatformDataType.STRING));
		ds.addColumn(new ColumnHeader("name", PlatformDataType.STRING));

		int setIdx;
		setIdx = ds.newRow();
		ds.set(setIdx, "id", "1");
		ds.set(setIdx, "name", "park1");

		setIdx = ds.newRow();
		ds.set(setIdx, "id", "2");
		ds.set(setIdx, "name", "park2");

		setIdx = ds.newRow();
		ds.set(setIdx, "id", "3");
		ds.set(setIdx, "name", "park3");


//		NodeList nodeList = sqlMapNode.getChildNodes();
//		Node[] nodes = XmlUtil.getChildNodes(sqlMapNode);
//		for(int i=0; i<nodes.length; i++) {
//			String nodeName = nodes[i].getNodeName();
//			
//			if("select".equals(nodeName) || "insert".equals(nodeName) || "update".equals(nodeName) || "delete".equals(nodeName)) {
//				new IterateElementTest().setIterateList(new HashMap(), nodes[i]);
//				
//				nodes[i].getTextContent();
//				
//				Node[] tmpNodes = XmlUtil.getChildNodes(nodes[i]);
//				for(int j=0; j<tmpNodes.length; j++) {
//					System.out.println(tmpNodes[j].getNodeValue());
//				}
//				
////				System.out.println(XmlUtil.transformDOM2String(nodes[i]));
//				break;
//			}
//		}
		
		
		new IterateElementTest().setIterateList(new HashMap(), sqlMapNode);
		
		System.out.println(XmlUtil.transformDOM2String(sqlMapNode));

	}

	public static final String	ITERATE						= "iterate";
	public static final String	PROPERTY_ATTRIBUTE			= "property";
	public static final String	PROPERTY_VALUE_SEPARATOR	= "*";

	public void setIterateList(Map map, Node node) throws TransformerException {

		Node[] nodes = null;
		nodes = XmlUtil.getChildNodes(node);
		for (int nodeIdx = 0; nodeIdx < nodes.length; nodeIdx++) {
			if (nodes[nodeIdx] == null) {
				return;
			}

			if (nodes[nodeIdx].getNodeType() == Node.ELEMENT_NODE) {

				if (ITERATE.equals(nodes[nodeIdx].getNodeName())) {

					String property = XmlUtil.getAttributeValue(nodes[nodeIdx], PROPERTY_ATTRIBUTE);

					String exceptionMessage = validationIterateProperty(property);
					if (exceptionMessage != null) {
						// 예외 발생 message를 던지자.
						System.out.println(exceptionMessage);
						return;
					}
					
					String[] propertylets = property.split("\\.");
					String dsName = propertylets[0];
					String colName = propertylets[1];
					DataSet ds = parameterSet.getDataSet(dsName);
					
					// Iterate List set
					String replaceString = dsName + PROPERTY_VALUE_SEPARATOR + colName;
					List rowList = new ArrayList();
					int rowCnt = ds.getRowCount();
					for (int rowIdx = 0; rowIdx < rowCnt; rowIdx++) {
						rowList.add(ds.getObject(rowIdx, colName));
					}
					map.put(replaceString, rowList);
					
					// node의 property 값 변경
					XmlUtil.setAttributeValue(nodes[nodeIdx], PROPERTY_ATTRIBUTE, replaceString);
					
					setIterateSubData(nodes[nodeIdx], property);
//					// node 이하 단 변경
//					String xmlString = XmlUtil.transformDOM2String(nodes[nodeIdx], MashupDataSet.UTF8);
//					String replacedXml = changeDot2AtAsterisk(xmlString, property);
//					nodes[nodeIdx] = XmlUtil.transformString2Document(replacedXml);
					
				} else {
					// 다시 찾자!!
					setIterateList(map, nodes[nodeIdx]);
				}

			}

		}
		
		
	}

	public void setIterateSubData(Node node, String property) {
		 
		Node[] nodes = null;
		nodes = XmlUtil.getChildNodes(node);
		for (int nodeIdx = 0; nodeIdx < nodes.length; nodeIdx++) {
			if (nodes[nodeIdx] == null) {
				return;
			}
		
			short nodeType = nodes[nodeIdx].getNodeType();
			if (nodeType == Node.ELEMENT_NODE) {
				
				if(ITERATE.equals(nodes[nodeIdx].getNodeName())) {
					// 예외 발생
					//"iterate elements can not have iterate elements as a child.";
					System.out.println("iterate elements can not have iterate elements as a child.");
					return;
				}
				
				NamedNodeMap namedNodeMap = nodes[nodeIdx].getAttributes();
				if(namedNodeMap==null) { continue; }

				int attrLength = namedNodeMap.getLength();
				for(int attrIdx = 0; attrIdx<attrLength; attrIdx++) {
					Node attributeNode = namedNodeMap.item(attrIdx);
					if(attributeNode==null) { continue; }
					
					String changedValue = changePropertiDot2AtAsterisk(attributeNode.getNodeValue(), property);
					attributeNode.setNodeValue(changedValue);
				}
				
				setIterateSubData(nodes[nodeIdx], property);
			}
			else if(nodeType == Node.TEXT_NODE) {
				String changedValue = changeDot2AtAsterisk(nodes[nodeIdx].getNodeValue(), property);
				nodes[nodeIdx].setNodeValue(changedValue);
			}
			
		}
		
	}

	public String changeDot2AtAsterisk(String sql, String compareValue) {

		final String bindingVariableFindingRegex = "(#("+compareValue+"\\[\\])#)";
		Pattern bindingVariableFindingPattern = Pattern.compile(bindingVariableFindingRegex);
		Matcher matcher = bindingVariableFindingPattern.matcher(sql);
		boolean isPatternFound = matcher.find();

		while (isPatternFound) {
			String bindingVarName = matcher.group(2);

			if (bindingVarName.indexOf(".") > 0) {
				String changedVarName = bindingVarName.replaceFirst("\\.", "*");

				StringBuffer origineVarNamebuffer = new StringBuffer("#");
				origineVarNamebuffer.append(bindingVarName).append("#");

				StringBuffer changedVarnameBuffer = new StringBuffer("#");
				changedVarnameBuffer.append(changedVarName).append("#");

				sql = sql.replace(origineVarNamebuffer.toString(), changedVarnameBuffer.toString());
			}
			isPatternFound = matcher.find();
		}
		
//		if (sql.indexOf(".") > 0) {
//			sql = changePropertiDot2AtAsterisk(sql, compareValue);
//		}

		return sql;

	}
	
	public String changePropertiDot2AtAsterisk(String sql, String compareValue) {

		final String bindingVariableFindingRegex = "(("+compareValue+"\\[\\]))";
		Pattern bindingVariableFindingPattern = Pattern.compile(bindingVariableFindingRegex);
		Matcher matcher = bindingVariableFindingPattern.matcher(sql);
		boolean isPatternFound = matcher.find();

		while (isPatternFound) {
			String bindingVarName = matcher.group(2);

			if (bindingVarName.indexOf(".") > 0) {
				String changedVarName = bindingVarName.replaceFirst("\\.", "*");

				StringBuffer origineVarNamebuffer = new StringBuffer();
				origineVarNamebuffer.append(bindingVarName);

				StringBuffer changedVarnameBuffer = new StringBuffer();
				changedVarnameBuffer.append(changedVarName);

				sql = sql.replace(origineVarNamebuffer.toString(), changedVarnameBuffer.toString());
			}
			isPatternFound = matcher.find();
		}

		return sql;

	}

	public String validationIterateProperty(String property) {

		if (property == null) {
			// 예외발생 iterate 엘리먼트의 property 속성 값을 명시해야 합니다.
			return "Iterate element must specify a property value of the property.";
		}
		property = property.trim();
		
		if (property.indexOf("[]") > -1) {
			// 예외발생 iterate 엘리먼트는 property 값으로 '[]'를 포함할 수 없습니다.
			return "Iterate elements of the property value can not be included '[]'.";
		}
		
		if (property.indexOf(".") < 0) {
			// 예외발생 iterate 엘리먼트는 property 값으로 DataSet.Column 형식을 지원합니다.
			return "Iterate element property value types are supported 'DataSet.Column'.";
		}

		String[] propertylets = property.split("\\.");

		if (propertylets.length != 2) {
			// 예외발생 iterate 엘리먼트는 property 값으로 DataSet.Column 형식을 지원합니다.
			return "Iterate element property value types are supported 'DataSet.Column'.";
		}

		String dsName = propertylets[0];
		String colName = propertylets[1];

		// 값이 데이터셋.컬럼 인지 확인

		DataSet ds = parameterSet.getDataSet(dsName);
		if (ds == null) {
			// 예외발생 iterate 엘리먼트의 property 속성의 데이터셋이 널 입니다.
			return "Iterate the elements of the property specified in the dataset is null. iterate the value of the property : '"
					+ property + "'.";
		}

		if (ds.getColumn(colName) == null) {
			// 예외발생 iterate 엘리먼트의 property 속성의 데이터셋 컬럼이 존재 하지 않습니다.
			return "Iterate the element's property attribute columns in the dataset does not exist. iterate the value of the property : '"
					+ property + "'.";
		}

		return null;
	}

}
