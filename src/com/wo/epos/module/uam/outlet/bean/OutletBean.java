package com.wo.epos.module.uam.outlet.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;


@ManagedBean(name = "outletBean")
@ViewScoped
public class OutletBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -3517429188827712315L;
	
	static Logger logger = Logger.getLogger(OutletBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{outletSearchBean}")
	private OutletSearchBean outletSearchBean;
	
	@ManagedProperty(value = "#{outletInputBean}")
	private OutletInputBean outletInputBean;
	
	@ManagedProperty(value = "#{outletEmpBean}")
	private OutletEmpBean outletEmpBean;
	
	@PostConstruct
	public void postConstruct(){
		
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		outletSearchBean.setSearchAll("");
		outletSearchBean.setCheckSearch(0);
		outletSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		outletInputBean.setMODE_TYPE("ADD");
		outletInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		outletInputBean.modeEdit(outletSearchBean.getSelectedOutlet());
	}
	
	public void modeView(){
		MODE_TYPE = "VIEW";
		outletEmpBean.modeView(outletSearchBean.getSelectedOutlet());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		
		outletSearchBean.modeDelete(outletSearchBean.getSelectedOutlet());
		outletSearchBean.search();
	}
	
	public void modeSave(){
		if (outletInputBean.validateOutlet()) {
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			outletInputBean.save();
			outletSearchBean.search();
		}
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public OutletSearchBean getOutletSearchBean() {
		return outletSearchBean;
	}

	public void setOutletSearchBean(OutletSearchBean outletSearchBean) {
		this.outletSearchBean = outletSearchBean;
	}

	public OutletInputBean getOutletInputBean() {
		return outletInputBean;
	}

	public void setOutletInputBean(OutletInputBean outletInputBean) {
		this.outletInputBean = outletInputBean;
	}

	public OutletEmpBean getOutletEmpBean() {
		return outletEmpBean;
	}

	public void setOutletEmpBean(OutletEmpBean outletEmpBean) {
		this.outletEmpBean = outletEmpBean;
	}	
	
	

}
