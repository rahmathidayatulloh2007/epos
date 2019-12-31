package com.wo.epos.module.inv.um.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "umBean")
@ViewScoped
public class UmBean implements Serializable{
	
	private static final long serialVersionUID = -2155060813223951121L;

	static Logger logger = Logger.getLogger(UmBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{umSearchBean}")
	private UmSearchBean umSearchBean;
	
	@ManagedProperty(value = "#{umInputBean}")
	private UmInputBean umInputBean;
	
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		umSearchBean.setSearchAll("");
		umSearchBean.setCheckSearch(0);
		umSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		umInputBean.setMODE_TYPE("ADD");
		umInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		umInputBean.modeEdit(umSearchBean.getSelectedUms());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		umSearchBean.modeDelete(umSearchBean.getSelectedUms());
		umSearchBean.search();
	}
	
	public void modeSave(){
		if(umInputBean.validateCompany()&& umInputBean.validateUm()){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		umInputBean.save();
		umSearchBean.search();
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
		UmBean.logger = logger;
	}

	public UmSearchBean getUmSearchBean() {
		return umSearchBean;
	}

	public void setUmSearchBean(UmSearchBean umSearchBean) {
		this.umSearchBean = umSearchBean;
	}

	public UmInputBean getUmInputBean() {
		return umInputBean;
	}

	public void setUmInputBean(UmInputBean umInputBean) {
		this.umInputBean = umInputBean;
	}

	
		
	
}
