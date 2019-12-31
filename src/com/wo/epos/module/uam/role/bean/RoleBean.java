package com.wo.epos.module.uam.role.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.sun.star.sdbc.SQLException;
import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "roleBean")
@ViewScoped
public class RoleBean extends CommonBean implements Serializable{	

	private static final long serialVersionUID = -7695226340286100894L;

	static Logger logger = Logger.getLogger(RoleBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{roleSearchBean}")
	private RoleSearchBean roleSearchBean;
	
	@ManagedProperty(value = "#{roleInputBean}")
	private RoleInputBean roleInputBean;
	
	@ManagedProperty(value = "#{roleMenuAccessBean}")
	private RoleMenuAccessBean roleMenuAccessBean;
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		roleSearchBean.setSearchAll("");
		roleSearchBean.setCheckSearch(0);
		roleSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		roleInputBean.setMODE_TYPE("ADD");
		roleInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		roleInputBean.modeEdit(roleSearchBean.getSelectedRoles());
	}
	
	public void modeDelete(){
		try{
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			roleSearchBean.modeDelete(roleSearchBean.getSelectedRoles());
			roleSearchBean.search();
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils
					.addFacesMsg(
							FacesMessage.SEVERITY_ERROR,
							"frm001:growlMessage",
							facesUtils
									.getResourceBundleStringValue("formRoleTitle")
									+ " tidak bisa dihapus karena sudah digunakan dibagian lain",
							null);
		}
	}
	
	public void modeSave(){
		if (roleInputBean.validateOutlet()) {
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			roleInputBean.save();
			roleSearchBean.clear();
		}
	}

	public void modeMenuAccess(){
		MODE_TYPE = CommonConstants.MODE_TYPE_MENU_ACCESS;
		roleMenuAccessBean.clear();
		roleMenuAccessBean.modeEditMenuAccess(roleSearchBean.getSelectedRoles());
	}
	
	public void modeSaveMenuAccess() throws ClassCastException, SQLException, Exception {
		try{
		     roleMenuAccessBean.saveRoleMenu();
		     MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		} catch (SQLException ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}
	
		

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RoleBean.logger = logger;
	}

	public RoleSearchBean getRoleSearchBean() {
		return roleSearchBean;
	}

	public void setRoleSearchBean(RoleSearchBean roleSearchBean) {
		this.roleSearchBean = roleSearchBean;
	}

	public RoleInputBean getRoleInputBean() {
		return roleInputBean;
	}

	public void setRoleInputBean(RoleInputBean roleInputBean) {
		this.roleInputBean = roleInputBean;
	}

	public RoleMenuAccessBean getRoleMenuAccessBean() {
		return roleMenuAccessBean;
	}

	public void setRoleMenuAccessBean(RoleMenuAccessBean roleMenuAccessBean) {
		this.roleMenuAccessBean = roleMenuAccessBean;
	}

		
		
	
}
