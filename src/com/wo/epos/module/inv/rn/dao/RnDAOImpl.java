package com.wo.epos.module.inv.rn.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.rn.model.Rn;
import com.wo.epos.module.inv.rn.vo.RnVO;

@ManagedBean(name = "rnDAO")
@ViewScoped
public class RnDAOImpl extends GenericDAOHibernate<Rn, Long> implements RnDAO, Serializable{

	private static final long serialVersionUID = -2233388977781228442L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<RnVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Rn.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<RnVO> voList = new ArrayList<RnVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Rn rn = (Rn)criteria.list().get(i);
			  RnVO vo = new RnVO();
			  
			  vo.setRnId(rn.getRnId());
			  vo.setRnDate(rn.getRnDate());
			  vo.setRnNo(rn.getRnNo());
			  vo.setItemResume(rn.getItemResume());
			  if(rn.getPo() !=null){
				  vo.setPoId(rn.getPoId());
				  vo.setPoNumber(rn.getPo().getPoNo());				  
			  }
			  if(rn.getRnType() !=null){
				  vo.setRnTypeCode(rn.getRnTypeCode());
				  vo.setRnTypeName(rn.getRnType().getParameterDtlName());
			  }
			  if(rn.getOutlet() !=null){
				  vo.setOutletId(rn.getOutletId());
				  vo.setOutletName(rn.getOutlet().getOutletName());				  
			  }
			  
			  if(rn.getCompany() !=null){
				  vo.setCompanyId(rn.getCompany().getCompanyId());
				  vo.setCompanyName(rn.getCompany().getCompanyName());
			  }
			  
			  if(rn.getOutletOrigin() !=null){
				  vo.setOutletOriginId(rn.getOutletId());
				  vo.setOutletOriginCode(rn.getOutletOrigin().getOutletCode());
				  vo.setOutletOriginName(rn.getOutletOrigin().getOutletName());				  
			  }
			  if(rn.getSupplier() !=null){
				  vo.setSupplierId(rn.getSupplierId());
				  vo.setSupplierCode(rn.getSupplier().getSupplierCode());
				  vo.setSupplierName(rn.getSupplier().getSupplierName());
			  }
			  if(rn.getDO() !=null){
				  vo.setDoId(rn.getDO().getDoId());
				  vo.setDoNo(rn.getDO().getDoNo());
				  vo.setDOName(rn.getDO().getDoNo());
			  }
			  
			  vo.setSupplierDocDate(rn.getSupplierDocDate());
			  vo.setSupplierDocNo(rn.getSupplierDocNo());
			  vo.setNotes(rn.getNotes());
			  vo.setActiveFlag(rn.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Rn.class);
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
		    crit.createAlias("outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		    crit.createAlias("supplier", "supplier", CriteriaSpecification.LEFT_JOIN);
		    crit.createAlias("rnType", "paramDtl");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
				   if(searchVal.getSearchColumn().compareTo("outletLogin")==0){
						crit.add(Restrictions.eq("outlet.outletId", searchVal.getSearchValue()));
					}
				    
				    if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						    String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("rnNo", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("supplierDocNo", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemResume", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("paramDtl.parameterDtlName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("supplier.supplierName", sSplit,MatchMode.ANYWHERE))))));
					}		
				    
				    if(searchVal.getSearchColumn().compareTo("company")==0){
						crit.add(Restrictions.eq("companyId", searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("rnNumber")==0){
						crit.add(Restrictions.ilike("rnNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("supplierDocNo")==0){
						crit.add(Restrictions.ilike("supplierDocNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("itemResume")==0){
						crit.add(Restrictions.ilike("itemResume", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}						
					if(searchVal.getSearchColumn().compareTo("typeSender")==0){
						crit.add(Restrictions.eq("paramDtl.parameterDtlName", searchVal.getSearchValue()));
					}
				
				}
			}
	}

	@Override
	public String searchRnNumberMax(String dateYearMonth) {
        String rnNumber = null;
		
		String sql = "SELECT MAX(RN_NO) FROM POS_RN WHERE RN_NO LIKE '%"+dateYearMonth+"%' ";
		Query query = getSession().createSQLQuery(sql);
	        
        if(query.list().size() > 0){
			if (query.uniqueResult() != null
					&& !query.uniqueResult().toString().equals("")) {
        	    rnNumber = query.uniqueResult().toString();
        	}
        }
		
		return rnNumber;
	}
	
}
