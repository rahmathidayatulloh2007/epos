package com.wo.epos.module.master.province.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class ProvinceVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4432099191460050723L;
	
	private Long provinceId;
	private String provinceCode;
	private String provinceName;
	
	private boolean checked;
	
	public ProvinceVO()
	{
		
	}
	
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
	
}
