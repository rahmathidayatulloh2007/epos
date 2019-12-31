package com.wo.epos.module.inv.itemStock.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;

public class ItemStockVO extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -1284665398279273253L;
	
	private Long itemStockId;
    private Long outletId;
    private Long itemId;
    private Long companyId;
    
    private Outlet outlet;
    private Item item;
    private Company company;
    
    private String outletName;
    private String itemName;
    
    private Double stockQty;
    private Double outgoingQty;
    private Double incomingQty;
    private String stockQtyString;
    private String outgoingQtyString;
    private String incomingQtyString;
    private Double reorderQty;
    private Double averagePrice;
    
    
    
	public Long getItemStockId() {
		return itemStockId;
	}
	public void setItemStockId(Long itemStockId) {
		this.itemStockId = itemStockId;
	}
	
	public Long getOutletId() {
		return outletId;
	}
	
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	
	public Long getItemId() {
		return itemId;
	}
	
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public String getOutletName() {
		return outletName;
	}
	
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public Double getStockQty() {
		return stockQty;
	}
	
	public void setStockQty(Double stockQty) {
		this.stockQty = stockQty;
	}
	
	public Double getOutgoingQty() {
		return outgoingQty;
	}
	
	public void setOutgoingQty(Double outgoingQty) {
		this.outgoingQty = outgoingQty;
	}
	
	public Double getIncomingQty() {
		return incomingQty;
	}
	
	public void setIncomingQty(Double incomingQty) {
		this.incomingQty = incomingQty;
	}
	
	public Double getReorderQty() {
		return reorderQty;
	}
	
	public void setReorderQty(Double reorderQty) {
		this.reorderQty = reorderQty;
	}
	
	public Double getAveragePrice() {
		return averagePrice;
	}
	
	public void setAveragePrice(Double averagePrice) {
		this.averagePrice = averagePrice;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Outlet getOutlet() {
		return outlet;
	}
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getStockQtyString() {
		return stockQtyString;
	}
	public void setStockQtyString(String stockQtyString) {
		this.stockQtyString = stockQtyString;
	}
	public String getOutgoingQtyString() {
		return outgoingQtyString;
	}
	public void setOutgoingQtyString(String outgoingQtyString) {
		this.outgoingQtyString = outgoingQtyString;
	}
	public String getIncomingQtyString() {
		return incomingQtyString;
	}
	public void setIncomingQtyString(String incomingQtyString) {
		this.incomingQtyString = incomingQtyString;
	}
		
    
    
    
	
	
	
}
