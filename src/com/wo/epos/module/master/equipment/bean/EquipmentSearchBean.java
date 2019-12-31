package com.wo.epos.module.master.equipment.bean;

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
import com.wo.epos.module.master.equipment.service.EquipmentService;
import com.wo.epos.module.master.equipment.vo.EquipmentVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "equipmentSearchBean")
@ViewScoped
public class EquipmentSearchBean extends CommonBean implements Serializable{	
	
	private static final long serialVersionUID = -5923531115141284089L;
	static Logger logger = Logger.getLogger(EquipmentSearchBean.class);
	
	@ManagedProperty(value = "#{equipmentService}")
	private EquipmentService equipmentService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;	
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;	
		
	private EquipmentVO equipmentVOSearchDialog = new EquipmentVO();	

	private PagingTableModel<EquipmentVO> pagingEquipment;	
	
	private List<EquipmentVO> selectedEquipments;
	
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> equipmentTypeSelectItem = new ArrayList<SelectItem>();
	
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;
	
	private String userPosition;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		equipmentVOSearchDialog = new EquipmentVO();
		pagingEquipment = new PagingTableModel(equipmentService, paging);
				
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		companySelectItem = new ArrayList<SelectItem>();		
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
	    List<ParameterDtl> paramSelectList = parameterService.parameterDtlList(CommonConstants.EQUIPMENT_TYPE);
	    equipmentTypeSelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : paramSelectList){
	    	equipmentTypeSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlDesc()));
	    }
	    
	    userPosition = getUserLevel();
	    
	    if(userPosition.equals(CommonConstants.ADMIN_LEVEL) || userPosition.equals(CommonConstants.COMPANY_LEVEL)){
			List<Outlet> outletList = outletService.findOutletByCompany(userSession.getCompanyId());
		    outletSelectItem = new ArrayList<SelectItem>();
		    for(Outlet dtl : outletList){
		    	outletSelectItem.add(new SelectItem(dtl.getOutletId(), dtl.getOutletName()));
		    }
		}
	    		
		disableSearchAll = false;
		
		checkSearch = 0;
		
		search();
		}
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
	         pagingEquipment.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 
	
	public void changeCompanyToOutlet(){
		if(equipmentVOSearchDialog.getCompanyId() !=null){
	        List<Outlet> outletList = outletService.findOutletByCompany(equipmentVOSearchDialog.getCompanyId());
	        outletSelectItem = new ArrayList<SelectItem>();
	        if(outletList.size() > 0){
	        	for(Outlet out : outletList){
	        		  outletSelectItem.add(new SelectItem(out.getOutletId(), out.getOutletName()));
	        	}
	        }
			
		}
	}

	public void modeDelete(List<EquipmentVO> equipmentVOList){
		try
		{
			for(int i=0; i<equipmentVOList.size(); i++){
				EquipmentVO equipVoTemp = (EquipmentVO)equipmentVOList.get(i);
				
				equipmentService.delete(equipVoTemp.getEquipmentId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formEquipmentTitle")),
					null);	  
		}	
	}
	
	public void clear(){
		searchAll = "";
		equipmentVOSearchDialog = new EquipmentVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		equipmentVOSearchDialog = new EquipmentVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		/*if(userSession.getOutletId() !=null){
			searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));
		}*/
		
		StringBuilder builder = new StringBuilder();
	   
		if(userPosition.equals(CommonConstants.OUTLET_LEVEL)){
			searchCriteria.add(new SearchValueObject("outlet", userSession.getOutletId()));
			searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
		}else if(userPosition.equals(CommonConstants.COMPANY_LEVEL)){
			searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
			
			if(equipmentVOSearchDialog.getOutletId() !=null) {
				builder.append(facesUtils.getResourceBundleStringValue("formEquipmentOutlet"));
				builder.append(":"+equipmentVOSearchDialog.getOutletId()+",");
				searchCriteria.add(new SearchValueObject("outlet", equipmentVOSearchDialog.getOutletId()));
			}	
		}else if(userPosition.equals(CommonConstants.ADMIN_LEVEL)){
			if(equipmentVOSearchDialog.getCompanyId() !=null) {
				builder.append(facesUtils.getResourceBundleStringValue("formEquipmentCompany"));
				builder.append(":"+equipmentVOSearchDialog.getCompanyId()+",");
				searchCriteria.add(new SearchValueObject("companyId", equipmentVOSearchDialog.getCompanyId()));
			}	
			
			if(equipmentVOSearchDialog.getOutletId() !=null) {
				builder.append(facesUtils.getResourceBundleStringValue("formEquipmentOutlet"));
				builder.append(":"+equipmentVOSearchDialog.getOutletId()+",");
				searchCriteria.add(new SearchValueObject("outlet", equipmentVOSearchDialog.getOutletId()));
			}	
		}
		
		if(equipmentVOSearchDialog.getEquipmentCode() !=null && StringUtils.isNotBlank(equipmentVOSearchDialog.getEquipmentCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formEquipmentCode"));
			builder.append(":"+equipmentVOSearchDialog.getEquipmentCode()+",");
			searchCriteria.add(new SearchValueObject("equipmentCode", equipmentVOSearchDialog.getEquipmentCode()));
		}		
		if(equipmentVOSearchDialog.getEquipmentName() !=null && StringUtils.isNotBlank(equipmentVOSearchDialog.getEquipmentName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formEquipmentName"));
			builder.append(":"+equipmentVOSearchDialog.getEquipmentName()+",");
			searchCriteria.add(new SearchValueObject("equipmentName", equipmentVOSearchDialog.getEquipmentName()));
		}
		if(equipmentVOSearchDialog.getEquipmentTypeCode() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formEquipmentType"));
			builder.append(":"+parameterService.findByDetailId(equipmentVOSearchDialog.getEquipmentTypeCode()).getParameterDtlName()+",");
			searchCriteria.add(new SearchValueObject("equipmentType", equipmentVOSearchDialog.getEquipmentTypeCode()));
		}
		if(!builder.toString().equals("")){
		     builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingEquipment.setSearchCriteria(searchCriteria);
		
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		EquipmentSearchBean.logger = logger;
	}

	public EquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public EquipmentVO getEquipmentVOSearchDialog() {
		return equipmentVOSearchDialog;
	}

	public void setEquipmentVOSearchDialog(EquipmentVO equipmentVOSearchDialog) {
		this.equipmentVOSearchDialog = equipmentVOSearchDialog;
	}

	public PagingTableModel<EquipmentVO> getPagingEquipment() {
		return pagingEquipment;
	}

	public void setPagingEquipment(PagingTableModel<EquipmentVO> pagingEquipment) {
		this.pagingEquipment = pagingEquipment;
	}

	public List<EquipmentVO> getSelectedEquipments() {
		return selectedEquipments;
	}

	public void setSelectedEquipments(List<EquipmentVO> selectedEquipments) {
		this.selectedEquipments = selectedEquipments;
	}

	public List<SelectItem> getEquipmentTypeSelectItem() {
		return equipmentTypeSelectItem;
	}
	
	public void setEquipmentTypeSelectItem(List<SelectItem> equipmentTypeSelectItem) {
		this.equipmentTypeSelectItem = equipmentTypeSelectItem;
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

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}

	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	} 
	
}
