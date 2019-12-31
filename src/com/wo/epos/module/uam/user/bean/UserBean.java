package com.wo.epos.module.uam.user.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "userBean")
@ViewScoped
public class UserBean implements Serializable{
	
	private static final long serialVersionUID = 7652680652038019924L;
	static Logger logger = Logger.getLogger(UserBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{userSearchBean}")
	private UserSearchBean userSearchBean;
	
	@ManagedProperty(value = "#{userInputBean}")
	private UserInputBean userInputBean;
		
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		userSearchBean.setSearchAll("");
		userSearchBean.setCheckSearch(0);
		userSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		userInputBean.setMODE_TYPE("ADD");
		userInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		userInputBean.modeEdit(userSearchBean.getSelectedUsers());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		userSearchBean.modeDelete(userSearchBean.getSelectedUsers());
		userSearchBean.search();
	}
	
	public void modeSave(){
		if(userInputBean.validateKosong() ){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			userInputBean.save();
		//	RequestContext.getCurrentInstance().update("frm001 frm001:dataTableUser");
			userSearchBean.search();
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		UserBean.logger = logger;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public UserSearchBean getUserSearchBean() {
		return userSearchBean;
	}

	public void setUserSearchBean(UserSearchBean userSearchBean) {
		this.userSearchBean = userSearchBean;
	}

	public UserInputBean getUserInputBean() {
		return userInputBean;
	}

	public void setUserInputBean(UserInputBean userInputBean) {
		this.userInputBean = userInputBean;
	}	

	
}
