package com.wo.epos.module.purchasing.po.vo;


import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;


public class PoDtlVO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6154602990686013768L;

	private Long poDtlId;
	private Long outletId;
	private Long poId;
	private Long itemId;
	private Long lineNo;	
	private Long orderUm;	
	private Long umId;
	private Long reorderQtyItem;
		
	private Double orderQty;
	private Double receiveQty;
	private Double unitPrice;		
	private Double discPercent;
	private Double discPercentExt;
	private Double discValue;
	private Double discValueExt;
	private Double totalPo;
	private Double totalTemp;
	
	private String discTypeCode;
	private String discTypeName;
	private String discTypeCode2;
	private String discTypeName2;
	private String notes;
	private String statusCode;
	private String statusName;
	private String itemName;
	private String itemCode;
	private String outletName;
	private String umName;
	private String completeItem;
	private String unitPriceStr;
	
	
	
	public Long getPoDtlId() {
		return poDtlId;
	}
	
	public void setPoDtlId(Long poDtlId) {
		this.poDtlId = poDtlId;
	}
	
	public Long getOutletId() {
		return outletId;
	}
	
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	
	public Long getPoId() {
		return poId;
	}
	
	public void setPoId(Long poId) {
		this.poId = poId;
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Long getLineNo() {
		return lineNo;
	}
	
	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}
	
	public Long getOrderUm() {
		return orderUm;
	}
	
	public void setOrderUm(Long orderUm) {
		this.orderUm = orderUm;
	}
	
	public Long getUmId() {
		return umId;
	}
	
	public void setUmId(Long umId) {
		this.umId = umId;
	}
	

	
	public Double getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}

	public Double getReceiveQty() {
		return receiveQty;
	}
	
	public void setReceiveQty(Double receiveQty) {
		this.receiveQty = receiveQty;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Double getDiscPercent() {
		return discPercent;
	}
	
	public void setDiscPercent(Double discPercent) {
		this.discPercent = discPercent;
	}
	
	public Double getDiscValue() {
		return discValue;
	}
	
	public void setDiscValue(Double discValue) {
		this.discValue = discValue;
	}
	
	public String getDiscTypeCode() {
		return discTypeCode;
	}
	
	public void setDiscTypeCode(String discTypeCode) {
		this.discTypeCode = discTypeCode;
	}
	
	public String getDiscTypeName() {
		return discTypeName;
	}
	
	public void setDiscTypeName(String discTypeName) {
		this.discTypeName = discTypeName;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getOutletName() {
		return outletName;
	}
	
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
		
	public String getUmName() {
		return umName;
	}
	
	public void setUmName(String umName) {
		this.umName = umName;
	}

	public Double getTotalPo() {
		return totalPo;
	}

	public void setTotalPo(Double totalPo) {
		this.totalPo = totalPo;
	}

	public String getCompleteItem() {
		return completeItem;
	}

	public void setCompleteItem(String completeItem) {
		this.completeItem = completeItem;
	}

	public Long getReorderQtyItem() {
		return reorderQtyItem;
	}

	public void setReorderQtyItem(Long reorderQtyItem) {
		this.reorderQtyItem = reorderQtyItem;
	}

	public String getDiscTypeCode2() {
		return discTypeCode2;
	}

	public void setDiscTypeCode2(String discTypeCode2) {
		this.discTypeCode2 = discTypeCode2;
	}

	public String getDiscTypeName2() {
		return discTypeName2;
	}

	public void setDiscTypeName2(String discTypeName2) {
		this.discTypeName2 = discTypeName2;
	}

	public Double getDiscPercentExt() {
		return discPercentExt;
	}

	public void setDiscPercentExt(Double discPercentExt) {
		this.discPercentExt = discPercentExt;
	}

	public Double getDiscValueExt() {
		return discValueExt;
	}

	public void setDiscValueExt(Double discValueExt) {
		this.discValueExt = discValueExt;
	}

	public Double getTotalTemp() {
		return totalTemp;
	}

	public void setTotalTemp(Double totalTemp) {
		this.totalTemp = totalTemp;
	}

	public String getUnitPriceStr() {
		return unitPriceStr;
	}

	public void setUnitPriceStr(String unitPriceStr) {
		this.unitPriceStr = unitPriceStr;
	}
	
	
	
	
}