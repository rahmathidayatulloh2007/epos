package com.wo.epos.module.sales.discount.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.sales.discount.service.DiscountService;
import com.wo.epos.module.sales.discount.vo.DiscountVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "discountSearchBean")
@ViewScoped
public class DiscountSearchBean  extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 1983303566708521846L;

	static Logger logger = Logger.getLogger(DiscountSearchBean.class);
	
	@ManagedProperty(value = "#{discountService}")
	private DiscountService discountService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	private DiscountVO discountVOSearchDialog = new DiscountVO();

	private PagingTableModel<DiscountVO> pagingDiscount;	
	
	private List<DiscountVO> DiscountList = new ArrayList<DiscountVO>();	
	private List<DiscountVO> selectedDiscount;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> categorySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> discountProviderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> discountCategorySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	private boolean adminMode;
	
	private String searchAll;	
	
	private Integer checkSearch;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		discountVOSearchDialog = new DiscountVO();
		
		pagingDiscount = new PagingTableModel(discountService, paging);
		
		activeSelectItem = new ArrayList<SelectItem>();
   		activeSelectItem.add(new SelectItem("Y", "Y"));
   		activeSelectItem.add(new SelectItem("N", "N"));
		
		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = discountService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
		categorySelectItem = new ArrayList<SelectItem>();
		List<CategoryVO> categorySelectList = discountService.searchCategoryList();
		for(CategoryVO vo : categorySelectList){		
			categorySelectItem.add(new SelectItem(vo.getCategoryId(), vo.getCategoryName()));
		}
		
		List<ParameterDtl> discountProvicerSelectList = discountService.parameterDtlList(CommonConstants.DISCOUNT_PROVIDER);
   		discountProviderSelectItem = new ArrayList<SelectItem>();
   	    for(ParameterDtl dtl : discountProvicerSelectList){
   	    	discountProviderSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
   	    }
   	    
   	    List<ParameterDtl> discountCategorySelectList = discountService.parameterDtlList(CommonConstants.DISCOUNT_CATEGORY);
		discountCategorySelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : discountCategorySelectList){
	    	discountCategorySelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
	    }
	    
		List<CompanyVO> companyUserList = discountService.searchCompanyList();
		for(int i = 0; i < companyUserList.size(); i++)
		if(userSession.getCompanyId()!=null)
		{
			if(userSession.getCompanyId().equals(companyUserList.get(i).getCompanyId())){
				adminMode = false;
				break;
			}
		}else{
			adminMode = true;
		}
		
		disableFlag = false;
		disableFlagAdd = true;
		disableSearchAll = false;

		checkSearch = 0;
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search(){
		if(checkSearch == 0){	
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}	
			
			if(userSession.getCompanyId() !=null){				
		    	searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId() ));		
		    }
			 disableSearchAll = false;
			 pagingDiscount.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
	}
	
	public void modeDelete(List<DiscountVO> discountVOList)
	{
		try
		{
			for(int i=0; i<discountVOList.size(); i++){
				DiscountVO discountVOTemp = (DiscountVO)discountVOList.get(i);
				
				discountService.delete(discountVOTemp.getDiscountId());
			}
			
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
	            facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
	            null);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formSystemPropertyTitle")),
					null);	  
		}	
	}
	
	public void clear(){
		searchAll = "";
		discountVOSearchDialog = new DiscountVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		discountVOSearchDialog = new DiscountVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(discountVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formDiscountCompany"));
			builder.append(":"+companyService.findById(discountVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("company", discountVOSearchDialog.getCompanyId()));
		}
		if(discountVOSearchDialog.getDiscountCode() !=null && StringUtils.isNotBlank(discountVOSearchDialog.getDiscountCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formDiscountCode"));
			builder.append(":"+discountVOSearchDialog.getDiscountCode()+",");
			searchCriteria.add(new SearchValueObject("discountCode", discountVOSearchDialog.getDiscountCode()));
		}
		if(discountVOSearchDialog.getDiscountName() !=null && StringUtils.isNotBlank(discountVOSearchDialog.getDiscountName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formDiscountName"));
			builder.append(":"+discountVOSearchDialog.getDiscountName()+",");
			searchCriteria.add(new SearchValueObject("discountName", discountVOSearchDialog.getDiscountName()));
		}
		if(discountVOSearchDialog.getDiscountProviderCode() !=null && StringUtils.isNotBlank(discountVOSearchDialog.getDiscountProviderCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formDiscountCategory"));
			builder.append(":"+parameterService.findByDetailId(discountVOSearchDialog.getDiscountProviderCode()).getParameterDtlName()+",");
			searchCriteria.add(new SearchValueObject("discountProvider", discountVOSearchDialog.getDiscountProviderCode()));
		}
		if(discountVOSearchDialog.getDiscountCategoryCode() !=null && StringUtils.isNotBlank(discountVOSearchDialog.getDiscountCategoryCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formDiscountType"));
			builder.append(":"+parameterService.findByDetailId(discountVOSearchDialog.getDiscountCategoryCode()).getParameterDtlName()+",");
			searchCriteria.add(new SearchValueObject("discountType", discountVOSearchDialog.getDiscountCategoryCode()));
		}
		if(discountVOSearchDialog.getCategoryResume() !=null && StringUtils.isNotBlank(discountVOSearchDialog.getCategoryResume())) {
			builder.append(facesUtils.getResourceBundleStringValue("formDiscountResume"));
			builder.append(":"+discountVOSearchDialog.getCategoryResume()+",");
			searchCriteria.add(new SearchValueObject("categoryResume", discountVOSearchDialog.getCategoryResume()));
		}
		if(discountVOSearchDialog.getActiveFlag() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formDiscountActive"));
			builder.append(":"+discountVOSearchDialog.getActiveFlag()+",");
			searchCriteria.add(new SearchValueObject("activeFlag", discountVOSearchDialog.getActiveFlag()));
		}
		
		if(userSession.getCompanyId() !=null){				
	    	searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId() ));		
	    }
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingDiscount.setSearchCriteria(searchCriteria);
		
	}

	public DiscountService getDiscountService() {
		return discountService;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public DiscountVO getDiscountVOSearchDialog() {
		return discountVOSearchDialog;
	}

	public void setDiscountVOSearchDialog(DiscountVO discountVOSearchDialog) {
		this.discountVOSearchDialog = discountVOSearchDialog;
	}

	public PagingTableModel<DiscountVO> getPagingDiscount() {
		return pagingDiscount;
	}

	public void setPagingDiscount(PagingTableModel<DiscountVO> pagingDiscount) {
		this.pagingDiscount = pagingDiscount;
	}

	public List<DiscountVO> getDiscountList() {
		return DiscountList;
	}

	public void setDiscountList(List<DiscountVO> discountList) {
		DiscountList = discountList;
	}

	public List<DiscountVO> getSelectedDiscount() {
		return selectedDiscount;
	}

	public void setSelectedDiscount(List<DiscountVO> selectedDiscount) {
		this.selectedDiscount = selectedDiscount;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getCategorySelectItem() {
		return categorySelectItem;
	}

	public void setCategorySelectItem(List<SelectItem> categorySelectItem) {
		this.categorySelectItem = categorySelectItem;
	}

	public boolean isDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}

	public boolean isDisableFlagAdd() {
		return disableFlagAdd;
	}

	public void setDisableFlagAdd(boolean disableFlagAdd) {
		this.disableFlagAdd = disableFlagAdd;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}

	public List<SelectItem> getDiscountProviderSelectItem() {
		return discountProviderSelectItem;
	}

	public void setDiscountProviderSelectItem(
			List<SelectItem> discountProviderSelectItem) {
		this.discountProviderSelectItem = discountProviderSelectItem;
	}

	public List<SelectItem> getDiscountCategorySelectItem() {
		return discountCategorySelectItem;
	}

	public void setDiscountCategorySelectItem(
			List<SelectItem> discountCategorySelectItem) {
		this.discountCategorySelectItem = discountCategorySelectItem;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
	}

}
