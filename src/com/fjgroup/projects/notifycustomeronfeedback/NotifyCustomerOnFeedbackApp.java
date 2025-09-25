package com.fjgroup.projects.notifycustomeronfeedback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotifyCustomerOnFeedbackApp {

	public static void main(String[] args) {  
		System.out.println("StartedNotifyCustomerOnFeedbackApp Application");  
		EmailConfigDbUtil emailConfigDbUtil=new EmailConfigDbUtil();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		   LocalDateTime now = LocalDateTime.now();
		   System.out.println("Scheduler ran on "+dtf.format(now));
        try {                  
         List<NotifyCustomerOnFeedback> poList= emailConfigDbUtil.getAllEmailErrorPOs();
         if(poList != null && !poList.isEmpty()) {			
        	 emailConfigDbUtil.sendMailToLogisticsTeam(poList);						 			 
			}
			else {
			  System.out.println("No Records FOUND");
			}
		 
         System.out.println("End NotifyLogisticsIfEmailError Mailer Application");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
