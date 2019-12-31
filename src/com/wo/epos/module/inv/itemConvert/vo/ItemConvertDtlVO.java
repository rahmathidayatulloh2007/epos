package com.wo.epos.module.inv.itemConvert.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class ItemConvertDtlVO extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5598673950735504996L;

	private Long convertDtlId;
	private Long convertId;
	private Long itemId;
	private Long umId;
	private Double itemQty;
	private Double stockQty;
	private Double remainQty;

	private String itemCode;
	private String itemName;
	private String umName;

	public Long getConvertDtlId() {
		return convertDtlId;
	}

	public void setConvertDtlId(Long convertDtlId) {
		this.convertDtlId = convertDtlId;
	}

	public Long getConvertId() {
		return convertId;
	}

	public void setConvertId(Long convertId) {
		this.convertId = convertId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getUmId() {
		return umId;
	}

	public void setUmId(Long umId) {
		this.umId = umId;
	}

	public String getUmName() {
		return umName;
	}

	public void setUmName(String umName) {
		this.umName = umName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

}