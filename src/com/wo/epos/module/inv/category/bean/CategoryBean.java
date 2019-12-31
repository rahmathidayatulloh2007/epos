package com.wo.epos.module.inv.category.bean;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.uam.company.service.CompanyService;

@ManagedBean(name = "categoryBean")
@ViewScoped
public class CategoryBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -743646042554424313L;
	
	static Logger logger = Logger.getLogger(CategoryBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{categorySearchBean}")
	private CategorySearchBean categorySearchBean;
	
	@ManagedProperty(value = "#{categoryInputBean}")
	private CategoryInputBean categoryInputBean;
	
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	
	@PostConstruct
	public void postConstruct() {    
    	MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;		
    }
    
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		try {
			categorySearchBean.searchCategory();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modeAddNew(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		categoryInputBean.setMODE_TYPE("ADD");
		categoryInputBean.modeAddNew();
	}
	
	public void modeAddFromParent(Category category){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		categoryInputBean.setMODE_TYPE("ADD");
		categoryInputBean.modeAdd(category);
	}
	
	public void modeEdit(Category category){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		categoryInputBean.modeEdit(category);
	}
	
	public void modeDelete(Category category){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		categorySearchBean.modeDelete(category);
		try {
			categorySearchBean.searchCategory();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modeSave(){
		if(categoryInputBean.validateCompany()&& categoryInputBean.validateCategory()){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		categoryInputBean.save();
		try {
			categorySearchBean.searchCategory();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
    
	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public CategorySearchBean getCategorySearchBean() {
		return categorySearchBean;
	}

	public void setCategorySearchBean(CategorySearchBean categorySearchBean) {
		this.categorySearchBean = categorySearchBean;
	}

	public CategoryInputBean getCategoryInputBean() {
		return categoryInputBean;
	}

	public void setCategoryInputBean(CategoryInputBean categoryInputBean) {
		this.categoryInputBean = categoryInputBean;
	}

}
