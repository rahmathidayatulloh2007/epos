package com.wo.epos.module.uam.company.bean;

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
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "companySearchBean")
@ViewScoped
public class CompanySearchBean extends CommonBean implements Serializable{
	
	
	private static final long serialVersionUID = 3322854440400528379L;
	static Logger logger = Logger.getLogger(CompanySearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
		
	private CompanyVO companyVOSearchDialog = new CompanyVO();	

	private PagingTableModel<CompanyVO> pagingCompany;	
	
	private List<CompanyVO> companyList = new ArrayList<CompanyVO>();	
	private List<CompanyVO> selectedCompany;
	
	private List<SelectItem> companyTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		companyVOSearchDialog = new CompanyVO();
		pagingCompany = new PagingTableModel(companyService, paging);
		companyList = companyService.searchCompanyList();
		
		citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = companyService.searchCityAll();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}
		
	    List<ParameterDtl> companySelectList = companyService.parameterDtlList(CommonConstants.COMPANY_TYPE);
	    companyTypeSelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : companySelectList){
	    	companyTypeSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
	    }
	    
	    List<ProvinceVO> provinceSelectList = provinceService.provinceSearch();
	    provinceSelectItem = new ArrayList<SelectItem>();
	    for(ProvinceVO vo : provinceSelectList){
	    	provinceSelectItem.add(new SelectItem(vo.getProvinceId(), vo.getProvinceName()));
	    }
	    		
		disableFlag = false;
		disableFlagAdd = true;	
		disableSearchAll = false;
		
		checkSearch = 0;
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
			
			 disableSearchAll = false;
	         pagingCompany.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 

	public void modeDelete(List<CompanyVO> companyVOList){
		try
		{
			for(int i=0; i<companyVOList.size(); i++){
				CompanyVO companyVoTemp = (CompanyVO)companyVOList.get(i);
				
				companyService.delete(companyVoTemp.getCompanyId());
			}
			
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
                null);
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_ERROR,
				"frm001:messages",
				facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formCompanyTitle")),
				null);
		}
	}
	
	public void clear(){
		searchAll = "";
		companyVOSearchDialog = new CompanyVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		companyVOSearchDialog = new CompanyVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(companyVOSearchDialog.getCompanyName() !=null && StringUtils.isNotBlank(companyVOSearchDialog.getCompanyName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyName"));
			builder.append(":"+companyVOSearchDialog.getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyName", companyVOSearchDialog.getCompanyName()));
		}			
		if(companyVOSearchDialog.getCompanyTypeCode() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyType"));
			builder.append(":"+parameterService.findByDetailId(companyVOSearchDialog.getCompanyTypeCode()).getParameterDtlName()+",");
			searchCriteria.add(new SearchValueObject("companyType", companyVOSearchDialog.getCompanyTypeCode()));
		}
		if(companyVOSearchDialog.getAddress() !=null && StringUtils.isNotBlank(companyVOSearchDialog.getAddress())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyAddress"));
			builder.append(":"+companyVOSearchDialog.getAddress()+",");
			searchCriteria.add(new SearchValueObject("address", companyVOSearchDialog.getAddress()));
		}
		if(companyVOSearchDialog.getProvinceId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyProvince"));
			builder.append(":"+provinceService.findById(companyVOSearchDialog.getProvinceId()).getProvinceName()+",");
			searchCriteria.add(new SearchValueObject("province", companyVOSearchDialog.getProvinceId()));
		}
		if(companyVOSearchDialog.getCityId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyCity"));
			builder.append(":"+cityService.findById(companyVOSearchDialog.getCityId()).getCityName()+",");
			searchCriteria.add(new SearchValueObject("city", companyVOSearchDialog.getCityId()));
		}
		if(companyVOSearchDialog.getPicName() !=null && StringUtils.isNotBlank(companyVOSearchDialog.getPicName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyPicName"));
			builder.append(":"+companyVOSearchDialog.getPicName()+",");
			searchCriteria.add(new SearchValueObject("picName", companyVOSearchDialog.getPicName()));
		}
		if(companyVOSearchDialog.getPicPhoneno() !=null && StringUtils.isNotBlank(companyVOSearchDialog.getPicPhoneno())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyPicPhoneno"));
			builder.append(":"+companyVOSearchDialog.getPicPhoneno()+",");
			searchCriteria.add(new SearchValueObject("picPhone", companyVOSearchDialog.getPicPhoneno()));
		}
		if(companyVOSearchDialog.getStartDate() !=null && companyVOSearchDialog.getEndDate() !=null){
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":"+sdf.format(companyVOSearchDialog.getStartDate()));
			builder.append(" s/d "+sdf.format(companyVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("startDate", companyVOSearchDialog.getStartDate()));
			searchCriteria.add(new SearchValueObject("endDate", companyVOSearchDialog.getEndDate()));
		}else if(companyVOSearchDialog.getStartDate() == null &&  companyVOSearchDialog.getEndDate() != null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":-");
			builder.append(" s/d "+sdf.format(companyVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("endDate", companyVOSearchDialog.getEndDate()));
		}else if(companyVOSearchDialog.getStartDate() != null &&  companyVOSearchDialog.getEndDate() == null){
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":"+sdf.format(companyVOSearchDialog.getStartDate()));
			builder.append(" s/d -"+",");
		}		
		if(companyVOSearchDialog.getOutletQty() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyQuantityOutlet"));
			builder.append(":"+companyVOSearchDialog.getOutletQty()+",");
			searchCriteria.add(new SearchValueObject("quantityOutlet", companyVOSearchDialog.getOutletQty()));
		}
		
		if(!builder.toString().equals("")){
		     builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingCompany.setSearchCriteria(searchCriteria);
		
	} 
		
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CompanySearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyVO getCompanyVOSearchDialog() {
		return companyVOSearchDialog;
	}

	public void setCompanyVOSearchDialog(CompanyVO companyVOSearchDialog) {
		this.companyVOSearchDialog = companyVOSearchDialog;
	}

	public PagingTableModel<CompanyVO> getPagingCompany() {
		return pagingCompany;
	}

	public void setPagingCompany(PagingTableModel<CompanyVO> pagingCompany) {
		this.pagingCompany = pagingCompany;
	}

	public List<CompanyVO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyVO> companyList) {
		this.companyList = companyList;
	}

	public List<CompanyVO> getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(List<CompanyVO> selectedCompany) {
		this.selectedCompany = selectedCompany;
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

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public List<SelectItem> getCompanyTypeSelectItem() {
		return companyTypeSelectItem;
	}

	public void setCompanyTypeSelectItem(List<SelectItem> companyTypeSelectItem) {
		this.companyTypeSelectItem = companyTypeSelectItem;
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

	public ProvinceService getProvinceService() {
		return provinceService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
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

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}
	
	
	
	
}
