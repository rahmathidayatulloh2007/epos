package com.wo.epos.module.sales.businessType.dao;

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
import com.wo.epos.module.sales.businessType.model.BusinessType;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;


@ManagedBean(name = "businessTypeDAO")
@ViewScoped
public class BusinessTypeDAOImpl extends GenericDAOHibernate<BusinessType, Long> implements BusinessTypeDAO, Serializable{

	private static final long serialVersionUID = -3583670681400614387L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<BusinessTypeVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(BusinessType.class);
		decorateCriteria(criteria, searchCriteria);
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<BusinessTypeVO> voList = new ArrayList<BusinessTypeVO>();
		
		for (int i = 0; i < criteria.list().size(); i++) {
			BusinessType businessType = (BusinessType) criteria.list().get(i);
			BusinessTypeVO vo = new BusinessTypeVO();

			vo.setBusinessTypeId(businessType.getBusinessTypeId());
			vo.setBusinessTypeCode(businessType.getBusinessTypeCode());
			vo.setBusinessTypeName(businessType.getBusinessTypeName());
			vo.setActiveFlag(businessType.getActiveFlag());

			voList.add(vo);
		}
		return voList; 
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(BusinessType.class);
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
			crit.createAlias("", "province");
	 		
	 	if (searchCriteria != null){
	 		for (SearchObject searchVal : searchCriteria){
	 			if (searchVal.getSearchColumn().compareTo("searchAll") == 0){
	 				String sSplit = searchVal.getSearchValueAsString().trim();
	 				crit.add(Restrictions.or(
	 						Restrictions.ilike("businessTypeCode",sSplit,MatchMode.ANYWHERE),
	 						Restrictions.or(Restrictions.ilike("businessTypeName", sSplit,MatchMode.ANYWHERE), 
	 						Restrictions.ilike("province.provinceName", sSplit,MatchMode.ANYWHERE))));
	 			}
	 			
	 			if(searchVal.getSearchColumn().compareTo("businessTypeCode")==0){
					crit.add(Restrictions.ilike("businessTypeCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
				}	
				
				if(searchVal.getSearchColumn().compareTo("businessTypeName")==0){
					crit.add(Restrictions.ilike("businessTypeName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
				}	
	
	 		}
	 	}
	 	
	 	crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@Override
	public List<BusinessTypeVO> businessTypeSearch() {
		// TODO Auto-generated method stub
		
		Criteria criteria = getSession().createCriteria(BusinessType.class);		
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		List<BusinessTypeVO> businessTypeVOList = new ArrayList<BusinessTypeVO>();
		
		for(int i = 0; i<criteria.list().size();i++)
		{
			BusinessType businessType = (BusinessType) criteria.list().get(i);
			BusinessTypeVO businessTypeVO = new BusinessTypeVO();
			
			businessTypeVO.setBusinessTypeId(businessType.getBusinessTypeId());
			businessTypeVO.setBusinessTypeCode(businessType.getBusinessTypeCode());
			businessTypeVO.setBusinessTypeName(businessType.getBusinessTypeName());
			businessTypeVO.setActiveFlag(businessType.getActiveFlag());
			
			businessTypeVOList.add(businessTypeVO);
		}
		return businessTypeVOList;
	
	}
	
	

}

	
	
	

