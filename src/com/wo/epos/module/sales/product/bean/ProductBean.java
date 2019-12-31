package com.wo.epos.module.sales.product.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "productBean")
@ViewScoped
public class ProductBean implements Serializable{
	
	private static final long serialVersionUID = 12098906783497810L;

	static Logger logger = Logger.getLogger(ProductBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{productSearchBean}")
	private ProductSearchBean productSearchBean;
	
	@ManagedProperty(value = "#{productInputBean}")
	private ProductInputBean productInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		productSearchBean.setSearchAll("");
		productSearchBean.setCheckSearch(0);
		productSearchBean.search();
		productInputBean.modeAdd();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		productInputBean.setMODE_TYPE("ADD");
		productInputBean.modeAdd();
	}
	
	public void modeEdit(){
		try{
		   MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		   productInputBean.modeEdit(productSearchBean.getSelectedProducts());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		productSearchBean.modeDelete(productSearchBean.getSelectedProducts());
		productSearchBean.search();
	}
	
	public void modeSave(){
		
		if(productInputBean.validateCompany()&& productInputBean.validateProduct() && productInputBean.validate()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			productInputBean.save();
			productSearchBean.search();
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
		ProductBean.logger = logger;
	}

	public ProductSearchBean getProductSearchBean() {
		return productSearchBean;
	}

	public void setProductSearchBean(ProductSearchBean productSearchBean) {
		this.productSearchBean = productSearchBean;
	}

	public ProductInputBean getProductInputBean() {
		return productInputBean;
	}

	public void setProductInputBean(ProductInputBean productInputBean) {
		this.productInputBean = productInputBean;
	}

	
}
