package com.wo.epos.module.sales.customer.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;
import com.wo.epos.module.sales.customer.model.CustomerSales;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;
import com.wo.epos.module.sales.subDistricts.vo.SubDistrictsVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface CustomerService extends RetrieverDataPage<CustomerSalesVO>{
	
	public void save(CustomerSalesVO customerVO , String user);
	public void update(CustomerSalesVO customerVO, String user);
	public void delete(Long customerId);
	public CustomerSales findById(Long customerId);
	
	/*public List<CityVO> searchCityAll();*/
	public List<CompanyVO> searchCompanyList();
	public List<BusinessTypeVO> businessTypeSearch();
	public List<DistrictsVO> districtsSearch();
	public List<GroupOutletVO> groupOutletSearch();
	public List<SubDistrictsVO> subDistrictsSearch();
	public List<ProvinceVO> searchProvinceAll();
	
	public List<CustomerSalesVO> searchCustomerList();	
	public CustomerSales findByCustomerCode(String customerCode);
	public List<CustomerSalesVO> searchCustomerListByCompany(Long companyId);;

	public List<ParameterDtl> parameterDtlList(String parameterCode);
	public ParameterDtl findByDetailCode(String parameterDtlCode);
}
