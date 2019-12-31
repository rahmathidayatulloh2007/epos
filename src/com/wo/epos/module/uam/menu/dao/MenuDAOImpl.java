package com.wo.epos.module.uam.menu.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.menu.vo.MenuVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
//import com.wo.epos.module.mst.parameter_dtl.model.ParamDetail;


@ManagedBean(name = "menuDAO")
@ViewScoped
public class MenuDAOImpl extends GenericDAOHibernate<Menu, Long> implements
		MenuDAO, Serializable {
	
	
	private static final long serialVersionUID = 2491636237436304290L;
	
	static Logger logger = Logger.getLogger(MenuDAOImpl.class);

	@Override
	public void deleteMenu(Long menuId) {
		Menu deleteMenu = findById(menuId);
		delete(deleteMenu);
		//flush();

	}

	@Override
	public List<Menu> getListMenuByParentId(MenuVO menuVO,
			boolean getWithDetail, boolean orderByCode) {

		if (menuVO.getParent()== null || menuVO.getParent().getMenuId() == null
				|| menuVO.getParent().getMenuId().longValue() == 0) {
			return searchParentIsNull();
		} else {
			return searchByParentId(menuVO.getParent().getMenuId());
		}

	}

	@SuppressWarnings("unchecked")
	private List<Menu> searchByParentId(Long parentId) {
		// getSession().enableFilter("myFilter");
		Criteria criteria = getSession().createCriteria(Menu.class);

		criteria.createAlias("parent", "parent");
		criteria.add(Restrictions.eq("parent.menuId", parentId));
		logger.debug("parent id : " + parentId);
		criteria.addOrder(Order.asc("menuLevel"));
		criteria.addOrder(Order.asc("menuOrder"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Menu> searchParentIsNull() {
		Criteria criteria = getSession().createCriteria(Menu.class);

		criteria.add(Restrictions.isNull("parent"));
		criteria.addOrder(Order.asc("menuLevel"));
		criteria.addOrder(Order.asc("menuOrder"));
		
		return criteria.list();
	}


	@Override
	public Menu getMenu(MenuVO menuVo) {
		Menu res = getById(menuVo.getMenuId());
		if (res == null) {
			res = new Menu(); // required so that it's behaviour same as before
								// changed to hibernate.
		}
		return res;
	}

	@Override
	public void insertMenu(MenuVO menuVO, boolean isInsert, String user)
			throws SQLException {
		Menu parentMenu = null;
		/*if (menuVO.getParent().getMenuId() != null && menuVO.getParent().getMenuId() > 0) {
			parentMenu = findById(menuVO.getParent().getMenuId());
		}*/
		ParameterDtl typeAsParam = null;
		/*if (StringUtils.isNotBlank(menuVO.getType())) {
			typeAsParam = paramDetailDAO.getParameterDtlByCode(
					ParamHeader.PARAM_HEAD_CODE_MENU_TYPE, menuVO.getType());
		}*/

		if (isInsert) {
			Menu newMenu = new Menu();

			newMenu.setMenuName(menuVO.getMenuName());
			newMenu.setMenuCode(menuVO.getMenuCode());
			newMenu.setAction(menuVO.getAction());
			newMenu.setDescription(menuVO.getDescription());
			newMenu.setMenuLevel(menuVO.getMenuLevel());
			newMenu.setMenuOrder(menuVO.getMenuOrder());
			newMenu.setActiveFlag(menuVO.getActiveFlag());
			newMenu.setMenuTypeCode(menuVO.getMenuType());
			
			if(menuVO.getParentId() !=null){
				newMenu.setParent(findById(menuVO.getParentId()));
				newMenu.setMenuOrder(maxMenuOrder(menuVO.getParentId()));
			}else{
				newMenu.setMenuOrder(maxMenuOrderParentIsNull());
			}
			

			updateCreationInfo(newMenu, user);
			save(newMenu);
			//flush();
		} else {
			Menu editedMenu = getById(menuVO.getMenuId());

			editedMenu.setMenuName(menuVO.getMenuName());
			editedMenu.setMenuCode(menuVO.getMenuCode());
			editedMenu.setAction(menuVO.getAction());
			editedMenu.setDescription(menuVO.getDescription());
			editedMenu.setMenuLevel(menuVO.getMenuLevel());
			editedMenu.setMenuOrder(menuVO.getMenuOrder());
			editedMenu.setActiveFlag(menuVO.getActiveFlag());
			editedMenu.setMenuTypeCode(menuVO.getMenuType());
			if(menuVO.getParentId()!=null){
				editedMenu.setParent(findById(menuVO.getParentId()));
			}

			updateLastUpdateInfo(editedMenu, user);
			update(editedMenu);
			//flush();

		}

	}

	@Override
	public List<MenuVO> getListParentMenu(MenuVO menuVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private StringBuilder getQueryWhereString(StringBuilder sqlQuery,
			List<? extends SearchObject> searchCriteria) {

		sqlQuery = sqlQuery.append(" Where 1=1 ");
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {

				if (searchVal.getSearchColumn().compareTo("menuName") == 0) {
					sqlQuery = sqlQuery.append("   AND upper(MENU_NAME) = upper('"+searchVal.getSearchValueAsString()+"') ");
				}


			}
		}

		System.out.println("SQL QUERY SEARCH " + sqlQuery);

		return sqlQuery;
	}
	
	@Override
	public List<MenuVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		StringBuilder sqlQuery = new StringBuilder("SELECT MENU_ID, MENU_CODE, MENU_NAME , MENU_TYPE, PARENT_ID,DESCRIPTION,ACTION,MENU_LEVEL,MENU_ORDER,ACTIVE_FLAG  FROM POS_MENU ");
        
		SQLQuery result = getSession().createSQLQuery(
				sqlQuery.toString());
		
		sqlQuery = getQueryWhereString(sqlQuery, searchCriteria);
		
		result.setFirstResult(first);
		result.setMaxResults(pageSize);
		
		List<MenuVO> menuVoList = new ArrayList<MenuVO>();
        
        for(int i=0; i<result.list().size(); i++){
        	Object[] obj = (Object[])result.list().get(i);
        	MenuVO menuVO = new MenuVO();
        	menuVO.setMenuId(Long.parseLong(obj[0]+""));
        	menuVO.setMenuCode((String)obj[1]);
        	menuVO.setMenuName((String)obj[2]);
        	menuVO.setMenuType((String)obj[3]);
        	//menuVO.setParent(findByIdToVO((Long)obj[4]));
        	menuVO.setParentId((Long)obj[4]);
        	menuVO.setDescription((String)obj[5]);
        	menuVO.setAction((String)obj[6]);
        	menuVO.setMenuLevel((Integer)obj[7]);
        	menuVO.setMenuOrder((Integer)obj[8]);
        	//menuVO.setActiveFlag((String)obj[9]);
        	menuVoList.add(menuVO);
        }
        
        
		return menuVoList;
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		

		StringBuilder sqlQuery = new StringBuilder("SELECT count(1)  FROM POS_MENU ");
        
		SQLQuery result = getSession().createSQLQuery(
				sqlQuery.toString());
		
		sqlQuery = getQueryWhereString(sqlQuery, searchCriteria);
		
		return ((Long) result.uniqueResult());
	}

	@Override
	public List<MenuVO> menuSearch() {
        String sql = "SELECT MENU_ID, MENU_CODE, MENU_NAME , MENU_TYPE, PARENT_ID,DESCRIPTION,ACTION,MENU_LEVEL,MENU_ORDER,ACTIVE_FLAG  FROM POS_MENU";
        
        Query query = getSession().createSQLQuery(sql);
		
        List<MenuVO> menuVoList = new ArrayList<MenuVO>();
        
        for(int i=0; i<query.list().size(); i++){
        	Object[] obj = (Object[])query.list().get(i);
        	MenuVO menuVO = new MenuVO();
        	menuVO.setMenuId(Long.parseLong(obj[0]+""));
        	menuVO.setMenuCode((String)obj[1]);
        	menuVO.setMenuName((String)obj[2]);
        	menuVO.setMenuType((String)obj[3]);
        	menuVO.setParentId((Long)obj[4]);
        	menuVO.setDescription((String)obj[5]);
        	menuVO.setAction((String)obj[6]);
        	menuVO.setMenuLevel((Integer)obj[7]);
        	menuVO.setMenuOrder((Integer)obj[8]);
        	//menuVO.setActiveFlag((String)obj[9]);
        	menuVoList.add(menuVO);
        }
        
		return menuVoList;
	}
	
	public MenuVO findByIdToVO(Long id){
		MenuVO menuVO = new MenuVO();
		Menu menu = findById(new Long(id));
		if(menu != null){
			menuVO.setMenuId(menu.getMenuId());
			menuVO.setAction(menu.getAction());
			//menuVO.setActiveFlag(menu.getActiveFlag());
			menuVO.setMenuCode(menu.getMenuCode());
			menuVO.setMenuLevel(menu.getMenuLevel());
			menuVO.setMenuName(menu.getMenuName());
			menuVO.setMenuType(menu.getMenuTypeCode());
			menuVO.setDescription(menu.getDescription());
			menuVO.setCreated_by(menu.getCreateBy());
			menuVO.setCreation_date(menu.getCreateOn());
			
			List<MenuVO> listDetail = new ArrayList<MenuVO>();
			for(int i=0;i<menu.getChildMenuList().size();i++){
				
				Menu menuDetail = (Menu)menu.getChildMenuList().get(i);
				MenuVO menuVODetail = new MenuVO();
				menuVODetail.setMenuId(menuDetail.getMenuId());
				menuVODetail.setAction(menuDetail.getAction());
				//menuVODetail.setActiveFlag(menuDetail.getActiveFlag());
				menuVODetail.setMenuCode(menuDetail.getMenuCode());
				menuVODetail.setMenuLevel(menuDetail.getMenuLevel());
				menuVODetail.setMenuName(menuDetail.getMenuName());
				menuVODetail.setMenuType(menuDetail.getMenuTypeCode());
				menuVODetail.setDescription(menuDetail.getDescription());
				menuVODetail.setCreated_by(menuDetail.getCreateBy());
				menuVODetail.setCreation_date(menuDetail.getCreateOn());
				
				listDetail.add(menuVODetail);
			}
			
			menuVO.setChildMenuList(listDetail);
		    menuVO.setParent(findByIdToVOParent(menu.getParent().getMenuId()));
		}
		return menuVO;
	}
	
	public MenuVO findByIdToVOParent(Long id){
		MenuVO menuVO = new MenuVO();
		Menu menu = findById(new Long(id));
		if(menu != null){
			menuVO.setMenuId(menu.getMenuId());
			menuVO.setAction(menu.getAction());
			//menuVO.setActiveFlag(menu.getActiveFlag());
			menuVO.setMenuCode(menu.getMenuCode());
			menuVO.setMenuLevel(menu.getMenuLevel());
			menuVO.setMenuName(menu.getMenuName());
			menuVO.setMenuType(menu.getMenuTypeCode());
			menuVO.setDescription(menu.getDescription());
			menuVO.setCreated_by(menu.getCreateBy());
			menuVO.setCreation_date(menu.getCreateOn());
			
			List<MenuVO> listDetail = new ArrayList<MenuVO>();
			for(int i=0;i<menu.getChildMenuList().size();i++){
				
				Menu menuDetail = (Menu)menu.getChildMenuList().get(i);
				MenuVO menuVODetail = new MenuVO();
				menuVODetail.setMenuId(menuDetail.getMenuId());
				menuVODetail.setAction(menuDetail.getAction());
				//menuVODetail.setActiveFlag(menuDetail.getActiveFlag());
				menuVODetail.setMenuCode(menuDetail.getMenuCode());
				menuVODetail.setMenuLevel(menuDetail.getMenuLevel());
				menuVODetail.setMenuName(menuDetail.getMenuName());
				menuVODetail.setMenuType(menuDetail.getMenuTypeCode());
				menuVODetail.setDescription(menuDetail.getDescription());
				menuVODetail.setCreated_by(menuDetail.getCreateBy());
				menuVODetail.setCreation_date(menuDetail.getCreateOn());
				
				listDetail.add(menuVODetail);
			}
			
			menuVO.setChildMenuList(listDetail);
		
		}
		return menuVO;
	}
	
	public List<Menu> getChildMenuByParentId(Long parentId) {

		Criteria criteria = getSession().createCriteria(Menu.class);
		
		criteria.createAlias("parent", "parent");
		criteria.add(Restrictions.eq("parent.menuId", parentId));
		//criteria.add(Restrictions.eq("parentId", parentId));
		criteria.addOrder(Order.asc("menuId"));
		criteria.addOrder(Order.asc("menuLevel"));
		criteria.addOrder(Order.asc("menuOrder"));

		return criteria.list();

	}
	
	public static String [] splitBySpace(String value) {
		return value.split("\\s+");
	}
	
	public Criterion buildResctrictionsAndForAllProperty(String [] listProp, String [] listValueLike) {
		Criterion criterion = null;
		
		for (String valueLike : listValueLike) {
			if (criterion == null) {
				criterion = buildResctrictionsOrForAllProperty(listProp, valueLike);
			} else {
				criterion = Restrictions.and(criterion, buildResctrictionsOrForAllProperty(listProp, valueLike));
			}
		}
		
		return criterion;
	}
	
	public Criterion buildResctrictionsOrForAllProperty(String [] listProp, String valueLike) {
		Criterion criterion = null;

		for (String prop : listProp) {
			if (criterion == null) {
				criterion = Restrictions.ilike(prop, valueLike, MatchMode.ANYWHERE);
			} else {
				String valueLikeTemp = null;
				if(prop.equals("activeFlag")){
				    if("HIDDEN".contains(valueLike.trim().toUpperCase())){
				    	valueLikeTemp = "H";
				    }else if("ENABLED".contains(valueLike.trim().toUpperCase())){
				    	valueLikeTemp = "Y";
				    }else if("DISABLED".contains(valueLike.trim().toUpperCase())){
				    	valueLikeTemp = "N";
				    }
					
				}
				else{
					valueLikeTemp = valueLike;
				}
				
				criterion = Restrictions.or(criterion, Restrictions.ilike(prop, valueLikeTemp, MatchMode.ANYWHERE));
			}
		}
		
		return criterion;
	}
	
	public List<Menu> searchMenuByFilterAll(String searchAny) {
		Criteria criteria = getSession().createCriteria(Menu.class);

		criteria.add(
				buildResctrictionsAndForAllProperty(
						new String[]{"menuCode", "menuName", "description", "activeFlag"},
						splitBySpace(searchAny))
		);
		
		
		criteria.addOrder(Order.asc("menuCode"));
		System.out.println("query=="+criteria.list().size());
		
		
		return criteria.list();
    }	
	
	
	public List<MenuVO> searchMenuDasboard(Long userId) {
		String sql = " SELECT pm2.menu_id, pm2.menu_code, pm2.menu_name FROM pos_menu pm2 "
				+ "       WHERE pm2.menu_id IN (" +
				"               SELECT pm.parent_id "
				+ "               FROM pos_menu pm "
				+ "              WHERE pm.menu_id IN (SELECT pm.menu_id " +
				"                                       FROM pos_role_dtl prd "
				+ "                                          INNER JOIN pos_menu pm ON prd.menu_id = pm.menu_id "
				+ "                                          INNER JOIN pos_role pr ON pr.role_id = prd.role_id "
				+ "                                          INNER JOIN pos_user pu ON PU.ROLE_ID = pr.role_id "
				+ "                                    WHERE PU.USER_ID =  "+userId+" "
				+ "                                                    )) and pm2.active_flag = 'Y' order by  pm2.menu_id asc";
				
		Query query = getSession().createSQLQuery(sql);
		
		List<MenuVO> menuList = new ArrayList<MenuVO>();
		for(int i=0; i<query.list().size(); i++){
		    Object[] obj = (Object[]) query.list().get(i);
		    MenuVO menu = new MenuVO();
		    menu.setMenuId(new Long(obj[0]+""));
		    menu.setMenuCode((String) (obj[1]+""));
		    menu.setMenuName((String) (obj[2]+""));
		 
		    menu.setChildMenuList(searchMenuDasboardDetail(menu.getMenuId(), userId));
		    menuList.add(menu);
		}
		
		return menuList;
    }
	
	private List<MenuVO> searchMenuDasboardDetail(Long menuId, Long userId) {

		List<MenuVO> menuList = new ArrayList<MenuVO>();
		try{
			String sql = "   SELECT pm.menu_id, pm.menu_code, pm.menu_name, pm.parent_id, pm.action " +
					     "     FROM pos_role_dtl prd INNER JOIN pos_menu pm ON prd.menu_id = pm.menu_id " +
					     "          INNER JOIN pos_role pr ON pr.role_id = prd.role_id " +
					     "          INNER JOIN pos_user pu ON pu.role_id = pr.role_id " +
					     "    WHERE pm.parent_id = "+menuId+" and pu.user_id = "+userId+" and pm.active_flag = 'Y' " +
					     " order by pm.parent_id, pm.menu_order asc";
					
			Query query = getSession().createSQLQuery(sql);
			
			for(int i=0; i<query.list().size(); i++){
			    Object[] obj = (Object[]) query.list().get(i);
			    MenuVO menu = new MenuVO();
			    menu.setMenuId(new Long(obj[0]+""));
			    menu.setMenuCode((String) (obj[1]+""));
			    menu.setMenuName((String) (obj[2]));
			    menu.setParentId(new Long(obj[3]+""));
			    menu.setAction((String) (obj[4]+""));
			    
			    menuList.add(menu);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return menuList;
    }

	@Override
	public Integer maxMenuOrder(Long parentId) {
		Integer maxMenuOrder = null;
		
		String sql= " SELECT MAX(MENU_ORDER) FROM POS_MENU WHERE PARENT_ID = "+parentId+" ";
		
		Query query = getSession().createSQLQuery(sql);
		
		if(query.list().size() > 0){
			if(query.uniqueResult() !=null){
				maxMenuOrder = Integer.parseInt(query.uniqueResult()+"")+1;
			}else{
				maxMenuOrder = 1;
			}
		}else{
			maxMenuOrder = 1;
		}
		
		return maxMenuOrder;
	}
	
	private Integer maxMenuOrderParentIsNull() {
		Integer maxMenuOrder = null;
		
		String sql= " SELECT MAX(MENU_ORDER) FROM POS_MENU ";
		
		Query query = getSession().createSQLQuery(sql);
		
		if(query.list().size() > 0){
			if(query.uniqueResult() !=null){
				maxMenuOrder = Integer.parseInt(query.uniqueResult()+"")+1;
			}else{
				maxMenuOrder = 1;
			}
		}else{
			maxMenuOrder = 1;
		}
		
		return maxMenuOrder;
	}


}
