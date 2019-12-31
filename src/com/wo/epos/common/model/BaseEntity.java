package com.wo.epos.common.model;

import java.sql.Timestamp;

public class BaseEntity {
	
	protected Timestamp createOn;
	protected String createBy;
	protected Timestamp updateOn;
	protected String updateBy;
	protected String activeFlag;
	
	
	public Timestamp getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Timestamp createOn) {
		this.createOn = createOn;
	}
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public Timestamp getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Timestamp updateOn) {
		this.updateOn = updateOn;
	}
	
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}	
	
	
	
}
