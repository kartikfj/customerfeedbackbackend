package com.fjgroup.projects.notifycustomeronfeedback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
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
			/*try {
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
			   }*/
		 try {
			 myCon = orcl.getOracleConn();

			 String sql = "SELECT " +
					 "S.INVSYSID, " +
					 "S.INVOICE_DATE, " +
					 "S.PROJECT, " +
					 "S.CUSTOMER_EMAIL, " +
					 "S.SALES_PERSON_NAME, " +
					 "S.FOOTER_COMP_NAME, " +
					 "M.COMPNAME, " +
					 "M.ADDRESS, " +
					 "M.FACEBOOK, " +
					 "M.INSTAGRAM, " +
					 "M.WEBSITE, " +
					 "M.IMAGEURL, " +
                     "S.INVOICE_CODE, "+
					 "S.INVOICE_NO, "+
					 "S.COMP_CODE " +
					 "FROM FJPORTAL.CUST_FEED_SUMM S " +
					 "LEFT JOIN FJPORTAL.CUST_FEED_MASTER M " +
					 "ON S.COMP_CODE = M.COMPCODE " +
					 "WHERE S.EMAIL_STATUS IS NULL OR S.EMAIL_STATUS = 'N'";

			 myStmt = myCon.prepareStatement(sql);
			 myRes = myStmt.executeQuery();

			 while (myRes.next()) {
				 String invoiceId = myRes.getString("INVSYSID");
				 String invoiceDate = myRes.getString("INVOICE_DATE");
				 String projectName = myRes.getString("PROJECT");
				 String customerEmailId = myRes.getString("CUSTOMER_EMAIL");
				 String salesMenName = myRes.getString("SALES_PERSON_NAME");
				 String footerName = myRes.getString("FOOTER_COMP_NAME");

				 // These can be null — no issue
				 String compName = myRes.getString("FOOTER_COMP_NAME");
				 String address = myRes.getString("ADDRESS");
				 String facebook = myRes.getString("FACEBOOK");
				 String instagram = myRes.getString("INSTAGRAM");
				 String website = myRes.getString("WEBSITE");
				 String imageUrl = myRes.getString("IMAGEURL");
				 String invoiceNO = myRes.getString("INVOICE_NO");
				 String invoiceCode = myRes.getString("INVOICE_CODE");
				 String compCode = myRes.getString("COMP_CODE");

				 NotifyCustomerOnFeedback tempObj = new NotifyCustomerOnFeedback(
						 invoiceId,
						 invoiceDate,
						 projectName,
						 customerEmailId,
						 salesMenName,
						 footerName,
						 compName,
						 address,
						 facebook,
						 instagram,
						 website,
						 imageUrl,
						 compCode,
						 invoiceNO,
						 invoiceCode
				 );

				 listObjs.add(tempObj);
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
			String approvalUrl="https://portal.fjtco.com:8444/fjhr/survey-form.jsp?dinvi="+theLData.getInvoiceCode()+"-"+theLData.getInvoiceNumber()+"&dinvisy="+theLData.getInvoiceId()+"&dinevtiad="+formattedDateString(theLData.getInvoiceDate())+"&pnraoejm="+theLData.getProjectName()+"&salesname="+theLData.getSalesMenName()+"&compCode="+theLData.getCompCode()+"&compName="+theLData.getCompName();
			// String approvalUrl="http://10.10.4.132:8080/FJPORTAL_DEV/survey-form.jsp?dinvi="+theLData.getInvoiceId()+"&dinevtiad="+formattedDateString(theLData.getInvoiceDate())+"&pnraoejm="+theLData.getProjectName()+"&salesname="+theLData.getSalesMenName();
			//kk String approvalUrl="http://10.10.4.132:8080/FJPORTAL_DEV/survey-form.jsp?dinvi="+theLData.getInvoiceCode()+"-"+theLData.getInvoiceNumber()+"&dinvisy="+theLData.getInvoiceId()+"&dinevtiad="+formattedDateString(theLData.getInvoiceDate())+"&pnraoejm="+theLData.getProjectName()+"&salesname="+theLData.getSalesMenName()+"&compCode="+theLData.getCompCode()+"&compName="+theLData.getCompName();
			// String approvalUrl="https://portal.fjtco.com:8444/fjhr/survey-form.jsp?dinvi="+theLData.getInvoiceId()+"&dinevtiad="+formattedDateString(theLData.getInvoiceDate())+"&pnraoejm="+theLData.getProjectName()+"&salesname="+theLData.getSalesMenName()+"&compCode="+theLData.getCompCode()+"&compName="+theLData.getCompName();

			 String companyName = theLData.getCompCode();  // Example: from DB
			 String logoUrl = "https://www.faisaljassim.ae/images/white-logo.png";

			 if (companyName != null) {
				 companyName = companyName.trim();
				 //logoUrl = "https://www.faisaljassim.ae/images/white-logo.png";
				 if (companyName.contains("ACL")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/image12.jpg";
				 } else if (companyName.contains("EME")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/ec.png";
				 }
				/* else if (companyName.contains("META")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/metaform.png";
				 }
				 else if (companyName.contains("MPD")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/mpd.jpg";
				 }
				 else if (companyName.contains("DCS")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/dcserve.png";
				 }
				 else if (companyName.contains("FJC")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/fjcare.png";
				 }
				 else if (companyName.contains("FJES")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/fjenergy.jpg";
				 }
				 else if (companyName.contains("FJIPP")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/aquaflow.png";
				 }
				 else if (companyName.contains("ADL")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/alphaducts.png";
				 }
				 else if (companyName.contains("FT")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/ft.png";
				 }
				 else if (companyName.contains("ECS")) {
					 logoUrl = "https://myfirstangulareg.s3.us-east-2.amazonaws.com/company-logo/ecs.jpg";
				 }*/
				 else {
					 // Default or fallback logo
					 logoUrl = "https://www.faisaljassim.ae/images/white-logo.png";
				 }
			 }




			 String msg =
					 "<table align='center' cellpadding='0' cellspacing='0' width='100%' style='font-family:Arial, Helvetica, sans-serif; background-color:#f4f6f9; padding:12px;'>"
							 + "  <tr><td align='center'>"
							 + "    <table cellpadding='0' cellspacing='0' width='600' style='background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.1);'>"

							 // Header
						/*	 + "      <tr><td align='center' style='background:#0073e6; padding:12px;'>"
							 + "        <h2 style='margin:0; font-size:22px; color:#ffffff;'>Customer Feedback Request</h2>"
							 + "      </td></tr>"*/
							 + "  <tr>"
							 + "    <td align='center' style='background:#0073e6; padding:12px;'>"
							 + "      <table width='100%' cellpadding='0' cellspacing='0' style='max-width:600px;'>"
							 + "        <tr>"
							 + "          <td align='left' style='width:50px; margin-right:20px;' >"
							 + "            <img src='" + logoUrl + "' alt='Company Logo' style='height:50px; display:block;'>"

							 + "          </td>"
							 + "          <td align='left'>"
							 + "            <h2 style='margin:0;margin-left:20px; font-size:22px; color:#ffffff;'>Customer Feedback Request</h2>"
							 + "          </td>"
							 + "        </tr>"
							 + "      </table>"
							 + "    </td>"
							 + "  </tr>"

							 // Body
							 + "      <tr><td style='padding:20px; font-size:15px; color:#333333; line-height:1.6;'>"
							 + "        Dear <b>Customer</b>,<br/><br/>"
							 + "        We value your opinion and like to hear your feedback on our service. "

							 + "        <br/><br/>"

							/* // CTA Button
							 + "        <div style='text-align:center; margin:8px 0;'>"
							 + "          <a href='" + approvalUrl + "' "
							 + "             style='display:inline-block; padding:14px 28px; background:#0073e6; color:#ffffff; text-decoration:none; "
							 + "                    border-radius:10px; font-size:16px; font-weight:bold;'>"
							 + "             Give Feedback"
							 + "          </a>"
							 + "        </div>"*/
							 // 5-Star Rating Block
							/* + "        <div style='text-align:center; margin:15px 0;'>"
							 + "          <p style='font-size:16px; color:#333;'>How would you rate our service?</p>"
							 + "          <div>"
							 + "            <a href='" + approvalUrl + "&rating=1&email=" + toAddress + "' style='text-decoration:none; font-size:30px;'>⭐</a>"
							 + "            <a href='" + approvalUrl + "&rating=2&email=" + toAddress + "' style='text-decoration:none; font-size:30px;'>⭐</a>"
							 + "            <a href='" + approvalUrl + "&rating=3&email=" + toAddress + "' style='text-decoration:none; font-size:30px;'>⭐</a>"
							 + "            <a href='" + approvalUrl + "&rating=4&email=" + toAddress + "' style='text-decoration:none; font-size:30px;'>⭐</a>"
							 + "            <a href='" + approvalUrl + "&rating=5&email=" + toAddress + "' style='text-decoration:none; font-size:30px;'>⭐</a>"
							 + "          </div>"
							 + "          <p style='font-size:12px; color:#666;'>Click on a star to share your feedback</p>"
							 + "        </div>"*/
							 + "  <div style='text-align:center; margin:15px 0;'>"
							 + "    <p style='font-size:16px; color:#333; margin:0 0 10px 0;'>How would you overall rate our service?</p>"
							 + "    <div style='display:inline-block;'>"
							 + "      <a href='" + approvalUrl + "&rating=1&email=" + toAddress + "' style='text-decoration:none; font-size:38px; color:#ccc; margin:0 2px;'>&#9734;</a>"
							 + "      <a href='" + approvalUrl + "&rating=2&email=" + toAddress + "' style='text-decoration:none; font-size:38px; color:#ccc; margin:0 2px;'>&#9734;</a>"
							 + "      <a href='" + approvalUrl + "&rating=3&email=" + toAddress + "' style='text-decoration:none; font-size:38px; color:#ccc; margin:0 2px;'>&#9734;</a>"
							 + "      <a href='" + approvalUrl + "&rating=4&email=" + toAddress + "' style='text-decoration:none; font-size:38px; color:#ccc; margin:0 2px;'>&#9734;</a>"
							 + "      <a href='" + approvalUrl + "&rating=5&email=" + toAddress + "' style='text-decoration:none; font-size:38px; color:#ccc; margin:0 2px;'>&#9734;</a>"
							 + "    </div>"
							 + "    <p style='font-size:12px; color:#666; margin-top:5px;'>Click on a star to share your feedback</p>"
							 + "  </div>"



			 // Project Details
							 + "        <h3 style='color:#0073e6; margin-top:8px;'>Project Details</h3>"
							 + "        <table cellpadding='6' cellspacing='0' width='100%' style='border:1px solid #e0e0e0; border-radius:6px;'>"
							 + "          <tr style='background:#f9f9f9;'><td><b>Sales<br> Person:</b></td><td>" + theLData.getSalesMenName().split("-")[0] + "</td></tr>"
							 + "          <tr><td><b>Invoice<br> Number:</b></td><td>" +theLData.getInvoiceNumber() + "</td></tr>"
							 + "          <tr style='background:#f9f9f9;'><td><b>Invoice <br> Date :</b></td><td>" + formattedDateString(theLData.getInvoiceDate()) + "</td></tr>"
							 + "          <tr><td><b>Project<br> Name:</b></td><td>" + theLData.getProjectName() + "</td></tr>"
							 + "        </table>"
							 + "        <br/><br/>"
							 + "        Thank you for your time and trust in us.  <br/>"
							 + "        <b>"+theLData.getFooterName()+"<br/>  Customer Experience Team</b>"
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
		 }catch(Exception e)
		 {
			 System.out.println("Error in sending email notification for the logistic team");
		 }
	 }
	 public int updateEmailStatus(String invNos) {
		// System.out.println("my invoidNO"+invNo);
		   int invNo=Integer.parseInt(invNos);
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
						+ " WHERE INVSYSID = ? ";
				myStmt = myCon.prepareStatement(sql);
				myStmt.setInt(1, invNo);
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
