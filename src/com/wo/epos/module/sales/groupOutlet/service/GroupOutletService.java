package com.wo.epos.module.sales.groupOutlet.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.businessType.model.BusinessType;
import com.wo.epos.module.sales.groupOutlet.model.GroupOutlet;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;


public interface GroupOutletService extends RetrieverDataPage<GroupOutletVO> {
	
	public GroupOutlet findById(Long groupOutletId);
}	
	