package com.wo.epos.module.purchasing.supplier.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "supplierBean")
@ViewScoped
public class SupplierBean {
	
static Logger logger = Logger.getLogger(SupplierBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{supplierSearchBean}")
	private SupplierSearchBean supplierSearchBean;
	
	@ManagedProperty(value = "#{supplierInputBean}")
	private SupplierInputBean supplierInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		supplierSearchBean.setSearchAll("");
		supplierSearchBean.setCheckSearch(0);
		supplierSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		supplierInputBean.setMODE_TYPE("ADD");
		supplierInputBean.modeAdd();
	}
	
	public void modeEdit(){
		try{
		  MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		   supplierInputBean.modeEdit(supplierSearchBean.getSelectedSupplier());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		supplierSearchBean.modeDelete(supplierSearchBean.getSelectedSupplier());
		supplierSearchBean.search();
	}
	
	public void modeSave(){
		if(supplierInputBean.validate()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			supplierInputBean.save();
			supplierSearchBean.search();
		}
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public SupplierSearchBean getSupplierSearchBean() {
		return supplierSearchBean;
	}

	public void setSupplierSearchBean(SupplierSearchBean supplierSearchBean) {
		this.supplierSearchBean = supplierSearchBean;
	}

	public SupplierInputBean getSupplierInputBean() {
		return supplierInputBean;
	}

	public void setSupplierInputBean(SupplierInputBean supplierInputBean) {
		this.supplierInputBean = supplierInputBean;
	}	
	

}
