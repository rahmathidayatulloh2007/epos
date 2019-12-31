package com.wo.epos.module.uam.company.model;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.franchise.model.Franchise;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class Company extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -266636174542943451L;
	
	private Long companyId;
	private Long franchiseId;
	
	private String companyCode;
	private String companyName;
	private String companyTypeCode;
	
	private ParameterDtl companyType;
	private Franchise franchise;
		
	private String address;
    
    private Long cityId;
    private City city;
    
    private String postalCode;
    private String picName;
    private String picPhoneno;
    
    private Date registerOn;
    
    private String paymentFlag;
    
    private Long outletQty;
    
    
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyTypeCode() {
		return companyTypeCode;
	}

	public void setCompanyTypeCode(String companyTypeCode) {
		this.companyTypeCode = companyTypeCode;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
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

	public Date getRegisterOn() {
		return registerOn;
	}

	public void setRegisterOn(Date registerOn) {
		this.registerOn = registerOn;
	}

	public String getPaymentFlag() {
		return paymentFlag;
	}

	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	public Long getOutletQty() {
		return outletQty;
	}

	public void setOutletQty(Long outletQty) {
		this.outletQty = outletQty;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

    public ParameterDtl getCompanyType() {
		return companyType;
	}

	public void setCompanyType(ParameterDtl companyType) {
		this.companyType = companyType;
	}

	public Long getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(Long franchiseId) {
		this.franchiseId = franchiseId;
	}

	public Franchise getFranchise() {
		return franchise;
	}

	public void setFranchise(Franchise franchise) {
		this.franchise = franchise;
	}
    
	
}