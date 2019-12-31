package com.wo.epos.module.sales.payment.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;

@ManagedBean(name = "paymentBean")
public class PaymentBean extends CommonBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4793375876743414033L;

	static Logger logger = Logger.getLogger(PaymentBean.class);
	
	private String MODE_TYPE;

	@ManagedProperty(value = "#{paymentInputBean}")
	private PaymentInputBean paymentInputBean;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;
	
	
	@PostConstruct
	public void postConstruct(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		if(userSession != null){
			SystemProperty systemProperty = new SystemProperty();		
			if(userSession.getOutletId() !=null){
				Outlet outletLogin = outletService.findById(userSession.getOutletId());
				if(outletLogin.getCompany() !=null){
					systemProperty = systemPropertyService.searchSystemPropertyNameAndCompany(CommonConstants.BUSINESS_TYPE, outletLogin.getCompany().getCompanyId()); 
					MODE_TYPE = systemProperty.getSystemPropertyValue();
				}
			}		
		}
	}
	
	public void modeAdd(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		paymentInputBean.setMODE_TYPE("ADD");
		paymentInputBean.modeAdd();
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PaymentBean.logger = logger;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public PaymentInputBean getPaymentInputBean() {
		return paymentInputBean;
	}

	public void setPaymentInputBean(PaymentInputBean paymentInputBean) {
		this.paymentInputBean = paymentInputBean;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}


	
	

}
