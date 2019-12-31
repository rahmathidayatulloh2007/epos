package com.wo.epos.common.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import com.wo.epos.common.vo.AuditVO;

public class MenuVO extends AuditVO implements Serializable {

	private static final long serialVersionUID = -7334566108154008427L;
	private Long menu_id;
	private String name;
	private String action;
	private Long parent_id;
	private String description;
	private String type;
	private Integer menu_order;
	private Integer menu_level;
	private String parent_name;
	private Integer parent_level;
	private String type_name;
	
	private List<MenuVO> childMenuList;
	
	public MenuVO () {
		table_name = "BTPN_MST_MENU"; 
		menu_id = new Long(0);
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
		if (this.menu_id != null && other.getMenu_id() != null) {
			if (this.menu_id.longValue() == other.getMenu_id().longValue())
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

	public Long getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(Long menuId) {
		menu_id = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parentId) {
		parent_id = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMenu_order() {
		return menu_order;
	}

	public void setMenu_order(Integer menuOrder) {
		menu_order = menuOrder;
	}

	public Integer getMenu_level() {
		return menu_level;
	}

	public void setMenu_level(Integer menuLevel) {
		menu_level = menuLevel;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parentName) {
		parent_name = parentName;
	}

	public Integer getParent_level() {
		return parent_level;
	}

	public void setParent_level(Integer parentLevel) {
		parent_level = parentLevel;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String typeName) {
		type_name = typeName;
	}

	public List<MenuVO> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<MenuVO> childMenuList) {
		this.childMenuList = childMenuList;
	}
}