package com.wo.epos.module.sales.receipt.model;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.master.paymentType.model.PaymentType;

public class ReceiptDtl extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1527015570617343223L;

	private Long receiptDtlId;
	private Long paymentTypeId;

	private PaymentType paymentType;
	private Receipt receipt;

	private Double paymentAmount;

	private String chequeNumber;
	private String accountNumber;
	private String orderName;
	private String invImg;
	private String underName;
	private String invFileName;
	
	private byte[] invFile;


	private Date chequeCashDate;
	private Date transferDate;

	public Long getReceiptDtlId() {
		return receiptDtlId;
	}

	public void setReceiptDtlId(Long receiptDtlId) {
		this.receiptDtlId = receiptDtlId;
	}

	public Long getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
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

	public String getInvFileName() {
		return invFileName;
	}

	public void setInvFileName(String invFileName) {
		this.invFileName = invFileName;
	}

	public byte[] getInvFile() {
		return invFile;
	}

	public void setInvFile(byte[] invFile) {
		this.invFile = invFile;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}


	
}