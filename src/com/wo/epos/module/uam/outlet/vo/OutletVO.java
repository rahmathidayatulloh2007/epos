package com.wo.epos.module.uam.outlet.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class OutletVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 3997545790290082720L;
	
	private Long outletId;	
	private Long companyId;
	private Long cityId;
	private Long ProvinceId;
	private Long picEmployeeId;
	
	private String companyName;
	private String cityName;
	private String outletCode;
	private String outletName;
	private String address;
	private String postalCode;
	private String picName;
	private String picPhoneno;
	private String provinceName;
	
	private boolean checked;
	
	public OutletVO(){
		
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getProvinceId() {
		return ProvinceId;
	}

	public void setProvinceId(Long provinceId) {
		ProvinceId = provinceId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getPicEmployeeId() {
		return picEmployeeId;
	}

	public void setPicEmployeeId(Long picEmployeeId) {
		this.picEmployeeId = picEmployeeId;
	}
	
	
}
