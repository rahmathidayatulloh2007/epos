package com.wo.epos.module.uam.role.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.menu.model.Menu;


public class RoleDtl extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -476149504300880520L;

	private Long roleDtlId;
	
	private Long roleId;
	private Role role;
	
	private Long menuId;
	private Menu menu;
	
	
	
	public Long getRoleDtlId() {
		return roleDtlId;
	}

	public void setRoleDtlId(Long roleDtlId) {
		this.roleDtlId = roleDtlId;
	}

	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public Long getMenuId() {
		return menuId;
	}
	
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
		
}