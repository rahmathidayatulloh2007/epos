package com.wo.epos.module.purchasing.po.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class PoDtl extends BaseEntity implements Serializable {

			
	private static final long serialVersionUID = -5980786711582491118L;
	
	private Long poDtlId;
	//private Long outletId;
	//private Long poId;
	private Long itemId;
	private Long lineNo;	
	private Long orderUm;	

	private Double orderQty;
	private Double unitPrice;		
	private Double discPercent;
	private Double discValue;
	private Double discPercentExt;
	private Double discValueExt;
	private Double receiveQty;
	
	private String discTypeCode;
	private String discTypeCodeExt;
	private String poNo;
	private String notes;
	private String statusCode;
	
 //   private Outlet outlet;
    private Po po;
    private Item item;
    private ParameterDtl discType;
    private ParameterDtl status;
    

    
	public Long getPoDtlId() {
		return poDtlId;
	}
	
	public void setPoDtlId(Long poDtlId) {
		this.poDtlId = poDtlId;
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
	
	public Double getOrderQty() {
		return orderQty;
	}
	
	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
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

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	
	public Po getPo() {
		return po;
	}

	public void setPo(Po po) {
		this.po = po;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ParameterDtl getDiscType() {
		return discType;
	}

	public void setDiscType(ParameterDtl discType) {
		this.discType = discType;
	}

	public ParameterDtl getStatus() {
		return status;
	}

	public void setStatus(ParameterDtl status) {
		this.status = status;
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

	public String getDiscTypeCodeExt() {
		return discTypeCodeExt;
	}

	public void setDiscTypeCodeExt(String discTypeCodeExt) {
		this.discTypeCodeExt = discTypeCodeExt;
	}

	    
    
	
}