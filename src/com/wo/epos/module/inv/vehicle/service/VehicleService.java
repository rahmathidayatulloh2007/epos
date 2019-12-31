package com.wo.epos.module.inv.vehicle.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.vehicle.model.Vehicle;
import com.wo.epos.module.inv.vehicle.vo.VehicleVO;

public interface VehicleService extends RetrieverDataPage<VehicleVO>{

	public void save(VehicleVO vehicleVO, String user);
	public void update(VehicleVO vehicleVO, String user);
	public void delete(Long vehicleId);
	public Vehicle findById(Long vehicleId);
	
}
