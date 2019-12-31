package com.wo.epos.module.uam.role.bean;

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
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.role.service.RoleService;
import com.wo.epos.module.uam.role.vo.RoleVO;

@ManagedBean(name = "roleInputBean")
@ViewScoped
public class RoleInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -4853077063403171097L;

	static Logger logger = Logger.getLogger(RoleInputBean.class);
	
	@ManagedProperty(value = "#{roleService}")
	private RoleService roleService;
		
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	private RoleVO roleVO = new RoleVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			roleVO = new RoleVO();

			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}

			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;
		}
	}
	
	
	public boolean validateOutlet(){
		boolean valid = true;
		
			if (roleVO.getRoleCode() == null || roleVO.getRoleCode().trim().isEmpty()) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRole",
						facesUtils.getResourceBundleStringValue("formRoleCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}  if (roleVO.getRoleName().trim().isEmpty() || roleVO.getRoleName() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRole",
						facesUtils.getResourceBundleStringValue("formRoleName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} 
	
		
		
		return valid;

	}
	
	
	public void save(){
		try 
		{
			if(userSession.getCompanyId() !=null){
				roleVO.setCompanyId(userSession.getCompanyId());
			}
			
			if(MODE_TYPE.equals("ADD"))
			{
				roleService.save(roleVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:growlMessage", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				roleService.update(roleVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:growlMessage", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:growlMessage",
					"Operation Failed : " + ex.getMessage(),
					null);	    
		}	
	}
	
	public void modeAdd(){
		roleVO = new RoleVO();
	}
	
	
	public void modeEdit(List<RoleVO> roleVOList){
		MODE_TYPE = "EDIT";
		for(RoleVO roleVOTemp : roleVOList){
			roleVO.setRoleId(roleVOTemp.getRoleId());
			roleVO.setRoleCode(roleVOTemp.getRoleCode());
			roleVO.setRoleName(roleVOTemp.getRoleName());
		    roleVO.setRoleDesc(roleVOTemp.getRoleDesc());
		    if(roleVOTemp.getCompanyId() !=null){
		    	roleVO.setCompanyId(roleVOTemp.getCompanyId());
		    	roleVO.setCompanyName(roleVOTemp.getCompanyName());
		    }
		    roleVO.setActiveFlag(roleVOTemp.getActiveFlag());	
		}
	}

	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RoleInputBean.logger = logger;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
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
