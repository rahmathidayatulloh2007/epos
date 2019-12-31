package com.wo.epos.module.inv.itemDiscount.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface ItemDiscountDAO extends GenericDAO<ItemDiscount, Long>,
		RetrieverDataPage<ItemDiscountVO> {

	public List<ItemDiscountVO> searchItemList();

	public ItemDiscount findByItemCode(String itemDiscountCode);


}
