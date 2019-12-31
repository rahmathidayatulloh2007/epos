package com.wo.epos.module.sales.discount.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.item.bean.ItemBean;

@ManagedBean(name = "discountBean")
@ViewScoped
public class DiscountBean implements Serializable{
	
	private static final long serialVersionUID = -3188406666041660222L;

	static Logger logger = Logger.getLogger(ItemBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{discountSearchBean}")
	private DiscountSearchBean discountSearchBean;
	
	@ManagedProperty(value = "#{discountInputBean}")
	private DiscountInputBean discountInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		discountSearchBean.setSearchAll("");
		discountSearchBean.setCheckSearch(0);
		discountSearchBean.search();
		discountInputBean.clearAll();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		discountInputBean.setMODE_TYPE("ADD");
		discountInputBean.clearAll();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		discountInputBean.clearAll();
		discountInputBean.modeEdit(discountSearchBean.getSelectedDiscount());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		discountSearchBean.modeDelete(discountSearchBean.getSelectedDiscount());
		discountSearchBean.search();
	}
	
	public void modeSave(){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			discountInputBean.save();
			discountSearchBean.search();
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public DiscountSearchBean getDiscountSearchBean() {
		return discountSearchBean;
	}

	public void setDiscountSearchBean(DiscountSearchBean discountSearchBean) {
		this.discountSearchBean = discountSearchBean;
	}

	public DiscountInputBean getDiscountInputBean() {
		return discountInputBean;
	}

	public void setDiscountInputBean(DiscountInputBean discountInputBean) {
		this.discountInputBean = discountInputBean;
	}
}
