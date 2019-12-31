package com.wo.epos.module.master.city.bean;

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
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.master.province.service.ProvinceService;

@ManagedBean(name = "citySearchBean")
@ViewScoped
public class CitySearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -3813245425630523844L;

	static Logger logger = Logger.getLogger("CitySearchBean");
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	private CityVO cityVOSearchDialog = new CityVO();
	
	private PagingTableModel<CityVO> pagingCity;
		
	private List<CityVO> selectedCities;
	private List<SelectItem> provinceSelect = new ArrayList<SelectItem>();
	private List<Province> provinceList = new ArrayList<Province>();
	
	private boolean disableSearchAll;
	private boolean selectAll;
	
	private String searchAll;
	
	private Integer checkSearch;
	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		cityVOSearchDialog = new CityVO();
		
		pagingCity = new PagingTableModel(cityService, paging);
		
		provinceList = cityService.showProvince();
		provinceSelect.add(new SelectItem("", "Pilih"));
		for(int i = 0; i < provinceList.size(); i++){   
			provinceSelect.add(new SelectItem(provinceList.get(i).getProvinceId(), provinceList.get(i).getProvinceName()));
		}
		
		disableSearchAll = false;			
		checkSearch=0;
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
			 pagingCity.setSearchCriteria(searchCriteria);
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
	   		
		if(cityVOSearchDialog.getProvinceId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCityProvince"));
			builder.append(":"+provinceService.findById(cityVOSearchDialog.getProvinceId()).getProvinceName()+",");
			searchCriteria.add(new SearchValueObject("provinceId", cityVOSearchDialog.getProvinceId()));
		}
		
		if(cityVOSearchDialog.getCityCode() !=null && StringUtils.isNotBlank(cityVOSearchDialog.getCityCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCityCode"));
			builder.append(":"+cityVOSearchDialog.getCityCode()+",");
			searchCriteria.add(new SearchValueObject("cityCode", cityVOSearchDialog.getCityCode()));
		}	
		
		if(cityVOSearchDialog.getCityName() !=null && StringUtils.isNotBlank(cityVOSearchDialog.getCityName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCityName"));
			builder.append(":"+cityVOSearchDialog.getCityName()+",");
			searchCriteria.add(new SearchValueObject("cityName", cityVOSearchDialog.getCityName()));
		}	
				
		if(builder.toString() !=null && !builder.toString().equals("")){
		    builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		
		searchAll = builder.toString();
		
		pagingCity.setSearchCriteria(searchCriteria);
		
	}
	
	public void clear(){
		searchAll = "";
		cityVOSearchDialog = new CityVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		cityVOSearchDialog = new CityVO();	
		search();
	}
	
	public void modeDelete(List<CityVO> cityVOList){
		try
		{
			for(int i = 0; i < cityVOList.size(); i++)
			{
				CityVO cityVOTemp = (CityVO)cityVOList.get(i);
				
				cityService.delete(cityVOTemp.getCityId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formCityTitle")),
					null);	  
		}
	}
	
	
	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	public PagingTableModel<CityVO> getPagingCity() {
		return pagingCity;
	}

	public void setPagingCity(PagingTableModel<CityVO> pagingCity) {
		this.pagingCity = pagingCity;
	}

	public List<CityVO> getSelectedCities() {
		return selectedCities;
	}

	public void setSelectedCities(List<CityVO> selectedCities) {
		this.selectedCities = selectedCities;
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
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CitySearchBean.logger = logger;
	}

	public CityVO getCityVOSearchDialog() {
		return cityVOSearchDialog;
	}

	public void setCityVOSearchDialog(CityVO cityVOSearchDialog) {
		this.cityVOSearchDialog = cityVOSearchDialog;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}

	public List<SelectItem> getProvinceSelect() {
		return provinceSelect;
	}

	public void setProvinceSelect(List<SelectItem> provinceSelect) {
		this.provinceSelect = provinceSelect;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}

	public ProvinceService getProvinceService() {
		return provinceService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	
	
}
