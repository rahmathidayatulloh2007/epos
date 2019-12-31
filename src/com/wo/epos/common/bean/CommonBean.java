package com.wo.epos.common.bean;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.constant.LoginConstants;
import com.wo.epos.common.constant.NavigationConstants;
import com.wo.epos.common.exception.ExceptionHandlerFactory;
import com.wo.epos.common.util.ConfigUtil;
import com.wo.epos.common.util.DateUtil;
import com.wo.epos.common.util.FacesUtils;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.user.vo.UserVO;

public class CommonBean implements Serializable {
	private static final long serialVersionUID = 1493101021908223346L;
	static Logger logger = Logger.getLogger(CommonBean.class);
	public FacesUtils facesUtils;
	protected String enabledFlag;
	public int paging;
	protected boolean enabledFlag2;
	protected final String insertAction = NavigationConstants.MODE_INSERT;
	protected final String viewAction = NavigationConstants.MODE_VIEW;
	protected final String dateFormat = CommonConstants.INPUT_DATE_FORMAT;
	protected final String dateLocale = CommonConstants.INPUT_DATE_LOCALE;
	protected final String dateTimeFormat = CommonConstants.INPUT_DATE_TIME_FORMAT;
	protected final String methodParam = CommonConstants.METHOD_PARAMETER;
	protected final SimpleDateFormat sdf = new SimpleDateFormat(
			CommonConstants.INPUT_DATE_FORMAT);
	protected final SimpleDateFormat sdf2 = new SimpleDateFormat(
			CommonConstants.INPUT_DATE_TIME_FORMAT);
	
	protected final SimpleDateFormat time = new SimpleDateFormat(CommonConstants.INPUT_TIME_FORMAT);
	protected final SimpleDateFormat dateYear = new SimpleDateFormat(CommonConstants.INPUT_DATE_YEAR_FORMAT);
	protected String userId; // -> assume employee nik in this var.
	protected String exportFileName;
	protected ConfigUtil config;
	
	public EmployeeVO employeeSession;
	public UserVO userSession;

