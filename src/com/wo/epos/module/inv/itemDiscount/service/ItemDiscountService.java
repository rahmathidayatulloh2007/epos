package com.wo.epos.module.inv.itemDiscount.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;



public interface ItemDiscountService  extends RetrieverDataPage<ItemDiscountVO>{
	
	
	public List<ItemDiscountVO> searchItemList();

	public ItemDiscount findById(Long itemDiscountId);

}
