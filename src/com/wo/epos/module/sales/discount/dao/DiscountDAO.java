package com.wo.epos.module.sales.discount.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.discount.model.Discount;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.vo.DiscountVO;

public interface DiscountDAO extends GenericDAO<Discount, Long>, RetrieverDataPage<DiscountVO>{
	
	public List<DiscountVO> searchDiscountList();
	public List<DiscountDtl> searchDiscountDtlList(Long discountId); 

	public List<DiscountVO> searchDiscountByCompanyList(Long companyId);
	
}
