package com.wo.epos.module.purchasing.supplier.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;

public class SupplierItemVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -2958131404093923202L;
	
	private Long supplierItemId;
	private Long supplierId;
	private String supplierName;
	private Long itemId;
	private String itemName;	
	private String flag;
	
	private Item item;
	
	private boolean checked;
	
	public SupplierItemVO()
	{
		
	}

	public Long getSupplierItemId() {
		return supplierItemId;
	}

	public void setSupplierItemId(Long supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
