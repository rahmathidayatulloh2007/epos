package com.wo.epos.module.sales.kitchenMonitoring.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.sales.kitchenMonitoring.service.KitchenMonitoringService;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

@ManagedBean(name = "kitchenMonitoringMainBean")
@ViewScoped
public class KitchenMonitoringMainBean extends CommonBean implements Serializable{

   private static final long serialVersionUID = 1911394853351658710L;

	static Logger logger = Logger.getLogger(KitchenMonitoringMainBean.class);
			
	@ManagedProperty(value = "#{kitchenMonitoringService}")
	private KitchenMonitoringService kitchenMonitoringService;
	
	private PagingTableModel<SalesOrderDtlVO> pagingSalesOrderDtl;	
	
	private List<SalesOrderDtlVO> selectedSalesOrderDtlVos;
		
	private String MODE_TYPE;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		searchCriteria.add(new SearchValueObject("preparationStatus", CommonConstants.PREPARATION_HOLD));
		 
		pagingSalesOrderDtl = new PagingTableModel<SalesOrderDtlVO>(kitchenMonitoringService, paging);		
		pagingSalesOrderDtl.setSearchCriteria(searchCriteria);
		
	}
	
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		KitchenMonitoringMainBean.logger = logger;
	}
	
	public KitchenMonitoringService getKitchenMonitoringService() {
		return kitchenMonitoringService;
	}

	public void setKitchenMonitoringService(
			KitchenMonitoringService kitchenMonitoringService) {
		this.kitchenMonitoringService = kitchenMonitoringService;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}
	
	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public PagingTableModel<SalesOrderDtlVO> getPagingSalesOrderDtl() {
		return pagingSalesOrderDtl;
	}

	public void setPagingSalesOrderDtl(
			PagingTableModel<SalesOrderDtlVO> pagingSalesOrderDtl) {
		this.pagingSalesOrderDtl = pagingSalesOrderDtl;
	}


	public List<SalesOrderDtlVO> getSelectedSalesOrderDtlVos() {
		return selectedSalesOrderDtlVos;
	}


	public void setSelectedSalesOrderDtlVos(
			List<SalesOrderDtlVO> selectedSalesOrderDtlVos) {
		this.selectedSalesOrderDtlVos = selectedSalesOrderDtlVos;
	}

	
	
}
