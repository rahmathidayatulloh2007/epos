package com.wo.epos.module.sales.payment.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;

public class PaymentVO extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4851231121737280943L;
	
	private Long companyId;
	private Long outletId;
	private Long customerId;
	private Long soId;
	private Long soDtlId;
	private Long paymentTypeId;

	private String parameterDtlCode;
	private String statusCode;
	private String soTypeCode;

	private Double paymentAmount;
	private Double changeAmount;
	private Double subTotal;
	private Double sumTotal;

	private List<SalesOrderDtl> salesOrderDtlList = new ArrayList<SalesOrderDtl>();

	public Long getCompanyId() {
		return companyId;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getSoId() {
		return soId;
	}

	public void setSoId(Long soId) {
		this.soId = soId;
	}

	public Long getSoDtlId() {
		return soDtlId;
	}

	public void setSoDtlId(Long soDtlId) {
		this.soDtlId = soDtlId;
	}

	public List<SalesOrderDtl> getSalesOrderDtlList() {
		return salesOrderDtlList;
	}

	public void setSalesOrderDtlList(List<SalesOrderDtl> salesOrderDtlList) {
		this.salesOrderDtlList = salesOrderDtlList;
	}

	public String getParameterDtlCode() {
		return parameterDtlCode;
	}

	public void setParameterDtlCode(String parameterDtlCode) {
		this.parameterDtlCode = parameterDtlCode;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(Double sumTotal) {
		this.sumTotal = sumTotal;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getSoTypeCode() {
		return soTypeCode;
	}

	public void setSoTypeCode(String soTypeCode) {
		this.soTypeCode = soTypeCode;
	}

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

}
