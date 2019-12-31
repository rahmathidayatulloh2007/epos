package com.wo.epos.module.purchasing.po.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class Po extends BaseEntity implements Serializable {

	
	private static final long serialVersionUID = -8864497095585824483L;
		
	private Long poId;
	private Long outletId;
	private Long supplierId;
	private Long companyId;

	private String poNo;
	private String notes;
	private String taxTypeCode;
	private String statusCode;
	private String itemResume;
	private String closeReason;
	
	private Date poDate;
		
	private Double taxValue;
	
	private Outlet outlet;
	private Supplier supplier;
	private ParameterDtl taxType;
	private ParameterDtl status;
	private Company company;
	
	private List<PoDtl> listPoDetail = new ArrayList<PoDtl>();		
	
	
	public Long getPoId() {
		return poId;
	}

	public void setPoId(Long poId) {
		this.poId = poId;
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

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public ParameterDtl getTaxType() {
		return taxType;
	}

	public void setTaxType(ParameterDtl taxType) {
		this.taxType = taxType;
	}

	public ParameterDtl getStatus() {
		return status;
	}

	public void setStatus(ParameterDtl status) {
		this.status = status;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTaxTypeCode() {
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public List<PoDtl> getListPoDetail() {
		return listPoDetail;
	}

	public void setListPoDetail(List<PoDtl> listPoDetail) {
		this.listPoDetail = listPoDetail;
	}

	public Double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}

	public String getItemResume() {
		return itemResume;
	}

	public void setItemResume(String itemResume) {
		this.itemResume = itemResume;
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

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}
	
	
}
















