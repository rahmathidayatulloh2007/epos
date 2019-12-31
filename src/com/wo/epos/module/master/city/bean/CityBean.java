package com.wo.epos.module.master.city.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.jboss.logging.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "cityBean")
@ViewScoped
public class CityBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -2757530445847128167L;

	static Logger logger = Logger.getLogger(CityBean.class);

	private String MODE_TYPE;

	@ManagedProperty(value = "#{citySearchBean}")
	private CitySearchBean citySearchBean;

	@ManagedProperty(value = "#{cityInputBean}")
	private CityInputBean cityInputBean;

	@PostConstruct
	public void postConstruct() {
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;

	}

	public void modeSearch() {
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		citySearchBean.search();
		citySearchBean.setSearchAll("");
		citySearchBean.setCheckSearch(0);
		citySearchBean.search();
	}

	public void modeAdd() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		cityInputBean.setMODE_TYPE("ADD");
		cityInputBean.modeAdd();
	}

	public void modeEdit() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		cityInputBean.modeEdit(citySearchBean.getSelectedCities());
	}

	public void modeDelete() {
		try {
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			citySearchBean.modeDelete(citySearchBean.getSelectedCities());
			citySearchBean.search();
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:growlMessage",
					facesUtils.getResourceBundleStringValue("formCityTitle")
							+ " tidak bisa dihapus karena sudah digunakan dibagian lain",
					null);
		}
	}

	public void modeSave() {
		if (cityInputBean.validate()) {
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			cityInputBean.save();
			citySearchBean.search();
		}
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public CitySearchBean getCitySearchBean() {
		return citySearchBean;
	}

	public void setCitySearchBean(CitySearchBean citySearchBean) {
		this.citySearchBean = citySearchBean;
	}

	public CityInputBean getCityInputBean() {
		return cityInputBean;
	}

	public void setCityInputBean(CityInputBean cityInputBean) {
		this.cityInputBean = cityInputBean;
	}

}
