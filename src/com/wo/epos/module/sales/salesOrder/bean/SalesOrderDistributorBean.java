package com.wo.epos.module.sales.salesOrder.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "salesOrderDistributorBean")
@ViewScoped
public class SalesOrderDistributorBean implements Serializable{
	
	private static final long serialVersionUID = -2155060813223951121L;

	static Logger logger = Logger.getLogger(SalesOrderDistributorBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{salesOrderDistributorSearchBean}")
	private SalesOrderDistributorSearchBean salesOrderDistributorSearchBean;
	
	@ManagedProperty(value = "#{salesOrderDistributorInputBean}")
	private SalesOrderDistributorInputBean salesOrderDistributorInputBean;
	
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		salesOrderDistributorSearchBean.setSearchAll("");
		salesOrderDistributorSearchBean.setCheckSearch(0);
		salesOrderDistributorSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		salesOrderDistributorInputBean.setMODE_TYPE("ADD");
		salesOrderDistributorInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		/*salesOrderDistributorInputBean.modeEdit(salesOrderDistributorSearchBean.getSelectedSo());*/
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		salesOrderDistributorSearchBean.modeDelete(salesOrderDistributorSearchBean.getSelectedSo());
		salesOrderDistributorSearchBean.search();
	}
	
	public void modeSave(){
		
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		salesOrderDistributorInputBean.saveDiect();;
		salesOrderDistributorSearchBean.search();

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

	public SalesOrderDistributorSearchBean getSalesOrderDistributorSearchBean() {
		return salesOrderDistributorSearchBean;
	}

	public void setSalesOrderDistributorSearchBean(SalesOrderDistributorSearchBean salesOrderDistributorSearchBean) {
		this.salesOrderDistributorSearchBean = salesOrderDistributorSearchBean;
	}

	public SalesOrderDistributorInputBean getSalesOrderDistributorInputBean() {
		return salesOrderDistributorInputBean;
	}

	public void setSalesOrderDistributorInputBean(SalesOrderDistributorInputBean salesOrderDistributorInputBean) {
		this.salesOrderDistributorInputBean = salesOrderDistributorInputBean;
	}

	public static void setLogger(Logger logger) {
		SalesOrderDistributorBean.logger = logger;
	}



	
		
	
}
