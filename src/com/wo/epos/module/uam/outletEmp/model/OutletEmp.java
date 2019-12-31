package com.wo.epos.module.uam.outletEmp.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.outlet.model.Outlet;

public class OutletEmp extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 310286307819067804L;
	
	private Long outletEmpId;
	private Long outletId;	
	private Long employeeId;
	private Long companyId;
	
	private Outlet outlet;
	private Employee employee;
	private Company company;
	
	private String picFlag;
	

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

	public String getPicFlag() {
		return picFlag;
	}

	public void setPicFlag(String picFlag) {
		this.picFlag = picFlag;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
}
