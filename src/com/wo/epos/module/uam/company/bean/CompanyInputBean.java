
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

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.util.FacesUtils;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.franchise.service.FranchiseService;
import com.wo.epos.module.master.franchise.vo.FranchiseVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "companyInputBean")
@ViewScoped
public class CompanyInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(CompanyInputBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
		
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{franchiseService}")
	private FranchiseService franchiseService;
	
	private CompanyVO companyVO = new CompanyVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companyTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> franchiseSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
    private boolean flagFranchise;
    
	@PostConstruct
	public void postConstruct(){
		super.init();
		companyVO = new CompanyVO();
				
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = companyService.searchCityAll();
	    citySelectItem.add(new SelectItem("", facesUtils.getResourceBundleStringValue("textChoose")));
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}
		
	    List<ParameterDtl> companySelectList = companyService.parameterDtlList(CommonConstants.COMPANY_TYPE);
	    companyTypeSelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : companySelectList){
	    	companyTypeSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlDesc()));
	    }
	    
	    List<FranchiseVO> franchiseVoList = franchiseService.searchFranchiseList();
	    franchiseSelectItem = new ArrayList<SelectItem>();
	    for(FranchiseVO franchiseVo : franchiseVoList){
	    	franchiseSelectItem.add(new SelectItem(franchiseVo.getFranchiseId(), franchiseVo.getFranchiseName()));
	    }
	    
	    
	       		
		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;		
		flagFranchise = false;
		
	}
	
	
	public boolean validateCompany(){
		boolean valid = true;
		
		try {
			if (companyVO.getCompanyCode().trim().isEmpty() || companyVO.getCompanyCode()== null && MODE_TYPE == "ADD" ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formCompanyCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else if (companyVO.getCompanyName().trim().isEmpty() || companyVO.getCompanyName() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formCompanyName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else if (companyVO.getCompanyTypeCode() == null || companyVO.getCompanyTypeCode().trim().isEmpty()  ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formCompanyType") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}else if ( companyVO.getFranchiseId() == null && flagFranchise == true) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formFranchiseTitle") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}else if ( companyVO.getPicName() == null || companyVO.getPicName() .trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formCompanyPicName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}else if ( companyVO.getPicPhoneno() == null || companyVO.getPicPhoneno().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formCompanyPicPhoneno") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}
			else if ( companyVO.getRegisterOn() == null ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formCompanyReqisterOn") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}else if ( companyVO.getAddress() == null || companyVO.getAddress().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formOutletAddress") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}else if ( companyVO.getCityId() == null ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesCompany",
						facesUtils.getResourceBundleStringValue("formCompanyCity") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}
			
		} catch (NumberFormatException ex) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesMenu",
					facesUtils.getResourceBundleStringValue("errorMustBeNumber"));
			valid = false;
		}
		
		
		return valid;

	}
	
	
	
	public void save(){
		try 
		{
			if(MODE_TYPE.equals("ADD"))
			{
			    companyService.save(companyVO, userSession.getUserCode());
			    
			    facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
	                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				companyService.update(companyVO, userSession.getUserCode());
				
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
	
	public void modeAdd(){
		companyVO = new CompanyVO();
	}
	
	
	public void modeEdit(List<CompanyVO> companyList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<companyList.size(); i++){
			  CompanyVO companyVOTemp = (CompanyVO)companyList.get(i);
			  companyVO.setCompanyId(companyVOTemp.getCompanyId());
			  companyVO.setCompanyCode(companyVOTemp.getCompanyCode());
			  companyVO.setCompanyName(companyVOTemp.getCompanyName());
			  companyVO.setCompanyTypeCode(companyVOTemp.getCompanyTypeCode());
			  
			  if(companyVO.getCompanyTypeCode().equals(CommonConstants.COMPANY_FRANCHISE)){
				  companyVO.setFranchiseId(companyVOTemp.getFranchiseId());
				  flagFranchise = true;
			  }else{
				  companyVO.setFranchiseId(null);
				  flagFranchise = false;
			  }
			  
			  companyVO.setCompanyTypeName(companyVOTemp.getCompanyTypeName());
			  companyVO.setPaymentFlag(companyVOTemp.getPaymentFlag());
			  companyVO.setAddress(companyVOTemp.getAddress());
			  companyVO.setCityId(companyVOTemp.getCityId());
			  companyVO.setCityName(companyVOTemp.getCityName());
			  companyVO.setProvinceName(companyVOTemp.getProvinceName());
			  companyVO.setPostalCode(companyVOTemp.getPostalCode());
			  companyVO.setPicName(companyVOTemp.getPicName());
			  companyVO.setPicPhoneno(companyVOTemp.getPicPhoneno());
			  companyVO.setRegisterOn(companyVOTemp.getRegisterOn());
			  companyVO.setOutletQty(companyVOTemp.getOutletQty());
			  companyVO.setActiveFlag(companyVOTemp.getActiveFlag());
		
		}
		
	}
	
	public void changeCityProvice(){
		if(companyVO.getCityId() !=null){
	        City cityModel = cityService.findById(companyVO.getCityId());
			companyVO.setProvinceName(cityModel.getProvince().getProvinceName());
		}else{
			companyVO.setProvinceName("");
		}
	}
	
	public void changeCompanyTypeFranchise(){
		if(companyVO.getCompanyTypeCode().equals(CommonConstants.COMPANY_FRANCHISE)){
			flagFranchise = true;
		}else{
		   flagFranchise = false;
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CompanyInputBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyVO getCompanyVO() {
		return companyVO;
	}

	public void setCompanyVO(CompanyVO companyVO) {
		this.companyVO = companyVO;
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

	public FacesUtils getFacesUtils() {
		return facesUtils;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
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

	public List<SelectItem> getCitySelectItem() {
		return citySelectItem;
	}

	public void setCitySelectItem(List<SelectItem> citySelectItem) {
		this.citySelectItem = citySelectItem;
	}

	public List<SelectItem> getCompanyTypeSelectItem() {
		return companyTypeSelectItem;
	}

	public void setCompanyTypeSelectItem(List<SelectItem> companyTypeSelectItem) {
		this.companyTypeSelectItem = companyTypeSelectItem;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public List<SelectItem> getFranchiseSelectItem() {
		return franchiseSelectItem;
	}

	public void setFranchiseSelectItem(List<SelectItem> franchiseSelectItem) {
		this.franchiseSelectItem = franchiseSelectItem;
	}

	public FranchiseService getFranchiseService() {
		return franchiseService;
	}

	public void setFranchiseService(FranchiseService franchiseService) {
		this.franchiseService = franchiseService;
	}

	public boolean isFlagFranchise() {
		return flagFranchise;
	}

	public void setFlagFranchise(boolean flagFranchise) {
		this.flagFranchise = flagFranchise;
	}
	
	
	
}
