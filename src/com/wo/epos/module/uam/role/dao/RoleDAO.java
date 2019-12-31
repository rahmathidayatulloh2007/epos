package com.wo.epos.module.uam.role.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.role.model.Role;
import com.wo.epos.module.uam.role.vo.RoleVO;
import com.wo.epos.module.uam.user.vo.UserVO;


public interface RoleDAO extends GenericDAO<Role, Long>, RetrieverDataPage<RoleVO> {
	
	public List<RoleVO> searchRoleList();
	public List<RoleVO> searchRoleListByCompany(Long companyId);
}