package com.wo.epos.module.sales.businessType.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class BusinessTypeVO extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -1284665398279273253L;
	
	private Long businessTypeId;
    private String businessTypeCode;
    private String businessTypeName;
	public Long getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(Long businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	public String getBusinessTypeCode() {
		return businessTypeCode;
	}
	public void setBusinessTypeCode(String businessTypeCode) {
		this.businessTypeCode = businessTypeCode;
	}
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
  
    
	
    
    
	
	
	
}
