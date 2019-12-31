package com.wo.epos.module.inv.transferItem.bean;

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

@ManagedBean(name = "transferItemBean")
@ViewScoped
public class TransferItemBean implements Serializable{
	
	private static final long serialVersionUID = -2155060813223951121L;

	static Logger logger = Logger.getLogger(TransferItemBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{transferItemSearchBean}")
	private TransferItemSearchBean transferItemSearchBean;
	
	@ManagedProperty(value = "#{transferItemInputBean}")
	private TransferItemInputBean transferItemInputBean;
	
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
		transferItemSearchBean.setSearchAll("");
		transferItemSearchBean.setCheckSearch(0);
		transferItemSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		transferItemInputBean.setMODE_TYPE("ADD");
		transferItemInputBean.modeAdd();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		transferItemInputBean.modeEdit(transferItemSearchBean.getSelectedTransferItems());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		transferItemSearchBean.modeDelete(transferItemSearchBean.getSelectedTransferItems());
		transferItemSearchBean.search();
	}
	
	public void modeSave(){
		
		if(!transferItemInputBean.save()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			transferItemSearchBean.search();
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
		TransferItemBean.logger = logger;
	}

	public TransferItemSearchBean getTransferItemSearchBean() {
		return transferItemSearchBean;
	}

	public void setTransferItemSearchBean(TransferItemSearchBean transferItemSearchBean) {
		this.transferItemSearchBean = transferItemSearchBean;
	}

	public TransferItemInputBean getTransferItemInputBean() {
		return transferItemInputBean;
	}

	public void setTransferItemInputBean(TransferItemInputBean transferItemInputBean) {
		this.transferItemInputBean = transferItemInputBean;
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

	

	
		
	
}
