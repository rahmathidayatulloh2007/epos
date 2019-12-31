package com.wo.epos.module.inv.item.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.vo.ProductBomVO;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;

public interface ProductService extends RetrieverDataPage<ProductVO>{
	
	public void save(ProductVO productVO, List<ProductBomVO> productBomVoList,List<ItemDiscountVO> itemDiscountList, String user);
	public void update(ProductVO productVO, List<ProductBomVO> productBomVoList,List<ItemDiscountVO> itemDiscountList, String user);
	public void delete(Long productId);
	public Product findById(Long productId);
	public List<ProductVO> searchAllProductByCategoryList(Long categoryId);
	public List<ProductVO> searchAllProductByCompanyId(Long companyId);
	public ProductVO findByProductCodeAndCompanyId(String productCode, Long companyId);
	public ProductVO findByProductCodeOrBarcodeAndCompanyId(String productCode, String barcode, Long companyId);
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId);
}
