package com.wo.epos.module.sales.salesOrder.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class SalesOrderDtl  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 828093377494892404L;
	
	private Long soDtlId;
	private Long productId;
    private Long orderUmId;
	
    private SalesOrder salesOrder;
    private Product product;
    private Um orderUm;
    private ParameterDtl discType;
    private ParameterDtl deliveryStatus;
    private ParameterDtl preparationStatus;

    private String discTypeCode;
    private String deliveryStatusCode;
    private String preparationStatusCode;
    private String notes;

    private Integer lineNo;
				
    private Double orderQty;
    private Double unitPrice;
    private Double discPercent;
    private Double discValue;
   
    private Integer discount1;
    private Integer discount2;
    private Integer discount3;
    private Double totalPriceDiscount;
    
    
	public Long getSoDtlId() {
		return soDtlId;
	}
	
	public void setSoDtlId(Long soDtlId) {
		this.soDtlId = soDtlId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}
	
	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public ParameterDtl getDiscType() {
		return discType;
	}
	
	public void setDiscType(ParameterDtl discType) {
		this.discType = discType;
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
	
	public Integer getLineNo() {
		return lineNo;
	}
	
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	
	public Double getOrderQty() {
		return orderQty;
	}
	
	public void setOrderQty(Double orderQty) {
		this.orderQty = orderQty;
	}
	
	public Long getOrderUmId() {
		return orderUmId;
	}

	public void setOrderUmId(Long orderUmId) {
		this.orderUmId = orderUmId;
	}

	public void setOrderUm(Um orderUm) {
		this.orderUm = orderUm;
	}

	public Um getOrderUm() {
		return orderUm;
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

	public ParameterDtl getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(ParameterDtl deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public ParameterDtl getPreparationStatus() {
		return preparationStatus;
	}

	public void setPreparationStatus(ParameterDtl preparationStatus) {
		this.preparationStatus = preparationStatus;
	}

	public String getDeliveryStatusCode() {
		return deliveryStatusCode;
	}

	public void setDeliveryStatusCode(String deliveryStatusCode) {
		this.deliveryStatusCode = deliveryStatusCode;
	}

	public String getPreparationStatusCode() {
		return preparationStatusCode;
	}

	public void setPreparationStatusCode(String preparationStatusCode) {
		this.preparationStatusCode = preparationStatusCode;
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

	public Double getTotalPriceDiscount() {
		return totalPriceDiscount;
	}

	public void setTotalPriceDiscount(Double totalPriceDiscount) {
		this.totalPriceDiscount = totalPriceDiscount;
	}

	
}
