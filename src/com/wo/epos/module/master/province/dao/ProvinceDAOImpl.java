package com.wo.epos.module.master.province.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.master.province.vo.ProvinceVO;

@ManagedBean(name = "provinceDAO")
@ViewScoped
public class ProvinceDAOImpl extends GenericDAOHibernate<Province, Long> 
        implements ProvinceDAO, Serializable {
	
	private static final long serialVersionUID = -8478104315317955850L;
	
	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<ProvinceVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
        Criteria criteria = getSession().createCriteria(Province.class);
    	decorateCriteria(criteria, searchCriteria);    	
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
				
		List<ProvinceVO> provinceVOList = new ArrayList<ProvinceVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Province provinceTemp = (Province)criteria.list().get(i);			
			ProvinceVO provinceVO = new ProvinceVO();
			provinceVO.setProvinceId(provinceTemp.getProvinceId());
			provinceVO.setProvinceCode(provinceTemp.getProvinceCode());
			provinceVO.setProvinceName(provinceTemp.getProvinceName());
			provinceVO.setActiveFlag(provinceTemp.getActiveFlag());
			
			provinceVOList.add(provinceVO);
		}
		
		
		return provinceVOList;
	}

	@SuppressWarnings({ "rawtypes"})
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Province.class);
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
									Restrictions.ilike("provinceCode", sSplit, MatchMode.ANYWHERE),
									Restrictions.ilike("provinceName", sSplit,MatchMode.ANYWHERE)));
					}
					
					if(searchVal.getSearchColumn().compareTo("provinceCode")==0){
						crit.add(Restrictions.ilike("provinceCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					
					if(searchVal.getSearchColumn().compareTo("provinceName")==0){
						crit.add(Restrictions.ilike("provinceName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}		
										
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
			//crit.addOrder(Order.asc("provinceCode"));
	}	

	@Override
	public List<ProvinceVO> provinceSearch() {
        String sql = "SELECT PROVINCE_ID, PROVINCE_CODE, PROVINCE_NAME, ACTIVE_FLAG FROM POS_PROVINCE";
        
        Query query = getSession().createSQLQuery(sql);
		
        List<ProvinceVO> provinceVoList = new ArrayList<ProvinceVO>();
        
        for(int i=0; i<query.list().size(); i++){
        	Object[] obj = (Object[])query.list().get(i);
        	ProvinceVO provinceVO = new ProvinceVO();
        	provinceVO.setProvinceId(Long.parseLong(obj[0]+""));
        	provinceVO.setProvinceCode((String)obj[1]);
        	provinceVO.setProvinceName((String)obj[2]);
        	provinceVO.setActiveFlag((String)obj[3]);
        	
        	provinceVoList.add(provinceVO);
        }
        
		return provinceVoList;
	}

		
}
