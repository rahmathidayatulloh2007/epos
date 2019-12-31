package com.wo.epos.module.inv.itemStock.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;


public interface ItemStockService extends RetrieverDataPage<ItemStockVO> {
	
	public List<ItemStockVO> searchItemStock(List searchCriteria);
	
	public ItemStockVO getItemStockByCompanyIdOutletIdAndItemId(Long companyId, Long outletId, Long itemId);
}	
	