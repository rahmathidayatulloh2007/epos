package com.wo.epos.module.uam.company.vo;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;


public class CompanyVO extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -6137320329520514977L;
		
    private Long companyId;
    private Long cityId;
    private Long outletQty;
    private Long provinceId;
    private Long franchiseId;
    
	private String companyCode;
	private String companyName;
    private String companyTypeCode;
	private String companyTypeName;
	private String address;
	private String cityName;    
    private String postalCode;
    private String picName;
    private String picPhoneno;
    private String paymentFlag;
    private String provinceName;
    private String franchiseName;
    
    private Date registerOn;
    private Date startDate;
    private Date endDate;
    
    private boolean checked;
    
    
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCityId() {
		return cityId;
	}
	
	public String getCompanyTypeCode() {
		return companyTypeCode;
	}

	public void setCompanyTypeCode(String companyTypeCode) {
		this.companyTypeCode = companyTypeCode;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getOutletQty() {
		return outletQty;
	}

	public void setOutletQty(Long outletQty) {
		this.outletQty = outletQty;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyTypeName() {
		return companyTypeName;
	}

	public void setCompanyTypeName(String companyTypeName) {
		this.companyTypeName = companyTypeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicPhoneno() {
		return picPhoneno;
	}

	public void setPicPhoneno(String picPhoneno) {
		this.picPhoneno = picPhoneno;
	}

	public String getPaymentFlag() {
		return paymentFlag;
	}

	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	public Date getRegisterOn() {
		return registerOn;
	}

	public void setRegisterOn(Date registerOn) {
		this.registerOn = registerOn;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(Long franchiseId) {
		this.franchiseId = franchiseId;
	}

	public String getFranchiseName() {
		return franchiseName;
	}

	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}
		
}