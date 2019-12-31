package com.wo.epos.module.uam.menu.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import com.wo.epos.common.vo.AuditVO;

public class MenuVO extends AuditVO implements Serializable {

	private static final long serialVersionUID = -7334566108154008427L;
	private Long menuId;
	private String menuCode;
	private String menuName;
	private String menuType;
	private String action;
	private String description;
	private Integer menuOrder;
	private Integer menuLevel;
	private Long parentId;
	private String parentCode;
	private String parentName;
	private MenuVO parent;
	private List<MenuVO> childMenuList;
	private String activeFlag;
	
	private Boolean checked;
	
	public MenuVO () {
		table_name = "POS_MENU"; 
		menuId = new Long(0);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MenuVO)) {
			return false;
		}
		MenuVO other = (MenuVO) obj;
		if (this.menuId != null && other.getMenuId() != null) {
			if (this.menuId.longValue() == other.getMenuId().longValue())
				return true;
		}
		return false;
	}
	
	public String toString() {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("["+getTable_name()+"]>>>>> ");
            try {
                Field[] fields = MenuVO.class.getDeclaredFields();
                for (int i = 0; i < fields.length; i++)
                    strBuff.append(fields[i].getName()).append(" = ").append(fields[i].get(this)).append(", ");
            } catch(IllegalAccessException iae) { }
        return strBuff.toString();
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

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
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

	

	public MenuVO getParent() {
		return parent;
	}

	public void setParent(MenuVO parent) {
		this.parent = parent;
	}

	public List<MenuVO> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<MenuVO> childMenuList) {
		this.childMenuList = childMenuList;
	}

	

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	
	
}