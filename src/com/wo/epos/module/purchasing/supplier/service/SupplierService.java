package com.wo.epos.module.purchasing.supplier.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface SupplierService extends RetrieverDataPage<SupplierVO>{
	
	public void save(SupplierVO supplierVO);
	public void update(SupplierVO supplierVO);
	public void delete(Long supplierId);
	public Supplier findById(Long supplierId);
		
	public List<CityVO> searchCityAll();
	public List<CompanyVO> searchCompanyList();
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	
	public List<SupplierVO> searchSupplierList();	
	public Supplier findBySupplierCode(String supplierCode);
	public List<SupplierVO> searchSupplierListByCompany(Long companyId);	


}
