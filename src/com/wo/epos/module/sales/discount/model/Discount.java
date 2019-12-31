package com.wo.epos.module.sales.discount.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class Discount  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8351350895951425356L;
	
	private Long discountId;
	private String discountCode;
	private String discountName;
	private String categoryResume;
	private Double discountValue;
	
	private Company company;
	private ParameterDtl discountProvider;
	private ParameterDtl discountCategory;
	
	private List<DiscountDtl> listDiscountDtl = new ArrayList<DiscountDtl>();

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getCategoryResume() {
		return categoryResume;
	}

	public void setCategoryResume(String categoryResume) {
		this.categoryResume = categoryResume;
	}

	public Double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public ParameterDtl getDiscountProvider() {
		return discountProvider;
	}

	public void setDiscountProvider(ParameterDtl discountProvider) {
		this.discountProvider = discountProvider;
	}

	public List<DiscountDtl> getListDiscountDtl() {
		return listDiscountDtl;
	}

	public void setListDiscountDtl(List<DiscountDtl> listDiscountDtl) {
		this.listDiscountDtl = listDiscountDtl;
	}

	public ParameterDtl getDiscountCategory() {
		return discountCategory;
	}

	public void setDiscountCategory(ParameterDtl discountCategory) {
		this.discountCategory = discountCategory;
	}

}
