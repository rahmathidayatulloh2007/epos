package com.wo.epos.module.uam.user.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;
import com.wo.epos.module.uam.user.model.User;
import com.wo.epos.module.uam.user.vo.UserVO;


public interface UserDAO extends GenericDAO<User, Long>, RetrieverDataPage<UserVO> {
	
	public UserVO getUserNamePassword(String user, String password);
	public UserVO dataUserEmp(Long employeeId);
}