package com.wo.epos.module.uam.user.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.role.model.Role;

public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3363860797524028284L;
		
	private Long userId;
	private Long companyId;
	private Long employeeId;
	private Long roleId;
	
	private Company company;
	private Employee employee;
	private Role role;
	
	private String userCode;
	private String userPasswd;
	private String firstLoginFlag;
	private String loginFlag;
	
	private Timestamp prevLogin;
	private Timestamp prevChngPasswd;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}
	
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getUserCode() {
		return userCode;
	}
	
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	public String getUserPasswd() {
		return userPasswd;
	}
	
	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}
	
	public String getFirstLoginFlag() {
		return firstLoginFlag;
	}
	
	public void setFirstLoginFlag(String firstLoginFlag) {
		this.firstLoginFlag = firstLoginFlag;
	}
	
	public String getLoginFlag() {
		return loginFlag;
	}
	
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	
	public Timestamp getPrevLogin() {
		return prevLogin;
	}
	
	public void setPrevLogin(Timestamp prevLogin) {
		this.prevLogin = prevLogin;
	}
	
	public Timestamp getPrevChngPasswd() {
		return prevChngPasswd;
	}
	
	public void setPrevChngPasswd(Timestamp prevChngPasswd) {
		this.prevChngPasswd = prevChngPasswd;
	}
		
}