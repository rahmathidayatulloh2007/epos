package com.wo.epos.module.uam.role.vo;

import java.io.Serializable;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;


public class RoleDtlVO extends BaseEntity implements Serializable {
			
	private static final long serialVersionUID = 5431254943757431889L;
	
	private Long roleDtlId;
	private Long roleId;
    private Long menuId;
    
	private String roleName;	
	private String menuName;
	private String menuCode;
	private String menuResume;
	
	private Boolean check;
	
	private List<RoleDtlVO> childRoleMenuList;
	private int childRoleMenuListSize;	
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
			
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}		
	
	public Long getRoleDtlId() {
		return roleDtlId;
	}

	public void setRoleDtlId(Long roleDtlId) {
		this.roleDtlId = roleDtlId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<RoleDtlVO> getChildRoleMenuList() {
		return childRoleMenuList;
	}

	public void setChildRoleMenuList(List<RoleDtlVO> childRoleMenuList) {
		this.childRoleMenuList = childRoleMenuList;
	}

	public int getChildRoleMenuListSize() {
		return childRoleMenuListSize;
	}

	public void setChildRoleMenuListSize(int childRoleMenuListSize) {
		this.childRoleMenuListSize = childRoleMenuListSize;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuResume() {
		return menuResume;
	}

	public void setMenuResume(String menuResume) {
		this.menuResume = menuResume;
	}
		
	
}