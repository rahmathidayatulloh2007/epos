package com.wo.epos.module.sales.customer.bean;

import java.io.Serializable;
import java.sql.Timestamp;
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
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;
import com.wo.epos.module.sales.customer.service.CustomerService;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;
import com.wo.epos.module.sales.subDistricts.vo.SubDistrictsVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "customerInputBeanSales")
@ViewScoped
public class CustomerInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 4175725157846889535L;

	static Logger logger = Logger.getLogger(CustomerInputBean.class);

	@ManagedProperty(value = "#{customerService2}")
	private CustomerService customerService2;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
		
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	private CustomerSalesVO customerVO = new CustomerSalesVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean adminMode;
	
	private String searchAll;
	
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> businessTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> groupOutletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> districtsSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> subDistrictsSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> customerTypeSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;

    private Long clickCount;

    
    @PostConstruct
   	public void postConstruct(){
   		super.init();
   		if(userSession != null){
   		customerVO = new CustomerSalesVO();   		
   		
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		provinceSelectItem = new ArrayList<SelectItem>();		
		List<ProvinceVO> provinceSelectList = customerService2.searchProvinceAll();
		for(ProvinceVO vo : provinceSelectList){		
			provinceSelectItem.add(new SelectItem(vo.getProvinceId(), vo.getProvinceName()));
		}
		
		businessTypeSelectItem = new ArrayList<SelectItem>();
		List<BusinessTypeVO> businessTypeSelectList = customerService2.businessTypeSearch();
		for(BusinessTypeVO vo : businessTypeSelectList){		
			businessTypeSelectItem.add(new SelectItem(vo.getBusinessTypeId(), vo.getBusinessTypeName()));
		}
		
		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
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
		List<SubDistrictsVO> subDistrictsSelectList = customerService2.subDistrictsSearch();
		
		for(SubDistrictsVO vo : subDistrictsSelectList){		
			subDistrictsSelectItem.add(new SelectItem(vo.getSubDistrictId(), vo.getSubDistrictName()));
		}
		
		List<ParameterDtl> customerTypeSelectList = customerService2.parameterDtlList(CommonConstants.CUSTOMER_TYPE);
		customerTypeSelectItem = new ArrayList<SelectItem>();
		for (ParameterDtl dtl : customerTypeSelectList) {
			customerTypeSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
		}
		
		
	List<CompanyVO> companyUserList = customerService2.searchCompanyList();
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
   	}
    
    public void save(){
    	try
		{
			if (userSession.getCompanyId() != null) {
				customerVO.setCompanyId(userSession.getCompanyId());
			}
			
				if (MODE_TYPE.equals("ADD")) {
					customerService2.save(customerVO, userSession.getUserCode());

					facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
							facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
							null);
				} else if (MODE_TYPE.equals("EDIT")) {
					customerService2.update(customerVO, userSession.getUserCode());

					facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages", facesUtils
							.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), null);
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
    	customerVO = new CustomerSalesVO(); 
	
	}
    
  
    public void modeEdit(List<CustomerSalesVO> list){
		MODE_TYPE = "EDIT";
		
		
		for(int i=0; i<list.size(); i++){
			CustomerSalesVO customerVOTemp = (CustomerSalesVO)list.get(i);
			
			customerVO.setCustomerId(customerVOTemp.getCustomerId());
			customerVO.setCustomerCode(customerVOTemp.getCustomerCode());
			customerVO.setCustomerName(customerVOTemp.getCustomerName());
			customerVO.setFullName(customerVOTemp.getFullName());
			customerVO.setPhoneNo(customerVOTemp.getPhoneNo());
			customerVO.setEmailAddress(customerVOTemp.getEmailAddress());
			customerVO.setAddress(customerVOTemp.getAddress());
			customerVO.setDepositBalance(customerVOTemp.getDepositBalance());
			customerVO.setSalesman(customerVOTemp.getSalesman());
			customerVO.setPortalCode(customerVOTemp.getPortalCode());
			customerVO.setAddressNpwp(customerVOTemp.getAddressNpwp());
			customerVO.setFullNameNpwp(customerVOTemp.getFullNameNpwp());
			customerVO.setNpwpNo(customerVOTemp.getNpwpNo());
			customerVO.setPhoneNo2(customerVOTemp.getPhoneNo2());
			customerVO.setFax(customerVOTemp.getFax());
			customerVO.setCompanyId(customerVOTemp.getCompanyId());
			customerVO.setCompanyName(customerVOTemp.getCompanyName());
			customerVO.setBusinessTypeId(customerVOTemp.getBusinessTypeId());
			customerVO.setBusinessTypeName(customerVOTemp.getBusinessTypeName());
			customerVO.setGroupOutletId(customerVOTemp.getGroupOutletId());
			customerVO.setGroupOutletName(customerVOTemp.getGroupOutletName());
			customerVO.setProvinceId(customerVOTemp.getProvinceId());
			customerVO.setProvinceName(customerVOTemp.getProvinceName());
			customerVO.setDistrictId(customerVOTemp.getDistrictId());
			customerVO.setDistrictName(customerVOTemp.getDistrictName());
			customerVO.setCustomerTypeCode(customerVOTemp.getCustomerTypeCode());
			customerVO.setCustomerTypeName(customerVOTemp.getCustomerTypeName());
			customerVO.setTermOfPayment(customerVOTemp.getTermOfPayment());
/*			customerVO.setRegisterOutletId(customerVOTemp.getRegisterOutletId());
			customerVO.setRegisterOutletName(customerVOTemp.getRegisterOutletName());*/
			customerVO.setSubDistrictId(customerVOTemp.getSubDistrictId());
			customerVO.setSubDistrictName(customerVOTemp.getSubDistrictName());
			customerVO.setActiveFlag(customerVOTemp.getActiveFlag());
			customerVO.setUpdateBy("Admin");
			customerVO.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			

			
		}
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

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public CustomerSalesVO getCustomerVO() {
		return customerVO;
	}

	public void setCustomerVO(CustomerSalesVO customerVO) {
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

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
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

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
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

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CustomerInputBean.logger = logger;
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

	public List<SelectItem> getProvinceSelectItem() {
		return provinceSelectItem;
	}

	public void setProvinceSelectItem(List<SelectItem> provinceSelectItem) {
		this.provinceSelectItem = provinceSelectItem;
	}

	public List<SelectItem> getCustomerTypeSelectItem() {
		return customerTypeSelectItem;
	}

	public void setCustomerTypeSelectItem(List<SelectItem> customerTypeSelectItem) {
		this.customerTypeSelectItem = customerTypeSelectItem;
	}


	
	


}
