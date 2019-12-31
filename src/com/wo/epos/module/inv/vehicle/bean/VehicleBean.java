package com.wo.epos.module.inv.vehicle.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "vehicleBean")
@ViewScoped
public class VehicleBean implements Serializable{

	private static final long serialVersionUID = 8037400614495616837L;
	static Logger logger = Logger.getLogger(VehicleBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{vehicleSearchBean}")
	private VehicleSearchBean vehicleSearchBean;
	
	@ManagedProperty(value = "#{vehicleInputBean}")
	private VehicleInputBean vehicleInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		vehicleSearchBean.setSearchAll("");
		vehicleSearchBean.setCheckSearch(0);
		vehicleSearchBean.search();
		vehicleInputBean.clearAll();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		vehicleInputBean.setMODE_TYPE("ADD");
		vehicleInputBean.modeAdd();
		
	}
	
	public void modeEdit(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		vehicleInputBean.clearAll();
		vehicleInputBean.modeEdit(vehicleSearchBean.getSelectedVehicle());
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		vehicleSearchBean.modeDelete(vehicleSearchBean.getSelectedVehicle());
		vehicleSearchBean.search();
	}
	
	public void modeSave(){
		if(vehicleInputBean.validate()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			vehicleInputBean.save();
			vehicleSearchBean.search();
	    } 
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public VehicleSearchBean getVehicleSearchBean() {
		return vehicleSearchBean;
	}

	public void setVehicleSearchBean(VehicleSearchBean vehicleSearchBean) {
		this.vehicleSearchBean = vehicleSearchBean;
	}

	public VehicleInputBean getVehicleInputBean() {
		return vehicleInputBean;
	}

	public void setVehicleInputBean(VehicleInputBean vehicleInputBean) {
		this.vehicleInputBean = vehicleInputBean;
	}	
}
