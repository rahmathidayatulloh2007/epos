package com.wo.epos.module.sales.register.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.register.model.Register;
import com.wo.epos.module.sales.register.vo.RegisterVO;


public interface RegisterDAO extends GenericDAO<Register, Long>, RetrieverDataPage<RegisterVO> {
	
	public List<RegisterVO> findRegisterByOutletId(Long outletId);
}