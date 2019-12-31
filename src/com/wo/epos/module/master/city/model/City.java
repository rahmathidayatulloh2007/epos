package com.wo.epos.module.master.city.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.province.model.Province;

public class City extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -9172094388285587776L;

	private Long cityId;	
	private Long provinceId;	
	
	private String cityCode;	
	private String cityName;
	
	private Province province;
	
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}	
	
}
