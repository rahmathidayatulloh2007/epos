package com.wo.epos.module.inv.itemConvert.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "itemConvertBean")
@ViewScoped
public class ItemConvertBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4809365354385475820L;

	static Logger logger = Logger.getLogger(ItemConvertBean.class);

	private String MODE_TYPE;

	@ManagedProperty(value = "#{itemConvertSearchBean}")
	private ItemConvertSearchBean itemConvertSearchBean;

	@ManagedProperty(value = "#{itemConvertInputBean}")
	private ItemConvertInputBean itemConvertInputBean;

	@PostConstruct
	public void postConstruct() {
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}

	public void modeSearch() {
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		itemConvertSearchBean.setSearchAll("");
		itemConvertSearchBean.setCheckSearch(0);
		itemConvertSearchBean.search();
		itemConvertInputBean.modeAdd();
	}

	public void modeAdd() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		itemConvertInputBean.setMODE_TYPE("ADD");
		itemConvertInputBean.modeAdd();
	}

	public void modeView() {
		try {
			MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
			itemConvertInputBean.modeView(itemConvertSearchBean
					.getSelectedItemConvert());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// public void modeEdit() {
	// try {
	// MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
	// itemConvertInputBean.modeEdit(itemConvertSearchBean
	// .getSelectedItemConvert());
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }
	//
	// public void modeDelete() {
	// MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	// itemConvertSearchBean.modeDelete(itemConvertSearchBean
	// .getSelectedItemConvert());
	// itemConvertSearchBean.search();
	// }

	public void modeSave() {
		try {
			if ( itemConvertInputBean.validateCompany() && itemConvertInputBean.validate() ) {
				MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
				itemConvertInputBean.save();
				itemConvertSearchBean.search();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
		ItemConvertBean.logger = logger;
	}

	public ItemConvertSearchBean getItemConvertSearchBean() {
		return itemConvertSearchBean;
	}

	public void setItemConvertSearchBean(
			ItemConvertSearchBean itemConvertSearchBean) {
		this.itemConvertSearchBean = itemConvertSearchBean;
	}

	public ItemConvertInputBean getItemConvertInputBean() {
		return itemConvertInputBean;
	}

	public void setItemConvertInputBean(
			ItemConvertInputBean itemConvertInputBean) {
		this.itemConvertInputBean = itemConvertInputBean;
	}

}
