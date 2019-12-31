package com.wo.epos.module.sales.receipt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.register.model.Register;
import com.wo.epos.module.uam.outlet.model.Outlet;

public class Receipt extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1527015570617343223L;
	
	private Long receiptId;
    private Long outletId;
	private Long registerId;
    
    private Outlet outlet;
    private Invoice invoice;
    private Register register;
    
    private String receiptNo;
    private String productResume;
    private String notes;
    
    private Date receiptDate;
    
    private Double receiptAmount;
    
    
    List<ReceiptDtl> receiptDtlList = new ArrayList<ReceiptDtl>();


	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	
	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getProductResume() {
		return productResume;
	}

	public void setProductResume(String productResume) {
		this.productResume = productResume;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public Double getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(Double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public List<ReceiptDtl> getReceiptDtlList() {
		return receiptDtlList;
	}

	public void setReceiptDtlList(List<ReceiptDtl> receiptDtlList) {
		this.receiptDtlList = receiptDtlList;
	}
 
}    