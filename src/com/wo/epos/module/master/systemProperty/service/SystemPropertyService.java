package com.wo.epos.module.master.systemProperty.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.vo.SystemPropertyVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;

public interface SystemPropertyService extends RetrieverDataPage<CompanyVO> {

	public void save(SystemPropertyVO systemPropertyVO, String user);
	public void update(SystemPropertyVO systemPropertyVO, String user);
	public void delete(Long id);
	public SystemProperty findById(Long id);	
	
	public List<SystemPropertyVO> searchSystemPropetyList();
	public SystemProperty searchSystemPropertyName(String systemPropertyName);
	public SystemProperty searchSystemPropertyNameAndCompany(String systemPropertyName, Long companyId);
	
	public void deleteByCompanyId(Long companyId);
}	
	