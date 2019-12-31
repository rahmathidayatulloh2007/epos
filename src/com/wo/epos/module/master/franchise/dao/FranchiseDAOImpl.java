package com.wo.epos.module.master.franchise.dao;

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

import com.wo.epos.module.master.city.dao.FranchiseDAO;
import com.wo.epos.module.master.franchise.model.Franchise;
import com.wo.epos.module.master.franchise.vo.FranchiseVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


@ManagedBean(name = "franchiseDAO")
@ViewScoped
public class FranchiseDAOImpl extends GenericDAOHibernate<Franchise, Long> implements FranchiseDAO, Serializable {
	
	private static final long serialVersionUID = 8770007149792286675L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<FranchiseVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Franchise.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<FranchiseVO> voList = new ArrayList<FranchiseVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Franchise franchise = (Franchise)criteria.list().get(i);
			FranchiseVO vo = new FranchiseVO();
			vo.setFranchiseId(franchise.getFranchiseId());
			vo.setFranchiseCode(franchise.getFranchiseCode());
			vo.setFranchiseName(franchise.getFranchiseName());
			vo.setCity(franchise.getCity());
			vo.setAddress(franchise.getAddress());
			vo.setGroupId(franchise.getGroupId());
			vo.setLogoFile(franchise.getLogoFile());
			vo.setPicName(franchise.getPicName());
			vo.setPicPhoneNo(franchise.getPicPhoneNo());
			vo.setPostalCode(franchise.getPostalCode());
			vo.setActiveFlag(franchise.getLogoFileName());
			
			
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Franchise.class);
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
		    crit.createAlias("city", "city");
		    crit.createAlias("city.province", "province");
		   
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("franchiseCode", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("franchiseName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("picName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("picPhoneNo", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("logoFileName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("address", sSplit,MatchMode.ANYWHERE),
								    Restrictions.or(Restrictions.ilike("province.provinceName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("city.cityName", sSplit,MatchMode.ANYWHERE)))))))));
					}
					
						
					if(searchVal.getSearchColumn().compareTo("franchiseCode")==0){
						crit.add(Restrictions.ilike("franchiseCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("franchiseName")==0){
						crit.add(Restrictions.ilike("franchiseName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("address")==0){
						crit.add(Restrictions.ilike("address", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("cityName")==0){
						crit.add(Restrictions.ge("city.cityName", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("cityId")==0){
						crit.add(Restrictions.ge("city.cityId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("province")==0){
						crit.add(Restrictions.le("city.province.provinceName", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("picName")==0){
						crit.add(Restrictions.ilike("picName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("picPhoneNo")==0){
						crit.add(Restrictions.ilike("picPhoneNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
							
					
				}
			}
	}

	@Override
	public List<FranchiseVO> searchFranchiseList() {
		
		Criteria criteria = getSession().createCriteria(Franchise.class);
		
		List<FranchiseVO> voList = new ArrayList<FranchiseVO>();
		for(int i=0;i<criteria.list().size(); i++){
			Franchise franchise = (Franchise)criteria.list().get(i);
			FranchiseVO vo = new FranchiseVO();
			vo.setFranchiseId(franchise.getFranchiseId());
			vo.setFranchiseCode(franchise.getFranchiseCode());
			vo.setFranchiseName(franchise.getFranchiseName());
			vo.setCity(franchise.getCity());
			vo.setAddress(franchise.getAddress());
			vo.setGroupId(franchise.getGroupId());
			vo.setLogoFile(franchise.getLogoFile());
			vo.setPicName(franchise.getPicName());
			vo.setPicPhoneNo(franchise.getPicPhoneNo());
			vo.setPostalCode(franchise.getPostalCode());
			vo.setActiveFlag(franchise.getLogoFileName());
			
			voList.add(vo);
		}		
		return  voList;		
	}
	
	public ParameterDtl findByDetailCode(String parameterDtlCode) {
		
		Criteria criteria = getSession().createCriteria(ParameterDtl.class);		
		criteria.add(Restrictions.eq("parameterDtlId",parameterDtlCode));
		
		return (ParameterDtl) criteria;
	}

	@Override
	public Franchise findByNo(String franchiseNo) {
		Criteria criteria = getSession().createCriteria(Franchise.class);		
		criteria.add(Restrictions.eq("franchiseNo", franchiseNo.trim()));
		
		return (Franchise) criteria.uniqueResult();
	}

}
