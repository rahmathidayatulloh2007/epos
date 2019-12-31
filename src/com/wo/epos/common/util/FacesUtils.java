package com.wo.epos.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.el.ELException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FacesUtils implements Serializable {

	public static enum FacesScope {
		APPLICATION_SCOPE, FLASH_SCOPE, REQUEST_SCOPE, SESSION_SCOPE, VIEW_SCOPE
	}

	public static final String EREC_CONFIG_FILE = "/WEB-INF/epos-config.xml";

	public static String BUNDLE_PROPERTIES_LOCATION = "com.wo.epos.resources.ApplicationResources";

	public static final String FLAG_FALSE = "false";

	public static final String FLAG_TRUE = "true";

	private static final Log LOG = LogFactory.getLog(FacesUtils.class);

	private static final String OPTION_SELECTED = "TRUE";

	public static String REQUEST_CONTEXT_PATH;

	private static final long serialVersionUID = 8256754316740626573L;

	static {
		try {
			if (FacesContext.getCurrentInstance() != null)
				FacesUtils.REQUEST_CONTEXT_PATH = FacesContext
						.getCurrentInstance().getExternalContext()
						.getRequestContextPath().replace('\\', '/');
		} catch (final Exception ex) {
			FacesUtils.LOG.error("Failed on FacesUtil<init>, cause: "
					+ ex.getMessage());
		}
	}

	public static String getContextRelativePageLocation(final String location) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestContextPath()
				+ location;
	}

	/** checkbox (true/false) */
	public static boolean isBooleanOptionSelected(final String option) {
		return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(
				FacesUtils.OPTION_SELECTED, option);
	}

	/** LOV */
	public static boolean isLOVOptionSelected(final String option) {
		return !org.apache.commons.lang3.StringUtils.isBlank(option)
				&& org.apache.commons.lang3.StringUtils.equals(option, "");
	}

	private Locale defaultLocalizationBean;

	public FacesUtils() {
		try {
			/*
			 * this.defaultLocalizationBean = (Locale)
			 * this.getManagedBean("localizationBean",
			 * FacesScope.SESSION_SCOPE);
			 */
			BUNDLE_PROPERTIES_LOCATION = FacesContext.getCurrentInstance()
					.getApplication().getMessageBundle();
			this.defaultLocalizationBean = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestLocale();
		} catch (final Exception ex) {
			this.defaultLocalizationBean = Locale.getAvailableLocales()[0];
		}
	}

	public void addFacesMessage(final FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public void addFacesMessage(final Severity severity, final String message) {
		this.addFacesMessage(severity, message, message);
	}

	public void addFacesMessage(final Severity severity, final String summary,
			final String detail) {
		final FacesMessage fm = new FacesMessage(severity, summary, detail);

		this.addFacesMessage(fm);
	}

	public void addFacesMessage(final String component,
			final FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(component, facesMessage);
	}

	public void addFacesMessage(final String component,
			final Severity severity, final String message) {
		this.addFacesMessage(component, severity, message, message);
	}

	public void addFacesMessage(final String component,
			final Severity severity, final String summary, final String detail) {
		final FacesMessage fm = new FacesMessage(severity, summary, detail);

		this.addFacesMessage(component, fm);
	}

	public Locale getDefaultLocalizationBean() {
		return this.defaultLocalizationBean;
	}

	/*
	 * public LoginBean getDefaultLoginBean() { LoginBean result = null; try {
	 * result = (LoginBean) this.getManagedBean("loginBean",
	 * FacesScope.SESSION_SCOPE); } catch (final ClassCastException ex) { result
	 * = null; }
	 * 
	 * return result; }
	 */
	public Object getManagedBean(final String managedBeanName) {
		Object result = null;

		for (final FacesScope fs : FacesScope.values()) {
			result = this.getManagedBean(managedBeanName, fs);

			if (result != null) {
				break;
			}
		}

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getManagedBean(final String expression, final Class clazz) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context,
				expression, clazz);
	}

	public String getParameter(final String param) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(param);
	}

	public Object getAttribute(final ActionEvent actionEvent,
			final String attrib) {
		return actionEvent.getComponent().getAttributes().get(attrib);
	}

	public Object getSessionAttribute(String attributeName) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (session != null)
			return session.getAttribute(attributeName);
		else
			return null;
	}

	public void setSessionAttribute(String attributeName, Object attributeValue) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (session != null)
			session.setAttribute(attributeName, attributeValue);
	}

	public void removeSessionAttribute(String attributeName) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (session != null)
			session.removeAttribute(attributeName);
	}

	public void removeAllSessionAttribute() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (session != null)
			session.invalidate();
	}

	public Object getFlashAttribute(String attributeName) {
		Flash flash = (Flash) FacesContext.getCurrentInstance()
				.getExternalContext().getFlash();
		if (flash != null)
			return flash.get(attributeName);
		else
			return null;
	}

	public void setFlashAttribute(String attributeName, Object attributeValue) {
		Flash flash = (Flash) FacesContext.getCurrentInstance()
				.getExternalContext().getFlash();
		if (flash != null)
			flash.put(attributeName, attributeValue);
	}

	public void setFlashAttributeNow(String attributeName, Object attributeValue) {
		Flash flash = (Flash) FacesContext.getCurrentInstance()
				.getExternalContext().getFlash();
		if (flash != null)
			flash.putNow(attributeName, attributeValue);
	}

	public Object getManagedBean(final String managedBeanName,
			final FacesScope facesScope) {
		Object result = null;

		try {
			switch (facesScope) {
			case FLASH_SCOPE:
				result = FacesContext.getCurrentInstance().getExternalContext()
						.getFlash().get(managedBeanName);
				break;
			case REQUEST_SCOPE:
				result = FacesContext.getCurrentInstance().getExternalContext()
						.getRequestMap().get(managedBeanName);
				break;
			case VIEW_SCOPE:
				result = FacesContext.getCurrentInstance().getViewRoot()
						.getViewMap().get(managedBeanName);
				break;
			case SESSION_SCOPE:
				result = FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get(managedBeanName);
				break;
			case APPLICATION_SCOPE:
				result = FacesContext.getCurrentInstance().getExternalContext()
						.getApplicationMap().get(managedBeanName);
				break;
			default:
				result = null;
				break;

			}
		} catch (final Exception ex) {
			if (FacesUtils.LOG.isDebugEnabled()) {
				FacesUtils.LOG.debug("Error getting bean on FacesUtil, cause: "
						+ ex.getMessage());
			}
		}

		return result;
	}

	public ResourceBundle getResourceBundle(final String resourceBundleName) {
		return ResourceBundle.getBundle(resourceBundleName,
				this.defaultLocalizationBean);
	}

	public ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(BUNDLE_PROPERTIES_LOCATION,
				this.defaultLocalizationBean);
	}

	public String getResourceBundleStringValue(final String resourceBundleName,
			final String key) {
		return this.getResourceBundle(resourceBundleName).getString(key);
	}

	public String getResourceBundleStringValue(final String resourceBundleName,
			final String key, String params[]) {
		String text = this.getResourceBundle(resourceBundleName).getString(key);
		if (params != null) {
			MessageFormat mf = new MessageFormat(text,
					this.defaultLocalizationBean);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}

	public String getResourceBundleStringValue(final String key) {
		return this.getResourceBundle(BUNDLE_PROPERTIES_LOCATION)
				.getString(key);
	}

	public String getResourceBundleStringValue(final String key, String[] params) {
		String text = this.getResourceBundle(BUNDLE_PROPERTIES_LOCATION)
				.getString(key);
		if (params != null) {
			MessageFormat mf = new MessageFormat(text,
					this.defaultLocalizationBean);
			text = mf.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}

	public void removeAllManagedBeans(final FacesScope facesScope) {
		try {
			Set<String> keySet = null;

			switch (facesScope) {
			case FLASH_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext()
						.getFlash().keySet();
				break;
			case REQUEST_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext()
						.getRequestMap().keySet();
				break;
			case VIEW_SCOPE:
				keySet = FacesContext.getCurrentInstance().getViewRoot()
						.getViewMap().keySet();
				break;
			case SESSION_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().keySet();
				break;
			case APPLICATION_SCOPE:
				keySet = FacesContext.getCurrentInstance().getExternalContext()
						.getApplicationMap().keySet();
				break;
			default:
				keySet = null;
				break;
			}

			if (keySet != null) {
				for (final String key : keySet) {
					if (!key.startsWith("SESSION"))
						this.removeManagedBean(key, facesScope);
				}
			}
		} catch (final Exception ex) {
			if (FacesUtils.LOG.isDebugEnabled()) {
				FacesUtils.LOG
						.debug("Error removing bean on FacesUtil, cause: "
								+ ex.getMessage());
			}
		}
	}

	public boolean removeManagedBean(final String managedBeanName) {
		boolean result = false;

		for (final FacesScope fs : FacesScope.values()) {
			if (this.removeManagedBean(managedBeanName, fs)) {
				result = true;
				break;
			}
		}

		return result;
	}

	public <T> T evaluateExpressionGet(String expression,
			Class<? extends T> expectedType) throws ELException {
		FacesContext fc = FacesContext.getCurrentInstance();
		return fc.getApplication().evaluateExpressionGet(fc, expression,
				expectedType);
	}

	@SuppressWarnings("unchecked")
	public <T> T evaluateExpressionGet(String beanName) throws ELException {
		FacesContext fc = FacesContext.getCurrentInstance();
		return (T) fc.getApplication().evaluateExpressionGet(fc,
				"#{" + beanName + "}", Object.class);
	}

	public boolean removeManagedBean(final String managedBeanName,
			final FacesScope facesScope) {
		boolean result = false;

		try {
			switch (facesScope) {
			case FLASH_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext()
						.getFlash().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case REQUEST_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext()
						.getRequestMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case VIEW_SCOPE:
				if (FacesContext.getCurrentInstance().getViewRoot()
						.getViewMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case SESSION_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			case APPLICATION_SCOPE:
				if (FacesContext.getCurrentInstance().getExternalContext()
						.getApplicationMap().remove(managedBeanName) != null) {
					result = true;
				}
				break;
			default:
				result = false;
				break;
			}
		} catch (final Exception ex) {
			if (FacesUtils.LOG.isDebugEnabled()) {
				FacesUtils.LOG
						.debug("Error removing bean on FacesUtil, cause: "
								+ ex.getMessage());
			}
		}

		return result;
	}

	public void redirect(final String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}

	public void redirectCurrentUrl() {
		FacesContext fc = FacesContext.getCurrentInstance();
		NavigationHandler nav = fc.getApplication().getNavigationHandler();
		HttpServletRequest hreq = (HttpServletRequest) fc.getExternalContext()
				.getRequest();
		String currentUrl = hreq.getRequestURI();
		String reqContextPath = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestContextPath();
		if (currentUrl.contains(reqContextPath))
			currentUrl = currentUrl.substring(reqContextPath.length());
		nav.handleNavigation(fc, null, currentUrl + "?faces-redirect=true");
		fc.renderResponse();
	}

	static public String handleParam(String input) {
		if (input == null || input.trim().equals(""))
			return input;
		return input.replace("\\", "\\\\").replaceAll("'", "\\\\\'")
				.replaceAll("\"", "\\\\\'\\\\\'");
	}

	public static UIComponent getUiComponent(String id) {
		return FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("frmEventEvaluationEdit:file_path");
	}
	
	public String retrieveRequestParam(String key) {
	    return FacesContext.getCurrentInstance()
	            .getExternalContext().getRequestParameterMap().get(key);
	}
	
	public void addFacesMsg(
            FacesMessage.Severity severity, String forComp, String msg, String detail) {
        FacesMessage message = new FacesMessage(
                                        severity,
                                        msg,
                                        detail);
        FacesContext.getCurrentInstance().addMessage(forComp, message);
        
    }
	
	public String retrieveMessage(String key) {
        System.out.println("locale " + FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        return bundle.getString(key);        
    }
	
	public String retrieveMessage(String key, String ... values ) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
        String msg = bundle.getString(key);        
        
        for (int i = 0 ; i < values.length ; i++) {
            msg = msg.replace("{" + (i) + "}", values[i]);
        }
        
        return msg;
    }
}