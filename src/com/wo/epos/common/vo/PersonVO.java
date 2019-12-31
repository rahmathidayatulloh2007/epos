package com.wo.epos.common.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.primefaces.model.menu.MenuModel;

import com.wo.epos.common.vo.MenuVO;

public class PersonVO extends AuditVO implements Serializable {

	private static final long serialVersionUID = 8742120964013070L;
	private String person_id;
	private String npk;
	private String name;
	// private Long position_id;
	// private String position_name;
	private String branch_code;
	// private String branch_name;
	private Long job_id;
	private String job_code;
	// private String job_description;
	// private String department;
	private List<MenuVO> listMenu;
	private MenuModel mymenu;
	private MenuModel portalMenu;
	private String org_name;

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getNpk() {
		return npk;
	}

	public void setNpk(String npk) {
		this.npk = npk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public Long getPosition_id() {
	// return position_id;
	// }
	//
	// public void setPosition_id(Long position_id) {
	// this.position_id = position_id;
	// }

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public Long getJob_id() {
		return job_id;
	}

	public void setJob_id(Long job_id) {
		this.job_id = job_id;
	}

	public String getJob_code() {
		return job_code;
	}

	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}

	//
	// public String getJob_description() {
	// return job_description;
	// }
	//
	// public void setJob_description(String job_description) {
	// this.job_description = job_description;
	// }

	public PersonVO() {
		table_name = "BTPN_V_EMPLOYEES, BTPN_V_JOBS, BTPN_MST_MENU, BTPN_MST_RESPONSIBILITY, BTPN_MST_RESPONSIBILITY_DTL, BTPN_MST_ROLE_RESPONSIBILITY";
		person_id = "";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PersonVO)) {
			return false;
		}
		PersonVO other = (PersonVO) obj;
		if (this.person_id != null && other.getPerson_id() != null) {
			if (this.person_id.compareTo(other.getPerson_id()) == 0)
				return true;
		}
		return false;
	}

	public String toString() {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("[" + getTable_name() + "]>>>>> ");
		try {
			Field[] fields = PersonVO.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
				strBuff.append(fields[i].getName()).append(" = ")
						.append(fields[i].get(this)).append(", ");
		} catch (IllegalAccessException iae) {
		}
		return strBuff.toString();
	}

	public MenuModel getMymenu() {
		return mymenu;
	}

	public void setMymenu(MenuModel mymenu) {
		this.mymenu = mymenu;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String orgName) {
		org_name = orgName;
	}

	public void setPortalMenu(MenuModel createPortalMenu) {
		this.portalMenu = createPortalMenu;
	}

	public MenuModel getPortalMenu() {
		return portalMenu;
	}

	public List<MenuVO> getListMenu() {
		return listMenu;
	}

	public void setListMenu(List<MenuVO> listMenu) {
		this.listMenu = listMenu;
	}

}
