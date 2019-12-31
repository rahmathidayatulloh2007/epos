package com.wo.epos.module.purchasing.supplier.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class SupplierVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 3285814261650465908L;
	private Long supplierId; 
	private Long apBalance;
	private String supplierCode;
	private String supplierName;
	private String address;
	private String postalCode;
	private String phoneNo;
	private String faxNo;	
	private String picName;	
	
	private Long companyId;
	private String companyName;
	private Long cityId;
	private String cityName;
	private Long provinceId;
	private String provinceName;
	private String picTitleCode;
	private String picTitleName;
		
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
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getPicTitleCode() {
		return picTitleCode;
	}
	
	public void setPicTitleCode(String picTitleCode) {
		this.picTitleCode = picTitleCode;
	}
	
	public String getPicTitleName() {
		return picTitleName;
	}
	
	public void setPicTitleName(String picTitleName) {
		this.picTitleName = picTitleName;
	}
	
	
	    
	    
	
}
