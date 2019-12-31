package com.wo.epos.module.inv.item.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.uam.company.model.Company;

public class Item extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -1212482090848119977L;

	private Long itemId;
	
	private Company company;
	
	private String itemCode;
	private String itemName;
	
	private String itemDesc;
	private Category category;
	private Um um;
	
	private Um um2;
	private Long unitPerUm2;
	private Double unitCost;
	private Double unitPrice;
	
	private Long stockQty;
	private Long reorderQty;
	private Long averagePrice;
	private byte[] imageFile;
	private String imageFilename;
	private String barcode;
	private String compositeFlag;
	
	private Product product;
	private ProductBom productBom;
	
	
	private List<ItemDiscount> listItemDiscount = new ArrayList<ItemDiscount>();
	
	private List<SupplierItem> listSupplierItem = new ArrayList<SupplierItem>();
	private List<ItemBom> listItemBom = new ArrayList<ItemBom>();
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Um getUm() {
		return um;
	}
	public void setUm(Um um) {
		this.um = um;
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
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public ProductBom getProductBom() {
		return productBom;
	}
	public void setProductBom(ProductBom productBom) {
		this.productBom = productBom;
	}
	public List<SupplierItem> getListSupplierItem() {
		return listSupplierItem;
	}
	public void setListSupplierItem(List<SupplierItem> listSupplierItem) {
		this.listSupplierItem = listSupplierItem;
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
	public List<ItemBom> getListItemBom() {
		return listItemBom;
	}
	public void setListItemBom(List<ItemBom> listItemBom) {
		this.listItemBom = listItemBom;
	}
	public Um getUm2() {
		return um2;
	}
	public void setUm2(Um um2) {
		this.um2 = um2;
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
	public List<ItemDiscount> getListItemDiscount() {
		return listItemDiscount;
	}
	public void setListItemDiscount(List<ItemDiscount> listItemDiscount) {
		this.listItemDiscount = listItemDiscount;
	}

	
}
