package com.wo.epos.module.master.systemProperty.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "systemPropertyBean")
@ViewScoped
public class SystemPropertyBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 1956513352091677697L;

	static Logger logger = Logger.getLogger(SystemPropertyBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{systemPropertySearchBean}")
	private SystemPropertySearchBean systemPropertySearchBean;
	
	@ManagedProperty(value = "#{systemPropertyInputBean}")
	private SystemPropertyInputBean systemPropertyInputBean;

	@ManagedProperty(value = "#{systemPropertyInput2Bean}")
	private SystemPropertyInput2Bean systemPropertyInput2Bean;
	
	Long companyId;
	Long outletId;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		
//		HttpServletRequest request = (HttpServletRequest) FacesContext
//				.getCurrentInstance().getExternalContext().getRequest();
//
//		HttpSession session = request.getSession(true);
//		UserVO userVO = (UserVO) session
//				.getAttribute(CommonConstants.SESSION_USER);

		if (userSession != null) {
			companyId = userSession.getCompanyId();
			outletId  = userSession.getOutletId();
		}
		
		if(companyId!=null || outletId!=null){
			MODE_TYPE = "ADD_NON_ADMIN";
		}else{
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		}
		
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		systemPropertySearchBean.setSearchAll("");
		systemPropertySearchBean.setCheckSearch(0);
		systemPropertySearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		systemPropertyInput2Bean.setMODE_TYPE("ADD");
		systemPropertyInput2Bean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		systemPropertyInput2Bean.modeEdit(systemPropertySearchBean.getSelectedCompanys());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		systemPropertySearchBean.modeDelete(systemPropertySearchBean.getSelectedCompanys());
		systemPropertySearchBean.search();
	}
	
	public void modeSave() {
		if(systemPropertyInput2Bean.validate()){
			try {
				systemPropertyInput2Bean.save();
				if (userSession.getCompanyId() != null) {
					addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:global",
							"Save Successfull", null);
				} else {
					MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
					systemPropertySearchBean.clear();
				}
			} catch (Exception ex) {
				logger.error("Save Failed", ex);
				addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:global",
						"Save Failed, " + ex.toString(), null);
			}
		}
		
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public SystemPropertySearchBean getSystemPropertySearchBean() {
		return systemPropertySearchBean;
	}

	public void setSystemPropertySearchBean(
			SystemPropertySearchBean systemPropertySearchBean) {
		this.systemPropertySearchBean = systemPropertySearchBean;
	}

	public SystemPropertyInputBean getSystemPropertyInputBean() {
		return systemPropertyInputBean;
	}

	public void setSystemPropertyInputBean(
			SystemPropertyInputBean systemPropertyInputBean) {
		this.systemPropertyInputBean = systemPropertyInputBean;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public SystemPropertyInput2Bean getSystemPropertyInput2Bean() {
		return systemPropertyInput2Bean;
	}

	public void setSystemPropertyInput2Bean(
			SystemPropertyInput2Bean systemPropertyInput2Bean) {
		this.systemPropertyInput2Bean = systemPropertyInput2Bean;
	}
}