	public void init() {
		facesUtils = new FacesUtils();
		config = ConfigUtil.getInstance();
		// SystemPropertyManager systemPropertyManager = new
		// SystemPropertyManager();
		try {
			if (facesUtils.getSessionAttribute(CommonConstants.SESSION_EMPLOYEE) != null) {
				employeeSession = (EmployeeVO) facesUtils.getSessionAttribute(CommonConstants.SESSION_EMPLOYEE);
				userSession = (UserVO) facesUtils.getSessionAttribute(CommonConstants.SESSION_USER);
				
				userId = employeeSession.getEmployeeNo();
			}
			
			paging = CommonConstants.DEFAULT_PAGINATION_ROWS;
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);/*
			facesUtils.setSessionAttribute(
					CommonConstants.SESSION_PAGING_NUMBER, new Integer(paging));*/
		} finally {
			// systemPropertyManager = null;
		}
	}
	
	public String getUserLevel(){
		if(userSession.getCompanyId() == null)
		{
			return CommonConstants.ADMIN_LEVEL;
		}
		else
		{
			if(userSession.getOutletId() != null)
			{
				return CommonConstants.OUTLET_LEVEL;
			}
			
			return CommonConstants.COMPANY_LEVEL;
		}
	}

	public static Date convertInputDateFromDDMMYYYY(String date)
			throws ParseException {
		return DateUtil.stringToDateFromDDMMYYYY(date);
	}

	public static Date convertInputDateFromDDMMMYYYY(String date)
			throws ParseException {
		return DateUtil.stringToDateFromDDMMMYYYY(date);
	}

	public FacesUtils getFacesUtils() {
		return facesUtils;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;

		if (enabledFlag != null && !enabledFlag.equals("")) {
			if (enabledFlag.equals(CommonConstants.ENABLED_FLAG_TRUE)
					&& !getEnabledFlag2()) {
				setEnabledFlag2(true);
			} else if (enabledFlag.equals(CommonConstants.ENABLED_FLAG_FALSE)
					&& getEnabledFlag2()) {
				setEnabledFlag2(false);
			}
		} else {
			setEnabledFlag(CommonConstants.ENABLED_FLAG_TRUE);
		}
	}

	public boolean getEnabledFlag2() {
		return enabledFlag2;
	}

	public void setEnabledFlag2(boolean enabledFlag2) {
		this.enabledFlag2 = enabledFlag2;

		if (enabledFlag != null && !enabledFlag.equals("")) {
			if (enabledFlag2
					&& enabledFlag.equals(CommonConstants.ENABLED_FLAG_FALSE))
				setEnabledFlag(CommonConstants.ENABLED_FLAG_TRUE);
			else if (!enabledFlag2
					&& enabledFlag.equals(CommonConstants.ENABLED_FLAG_TRUE)) {
				setEnabledFlag(CommonConstants.ENABLED_FLAG_FALSE);
			}
		} else {
			if (enabledFlag2)
				setEnabledFlag(CommonConstants.ENABLED_FLAG_TRUE);
			else
				setEnabledFlag(CommonConstants.ENABLED_FLAG_FALSE);
		}
	}
	
	public void addFacesMsg(
            FacesMessage.Severity severity, String forComp, String msg, String detail) {
        FacesMessage message = new FacesMessage(
                                        severity,
                                        msg,
                                        detail);
        FacesContext.getCurrentInstance().addMessage(forComp, message);
        
    }

	public String getInsertAction() {
		return insertAction;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public String getDateLocale() {
		return dateLocale;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getViewAction() {
		return viewAction;
	}

	protected int getHHMMSS(String input, int time) throws Exception {
		return DateUtil.getHourMinuteSecond(input, time);
	}

	
	public void getSQLExceptionErrorMessage(final SQLException ex) {
		ExceptionHandlerFactory.getSQLExceptionErrorMessage(ex, facesUtils);
	}

	protected void invokeMethod(Object beanObject) {
		String parameter = facesUtils.getParameter(getMethodParam());
		if (parameter != null && !parameter.isEmpty()) {
			try {
				Method method = beanObject.getClass().getMethod(parameter);
				method.invoke(beanObject);
			} catch (NoSuchMethodException ex) {
				logger.error("NoSuchMethodException:" + ex.getMessage());
			} catch (InvocationTargetException ex) {
				logger.error("InvocationTargetException:" + ex.getMessage());
			} catch (IllegalAccessException ex) {
				logger.error("IllegalAccessException:" + ex.getMessage());
			}
		}
	}

	public String getMethodParam() {
		return methodParam;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public TimeZone getTimeZone() {
		return DateUtil.getDefaultTimeZone();
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public SimpleDateFormat getSdf2() {
		return sdf2;
	}

	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	protected String getParam(String id) {
		return facesUtils.getParameter(id) == null ? "" : facesUtils
				.getParameter(id);
	}

	protected String getResource(String resourceKey) {
		return facesUtils.getResourceBundleStringValue(resourceKey);
	}

	public SimpleDateFormat getTime() {
		return time;
	}

	public SimpleDateFormat getDateYear() {
		return dateYear;
	}

	public int getPaging() {
		return paging;
	}

	public void setPaging(int paging) {
		this.paging = paging;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CommonBean.logger = logger;
	}

	public ConfigUtil getConfig() {
		return config;
	}

	public void setConfig(ConfigUtil config) {
		this.config = config;
	}

	public EmployeeVO getEmployeeSession() {
		return employeeSession;
	}

	public void setEmployeeSession(EmployeeVO employeeSession) {
		this.employeeSession = employeeSession;
	}

	public UserVO getUserSession() {
		return userSession;
	}

	public void setUserSession(UserVO userSession) {
		this.userSession = userSession;
	}

}