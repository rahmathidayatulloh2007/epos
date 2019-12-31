package com.wo.epos.module.sales.invoice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class Invoice extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1527015570617343223L;
	
	private Long soInvId;
    private Long outletId;
	private Long salesOrderId;
    
    private Outlet outlet;
    private SalesOrder salesOrder;
    private ParameterDtl status;
    
    private String soInvNo;
    private String productResume;
    private String statusCode;
    
    private Date soInvDate;
    
    
    List<InvoiceDtl> invoiceDtlList = new ArrayList<InvoiceDtl>();


	public Long getSoInvId() {
		return soInvId;
	}

	public void setSoInvId(Long soInvId) {
		this.soInvId = soInvId;
	}

	public Long getOutletId() {
		return outletId;
	}


	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public Long getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getSoInvNo() {
		return soInvNo;
	}

	public void setSoInvNo(String soInvNo) {
		this.soInvNo = soInvNo;
	}

	public String getProductResume() {
		return productResume;
	}

	public void setProductResume(String productResume) {
		this.productResume = productResume;
	}

	public Date getSoInvDate() {
		return soInvDate;
	}

	public void setSoInvDate(Date soInvDate) {
		this.soInvDate = soInvDate;
	}

	public List<InvoiceDtl> getInvoiceDtlList() {
		return invoiceDtlList;
	}

	public void setInvoiceDtlList(List<InvoiceDtl> invoiceDtlList) {
		this.invoiceDtlList = invoiceDtlList;
	}

	public ParameterDtl getStatus() {
		return status;
	}
	
	public void setStatus(ParameterDtl status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

    
    
    
}
