package com.wo.epos.module.inv.transferItem.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class TransferItemDtlVO extends BaseEntity implements Serializable {

 	private static final long serialVersionUID = -1774351694860295223L;

 	private Long doDtlId;
	private Long doId;
	private Long itemId;
	private Long umId;
	private Long outletId;
	private Long outletTransferFrom;
	private Long outletTransferTo;
	
	private String itemCode;
	private String itemName;
	private String umName;
	private String outletCode;
	private String outletName;
	
	private Long deliveryQty;
	
	

	public Long getDoDtlId() {
		return doDtlId;
	}

	public void setDoDtlId(Long doDtlId) {
		this.doDtlId = doDtlId;
	}

	public Long getDoId() {
		return doId;
	}

	public void setDoId(Long doId) {
		this.doId = doId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUmId() {
		return umId;
	}

	public void setUmId(Long umId) {
		this.umId = umId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public Long getDeliveryQty() {
		return deliveryQty;
	}

	public void setDeliveryQty(Long deliveryQty) {
		this.deliveryQty = deliveryQty;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public String getOutletCode() {
		return outletCode;
	}

	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public Long getOutletTransferFrom() {
		return outletTransferFrom;
	}

	public void setOutletTransferFrom(Long outletTransferFrom) {
		this.outletTransferFrom = outletTransferFrom;
	}

	public Long getOutletTransferTo() {
		return outletTransferTo;
	}

	public void setOutletTransferTo(Long outletTransferTo) {
		this.outletTransferTo = outletTransferTo;
	}
	
	
	
}	
	
	