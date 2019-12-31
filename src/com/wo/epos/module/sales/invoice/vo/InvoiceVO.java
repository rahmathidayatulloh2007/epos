package com.wo.epos.module.sales.invoice.vo;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;

public class InvoiceVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -6461755445492400021L;
	
	private Long soInvId;
    private Long outletId;
	private Long salesOrderId;
    
    private String outletName;    
    private String soInvNo;
    private String productResume;
    
    private Date soInvDate;
    

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

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
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


    
    
    
}
