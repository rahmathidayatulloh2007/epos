package com.wo.epos.module.inv.item.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;

public interface ProductDAO extends GenericDAO<Product, Long>, RetrieverDataPage<ProductVO> {

	public List<ProductVO> searchAllProductByCategoryList(Long categoryId);
	public List<ProductVO> searchAllProductByCompanyId(Long companyId);
	public ProductVO findByProductCodeAndCompanyId(String productCode, Long companyId);
	public ProductVO findByProductCodeOrBarcodeAndCompanyId(String productCode, String barcode, Long companyId);
	
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId);
	public ItemDiscount findByDetailId2(Long id);
}
