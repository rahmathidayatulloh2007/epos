package com.wo.epos.module.master.franchise.bean;

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
import com.wo.epos.module.master.franchise.service.FranchiseService;
import com.wo.epos.module.master.franchise.vo.FranchiseVO;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "franchiseSearchBean")
@ViewScoped
public class FranchiseSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -8188275715592692045L;

	static Logger logger = Logger.getLogger(FranchiseSearchBean.class);
	
	@ManagedProperty(value = "#{franchiseService}")
	private FranchiseService franchiseService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	private FranchiseVO franchiseVOSearchDialog = new FranchiseVO();

	private PagingTableModel<FranchiseVO> pagingFranchise;	
	
	private List<FranchiseVO> franchiseList = new ArrayList<FranchiseVO>();	
	private List<FranchiseVO> selectedFranchise;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> genderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> religionSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> maritalStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> franchiseStatSelectItem = new ArrayList<SelectItem>();
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
		
		if(userSession != null){
		franchiseVOSearchDialog = new FranchiseVO();
		pagingFranchise = new PagingTableModel(franchiseService, paging);
		
		citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = franchiseService.searchCityAll();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}
		
		provinceSelectItem = new ArrayList<SelectItem>();		
		List<ProvinceVO> provinceSelectList = provinceService.provinceSearch();
		for(ProvinceVO vo : provinceSelectList){		
			provinceSelectItem.add(new SelectItem(vo.getProvinceId(), vo.getProvinceName()));
		}
		
		disableFlag = false;
		disableFlagAdd = true;	
		disableSearchAll = false;
		
		checkSearch = 0;
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
			
			 disableSearchAll = false;
	         pagingFranchise.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
	}
	
	public void modeDelete(List<FranchiseVO> franchiseVOList){
		try
		{
			for(int i=0; i<franchiseVOList.size(); i++)
			{
				FranchiseVO franchiseVoTemp = (FranchiseVO)franchiseVOList.get(i);
				
				franchiseService.delete(franchiseVoTemp.getFranchiseId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formFranchiseTitle")),
					null);	  
		}	
	}
	
	public void clear(){
		searchAll = "";
		franchiseVOSearchDialog = new FranchiseVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		franchiseVOSearchDialog = new FranchiseVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(franchiseVOSearchDialog.getFranchiseCode() !=null && StringUtils.isNotBlank(franchiseVOSearchDialog.getFranchiseCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formFranchiseCode"));
			builder.append(":"+franchiseVOSearchDialog.getFranchiseCode()+",");
			searchCriteria.add(new SearchValueObject("franchiseCode", franchiseVOSearchDialog.getFranchiseCode()));
		}	
		if(franchiseVOSearchDialog.getFranchiseName() !=null && StringUtils.isNotBlank(franchiseVOSearchDialog.getFranchiseName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formFranchiseName"));
			builder.append(":"+franchiseVOSearchDialog.getFranchiseName()+",");
			searchCriteria.add(new SearchValueObject("franchiseName", franchiseVOSearchDialog.getFranchiseName()));
		}
		if(franchiseVOSearchDialog.getAddress() !=null && StringUtils.isNotBlank(franchiseVOSearchDialog.getAddress())) {
			builder.append(facesUtils.getResourceBundleStringValue("formFranchiseAddress"));
			builder.append(":"+franchiseVOSearchDialog.getAddress()+",");
			searchCriteria.add(new SearchValueObject("address", franchiseVOSearchDialog.getAddress()));
		}
		if(franchiseVOSearchDialog.getCityId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formFranchiseCity"));
			builder.append(":"+franchiseVOSearchDialog.getCityId()+",");
			searchCriteria.add(new SearchValueObject("cityId", franchiseVOSearchDialog.getCityId()));
		}	
		if(franchiseVOSearchDialog.getProvinceId()!=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formFranchiseProvince"));
			builder.append(":"+franchiseVOSearchDialog.getProvinceId()+",");
			searchCriteria.add(new SearchValueObject("provinceId", franchiseVOSearchDialog.getProvinceId()));
		}	
		if(franchiseVOSearchDialog.getPicName() !=null && StringUtils.isNotBlank(franchiseVOSearchDialog.getPicName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formFranchisePICName"));
			builder.append(":"+franchiseVOSearchDialog.getPicName()+",");
			searchCriteria.add(new SearchValueObject("picName", franchiseVOSearchDialog.getPicName()));
		}
		if(franchiseVOSearchDialog.getPicPhoneNo() !=null && StringUtils.isNotBlank(franchiseVOSearchDialog.getPicPhoneNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formFranchisePICPhoneNo"));
			builder.append(":"+franchiseVOSearchDialog.getPicPhoneNo()+",");
			searchCriteria.add(new SearchValueObject("picPhoneNo", franchiseVOSearchDialog.getPicPhoneNo()));
		}
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingFranchise.setSearchCriteria(searchCriteria);		
	}

	public FranchiseService getFranchiseService() {
		return franchiseService;
	}

	public void setFranchiseService(FranchiseService franchiseService) {
		this.franchiseService = franchiseService;
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

	public FranchiseVO getFranchiseVOSearchDialog() {
		return franchiseVOSearchDialog;
	}

	public void setFranchiseVOSearchDialog(FranchiseVO franchiseVOSearchDialog) {
		this.franchiseVOSearchDialog = franchiseVOSearchDialog;
	}

	public PagingTableModel<FranchiseVO> getPagingFranchise() {
		return pagingFranchise;
	}

	public void setPagingFranchise(PagingTableModel<FranchiseVO> pagingFranchise) {
		this.pagingFranchise = pagingFranchise;
	}

	public List<FranchiseVO> getFranchiseList() {
		return franchiseList;
	}

	public void setFranchiseList(List<FranchiseVO> franchiseList) {
		this.franchiseList = franchiseList;
	}

	public List<FranchiseVO> getSelectedFranchise() {
		return selectedFranchise;
	}

	public void setSelectedFranchise(List<FranchiseVO> selectedFranchise) {
		this.selectedFranchise = selectedFranchise;
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

	public List<SelectItem> getFranchiseStatSelectItem() {
		return franchiseStatSelectItem;
	}

	public void setFranchiseStatSelectItem(List<SelectItem> franchiseStatSelectItem) {
		this.franchiseStatSelectItem = franchiseStatSelectItem;
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

	public List<SelectItem> getProvinceSelectItem() {
		return provinceSelectItem;
	}

	public void setProvinceSelectItem(List<SelectItem> provinceSelectItem) {
		this.provinceSelectItem = provinceSelectItem;
	}
	
	
		
}
