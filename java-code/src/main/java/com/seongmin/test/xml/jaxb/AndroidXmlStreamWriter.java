package com.seongmin.test.xml.jaxb;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class AndroidXmlStreamWriter extends IndentingXMLStreamWriter {

	public AndroidXmlStreamWriter(XMLStreamWriter writer) {
		super(writer);
	}

	public void writeStartDocument() throws XMLStreamException {
		super.writeStartDocument("UTF-8", "1.0");
	}
	
	@Override
	public void writeNamespace(String arg0, String arg1)
			throws XMLStreamException {
		if("".equals(arg0) && arg1.equals("")) {
			arg0 = "android";
			arg1 = "http://schemas.android.com/apk/res/android";
		}
		super.writeNamespace(arg0, arg1);
	}
}
