package com.wo.epos.module.sales.discount.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.category.model.Category;

public class DiscountDtl extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 3546533177996660089L;

	private Long discountDtlId;
	
	private Discount discount;
	private Category category;
	
	public Long getDiscountDtlId() {
		return discountDtlId;
	}
	public void setDiscountDtlId(Long discountDtlId) {
		this.discountDtlId = discountDtlId;
	}
	public Discount getDiscount() {
		return discount;
	}
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	
}
