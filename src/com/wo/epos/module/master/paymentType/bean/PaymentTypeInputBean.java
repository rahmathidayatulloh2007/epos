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

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.master.paymentType.service.PaymentTypeService;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "paymentTypeInputBean")
@ViewScoped
public class PaymentTypeInputBean  extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -2004942113180374717L;
	static Logger logger = Logger.getLogger(PaymentTypeInputBean.class);
	
	@ManagedProperty(value = "#{paymentTypeService}")
	private PaymentTypeService paymentTypeService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	private PaymentTypeVO paymentTypeVO = new PaymentTypeVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean adminMode;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> paymentMethodSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
    @PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		paymentTypeVO = new PaymentTypeVO();
				
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
		if(userSession.getCompanyId()!=null){
			if(userSession.getCompanyId().equals(companyUserList.get(i).getCompanyId())){
				adminMode = false;
				break;
			}
		}
		else{
			adminMode = true;
		}
	       		
		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;	
		}
	}
    
    public boolean validate() {
		boolean valid = true;

		if (paymentTypeVO.getCompanyId() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSupplierCompany")
					+ " " + facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		if (paymentTypeVO.getPaytypeCode().trim().equals("") || paymentTypeVO.getPaytypeCode().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formPaymentTypeCode") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if (paymentTypeVO.getPaytypeName().trim().equals("") || paymentTypeVO.getPaytypeName().trim().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formPaymentTypeName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if (paymentTypeVO.getPaymentMethodCode() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formPaymentTypeMethod") + " "
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
		    	paymentTypeVO.setCompanyId(userSession.getCompanyId() );
		    }
		    
			if(MODE_TYPE.equals("ADD"))
			{
			    paymentTypeService.save(paymentTypeVO, userSession.getUserCode());
			    
			    facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				paymentTypeService.update(paymentTypeVO, userSession.getUserCode());
				
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
		paymentTypeVO = new PaymentTypeVO();
	}
	
	public void modeEdit(List<PaymentTypeVO> paymentTypeList){
		MODE_TYPE = "EDIT";
		paymentTypeVO = new PaymentTypeVO();
		for(int i=0; i<paymentTypeList.size(); i++){
			PaymentTypeVO paymentTypeVOTemp = (PaymentTypeVO)paymentTypeList.get(i);
			
			paymentTypeVO.setPaytypeId(paymentTypeVOTemp.getPaytypeId());
			paymentTypeVO.setCompanyId(paymentTypeVOTemp.getCompanyId());
			paymentTypeVO.setCompanyName(paymentTypeVOTemp.getCompanyName());
			paymentTypeVO.setPaytypeCode(paymentTypeVOTemp.getPaytypeCode());
			paymentTypeVO.setPaytypeName(paymentTypeVOTemp.getPaytypeName());
			paymentTypeVO.setPaymentMethodCode(paymentTypeVOTemp.getPaymentMethodCode());
			paymentTypeVO.setPaymentMethodName(paymentTypeVOTemp.getPaymentMethodName());
			
			if(paymentTypeVOTemp.getPaytypeValue() != null){
				paymentTypeVO.setPaytypeValue(paymentTypeVOTemp.getPaytypeValue());
			}

			paymentTypeVO.setActiveFlag(paymentTypeVOTemp.getActiveFlag());
		}
		
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

	public PaymentTypeVO getPaymentTypeVO() {
		return paymentTypeVO;
	}

	public void setPaymentTypeVO(PaymentTypeVO paymentTypeVO) {
		this.paymentTypeVO = paymentTypeVO;
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

	public List<SelectItem> getPaymentMethodSelectItem() {
		return paymentMethodSelectItem;
	}

	public void setPaymentMethodSelectItem(List<SelectItem> paymentMethodSelectItem) {
		this.paymentMethodSelectItem = paymentMethodSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}
	
}
