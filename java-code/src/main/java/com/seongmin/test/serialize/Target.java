package com.seongmin.test.serialize;

import java.io.Serializable;

public class Target implements Serializable {


	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7137179890899893505L;

	public String			str;

	public transient int	ivalue;
	
	public String test;

	public Target(String s, int i)

	{

		str = s;

		ivalue = i;
		
	}
	

}
