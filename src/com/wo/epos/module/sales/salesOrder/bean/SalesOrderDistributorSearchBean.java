package com.wo.epos.module.sales.salesOrder.bean;

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
import com.wo.epos.module.inv.um.bean.UmInputBean;
import com.wo.epos.module.sales.salesOrder.service.SalesOrderDistributorService;
import com.wo.epos.module.sales.salesOrder.service.SalesOrderService;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "salesOrderDistributorSearchBean")
@ViewScoped
public class SalesOrderDistributorSearchBean extends CommonBean implements Serializable {
		
	private static final long serialVersionUID = -5010765202025439622L;

	static Logger logger = Logger.getLogger(SalesOrderDistributorSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{salesOrderDistributorInputBean}")
	private SalesOrderDistributorInputBean salesOrderDistributorInputBean;
	
	
	@ManagedProperty(value = "#{salesOrderService}")
	private SalesOrderService salesOrderService;
			
	private SalesOrderVO salesOrderVOSearchDialog = new SalesOrderVO();	

	private PagingTableModel<SalesOrderVO> pagingSo;	
	
	private List<SalesOrderVO> selectedSo;
		
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
			salesOrderVOSearchDialog = new SalesOrderVO();
			pagingSo = new PagingTableModel(salesOrderService, paging);

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
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		if(checkSearch == 0){	
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
		
		pagingSo.setSearchCriteria(searchCriteria);
		
	} 
	
	public void modeDelete(List<SalesOrderVO> umVOList){
		try
		{
			for(int i=0; i<umVOList.size(); i++){
				SalesOrderVO salesOrderVoTemp = (SalesOrderVO)umVOList.get(i);
				
				salesOrderService.delete(salesOrderVoTemp.getSoId());
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
		salesOrderVOSearchDialog = new SalesOrderVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		salesOrderVOSearchDialog = new SalesOrderVO();
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
		SalesOrderDistributorSearchBean.logger = logger;
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


	public SalesOrderService getSalesOrderService() {
		return salesOrderService;
	}


	public void setSalesOrderService(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}


	public SalesOrderVO getSalesOrderVOSearchDialog() {
		return salesOrderVOSearchDialog;
	}


	public void setSalesOrderVOSearchDialog(SalesOrderVO salesOrderVOSearchDialog) {
		this.salesOrderVOSearchDialog = salesOrderVOSearchDialog;
	}


	public PagingTableModel<SalesOrderVO> getPagingSo() {
		return pagingSo;
	}


	public void setPagingSo(PagingTableModel<SalesOrderVO> pagingSo) {
		this.pagingSo = pagingSo;
	}


	public List<SalesOrderVO> getSelectedSo() {
		return selectedSo;
	}


	public void setSelectedSo(List<SalesOrderVO> selectedSo) {
		this.selectedSo = selectedSo;
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


	public SalesOrderDistributorInputBean getSalesOrderDistributorInputBean() {
		return salesOrderDistributorInputBean;
	}


	public void setSalesOrderDistributorInputBean(SalesOrderDistributorInputBean salesOrderDistributorInputBean) {
		this.salesOrderDistributorInputBean = salesOrderDistributorInputBean;
	}
	
	
	
	
}
