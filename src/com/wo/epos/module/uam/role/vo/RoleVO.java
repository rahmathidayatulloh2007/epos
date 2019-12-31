package com.wo.epos.module.uam.role.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;


public class RoleVO extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -5312582077686561381L;
		
	private Long roleId;
    private Long companyId;
    
	private String roleCode;
	private String roleName;
	private String roleDesc;	
	private String companyName;
	private String menuResume;	
	
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getRoleCode() {
		return roleCode;
	}
	
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}
	
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMenuResume() {
		return menuResume;
	}

	public void setMenuResume(String menuResume) {
		this.menuResume = menuResume;
	}
	
}