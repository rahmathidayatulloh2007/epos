package com.wo.epos.module.purchasing.supplier.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;

public interface SupplierDAO extends GenericDAO<Supplier, Long>, RetrieverDataPage<SupplierVO> {
	
	public List<SupplierVO> searchSupplierList();
	
	public Supplier findBySupplierCode(String supplierCode);
	public List<SupplierVO> searchSupplierListByCompany(Long companyId);
}
