package com.wo.epos.module.master.province.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;

@ManagedBean(name = "provinceBean")
@ViewScoped
public class ProvinceBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(ProvinceBean.class);

	private String MODE_TYPE;

	@ManagedProperty(value = "#{provinceSearchBean}")
	private ProvinceSearchBean provinceSearchBean;

	@ManagedProperty(value = "#{provinceInputBean}")
	private ProvinceInputBean provinceInputBean;

	@PostConstruct
	public void postConstruct() {
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
	}

	public void modeSearch() {
		MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
		provinceSearchBean.search();
		provinceSearchBean.setSearchAll("");
		provinceSearchBean.setCheckSearch(0);
		provinceSearchBean.search();
	}

	public void modeAdd() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		provinceInputBean.setMODE_TYPE("ADD");
		provinceInputBean.modeAdd();
	}

	public void modeEdit() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		provinceInputBean.modeEdit(provinceSearchBean.getSelectedProvinces());
	}

	public void modeDelete() {
		try {
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			provinceSearchBean.modeDelete(provinceSearchBean
					.getSelectedProvinces());
			provinceSearchBean.search();
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils
					.addFacesMsg(
							FacesMessage.SEVERITY_ERROR,
							"frm001:growlMessage",
							facesUtils
									.getResourceBundleStringValue("formProvinceTitle")
									+ " tidak bisa dihapus karena sudah digunakan dibagian lain",
							null);
		}

	}

	public void modeSave() {
		if(provinceInputBean.validate()){
			MODE_TYPE = CommonConstants.MODE_TYPE_SEARCH;
			provinceInputBean.save();
			provinceSearchBean.search();
		}
		
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public ProvinceSearchBean getProvinceSearchBean() {
		return provinceSearchBean;
	}

	public void setProvinceSearchBean(ProvinceSearchBean provinceSearchBean) {
		this.provinceSearchBean = provinceSearchBean;
	}

	public ProvinceInputBean getProvinceInputBean() {
		return provinceInputBean;
	}

	public void setProvinceInputBean(ProvinceInputBean provinceInputBean) {
		this.provinceInputBean = provinceInputBean;
	}

}
