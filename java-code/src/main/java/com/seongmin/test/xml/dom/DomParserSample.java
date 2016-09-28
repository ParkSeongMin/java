package com.seongmin.test.xml.dom;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.seongmin.test.xml.Employee;

//https://www.javacodegeeks.com/2013/05/parsing-xml-using-dom-sax-and-stax-parser-in-java.html
public class DomParserSample {
	
	public static void main(String[] args) throws Exception {
		// Get the DOM Builder Factory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Get the DOM Builder
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Load and Parse the XML document
		// document contains the complete XML as a Tree.
		Document document = builder.parse(ClassLoader.getSystemResourceAsStream("com/seongmin/test/xml/employees.xml"));

		List<Employee> empList = new ArrayList<Employee>();

		// Iterating through the nodes and extracting the data.
		NodeList nodeList = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {

			// We have encountered an <employee> tag.
			Node node = nodeList.item(i);
			if (node instanceof Element) {
				Employee emp = new Employee();
				emp.setId(node.getAttributes().getNamedItem("id")
						.getNodeValue());

				NodeList childNodes = node.getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node cNode = childNodes.item(j);

					// Identifying the child tag of employee encountered.
					if (cNode instanceof Element) {
						// jdk 1.7 이하
						//String content = cNode.getLastChild().getTextContent().trim();
						
						String content = cNode.getLastChild().getNodeValue();
						String nodeName = cNode.getNodeName();
						if ("firstName".equals(nodeName)) {
							emp.setFirstName(content);
						} else if ("lastName".equals(nodeName)) {
							emp.setLastName(content);
						} else if ("location".equals(nodeName)) {
							emp.setLocation(content);
						}
					}
				}
				empList.add(emp);
			}

		}

		// Printing the Employee list populated.
		for (Employee emp : empList) {
			System.out.println(emp);
		}

	}
}
