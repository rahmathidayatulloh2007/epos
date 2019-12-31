package com.wo.epos.module.inv.stockOpname.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class StockOpname extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -6921996017724357424L;

	private Long opnameId;

	private Company company;
	private Outlet outlet;
	private String period;
	private String opnameNo;
	private Date opnameDate;
	private String notes;
	private String status;
	private ParameterDtl paramStatus;
	private List<StockOpnameDtl> stockOpnameDtl = new ArrayList<StockOpnameDtl>();
	
	public Long getOpnameId() {
		return opnameId;
	}
	public void setOpnameId(Long opnameId) {
		this.opnameId = opnameId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Outlet getOutlet() {
		return outlet;
	}
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getOpnameNo() {
		return opnameNo;
	}
	public void setOpnameNo(String opnameNo) {
		this.opnameNo = opnameNo;
	}
	public Date getOpnameDate() {
		return opnameDate;
	}
	public void setOpnameDate(Date opnameDate) {
		this.opnameDate = opnameDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<StockOpnameDtl> getStockOpnameDtl() {
		return stockOpnameDtl;
	}
	public void setStockOpnameDtl(List<StockOpnameDtl> stockOpnameDtl) {
		this.stockOpnameDtl = stockOpnameDtl;
	}
	public ParameterDtl getParamStatus() {
		return paramStatus;
	}
	public void setParamStatus(ParameterDtl paramStatus) {
		this.paramStatus = paramStatus;
	}

}
