package com.wo.epos.module.purchasing.po.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.purchasing.po.model.PoDtl;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;


public interface PoDtlDAO extends GenericDAO<PoDtl, Long>, RetrieverDataPage<PoDtlVO> {
	
	public List<PoDtlVO> searchListPoDtlVO(Long poId);
	
}