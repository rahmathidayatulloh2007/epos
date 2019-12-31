package com.wo.epos.module.sales.register.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.register.model.Register;
import com.wo.epos.module.sales.register.vo.RegisterVO;

@ManagedBean(name = "registerDAO")
@ViewScoped
public class RegisterDAOImpl extends GenericDAOHibernate<Register, Long> 
        implements RegisterDAO, Serializable {

	
	private static final long serialVersionUID = -5169317915140114817L;

	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	public List<RegisterVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		/*Criteria criteria = getSession().createCriteria(Register.class);*/
		//decorateCriteria(criteria, searchCriteria);
		StringBuffer sqlQuery = new StringBuffer(
				          " SELECT DISTINCT REG.REG_ID, OUTLET.OUTLET_ID, OUTLET.OUTLET_CODE, OUTLET.OUTLET_NAME, COM.COMPANY_ID, COM.COMPANY_CODE, " +
				          "        COM.COMPANY_NAME, EMP.EMPLOYEE_ID, EMP.EMPLOYEE_NO, EMP.FULL_NAME, PARAMDTL.PARAMETER_DTL_CODE, " +
				          "        PARAMDTL.PARAMETER_DTL_NAME, REG.START_TIME, REG.END_TIME, REG.TOTAL_PAYMENT, REG.ACTIVE_FLAG, REGDTL.TOTAL_PAYMENT CASH, " +
				          "        REGDTL2.TOTAL_PAYMENT DEBIT, REGDTL3.TOTAL_PAYMENT CREDIT " +
				          "   FROM POS_REGISTER REG INNER JOIN POS_PARAMETER_DTL PARAMDTL ON REG.STATUS = PARAMDTL.PARAMETER_DTL_CODE " +
					      "        LEFT JOIN POS_OUTLET OUTLET ON REG.OUTLET_ID = OUTLET.OUTLET_ID " +
					      "        LEFT JOIN POS_COMPANY COM ON COM.COMPANY_ID = OUTLET.COMPANY_ID " +
					      "        LEFT JOIN POS_OUTLET_EMP OUTEMP ON (OUTEMP.OUTLET_ID = OUTLET.OUTLET_ID AND REG.CASHIER_ID = OUTEMP.EMPLOYEE_ID)" +
					      "        LEFT JOIN POS_EMPLOYEE EMP ON EMP.EMPLOYEE_ID = OUTEMP.EMPLOYEE_ID " +
					      "        INNER JOIN POS_REGISTER_DTL REGDTL ON (REGDTL.REG_ID = REG.REG_ID AND REGDTL.PAYMENT_METHOD = 'PAYMENT_CASH') " +
					      "        INNER JOIN POS_REGISTER_DTL REGDTL2 ON (REGDTL2.REG_ID = REG.REG_ID AND REGDTL2.PAYMENT_METHOD = 'PAYMENT_DEBIT') " +
					      "        INNER JOIN POS_REGISTER_DTL REGDTL3 ON (REGDTL3.REG_ID = REG.REG_ID AND REGDTL3.PAYMENT_METHOD = 'PAYMENT_CREDIT') ");
		
		sqlQuery = decorateCriteria(sqlQuery, searchCriteria);
		
		SQLQuery result = getSession().createSQLQuery(sqlQuery.toString());
		
		result.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 8061222322254832646L;

			public Object transformTuple(Object[] result, String[] aliases) {
				RegisterVO vo = null;
				try {
					  vo = new RegisterVO();
					  vo.setRegId(new Long(result[0]+""));
					  if(result[1] !=null){
						  vo.setOutletId(new Long(result[1]+""));
						  vo.setOutletCode(result[2]+"");
						  vo.setOutletName(result[3]+"");
						  
						  if(result[4] !=null){
							  vo.setCompanyId(new Long(result[4]+""));
							  vo.setCompanyCode(result[5]+"");
							  vo.setCompanyName(result[6]+"");
						  }
						  
					  }
					  if(result[7] !=null){
					      vo.setCashierId(new Long(result[7]+""));
					      vo.setCashierCode(result[8]+"");
					      vo.setCashierName(result[9]+"");
					  }
					  
					  vo.setStatusCode(result[10]+"");
					  vo.setStatusName(result[11]+"");
					  if(result[12] !=null){
						  vo.setStartTime((Date)result[12]);
						  SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
						  vo.setTime(sdfTime.format(vo.getStartTime()));
					  }
					  if(result[13] !=null){
						  vo.setEndTime((Date)result[13]);
					  }
					 
					  if(result[14] !=null){
					      vo.setTotalPayment(new Double(result[14]+""));
					  }
					  
					  vo.setActiveFlag(result[15]+"");
					  
					  if(result[16] !=null){
						  vo.setTotalPaymentCash(new Double(result[16]+""));
					  }
					  if(result[17] !=null){
						  vo.setTotalPaymentDebit(new Double(result[17]+""));
					  }
					  if(result[18] !=null){
						  vo.setTotalPaymentCredit(new Double(result[18]+""));
					  }
					  
					 
					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return vo;
			}

			public List transformList(List list) {
				return list;
			}
		});

		result.setFirstResult(first);
		result.setMaxResults(pageSize);

		return result.list();
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		/*Criteria criteria = getSession().createCriteria(Register.class);
		decorateCriteria(criteria, searchCriteria);*/
		StringBuffer sqlQuery = new StringBuffer(
				          " SELECT DISTINCT COUNT(1) " +
				          "   FROM POS_REGISTER REG INNER JOIN POS_PARAMETER_DTL PARAMDTL ON REG.STATUS = PARAMDTL.PARAMETER_DTL_CODE " +
					      "        LEFT JOIN POS_OUTLET OUTLET ON REG.OUTLET_ID = OUTLET.OUTLET_ID " +
					      "        LEFT JOIN POS_COMPANY COM ON COM.COMPANY_ID = OUTLET.COMPANY_ID " +
					      "        LEFT JOIN POS_OUTLET_EMP OUTEMP ON (OUTEMP.OUTLET_ID = OUTLET.OUTLET_ID AND REG.CASHIER_ID = OUTEMP.EMPLOYEE_ID) " +
					      "        LEFT JOIN POS_EMPLOYEE EMP ON EMP.EMPLOYEE_ID = OUTEMP.EMPLOYEE_ID " +
					      "        INNER JOIN POS_REGISTER_DTL REGDTL ON (REGDTL.REG_ID = REG.REG_ID AND REGDTL.PAYMENT_METHOD = 'PAYMENT_CASH') " +
					      "        INNER JOIN POS_REGISTER_DTL REGDTL2 ON (REGDTL2.REG_ID = REG.REG_ID AND REGDTL2.PAYMENT_METHOD = 'PAYMENT_DEBIT') " +
					      "        INNER JOIN POS_REGISTER_DTL REGDTL3 ON (REGDTL3.REG_ID = REG.REG_ID AND REGDTL3.PAYMENT_METHOD = 'PAYMENT_CREDIT') ");
	
		sqlQuery = decorateCriteria(sqlQuery, searchCriteria);

		Query result = getSession().createSQLQuery(sqlQuery.toString());

		Number results = (Number) result.uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}
	
	@SuppressWarnings({ "rawtypes"})
	private StringBuffer decorateCriteria(StringBuffer sqlQuery,
			List<? extends SearchObject> searchCriteria) {
		sqlQuery = sqlQuery
				.append(" WHERE 0=0 ");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if(searchVal.getSearchColumn().compareTo("outletLogin") == 0){
						sqlQuery = sqlQuery.append("AND OUTLET.OUTLET_ID = '"
								+ searchVal.getSearchValueAsString() + "' ");
					}
					
					if(searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();							
						 sqlQuery = sqlQuery.append(" AND (UPPER(OUTLET.OUTLET_NAME) LIKE UPPER('%"+sSplit.trim()+"%') " +
									                " OR UPPER(EMP.FULL_NAME) LIKE UPPER('%"+sSplit.trim()+"%') " +
									                " OR UPPER(PARAMDTL.PARAMETER_DTL_NAME) LIKE UPPER('%"+sSplit.trim()+"%'))");
					}
					
					if(searchVal.getSearchColumn().compareTo("status")==0){
						sqlQuery = sqlQuery.append(" AND PARAMDTL.PARAMETER_DTL_CODE = '"+searchVal.getSearchValueAsString().trim()+"' ");
					}					
					if(searchVal.getSearchColumn().compareTo("cashier")==0){
						sqlQuery = sqlQuery.append(" AND EMP.EMPLOYEE_NO = '"+searchVal.getSearchValueAsString().trim()+"' ");
					}					
					if(searchVal.getSearchColumn().compareTo("outlet")==0){
						sqlQuery = sqlQuery.append(" AND OUTLET.OUTLET_ID = '"+searchVal.getSearchValue()+"' ");
					}		
					/*if(searchVal.getSearchColumn().compareTo("startDate")==0){
						crit.add(Restrictions.ge("startTime", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("endDate")==0){
						crit.add(Restrictions.le("startTime", searchVal.getSearchValue()));
					}*/
					
				}
			}
			
			return sqlQuery;
	}

	@Override
	public List<RegisterVO> findRegisterByOutletId(Long outletId) {
		Criteria criteria = getSession().createCriteria(Register.class);
		criteria.add(Restrictions.eq("outletId", outletId));
		
		List<RegisterVO> registerVoList = new ArrayList<RegisterVO>();
		if(criteria !=null){
			for(int i=0; i<criteria.list().size(); i++){
				Register reg = (Register)criteria.list().get(i);
				RegisterVO vo = new RegisterVO();
				vo.setRegId(reg.getRegId());
				vo.setOutletId(reg.getOutletId());
				vo.setOutletName(reg.getOutlet().getOutletName());
				vo.setCashierId(reg.getCashierId());
				vo.setCashierCode(reg.getCashier().getEmployeeNo());
				vo.setCashierName(reg.getCashier().getFullName());
				
				registerVoList.add(vo);
			}
		}
		
		return registerVoList;
	}

	
}
	