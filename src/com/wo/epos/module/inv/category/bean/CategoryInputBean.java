package com.wo.epos.module.inv.category.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "categoryInputBean")
@ViewScoped
public class CategoryInputBean extends CommonBean implements Serializable{

private static final long serialVersionUID = -743646042554424313L;
	
	static Logger logger = Logger.getLogger(CategoryInputBean.class);
	
	// Add Service
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	// Add Object Class
	private CategoryVO categoryVO = new CategoryVO();
	
	
	// Add Object List
	private List<Category> categoryList = new ArrayList<Category>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
    
//	private PagingTableModel<CategoryVO> pagingCategory;
	
	 private String MODE_TYPE;
	
	@PostConstruct
	public void postConstruct() {
		super.init();
		
			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}
		
	}
	
	
	public boolean validateCompany() {
		boolean valid = true;
		if (userSession.getCompanyId() == null) {
			if (categoryVO.getCompany().getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCategory",
						facesUtils.getResourceBundleStringValue("formProductCompany") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else {
				valid = true;
			}
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	public boolean validateCategory() {
		boolean valid = true;
		
			if (categoryVO.getCategoryCode() == null || categoryVO.getCategoryCode().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCategory",
						facesUtils.getResourceBundleStringValue("formCategoryCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}  if(categoryVO.getCategoryName() == null || categoryVO.getCategoryName().trim().isEmpty()) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCategory",
						facesUtils.getResourceBundleStringValue("formCategoryName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}else{valid = true;}

		
		return valid;
	}
	
	
	
	
	public void save(){
		try 
		{
			if(MODE_TYPE.equals("ADD"))
			{
			    categoryService.insertCategory(categoryVO, true, userSession.getUserCode());
			    
			    facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				categoryService.insertCategory(categoryVO, false, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
			}
		} 
		catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);	  
		}
	}
	
	public void modeEdit(Category vo){
		
		categoryVO = new CategoryVO();
		MODE_TYPE = "EDIT";
		categoryVO.setCategoryId(vo.getCategoryId());
		categoryVO.setCompany(vo.getCompany());
		categoryVO.setActiveFlag(vo.getActiveFlag());
		categoryVO.setCategoryDesc(vo.getCategoryDesc());
		categoryVO.setCategoryLevel(vo.getCategoryLevel());
		categoryVO.setCategoryName(vo.getCategoryName());
		categoryVO.setCategoryCode(vo.getCategoryCode());
		
		if(vo.getParent()!=null){
			categoryVO.setParentCode(vo.getParent().getCategoryCode());
			categoryVO.setParentName(vo.getParent().getCategoryName());
		}
	}
	
	public void modeAddNew(){		
		categoryVO = new CategoryVO();
		categoryVO.setCompany(new Company());
		categoryVO.getCompany().setCompanyId(getCompanyIdFromUser());
		categoryVO.setCategoryLevel(1);
	}
	
	public void modeAdd(Category vo){
		
		categoryVO = new CategoryVO();
		categoryVO.setCompany(vo.getCompany());
		categoryVO.setParentId(vo.getCategoryId());
		categoryVO.setParentCode(vo.getCategoryCode());
		categoryVO.setParentName(vo.getCategoryName());
		categoryVO.setCategoryLevel(vo.getCategoryLevel()+1);
	}
    
    
    // TODO get from user login
    public Long getCompanyIdFromUser() {
    	return userSession.getCompanyId();
    }
    
	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public CategoryVO getCategoryVO() {
		return categoryVO;
	}

	public void setCategoryVO(CategoryVO categoryVO) {
		this.categoryVO = categoryVO;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
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

}
