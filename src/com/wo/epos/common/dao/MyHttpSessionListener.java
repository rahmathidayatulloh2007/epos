package com.wo.epos.common.dao;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


import com.wo.epos.common.bean.CommonBean;

public class MyHttpSessionListener extends CommonBean implements HttpSessionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
    @Override
    public void sessionCreated(HttpSessionEvent event){
      event.getSession().setMaxInactiveInterval(60 * 60); // in seconds
      
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	
    }
  
    

}