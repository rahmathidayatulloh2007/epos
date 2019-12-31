package com.wo.epos.module.uam.outlet.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.uam.company.model.Company;

public class Outlet extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6143470924780619930L;
	private Long outletId;
	private Long cityId;
	
	private String outletCode;
	private String outletName;
	private String address;
	private String postalCode;
	private String picName;
	private String picPhoneno;
	
	private Company company;
	private City city;
	
	
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
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
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	
	
	
}
