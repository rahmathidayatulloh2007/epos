package com.wo.epos.module.sales.salesOrderMonitoring.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

public interface SalesOrderMonitoringService extends RetrieverDataPage<SalesOrderDtlVO>{
	
	public void updateSalesOrderMonitoring(SalesOrderDtlVO salesOrderDtlVO, String user);
	
}
