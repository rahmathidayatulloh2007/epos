package com.wo.epos.module.purchasing.supplier.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.city.dao.CityDAO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.purchasing.supplier.dao.SupplierDAO;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="supplierService")
@ViewScoped
public class SupplierServiceImpl implements SupplierService, Serializable {

	private static final long serialVersionUID = -4232713007321826279L;
	
	@ManagedProperty(value="#{supplierDAO}")
	private SupplierDAO supplierDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
	

	public SupplierDAO getSupplierDAO() {
		return supplierDAO;
	}

	public void setSupplierDAO(SupplierDAO supplierDAO) {
		this.supplierDAO = supplierDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SupplierVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return supplierDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return supplierDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(SupplierVO supplierVO) {
		
		Company company = new Company();
		City city = new City();		
		ParameterDtl paramDtlPicTitle = new ParameterDtl();
		Supplier supplier = new Supplier();
		
		company = companyDAO.findById(supplierVO.getCompanyId());
		city = cityDAO.findById(supplierVO.getCityId());
		paramDtlPicTitle = parameterDAO.findByDetailId(supplierVO.getPicTitleCode());
		
	
		
		supplier.setSupplierId(supplierVO.getSupplierId());
		supplier.setApBalance(supplierVO.getApBalance());
		supplier.setSupplierCode(supplierVO.getSupplierCode());
		supplier.setSupplierName(supplierVO.getSupplierName());
		supplier.setAddress(supplierVO.getAddress());
		supplier.setPostalCode(supplierVO.getPostalCode());
		supplier.setPhoneNo(supplierVO.getPhoneNo());
		supplier.setFaxNo(supplierVO.getFaxNo());
		supplier.setPicName(supplierVO.getPicName());
		supplier.setCompany(company);
		supplier.setCity(city);	
		supplier.setPicTitle(paramDtlPicTitle);
		supplier.setActiveFlag(supplierVO.getActiveFlag());
		supplier.setCreateBy("Admin");
		supplier.setCreateOn(new Timestamp(System.currentTimeMillis()));
			
		supplierDAO.save(supplier);
		supplierDAO.flush();
			
	}

	@Override
	public void update(SupplierVO supplierVO) {
		
		Company company = new Company();
		City city = new City();		
		ParameterDtl paramDtlPicTitle = new ParameterDtl();
		Supplier supplier = new Supplier();
		
		company = companyDAO.findById(supplierVO.getCompanyId());
		city = cityDAO.findById(supplierVO.getCityId());
		paramDtlPicTitle = parameterDAO.findByDetailId(supplierVO.getPicTitleCode());
		
		supplier = supplierDAO.findById(supplierVO.getSupplierId());
		
		supplier.setSupplierId(supplierVO.getSupplierId());
		supplier.setApBalance(supplierVO.getApBalance());
		supplier.setSupplierCode(supplierVO.getSupplierCode());
		supplier.setSupplierName(supplierVO.getSupplierName());
		supplier.setAddress(supplierVO.getAddress());
		supplier.setPostalCode(supplierVO.getPostalCode());
		supplier.setPhoneNo(supplierVO.getPhoneNo());
		supplier.setFaxNo(supplierVO.getFaxNo());
		supplier.setPicName(supplierVO.getPicName());
		supplier.setCompany(company);
		supplier.setCity(city);	
		supplier.setPicTitle(paramDtlPicTitle);
		supplier.setActiveFlag(supplierVO.getActiveFlag());
		supplier.setUpdateBy("Admin");
		supplier.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			
		supplierDAO.update(supplier);
		supplierDAO.flush();
			
	}

	@Override
	public void delete(Long supplierId) {
		
		Supplier supplier = new Supplier();
		supplier.setSupplierId(supplierId);
		supplierDAO.delete(supplier);
		supplierDAO.flush();
	}

	@Override
	public Supplier findById(Long supplierId) {
		
		return supplierDAO.findById(supplierId);
	}

	@Override
	public List<CityVO> searchCityAll() {
		
		return cityDAO.citySearch();
	}

	@Override
	public List<CompanyVO> searchCompanyList() {
		
		return companyDAO.searchCompanyList();
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		
		return parameterDAO.parameterDtlList(parameterCode);
	}

	@Override
	public List<SupplierVO> searchSupplierList() {
		
		return supplierDAO.searchSupplierList();
	}

	@Override
	public Supplier findBySupplierCode(String supplierCode) {
		return supplierDAO.findBySupplierCode(supplierCode);
	}

	@Override
	public List<SupplierVO> searchSupplierListByCompany(Long companyId) {
		return supplierDAO.searchSupplierListByCompany(companyId);
	}
	
	
}
