package com.wo.epos.module.inv.stockOpname.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "stockOpnameBean")
@ViewScoped
public class StockOpnameBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 8037400614495616837L;
	static Logger logger = Logger.getLogger(StockOpnameBean.class);

	private String MODE_TYPE;

	@ManagedProperty(value = "#{stockOpnameSearchBean}")
	private StockOpnameSearchBean stockOpnameSearchBean;

	@ManagedProperty(value = "#{stockOpnameInputBean}")
	private StockOpnameInputBean stockOpnameInputBean;

	@PostConstruct
	public void postConstruct() {
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}

	public void modeSearch() {
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		stockOpnameSearchBean.setSearchAll("");
		stockOpnameSearchBean.setCheckSearch(0);
		stockOpnameSearchBean.search();
		stockOpnameInputBean.clearAll();
	}

	public void modeAdd() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		stockOpnameInputBean.setMODE_TYPE("ADD");
		stockOpnameInputBean.modeAdd();
		stockOpnameInputBean.clearAll();
	}

	public void modeEdit() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		stockOpnameInputBean.clearAll();
		stockOpnameInputBean.modeEdit(stockOpnameSearchBean
				.getSelectedStockOpname());
	}

	public void modeDelete() {
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		stockOpnameSearchBean.modeDelete(stockOpnameSearchBean.getSelectedStockOpname());
		stockOpnameSearchBean.search();
	}

	public void modeSave() {
		if (stockOpnameInputBean.validateCompany()
				&& stockOpnameInputBean.validate()) {
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			stockOpnameInputBean.save();
			stockOpnameSearchBean.search();
		}
	}

	public void modeClose() {
		stockOpnameSearchBean.modeClose(stockOpnameSearchBean.getSelectedStockOpname());
		stockOpnameSearchBean.search();
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public StockOpnameSearchBean getStockOpnameSearchBean() {
		return stockOpnameSearchBean;
	}

	public void setStockOpnameSearchBean(
			StockOpnameSearchBean stockOpnameSearchBean) {
		this.stockOpnameSearchBean = stockOpnameSearchBean;
	}

	public StockOpnameInputBean getStockOpnameInputBean() {
		return stockOpnameInputBean;
	}

	public void setStockOpnameInputBean(
			StockOpnameInputBean stockOpnameInputBean) {
		this.stockOpnameInputBean = stockOpnameInputBean;
	}

}
