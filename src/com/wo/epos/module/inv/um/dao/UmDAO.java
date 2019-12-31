package com.wo.epos.module.inv.um.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.vo.UmVO;


public interface UmDAO extends GenericDAO<Um, Long>, RetrieverDataPage<UmVO> {

	public List<UmVO> searchUmList();
	public List<UmVO> searchUmCompany(Long companyId);
	public Um findByName(String umName);
	
}