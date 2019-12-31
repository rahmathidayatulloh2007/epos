package com.wo.epos.module.sales.customer.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.province.dao.ProvinceDAO;
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.sales.businessType.dao.BusinessTypeDAO;
import com.wo.epos.module.sales.businessType.model.BusinessType;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;
import com.wo.epos.module.sales.customer.dao.CustomerDAO;
import com.wo.epos.module.sales.customer.model.CustomerSales;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.sales.districts.dao.DistrictsDAO;
import com.wo.epos.module.sales.districts.model.Districts;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;
import com.wo.epos.module.sales.groupOutlet.dao.GroupOutletDAO;
import com.wo.epos.module.sales.groupOutlet.model.GroupOutlet;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;
import com.wo.epos.module.sales.subDistricts.dao.SubDistrictsDAO;
import com.wo.epos.module.sales.subDistricts.model.SubDistricts;
import com.wo.epos.module.sales.subDistricts.vo.SubDistrictsVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


@ManagedBean(name="customerService2")
@ViewScoped
public class CustomerServiceImpl implements CustomerService, Serializable {
	
	private static final long serialVersionUID = 3870125958087235266L;

	@ManagedProperty(value="#{customerDAO2}")
	private CustomerDAO customerDAO2;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
/*	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;*/
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
	@ManagedProperty(value="#{provinceDAO}")
	private ProvinceDAO provinceDAO;
	
	@ManagedProperty(value="#{districtsDAO}")
	private DistrictsDAO districtsDAO;
	
	@ManagedProperty(value="#{subDistrictsDAO}")
	private SubDistrictsDAO subDistrictsDAO;
	
	@ManagedProperty(value="#{businessTypeDAO}")
	private BusinessTypeDAO businessTypeDAO;
	
	@ManagedProperty(value="#{groupOutletDAO}")
	private GroupOutletDAO groupOutletDAO;
	
	
	
	
	
	public ProvinceDAO getProvinceDAO() {
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	public DistrictsDAO getDistrictsDAO() {
		return districtsDAO;
	}

	public void setDistrictsDAO(DistrictsDAO districtsDAO) {
		this.districtsDAO = districtsDAO;
	}

	public SubDistrictsDAO getSubDistrictsDAO() {
		return subDistrictsDAO;
	}

	public void setSubDistrictsDAO(SubDistrictsDAO subDistrictsDAO) {
		this.subDistrictsDAO = subDistrictsDAO;
	}

	public BusinessTypeDAO getBusinessTypeDAO() {
		return businessTypeDAO;
	}

	public void setBusinessTypeDAO(BusinessTypeDAO businessTypeDAO) {
		this.businessTypeDAO = businessTypeDAO;
	}

	public GroupOutletDAO getGroupOutletDAO() {
		return groupOutletDAO;
	}

	public void setGroupOutletDAO(GroupOutletDAO groupOutletDAO) {
		this.groupOutletDAO = groupOutletDAO;
	}

	public CustomerDAO getCustomerDAO2() {
		return customerDAO2;
	}

	public void setCustomerDAO2(CustomerDAO customerDAO2) {
		this.customerDAO2 = customerDAO2;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}


	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	@Override
	public List<CustomerSalesVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return customerDAO2.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return customerDAO2.searchCountData(searchCriteria);
	}

	@Override
	public void save(CustomerSalesVO customerVO , String user) {
		// TODO Auto-generated method stub
		
		Company company = new Company();
		/*City city = new City();*/
		Province province = new Province();
		Districts districts =new Districts();
		SubDistricts subDistricts =new SubDistricts();
		BusinessType businessType = new BusinessType();
		GroupOutlet  groupOutlet = new GroupOutlet();
		ParameterDtl parameterDtlCustomerType = null;
		
		company = companyDAO.findById(customerVO.getCompanyId());
		province = provinceDAO.findById(customerVO.getProvinceId());
		districts = districtsDAO.findById(customerVO.getDistrictId());
		subDistricts = subDistrictsDAO.findById(customerVO.getSubDistrictId());
		businessType = businessTypeDAO.findById(customerVO.getBusinessTypeId());
		groupOutlet = groupOutletDAO.findById(customerVO.getGroupOutletId());

		CustomerSales customer = new CustomerSales();
		
		customer.setCustomerId(customerVO.getCustomerId());  
		customer.setCustomerCode(customerVO.getCustomerCode());
		customer.setCustomerName(customerVO.getCustomerName());
		
		customer.setFullName(customerVO.getFullName());
		customer.setPhoneNo(customerVO.getPhoneNo());
		customer.setEmailAddress(customerVO.getEmailAddress());
		customer.setAddress(customerVO.getAddress());
		customer.setDepositBalance(customerVO.getDepositBalance());
		customer.setSalesman(customerVO.getSalesman());
		customer.setPortalCode(customerVO.getPortalCode());
		customer.setAddressNpwp(customerVO.getAddressNpwp());
		customer.setFullNameNpwp(customerVO.getFullNameNpwp());
		customer.setNpwpNo(customerVO.getNpwpNo());
		customer.setPhoneNo2(customerVO.getPhoneNo2());
		customer.setFax(customerVO.getFax());
		customer.setTermOfPayment(customerVO.getTermOfPayment());

		customer.setCompany(company);
		  
		customer.setProvince(province);
		  
		customer.setDistricts(districts);
		  
		customer.setSubDistricts(subDistricts);
		
		customer.setBusinessType(businessType);
		
		customer.setGroupOutlet(groupOutlet);
		  

		if (customerVO.getCustomerTypeCode() != null) {
			parameterDtlCustomerType = parameterDAO.findByDetailId(customerVO.getCustomerTypeCode());
		}
		customer.setCustomerType(parameterDtlCustomerType);
		
		customer.setActiveFlag("Y");
		customer.setCreateBy(user);
		customer.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		customerDAO2.save(customer);
		customerDAO2.flush();
		
	}

