package com.wo.epos.module.sales.discount.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class DiscountDtlVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -2883972201545010037L;

	private Long discountDtlId;
	
	private Long discountId;	
	private Long categoryId;
	private String discountName;
	private String categoryName;
	
	public Long getDiscountDtlId() {
		return discountDtlId;
	}
	public void setDiscountDtlId(Long discountDtlId) {
		this.discountDtlId = discountDtlId;
	}
	public Long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
