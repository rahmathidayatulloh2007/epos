package com.wo.epos.module.uam.employee.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface EmployeeService extends RetrieverDataPage<EmployeeVO>{

	public void save(EmployeeVO employeeVO, String user);
	public void update(EmployeeVO employeeVO, String user);
	public void delete(Long employeeId);
	public Employee findById(Long employeeId);
	public Employee findByNo(String employeeNo);
		
	public List<CityVO> searchCityAll();
	public List<CompanyVO> searchCompanyList();
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	
	public List<EmployeeVO> searchEmployeeList();
	public ParameterDtl findByDetailCode(String parameterDtlCode);
	
	public List<EmployeeVO> searchEmployeeListByCompany(Long companyId);
}
