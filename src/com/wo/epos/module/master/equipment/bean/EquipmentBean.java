package com.wo.epos.module.master.equipment.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "equipmentBean")
@ViewScoped
public class EquipmentBean implements Serializable{

	
	private static final long serialVersionUID = -6865645181578367462L;
	static Logger logger = Logger.getLogger(EquipmentBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{equipmentSearchBean}")
	private EquipmentSearchBean equipmentSearchBean;
	
	@ManagedProperty(value = "#{equipmentInputBean}")
	private EquipmentInputBean equipmentInputBean;
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		equipmentSearchBean.setSearchAll("");
		equipmentSearchBean.setCheckSearch(0);
		equipmentSearchBean.search();
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		equipmentInputBean.setMODE_TYPE("ADD");
		equipmentInputBean.modeAdd();
	}
	
	public void modeEdit(){
		try{
		   MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		   equipmentInputBean.modeEdit(equipmentSearchBean.getSelectedEquipments());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void modeDelete(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		equipmentSearchBean.modeDelete(equipmentSearchBean.getSelectedEquipments());
		equipmentSearchBean.search();
	}
	
	public void modeSave(){
		if(equipmentInputBean.validate()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			equipmentInputBean.save();
			equipmentSearchBean.search();
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
		EquipmentBean.logger = logger;
	}

	public EquipmentSearchBean getEquipmentSearchBean() {
		return equipmentSearchBean;
	}

	public void setEquipmentSearchBean(EquipmentSearchBean equipmentSearchBean) {
		this.equipmentSearchBean = equipmentSearchBean;
	}

	public EquipmentInputBean getEquipmentInputBean() {
		return equipmentInputBean;
	}

	public void setEquipmentInputBean(EquipmentInputBean equipmentInputBean) {
		this.equipmentInputBean = equipmentInputBean;
	}


}
