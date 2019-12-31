package com.wo.epos.module.inv.customer.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.customer.model.Customer;
import com.wo.epos.module.inv.customer.service.CustomerService;
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "customerInputBean")
@ViewScoped
public class CustomerInputBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 4740244802528600L;
	
	static Logger logger = Logger.getLogger(CustomerInputBean.class);
	
	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
		
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	private CustomerVO customerVO = new CustomerVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	
	private String MODE_TYPE;
	
	private Long companyId;
	private Long outletId;
	
	
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
			outletId = userVO.getOutletId();
		}
		customerVO = new CustomerVO();
		
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
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
		
		outletSelectItem = new ArrayList<SelectItem>();
		List<OutletVO> outletSelectList = outletService.searchOutletList();
		for(OutletVO vo : outletSelectList){		
			outletSelectItem.add(new SelectItem(vo.getOutletId(), vo.getOutletName()));
		}
		
		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;
	}
	
	public Boolean save(){
		Boolean flag=false;
		if(MODE_TYPE.equals("ADD")){
			if(!validate()){
		    //customerService.save(customerVO, "admin");
		    customerService.save(customerVO, userSession.getUserCode());
			}else{
				flag = true;
			}
		}else if(MODE_TYPE.equals("EDIT")){
			if(!validate()){
			//customerService.update(customerVO, "admin");
			customerService.update(customerVO, userSession.getUserCode());
			}else{
				flag = true;
			}
		}
		
		return flag;
	}
	
	public Boolean validate(){
		Boolean result = false;
		if(customerVO.getCompany().getCompanyId() == null){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:companyName", 
	                facesUtils.getResourceBundleStringValue("formCustomerCompany")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getCompany().getCompanyId() == null){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:outletName", 
	                facesUtils.getResourceBundleStringValue("formCustomerOutlet")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getTitle() == null || customerVO.getTitle().isEmpty()){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:title", 
	                facesUtils.getResourceBundleStringValue("formCustomerName")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getFullName() == null || customerVO.getFullName().isEmpty()){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:customerName", 
	                facesUtils.getResourceBundleStringValue("formCustomerName")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getPhoneNo() == null || customerVO.getPhoneNo().isEmpty()){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:phoneNo", 
	                facesUtils.getResourceBundleStringValue("formCustomerPhoneNo")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getEmailAddress() == null || customerVO.getEmailAddress().isEmpty()){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:emailAdd", 
	                facesUtils.getResourceBundleStringValue("formCustomerEmailAdd")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getBirthDate() == null){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:birthDate", 
	                facesUtils.getResourceBundleStringValue("formCustomerBirthDate")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getAddress() == null || customerVO.getAddress().isEmpty()){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:address", 
	                facesUtils.getResourceBundleStringValue("formCustomerAddress")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		if(customerVO.getCity().getCityId() == null){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:cityId", 
	                facesUtils.getResourceBundleStringValue("formCustomerCity")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		
		return result;
	}
	
	
	public void modeAdd(){
		customerVO = new CustomerVO();
		Outlet outlet = new Outlet();
		Company company = new Company();
		City city = new City();
		customerVO.setRegisterOutlet(outlet);
		customerVO.setCity(city);
		customerVO.setCompany(company);
		
		if(companyId!=null){
			customerVO.setCompany(companyService.findById(companyId));
			
			if(outletId!=null){
				customerVO.setRegisterOutlet(outletService.findById(outletId));
			}
		}
		
	}
	
	public void modeEdit(List<CustomerVO> customerList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<customerList.size(); i++){
			  CustomerVO companyVOTemp = (CustomerVO)customerList.get(i);
			  Customer customer = customerService.findById(companyVOTemp.getCustomerId());
			  
			  customerVO.setCustomerId(customer.getCustomerId());
			  customerVO.setCustomerCode(customer.getCustomerCode());
			  customerVO.setFullName(customer.getFullName());
			  customerVO.setPhoneNo(customer.getPhoneNo());
			  customerVO.setCompany(customer.getCompany());
			  customerVO.setCity(customer.getCity());
			  customerVO.setAddress(customer.getAddress());
			  customerVO.setEmailAddress(customer.getEmailAddress());
			  customerVO.setBirthDate(customer.getBirthDate());
			  customerVO.setRegisterOutlet(customer.getRegisterOutlet());
			  customerVO.setTitle(customer.getTitle());
			  customerVO.setActiveFlag(customer.getActiveFlag());
			  
		}
		
	}
	
	public void onChangeCompany(){
		List<Outlet> transferFromSelectList = outletService.findOutletByCompany(customerVO.getCompany().getCompanyId());
		outletSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : transferFromSelectList){
	    	outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    }
		
	}
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public CustomerVO getCustomerVO() {
		return customerVO;
	}

	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
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

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
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

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
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

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}

	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	
	
	

}
