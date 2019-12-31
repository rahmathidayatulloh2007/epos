package com.wo.epos.module.inv.item.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.item.dao.ItemDAO;
import com.wo.epos.module.inv.item.dao.ProductBomDAO;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ProductBomVO;

@ManagedBean(name="productBomService")
@ViewScoped
public class ProductBomServiceImpl implements ProductBomService, Serializable {


	private static final long serialVersionUID = -1250779249425560823L;
	

	@ManagedProperty(value="#{productBomDAO}")
	private ProductBomDAO productBomDAO;
	
	@ManagedProperty(value="#{itemDAO}")
	private ItemDAO itemDAO;
	
	
	public ProductBomDAO getProductBomDAO() {
		return productBomDAO;
	}

	public void setProductBomDAO(ProductBomDAO productBomDAO) {
		this.productBomDAO = productBomDAO;
	}

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProductBomVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return productBomDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return productBomDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(ProductBomVO productBomVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ProductBomVO productBomVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long productBomId) {
		ProductBom productBom = new ProductBom();
		productBom.setBomId(productBomId);
		productBomDAO.delete(productBom);
		productBomDAO.flush();
		
	}

	@Override
	public ProductBom findById(Long productBomId) {
		
		return productBomDAO.findById(productBomId);
	}

	@Override
	public ProductBom findByItemId(Long id) {
		
		return productBomDAO.findByItemId(id);
	}

	@Override
	public List<ProductBomVO> searchFindByProductId(Long productId) {
		return productBomDAO.searchFindByProductId(productId);
	}
	
	@Override
	public List<ProductBomVO> findBomByItemId(Long itemId) {
		return productBomDAO.findBomByItemId(itemId);
	}
	
	
}
