package com.wo.epos.module.master.equipment.dao;

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
import com.wo.epos.module.master.equipment.model.Equipment;
import com.wo.epos.module.master.equipment.vo.EquipmentVO;

@ManagedBean(name = "equipmentDAO")
@ViewScoped
public class EquipmentDAOImpl extends GenericDAOHibernate<Equipment, Long> 
        implements EquipmentDAO, Serializable {

	private static final long serialVersionUID = -74704552711867118L;

	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<EquipmentVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Equipment.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<EquipmentVO> voList = new ArrayList<EquipmentVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Equipment equipment = (Equipment)criteria.list().get(i);
			  EquipmentVO vo = new EquipmentVO();
			  
			  vo.setEquipmentId(equipment.getEquipmentId());
			  vo.setEquipmentCode(equipment.getEquipmentCode());
			  vo.setEquipmentName(equipment.getEquipmentName());
			  
			  if(equipment.getOutlet() !=null){
				  vo.setOutletId(equipment.getOutletId());
				  vo.setOutletCode(equipment.getOutlet().getOutletCode());
				  vo.setOutletName(equipment.getOutlet().getOutletName());
				  vo.setCompanyName(equipment.getOutlet().getCompany().getCompanyName());
 			  }
			  
			  if(equipment.getEquipmentType() !=null){
				  vo.setEquipmentTypeCode(equipment.getEquipmentTypeCode());
				  vo.setEquipmentTypeName(equipment.getEquipmentType().getParameterDtlName());
			  }
			  
			  vo.setEquipmentStatusCode(equipment.getEquipmentStatusCode());
			  if(equipment.getEquipmentStatus() !=null){
				  vo.setEquipmentStatusName(equipment.getEquipmentStatus().getParameterDtlName());
			  }
			  vo.setActiveFlag(equipment.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Equipment.class);
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
		    crit.createAlias("outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		    crit.createAlias("equipmentType", "equipmentType");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
										
					if(searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("equipmentCode", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("equipmentName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("outlet.outletName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("equipmentType.parameterDtlName", sSplit,MatchMode.ANYWHERE)))));
						 
					}
					
					if(searchVal.getSearchColumn().compareTo("equipmentCode")==0){
						crit.add(Restrictions.ilike("equipmentCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}		
					if(searchVal.getSearchColumn().compareTo("equipmentName")==0){
						crit.add(Restrictions.ilike("equipmentName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("equipmentType")==0){
						crit.add(Restrictions.eq("equipmentType.parameterDtlCode", searchVal.getSearchValue()));
					}				
					if(searchVal.getSearchColumn().compareTo("outlet")==0){
						crit.add(Restrictions.eq("outlet.outletId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("outlet.company.companyId", searchVal.getSearchValue()));
					}
				}
			}
	}

	@Override
	public List<EquipmentVO> searchDataEquipmentByParameterCode(
			String parameterCode) {
		Criteria criteria = getSession().createCriteria(Equipment.class);

		criteria.add(Restrictions.eq("equipmentTypeCode", parameterCode));		
		criteria.addOrder(Order.asc("equipmentName"));
		
		List<EquipmentVO> voList = new ArrayList<EquipmentVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Equipment equipment = (Equipment)criteria.list().get(i);
			  EquipmentVO vo = new EquipmentVO();
			  
			  vo.setEquipmentId(equipment.getEquipmentId());
			  vo.setEquipmentCode(equipment.getEquipmentCode());
			  vo.setEquipmentName(equipment.getEquipmentName());
			  
			  if(equipment.getOutlet() !=null){
				  vo.setOutletId(equipment.getOutletId());
				  vo.setOutletCode(equipment.getOutlet().getOutletCode());
				  vo.setOutletName(equipment.getOutlet().getOutletName());
 			  }
			  
			  if(equipment.getEquipmentType() !=null){
				  vo.setEquipmentTypeCode(equipment.getEquipmentTypeCode());
				  vo.setEquipmentTypeName(equipment.getEquipmentType().getParameterDtlName());
			  }
			  
			  vo.setEquipmentStatusCode(equipment.getEquipmentStatusCode());
			  
			  vo.setActiveFlag(equipment.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}
	
	@Override
	public List<EquipmentVO> searchDataEquipmentByParameterCodeAndOutletId(
			String parameterCode, Long outletId) {
		Criteria criteria = getSession().createCriteria(Equipment.class);

		criteria.add(Restrictions.eq("equipmentTypeCode", parameterCode));		
		criteria.add(Restrictions.eq("outletId", outletId));	
		
		criteria.addOrder(Order.asc("equipmentName"));
		
		List<EquipmentVO> voList = new ArrayList<EquipmentVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Equipment equipment = (Equipment)criteria.list().get(i);
			  EquipmentVO vo = new EquipmentVO();
			  
			  vo.setEquipmentId(equipment.getEquipmentId());
			  vo.setEquipmentCode(equipment.getEquipmentCode());
			  vo.setEquipmentName(equipment.getEquipmentName());
			  
			  if(equipment.getOutlet() !=null){
				  vo.setOutletId(equipment.getOutletId());
				  vo.setOutletCode(equipment.getOutlet().getOutletCode());
				  vo.setOutletName(equipment.getOutlet().getOutletName());
 			  }
			  
			  if(equipment.getEquipmentType() !=null){
				  vo.setEquipmentTypeCode(equipment.getEquipmentTypeCode());
				  vo.setEquipmentTypeName(equipment.getEquipmentType().getParameterDtlName());
			  }
			  
			  vo.setEquipmentStatusCode(equipment.getEquipmentStatusCode());
			  
			  vo.setActiveFlag(equipment.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}
	
	
}
	