package com.wo.epos.module.inv.DO.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "DOBean")
@ViewScoped
public class DOBean implements Serializable{
	
	private static final long serialVersionUID = -2155060813223951121L;

	static Logger logger = Logger.getLogger(DOBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{DOSearchBean}")
	private DOSearchBean DOSearchBean;
	
	@ManagedProperty(value = "#{DOInputBean}")
	private DOInputBean DOInputBean;
	
	private Long companyId;
	
	private Long outletId;
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext()
		.getRequest();
		
		 HttpSession session = request.getSession(true);
		 
		 UserVO userVO = (UserVO)session.getAttribute(CommonConstants.SESSION_USER);
		 
		 if(userVO!=null){
			 companyId = userVO.getCompanyId();
			 outletId = userVO.getOutletId();
		 }
		 
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		DOSearchBean.setSearchAll("");
		DOSearchBean.setCheckSearch(0);
		DOSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		DOInputBean.setMODE_TYPE("ADD");
		DOInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		DOInputBean.modeEdit(DOSearchBean.getSelectedDOs());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		DOSearchBean.modeDelete(DOSearchBean.getSelectedDOs());
		DOSearchBean.search();
	}
	
	public void modeSave(){
		if (DOInputBean.validateCompany()  && DOInputBean.validate() 
				&& DOInputBean.validatePesanperpindahan()) {
				DOInputBean.save() ;
				MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
				DOSearchBean.search();
			
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
		DOBean.logger = logger;
	}

	public DOSearchBean getDOSearchBean() {
		return DOSearchBean;
	}

	public void setDOSearchBean(DOSearchBean DOSearchBean) {
		this.DOSearchBean = DOSearchBean;
	}

	public DOInputBean getDOInputBean() {
		return DOInputBean;
	}

	public void setDOInputBean(DOInputBean DOInputBean) {
		this.DOInputBean = DOInputBean;
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

	

	
		
	
}
