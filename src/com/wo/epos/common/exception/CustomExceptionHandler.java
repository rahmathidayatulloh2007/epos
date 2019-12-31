package com.wo.epos.common.exception;

import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.constant.LoginConstants;
import com.wo.epos.common.vo.PersonVO;
import com.wo.epos.common.vo.MenuVO;

class CustomExceptionHandler extends ExceptionHandlerWrapper {
	static Logger logger = Logger.getLogger(CustomExceptionHandler.class);
    private ExceptionHandler parent;

    public CustomExceptionHandler(ExceptionHandler parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.parent;
    }

    @Override
    public void handle() throws FacesException {
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            FacesContext fc = FacesContext.getCurrentInstance();
            NavigationHandler nav = fc.getApplication().getNavigationHandler();
            if (t instanceof ViewExpiredException || (t instanceof FacesException && t.getCause() != null &&
            		t.getCause().toString().contains("javax.el.PropertyNotFoundException") && 
            		t.getCause().toString().contains("loginBean.person.mymenu"))) {
                //ViewExpiredException vee = (ViewExpiredException) t;
            	if (t instanceof ViewExpiredException)
            		logger.debug("FacesException of ViewExpiredException");
            	else
            		logger.debug("FacesException of PropertyNotFoundException");
                try {
                    // Push some useful stuff to the flash scope for
                    // use in the page
                    //fc.getExternalContext().getFlash().put("expiredViewId", vee.getViewId());
                    nav.handleNavigation(fc, null, LoginConstants.COMPLETE_LOGIN_REDIRECT_EXPIRED_EXCEPTION);
                    fc.renderResponse();

                } finally {
                    i.remove();
                }
            } else if (t.getCause() != null && t.getCause().toString().equals("java.lang.NullPointerException") &&
            		t instanceof FacesException) {
            	logger.debug("FacesException of NullPointerException");
            	try {
	            	HttpServletRequest hreq = (HttpServletRequest) fc.getExternalContext().getRequest();
	        		String currentUrl = hreq.getRequestURI();
	        		String requestContext = fc.getExternalContext().getRequestContextPath();
	        		// strip the requestContext from current url
	        		currentUrl = currentUrl.substring(requestContext.length());
	        		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	        		PersonVO person = (PersonVO) session.getAttribute(CommonConstants.SESSION_PERSON);
	        		if (person != null) {
	        			List<MenuVO> listmenu = person.getListMenu();
	        			if (listmenu!=null && listmenu.size()>0) {
	        				for (MenuVO m : listmenu) {
	        					String url = m.getAction().replace(".jsf", "");
	        					if (currentUrl.indexOf(url)>=0) {
	        						nav.handleNavigation(fc, null, url + "?faces-redirect=true");
	        	                    fc.renderResponse();
	        					}
	        				}
	        			}
	        		} else {
	        			nav.handleNavigation(fc, null, LoginConstants.COMPLETE_LOGIN_REDIRECT_EXPIRED_EXCEPTION);
	                    fc.renderResponse();
	        		}
            	} finally {
            		i.remove();
            	}
            }
        }
        // At this point, the queue will not contain any ViewExpiredEvents.
        // Therefore, let the parent handle them.
        getWrapped().handle();
    }
}