package com.wo.epos.module.master.city.dao;

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
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.model.Province;

@ManagedBean(name = "cityDAO")
@ViewScoped
public class CityDAOImpl extends GenericDAOHibernate<City, Long> implements CityDAO, Serializable{

	private static final long serialVersionUID = -4611727406366648590L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<CityVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {		
		Criteria criteria = getSession().createCriteria(City.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<CityVO> cityVOList = new ArrayList<CityVO>();
		
		for(int i=0; i<criteria.list().size(); i++){
			City city= (City)criteria.list().get(i);
			CityVO cityVO = new CityVO();
			cityVO.setCityId(city.getCityId());
			cityVO.setProvinceId(city.getProvince().getProvinceId());
			cityVO.setCityCode(city.getCityCode());
			cityVO.setCityName(city.getCityName());
			cityVO.setProvinceName(city.getProvince().getProvinceName());
			cityVO.setActiveFlag(city.getActiveFlag());
			
			cityVOList.add(cityVO);
		}
		
		return cityVOList;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(City.class);
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
			List<? extends SearchObject> searchCriteria){
		 	crit.createAlias("province", "province");
		 	
		 	if (searchCriteria != null){
		 		for (SearchObject searchVal : searchCriteria){
		 			if (searchVal.getSearchColumn().compareTo("searchAll") == 0){
		 				String sSplit = searchVal.getSearchValueAsString().trim();
		 				crit.add(Restrictions.or(
		 						Restrictions.ilike("cityCode",sSplit,MatchMode.ANYWHERE),
		 						Restrictions.or(Restrictions.ilike("cityName", sSplit,MatchMode.ANYWHERE), 
		 						Restrictions.ilike("province.provinceName", sSplit,MatchMode.ANYWHERE))));
		 			}
		 			
		 			if(searchVal.getSearchColumn().compareTo("cityCode")==0){
						crit.add(Restrictions.ilike("cityCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					
					if(searchVal.getSearchColumn().compareTo("cityName")==0){
						crit.add(Restrictions.ilike("cityName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					
					if(searchVal.getSearchColumn().compareTo("provinceId")==0){
						crit.add(Restrictions.eq("province.provinceId", searchVal.getSearchValue()));
					}	
		 		}
		 	}
		 	
		 	crit.add(Restrictions.eq("activeFlag", "Y"));
	}


	@Override
	public List<CityVO> citySearch() {
		
		//String sql = "SELECT CITY_ID, PROVINCE_ID, CITY_CODE, CITY_NAME, ACTIVE_FLAG FROM POS_CITY";
		
		Criteria criteria = getSession().createCriteria(City.class);		
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		List<CityVO> cityVOList = new ArrayList<CityVO>();
		
		for(int i = 0; i<criteria.list().size(); i++)
		{
			City city = (City) criteria.list().get(i);
			CityVO cityVO = new CityVO();
			cityVO.setCityId(city.getCityId());
			cityVO.setProvinceId(city.getProvince().getProvinceId());
			cityVO.setCityCode(city.getCityCode());
			cityVO.setCityName(city.getCityName());
			cityVO.setProvinceName(city.getProvince().getProvinceName());
			cityVO.setActiveFlag(city.getActiveFlag());
			cityVOList.add(cityVO);
		}
		return cityVOList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Province> showProvince()
	{
		Criteria criteria = getSession().createCriteria(Province.class);
		criteria.addOrder(Order.asc("provinceCode"));
		List<Province> provinceList = criteria.list();
		return provinceList;
	}

}
