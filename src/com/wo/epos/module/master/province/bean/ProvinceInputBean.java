package com.wo.epos.module.master.province.bean;

import java.io.Serializable;
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
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;

@ManagedBean(name = "provinceInputBean")
@ViewScoped
public class ProvinceInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(ProvinceInputBean.class);

	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;

	private ProvinceVO provinceVO = new ProvinceVO();

	private boolean disableFlag;
	private boolean disableFlagAdd;

	private String searchAll;
	private String cBorder;

	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;

	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			provinceVO = new ProvinceVO();

			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));
			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;
		}
	}

	public void save() {
		try {
			if (MODE_TYPE.equals("ADD")) {

				provinceService.save(provinceVO, userSession.getUserCode());

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
						null);

			} else if (MODE_TYPE.equals("EDIT")) {
				provinceService.update(provinceVO, userSession.getUserCode());

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")),
						null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:messages",
					"Operation Failed : " + ex.getMessage(), null);
		}
	}

	public boolean validate() {
		boolean valid = true;

		if (provinceVO.getProvinceCode().trim().equals("") || provinceVO.getProvinceCode().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formProvinceCode") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			cBorder ="#ed1111";
			valid = false;
		}

		if (provinceVO.getProvinceName().trim().equals("") || provinceVO.getProvinceName().isEmpty()) {			
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formProvinceName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			cBorder ="#ed1111";	
			valid = false;
		}

		return valid;
	}

	public void modeEdit(List<ProvinceVO> provinceList) {
		MODE_TYPE = "EDIT";
		for (int i = 0; i < provinceList.size(); i++) {
			ProvinceVO provinceVOTemp = (ProvinceVO) provinceList.get(i);
			provinceVO.setProvinceId(provinceVOTemp.getProvinceId());
			provinceVO.setProvinceCode(provinceVOTemp.getProvinceCode());
			provinceVO.setProvinceName(provinceVOTemp.getProvinceName());
			provinceVO.setActiveFlag(provinceVOTemp.getActiveFlag());
		}
	}

	public void modeAdd() {
		provinceVO = new ProvinceVO();
	}

	public ProvinceVO getProvinceVO() {
		return provinceVO;
	}

	public void setProvinceVO(ProvinceVO provinceVO) {
		this.provinceVO = provinceVO;
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

	public ProvinceService getProvinceService() {
		return provinceService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
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

	public String getcBorder() {
		return cBorder;
	}

	public void setcBorder(String cBorder) {
		this.cBorder = cBorder;
	}

}
