package com.wo.epos.module.inv.transferItem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;

public class TransferItem extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -48164502059437823L;
	
	private Long doId;
	private Company company;
	private Outlet outlet;
	private String soId;
	private String doType;
	private String doNo;
	private Date doDate;
	private Outlet transferFrom;
	private Outlet transferTo;
	private String notes;
	private String status;
	private String itemResume;
	
	private List<TransferItemDtl> listTIDtl;
	
	
	public Long getDoId() {
		return doId;
	}
	public void setDoId(Long doId) {
		this.doId = doId;
	}
	public Outlet getOutlet() {
		return outlet;
	}
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}
	
	public String getDoNo() {
		return doNo;
	}
	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}
	public Date getDoDate() {
		return doDate;
	}
	public void setDoDate(Date doDate) {
		this.doDate = doDate;
	}
	
	public Outlet getTransferFrom() {
		return transferFrom;
	}
	public void setTransferFrom(Outlet transferFrom) {
		this.transferFrom = transferFrom;
	}
	public Outlet getTransferTo() {
		return transferTo;
	}
	public void setTransferTo(Outlet transferTo) {
		this.transferTo = transferTo;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TransferItemDtl> getListTIDtl() {
		return listTIDtl;
	}
	public void setListTIDtl(List<TransferItemDtl> listTIDtl) {
		this.listTIDtl = listTIDtl;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getDoType() {
		return doType;
	}
	public void setDoType(String doType) {
		this.doType = doType;
	}
	public String getItemResume() {
		return itemResume;
	}
	public void setItemResume(String itemResume) {
		this.itemResume = itemResume;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
		
}