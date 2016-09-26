package com.seongmin.test.utils.xml;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XmlPrettyPrinter {

	public static String getPrettyXml(String xml) {
		return getPrettyXml(xml, XmlUtil.DEFAULT_CHARSET);
	}
	
	public static String getPrettyXml(String xml, String charset) {

		if(xml==null) { return null; }
		
		String orgXml = xml;
		// white space는 처리해 주지 않는다. 손수 처리하자.
		// TODO : CDATA의 경우 건드려서는 안된다. 처리하자.
		xml = xml.replaceAll("\t", "    ");
		xml = xml.replaceAll(">[ ]*\r\n", ">");
		xml = xml.replaceAll(">[ ]*\r", ">");
		xml = xml.replaceAll(">[ ]*\n", ">");
		xml = xml.replaceAll(">[ ]*<", "\\>\\<");

		try {
			
			// refer from http://xrath.com/blog/entry/164
			Document document = XmlUtil.transformString2Document(xml, charset);

			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// 안먹네.. 우짜나.
//			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			StreamResult streamResult = new StreamResult(outputStream);
			transformer.transform(new DOMSource(document), streamResult);
			xml = outputStream.toString(charset);
			
			// 첫줄에  standalone="no"이 항상 붙는다.
			// 일딴 떨구자.
			// TODO : do it with transformer
			xml = xml.replace(" standalone=\"no\"", "");
			return xml;
			
		} catch (TransformerException ignore) {
			// do nothing
		
		} catch (UnsupportedEncodingException ignore) {
			// do nothing
		} catch (XmlParseException e) {
			// do nothing
		}

		// 예외가 던져지면 딴 거 없이 그냥 원래 xml을 반환하자.
		return orgXml;

	}

	

	
}
