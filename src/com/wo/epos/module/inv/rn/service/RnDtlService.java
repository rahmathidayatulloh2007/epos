package com.wo.epos.module.inv.rn.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.rn.model.RnDtl;
import com.wo.epos.module.inv.rn.vo.RnDtlVO;

public interface RnDtlService  extends RetrieverDataPage<RnDtlVO>{
	
	public void delete(Long rnDtlId);
	public RnDtl findById(Long rnDtlId);

}
