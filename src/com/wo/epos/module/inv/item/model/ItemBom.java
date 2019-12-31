package com.wo.epos.module.inv.item.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class ItemBom  extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1653331045479642171L;
	
	private Long bomId;
	private Item item;
	private Item itemComposition;
	
	private Double itemQty;

	public Long getBomId() {
		return bomId;
	}

	public void setBomId(Long bomId) {
		this.bomId = bomId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Item getItemComposition() {
		return itemComposition;
	}

	public void setItemComposition(Item itemComposition) {
		this.itemComposition = itemComposition;
	}

	public Double getItemQty() {
		return itemQty;
	}

	public void setItemQty(Double itemQty) {
		this.itemQty = itemQty;
	}
	
	
}
