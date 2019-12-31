package com.wo.epos.module.inv.vehicle.bean;

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
import com.wo.epos.module.inv.vehicle.service.VehicleService;
import com.wo.epos.module.inv.vehicle.vo.VehicleVO;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.province.service.ProvinceService;

import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;


@ManagedBean(name = "vehicleSearchBean")
@ViewScoped
public class VehicleSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -8188275715592692045L;

	static Logger logger = Logger.getLogger(VehicleSearchBean.class);
	
	@ManagedProperty(value = "#{vehicleService}")
	private VehicleService vehicleService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	
	
	private VehicleVO vehicleVOSearchDialog = new VehicleVO();

	private PagingTableModel<VehicleVO> pagingVehicle;	
	
	private List<VehicleVO> vehicleList = new ArrayList<VehicleVO>();	
	private List<VehicleVO> selectedVehicle;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> genderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> religionSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> maritalStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> vehicleStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> vehicleTypeSelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;

	private String userPosition;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		
		vehicleVOSearchDialog = new VehicleVO();
		
		companySelectItem = new ArrayList<SelectItem>();		
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
		List<ParameterDtl> genderSelectList = parameterService.parameterDtlList("VEHICLE_TYPE");
		vehicleTypeSelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : genderSelectList){
	    	vehicleTypeSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
	    }
	    
		pagingVehicle = new PagingTableModel(vehicleService, paging);
		
		userPosition = getUserLevel();
		
		if(userPosition.equals(CommonConstants.ADMIN_LEVEL) || userPosition.equals(CommonConstants.COMPANY_LEVEL)){
			List<Outlet> outletList = outletService.findOutletByCompany(userSession.getCompanyId());
		    outletSelectItem = new ArrayList<SelectItem>();
		    for(Outlet dtl : outletList){
		    	outletSelectItem.add(new SelectItem(dtl.getOutletId(), dtl.getOutletName()));
		    }
		}
		
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
			if(userPosition.equals(CommonConstants.OUTLET_LEVEL)){
				searchCriteria.add(new SearchValueObject("outlet", userSession.getOutletId()));
			}else if(userPosition.equals(CommonConstants.COMPANY_LEVEL)){
				searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
			}
			
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}		
			
			 disableSearchAll = false;
	         pagingVehicle.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
	}
	
	public void modeDelete(List<VehicleVO> vehicleVOList){
		try
		{
			for(int i=0; i<vehicleVOList.size(); i++)
			{
				VehicleVO vehicleVoTemp = (VehicleVO)vehicleVOList.get(i);
				
				vehicleService.delete(vehicleVoTemp.getVehicleId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formPaymentTypeTitle")),
					null);	  
		}
	}
	
	public void clear(){
		searchAll = "";
		vehicleVOSearchDialog = new VehicleVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		vehicleVOSearchDialog = new VehicleVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(userPosition.equals(CommonConstants.OUTLET_LEVEL)){
			searchCriteria.add(new SearchValueObject("outlet", userSession.getOutletId()));
			searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
		}else if(userPosition.equals(CommonConstants.COMPANY_LEVEL)){
			searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
			
			if(vehicleVOSearchDialog.getOutletId() !=null) {
				builder.append(facesUtils.getResourceBundleStringValue("formEquipmentOutlet"));
				builder.append(":"+vehicleVOSearchDialog.getOutletId()+",");
				searchCriteria.add(new SearchValueObject("outlet", vehicleVOSearchDialog.getOutletId()));
			}	
		}else if(userPosition.equals(CommonConstants.ADMIN_LEVEL)){
			if(vehicleVOSearchDialog.getCompanyId() !=null) {
				builder.append(facesUtils.getResourceBundleStringValue("formEquipmentCompany"));
				builder.append(":"+vehicleVOSearchDialog.getCompanyId()+",");
				searchCriteria.add(new SearchValueObject("companyId", vehicleVOSearchDialog.getCompanyId()));
			}	
			
			if(vehicleVOSearchDialog.getOutletId() !=null) {
				builder.append(facesUtils.getResourceBundleStringValue("formEquipmentOutlet"));
				builder.append(":"+vehicleVOSearchDialog.getOutletId()+",");
				searchCriteria.add(new SearchValueObject("outlet", vehicleVOSearchDialog.getOutletId()));
			}	
		}
		
		if(vehicleVOSearchDialog.getPoliceNo() !=null && StringUtils.isNotBlank(vehicleVOSearchDialog.getPoliceNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formVehiclePoliceNo"));
			builder.append(":"+vehicleVOSearchDialog.getPoliceNo()+",");
			searchCriteria.add(new SearchValueObject("policeNo", vehicleVOSearchDialog.getPoliceNo()));
		}	
		if(vehicleVOSearchDialog.getVehicleType() !=null && StringUtils.isNotBlank(vehicleVOSearchDialog.getVehicleType())) {
			builder.append(facesUtils.getResourceBundleStringValue("formVehicleType"));
			builder.append(":"+vehicleVOSearchDialog.getVehicleType()+",");
			searchCriteria.add(new SearchValueObject("vehicleType", vehicleVOSearchDialog.getVehicleType()));
		}
		if(vehicleVOSearchDialog.getVehicleDesc() !=null && StringUtils.isNotBlank(vehicleVOSearchDialog.getVehicleDesc())) {
			builder.append(facesUtils.getResourceBundleStringValue("formVehicleDesc"));
			builder.append(":"+vehicleVOSearchDialog.getVehicleDesc()+",");
			searchCriteria.add(new SearchValueObject("vehicleDesc", vehicleVOSearchDialog.getVehicleDesc()));
		}	
		/*if(vehicleVOSearchDialog.getOccupyFlag() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formVehicleOutlet"));
			builder.append(":"+vehicleVOSearchDialog.getOutletId()+",");
			searchCriteria.add(new SearchValueObject("outletId", vehicleVOSearchDialog.getOutletId()));
		}	*/
		
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingVehicle.setSearchCriteria(searchCriteria);		
	}

	public void changeCompanyToOutlet(){
		if(vehicleVOSearchDialog.getCompanyId() !=null){
	        List<Outlet> outletList = outletService.findOutletByCompany(vehicleVOSearchDialog.getCompanyId());
	        outletSelectItem = new ArrayList<SelectItem>();
	        if(outletList.size() > 0){
	        	for(Outlet out : outletList){
	        		  outletSelectItem.add(new SelectItem(out.getOutletId(), out.getOutletName()));
	        	}
	        }
			
		}
	}
	
	public VehicleService getVehicleService() {
		return vehicleService;
	}

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
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

	public VehicleVO getVehicleVOSearchDialog() {
		return vehicleVOSearchDialog;
	}

	public void setVehicleVOSearchDialog(VehicleVO vehicleVOSearchDialog) {
		this.vehicleVOSearchDialog = vehicleVOSearchDialog;
	}

	public PagingTableModel<VehicleVO> getPagingVehicle() {
		return pagingVehicle;
	}

	public void setPagingVehicle(PagingTableModel<VehicleVO> pagingVehicle) {
		this.pagingVehicle = pagingVehicle;
	}

	public List<VehicleVO> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<VehicleVO> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public List<VehicleVO> getSelectedVehicle() {
		return selectedVehicle;
	}

	public void setSelectedVehicle(List<VehicleVO> selectedVehicle) {
		this.selectedVehicle = selectedVehicle;
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

	public List<SelectItem> getVehicleStatSelectItem() {
		return vehicleStatSelectItem;
	}

	public void setVehicleStatSelectItem(List<SelectItem> vehicleStatSelectItem) {
		this.vehicleStatSelectItem = vehicleStatSelectItem;
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

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}

	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}

	public List<SelectItem> getVehicleTypeSelectItem() {
		return vehicleTypeSelectItem;
	}

	public void setVehicleTypeSelectItem(List<SelectItem> vehicleTypeSelectItem) {
		this.vehicleTypeSelectItem = vehicleTypeSelectItem;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}
		
}
