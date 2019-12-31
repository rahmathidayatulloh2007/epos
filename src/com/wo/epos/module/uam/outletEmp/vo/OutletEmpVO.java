package com.wo.epos.module.uam.outletEmp.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class OutletEmpVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 6155367409223772764L;
	
	private Long outletEmpId;	
	private Long outletId;	
	private Long employeeId;
	
	private String employeeName;
	private String outletName;
	private String picFlag;
	private String employeeNo;
	private String position;
	
	
	public OutletEmpVO(){
		
	}

	public Long getOutletEmpId() {
		return outletEmpId;
	}

	public void setOutletEmpId(Long outletEmpId) {
		this.outletEmpId = outletEmpId;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getPicFlag() {
		return picFlag;
	}

	public void setPicFlag(String picFlag) {
		this.picFlag = picFlag;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	
	
}
