package com.wo.epos.module.sales.register.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;


public class RegisterDtlVO extends BaseEntity implements Serializable {
	
    private static final long serialVersionUID = 5710312163692205537L;
	
    private Long regDtlId;
	private Long regId;
	
	private String paymentMethodCode;	
	private String registerName;
	private String paymentMethodName;
		
	private Double initFund;
	private Double totalPayment;
	
	private Boolean disableInitFund;

	
	public RegisterDtlVO(){
		
	}

	
	public Long getRegDtlId() {
		return regDtlId;
	}

	public void setRegDtlId(Long regDtlId) {
		this.regDtlId = regDtlId;
	}

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}

	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public Double getInitFund() {
		return initFund;
	}

	public void setInitFund(Double initFund) {
		this.initFund = initFund;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Boolean getDisableInitFund() {
		return disableInitFund;
	}

	public void setDisableInitFund(Boolean disableInitFund) {
		this.disableInitFund = disableInitFund;
	}
	
	
	
}