package com.wo.epos.module.sales.discount.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.vo.DiscountDtlVO;

public interface DiscountDtlDAO extends GenericDAO<DiscountDtl, Long>, RetrieverDataPage<DiscountDtlVO>{
	
}
