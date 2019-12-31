package com.wo.epos.module.inv.item.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class ProductBom  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -1649182137377083005L;
	
	private Long bomId;
	private Product product;
	private Item item;
	
	private Double itemQty;
	
	public Long getBomId() {
		return bomId;
	}
	public void setBomId(Long bomId) {
		this.bomId = bomId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Double getItemQty() {
		return itemQty;
	}
	public void setItemQty(Double itemQty) {
		this.itemQty = itemQty;
	}
	
	

}
