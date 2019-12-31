package com.wo.epos.module.sales.register.vo;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;


public class RegisterVO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4389125084188201963L;
	
    private Long regId;
	private Long outletId;
	private Long cashierId;
	private Long companyId;
	
	private String statusCode;	
	private String outletCode;
	private String outletName;
	private String cashierCode;
	private String cashierName;
	private String statusName;
	private String time;
	private String companyCode;
	private String companyName;
	
	private Date startTime;
	private Date endTime;
	
	private Date startDate;
	private Date endDate;
	
	private Double totalPaymentCash;
	private Double totalPaymentDebit;
	private Double totalPaymentCredit;
	private Double totalPayment;

	
	public RegisterVO(){
		
	}
	
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

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCashierCode() {
		return cashierCode;
	}

	public void setCashierCode(String cashierCode) {
		this.cashierCode = cashierCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getTotalPaymentCash() {
		return totalPaymentCash;
	}

	public void setTotalPaymentCash(Double totalPaymentCash) {
		this.totalPaymentCash = totalPaymentCash;
	}

	public Double getTotalPaymentDebit() {
		return totalPaymentDebit;
	}

	public void setTotalPaymentDebit(Double totalPaymentDebit) {
		this.totalPaymentDebit = totalPaymentDebit;
	}

	public Double getTotalPaymentCredit() {
		return totalPaymentCredit;
	}

	public void setTotalPaymentCredit(Double totalPaymentCredit) {
		this.totalPaymentCredit = totalPaymentCredit;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	
		
	
}