package com.wo.epos.module.uam.menu.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.module.uam.menu.model.Menu;
import com.wo.epos.module.uam.menu.service.MenuService;
import com.wo.epos.module.uam.menu.vo.MenuVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "menuInputBean")
@ViewScoped
public class MenuInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(MenuInputBean.class);
	
	@ManagedProperty(value = "#{menuService}")
	private MenuService menuService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	private MenuVO menuVO = new MenuVO();
	private Long parentCode;
	private String parentName;
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> menuTypeList = new ArrayList<SelectItem>();
	private List<SelectItem> menuLevelList = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		menuVO = new MenuVO();
				
		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));
		
		menuTypeList = new ArrayList<SelectItem>();
		List<ParameterDtl> menuSelectList = parameterService.parameterDtlList("MENU_TYPE");
		for(int i=0; i<menuSelectList.size(); i++){
			ParameterDtl paramDtl = (ParameterDtl)menuSelectList.get(i);
			menuTypeList.add(new SelectItem(paramDtl.getParameterDtlCode(), paramDtl.getParameterDtlName()));
		}	
		
		menuLevelList = new ArrayList<SelectItem>();
		for(int i=1;i<50;i++){
			menuLevelList.add(new SelectItem(""+i, ""+i));
		}
		
		
		
		//MenuVO parent = new MenuVO();
		//menuVO.setParent(parent);
		
		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;				
		
	}
		
	public boolean validateMenu(){
		boolean valid = true;
		
		try {
			if (menuVO.getMenuCode().trim().isEmpty() || menuVO.getMenuCode() == null && MODE_TYPE == "ADD" ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesMenu",
						facesUtils.getResourceBundleStringValue("menuCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (menuVO.getMenuName().trim().isEmpty() || menuVO.getMenuName() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesMenu",
						facesUtils.getResourceBundleStringValue("menuName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if ( menuVO.getMenuType() == null || menuVO.getMenuType().trim().isEmpty() ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesMenu",
						facesUtils.getResourceBundleStringValue("menuType") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if ( menuVO.getMenuLevel() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesMenu",
						facesUtils.getResourceBundleStringValue("menuLevel") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}
		} catch (NumberFormatException ex) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesMenu",
					facesUtils.getResourceBundleStringValue("errorMustBeNumber"));
			valid = false;
		}
		
		
		return valid;

	}
	public void save()
	{
		try 
		{
			if(MODE_TYPE.equals("ADD"))
			{
				menuService.insertMenu(menuVO, true, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				menuService.insertMenu(menuVO, false, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
		}
		catch (SQLException sqlEx) {
			// TODO Auto-generated catch block
			sqlEx.printStackTrace();
			
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + sqlEx.getMessage(),
					null);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);	    
		}
	}
	
	public void modeEdit(Menu vo){
		menuVO = new MenuVO();
		MODE_TYPE = "EDIT";
		menuVO.setMenuId(vo.getMenuId());
		menuVO.setAction(vo.getAction());
		//menuVO.setActiveFlag(vo.getActiveFlag().equals("Y")?true:false);
		menuVO.setActiveFlag(vo.getActiveFlag());
		menuVO.setDescription(vo.getDescription());
		menuVO.setMenuLevel(vo.getMenuLevel());
		menuVO.setMenuName(vo.getMenuName());
		menuVO.setMenuCode(vo.getMenuCode());
		menuVO.setMenuOrder(vo.getMenuOrder());
		menuVO.setMenuType(vo.getMenuTypeCode());
		if(vo.getParent()!=null){
			menuVO.setParentCode(vo.getParent().getMenuCode());
			menuVO.setParentName(vo.getParent().getMenuName());
		}
	}
	
	public void modeAdd(Menu vo){
		menuVO = new MenuVO();
		menuVO.setParentId(vo.getMenuId());
		menuVO.setParentCode(vo.getMenuCode());
		menuVO.setParentName(vo.getMenuName());
		menuVO.setMenuLevel(vo.getMenuLevel()+1);
	}
	
	public void modeAddNew(){
		menuVO = new MenuVO();
		menuVO.setMenuLevel(1);
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

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public List<SelectItem> getMenuTypeList() {
		return menuTypeList;
	}

	public void setMenuTypeList(List<SelectItem> menuTypeList) {
		this.menuTypeList = menuTypeList;
	}

	public Long getParentCode() {
		return parentCode;
	}

	public void setParentCode(Long parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<SelectItem> getMenuLevelList() {
		return menuLevelList;
	}

	public void setMenuLevelList(List<SelectItem> menuLevelList) {
		this.menuLevelList = menuLevelList;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	
	
	
}
