package com.wo.epos.module.purchasing.supplier.dao;

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
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;

@ManagedBean(name = "supplierDAO")
@ViewScoped
public class SupplierDAOImpl extends GenericDAOHibernate<Supplier, Long> 
implements SupplierDAO, Serializable {

	private static final long serialVersionUID = -7111480365355288919L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<SupplierVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Supplier.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<SupplierVO> voList = new ArrayList<SupplierVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Supplier supplier = (Supplier)criteria.list().get(i);
			  SupplierVO vo = new SupplierVO();
			  vo.setSupplierId(supplier.getSupplierId());
			  vo.setApBalance(supplier.getApBalance());
			  vo.setSupplierCode(supplier.getSupplierCode());
			  vo.setSupplierName(supplier.getSupplierName());
			  vo.setAddress(supplier.getAddress());
			  vo.setPostalCode(supplier.getPostalCode());
			  vo.setPhoneNo(supplier.getPhoneNo());
			  vo.setFaxNo(supplier.getFaxNo());
			  vo.setPicName(supplier.getPicName());
			  vo.setCompanyId(supplier.getCompany().getCompanyId());
			  vo.setCompanyName(supplier.getCompany().getCompanyName());
			  vo.setCityId(supplier.getCity().getCityId());
			  vo.setCityName(supplier.getCity().getCityName());
			  vo.setProvinceName(supplier.getCity().getProvince().getProvinceName());
			  vo.setActiveFlag(supplier.getActiveFlag());
			  if(supplier.getPicTitle() != null){
				 vo.setPicTitleCode(supplier.getPicTitle().getParameterDtlCode());
			     vo.setPicTitleName(supplier.getPicTitle().getParameterDtlName());
			  }
			  			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Supplier.class);
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
		    crit.createAlias("company", "company");
		    crit.createAlias("city.province", "province");
		    crit.createAlias("picTitle", "picTitle");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("company.companyName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("supplierCode", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("supplierName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("address", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("city.cityName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("picTitle.parameterDtlDesc", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("province.provinceName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("picName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("phoneNo", sSplit,MatchMode.ANYWHERE))))))))));
					}
					
					if(searchVal.getSearchColumn().compareTo("company")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("supplierCode")==0){
						crit.add(Restrictions.ilike("supplierCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("supplierName")==0){
						crit.add(Restrictions.ilike("supplierName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("address")==0){
						crit.add(Restrictions.ilike("address", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("city")==0){
						crit.add(Restrictions.eq("city.cityId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("province")==0){
						crit.add(Restrictions.ilike("province.provinceName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("picName")==0){
						crit.add(Restrictions.ilike("picName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("phoneNo")==0){
						crit.add(Restrictions.ilike("phoneNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
				
				}
			}
	}

	@Override
	public List<SupplierVO> searchSupplierList() {
		Criteria criteria = getSession().createCriteria(Supplier.class);
		criteria.addOrder(Order.asc("supplierId"));
		
		List<SupplierVO> voList = new ArrayList<SupplierVO>();
		for(int i=0;i<criteria.list().size(); i++){
			  Supplier supplier = (Supplier) criteria.list().get(i);
			  SupplierVO vo = new SupplierVO();
			  
			  vo.setSupplierId(supplier.getSupplierId());
			  vo.setApBalance(supplier.getApBalance());
			  vo.setSupplierCode(supplier.getSupplierCode());
			  vo.setSupplierName(supplier.getSupplierName());
			  vo.setAddress(supplier.getAddress());
			  vo.setPostalCode(supplier.getPostalCode());
			  vo.setPhoneNo(supplier.getPhoneNo());
			  vo.setFaxNo(supplier.getFaxNo());
			  vo.setPicName(supplier.getPicName());
			  if(supplier.getCompany() !=null){
				  vo.setCompanyId(supplier.getCompany().getCompanyId());
				  vo.setCompanyName(supplier.getCompany().getCompanyName());
			  }
			  if(supplier.getCity() !=null){
				  vo.setCityId(supplier.getCity().getCityId());
				  vo.setCityName(supplier.getCity().getCityName());
			  }
			  vo.setProvinceName(supplier.getCity().getProvince().getProvinceName());
			  vo.setActiveFlag(supplier.getActiveFlag());
			  if(supplier.getPicTitle() != null){
				 vo.setPicTitleCode(supplier.getPicTitle().getParameterDtlCode());
			     vo.setPicTitleName(supplier.getPicTitle().getParameterDtlName());
			  }
			  voList.add(vo);
		}
		return  voList;		
	}

	@Override
	public Supplier findBySupplierCode(String supplierCode) {
		Criteria criteria = getSession().createCriteria(Supplier.class);
		criteria.add(Restrictions.eq("supplierCode", supplierCode));
		
		return (Supplier) criteria.uniqueResult();
	}

	@Override
	public List<SupplierVO> searchSupplierListByCompany(Long companyId) {
		Criteria criteria = getSession().createCriteria(Supplier.class);
		criteria.add(Restrictions.eq("company.companyId", companyId));
		
		criteria.addOrder(Order.asc("supplierId"));
		
		List<SupplierVO> voList = new ArrayList<SupplierVO>();
		for(int i=0;i<criteria.list().size(); i++){
			  Supplier supplier = (Supplier) criteria.list().get(i);
			  SupplierVO vo = new SupplierVO();
			  
			  vo.setSupplierId(supplier.getSupplierId());
			  vo.setApBalance(supplier.getApBalance());
			  vo.setSupplierCode(supplier.getSupplierCode());
			  vo.setSupplierName(supplier.getSupplierName());
			  vo.setAddress(supplier.getAddress());
			  vo.setPostalCode(supplier.getPostalCode());
			  vo.setPhoneNo(supplier.getPhoneNo());
			  vo.setFaxNo(supplier.getFaxNo());
			  vo.setPicName(supplier.getPicName());
			  if(supplier.getCompany() !=null){
				  vo.setCompanyId(supplier.getCompany().getCompanyId());
				  vo.setCompanyName(supplier.getCompany().getCompanyName());
			  }
			  if(supplier.getCity() !=null){
				  vo.setCityId(supplier.getCity().getCityId());
				  vo.setCityName(supplier.getCity().getCityName());
			  }
			  vo.setProvinceName(supplier.getCity().getProvince().getProvinceName());
			  vo.setActiveFlag(supplier.getActiveFlag());
			  if(supplier.getPicTitle() != null){
				 vo.setPicTitleCode(supplier.getPicTitle().getParameterDtlCode());
			     vo.setPicTitleName(supplier.getPicTitle().getParameterDtlName());
			  }
			  voList.add(vo);
		}
		
		return  voList;		
	}

	
}
