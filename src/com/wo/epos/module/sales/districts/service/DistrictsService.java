package com.wo.epos.module.sales.districts.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.districts.model.Districts;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;
import com.wo.epos.module.sales.groupOutlet.model.GroupOutlet;


public interface DistrictsService extends RetrieverDataPage<DistrictsVO> {
	
	public Districts findById(Long districtsId);
}	
	