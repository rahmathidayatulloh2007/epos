package com.wo.epos.module.purchasing.po.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.purchasing.po.model.Po;
import com.wo.epos.module.purchasing.po.vo.PoVO;


@ManagedBean(name = "poDAO")
@ViewScoped
public class PoDAOImpl extends GenericDAOHibernate<Po, Long> implements PoDAO, Serializable  {

	private static final long serialVersionUID = 7966659353135799855L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<PoVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Po.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<PoVO> voList = new ArrayList<PoVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Po po = (Po)criteria.list().get(i);
			  PoVO vo = new PoVO();
			  vo.setPoId(po.getPoId());
			  	  
			  if(po.getOutlet() !=null){
				  vo.setOutletId(po.getOutletId());		
				  vo.setOutletName(po.getOutlet().getOutletName());				  
			  }
			  
			  if(po.getCompany() !=null){
				  vo.setCompanyId(po.getCompany().getCompanyId());
				  vo.setCompanyName(po.getCompany().getCompanyName());
			  }
			  
			  vo.setSupplierId(po.getSupplierId());
			  if(po.getSupplier() !=null){
				  vo.setSupplierName(po.getSupplier().getSupplierName());
				  vo.setSupplierCode(po.getSupplier().getSupplierCode());
			  }
			  
			  vo.setPoNo(po.getPoNo());
			  vo.setNotes(po.getNotes());
			  
			  vo.setTaxTypeCode(po.getTaxTypeCode());
			  if(po.getTaxType() !=null){
				  vo.setTaxTypeName(po.getTaxType().getParameterDtlName());
			  }
			  
			  vo.setStatusCode(po.getStatusCode());
			  if(po.getStatus() !=null){
				  vo.setStatusName(po.getStatus().getParameterDtlName());
			  }
			  
			  vo.setCloseReason(po.getCloseReason());
			  vo.setPoDate((Date)po.getPoDate());				  
			  vo.setTaxValue(po.getTaxValue());	
			  vo.setItemResume(po.getItemResume());
			  vo.setActiveFlag(po.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Po.class);
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
		    crit.createAlias("supplier", "supplier");
		    crit.createAlias("outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		    crit.createAlias("status", "parameterDtl", CriteriaSpecification.LEFT_JOIN);
		    		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					
					if(searchVal.getSearchColumn().compareTo("outletLogin")==0){
						crit.add(Restrictions.eq("outlet.outletId", searchVal.getSearchValue()));
						
						if(searchVal.getSearchColumn().compareTo("searchAll") == 0) {
							 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(Restrictions.ilike("supplier.supplierName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("poNo", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemResume", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("parameterDtl.parameterDtlName", sSplit,MatchMode.ANYWHERE)))));
						}
						
					} else if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("outlet.outletName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("supplier.supplierName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("poNo", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemResume", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("parameterDtl.parameterDtlName", sSplit,MatchMode.ANYWHERE))))));
					}
					
					if(searchVal.getSearchColumn().compareTo("company")==0){
						crit.add(Restrictions.eq("companyId", searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("poNo")==0){
						crit.add(Restrictions.ilike("poNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}		
					
					if(searchVal.getSearchColumn().compareTo("supplierName")==0){
						crit.add(Restrictions.ilike("supplier.supplierName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("itemResume")==0){
						crit.add(Restrictions.ilike("itemResume", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("statusName")==0){
						crit.add(Restrictions.ilike("parameterDtl.parameterDtlName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("startDate")==0){
						crit.add(Restrictions.ge("poDate", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("endDate")==0){
						crit.add(Restrictions.le("poDate", searchVal.getSearchValue()));
					}
					
				}
			}
	}

	@Override
	public String searchPoMax(String yearMonth) {
		String numberPo = null;
		
		String sql = "SELECT MAX(PO_NO) FROM POS_PO WHERE PO_NO LIKE '%"+yearMonth+"%' ";
		Query query = getSession().createSQLQuery(sql);
		        
        if(query.list().size() > 0){
			if (query.uniqueResult() != null
					&& !query.uniqueResult().toString().equals("")) {
        	   numberPo = query.uniqueResult().toString();
        	} 
        }
		
		return numberPo;
	}

	@Override
	public List<PoVO> searchPoNumber(Long supplierId) {
		Criteria criteria = getSession().createCriteria(Po.class);		
		if(supplierId !=null){
			criteria.add(Restrictions.eq("supplierId", supplierId));
		}
		
		criteria.add(Restrictions.ne("statusCode", CommonConstants.PO_CLOSE));
		
		List<PoVO> poVoList = new ArrayList<PoVO>();
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				  Po poTemp = (Po) criteria.list().get(i);
				  
				  PoVO poVo = new PoVO();
                  poVo.setPoId(poTemp.getPoId());
                  poVo.setPoNo(poTemp.getPoNo());
                  if(poTemp.getOutlet() !=null){
                	  if(poTemp.getOutlet().getCompany() !=null){
                		  poVo.setCompanyId(poTemp.getOutlet().getCompany().getCompanyId());
                		  poVo.setCompanyName(poTemp.getOutlet().getCompany().getCompanyName());
                	  }
                	  
                	  poVo.setOutletId(poTemp.getOutlet().getOutletId());
                	  poVo.setOutletName(poTemp.getOutlet().getOutletName());
                  }
				  
				  poVoList.add(poVo);
			}
		}
		
		return poVoList;
	}

	@Override
	public Po findByPoNumber(String poNumber) {
		Criteria criteria = getSession().createCriteria(Po.class);
		criteria.add(Restrictions.eq("poNo", poNumber.trim()));
		
		return (Po) criteria.uniqueResult();
	}
	
	
	
}