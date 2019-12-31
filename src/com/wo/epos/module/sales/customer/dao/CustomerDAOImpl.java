package com.wo.epos.module.sales.customer.dao;

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
import com.wo.epos.module.sales.customer.model.CustomerSales;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;



@ManagedBean(name = "customerDAO2")
@ViewScoped
public class CustomerDAOImpl extends GenericDAOHibernate<CustomerSales, Long> 
implements CustomerDAO, Serializable{

	private static final long serialVersionUID = -3124763343507384734L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<CustomerSalesVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(CustomerSales.class);		
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		/*List resultList = criteria.list();*/
		
		List<CustomerSalesVO> voList = new ArrayList<CustomerSalesVO>();
		
		/*if(resultList != null){*/
			for(int i=0; i<criteria.list().size(); i++){
		
			  CustomerSales customer = (CustomerSales)criteria.list().get(i);
			  CustomerSalesVO vo = new CustomerSalesVO();

			  vo.setCustomerId(customer.getCustomerId());
			  vo.setCustomerCode(customer.getCustomerCode());
			  vo.setCustomerName(customer.getCustomerName());
			  vo.setFullName(customer.getFullName());
			  vo.setPhoneNo(customer.getPhoneNo());
			  vo.setEmailAddress(customer.getEmailAddress());
			  vo.setAddress(customer.getAddress());
			  vo.setDepositBalance(customer.getDepositBalance());
			  vo.setSalesman(customer.getSalesman());
			  vo.setPortalCode(customer.getPortalCode());
			  vo.setAddressNpwp(customer.getAddressNpwp());
			  vo.setFullNameNpwp(customer.getFullNameNpwp());
			  vo.setNpwpNo(customer.getNpwpNo());
			  vo.setPhoneNo2(customer.getPhoneNo2());
			  vo.setFax(customer.getFax());
			  vo.setTermOfPayment(customer.getTermOfPayment());
			  
			  if(customer.getCompany() != null){
				  vo.setCompanyId(customer.getCompany().getCompanyId());
				  vo.setCompanyName(customer.getCompany().getCompanyName());
			  }
			  
			 if(customer.getProvince() != null){
				 vo.setProvinceId(customer.getProvince().getProvinceId());
				  vo.setProvinceName(customer.getProvince().getProvinceName());
			 }
	
			 if(customer.getDistricts() != null){
				 vo.setDistrictId(customer.getDistricts().getDistrictId());
				  vo.setDistrictName(customer.getDistricts().getDistrictName());
			 }
			  
			 if(customer.getSubDistricts() != null){
				 vo.setSubDistrictId(customer.getSubDistricts().getSubDistrictId());
				  vo.setSubDistrictName(customer.getSubDistricts().getSubDistrictName());
			 }
			 
			 if(customer.getBusinessType() != null){
				 vo.setBusinessTypeId(customer.getBusinessType().getBusinessTypeId());
				  vo.setBusinessTypeName(customer.getBusinessType().getBusinessTypeName());
			 }
			 
			 if(customer.getGroupOutlet() != null){
				 vo.setGroupOutletId(customer.getGroupOutlet().getGroupOutletId());
			      vo.setGroupOutletName(customer.getGroupOutlet().getGroupOutletName());
			 }
			 
			if (customer.getCustomerType() != null) {
				vo.setCustomerTypeCode(customer.getCustomerType().getParameterDtlCode());
				vo.setCustomerTypeName(customer.getCustomerType().getParameterDtlName());
			}
			
			  vo.setActiveFlag(customer.getActiveFlag()); 
			  voList.add(vo);
			}
	
		return  voList;	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(CustomerSales.class);
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
			
	    crit.createAlias("districts", "districts");
	    crit.createAlias("businessType", "businessType");
	    crit.createAlias("groupOutlet", "groupOutlet");
			
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("customerCode", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("customerName", sSplit,MatchMode.ANYWHERE)));
					}
					

					if(searchVal.getSearchColumn().compareTo("businessType")==0){
						crit.add(Restrictions.eq("businessType.businessTypeId", searchVal.getSearchValue()));
					}	
					if(searchVal.getSearchColumn().compareTo("groupOutlet")==0){
						crit.add(Restrictions.eq("groupOutlet.groupOutletId", searchVal.getSearchValue()));
					}
				
					if(searchVal.getSearchColumn().compareTo("districts")==0){
						crit.add(Restrictions.eq("districts.districtId", searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("customerCode")==0){
						crit.add(Restrictions.ilike("customerCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("customerName")==0){
						crit.add(Restrictions.ilike("customerName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("salesman")==0){
						crit.add(Restrictions.ilike("salesman", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					
				}
			}
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	

	@Override
	public List<CustomerSalesVO> searchCustomerList() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(CustomerSales.class);
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		criteria.addOrder(Order.asc("customerId"));
		
		List<CustomerSalesVO> voList = new ArrayList<CustomerSalesVO>();
		
			
			for(int i=0;i<criteria.list().size(); i++){
			 
			  CustomerSales customer = (CustomerSales) criteria.list().get(i);
			  CustomerSalesVO vo = new CustomerSalesVO();
			  
			  vo.setCustomerId(customer.getCustomerId());
			  vo.setCustomerCode(customer.getCustomerCode());
			  vo.setCustomerName(customer.getCustomerName());
			  vo.setFullName(customer.getFullName());
			  vo.setPhoneNo(customer.getPhoneNo());
			  vo.setEmailAddress(customer.getEmailAddress());
			  vo.setAddress(customer.getAddress());
			  vo.setDepositBalance(customer.getDepositBalance());
			  vo.setSalesman(customer.getSalesman());
			  vo.setPortalCode(customer.getPortalCode());
			  vo.setAddressNpwp(customer.getAddressNpwp());
			  vo.setFullNameNpwp(customer.getFullNameNpwp());
			  vo.setNpwpNo(customer.getNpwpNo());
			  vo.setPhoneNo2(customer.getPhoneNo2());
			  vo.setFax(customer.getFax());
			  vo.setActiveFlag(customer.getActiveFlag());
			  vo.setTermOfPayment(customer.getTermOfPayment());
			  
			  if(customer.getCompany() !=null){
				  vo.setCompanyId(customer.getCompany().getCompanyId());
				  vo.setCompanyName(customer.getCompany().getCompanyName());
			  }

			  if(customer.getProvince() != null){
				  vo.setProvinceId(customer.getProvince().getProvinceId());
				  vo.setProvinceName(customer.getProvince().getProvinceName());
			  }
			 
			  if(customer.getDistricts() != null){
			  vo.setDistrictId(customer.getDistricts().getDistrictId());
			  vo.setDistrictName(customer.getDistricts().getDistrictName());
			  }
			  
			  if(customer.getSubDistricts() != null){
			  vo.setSubDistrictId(customer.getSubDistricts().getSubDistrictId());
			  vo.setSubDistrictName(customer.getSubDistricts().getSubDistrictName());
			  }
			  
			  if(customer.getBusinessType() != null){
			  vo.setBusinessTypeId(customer.getBusinessType().getBusinessTypeId());
			  vo.setBusinessTypeName(customer.getBusinessType().getBusinessTypeName());
			  }
			  
			  if(customer.getGroupOutlet() != null){
			  vo.setGroupOutletId(customer.getGroupOutlet().getGroupOutletId());
		      vo.setGroupOutletName(customer.getGroupOutlet().getGroupOutletName());
			  }
			 
	/*		  if (customer.getRegisterOutlet() != null) {
				vo.setRegisterOutletId(customer.getRegisterOutlet().getOutletId());
				vo.setRegisterOutletName(customer.getRegisterOutlet().getOutletName());
			  }*/
			if (customer.getCustomerType() != null) {
				vo.setCustomerTypeCode(customer.getCustomerType().getParameterDtlCode());
				vo.setCustomerTypeName(customer.getCustomerType().getParameterDtlName());
			}
			 /*
			 * if(customer.getCity() !=null){
			 * vo.setCityId(customer.getCity().getCityId());
			 * vo.setCityName(customer.getCity().getCityName()); }
			 * vo.setTitle(customer.getTitle());
			 * vo.setGroupId(customer.getGroupId());
			 * vo.setRegisterDate(customer.getRegisterDate());
			 * vo.setBirthDate(customer.getBirthDate());
			 * 
			 */
			  voList.add(vo);
		}
		return  voList;			
	}

	@Override
	public List<CustomerSalesVO> searchSupplierListByCompany(Long companyId) {
		Criteria criteria = getSession().createCriteria(CustomerSales.class);
		criteria.add(Restrictions.eq("company.companyId", companyId));
		
		criteria.addOrder(Order.asc("customerId"));
		
		List<CustomerSalesVO> voList = new ArrayList<CustomerSalesVO>();
		for(int i=0;i<criteria.list().size(); i++){
			CustomerSales customer = (CustomerSales) criteria.list().get(i);
			CustomerSalesVO vo = new CustomerSalesVO();
			  
			  
			  vo.setCustomerId(customer.getCustomerId());
			  vo.setCustomerCode(customer.getCustomerCode());
			  vo.setCustomerName(customer.getCustomerName());
			  vo.setFullName(customer.getFullName());
			  vo.setPhoneNo(customer.getPhoneNo());
			  vo.setEmailAddress(customer.getEmailAddress());
			  vo.setAddress(customer.getAddress());
			  vo.setDepositBalance(customer.getDepositBalance());
			  vo.setSalesman(customer.getSalesman());
			  vo.setPortalCode(customer.getPortalCode());
			  vo.setAddressNpwp(customer.getAddressNpwp());
			  vo.setFullNameNpwp(customer.getFullNameNpwp());
			  vo.setNpwpNo(customer.getNpwpNo());
			  vo.setPhoneNo2(customer.getPhoneNo2());
			  vo.setFax(customer.getFax());
			  vo.setActiveFlag(customer.getActiveFlag());
			  vo.setTermOfPayment(customer.getTermOfPayment());
			  
			  if(customer.getCompany() != null){
				  vo.setCompanyId(customer.getCompany().getCompanyId());
				  vo.setCompanyName(customer.getCompany().getCompanyName());
			  }
			  
/*			  if(customer.getCity() != null){
				  vo.setCityId(customer.getCity().getCityId());
				  vo.setCityName(customer.getCity().getCityName());
			  }
			  
			   vo.setTitle(customer.getTitle());
			   vo.setBirthDate(customer.getBirthDate());
			   vo.setRegisterDate(customer.getRegisterDate());
			   vo.setGroupId(customer.getGroupId());
			   
			  */
			 if(customer.getProvince() != null){
				 vo.setProvinceId(customer.getProvince().getProvinceId());
				  vo.setProvinceName(customer.getProvince().getProvinceName());
			 }
	
			 if(customer.getDistricts() != null){
				 vo.setDistrictId(customer.getDistricts().getDistrictId());
				  vo.setDistrictName(customer.getDistricts().getDistrictName());
			 }
			  
			 if(customer.getSubDistricts() != null){
				 vo.setSubDistrictId(customer.getSubDistricts().getSubDistrictId());
				  vo.setSubDistrictName(customer.getSubDistricts().getSubDistrictName());
			 }
			 
			 if(customer.getBusinessType() != null){
				 vo.setBusinessTypeId(customer.getBusinessType().getBusinessTypeId());
				  vo.setBusinessTypeName(customer.getBusinessType().getBusinessTypeName());
			 }
			 
			 if(customer.getGroupOutlet() != null){
				 vo.setGroupOutletId(customer.getGroupOutlet().getGroupOutletId());
			      vo.setGroupOutletName(customer.getGroupOutlet().getGroupOutletName());
			 }
			  
				if (customer.getCustomerType() != null) {
					vo.setCustomerTypeCode(customer.getCustomerType().getParameterDtlCode());
					vo.setCustomerTypeName(customer.getCustomerType().getParameterDtlName());
				}
		       
			  voList.add(vo);
			}
		return voList;
		}
			


	@Override
	public CustomerSales findByCustomerCode(String customerCode) {
		Criteria criteria = getSession().createCriteria(CustomerSales.class);
		criteria.add(Restrictions.eq("customerCode", customerCode));
		
		return (CustomerSales) criteria.uniqueResult();
	}
	public ParameterDtl findByDetailCode(String parameterDtlCode) {
		
		Criteria criteria = getSession().createCriteria(ParameterDtl.class);		
		criteria.add(Restrictions.eq("parameterDtlId",parameterDtlCode));
		
		return (ParameterDtl) criteria;
	}

}
