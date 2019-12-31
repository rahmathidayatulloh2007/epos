package com.wo.epos.module.inv.stockOpname.vo;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.common.util.DateUtil;
import com.wo.epos.module.inv.stockOpname.model.StockOpnameDtl;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class StockOpnameVO extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -3956809983667140750L;
	
	private Long opnameId;
	private Long companyId;
	private Long outletId;

	private Company company;
	private Outlet outlet;
	private ParameterDtl paramStatus;
	private String period;
	private String periodBulan;
	private String periodTahun;
	private String opnameNo;
	private Date opnameDate;
	private Date opnameDateAwal;
	private Date opnameDateAkhir;
	private String notes;
	private String status;
	
	private List<StockOpnameDtl> stockOpnameDtl;
	
	public Long getCompanyId() {
		return companyId;
	}
	public Date getOpnameDateAwal() {
		return opnameDateAwal;
	}
	public void setOpnameDateAwal(Date opnameDateAwal) {
		this.opnameDateAwal = opnameDateAwal;
	}
	public Date getOpnameDateAkhir() {
		return opnameDateAkhir;
	}
	public void setOpnameDateAkhir(Date opnameDateAkhir) {
		this.opnameDateAkhir = opnameDateAkhir;
	}
	public ParameterDtl getParamStatus() {
		return paramStatus;
	}
	public void setParamStatus(ParameterDtl paramStatus) {
		this.paramStatus = paramStatus;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
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
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getOpnameNo() {
		return opnameNo;
	}
	public void setOpnameNo(String opnameNo) {
		this.opnameNo = opnameNo;
	}
	public String getPeriod() {
		String bulanInt = period.substring(0, 2);
		String tahunInt = period.substring(2, 6);
		
		String[] months = new DateFormatSymbols().getShortMonths();
		String bulanString = months[Integer.valueOf(bulanInt)];
		period = bulanString.concat(" ").concat(tahunInt);
		return period;
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
	public String getPeriodBulan() {
		return periodBulan;
	}
	public void setPeriodBulan(String periodBulan) {
		this.periodBulan = periodBulan;
	}
	public String getPeriodTahun() {
		return periodTahun;
	}
	public void setPeriodTahun(String periodTahun) {
		this.periodTahun = periodTahun;
	}
	public List<StockOpnameDtl> getStockOpnameDtl() {
		return stockOpnameDtl;
	}
	public void setStockOpnameDtl(List<StockOpnameDtl> stockOpnameDtl) {
		this.stockOpnameDtl = stockOpnameDtl;
	}
	
}
