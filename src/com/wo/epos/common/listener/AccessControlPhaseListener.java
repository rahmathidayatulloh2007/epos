package com.wo.epos.common.listener;

import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.AuthBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.constant.LoginConstants;
import com.wo.epos.module.login.bean.LoginBean;
import com.wo.epos.module.uam.menu.vo.MenuVO;
import com.wo.epos.module.uam.user.vo.UserVO;

public class AccessControlPhaseListener implements PhaseListener {
	private static final long serialVersionUID = -407781932726331562L;
	/** Logger for this class */
	private static final Logger logger = Logger.getLogger(AccessControlPhaseListener.class);

	private UserVO userVO;
	
	List<MenuVO> menuList;

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

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

	public void afterPhase(PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		HttpServletRequest hreq = (HttpServletRequest) fc.getExternalContext().getRequest();
		String viewId = hreq.getRequestURI();

		// LoginBean loginBean = new LoginBean();
		AuthBean authBean = new AuthBean();
		
		
		
		if (!sessionLoginIsExist(session)) {
			
			if (userVO != null) {
				ELContext elContext = FacesContext.getCurrentInstance().getELContext();
				LoginBean loginBean = (LoginBean) FacesContext.getCurrentInstance().getApplication().getELResolver()
						.getValue(elContext, null, "loginBean");
				userVO.setLoginFlag(CommonConstants.N);
				loginBean.getUserService().update(userVO, userVO.getUserCode());
			}
			
			if (authBean.viewIsIncludedInNoAuthReqList(viewId)) {
				// allow this
			} else if (authBean.checkExceptionPage(viewId) || authBean.checkErrorPage(viewId)) {
				// allow this
			} else {
				
				logger.debug("Session is not availab;le or user not logged in");
				fc.getApplication().getNavigationHandler().handleNavigation(fc, null,
						LoginConstants.COMPLETE_LOGIN_REDIRECT);
				fc.renderResponse();
				return;
			}
		} else {
			if (authBean.viewIsIncludedInNoAuthReqList(viewId)) {
				// allow this
			} else if (authBean.viewIsIncludedInAuthAnyoneList(viewId)) {
				// allow this
			} else if (authBean.viewIsSuperAdminAuthList(viewId, session)) {
				// allow this
			} else {
				
				userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);
				menuList = (List<MenuVO>) session.getAttribute(CommonConstants.SESSION_MENU);

				boolean isValid = authBean.validateAccessRight(viewId, userVO, menuList);
				if (!isValid) {
					logger.debug("User Logged in but trying to access unauthorized page");
					fc.getApplication().getNavigationHandler().handleNavigation(fc, null,
							LoginConstants.COMPLETE_INVALID_ACCESS_REDIRECT);
					fc.renderResponse();
				}
			}
		}
	}

	private boolean sessionLoginIsExist(HttpSession session) {
		if (session == null || session.getAttribute(CommonConstants.SESSION_USER) == null) {
			return false;
		} else {
			return true;
		}
	}

	public void beforePhase(PhaseEvent event) {
	}
}