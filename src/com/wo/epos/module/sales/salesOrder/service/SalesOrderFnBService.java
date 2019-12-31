package com.wo.epos.module.sales.salesOrder.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;

public interface SalesOrderFnBService extends RetrieverDataPage<SalesOrderVO>{
	
	public SalesOrder saveSoDistro(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
	public void updateSoDistro(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
	//public void saveSendKitchenSoDistro(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
//	public void updateSendKitchenSoDistro(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
	public void delete(Long salesOrderId);
	public SalesOrder findById(Long salesOrderId);
	public String searchSalesOrderMax(String year, String yearMonth, String yearMonthDate);
	public SalesOrderVO searchDataSoByEquipment(Long equipmentId, String equipmentStatus);
    public void savePayment(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, List<PaymentTypeVO> paymentTypeVOList, String user);
    public void flush();
    public void rollback();
    public SalesOrder saveSoDistroBill(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, List<SalesOrderVO> tagihanList, String user);
    public void saveSoDistroPaymentBill(SalesOrderVO salesOrderVO, List<PaymentTypeVO> paymentTypeVOList, String user);
   
	
}
