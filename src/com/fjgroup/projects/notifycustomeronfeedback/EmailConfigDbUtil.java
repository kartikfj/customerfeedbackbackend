package com.fjgroup.projects.notifycustomeronfeedback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * @author NUFAIL ACHATH
 */
public class EmailConfigDbUtil {
	    
	 public List<NotifyCustomerOnFeedback> getAllEmailErrorPOs() throws SQLException, NoSuchAlgorithmException, FileNotFoundException, IOException {
		
			Connection myCon=null;
			PreparedStatement myStmt=null;
			ResultSet myRes=null;
			OracleDBConnection orcl=new OracleDBConnection();
			List<NotifyCustomerOnFeedback> listObjs = new ArrayList<>();
			try {
				myCon = orcl.getOracleConn();
		        String sql = "select INVSYSID, INVOICE_DATE, PROJECT, CUSTOMER_EMAIL, SALES_PERSON_NAME, FOOTER_COMP_NAME FROM CUST_FEED_SUMM WHERE (EMAIL_STATUS IS NULL OR EMAIL_STATUS ='N') ";
			   myStmt = myCon.prepareStatement(sql);			  
			   myRes=myStmt.executeQuery();
			   while(myRes.next()) {				   
	                String invoiceId=myRes.getString(1);
				    String invoiceDate=myRes.getString(2);  
				    String projectName=myRes.getString(3);
				    String customerEmailId=myRes.getString(4);
					String salesMenName = myRes.getString(5);
					String footerName = myRes.getString(6);
				    NotifyCustomerOnFeedback tempsoList=new NotifyCustomerOnFeedback(invoiceId,invoiceDate,  projectName, customerEmailId, salesMenName,footerName);
				    listObjs.add(tempsoList);				  
				} 
			   return listObjs;
			   }
			finally { // close jdbc objects
				close(myStmt,myRes);
				orcl.closeOrclConnection(); }
		   }
	 public void sendMailToLogisticsTeam(List<NotifyCustomerOnFeedback> theListData) {	
		 try {
			Properties dbProps= new Properties();			
			dbProps.load(new FileInputStream("//fjtco-ho-svr-02/Bin106/config.properties"));
			String theUrlAddress=dbProps.getProperty("url");
		 for(NotifyCustomerOnFeedback theLData : theListData) {	
		    EmailConfig sslmail = new EmailConfig();
			
			String toAddress = theLData.getCustomerEmailId();
			String today = getCurrentDateString();
			String todayTime = getCurrentDateTimeString();


			System.out.println("Logistic to Division, TO Address : " + toAddress);			
			//String approvalUrl=""+theUrlAddress+"survey-form.jsp?dinvi="+theLData.getInvoiceId()+"&dinevtiad="+formattedDateString(theLData.getInvoiceDate())+"&pnraoejm="+theLData.getProjectName();
			String approvalUrl="https://portal.fjtco.com:8444/fjhr/survey-form.jsp?dinvi="+theLData.getInvoiceId()+"&dinevtiad="+formattedDateString(theLData.getInvoiceDate())+"&pnraoejm="+theLData.getProjectName()+"&salesname="+theLData.getSalesMenName();
//			 String approvalUrl="http://10.10.4.132:8080/FJPORTAL_DEV/survey-form.jsp?dinvi="+theLData.getInvoiceId()+"&dinevtiad="+formattedDateString(theLData.getInvoiceDate())+"&pnraoejm="+theLData.getProjectName()+"&salesname="+theLData.getSalesMenName();

			 //https://portal.fjtco.com:8444/fjhr/survey-form.jsp
		/*	String msg = "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"width:100%; font-family:Helvetica,Arial,sans-serif; background-color:#f5f7fa; padding:20px;\">"
				    + "<tr><td>"
				        + "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"background:#ffffff; border-radius:8px; box-shadow:0 2px 6px rgba(0,0,0,0.1); padding:20px;\">"
				            + "<tr><td style=\"font-size:16px; color:#333;\">"
				                + "Dear <b>Customer</b>,<br/><br/>"
				                + "We value your opinion! Please share your feedback by clicking the button below:<br/><br/>"
				                + "<a href=\"" + approvalUrl + "\" "
				                    + "style=\"display:inline-block; padding:10px 20px; background:#0073e6; color:#ffffff; border-radius:4px; text-decoration:none; font-weight:bold;\">"
				                    + "Give Feedback"
				                + "</a><br/><br/>"
				                + "<h3 style=\"color:#444;\">Project Details</h3>"
				                + "<p><b>SalesMan Name :</b> <span style=\"color:#0073e6;\">" + theLData.getSalesMenName() + "</span><br/>"
				            	+ "<p><b>Invoice Number:</b> <span style=\"color:#0073e6;\">" + theLData.getInvoiceId() + "</span><br/>"
				            	+ "<b>Invoice Date:</b> <span style=\"color:#0073e6;\">" + formattedDateString(theLData.getInvoiceDate()) + "</span><br/>"
				                + "<b>Project Name:</b> <span style=\"color:#0073e6;\">" + theLData.getProjectName() + "</span></p><br/><br/>";
		*/

			 String msg =
					 "<table align='center' cellpadding='0' cellspacing='0' width='100%' style='font-family:Arial, Helvetica, sans-serif; background-color:#f4f6f9; padding:12px;'>"
							 + "  <tr><td align='center'>"
							 + "    <table cellpadding='0' cellspacing='0' width='600' style='background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.1);'>"

							 // Header
							 + "      <tr><td align='center' style='background:#0073e6; padding:12px;'>"
							 + "        <h2 style='margin:0; font-size:22px; color:#ffffff;'>Customer Feedback Request</h2>"
							 + "      </td></tr>"

							 // Body
							 + "      <tr><td style='padding:20px; font-size:15px; color:#333333; line-height:1.6;'>"
							 + "        Dear <b>Customer</b>,<br/><br/>"
							 + "        We value your opinion and like to hear your feedback on our service. "
							 + "        Please click the button below to share your thoughts with us."
							 + "        <br/><br/>"

							 // CTA Button
							 + "        <div style='text-align:center; margin:8px 0;'>"
							 + "          <a href='" + approvalUrl + "' "
							 + "             style='display:inline-block; padding:14px 28px; background:#0073e6; color:#ffffff; text-decoration:none; "
							 + "                    border-radius:10px; font-size:16px; font-weight:bold;'>"
							 + "             Give Feedback"
							 + "          </a>"
							 + "        </div>"

							 // Project Details
							 + "        <h3 style='color:#0073e6; margin-top:8px;'>Project Details</h3>"
							 + "        <table cellpadding='6' cellspacing='0' width='100%' style='border:1px solid #e0e0e0; border-radius:6px;'>"
							 + "          <tr style='background:#f9f9f9;'><td><b>Salesman<br> Name:</b></td><td>" + theLData.getSalesMenName() + "</td></tr>"
							 + "          <tr><td><b>Invoice<br> Number:</b></td><td>" + theLData.getInvoiceId() + "</td></tr>"
							 + "          <tr style='background:#f9f9f9;'><td><b>Invoice <br> Date :</b></td><td>" + formattedDateString(theLData.getInvoiceDate()) + "</td></tr>"
							 + "          <tr><td><b>Project<br> Name:</b></td><td>" + theLData.getProjectName() + "</td></tr>"
							 + "        </table>"
							 + "        <br/><br/>"
							 + "        Thank you for your time and trust in us.  <br/>"
							 + "        <b>"+theLData.getFooterName()+"  Customer Experience Team</b>"
							 + "      </td></tr>"

							 // Footer
							 + "      <tr><td align='center' style='background:#f4f6f9; padding:10px; font-size:10px; color:#777;'>"
							 + "        ©  " + today.substring(today.length()-4)+" "+theLData.getFooterName()+ "  All Rights Reserved."
							 + "      </td></tr>"

							 + "    </table>"
							 + "  </td></tr>"
							 + "</table>";

			 sslmail.setToaddr(toAddress);
			sslmail.setMessageSub("Customer Feedback Form");
			sslmail.setMessagebody(msg);
			int emailstatus = sslmail.sendMail();
			if (emailstatus == 1) {				
				updateEmailStatus(theLData.getInvoiceId());
			}else {
				System.out.println("Error in sending email notification for the logistic team");
			}
		}
		 }catch(Exception e) {
			 System.out.println("Error in sending email notification for the logistic team");
		 }
	 }
	 public int updateEmailStatus(String invNo) {	
			int logType = -2;
			Connection myCon = null;
			PreparedStatement myStmt = null;
			ResultSet myRes = null;
			OracleDBConnection orcl = new OracleDBConnection();
			try {
				myCon = orcl.getOracleConn();
				if (myCon == null)
					return -2;
				String sql = " UPDATE FJPORTAL.CUST_FEED_SUMM   " + " SET EMAIL_STATUS  = 'Y' "
						+ " WHERE INVOICE_NO = ? ";
				myStmt = myCon.prepareStatement(sql);
				myStmt.setString(1, invNo);				
				logType = myStmt.executeUpdate();
				System.out.println("updateEmailStatus LOG VALUE : " + logType);
			} catch (SQLException ex) {
				logType = -2;				
				ex.printStackTrace();
			} finally {				
				close(myStmt, myRes);
				orcl.closeOrclConnection();
			}
			return logType;
		}
	
	 private String getCurrentDateTimeString() {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
			Date date = new Date(System.currentTimeMillis());
			return formatter.format(date);
		}
		private String formattedDateString(String strDate) {
			String out = "-";
			if (strDate == null || strDate.isEmpty() || strDate.equalsIgnoreCase("undefined")) {
				return out;
			} else {
				
			DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
			LocalDateTime dt = LocalDateTime.parse(strDate, parser);
			String formattedDate = dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			return formattedDate;
			}
		}
		private String getCurrentDateString() {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date(System.currentTimeMillis());
			// System.out.println(" date : "+formatter.format(date));
			return formatter.format(date);
		}
	
	private void close(Statement myStmt, ResultSet myRes) {
		try { if(myRes!=null) {myRes.close();} if(myStmt!=null) {myStmt.close();} }
		catch(Exception exc) {exc.printStackTrace();}
    }
	

}
