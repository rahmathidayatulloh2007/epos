package com.wo.epos.module.inv.itemDiscount.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class ItemDiscount extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -1212482090848119977L;
	private Long ItemDiscountId;
	private Item itemId;
	private Product productId;
	private ParameterDtl customerType;
	private Integer Discount1;
	private Integer Discount2;
	private Integer Discount3;
	
	public Long getItemDiscountId() {
		return ItemDiscountId;
	}
	public void setItemDiscountId(Long itemDiscountId) {
		ItemDiscountId = itemDiscountId;
	}
	public Item getItemId() {
		return itemId;
	}
	public void setItemId(Item itemId) {
		this.itemId = itemId;
	}
	public ParameterDtl getCustomerType() {
		return customerType;
	}
	public void setCustomerType(ParameterDtl customerType) {
		this.customerType = customerType;
	}
	public Integer getDiscount1() {
		return Discount1;
	}
	public void setDiscount1(Integer discount1) {
		Discount1 = discount1;
	}
	public Integer getDiscount2() {
		return Discount2;
	}
	public void setDiscount2(Integer discount2) {
		Discount2 = discount2;
	}
	public Integer getDiscount3() {
		return Discount3;
	}
	public void setDiscount3(Integer discount3) {
		Discount3 = discount3;
	}
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
	}
	
}



























