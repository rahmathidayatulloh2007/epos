package com.wo.epos.module.sales.districts.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.districts.model.Districts;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;

@ManagedBean(name = "districtsDAO")
@ViewScoped
public class DistrictsDAOImpl extends GenericDAOHibernate<Districts, Long> implements DistrictsDAO, Serializable{

	private static final long serialVersionUID = -3583670681400614387L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<DistrictsVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Districts.class);
		decorateCriteria(criteria, searchCriteria);
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<DistrictsVO> voList = new ArrayList<DistrictsVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Districts districts = (Districts)criteria.list().get(i);
			  DistrictsVO vo = new DistrictsVO();
			
			  vo.setDistrictId(districts.getDistrictId());
			  vo.setDistrictCode(districts.getDistrictCode());
			  vo.setDistrictName(districts.getDistrictName());
			  vo.setProvinceId(districts.getProvince().getProvinceId());
			  vo.setProvinceName(districts.getProvince().getProvinceName());
			  vo.setActiveFlag(districts.getActiveFlag());
			  
			  voList.add(vo);
		}
		return voList;	 
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Districts.class);
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
			List<? extends SearchObject> searchCriteria) {}

	@Override
	public List<DistrictsVO> districtsSearch() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Districts.class);		
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		List<DistrictsVO> districtsVOList = new ArrayList<DistrictsVO>();
		
		for(int i = 0; i<criteria.list().size(); i++)
		{
			Districts districts = (Districts) criteria.list().get(i);
			DistrictsVO districtsVO = new DistrictsVO();
			
			districtsVO.setDistrictId(districts.getDistrictId());
			districtsVO.setDistrictCode(districts.getDistrictCode());
			districtsVO.setDistrictName(districts.getDistrictName());
			
			districtsVO.setProvinceId(districts.getProvince().getProvinceId());
			districtsVO.setProvinceName(districts.getProvince().getProvinceName());
			
			districtsVO.setActiveFlag(districts.getActiveFlag());
			
			districtsVOList.add(districtsVO);
		}
		return districtsVOList;
	}

	
	
	
}
