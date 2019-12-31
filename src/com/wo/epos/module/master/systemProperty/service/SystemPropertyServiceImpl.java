package com.wo.epos.module.master.systemProperty.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.systemProperty.dao.SystemPropertyDAO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.vo.SystemPropertyVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name="systemPropertyService")
@ViewScoped
public class SystemPropertyServiceImpl implements SystemPropertyService, Serializable {

	private static final long serialVersionUID = 1479869894372462441L;

	@ManagedProperty(value="#{systemPropertyDAO}")
	private SystemPropertyDAO systemPropertyDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	
	public SystemPropertyDAO getSystemPropertyDAO() {
		return systemPropertyDAO;
	}

	public void setSystemPropertyDAO(SystemPropertyDAO systemPropertyDAO) {
		this.systemPropertyDAO = systemPropertyDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CompanyVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		
		return systemPropertyDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return systemPropertyDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(SystemPropertyVO systemPropertyVO, String user) {
		SystemProperty systemProperty = new SystemProperty();
		if(systemPropertyVO.getCompanyId() !=null){
			systemProperty.setCompanyId(systemPropertyVO.getCompanyId());
			Company company = companyDAO.findById(systemPropertyVO.getCompanyId());
			systemProperty.setCompany(company); 
		}	
		systemProperty.setSystemPropertyName(systemPropertyVO.getSystemPropertyName());
		systemProperty.setSystemPropertyValue(systemPropertyVO.getSystemPropertyValue());
		systemProperty.setSystemPropertyDesc(systemPropertyVO.getSystemPropertyDesc());
		systemProperty.setActiveFlag(systemPropertyVO.getActiveFlag());
		systemProperty.setCreateBy(user);
		systemProperty.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		systemPropertyDAO.save(systemProperty);
		systemPropertyDAO.flush();
	}

	@Override
	public void update(SystemPropertyVO systemPropertyVO, String user) {
		SystemProperty systemProperty = new SystemProperty();
		systemProperty = findById(systemPropertyVO.getSystemPropertyId());
		if(systemPropertyVO.getCompanyId() !=null){
			systemProperty.setCompanyId(systemPropertyVO.getCompanyId());
			Company company = companyDAO.findById(systemPropertyVO.getCompanyId());
			systemProperty.setCompany(company); 
		}
		systemProperty.setSystemPropertyName(systemPropertyVO.getSystemPropertyName());
		systemProperty.setSystemPropertyValue(systemPropertyVO.getSystemPropertyValue());
		systemProperty.setSystemPropertyDesc(systemPropertyVO.getSystemPropertyDesc());
		systemProperty.setActiveFlag(systemPropertyVO.getActiveFlag());
		systemProperty.setUpdateBy(user);
		systemProperty.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
		
		systemPropertyDAO.update(systemProperty);
		systemPropertyDAO.flush();
		
	}

	@Override
	public void delete(Long id) {
		SystemProperty systemProperty = new SystemProperty();
		systemProperty = findById(id);
		
		systemPropertyDAO.delete(systemProperty); 
		systemPropertyDAO.flush();
		
	}

	@Override
	public SystemProperty findById(Long id) {		
		return systemPropertyDAO.findById(id);
	}

	@Override
	public List<SystemPropertyVO> searchSystemPropetyList() {		
		return systemPropertyDAO.searchSystemPropertyList();
	}

	@Override
	public SystemProperty searchSystemPropertyName(String systemPropertyName) {
		return systemPropertyDAO.searchSystemPropertyName(systemPropertyName);
	}
	
	@Override
	public SystemProperty searchSystemPropertyNameAndCompany(String systemPropertyName, Long companyId){
		return systemPropertyDAO.searchSystemPropertyNameAndCompany(systemPropertyName, companyId);
	}
	
	@Override
	public void deleteByCompanyId(Long companyId) {
		systemPropertyDAO.deleteByCompanyId(companyId);
		systemPropertyDAO.flush();
	}	
}