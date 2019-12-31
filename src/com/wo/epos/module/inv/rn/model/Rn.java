package com.wo.epos.module.inv.rn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.DO.model.DO;
import com.wo.epos.module.purchasing.po.model.Po;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public class Rn extends BaseEntity implements Serializable {	
   
	private static final long serialVersionUID = 5047098509121325765L;

	private Long rnId;	
	private Long companyId;
	private Long outletId;
	private Long supplierId;
	private Long poId;
	private Long outletOriginId;
	private Long doId;
	
	private Company company;
	private Outlet outlet;
	private ParameterDtl rnType;
	private Supplier supplier;
	private Po po;
	private Outlet outletOrigin;
	private DO DO;

	private String rnTypeCode;
	private String rnNo;
	private String supplierDocNo;
	private String notes;
	private String itemResume;
	
	private Date rnDate;
	private Date supplierDocDate;
			
	private List<RnDtl> listRnDetail = new ArrayList<RnDtl>();

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

	public void setPoId(Long poId) {
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

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public ParameterDtl getRnType() {
		return rnType;
	}

	public void setRnType(ParameterDtl rnType) {
		this.rnType = rnType;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Po getPo() {
		return po;
	}

	public void setPo(Po po) {
		this.po = po;
	}

	public Outlet getOutletOrigin() {
		return outletOrigin;
	}

	public void setOutletOrigin(Outlet outletOrigin) {
		this.outletOrigin = outletOrigin;
	}

	

	

	public DO getDO() {
		return DO;
	}

	public void setDO(DO dO) {
		DO = dO;
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

	public List<RnDtl> getListRnDetail() {
		return listRnDetail;
	}

	public void setListRnDetail(List<RnDtl> listRnDetail) {
		this.listRnDetail = listRnDetail;
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
