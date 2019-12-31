package com.wo.epos.module.inv.um.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;

public class Um extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -48164502059437823L;
	
	private Long umId;
    private Long companyId;
    
	private Company company;
	
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
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
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