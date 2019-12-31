package com.wo.epos.module.sales.salesOrderMonitoring.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.sales.salesOrderMonitoring.service.SalesOrderMonitoringService;

@ManagedBean(name = "salesOrderMonitoringSearchBean")
@ViewScoped
public class SalesOrderMonitoringSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 5173492607946960460L;

	static Logger logger = Logger.getLogger(SalesOrderMonitoringSearchBean.class);
			
	@ManagedProperty(value = "#{salesOrderMonitoringService}")
	private SalesOrderMonitoringService salesOrderMonitoringService;
	
	private PagingTableModel<SalesOrderDtlVO> pagingSalesOrderDtl;	
	
	private List<SalesOrderDtlVO> selectedSalesOrderDtlVos;
		
	private String MODE_TYPE;
	
	private Date salesOrderDate;
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		salesOrderDate = new Date();
		
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();

		pagingSalesOrderDtl = new PagingTableModel<SalesOrderDtlVO>(salesOrderMonitoringService, paging);	
		
		searchCriteria.add(new SearchValueObject("salesOrderDate", salesOrderDate));
		pagingSalesOrderDtl.setSearchCriteria(searchCriteria);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void buttonSearch(){
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		
		if(salesOrderDate !=null){
			searchCriteria.add(new SearchValueObject("salesOrderDate", salesOrderDate));
		}
		 
		pagingSalesOrderDtl = new PagingTableModel<SalesOrderDtlVO>(salesOrderMonitoringService, paging);		
		pagingSalesOrderDtl.setSearchCriteria(searchCriteria);
		
	}
	
	public void butonFinish(SalesOrderDtlVO salesOrderDtlVO){
		try{
		 
			salesOrderMonitoringService.updateSalesOrderMonitoring(salesOrderDtlVO, userSession.getUserCode());
			buttonSearch();
		
		}catch(Exception ex){
		    ex.printStackTrace();
		}
	}
	
	
	public static Logger getLogger() {
		return logger;
	}

	public SalesOrderMonitoringService getSalesOrderMonitoringService() {
		return salesOrderMonitoringService;
	}

	public void setSalesOrderMonitoringService(
			SalesOrderMonitoringService salesOrderMonitoringService) {
		this.salesOrderMonitoringService = salesOrderMonitoringService;
	}

	public static void setLogger(Logger logger) {
		SalesOrderMonitoringSearchBean.logger = logger;
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

	public Date getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}
	
	
}
