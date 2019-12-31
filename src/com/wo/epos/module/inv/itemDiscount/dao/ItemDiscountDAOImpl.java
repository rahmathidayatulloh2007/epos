package com.wo.epos.module.inv.itemDiscount.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;

@ManagedBean(name = "itemDiscountDAO")
@ViewScoped
public class ItemDiscountDAOImpl extends GenericDAOHibernate<ItemDiscount, Long> 
implements ItemDiscountDAO, Serializable{

	private static final long serialVersionUID = -2613073755499205529L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemDiscountVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(ItemDiscount.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<ItemDiscountVO> voList = new ArrayList<ItemDiscountVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  ItemDiscount item = (ItemDiscount)criteria.list().get(i);
			  ItemDiscountVO vo = new ItemDiscountVO();
			  
			  vo.setItemDiscountId(item.getItemDiscountId());
			  vo.setItemId(item.getItemId().getItemId());
			  vo.setItemCode(item.getItemId().getItemCode());
			  vo.setProductId(item.getProductId().getProductId());
			  vo.setProductCode(item.getProductId().getProductCode());
			  vo.setParameterDtl(item.getCustomerType());
			  vo.setDiscount1(item.getDiscount1());
			  vo.setDiscount2(item.getDiscount2());
			  vo.setDiscount3(item.getDiscount3());
			  vo.setActiveFlag(item.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(ItemDiscount.class);
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
		    crit.createAlias("company", "company");
		    crit.createAlias("category", "category");
		    crit.createAlias("um", "um");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("company.companyName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemCode", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemDesc", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("category.categoryName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("um.umName", sSplit,MatchMode.ANYWHERE)))))));
					}
					
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("itemCode")==0){
						crit.add(Restrictions.ilike("itemCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("itemName")==0){
						crit.add(Restrictions.ilike("itemName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("category")==0){
						crit.add(Restrictions.eq("category.categoryId", searchVal.getSearchValue()));
					}						
					if(searchVal.getSearchColumn().compareTo("um")==0){
						crit.add(Restrictions.eq("um.umId", searchVal.getSearchValue()));
					}
				
				}
			}
	}

	@Override
	public List<ItemDiscountVO> searchItemList() {
		
		Criteria criteria = getSession().createCriteria(ItemDiscount.class);
		
		List<ItemDiscountVO> voList = new ArrayList<ItemDiscountVO>();
		for(int i=0; i<criteria.list().size(); i++){
			ItemDiscount item = (ItemDiscount)criteria.list().get(i);
			ItemDiscountVO vo = new ItemDiscountVO();
			  
			  vo.setItemDiscountId(item.getItemDiscountId());
			  vo.setItemId(item.getItemId().getItemId());
			  vo.setItemCode(item.getItemId().getItemCode());
			  vo.setProductId(item.getProductId().getProductId());
			  vo.setProductCode(item.getProductId().getProductCode());
			  vo.setParameterDtl(item.getCustomerType());
			  vo.setDiscount1(item.getDiscount1());
			  vo.setDiscount2(item.getDiscount2());
			  vo.setDiscount3(item.getDiscount3());
			  vo.setActiveFlag(item.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ItemDiscount findByItemCode(String itemCode) {
		Criteria criteria = getSession().createCriteria(ItemDiscount.class);
		criteria.add(Restrictions.eq("itemCode", itemCode.trim()));
		
		criteria.addOrder(Order.asc("itemCode"));
		
		return (ItemDiscount) criteria.uniqueResult();
	}


	
}
