package com.wo.epos.module.uam.company.dao;

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

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "companyDAO")
@ViewScoped
public class CompanyDAOImpl extends GenericDAOHibernate<Company, Long> 
        implements CompanyDAO, Serializable {

	private static final long serialVersionUID = 7388440451803717817L;
	
	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<CompanyVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Company.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<CompanyVO> voList = new ArrayList<CompanyVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Company com = (Company)criteria.list().get(i);
			  CompanyVO vo = new CompanyVO();
			  vo.setCompanyId(com.getCompanyId());
			  vo.setCompanyCode(com.getCompanyCode());
			  vo.setCompanyName(com.getCompanyName());
			  vo.setCompanyTypeCode(com.getCompanyTypeCode());
			  
			  if(com.getCompanyType() !=null){
			     vo.setCompanyTypeName(com.getCompanyType().getParameterDtlName());
			  }
			  
			  vo.setPaymentFlag(com.getPaymentFlag());
			  vo.setAddress(com.getAddress());
			  vo.setCityId(com.getCityId());
			  
			  if(com.getCity() !=null){
			     vo.setCityName(com.getCity().getCityName());
			     vo.setProvinceName(com.getCity().getProvince().getProvinceName());
			  }
			  
			  if(com.getCompanyTypeCode().equals(CommonConstants.COMPANY_FRANCHISE)){
				  if(com.getFranchiseId() !=null){
				     vo.setFranchiseId(com.getFranchiseId());
				  }
			  }
			  
			  vo.setPostalCode(com.getPostalCode());
			  vo.setPicName(com.getPicName());
			  vo.setPicPhoneno(com.getPicPhoneno());
			  vo.setRegisterOn(com.getRegisterOn());
			  vo.setOutletQty(com.getOutletQty());
			  vo.setActiveFlag(com.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Company.class);
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
		    crit.createAlias("companyType", "companyType");
		    crit.createAlias("city.province", "province");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("companyCode", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("companyName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("address", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("city.cityName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("companyType.parameterDtlName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("province.provinceName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("picName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("picPhoneno", sSplit,MatchMode.ANYWHERE)))))))));
									//Restrictions.ilike("outletQty", sSplit[i],MatchMode.ANYWHERE))))))))));
					}
					
					if(searchVal.getSearchColumn().compareTo("companyName")==0){
						crit.add(Restrictions.ilike("companyName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}					
					if(searchVal.getSearchColumn().compareTo("companyType")==0){
						crit.add(Restrictions.eq("companyType.parameterDtlCode", searchVal.getSearchValue()));
					}					
					if(searchVal.getSearchColumn().compareTo("address")==0){
						crit.add(Restrictions.ilike("address", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}					
					if(searchVal.getSearchColumn().compareTo("province")==0){
						crit.add(Restrictions.eq("province.provinceId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("city")==0){
						crit.add(Restrictions.eq("city.cityId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("picName")==0){
						crit.add(Restrictions.ilike("picName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("picPhone")==0){
						crit.add(Restrictions.ilike("picPhoneno", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("startDate")==0){
						crit.add(Restrictions.ge("registerOn", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("endDate")==0){
						crit.add(Restrictions.le("registerOn", searchVal.getSearchValue()));
					}
					
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}
	
	
	@Override
	public List<CompanyVO> searchCompanyList(){
		Criteria criteria = getSession().createCriteria(Company.class);
		
		List<CompanyVO> voList = new ArrayList<CompanyVO>();
		for(int i=0;i<criteria.list().size(); i++){
			  Company com = (Company) criteria.list().get(i);
			  CompanyVO vo = new CompanyVO();
			  vo.setCompanyId(com.getCompanyId());
			  vo.setCompanyCode(com.getCompanyCode());
			  vo.setCompanyName(com.getCompanyName());
			  vo.setCompanyTypeCode(com.getCompanyTypeCode());
			  vo.setCompanyTypeName(com.getCompanyType().getParameterDtlDesc());
			  vo.setPaymentFlag(com.getPaymentFlag());
			  vo.setAddress(com.getAddress());
			  vo.setCityId(com.getCityId());
			  vo.setCityName(com.getCity().getCityName());
			  vo.setProvinceName(com.getCity().getProvince().getProvinceName());
			  vo.setPostalCode(com.getPostalCode());
			  vo.setPicName(com.getPicName());
			  vo.setPicPhoneno(com.getPicPhoneno());
			  vo.setRegisterOn(com.getRegisterOn());
			  vo.setOutletQty(com.getOutletQty());
			  vo.setActiveFlag(com.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return  voList;
		
	}

	@Override
	public Company findByCode(String companyCode) {
		Criteria criteria = getSession().createCriteria(Company.class);
		
		criteria.add(Restrictions.eq("companyCode", companyCode));
		criteria.addOrder(Order.asc("companyCode"));
		return (Company) criteria.uniqueResult();
	}
	
	
}
	