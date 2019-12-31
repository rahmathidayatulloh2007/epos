package com.wo.epos.module.sales.districts.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.province.model.Province;

public class Districts extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -3510737878336808423L;
	
	private Long districtId;
    private Province province;
    private String districtCode;
    private String districtName;
    
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
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
