package com.wo.epos.module.sales.draftSalesOrder.bean;

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
import com.wo.epos.module.sales.draftSalesOrder.service.DraftSalesOrderService;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "draftSalesOrderDistributorSearchBean")
@ViewScoped
public class DraftSalesOrderDistributorSearchBean extends CommonBean implements Serializable {
		
	private static final long serialVersionUID = -5010765202025439622L;

	static Logger logger = Logger.getLogger(DraftSalesOrderDistributorSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{draftSalesOrderDistributorInputBean}")
	private DraftSalesOrderDistributorInputBean draftSalesOrderDistributorInputBean;
	
	
	@ManagedProperty(value = "#{draftSalesOrderService}")
	private DraftSalesOrderService draftSalesOrderService;
			
	private DraftSalesOrderVO salesOrderVOSearchDialog = new DraftSalesOrderVO();	

	private PagingTableModel<DraftSalesOrderVO> pagingSo;	
	
	private List<DraftSalesOrderVO> selectedSo;
		
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
		
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	private String MODE_TYPE;
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		
		if (userSession != null) {
			salesOrderVOSearchDialog = new DraftSalesOrderVO();
			pagingSo = new PagingTableModel(draftSalesOrderService, paging);

			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			companySelectItem = new ArrayList<SelectItem>();
			for (CompanyVO com : companySelectList) {
				companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
			}

			if (userSession.getCompanyId() != null) {
				List<SearchObject> searchCriteria = new ArrayList<SearchObject>();

				searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));

				pagingSo.setSearchCriteria(searchCriteria);
			}

			disableSearchAll = false;

			checkSearch = 0;
			searchAll="";
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
			pagingSo.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 
	
	public void modeDelete(List<SalesOrderVO> umVOList){
		try
		{
			for(int i=0; i<umVOList.size(); i++){
				SalesOrderVO salesOrderVoTemp = (SalesOrderVO)umVOList.get(i);
				
				draftSalesOrderService.delete(salesOrderVoTemp.getSoId());
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
		salesOrderVOSearchDialog = new DraftSalesOrderVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		salesOrderVOSearchDialog = new DraftSalesOrderVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		
		if(salesOrderVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formUmCompany"));
			builder.append(":"+companyService.findById(salesOrderVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyId", salesOrderVOSearchDialog.getCompanyId()));
		}else{
			if(userSession.getCompanyId() !=null){				
		    	searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId() ));
		    }
		}
		
		if(salesOrderVOSearchDialog.getSoNo() !=null && StringUtils.isNotBlank(salesOrderVOSearchDialog.getSoNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUmName"));
			builder.append(":"+salesOrderVOSearchDialog.getSoNo()+",");
			searchCriteria.add(new SearchValueObject("umName", salesOrderVOSearchDialog.getSoNo()));
		}	
		
		if(salesOrderVOSearchDialog.getStatusName() !=null && StringUtils.isNotBlank(salesOrderVOSearchDialog.getStatusName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUmDesc"));
			builder.append(":"+salesOrderVOSearchDialog.getStatusName()+",");
			searchCriteria.add(new SearchValueObject("umCode", salesOrderVOSearchDialog.getStatusName()));
		}	
		
		if(builder.toString() !=null && !builder.toString().equals("")){
		    builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingSo.setSearchCriteria(searchCriteria);
		
	}

	

	public static Logger getLogger() {
		return logger;
	}

	

	public static void setLogger(Logger logger) {
		DraftSalesOrderDistributorSearchBean.logger = logger;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getMODE_TYPE() {
		return MODE_TYPE;
	}


	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}


	public DraftSalesOrderDistributorInputBean getDraftSalesOrderDistributorInputBean() {
		return draftSalesOrderDistributorInputBean;
	}


	public void setDraftSalesOrderDistributorInputBean(
			DraftSalesOrderDistributorInputBean draftSalesOrderDistributorInputBean) {
		this.draftSalesOrderDistributorInputBean = draftSalesOrderDistributorInputBean;
	}


	public DraftSalesOrderService getDraftSalesOrderService() {
		return draftSalesOrderService;
	}


	public void setDraftSalesOrderService(DraftSalesOrderService draftSalesOrderService) {
		this.draftSalesOrderService = draftSalesOrderService;
	}


	public DraftSalesOrderVO getSalesOrderVOSearchDialog() {
		return salesOrderVOSearchDialog;
	}


	public void setSalesOrderVOSearchDialog(DraftSalesOrderVO salesOrderVOSearchDialog) {
		this.salesOrderVOSearchDialog = salesOrderVOSearchDialog;
	}


	public PagingTableModel<DraftSalesOrderVO> getPagingSo() {
		return pagingSo;
	}


	public void setPagingSo(PagingTableModel<DraftSalesOrderVO> pagingSo) {
		this.pagingSo = pagingSo;
	}


	public List<DraftSalesOrderVO> getSelectedSo() {
		return selectedSo;
	}


	public void setSelectedSo(List<DraftSalesOrderVO> selectedSo) {
		this.selectedSo = selectedSo;
	}



	
	
	
	
}
