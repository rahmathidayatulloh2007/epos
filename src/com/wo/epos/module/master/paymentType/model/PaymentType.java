package com.wo.epos.module.master.paymentType.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class PaymentType extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 8159831289065093896L;
	
	private Long paytypeId;
	private Long paytypeValue;
	private String paytypeCode;
	private String paytypeName;
	
	private Company company;
	private ParameterDtl paymentMethod;
	
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public ParameterDtl getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(ParameterDtl paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
