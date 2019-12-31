package com.wo.epos.module.sales.draftSalesOrder.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;

@ManagedBean(name = "draftSalesOrderBean")
@ViewScoped
public class DraftSalesOrderBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 3119279313708020948L;

	static Logger logger = Logger.getLogger(DraftSalesOrderBean.class);
			
	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{draftSalesOrderDistributorSearchBean}")
	private DraftSalesOrderDistributorSearchBean draftSalesOrderDistributorSearchBean;
	
	@ManagedProperty(value = "#{draftSalesOrderDistributorInputBean}")
	private DraftSalesOrderDistributorInputBean draftSalesOrderDistributorInputBean;
	
	private String MODE_TYPE;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = "BUSINESS_DISTRIBUTOR";
		if(userSession != null){
			SystemProperty systemProperty = new SystemProperty();		
			if(userSession.getOutletId() !=null){
				Outlet outletLogin = outletService.findById(userSession.getOutletId());
				if(outletLogin.getCompany() !=null){
					systemProperty = systemPropertyService.searchSystemPropertyNameAndCompany(CommonConstants.BUSINESS_TYPE, outletLogin.getCompany().getCompanyId()); 
					MODE_TYPE = systemProperty.getSystemPropertyValue();
				}
			}		
		}}
		

	public void modeSearch(){
		MODE_TYPE = "BUSINESS_DISTRIBUTOR";
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
		MODE_TYPE = "BUSINESS_DISTRIBUTOR";
		/*draftSalesOrderDistributorSearchBean.modeDelete(draftSalesOrderDistributorSearchBean.getSelectedSo());*/
		draftSalesOrderDistributorSearchBean.search();
	}
	
	public void modeSave(){
		
		
		if(draftSalesOrderDistributorInputBean.validateSaveDirect()){
		MODE_TYPE = "BUSINESS_DISTRIBUTOR";
		draftSalesOrderDistributorInputBean.saveDiect();
		draftSalesOrderDistributorSearchBean.search();
		}
		

	}

	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DraftSalesOrderBean.logger = logger;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
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
	
	
	
}
