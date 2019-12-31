package com.wo.epos.module.sales.discount.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.sales.discount.model.Discount;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.vo.DiscountDtlVO;
import com.wo.epos.module.sales.discount.vo.DiscountVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface DiscountService extends RetrieverDataPage<DiscountVO>{
	
	public void save(DiscountVO discountVO, List<DiscountDtlVO> discountDtlList, String user);
	public void update(DiscountVO discountVO, List<DiscountDtlVO> discountDtlList, String user);
	public void delete(Long discountId);
	public Discount findById(Long discountId);
	public List<DiscountDtl> searchDiscountDtlList(Long discountId); 
	
	public List<CompanyVO> searchCompanyList();
	public List<CategoryVO> searchCategoryList();
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	
	public List<DiscountVO> searchDiscountByCompanyList(Long companyId);

}
