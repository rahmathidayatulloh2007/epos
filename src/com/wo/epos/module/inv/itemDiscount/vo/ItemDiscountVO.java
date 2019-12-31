package com.wo.epos.module.inv.itemDiscount.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class ItemDiscountVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 7319349178480398920L;

	private Long ItemDiscountId;
	
	private Long itemId;
	private String itemCode;
	
	private Long productId;
	private String productCode;
	
	private Integer discount1;
	private Integer discount2;
	private Integer discount3;
	
	private ParameterDtl parameterDtl;
	
	public Long getItemDiscountId() {
		return ItemDiscountId;
	}
	public void setItemDiscountId(Long itemDiscountId) {
		ItemDiscountId = itemDiscountId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public ParameterDtl getParameterDtl() {
		return parameterDtl;
	}
	public void setParameterDtl(ParameterDtl parameterDtl) {
		this.parameterDtl = parameterDtl;
	}
	public Integer getDiscount1() {
		return discount1;
	}
	public void setDiscount1(Integer discount1) {
		this.discount1 = discount1;
	}
	public Integer getDiscount2() {
		return discount2;
	}
	public void setDiscount2(Integer discount2) {
		this.discount2 = discount2;
	}
	public Integer getDiscount3() {
		return discount3;
	}
	public void setDiscount3(Integer discount3) {
		this.discount3 = discount3;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	
}
