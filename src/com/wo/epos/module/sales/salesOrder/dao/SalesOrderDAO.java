package com.wo.epos.module.sales.salesOrder.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;

public interface SalesOrderDAO  extends GenericDAO<SalesOrder, Long>, RetrieverDataPage<SalesOrderVO>{
	
	public String searchSalesOrderMax(String year, String yearMonth, String yearMonthDate);
	
	public SalesOrderVO searchDataSoByEquipment(Long equipmentId, String equipmentStatus);
	
	public String runningNumberSo(String systemPropertyName, Long companyId);
	
	
	//Added By R 
	public List<SalesOrderVO> searchSalesOrderListCustomerId(Long customerId);
	public List<SalesOrderVO> searchSalesOrderListOutletId(Long outletId);
	public List<SalesOrder> searchDataSalesOrderByOutlet(Long outletId);
	public List<SalesOrder> findSalesOrderByCustomer(Long customerId);
	public SalesOrder findDtlById(Long id);
	
}
