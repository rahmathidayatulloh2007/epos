package com.wo.epos.module.sales.draftSalesOrder.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrder;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;


public interface DraftSalesOrderDAO  extends GenericDAO<DraftSalesOrder, Long>, RetrieverDataPage<DraftSalesOrderVO>{
	
	public String searchSalesOrderMax(String year, String yearMonth, String yearMonthDate);
	
	public DraftSalesOrderVO searchDataSoByEquipment(Long equipmentId, String equipmentStatus);
	
	public String runningNumberSo(String systemPropertyName, Long companyId);
	
	public List<DraftSalesOrderVO> searchDraftSalesOrderList();
	public List<DraftSalesOrderVO> searchSalesOrderListCustomerId(Long customerId);
	public List<DraftSalesOrder> findSalesOrderByCustomer(Long customerId);
	public List<DraftSalesOrder> findSalesOrder();
}