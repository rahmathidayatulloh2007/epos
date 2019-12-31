package com.wo.epos.module.inv.DO.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.DO.model.DODtl;
import com.wo.epos.module.uam.outlet.model.Outlet;

public class DOVO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1064498451055473800L;
	
	private Long doId;
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
	private Long companyId;
	private String outletName;
	
	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	private List<DODtl> listDODtl;

	

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

	public String getItemResume() {
		return itemResume;
	}

	public void setItemResume(String itemResume) {
		this.itemResume = itemResume;
	}

	

	public List<DODtl> getListDODtl() {
		return listDODtl;
	}

	public void setListDODtl(List<DODtl> listDODtl) {
		this.listDODtl = listDODtl;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	
	
	
	
	
}