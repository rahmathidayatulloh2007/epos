package com.wo.epos.module.inv.um.bean;

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
import com.wo.epos.module.inv.um.service.UmService;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "umSearchBean")
@ViewScoped
public class UmSearchBean extends CommonBean implements Serializable {
		
	private static final long serialVersionUID = -5010765202025439622L;

	static Logger logger = Logger.getLogger(UmSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{umService}")
	private UmService umService;
			
	private UmVO umVOSearchDialog = new UmVO();	

	private PagingTableModel<UmVO> pagingUm;	
	
	private List<UmVO> selectedUms;
		
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
		
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			umVOSearchDialog = new UmVO();
			pagingUm = new PagingTableModel(umService, paging);

			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			companySelectItem = new ArrayList<SelectItem>();
			for (CompanyVO com : companySelectList) {
				companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
			}

			if (userSession.getCompanyId() != null) {
				List<SearchObject> searchCriteria = new ArrayList<SearchObject>();

				searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));

				pagingUm.setSearchCriteria(searchCriteria);
			}

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
			
			if(userSession.getCompanyId() !=null){				
		    	searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId() ));		
		    }
			
			disableSearchAll = false;
			pagingUm.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 

	public void modeDelete(List<UmVO> umVOList){
		try
		{
			for(int i=0; i<umVOList.size(); i++){
				UmVO umVoTemp = (UmVO)umVOList.get(i);
				
				umService.delete(umVoTemp.getUmId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formUmTitle")),
					null);	  
		}	
	}
	
	public void clear(){
		searchAll = "";
		umVOSearchDialog = new UmVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		umVOSearchDialog = new UmVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		
		if(umVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formUmCompany"));
			builder.append(":"+companyService.findById(umVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyId", umVOSearchDialog.getCompanyId()));
		}else{
			if(userSession.getCompanyId() !=null){				
		    	searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId() ));
		    }
		}
		
		if(umVOSearchDialog.getUmName() !=null && StringUtils.isNotBlank(umVOSearchDialog.getUmName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUmName"));
			builder.append(":"+umVOSearchDialog.getUmName()+",");
			searchCriteria.add(new SearchValueObject("umName", umVOSearchDialog.getUmName()));
		}	
		
		if(umVOSearchDialog.getUmDesc() !=null && StringUtils.isNotBlank(umVOSearchDialog.getUmDesc())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUmDesc"));
			builder.append(":"+umVOSearchDialog.getUmDesc()+",");
			searchCriteria.add(new SearchValueObject("umCode", umVOSearchDialog.getUmDesc()));
		}	
		
		if(builder.toString() !=null && !builder.toString().equals("")){
		    builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingUm.setSearchCriteria(searchCriteria);
		
	}

	

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		UmSearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public UmService getUmService() {
		return umService;
	}

	public void setUmService(UmService umService) {
		this.umService = umService;
	}

	public UmVO getUmVOSearchDialog() {
		return umVOSearchDialog;
	}

	public void setUmVOSearchDialog(UmVO umVOSearchDialog) {
		this.umVOSearchDialog = umVOSearchDialog;
	}

	public PagingTableModel<UmVO> getPagingUm() {
		return pagingUm;
	}

	public void setPagingUm(PagingTableModel<UmVO> pagingUm) {
		this.pagingUm = pagingUm;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
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

	public List<UmVO> getSelectedUms() {
		return selectedUms;
	}

	public void setSelectedUms(List<UmVO> selectedUms) {
		this.selectedUms = selectedUms;
	}
	
	
}
