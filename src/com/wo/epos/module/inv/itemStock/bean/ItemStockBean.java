package com.wo.epos.module.inv.itemStock.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "itemStockBean")
@ViewScoped
public class ItemStockBean implements Serializable{
	
	private static final long serialVersionUID = -2155060813223951121L;

	static Logger logger = Logger.getLogger(ItemStockBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{itemStockSearchBean}")
	private ItemStockSearchBean itemStockSearchBean;
	
	
	private Long companyId;
	
	private Long outletId;
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext()
		.getRequest();
		
		 HttpSession session = request.getSession(true);
		 
		 UserVO userVO = (UserVO)session.getAttribute(CommonConstants.SESSION_USER);
		 
		 if(userVO!=null){
			 companyId = userVO.getCompanyId();
			 outletId = userVO.getOutletId();
		 }
		 
	}
		
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		itemStockSearchBean.setSearchAll("");
		itemStockSearchBean.setCheckSearch(0);
		itemStockSearchBean.search();
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
		ItemStockBean.logger = logger;
	}

	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public ItemStockSearchBean getItemStockSearchBean() {
		return itemStockSearchBean;
	}

	public void setItemStockSearchBean(ItemStockSearchBean itemStockSearchBean) {
		this.itemStockSearchBean = itemStockSearchBean;
	}

	

	
		
	
}
