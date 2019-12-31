package com.wo.epos.module.inv.item.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ProductBomVO;

public interface ProductBomService extends RetrieverDataPage<ProductBomVO>{
	
	public void save(ProductBomVO productBomVO);
	public void update(ProductBomVO productBomVO);
	public void delete(Long productBomId);
	public ProductBom findById(Long productBomId);	
	public ProductBom findByItemId(Long id);
	public List<ProductBomVO> searchFindByProductId(Long productId);
	public List<ProductBomVO> findBomByItemId(Long itemId);

}
