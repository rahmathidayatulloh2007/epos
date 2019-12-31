package com.wo.epos.module.master.systemProperty.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.vo.SystemPropertyVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;


@ManagedBean(name = "systemPropertyDAO")
@ViewScoped
public class SystemPropertyDAOImpl extends GenericDAOHibernate<SystemProperty, Long>
   implements SystemPropertyDAO, Serializable {

	static Logger logger = Logger.getLogger(SystemPropertyDAOImpl.class);
	
	private static final long serialVersionUID = -8830107780359207569L;

//	@SuppressWarnings("rawtypes")
//	@Override
//	public List<SystemPropertyVO> searchData(
//			List<? extends SearchObject> searchCriteria, int first,
//			int pageSize, String sortField, boolean sortOrder) throws Exception {
//		Criteria criteria = getSession().createCriteria(SystemProperty.class);
//		decorateCriteria(criteria, searchCriteria);
//		
//		criteria.setFirstResult(first);
//		criteria.setMaxResults(pageSize);
//		
//		List<SystemPropertyVO> voList = new ArrayList<SystemPropertyVO>();
//		for(int i=0; i<criteria.list().size(); i++){
//			  SystemProperty sysProp = (SystemProperty)criteria.list().get(i);
//			  SystemPropertyVO sysPropVO = new SystemPropertyVO();
//			  sysPropVO.setSystemPropertyId(sysProp.getSystemPropertyId());
//			  sysPropVO.setSystemPropertyName(sysProp.getSystemPropertyName());
//			  sysPropVO.setSystemPropertyValue(sysProp.getSystemPropertyValue());
//			  sysPropVO.setSystemPropertyDesc(sysProp.getSystemPropertyDesc());
//			  if(sysProp.getCompany() !=null){
//			     sysPropVO.setCompanyId(sysProp.getCompany().getCompanyId());
//			     sysPropVO.setCompanyName(sysProp.getCompany().getCompanyName());
//			  }
//			 
//			  sysPropVO.setActiveFlag(sysProp.getActiveFlag());
//			  
//			  voList.add(sysPropVO);
//		}
//		
//		return voList;
//	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CompanyVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		Criteria criteria = getSession().createCriteria(SystemProperty.class);
		decorateCriteria(criteria, searchCriteria);

		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<CompanyVO> voList = new ArrayList<CompanyVO>();
		for (int i = 0; i < criteria.list().size(); i++) {
			Company obj = (Company) criteria.list().get(i);
			CompanyVO vo = new CompanyVO();
			vo.setCompanyId(obj.getCompanyId());
			vo.setCompanyCode(obj.getCompanyCode());
			vo.setCompanyName(obj.getCompanyName());

			vo.setActiveFlag(obj.getActiveFlag());

			voList.add(vo);
		}

		return voList;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(SystemProperty.class);
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
	    crit.createAlias("company", "company", CriteriaSpecification.LEFT_JOIN);
	    
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
					 String sSplit = searchVal.getSearchValueAsString().trim();
						crit.add(Restrictions.or(
								Restrictions.ilike("systemPropertyName", sSplit, MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("systemPropertyValue", sSplit,MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("systemPropertyDesc", sSplit,MatchMode.ANYWHERE),
								Restrictions.ilike("company.companyName", sSplit,MatchMode.ANYWHERE)))));
				}
				
				if(searchVal.getSearchColumn().compareTo("systemPropertyName")==0){
					crit.add(Restrictions.ilike("systemPropertyName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
				}			
				if(searchVal.getSearchColumn().compareTo("systemPropertyValue")==0){
					crit.add(Restrictions.ilike("systemPropertyValue", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
				}	
				if(searchVal.getSearchColumn().compareTo("systemPropertyDesc")==0){
					crit.add(Restrictions.ilike("systemPropertyDesc", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
				}
				if(searchVal.getSearchColumn().compareTo("companyId")==0){
					crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
				}
			}
		}
		
		crit.add(Restrictions.eq("activeFlag", "Y"));
		
		crit.add(Restrictions.isNotNull("company.companyId"));
		crit.setProjection(Projections.projectionList().add(
				Projections.distinct(Projections.property("company"))));
	}

	@Override
	public List<SystemPropertyVO> searchSystemPropertyList() {		
		return null;
	}


	@Override
	public SystemProperty searchSystemPropertyName(String systemPropertyName) {
		SystemProperty systemProperty = null;
		
		Criteria criteria = getSession().createCriteria(SystemProperty.class);
		criteria.add(Restrictions.eq("systemPropertyName", systemPropertyName));
		
		if(criteria.list().size() > 0){
			systemProperty = (SystemProperty) criteria.uniqueResult();
		}
		
		return systemProperty;
	}
	
	@Override
	public SystemProperty searchSystemPropertyNameAndCompany(String systemPropertyName, Long companyId) {
		SystemProperty systemProperty = null;
		
		Criteria criteria = getSession().createCriteria(SystemProperty.class);
		criteria.createAlias("company", "company", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("company.companyId", companyId));
		criteria.add(Restrictions.eq("systemPropertyName", systemPropertyName));
		
		if(criteria.list().size() > 0){
			systemProperty = (SystemProperty) criteria.uniqueResult();
		}
		
		return systemProperty;
	}
	
	@Override
	public void deleteByCompanyId(Long companyId) {
		try {
			String hql = "delete from SystemProperty where company.companyId = :companyId";
			
			Query query = getSession().createQuery(hql);
			query.setLong("companyId", companyId);
			
			query.executeUpdate();
		} catch (HibernateException e) {

			logger.error(e.getMessage(), e);
			throw e;
		}
	}	
}