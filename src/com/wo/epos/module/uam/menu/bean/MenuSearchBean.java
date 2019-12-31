package com.wo.epos.module.uam.menu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.sun.star.sdbc.SQLException;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.util.FacesUtils;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.menu.service.MenuService;
import com.wo.epos.module.uam.menu.vo.MenuVO;

@ManagedBean(name = "menuSearchBean")
@ViewScoped
public class MenuSearchBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(MenuSearchBean.class);
	
	@ManagedProperty(value = "#{menuService}")
	private MenuService menuService;
	
	private MenuVO menuVO = new MenuVO();	

	private PagingTableModel<Menu> pagingMenu;	
	
	private List<Menu> menuList = new ArrayList<Menu>();
	
	private List<MenuVO> selectedMenus;
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean checkRowEmpty = true;
	private boolean flgRenderBtn = true;
	
	private String searchAll;
	
	private boolean selectAll;
	
	private Integer menu_parent_level;
	
	private TreeNode root;
	
	@ManagedProperty(value = "#{facesUtil}")
	private FacesUtils facesUtils;
	

	@PostConstruct
	public void postConstruct(){
		try{
		menuVO = new MenuVO();
		
		//pagingMenu = new PagingTableModel(menuService, 10);
		//menuList = menuService.menuSearch();	
		searchMenu();
		disableFlag = false;
		disableFlagAdd = true;		
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	public void search() throws ClassCastException, SQLException, Exception{
		
		if(searchAll == null || searchAll.isEmpty()){
			searchMenu();
		}else{
			searchFromButton();
		}
	}
	
	public void searchFromButton() {
		logger.debug("MenuEditBean searchMenu from button");
		
		try {
			
			setMenuList(menuService.searchMenuByFilterAll(searchAll));
			
			root = new DefaultTreeNode("root", null);
			populateTree(getMenuList(), root);
			
			//removeNodeContainingChild(root);

		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		} finally {
			if (getMenuList() != null && getMenuList().size() > 0) setCheckRowEmpty(false);
			else setCheckRowEmpty(true);
			
			if (getMenu_parent_level() != null && getMenu_parent_level().intValue() <= 0) setFlgRenderBtn(true);
			else setFlgRenderBtn(false);
		}

		
		
	}
	
	private void removeNodeContainingChild(TreeNode nodeRoot) {
		List <TreeNode> listNodeToRemove = new ArrayList<TreeNode>();
		for (TreeNode node : nodeRoot.getChildren()) {
			if (node.getChildCount() > 0) {
				listNodeToRemove.add(node);
			}
		}

		for (TreeNode node : listNodeToRemove) {
			nodeRoot.getChildren().remove(node);
		}
	}
	
	public void searchMenu() throws ClassCastException, SQLException, Exception {
		logger.debug("MenuEditBean searchMenu");
		try {
			MenuVO searchMenuVO = new MenuVO();
			Long menuId = null;
			Integer menuLevel = null;
			String tempMenuTitle = "";
			String tempMenuCode = "";
			
			if (FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get("menuParam") != null) {
				String stringForMenu = FacesContext.getCurrentInstance().getExternalContext()
						.getRequestParameterMap().get("menuParam");
				tempMenuTitle = stringForMenu.split(";") [0];
				menuId = new Long(stringForMenu.split(";")[1]);
				menuLevel = new Integer(stringForMenu.split(";")[2]);
				//tempMenuCode = stringForMenu.split(";") [3];
			}

			/*if (menuId != null && menuLevel != null) {
				searchMenuVO.setId(menuId);
				setPrev_parent_id(getMenu_parent_id());
				setPrev_parent_level(getMenu_parent_level());
				setPrev_menu_title(getMenuTitle());
				setPrev_menu_code(getMenuCode());
				setMenu_parent_level(menuLevel);
				setMenu_parent_id(menuId);
				setMenuTitle(tempMenuTitle);
				setMenuCode(tempMenuCode);
			} else {
				setMenu_parent_level(new Integer(0));
				setMenu_parent_id(new Long(0));
				setMenuTitle(defaultTitle);
				setPrev_parent_id(new Long(0));
				setPrev_parent_level(new Integer(0));
				setPrev_menu_title("");
				setPrev_menu_code("");
				setMenuCode("");
			}*/
			
			searchMenuAndPopulateTree(searchMenuVO, true);
			
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			/*if (getMenuList() != null && getMenuList().size() > 0) setCheckRowEmpty(false);
			else setCheckRowEmpty(true);
			
			if (getMenu_parent_level() != null && getMenu_parent_level().intValue() <= 0) setFlgRenderBtn(true);
			else setFlgRenderBtn(false);*/
		}
    }
	
	public void searchMenuAndPopulateTree (MenuVO menuVO, boolean getWithDetail) throws ClassCastException, SQLException, Exception {
		setMenuList(menuService.getListMenuByParentId(menuVO, getWithDetail, true));
		
		root = new DefaultTreeNode("root", null);
		populateTree(getMenuList(), root);
	}
    
    private void populateTree(List<Menu> menuListRaw, TreeNode rootNode) {
		for (int index = 0; (menuListRaw != null && index < menuListRaw.size()); index++) {
			Menu menuVO = (Menu) menuListRaw.get(index);
			TreeNode treeNode = new DefaultTreeNode(menuVO , rootNode);
			
			List<Menu> childMenuList = menuService.getChildMenuByParentId(menuVO.getMenuId());
			if(childMenuList!=null && childMenuList.size()>0){
				populateTree(childMenuList, treeNode);
			}
		}
	}
    
	public void modeDelete(Menu vo){
		try
		{
			menuService.deleteMenu(vo.getMenuId());
			
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
                null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formMenuTitle")),
					null);	    
		}
	}
	
	public void selectAllData(javax.faces.event.AjaxBehaviorEvent e) {
        int total = 0;
        if (selectAll) {
            for (int i = 0; i < menuList.size(); i++) {
                ((Menu) menuList.get(i)).setChecked(true);
                total++;
            }
        } else {
            for (int i = 0; i < menuList.size(); i++) {
            	 ((Menu) menuList.get(i)).setChecked(false);
            }
        }
}
	

	public MenuVO getMenuVO() {
		return menuVO;
	}

	public void setMenuVO(MenuVO menuVO) {
		this.menuVO = menuVO;
	}

	public boolean isDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}

	public boolean isDisableFlagAdd() {
		return disableFlagAdd;
	}

	public void setDisableFlagAdd(boolean disableFlagAdd) {
		this.disableFlagAdd = disableFlagAdd;
	}

	public FacesUtils getFacesUtils() {
		return facesUtils;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public PagingTableModel<Menu> getPagingMenu() {
		return pagingMenu;
	}

	public void setPagingMenu(PagingTableModel<Menu> pagingMenu) {
		this.pagingMenu = pagingMenu;
	}

	
	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public List<MenuVO> getSelectedMenus() {
		return selectedMenus;
	}

	public void setSelectedMenus(List<MenuVO> selectedMenus) {
		this.selectedMenus = selectedMenus;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public boolean isCheckRowEmpty() {
		return checkRowEmpty;
	}

	public void setCheckRowEmpty(boolean checkRowEmpty) {
		this.checkRowEmpty = checkRowEmpty;
	}

	public Integer getMenu_parent_level() {
		return menu_parent_level;
	}

	public void setMenu_parent_level(Integer menu_parent_level) {
		this.menu_parent_level = menu_parent_level;
	}

	public boolean isFlgRenderBtn() {
		return flgRenderBtn;
	}

	public void setFlgRenderBtn(boolean flgRenderBtn) {
		this.flgRenderBtn = flgRenderBtn;
	}

	
	
	
}
