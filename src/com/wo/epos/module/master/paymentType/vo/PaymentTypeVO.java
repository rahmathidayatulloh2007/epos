package com.wo.epos.module.master.paymentType.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.sales.payment.service.PaymentService;

public class PaymentTypeVO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6364882492019008148L;

	private Long paytypeId;
	private Long paytypeValue;
	private Long companyId;

	private String companyName;
	private String paymentMethodCode;
	private String paymentMethodName;
	private String paytypeCode;
	private String paytypeName;
	private String giroNo;
	private String imgInvoice;
	private String noRek;
	private String payName;
	private String payUnderName;
	private String invFileName;
	private String strPayValue;

	private byte[] invFile;

	private UploadedFile uploadedFile;

	private Double payValue;
	private Double totalPayment;

	private Date payDate;
	private Date transferDate;

	private List<SelectItem> paymentTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> paymentMethodSelectItem = new ArrayList<SelectItem>();

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public void handleUploadedFile() throws IOException {
		setInvFile(IOUtils.toByteArray(uploadedFile.getInputstream()));
		setInvFileName(getUploadedFile().getFileName());

	}

	public Long getPaytypeId() {
		return paytypeId;
	}

	public void setPaytypeId(Long paytypeId) {
		this.paytypeId = paytypeId;
	}

	public Long getPaytypeValue() {
		return paytypeValue;
	}

	public void setPaytypeValue(Long paytypeValue) {
		this.paytypeValue = paytypeValue;
	}

	public String getPaytypeCode() {
		return paytypeCode;
	}

	public void setPaytypeCode(String paytypeCode) {
		this.paytypeCode = paytypeCode;
	}

	public String getPaytypeName() {
		return paytypeName;
	}

	public void setPaytypeName(String paytypeName) {
		this.paytypeName = paytypeName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}

	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getGiroNo() {
		return giroNo;
	}

	public void setGiroNo(String giroNo) {
		this.giroNo = giroNo;
	}

	public String getImgInvoice() {
		return imgInvoice;
	}

	public void setImgInvoice(String imgInvoice) {
		this.imgInvoice = imgInvoice;
	}

	public String getNoRek() {
		return noRek;
	}

	public void setNoRek(String noRek) {
		this.noRek = noRek;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Double getPayValue() {
		return payValue;
	}

	public void setPayValue(Double payValue) {
		this.payValue = payValue;
	}

	public String getPayUnderName() {
		return payUnderName;
	}

	public void setPayUnderName(String payUnderName) {
		this.payUnderName = payUnderName;
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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getStrPayValue() {
		return strPayValue;
	}

	public void setStrPayValue(String strPayValue) {
		this.strPayValue = strPayValue;
	}

	public List<SelectItem> getPaymentTypeSelectItem() {
		return paymentTypeSelectItem;
	}

	public void setPaymentTypeSelectItem(List<SelectItem> paymentTypeSelectItem) {
		this.paymentTypeSelectItem = paymentTypeSelectItem;
	}

	public List<SelectItem> getPaymentMethodSelectItem() {
		return paymentMethodSelectItem;
	}

	public void setPaymentMethodSelectItem(List<SelectItem> paymentMethodSelectItem) {
		this.paymentMethodSelectItem = paymentMethodSelectItem;
	}

	
}
