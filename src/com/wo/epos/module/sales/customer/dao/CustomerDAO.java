package com.wo.epos.module.sales.customer.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.customer.model.CustomerSales;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;



public interface CustomerDAO extends GenericDAO<CustomerSales, Long>, RetrieverDataPage<CustomerSalesVO>{
	
	public List<CustomerSalesVO> searchCustomerList();
	public CustomerSales findByCustomerCode(String customerCode);
	public List<CustomerSalesVO> searchSupplierListByCompany(Long companyId);
	public ParameterDtl findByDetailCode(String parameterDtlCode);
}
