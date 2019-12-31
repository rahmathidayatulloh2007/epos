package com.wo.epos.module.sales.draftSalesOrder.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;


public interface DraftSalesOrderDistributorService extends RetrieverDataPage<DraftSalesOrderVO>{
	
	public void savePayment(DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, List<PaymentTypeVO> paymentTypeVOList, String user);
    public void flush();
    public void rollback();
	public void saveDirect(DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user);      
	public String runningNumberSo(String systemPropertyName, Long companyId);
}
