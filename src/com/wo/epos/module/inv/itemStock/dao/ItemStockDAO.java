package com.wo.epos.module.inv.itemStock.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;

public interface ItemStockDAO extends GenericDAO<ItemStock, Long>, RetrieverDataPage<ItemStockVO> {
	
    public List<ItemStockVO> searchItemStock(List searchCriteria);
    
    public ItemStockVO dataItemStockItem(Long itemId);
    
    public ItemStock getItemStockByCompanyIdOutletIdAndItemId(Long companyId,Long outletId, Long itemId);

    public ItemStockVO getItemStockByCompanyIdOutletIdAndItemIdVO(Long companyId, Long outletId, Long itemId);
    
	List<ItemStock> searchAllItemStockByCompanyIdOrOutleId(Long companyId,
			Long outletId);
	
	public ItemStock getItemStockByOutletIdAndItemId(Long outletId, Long itemId);
	
}
