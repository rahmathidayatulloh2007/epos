package com.wo.epos.module.inv.item.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.uam.company.model.Company;

public class Product extends BaseEntity implements Serializable{

	private static final long serialVersionUID = -7342267048656890690L;	
	
	private Long productId; 
	private Long companyId;
	private Long categoryId;
	
	private Double unitCost;
	private Double unitPrice;
	
	private Um um2;
	private Long unitPerUm2;
	private Long bufferStock;
	
	private Company company;
	private Category category;
	private Um um;	
	
	private String productCode; 
	private String productName;
	private String productDesc;
	private String itemComposition;
	private String imageFilename;
	private String ingredientFlag;	
	private String barcode;
	
	private byte[] imageFile;
	
	private Date launchingDt;
	
	private List<ProductBom> productBomList = new ArrayList<ProductBom>();
	private List<ItemDiscount> listItemDiscount = new ArrayList<ItemDiscount>();
	
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductDesc() {
		return productDesc;
	}
	
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
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
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public Date getLaunchingDt() {
		return launchingDt;
	}
	
	public void setLaunchingDt(Date launchingDt) {
		this.launchingDt = launchingDt;
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

	public Um getUm() {
		return um;
	}

	public void setUm(Um um) {
		this.um = um;
	}

	public String getItemComposition() {
		return itemComposition;
	}

	public void setItemComposition(String itemComposition) {
		this.itemComposition = itemComposition;
	}

	public String getIngredientFlag() {
		return ingredientFlag;
	}

	public void setIngredientFlag(String ingredientFlag) {
		this.ingredientFlag = ingredientFlag;
	}

	public List<ProductBom> getProductBomList() {
		return productBomList;
	}

	public void setProductBomList(List<ProductBom> productBomList) {
		this.productBomList = productBomList;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public List<ItemDiscount> getListItemDiscount() {
		return listItemDiscount;
	}

	public void setListItemDiscount(List<ItemDiscount> listItemDiscount) {
		this.listItemDiscount = listItemDiscount;
	}

	public Long getBufferStock() {
		return bufferStock;
	}

	public void setBufferStock(Long bufferStock) {
		this.bufferStock = bufferStock;
	}
	

}
