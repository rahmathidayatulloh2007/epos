package com.wo.epos.module.uam.outlet.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;

@ManagedBean(name = "outletEmpBean")
@ViewScoped
public class OutletEmpBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 4740244802528600L;
	
	static Logger logger = Logger.getLogger(OutletEmpBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
		
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{outletEmpService}")
	private OutletEmpService outletEmpService;
	
	private OutletVO outletVO = new OutletVO();
	
	private List<OutletEmpVO> listEmployee;
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
	
	private String companyName;
	
	private String cityName;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	
	private String MODE_TYPE;
	
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		outletVO = new OutletVO();
		System.out.println("userSession="+userSession);
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
		
		disableFlag = false;
		disableFlagAdd = true;
	}
	
	
	
	public void modeView(List<OutletVO> outletList){
		MODE_TYPE = "VIEW";
		for(int i=0; i<outletList.size(); i++){
			  OutletVO companyVOTemp = (OutletVO)outletList.get(i);
			  
			  outletVO.setOutletId(companyVOTemp.getOutletId());
			  outletVO.setOutletCode(companyVOTemp.getOutletCode());
			  outletVO.setOutletName(companyVOTemp.getOutletName());
			  outletVO.setCompanyId(companyVOTemp.getCompanyId());
			  Company company = companyService.findById(companyVOTemp.getCompanyId());
			  companyName = company.getCompanyName();
			  outletVO.setCityId(companyVOTemp.getCityId());
			  City city = cityService.findById(companyVOTemp.getCityId());
			  cityName = city.getCityName();
			  outletVO.setProvinceName(companyVOTemp.getProvinceName());
			  outletVO.setAddress(companyVOTemp.getAddress());
			  outletVO.setPostalCode(companyVOTemp.getPostalCode());
			  //outletVO.setPicName(companyVOTemp.getPicName());
			  //outletVO.setPicPhoneno(companyVOTemp.getPicPhoneno());
			  outletVO.setActiveFlag(companyVOTemp.getActiveFlag());
			  outletVO.setCreateBy(userSession.getUserCode());
			  outletVO.setCreateOn(new Timestamp(System.currentTimeMillis()));	
			  
			  listEmployee = outletEmpService.findOutletEmpByOutletIdList(outletVO.getOutletId());
			  
		}
		
	}
	
	public void changeCityProvince(){
		City city = new City();
		city = cityService.findById(outletVO.getCityId());
		outletVO.setProvinceName(city.getProvince().getProvinceName());
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}
	

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public OutletVO getOutletVO() {
		return outletVO;
	}

	public void setOutletVO(OutletVO outletVO) {
		this.outletVO = outletVO;
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



	public List<OutletEmpVO> getListEmployee() {
		return listEmployee;
	}



	public void setListEmployee(List<OutletEmpVO> listEmployee) {
		this.listEmployee = listEmployee;
	}



	public OutletEmpService getOutletEmpService() {
		return outletEmpService;
	}



	public void setOutletEmpService(OutletEmpService outletEmpService) {
		this.outletEmpService = outletEmpService;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getCityName() {
		return cityName;
	}



	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



	public CompanyService getCompanyService() {
		return companyService;
	}



	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	
	
}
