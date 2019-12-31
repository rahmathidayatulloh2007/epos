package com.wo.epos.module.inv.item.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;

public class ProductVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 232064892086000114L;
	
	private Long productId; 
	private Long companyId; 
	private Long categoryId;
	private Long umId;
	
	private Double unitCost;
	private Double unitPrice;
	
	private Long um2Id;
	private String um2Name;
	private Long unitPerUm2;
	private Long bufferStock;
	
	private String productCode; 
	private String productName;
	private String productDesc;
	private String companyCode;
	private String companyName;
	private String categoryCode;
	private String categoryName;
	private String itemComposition;
	private String imageFilename;
	private String umName;
	private String ingredientFlag;
	private String barcode;
	
	private byte[] imageFile;
	
	private Date launchingDt;
	
	private Boolean sellProduct;
	
	/*private StreamedContent imageDisplay;*/
	private List<ItemDiscountVO> listItemDiscount = new ArrayList<ItemDiscountVO>();
	
	 private String streamUploadId;
    
	
	
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
	
	public Boolean getSellProduct() {
		return sellProduct;
	}
	
	public void setSellProduct(Boolean sellProduct) {
		this.sellProduct = sellProduct;
	}

	public Long getUmId() {
		return umId;
	}

	public void setUmId(Long umId) {
		this.umId = umId;
	}

	public String getItemComposition() {
		return itemComposition;
	}

	public void setItemComposition(String itemComposition) {
		this.itemComposition = itemComposition;
	}

	public String getUmName() {
		return umName;
	}

	public void setUmName(String umName) {
		this.umName = umName;
	}

	public String getIngredientFlag() {
		return ingredientFlag;
	}

	public void setIngredientFlag(String ingredientFlag) {
		this.ingredientFlag = ingredientFlag;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStreamUploadId() {
		return streamUploadId;
	}

	public void setStreamUploadId(String streamUploadId) {
		this.streamUploadId = streamUploadId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public List<ItemDiscountVO> getListItemDiscount() {
		return listItemDiscount;
	}

	public void setListItemDiscount(List<ItemDiscountVO> listItemDiscount) {
		this.listItemDiscount = listItemDiscount;
	}

	public Long getBufferStock() {
		return bufferStock;
	}

	public void setBufferStock(Long bufferStock) {
		this.bufferStock = bufferStock;
	}




	/*public StreamedContent getImageDisplay() {
		return imageDisplay;
	}

	public void setImageDisplay(StreamedContent imageDisplay) {
		this.imageDisplay = imageDisplay;
	}*/
	
	
}
