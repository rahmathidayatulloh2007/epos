package com.wo.epos.module.sales.salesOrder.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrderDtl;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;

public interface SalesOrderDistributorService extends RetrieverDataPage<SalesOrderVO>{
	
	public void savePayment(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, List<PaymentTypeVO> paymentTypeVOList, String user);
    public void flush();
    public void rollback();
	public void saveDirect(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
	public void updateDraftSo(SalesOrderVO salesOrderVO,List<SalesOrderDtlVO> draftSalesOrderDtlVoList, String user);
	public List<DraftSalesOrderDtlVO> searchListSoDtlVO(Long soId);
	public List<DraftSalesOrderDtl> searchListSoDtl(Long soId);
	
	
	public String runningNumberSo(String systemPropertyName, Long companyId);
}
