package com.wo.epos.module.uam.role.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.role.dao.RoleDAO;
import com.wo.epos.module.uam.role.model.Role;
import com.wo.epos.module.uam.role.vo.RoleVO;

@ManagedBean(name="roleService")
@ViewScoped
public class RoleServiceImpl implements RoleService, Serializable {

	private static final long serialVersionUID = -8678250072781710977L;

	@ManagedProperty(value="#{roleDAO}")
	private RoleDAO roleDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RoleVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		
		return roleDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return roleDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(RoleVO roleVO, String user) {
		 Role role = new Role();
		 role.setRoleId(roleVO.getRoleId()); 
		 role.setRoleCode(roleVO.getRoleCode());
		 role.setRoleName(roleVO.getRoleName());
		 role.setRoleDesc(roleVO.getRoleDesc());
		 if(roleVO.getCompanyId() !=null){
			 role.setCompanyId(roleVO.getCompanyId());
			 Company company = companyDAO.findById(roleVO.getCompanyId());
			 role.setCompany(company);
		 }
		 role.setActiveFlag(roleVO.getActiveFlag());
		 role.setCreateBy(user);
		 role.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		 roleDAO.save(role);
		 roleDAO.flush();
	}

	@Override
	public void update(RoleVO roleVO, String user) {
		Role role = new Role();
		role = findById(roleVO.getRoleId());
		
		role.setRoleCode(roleVO.getRoleCode());
		role.setRoleName(roleVO.getRoleName());
		role.setRoleDesc(roleVO.getRoleDesc());		
		if(roleVO.getCompanyId() !=null){
			role.setCompanyId(roleVO.getCompanyId());
			Company company = companyDAO.findById(roleVO.getCompanyId());
			role.setCompany(company);
		}
		role.setActiveFlag(roleVO.getActiveFlag());
		role.setUpdateBy(user);
		role.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
		
		roleDAO.update(role);
		roleDAO.flush();
		
	}

	@Override
	public void delete(Long id) {
		Role role = new Role();
		role = findById(id);
		
		roleDAO.delete(role); 
		roleDAO.flush();
		
	}

	@Override
	public Role findById(Long id) {
		
		return roleDAO.findById(id);
	}

	@Override
	public List<RoleVO> searchRoleList() {
		return roleDAO.searchRoleList();
	}
	
	@Override
	public List<RoleVO> searchRoleListByCompany(Long companyId) {
		return roleDAO.searchRoleListByCompany(companyId);
	}
		
}