package com.wo.epos.module.uam.user.bean;

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
import com.wo.epos.module.uam.employee.service.EmployeeService;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.role.service.RoleService;
import com.wo.epos.module.uam.role.vo.RoleVO;
import com.wo.epos.module.uam.user.service.UserService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "userInputBean")
@ViewScoped
public class UserInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 3982389021994369485L;
	static Logger logger = Logger.getLogger(UserInputBean.class);

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	@ManagedProperty(value = "#{roleService}")
	private RoleService roleService;

	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService employeeService;

	private UserVO userVO = new UserVO();

	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> employeeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> roleSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;

	@PostConstruct
	public void postConstruct() {
		super.init();

		userVO = new UserVO();

		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));

		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for (CompanyVO com : companySelectList) {
			companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
		}
		// ====================================================================
		List<RoleVO> roleSelectList = roleService.searchRoleList();
		roleSelectItem = new ArrayList<SelectItem>();
		for (RoleVO role : roleSelectList) {
			roleSelectItem.add(new SelectItem(role.getRoleId(), role.getRoleName()));
		}

		List<EmployeeVO> employeeSelectList = employeeService.searchEmployeeList();
		employeeSelectItem = new ArrayList<SelectItem>();
		for (EmployeeVO emp : employeeSelectList) {
			employeeSelectItem.add(new SelectItem(emp.getEmployeeId(), emp.getFullName()));
		}
		// ====================================================================

		/* initListAdd(); */

		MODE_TYPE = "ADD";

	}

	public void initListAdd() {
		Long companyId = null;
		if (userSession.getCompanyId() != null) {
			companyId = userSession.getCompanyId();
		} else {
			companyId = null;
		}

		roleSelectItem = new ArrayList<SelectItem>();
		employeeSelectItem = new ArrayList<SelectItem>();
	}

	public void initListEdit(Long companyId) {

		List<RoleVO> roleSelectList = roleService.searchRoleListByCompany(companyId);
		roleSelectItem = new ArrayList<SelectItem>();
		for (RoleVO role : roleSelectList) {
			roleSelectItem.add(new SelectItem(role.getRoleId(), role.getRoleName()));
		}

		List<EmployeeVO> employeeSelectList = employeeService.searchEmployeeListByCompany(companyId);
		employeeSelectItem = new ArrayList<SelectItem>();
		for (EmployeeVO emp : employeeSelectList) {
			employeeSelectItem.add(new SelectItem(emp.getEmployeeId(), emp.getFullName()));
		}

	}

	public void save() {
		try {
			if (userSession.getCompanyId() != null) {
				userVO.setCompanyId(userSession.getCompanyId());
			}

			if (MODE_TYPE.equals("ADD")) {
				userService.save(userVO, userSession.getUserCode());

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
						null);
			} else if (MODE_TYPE.equals("EDIT")) {
				userService.update(userVO, userSession.getUserCode());

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")),
						null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:messages",
					"Operation Failed : " + ex.getMessage(), null);
		}
	}

	public void modeAdd() {
		userVO = new UserVO();
	}

	public void modeEdit(List<UserVO> userList) {
		MODE_TYPE = "EDIT";
		for (int i = 0; i < userList.size(); i++) {
			UserVO userVOTemp = (UserVO) userList.get(i);
			userVO.setUserId(userVOTemp.getUserId());
			userVO.setEmployeeId(userVOTemp.getEmployeeId());
			userVO.setEmployeeName(userVOTemp.getEmployeeName());
			if (userVOTemp.getCompanyId() != null) {
				userVO.setCompanyId(userVOTemp.getCompanyId());
				userVO.setCompanyName(userVOTemp.getCompanyName());

				initListEdit(userVOTemp.getCompanyId());
			}
			userVO.setRoleId(userVOTemp.getRoleId());
			userVO.setUserCode(userVOTemp.getUserCode());
			userVO.setUserPassword(userVOTemp.getUserPassword());
			userVO.setActiveFlag(userVOTemp.getActiveFlag());
		}

	}

	public boolean validateKosong() {
		boolean valid = true;

		if (userVO.getUserCode() == null || userVO.getUserCode().trim().isEmpty() && MODE_TYPE == "ADD") {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesInput",
					facesUtils.getResourceBundleStringValue("formUserName") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;
		}
		if (userVO.getUserPassword() == null || userVO.getUserPassword().trim().isEmpty() && MODE_TYPE == "ADD") {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesInput",
					facesUtils.getResourceBundleStringValue("formUserPassword") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;
		}
		if (MODE_TYPE == "ADD") {
			if (userVO.getUserPasswdConfirm() == null || userVO.getUserPasswdConfirm().trim().isEmpty()) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesInput",
						facesUtils.getResourceBundleStringValue("formUserConfirmasiPassword") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}
		}
		if (!userVO.getUserPassword().equals(userVO.getUserPasswdConfirm()) && MODE_TYPE == "ADD") {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesInput",
					facesUtils.getResourceBundleStringValue("formUserMatchPassword"));
			valid = false;
		}
		if (userVO.getEmployeeId() == null && MODE_TYPE == "ADD") {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesInput",
					facesUtils.getResourceBundleStringValue("formEmployeeTitle") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;
		}
		if (userVO.getRoleId() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesInput",
					facesUtils.getResourceBundleStringValue("formRoleTitle") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;
		}

		return valid;

	}

	public void changeUserCompany() {
		List<RoleVO> roleSelectList = roleService.searchRoleListByCompany(userVO.getCompanyId());
		roleSelectItem = new ArrayList<SelectItem>();
		for (RoleVO role : roleSelectList) {
			roleSelectItem.add(new SelectItem(role.getRoleId(), role.getRoleName()));
		}

		List<EmployeeVO> employeeSelectList = employeeService.searchEmployeeListByCompany(userVO.getCompanyId());
		employeeSelectItem = new ArrayList<SelectItem>();
		for (EmployeeVO emp : employeeSelectList) {
			employeeSelectItem.add(new SelectItem(emp.getEmployeeId(), emp.getFullName()));
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		UserInputBean.logger = logger;
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

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
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

	public List<SelectItem> getEmployeeSelectItem() {
		return employeeSelectItem;
	}

	public void setEmployeeSelectItem(List<SelectItem> employeeSelectItem) {
		this.employeeSelectItem = employeeSelectItem;
	}

	public List<SelectItem> getRoleSelectItem() {
		return roleSelectItem;
	}

	public void setRoleSelectItem(List<SelectItem> roleSelectItem) {
		this.roleSelectItem = roleSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

}
