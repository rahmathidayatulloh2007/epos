package com.wo.epos.module.sales.customer.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "customerBeanSales")
@ViewScoped
public class CustomerBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -2757530445847128167L;
	
	static Logger logger = Logger.getLogger(CustomerBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{customerSearchBeanSales}")
	private CustomerSearchBean customerSearchBeanSales;
	
	@ManagedProperty(value = "#{customerInputBeanSales}")
	private CustomerInputBean customerInputBeanSales;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		customerSearchBeanSales.search();
		customerSearchBeanSales.setSearchAll("");
		customerSearchBeanSales.setCheckSearch(0);
		customerSearchBeanSales.search();
	
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		customerInputBeanSales.setMODE_TYPE("ADD");
		customerInputBeanSales.modeAdd();
		
	}
	
	public void modeEdit(){
		try{
			  MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
			  customerInputBeanSales.modeEdit(customerSearchBeanSales.getSelectedCustomer());
			}catch(Exception ex){
				ex.printStackTrace();
			}
		
	}
	
	public void modeDelete(){
			
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		customerSearchBeanSales.modeDelete(customerSearchBeanSales.getSelectedCustomer());
		customerSearchBeanSales.search();

	}
	
	public void modeSave(){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			customerInputBeanSales.save();
			customerSearchBeanSales.search();
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public CustomerSearchBean getCustomerSearchBeanSales() {
		return customerSearchBeanSales;
	}

	public void setCustomerSearchBeanSales(CustomerSearchBean customerSearchBeanSales) {
		this.customerSearchBeanSales = customerSearchBeanSales;
	}

	public CustomerInputBean getCustomerInputBeanSales() {
		return customerInputBeanSales;
	}

	public void setCustomerInputBeanSales(CustomerInputBean customerInputBeanSales) {
		this.customerInputBeanSales = customerInputBeanSales;
	}



	

}
