package com.wo.epos.module.inv.rn.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "rnBean")
@ViewScoped
public class RnBean implements Serializable{
	
	private static final long serialVersionUID = 4623571209817774226L;

	static Logger logger = Logger.getLogger(RnBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{rnSearchBean}")
	private RnSearchBean rnSearchBean;
	
	@ManagedProperty(value = "#{rnInputBean}")
	private RnInputBean rnInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		rnSearchBean.setSearchAll("");
		rnSearchBean.setCheckSearch(0);
		rnSearchBean.search();
		rnInputBean.modeAdd();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		rnInputBean.setMODE_TYPE("ADD");
		rnInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		rnInputBean.modeEdit(rnSearchBean.getSelectedRns());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		rnSearchBean.modeDelete(rnSearchBean.getSelectedRns());
		rnSearchBean.search();
	}
	
	public void modeSave(){
		if (rnInputBean.validateCompany() && rnInputBean.validateRn()
				&& rnInputBean.purchaseOrder() && rnInputBean.DO()&& rnInputBean.validate()) {

			rnInputBean.save();
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			rnSearchBean.search();

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
		RnBean.logger = logger;
	}

	public RnSearchBean getRnSearchBean() {
		return rnSearchBean;
	}

	public void setRnSearchBean(RnSearchBean rnSearchBean) {
		this.rnSearchBean = rnSearchBean;
	}

	public RnInputBean getRnInputBean() {
		return rnInputBean;
	}

	public void setRnInputBean(RnInputBean rnInputBean) {
		this.rnInputBean = rnInputBean;
	}

	
	
		
	
}
