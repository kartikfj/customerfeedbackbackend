package com.fjgroup.projects.notifycustomeronfeedback;

import java.util.List;



public class NotifyCustomerOnFeedback {
	private String invoiceId = null;
	private String invoiceDate = null;
	private String projectName = null;	
	private String customerEmailId = null;
	private String salesMenName = null;

	public String getFooterName() {
		return footerName;
	}

	public void setFooterName(String footerName) {
		this.footerName = footerName;
	}

	private String footerName = null;
	public String getInvoiceId() {
		return invoiceId;
	}
    public String getSalesMenName(){
		return salesMenName;
	}
	public void setSalesMenName(String salesMenName){
		this.salesMenName=salesMenName;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}


	public String getInvoiceDate() {
		return invoiceDate;
	}


	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	

	public String getCustomerEmailId() {
		return customerEmailId;
	}


	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}


	public NotifyCustomerOnFeedback(String invoiceId,String invoiceDate,String projectName, String customerEmailId, String salesMenName,String footerName) {
		super();
		this.invoiceId = invoiceId;
		this.invoiceDate = invoiceDate;
		this.projectName = projectName;
		this.customerEmailId = customerEmailId;
		this.salesMenName = salesMenName;
		this.footerName = footerName;
	}
	
}
