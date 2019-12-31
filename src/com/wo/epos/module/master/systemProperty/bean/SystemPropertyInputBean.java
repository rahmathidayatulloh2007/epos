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

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.master.systemProperty.vo.SystemPropertyVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "systemPropertyInputBean")
@ViewScoped
public class SystemPropertyInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 7113159650537924182L;

	static Logger logger = Logger.getLogger(SystemPropertyInputBean.class);
	
	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;
		
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	private SystemPropertyVO systemPropertyVO = new SystemPropertyVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		systemPropertyVO = new SystemPropertyVO();
				
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		companySelectItem = new ArrayList<SelectItem>();		
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
	    	       		
		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;				
		}
	}
			
	public void save(){
		try
		{
			if(userSession.getCompanyId() !=null){
				systemPropertyVO.setCompanyId(userSession.getCompanyId());			
			}
			
			if(MODE_TYPE.equals("ADD"))
			{
				systemPropertyService.save(systemPropertyVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				systemPropertyService.update(systemPropertyVO, userSession.getUserCode());
				
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
		systemPropertyVO = new SystemPropertyVO();
	}
	
	
	public void modeEdit(List<SystemPropertyVO> sysPropVOList){
		MODE_TYPE = "EDIT";
		for(SystemPropertyVO sysPropVOTemp: sysPropVOList){
		    systemPropertyVO.setSystemPropertyId(sysPropVOTemp.getSystemPropertyId());
		    systemPropertyVO.setSystemPropertyName(sysPropVOTemp.getSystemPropertyName());
		    systemPropertyVO.setSystemPropertyValue(sysPropVOTemp.getSystemPropertyValue());
		    systemPropertyVO.setSystemPropertyDesc(sysPropVOTemp.getSystemPropertyDesc());
		    if(sysPropVOTemp.getCompanyId() !=null){
		    	systemPropertyVO.setCompanyId(sysPropVOTemp.getCompanyId());
		    	systemPropertyVO.setCompanyName(sysPropVOTemp.getCompanyName());
		    }
		    systemPropertyVO.setActiveFlag(sysPropVOTemp.getActiveFlag());	
		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SystemPropertyInputBean.logger = logger;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public SystemPropertyVO getSystemPropertyVO() {
		return systemPropertyVO;
	}

	public void setSystemPropertyVO(SystemPropertyVO systemPropertyVO) {
		this.systemPropertyVO = systemPropertyVO;
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

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}
	
	
	
}
