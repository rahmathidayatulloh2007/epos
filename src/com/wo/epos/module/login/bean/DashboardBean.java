package com.wo.epos.module.login.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.login.service.LoginService;
import com.wo.epos.module.uam.employee.service.EmployeeService;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.menu.service.MenuService;
import com.wo.epos.module.uam.menu.vo.MenuVO;
import com.wo.epos.module.uam.user.service.UserService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "dashboardBean")
@RequestScoped
public class DashboardBean extends CommonBean implements Serializable {
	
	private static final long serialVersionUID = -5542786234895767931L;
	
	static Logger logger = Logger.getLogger(DashboardBean.class);
		
	@ManagedProperty(value = "#{loginService}")
	private LoginService loginService;	
	
	@ManagedProperty(value = "#{userService}")
	private UserService userService;
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService employeeService;
	
	@ManagedProperty(value = "#{menuService}")
	private MenuService menuService;
	
	private EmployeeVO employeeVO;
	private UserVO userVO;

	private List<MenuVO> menuList;
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void postConstruct(){
		super.init();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext()
				.getRequest();
		HttpSession session = request.getSession(true);
		userVO = (UserVO)session.getAttribute(CommonConstants.SESSION_USER);
		//menuList = loginService.searchMenuDasboard(userVO.getUserId());
		menuList = (List<MenuVO>) session.getAttribute(CommonConstants.SESSION_MENU);
		
    }

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DashboardBean.logger = logger;
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

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public EmployeeVO getEmployeeVO() {
		return employeeVO;
	}

	public void setEmployeeVO(EmployeeVO employeeVO) {
		this.employeeVO = employeeVO;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public List<MenuVO> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<MenuVO> menuList) {
		this.menuList = menuList;
	}

	

	
	
	
	
}