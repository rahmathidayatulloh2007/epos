package com.wo.epos.module.sales.receipt.vo;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;

public class ReceiptDtlVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -9032794676178231212L;

	private Long receiptDtlId;
    private Long receiptId;
	private Long paymentTypeId;
    
    private Double paymentAmount;
    
    private String chequeNumber;
	private String accountNumber;
	private String orderName;
	private String invImg;
	private String underName;

	private Date chequeCashDate;
	private Date transferDate;

	public Long getReceiptDtlId() {
		return receiptDtlId;
	}

	public void setReceiptDtlId(Long receiptDtlId) {
		this.receiptDtlId = receiptDtlId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getInvImg() {
		return invImg;
	}

	public void setInvImg(String invImg) {
		this.invImg = invImg;
	}

	public String getUnderName() {
		return underName;
	}

	public void setUnderName(String underName) {
		this.underName = underName;
	}

	public Date getChequeCashDate() {
		return chequeCashDate;
	}

	public void setChequeCashDate(Date chequeCashDate) {
		this.chequeCashDate = chequeCashDate;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
    
	
}    