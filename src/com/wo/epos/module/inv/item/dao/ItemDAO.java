package com.wo.epos.module.inv.item.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ItemBomVO;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;

public interface ItemDAO extends GenericDAO<Item, Long>,
		RetrieverDataPage<ItemVO> {

	public List<ItemVO> searchItemList();

	public List<ItemVO> searchItemList(String compositeFlag,
			boolean filterCompany, Long companyId);

	public List<SupplierItem> searchSupplierItemList(Long itemId);

	public List<ItemBomVO> searchItemBomList(Long itemId);

	public SupplierItem findByDetailId(Long id);

	public Product findByProductId(Long id);

	public ProductBom findByProductBomId(Long id);

	public List<ItemVO> searchItemDiloagPoList(String search);

	public Long getNextId();

	public Item findByItemCode(String itemCode);

	public List<ItemVO> searchItemByCompanyList(Long companyId);
	
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId);
	public ItemDiscount findByDetailId2(Long id);


}
