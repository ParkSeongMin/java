package com.seongmin.test.jco;
import java.util.Properties;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SapJcpConnector {

	private static final int					DEFAULT_MAX_COUNT			= 5;
	private static SapDestinationDataProvider	sapDestinationDataProvider	= null;
	private static boolean						init						= false;

	public SapJcpConnector() {

		if (!init) {
			sapDestinationDataProvider = new SapDestinationDataProvider();
			com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(sapDestinationDataProvider);

			init = true;
		}

	}

	public JCoDestination connect(String desinationName, Properties env) throws JCoException {
		sapDestinationDataProvider.changeProperties(desinationName, env);
		JCoDestination destination = JCoDestinationManager.getDestination(desinationName);

		return destination;
	}
	
	public void test01() throws JCoException {
		
		Properties properties = getProperties();
		
		
		//JCoDestination is the logic address of an ABAP system and ...
		JCoDestination destination = connect("ABAP_AS_WITH_POOL", properties);
		// ... it always has a reference to a metadata repository
		JCoFunction function = destination.getRepository().getFunction("STFC_CONNECTION");
		if(function == null)
		    throw new RuntimeException("BAPI_COMPANYCODE_GETLIST not found in SAP.");

		//JCoFunction is container for function values. Each function contains separate
		//containers for import, export, changing and table parameters.
		//To set or get the parameters use the APIS setValue() and getXXX(). 
		function.getImportParameterList().setValue("REQUTEXT", "Hello SAP");

		try
		{
		    //execute, i.e. send the function to the ABAP system addressed 
		    //by the specified destination, which then returns the function result.
		    //All necessary conversions between Java and ABAP data types
		    //are done automatically.
		    function.execute(destination);
		}
		catch(AbapException e)
		{
		    System.out.println(e.toString());
		    return;
		} catch (JCoException e) {
			  System.out.println(e.toString());
			   return;
		}

		System.out.println("STFC_CONNECTION finished:");
		System.out.println(" Echo: " + function.getExportParameterList().getString("ECHOTEXT"));
		System.out.println(" Response: " + function.getExportParameterList().getString("RESPTEXT"));
		System.out.println();
		
		
	}
	
	public Properties getProperties() {

		Properties connectionProperties = new Properties();
		connectionProperties.setProperty(DestinationDataProvider.JCO_ASHOST,"ip");
		connectionProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
		connectionProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "100");
		connectionProperties.setProperty(DestinationDataProvider.JCO_USER,"user");
		connectionProperties.setProperty(DestinationDataProvider.JCO_PASSWD,"pw");
		connectionProperties.setProperty(DestinationDataProvider.JCO_LANG, "KO");
		connectionProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,	"10");
		connectionProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");
		
		return connectionProperties;
	}
	
}
