package com.seongmin.test.utils.xml;

public class XmlUtilHelper 
{
	// 만약 jaxp1.4가 아닌 다른 버전을 사용한다면 이곳에서 설정을 바꿔야 한다.
	public static void setToUseJaxp14() {

		// System property 의 내용을 가져와 만일 false일 경우 해당 system property를 사용하지 않는다.
		String useJaxpProperty = System.getProperty("com.tobesoft.useJaxpProperty");
		if(useJaxpProperty!=null && useJaxpProperty.equals("false"))
		{
			return;
		}
		
		
		// 다음과 같이 시스템 프로퍼티를 설정하여 특정 클래스를 사용하도록 한다.
		// 이러한 방법이 Java의 Jaxp에 대한 제대로된 사용 방법이다.

		// 여기서 설정할 내용은 jaxp-ri.jar/META-INF/service안에서 찾을 수 있다.
		// java 1.4에 정의된 것.
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		System.setProperty("javax.xml.parsers.SAXParserFactory", "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
		System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
		
		// java 1.5에 정의된 것.
		System.setProperty("javax.xml.stream.XMLEventFactory", "com.sun.xml.internal.stream.events.XMLEventFactoryImpl");
		System.setProperty("javax.xml.stream.XMLInputFactory", "com.sun.xml.internal.stream.XMLInputFactoryImpl");
		System.setProperty("javax.xml.stream.XMLOutputFactory", "com.sun.xml.internal.stream.XMLOutputFactoryImpl");
		System.setProperty("javax.xml.validation.SchemaFactory", "com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory");
		System.setProperty("javax.xml.xpath.XPathFactory", "com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl");
		
	}
}
