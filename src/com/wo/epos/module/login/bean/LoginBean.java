package com.wo.epos.module.login.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.constant.LoginConstants;
import com.wo.epos.common.util.FacesUtils;
import com.wo.epos.common.util.FacesUtils.FacesScope;
import com.wo.epos.module.login.service.LoginService;
import com.wo.epos.module.uam.employee.service.EmployeeService;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.menu.service.MenuService;
import com.wo.epos.module.uam.menu.vo.MenuVO;
import com.wo.epos.module.uam.user.service.UserService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -726767918480557937L;

	private String username;
	private String password;
	static Logger logger = Logger.getLogger(LoginBean.class);

	@ManagedProperty(value = "#{loginService}")
	private LoginService loginService;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;

	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService employeeService;

	@ManagedProperty(value = "#{menuService}")
	private MenuService menuService;

	protected EmployeeVO employeeVO;
	protected UserVO userVO;

	ExternalContext externalContext;

	@PostConstruct
	public void postConstruct() {

		externalContext = FacesContext.getCurrentInstance().getExternalContext();
		if (FacesContext.getCurrentInstance() != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			HttpSession session = request.getSession(true);
			if (session != null && session.getAttribute(CommonConstants.SESSION_USER) != null) {
				userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);
				try {
					externalContext.redirect(
							externalContext.getApplicationContextPath() + LoginConstants.COMPLETE_LOGIN_DASHBOARD);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}

		}
	}

	public void doHandleLogOut() {
		String nextUrlLogout = LoginConstants.COMPLETE_LOGIN_REDIRECT;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null) {
			try {
				HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
				HttpSession session = request.getSession(true);
				if (session != null) {
					userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);
					userVO.setLoginFlag(CommonConstants.N);
					userService.update(userVO, userVO.getUserCode());
					session.removeAttribute(CommonConstants.SESSION_USER);
					FacesUtils fu = new FacesUtils();
					fu.removeAllManagedBeans(FacesScope.SESSION_SCOPE);
					session.invalidate();
				}
			} catch (Exception ex) {
				FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Logout Error:" + ex.getMessage(), ex.getMessage());
				fc.addMessage(null, errorMessage);
				logger.error(ex.getMessage(), ex);
			}
			fc.getApplication().getNavigationHandler().handleNavigation(fc, null, nextUrlLogout); // LoginConstants.COMPLETE_LOGIN_REDIRECT);
		}
		 
	}

	public String doHandleLogin() {
		String afterLoginUrl = "";
		logger.debug("loginBean doHandleLogin");
		String result = null;
		try {
			userVO = userService.getUserNamePassword(username, password);

			if (userVO != null) {

				if (userVO.getLoginFlag() != null && userVO.getLoginFlag().equals(CommonConstants.Y)) {
					throw new Exception("User Name " + username + " already login");
				}

				employeeVO = createEmployeeVO(userVO.getEmployeeId());

				if (StringUtils.isNotEmpty(afterLoginUrl)) {
					result = afterLoginUrl;
				} else {
					result = LoginConstants.COMPLETE_LOGIN_DASHBOARD;
				}

				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
						.getRequest();
				HttpSession session = request.getSession(true);
				session.setAttribute(CommonConstants.SESSION_EMPLOYEE, employeeVO);
				session.setAttribute(CommonConstants.SESSION_USER, userVO);
				session.setMaxInactiveInterval(61 * 60); // in seconds

				List<MenuVO> menuList = loginService.searchMenuDasboard(userVO.getUserId());
				session.setAttribute(CommonConstants.SESSION_MENU, menuList);

				userVO.setFirstLoginFlag(CommonConstants.Y);
				userVO.setLoginFlag(CommonConstants.Y);
				userVO.setPrevLogin(new Timestamp(System.currentTimeMillis()));
				userService.update(userVO, userVO.getUserCode());
				externalContext.redirect(externalContext.getApplicationContextPath() + result);

			} else {
				FacesUtils fu = new FacesUtils();
				FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						fu.getResourceBundleStringValue("errorLoginAttemptFailNoParam"), result);
				FacesContext.getCurrentInstance().addMessage(null, errorMessage);
				logger.error(errorMessage.getSummary());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error:" + ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			result = null;
		} finally {
			afterLoginUrl = "usertaskpage.jsp";
		}
		return result;
	}

	private EmployeeVO createEmployeeVO(Long employeeId) throws SQLException, Exception {
		return loginService.getEmployee(employeeId);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		LoginBean.logger = logger;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeVO getEmployeeVO() {
		return employeeVO;
	}

	public void setEmployeeVO(EmployeeVO employeeVO) {
		this.employeeVO = employeeVO;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public ExternalContext getExternalContext() {
		return externalContext;
	}

	public void setExternalContext(ExternalContext externalContext) {
		this.externalContext = externalContext;
	}

}