package com.wo.epos.module.sales.groupOutlet.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.groupOutlet.model.GroupOutlet;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;



@ManagedBean(name = "groupOutletDAO")
@ViewScoped
public class GroupOutletDAOImpl extends GenericDAOHibernate<GroupOutlet, Long> implements GroupOutletDAO, Serializable{

	private static final long serialVersionUID = -3583670681400614387L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<GroupOutletVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(GroupOutlet.class);
		decorateCriteria(criteria, searchCriteria);
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<GroupOutletVO> voList = new ArrayList<GroupOutletVO>();
		for(int i=0; i<criteria.list().size(); i++){
			GroupOutlet groupOutlet = (GroupOutlet)criteria.list().get(i);
			GroupOutletVO vo = new GroupOutletVO();
			
			  vo.setGroupOutletId(groupOutlet.getGroupOutletId());
			  vo.setGroupOutletCode(groupOutlet.getGroupOutletCode());
			  vo.setGroupOutletName(groupOutlet.getGroupOutletName());
			  vo.setActiveFlag(groupOutlet.getActiveFlag());
			  
			  voList.add(vo);
		}
		return voList;	 
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(GroupOutlet.class);
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
			List<? extends SearchObject> searchCriteria) {}

	@Override
	public List<GroupOutletVO> groupOutletSearch() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(GroupOutlet.class);		
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		List<GroupOutletVO> groupOutletVOList = new ArrayList<GroupOutletVO>();
		
		for(int i = 0; i<criteria.list().size(); i++)
		{
			GroupOutlet groupOutlet = (GroupOutlet) criteria.list().get(i);
			GroupOutletVO groupOutletVO = new GroupOutletVO();
			
			groupOutletVO.setGroupOutletId(groupOutlet.getGroupOutletId());
			groupOutletVO.setGroupOutletCode(groupOutlet.getGroupOutletCode());
			groupOutletVO.setGroupOutletName(groupOutlet.getGroupOutletName());
			
			groupOutletVO.setActiveFlag(groupOutlet.getActiveFlag());
			
			groupOutletVOList.add(groupOutletVO);
		}
		return groupOutletVOList;
	}

	
	
	
}
