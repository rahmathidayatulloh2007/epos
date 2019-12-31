package com.wo.epos.module.master.equipment.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;


public class EquipmentVO extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1021188643473827405L;
	
	private Long equipmentId;
	private Long outletId;
	private Long companyId;
	
	private String outletCode;
	private String outletName;
	private String equipmentCode;
	private String equipmentName;
	private String equipmentTypeCode;	
	private String equipmentTypeName;
	private String equipmentStatusCode;
	private String equipmentStatusName;
	private String companyName;
	
	
	public EquipmentVO(){
		
	}

	
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

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
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

	public String getEquipmentTypeName() {
		return equipmentTypeName;
	}

	public void setEquipmentTypeName(String equipmentTypeName) {
		this.equipmentTypeName = equipmentTypeName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEquipmentStatusCode() {
		return equipmentStatusCode;
	}

	public void setEquipmentStatusCode(String equipmentStatusCode) {
		this.equipmentStatusCode = equipmentStatusCode;
	}

	public String getEquipmentStatusName() {
		return equipmentStatusName;
	}

	public void setEquipmentStatusName(String equipmentStatusName) {
		this.equipmentStatusName = equipmentStatusName;
	}
	
	
	
	
}	