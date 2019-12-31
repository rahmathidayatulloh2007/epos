package com.wo.epos.module.sales.customer.bean;

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
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.sales.businessType.service.BusinessTypeService;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;
import com.wo.epos.module.sales.customer.service.CustomerService;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.sales.districts.service.DistrictsService;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;
import com.wo.epos.module.sales.groupOutlet.service.GroupOutletService;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "customerSearchBeanSales")
@ViewScoped
public class CustomerSearchBean  extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 1983303566708521846L;

	static Logger logger = Logger.getLogger(CustomerSearchBean.class);
	
	private List<SearchObject> searchCriteria;
	@ManagedProperty(value = "#{customerService2}")
	private CustomerService customerService2;
	
	@ManagedProperty(value = "#{businessTypeService}")
	private BusinessTypeService businessTypeService;
	
	@ManagedProperty(value = "#{groupOutletService}")
	private GroupOutletService groupOutletService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{districtsService}")
	private DistrictsService districtsService;
	
	private CustomerSalesVO customerVOSearchDialog = new CustomerSalesVO();
	
	private PagingTableModel<CustomerSalesVO> pagingCustomer;

	private List<CustomerSalesVO> customerList = new ArrayList<CustomerSalesVO>();	
	private List<CustomerSalesVO> selectedCustomer;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> businessTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> groupOutletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> districtsSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> subDistrictsSelectItem = new ArrayList<SelectItem>();
	
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
		customerVOSearchDialog= new CustomerSalesVO();
		pagingCustomer = new PagingTableModel(customerService2,paging);
		
		customerList = customerService2.searchCustomerList();
		

		
	/*	citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = customerService2.searchCityAll();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}*/
		
	    businessTypeSelectItem = new ArrayList<SelectItem>();
		List<BusinessTypeVO> businessTypeSelectList = customerService2.businessTypeSearch();
		for(BusinessTypeVO vo : businessTypeSelectList){		
			businessTypeSelectItem.add(new SelectItem(vo.getBusinessTypeId(), vo.getBusinessTypeName()));
		}
		
		
		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = customerService2.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
	    
	    List<ProvinceVO> provinceSelectList = provinceService.provinceSearch();
	    provinceSelectItem = new ArrayList<SelectItem>();
	    for(ProvinceVO vo : provinceSelectList){
	    	provinceSelectItem.add(new SelectItem(vo.getProvinceId(), vo.getProvinceName()));
	    }
		

		groupOutletSelectItem = new ArrayList<SelectItem>();
		List<GroupOutletVO> groupOuletSelectList = customerService2.groupOutletSearch();
		for(GroupOutletVO vo : groupOuletSelectList){		
			groupOutletSelectItem.add(new SelectItem(vo.getGroupOutletId(), vo.getGroupOutletName()));
		}
		
		districtsSelectItem = new ArrayList<SelectItem>();
		List<DistrictsVO> districtsSelectList = customerService2.districtsSearch();
		for(DistrictsVO vo : districtsSelectList){		
			districtsSelectItem.add(new SelectItem(vo.getDistrictId(), vo.getDistrictName()));
		}
		
		subDistrictsSelectItem = new ArrayList<SelectItem>();
		List<CustomerSalesVO> subDistrictsSelectList = customerService2.searchCustomerList();
		for(CustomerSalesVO vo : subDistrictsSelectList){		
			subDistrictsSelectItem.add(new SelectItem(vo.getSubDistrictId(), vo.getSubDistrictName()));
		}

		disableFlag = false;
		disableFlagAdd = true;
		disableSearchAll = false;

		checkSearch = 0;
		//search();
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
			 pagingCustomer.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
			
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();

		if(customerVOSearchDialog.getCustomerCode() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getCustomerCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerCode"));
			builder.append(":"+customerVOSearchDialog.getCustomerCode()+",");
			searchCriteria.add(new SearchValueObject("customerCode", customerVOSearchDialog.getCustomerCode()));
		}
		if(customerVOSearchDialog.getCustomerName() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getCustomerName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerName"));
			builder.append(":"+customerVOSearchDialog.getCustomerName()+",");
			searchCriteria.add(new SearchValueObject("customerName", customerVOSearchDialog.getCustomerName()));
		}
		if(customerVOSearchDialog.getDistrictId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierCity"));
			builder.append(":"+districtsService.findById(customerVOSearchDialog.getDistrictId()).getDistrictName()+",");
			searchCriteria.add(new SearchValueObject("districts", customerVOSearchDialog.getDistrictId()));
		}
		//--------------------------------------Blom bener-----------------------------------------------------------
		if(customerVOSearchDialog.getSalesman() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getSalesman())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerName"));
			builder.append(":"+customerVOSearchDialog.getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("salesman", customerVOSearchDialog.getSalesman()));
		}
		
		if(customerVOSearchDialog.getBusinessTypeId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formSupplierCity"));
			builder.append(":"+businessTypeService.findById(customerVOSearchDialog.getBusinessTypeId()).getBusinessTypeName()+",");
			searchCriteria.add(new SearchValueObject("businessType", customerVOSearchDialog.getBusinessTypeId()));
		}
		if(customerVOSearchDialog.getGroupOutletId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerCity"));
			builder.append(":"+groupOutletService.findById(customerVOSearchDialog.getGroupOutletId()).getGroupOutletName()+",");
			searchCriteria.add(new SearchValueObject("groupOutlet", customerVOSearchDialog.getGroupOutletId()));
		}
		
		//-----------------------------------------------------------------------------------------------
		
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingCustomer.setSearchCriteria(searchCriteria);
		
	}
	
	

	
	public void modeDelete(List<CustomerSalesVO> customerVOList){
		try
		{
			for(int i=0; i<customerVOList.size(); i++){
				CustomerSalesVO customerVoTemp = (CustomerSalesVO)customerVOList.get(i);
				
				customerService2.delete(customerVoTemp.getCustomerId());
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
		customerVOSearchDialog= new CustomerSalesVO();
		checkSearch = 0;
		search();
		selectedCustomer=null;
	}
	
	public void clearDialog(){
		
		searchAll = "";
		checkSearch = 0;
		customerVOSearchDialog= new CustomerSalesVO();
		search();
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


	public CustomerService getCustomerService2() {
		return customerService2;
	}

	public void setCustomerService2(CustomerService customerService2) {
		this.customerService2 = customerService2;
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

	
	public DistrictsService getDistrictsService() {
		return districtsService;
	}

	public void setDistrictsService(DistrictsService districtsService) {
		this.districtsService = districtsService;
	}

	public CustomerSalesVO getCustomerVOSearchDialog() {
		return customerVOSearchDialog;
	}

	public void setCustomerVOSearchDialog(CustomerSalesVO customerVOSearchDialog) {
		this.customerVOSearchDialog = customerVOSearchDialog;
	}

	public PagingTableModel<CustomerSalesVO> getPagingCustomer() {
		return pagingCustomer;
	}

	public void setPagingCustomer(PagingTableModel<CustomerSalesVO> pagingCustomer) {
		this.pagingCustomer = pagingCustomer;
	}

	public List<CustomerSalesVO> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerSalesVO> customerList) {
		this.customerList = customerList;
	}

	public List<CustomerSalesVO> getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(List<CustomerSalesVO> selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
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

	public List<SelectItem> getBusinessTypeSelectItem() {
		return businessTypeSelectItem;
	}

	public void setBusinessTypeSelectItem(List<SelectItem> businessTypeSelectItem) {
		this.businessTypeSelectItem = businessTypeSelectItem;
	}

	public List<SelectItem> getGroupOutletSelectItem() {
		return groupOutletSelectItem;
	}

	public void setGroupOutletSelectItem(List<SelectItem> groupOutletSelectItem) {
		this.groupOutletSelectItem = groupOutletSelectItem;
	}

	public List<SelectItem> getDistrictsSelectItem() {
		return districtsSelectItem;
	}

	public void setDistrictsSelectItem(List<SelectItem> districtsSelectItem) {
		this.districtsSelectItem = districtsSelectItem;
	}

	public List<SelectItem> getSubDistrictsSelectItem() {
		return subDistrictsSelectItem;
	}

	public void setSubDistrictsSelectItem(List<SelectItem> subDistrictsSelectItem) {
		this.subDistrictsSelectItem = subDistrictsSelectItem;
	}

	public BusinessTypeService getBusinessTypeService() {
		return businessTypeService;
	}

	public void setBusinessTypeService(BusinessTypeService businessTypeService) {
		this.businessTypeService = businessTypeService;
	}

	public GroupOutletService getGroupOutletService() {
		return groupOutletService;
	}

	public void setGroupOutletService(GroupOutletService groupOutletService) {
		this.groupOutletService = groupOutletService;
	}

	public List<SearchObject> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(List<SearchObject> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}





}
