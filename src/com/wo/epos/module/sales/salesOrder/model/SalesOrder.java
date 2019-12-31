package com.wo.epos.module.sales.salesOrder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;

import com.wo.epos.module.master.equipment.model.Equipment;
import com.wo.epos.module.sales.customer.model.CustomerSales;
import com.wo.epos.module.sales.discount.model.Discount;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class SalesOrder  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -1205908091375513810L;

	private Long soId;
    private Long outletId;
    private Long equipmentId;
    private Long deliveryCost;
    private Long itemAmount;
    private Long discountId;
    private Long customerId;
    
    private Outlet outlet;
    private Equipment equipment;
    private ParameterDtl soType;
    private ParameterDtl taxType;
    private ParameterDtl status;
    private ParameterDtl deliveryStatus;
    private Discount discount;
    private CustomerSales customer;
    
    private String soNo;
    private String notes;
    private String soTypeCode;
    private String deliveryStatusCode;
    private String taxTypeCode;
    private String productResume;
    private String statusCode;
    
    private Date soDate;
    
    private Double taxValue;

    
    List<SalesOrderDtl> salesOrderDtlList = new ArrayList<SalesOrderDtl>();


	public Long getSoId() {
		return soId;
	}

	public void setSoId(Long soId) {
		this.soId = soId;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public Long getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(Long deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public Long getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Long itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public ParameterDtl getSoType() {
		return soType;
	}

	public void setSoType(ParameterDtl soType) {
		this.soType = soType;
	}

	public ParameterDtl getTaxType() {
		return taxType;
	}

	public void setTaxType(ParameterDtl taxType) {
		this.taxType = taxType;
	}

	public ParameterDtl getStatus() {
		return status;
	}

	public void setStatus(ParameterDtl status) {
		this.status = status;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

    public String getSoTypeCode() {
		return soTypeCode;
	}

	public void setSoTypeCode(String soTypeCode) {
		this.soTypeCode = soTypeCode;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public String getProductResume() {
		return productResume;
	}

	public void setProductResume(String productResume) {
		this.productResume = productResume;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getSoDate() {
		return soDate;
	}

	public void setSoDate(Date soDate) {
		this.soDate = soDate;
	}

	public Double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}

	public List<SalesOrderDtl> getSalesOrderDtlList() {
		return salesOrderDtlList;
	}

	public void setSalesOrderDtlList(List<SalesOrderDtl> salesOrderDtlList) {
		this.salesOrderDtlList = salesOrderDtlList;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public ParameterDtl getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(ParameterDtl deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryStatusCode() {
		return deliveryStatusCode;
	}

	public void setDeliveryStatusCode(String deliveryStatusCode) {
		this.deliveryStatusCode = deliveryStatusCode;
	}


	public CustomerSales getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerSales customer) {
		this.customer = customer;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}	
	
	
	
}
