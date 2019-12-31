package com.wo.epos.module.inv.item.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "itemBean")
@ViewScoped
public class ItemBean implements Serializable{
	
	private static final long serialVersionUID = -6683824737820520398L;

	static Logger logger = Logger.getLogger(ItemBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{itemSearchBean}")
	private ItemSearchBean itemSearchBean;
	
	@ManagedProperty(value = "#{itemInputBean}")
	private ItemInputBean itemInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		itemSearchBean.setSearchAll("");
		itemSearchBean.setCheckSearch(0);
		itemSearchBean.search();
		itemInputBean.clearAll();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		itemInputBean.setMODE_TYPE("ADD");
		itemInputBean.clearAll();
		itemInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		itemInputBean.clearAll();
		itemInputBean.modeEdit(itemSearchBean.getSelectedItems());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		itemSearchBean.modeDelete(itemSearchBean.getSelectedItems());
		itemSearchBean.search();
	}
	
	public void modeSave(){
		if(itemInputBean.validateCompany() && itemInputBean.validateItem()&& itemInputBean.validasi()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			itemInputBean.save();
			itemSearchBean.search();
		}
		
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public ItemSearchBean getItemSearchBean() {
		return itemSearchBean;
	}

	public void setItemSearchBean(ItemSearchBean itemSearchBean) {
		this.itemSearchBean = itemSearchBean;
	}

	public ItemInputBean getItemInputBean() {
		return itemInputBean;
	}

	public void setItemInputBean(ItemInputBean itemInputBean) {
		this.itemInputBean = itemInputBean;
	}
	

}
