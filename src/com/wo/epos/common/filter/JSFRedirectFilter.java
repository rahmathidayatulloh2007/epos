package com.wo.epos.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.constant.LoginConstants;

public class JSFRedirectFilter implements Filter {

	private static final String JSF_EXTENSION = "jsf";

	@SuppressWarnings("unused")
	private FilterConfig filterConfig;

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession(true);

		String requestURI = httpServletRequest.getRequestURI();
		String newRequestURI = "";

		if (requestURI.toLowerCase().endsWith(".jsp")
				|| requestURI.toLowerCase().endsWith(".asp")
				|| requestURI.toLowerCase().endsWith(".php")) {
			// redirect only if direct jsf call
			newRequestURI = requestURI.substring(0, requestURI.length() - 3)
					+ JSFRedirectFilter.JSF_EXTENSION;
			httpServletResponse.sendRedirect(newRequestURI);
		} else if (requestURI.toLowerCase().endsWith(".html")) {
			newRequestURI = requestURI.substring(0, requestURI.length() - 4)
					+ JSFRedirectFilter.JSF_EXTENSION;
			httpServletResponse.sendRedirect(newRequestURI);
		} else if (requestURI.toLowerCase().endsWith(".faces")
				|| requestURI.toLowerCase().endsWith(".xhtml")) {
			newRequestURI = requestURI.substring(0, requestURI.length() - 5)
					+ JSFRedirectFilter.JSF_EXTENSION;
			httpServletResponse.sendRedirect(newRequestURI);
		} else if (requestURI.toLowerCase().contains("/faces/")
				&& requestURI.toLowerCase().endsWith("/")) {
			if (session != null
					&& session.getAttribute(CommonConstants.SESSION_PERSON) != null) {
				newRequestURI = httpServletRequest.getContextPath()
						+ LoginConstants.COMPLETE_HOME_URL;
				httpServletResponse.sendRedirect(newRequestURI);
			} else {
				newRequestURI = httpServletRequest.getContextPath()
						+ LoginConstants.COMPLETE_LOGIN;
				httpServletResponse.sendRedirect(newRequestURI);
			}
		} else if (requestURI.toLowerCase().endsWith("/")) {
			if (session != null
					&& session.getAttribute(CommonConstants.SESSION_PERSON) != null) {
				newRequestURI = httpServletRequest.getContextPath()
						+ LoginConstants.COMPLETE_HOME_URL;
				httpServletResponse.sendRedirect(newRequestURI);
			} else {
				newRequestURI = httpServletRequest.getContextPath()
						+ LoginConstants.COMPLETE_LOGIN;
				httpServletResponse.sendRedirect(newRequestURI);
			}
		} else if ((requestURI.contains("login")
				|| requestURI.contains("500.jsf")
				|| requestURI.contains("401.jsf")
				|| requestURI.contains("403.jsf") || requestURI
					.contains("404.jsf"))) {
			try {
				filterChain.doFilter(request, response);
			} catch (ServletException ex) {
				newRequestURI = httpServletRequest.getContextPath()
						+ LoginConstants.COMPLETE_LOGIN;
				httpServletResponse.sendRedirect(newRequestURI);
			}
		} else {
			// call next filter in chain
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}