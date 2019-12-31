package com.wo.epos.module.master.systemProperty.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;


public class SystemProperty extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -3721894019339322469L;

	private Long systemPropertyId;
	
	private String systemPropertyName;
	private String systemPropertyValue;
	private String systemPropertyDesc;
	
	private Long companyId;
	private Company company;
	
	
	
	public Long getSystemPropertyId() {
		return systemPropertyId;
	}
	
	public void setSystemPropertyId(Long systemPropertyId) {
		this.systemPropertyId = systemPropertyId;
	}
	
	public String getSystemPropertyName() {
		return systemPropertyName;
	}
	
	public void setSystemPropertyName(String systemPropertyName) {
		this.systemPropertyName = systemPropertyName;
	}
	
	public String getSystemPropertyValue() {
		return systemPropertyValue;
	}
	
	public void setSystemPropertyValue(String systemPropertyValue) {
		this.systemPropertyValue = systemPropertyValue;
	}
	
	public String getSystemPropertyDesc() {
		return systemPropertyDesc;
	}
	
	public void setSystemPropertyDesc(String systemPropertyDesc) {
		this.systemPropertyDesc = systemPropertyDesc;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
		
	
	
	
	
}