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

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.service.DiscountService;
import com.wo.epos.module.sales.discount.vo.DiscountDtlVO;
import com.wo.epos.module.sales.discount.vo.DiscountVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "discountInputBean")
@ViewScoped
public class DiscountInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 4175725157846889535L;

	static Logger logger = Logger.getLogger(DiscountInputBean.class);
	
	@ManagedProperty(value = "#{discountService}")
	private DiscountService discountService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	
	private DiscountVO discountVO = new DiscountVO();
	private List<DiscountDtlVO> selectedDiscountDtl;
	private List<DiscountDtlVO> discountDtlList;
	private List<CategoryVO> categorySelectList = new ArrayList<CategoryVO>();
	private List<String> selectedCategories;
	private List<Long> discountDtlVOIdList = new ArrayList<Long>();

	private DiscountDtlVO discountDtlVO = new DiscountDtlVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean adminMode;
	private boolean sellChecked;

	private String searchAll;
		
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> discountProviderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> discountCategorySelectItem = new ArrayList<SelectItem>();
	
	
    private String MODE_TYPE;
    
    private Long clickCount;
    
    @PostConstruct
   	public void postConstruct(){
   		super.init();
   		discountVO = new DiscountVO();
   		discountDtlList = new ArrayList<DiscountDtlVO>();
   		//selectedCategories = selectedCategoriesTemp;
   		selectedCategories = new ArrayList<String>();
   		
   		activeSelectItem = new ArrayList<SelectItem>();
   		activeSelectItem.add(new SelectItem("Y", "Y"));
   		activeSelectItem.add(new SelectItem("N", "N"));
   		
   		categorySelectList = new ArrayList<CategoryVO>();
   		categorySelectList = discountService.searchCategoryList();
   		
   		companySelectItem = new ArrayList<SelectItem>();
   		List<CompanyVO> companySelectList = discountService.searchCompanyList();
   		for(CompanyVO vo : companySelectList){		
   			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
   		}
 
   	    
   		List<ParameterDtl> discountProviderSelectList = discountService.parameterDtlList(CommonConstants.DISCOUNT_PROVIDER);
   		discountProviderSelectItem = new ArrayList<SelectItem>();
   	    for(ParameterDtl dtl : discountProviderSelectList){
   	    	discountProviderSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
   	    }
   	    
   	    List<ParameterDtl> discountCategorySelectList = discountService.parameterDtlList(CommonConstants.DISCOUNT_CATEGORY);
   	    discountCategorySelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : discountCategorySelectList){
	    	discountCategorySelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
	    }
   	    
   	    List<CompanyVO> companyUserList = discountService.searchCompanyList();
   		for(int i = 0; i < companyUserList.size(); i++)
   		if(userSession.getCompanyId()!=null){
   			if(userSession.getCompanyId().equals(companyUserList.get(i).getCompanyId())){
   				adminMode = false;
   				break;
   			}
   		}
   		else{
   			adminMode = true;
   		}
   	       		
   		MODE_TYPE = "ADD";
   		disableFlag = false;
   		disableFlagAdd = true;						
   	}
    
    public void save(){
    	try
		{
	    	StringBuilder builder = new StringBuilder();
	    	Category category = new Category();
	    	discountDtlList = new ArrayList<DiscountDtlVO>();
	   
	    	for(int j = 0; j < selectedCategories.size() ; j++){
	   			List<String> categ = selectedCategories;
	   			discountDtlVO  = new DiscountDtlVO();
	   			category = categoryService.findById(new Long(categ.get(j).toString()));
	   			discountDtlVO.setCategoryId(new Long(categ.get(j).toString()));
	   			discountDtlVO.setCategoryName(category.getCategoryName());
	   			builder.append(category.getCategoryName()+",");
	   			discountDtlList.add(discountDtlVO);
	   		}
	    	
	    	if(builder.toString()!=null && !builder.toString().isEmpty()) {
	    		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
	    		discountVO.setCategoryResume(builder.toString());
	    	}	
	    	
	    	if(userSession.getCompanyId() !=null){
		    	discountVO.setCompanyId(userSession.getCompanyId() );
		    }
	    	
	    	if(MODE_TYPE.equals("ADD"))
	    	{
	    	    discountService.save(discountVO, discountDtlList, userSession.getUserCode());
	    	    
	    	    facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
	    	}
	    	else if(MODE_TYPE.equals("EDIT"))
	    	{
	    		discountService.update(discountVO, discountDtlList, userSession.getUserCode());
	    		
	    		facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
	    	}
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
    
    public void modeAdd(){
    	MODE_TYPE = "ADD";
    	
		discountVO = new DiscountVO();
		discountDtlList = new ArrayList<DiscountDtlVO>();
		selectedCategories = new ArrayList<String>();
	}
    
    public void modeEdit(List<DiscountVO> list){
		MODE_TYPE = "EDIT";
		
		for(int i=0; i<list.size(); i++){
			
			DiscountVO vo = (DiscountVO) list.get(i);			  
			discountVO.setDiscountId(vo.getDiscountId());
			discountVO.setCompanyId(vo.getCompanyId());
			discountVO.setCompanyName(vo.getCompanyName());
			discountVO.setDiscountCode(vo.getDiscountCode());
			discountVO.setDiscountName(vo.getDiscountName());
			discountVO.setDiscountValue(vo.getDiscountValue());
			discountVO.setDiscountProviderCode(vo.getDiscountProviderCode());
			discountVO.setDiscountProviderName(vo.getDiscountProviderCode());
			discountVO.setDiscountCategoryCode(vo.getDiscountCategoryCode());
			discountVO.setDiscountCategoryName(vo.getDiscountCategoryCode());
			discountVO.setCategoryResume(vo.getCategoryResume());
			discountVO.setActiveFlag(vo.getActiveFlag());			  	
		}
		
		discountDtlList = new ArrayList<DiscountDtlVO>();
		List<DiscountDtl> discDtlList = discountService.searchDiscountDtlList(discountVO.getDiscountId());
		Category category = new Category();
		
		selectedCategories = new ArrayList<String>();
		for(int j=0; j<discDtlList.size();j++){
			DiscountDtl discountDtl = (DiscountDtl) discDtlList.get(j);
			
			DiscountDtlVO disc = new DiscountDtlVO();
			category = categoryService.findById(discountDtl.getCategory().getCategoryId());
			disc.setDiscountDtlId(discountDtl.getDiscountDtlId());
			disc.setCategoryId(discountDtl.getCategory().getCategoryId());
			disc.setDiscountId(discountDtl.getDiscount().getDiscountId());
			disc.setCategoryName(category.getCategoryName());
			disc.setActiveFlag(discountDtl.getActiveFlag());
			
			discountDtlList.add(disc);
			
			selectedCategories.add(discountDtl.getCategory().getCategoryId().toString());
			
		}
		
	}
    
 
	public void clearAll(){
		discountVO = new DiscountVO();
		discountDtlList = new ArrayList<DiscountDtlVO>();
		selectedCategories = new ArrayList<String>();
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

	public DiscountVO getDiscountVO() {
		return discountVO;
	}

	public void setDiscountVO(DiscountVO discountVO) {
		this.discountVO = discountVO;
	}

	public List<DiscountDtlVO> getSelectedDiscountDtl() {
		return selectedDiscountDtl;
	}

	public void setSelectedDiscountDtl(List<DiscountDtlVO> selectedDiscountDtl) {
		this.selectedDiscountDtl = selectedDiscountDtl;
	}

	public List<DiscountDtlVO> getDiscountDtlList() {
		return discountDtlList;
	}

	public void setDiscountDtlList(List<DiscountDtlVO> discountDtlList) {
		this.discountDtlList = discountDtlList;
	}

	public List<Long> getDiscountDtlVOIdList() {
		return discountDtlVOIdList;
	}

	public void setDiscountDtlVOIdList(List<Long> discountDtlVOIdList) {
		this.discountDtlVOIdList = discountDtlVOIdList;
	}

	public DiscountDtlVO getDiscountDtlVO() {
		return discountDtlVO;
	}

	public void setDiscountDtlVO(DiscountDtlVO discountDtlVO) {
		this.discountDtlVO = discountDtlVO;
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

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

	public boolean isSellChecked() {
		return sellChecked;
	}

	public void setSellChecked(boolean sellChecked) {
		this.sellChecked = sellChecked;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
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

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public List<CategoryVO> getCategorySelectList() {
		return categorySelectList;
	}

	public void setCategorySelectList(List<CategoryVO> categorySelectList) {
		this.categorySelectList = categorySelectList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DiscountInputBean.logger = logger;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public List<String> getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(List<String> selectedCategories) {
		this.selectedCategories = selectedCategories;
	}
	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}


}
