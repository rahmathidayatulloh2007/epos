package com.wo.epos.module.sales.register.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "registerBean")
@ViewScoped
public class RegisterBean implements Serializable{
	
	private static final long serialVersionUID = 230600272955236974L;

	static Logger logger = Logger.getLogger(RegisterBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{registerSearchBean}")
	private RegisterSearchBean registerSearchBean;
	
	@ManagedProperty(value = "#{registerInputBean}")
	private RegisterInputBean registerInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		registerSearchBean.setSearchAll("");
		registerSearchBean.setCheckSearch(0);
		registerSearchBean.search();
		registerInputBean.modeAdd();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		registerInputBean.setMODE_TYPE("ADD");
		registerInputBean.modeAdd();
	}
	
	public void modeEdit(){
		try{
		   MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		   registerInputBean.modeEdit(registerSearchBean.getSelectedRegisters());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void modeDelete(){
		if(registerSearchBean.validDelete(registerSearchBean.getSelectedRegisters())){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			registerSearchBean.modeDelete(registerSearchBean.getSelectedRegisters());
			registerSearchBean.search();
		}
	}
	
	public void modeSave(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		registerInputBean.save();
		registerSearchBean.search();
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
		RegisterBean.logger = logger;
	}

	public RegisterSearchBean getRegisterSearchBean() {
		return registerSearchBean;
	}

	public void setRegisterSearchBean(RegisterSearchBean registerSearchBean) {
		this.registerSearchBean = registerSearchBean;
	}

	public RegisterInputBean getRegisterInputBean() {
		return registerInputBean;
	}

	public void setRegisterInputBean(RegisterInputBean registerInputBean) {
		this.registerInputBean = registerInputBean;
	}
	

}
