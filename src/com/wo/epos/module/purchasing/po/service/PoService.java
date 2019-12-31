package com.wo.epos.module.purchasing.po.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.purchasing.po.model.Po;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;
import com.wo.epos.module.purchasing.po.vo.PoVO;

public interface PoService extends RetrieverDataPage<PoVO> {

	public void save(PoVO poVO, List<PoDtlVO> listPoDtlVO, String user);
	public void update(PoVO poVO, List<PoDtlVO> listPoDtlVO, String user);
	public void delete(Long poId);	
	public Po findById(Long poId);
	public Po findByPoNumber(String poNumber);
	
	public String searchPoMax(String yearMonth);
	public List<PoVO> searchPoNumber(Long supplierId);
	public String numberPo();

}	
	