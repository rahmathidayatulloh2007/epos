package com.wo.epos.module.uam.menu.service;

import java.sql.SQLException;
import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.menu.vo.MenuVO;

public interface MenuService extends RetrieverDataPage<MenuVO> {

	public void deleteMenu(Long menu_id)  throws SQLException ;

	public List<Menu> getListMenuByParentId(MenuVO menuVO, boolean getWithDetail, boolean orderByCode)
			throws SQLException ;
	
	public Menu getMenu(MenuVO menuVo) throws SQLException ;

	public void insertMenu(MenuVO menuVO, boolean isInsert, String user) throws SQLException ;
	
	public List<MenuVO> getListParentMenu(MenuVO menuVO) throws SQLException;
	
	public Menu findById(Long menuId); 
	
	public List<MenuVO> menuSearch() throws SQLException;
	
	public List<Menu> getChildMenuByParentId(Long parentId);
	
	public List<Menu> searchMenuByFilterAll(String searchAny);
	
	public List<Menu> searchParentIsNull();
}
