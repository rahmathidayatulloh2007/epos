package com.wo.epos.module.sales.salesOrderMonitoring.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.salesOrder.dao.SalesOrderDtlDAO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

@ManagedBean(name="salesOrderMonitoringService")
@ViewScoped
public class SalesOrderMonitoringServiceImpl implements SalesOrderMonitoringService, Serializable{
	
	private static final long serialVersionUID = -946217103964173489L;
	
	@ManagedProperty(value="#{salesOrderDtlDAO}")
	private SalesOrderDtlDAO salesOrderDtlDAO;	
	

	public SalesOrderDtlDAO getSalesOrderDtlDAO() {
		return salesOrderDtlDAO;
	}

	public void setSalesOrderDtlDAO(SalesOrderDtlDAO salesOrderDtlDAO) {
		this.salesOrderDtlDAO = salesOrderDtlDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SalesOrderDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return salesOrderDtlDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return salesOrderDtlDAO.searchCountData(searchCriteria);
	}

	@Override
	public void updateSalesOrderMonitoring(SalesOrderDtlVO salesOrderDtlVO, String user) {
		try{
			
		     SalesOrderDtl salesOrderDtl = new SalesOrderDtl();
		     salesOrderDtl = salesOrderDtlDAO.findById(salesOrderDtlVO.getSoDtlId());
		     salesOrderDtl.setPreparationStatusCode(CommonConstants.PREPARATION_FINISH);
		     salesOrderDtl.setUpdateBy(user);
		     salesOrderDtl.setUpdateOn(new Timestamp(System.currentTimeMillis()));			
		     
		     salesOrderDtlDAO.update(salesOrderDtl);			
		     salesOrderDtlDAO.flush();
			
		}catch(Exception ex){
			ex.printStackTrace();
			salesOrderDtlDAO.rollback();
		}
	}
	
	
  	

}
