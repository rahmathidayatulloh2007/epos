package com.wo.epos.module.sales.businessType.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.businessType.model.BusinessType;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;


public interface BusinessTypeDAO extends GenericDAO<BusinessType, Long>, RetrieverDataPage<BusinessTypeVO> {
	public List<BusinessTypeVO> businessTypeSearch();
	
}
