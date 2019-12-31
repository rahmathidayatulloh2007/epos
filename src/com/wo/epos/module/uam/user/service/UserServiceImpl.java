package com.wo.epos.module.uam.user.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.bean.EncryptMd5;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.employee.dao.EmployeeDAO;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.role.dao.RoleDAO;
import com.wo.epos.module.uam.role.model.Role;
import com.wo.epos.module.uam.user.dao.UserDAO;
import com.wo.epos.module.uam.user.model.User;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name="userService")
@ViewScoped
public class UserServiceImpl implements UserService, Serializable {
	
	private static final long serialVersionUID = -3977176704268180026L;

	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{userDAO}")
	private UserDAO userDAO;
	
	@ManagedProperty(value="#{roleDAO}")
	private RoleDAO roleDAO;	
	
	@ManagedProperty(value="#{employeeDAO}")
	private EmployeeDAO employeeDAO;
	
	
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<UserVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return userDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return userDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(UserVO userVO, String userLogin) {
		 User user = new User();			
		 
		 user.setEmployeeId(userVO.getEmployeeId());
		 Employee employee = employeeDAO.getById(userVO.getEmployeeId());
		 user.setEmployee(employee);
		 if(userVO.getCompanyId() !=null){
			 user.setCompanyId(userVO.getCompanyId());
			 Company company = companyDAO.findById(userVO.getCompanyId());
			 user.setCompany(company);
		 }
		 user.setRoleId(userVO.getRoleId());
		 Role role = roleDAO.findById(userVO.getRoleId());
		 user.setRole(role);
		 user.setUserCode(userVO.getUserCode());		
		 String password = EncryptMd5.crypt(userVO.getUserPassword());
		 user.setUserPasswd(password);		 
		 user.setActiveFlag(userVO.getActiveFlag());
		 user.setCreateBy(userLogin);
		 user.setCreateOn(new Timestamp(System.currentTimeMillis()));
		 
		 userDAO.save(user);
		 userDAO.flush();
		 
	}

	@Override
	public void update(UserVO userVO, String userLogin) {
		User userUpdate = new User();	
        
		userUpdate = userDAO.findById(userVO.getUserId());
		userUpdate.setRoleId(userVO.getRoleId());
		Role role = roleDAO.findById(userVO.getRoleId());
		userUpdate.setRole(role);
		userUpdate.setActiveFlag(userVO.getActiveFlag());
		userUpdate.setUpdateBy(userLogin);
		userUpdate.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		userUpdate.setLoginFlag(userVO.getLoginFlag());
		userUpdate.setFirstLoginFlag(userVO.getFirstLoginFlag());
		userUpdate.setPrevLogin(userVO.getPrevLogin());
			
		userDAO.update(userUpdate);
		userDAO.flush();		
	}

	@Override
	public void delete(Long userId) {
		User userDel = new User();
		userDel.setUserId(userId);
		
		userDAO.delete(userDel);
		userDAO.flush();
	}

	@Override
	public User findById(Long userId) {
		return userDAO.findById(userId);
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public UserVO getUserNamePassword(String user, String password) {
		return userDAO.getUserNamePassword(user, password);
	}
	
	
		
}
