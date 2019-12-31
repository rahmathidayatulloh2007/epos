package com.wo.epos.module.uam.role.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.sun.star.sdbc.SQLException;
import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.menu.service.MenuService;
import com.wo.epos.module.uam.menu.vo.MenuVO;
import com.wo.epos.module.uam.role.model.RoleDtl;
import com.wo.epos.module.uam.role.service.RoleDtlService;
import com.wo.epos.module.uam.role.service.RoleService;
import com.wo.epos.module.uam.role.vo.RoleDtlVO;
import com.wo.epos.module.uam.role.vo.RoleVO;

@ManagedBean(name = "roleMenuAccessBean")
@ViewScoped
public class RoleMenuAccessBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -4853077063403171097L;

	static Logger logger = Logger.getLogger(RoleMenuAccessBean.class);
	
	@ManagedProperty(value = "#{roleService}")
	private RoleService roleService;
	
	@ManagedProperty(value = "#{roleDtlService}")
	private RoleDtlService roleDetailService;
		
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{menuService}")
	private MenuService menuService;	
	
	private RoleVO roleVO = new RoleVO();
	
	private TreeNode root;
	private TreeNode[] selectedMenus;
	
	private List<RoleDtlVO> roleDetailVOList = new ArrayList<RoleDtlVO>();
	
	private String MODE_TYPE;
			
	@PostConstruct
	public void postConstruct(){
		try{
			super.init();
			roleVO = new RoleVO();
			MODE_TYPE = "INSERT";
				
			//searchMenu();
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
		
	public void modeEditMenuAccess(List<RoleVO> roleVOList){
		try{
			for(RoleVO roleVOTemp : roleVOList){
				roleVO.setRoleId(roleVOTemp.getRoleId());
				roleVO.setRoleCode(roleVOTemp.getRoleCode());
				roleVO.setRoleName(roleVOTemp.getRoleName());
			    roleVO.setRoleDesc(roleVOTemp.getRoleDesc());
			    if(roleVOTemp.getCompanyId() !=null){
			    	roleVO.setCompanyId(roleVOTemp.getCompanyId());
			    	roleVO.setCompanyName(roleVOTemp.getCompanyName());
			    }else{
			    	roleVO.setCompanyId(null);
			    }
			    roleVO.setActiveFlag(roleVOTemp.getActiveFlag());	
			}
			
		    searchMenu();
		}catch(Exception e){
			System.out.println(e);
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
			MODE_TYPE = "INSERT";
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
			
		}
    }
	
	public void clear(){
		roleDetailVOList = new ArrayList<RoleDtlVO>();
	}
	
	public void searchMenuAndPopulateTree (MenuVO menuVO, boolean getWithDetail) throws ClassCastException, SQLException, Exception {
		List<Menu> menuList =  menuService.getListMenuByParentId(menuVO, getWithDetail, true);
		
		List<RoleDtl> rdDtlList = roleDetailService.searchRoleDetail(roleVO.getRoleId());
		if(rdDtlList.size() > 0){
			if(menuList.size() > 0 ){
				for(int i=0; i<menuList.size(); i++){
					Menu menu = (Menu)menuList.get(i);
					RoleDtlVO  vo = new RoleDtlVO();
					boolean check = false;
					for(int j=0; j<rdDtlList.size(); j++){					
						RoleDtl  roleDtl = new RoleDtl();
						if(roleDtl.getMenuId() == menu.getMenuId()){
							check = true;
						}
					}
					
					if(check){
						vo.setMenuId(menu.getMenuId());
						vo.setMenuCode(menu.getMenuCode());
						vo.setMenuName(menu.getMenuName());
						vo.setCheck(true);
						roleDetailVOList.add(vo);
					}else{
						vo.setMenuId(menu.getMenuId());
						vo.setMenuCode(menu.getMenuCode());
						vo.setMenuName(menu.getMenuName());
						vo.setCheck(true);
						roleDetailVOList.add(vo);
					}
				}
			}
		}else{
			if(menuList.size() > 0 ){
				for(int i=0; i<menuList.size(); i++){
					Menu menu = (Menu)menuList.get(i);
					RoleDtlVO  vo = new RoleDtlVO();
					vo.setMenuId(menu.getMenuId());
					vo.setMenuCode(menu.getMenuCode());
					vo.setMenuName(menu.getMenuName());
					roleDetailVOList.add(vo);
				}
			}
		}
		
		setRoleDetailVOList(setRealChildListSize(roleDetailVOList));
		root = new DefaultTreeNode("root", null);
		populateTree(getRoleDetailVOList(), root);
	}
	
	/*public void searchRoleMenuAndPopulateTree (Long responsibility_id) throws ClassCastException, SQLException, Exception {
		try {
			ResponsibilityDtlVO responsibilityMenuVO = new ResponsibilityDtlVO();
			responsibilityMenuVO.setResponsibility_id(responsibility_id);
			if (getResponsibility_name().trim().equalsIgnoreCase(getResponsibilityDtlVendor()))
				setResponsibilityMenuList(responsibilityDetailService.searchResponsibilityMenuAllMenu(responsibilityMenuVO, Integer.valueOf(1)));
			else
				setResponsibilityMenuList(responsibilityDetailService.searchResponsibilityMenuAllMenu(responsibilityMenuVO, Integer.valueOf(2)));
			
			setResponsibilityMenuList(setRealChildListSize(getResponsibilityMenuList()));
			
			indexCounter = 0;
			root = new DefaultTreeNode("root", null);
			populateTree(getResponsibilityMenuList(), root);
			
		} catch (SQLException ex) {
			getSQLExceptionErrorMessage(ex);
			logger.error(ex.getMessage(), ex);
			throw ex;
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}*/
    
    private void populateTree(List<RoleDtlVO> roleMenuListRaw, TreeNode rootNode) {
		for (int index = 0; (roleMenuListRaw != null && index < roleMenuListRaw.size()); index++) {
			RoleDtlVO roleVOTemp = (RoleDtlVO) roleMenuListRaw.get(index);
			TreeNode treeNode = new DefaultTreeNode(roleVOTemp , rootNode);
			
			List<Menu> childMenuList = menuService.getChildMenuByParentId(roleVOTemp.getMenuId());
						
			if(childMenuList.size() > 0){
				for(int i=0; i<childMenuList.size(); i++){
				    List<RoleDtlVO> roleDetailVOList = new ArrayList<RoleDtlVO>();	
					Menu menuTemp = (Menu)childMenuList.get(i);
					RoleDtlVO  vo = new RoleDtlVO();
					
					boolean flag = false;
					if(roleVO.getCompanyId() !=null){
						if(menuTemp.getParent() == null){
							vo.setMenuId(menuTemp.getMenuId());
							vo.setMenuCode(menuTemp.getMenuCode());
							vo.setMenuName(menuTemp.getMenuName());
						}else{
							if(roleVOTemp.getMenuCode().equals("MASTER") 
									|| roleVOTemp.getMenuCode().equals("UAM")){							
								if(menuTemp.getMenuCode().equals("ROLE") || menuTemp.getMenuCode().equals("EMPLOYEE") 
										|| menuTemp.getMenuCode().equals("OUTLET") || menuTemp.getMenuCode().equals("USER") 
										|| menuTemp.getMenuCode().equals("SYSTEM_PROPERTY") || menuTemp.getMenuCode().equals("EQUIPMENT") 
										|| menuTemp.getMenuCode().equals("VEHICLE") || menuTemp.getMenuCode().equals("PAYMENT_TYPE")){
									vo.setMenuId(menuTemp.getMenuId());
									vo.setMenuCode(menuTemp.getMenuCode());
									vo.setMenuName(menuTemp.getMenuName());
									
									List<RoleDtlVO> roleDtlVOList = roleDetailService.dataRoleDetailList(menuTemp.getMenuId());
									if(roleDtlVOList.size() > 0){
										for(RoleDtlVO voDtl : roleDtlVOList){		
											if(Integer.parseInt(voDtl.getMenuId()+"") == Integer.parseInt(menuTemp.getMenuId()+"") && 
													Integer.parseInt(roleVO.getRoleId()+"") == Integer.parseInt(voDtl.getRoleId()+"")){								
									    	    vo.setRoleDtlId(voDtl.getRoleDtlId());
									    	    MODE_TYPE = "UPDATE";
									    	    flag = true;
									    	   // break;
											}
										}
									}
	
									roleDetailVOList.add(vo);
								}
							}else{
								vo.setMenuId(menuTemp.getMenuId());
								vo.setMenuCode(menuTemp.getMenuCode());
								vo.setMenuName(menuTemp.getMenuName());
								
								List<RoleDtlVO> roleDtlVOList = roleDetailService.dataRoleDetailList(menuTemp.getMenuId());
								if(roleDtlVOList.size() > 0){
									for(RoleDtlVO voDtl : roleDtlVOList){		
										if(Integer.parseInt(voDtl.getMenuId()+"") == Integer.parseInt(menuTemp.getMenuId()+"") && 
												Integer.parseInt(roleVO.getRoleId()+"") == Integer.parseInt(voDtl.getRoleId()+"")){								
								    	    vo.setRoleDtlId(voDtl.getRoleDtlId());
								    	    MODE_TYPE = "UPDATE";
								    	    flag = true;
								    	   // break;
										}
									}
								}

								roleDetailVOList.add(vo);
							}
						}
					}else{
						vo.setMenuId(menuTemp.getMenuId());
						vo.setMenuCode(menuTemp.getMenuCode());
						vo.setMenuName(menuTemp.getMenuName());
					
						List<RoleDtlVO> roleDtlVOList = roleDetailService.dataRoleDetailList(menuTemp.getMenuId());
						if(roleDtlVOList.size() > 0){
							for(RoleDtlVO voDtl : roleDtlVOList){		
								if(Integer.parseInt(voDtl.getMenuId()+"") == Integer.parseInt(menuTemp.getMenuId()+"") && 
										Integer.parseInt(roleVO.getRoleId()+"") == Integer.parseInt(voDtl.getRoleId()+"")){								
						    	    vo.setRoleDtlId(voDtl.getRoleDtlId());
						    	    MODE_TYPE = "UPDATE";
						    	    flag = true;
						    	   // break;
								}
							}
						}			
						
						roleDetailVOList.add(vo);
					}

					
					treeNode.setExpanded(true);
                    if(flag == true){
                    	TreeNode treeChild = new DefaultTreeNode(vo , treeNode);
                	    treeNode.setSelected(true);
			    	    treeChild.setSelected(true);                    
                    }
                    else{                    	
						populateTree(roleDetailVOList, treeNode);	
                   }
                    
				}

			}
			
		}
		
	}
    
    private TreeNode[] addElement(TreeNode[] org, TreeNode added) {
		TreeNode[] result = Arrays.copyOf(org, org.length +1);
	    result[org.length] = added;
	    return result;
	}
    
    public void onNodeSelect(NodeSelectEvent event) {  
		selectUnselectTreeNode(getRoot(), event.getTreeNode(), true);  
    }  
  
    public void onNodeUnselect(NodeUnselectEvent event) {  
    	selectUnselectTreeNode(getRoot(), event.getTreeNode(), false);  
    }
    
    private void selectUnselectTreeNode(TreeNode rootNode, TreeNode paramNode, boolean selected) {
		try {
			for (int index = 0; (rootNode != null && index < rootNode.getChildCount()); index++) {
				TreeNode roleMenuTree = (TreeNode) rootNode.getChildren().get(index);
				if (roleMenuTree.equals(paramNode))
				{
					roleMenuTree.setSelected(selected);
					if (roleMenuTree.getChildren() != null && 
							roleMenuTree.getChildCount() > 0)
						setAllChild(roleMenuTree, selected);
					break;
				}
			
				selectUnselectTreeNode(roleMenuTree, paramNode, selected);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    private void extractTreeSelectable(TreeNode rootNode, int i) throws Exception {
		try {
			if (i == 0)
				selectedMenus = new TreeNode[0]; 
			for (int index = 0; (rootNode != null && index < rootNode.getChildCount()); index++) {
				TreeNode roleMenuTree = (TreeNode) rootNode.getChildren().get(index);
				if (roleMenuTree.isSelected())
					selectedMenus = addElement(selectedMenus, roleMenuTree);
				extractTreeSelectable(roleMenuTree, 1);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private void setAllChild(TreeNode theNode, boolean selected) throws Exception {
		for (int index = 0; (theNode != null && index < theNode.getChildCount()); index++) {
			TreeNode childNode = (TreeNode) theNode.getChildren().get(index);
			childNode.setSelected(selected);
			setAllChild(childNode, selected);
		}
	}
	
	private List<RoleDtlVO> setRealChildListSize(List<RoleDtlVO> initialList) {
		if (initialList != null) {
			for (int a = 0; a < initialList.size(); a++) {
				RoleDtlVO aVo = (RoleDtlVO) initialList.get(a);
				List<RoleDtlVO> aNextList = aVo.getChildRoleMenuList();
				int aSize = 0;
				if (aNextList != null) {
					aSize += aNextList.size();
					for (int b = 0; b < aNextList.size(); b++) {
						RoleDtlVO bVo = (RoleDtlVO) aNextList.get(b);
						List<RoleDtlVO> bNextList = bVo.getChildRoleMenuList();
						int bSize = 0;
						if (bNextList != null) {
							aSize += bNextList.size();
							bSize = bNextList.size();
							for (int c = 0; c < bNextList.size(); c++) {
								RoleDtlVO cVo = (RoleDtlVO) bNextList.get(c);
								List<RoleDtlVO> cNextList = cVo.getChildRoleMenuList();
								int cSize = 0;
								if (cNextList != null) {
									aSize += cNextList.size();
									bSize += cNextList.size();
									cSize = cNextList.size();
									for (int d = 0; d < cNextList.size(); d++) {
										RoleDtlVO dVo = (RoleDtlVO) cNextList.get(d);
										List<RoleDtlVO> dNextList = dVo.getChildRoleMenuList();
										int dSize = 0;
										if (dNextList != null) {
											aSize += dNextList.size();
											bSize += dNextList.size();
											cSize += dNextList.size();
											dSize = dNextList.size();
											for (int e = 0; e < dNextList.size(); e++) {
												RoleDtlVO eVo = (RoleDtlVO) dNextList.get(e);
												List<RoleDtlVO> eNextList = eVo.getChildRoleMenuList();
												int eSize = 0;
												if (eNextList != null) {
													aSize += eNextList.size();
													bSize += eNextList.size();
													cSize += eNextList.size();
													dSize += eNextList.size();
													eSize = eNextList.size();
												}
												eVo.setChildRoleMenuListSize(eSize);
												dNextList.set(e, eVo);
											}
										}
										dVo.setChildRoleMenuListSize(dSize);
										cNextList.set(d, dVo);
									}
								}
								cVo.setChildRoleMenuListSize(cSize);
								bNextList.set(c, cVo);
							}
						}
						bVo.setChildRoleMenuListSize(bSize);
						aNextList.set(b, bVo);
					}
				}
				aVo.setChildRoleMenuListSize(aSize);
				initialList.set(a, aVo);
			}
		}
		return initialList;
	}
	
	public void saveRoleMenu() throws ClassCastException, SQLException, Exception {
		try 
		{
			List<RoleDtlVO> newRoleMenuList = new ArrayList<RoleDtlVO>();
			StringBuilder builder = new StringBuilder();
			Menu menu = new Menu();
			extractTreeSelectable(getRoot(), 0);
			if (getSelectedMenus() != null) 
			{
				for (TreeNode tn : getSelectedMenus()) 
				{
					RoleDtlVO roleMenuVO = (RoleDtlVO) tn.getData();
					menu = menuService.findById(roleMenuVO.getMenuId());
					newRoleMenuList.add(roleMenuVO);					   
					
						builder.append(menu.getMenuCode());
						builder.append(":"+menu.getMenuName()+",");

				}
				
				if(builder!=null) 
				{
					builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
				}
				else 
				{
					facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_ERROR,
						null,
						facesUtils.getResourceBundleStringValue("formRoleErrMsgMinim"),
						null);
				}	
			}
			    
			RoleDtlVO rmv = new RoleDtlVO();
			rmv.setRoleId(roleVO.getRoleId());
			rmv.setChildRoleMenuList(newRoleMenuList);
			rmv.setMenuResume(builder.toString());
			if(MODE_TYPE.equals("INSERT"))
			{
			    roleDetailService.save(rmv, userSession.getUserCode());
			    
			    facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
	                null);
			}
			else if(MODE_TYPE.equals("UPDATE"))
			{
				roleDetailService.update(rmv, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
	                null);
			}
		} catch (SQLException ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
    }
	

	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RoleInputBean.logger = logger;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode[] getSelectedMenus() {
		return selectedMenus;
	}

	public void setSelectedMenus(TreeNode[] selectedMenus) {
		this.selectedMenus = selectedMenus;
	}

	public RoleDtlService getRoleDetailService() {
		return roleDetailService;
	}

	public void setRoleDetailService(RoleDtlService roleDetailService) {
		this.roleDetailService = roleDetailService;
	}

	public List<RoleDtlVO> getRoleDetailVOList() {
		return roleDetailVOList;
	}

	public void setRoleDetailVOList(List<RoleDtlVO> roleDetailVOList) {
		this.roleDetailVOList = roleDetailVOList;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	
		
	
}
