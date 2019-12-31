package com.wo.epos.module.sales.discount.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.sales.discount.model.DiscountDtl;

public class DiscountVO  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1347051277460519978L;
	
	private Long discountId;
	private Long companyId;
	private String companyName;
	private String discountCode;
	private String discountName;
	private String categoryResume;
	private String discountProviderCode;
	private String discountProviderName;
	private String discountCategoryCode;
	private String discountCategoryName;
	private Double discountValue;
	
	private List<DiscountDtl> listDiscountDtl = new ArrayList<DiscountDtl>();

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
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

	public String getDiscountProviderCode() {
		return discountProviderCode;
	}

	public void setDiscountProviderCode(String discountProviderCode) {
		this.discountProviderCode = discountProviderCode;
	}

	public String getDiscountProviderName() {
		return discountProviderName;
	}

	public void setDiscountProviderName(String discountProviderName) {
		this.discountProviderName = discountProviderName;
	}

	public Double getDiscountValue() {
		return discountValue;
	}


	public String getDiscountCategoryCode() {
		return discountCategoryCode;
	}

	public void setDiscountCategoryCode(String discountCategoryCode) {
		this.discountCategoryCode = discountCategoryCode;
	}

	public String getDiscountCategoryName() {
		return discountCategoryName;
	}

	public void setDiscountCategoryName(String discountCategoryName) {
		this.discountCategoryName = discountCategoryName;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
	}

	public List<DiscountDtl> getListDiscountDtl() {
		return listDiscountDtl;
	}

	public void setListDiscountDtl(List<DiscountDtl> listDiscountDtl) {
		this.listDiscountDtl = listDiscountDtl;
	}
	
}
