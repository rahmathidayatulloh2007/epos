package com.wo.epos.module.uam.role.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.role.model.RoleDtl;
import com.wo.epos.module.uam.role.vo.RoleDtlVO;


public interface RoleDtlDAO extends GenericDAO<RoleDtl, Long>, RetrieverDataPage<RoleDtlVO> {
		
	public List<RoleDtl> searchRoleDetail(Long roleId); 
	
	public List<RoleDtlVO> dataRoleDetailList(Long menuId);
		
}