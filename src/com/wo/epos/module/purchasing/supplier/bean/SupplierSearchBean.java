package com.wo.epos.module.purchasing.supplier.bean;

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
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.purchasing.supplier.service.SupplierService;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "supplierSearchBean")
@ViewScoped
public class SupplierSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -4253712855380640035L;

	static Logger logger = Logger.getLogger(SupplierSearchBean.class);
	
	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
		
	private SupplierVO supplierVOSearchDialog = new SupplierVO();	

	private PagingTableModel<SupplierVO> pagingSupplier;	
	
	private List<SupplierVO> supplierList = new ArrayList<SupplierVO>();	
	private List<SupplierVO> selectedSupplier;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();
	
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
		supplierVOSearchDialog = new SupplierVO();
		pagingSupplier = new PagingTableModel(supplierService, paging);
		supplierList = supplierService.searchSupplierList();
		
		citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = companyService.searchCityAll();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}
		
		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = supplierService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
	    
	    List<ProvinceVO> provinceSelectList = provinceService.provinceSearch();
	    provinceSelectItem = new ArrayList<SelectItem>();
	    for(ProvinceVO vo : provinceSelectList){
	    	provinceSelectItem.add(new SelectItem(vo.getProvinceId(), vo.getProvinceName()));
	    }
	    
		if(adminMode == false){
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));
			 pagingSupplier.setSearchCriteria(searchCriteria);
		}
		 
        ;
		disableFlag = false;
		disableFlagAdd = true;	
		disableSearchAll = false;
		
		checkSearch = 0;
		
		List<CompanyVO> companyUserList = supplierService.searchCompanyList();
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
			
			if(adminMode == false){
				searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));
			}
			 disableSearchAll = false;
	         pagingSupplier.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 

	public void modeDelete(List<SupplierVO> supplierVOList){
		try
		{
			for(int i=0; i<supplierVOList.size(); i++){
				SupplierVO supplierVoTemp = (SupplierVO)supplierVOList.get(i);
				
				supplierService.delete(supplierVoTemp.getSupplierId());
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
		supplierVOSearchDialog = new SupplierVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		supplierVOSearchDialog = new SupplierVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(supplierVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierCompany"));
			builder.append(":"+companyService.findById(supplierVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("company", supplierVOSearchDialog.getCompanyId()));
		}
		if(supplierVOSearchDialog.getSupplierCode() !=null && StringUtils.isNotBlank(supplierVOSearchDialog.getSupplierCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierCode"));
			builder.append(":"+supplierVOSearchDialog.getSupplierCode()+",");
			searchCriteria.add(new SearchValueObject("supplierCode", supplierVOSearchDialog.getSupplierCode()));
		}
		if(supplierVOSearchDialog.getSupplierName() !=null && StringUtils.isNotBlank(supplierVOSearchDialog.getSupplierName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierName"));
			builder.append(":"+supplierVOSearchDialog.getSupplierName()+",");
			searchCriteria.add(new SearchValueObject("supplierName", supplierVOSearchDialog.getSupplierName()));
		}
		if(supplierVOSearchDialog.getAddress() !=null && StringUtils.isNotBlank(supplierVOSearchDialog.getAddress())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierAddress"));
			builder.append(":"+supplierVOSearchDialog.getAddress()+",");
			searchCriteria.add(new SearchValueObject("address", supplierVOSearchDialog.getAddress()));
		}
		if(supplierVOSearchDialog.getProvinceName() !=null && StringUtils.isNotBlank(supplierVOSearchDialog.getProvinceName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierProvince"));
			builder.append(":"+supplierVOSearchDialog.getProvinceName()+",");
			//builder.append(":"+provinceService.findById(supplierVOSearchDialog.getProvinceId()).getProvinceName()+",");
			searchCriteria.add(new SearchValueObject("province", supplierVOSearchDialog.getProvinceName()));
		}
		if(supplierVOSearchDialog.getCityId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierCity"));
			builder.append(":"+cityService.findById(supplierVOSearchDialog.getCityId()).getCityName()+",");
			searchCriteria.add(new SearchValueObject("city", supplierVOSearchDialog.getCityId()));
		}
		if(supplierVOSearchDialog.getPicName() !=null && StringUtils.isNotBlank(supplierVOSearchDialog.getPicName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierPicName"));
			builder.append(":"+supplierVOSearchDialog.getPicName()+",");
			searchCriteria.add(new SearchValueObject("picName", supplierVOSearchDialog.getPicName()));
		}
		if(supplierVOSearchDialog.getPhoneNo() !=null && StringUtils.isNotBlank(supplierVOSearchDialog.getPhoneNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierPhoneNo"));
			builder.append(":"+supplierVOSearchDialog.getPhoneNo()+",");
			searchCriteria.add(new SearchValueObject("phoneNo", supplierVOSearchDialog.getPhoneNo()));
		}
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingSupplier.setSearchCriteria(searchCriteria);
		
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ProvinceService getProvinceService() {
		return provinceService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public SupplierVO getSupplierVOSearchDialog() {
		return supplierVOSearchDialog;
	}

	public void setSupplierVOSearchDialog(SupplierVO supplierVOSearchDialog) {
		this.supplierVOSearchDialog = supplierVOSearchDialog;
	}

	public PagingTableModel<SupplierVO> getPagingSupplier() {
		return pagingSupplier;
	}

	public void setPagingSupplier(PagingTableModel<SupplierVO> pagingSupplier) {
		this.pagingSupplier = pagingSupplier;
	}

	public List<SupplierVO> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<SupplierVO> supplierList) {
		this.supplierList = supplierList;
	}

	public List<SupplierVO> getSelectedSupplier() {
		return selectedSupplier;
	}

	public void setSelectedSupplier(List<SupplierVO> selectedSupplier) {
		this.selectedSupplier = selectedSupplier;
	}

	
	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getCitySelectItem() {
		return citySelectItem;
	}

	public void setCitySelectItem(List<SelectItem> citySelectItem) {
		this.citySelectItem = citySelectItem;
	}

	public List<SelectItem> getProvinceSelectItem() {
		return provinceSelectItem;
	}

	public void setProvinceSelectItem(List<SelectItem> provinceSelectItem) {
		this.provinceSelectItem = provinceSelectItem;
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
	
	
}
