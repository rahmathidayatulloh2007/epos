package com.wo.epos.module.uam.role.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.role.model.RoleDtl;
import com.wo.epos.module.uam.role.vo.RoleDtlVO;

public interface RoleDtlService extends RetrieverDataPage<RoleDtlVO> {

	public void save(RoleDtlVO roleDetailVO, String user);
	public void update(RoleDtlVO roleDetailVO, String user);
	public void delete(Long id);
	public RoleDtl findById(Long id);	
	
	public List<RoleDtl> searchRoleDetail(Long roleId); 
	
	public List<RoleDtlVO> dataRoleDetailList(Long menuId);
		
}	
	