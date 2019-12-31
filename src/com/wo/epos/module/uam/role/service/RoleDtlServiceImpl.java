package com.wo.epos.module.uam.role.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.menu.dao.MenuDAO;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.role.dao.RoleDAO;
import com.wo.epos.module.uam.role.dao.RoleDtlDAO;
import com.wo.epos.module.uam.role.model.Role;
import com.wo.epos.module.uam.role.model.RoleDtl;
import com.wo.epos.module.uam.role.vo.RoleDtlVO;

@ManagedBean(name="roleDtlService")
@ViewScoped
public class RoleDtlServiceImpl implements RoleDtlService, Serializable {

	private static final long serialVersionUID = 2130690041937165750L;

	@ManagedProperty(value="#{roleDtlDAO}")
	private RoleDtlDAO roleDetailDAO;
	
	@ManagedProperty(value="#{roleDAO}")
	private RoleDAO roleDAO;
	
	@ManagedProperty(value="#{menuDAO}")
	private MenuDAO menuDAO;
		
	
	public RoleDtlDAO getRoleDetailDAO() {
		return roleDetailDAO;
	}

	public void setRoleDetailDAO(RoleDtlDAO roleDetailDAO) {
		this.roleDetailDAO = roleDetailDAO;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RoleDtlVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		
		return roleDetailDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return roleDetailDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(RoleDtlVO roleDetailVO, String user) {
		if(roleDetailVO.getChildRoleMenuList().size() > 0){		
			for (RoleDtlVO dtlVO : roleDetailVO.getChildRoleMenuList()) {
				  Menu menu = menuDAO.findById(dtlVO.getMenuId()); 
				  if(menu.getParent() != null && menu.getParent().getMenuId() !=null){
				     RoleDtl newRoleDet = new RoleDtl();		
					 newRoleDet.setMenuId(dtlVO.getMenuId());
					 newRoleDet.setRoleId(roleDetailVO.getRoleId());
					 newRoleDet.setActiveFlag("Y");
					 newRoleDet.setCreateBy(user);
					 newRoleDet.setCreateOn(new Timestamp(System.currentTimeMillis()));		
					 roleDetailDAO.save(newRoleDet);
				  }	
			   }
			   Role role = roleDAO.findById(roleDetailVO.getRoleId());
			   role.setMenuResume(roleDetailVO.getMenuResume());
			   roleDAO.save(role);
			   roleDetailDAO.flush();
			}
	}

	@Override
	public void update(RoleDtlVO roleDetailVO, String user) {
		Role role = null;
	    role = roleDAO.findById(roleDetailVO.getRoleId());
	    role.setMenuResume(roleDetailVO.getMenuResume());
	    
		List<RoleDtl> childListReal = role.getRoleDetailList();
		if(roleDetailVO.getChildRoleMenuList() != null) {
			RoleDtl roleDetail = null;
			RoleDtl roleDetailDatabase = null;
			RoleDtlVO roleDtlVOTemp = null;
			boolean exist = false;
			
			/* untuk insert data baru dan update data lama */
			for(int x=0; x<roleDetailVO.getChildRoleMenuList().size(); x++)
			{
				roleDtlVOTemp = (RoleDtlVO) roleDetailVO.getChildRoleMenuList().get(x);
				
				exist = false;
				for(int i=0; i<childListReal.size(); i++)
				{
					roleDetailDatabase = (RoleDtl) childListReal.get(i);
					
					if (roleDetailDatabase.getMenuId().equals(roleDtlVOTemp.getMenuId())) {
						exist = true;
						break;
					}
				}
				
				if (exist)
				{	/* update */
					roleDetailDatabase.setActiveFlag("Y");
					roleDetailDatabase.setUpdateBy(user);
					roleDetailDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				}
				else
				{	/* insert */
					Menu menu = menuDAO.findById(roleDtlVOTemp.getMenuId()); 
					if(menu.getParent() != null && menu.getParent().getMenuId() !=null){
						roleDetail = new RoleDtl();
						roleDetail.setMenuId(roleDtlVOTemp.getMenuId());
						roleDetail.setRoleId(roleDetailVO.getRoleId());
						roleDetail.setActiveFlag("Y");
						roleDetail.setCreateBy(user);
						roleDetail.setCreateOn(new Timestamp(System.currentTimeMillis()));
						
						childListReal.add(roleDetail);
					}	
				}
			}
	
			
			for(int i=0; i<childListReal.size(); i++)
			{
				roleDetailDatabase = (RoleDtl) childListReal.get(i);
	
				for(int x=0; x<roleDetailVO.getChildRoleMenuList().size(); x++)
				{
					roleDtlVOTemp = (RoleDtlVO) roleDetailVO.getChildRoleMenuList().get(x);
					
					exist = false;
					
					if(roleDetailDatabase.getMenuId() !=null) {
						if (roleDetailDatabase.getMenuId().equals(roleDtlVOTemp.getMenuId())) {
							exist = true;									
							break;
						}
					}
				}
	
				if (!exist)
				{	/* delete */
					i--;
					childListReal.remove(roleDetailDatabase);
				}
			}
			
		}		
		
		roleDAO.update(role);
		roleDAO.flush();
		
	}

	@Override
	public void delete(Long id) {
		RoleDtl roleDtl = new RoleDtl();
		roleDtl = findById(id);
		
		roleDetailDAO.delete(roleDtl); 
		roleDetailDAO.flush();
		
	}

	@Override
	public RoleDtl findById(Long id) {
		
		return roleDetailDAO.findById(id);
	}

	@Override
	public List<RoleDtl> searchRoleDetail(Long roleId) {
		return roleDetailDAO.searchRoleDetail(roleId);
	}

	@Override
	public List<RoleDtlVO> dataRoleDetailList(Long menuId){
		return roleDetailDAO.dataRoleDetailList(menuId);
	}
		
	
}