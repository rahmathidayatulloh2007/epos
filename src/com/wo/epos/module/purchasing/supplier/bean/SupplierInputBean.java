package com.wo.epos.module.purchasing.supplier.bean;

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
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.purchasing.supplier.service.SupplierService;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "supplierInputBean")
@ViewScoped
public class SupplierInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -5088505975571538203L;

	static Logger logger = Logger.getLogger(SupplierInputBean.class);
	
	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
		
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	private SupplierVO supplierVO = new SupplierVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean adminMode;

	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> picTitleSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
    @PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		supplierVO = new SupplierVO();
				
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = supplierService.searchCityAll();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}
		
		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = supplierService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
	   
	    List<ParameterDtl> picTitleSelectList = supplierService.parameterDtlList("PIC_TITLE");
	    picTitleSelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : picTitleSelectList){
	    	picTitleSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
	    }
	       		
		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;		
		
		List<CompanyVO> companyUserList = supplierService.searchCompanyList();
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
		
		}
	}
    
    public boolean validate() {
		boolean valid = true;
		
		if (supplierVO.getCompanyId() == null){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierCompany") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getSupplierCode().trim().equals("")||supplierVO.getSupplierCode().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierCode") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getSupplierName().trim().equals("")||supplierVO.getSupplierName().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getPicTitleCode() == null){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierPicTitle") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getSupplierName().trim().equals("")||supplierVO.getSupplierName().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}

		if (supplierVO.getPicName().trim().equals("")||supplierVO.getPicName().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierPicName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getPhoneNo().trim().equals("")||supplierVO.getPhoneNo().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierPhoneNo") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getFaxNo().trim().equals("")||supplierVO.getFaxNo().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierFaxNo") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getAddress().trim().equals("")||supplierVO.getAddress().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierAddress") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getCityId() == null){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierCity") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		if (supplierVO.getActiveFlag().trim().equals("")||supplierVO.getActiveFlag().isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierActive") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		return valid;

	}

    
    public void save(){
    	try
		{
	    	if(userSession.getCompanyId() !=null)
	    	{
	    		supplierVO.setCompanyId(userSession.getCompanyId());
	    	}
			
	    	if(MODE_TYPE.equals("ADD"))
	    	{
			    supplierService.save(supplierVO);
			    
			    facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
	                null);
			}
	    	else if(MODE_TYPE.equals("EDIT"))
	    	{
				supplierService.update(supplierVO);
				
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
		supplierVO = new SupplierVO();
	}
	
	public void modeEdit(List<SupplierVO> supplierList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<supplierList.size(); i++){
			SupplierVO supplierVOTemp = (SupplierVO)supplierList.get(i);
			
			supplierVO.setSupplierId(supplierVOTemp.getSupplierId());
			supplierVO.setApBalance(supplierVOTemp.getApBalance());
			supplierVO.setSupplierCode(supplierVOTemp.getSupplierCode());
			supplierVO.setSupplierName(supplierVOTemp.getSupplierName());
			supplierVO.setAddress(supplierVOTemp.getAddress());
			supplierVO.setPostalCode(supplierVOTemp.getPostalCode());
			supplierVO.setPhoneNo(supplierVOTemp.getPhoneNo());
			supplierVO.setFaxNo(supplierVOTemp.getFaxNo());
			supplierVO.setPicName(supplierVOTemp.getPicName());
			supplierVO.setCompanyId(supplierVOTemp.getCompanyId());
			supplierVO.setCompanyName(supplierVOTemp.getCompanyName());
			supplierVO.setProvinceName(supplierVOTemp.getProvinceName());
			supplierVO.setCityId(supplierVOTemp.getCityId());
			supplierVO.setCityName(supplierVOTemp.getCityName());
			supplierVO.setPicTitleCode(supplierVOTemp.getPicTitleCode());
			supplierVO.setPicTitleName(supplierVOTemp.getPicTitleCode());
			supplierVO.setActiveFlag(supplierVOTemp.getActiveFlag());
			supplierVO.setUpdateBy("Admin");
			supplierVO.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			
		}
	}
	
	public void changeCityProvince(){
		City city = new City();
		city = cityService.findById(supplierVO.getCityId());
		supplierVO.setProvinceName(city.getProvince().getProvinceName());
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
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

	public SupplierVO getSupplierVO() {
		return supplierVO;
	}

	public void setSupplierVO(SupplierVO supplierVO) {
		this.supplierVO = supplierVO;
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

	public List<SelectItem> getPicTitleSelectItem() {
		return picTitleSelectItem;
	}

	public void setPicTitleSelectItem(List<SelectItem> picTitleSelectItem) {
		this.picTitleSelectItem = picTitleSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

}
