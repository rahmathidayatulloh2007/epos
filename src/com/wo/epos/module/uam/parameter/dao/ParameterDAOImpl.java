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
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

@ManagedBean(name = "parameterDAO")
@ViewScoped
public class ParameterDAOImpl extends GenericDAOHibernate<Parameter, String> 
        implements ParameterDAO, Serializable {
	
	private static final long serialVersionUID = -8478104315317955850L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ParameterVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
        Criteria criteria = getSession().createCriteria(Parameter.class);
				
        decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<ParameterVO> voList = new ArrayList<ParameterVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Parameter param = (Parameter)criteria.list().get(i);
			  ParameterVO vo = new ParameterVO();
			  vo.setParameterCode(param.getParameterCode());
			  vo.setParameterName(param.getParameterName());
			  vo.setParameterDesc(param.getParameterDesc());
			  vo.setDetailResume(param.getDetailResume());
			  vo.setActiveFlag(param.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Parameter.class);
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
	public List<ParameterVO> parameterSearch() {
        String sql = "SELECT PARAMETER_ID, PARAMETER_CODE, PARAMETER_NAME, PARAMETER_DESC, DETAIL_RESUME, ACTIVE_FLAG FROM POS_PARAMETER";
        
		Query query = getSession().createSQLQuery(sql);

		List<ParameterVO> parameterVoList = new ArrayList<ParameterVO>();

		for (int i = 0; i < query.list().size(); i++) {
			Object[] obj = (Object[]) query.list().get(i);
			ParameterVO parameterVO = new ParameterVO();
			parameterVO.setParameterCode((String) obj[0]);
			parameterVO.setParameterName((String) obj[1]);
			parameterVO.setParameterDesc((String) obj[2]);
			parameterVO.setDetailResume((String) obj[3]);
			parameterVO.setActiveFlag((String) obj[4]);

			parameterVoList.add(parameterVO);
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
