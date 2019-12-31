package com.wo.epos.module.inv.DO.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.um.model.Um;

public class DODtl extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -48164502059437823L;
	
	private Long doDtlId;
	private DO DO;
	//private Outlet outlet;
	
	private Item item;
	private Long deliveryQty;
	private Um deliveryUm;
	
	
	
	public Long getDoDtlId() {
		return doDtlId;
	}
	public void setDoDtlId(Long doDtlId) {
		this.doDtlId = doDtlId;
	}
	
	/*public Outlet getOutlet() {
		return outlet;
	}
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}*/
	
	public DO getDO() {
		return DO;
	}
	public void setDO(DO dO) {
		DO = dO;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Long getDeliveryQty() {
		return deliveryQty;
	}
	public void setDeliveryQty(Long deliveryQty) {
		this.deliveryQty = deliveryQty;
	}
	public Um getDeliveryUm() {
		return deliveryUm;
	}
	public void setDeliveryUm(Um deliveryUm) {
		this.deliveryUm = deliveryUm;
	}
	
	
	
	
	
	
	
	
	
}