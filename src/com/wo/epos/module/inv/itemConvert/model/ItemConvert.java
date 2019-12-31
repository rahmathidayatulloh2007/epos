package com.wo.epos.module.inv.itemConvert.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.uam.company.model.Company;

public class ItemConvert extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1719927836243858434L;

	private Long convertId;
	private Company company;
	// private Outlet outlet;
	private String convertNo;
	private Date convertDate;
	private Item item;
	private Double itemQty;
	private String convertDesc;

	private List<ItemConvertDtl> listItemConvertDtl = new ArrayList<ItemConvertDtl>();

	public Long getConvertId() {
		return convertId;
	}

	public void setConvertId(Long convertId) {
		this.convertId = convertId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Double getItemQty() {
		return itemQty;
	}

	public void setItemQty(Double itemQty) {
		this.itemQty = itemQty;
	}

	public String getConvertDesc() {
		return convertDesc;
	}

	public void setConvertDesc(String convertDesc) {
		this.convertDesc = convertDesc;
	}

	public List<ItemConvertDtl> getListItemConvertDtl() {
		return listItemConvertDtl;
	}

	public void setListItemConvertDtl(List<ItemConvertDtl> listItemConvertDtl) {
		this.listItemConvertDtl = listItemConvertDtl;
	}

	public String getConvertNo() {
		return convertNo;
	}

	public void setConvertNo(String convertNo) {
		this.convertNo = convertNo;
	}

	public Date getConvertDate() {
		return convertDate;
	}

	public void setConvertDate(Date convertDate) {
		this.convertDate = convertDate;
	}

	// public Outlet getOutlet() {
	// return outlet;
	// }
	//
	// public void setOutlet(Outlet outlet) {
	// this.outlet = outlet;
	// }

}