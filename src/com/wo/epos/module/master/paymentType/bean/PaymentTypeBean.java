package com.wo.epos.module.master.paymentType.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "paymentTypeBean")
@ViewScoped
public class PaymentTypeBean  implements Serializable{
	
	private static final long serialVersionUID = 4690972218442883965L;
	static Logger logger = Logger.getLogger(PaymentTypeSearchBean.class);

	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{paymentTypeSearchBean}")
	private PaymentTypeSearchBean paymentTypeSearchBean;
	
	@ManagedProperty(value = "#{paymentTypeInputBean}")
	private PaymentTypeInputBean paymentTypeInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		paymentTypeSearchBean.setSearchAll("");
		paymentTypeSearchBean.setCheckSearch(0);
		paymentTypeSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		paymentTypeInputBean.setMODE_TYPE("ADD");
		paymentTypeInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		paymentTypeInputBean.modeEdit(paymentTypeSearchBean.getSelectedPaymentType());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		paymentTypeSearchBean.modeDelete(paymentTypeSearchBean.getSelectedPaymentType());
		paymentTypeSearchBean.search();
	}
	
	public void modeSave(){
		if(paymentTypeInputBean.validate()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			paymentTypeInputBean.save();
			paymentTypeSearchBean.search();
		}
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public PaymentTypeSearchBean getPaymentTypeSearchBean() {
		return paymentTypeSearchBean;
	}

	public void setPaymentTypeSearchBean(PaymentTypeSearchBean paymentTypeSearchBean) {
		this.paymentTypeSearchBean = paymentTypeSearchBean;
	}

	public PaymentTypeInputBean getPaymentTypeInputBean() {
		return paymentTypeInputBean;
	}

	public void setPaymentTypeInputBean(PaymentTypeInputBean paymentTypeInputBean) {
		this.paymentTypeInputBean = paymentTypeInputBean;
	}
	
}
