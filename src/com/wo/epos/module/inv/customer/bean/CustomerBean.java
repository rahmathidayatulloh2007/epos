package com.wo.epos.module.inv.customer.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;


@ManagedBean(name = "customerBean")
@ViewScoped
public class CustomerBean implements Serializable{

	private static final long serialVersionUID = -3517429188827712315L;
	
	static Logger logger = Logger.getLogger(CustomerBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{customerSearchBean}")
	private CustomerSearchBean customerSearchBean;
	
	@ManagedProperty(value = "#{customerInputBean}")
	private CustomerInputBean customerInputBean;
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		customerSearchBean.setSearchAll("");
		customerSearchBean.setCheckSearch(0);
		customerSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		customerInputBean.setMODE_TYPE("ADD");
		customerInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		customerInputBean.modeEdit(customerSearchBean.getSelectedCustomer());
	}
	
	public void modeView(){
		MODE_TYPE = "VIEW";
		//customerEmpBean.modeView(customerSearchBean.getSelectedCustomer());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		
		customerSearchBean.modeDelete(customerSearchBean.getSelectedCustomer());
		customerSearchBean.search();
	}
	
	public void modeSave(){
		if(!customerInputBean.save()){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		customerSearchBean.search();
		}
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public CustomerSearchBean getCustomerSearchBean() {
		return customerSearchBean;
	}

	public void setCustomerSearchBean(CustomerSearchBean customerSearchBean) {
		this.customerSearchBean = customerSearchBean;
	}

	public CustomerInputBean getCustomerInputBean() {
		return customerInputBean;
	}

	public void setCustomerInputBean(CustomerInputBean customerInputBean) {
		this.customerInputBean = customerInputBean;
	}

	
	
	

}
