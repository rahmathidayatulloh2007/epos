package com.wo.epos.module.purchasing.supplier.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class Supplier extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -7007176314575816763L;
	
	private Long supplierId; 
	private Long apBalance;
	private String supplierCode;
	private String supplierName;
	private String address;
	private String postalCode;
	private String phoneNo;
	private String faxNo;	
	private String picName;	
	
	private Company company;
	private City city;
	private ParameterDtl picTitle;
	
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getApBalance() {
		return apBalance;
	}
	public void setApBalance(Long apBalance) {
		this.apBalance = apBalance;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public ParameterDtl getPicTitle() {
		return picTitle;
	}
	public void setPicTitle(ParameterDtl picTitle) {
		this.picTitle = picTitle;
	}
	
	
}
