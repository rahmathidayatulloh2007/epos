package com.wo.epos.module.master.paymentType.bean;

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
import com.wo.epos.module.master.paymentType.service.PaymentTypeService;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "paymentTypeSearchBean")
@ViewScoped
public class PaymentTypeSearchBean  extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -1144001953528924973L;

	static Logger logger = Logger.getLogger(PaymentTypeSearchBean.class);
	
	@ManagedProperty(value = "#{paymentTypeService}")
	private PaymentTypeService paymentTypeService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	private PaymentTypeVO paymentTypeVOSearchDialog = new PaymentTypeVO();

	private PagingTableModel<PaymentTypeVO> pagingPaymentType;	
	
	private List<PaymentTypeVO> paymentTypeList = new ArrayList<PaymentTypeVO>();	
	private List<PaymentTypeVO> selectedPaymentType;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> paymentMethodSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	private boolean adminMode;
	
	private String searchAll;	
	
	private Integer checkSearch;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		paymentTypeVOSearchDialog = new PaymentTypeVO();
		pagingPaymentType = new PagingTableModel(paymentTypeService, paging);
		
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = paymentTypeService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
	    
		List<ParameterDtl> paymentMethodSelectList = paymentTypeService.parameterDtlList(CommonConstants.PAYMENT_METHOD);
	    paymentMethodSelectItem = new ArrayList<SelectItem>();
	    for(ParameterDtl dtl : paymentMethodSelectList){
	    	paymentMethodSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
	    }
	    
	    List<CompanyVO> companyUserList = paymentTypeService.searchCompanyList();
		for(int i = 0; i < companyUserList.size(); i++)
		if(userSession.getCompanyId()!=null)
		{
			if(userSession.getCompanyId().equals(companyUserList.get(i).getCompanyId())){
				adminMode = false;
				break;
			}
		}else{
			adminMode = true;
		}
		
		disableFlag = false;
		disableFlagAdd = true;	
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
			
			 disableSearchAll = false;
	         pagingPaymentType.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
	}
	
	public void modeDelete(List<PaymentTypeVO> paymentTypeVOList){
		try
		{
			for(int i=0; i<paymentTypeVOList.size(); i++)
			{
				PaymentTypeVO paymentTypeVOTemp = (PaymentTypeVO)paymentTypeVOList.get(i);
				
				paymentTypeService.delete(paymentTypeVOTemp.getPaytypeId());
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
		paymentTypeVOSearchDialog = new PaymentTypeVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		paymentTypeVOSearchDialog = new PaymentTypeVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(paymentTypeVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formPaymentTypeCompany"));
			builder.append(":"+companyService.findById(paymentTypeVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("company", paymentTypeVOSearchDialog.getCompanyId()));
		}	
		if(paymentTypeVOSearchDialog.getPaytypeCode() !=null && StringUtils.isNotBlank(paymentTypeVOSearchDialog.getPaytypeCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formPaymentTypeCode"));
			builder.append(":"+paymentTypeVOSearchDialog.getPaytypeCode()+",");
			searchCriteria.add(new SearchValueObject("paytypeCode", paymentTypeVOSearchDialog.getPaytypeCode()));
		}
		if(paymentTypeVOSearchDialog.getPaytypeName() !=null && StringUtils.isNotBlank(paymentTypeVOSearchDialog.getPaytypeName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formPaymentTypeName"));
			builder.append(":"+paymentTypeVOSearchDialog.getPaytypeName()+",");
			searchCriteria.add(new SearchValueObject("paytypeName", paymentTypeVOSearchDialog.getPaytypeName()));
		}
		if(paymentTypeVOSearchDialog.getPaymentMethodCode() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formPaymentTypeMethod"));
			builder.append(":"+parameterService.findByDetailId(paymentTypeVOSearchDialog.getPaymentMethodCode()).getParameterDtlName()+",");
			searchCriteria.add(new SearchValueObject("paymentMethod", paymentTypeVOSearchDialog.getPaymentMethodCode()));
		}	
		if(paymentTypeVOSearchDialog.getActiveFlag() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formPaymentTypeActive"));
			builder.append(":"+paymentTypeVOSearchDialog.getActiveFlag()+",");
			searchCriteria.add(new SearchValueObject("activeFlag", paymentTypeVOSearchDialog.getActiveFlag()));
		}
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingPaymentType.setSearchCriteria(searchCriteria);		
	}

	public PaymentTypeService getPaymentTypeService() {
		return paymentTypeService;
	}

	public void setPaymentTypeService(PaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public PaymentTypeVO getPaymentTypeVOSearchDialog() {
		return paymentTypeVOSearchDialog;
	}

	public void setPaymentTypeVOSearchDialog(PaymentTypeVO paymentTypeVOSearchDialog) {
		this.paymentTypeVOSearchDialog = paymentTypeVOSearchDialog;
	}

	public PagingTableModel<PaymentTypeVO> getPagingPaymentType() {
		return pagingPaymentType;
	}

	public void setPagingPaymentType(
			PagingTableModel<PaymentTypeVO> pagingPaymentType) {
		this.pagingPaymentType = pagingPaymentType;
	}

	public List<PaymentTypeVO> getPaymentTypeList() {
		return paymentTypeList;
	}

	public void setPaymentTypeList(List<PaymentTypeVO> paymentTypeList) {
		this.paymentTypeList = paymentTypeList;
	}

	public List<PaymentTypeVO> getSelectedPaymentType() {
		return selectedPaymentType;
	}

	public void setSelectedPaymentType(List<PaymentTypeVO> selectedPaymentType) {
		this.selectedPaymentType = selectedPaymentType;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getPaymentMethodSelectItem() {
		return paymentMethodSelectItem;
	}

	public void setPaymentMethodSelectItem(List<SelectItem> paymentMethodSelectItem) {
		this.paymentMethodSelectItem = paymentMethodSelectItem;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
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

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
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
