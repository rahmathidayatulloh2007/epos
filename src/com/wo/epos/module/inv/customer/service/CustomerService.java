package com.wo.epos.module.inv.customer.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.customer.model.Customer;
import com.wo.epos.module.inv.customer.vo.CustomerVO;


public interface CustomerService extends RetrieverDataPage<CustomerVO>{

	public void save(CustomerVO outletVO, String user);
	public void update(CustomerVO outletVO, String user);
	public void delete(Long outletId);
	public Customer findById(Long outletId);
	public List<CustomerVO> searchCustomerList();
	
	
}
