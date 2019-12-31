package com.wo.epos.module.uam.outletEmp.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;

public interface OutletEmpDAO extends GenericDAO<OutletEmp, Long>, RetrieverDataPage<OutletEmpVO>{
	
	List<OutletEmpVO> searchOutletEmpList();
	public OutletEmp findOutletEmpByOutletId(Long outletId);
	public List<OutletEmpVO> findOutletEmpByOutletIdList(Long outletId);
	public OutletEmp findOutletEmpByEmployeeId(Long employeeId);
}
