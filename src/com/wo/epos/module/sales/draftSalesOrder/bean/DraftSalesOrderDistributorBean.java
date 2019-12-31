package com.wo.epos.module.sales.draftSalesOrder.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "drafSalesOrderDistributorBean")
@ViewScoped
public class DraftSalesOrderDistributorBean implements Serializable{
	
	private static final long serialVersionUID = -2155060813223951121L;

	static Logger logger = Logger.getLogger(DraftSalesOrderDistributorBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{draftSalesOrderDistributorSearchBean}")
	private DraftSalesOrderDistributorSearchBean draftSalesOrderDistributorSearchBean;
	
	@ManagedProperty(value = "#{draftSalesOrderDistributorInputBean}")
	private DraftSalesOrderDistributorInputBean draftSalesOrderDistributorInputBean;
	
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		draftSalesOrderDistributorSearchBean.setSearchAll("");
		draftSalesOrderDistributorSearchBean.setCheckSearch(0);
		draftSalesOrderDistributorSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		draftSalesOrderDistributorInputBean.setMODE_TYPE("ADD");
		draftSalesOrderDistributorInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		/*salesOrderDistributorInputBean.modeEdit(salesOrderDistributorSearchBean.getSelectedSo());*/
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		/*draftSalesOrderDistributorSearchBean.modeDelete(draftSalesOrderDistributorSearchBean.getSelectedSo());*/
		draftSalesOrderDistributorSearchBean.search();
	}
	
	public void modeSave(){
		
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		draftSalesOrderDistributorInputBean.saveDiect();;
		draftSalesOrderDistributorSearchBean.search();

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

	public DraftSalesOrderDistributorSearchBean getDraftSalesOrderDistributorSearchBean() {
		return draftSalesOrderDistributorSearchBean;
	}

	public void setDraftSalesOrderDistributorSearchBean(
			DraftSalesOrderDistributorSearchBean draftSalesOrderDistributorSearchBean) {
		this.draftSalesOrderDistributorSearchBean = draftSalesOrderDistributorSearchBean;
	}

	public DraftSalesOrderDistributorInputBean getDraftSalesOrderDistributorInputBean() {
		return draftSalesOrderDistributorInputBean;
	}

	public void setDraftSalesOrderDistributorInputBean(
			DraftSalesOrderDistributorInputBean draftSalesOrderDistributorInputBean) {
		this.draftSalesOrderDistributorInputBean = draftSalesOrderDistributorInputBean;
	}

	public static void setLogger(Logger logger) {
		DraftSalesOrderDistributorBean.logger = logger;
	}





	
		
	
}
