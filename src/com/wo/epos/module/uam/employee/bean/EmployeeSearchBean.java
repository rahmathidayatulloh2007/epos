package com.wo.epos.module.uam.employee.bean;

import java.io.Serializable;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.employee.service.EmployeeService;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "employeeSearchBean")
@ViewScoped
public class EmployeeSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -8188275715592692045L;

	static Logger logger = Logger.getLogger(EmployeeSearchBean.class);
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService employeeService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	private EmployeeVO employeeVOSearchDialog = new EmployeeVO();

	private PagingTableModel<EmployeeVO> pagingEmployee;	
	
	private List<EmployeeVO> employeeList = new ArrayList<EmployeeVO>();	
	private List<EmployeeVO> selectedEmployee;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> genderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> religionSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> maritalStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> employeeStatSelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;
	
	private Long companyId;
	
	private Long outletId;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			HttpSession session = request.getSession(true);

			UserVO userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);

			if (userVO != null) {
				companyId = userVO.getCompanyId();
				outletId = userVO.getOutletId();
			}

			employeeVOSearchDialog = new EmployeeVO();
			pagingEmployee = new PagingTableModel(employeeService, paging);

			citySelectItem = new ArrayList<SelectItem>();
			List<CityVO> citySelectList = employeeService.searchCityAll();
			for (CityVO vo : citySelectList) {
				citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
			}

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = employeeService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}

			List<ParameterDtl> genderSelectList = employeeService.parameterDtlList(CommonConstants.GENDER);
			genderSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : genderSelectList) {
				genderSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			List<ParameterDtl> religionSelectList = employeeService.parameterDtlList(CommonConstants.RELIGION);
			religionSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : religionSelectList) {
				religionSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			List<ParameterDtl> maritalStatSelectList = employeeService.parameterDtlList(CommonConstants.MARITAL_STATUS);
			maritalStatSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : maritalStatSelectList) {
				maritalStatSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			List<ParameterDtl> employeeStatSelectList = employeeService
					.parameterDtlList(CommonConstants.EMPLOYEE_STATUS);
			employeeStatSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : employeeStatSelectList) {
				employeeStatSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
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
			
			if(getCompanyId() != null){
				searchCriteria.add(new SearchValueObject("company", ""+getCompanyId()));
			}
			
			 disableSearchAll = false;
	         pagingEmployee.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
	}
	
	public void modeDelete(List<EmployeeVO> employeeVOList){
		try
		{
			for(int i=0; i<employeeVOList.size(); i++){
				EmployeeVO employeeVoTemp = (EmployeeVO)employeeVOList.get(i);
				
				employeeService.delete(employeeVoTemp.getEmployeeId());
			}
			
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
	            facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
	            null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formEmployeeTitle")),
					null);	    
		}	
	}
	
	public void clear(){
		searchAll = "";
		employeeVOSearchDialog = new EmployeeVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		employeeVOSearchDialog = new EmployeeVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(getCompanyId() != null){
			searchCriteria.add(new SearchValueObject("company", ""+getCompanyId()));
		}
		
		if(employeeVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeCompany"));
			builder.append(":"+companyService.findById(employeeVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("company", employeeVOSearchDialog.getCompanyId()));
		}	
		if(employeeVOSearchDialog.getEmployeeNo() !=null && StringUtils.isNotBlank(employeeVOSearchDialog.getEmployeeNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeNo"));
			builder.append(":"+employeeVOSearchDialog.getEmployeeNo()+",");
			searchCriteria.add(new SearchValueObject("employeeNo", employeeVOSearchDialog.getEmployeeNo()));
		}
		if(employeeVOSearchDialog.getFullName() !=null && StringUtils.isNotBlank(employeeVOSearchDialog.getFullName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeFullName"));
			builder.append(":"+employeeVOSearchDialog.getFullName()+",");
			searchCriteria.add(new SearchValueObject("fullName", employeeVOSearchDialog.getFullName()));
		}
		if(employeeVOSearchDialog.getGenderCode() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeGender"));
			builder.append(":"+parameterService.findByDetailId(employeeVOSearchDialog.getGenderCode()).getParameterDtlName()+",");
			searchCriteria.add(new SearchValueObject("gender", employeeVOSearchDialog.getGenderCode()));
		}		
		if(employeeVOSearchDialog.getStartDate() !=null && employeeVOSearchDialog.getEndDate() !=null){
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeBirthDate"));
			builder.append(":"+sdf.format(employeeVOSearchDialog.getStartDate()));
			builder.append(" s/d "+sdf.format(employeeVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("startDate", employeeVOSearchDialog.getStartDate()));
			searchCriteria.add(new SearchValueObject("endDate", employeeVOSearchDialog.getEndDate()));
		}else if(employeeVOSearchDialog.getStartDate() == null &&  employeeVOSearchDialog.getEndDate() != null) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeBirthDate"));
			builder.append(":-");
			builder.append(" s/d "+sdf.format(employeeVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("endDate", employeeVOSearchDialog.getEndDate()));
		}else if(employeeVOSearchDialog.getStartDate() != null &&  employeeVOSearchDialog.getEndDate() == null){
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeBirthDate"));
			builder.append(":"+sdf.format(employeeVOSearchDialog.getStartDate()));
			builder.append(" s/d -"+",");
			searchCriteria.add(new SearchValueObject("startDate", employeeVOSearchDialog.getStartDate()));
		}
		if(employeeVOSearchDialog.getBirthPlace() !=null && StringUtils.isNotBlank(employeeVOSearchDialog.getBirthPlace())) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeBirthPlace"));
			builder.append(":"+employeeVOSearchDialog.getBirthPlace()+",");
			searchCriteria.add(new SearchValueObject("birthPlace", employeeVOSearchDialog.getBirthPlace()));
		}
		if(employeeVOSearchDialog.getReligionCode() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeReligion"));
			builder.append(":"+parameterService.findByDetailId(employeeVOSearchDialog.getReligionCode()).getParameterDtlName()+",");
			searchCriteria.add(new SearchValueObject("religion", employeeVOSearchDialog.getReligionCode()));
		}
		if(employeeVOSearchDialog.getAddress() !=null && StringUtils.isNotBlank(employeeVOSearchDialog.getAddress())) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeAddress"));
			builder.append(":"+employeeVOSearchDialog.getAddress()+",");
			searchCriteria.add(new SearchValueObject("address", employeeVOSearchDialog.getAddress()));
		}
		if(employeeVOSearchDialog.getCityId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formEmployeeCity"));
			builder.append(":"+cityService.findById(employeeVOSearchDialog.getCityId()).getCityName()+",");
			searchCriteria.add(new SearchValueObject("city", employeeVOSearchDialog.getCityId()));
		}
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingEmployee.setSearchCriteria(searchCriteria);		
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
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

	public EmployeeVO getEmployeeVOSearchDialog() {
		return employeeVOSearchDialog;
	}

	public void setEmployeeVOSearchDialog(EmployeeVO employeeVOSearchDialog) {
		this.employeeVOSearchDialog = employeeVOSearchDialog;
	}

	public PagingTableModel<EmployeeVO> getPagingEmployee() {
		return pagingEmployee;
	}

	public void setPagingEmployee(PagingTableModel<EmployeeVO> pagingEmployee) {
		this.pagingEmployee = pagingEmployee;
	}

	public List<EmployeeVO> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeVO> employeeList) {
		this.employeeList = employeeList;
	}

	public List<EmployeeVO> getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(List<EmployeeVO> selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
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

	public List<SelectItem> getGenderSelectItem() {
		return genderSelectItem;
	}

	public void setGenderSelectItem(List<SelectItem> genderSelectItem) {
		this.genderSelectItem = genderSelectItem;
	}

	public List<SelectItem> getReligionSelectItem() {
		return religionSelectItem;
	}

	public void setReligionSelectItem(List<SelectItem> religionSelectItem) {
		this.religionSelectItem = religionSelectItem;
	}

	public List<SelectItem> getMaritalStatSelectItem() {
		return maritalStatSelectItem;
	}

	public void setMaritalStatSelectItem(List<SelectItem> maritalStatSelectItem) {
		this.maritalStatSelectItem = maritalStatSelectItem;
	}

	public List<SelectItem> getEmployeeStatSelectItem() {
		return employeeStatSelectItem;
	}

	public void setEmployeeStatSelectItem(List<SelectItem> employeeStatSelectItem) {
		this.employeeStatSelectItem = employeeStatSelectItem;
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

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	
	
		
}
