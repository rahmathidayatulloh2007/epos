package com.wo.epos.module.uam.role.dao;

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
import com.wo.epos.module.uam.role.model.Role;
import com.wo.epos.module.uam.role.vo.RoleVO;
import com.wo.epos.module.uam.user.model.User;
import com.wo.epos.module.uam.user.vo.UserVO;


@ManagedBean(name = "roleDAO")
@ViewScoped
public class RoleDAOImpl extends GenericDAOHibernate<Role, Long>
   implements RoleDAO, Serializable {

	private static final long serialVersionUID = -21375150696259403L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<RoleVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		Criteria criteria = getSession().createCriteria(Role.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<RoleVO> voList = new ArrayList<RoleVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Role role = (Role)criteria.list().get(i);
			  RoleVO roleVO = new RoleVO();
			  roleVO.setRoleId(role.getRoleId());
			  roleVO.setRoleCode(role.getRoleCode());
			  roleVO.setRoleName(role.getRoleName());
			  roleVO.setRoleDesc(role.getRoleDesc());
			  
			  if(role.getCompany() !=null){
				  roleVO.setCompanyId(role.getCompany().getCompanyId());
				  roleVO.setCompanyName(role.getCompany().getCompanyName());
			  }
			  if(role.getMenuResume() !=null){
				  roleVO.setMenuResume(role.getMenuResume());
			  }
			 
			  roleVO.setActiveFlag(role.getActiveFlag());
			  
			  voList.add(roleVO);
		}
		
		return voList;
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Role.class);
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
		    crit.createAlias("company", "company", CriteriaSpecification.LEFT_JOIN);
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("roleCode", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("roleName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("roleDesc", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("company.companyName", sSplit,MatchMode.ANYWHERE)))));
					}
					
					if(searchVal.getSearchColumn().compareTo("roleCode")==0){
						crit.add(Restrictions.ilike("roleCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}			
					if(searchVal.getSearchColumn().compareTo("roleName")==0){
						crit.add(Restrictions.ilike("roleName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("roleDesc")==0){
						crit.add(Restrictions.ilike("roleDesc", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}
					
					
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}


	@Override
	public List<RoleVO> searchRoleList() {
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		
		List<RoleVO> voList = new ArrayList<RoleVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Role role = (Role)criteria.list().get(i);
			  RoleVO roleVO = new RoleVO();
			  roleVO.setRoleId(role.getRoleId());
			  roleVO.setRoleCode(role.getRoleCode());
			  roleVO.setRoleName(role.getRoleName());
			  roleVO.setRoleDesc(role.getRoleDesc());
			  
			  if(role.getCompany() !=null){
				  roleVO.setCompanyId(role.getCompany().getCompanyId());
				  roleVO.setCompanyName(role.getCompany().getCompanyName());
			  }
			  if(role.getMenuResume() !=null){
				  roleVO.setMenuResume(role.getMenuResume());
			  }
			  roleVO.setActiveFlag(role.getActiveFlag());
			  
			  voList.add(roleVO);
		}
		
		return voList;
	}
	
	@Override
	public List<RoleVO> searchRoleListByCompany(Long companyId) {
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		
		if(companyId !=null){
		    criteria.add(Restrictions.eq("companyId", companyId));
		}else{
			criteria.add(Restrictions.isNull("companyId"));
		}
		
		List<RoleVO> voList = new ArrayList<RoleVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Role role = (Role)criteria.list().get(i);
			  RoleVO roleVO = new RoleVO();
			  roleVO.setRoleId(role.getRoleId());
			  roleVO.setRoleCode(role.getRoleCode());
			  roleVO.setRoleName(role.getRoleName());
			  roleVO.setRoleDesc(role.getRoleDesc());
			  
			  if(role.getCompany() !=null){
				  roleVO.setCompanyId(role.getCompany().getCompanyId());
				  roleVO.setCompanyName(role.getCompany().getCompanyName());
			  }
			  if(role.getMenuResume() !=null){
				  roleVO.setMenuResume(role.getMenuResume());
			  }
			  roleVO.setActiveFlag(role.getActiveFlag());
			  
			  voList.add(roleVO);
		}
		
		return voList;
	}

}