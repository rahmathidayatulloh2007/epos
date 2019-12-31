package com.wo.epos.module.uam.user.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.user.model.User;
import com.wo.epos.module.uam.user.vo.UserVO;

public interface UserService extends RetrieverDataPage<UserVO> {

	public void save(UserVO userVO, String userLogin);
	public void update(UserVO userVO, String userLogin);
	public void delete(Long userId);
	public User findById(Long userId);
	
	public UserVO getUserNamePassword(String user, String password);
		
	
}	
	