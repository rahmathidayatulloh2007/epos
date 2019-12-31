package com.wo.epos.module.inv.item.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;

public class ItemVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 7319349178480398920L;

	private Long itemId;
	
	private Long companyId;
	private String companyName;
	
	private String itemCode;
	private String itemName;
	private String barcode;
	private String itemDesc;
	
	private Long categoryId;
	private String categoryName;
	
	private Long umId;
	private String umName;
	
	private Long um2Id;
	private String um2Name;
	private Long unitPerUm2;
	private Double unitCost;
	private Double unitPrice;
	
	private Long stockQty;
	private Long reorderQty;
	private Long averagePrice;
	private byte[] imageFile;
	private String imageFilename;
	private String compositeFlag;
	
	private String CustomerType;
	private ItemDiscount itemDiscount;
	private Long itemDiscountId;
	
	private List<SupplierItemVO> listSupplierItem = new ArrayList<SupplierItemVO>();
	private List<ItemDiscountVO> listItemDiscount = new ArrayList<ItemDiscountVO>();
	
	private boolean checked;
	
	public ItemVO()
	{
		
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getUmId() {
		return umId;
	}

	public void setUmId(Long umId) {
		this.umId = umId;
	}

	public String getUmName() {
		return umName;
	}

	public void setUmName(String umName) {
		this.umName = umName;
	}

	public Long getStockQty() {
		return stockQty;
	}

	public void setStockQty(Long stockQty) {
		this.stockQty = stockQty;
	}

	public Long getReorderQty() {
		return reorderQty;
	}

	public void setReorderQty(Long reorderQty) {
		this.reorderQty = reorderQty;
	}

	public Long getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Long averagePrice) {
		this.averagePrice = averagePrice;
	}


	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}

	public List<SupplierItemVO> getListSupplierItem() {
		return listSupplierItem;
	}

	public void setListSupplierItem(List<SupplierItemVO> listSupplierItem) {
		this.listSupplierItem = listSupplierItem;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCompositeFlag() {
		return compositeFlag;
	}

	public void setCompositeFlag(String compositeFlag) {
		this.compositeFlag = compositeFlag;
	}



	public Long getUm2Id() {
		return um2Id;
	}

	public void setUm2Id(Long um2Id) {
		this.um2Id = um2Id;
	}

	public String getUm2Name() {
		return um2Name;
	}

	public void setUm2Name(String um2Name) {
		this.um2Name = um2Name;
	}

	public Long getUnitPerUm2() {
		return unitPerUm2;
	}

	public void setUnitPerUm2(Long unitPerUm2) {
		this.unitPerUm2 = unitPerUm2;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCustomerType() {
		return CustomerType;
	}

	public void setCustomerType(String customerType) {
		CustomerType = customerType;
	}

	public List<ItemDiscountVO> getListItemDiscount() {
		return listItemDiscount;
	}

	public void setListItemDiscount(List<ItemDiscountVO> listItemDiscount) {
		this.listItemDiscount = listItemDiscount;
	}

	public ItemDiscount getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(ItemDiscount itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public Long getItemDiscountId() {
		return itemDiscountId;
	}

	public void setItemDiscountId(Long itemDiscountId) {
		this.itemDiscountId = itemDiscountId;
	}

	
}
