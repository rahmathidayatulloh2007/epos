package com.wo.epos.module.sales.subDistricts.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.subDistricts.model.SubDistricts;
import com.wo.epos.module.sales.subDistricts.vo.SubDistrictsVO;


@ManagedBean(name = "subDistrictsDAO")
@ViewScoped
public class SubDistrictsDAOImpl extends GenericDAOHibernate<SubDistricts, Long> implements SubDistrictsDAO, Serializable{

	private static final long serialVersionUID = -3583670681400614387L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<SubDistrictsVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(SubDistricts.class);
		decorateCriteria(criteria, searchCriteria);
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<SubDistrictsVO> voList = new ArrayList<SubDistrictsVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  SubDistricts subDistricts = (SubDistricts)criteria.list().get(i);
			  SubDistrictsVO vo = new SubDistrictsVO();
			  
			  vo.setSubDistrictId(subDistricts.getSubDistrictId());
			  vo.setSubDistrictCode(subDistricts.getSubDistrictCode());
			  vo.setSubDistrictName(subDistricts.getSubDistrictName());
			  vo.setDistrictId(subDistricts.getDistricts().getDistrictId());
			  vo.setDistrictName(subDistricts.getDistricts().getDistrictName());
			 
			  vo.setActiveFlag(subDistricts.getActiveFlag());
			  
			  voList.add(vo);
			  
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(SubDistricts.class);
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
	public List<SubDistrictsVO> subDistrictsSearch() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(SubDistricts.class);		
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		List<SubDistrictsVO> subDistrictsVOList = new ArrayList<SubDistrictsVO>();
		
		for(int i = 0; i<criteria.list().size(); i++)
		{
			SubDistricts subDistricts = (SubDistricts) criteria.list().get(i);
			SubDistrictsVO subDistrictsVO = new SubDistrictsVO();
			
			subDistrictsVO.setSubDistrictId(subDistricts.getSubDistrictId());
			subDistrictsVO.setSubDistrictCode(subDistricts.getSubDistrictCode());
			subDistrictsVO.setSubDistrictName(subDistricts.getSubDistrictName());
			subDistrictsVO.setDistrictId(subDistricts.getDistricts().getDistrictId());
			subDistrictsVO.setDistrictName(subDistricts.getDistricts().getDistrictName());
			
			subDistrictsVO.setActiveFlag(subDistricts.getActiveFlag());
			subDistrictsVOList.add(subDistrictsVO);
		}
		return subDistrictsVOList;
	}

	
	
	
}
