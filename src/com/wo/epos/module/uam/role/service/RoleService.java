package com.wo.epos.module.uam.role.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.role.model.Role;
import com.wo.epos.module.uam.role.vo.RoleVO;

public interface RoleService extends RetrieverDataPage<RoleVO> {

	public void save(RoleVO roleVO, String user);
	public void update(RoleVO roleVO, String user);
	public void delete(Long id);
	public Role findById(Long id);	
	public List<RoleVO> searchRoleList();
	public List<RoleVO> searchRoleListByCompany(Long companyId);
		
}	
	