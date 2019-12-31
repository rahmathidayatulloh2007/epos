package com.wo.epos.module.sales.invoice.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class InvoiceDtlVO  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 3883689153139675926L;
	
	private Long soInvDtlId;
	private Long soDtlId;
	private Long invoiceId;
		
	private Integer lineNo;
	
	private Double invoiceQty;

	
	
	public Long getSoInvDtlId() {
		return soInvDtlId;
	}

	public void setSoInvDtlId(Long soInvDtlId) {
		this.soInvDtlId = soInvDtlId;
	}
	
	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getSoDtlId() {
		return soDtlId;
	}

	public void setSoDtlId(Long soDtlId) {
		this.soDtlId = soDtlId;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public Double getInvoiceQty() {
		return invoiceQty;
	}

	public void setInvoiceQty(Double invoiceQty) {
		this.invoiceQty = invoiceQty;
	}
	
    

	
}
