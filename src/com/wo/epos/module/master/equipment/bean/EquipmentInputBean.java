package com.wo.epos.module.master.equipment.bean;

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
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.master.equipment.service.EquipmentService;
import com.wo.epos.module.master.equipment.vo.EquipmentVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "equipmentInputBean")
@ViewScoped
public class EquipmentInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 8006909196838390207L;
	static Logger logger = Logger.getLogger(EquipmentInputBean.class);

	@ManagedProperty(value = "#{equipmentService}")
	private EquipmentService equipmentService;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	private EquipmentVO equipmentVO = new EquipmentVO();

	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> equipmentTypeSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;
	private String userPosition;

	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			equipmentVO = new EquipmentVO();

			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}

			List<ParameterDtl> paramSelectList = parameterService.parameterDtlList(CommonConstants.EQUIPMENT_TYPE);
			equipmentTypeSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : paramSelectList) {
				equipmentTypeSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlDesc()));
			}

			MODE_TYPE = "ADD";

			userPosition = getUserLevel();

			if (userPosition.equals(CommonConstants.ADMIN_LEVEL)
					|| userPosition.equals(CommonConstants.COMPANY_LEVEL)) {
				List<Outlet> outletList = outletService.findOutletByCompany(userSession.getCompanyId());
				outletSelectItem = new ArrayList<SelectItem>();
				for (Outlet dtl : outletList) {
					outletSelectItem.add(new SelectItem(dtl.getOutletId(), dtl.getOutletName()));
				}
			}
		}
	}

	public boolean validate() {
		boolean valid = true;

		if (equipmentVO.getCompanyId() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formEquipmentCompany")
					+ " " + facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		if (equipmentVO.getOutletId() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formEquipmentOutlet")
					+ " " + facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		if (equipmentVO.getEquipmentCode().trim().equals("") || equipmentVO.getEquipmentCode().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formEquipmentCode") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		if (equipmentVO.getEquipmentName() == null || equipmentVO.getEquipmentName().trim().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formEquipmentName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if (equipmentVO.getEquipmentTypeCode() == null || equipmentVO.getEquipmentTypeCode().trim().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formEquipmentName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		return valid;

	}

	public void save() {
		try {
			if (userSession.getOutletId() != null) {
				equipmentVO.setOutletId(userSession.getOutletId());
			}

			if (MODE_TYPE.equals("ADD")) {
				equipmentService.save(equipmentVO, userSession.getUserCode());

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
						null);
			} else if (MODE_TYPE.equals("EDIT")) {
				equipmentService.update(equipmentVO, userSession.getUserCode());

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

	public void modeAdd() {
		equipmentVO = new EquipmentVO();
	}

	public void modeEdit(List<EquipmentVO> equipmentList) {
		MODE_TYPE = "EDIT";
		for (int i = 0; i < equipmentList.size(); i++) {
			EquipmentVO equipmentVOTemp = (EquipmentVO) equipmentList.get(i);
			equipmentVO = equipmentVOTemp;
		}

	}

	public void changeCompanyToOutlet() {
		if (equipmentVO.getCompanyId() != null) {
			List<Outlet> outletList = outletService.findOutletByCompany(equipmentVO.getCompanyId());
			outletSelectItem = new ArrayList<SelectItem>();
			if (outletList.size() > 0) {
				for (Outlet out : outletList) {
					outletSelectItem.add(new SelectItem(out.getOutletId(), out.getOutletName()));
				}
			}

		}
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		EquipmentInputBean.logger = logger;
	}

	public EquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public EquipmentVO getEquipmentVO() {
		return equipmentVO;
	}

	public void setEquipmentVO(EquipmentVO equipmentVO) {
		this.equipmentVO = equipmentVO;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}

	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}

	public List<SelectItem> getEquipmentTypeSelectItem() {
		return equipmentTypeSelectItem;
	}

	public void setEquipmentTypeSelectItem(List<SelectItem> equipmentTypeSelectItem) {
		this.equipmentTypeSelectItem = equipmentTypeSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

}
