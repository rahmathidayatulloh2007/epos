package com.wo.epos.module.uam.employee.vo;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;

public class EmployeeVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -9097918548035516978L;
	
	private Long employeeId; 
	private Long companyId ;
	private Long cityId;
	private Long outletId;
	
	private String companyName;
	private String cityName;
	private String outletName;
	
	private String employeeNo;
	private String fullName;
	private String birthPlace;
	private String address;
	private String postalCode;
	private String hpNo;
	private String workEmail;
	private String profileImgname;
	private String position;
	
	private String genderCode;
	private String genderName;	
	private String religionCode;
	private String religionName;
	private String maritalStatusCode;
	private String maritalStatusName;
	private String employeeStatusCode;
	private String employeeStatusName;
	
	private Date birthDate;
	private Date joinDt;
	private Date startDate;
    private Date endDate;
	
	private byte[] profileImg;
	
	private boolean picFlagChecked;
	
	private List<OutletEmp> listOutletEmp;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
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

	public String getHpNo() {
		return hpNo;
	}

	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getProfileImgname() {
		return profileImgname;
	}

	public void setProfileImgname(String profileImgname) {
		this.profileImgname = profileImgname;
	}

	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getReligionCode() {
		return religionCode;
	}

	public void setReligionCode(String religionCode) {
		this.religionCode = religionCode;
	}

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public String getMaritalStatusName() {
		return maritalStatusName;
	}

	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}

	public String getEmployeeStatusCode() {
		return employeeStatusCode;
	}

	public void setEmployeeStatusCode(String employeeStatusCode) {
		this.employeeStatusCode = employeeStatusCode;
	}

	public String getEmployeeStatusName() {
		return employeeStatusName;
	}

	public void setEmployeeStatusName(String employeeStatusName) {
		this.employeeStatusName = employeeStatusName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getJoinDt() {
		return joinDt;
	}

	public void setJoinDt(Date joinDt) {
		this.joinDt = joinDt;
	}

	

	public byte[] getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(byte[] profileImg) {
		this.profileImg = profileImg;
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

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isPicFlagChecked() {
		return picFlagChecked;
	}

	public void setPicFlagChecked(boolean picFlagChecked) {
		this.picFlagChecked = picFlagChecked;
	}

	public List<OutletEmp> getListOutletEmp() {
		return listOutletEmp;
	}

	public void setListOutletEmp(List<OutletEmp> listOutletEmp) {
		this.listOutletEmp = listOutletEmp;
	}
	
	
	
}
