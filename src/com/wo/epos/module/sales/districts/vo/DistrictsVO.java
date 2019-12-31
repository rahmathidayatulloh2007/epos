package com.wo.epos.module.sales.districts.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class DistrictsVO extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -1284665398279273253L;
	
	private Long districtId;
    
	private Long provinceId;
    private String provinceName;
    
    private String districtCode;
    private String districtName;
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
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
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
    
	
    
    
	
	
	
}
