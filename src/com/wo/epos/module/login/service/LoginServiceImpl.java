package com.wo.epos.module.login.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.module.uam.employee.dao.EmployeeDAO;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.menu.dao.MenuDAO;
import com.wo.epos.module.uam.menu.vo.MenuVO;


@ManagedBean(name="loginService")
@ViewScoped
public class LoginServiceImpl implements LoginService, Serializable {
	
	private static final long serialVersionUID = -6482897537331084602L;
	
	@ManagedProperty(value = "#{employeeDAO}")
	private EmployeeDAO employeeDAO;
	
	@ManagedProperty(value = "#{menuDAO}")
	private MenuDAO menuDAO;


	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	@Override
	public EmployeeVO getEmployee(Long employeeId) {
		EmployeeVO vo = new EmployeeVO();
		Employee employee = employeeDAO.findById(employeeId);
		
		vo.setEmployeeId(employee.getEmployeeId());
		vo.setEmployeeNo(employee.getEmployeeNo());
		if(employee.getCompany() !=null){
			vo.setCompanyId(employee.getCompany().getCompanyId());
			vo.setCompanyName(employee.getCompany().getCompanyName());
		}
		
		vo.setCityId(employee.getCity().getCityId());
		vo.setCityName(employee.getCity().getCityName());
		vo.setFullName(employee.getFullName());
		vo.setBirthPlace(employee.getBirthPlace());
		vo.setAddress(employee.getAddress());
		vo.setPostalCode(employee.getPostalCode());
		vo.setHpNo(employee.getHpNo());
		vo.setWorkEmail(employee.getWorkEmail());
		if(employee.getProfileImgname() != null){
			vo.setProfileImgname(employee.getProfileImgname());
		}
		
		vo.setGenderCode(employee.getGender().getParameterDtlCode());
		vo.setGenderName(employee.getGender().getParameterDtlName());
		vo.setReligionCode(employee.getReligion().getParameterDtlCode());
		vo.setReligionName(employee.getReligion().getParameterDtlName());
		vo.setMaritalStatusCode(employee.getMaritalStatus().getParameterDtlCode());
		vo.setMaritalStatusName(employee.getMaritalStatus().getParameterDtlName());
		if(employee.getEmployeeStatus().getParameterDtlCode() != null){
			vo.setEmployeeStatusCode(employee.getEmployeeStatus().getParameterDtlCode());
		}
		if(employee.getEmployeeStatus().getParameterDtlName() != null){
			vo.setEmployeeStatusName(employee.getEmployeeStatus().getParameterDtlName());
		}
		vo.setBirthDate(employee.getBirthDate());
		vo.setJoinDt(employee.getJoinDt());
		vo.setActiveFlag(employee.getActiveFlag());
		
		return vo;
	}

	@Override
	public List<MenuVO> searchMenuDasboard(Long userId) {
		return menuDAO.searchMenuDasboard(userId);
	}

	
	
}