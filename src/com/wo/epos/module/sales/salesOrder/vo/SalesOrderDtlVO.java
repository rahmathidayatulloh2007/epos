package com.wo.epos.module.sales.salesOrder.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;

public class SalesOrderDtlVO  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -4658632292811170380L;
	
	private Long soDtlId;
	private Long productId;	
    private Long salesOrderId;
    private Long orderUm;
    private Long equipmentId;
    
    private String productName;
    private String ingredientFlag;
    private String salesOrderNo; 
    private String discTypeCode;
    private String discTypeName;
    private String deliveryStatusCode;
    private String preparationSatusCode;
    private String notes;
    private String orderUmName;
    private String categoryCode;
    private String equipmentName;

    private Integer lineNo;
    
    private Long itemAmount;
				
    private Double orderQty;
    private Double unitPrice;
    private Double discPercent;
    private Double discValue;
    private Double totalPrice;
    private Double sumTotal;
    private Double subTotal;
    private Integer discount1;
    private Integer discount2;
    private Integer discount3;
    private Double totalPriceDiscount;
    private String customerType;
    private List<ItemDiscountVO> listItemDiscount = new ArrayList<ItemDiscountVO>();
    private Date salesOrderDate;
    
    private boolean checkTagihanDtl;
    
    
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
	
	public Long getSalesOrderId() {
		return salesOrderId;
	}
	
	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getSalesOrderNo() {
		return salesOrderNo;
	}
	
	public void setSalesOrderNo(String salesOrderNo) {
		this.salesOrderNo = salesOrderNo;
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
	
	public String getDeliveryStatusCode() {
		return deliveryStatusCode;
	}

	public void setDeliveryStatusCode(String deliveryStatusCode) {
		this.deliveryStatusCode = deliveryStatusCode;
	}

	public String getPreparationSatusCode() {
		return preparationSatusCode;
	}

	public void setPreparationSatusCode(String preparationSatusCode) {
		this.preparationSatusCode = preparationSatusCode;
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
	
	public Long getOrderUm() {
		return orderUm;
	}
	
	public void setOrderUm(Long orderUm) {
		this.orderUm = orderUm;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getOrderUmName() {
		return orderUmName;
	}

	public void setOrderUmName(String orderUmName) {
		this.orderUmName = orderUmName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public boolean isCheckTagihanDtl() {
		return checkTagihanDtl;
	}

	public void setCheckTagihanDtl(boolean checkTagihanDtl) {
		this.checkTagihanDtl = checkTagihanDtl;
	}

	public String getIngredientFlag() {
		return ingredientFlag;
	}

	public void setIngredientFlag(String ingredientFlag) {
		this.ingredientFlag = ingredientFlag;
	}

	public Date getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
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

	public List<ItemDiscountVO> getListItemDiscount() {
		return listItemDiscount;
	}

	public void setListItemDiscount(List<ItemDiscountVO> listItemDiscount) {
		this.listItemDiscount = listItemDiscount;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Double getTotalPriceDiscount() {
		return totalPriceDiscount;
	}

	public void setTotalPriceDiscount(Double totalPriceDiscount) {
		this.totalPriceDiscount = totalPriceDiscount;
	}

	public Long getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Long itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Double getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(Double sumTotal) {
		this.sumTotal = sumTotal;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	
	
		
}




