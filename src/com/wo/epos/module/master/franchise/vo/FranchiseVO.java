package com.wo.epos.module.master.franchise.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.city.model.City;


public class FranchiseVO extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -1212482090848119977L;

	private Long franchiseId;

	private String franchiseCode;
	private String franchiseName;
	private String address;
	private City city;
	private String postalCode;
	private String picName;
	private String picPhoneNo;
	private byte[] logoFile;
	private String logoFileName;
	private String groupId;
	private Long cityId;
	private Long provinceId;
	private String provinceName;
	
	public Long getFranchiseId() {
		return franchiseId;
	}
	public void setFranchiseId(Long franchiseId) {
		this.franchiseId = franchiseId;
	}
	public String getFranchiseCode() {
		return franchiseCode;
	}
	public void setFranchiseCode(String franchiseCode) {
		this.franchiseCode = franchiseCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
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
	public String getPicPhoneNo() {
		return picPhoneNo;
	}
	public void setPicPhoneNo(String picPhoneNo) {
		this.picPhoneNo = picPhoneNo;
	}
	
	public byte[] getLogoFile() {
		return logoFile;
	}
	public void setLogoFile(byte[] logoFile) {
		this.logoFile = logoFile;
	}
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFranchiseName() {
		return franchiseName;
	}
	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
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
	
	
	
	
	

}
