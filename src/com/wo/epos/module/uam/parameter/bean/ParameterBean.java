package com.wo.epos.module.uam.parameter.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "parameterBean")
@ViewScoped
public class ParameterBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(ParameterBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{parameterSearchBean}")
	private ParameterSearchBean parameterSearchBean;
	
	@ManagedProperty(value = "#{parameterInputBean}")
	private ParameterInputBean parameterInputBean;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		parameterSearchBean.search();
		parameterInputBean.clearAll();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		parameterInputBean.setMODE_TYPE("ADD");
		parameterInputBean.clearAll();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		parameterInputBean.clearAll();
		parameterInputBean.modeEdit(parameterSearchBean.getSelectedParameters());
	}
	
	public void modeDelete(){
		try{
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			parameterSearchBean.modeDelete(parameterSearchBean.getSelectedParameters());
			parameterSearchBean.search();
		}catch(Exception ex){
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:growlMessage",
					"Parameter tidak bisa dihapus karena sudah digunakan dibagian lain",
					null);	    					
		}
	}
	
	public void modeSave(){
		if(parameterInputBean.validasi()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			parameterInputBean.save();
			parameterSearchBean.search();
		}
	}
	

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public ParameterSearchBean getParameterSearchBean() {
		return parameterSearchBean;
	}

	public void setParameterSearchBean(ParameterSearchBean parameterSearchBean) {
		this.parameterSearchBean = parameterSearchBean;
	}


	public ParameterInputBean getParameterInputBean() {
		return parameterInputBean;
	}


	public void setParameterInputBean(ParameterInputBean parameterInputBean) {
		this.parameterInputBean = parameterInputBean;
	}

	
	
}
