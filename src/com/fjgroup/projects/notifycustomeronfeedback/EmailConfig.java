package com.fjgroup.projects.notifycustomeronfeedback;

import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * @author NUFAIL ACHATH
 */
public class EmailConfig {

	private String messageSub=null;
    private String toaddr="nufail.a@fjtco.com";
    private String ccaddr = "nufail.a@fjtco.com";;
    private String fromaddr = null;
    private String password =null;
    private String messagebody="";
    private String host=null;
    private String port="0";
    private String is_ssl="0";
    private String filename = null;



	public String getMessageSub() {
		return messageSub;
	}
	public void setMessageSub(String messageSub) {
		this.messageSub = messageSub;
	}
	public String getToaddr() {
		return toaddr;
	}
	public void setToaddr(String toaddr) {
		this.toaddr = toaddr;
	}
	public String getFromaddr() {
		return fromaddr;
	}
	public void setFromaddr(String fromaddr) {
		this.fromaddr = fromaddr;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessagebody() {
		return messagebody;
	}
	public void setMessagebody(String messagebody) {
		this.messagebody = messagebody;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getIs_ssl() {
		return is_ssl;
	}
	public void setIs_ssl(String is_ssl) {
		this.is_ssl = is_ssl;
	}

	public String getCcaddr() {
		return ccaddr;
	}

	public void setCcaddr(String ccaddr) {
		this.ccaddr = ccaddr;
	}


	 @SuppressWarnings("finally")
	public int sendMail() {
         readDefaultSenderMailProps();
		 Properties props = new Properties();
		 props.put("mail.smtp.host", this.host);
		 System.out.println("sendMail");
		 props.put("mail.smtp.host", host);
		 props.put("mail.smtp.auth", "true");
		 props.setProperty("mail.smtp.starttls.enable", "true");
		 props.put("mail.smtp.port", port);

		 int status=0;
		 Session session = Session.getDefaultInstance(props,
				 new javax.mail.Authenticator() {
					 protected PasswordAuthentication getPasswordAuthentication() {
						 return new PasswordAuthentication(fromaddr,password);
					 }
				 });



	try {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(this.fromaddr, "FJGroup No_Reply"));
        System.out.println(this.toaddr);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.toaddr));
		//message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kartik.p@fjtco.com"));
		//message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(this.ccaddr));
		message.setSubject(this.messageSub);   
		
		
		BodyPart messageBodyPart = new MimeBodyPart();	
		this.messagebody+=getMessageFooter();
		messageBodyPart.setContent(this.messagebody, "text/html");
		
		// Create a multipar message
        Multipart multipart = new MimeMultipart();
     // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();    

        // Send the complete message parts
        message.setContent(multipart);
        
        
        
		Transport.send(message);
                 status=1;
		//System.out.println("Done");

	} catch (MessagingException e) {
		 System.out.print(e);
		//throw new RuntimeException(e);
             status = -1;
	}
         finally{
             return status;
         }
}
	 
	 
	 
	  public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}




	private int readDefaultSenderMailProps(){

		int retval=0;

		try {
			Properties dbProps= new Properties();
			//dbProps.load(new FileInputStream("//fjtco-ho-svr-02/STOCKREPORT/frommail.properties"));
			dbProps.load(new FileInputStream("D://FJWISHES/frommail.properties"));
			String theMailid=dbProps.getProperty("mailid");
			String thePassword=dbProps.getProperty("password");
			String theHost=dbProps.getProperty("host");
			String thePort=dbProps.getProperty("port");
			String theSsl=dbProps.getProperty("ssl");
			this.fromaddr=theMailid;
			this.password=thePassword;
			this.host=theHost;
			this.port=thePort;
			this.is_ssl = theSsl;
		}catch(Exception e){
			retval=-2;

			System.out.println("Couldn't get data from mail file." + e.getMessage());
		}

		return retval;
	}




	public String getMessageFooter() {
		//String div = " <span style=\"color:red;\">Please Check  <a href=\"https://portal.fjtco.com:8444/fjhr/\" style=\"font-weight:700;\">FJ Portal</a> for more details.</span><br /><br />  "
		String div = "This is an auto-generated email. Please do not reply to this email. <br/>  "
				+ "Thank you <br/> Regards, <br/></td></tr>" + "</td></tr></table>";
		StringBuilder mbody = new StringBuilder("");
		mbody.append(div);
		return mbody.toString();
	}
      
}
