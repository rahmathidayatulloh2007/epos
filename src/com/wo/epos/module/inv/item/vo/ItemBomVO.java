package com.wo.epos.module.inv.item.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class ItemBomVO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1548975647190137581L;

	private Long bomId;
	private Long itemCompositionId;
	private String itemCompositionCode;
	private String itemCompositionName;
	private Double itemQty;

	private boolean checked;

	public ItemBomVO() {

	}

	public Double getItemQty() {
		return itemQty;
	}

	public void setItemQty(Double itemQty) {
		this.itemQty = itemQty;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getBomId() {
		return bomId;
	}

	public void setBomId(Long bomId) {
		this.bomId = bomId;
	}

	public Long getItemCompositionId() {
		return itemCompositionId;
	}

	public void setItemCompositionId(Long itemCompositionId) {
		this.itemCompositionId = itemCompositionId;
	}

	public String getItemCompositionName() {
		return itemCompositionName;
	}

	public void setItemCompositionName(String itemCompositionName) {
		this.itemCompositionName = itemCompositionName;
	}

	public String getItemCompositionCode() {
		return itemCompositionCode;
	}

	public void setItemCompositionCode(String itemCompositionCode) {
		this.itemCompositionCode = itemCompositionCode;
	}
}
