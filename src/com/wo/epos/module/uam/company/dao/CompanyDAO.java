package com.wo.epos.module.uam.company.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;


public interface CompanyDAO extends GenericDAO<Company, Long>, RetrieverDataPage<CompanyVO> {
	
	public List<CompanyVO> searchCompanyList();
	public Company findByCode(String companyCode);
	
}