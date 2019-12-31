package com.wo.epos.module.uam.parameter.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;


@ManagedBean(name = "parameterDtlDAO")
@ViewScoped
public class ParameterDtlDAOImpl extends GenericDAOHibernate<ParameterDtl, String> 
        implements ParameterDtlDAO, Serializable {
	
	private static final long serialVersionUID = -8478104315317955850L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ParameterDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
        Criteria criteria = getSession().createCriteria(ParameterDtl.class);
				
        decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<ParameterDtlVO> voList = new ArrayList<ParameterDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  ParameterDtl param = (ParameterDtl)criteria.list().get(i);
			  ParameterDtlVO vo = new ParameterDtlVO();
			  vo.setParameterDtlCode(param.getParameterDtlCode());
			  vo.setParameterDtlName(param.getParameterDtlName());
			  vo.setParameterDtlDesc(param.getParameterDtlDesc());
			  vo.setActiveFlag(param.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(ParameterDtl.class);
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
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("parameterCode", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("parameterName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("parameterDesc", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("detailResume", sSplit,MatchMode.ANYWHERE)))));
					}
					
					if(searchVal.getSearchColumn().compareTo("parameterCode")==0){
						crit.add(Restrictions.ilike("parameterCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}					
					if(searchVal.getSearchColumn().compareTo("parameterName")==0){
						crit.add(Restrictions.ilike("parameterName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}					
					if(searchVal.getSearchColumn().compareTo("parameterDesc")==0){
						crit.add(Restrictions.ilike("parameterDesc", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("detailResume")==0){
						crit.add(Restrictions.ilike("detailResume", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}					
				}
			}
	}
	

	@Override
	public List<ParameterDtlVO> parameterSearch() {
        String sql = "SELECT PARAMETER_ID, PARAMETER_CODE, PARAMETER_NAME, PARAMETER_DESC, DETAIL_RESUME, ACTIVE_FLAG FROM POS_PARAMETER";
        
		Query query = getSession().createSQLQuery(sql);

		List<ParameterDtlVO> parameterVoList = new ArrayList<ParameterDtlVO>();

		for (int i = 0; i < query.list().size(); i++) {
			Object[] obj = (Object[]) query.list().get(i);
			ParameterDtlVO parameterDtlVO = new ParameterDtlVO();
			parameterDtlVO.setParameterDtlCode((String) obj[0]);
			parameterDtlVO.setParameterDtlName((String) obj[1]);
			parameterDtlVO.setParameterDtlDesc((String) obj[2]);
			parameterDtlVO.setActiveFlag((String) obj[3]);

			parameterVoList.add(parameterDtlVO);
		}
        
		return parameterVoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		Criteria criteria = getSession().createCriteria(ParameterDtl.class);
		criteria.createAlias("parameter", "param",
				CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("param.parameterCode",parameterCode));
		
		criteria.addOrder(Order.asc("param.parameterCode"));
		
		return criteria.list();
	}

	@Override
	public ParameterDtl findByDetailId(String id) {
		Criteria criteria = getSession().createCriteria(ParameterDtl.class);
		
		criteria.add(Restrictions.eq("parameterDtlCode",id));
		
		return (ParameterDtl)criteria.uniqueResult();
	}

		
}
