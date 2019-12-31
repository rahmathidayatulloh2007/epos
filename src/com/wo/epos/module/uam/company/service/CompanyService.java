package com.wo.epos.module.uam.company.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface CompanyService extends RetrieverDataPage<CompanyVO> {

	public void save(CompanyVO companyVO, String user);
	public void update(CompanyVO companyVO, String user);
	public void delete(Long companyId);
	public Company findById(Long companyId);
	public Company findByCode(String companyCode);
		
	public List<CityVO> searchCityAll();
	
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	
	public List<CompanyVO> searchCompanyList();
	
}	
	