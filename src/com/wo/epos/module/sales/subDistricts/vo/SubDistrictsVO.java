package com.wo.epos.module.sales.subDistricts.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class SubDistrictsVO extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -1284665398279273253L;
	
	private Long subDistrictId;
    
	private Long districtId;
	private String districtName;
	
    private String subDistrictCode;
    private String subDistrictName;
    
    
	public Long getSubDistrictId() {
		return subDistrictId;
	}
	public void setSubDistrictId(Long subDistrictId) {
		this.subDistrictId = subDistrictId;
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
	public String getSubDistrictCode() {
		return subDistrictCode;
	}
	public void setSubDistrictCode(String subDistrictCode) {
		this.subDistrictCode = subDistrictCode;
	}
	public String getSubDistrictName() {
		return subDistrictName;
	}
	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
	}
    
    
    
    
    
	
	
	
}
