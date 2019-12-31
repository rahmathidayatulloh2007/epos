package com.wo.epos.module.inv.vehicle.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.vehicle.model.Vehicle;
import com.wo.epos.module.inv.vehicle.vo.VehicleVO;

public interface VehicleDAO extends GenericDAO<Vehicle, Long>, RetrieverDataPage<VehicleVO>{
	
	
}
