/**
 * 아래 XmlNs의 경우 jdk 1.7에서 정상적으로 prefix가 적용 된다.
 * JAXB Marshalling with Custom Namespace Prefixes The Problem
 * http://stackoverflow.com/questions/6586304/jaxb-xml-object-marshalling-without-namespace-prefixes
 */
@XmlSchema (
		namespace="http://schemas.android.com/apk/res/android"
		, elementFormDefault=XmlNsForm.UNQUALIFIED
		, attributeFormDefault=XmlNsForm.QUALIFIED
		, xmlns={
			@XmlNs(namespaceURI="http://schemas.android.com/apk/res/android", prefix="android")
		}
//		namespace="http://schemas.android.com/apk/res/dummy"
//		, elementFormDefault=XmlNsForm.QUALIFIED
//		//, attributeFormDefault=XmlNsForm.QUALIFIED
//		, xmlns={
//			@XmlNs(namespaceURI="http://schemas.android.com/apk/res/android", prefix="android")
//			, @XmlNs(namespaceURI="http://schemas.android.com/apk/res/dummy", prefix="")
//		}
)
package com.seongmin.test.xml.jaxb;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;

// http://schemas.android.com/apk/res/android