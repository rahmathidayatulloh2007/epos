package com.wo.epos.module.inv.stockOpname.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;


public class StockOpnameDtl extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = 5127826595265689972L;

	private Long opnameDtlId;

	private StockOpname stockOpname;
	private Item item;
	private Double stockQty;
	private Double opnameQty;
	
	public Long getOpnameDtlId() {
		return opnameDtlId;
	}
	public void setOpnameDtlId(Long opnameDtlId) {
		this.opnameDtlId = opnameDtlId;
	}
	public StockOpname getStockOpname() {
		return stockOpname;
	}
	public void setStockOpname(StockOpname stockOpname) {
		this.stockOpname = stockOpname;
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
	public Double getOpnameQty() {
		return opnameQty;
	}
	public void setOpnameQty(Double opnameQty) {
		this.opnameQty = opnameQty;
	}
	
}
