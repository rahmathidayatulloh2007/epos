package com.wo.epos.module.master.franchise.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "franchiseBean")
@ViewScoped
public class FranchiseBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 8037400614495616837L;
	static Logger logger = Logger.getLogger(FranchiseBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{franchiseSearchBean}")
	private FranchiseSearchBean franchiseSearchBean;
	
	@ManagedProperty(value = "#{franchiseInputBean}")
	private FranchiseInputBean franchiseInputBean;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		franchiseSearchBean.setSearchAll("");
		franchiseSearchBean.setCheckSearch(0);
		franchiseSearchBean.search();
		franchiseInputBean.clearAll();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		franchiseInputBean.setMODE_TYPE("ADD");
		franchiseInputBean.modeAdd();
		franchiseInputBean.clearAll();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		franchiseInputBean.clearAll();
		franchiseInputBean.modeEdit(franchiseSearchBean.getSelectedFranchise());
	}
	
	public void modeDelete(){
		try{
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			franchiseSearchBean.modeDelete(franchiseSearchBean.getSelectedFranchise());
			franchiseSearchBean.search();
		}catch(Exception ex){
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:growlMessage",
					facesUtils.getResourceBundleStringValue("formFranchiseTitle")+" tidak bisa dihapus karena sudah digunakan dibagian lain",
					null);	  
		}	
	}
	
	public void modeSave(){
		if(franchiseInputBean.validate()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			franchiseInputBean.save();
			franchiseSearchBean.search();
	    } 
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public FranchiseSearchBean getFranchiseSearchBean() {
		return franchiseSearchBean;
	}

	public void setFranchiseSearchBean(FranchiseSearchBean franchiseSearchBean) {
		this.franchiseSearchBean = franchiseSearchBean;
	}

	public FranchiseInputBean getFranchiseInputBean() {
		return franchiseInputBean;
	}

	public void setFranchiseInputBean(FranchiseInputBean franchiseInputBean) {
		this.franchiseInputBean = franchiseInputBean;
	}	
}
