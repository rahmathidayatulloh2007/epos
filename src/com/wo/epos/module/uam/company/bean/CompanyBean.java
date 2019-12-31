package com.wo.epos.module.uam.company.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "companyBean")
@ViewScoped
public class CompanyBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(CompanyBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{companySearchBean}")
	private CompanySearchBean companySearchBean;
	
	@ManagedProperty(value = "#{companyInputBean}")
	private CompanyInputBean companyInputBean;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		companySearchBean.setSearchAll("");
		companySearchBean.setCheckSearch(0);
		companySearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		companyInputBean.setMODE_TYPE("ADD");
		companyInputBean.modeAdd();
	}
	
	public void modeEdit(){
		try{
		   MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		   companyInputBean.modeEdit(companySearchBean.getSelectedCompany());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void modeDelete(){
		try{
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			companySearchBean.modeDelete(companySearchBean.getSelectedCompany());
			companySearchBean.search();
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils
					.addFacesMsg(
							FacesMessage.SEVERITY_ERROR,
							"frm001:growlMessage",
							facesUtils
									.getResourceBundleStringValue("formCompanyTitle")
									+ " tidak bisa dihapus karena sudah digunakan dibagian lain",
							null);
		}
	}
	
	public void modeSave(){
		if(companyInputBean.validateCompany()){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		companyInputBean.save();
		companySearchBean.search();
		}	}	

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public CompanySearchBean getCompanySearchBean() {
		return companySearchBean;
	}

	public void setCompanySearchBean(CompanySearchBean companySearchBean) {
		this.companySearchBean = companySearchBean;
	}

	public CompanyInputBean getCompanyInputBean() {
		return companyInputBean;
	}

	public void setCompanyInputBean(CompanyInputBean companyInputBean) {
		this.companyInputBean = companyInputBean;
	}

		
	
}
