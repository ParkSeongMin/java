package com.seongmin.test.xml.sax;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.seongmin.test.xml.Employee;

// https://www.javacodegeeks.com/2013/05/parsing-xml-using-dom-sax-and-stax-parser-in-java.html
public class SaxParserSample {
	public static void main(String[] args) throws Exception {
		SAXParserFactory parserFactor = SAXParserFactory.newInstance();
		SAXParser parser = parserFactor.newSAXParser();
		SAXHandler handler = new SAXHandler();
		parser.parse(ClassLoader.getSystemResourceAsStream("com/seongmin/test/xml/employees.xml"), handler);

		// Printing the list of employees obtained from XML
		for (Employee emp : handler.empList) {
			System.out.println(emp);
		}
	}
}

/**
 * The Handler for SAX Events.
 */
class SAXHandler extends DefaultHandler {

	List<Employee> empList = new ArrayList<Employee>();
	Employee emp = null;
	String content = null;

	@Override
	// Triggered when the start of tag is found.
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		// Create a new Employee object when the start tag is found
		if ("employee".equals(qName)) {
			emp = new Employee();
			emp.setId(attributes.getValue("id"));
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		// For all other end tags the employee has to be updated.
		if ("firstName".equals(qName)) {
			emp.setFirstName(content);
		} else if ("lastName".equals(qName)) {
			emp.setLastName(content);
		} else if ("location".equals(qName)) {
			emp.setLocation(content);
		} else if ("employee".equals(qName)) {
			// Add the employee to list once end tag is found
			empList.add(emp);
		}

	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content = String.copyValueOf(ch, start, length).trim();
	}

}