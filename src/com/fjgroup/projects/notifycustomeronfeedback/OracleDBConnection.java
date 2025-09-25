package com.fjgroup.projects.notifycustomeronfeedback;

import java.sql.*;

public class OracleDBConnection {

    Connection myConn=null;

	public OracleDBConnection() {}

     public synchronized Connection getOracleConn() {


		try{
			//Properties dbProps= new Properties();
			 //dbProps.load(new FileInputStream("D:/FJSOBDGAPRV/orcl.properties"));
			//dbProps.load(new FileInputStream("orcl.properties"));
			//dbProps.load(new FileInputStream("//fjtco-ho-svr-02/FJSOBDGAPRV/orcl.properties"));
			//String theUser=dbProps.getProperty("user");
			//String thePaswd=dbProps.getProperty("password");
			//String theUrl=dbProps.getProperty("dburl");
			String theUser = "FJPORTAL";
			String thePaswd = "KHLATAM123";
			String theUrl = "jdbc:oracle:thin:@10.10.0.46:1521:orcl";


	        //1. Get Connection to database
			 myConn = DriverManager.getConnection(theUrl,theUser,thePaswd);
	    }catch(Exception e){
	        System.out.println("Couldn't create connection to oracle db ." + e.getMessage());
	    }

	return myConn;

	}
	public synchronized void closeOrclConnection(){
        try{
        	myConn.close();
            System.out.println("closed connection to oracle database.");

        }catch(Exception e){
            System.out.println("error in close oracle db connection or context.");
        }
    }


}
