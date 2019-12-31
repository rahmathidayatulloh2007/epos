package com.wo.epos.module.sales.kitchenMonitoring.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

public interface KitchenMonitoringService extends RetrieverDataPage<SalesOrderDtlVO>{
	
	public void updateKitchenMonitoring(List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
	
}
