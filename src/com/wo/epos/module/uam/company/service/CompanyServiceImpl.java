package com.wo.epos.module.uam.company.service;

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
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="companyService")
@ViewScoped
public class CompanyServiceImpl implements CompanyService, Serializable {

	private static final long serialVersionUID = 1084343757185305747L;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;

	
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
	public List<CompanyVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return companyDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return companyDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(CompanyVO companyVO, String user) {
		 Company company = new Company();		 
		// company.setCompanyId(companyVO.getCompanyId());
		 company.setCompanyCode(companyVO.getCompanyCode());
		 company.setCompanyName(companyVO.getCompanyName());
		 company.setCompanyTypeCode(companyVO.getCompanyTypeCode());
		 ParameterDtl parameterDtl = parameterDAO.findByDetailId(companyVO.getCompanyTypeCode());
		 company.setCompanyType(parameterDtl);
		 company.setPaymentFlag(companyVO.getPaymentFlag());
		 company.setAddress(companyVO.getAddress());
		 company.setCityId(companyVO.getCityId());
		 City city = cityDAO.findById(companyVO.getCityId());
		 company.setCity(city);
		 company.setPostalCode(companyVO.getPostalCode());
		 company.setPicName(companyVO.getPicName());
		 company.setPicPhoneno(companyVO.getPicPhoneno());
		 company.setRegisterOn(companyVO.getRegisterOn());
		 company.setOutletQty(companyVO.getOutletQty());
		 if(companyVO.getFranchiseId() !=null){
			 company.setFranchiseId(companyVO.getFranchiseId());
		 }
		 
		 company.setActiveFlag(companyVO.getActiveFlag());
		 company.setCreateBy(user);
		 company.setCreateOn(new Timestamp(System.currentTimeMillis()));
		 
		 companyDAO.save(company);
		 companyDAO.flush();
		 
	}

	@Override
	public void update(CompanyVO companyVO, String user) {
        Company company = new Company();
        
        company = companyDAO.findById(companyVO.getCompanyId());
       // company.setCompanyId(companyVO.getCompanyId());
		company.setCompanyCode(companyVO.getCompanyCode());
		company.setCompanyName(companyVO.getCompanyName());
		company.setCompanyTypeCode(companyVO.getCompanyTypeCode());
		ParameterDtl parameterDtl = parameterDAO.findByDetailId(companyVO.getCompanyTypeCode());
		company.setCompanyType(parameterDtl);
		company.setPaymentFlag(companyVO.getPaymentFlag());
		company.setAddress(companyVO.getAddress());
		company.setCityId(companyVO.getCityId());
		City city = cityDAO.findById(companyVO.getCityId());
		company.setCity(city);
		company.setPostalCode(companyVO.getPostalCode());
		company.setPicName(companyVO.getPicName());
		company.setPicPhoneno(companyVO.getPicPhoneno());
		company.setRegisterOn(companyVO.getRegisterOn());
		company.setOutletQty(companyVO.getOutletQty());
		if(companyVO.getFranchiseId() !=null){
			 company.setFranchiseId(companyVO.getFranchiseId());
		}
		company.setActiveFlag(companyVO.getActiveFlag());
		company.setUpdateBy(user);
		company.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
		
		
		companyDAO.update(company);
		companyDAO.flush();
		
	}

	@Override
	public void delete(Long companyId) {
		Company company = new Company();
		company.setCompanyId(companyId);
		companyDAO.delete(company);
		companyDAO.flush();
	}

	@Override
	public Company findById(Long companyId) {
		return companyDAO.findById(companyId);
	}

	@Override
	public List<CityVO> searchCityAll() {
		return cityDAO.citySearch();
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		return parameterDAO.parameterDtlList(parameterCode);
	}
	
	@Override
	public List<CompanyVO> searchCompanyList(){
		return companyDAO.searchCompanyList();
	}

	@Override
	public Company findByCode(String companyCode) {
		return companyDAO.findByCode(companyCode);
	}
	
		
	
	
}