	@Override
	public void update(CustomerSalesVO customerVO , String user) {
		
		
		CustomerSales customer = customerDAO2.findById(customerVO.getCustomerId());
		
		Company company = new Company();
		company = companyDAO.findById(customerVO.getCompanyId());
		customer.setCompany(company);
		
		
		Province province = new Province();
		province = provinceDAO.findById(customerVO.getProvinceId());
		customer.setProvince(province);
		
		Districts districts =new Districts();
		districts = districtsDAO.findById(customerVO.getDistrictId());
		customer.setDistricts(districts);
		
		SubDistricts subDistricts =new SubDistricts();
		subDistricts = subDistrictsDAO.findById(customerVO.getSubDistrictId());
		customer.setSubDistricts(subDistricts);
		
		BusinessType businessType = new BusinessType();
		businessType = businessTypeDAO.findById(customerVO.getBusinessTypeId());
		customer.setBusinessType(businessType);
		
		GroupOutlet  groupOutlet = new GroupOutlet();
		groupOutlet = groupOutletDAO.findById(customerVO.getGroupOutletId());
		customer.setGroupOutlet(groupOutlet);

		
		customer.setCustomerId(customerVO.getCustomerId());  
		customer.setCustomerCode(customerVO.getCustomerCode());
		customer.setCustomerName(customerVO.getCustomerName());
		customer.setFullName(customerVO.getFullName());
		customer.setPhoneNo(customerVO.getPhoneNo());
		customer.setEmailAddress(customerVO.getEmailAddress());
		customer.setAddress(customerVO.getAddress());
		customer.setDepositBalance(customerVO.getDepositBalance());
		customer.setSalesman(customerVO.getSalesman());
		customer.setPortalCode(customerVO.getPortalCode());
		customer.setAddressNpwp(customerVO.getAddressNpwp());
		customer.setFullNameNpwp(customerVO.getFullNameNpwp());
		customer.setNpwpNo(customerVO.getNpwpNo());
		customer.setPhoneNo2(customerVO.getPhoneNo2());
		customer.setFax(customerVO.getFax());
		customer.setTermOfPayment(customerVO.getTermOfPayment());
	
		customer.setActiveFlag(customerVO.getActiveFlag());
		customer.setUpdateBy(user);
		customer.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		customerDAO2.update(customer);
		customerDAO2.flush();
		
		
	}

	@Override
	public void delete(Long customerId) {
		CustomerSales customer = new CustomerSales();
		customer.setCustomerId(customerId);
		customerDAO2.delete(customer);
		customerDAO2.flush();
		
	}

	@Override
	public CustomerSales findById(Long customerId) {
		
		return customerDAO2.findById(customerId);
	}


	@Override
	public List<CompanyVO> searchCompanyList() {
		// TODO Auto-generated method stub
		return companyDAO.searchCompanyList();
	}


	@Override
	public List<CustomerSalesVO> searchCustomerList() {
		// TODO Auto-generated method stub
		return customerDAO2.searchCustomerList();
	}

	@Override
	public CustomerSales findByCustomerCode(String customerCode) {
		// TODO Auto-generated method stub
		return customerDAO2.findByCustomerCode(customerCode);
	}

	@Override
	public List<CustomerSalesVO> searchCustomerListByCompany(Long companyId) {
		// TODO Auto-generated method stub
		return customerDAO2.searchSupplierListByCompany(companyId);
	}

	@Override
	public List<BusinessTypeVO> businessTypeSearch() {
		// TODO Auto-generated method stub
		return businessTypeDAO.businessTypeSearch();
	}

	@Override
	public List<DistrictsVO> districtsSearch() {
		// TODO Auto-generated method stub
		return districtsDAO.districtsSearch();
	}

	@Override
	public List<GroupOutletVO> groupOutletSearch() {
		// TODO Auto-generated method stub
		return groupOutletDAO.groupOutletSearch();
	}

	@Override
	public List<SubDistrictsVO> subDistrictsSearch() {
		// TODO Auto-generated method stub
		return subDistrictsDAO.subDistrictsSearch();
	}

	@Override
	public List<ProvinceVO> searchProvinceAll() {
		// TODO Auto-generated method stub
		return provinceDAO.provinceSearch();
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		// TODO Auto-generated method stub
		return parameterDAO.parameterDtlList(parameterCode);
	}

	@Override
	public ParameterDtl findByDetailCode(String parameterDtlCode) {
		// TODO Auto-generated method stub
		return customerDAO2.findByDetailCode(parameterDtlCode);
	}

	
}
