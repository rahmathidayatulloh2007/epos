package com.wo.epos.module.sales.register.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class Register extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 133290072353074095L;
	
	private Long regId;
	private Long outletId;
	private Long cashierId;
	
	private String statusCode;
	
	private Outlet outlet;
	private Employee cashier;
	private ParameterDtl status;
	
	private Timestamp startTime;
	private Timestamp endTime;
	
	private Double totalPayment;
	
	private List<RegisterDtl> registerDtlList = new ArrayList<RegisterDtl>();
	
	
	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public Long getCashierId() {
		return cashierId;
	}

	public void setCashierId(Long cashierId) {
		this.cashierId = cashierId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public Employee getCashier() {
		return cashier;
	}

	public void setCashier(Employee cashier) {
		this.cashier = cashier;
	}

	public ParameterDtl getStatus() {
		return status;
	}

	public void setStatus(ParameterDtl status) {
		this.status = status;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public List<RegisterDtl> getRegisterDtlList() {
		return registerDtlList;
	}

	public void setRegisterDtlList(List<RegisterDtl> registerDtlList) {
		this.registerDtlList = registerDtlList;
	}		
	
	
	
	
}