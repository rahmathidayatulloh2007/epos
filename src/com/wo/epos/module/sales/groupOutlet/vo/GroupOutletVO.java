package com.wo.epos.module.sales.groupOutlet.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class GroupOutletVO extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -1284665398279273253L;
	
	private Long groupOutletId;
    private String groupOutletCode;
    private String groupOutletName;
	
    public Long getGroupOutletId() {
		return groupOutletId;
	}
	public void setGroupOutletId(Long groupOutletId) {
		this.groupOutletId = groupOutletId;
	}
	public String getGroupOutletCode() {
		return groupOutletCode;
	}
	public void setGroupOutletCode(String groupOutletCode) {
		this.groupOutletCode = groupOutletCode;
	}
	public String getGroupOutletName() {
		return groupOutletName;
	}
	public void setGroupOutletName(String groupOutletName) {
		this.groupOutletName = groupOutletName;
	}
  
    
	
    
    
	
	
	
}
