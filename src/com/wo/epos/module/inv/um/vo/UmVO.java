package com.wo.epos.module.inv.um.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class UmVO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1064498451055473800L;
	
	private Long umId;
    private Long companyId;
    
	private String companyName;
	private String umName;
	private String umDesc;
		
	
	public Long getUmId() {
		return umId;
	}
	
	public void setUmId(Long umId) {
		this.umId = umId;
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
	
	public String getUmName() {
		return umName;
	}

	public void setUmName(String umName) {
		this.umName = umName;
	}

	public String getUmDesc() {
		return umDesc;
	}
	
	public void setUmDesc(String umDesc) {
		this.umDesc = umDesc;
	}
	
	
	
	
	
}