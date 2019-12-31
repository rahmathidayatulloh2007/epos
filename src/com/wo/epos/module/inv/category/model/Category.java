package com.wo.epos.module.inv.category.model;

import java.io.Serializable;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;

public class Category extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6143470924780619930L;
	
	private Long categoryId;	
	private String categoryCode;
	private String categoryName;
	private String categoryDesc;
	private Integer categoryLevel;
	
	private Company company;
	private Category parent;
	
	private List<Category> childCategoryList;
	
	public Integer getDetailSize(){
		return childCategoryList!=null?childCategoryList.size():0;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public Integer getCategoryLevel() {
		return categoryLevel;
	}
	public void setCategoryLevel(Integer categoryLevel) {
		this.categoryLevel = categoryLevel;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public List<Category> getChildCategoryList() {
		return childCategoryList;
	}
	public void setChildCategoryList(List<Category> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}

}
