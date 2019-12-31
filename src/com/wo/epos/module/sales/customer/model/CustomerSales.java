package com.wo.epos.module.sales.customer.model;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.sales.businessType.model.BusinessType;
import com.wo.epos.module.sales.districts.model.Districts;
import com.wo.epos.module.sales.groupOutlet.model.GroupOutlet;
import com.wo.epos.module.sales.subDistricts.model.SubDistricts;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class CustomerSales extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -1212482090848119977L;

	private Long customerId;
	
	private Company company;
	/*private City city;*/
	private BusinessType businessType; 
	private GroupOutlet groupOutlet; 
	private Province province;
	private Districts districts;
	private SubDistricts subDistricts;
	
	
	private String customerTypeCode;
	private ParameterDtl customerType;
	
/*	private Outlet registerOutlet;
 	private Long groupId;
	private String title;
	private Date registerDate;*/
	
	private Date birthDate;
	private String customerCode;
	private String customerName;
	private String fullName;
	private String phoneNo;
	private String emailAddress;
	private String address;
	private Long depositBalance;
	private String salesman;
	private Integer portalCode;
	private String addressNpwp;
	private String fullNameNpwp;
	private Integer npwpNo;
	private String phoneNo2;
	private String fax;
	private Integer termOfPayment;
	
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhoneNo2() {
		return phoneNo2;
	}
	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}
	public Integer getPortalCode() {
		return portalCode;
	}
	public void setPortalCode(Integer portalCode) {
		this.portalCode = portalCode;
	}
	public String getAddressNpwp() {
		return addressNpwp;
	}
	public void setAddressNpwp(String addressNpwp) {
		this.addressNpwp = addressNpwp;
	}
	public String getFullNameNpwp() {
		return fullNameNpwp;
	}
	public void setFullNameNpwp(String fullNameNpwp) {
		this.fullNameNpwp = fullNameNpwp;
	}
	public Integer getNpwpNo() {
		return npwpNo;
	}
	public void setNpwpNo(Integer npwpNo) {
		this.npwpNo = npwpNo;
	}
	public BusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}
	public GroupOutlet getGroupOutlet() {
		return groupOutlet;
	}
	public void setGroupOutlet(GroupOutlet groupOutlet) {
		this.groupOutlet = groupOutlet;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public Districts getDistricts() {
		return districts;
	}
	public void setDistricts(Districts districts) {
		this.districts = districts;
	}
	public SubDistricts getSubDistricts() {
		return subDistricts;
	}
	public void setSubDistricts(SubDistricts subDistricts) {
		this.subDistricts = subDistricts;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getDepositBalance() {
		return depositBalance;
	}
	public void setDepositBalance(Long depositBalance) {
		this.depositBalance = depositBalance;
	}
	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public ParameterDtl getCustomerType() {
		return customerType;
	}
	public void setCustomerType(ParameterDtl customerType) {
		this.customerType = customerType;
	}
	public Integer getTermOfPayment() {
		return termOfPayment;
	}
	public void setTermOfPayment(Integer termOfPayment) {
		this.termOfPayment = termOfPayment;
	}
	public String getCustomerTypeCode() {
		return customerTypeCode;
	}
	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}
	

	
	

}
