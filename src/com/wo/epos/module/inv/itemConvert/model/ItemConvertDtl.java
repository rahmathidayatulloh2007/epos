package com.wo.epos.module.inv.itemConvert.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;

public class ItemConvertDtl extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4163546068795961451L;

	private Long convertDtlId;
	private ItemConvert itemConvert;
	private Item item;
	private Double itemQty;
	private Double stockQty;
	private Double remainQty;

	public Long getConvertDtlId() {
		return convertDtlId;
	}

	public void setConvertDtlId(Long convertDtlId) {
		this.convertDtlId = convertDtlId;
	}

	public ItemConvert getItemConvert() {
		return itemConvert;
	}

	public void setItemConvert(ItemConvert itemConvert) {
		this.itemConvert = itemConvert;
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

	public Double getStockQty() {
		return stockQty;
	}

	public void setStockQty(Double stockQty) {
		this.stockQty = stockQty;
	}

	public Double getRemainQty() {
		return remainQty;
	}

	public void setRemainQty(Double remainQty) {
		this.remainQty = remainQty;
	}

}