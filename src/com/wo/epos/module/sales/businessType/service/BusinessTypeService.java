package com.wo.epos.module.sales.businessType.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.businessType.model.BusinessType;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;


public interface BusinessTypeService extends RetrieverDataPage<BusinessTypeVO> {
	
	public BusinessType findById(Long businessTypeId);
}	
	