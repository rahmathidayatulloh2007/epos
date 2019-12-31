package com.wo.epos.module.inv.customer.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.customer.model.Customer;
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.vo.OutletVO;

public interface CustomerDAO extends GenericDAO<Customer, Long>, RetrieverDataPage<CustomerVO>{

	public List<CustomerVO> searchCustomerList();
	public Customer getLastCustomerCode();
	
}
