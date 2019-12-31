package com.wo.epos.module.uam.parameter.bean;

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
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

@ManagedBean(name = "parameterInputBean")
@ViewScoped
public class ParameterInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(ParameterInputBean.class);
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	private ParameterVO parameterVO = new ParameterVO();
	private ParameterDtlVO parameterDtlVO = new ParameterDtlVO();	
	
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		parameterVO = new ParameterVO();
				
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		MODE_TYPE = "ADD";
		
		disableFlagAdd = true;				
		
	}
	
	public boolean validasi(){
    	boolean valid = true;
    	
    	if(parameterVO.getParameterCode()==null ||parameterVO.getParameterCode().trim().isEmpty() && MODE_TYPE == "ADD" ){

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:parameterDtlTable",
					facesUtils.getResourceBundleStringValue("formParameterCode") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		}if(parameterVO.getParameterName()==null ||parameterVO.getParameterName().trim().isEmpty() ){
			
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:parameterDtlTable",
					facesUtils.getResourceBundleStringValue("formParameterName") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;
			
			}else
    	
    	if(parameterVO.getListDetail().size() > 0){
    		for(int i=0 ; i < parameterVO.getListDetail().size() ; i++) {
    			if(parameterVO.getListDetail().get(i).getParameterDtlCode() == null ||
    					parameterVO.getListDetail().get(i).getParameterDtlCode().equals("")){
    				
    				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:parameterDtlTable"+i+":detailCode",
    						facesUtils.getResourceBundleStringValue("formParameterDetailCode") + " "
    								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
    				valid = false;
    				
/*    				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_ERROR, 
						"frm001:parameterDtlTable:"+i+":detailCode", 
						facesUtils.retrieveMessage("formParameterDetailCode") + " " + facesUtils.retrieveMessage("errorMustBeFilled"), 
		                null);
    				
    				valid = false;*/
    			}
    			else
    			if(parameterVO.getListDetail().get(i).getParameterDtlName() == null ||
    					parameterVO.getListDetail().get(i).getParameterDtlName().equals("")){
    				
    				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:parameterDtlTable"+i+":detailCode",
    						facesUtils.getResourceBundleStringValue("formParameterDetailName") + " "
    								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
    				valid = false;
    				
    				
 /*   				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_ERROR, 
						"frm001:parameterDtlTable:"+i+":detailName", 
						facesUtils.retrieveMessage("formParameterDetailName") + " " + facesUtils.retrieveMessage("errorMustBeFilled"), 
		                null);
    				
    				valid = false;*/
    			}
    			
    		}
    	}
    	else
    	{
    		facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					null,
					facesUtils.getResourceBundleStringValue("formParameterDetailErrMsgMinim"),
					null);	
    		
			valid = false;    		
    	}
    	
    	return valid;
	}
			
	public void save(){
		try 
		{
			if(MODE_TYPE.equals("ADD"))
			{
				parameterService.save(parameterVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
	                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				parameterService.update(parameterVO, userSession.getUserCode());
				
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
	
	public void modeEdit(List<ParameterVO> parameterList){
		MODE_TYPE = "EDIT";
		parameterVO = new ParameterVO();
		for(int i=0; i<parameterList.size(); i++){
			  ParameterVO parameterVOTemp = (ParameterVO)parameterList.get(i);
			  parameterVO.setParameterCode(parameterVOTemp.getParameterCode());
			  parameterVO.setParameterName(parameterVOTemp.getParameterName());
			  parameterVO.setParameterDesc(parameterVOTemp.getParameterDesc());
			  parameterVO.setActiveFlag(parameterVOTemp.getActiveFlag());
		}
		
		List<ParameterDtl> parameterDtlList = parameterService.parameterDtlList(parameterVO.getParameterCode());
		for(int j=0; j<parameterDtlList.size();j++){
			ParameterDtl paramDtl = (ParameterDtl) parameterDtlList.get(j);
			parameterDtlVO = new ParameterDtlVO();
			parameterDtlVO.setParameterDtlCode(paramDtl.getParameterDtlCode());
			parameterDtlVO.setParameterDtlName(paramDtl.getParameterDtlName());
			parameterDtlVO.setParameterDtlDesc(paramDtl.getParameterDtlDesc());
			parameterDtlVO.setFlag("true");
			parameterVO.getListDetail().add(parameterDtlVO);
		}
	}
		
	public void reinit() { 
		parameterVO.getListDetail().add(new ParameterDtlVO());
    }
	
	public void clearAll(){
		parameterVO = new ParameterVO();
		parameterVO.setListDetail(new ArrayList<ParameterDtlVO>());
	}
	
	public void deleteDetail(ParameterDtlVO statDetailIndex)
    {
		parameterVO.getListDetail().remove(statDetailIndex);
    }

	public ParameterVO getParameterVO() {
		return parameterVO;
	}

	public void setParameterVO(ParameterVO parameterVO) {
		this.parameterVO = parameterVO;
	}

	public boolean isDisableFlagAdd() {
		return disableFlagAdd;
	}

	public void setDisableFlagAdd(boolean disableFlagAdd) {
		this.disableFlagAdd = disableFlagAdd;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
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

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public ParameterDtlVO getParameterDtlVO() {
		return parameterDtlVO;
	}

	public void setParameterDtlVO(ParameterDtlVO parameterDtlVO) {
		this.parameterDtlVO = parameterDtlVO;
	}
	
}
