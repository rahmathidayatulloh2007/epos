package com.wo.epos.module.inv.itemConvert.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;

public class ItemConvertVO extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1279313879944118617L;

	private Long convertId;
	private Long companyId;
	// private Long outletId;
	private String convertNo;
	private Date convertDate;
	private Long itemId;
	private Double itemQty;
	private String convertDesc;

	private String companyCode;
	private String companyName;
	// private String outletCode;
	// private String outletName;
	private String itemCode;
	private String itemName;

	private Date startDate;
	private Date endDate;

	private List<ItemConvertDtlVO> listItemConvertDtlVO = new ArrayList<ItemConvertDtlVO>();

	public Long getConvertId() {
		return convertId;
	}

	public void setConvertId(Long convertId) {
		this.convertId = convertId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<ItemConvertDtlVO> getListItemConvertDtlVO() {
		return listItemConvertDtlVO;
	}

	public void setListItemConvertDtlVO(
			List<ItemConvertDtlVO> listItemConvertDtlVO) {
		this.listItemConvertDtlVO = listItemConvertDtlVO;
	}

	// public Long getOutletId() {
	// return outletId;
	// }
	//
	// public void setOutletId(Long outletId) {
	// this.outletId = outletId;
	// }
	//
	// public String getOutletName() {
	// return outletName;
	// }
	//
	// public void setOutletName(String outletName) {
	// this.outletName = outletName;
	// }

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	// public String getOutletCode() {
	// return outletCode;
	// }
	//
	// public void setOutletCode(String outletCode) {
	// this.outletCode = outletCode;
	// }

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
}