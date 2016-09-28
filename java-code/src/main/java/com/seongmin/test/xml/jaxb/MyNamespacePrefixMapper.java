package com.seongmin.test.xml.jaxb;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public class MyNamespacePrefixMapper extends NamespacePrefixMapper {

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		
		if("http://schemas.android.com/apk/res/android".equals(namespaceUri)) {
			return "android";
		}
		
		return null;
	}

}
