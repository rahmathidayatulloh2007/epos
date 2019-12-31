package com.wo.epos.module.master.systemProperty.bean;

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
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.master.systemProperty.vo.SystemPropertyVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "systemPropertySearchBean")
@ViewScoped
public class SystemPropertySearchBean extends CommonBean implements Serializable{
	
    private static final long serialVersionUID = 6621295013408114353L;

	static Logger logger = Logger.getLogger(SystemPropertySearchBean.class);
	
	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	private SystemPropertyVO searchSystemPropertyVO = new SystemPropertyVO();	

	private PagingTableModel<SystemPropertyVO> pagingSystemProperty;	
		
//	private List<SystemPropertyVO> selectedSystemPropertys;
	private List<CompanyVO> selectedCompanys;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;
	
	private Integer checkSearch;
		
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		searchSystemPropertyVO = new SystemPropertyVO();
		
		pagingSystemProperty = new PagingTableModel(systemPropertyService, 10);
		disableFlag = false;
		disableFlagAdd = true;	
		
		companySelectItem = new ArrayList<SelectItem>();		
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
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
			 pagingSystemProperty.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 
	
//	public void modeDelete(List<SystemPropertyVO> systemPropertyVOList){
//		for(int i=0; i<systemPropertyVOList.size(); i++){
//			SystemPropertyVO systemPropertyVOTemp = (SystemPropertyVO)systemPropertyVOList.get(i);
//			
//			systemPropertyService.delete(systemPropertyVOTemp.getSystemPropertyId());
//		}
//	}
	
	public void modeDelete(List<CompanyVO> voList){
		try
		{
			for(int i=0; i<voList.size(); i++)
			{
				CompanyVO voTemp = (CompanyVO)voList.get(i);
				
				systemPropertyService.deleteByCompanyId(voTemp.getCompanyId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formSystemPropertyTitle")),
					null);	  
		}	
	}
	

	public void clear(){
		searchAll = "";
	    searchSystemPropertyVO = new SystemPropertyVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		searchSystemPropertyVO = new SystemPropertyVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(searchSystemPropertyVO.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formSystemPropertyCompany"));
			builder.append(":"+companyService.findById(searchSystemPropertyVO.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyId", searchSystemPropertyVO.getCompanyId()));
		}			
		if(searchSystemPropertyVO.getSystemPropertyName() !=null && StringUtils.isNotBlank(searchSystemPropertyVO.getSystemPropertyName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSystemPropertyName"));
			builder.append(":"+searchSystemPropertyVO.getSystemPropertyName()+",");
			searchCriteria.add(new SearchValueObject("systemPropertyName", searchSystemPropertyVO.getSystemPropertyName()));
		}
		if(searchSystemPropertyVO.getSystemPropertyValue() !=null && StringUtils.isNotBlank(searchSystemPropertyVO.getSystemPropertyValue())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSystemPropertyValue"));
			builder.append(":"+searchSystemPropertyVO.getSystemPropertyValue()+",");
			searchCriteria.add(new SearchValueObject("systemPropertyValue", searchSystemPropertyVO.getSystemPropertyValue()));
		}
		if(searchSystemPropertyVO.getSystemPropertyDesc() !=null && StringUtils.isNotBlank(searchSystemPropertyVO.getSystemPropertyDesc())) {
			builder.append(facesUtils.getResourceBundleStringValue("formSystemPropertyDesc"));
			builder.append(":"+searchSystemPropertyVO.getSystemPropertyDesc()+",");
			searchCriteria.add(new SearchValueObject("systemPropertyDesc", searchSystemPropertyVO.getSystemPropertyDesc()));
		}
		
		if(builder.toString() !=null && !builder.toString().equals("")){
			builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");			
		}	
		
		searchAll = builder.toString();
		
		pagingSystemProperty.setSearchCriteria(searchCriteria);
		
	} 

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SystemPropertySearchBean.logger = logger;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	public SystemPropertyVO getSearchSystemPropertyVO() {
		return searchSystemPropertyVO;
	}

	public void setSearchSystemPropertyVO(SystemPropertyVO searchSystemPropertyVO) {
		this.searchSystemPropertyVO = searchSystemPropertyVO;
	}

	public PagingTableModel<SystemPropertyVO> getPagingSystemProperty() {
		return pagingSystemProperty;
	}

	public void setPagingSystemProperty(
			PagingTableModel<SystemPropertyVO> pagingSystemProperty) {
		this.pagingSystemProperty = pagingSystemProperty;
	}

//	public List<SystemPropertyVO> getSelectedSystemPropertys() {
//		return selectedSystemPropertys;
//	}
//
//	public void setSelectedSystemPropertys(
//			List<SystemPropertyVO> selectedSystemPropertys) {
//		this.selectedSystemPropertys = selectedSystemPropertys;
//	}

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

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<CompanyVO> getSelectedCompanys() {
		return selectedCompanys;
	}

	public void setSelectedCompanys(List<CompanyVO> selectedCompanys) {
		this.selectedCompanys = selectedCompanys;
	}
}
