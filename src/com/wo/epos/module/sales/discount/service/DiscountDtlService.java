package com.wo.epos.module.sales.discount.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.vo.DiscountDtlVO;

public interface DiscountDtlService extends RetrieverDataPage<DiscountDtlVO>{
	
	public void save(DiscountDtlVO discountDtlVO);
	public void update(DiscountDtlVO discountDtlVO);
	public void delete(Long discountDtlId);
	public DiscountDtl findById(Long discountDtlId);

}
