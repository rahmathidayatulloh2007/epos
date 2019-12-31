package com.wo.epos.module.inv.item.bean;

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
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.inv.item.service.ItemService;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.purchasing.supplier.service.SupplierService;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.inv.um.service.UmService;
import com.wo.epos.module.inv.um.vo.UmVO;

@ManagedBean(name = "itemSearchBean")
@ViewScoped
public class ItemSearchBean  extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 1510375883523355296L;

	static Logger logger = Logger.getLogger(ItemSearchBean.class);
	
	@ManagedProperty(value = "#{itemService}")
	private ItemService itemService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{umService}")
	private UmService umService;
	
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	
	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;
	
	private ItemVO itemVOSearchDialog = new ItemVO();

	private PagingTableModel<ItemVO> pagingItem;	
	
	private List<ItemVO> itemList = new ArrayList<ItemVO>();	
	private List<ItemVO> selectedItems;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> umSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> supplierSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> categorySelectItem = new ArrayList<SelectItem>();
	
	
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
		if(userSession != null){
			itemVOSearchDialog = new ItemVO();
			
			pagingItem = new PagingTableModel(itemService, paging);
			
			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = itemService.searchCompanyList();
			for(CompanyVO vo : companySelectList){		
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}
			
			umSelectItem = new ArrayList<SelectItem>();
			List<UmVO> umSelectList = itemService.searchUmList();
			for(UmVO vo : umSelectList){		
				if(adminMode == false)
		    	{
		    		if(vo.getCompanyId().equals(userSession.getCompanyId())){
		    			umSelectItem.add(new SelectItem(vo.getUmId(), vo.getUmName()));
		    		}
		    	}else
		    	{
		    		umSelectItem.add(new SelectItem(vo.getUmId(), vo.getUmName()));
		    	}
			}
			
			supplierSelectItem = new ArrayList<SelectItem>();
			List<SupplierVO> supplierSelectList = itemService.searchSupplierList();
			for(SupplierVO vo : supplierSelectList){		
				if(adminMode == false)
		    	{
		    		if(vo.getCompanyId().equals(userSession.getCompanyId())){
		    			supplierSelectItem.add(new SelectItem(vo.getSupplierId(), vo.getSupplierName()));
		    		}
		    	}else
		    	{
		    		supplierSelectItem.add(new SelectItem(vo.getSupplierId(), vo.getSupplierName()));
		    	}
			}
			
			categorySelectItem = new ArrayList<SelectItem>();
			List<CategoryVO> categorySelectList = itemService.searchCategoryList();
			for(CategoryVO vo : categorySelectList){		
				if(adminMode == false)
		    	{
		    		if(vo.getCompanyId().equals(userSession.getCompanyId())){
		    			categorySelectItem.add(new SelectItem(vo.getCategoryId(), vo.getCategoryName()));
		    		}
		    	}else
		    	{
		    		categorySelectItem.add(new SelectItem(vo.getCategoryId(), vo.getCategoryName()));
		    	}
			}
			
			List<CompanyVO> companyUserList = itemService.searchCompanyList();
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
		    	searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId() ));		
		    }
			 disableSearchAll = false;
			 pagingItem.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
	}
	
	public void modeDelete(List<ItemVO> itemVOList){
		try
		{
			for(int i=0; i<itemVOList.size(); i++){
				ItemVO itemVoTemp = (ItemVO)itemVOList.get(i);
				
				itemService.delete(itemVoTemp.getItemId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formItemTitle")),
					null);	  
		}	
	}
	
	public void clear(){
		searchAll = "";
		itemVOSearchDialog = new ItemVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		itemVOSearchDialog = new ItemVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(itemVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formItemCompany"));
			builder.append(":"+companyService.findById(itemVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("company", itemVOSearchDialog.getCompanyId()));
		}
		if(itemVOSearchDialog.getItemCode() !=null && StringUtils.isNotBlank(itemVOSearchDialog.getItemCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formItemCode"));
			builder.append(":"+itemVOSearchDialog.getItemCode()+",");
			searchCriteria.add(new SearchValueObject("itemCode", itemVOSearchDialog.getItemCode()));
		}
		if(itemVOSearchDialog.getItemName() !=null && StringUtils.isNotBlank(itemVOSearchDialog.getItemName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formItemName"));
			builder.append(":"+itemVOSearchDialog.getItemName()+",");
			searchCriteria.add(new SearchValueObject("itemName", itemVOSearchDialog.getItemName()));
		}
		if(itemVOSearchDialog.getCategoryId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formItemCategory"));
			builder.append(":"+categoryService.findById(itemVOSearchDialog.getCategoryId()).getCategoryName()+",");
			//builder.append(":"+itemVOSearchDialog.getCategoryName()+",");
			searchCriteria.add(new SearchValueObject("category", itemVOSearchDialog.getCategoryId()));
		}
		if(itemVOSearchDialog.getUmId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formItemUm"));
			builder.append(":"+umService.findById(itemVOSearchDialog.getUmId()).getUmName()+",");
			searchCriteria.add(new SearchValueObject("um", itemVOSearchDialog.getUmId()));
		}
		
		if(userSession.getCompanyId() !=null){				
	    	searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId() ));		
	    }
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingItem.setSearchCriteria(searchCriteria);
		
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public UmService getUmService() {
		return umService;
	}

	public void setUmService(UmService umService) {
		this.umService = umService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public ItemVO getItemVOSearchDialog() {
		return itemVOSearchDialog;
	}

	public void setItemVOSearchDialog(ItemVO itemVOSearchDialog) {
		this.itemVOSearchDialog = itemVOSearchDialog;
	}

	public PagingTableModel<ItemVO> getPagingItem() {
		return pagingItem;
	}

	public void setPagingItem(PagingTableModel<ItemVO> pagingItem) {
		this.pagingItem = pagingItem;
	}

	public List<ItemVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemVO> itemList) {
		this.itemList = itemList;
	}

	public List<ItemVO> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(List<ItemVO> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getUmSelectItem() {
		return umSelectItem;
	}

	public void setUmSelectItem(List<SelectItem> umSelectItem) {
		this.umSelectItem = umSelectItem;
	}

	public List<SelectItem> getSupplierSelectItem() {
		return supplierSelectItem;
	}

	public void setSupplierSelectItem(List<SelectItem> supplierSelectItem) {
		this.supplierSelectItem = supplierSelectItem;
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

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

	public List<SelectItem> getCategorySelectItem() {
		return categorySelectItem;
	}

	public void setCategorySelectItem(List<SelectItem> categorySelectItem) {
		this.categorySelectItem = categorySelectItem;
	} 
	
}
