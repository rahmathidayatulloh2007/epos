package com.wo.epos.module.master.systemProperty.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;


public class SystemPropertyVO extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 5274645606508304957L;
	
    private Long systemPropertyId;
    private Long companyId;
    
	private String systemPropertyName;
	private String systemPropertyValue;
	private String systemPropertyDesc;	
	private String companyName;
		
	
	
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
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
    
	
}