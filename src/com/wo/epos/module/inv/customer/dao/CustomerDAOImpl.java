package com.wo.epos.module.inv.customer.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.customer.model.Customer;
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;


@ManagedBean(name = "customerDAO")
@ViewScoped
public class CustomerDAOImpl extends GenericDAOHibernate<Customer, Long> implements CustomerDAO, Serializable{

	private static final long serialVersionUID = -4029047092497545800L;

	@Override
	public List<CustomerVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {		
		
		
		
		StringBuffer sqlQuery = new StringBuffer(
				"	select cus.customer_id, com.COMPANY_NAME, cus.title ||' '|| cus.FULL_NAME,cus.PHONE_NO,cus.EMAIL_ADDRESS,cus.BIRTH_DATE,cus.ADDRESS, c.CITY_NAME 		"+
						"	from POS_CUSTOMER cus		"+
						"	left join POS_COMPANY com on CUS.COMPANY_ID = COM.COMPANY_ID		"+
						"	left join POS_CITY c on C.CITY_ID = CUS.CITY_ID	where 1=1	");
		
		sqlQuery = getQueryWhereString(sqlQuery, searchCriteria);
		SQLQuery result = getSession().createSQLQuery(
				sqlQuery.toString());

		
		result.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 8061222322254832646L;

			public Object transformTuple(Object[] result, String[] aliases) {
				CustomerVO customerVO = new CustomerVO();
				customerVO.setCustomerId(((BigDecimal)result[0]).longValue());
				customerVO.setCompanyName((String)result[1]);
				customerVO.setFullName((String)result[2]);
				customerVO.setPhoneNo((String)result[3]);
				customerVO.setEmailAddress((String)result[4]);
				customerVO.setBirthDate((Date)result[5]);
				customerVO.setAddress(result[6]!=null?(String)result[6]:null);
				customerVO.setCityName(result[7]!=null?(String)result[7]:null);
				
				return customerVO;
			}

			public List transformList(List list) {
				return list;
			}
		});

		result.setFirstResult(first);
		result.setMaxResults(pageSize);
		
		return result.list();
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		
		StringBuffer sqlQuery = new StringBuffer(
				"	select count(cus.customer_id)		"+
						"	from POS_CUSTOMER cus		"+
						"	left join POS_COMPANY com on CUS.COMPANY_ID = COM.COMPANY_ID		"+
						"	left join POS_CITY c on C.CITY_ID = CUS.CITY_ID	where 1=1	");
        
	    // Set where query string
 		sqlQuery = getQueryWhereString(sqlQuery, searchCriteria);
	    
 		Query result = getSession().createSQLQuery(sqlQuery.toString());
        
 		Number results = (Number) result.uniqueResult();
        if (results == null) {
            results = 0;
        }
        
        return results.longValue();
	}
	
	
	
	private StringBuffer getQueryWhereString(StringBuffer sqlQuery, List<? extends SearchObject> searchCriteria)  {
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(searchCriteria!=null) {
            for (SearchObject searchVal : searchCriteria) {
            	
            	if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
                    String[] sSplit = searchVal.getSearchValueAsString().trim().split("\\s+");
                    sqlQuery = sqlQuery.append("AND ");
                    for(int i=0; i<sSplit.length; i++) {
                    	sqlQuery = sqlQuery.append("(UPPER(com.company_name) LIKE UPPER('%"+sSplit[i]+"%') "); 
                    	sqlQuery = sqlQuery.append("OR UPPER(cus.full_name) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(cus.PHONE_NO) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(c.city_name) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(cus.EMAIL_ADDRESS) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(cus.ADDRESS) LIKE UPPER('%"+sSplit[i]+"%')) ");
                    	
                        if(i < (sSplit.length-1)) {
                        	sqlQuery = sqlQuery.append("AND ");
                        }    

                    }
                }
            	
            	if(searchVal.getSearchColumn().compareTo("company")==0){
            		sqlQuery = sqlQuery.append(" and cus.company_Id = "+ searchVal.getSearchValue());
				}	
				
				if(searchVal.getSearchColumn().compareTo("customerName")==0){
					sqlQuery = sqlQuery.append(" and UPPER(cus.full_Name) like UPPER('%"+ searchVal.getSearchValue()+"%')");
				}
				
				if(searchVal.getSearchColumn().compareTo("phoneNo")==0){
					sqlQuery = sqlQuery.append(" and UPPER(cus.phone_no) like UPPER('%"+ searchVal.getSearchValue()+"%')");
				}
				
				if(searchVal.getSearchColumn().compareTo("emailAdd")==0){
					sqlQuery = sqlQuery.append(" and UPPER(cus.email_address) like UPPER('%"+ searchVal.getSearchValue()+"%')");
				}
				
				if(searchVal.getSearchColumn().compareTo("startBirthDate")==0){
					sqlQuery = sqlQuery.append(" and TRUNC(cus.birth_date) >= TO_DATE('"+ sdf.format(searchVal.getSearchValue()) +"','yyyy-MM-dd')");
				}
				
				if(searchVal.getSearchColumn().compareTo("endBirthDate")==0){
					sqlQuery = sqlQuery.append(" and TRUNC(cus.birth_date) <= TO_DATE('"+ sdf.format(searchVal.getSearchValue()) +"','yyyy-MM-dd')");
				}
				
				if(searchVal.getSearchColumn().compareTo("address")==0){
					sqlQuery = sqlQuery.append(" and UPPER(cus.address) like UPPER('%"+ searchVal.getSearchValue()+"%')");
					
				}
				
				if(searchVal.getSearchColumn().compareTo("city")==0){
					sqlQuery = sqlQuery.append(" and UPPER(cus.city_Id) like UPPER('%"+ searchVal.getSearchValue()+"%')");
				}
				
				
            }    
        }
		}catch(Exception e){
			System.out.println(e);
		}
        
        return sqlQuery;
    }

	@Override
	public List<CustomerVO> searchCustomerList() {
		
		Criteria criteria = getSession().createCriteria(Customer.class);		
		
		List<CustomerVO> customerVOList = new ArrayList<CustomerVO>();
		
		for(int i = 0; i<criteria.list().size(); i++)
		{
			Customer customer = (Customer) criteria.list().get(i);
			CustomerVO customerVO = new CustomerVO();
			customerVO.setCustomerId(customer.getCustomerId());
			customerVO.setCustomerCode(customer.getCustomerCode());
			customerVO.setFullName(customer.getFullName());			
			customerVO.setCompanyName(customer.getCompany().getCompanyName());		
			customerVO.setAddress(customer.getAddress());
			customerVO.setCity(customer.getCity());
			customerVO.setCityName(customer.getCity().getCityName());
			customerVO.setEmailAddress(customer.getEmailAddress());
			customerVO.setBirthDate(customer.getBirthDate());
			customerVOList.add(customerVO);
		}
		return customerVOList;
	}

	public Customer getLastCustomerCode() {
		
		Criteria criteria = getSession().createCriteria(Customer.class);
		criteria.addOrder(Order.desc("customerCode"));
		
		//criteria.setFirstResult(1);
		criteria.setMaxResults(1);
		
		return (Customer)criteria.uniqueResult();
	}
	
	
	
	
	
}
