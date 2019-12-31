package com.wo.epos.module.inv.rn.vo;

import java.io.Serializable;
import java.util.Date;

import com.wo.epos.common.model.BaseEntity;

public class RnVO extends BaseEntity implements Serializable {	
   
	private static final long serialVersionUID = 8951387702466617195L;

	private Long rnId;	
	private Long outletId;
	private Long supplierId;
	private Long poId;
	private Long outletOriginId;
	private Long doId;
	private Long companyId;
	
	private String outletName;
	private String rnTypeName;
	private String supplierCode;
	private String supplierName;
	private String poNumber;
	private String outletOriginCode;
	private String outletOriginName;
	private String doNo;
	private String DOName;
	private String rnTypeCode;
	private String rnNo;
	private String supplierDocNo;
	private String notes;
	private String itemResume;
	private String companyName;
	
	private Date rnDate;
	private Date supplierDocDate;
	private Date startDate;
	private Date endDate;
	
	
	
	public Long getRnId() {
		return rnId;
	}
	
	public void setRnId(Long rnId) {
		this.rnId = rnId;
	}
	
	public Long getOutletId() {
		return outletId;
	}
	
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	
	public Long getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	
	public Long getPoId() {
		return poId;
	}
	public void 
	setPoId(Long poId) {
		this.poId = poId;
	}
	
	public Long getOutletOriginId() {
		return outletOriginId;
	}
	
	public void setOutletOriginId(Long outletOriginId) {
		this.outletOriginId = outletOriginId;
	}
	
	public Long getDoId() {
		return doId;
	}
	
	public void setDoId(Long doId) {
		this.doId = doId;
	}
	
	public String getOutletName() {
		return outletName;
	}
	
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	
	public String getRnTypeName() {
		return rnTypeName;
	}
	
	public void setRnTypeName(String rnTypeName) {
		this.rnTypeName = rnTypeName;
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getPoNumber() {
		return poNumber;
	}
	
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	public String getOutletOriginName() {
		return outletOriginName;
	}
	
	public void setOutletOriginName(String outletOriginName) {
		this.outletOriginName = outletOriginName;
	}
	
	
	
	public String getDOName() {
		return DOName;
	}

	public void setDOName(String dOName) {
		DOName = dOName;
	}

	public String getRnTypeCode() {
		return rnTypeCode;
	}
	
	public void setRnTypeCode(String rnTypeCode) {
		this.rnTypeCode = rnTypeCode;
	}
	
	public String getRnNo() {
		return rnNo;
	}
	
	public void setRnNo(String rnNo) {
		this.rnNo = rnNo;
	}
	
	public String getSupplierDocNo() {
		return supplierDocNo;
	}
	
	public void setSupplierDocNo(String supplierDocNo) {
		this.supplierDocNo = supplierDocNo;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getItemResume() {
		return itemResume;
	}
	
	public void setItemResume(String itemResume) {
		this.itemResume = itemResume;
	}
	
	public Date getRnDate() {
		return rnDate;
	}
	
	public void setRnDate(Date rnDate) {
		this.rnDate = rnDate;
	}
	
	public Date getSupplierDocDate() {
		return supplierDocDate;
	}
	
	public void setSupplierDocDate(Date supplierDocDate) {
		this.supplierDocDate = supplierDocDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getOutletOriginCode() {
		return outletOriginCode;
	}

	public void setOutletOriginCode(String outletOriginCode) {
		this.outletOriginCode = outletOriginCode;
	}

	public String getDoNo() {
		return doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}
			
	
	
	
}
