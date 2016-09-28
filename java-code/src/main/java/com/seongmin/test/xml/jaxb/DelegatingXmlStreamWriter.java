package com.seongmin.test.xml.jaxb;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class DelegatingXmlStreamWriter implements XMLStreamWriter {

	private XMLStreamWriter delegate;

	public DelegatingXmlStreamWriter(XMLStreamWriter writer) {
		this.delegate = writer;
	}
	
	public void close() throws XMLStreamException {
		delegate.close();
	}

	public void flush() throws XMLStreamException {
		delegate.flush();
	}

	public NamespaceContext getNamespaceContext() {
		return delegate.getNamespaceContext();
	}

	public String getPrefix(String arg0) throws XMLStreamException {
		return delegate.getPrefix(arg0);
	}

	public Object getProperty(String arg0) throws IllegalArgumentException {
		return delegate.getProperty(arg0);
	}

	public void setDefaultNamespace(String arg0) throws XMLStreamException {
		delegate.setDefaultNamespace(arg0);
	}

	public void setNamespaceContext(NamespaceContext arg0)
			throws XMLStreamException {
		delegate.setNamespaceContext(arg0);
	}

	public void setPrefix(String arg0, String arg1) throws XMLStreamException {
		delegate.setPrefix(arg0, arg1);
	}

	public void writeAttribute(String arg0, String arg1, String arg2,
			String arg3) throws XMLStreamException {
		delegate.writeAttribute(arg0, arg1, arg2, arg3);
	}

	public void writeAttribute(String arg0, String arg1, String arg2)
			throws XMLStreamException {
		delegate.writeAttribute(arg0, arg1, arg2);
	}

	public void writeAttribute(String arg0, String arg1)
			throws XMLStreamException {
		delegate.writeAttribute(arg0, arg1);
	}

	public void writeCData(String arg0) throws XMLStreamException {
		delegate.writeCData(arg0);
	}

	public void writeCharacters(char[] arg0, int arg1, int arg2)
			throws XMLStreamException {
		delegate.writeCharacters(arg0, arg1, arg2);
	}

	public void writeCharacters(String arg0) throws XMLStreamException {
		delegate.writeCharacters(arg0);
	}

	public void writeComment(String arg0) throws XMLStreamException {
		delegate.writeComment(arg0);
	}

	public void writeDTD(String arg0) throws XMLStreamException {
		delegate.writeDTD(arg0);
	}

	public void writeDefaultNamespace(String arg0) throws XMLStreamException {
		delegate.writeDefaultNamespace(arg0);
	}

	public void writeEmptyElement(String arg0, String arg1, String arg2)
			throws XMLStreamException {
		delegate.writeEmptyElement(arg0, arg1, arg2);
	}

	public void writeEmptyElement(String arg0, String arg1)
			throws XMLStreamException {
		delegate.writeEmptyElement(arg0, arg1);
	}

	public void writeEmptyElement(String arg0) throws XMLStreamException {
		delegate.writeEmptyElement(arg0);
	}

	public void writeEndDocument() throws XMLStreamException {
		delegate.writeEndDocument();
	}

	public void writeEndElement() throws XMLStreamException {
		delegate.writeEndElement();
	}

	public void writeEntityRef(String arg0) throws XMLStreamException {
		delegate.writeEntityRef(arg0);
	}

	public void writeNamespace(String arg0, String arg1)
			throws XMLStreamException {
		delegate.writeNamespace(arg0, arg1);
	}

	public void writeProcessingInstruction(String arg0, String arg1)
			throws XMLStreamException {
		delegate.writeProcessingInstruction(arg0, arg1);
	}

	public void writeProcessingInstruction(String arg0)
			throws XMLStreamException {
		delegate.writeProcessingInstruction(arg0);
	}

	public void writeStartDocument() throws XMLStreamException {
		delegate.writeStartDocument();
	}

	public void writeStartDocument(String arg0, String arg1)
			throws XMLStreamException {
		delegate.writeStartDocument(arg0, arg1);
	}

	public void writeStartDocument(String arg0) throws XMLStreamException {
		delegate.writeStartDocument(arg0);
	}

	public void writeStartElement(String arg0, String arg1, String arg2)
			throws XMLStreamException {
		delegate.writeStartElement(arg0, arg1, arg2);
	}

	public void writeStartElement(String arg0, String arg1)
			throws XMLStreamException {
		delegate.writeStartElement(arg0, arg1);
	}

	public void writeStartElement(String arg0) throws XMLStreamException {
		delegate.writeStartElement(arg0);
	}
	
	

}
