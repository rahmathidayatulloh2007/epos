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

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.module.inv.um.service.UmService;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "umInputBean")
@ViewScoped
public class UmInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -6329779815150150732L;
	static Logger logger = Logger.getLogger(UmInputBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
		
	@ManagedProperty(value = "#{umService}")
	private UmService umService;
	
	private UmVO umVO = new UmVO();
				
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			umVO = new UmVO();

			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));

			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			companySelectItem = new ArrayList<SelectItem>();
			for (CompanyVO com : companySelectList) {
				companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
			}

			MODE_TYPE = "ADD";
		}
	}
			
	public void save(){
		try 
		{
			if(userSession.getCompanyId() !=null)
			{
				   umVO.setCompanyId(userSession.getCompanyId());
			}
			
			if(MODE_TYPE.equals("ADD"))
			{
			    umService.save(umVO, userSession.getUserCode());
			    
			    facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
	                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				umService.update(umVO, userSession.getUserCode());
				
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
		umVO = new UmVO();
	}
	
	public boolean validateCompany() {
		boolean valid = true;
		if (userSession.getCompanyId() == null) {
			if (umVO.getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesUM",
						facesUtils.getResourceBundleStringValue("formProductCompany") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else {
				valid = true;
			}
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	public boolean validateUm() {
		boolean valid = true;
		
			if (umVO.getUmName() == null || umVO.getUmName() .trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesUM",
						facesUtils.getResourceBundleStringValue("formUmName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else {
				valid = true;
			}

		
		return valid;
	}
	
	public void modeEdit(List<UmVO> umList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<umList.size(); i++){
			  UmVO umVOTemp = (UmVO)umList.get(i);
			  umVO.setUmId(umVOTemp.getUmId());
			  
			  if(umVOTemp.getCompanyId() !=null){
				   umVO.setCompanyId(umVOTemp.getCompanyId());
			  }
			  
			  umVO.setUmName(umVOTemp.getUmName());
			  umVO.setUmDesc(umVOTemp.getUmDesc());			  
			  umVO.setActiveFlag(umVOTemp.getActiveFlag());		
		}
		
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		UmInputBean.logger = logger;
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

	public UmVO getUmVO() {
		return umVO;
	}

	public void setUmVO(UmVO umVO) {
		this.umVO = umVO;
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

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}
	
	
	
	
	
}
