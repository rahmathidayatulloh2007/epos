package com.wo.epos.module.purchasing.po.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.purchasing.po.model.Po;
import com.wo.epos.module.purchasing.po.vo.PoVO;


public interface PoDAO extends GenericDAO<Po, Long>, RetrieverDataPage<PoVO> {
	
	public String searchPoMax(String yearMonth);
	
	public List<PoVO> searchPoNumber(Long supplierId);
	
	public Po findByPoNumber(String poNumber);
	
}