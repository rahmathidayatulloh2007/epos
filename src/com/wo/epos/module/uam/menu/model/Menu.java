package com.wo.epos.module.uam.menu.model;

import java.io.Serializable;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
//import com.wo.epos.module.mst.parameter_dtl.model.ParamDetail;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class Menu extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -6014917168865085318L;
	private Long menuId;
	private String menuCode;
	private String menuName;
	private String menuTypeCode;
	private ParameterDtl menuType;
	private String action;
	private String description;
	private Integer menuOrder;
	private Integer menuLevel;
	private Boolean checked;	
	private Menu parent;
	
	private List<Menu> childMenuList;	
	
	
	public Integer getDetailSize(){
		return childMenuList!=null?childMenuList.size():0;
	}
	
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}	
	public String getMenuTypeCode() {
		return menuTypeCode;
	}
	public void setMenuTypeCode(String menuTypeCode) {
		this.menuTypeCode = menuTypeCode;
	}
	public ParameterDtl getMenuType() {
		return menuType;
	}
	public void setMenuType(ParameterDtl menuType) {
		this.menuType = menuType;
	}

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public Integer getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public List<Menu> getChildMenuList() {
		return childMenuList;
	}
	public void setChildMenuList(List<Menu> childMenuList) {
		this.childMenuList = childMenuList;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}
