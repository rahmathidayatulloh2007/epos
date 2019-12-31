package com.wo.epos.module.master.paymentType.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.paymentType.model.PaymentType;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;

public interface PaymentTypeDAO  extends GenericDAO<PaymentType, Long>, RetrieverDataPage<PaymentTypeVO>{

	public List<PaymentTypeVO> searchPaymentTypeList();
	public List<PaymentTypeVO> searchDataPaymentByCompany(Long companyId);
	
	public List<PaymentTypeVO> searchDataPaymentByPaymentMethod(String paymentMethodCode);
	public List<PaymentTypeVO> searchDataPaymentType();
}
