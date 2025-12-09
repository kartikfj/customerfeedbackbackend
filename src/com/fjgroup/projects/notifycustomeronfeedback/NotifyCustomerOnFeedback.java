package com.fjgroup.projects.notifycustomeronfeedback;

import java.util.List;



public class NotifyCustomerOnFeedback {
	private String invoiceId = null;
	private String invoiceDate = null;
	private String projectName = null;	
	private String customerEmailId = null;
	private String salesMenName = null;
    private String invoiceCode = null;
	private String invoiceNumber = null;

	private String compName;

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFacebook() {
		return facebook;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	private String address;
	private String facebook;
	private String instagram;
	private String website;
	private String imageUrl;
	private String compCode;

	public NotifyCustomerOnFeedback(String invoiceId, String invoiceDate, String projectName,
									String customerEmailId, String salesMenName, String footerName,
									String compName, String address, String facebook,
									String instagram, String website, String imageUrl, String compCode,String invno,String invcode) {
		this.invoiceId = invoiceId;
		this.invoiceDate = invoiceDate;
		this.projectName = projectName;
		this.customerEmailId = customerEmailId;
		this.salesMenName = salesMenName;
		this.footerName = footerName;
		this.compName = compName;
		this.address = address;
		this.facebook = facebook;
		this.instagram = instagram;
		this.website = website;
		this.imageUrl = imageUrl;
		this.compCode = compCode;
		this.invoiceNumber = invno;
		this.invoiceCode = invcode;
	}
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
