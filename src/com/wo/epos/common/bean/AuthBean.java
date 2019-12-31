package com.wo.epos.common.bean;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.util.ConfigUtil;
import com.wo.epos.common.util.ConfigUtil.AuthAnyoneUrl;
import com.wo.epos.common.util.ConfigUtil.NoAuthUrl;
import com.wo.epos.common.vo.PersonVO;
import com.wo.epos.module.uam.menu.vo.MenuVO;
import com.wo.epos.module.uam.user.vo.UserVO;

public class AuthBean {

	public boolean checkErrorPage(String viewId) {
		boolean isErrorPage = false;
		if (viewId.indexOf("401.jsf") >= 0)
			isErrorPage = true;
		else if (viewId.indexOf("403.jsf") >= 0)
			isErrorPage = true;
		else if (viewId.indexOf("404.jsf") >= 0)
			isErrorPage = true;
		else if (viewId.indexOf("500.jsf") >= 0)
			isErrorPage = true;
		else if (viewId.indexOf("view_expired_exception.jsf") >= 0)
			isErrorPage = true;
		else if (viewId.indexOf("document_generation_error.jsf") >= 0)
			isErrorPage = true;
		return isErrorPage;
	}

	public boolean checkExceptionPage(String viewId) {
		boolean isExceptionPage = false;
		if (viewId.indexOf("login.jsf") >= 0 || viewId.indexOf("loginP.jsf") >= 0)
			isExceptionPage = true;
		else if (viewId.indexOf("logout.jsf") >= 0)
			isExceptionPage = true;
		else if (viewId.indexOf(".css.jsf") >= 0)
			isExceptionPage = true;
		return isExceptionPage;
	}

	public boolean validateAccessRight(String viewId, UserVO loggedInPerson, List<MenuVO> menuList) {

		boolean isValid = true  ;

		if (checkErrorPage(viewId) || checkExceptionPage(viewId)) {

			if (menuList != null && menuList.size() > 0) {
				for (MenuVO m : menuList) {
					if (!m.getAction().equals("-")) {
						String authorizedUrl = m.getAction().split(".jsf")[0];
						m.getAction().replace(".jsf", "");
						if (viewId.indexOf(authorizedUrl) >= 0) {
							isValid = true;
							break;
						}
					}
					else{
						isValid = false;
						break;
					}
				}
			}
			else {
				isValid = false;
			}
/*
			return true;*/

		}

		/*
		 * List<MenuVO> listmenu = loggedInPerson.getListMenu(); if (listmenu !=
		 * null && listmenu.size() > 0) { for (MenuVO m : listmenu) { if
		 * (!m.getAction().equals("-")) { String authorizedUrl =
		 * m.getAction().split(".jsf")[0];// m.getAction().replace(".jsf", //
		 * ""); if (viewId.indexOf(authorizedUrl) >= 0) { isValid = true; break;
		 * } } } }
		 */
		return isValid;
	}

	public boolean viewIsIncludedInNoAuthReqList(String viewId) {
		String baseContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

		for (NoAuthUrl noAuthUrl : ConfigUtil.getInstance().getNoAuthUrls()) {
			String fullUrl = baseContextPath + noAuthUrl.url;
			if (noAuthUrl.isRegex) {
				if (viewId.matches(fullUrl)) {
					return true;
				}
			} else {
				if (StringUtils.equals(fullUrl, viewId)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean viewIsIncludedInAuthAnyoneList(String viewId) {
		String baseContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

		for (AuthAnyoneUrl authAnyoneUrl : ConfigUtil.getInstance().getAuthAnyoneUrls()) {
			String fullUrl = baseContextPath + authAnyoneUrl.url;
			if (authAnyoneUrl.isRegex) {
				if (viewId.matches(fullUrl)) {
					return true;
				}
			} else {
				if (StringUtils.equals(fullUrl, viewId)) {
					return true;
				}
			}

		}
		return false;
	}

	public boolean viewIsSuperAdminAuthList(String viewId, HttpSession session) {
		String baseContextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		if (StringUtils.equals(viewId, baseContextPath + "/faces/usm/checking_management/management_check.jsf")) {
			if (session != null && session.getAttribute(CommonConstants.SESSION_PERSON) != null) {
				PersonVO person = (PersonVO) session.getAttribute(CommonConstants.SESSION_PERSON);

				if (person.getPerson_id().equals("11020513"))
					return true;
			}
		}

		return false;
	}

}
