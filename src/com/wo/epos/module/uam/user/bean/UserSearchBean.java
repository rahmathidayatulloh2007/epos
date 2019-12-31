package com.wo.epos.module.uam.user.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.role.service.RoleService;
import com.wo.epos.module.uam.role.vo.RoleVO;
import com.wo.epos.module.uam.user.service.UserService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "userSearchBean")
@ViewScoped
public class UserSearchBean extends CommonBean implements Serializable {
		
	private static final long serialVersionUID = -4156613283871118032L;

	static Logger logger = Logger.getLogger(UserSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{userService}")
	private UserService userService;
	
	@ManagedProperty(value = "#{roleService}")
	private RoleService roleService;
		
	private UserVO userVOSearchDialog = new UserVO();	

	private PagingTableModel<UserVO> pagingUser;	
	
	private List<UserVO> selectedUsers;
		
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> roleSelectItem = new ArrayList<SelectItem>();
		
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;
	
	private Long companyId;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			HttpSession session = request.getSession(true);

			UserVO userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);

			if (userVO != null) {
				companyId = userVO.getCompanyId();
			}
			userVOSearchDialog = new UserVO();
			pagingUser = new PagingTableModel(userService, paging);

			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			companySelectItem = new ArrayList<SelectItem>();
			for (CompanyVO com : companySelectList) {
				companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
			}

			List<RoleVO> roleSelectList = roleService.searchRoleList();
			roleSelectItem = new ArrayList<SelectItem>();
			for (RoleVO role : roleSelectList) {
				roleSelectItem.add(new SelectItem(role.getRoleId(), role.getRoleName()));
			}

			disableSearchAll = false;

			checkSearch = 0;
			search();
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
			if(getCompanyId()!=null){
			searchCriteria.add(new SearchValueObject("companyId", getCompanyId()));
			}
			
			 disableSearchAll = false;
			 pagingUser.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 

	public void modeDelete(List<UserVO> userVOList){
		try
		{
			for(int i=0; i<userVOList.size(); i++){
				UserVO userVoTemp = (UserVO)userVOList.get(i);
				
				userService.delete(userVoTemp.getUserId());
			}
			
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
	            facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
	            null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_ERROR,
				"frm001:messages",
				facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formUserTitle")),
				null);	    
		}	
	}
	
	public void clear(){
		searchAll = "";
		userVOSearchDialog = new UserVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		userVOSearchDialog = new UserVO();		
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(getCompanyId()!=null){
			searchCriteria.add(new SearchValueObject("companyId", getCompanyId()));
		}
		
		if(userVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formUserCompany"));
			builder.append(":"+companyService.findById(userVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyId", userVOSearchDialog.getCompanyId()));
		}
		
		if(userVOSearchDialog.getUserCode() !=null && StringUtils.isNotBlank(userVOSearchDialog.getUserCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUserCode"));
			builder.append(":"+userVOSearchDialog.getUserCode()+",");
			searchCriteria.add(new SearchValueObject("userCode", userVOSearchDialog.getUserCode()));
		}	
		
		if(userVOSearchDialog.getEmployeeNo() !=null && StringUtils.isNotBlank(userVOSearchDialog.getEmployeeNo())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUserEmployeeNo"));
			builder.append(":"+userVOSearchDialog.getEmployeeNo()+",");
			searchCriteria.add(new SearchValueObject("employeeNo", userVOSearchDialog.getEmployeeNo()));
		}	
		
		if(userVOSearchDialog.getEmployeeName() !=null && StringUtils.isNotBlank(userVOSearchDialog.getEmployeeName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUserEmployeeName"));
			builder.append(":"+userVOSearchDialog.getEmployeeName()+",");
			searchCriteria.add(new SearchValueObject("employeeName", userVOSearchDialog.getEmployeeName()));
		}	
		
		if(userVOSearchDialog.getRoleId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formUserRole"));
			builder.append(":"+roleService.findById(userVOSearchDialog.getRoleId()).getRoleName()+",");
			searchCriteria.add(new SearchValueObject("roleId", userVOSearchDialog.getRoleId()));
		}
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingUser.setSearchCriteria(searchCriteria);
		
	}

	
	

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		UserSearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public UserVO getUserVOSearchDialog() {
		return userVOSearchDialog;
	}

	public void setUserVOSearchDialog(UserVO userVOSearchDialog) {
		this.userVOSearchDialog = userVOSearchDialog;
	}

	public PagingTableModel<UserVO> getPagingUser() {
		return pagingUser;
	}

	public void setPagingUser(PagingTableModel<UserVO> pagingUser) {
		this.pagingUser = pagingUser;
	}

	public List<UserVO> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<UserVO> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getRoleSelectItem() {
		return roleSelectItem;
	}

	public void setRoleSelectItem(List<SelectItem> roleSelectItem) {
		this.roleSelectItem = roleSelectItem;
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


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	

	
	
}
