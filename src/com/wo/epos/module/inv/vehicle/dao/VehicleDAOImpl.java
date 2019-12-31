package com.wo.epos.module.inv.vehicle.dao;

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
import com.wo.epos.module.inv.vehicle.model.Vehicle;
import com.wo.epos.module.inv.vehicle.vo.VehicleVO;


@ManagedBean(name = "vehicleDAO")
@ViewScoped
public class VehicleDAOImpl extends GenericDAOHibernate<Vehicle, Long> implements VehicleDAO, Serializable {
	
	private static final long serialVersionUID = 8770007149792286675L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<VehicleVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Vehicle.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<VehicleVO> voList = new ArrayList<VehicleVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Vehicle vehicle = (Vehicle)criteria.list().get(i);
			VehicleVO vo = new VehicleVO();
			vo.setVehicleId(vehicle.getVehicleId());
			vo.setOutlet(vehicle.getOutlet());
			vo.setPoliceNo(vehicle.getPoliceNo());
			vo.setVehicleType(vehicle.getVehicleType());
			vo.setVehicleDesc(vehicle.getVehicleDesc());
			vo.setOccupyFlag(vehicle.getOccupyFlag());
			vo.setActiveFlag(vehicle.getActiveFlag());
			vo.setParamVehicleType(vehicle.getParamVehicleType());
		    vo.setCompanyName(vehicle.getOutlet().getCompany().getCompanyName());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Vehicle.class);
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
		    crit.createAlias("outlet", "outlet");
		    
		   
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("policeNo", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("vehicleType", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.eq("outlet.outletName", sSplit),
									Restrictions.ilike("vehicleDesc", sSplit,MatchMode.ANYWHERE)))));
							
					}
					
						
					if(searchVal.getSearchColumn().compareTo("policeNo")==0){
						crit.add(Restrictions.ilike("policeNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("vehicleType")==0){
						crit.add(Restrictions.ilike("vehicleType", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("vehicleDesc")==0){
						crit.add(Restrictions.ilike("vehicleDesc", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					if(searchVal.getSearchColumn().compareTo("occupyFlag")==0){
						crit.add(Restrictions.ilike("occupyFlag", searchVal.getSearchValueAsString().trim()));
					}
					if(searchVal.getSearchColumn().compareTo("outletId")==0){
						crit.add(Restrictions.eq("outlet.outletId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("outlet.company.companyId", searchVal.getSearchValue()));
					}		
					
				}
			}
	}

	/*@Override
	public List<VehicleVO> searchVehicleList() {
		
		Criteria criteria = getSession().createCriteria(Vehicle.class);
		
		List<VehicleVO> voList = new ArrayList<VehicleVO>();
		for(int i=0;i<criteria.list().size(); i++){
			Vehicle vehicle = (Vehicle)criteria.list().get(i);
			VehicleVO vo = new VehicleVO();
			vo.setVehicleId(vehicle.getVehicleId());
			vo.setVehicleCode(vehicle.getVehicleCode());
			vo.setCity(vehicle.getCity());
			vo.setAddress(vehicle.getAddress());
			vo.setGroupId(vehicle.getGroupId());
			vo.setLogoFile(vehicle.getLogoFile());
			vo.setPicName(vehicle.getPicName());
			vo.setPicPhoneNo(vehicle.getPicPhoneNo());
			vo.setPostalCode(vehicle.getPostalCode());
			vo.setActiveFlag(vehicle.getLogoFileName());
			
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
	public Vehicle findByNo(String vehicleNo) {
		Criteria criteria = getSession().createCriteria(Vehicle.class);		
		criteria.add(Restrictions.eq("vehicleNo", vehicleNo.trim()));
		
		return (Vehicle) criteria.uniqueResult();
	}*/

}
