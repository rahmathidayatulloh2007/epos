package com.wo.epos.module.sales.register.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.register.model.RegisterDtl;
import com.wo.epos.module.sales.register.vo.RegisterDtlVO;

@ManagedBean(name = "registerDtlDAO")
@ViewScoped
public class RegisterDtlDAOImpl extends GenericDAOHibernate<RegisterDtl, Long> 
        implements RegisterDtlDAO, Serializable {

	
	private static final long serialVersionUID = -5169317915140114817L;

	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<RegisterDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(RegisterDtl.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<RegisterDtlVO> voList = new ArrayList<RegisterDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  RegisterDtl regDtl = (RegisterDtl)criteria.list().get(i);
			  RegisterDtlVO vo = new RegisterDtlVO();
			  vo.setRegDtlId(regDtl.getRegDtlId());
			  if(regDtl.getRegister() !=null){
			      vo.setRegId(regDtl.getRegister().getRegId());
			  }
			  if(regDtl.getPaymentMethod() !=null){
				  vo.setPaymentMethodCode(regDtl.getPaymentMethodCode());
				  vo.setPaymentMethodName(regDtl.getPaymentMethod().getParameterDtlName());
			  }
			  
			  vo.setInitFund(regDtl.getInitFund());
			  vo.setTotalPayment(regDtl.getTotalPayment());
			  vo.setActiveFlag(regDtl.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(RegisterDtl.class);
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
		    crit.createAlias("paymentMethod", "paymentMethod");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
										
					if(searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 crit.add(Restrictions.ilike("paymentMethod.parameterDtlName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
						 
					}
					
				}
			}
	}
	
	@Override
	public List<RegisterDtlVO> searchRegisterDtlVoList(Long registerId) {
		Criteria criteria = getSession().createCriteria(RegisterDtl.class);
		criteria.createAlias("register", "register", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("register.regId", registerId));
		
		List<RegisterDtlVO> registerDtlVoList = new ArrayList<RegisterDtlVO>();
		if(criteria !=null){
			for(int i=0; i<criteria.list().size(); i++){
				   RegisterDtl dtl = (RegisterDtl)criteria.list().get(i);
				   RegisterDtlVO dtlVo = new RegisterDtlVO();
				   
				   if(dtl.getRegister() !=null){
					   dtlVo.setRegId(dtl.getRegister().getRegId());
				   }
				   if(dtl.getPaymentMethod() !=null){
					   dtlVo.setPaymentMethodCode(dtl.getPaymentMethodCode());
					   dtlVo.setPaymentMethodName(dtl.getPaymentMethod().getParameterDtlName());
				   }
				   
				   dtlVo.setDisableInitFund(true);				   
				   dtlVo.setInitFund(dtl.getInitFund());
				   dtlVo.setTotalPayment(dtl.getTotalPayment());
				   
				   registerDtlVoList.add(dtlVo);				   
			}
		}
		
		return registerDtlVoList;
	}
	
}
	