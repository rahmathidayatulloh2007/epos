package com.wo.epos.module.sales.customer.vo;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;

public class CustomerSalesVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 6155367409223772764L;
	
	
	
	private Company company;
	private Long companyId;
	private String companyName;
	
	private Long businessTypeId; 
	private String businessTypeName; 
	
	private Long groupOutletId; 
	private String groupOutletName; 
	
	/*private City city;
	private String cityName;
	private Long cityId;*/
	
	private Long provinceId;
	private String provinceName;
	
	private Long districtId;
	private String districtName;
	

	
	private Long subDistrictId;
	private String subDistrictName;
	
/*	
 	private Long registerOutletId;
	private String registerOutletName;
	private Long groupId;
	private String title;
	private Date registerDate;*/
	
	private Long customerId;
	private String customerCode;
	private String customerName;
	
	private String fullName;
	private String phoneNo;
	private String emailAddress;
	private Date birthDate;
	private String address;
	
	
	private Long depositBalance;
	private Outlet registerOutlet;
	private String salesman;
	
	
	private Integer portalCode;
	private String addressNpwp;
	private String fullNameNpwp;
	private Integer npwpNo;
	private String phoneNo2;
	private String fax;
	private String activeFlag;
	private Integer termOfPayment;
	private String customerTypeCode;
	private String customerTypeName;
	
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


	public Long getBusinessTypeId() {
		return businessTypeId;
	}


	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}


	public String getBusinessTypeName() {
		return businessTypeName;
	}


	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}


	public Long getGroupOutletId() {
		return groupOutletId;
	}


	public void setGroupOutletId(Long groupOutletId) {
		this.groupOutletId = groupOutletId;
	}


	public String getGroupOutletName() {
		return groupOutletName;
	}


	public void setGroupOutletName(String groupOutletName) {
		this.groupOutletName = groupOutletName;
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


	public Long getDistrictId() {
		return districtId;
	}


	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}


	public String getDistrictName() {
		return districtName;
	}


	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}


	public Long getSubDistrictId() {
		return subDistrictId;
	}


	public void setSubDistrictId(Long subDistrictId) {
		this.subDistrictId = subDistrictId;
	}


	public String getSubDistrictName() {
		return subDistrictName;
	}


	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
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



	public Outlet getRegisterOutlet() {
		return registerOutlet;
	}


	public void setRegisterOutlet(Outlet registerOutlet) {
		this.registerOutlet = registerOutlet;
	}



	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}



	public String getActiveFlag() {
		return activeFlag;
	}


	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
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


	public String getCustomerTypeName() {
		return customerTypeName;
	}


	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	
	
	
}
