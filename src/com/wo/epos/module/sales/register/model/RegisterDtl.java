package com.wo.epos.module.sales.register.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class RegisterDtl extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 8035468622603690879L;
	
	private Long regDtlId;
	
	private String paymentMethodCode;
	
	private Register register;
	private ParameterDtl paymentMethod;
		
	private Double initFund;
	private Double totalPayment;
	
	
	public Long getRegDtlId() {
		return regDtlId;
	}
	
	public void setRegDtlId(Long regDtlId) {
		this.regDtlId = regDtlId;
	}

	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}
	
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
	
	public Register getRegister() {
		return register;
	}
	
	public void setRegister(Register register) {
		this.register = register;
	}
	
	public ParameterDtl getPaymentMethod() {
		return paymentMethod;
	}
	
	public void setPaymentMethod(ParameterDtl paymentMethod) {
		this.paymentMethod = paymentMethod;
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
	
	
	
	
	
	
}