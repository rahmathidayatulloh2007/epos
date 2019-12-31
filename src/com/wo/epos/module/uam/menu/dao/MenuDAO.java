package com.wo.epos.module.uam.menu.dao;

import java.sql.SQLException;
import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.menu.vo.MenuVO;

public interface MenuDAO extends GenericDAO<Menu, Long> , RetrieverDataPage<MenuVO>{

	void deleteMenu(Long menuId);

	List<Menu> getListMenuByParentId(MenuVO menuVO, boolean getWithDetail,
			boolean orderByCode);

	Menu getMenu(MenuVO menuVo);

	void insertMenu(MenuVO menuVO, boolean isInsert, String user)
			throws SQLException;

	List<MenuVO> getListParentMenu(MenuVO menuVO);
	
	List<MenuVO> menuSearch();
	
	public List<Menu> getChildMenuByParentId(Long parentId);
	
	public List<Menu> searchMenuByFilterAll(String searchAny);
	
	public List<Menu> searchParentIsNull();
	
	public List<MenuVO> searchMenuDasboard(Long userId);
	
	public Integer maxMenuOrder(Long parentId);

}
