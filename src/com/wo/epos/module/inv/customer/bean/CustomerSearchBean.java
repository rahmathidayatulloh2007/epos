package com.wo.epos.module.inv.customer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.customer.service.CustomerService;
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "customerSearchBean")
@ViewScoped
public class CustomerSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -743646042554424313L;
	
	static Logger logger = Logger.getLogger(CustomerSearchBean.class);
	
	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	private CustomerVO customerVOSearchDialog = new CustomerVO();
	
	private PagingTableModel<CustomerVO> pagingCustomer;
	
	private List<CustomerVO> customerList = new ArrayList<CustomerVO>();
	private List<CustomerVO> selectedCustomer;
	
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;	
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private Date startBirthDate;
	private Date endBirthDate;

	private String searchAll;
	
	private Integer checkSearch;
	
	private Long companyId;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
    	super.init();
    	
    	HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		HttpSession session = request.getSession(true);

		UserVO userVO = (UserVO) session
				.getAttribute(CommonConstants.SESSION_USER);

		if (userVO != null) {
			companyId = userVO.getCompanyId();
		}
		
    	customerVOSearchDialog = new CustomerVO();
    	
    	citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = outletService.searchCityAll();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}
		
		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = outletService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
		
		
    	pagingCustomer = new PagingTableModel(customerService, paging);
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
			
			if(companyId!=null){
			searchCriteria.add(new SearchValueObject("company", companyId));
			}
			
			disableSearchAll = false;
			pagingCustomer.setSearchCriteria(searchCriteria);
			//customerList = customerService.searchCustomerList();
    	}else{
    		searchDialog();
    	}
    }
    
    public void modeDelete(List<CustomerVO> customerVOList){
    	for(int i=0; i<customerVOList.size(); i++){
    		CustomerVO customerVOTemp = customerVOList.get(i);
    		customerService.delete(customerVOTemp.getCustomerId());
    	}
    }
    
    public void clear(){
		searchAll = "";
		customerVOSearchDialog = new CustomerVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		customerVOSearchDialog = new CustomerVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(companyId!= null){
			customerVOSearchDialog.setCompanyId(companyId);
		}
		
		if(customerVOSearchDialog.getCompanyId() !=null ) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerCompany"));
			builder.append(":"+companyService.findById(customerVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("company", customerVOSearchDialog.getCompanyId()));
		}
		if(customerVOSearchDialog.getFullName() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getFullName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerName"));
			builder.append(":"+customerVOSearchDialog.getFullName()+",");
			searchCriteria.add(new SearchValueObject("customerName", customerVOSearchDialog.getFullName()));
		}
		
		if(customerVOSearchDialog.getPhoneNo() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getPhoneNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerPhoneNo"));
			builder.append(":"+customerVOSearchDialog.getAddress()+",");
			searchCriteria.add(new SearchValueObject("phoneNo", customerVOSearchDialog.getPhoneNo()));
		}
		if(customerVOSearchDialog.getEmailAddress() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getEmailAddress())){
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerEmailAdd"));
			builder.append(":"+customerVOSearchDialog.getEmailAddress()+",");
			searchCriteria.add(new SearchValueObject("emailAdd", customerVOSearchDialog.getEmailAddress()));
		}
		if(getStartBirthDate() !=null || getEndBirthDate()!=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerBirthDate"));
			if(getStartBirthDate() !=null){
			builder.append(":"+getStartBirthDate()+",");
			searchCriteria.add(new SearchValueObject("startBirthDate", getStartBirthDate()));
			}
			if(getEndBirthDate() !=null){
				builder.append(":"+getEndBirthDate()+",");
				searchCriteria.add(new SearchValueObject("endBirthDate", getEndBirthDate()));
			}
		}
		if(customerVOSearchDialog.getAddress() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getAddress())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerAddress"));
			builder.append(":"+customerVOSearchDialog.getAddress()+",");
			searchCriteria.add(new SearchValueObject("address", customerVOSearchDialog.getAddress()));
		}
		if(customerVOSearchDialog.getCityId() !=null && StringUtils.isNotBlank(customerVOSearchDialog.getCityId().toString())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCustomerCity"));
			builder.append(":"+cityService.findById(customerVOSearchDialog.getCityId()).getCityName()+",");
			searchCriteria.add(new SearchValueObject("city", customerVOSearchDialog.getCityId()));
		}
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingCustomer.setSearchCriteria(searchCriteria);
		
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public CustomerVO getCustomerVOSearchDialog() {
		return customerVOSearchDialog;
	}

	public void setCustomerVOSearchDialog(CustomerVO customerVOSearchDialog) {
		this.customerVOSearchDialog = customerVOSearchDialog;
	}

	public PagingTableModel<CustomerVO> getPagingCustomer() {
		return pagingCustomer;
	}

	public void setPagingCustomer(PagingTableModel<CustomerVO> pagingCustomer) {
		this.pagingCustomer = pagingCustomer;
	}

	public List<CustomerVO> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerVO> customerList) {
		this.customerList = customerList;
	}

	public List<CustomerVO> getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(List<CustomerVO> selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public List<SelectItem> getCitySelectItem() {
		return citySelectItem;
	}

	public void setCitySelectItem(List<SelectItem> citySelectItem) {
		this.citySelectItem = citySelectItem;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
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


	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}



	public Date getStartBirthDate() {
		return startBirthDate;
	}



	public void setStartBirthDate(Date startBirthDate) {
		this.startBirthDate = startBirthDate;
	}



	public Date getEndBirthDate() {
		return endBirthDate;
	}



	public void setEndBirthDate(Date endBirthDate) {
		this.endBirthDate = endBirthDate;
	}

	
	
	

}
