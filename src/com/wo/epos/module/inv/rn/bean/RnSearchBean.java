package com.wo.epos.module.inv.rn.bean;

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
import com.wo.epos.module.inv.rn.service.RnService;
import com.wo.epos.module.inv.rn.vo.RnVO;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "rnSearchBean")
@ViewScoped
public class RnSearchBean extends CommonBean implements Serializable {
	
	private static final long serialVersionUID = -5162577651037398430L;

	static Logger logger = Logger.getLogger(RnSearchBean.class);
	
	@ManagedProperty(value = "#{rnService}")
	private RnService rnService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	private RnVO rnVOSearchDialog = new RnVO();	

	private PagingTableModel<RnVO> pagingRn;	
	
	private List<RnVO> selectedRns;
	
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> rnTypeSelectItem = new ArrayList<SelectItem>();
					
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		rnVOSearchDialog = new RnVO();
		pagingRn = new PagingTableModel(rnService, paging);
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		if(userSession.getOutletId() !=null){
		     searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));		     
		}
		
		if(userSession.getCompanyId() !=null){
			searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));		     
		}
		
		pagingRn.setSearchCriteria(searchCriteria);
		
		disableSearchAll = false;
		
		
		outletSelectItem = new ArrayList<SelectItem>();
		List<OutletVO>  outletVoList = outletService.searchOutletList();
		for(OutletVO out : outletVoList){
			 outletSelectItem.add(new SelectItem(out.getOutletId(), out.getOutletName()));
		}
		
		rnTypeSelectItem = new ArrayList<SelectItem>();
		List<ParameterDtl>  paramDetlVoList = parameterService.parameterDtlList(CommonConstants.RN_TYPE);
		for(ParameterDtl param : paramDetlVoList){
			rnTypeSelectItem.add(new SelectItem(param.getParameterDtlCode(), param.getParameterDtlName()));
		}
		    
	    disableSearchAll = false;
		
		checkSearch = 0;
		
	}
	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search(){		
		
		if(checkSearch == 0){	
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if(userSession.getOutletId() !=null){
			     searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));
			}
			
			if(userSession.getCompanyId() !=null){
				searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));		     
			}
			
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}		
			
			 disableSearchAll = false;
			 pagingRn.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 

	public void modeDelete(List<RnVO> rnVOList){
		try
		{
			for(int i=0; i<rnVOList.size(); i++){
				RnVO rnVoTemp = (RnVO)rnVOList.get(i);
				
				rnService.delete(rnVoTemp.getRnId(), userSession.getUserCode());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formRnTitle")),
					null);	  
		}	
	}
	
	public void clear(){
		searchAll = "";
		rnVOSearchDialog = new RnVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		rnVOSearchDialog = new RnVO();
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
		}
		
		if(userSession.getOutletId() !=null){
		     searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));
		}
		
		if(rnVOSearchDialog.getOutletId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formRnOutlet"));
			builder.append(":"+outletService.findById(rnVOSearchDialog.getOutletId()).getOutletName()+",");
			searchCriteria.add(new SearchValueObject("outletId", rnVOSearchDialog.getOutletId()));
		}
		
		if(rnVOSearchDialog.getRnNo() !=null && StringUtils.isNotBlank(rnVOSearchDialog.getRnNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRnNumber"));
			builder.append(":"+rnVOSearchDialog.getRnNo()+",");
			searchCriteria.add(new SearchValueObject("rnNumber", rnVOSearchDialog.getRnNo()));
		}
		
		if(rnVOSearchDialog.getStartDate() !=null && rnVOSearchDialog.getEndDate() !=null){
			builder.append(facesUtils.getResourceBundleStringValue("formRnDate"));
			builder.append(":"+sdf.format(rnVOSearchDialog.getStartDate()));
			builder.append(" s/d "+sdf.format(rnVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("startDate", rnVOSearchDialog.getStartDate()));
			searchCriteria.add(new SearchValueObject("endDate", rnVOSearchDialog.getEndDate()));
		}else if(rnVOSearchDialog.getStartDate() == null &&  rnVOSearchDialog.getEndDate() != null) {
			builder.append(facesUtils.getResourceBundleStringValue("formRnDate"));
			builder.append(":-");
			builder.append(" s/d "+sdf.format(rnVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("endDate", rnVOSearchDialog.getEndDate()));
		}else if(rnVOSearchDialog.getStartDate() != null &&  rnVOSearchDialog.getEndDate() == null){
			builder.append(facesUtils.getResourceBundleStringValue("formRnDate"));
			builder.append(":"+sdf.format(rnVOSearchDialog.getStartDate()));
			builder.append(" s/d -"+",");
		}	
		
		if(rnVOSearchDialog.getRnTypeCode() !=null && StringUtils.isNotBlank(rnVOSearchDialog.getRnTypeCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRnType"));
			builder.append(":"+rnVOSearchDialog.getRnTypeCode()+",");
			searchCriteria.add(new SearchValueObject("rnType", rnVOSearchDialog.getRnTypeCode()));
		}	
		
		if(rnVOSearchDialog.getOutletOriginName() !=null && StringUtils.isNotBlank(rnVOSearchDialog.getOutletOriginName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRnSender"));
			builder.append(":"+rnVOSearchDialog.getOutletOriginName()+",");
			searchCriteria.add(new SearchValueObject("rnSender", rnVOSearchDialog.getOutletOriginName()));
		}
		
		if(rnVOSearchDialog.getSupplierDocNo() !=null && StringUtils.isNotBlank(rnVOSearchDialog.getSupplierDocNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRnSenderDoc"));
			builder.append(":"+rnVOSearchDialog.getSupplierDocNo()+",");
			searchCriteria.add(new SearchValueObject("rnSenderDoc", rnVOSearchDialog.getSupplierDocNo()));
		}
		
		if(builder.toString() !=null && !builder.toString().equals("")){
		    builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingRn.setSearchCriteria(searchCriteria);
		
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RnSearchBean.logger = logger;
	}

	public RnService getRnService() {
		return rnService;
	}

	public void setRnService(RnService rnService) {
		this.rnService = rnService;
	}

	public RnVO getRnVOSearchDialog() {
		return rnVOSearchDialog;
	}

	public void setRnVOSearchDialog(RnVO rnVOSearchDialog) {
		this.rnVOSearchDialog = rnVOSearchDialog;
	}

	public PagingTableModel<RnVO> getPagingRn() {
		return pagingRn;
	}

	public void setPagingRn(PagingTableModel<RnVO> pagingRn) {
		this.pagingRn = pagingRn;
	}

	public List<RnVO> getSelectedRns() {
		return selectedRns;
	}

	public void setSelectedRns(List<RnVO> selectedRns) {
		this.selectedRns = selectedRns;
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

	public List<SelectItem> getRnTypeSelectItem() {
		return rnTypeSelectItem;
	}

	public void setRnTypeSelectItem(List<SelectItem> rnTypeSelectItem) {
		this.rnTypeSelectItem = rnTypeSelectItem;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	
}
