package com.wo.epos.module.master.systemProperty.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.vo.SystemPropertyVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;


public interface SystemPropertyDAO extends GenericDAO<SystemProperty, Long>, RetrieverDataPage<CompanyVO> {
	
	public List<SystemPropertyVO> searchSystemPropertyList();
	public SystemProperty searchSystemPropertyName(String systemPropertyName);
	public SystemProperty searchSystemPropertyNameAndCompany(String systemPropertyName, Long companyId);
	public void deleteByCompanyId(Long companyId);
}