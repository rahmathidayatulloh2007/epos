package com.wo.epos.module.uam.role.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.role.model.RoleDtl;
import com.wo.epos.module.uam.role.vo.RoleDtlVO;


@ManagedBean(name = "roleDtlDAO")
@ViewScoped
public class RoleDtlDAOImpl extends GenericDAOHibernate<RoleDtl, Long>
   implements RoleDtlDAO, Serializable {

	private static final long serialVersionUID = -2109624437681396903L;


	@SuppressWarnings("rawtypes")
	@Override
	public List<RoleDtlVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		Criteria criteria = getSession().createCriteria(RoleDtl.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<RoleDtlVO> voList = new ArrayList<RoleDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  RoleDtl roleDtl = (RoleDtl)criteria.list().get(i);
			  RoleDtlVO roleDtlVO = new RoleDtlVO();
			  roleDtlVO.setRoleDtlId(roleDtl.getRoleDtlId());
						  
			  if(roleDtl.getRole() !=null){
				  roleDtlVO.setRoleId(roleDtl.getRole().getRoleId());
				  roleDtlVO.setRoleName(roleDtl.getRole().getRoleName());
			  }
			  
			  if(roleDtl.getMenu() !=null){
				  roleDtlVO.setMenuId(roleDtl.getMenu().getMenuId());
				  roleDtlVO.setMenuName(roleDtl.getRole().getRoleName());
			  }
			 
			  roleDtlVO.setActiveFlag(roleDtl.getActiveFlag());
			  
			  voList.add(roleDtlVO);
		}
		
		return voList;
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(RoleDtl.class);
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
		    crit.createAlias("role", "role");
		    crit.createAlias("menu", "menu");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("menu.menuName", sSplit, MatchMode.ANYWHERE),
									Restrictions.ilike("role.roleName", sSplit,MatchMode.ANYWHERE)));
					}		
				}
			}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDtl> searchRoleDetail(Long roleId) {
		Criteria criteria = getSession().createCriteria(RoleDtl.class);
		
		criteria.add(Restrictions.eq("roleId", roleId));
		
		return criteria.list();
	}

	
	public List<RoleDtlVO> dataRoleDetailList(Long menuId){
		Criteria criteria = getSession().createCriteria(RoleDtl.class);
		criteria.add(Restrictions.eq("menuId", menuId));
		
		List<RoleDtlVO> voList = new ArrayList<RoleDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  RoleDtl role = (RoleDtl)criteria.list().get(i);
			  RoleDtlVO roleVO = new RoleDtlVO();
			  roleVO.setRoleDtlId(role.getRoleDtlId());
			  roleVO.setRoleId(role.getRoleId());
			  roleVO.setMenuId(role.getMenuId());
			 		 
			  roleVO.setActiveFlag(role.getActiveFlag());
			  
			  voList.add(roleVO);
		}
		
		return voList;
		
	}
		
	
}