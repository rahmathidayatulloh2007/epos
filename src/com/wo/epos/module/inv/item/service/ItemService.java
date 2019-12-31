package com.wo.epos.module.inv.item.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.vo.ItemBomVO;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.inv.item.vo.ProductBomVO;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface ItemService  extends RetrieverDataPage<ItemVO>{
	
	public void save(ItemVO itemVO, List<SupplierItemVO> supplierItemList, ProductVO productVO, 
			ProductBomVO productBomVO, List<ItemBomVO> itemBomList,List<ItemDiscountVO> itemDiscountList, String user);
	
	public void update(ItemVO itemVO, List<SupplierItemVO> supplierItemList, ProductVO productVO, 
			ProductBomVO productBomVO, List<ItemBomVO> itemBomList,List<ItemDiscountVO> itemDiscountList, String user);
	
	public void delete(Long itemId);
	
	public Item findById(Long itemId);
	public Item findByItemCode(String itemCode);

	public SupplierItem findByDetailId(Long id);
	
	public List<SupplierItem> searchSupplierItemList(Long itemId); 
	public List<ItemBomVO> searchItemBomList(Long itemId);
	
	public List<ItemVO> searchItemList();
	
	public List<ItemVO> searchItemNonCompositeListByCompany(Long companyId);

	public List<ItemVO> searchItemCompositeListByCompany(Long companyId);

	public List<ItemVO> searchItemNonCompositeList();

	public List<ItemVO> searchItemCompositeList();

	public List<ItemVO> searchItemList(String compositeFlag,
			boolean filterCompany, Long companyId);
	
	public List<CompanyVO> searchCompanyList();
	public List<CategoryVO> searchCategoryList();
	public List<UmVO> searchUmList();
	public List<SupplierVO> searchSupplierList();
	public List<ItemVO> searchItemDialogPoList(String search);
	
	public Long getNextId();
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	public List<ItemVO> searchItemByCompanyList(Long companyId);
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId);

}
