package com.wo.epos.module.inv.category.vo;

import java.io.Serializable;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.uam.company.model.Company;

public class CategoryVO extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 3997545790290082720L;
	
	private Long categoryId;	
	private String categoryCode;
	private String categoryName;
	private String categoryDesc;
	private Integer categoryLevel;
	private Long parentId;
	private String parentCode;
	private String parentName;
	private Long companyId;
	private String activeFlag;
	protected String enabledFlag;
	
	private Company company;
	
	private List<Category> childCategoryList;
	
	public CategoryVO() {
		categoryId = new Long(0);
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<Category> getChildCategoryList() {
		return childCategoryList;
	}

	public void setChildCategoryList(List<Category> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
