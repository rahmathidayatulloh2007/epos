package com.wo.epos.module.inv.item.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class ProductBomVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -8183055322559700567L;
	
	private Long bomId;
	private Long productId;
	private Long itemId;
	private Long umId;

	private Double itemQty;
	
	private String productName;
	private String itemCode;
	private String itemName;
	private String umName;		
	private String completeItem;
	

	public ProductBomVO()
	{
		
	}

	public Long getBomId() {
		return bomId;
	}

	public void setBomId(Long bomId) {
		this.bomId = bomId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Long getUmId() {
		return umId;
	}

	public void setUmId(Long umId) {
		this.umId = umId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUmName() {
		return umName;
	}

	public void setUmName(String umName) {
		this.umName = umName;
	}

	public String getCompleteItem() {
		return completeItem;
	}

	public void setCompleteItem(String completeItem) {
		this.completeItem = completeItem;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	

}
