package com.wo.epos.module.inv.item.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ProductBomVO;

public interface ProductBomDAO extends GenericDAO<ProductBom, Long>, RetrieverDataPage<ProductBomVO> {
	public ProductBom findByItemId(Long id);
	public List<ProductBomVO> searchFindByProductId(Long productId);
	public List<ProductBomVO> findBomByItemId(Long itemId);
}
