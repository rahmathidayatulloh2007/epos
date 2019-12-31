package com.wo.epos.module.sales.subDistricts.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.sales.districts.model.Districts;

public class SubDistricts extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -3510737878336808423L;
	
	private Long subDistrictId;
    
	private Districts districts;
    
    private String subDistrictCode;
    private String subDistrictName;
    
    
	public Long getSubDistrictId() {
		return subDistrictId;
	}
	public void setSubDistrictId(Long subDistrictId) {
		this.subDistrictId = subDistrictId;
	}
	
	public Districts getDistricts() {
		return districts;
	}
	public void setDistricts(Districts districts) {
		this.districts = districts;
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
