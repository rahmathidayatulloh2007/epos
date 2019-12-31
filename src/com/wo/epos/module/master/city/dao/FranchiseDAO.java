package com.wo.epos.module.master.city.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.franchise.model.Franchise;
import com.wo.epos.module.master.franchise.vo.FranchiseVO;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface FranchiseDAO extends GenericDAO<Franchise, Long>, RetrieverDataPage<FranchiseVO>{
	
	public List<FranchiseVO> searchFranchiseList();
	public Franchise findByNo(String employeeNo);
}
