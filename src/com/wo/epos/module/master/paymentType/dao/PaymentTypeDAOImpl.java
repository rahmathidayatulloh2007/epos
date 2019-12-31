package com.wo.epos.module.master.paymentType.dao;

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
import com.wo.epos.module.master.paymentType.model.PaymentType;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;

@ManagedBean(name = "paymentTypeDAO")
@ViewScoped
public class PaymentTypeDAOImpl extends GenericDAOHibernate<PaymentType, Long> implements PaymentTypeDAO, Serializable{

	private static final long serialVersionUID = -1205230769463543584L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<PaymentTypeVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(PaymentType.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<PaymentTypeVO> voList = new ArrayList<PaymentTypeVO>();
		for(int i=0; i<criteria.list().size(); i++){
			PaymentType paymentType = (PaymentType)criteria.list().get(i);
			PaymentTypeVO vo = new PaymentTypeVO();
			vo.setPaytypeId(paymentType.getPaytypeId());
			vo.setCompanyId(paymentType.getCompany().getCompanyId());
			vo.setCompanyName(paymentType.getCompany().getCompanyName());
			vo.setPaytypeCode(paymentType.getPaytypeCode());
			vo.setPaytypeName(paymentType.getPaytypeName());
			vo.setPaymentMethodCode(paymentType.getPaymentMethod().getParameterDtlCode());
			vo.setPaymentMethodName(paymentType.getPaymentMethod().getParameterDtlName());
			
			if(paymentType.getPaytypeValue() != null){
				vo.setPaytypeValue(paymentType.getPaytypeValue());
			}

			vo.setActiveFlag(paymentType.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(PaymentType.class);
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
		    crit.createAlias("company", "company");
		    crit.createAlias("paymentMethod", "paymentMethod");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("company.companyName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("paytypeCode", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("paytypeName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("paymentMethod.parameterDtlName", sSplit,MatchMode.ANYWHERE)))));
					}
					
					if(searchVal.getSearchColumn().compareTo("company")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}	
					if(searchVal.getSearchColumn().compareTo("paytypeCode")==0){
						crit.add(Restrictions.ilike("paytypeCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("paytypeName")==0){
						crit.add(Restrictions.ilike("paytypeName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("paymentMethod")==0){
						crit.add(Restrictions.ilike("paymentMethod.parameterDtlCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("activeFlag")==0){
						crit.add(Restrictions.ilike("activeFlag", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
	
	
					
				}
			}
	}

	@Override
	public List<PaymentTypeVO> searchPaymentTypeList() {
		
		Criteria criteria = getSession().createCriteria(PaymentType.class);
		
		List<PaymentTypeVO> voList = new ArrayList<PaymentTypeVO>();
		for(int i=0; i<criteria.list().size(); i++){
			PaymentType paymentType = (PaymentType)criteria.list().get(i);
			PaymentTypeVO vo = new PaymentTypeVO();
			vo.setPaytypeId(paymentType.getPaytypeId());
			vo.setCompanyId(paymentType.getCompany().getCompanyId());
			vo.setCompanyName(paymentType.getCompany().getCompanyName());
			vo.setPaytypeCode(paymentType.getPaytypeCode());
			vo.setPaytypeName(paymentType.getPaytypeName());
			vo.setPaymentMethodCode(paymentType.getPaymentMethod().getParameterDtlCode());
			vo.setPaymentMethodName(paymentType.getPaymentMethod().getParameterDtlName());
			
			if(paymentType.getPaytypeValue() != null){
				vo.setPaytypeValue(paymentType.getPaytypeValue());
			}

			vo.setActiveFlag(paymentType.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public List<PaymentTypeVO> searchDataPaymentByCompany(Long companyId) {
        Criteria criteria = getSession().createCriteria(PaymentType.class);
        criteria.createAlias("company", "company");
        
        criteria.add(Restrictions.eq("company.companyId", companyId));
        criteria.addOrder(Order.asc("paytypeCode"));
		
		List<PaymentTypeVO> voList = new ArrayList<PaymentTypeVO>();
		for(int i=0; i<criteria.list().size(); i++){
			PaymentType paymentType = (PaymentType)criteria.list().get(i);
			PaymentTypeVO vo = new PaymentTypeVO();
			vo.setPaytypeId(paymentType.getPaytypeId());
			vo.setCompanyId(paymentType.getCompany().getCompanyId());
			vo.setCompanyName(paymentType.getCompany().getCompanyName());
			vo.setPaytypeCode(paymentType.getPaytypeCode());
			vo.setPaytypeName(paymentType.getPaytypeName());
			vo.setPaymentMethodCode(paymentType.getPaymentMethod().getParameterDtlCode());
			vo.setPaymentMethodName(paymentType.getPaymentMethod().getParameterDtlName());
			
			if(paymentType.getPaytypeValue() != null){
				vo.setPaytypeValue(paymentType.getPaytypeValue());
			}

			vo.setActiveFlag(paymentType.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}
	
	public List<PaymentTypeVO> searchDataPaymentByPaymentMethod(String paymentMethodCode) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(PaymentType.class);
        criteria.createAlias("paymentMethod", "paymentMethod");
        criteria.add(Restrictions.eq("paymentMethod.parameterDtlCode", paymentMethodCode));
        criteria.addOrder(Order.asc("paytypeCode"));
		
		List<PaymentTypeVO> voList = new ArrayList<PaymentTypeVO>();
		for(int i=0; i<criteria.list().size(); i++){
			PaymentType paymentType = (PaymentType)criteria.list().get(i);
			PaymentTypeVO vo = new PaymentTypeVO();
			vo.setPaytypeId(paymentType.getPaytypeId());
			vo.setCompanyId(paymentType.getCompany().getCompanyId());
			vo.setCompanyName(paymentType.getCompany().getCompanyName());
			vo.setPaytypeCode(paymentType.getPaytypeCode());
			vo.setPaytypeName(paymentType.getPaytypeName());
			vo.setPaymentMethodCode(paymentType.getPaymentMethod().getParameterDtlCode());
			vo.setPaymentMethodName(paymentType.getPaymentMethod().getParameterDtlName());
			
			if(paymentType.getPaytypeValue() != null){
				vo.setPaytypeValue(paymentType.getPaytypeValue());
			}

			vo.setActiveFlag(paymentType.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public List<PaymentTypeVO> searchDataPaymentType() {
		// TODO Auto-generated method stub
				Criteria criteria = getSession().createCriteria(PaymentType.class);
		        criteria.addOrder(Order.asc("paytypeCode"));
				
				List<PaymentTypeVO> voList = new ArrayList<PaymentTypeVO>();
				for(int i=0; i<criteria.list().size(); i++){
					PaymentType paymentType = (PaymentType)criteria.list().get(i);
					PaymentTypeVO vo = new PaymentTypeVO();
					vo.setPaytypeId(paymentType.getPaytypeId());
					vo.setCompanyId(paymentType.getCompany().getCompanyId());
					vo.setCompanyName(paymentType.getCompany().getCompanyName());
					vo.setPaytypeCode(paymentType.getPaytypeCode());
					vo.setPaytypeName(paymentType.getPaytypeName());
					vo.setPaymentMethodCode(paymentType.getPaymentMethod().getParameterDtlCode());
					vo.setPaymentMethodName(paymentType.getPaymentMethod().getParameterDtlName());
					
					if(paymentType.getPaytypeValue() != null){
						vo.setPaytypeValue(paymentType.getPaytypeValue());
					}

					vo.setActiveFlag(paymentType.getActiveFlag());
					
					voList.add(vo);
				}
				
				return voList;
	}

}
