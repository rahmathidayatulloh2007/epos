package com.wo.epos.module.master.province.model;

import java.io.Serializable;


import com.wo.epos.common.model.BaseEntity;


public class Province extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6722568395143105217L;
		
	private Long provinceId;	
	private String provinceCode;	
	private String provinceName;
	
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	
	
}
