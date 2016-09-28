package com.seongmin.test.xml.stax;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.seongmin.test.xml.Employee;

//https://www.javacodegeeks.com/2013/05/parsing-xml-using-dom-sax-and-stax-parser-in-java.html
public class StaxParserSample {

	public static void main(String[] args) throws XMLStreamException {
		
		List<Employee> empList = null;
		Employee currEmp = null;
		String tagContent = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream("com/seongmin/test/xml/employees.xml"));

		while (reader.hasNext()) {
			int event = reader.next();

			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if ("employee".equals(reader.getLocalName())) {
					currEmp = new Employee();
					currEmp.setId(reader.getAttributeValue(0));
				}
				if ("employees".equals(reader.getLocalName())) {
					empList = new ArrayList<Employee>();
				}
				break;

			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
				break;

			case XMLStreamConstants.END_ELEMENT:

				String qName = reader.getLocalName();
				if ("firstName".equals(qName)) {
					currEmp.setFirstName(tagContent);
				} else if ("lastName".equals(qName)) {
					currEmp.setLastName(tagContent);
				} else if ("location".equals(qName)) {
					currEmp.setLocation(tagContent);
				} else if ("employee".equals(qName)) {
					// Add the employee to list once end tag is found
					empList.add(currEmp);
				}
				break;

			case XMLStreamConstants.START_DOCUMENT:
				empList = new ArrayList<Employee>();
				break;
			}

		}

		// Print the employee list populated from XML
		for (Employee emp : empList) {
			System.out.println(emp);
		}

	}
}
