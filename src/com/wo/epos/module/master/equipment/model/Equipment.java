package com.wo.epos.module.master.equipment.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class Equipment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 7130032291676553711L;
	
	private Long equipmentId ;
	private Long outletId;
	
	private String equipmentCode;
	private String equipmentName;
	private String equipmentTypeCode;
	private String equipmentStatusCode;	
	
	private Outlet outlet;
	private ParameterDtl equipmentType;
	private ParameterDtl equipmentStatus;
		
	
	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Long getOutletId() {
		return outletId;
	}
	
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	
	public String getEquipmentCode() {
		return equipmentCode;
	}
	
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	
	public String getEquipmentName() {
		return equipmentName;
	}
	
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	
	public String getEquipmentTypeCode() {
		return equipmentTypeCode;
	}
	
	public void setEquipmentTypeCode(String equipmentTypeCode) {
		this.equipmentTypeCode = equipmentTypeCode;
	}
	
	public Outlet getOutlet() {
		return outlet;
	}
	
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}
	
	public ParameterDtl getEquipmentType() {
		return equipmentType;
	}
	
	public void setEquipmentType(ParameterDtl equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getEquipmentStatusCode() {
		return equipmentStatusCode;
	}

	public void setEquipmentStatusCode(String equipmentStatusCode) {
		this.equipmentStatusCode = equipmentStatusCode;
	}

	public ParameterDtl getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(ParameterDtl equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}
	
}