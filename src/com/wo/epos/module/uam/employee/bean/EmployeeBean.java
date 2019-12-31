package com.wo.epos.module.uam.employee.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "employeeBean")
@ViewScoped
public class EmployeeBean implements Serializable{

	private static final long serialVersionUID = 8037400614495616837L;
	static Logger logger = Logger.getLogger(EmployeeBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{employeeSearchBean}")
	private EmployeeSearchBean employeeSearchBean;
	
	@ManagedProperty(value = "#{employeeInputBean}")
	private EmployeeInputBean employeeInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		employeeSearchBean.setSearchAll("");
		employeeSearchBean.setCheckSearch(0);
		employeeSearchBean.search();
		employeeInputBean.clearAll();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		employeeInputBean.setMODE_TYPE("ADD");
		employeeInputBean.modeAdd();
		employeeInputBean.clearAll();
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		employeeInputBean.clearAll();
		employeeInputBean.modeEdit(employeeSearchBean.getSelectedEmployee());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		employeeSearchBean.modeDelete(employeeSearchBean.getSelectedEmployee());
		employeeSearchBean.search();
	}
	
	public void modeSave(){
		if(employeeInputBean.validateCompany()&&employeeInputBean.validateEmployee()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			employeeInputBean.save();
			employeeSearchBean.search();
	    } 
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public EmployeeSearchBean getEmployeeSearchBean() {
		return employeeSearchBean;
	}

	public void setEmployeeSearchBean(EmployeeSearchBean employeeSearchBean) {
		this.employeeSearchBean = employeeSearchBean;
	}

	public EmployeeInputBean getEmployeeInputBean() {
		return employeeInputBean;
	}

	public void setEmployeeInputBean(EmployeeInputBean employeeInputBean) {
		this.employeeInputBean = employeeInputBean;
	}	
}
