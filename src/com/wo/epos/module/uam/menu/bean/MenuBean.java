package com.wo.epos.module.uam.menu.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.sun.star.sdbc.SQLException;
import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.uam.menu.model.Menu;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(MenuBean.class);
	
	private String MODE_TYPE;
	
	@ManagedProperty(value = "#{menuSearchBean}")
	private MenuSearchBean menuSearchBean;
	
	@ManagedProperty(value = "#{menuInputBean}")
	private MenuInputBean menuInputBean;
	
	@PostConstruct
	public void postConstruct(){
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}
	
	
	public void modeSearch(){
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		try {
			menuSearchBean.searchMenu();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modeAddNew(){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		menuInputBean.setMODE_TYPE("ADD");
		menuInputBean.modeAddNew();
	}
	
	public void modeAddFromParent(Menu menu){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		menuInputBean.setMODE_TYPE("ADD");
		menuInputBean.modeAdd(menu);
	}
	
	public void modeEdit(Menu menu){
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		menuInputBean.modeEdit(menu);
	}
	
	public void modeDelete(Menu menu){
		try{
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			menuSearchBean.modeDelete(menu);
		}catch(Exception e){
			e.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:growlMessage",
					"Menu tidak bisa dihapus karena sudah digunakan dibagian lain",
					null);	    
		}		
		
		try {
			menuSearchBean.searchMenu();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modeSave(){
		
		if (menuInputBean.validateMenu()) {
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			menuInputBean.save();
			try {
				menuSearchBean.searchMenu();
			} catch (ClassCastException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public MenuSearchBean getMenuSearchBean() {
		return menuSearchBean;
	}

	public void setMenuSearchBean(MenuSearchBean menuSearchBean) {
		this.menuSearchBean = menuSearchBean;
	}


	public MenuInputBean getMenuInputBean() {
		return menuInputBean;
	}


	public void setMenuInputBean(MenuInputBean menuInputBean) {
		this.menuInputBean = menuInputBean;
	}

	
	
}
