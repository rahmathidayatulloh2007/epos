package com.wo.epos.module.sales.invoice.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;

public class InvoiceDtl  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 3883689153139675926L;
	
	private Long soInvDtlId;
	private Long salesOrderDtlId;
	
	private Invoice invoice;
	private SalesOrderDtl salesOrderDtl;
	
	private Integer lineNo;
	
	private Double invoiceQty;

	
	
	public Long getSoInvDtlId() {
		return soInvDtlId;
	}

	public void setSoInvDtlId(Long soInvDtlId) {
		this.soInvDtlId = soInvDtlId;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public SalesOrderDtl getSalesOrderDtl() {
		return salesOrderDtl;
	}

	public void setSalesOrderDtl(SalesOrderDtl salesOrderDtl) {
		this.salesOrderDtl = salesOrderDtl;
	}

	public Long getSalesOrderDtlId() {
		return salesOrderDtlId;
	}

	public void setSalesOrderDtlId(Long salesOrderDtlId) {
		this.salesOrderDtlId = salesOrderDtlId;
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
