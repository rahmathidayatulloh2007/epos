package com.wo.epos.module.sales.draftSalesOrder.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrder;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;


public interface DraftSalesOrderService extends RetrieverDataPage<DraftSalesOrderVO>{
	
	public DraftSalesOrder saveSoDistro(DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user);
	public void updateSoDistro(DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user);
	//public void saveSendKitchenSoDistro(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
//	public void updateSendKitchenSoDistro(SalesOrderVO salesOrderVO, List<SalesOrderDtlVO> salesOrderDtlVoList, String user);
	public void delete(Long salesOrderId);
	public DraftSalesOrder findById(Long salesOrderId);
	public String searchSalesOrderMax(String year, String yearMonth, String yearMonthDate);
	public DraftSalesOrderVO searchDataSoByEquipment(Long equipmentId, String equipmentStatus);
    public void savePayment(DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, List<PaymentTypeVO> paymentTypeVOList, String user);
    public void flush();
    public void rollback();
    public DraftSalesOrder saveSoDistroBill(DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, List<DraftSalesOrderVO> tagihanList, String user);
    public void saveSoDistroPaymentBill(DraftSalesOrderVO salesOrderVO, List<PaymentTypeVO> paymentTypeVOList, String user);  
    public DraftSalesOrder updateSoPayment(DraftSalesOrderVO salesOrderVO, String user);
   
    public List<DraftSalesOrderVO> searchDraftSalesOrderList();
    public List<DraftSalesOrderVO> searchSalesOrderListCustomerId(Long customerId);
    public List<DraftSalesOrder> findSalesOrderByCustomer(Long customerId);
    public List<DraftSalesOrder> findSalesOrder();
}
