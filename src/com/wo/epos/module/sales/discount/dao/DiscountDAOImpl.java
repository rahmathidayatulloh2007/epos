package com.wo.epos.module.sales.discount.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.discount.model.Discount;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.vo.DiscountVO;

@ManagedBean(name = "discountDAO")
@ViewScoped
public class DiscountDAOImpl extends GenericDAOHibernate<Discount, Long> 
implements DiscountDAO, Serializable{

	private static final long serialVersionUID = -3124763343507384734L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<DiscountVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Discount.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<DiscountVO> voList = new ArrayList<DiscountVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Discount discount = (Discount)criteria.list().get(i);
			  DiscountVO vo = new DiscountVO();
			  
			  vo.setDiscountId(discount.getDiscountId());
			  vo.setCompanyId(discount.getCompany().getCompanyId());
			  vo.setCompanyName(discount.getCompany().getCompanyName());
			  vo.setDiscountCode(discount.getDiscountCode());
			  vo.setDiscountName(discount.getDiscountName());
			  vo.setDiscountValue(discount.getDiscountValue());
			  vo.setDiscountProviderCode(discount.getDiscountProvider().getParameterDtlCode());
			  vo.setDiscountProviderName(discount.getDiscountProvider().getParameterDtlName());
			  vo.setDiscountCategoryCode(discount.getDiscountCategory().getParameterDtlCode());
			  vo.setDiscountCategoryName(discount.getDiscountCategory().getParameterDtlName());
			  vo.setCategoryResume(discount.getCategoryResume());
			  vo.setActiveFlag(discount.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Discount.class);
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
		    crit.createAlias("discountProvider", "discountProvider");
		    crit.createAlias("discountCategory", "discountCategory");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("company.companyName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("discountCode", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("discountName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("categoryResume", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("discountCategory.parameterDtlName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("discountProvider.parameterDtlName", sSplit,MatchMode.ANYWHERE)))))));
					}
					
					if(searchVal.getSearchColumn().compareTo("company")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}	
					if(searchVal.getSearchColumn().compareTo("discountCode")==0){
						crit.add(Restrictions.ilike("discountCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("discountName")==0){
						crit.add(Restrictions.ilike("discountName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("discountProvider")==0){
						crit.add(Restrictions.ilike("discountProvider.parameterDtlCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("discountCategpry")==0){
						crit.add(Restrictions.ilike("discountCategory.parameterDtlCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("categoryResume")==0){
						crit.add(Restrictions.ilike("categoryResume", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("activeFlag")==0){
						crit.add(Restrictions.ilike("activeFlag", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
	
	
					
				}
			}
	}

	@Override
	public List<DiscountVO> searchDiscountList() {
		
		Criteria criteria = getSession().createCriteria(Discount.class);
		
		List<DiscountVO> voList = new ArrayList<DiscountVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Discount discount = (Discount)criteria.list().get(i);
			DiscountVO vo = new DiscountVO();
			vo.setDiscountId(discount.getDiscountId());
			vo.setCompanyId(discount.getCompany().getCompanyId());
			vo.setCompanyName(discount.getCompany().getCompanyName());
			vo.setDiscountCode(discount.getDiscountCode());
			vo.setDiscountName(discount.getDiscountName());
			vo.setDiscountValue(discount.getDiscountValue());
			vo.setDiscountProviderCode(discount.getDiscountProvider().getParameterDtlCode());
			vo.setDiscountProviderName(discount.getDiscountProvider().getParameterDtlName());
			vo.setDiscountCategoryCode(discount.getDiscountCategory().getParameterDtlCode());
			vo.setDiscountCategoryName(discount.getDiscountCategory().getParameterDtlName());
			vo.setCategoryResume(discount.getCategoryResume());
			vo.setActiveFlag(discount.getActiveFlag());
			voList.add(vo);
		}  		
		return voList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiscountDtl> searchDiscountDtlList(Long discountId) {
		Criteria criteria = getSession().createCriteria(DiscountDtl.class);
		criteria.createAlias("discount", "dicsount",
				CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("discount.discountId",discountId));
		
		criteria.addOrder(Order.asc("discount.discountId"));
		
		return criteria.list();
	}
	
	@Override
	public List<DiscountVO> searchDiscountByCompanyList(Long companyId) {
		
		Criteria criteria = getSession().createCriteria(Discount.class);
		criteria.createAlias("company", "company",
				CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("company.companyId", companyId));
		criteria.addOrder(Order.asc("discountId"));
		
		List<DiscountVO> voList = new ArrayList<DiscountVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Discount discount = (Discount)criteria.list().get(i);
			DiscountVO vo = new DiscountVO();
			vo.setDiscountId(discount.getDiscountId());
			vo.setCompanyId(discount.getCompany().getCompanyId());
			vo.setCompanyName(discount.getCompany().getCompanyName());
			vo.setDiscountCode(discount.getDiscountCode());
			vo.setDiscountName(discount.getDiscountName());
			vo.setDiscountValue(discount.getDiscountValue());
			vo.setDiscountProviderCode(discount.getDiscountProvider().getParameterDtlCode());
			vo.setDiscountProviderName(discount.getDiscountProvider().getParameterDtlName());
			vo.setDiscountCategoryCode(discount.getDiscountCategory().getParameterDtlCode());
			vo.setDiscountCategoryName(discount.getDiscountCategory().getParameterDtlName());
			vo.setCategoryResume(discount.getCategoryResume());
			vo.setActiveFlag(discount.getActiveFlag());
			voList.add(vo);
		}  		
		
		return voList;
	}
	

}
