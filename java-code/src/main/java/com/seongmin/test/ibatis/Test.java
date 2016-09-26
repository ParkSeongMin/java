package com.seongmin.test.ibatis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		
		
		String xml = "ddd #test.dd]# ddd";
		
		System.out.println(xml.indexOf("[]"));
		
		xml.replace("#test.dd[]#", " sdf ");
		
		System.out.println(changeDot2AtAsterisk(xml, "test.dd"));
		
		
	}
	
	
	public static String changeDot2AtAsterisk(String sql, String compareValue) {

		final String bindingVariableFindingRegex = "(#("+compareValue+"\\[\\])#)";
		Pattern bindingVariableFindingPattern = Pattern.compile(bindingVariableFindingRegex);
		Matcher matcher = bindingVariableFindingPattern.matcher(sql);
		boolean isPatternFound = matcher.find();

		while (isPatternFound) {
			String bindingVarName = matcher.group(2);

			if (bindingVarName.indexOf(".") > 0) {
				String changedVarName = bindingVarName.replaceFirst("\\.", "*");

				StringBuffer origineVarNamebuffer = new StringBuffer("#");
				origineVarNamebuffer.append(bindingVarName).append("#");

				StringBuffer changedVarnameBuffer = new StringBuffer("#");
				changedVarnameBuffer.append(changedVarName).append("#");

				sql = sql.replace(origineVarNamebuffer.toString(), changedVarnameBuffer.toString());
			}
			isPatternFound = matcher.find();
		}
		
		if (sql.indexOf(".") > 0) {
			sql = changePropertiDot2AtAsterisk(sql, compareValue);
		}

		return sql;

	}
	
	public static String changePropertiDot2AtAsterisk(String sql, String compareValue) {

		final String bindingVariableFindingRegex = "(\"("+compareValue+")\\[\\]\")";
		Pattern bindingVariableFindingPattern = Pattern.compile(bindingVariableFindingRegex);
		Matcher matcher = bindingVariableFindingPattern.matcher(sql);
		boolean isPatternFound = matcher.find();

		while (isPatternFound) {
			String bindingVarName = matcher.group(2);

			if (bindingVarName.indexOf(".") > 0) {
				String changedVarName = bindingVarName.replaceFirst("\\.", "*");

				StringBuffer origineVarNamebuffer = new StringBuffer("\"");
				origineVarNamebuffer.append(bindingVarName).append("\"");

				StringBuffer changedVarnameBuffer = new StringBuffer("\"");
				changedVarnameBuffer.append(changedVarName).append("\"");

				sql = sql.replace(origineVarNamebuffer.toString(), changedVarnameBuffer.toString());
			}
			isPatternFound = matcher.find();
		}

		return sql;

	}
	
	
}
