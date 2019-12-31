package com.wo.epos.module.sales.register.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.register.model.RegisterDtl;
import com.wo.epos.module.sales.register.vo.RegisterDtlVO;


public interface RegisterDtlDAO extends GenericDAO<RegisterDtl, Long>, RetrieverDataPage<RegisterDtlVO> {
	
	public List<RegisterDtlVO> searchRegisterDtlVoList(Long registerId);
	
}