package com.wo.epos.module.purchasing.supplier.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.purchasing.supplier.model.Supplier;

public class SupplierItem  extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -1443609554552706381L;
	
	private Long supplierItemId;
	private Supplier supplier;
	private Item item;
	
	public Long getSupplierItemId() {
		return supplierItemId;
	}
	public void setSupplierItemId(Long supplierItemId) {
		this.supplierItemId = supplierItemId;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	

}
