package com.wo.epos.module.inv.itemStock.dao;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;

@ManagedBean(name = "itemStockDAO")
@ViewScoped
public class ItemStockDAOImpl extends GenericDAOHibernate<ItemStock, Long> implements ItemStockDAO, Serializable{

	private static final long serialVersionUID = -3583670681400614387L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemStockVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		DecimalFormat df = new DecimalFormat("###,###,##0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		Criteria criteria = getSession().createCriteria(ItemStock.class);
		decorateCriteria(criteria, searchCriteria);
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<ItemStockVO> voList = new ArrayList<ItemStockVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  ItemStock itemStock = (ItemStock)criteria.list().get(i);
			  ItemStockVO vo = new ItemStockVO();
			  
			  vo.setItemStockId(itemStock.getItemStockId());
			  if(itemStock.getCompany() !=null){
				  vo.setCompany(itemStock.getCompany());
				  vo.setCompanyId(itemStock.getItemId());
			 
			  }
			  
			  if(itemStock.getItem() !=null){
				  vo.setItem(itemStock.getItem());
				  vo.setItemId(itemStock.getItemId());
			  	  vo.setItemName(itemStock.getItem().getItemName());
			  }
			  
			  if(itemStock.getOutlet() !=null){
				  vo.setOutlet(itemStock.getOutlet());
				  vo.setOutletId(itemStock.getOutletId());
			  	  vo.setOutletName(itemStock.getOutlet().getOutletName());
			  }
			  
			  vo.setOutgoingQty(itemStock.getOutgoingQty());
			  vo.setReorderQty(itemStock.getReorderQty());
			  vo.setStockQty(itemStock.getStockQty());
			  vo.setIncomingQty(itemStock.getIncomingQty());
			  vo.setAveragePrice(itemStock.getAveragePrice());
			  
			  if(itemStock.getOutgoingQty() != null){
				  vo.setOutgoingQtyString(df.format(itemStock.getOutgoingQty()));
			  }
			  
			  if(itemStock.getStockQty() != null){
				  vo.setStockQtyString(df.format(itemStock.getStockQty()));
			  }
			  
			  if(itemStock.getIncomingQty() != null){
				  vo.setIncomingQtyString(df.format(itemStock.getIncomingQty()));
			  }
			  
			  
			  vo.setActiveFlag(itemStock.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(ItemStock.class);
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
		    crit.createAlias("outlet", "outlet", Criteria.LEFT_JOIN);
		    crit.createAlias("item", "item", Criteria.LEFT_JOIN);
		    crit.createAlias("item.category", "category", Criteria.LEFT_JOIN);
		    crit.createAlias("item.um", "um", Criteria.LEFT_JOIN);
		    crit.createAlias("company", "company", Criteria.LEFT_JOIN);
		    
		    		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("outlet.outletName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("company.companyName", sSplit,MatchMode.ANYWHERE),
								    Restrictions.or(Restrictions.ilike("item.itemName", sSplit,MatchMode.ANYWHERE),
								    Restrictions.or(Restrictions.ilike("category.categoryName", sSplit,MatchMode.ANYWHERE),
								    //Restrictions.or(Restrictions.eq("stockQty",sSplit),
									Restrictions.ilike("um.umName", sSplit,MatchMode.ANYWHERE)
									//)
									)
									))
									));
					}
					
					if(searchVal.getSearchColumn().compareTo("outletId")==0){
						crit.add(Restrictions.eq("outlet.outletId", new Long(searchVal.getSearchValueAsString().trim())));
					}	
					
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("company.companyId", new Long(searchVal.getSearchValueAsString().trim())));
					}		
					
					
					
					
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@Override
	public List<ItemStockVO> searchItemStock(List searchCriteria) {
		Criteria criteria = getSession().createCriteria(ItemStock.class);
		decorateCriteria(criteria, searchCriteria);
		
		List<ItemStockVO> listItemStockVO = new ArrayList<ItemStockVO>();
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				  ItemStock itemStock = (ItemStock)criteria.list().get(i);
				  ItemStockVO vo = new ItemStockVO();
				  
				  vo.setItemStockId(itemStock.getItemStockId());
				  if(itemStock.getCompany() !=null){
					  vo.setCompany(itemStock.getCompany());
					  vo.setCompanyId(itemStock.getItemId());
				 
				  }
				  
				  if(itemStock.getItem() !=null){
					  vo.setItem(itemStock.getItem());
					  vo.setItemId(itemStock.getItemId());
				  	  vo.setItemName(itemStock.getItem().getItemName());
				  }
				  
				  if(itemStock.getOutlet() !=null){
					  vo.setOutlet(itemStock.getOutlet());
					  vo.setOutletId(itemStock.getOutletId());
				  	  vo.setOutletName(itemStock.getOutlet().getOutletName());
				  }
				  
				  vo.setOutgoingQty(itemStock.getOutgoingQty());
				  vo.setReorderQty(itemStock.getReorderQty());
				  vo.setStockQty(itemStock.getStockQty());
				  vo.setIncomingQty(itemStock.getIncomingQty());
				  vo.setAveragePrice(itemStock.getAveragePrice());
				  
				  vo.setActiveFlag(itemStock.getActiveFlag());
				  
				  listItemStockVO.add(vo);
			}
		}
		
		return listItemStockVO;
	}

	@Override
	public ItemStockVO dataItemStockItem(Long itemId) {
		Criteria criteria = getSession().createCriteria(ItemStock.class);
		criteria.createAlias("item", "item", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("item.itemId", itemId));
		
		ItemStockVO itemStockVO = null;
		if(criteria !=null){
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				ItemStock itemStock = (ItemStock)criteria.list().get(i);
				
				itemStockVO = new ItemStockVO();

				itemStockVO.setItemStockId(itemStock.getItemStockId());
				if(itemStock.getItem() !=null){
					itemStockVO.setItemId(itemStock.getItemId());
					itemStockVO.setItemName(itemStock.getItem().getItemName());
				}
				
				if(itemStock.getOutlet() !=null){
					itemStockVO.setOutletId(itemStock.getOutletId());
					itemStockVO.setOutletName(itemStock.getOutlet().getOutletName());
				}
				
				itemStockVO.setIncomingQty(itemStock.getIncomingQty());
				itemStockVO.setOutgoingQty(itemStock.getOutgoingQty());
				itemStockVO.setReorderQty(itemStock.getReorderQty());
				itemStockVO.setAveragePrice(itemStock.getAveragePrice());
				itemStockVO.setActiveFlag(itemStock.getActiveFlag());
			}
		}
		}
		
		return itemStockVO;
	}	
	
 	public ItemStock getItemStockByCompanyIdOutletIdAndItemId(Long companyId, Long outletId, Long itemId) {
		
		Criteria criteria = getSession().createCriteria(ItemStock.class);
		criteria.add(Restrictions.eq("company.companyId", companyId));
		if(outletId!=null){
			criteria.add(Restrictions.eq("outlet.outletId", outletId));
		}else{
			criteria.add(Restrictions.isNull("outlet.outletId"));
		}
		criteria.add(Restrictions.eq("item.itemId", itemId));
		criteria.setMaxResults(1);
		
		return (ItemStock)criteria.uniqueResult();
	}	
	
 	public ItemStockVO getItemStockByCompanyIdOutletIdAndItemIdVO(Long companyId, Long outletId, Long itemId) {
		ItemStock obj = this.getItemStockByCompanyIdOutletIdAndItemId(companyId, outletId, itemId);
		
		return this.convertToVO(obj);
	}	
	
 	private ItemStockVO convertToVO(ItemStock obj) {
 		if (obj == null) {
 			return null;
 		}
 		
 		ItemStockVO vo = new ItemStockVO();

		vo.setItemStockId(obj.getItemStockId());
		if(obj.getItem() !=null){
			vo.setItemId(obj.getItemId());
			vo.setItemName(obj.getItem().getItemName());
		}
		
		if(obj.getOutlet() !=null){
			vo.setOutletId(obj.getOutletId());
			vo.setOutletName(obj.getOutlet().getOutletName());
		}
		
		vo.setStockQty(obj.getStockQty());		
		vo.setIncomingQty(obj.getIncomingQty());
		vo.setOutgoingQty(obj.getOutgoingQty());
		vo.setReorderQty(obj.getReorderQty());
		vo.setAveragePrice(obj.getAveragePrice());
		vo.setActiveFlag(obj.getActiveFlag()); 	
		
		return vo;
 	}
 	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemStock> searchAllItemStockByCompanyIdOrOutleId(Long companyId, Long outletId) {
		Criteria criteria = getSession().createCriteria(ItemStock.class);
		
		if(outletId == null || outletId.equals(""))
			criteria.add(Restrictions.eq("company.companyId", companyId));
		if(companyId == null || companyId.equals(""))
			criteria.add(Restrictions.eq("outlet.outletId", outletId));
		
		return (List<ItemStock>) criteria.list();
	}
	
public ItemStock getItemStockByOutletIdAndItemId(Long outletId, Long itemId) {
		
		Criteria criteria = getSession().createCriteria(ItemStock.class);
		criteria.add(Restrictions.eq("outlet.outletId", outletId));
		criteria.add(Restrictions.eq("item.itemId", itemId));
		criteria.setMaxResults(1);
		
		return (ItemStock)criteria.uniqueResult();
	}	
	
	
}
