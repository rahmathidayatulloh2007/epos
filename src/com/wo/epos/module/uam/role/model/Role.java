package com.wo.epos.module.uam.role.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;


public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 5672203397873237822L;

	private Long roleId;
	
	private String roleCode;
	private String roleName;
	private String roleDesc;
	private String menuResume;
	
	private Long companyId;
	private Company company;
	
	private List<RoleDtl> roleDetailList = new ArrayList<RoleDtl>();		
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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
	
	public String getMenuResume() {
		return menuResume;
	}

	public void setMenuResume(String menuResume) {
		this.menuResume = menuResume;
	}

	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	public List<RoleDtl> getRoleDetailList() {
		return roleDetailList;
	}

	public void setRoleDetailList(List<RoleDtl> roleDetailList) {
		this.roleDetailList = roleDetailList;
	}
	
		
	
	
}