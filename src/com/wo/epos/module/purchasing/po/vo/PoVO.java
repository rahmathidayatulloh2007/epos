package com.wo.epos.module.purchasing.po.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;


public class PoVO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4789561274866909384L;
	
	private Long poId;
	private Long outletId;
	private Long supplierId;
	private Long companyId;

	private String poNo;
	private String notes;
	private String taxTypeCode;
	private String statusCode;
	private String outletName;
	private String supplierName;
	private String supplierCode;
	private String taxTypeName;
	private String statusName;
	private String itemResume;
	private String companyName;
	private String closeReason;
	
	private Date poDate;
	private Date startDate;
	private Date endDate;	
	
	private Double taxValue;	
	private BigDecimal subTotal;
	private BigDecimal ppn;
	private BigDecimal discount;
	private BigDecimal totalTemp;
	private BigDecimal total;
	
	
	private List<PoDtlVO> listPoDtlVO = new ArrayList<PoDtlVO>();
	
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
	
	public Double getTaxValue() {
		return taxValue;
	}
	
	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}
	
	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTaxTypeName() {
		return taxTypeName;
	}

	public void setTaxTypeName(String taxTypeName) {
		this.taxTypeName = taxTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public List<PoDtlVO> getListPoDtlVO() {
		return listPoDtlVO;
	}

	public void setListPoDtlVO(List<PoDtlVO> listPoDtlVO) {
		this.listPoDtlVO = listPoDtlVO;
	}
	
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getPpn() {
		return ppn;
	}

	public void setPpn(BigDecimal ppn) {
		this.ppn = ppn;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getItemResume() {
		return itemResume;
	}

	public void setItemResume(String itemResume) {
		this.itemResume = itemResume;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
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

	public BigDecimal getTotalTemp() {
		return totalTemp;
	}

	public void setTotalTemp(BigDecimal totalTemp) {
		this.totalTemp = totalTemp;
	}

	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}
	

}