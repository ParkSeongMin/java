package com.seongmin.test.xml.jaxb;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class AndroidManifestParserSample {

	public static void main(String[] args) throws Exception {
		marshal();
		System.out.println();
		unmarshal();
	}

	private static void marshal() throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(Manifest.class);
		Manifest manifest = new Manifest();
		manifest.setPackageName("com.seongmin");
		manifest.setVersionCode(1);
		manifest.setVersionName("1.0.0");
		manifest.setApplication(new Application());

		UsesPermission usesPermission = new UsesPermission();
		usesPermission.setName("test");
		List<UsesPermission> list = new ArrayList<UsesPermission>();
		list.add(usesPermission);
		manifest.setUsesPermission(list);
		
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		
//		marshaldefault(marshaller, manifest);
		marshalWithMapper(marshaller, manifest);
//		marshalWithStreamWriter(marshaller, manifest);
//		marshalWithEventWriter(marshaller, manifest);
//		marshalWithDelegatingStreamWriter(marshaller, manifest);
//		marshalWithNamespaceContext(marshaller, manifest);
	}

	private static void marshaldefault(Marshaller marshaller, Manifest manifest) throws Exception {
		marshaller.marshal(manifest, System.out);
//		<!-- https://mvnrepository.com/artifact/com.sun.xml.bind/jaxb-impl -->
//		<dependency>
//			<groupId>com.sun.xml.bind</groupId>
//			<artifactId>jaxb-impl</artifactId>
//			<version>2.2.7</version>
//		</dependency>
//		saxparser의 security exception 발생하게 되면 xerces 최신 라이브러리로 교체
//		<!-- https://mvnrepository.com/artifact/xerces/xercesImpl -->
//		<dependency>
//		    <groupId>xerces</groupId>
//		    <artifactId>xercesImpl</artifactId>
//		    <version>2.11.0</version>
//		</dependency>
	}
	
	private static void marshalWithMapper(Marshaller marshaller, Manifest manifest) throws Exception {
		// jre가 1.6이하이며, sun sdk에서만 돌아가게 해야 할 것이다. 
		marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new MyNamespacePrefixMapper());
		marshaller.marshal(manifest, System.out);
	}

	private static void marshalWithStreamWriter(Marshaller marshaller, Manifest manifest) throws XMLStreamException, FactoryConfigurationError, JAXBException {
		// CASE 2
		XMLStreamWriter writer = null;
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
		
		writer = factory.createXMLStreamWriter(System.out);
//		writer.setDefaultNamespace("http://schemas.android.com/apk/res/android");
//		writer.setPrefix("android", "http://schemas.android.com/apk/res/android");
		marshaller.marshal(manifest, writer);
	}
	
	private static void marshalWithDelegatingStreamWriter(Marshaller marshaller, Manifest manifest) throws XMLStreamException, FactoryConfigurationError, JAXBException {
		// CASE 2
		XMLStreamWriter writer = null;
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
		
		writer = factory.createXMLStreamWriter(System.out);
		writer = new AndroidXmlStreamWriter(writer);
		writer.setPrefix("android", "http://schemas.android.com/apk/res/android");
		marshaller.marshal(manifest, writer);
	}
	
	private static void marshalWithEventWriter(Marshaller marshaller, Manifest manifest) throws XMLStreamException, FactoryConfigurationError, JAXBException {
		// CASE 2
		XMLEventWriter writer = null;
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
		writer = factory.createXMLEventWriter(System.out);
		writer.setPrefix("android", "http://schemas.android.com/apk/res/android");
		marshaller.marshal(manifest, writer);
	}


	private static void marshalWithNamespaceContext(Marshaller marshaller, Manifest manifest)  throws XMLStreamException, FactoryConfigurationError, JAXBException {

		// CASE 3
		XMLStreamWriter writer = null;
		writer = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
		
//		NamespaceContextImpl namespaceContextImpl = new NamespaceContextImpl();
//		namespaceContextImpl.bindPrefixToNamespaceURI("android", "http://schemas.android.com/apk/res/android");
//		writer.setNamespaceContext(namespaceContextImpl);
		
		writer.setNamespaceContext(new NamespaceContext() {
			
			@Override
			public Iterator getPrefixes(String arg0) {
				System.err
						.println("AndroidManifestParserSample.marshalWithNamespaceContext(...).new NamespaceContext() {...}.getPrefixes()");
				return null;
			}
			
			@Override
			public String getPrefix(String arg0) {
				System.err
						.println("AndroidManifestParserSample.marshalWithNamespaceContext(...).new NamespaceContext() {...}.getPrefix()=" + arg0);
				if("http://schemas.android.com/apk/res/android".equals(arg0)) {
					return "android";
				}
				return "test";
			}
			
			@Override
			public String getNamespaceURI(String arg0) {
				System.err
						.println("AndroidManifestParserSample.marshalWithNamespaceContext(...).new NamespaceContext() {...}.getNamespaceURI()");
				return "http://schemas.android.com/apk/res/android";
			}
		});		
		
		marshaller.marshal(manifest, writer);
	}

	private static void marshalWithString() {
		// in spring oxm
		// http://stackoverflow.com/questions/3289644/define-spring-jaxb-namespaces-without-using-namespaceprefixmapper
		// Jaxb2Marshaller mar = new Jaxb2Marshaller();
		// mar.setClassesToBeBound(Manifest.class);
		// mar.marshal(manifest, new StreamResult(System.out));
//		<!-- https://mvnrepository.com/artifact/org.springframework/spring-oxm -->
//		<dependency>
//			<groupId>org.springframework</groupId>
//			<artifactId>spring-oxm</artifactId>
//			<version>${org.springframework-version}</version>
//		</dependency>
	}

	private static void unmarshal() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Manifest.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		Manifest manifest = (Manifest) unmarshaller
				.unmarshal(new File(
						"src/main/java/com/seongmin/test/xml/jaxb/AndroidManifest.xml"));
		System.out.println(manifest.toString());
	}

}
