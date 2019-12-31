package com.wo.epos.module.inv.DO.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.DO.model.DO;
import com.wo.epos.module.inv.DO.vo.DOVO;


@ManagedBean(name = "DODAO")
@ViewScoped
public class DODAOImpl extends GenericDAOHibernate<DO, Long> 
        implements DODAO, Serializable {

	private static final long serialVersionUID = 6573646331032730070L;

	
	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<DOVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(DO.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		System.out.println("query=="+criteria.toString());
		List<DOVO> voList = new ArrayList<DOVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  DO DO = (DO)criteria.list().get(i);
			  DOVO vo = new DOVO();
			  vo.setDoId(DO.getDoId());
			  vo.setOutlet(DO.getOutlet());
			  vo.setDoDate(DO.getDoDate());
			  vo.setDoNo(DO.getDoNo());
			  vo.setTransferFrom(DO.getTransferFrom());
			  vo.setTransferTo(DO.getTransferTo());
			  vo.setOutlet(DO.getOutlet());
			  vo.setDoType(DO.getDoType());
			  vo.setSoId(DO.getSoId());
			  vo.setItemResume(DO.getItemResume());
			  vo.setNotes(DO.getNotes());
			  vo.setStatus(DO.getStatus());
			  vo.setActiveFlag(DO.getActiveFlag());
			  vo.setOutletName(DO.getOutlet()!=null?DO.getOutlet().getOutletName():null);
			  vo.setCompanyId(DO.getCompany().getCompanyId());
			
			  vo.setListDODtl(DO.getListDODtl());	
			  System.out.println("size=="+DO.getListDODtl().size());
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(DO.class);
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
		    crit.createAlias("transferTo", "transferTo", Criteria.LEFT_JOIN);
		    		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("doNo", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("outlet.outletName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("transferTo.outletName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("itemResume", sSplit,MatchMode.ANYWHERE)
									))
									));
					}
					
					if(searchVal.getSearchColumn().compareTo("outletId")==0){
						crit.add(Restrictions.eq("outlet.outletId", new Long(searchVal.getSearchValueAsString().trim())));
					}	
					
					if(searchVal.getSearchColumn().compareTo("doNo")==0){
						crit.add(Restrictions.ilike("doNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}		
					
					if(searchVal.getSearchColumn().compareTo("startDoDate")==0){
						crit.add(Restrictions.gt("doDate", (Date)searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("endDoDate")==0){
						crit.add(Restrictions.lt("doDate", (Date)searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("transferTo")==0){
						crit.add(Restrictions.eq("transferTo.outletName", searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("item")==0){
						crit.add(Restrictions.eq("itemResume", searchVal.getSearchValue()));
					}	
					
					
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<DOVO> getDOList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder)
		 {
		Criteria criteria = getSession().createCriteria(DO.class);
		decorateCriteria(criteria, searchCriteria);
		
		//criteria.setFirstResult(first);
		//criteria.setMaxResults(pageSize);
		
		System.out.println("query=="+criteria.toString());
		List<DOVO> voList = new ArrayList<DOVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  DO DO = (DO)criteria.list().get(i);
			  DOVO vo = new DOVO();
			  vo.setDoId(DO.getDoId());
			  vo.setOutlet(DO.getOutlet());
			  vo.setDoDate(DO.getDoDate());
			  vo.setDoNo(DO.getDoNo());
			  vo.setTransferFrom(DO.getTransferFrom());
			  vo.setTransferTo(DO.getTransferTo());
			  vo.setOutlet(DO.getOutlet());
			  vo.setDoType(DO.getDoType());
			  vo.setSoId(DO.getSoId());
			  vo.setItemResume(DO.getItemResume());
			  vo.setNotes(DO.getNotes());
			  vo.setStatus(DO.getStatus());
			  vo.setActiveFlag(DO.getActiveFlag());
			  vo.setListDODtl(DO.getListDODtl());	
			  //System.out.println("size=="+DO.getListDODtl().size());
			  voList.add(vo);
		}
		
		return voList;
	}
	
	public DO getLastDoId() {
		
		Criteria criteria = getSession().createCriteria(DO.class);
		criteria.add(Restrictions.isNotNull("doNo"));
		criteria.addOrder(Order.desc("doNo"));
		
		//criteria.setFirstResult(1);
		criteria.setMaxResults(1);
		
		return (DO)criteria.uniqueResult();
	}

	@Override
	public List<DOVO> findByOutlet(Long outletId) {
		Criteria criteria = getSession().createCriteria(DO.class);
		criteria.createAlias("outlet", "outlet", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("outlet.outletId", outletId));
		
		List<DOVO> listTraItemVo = new ArrayList<DOVO>();
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				  DO item = (DO)criteria.list().get(i);
				  DOVO vo = new DOVO();
				  vo.setDoId(item.getDoId());
				  vo.setOutlet(item.getOutlet());
				  vo.setDoNo(item.getDoNo());
				  
				  listTraItemVo.add(vo);
			}
		}
		
		return listTraItemVo;
	}

	@Override
	public DO findByDoNo(String doNumber) {
		Criteria criteria = getSession().createCriteria(DO.class);
		
		criteria.add(Restrictions.eq("doNo", doNumber));
		
		return (DO) criteria.uniqueResult();
	}	
	
	@Override
	public List<DOVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId) {
		Criteria criteria = getSession().createCriteria(DO.class);
		criteria.createAlias("transferFrom", "transferFrom", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("transferTo", "transferTo", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("status", CommonConstants.DO_NEW));
		criteria.add(Restrictions.eq("doType", CommonConstants.DOTYPE_TRANSFERITEM));
		criteria.add(Restrictions.eq("transferFrom.outletId", outletFromId));
		criteria.add(Restrictions.eq("transferTo.outletId", outletToId));
		
		List<DOVO> listTraItemVo = new ArrayList<DOVO>();
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				  DO item = (DO)criteria.list().get(i);
				  DOVO vo = new DOVO();
				  vo.setDoId(item.getDoId());
				  vo.setOutlet(item.getOutlet());
				  vo.setDoNo(item.getDoNo());
				  vo.setTransferFrom(item.getTransferFrom());
				  vo.setTransferTo(item.getTransferTo());
				  vo.setDoType(item.getDoType());
				  vo.setStatus(item.getStatus());
				  
				  listTraItemVo.add(vo);
			}
		}
		
		return listTraItemVo;
	}
	
	public java.sql.Connection getConnection() {
        try {
        	Session sfi = (Session)getSession();
        	java.sql.Connection conn = sfi.connection();
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
	
	
	
}
	