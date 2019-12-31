package com.wo.epos.module.purchasing.po.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.purchasing.po.service.PoService;
import com.wo.epos.module.purchasing.po.vo.PoVO;

@ManagedBean(name = "poSearchBean")
@ViewScoped
public class PoSearchBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -2439945548467048855L;

	static Logger logger = Logger.getLogger(PoSearchBean.class);
	
	@ManagedProperty(value = "#{poService}")
	private PoService poService;
	
	private PoVO poVOSearchDialog = new PoVO();	

	private PagingTableModel<PoVO> pagingPo;	
	
	private List<PoVO> selectedPo;
	
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		poVOSearchDialog = new PoVO();

		pagingPo = new PagingTableModel(poService, paging);
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		if(userSession.getCompanyId() !=null){
		     searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));	 	     
	 	     
		     if(userSession.getOutletId() !=null){
	 	    	   searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));	
	 	     }
	 	     
	 	    pagingPo.setSearchCriteria(searchCriteria);
		}
		
		disableSearchAll = false;
		
		checkSearch = 0;
		}
	}
	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search(){		
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
				
		if(checkSearch == 0){	
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}		
			
			if(userSession.getCompanyId() !=null){
			     searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));
			     
			     if(userSession.getOutletId() !=null){
		 	    	   searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));	
		 	     }
		 	     
		 	    pagingPo.setSearchCriteria(searchCriteria);
			}
			
			 disableSearchAll = false;
			 pagingPo.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 

	public void modeDelete(List<PoVO> poVOList)
	{
		try
		{
			for(int i=0; i<poVOList.size(); i++){
				PoVO poVoTemp = (PoVO)poVOList.get(i);
				
				poService.delete(poVoTemp.getPoId());
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
		poVOSearchDialog = new PoVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		poVOSearchDialog = new PoVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(userSession.getCompanyId() !=null){
		     searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));
		     
		     if(userSession.getOutletId() !=null){
	 	    	   searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));	
	 	     }
	 	     
	 	    pagingPo.setSearchCriteria(searchCriteria);
		}		
		
		if(poVOSearchDialog.getPoNo() !=null && StringUtils.isNotBlank(poVOSearchDialog.getPoNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formPoNo"));
			builder.append(":"+poVOSearchDialog.getPoNo()+",");
			searchCriteria.add(new SearchValueObject("poNo", poVOSearchDialog.getPoNo()));
		}			
		if(poVOSearchDialog.getStatusName() !=null && StringUtils.isNotBlank(poVOSearchDialog.getStatusName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formPoSupplier"));
			builder.append(":"+poVOSearchDialog.getStatusName()+",");
			searchCriteria.add(new SearchValueObject("supplierName", poVOSearchDialog.getStatusName()));
		}
		if(poVOSearchDialog.getStatusName() !=null && StringUtils.isNotBlank(poVOSearchDialog.getStatusName() )) {
			builder.append(facesUtils.getResourceBundleStringValue("formPoStatus"));
			builder.append(":"+poVOSearchDialog.getStatusName()+",");
			searchCriteria.add(new SearchValueObject("status", poVOSearchDialog.getStatusName()));
		}		
		if(poVOSearchDialog.getStartDate() !=null && poVOSearchDialog.getEndDate() !=null){
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":"+sdf.format(poVOSearchDialog.getStartDate()));
			builder.append(" s/d "+sdf.format(poVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("startDate", poVOSearchDialog.getStartDate()));
			searchCriteria.add(new SearchValueObject("endDate", poVOSearchDialog.getEndDate()));
		}else if(poVOSearchDialog.getStartDate() == null &&  poVOSearchDialog.getEndDate() != null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":-");
			builder.append(" s/d "+sdf.format(poVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("endDate", poVOSearchDialog.getEndDate()));
		}else if(poVOSearchDialog.getStartDate() != null &&  poVOSearchDialog.getEndDate() == null){
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":"+sdf.format(poVOSearchDialog.getStartDate()));
			builder.append(" s/d -"+",");
		}
		if(poVOSearchDialog.getItemResume() !=null && StringUtils.isNotBlank(poVOSearchDialog.getItemResume() )) {
			builder.append(facesUtils.getResourceBundleStringValue("formPoItem"));
			builder.append(":"+poVOSearchDialog.getItemResume()+",");
			searchCriteria.add(new SearchValueObject("itemResume", poVOSearchDialog.getItemResume()));
		}
		
		if(!builder.toString().equals("")){
			builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		
		searchAll = builder.toString();
		
		pagingPo.setSearchCriteria(searchCriteria);
		
	}

	
	

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PoSearchBean.logger = logger;
	}

	public PoService getPoService() {
		return poService;
	}

	public void setPoService(PoService poService) {
		this.poService = poService;
	}

	public PoVO getPoVOSearchDialog() {
		return poVOSearchDialog;
	}

	public void setPoVOSearchDialog(PoVO poVOSearchDialog) {
		this.poVOSearchDialog = poVOSearchDialog;
	}

	public PagingTableModel<PoVO> getPagingPo() {
		return pagingPo;
	}

	public void setPagingPo(PagingTableModel<PoVO> pagingPo) {
		this.pagingPo = pagingPo;
	}

	public List<PoVO> getSelectedPo() {
		return selectedPo;
	}

	public void setSelectedPo(List<PoVO> selectedPo) {
		this.selectedPo = selectedPo;
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
		
	
	
	
}
