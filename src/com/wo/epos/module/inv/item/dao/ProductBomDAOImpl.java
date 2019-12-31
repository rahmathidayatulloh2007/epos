package com.wo.epos.module.inv.item.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ProductBomVO;

@ManagedBean(name = "productBomDAO")
@ViewScoped
public class ProductBomDAOImpl  extends GenericDAOHibernate<ProductBom, Long> 
implements ProductBomDAO, Serializable {

	private static final long serialVersionUID = 4904651695688378911L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProductBomVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(ProductBom.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<ProductBomVO> voList = new ArrayList<ProductBomVO>();
		for(int i=0; i<criteria.list().size(); i++){
			ProductBom productBom = (ProductBom)criteria.list().get(i);
			  ProductBomVO vo = new ProductBomVO();
			  vo.setBomId(productBom.getBomId());
			  vo.setProductId(productBom.getProduct().getProductId());
			  vo.setItemId(productBom.getItem().getItemId());
			  vo.setItemQty(productBom.getItemQty());
			  vo.setActiveFlag(productBom.getActiveFlag());
			  			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(ProductBom.class);
		decorateCriteria(criteria, searchCriteria);
		 
		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}
	
	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit,
			List<? extends SearchObject> searchCriteria) {
		crit.createAlias("product", "product");
	    crit.createAlias("item", "item");
		    		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("product.productId", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("item.itemId", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("itemQty", sSplit,MatchMode.ANYWHERE))));
					}
					
					if(searchVal.getSearchColumn().compareTo("productId")==0){
						crit.add(Restrictions.eq("product.productId", searchVal.getSearchValue()));
					}	
					
					if(searchVal.getSearchColumn().compareTo("itemId")==0){
						crit.add(Restrictions.eq("item.itemId", searchVal.getSearchValue()));
					}	
					
					if(searchVal.getSearchColumn().compareTo("itemQty")==0){
						crit.add(Restrictions.ilike("itemQty", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}				
					
					
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}
	
	@Override
	public ProductBom findByItemId(Long id) {
		Criteria criteria = getSession().createCriteria(ProductBom.class);
		criteria.createAlias("item", "item", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("item.itemId",id));
		
		return (ProductBom)criteria.uniqueResult();
	}

	@Override
	public List<ProductBomVO> searchFindByProductId(Long productId) {
		Criteria criteria = getSession().createCriteria(ProductBom.class);
		criteria.createAlias("product", "product", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("product.productId",productId));
		
		List<ProductBomVO> productBomVoList = new ArrayList<ProductBomVO>();
		if(criteria !=null){
			for(int i=0; i<criteria.list().size(); i++){
				  ProductBom bom = (ProductBom)criteria.list().get(i);
				  ProductBomVO bomVo = new ProductBomVO();
				  
				  bomVo.setBomId(bom.getBomId());
				  bomVo.setProductId(bom.getProduct().getProductId());
				  bomVo.setItemId(bom.getItem().getItemId());
				  bomVo.setItemCode(bom.getItem().getItemCode());
				  bomVo.setItemName(bom.getItem().getItemName());
				  bomVo.setUmId(bom.getItem().getUm().getUmId());
				  bomVo.setUmName(bom.getItem().getUm().getUmName());
				  bomVo.setItemQty(bom.getItemQty());			
				  bomVo.setCompleteItem(bom.getItem().getItemCode()+" - "+bom.getItem().getItemName());
				  
				  productBomVoList.add(bomVo);
				  
			}
		}
		
		return productBomVoList;
	}
	
	@Override
	public List<ProductBomVO> findBomByItemId(Long itemId) {
		Criteria criteria = getSession().createCriteria(ProductBom.class);
		criteria.createAlias("item", "item", CriteriaSpecification.INNER_JOIN);		
		criteria.add(Restrictions.eq("item.itemId",itemId));
		
		List<ProductBomVO> productBomVoList = new ArrayList<ProductBomVO>();
		if(criteria !=null){
			for(int i=0; i<criteria.list().size(); i++){
				  ProductBom bom = (ProductBom)criteria.list().get(i);
				  ProductBomVO bomVo = new ProductBomVO();
				  
				  bomVo.setBomId(bom.getBomId());
				  bomVo.setProductId(bom.getProduct().getProductId());
				  bomVo.setItemId(bom.getItem().getItemId());
				  bomVo.setItemCode(bom.getItem().getItemCode());
				  bomVo.setItemName(bom.getItem().getItemName());
				  bomVo.setUmId(bom.getItem().getUm().getUmId());
				  bomVo.setUmName(bom.getItem().getUm().getUmName());
				  bomVo.setItemQty(bom.getItemQty());		
				  bomVo.setActiveFlag(bom.getActiveFlag());
				  bomVo.setCompleteItem(bom.getItem().getItemCode()+" - "+bom.getItem().getItemName());
				  
				  productBomVoList.add(bomVo);
				  
			}
		}
		
		return productBomVoList;		
	}

}
