package com.wo.epos.module.login.service;

import java.util.List;

import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.menu.vo.MenuVO;


public interface LoginService {
	
	public EmployeeVO getEmployee(Long employeeId);
	
	public List<MenuVO> searchMenuDasboard(Long userId);
}