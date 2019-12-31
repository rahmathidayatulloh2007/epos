package com.wo.epos.module.sales.draftSalesOrder.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;

public class DraftSalesOrderVO extends BaseEntity implements Serializable {
	

	private static final long serialVersionUID = 5615814065371129389L;
	private Long soId;
    private Long outletId;
    private Long equipmentId;
    private Long deliveryCost;
    private Long itemAmount;
    private Long companyId;
    private Long registerId;
    private Long discountId;
    private Long invoiceId;
    private Long customerId;
        
    private String outletName;
    private String equipmentName;
    private String customerName;
    private String soNo;
    private String notes;
    private String soTypeCode;
    private String soTypeName;
    private String deliveryStatusCode;
    private String taxTypeCode;
    private String taxTypeName;
    private String productResume;
    private String statusCode;
    private String statusName;
    private String orderType;
    private String registerName;
    private String depositNotes;
    private String discountName;
    private String titelDiscount;
    private String statusInvoiceCode;
    private String ppn;
    private Date soDate;
    
    private Double taxValue;
    private Double subTotal;
    private Double subDiskon;
    private Double sumTotal;
    private Double orderPrice;
    private Double paymentAmount;
    private Double depositAmount;
    private Double changeAmount;
    private Double subTotalBill;
    private Double sumTotalPayment;
    private Double subTaxValue;
    
    
    private List<DraftSalesOrderDtlVO> salesOrderDtlVoList = new ArrayList<DraftSalesOrderDtlVO>();
    private List<DraftSalesOrderDtlVO> tagihanList = new ArrayList<DraftSalesOrderDtlVO>();
    
    private boolean checkTagihan;
    
    public DraftSalesOrderVO(){
    	
    }


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

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
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

	public String getSoTypeName() {
		return soTypeName;
	}

	public void setSoTypeName(String soTypeName) {
		this.soTypeName = soTypeName;
	}

	public String getDeliveryStatusCode() {
		return deliveryStatusCode;
	}

	public void setDeliveryStatusCode(String deliveryStatusCode) {
		this.deliveryStatusCode = deliveryStatusCode;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public String getTaxTypeName() {
		return taxTypeName;
	}

	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getSubDiskon() {
		return subDiskon;
	}

	public void setSubDiskon(Double subDiskon) {
		this.subDiskon = subDiskon;
	}

	public Double getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(Double sumTotal) {
		this.sumTotal = sumTotal;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getRegisterId() {
		return registerId;
	}

	public void setRegisterId(Long registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	
	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public String getDepositNotes() {
		return depositNotes;
	}

	public void setDepositNotes(String depositNotes) {
		this.depositNotes = depositNotes;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public String getTitelDiscount() {
		return titelDiscount;
	}

	public void setTitelDiscount(String titelDiscount) {
		this.titelDiscount = titelDiscount;
	}
	

	public boolean isCheckTagihan() {
		return checkTagihan;
	}

	public void setCheckTagihan(boolean checkTagihan) {
		this.checkTagihan = checkTagihan;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getSubTotalBill() {
		return subTotalBill;
	}

	public void setSubTotalBill(Double subTotalBill) {
		this.subTotalBill = subTotalBill;
	}

	public Double getSumTotalPayment() {
		return sumTotalPayment;
	}

	public void setSumTotalPayment(Double sumTotalPayment) {
		this.sumTotalPayment = sumTotalPayment;
	}

	public String getStatusInvoiceCode() {
		return statusInvoiceCode;
	}

	public void setStatusInvoiceCode(String statusInvoiceCode) {
		this.statusInvoiceCode = statusInvoiceCode;
	}

	public Double getSubTaxValue() {
		return subTaxValue;
	}

	public void setSubTaxValue(Double subTaxValue) {
		this.subTaxValue = subTaxValue;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public List<DraftSalesOrderDtlVO> getSalesOrderDtlVoList() {
		return salesOrderDtlVoList;
	}


	public void setSalesOrderDtlVoList(List<DraftSalesOrderDtlVO> salesOrderDtlVoList) {
		this.salesOrderDtlVoList = salesOrderDtlVoList;
	}


	public List<DraftSalesOrderDtlVO> getTagihanList() {
		return tagihanList;
	}


	public void setTagihanList(List<DraftSalesOrderDtlVO> tagihanList) {
		this.tagihanList = tagihanList;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getPpn() {
		return ppn;
	}


	public void setPpn(String ppn) {
		this.ppn = ppn;
	}
	
	
	    
}