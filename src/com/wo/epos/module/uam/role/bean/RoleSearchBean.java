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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.role.service.RoleService;
import com.wo.epos.module.uam.role.vo.RoleVO;

@ManagedBean(name = "roleSearchBean")
@ViewScoped
public class RoleSearchBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 3621259688079111820L;

	static Logger logger = Logger.getLogger(RoleSearchBean.class);
	
	@ManagedProperty(value = "#{roleService}")
	private RoleService roleService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	private RoleVO roleSearchVO = new RoleVO();	

	private PagingTableModel<RoleVO> pagingRole;	
		
	private List<RoleVO> selectedRoles;
	
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;
	
	private Integer checkSearch;
		
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		roleSearchVO = new RoleVO();
		
		pagingRole = new PagingTableModel(roleService, 10);
		disableFlag = false;
		disableFlagAdd = true;	
		
		companySelectItem = new ArrayList<SelectItem>();		
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
		if(userSession.getCompanyId() !=null){
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();			
			searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
			pagingRole.setSearchCriteria(searchCriteria);
		}
		
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
			
			if(userSession.getCompanyId() !=null){
				searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
			}
			 disableSearchAll = false;
			 pagingRole.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 
	
	public void modeDelete(List<RoleVO> roleVOList){
		try
		{
			for(int i=0; i<roleVOList.size(); i++){
				RoleVO roleVOTemp = (RoleVO)roleVOList.get(i);
				
				roleService.delete(roleVOTemp.getRoleId());
			}
			
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
	                null);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_ERROR,
				"frm001:messages",
				facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formRoleTitle")),
				null);
		}
	}
	
	

	public void clear(){
		searchAll = "";
	    roleSearchVO = new RoleVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		roleSearchVO = new RoleVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(userSession.getCompanyId() !=null){
			searchCriteria.add(new SearchValueObject("companyId", userSession.getCompanyId()));
		}
		
		if(roleSearchVO.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formRoleCompany"));
			builder.append(":"+companyService.findById(roleSearchVO.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyId", roleSearchVO.getCompanyId()));
		}			
		if(roleSearchVO.getRoleCode() !=null && StringUtils.isNotBlank(roleSearchVO.getRoleCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRoleCode"));
			builder.append(":"+roleSearchVO.getRoleCode()+",");
			searchCriteria.add(new SearchValueObject("roleCode", roleSearchVO.getRoleCode()));
		}
		if(roleSearchVO.getRoleName() !=null && StringUtils.isNotBlank(roleSearchVO.getRoleName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRoleName"));
			builder.append(":"+roleSearchVO.getRoleName()+",");
			searchCriteria.add(new SearchValueObject("roleName", roleSearchVO.getRoleName()));
		}
		if(roleSearchVO.getRoleDesc() !=null && StringUtils.isNotBlank(roleSearchVO.getRoleDesc())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRoleDesc"));
			builder.append(":"+roleSearchVO.getRoleDesc()+",");
			searchCriteria.add(new SearchValueObject("roleDesc", roleSearchVO.getRoleDesc()));
		}
		
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingRole.setSearchCriteria(searchCriteria);
		
	} 

	public static Logger getLogger() {
		return logger;
	}
	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public RoleVO getRoleSearchVO() {
		return roleSearchVO;
	}

	public void setRoleSearchVO(RoleVO roleSearchVO) {
		this.roleSearchVO = roleSearchVO;
	}

	public PagingTableModel<RoleVO> getPagingRole() {
		return pagingRole;
	}

	public void setPagingRole(PagingTableModel<RoleVO> pagingRole) {
		this.pagingRole = pagingRole;
	}

	public List<RoleVO> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(List<RoleVO> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public static void setLogger(Logger logger) {
		RoleSearchBean.logger = logger;
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

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
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

	
	
	
	
}
