package com.wo.epos.module.uam.user.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.wo.epos.common.model.BaseEntity;

public class UserVO extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 2918985568831336523L;
	
	private Long userId;
	private Long companyId;
	private Long employeeId;
	private Long roleId;
	private Long outletId;
	
	private String employeeNo;
	private String companyName;
	private String employeeName;
	private String roleName;	
	private String userCode;
	private String userPassword;
	private String userPasswdConfirm;
	private String firstLoginFlag;
	private String loginFlag;
	private String outletName;
	private String picFlag;
	
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
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getUserPasswdConfirm() {
		return userPasswdConfirm;
	}

	public void setUserPasswdConfirm(String userPasswdConfirm) {
		this.userPasswdConfirm = userPasswdConfirm;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	public String getPicFlag() {
		return picFlag;
	}

	public void setPicFlag(String picFlag) {
		this.picFlag = picFlag;
	}
	
	
		
}