package com.wo.epos.module.uam.outlet.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.vo.OutletVO;

@ManagedBean(name = "outletDAO")
@ViewScoped
public class OutletDAOImpl extends GenericDAOHibernate<Outlet, Long> implements OutletDAO, Serializable{

	private static final long serialVersionUID = -4029047092497545800L;

	@Override
	public List<OutletVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {		
		
		
		
		StringBuffer sqlQuery = new StringBuffer(
				"	Select o.outlet_id,c.company_name,o.outlet_name,o.address,city_name,p.province_name, E.FULL_NAME, E.HP_NO, o.outlet_code,o.city_id,o.postal_code,o.active_flag,o.company_id from pos_outlet o 		"+
						"	inner join pos_company c on O.COMPANY_ID = C.COMPANY_ID		"+
						"	inner join pos_city ct on O.CITY_ID = ct.CITY_ID		"+
						"	inner join pos_province p on CT.PROVINCE_ID = p.province_id		"+
						"	left join pos_outlet_emp oe on (O.OUTLET_ID = OE.OUTLET_ID AND OE.PIC_FLAG = 'Y') "+
						"	left join pos_employee e on E.EMPLOYEE_ID = OE.EMPLOYEE_ID  where 1=1 ");
		
		sqlQuery = getQueryWhereString(sqlQuery, searchCriteria);
		SQLQuery result = getSession().createSQLQuery(
				sqlQuery.toString());

		
		result.setResultTransformer(new ResultTransformer() {
			private static final long serialVersionUID = 8061222322254832646L;

			public Object transformTuple(Object[] result, String[] aliases) {
				OutletVO outletVO = new OutletVO();
				outletVO.setOutletId(((BigDecimal)result[0]).longValue());
				outletVO.setCompanyName((String)result[1]);
				outletVO.setOutletName((String)result[2]);
				outletVO.setAddress((String)result[3]);
				outletVO.setCityName((String)result[4]);
				outletVO.setProvinceName((String)result[5]);
				outletVO.setPicName(result[6]!=null?(String)result[6]:null);
				outletVO.setPicPhoneno(result[7]!=null?(String)result[7]:null);
				outletVO.setOutletCode((String)result[8]);
				outletVO.setCityId(((BigDecimal)result[9]).longValue());
				outletVO.setPostalCode((String)result[10]);
				outletVO.setActiveFlag((String)result[11]);
				outletVO.setCompanyId(((BigDecimal)result[12]).longValue());
				

				return outletVO;
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
				"	Select count(o.outlet_id) from pos_outlet o 		"+
						"	inner join pos_company c on O.COMPANY_ID = C.COMPANY_ID		"+
						"	inner join pos_city ct on O.CITY_ID = ct.CITY_ID		"+
						"	inner join pos_province p on CT.PROVINCE_ID = p.province_id		"+
						"	left join pos_outlet_emp oe on (O.OUTLET_ID = OE.OUTLET_ID AND OE.PIC_FLAG = 'Y') "+
						"	left join pos_employee e on E.EMPLOYEE_ID = OE.EMPLOYEE_ID  where 1=1 ");
        
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
        if(searchCriteria!=null) {
            for (SearchObject searchVal : searchCriteria) {
            	
            	if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
                    String[] sSplit = searchVal.getSearchValueAsString().trim().split("\\s+");
                    sqlQuery = sqlQuery.append("AND ");
                    for(int i=0; i<sSplit.length; i++) {
                    	sqlQuery = sqlQuery.append("(UPPER(company_name) LIKE UPPER('%"+sSplit[i]+"%') "); 
                    	sqlQuery = sqlQuery.append("OR UPPER(outlet_name) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(o.address) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(city_name) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(province_name) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(E.FULL_NAME) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	sqlQuery = sqlQuery.append("OR UPPER(E.HP_NO) LIKE UPPER('%"+sSplit[i]+"%') ");
                    	//change ambigue column
                    	sqlQuery = sqlQuery.append("OR UPPER(o.postal_code) LIKE UPPER('%"+sSplit[i]+"%')) ");
                    	
                        if(i < (sSplit.length-1)) {
                        	sqlQuery = sqlQuery.append("AND ");
                        }    

                    }
                }
            	
            	if(searchVal.getSearchColumn().compareTo("company")==0){
            		sqlQuery = sqlQuery.append(" and o.company_Id = "+ searchVal.getSearchValue());
				}	
				
				if(searchVal.getSearchColumn().compareTo("outletName")==0){
					sqlQuery = sqlQuery.append(" and UPPER(o.outlet_Name) like UPPER('%"+ searchVal.getSearchValue()+"%')");
				}
				
				if(searchVal.getSearchColumn().compareTo("address")==0){
					sqlQuery = sqlQuery.append(" and UPPER(o.address) like UPPER('%"+ searchVal.getSearchValue()+"%')");
					
				}
				
				if(searchVal.getSearchColumn().compareTo("province")==0){
					sqlQuery = sqlQuery.append(" and p.province_Id = '"+ searchVal.getSearchValue()+"'");
					
				}
				
				if(searchVal.getSearchColumn().compareTo("city")==0){
					sqlQuery = sqlQuery.append(" and ct.city_Id ='"+ searchVal.getSearchValue()+"'");
				}
				
				if(searchVal.getSearchColumn().compareTo("picName")==0){
					sqlQuery = sqlQuery.append(" and UPPER(E.FULL_NAME) like UPPER('%"+ searchVal.getSearchValue()+"%')");
					
				}
				
				if(searchVal.getSearchColumn().compareTo("picPhone")==0){
					sqlQuery = sqlQuery.append(" and UPPER(E.HP_NO) like UPPER('%"+ searchVal.getSearchValue()+"%')");
				}
            }    
        }
		}catch(Exception e){
			System.out.println(e);
		}
        
        return sqlQuery;
    }

	@Override
	public List<OutletVO> searchOutletList() {
		
		Criteria criteria = getSession().createCriteria(Outlet.class);		
		
		List<OutletVO> outletVOList = new ArrayList<OutletVO>();
		
		for(int i = 0; i<criteria.list().size(); i++)
		{
			Outlet outlet = (Outlet) criteria.list().get(i);
			OutletVO outletVO = new OutletVO();
			outletVO.setOutletId(outlet.getOutletId());
			outletVO.setOutletCode(outlet.getOutletCode());
			outletVO.setOutletName(outlet.getOutletName());		
			outletVO.setCompanyId(outlet.getCompany().getCompanyId());
			outletVO.setCompanyName(outlet.getCompany().getCompanyName());		
			outletVO.setAddress(outlet.getAddress());
			outletVO.setCityId(outlet.getCity().getCityId());
			outletVO.setCityName(outlet.getCity().getCityName());
			outletVO.setProvinceName(outlet.getCity().getProvince().getProvinceName());
			outletVO.setPostalCode(outlet.getPostalCode());
			outletVO.setPicName(outlet.getPicName());
			outletVO.setPicPhoneno(outlet.getPicPhoneno());
			outletVO.setActiveFlag(outlet.getActiveFlag());	
			outletVOList.add(outletVO);
		}
		return outletVOList;
	}	
	
	public List<Outlet> findOutletByCompany(Long companyId){
		Criteria criteria = getSession().createCriteria(Outlet.class);
		criteria.createAlias("company", "company", Criteria.INNER_JOIN);
		criteria.add(Restrictions.eq("company.companyId", companyId));
		
		return criteria.list();
		
	}

	@Override
	public Outlet findByOutletCode(String outletCode) {
		Criteria criteria = getSession().createCriteria(Outlet.class);
		criteria.add(Restrictions.eq("outletCode", outletCode));
		
		return (Outlet) criteria.uniqueResult();
	}
	
	public List<Outlet> searchDataOutletByCompany(Long companyId, Long outletId){
		Criteria criteria = getSession().createCriteria(Outlet.class);
		criteria.createAlias("company", "company");
		criteria.add(Restrictions.eq("company.companyId", companyId));
	    
		List<Outlet> outletList = new ArrayList<Outlet>();
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				    Outlet outlet = (Outlet)criteria.list().get(i);
				    if(Integer.parseInt(outlet.getOutletId()+"") != Integer.parseInt(outletId+"")){
				    	outletList.add(outlet);
				    }
			}
		}
		
		return outletList;
		
	}
	
	
	
}
