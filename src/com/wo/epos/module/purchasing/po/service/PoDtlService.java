package com.wo.epos.module.purchasing.po.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.purchasing.po.model.PoDtl;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;

public interface PoDtlService extends RetrieverDataPage<PoDtlVO> {

	public void save(PoDtlVO poDtlVO, String user);
	public void update(PoDtlVO poDtlVO, String user);
	public void delete(Long poDtlId);	
	public PoDtl findById(Long poDtlId);
	
	public List<PoDtlVO> searchListPoDtlVO(Long poId);
		
	
}	
	