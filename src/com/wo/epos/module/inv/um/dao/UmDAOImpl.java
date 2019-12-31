package com.wo.epos.module.inv.um.dao;

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
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.vo.UmVO;

@ManagedBean(name = "umDAO")
@ViewScoped
public class UmDAOImpl extends GenericDAOHibernate<Um, Long> 
        implements UmDAO, Serializable {

	private static final long serialVersionUID = 6573646331032730070L;

	
	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<UmVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Um.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<UmVO> voList = new ArrayList<UmVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Um um = (Um)criteria.list().get(i);
			  UmVO vo = new UmVO();
			  vo.setUmId(um.getUmId());
			  vo.setUmName(um.getUmName());
			  vo.setUmDesc(um.getUmDesc());
			  vo.setCompanyId(um.getCompanyId());
			  
			  if(um.getCompany() !=null){
			     vo.setCompanyName(um.getCompany().getCompanyName());
			  }
			  
			  vo.setActiveFlag(um.getActiveFlag());
			  			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Um.class);
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
		    crit.createAlias("company", "company", Criteria.LEFT_JOIN);
		    		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("umName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("umDesc", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("company.companyName", sSplit,MatchMode.ANYWHERE))));
					}
					
					if(searchVal.getSearchColumn().compareTo("umName")==0){
						crit.add(Restrictions.ilike("umName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					
					if(searchVal.getSearchColumn().compareTo("umDesc")==0){
						crit.add(Restrictions.ilike("umDesc", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}		
					
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}					
					
					
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@Override
	public List<UmVO> searchUmList() {
		
		Criteria criteria = getSession().createCriteria(Um.class);
		
		List<UmVO> voList = new ArrayList<UmVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Um um = (Um)criteria.list().get(i);
			  UmVO vo = new UmVO();
			  vo.setUmId(um.getUmId());
			  vo.setUmName(um.getUmName());
			  vo.setUmDesc(um.getUmDesc());
			  vo.setCompanyId(um.getCompanyId());
			  
			  if(um.getCompany() !=null){
			     vo.setCompanyName(um.getCompany().getCompanyName());
			  }
			  
			  vo.setActiveFlag(um.getActiveFlag());
			  			  
			  voList.add(vo);
		}
		
		return voList;
	}	
	
	@Override
	public List<UmVO> searchUmCompany(Long companyId) {		
		Criteria criteria = getSession().createCriteria(Um.class);
		criteria.createAlias("company", "company", Criteria.LEFT_JOIN);
		
		if(companyId !=null){
			criteria.add(Restrictions.eq("company.companyId", companyId));
		}else{
			criteria.add(Restrictions.isNull("company.companyId"));
		}
		
		List<UmVO> voList = new ArrayList<UmVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Um um = (Um)criteria.list().get(i);
			  UmVO vo = new UmVO();
			  vo.setUmId(um.getUmId());
			  vo.setUmName(um.getUmName());
			  vo.setUmDesc(um.getUmDesc());
			  vo.setCompanyId(um.getCompanyId());			  
			  if(um.getCompany() !=null){
			     vo.setCompanyName(um.getCompany().getCompanyName());
			  }			  
			  vo.setActiveFlag(um.getActiveFlag());			  			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public Um findByName(String umName) {
		Criteria criteria = getSession().createCriteria(Um.class);	
		
		criteria.add(Restrictions.eq("umName", umName));		
		criteria.addOrder(Order.asc("umId"));
		
		return (Um) criteria.uniqueResult();
	}	
	
}
	