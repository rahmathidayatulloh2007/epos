package com.wo.epos.module.uam.employee.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface EmployeeDAO extends GenericDAO<Employee, Long>, RetrieverDataPage<EmployeeVO>{
	
	public List<EmployeeVO> searchEmployeeList();
	public ParameterDtl findByDetailCode(String parameterDtlCode);
	public Employee findByNo(String employeeNo);
	public List<EmployeeVO> searchEmployeeListByCompany(Long companyId);
	
}
