package com.wo.epos.module.sales.payment.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.payment.vo.PaymentVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface PaymentService extends RetrieverDataPage<PaymentVO> {
	public List<SalesOrderDtlVO> searchListSoDtlVO(Long soId);
	public List<SalesOrderDtl> searchListSoDtl(Long soId);
	public List<ParameterDtl> parameterDtlList();
	public List<PaymentTypeVO> searchDataPaymentByPaymentMethod(String paymentMethodCode);
	public List<PaymentTypeVO> searchDataPaymentType();
	
	public void savePayment(PaymentVO paymentVO,List<PaymentTypeVO> paymentTypeVOs,String userCode);
	
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	
	
}
