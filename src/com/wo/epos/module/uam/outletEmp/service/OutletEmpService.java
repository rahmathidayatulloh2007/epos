package com.wo.epos.module.uam.outletEmp.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;

public interface OutletEmpService extends RetrieverDataPage<OutletEmpVO>{

	public void save(OutletEmpVO outletEmpVO, String user);
	public void update(OutletEmpVO outletEmpVO, String user);
	public void delete(Long outletEmpId);
	public OutletEmp findById(Long outletEmpId);
	
	public List<OutletEmpVO> searchOutletEmpList();
	public OutletEmp findOutletEmpByOutletId(Long outletId);
	public List<OutletEmpVO> findOutletEmpByOutletIdList(Long outletId);
	public OutletEmp findOutletEmpByEmployeeId(Long employeeId);
	
}
