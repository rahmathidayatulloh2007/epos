package com.wo.epos.module.master.paymentType.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.paymentType.model.PaymentType;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface PaymentTypeService extends RetrieverDataPage<PaymentTypeVO>{
	
	public void save(PaymentTypeVO paymentTypeVO, String user);
	public void update(PaymentTypeVO paymentTypeVO, String user);
	public void delete(Long paymentTypeId);
	public PaymentType findById(Long paymentTypeId);
	
	public List<CompanyVO> searchCompanyList();
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	public List<PaymentTypeVO> searchDataPaymentByCompany(Long companyId);

}
