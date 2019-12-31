package com.wo.epos.module.uam.menu.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.menu.dao.MenuDAO;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.menu.vo.MenuVO;



@ManagedBean(name="menuService")
@ViewScoped
public class MenuServiceImpl implements MenuService, Serializable{
	
	private static final long serialVersionUID = -6646936612328132158L;
	
	@ManagedProperty(value="#{menuDAO}")
	private MenuDAO menuDAO;
	
	public void deleteMenu(Long menu_id) throws SQLException {
		menuDAO.deleteMenu(menu_id);
		menuDAO.flush();
	}

	public List<Menu> getListMenuByParentId(MenuVO menuVO, boolean getWithDetail, boolean orderByCode)
			throws SQLException {
		return menuDAO.getListMenuByParentId(menuVO, getWithDetail, orderByCode);
	}
	
	public Menu getMenu(MenuVO menuVo) throws SQLException {
		return menuDAO.getMenu(menuVo);
	}
	
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public void insertMenu(MenuVO menuVO, boolean isInsert, String user) throws SQLException {
		menuDAO.insertMenu(menuVO, isInsert, user);
		menuDAO.flush();
	}
	
	public void update(MenuVO vo) {
		
        
        Menu menu =  menuDAO.findById(vo.getMenuId());
		
        menu.setMenuId(vo.getMenuId());
        menu.setAction(vo.getAction());
        menu.setActiveFlag(vo.getActiveFlag());
        menu.setDescription(vo.getDescription());
        menu.setMenuLevel(vo.getMenuLevel());
        menu.setMenuName(vo.getMenuName());
        menu.setMenuCode(vo.getMenuCode());
        menu.setMenuOrder(vo.getMenuOrder());
        menu.setMenuTypeCode(vo.getMenuType());
        menu.setUpdateBy("Admin");
        menu.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
		
		
		menuDAO.update(menu);
		menuDAO.flush();
		
	}
	
	public List<MenuVO> getListParentMenu(MenuVO menuVO) throws SQLException {
		return menuDAO.getListParentMenu(menuVO);
	}
	
	@Override
	public Menu findById(Long menuId)
	{
		return menuDAO.findById(menuId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<MenuVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return menuDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return menuDAO.searchCountData(searchCriteria);
	}

	@Override
	public List<MenuVO> menuSearch() {
		// TODO Auto-generated method stub
		return menuDAO.menuSearch();
	}
	
	public List<Menu> getChildMenuByParentId(Long parentId){
		return menuDAO.getChildMenuByParentId(parentId);
	}
	
	public List<Menu> searchMenuByFilterAll(String searchAny) {
		return menuDAO.searchMenuByFilterAll(searchAny);
	}

	@Override
	public List<Menu> searchParentIsNull() {
		return menuDAO.searchParentIsNull();
	}
}
