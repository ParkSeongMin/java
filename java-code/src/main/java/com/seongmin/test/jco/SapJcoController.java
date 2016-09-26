package com.seongmin.test.jco;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.conn.jco.JCoException;



public class SapJcoController extends HttpServlet {


	@Override
	public void init(ServletConfig servletConfig) throws ServletException {

	}
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SapJcpConnector connector = new SapJcpConnector();
		try {
			connector.test01();
		} catch (JCoException e) {
			throw new ServletException(e.getMessage());
		}
		
		PrintWriter pw = response.getWriter();
		pw.write("success");
		pw.flush();
		pw.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		service(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
}
