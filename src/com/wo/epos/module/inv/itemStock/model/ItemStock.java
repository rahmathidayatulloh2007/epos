package com.wo.epos.module.inv.itemStock.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;

public class ItemStock extends BaseEntity implements Serializable {	
      
	private static final long serialVersionUID = -3510737878336808423L;
	
	private Long itemStockId;
    private Long outletId;
    private Long itemId;
    private Long companyId;
    
    private Outlet outlet;
    private Item item;
    private Company company;
    
    private Double stockQty;
    private Double outgoingQty;
    private Double incomingQty;
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
			
}
