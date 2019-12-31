package com.wo.epos.module.inv.vehicle.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.vehicle.dao.VehicleDAO;
import com.wo.epos.module.inv.vehicle.model.Vehicle;
import com.wo.epos.module.inv.vehicle.vo.VehicleVO;
import com.wo.epos.module.master.city.dao.CityDAO;

import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;
import com.wo.epos.module.uam.outletEmp.dao.OutletEmpDAO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;

@ManagedBean(name="vehicleService")
@ViewScoped
public class VehicleServiceImpl implements VehicleService, Serializable {

	private static final long serialVersionUID = 4359448049743461350L;

	@ManagedProperty(value="#{vehicleDAO}")
	private VehicleDAO vehicleDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
	@ManagedProperty(value="#{outletDAO}")
	private OutletDAO outletDAO;
	
	@ManagedProperty(value="#{outletEmpDAO}")
	private OutletEmpDAO outletEmpDAO;
	
	
	public VehicleDAO getVehicleDAO() {
		return vehicleDAO;
	}

	public void setVehicleDAO(VehicleDAO vehicleDAO) {
		this.vehicleDAO = vehicleDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}
	
	public OutletEmpDAO getOutletEmpDAO() {
		return outletEmpDAO;
	}

	public void setOutletEmpDAO(OutletEmpDAO outletEmpDAO) {
		this.outletEmpDAO = outletEmpDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<VehicleVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return vehicleDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return vehicleDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(VehicleVO vehicleVO, String user) {
		
		try{
		
		
		
		Vehicle vehicle = new Vehicle();	
		
		vehicle.setVehicleId(vehicleVO.getVehicleId());
		vehicle.setOutlet(outletDAO.findById(vehicleVO.getOutletId()));
		vehicle.setPoliceNo(vehicleVO.getPoliceNo());
		vehicle.setVehicleType(vehicleVO.getVehicleType());
		vehicle.setVehicleDesc(vehicleVO.getVehicleDesc());
		vehicle.setOccupyFlag(vehicleVO.getOccupyFlag());
		vehicle.setActiveFlag(vehicleVO.getActiveFlag());
		vehicle.setCreateBy(user);
		vehicle.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		vehicleDAO.save(vehicle);

		vehicleDAO.flush();
		}catch(Exception e){
			vehicleDAO.rollback();
			System.out.println("err=="+e);
		}
		
	}

	@Override
	public void update(VehicleVO vehicleVO, String user) {
	
		try{
		
		Vehicle vehicle = vehicleDAO.findById(vehicleVO.getVehicleId());
		
		vehicle.setOutlet(outletDAO.findById(vehicleVO.getOutletId()));
		vehicle.setPoliceNo(vehicleVO.getPoliceNo());
		vehicle.setVehicleType(vehicleVO.getVehicleType());
		vehicle.setVehicleDesc(vehicleVO.getVehicleDesc());
		vehicle.setOccupyFlag(vehicleVO.getOccupyFlag());
		vehicle.setActiveFlag(vehicleVO.getActiveFlag());
		vehicle.setUpdateBy(user);
		vehicle.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		
		vehicleDAO.update(vehicle);
		vehicleDAO.flush();
		}catch(Exception e){
			vehicleDAO.rollback();
			System.out.println(e);
		}
	}

	@Override
	public void delete(Long vehicleId) {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleId(vehicleId);
		vehicleDAO.delete(vehicle);
		vehicleDAO.flush();
	}

	@Override
	public Vehicle findById(Long vehicleId) {
		
		return vehicleDAO.findById(vehicleId);
	}

	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
	}

	
	

	
	
	
	

	

}
