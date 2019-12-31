package com.wo.epos.module.uam.employee.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class Employee extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 992668187096040847L;
	
	private Long employeeId; 
	private Long companyId ;
	private String employeeNo;
	private String fullName;
	private String birthPlace;
	private Date birthDate;
	
	private String genderCode;
	private ParameterDtl gender;
	
	private String religionCode;
	private ParameterDtl religion;
	
	private String maritalStatusCode;
	private ParameterDtl maritalStatus;
	
	private String address;
	private Long cityId;
	private String postalCode;
	private String hpNo;
	private String workEmail;
	private byte[] profileImg;
	private String profileImgname;
	private Date joinDt;
	
	private String employeeStatusCode;
	private ParameterDtl employeeStatus;
	
	private Company company;
	private City city;
	private OutletEmp outletEmp;
	
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
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public ParameterDtl getGender() {
		return gender;
	}
	public void setGender(ParameterDtl gender) {
		this.gender = gender;
	}
	public String getReligionCode() {
		return religionCode;
	}
	public void setReligionCode(String religionCode) {
		this.religionCode = religionCode;
	}
	public ParameterDtl getReligion() {
		return religion;
	}
	public void setReligion(ParameterDtl religion) {
		this.religion = religion;
	}
	public String getMaritalStatusCode() {
		return maritalStatusCode;
	}
	public void setMaritalStatusCode(String maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}
	public ParameterDtl getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(ParameterDtl maritalStatus) {
		this.maritalStatus = maritalStatus;
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
	
	public byte[] getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(byte[] profileImg) {
		this.profileImg = profileImg;
	}
	public String getProfileImgname() {
		return profileImgname;
	}
	public void setProfileImgname(String profileImgname) {
		this.profileImgname = profileImgname;
	}
	public Date getJoinDt() {
		return joinDt;
	}
	public void setJoinDt(Date joinDt) {
		this.joinDt = joinDt;
	}
	public String getEmployeeStatusCode() {
		return employeeStatusCode;
	}
	public void setEmployeeStatusCode(String employeeStatusCode) {
		this.employeeStatusCode = employeeStatusCode;
	}
	public ParameterDtl getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(ParameterDtl employeeStatus) {
		this.employeeStatus = employeeStatus;
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
	public OutletEmp getOutletEmp() {
		return outletEmp;
	}
	public void setOutletEmp(OutletEmp outletEmp) {
		this.outletEmp = outletEmp;
	}
	public List<OutletEmp> getListOutletEmp() {
		return listOutletEmp;
	}
	public void setListOutletEmp(List<OutletEmp> listOutletEmp) {
		this.listOutletEmp = listOutletEmp;
	}
	
	

}
