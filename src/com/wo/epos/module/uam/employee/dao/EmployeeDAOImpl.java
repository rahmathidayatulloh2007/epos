package com.wo.epos.module.uam.employee.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "employeeDAO")
@ViewScoped
public class EmployeeDAOImpl extends GenericDAOHibernate<Employee, Long> implements EmployeeDAO, Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3038868187008503257L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<EmployeeVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Employee.class);
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("employeeNo"));
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<EmployeeVO> voList = new ArrayList<EmployeeVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Employee employee = (Employee)criteria.list().get(i);
			EmployeeVO vo = new EmployeeVO();
			vo.setEmployeeId(employee.getEmployeeId());
			vo.setEmployeeNo(employee.getEmployeeNo());
			
			if(employee.getCompany() !=null){
			   vo.setCompanyId(employee.getCompany().getCompanyId());
			   vo.setCompanyName(employee.getCompany().getCompanyName());
			}
			
			if (employee.getCity() != null) {
				vo.setCityId(employee.getCity().getCityId());
				vo.setCityName(employee.getCity().getCityName());
			}
			
			vo.setFullName(employee.getFullName());
			vo.setBirthPlace(employee.getBirthPlace());
			vo.setAddress(employee.getAddress());
			vo.setPostalCode(employee.getPostalCode());
			vo.setHpNo(employee.getHpNo());
			vo.setWorkEmail(employee.getWorkEmail());
			
			if(employee.getProfileImgname() != null){
				vo.setProfileImgname(employee.getProfileImgname());
			}
			
			if (employee.getGender() != null) {
				vo.setGenderCode(employee.getGender().getParameterDtlCode());
				vo.setGenderName(employee.getGender().getParameterDtlName());
			}
			
			if (employee.getReligion() != null) {
				vo.setReligionCode(employee.getReligion().getParameterDtlCode());
				vo.setReligionName(employee.getReligion().getParameterDtlName());
			}
			
			if (employee.getMaritalStatus() != null) {
				vo.setMaritalStatusCode(employee.getMaritalStatus().getParameterDtlCode());
				vo.setMaritalStatusName(employee.getMaritalStatus().getParameterDtlName());
			}
			
			if(employee.getEmployeeStatus() != null){
				vo.setEmployeeStatusCode(employee.getEmployeeStatus().getParameterDtlCode());
				vo.setEmployeeStatusName(employee.getEmployeeStatus().getParameterDtlName());
			}
			
			vo.setBirthDate(employee.getBirthDate());
			vo.setJoinDt(employee.getJoinDt());
			vo.setActiveFlag(employee.getActiveFlag());
			vo.setProfileImg(employee.getProfileImg());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Employee.class);
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
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
		    crit.createAlias("city", "city", Criteria.LEFT_JOIN);
		    crit.createAlias("company", "company", Criteria.LEFT_JOIN);
		    crit.createAlias("gender", "gender", Criteria.LEFT_JOIN);
		    crit.createAlias("religion", "religion", Criteria.LEFT_JOIN);
		    crit.createAlias("maritalStatus", "maritalStatus", Criteria.LEFT_JOIN);
		    crit.createAlias("employeeStatus", "employeeStatus", Criteria.LEFT_JOIN);
		    crit.createAlias("city.province", "province", Criteria.LEFT_JOIN);
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("company.companyName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("employeeNo", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("fullName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("gender.parameterDtlName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("religion.parameterDtlName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("address", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("city.cityName", sSplit,MatchMode.ANYWHERE))))))));
					}
					
					if(searchVal.getSearchColumn().compareTo("company")==0){
						crit.add(Restrictions.eq("company.companyId", new Long(searchVal.getSearchValueAsString())));
					}	
					
					/*if(searchVal.getSearchColumn().compareTo("outletId")==0){
						crit.createAlias("listOutletEmp", "listOutletEmp");
						crit.add(Restrictions.isNotNull("company"));
						crit.add(Restrictions.eq("listOutletEmp.outletId", new Long(searchVal.getSearchValueAsString().trim())));
						
					}*/	
					
					if(searchVal.getSearchColumn().compareTo("employeeNo")==0){
						crit.add(Restrictions.ilike("employeeNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("fullName")==0){
						crit.add(Restrictions.ilike("fullName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("gender")==0){
						crit.add(Restrictions.ilike("gender.parameterDtlCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("startDate")==0){
						crit.add(Restrictions.ge("birthDate", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("endDate")==0){
						crit.add(Restrictions.le("birthDate", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("birthPlace")==0){
						crit.add(Restrictions.ilike("birthPlace", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("religion")==0){
						crit.add(Restrictions.ilike("religion.parameterDtlCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("address")==0){
						crit.add(Restrictions.ilike("address", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("city")==0){
						crit.add(Restrictions.eq("city.cityId", searchVal.getSearchValue()));
					}			
					
				}
			}
	}

	@Override
	public List<EmployeeVO> searchEmployeeList() {
		
		Criteria criteria = getSession().createCriteria(Employee.class);
		
		List<EmployeeVO> voList = new ArrayList<EmployeeVO>();
		for(int i=0;i<criteria.list().size(); i++){
			Employee employee = (Employee)criteria.list().get(i);
			EmployeeVO vo = new EmployeeVO();
			vo.setEmployeeId(employee.getEmployeeId());
			vo.setEmployeeNo(employee.getEmployeeNo());
			if(employee.getCompany() !=null){
			   vo.setCompanyId(employee.getCompany().getCompanyId());
			   vo.setCompanyName(employee.getCompany().getCompanyName());
			}
			if(employee.getCity() !=null){
			vo.setCityId(employee.getCity().getCityId());
			vo.setCityName(employee.getCity().getCityName());
			}
			vo.setFullName(employee.getFullName());
			vo.setBirthPlace(employee.getBirthPlace());
			vo.setAddress(employee.getAddress());
			vo.setPostalCode(employee.getPostalCode());
			vo.setHpNo(employee.getHpNo());
			vo.setWorkEmail(employee.getWorkEmail());
			if(employee.getProfileImgname() != null){
				vo.setProfileImgname(employee.getProfileImgname());
			}	
			if(employee.getGender() !=null){
			vo.setGenderCode(employee.getGender().getParameterDtlCode());
			vo.setGenderName(employee.getGender().getParameterDtlName());
			}
			
			if(employee.getReligion() !=null){
			vo.setReligionCode(employee.getReligion().getParameterDtlCode());
			vo.setReligionName(employee.getReligion().getParameterDtlName());
			}
			if(employee.getMaritalStatus() !=null){
			vo.setMaritalStatusCode(employee.getMaritalStatus().getParameterDtlCode());
			vo.setMaritalStatusName(employee.getMaritalStatus().getParameterDtlName());
			}
			
			if(employee.getEmployeeStatus() != null){
				vo.setEmployeeStatusCode(employee.getEmployeeStatus().getParameterDtlCode());
				vo.setEmployeeStatusName(employee.getEmployeeStatus().getParameterDtlName());
			}
		
			vo.setBirthDate(employee.getBirthDate());
			vo.setJoinDt(employee.getJoinDt());
			vo.setActiveFlag(employee.getActiveFlag());
			
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
	public Employee findByNo(String employeeNo) {
		Criteria criteria = getSession().createCriteria(Employee.class);		
		criteria.add(Restrictions.eq("employeeNo", employeeNo.trim()));
		
		return (Employee) criteria.uniqueResult();
	}
	
	@Override
	public List<EmployeeVO> searchEmployeeListByCompany(Long companyId) {
		
		Criteria criteria = getSession().createCriteria(Employee.class);
		criteria.createAlias("company", "company", CriteriaSpecification.LEFT_JOIN);
		
		if(companyId !=null){
		    criteria.add(Restrictions.eq("company.companyId", companyId));
		}else{
			criteria.add(Restrictions.isNull("company.companyId"));
		}
		
		List<EmployeeVO> voList = new ArrayList<EmployeeVO>();
		for(int i=0;i<criteria.list().size(); i++){
			Employee employee = (Employee)criteria.list().get(i);
			EmployeeVO vo = new EmployeeVO();
			vo.setEmployeeId(employee.getEmployeeId());
			vo.setEmployeeNo(employee.getEmployeeNo());
			if(employee.getCompany() !=null){
			   vo.setCompanyId(employee.getCompany().getCompanyId());
			   vo.setCompanyName(employee.getCompany().getCompanyName());
			}
			
			if(employee.getCity()!=null) {
				vo.setCityId(employee.getCity().getCityId());
				vo.setCityName(employee.getCity().getCityName());
			}
			
			vo.setFullName(employee.getFullName());
			vo.setBirthPlace(employee.getBirthPlace());
			vo.setAddress(employee.getAddress());
			vo.setPostalCode(employee.getPostalCode());
			vo.setHpNo(employee.getHpNo());
			vo.setWorkEmail(employee.getWorkEmail());
			if(employee.getProfileImgname() != null){
				vo.setProfileImgname(employee.getProfileImgname());
			}
			if(employee.getGender() != null){
				vo.setGenderCode(employee.getGender().getParameterDtlCode());
				vo.setGenderName(employee.getGender().getParameterDtlName());
			}
			if(employee.getReligion() != null){
				vo.setReligionCode(employee.getReligion().getParameterDtlCode());
				vo.setReligionName(employee.getReligion().getParameterDtlName());
			}
			if(employee.getMaritalStatus() != null){
				vo.setMaritalStatusCode(employee.getMaritalStatus().getParameterDtlCode());
				vo.setMaritalStatusName(employee.getMaritalStatus().getParameterDtlName());
			}	
			if(employee.getEmployeeStatus() != null){
				vo.setEmployeeStatusCode(employee.getEmployeeStatus().getParameterDtlCode());
				vo.setEmployeeStatusName(employee.getEmployeeStatus().getParameterDtlName());
			}
			vo.setBirthDate(employee.getBirthDate());
			vo.setJoinDt(employee.getJoinDt());
			vo.setActiveFlag(employee.getActiveFlag());
			
			voList.add(vo);
		}		
		return  voList;		
	}

}
