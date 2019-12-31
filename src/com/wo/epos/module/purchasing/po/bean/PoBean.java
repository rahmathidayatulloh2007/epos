package com.wo.epos.module.purchasing.po.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "poBean")
@ViewScoped
public class PoBean implements Serializable{
	
	private static final long serialVersionUID = -4971946424804881152L;

	static Logger logger = Logger.getLogger(PoBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{poSearchBean}")
	private PoSearchBean poSearchBean;
	
	@ManagedProperty(value = "#{poInputBean}")
	private PoInputBean poInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		poSearchBean.setSearchAll("");
		poSearchBean.setCheckSearch(0);
		poSearchBean.search();
		poInputBean.modeAdd();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		poInputBean.setMODE_TYPE("ADD");
		poInputBean.modeAdd();
	}
	
	public void modeEdit(){
		try{
		   MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		   poInputBean.modeEdit(poSearchBean.getSelectedPo());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		poSearchBean.modeDelete(poSearchBean.getSelectedPo());
		poSearchBean.search();
	}
	
	public void modeSave(){
		try{	
			if(poInputBean.validate()){
				MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
				poInputBean.save();
				poSearchBean.search();
			}
		}catch(Exception ex){
			ex.printStackTrace();
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
		PoBean.logger = logger;
	}

	public PoSearchBean getPoSearchBean() {
		return poSearchBean;
	}

	public void setPoSearchBean(PoSearchBean poSearchBean) {
		this.poSearchBean = poSearchBean;
	}

	public PoInputBean getPoInputBean() {
		return poInputBean;
	}

	public void setPoInputBean(PoInputBean poInputBean) {
		this.poInputBean = poInputBean;
	}

	
		
	
}
