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

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.model.Province;

@ManagedBean(name = "cityInputBean")
@ViewScoped
public class CityInputBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -706006746112437477L;
	static Logger logger = Logger.getLogger(CityInputBean.class);
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	private CityVO cityVO = new CityVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
	
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelect = new ArrayList<SelectItem>();
	private List<Province> provinceList = new ArrayList<Province>();
	
	private String MODE_TYPE;
	
	private boolean activeFlagCB;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		cityVO = new CityVO();
		
		activeSelectItem = new ArrayList<SelectItem>();
		//activeSelectItem.add(new SelectItem("", "Pilih"));
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		
		provinceList = cityService.showProvince();
		provinceSelect.add(new SelectItem("", "Pilih"));
		for(int i = 0; i < provinceList.size(); i++)
		{   
			provinceSelect.add(new SelectItem(provinceList.get(i).getProvinceId(), provinceList.get(i).getProvinceName()));
		}
		
				
		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;
		}
	}
	
	public void modeAdd(){
		cityVO = new CityVO();
	}
	
	
	public boolean validate() {
		boolean valid = true;

		if (cityVO.getCityCode().trim().equals("") || cityVO.getCityCode().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formCityCode") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		if (cityVO.getCityName().trim().equals("") || cityVO.getCityName().isEmpty()) {			
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formCityName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if (cityVO.getProvinceId()==null) {			
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formCityProvince") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		return valid;
	}
	
/*	private boolean validasi()
	{
		boolean valid = true;
		if(cityVO.getCityCode() == null ||
				cityVO.getCityCode().equals("")){
			facesUtils.addFacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"frm001:cityCode",
					"City Code harus disi");
			valid = false;
		}
		
		if(cityVO.getCityName() == null ||
				cityVO.getCityName().equals("")){
			facesUtils.addFacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"frm001:cityName",
					"City Name harus disi");
			valid = false;
		}
		
		
		return valid;
	}*/
	
	public void save(){
		try 
		{
			if(MODE_TYPE.equals("ADD"))
			{
			    cityService.save(cityVO, userSession.getUserCode());
			    
			    facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				cityService.update(cityVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
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
	
	public void modeEdit(List<CityVO> cityList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<cityList.size(); i++){
			  CityVO cityVOTemp = (CityVO)cityList.get(i);
			  cityVO.setCityId(cityVOTemp.getCityId());
			  cityVO.setProvinceId(cityVOTemp.getProvinceId());
			  cityVO.setCityCode(cityVOTemp.getCityCode());
			  cityVO.setCityName(cityVOTemp.getCityName());
			  cityVO.setActiveFlag(cityVOTemp.getActiveFlag());
			  /*
			  if(cityVO.getActiveFlag().equals("Y"))
				{
					activeFlagCB = true;
				}else{
					activeFlagCB = false;
				}
				*/
		}
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public CityVO getCityVO() {
		return cityVO;
	}

	public void setCityVO(CityVO cityVO) {
		this.cityVO = cityVO;
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

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
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

	public boolean isActiveFlagCB() {
		return activeFlagCB;
	}

	public void setActiveFlagCB(boolean activeFlagCB) {
		this.activeFlagCB = activeFlagCB;
	}
		

}
