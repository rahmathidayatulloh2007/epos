package com.wo.epos.module.inv.vehicle.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.vehicle.service.VehicleService;
import com.wo.epos.module.inv.vehicle.vo.VehicleVO;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.province.service.ProvinceService;

import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "vehicleInputBean")
@ViewScoped
public class VehicleInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -1976327850226168845L;
	static Logger logger = Logger.getLogger(VehicleInputBean.class);

	@ManagedProperty(value = "#{vehicleService}")
	private VehicleService vehicleService;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	@ManagedProperty(value = "#{outletEmpService}")
	private OutletEmpService outletEmpService;

	@ManagedProperty(value = "#{mbImageStreamer}")
	private MbImageStreamer mbImageStreamer;

	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	private Outlet outlet = new Outlet();
	private VehicleVO vehicleVO = new VehicleVO();
	// private OutletEmpVO outletEmpVO = new OutletEmpVO();

	private boolean disableFlag;
	private boolean disableFlagAdd;
	// private boolean picFlagChecked;

	private String searchAll;

	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> vehicleTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> genderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> religionSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> maritalStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> vehicleStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;

	private String streamUploadId;

	private UploadedFile uploadedFile;
	private String userPosition;

	private static final String UPLOAD_FUNC = "VehicleEDIT";

	@PostConstruct
	public void postConstruct() {
		super.init();
		vehicleVO = new VehicleVO();
		outlet = new Outlet();

		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for (CompanyVO vo : companySelectList) {
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}

		List<ParameterDtl> genderSelectList = parameterService.parameterDtlList("VEHICLE_TYPE");
		vehicleTypeSelectItem = new ArrayList<SelectItem>();
		for (ParameterDtl dtl : genderSelectList) {
			vehicleTypeSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
		}

		activeSelectItem = new ArrayList<SelectItem>();
		activeSelectItem.add(new SelectItem("Y", "Y"));
		activeSelectItem.add(new SelectItem("N", "N"));

		MODE_TYPE = "ADD";
		disableFlag = false;
		disableFlagAdd = true;

		userPosition = getUserLevel();

		if (userPosition.equals(CommonConstants.ADMIN_LEVEL) || userPosition.equals(CommonConstants.COMPANY_LEVEL)) {
			List<Outlet> outletList = outletService.findOutletByCompany(userSession.getCompanyId());
			outletSelectItem = new ArrayList<SelectItem>();
			for (Outlet dtl : outletList) {
				outletSelectItem.add(new SelectItem(dtl.getOutletId(), dtl.getOutletName()));
			}
		}
	}

	public boolean validate() {
		boolean valid = true;
		
		if (vehicleVO.getOutletId() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formVehicleOutlet") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if (vehicleVO.getPoliceNo() == null || vehicleVO.getPoliceNo().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formVehiclePoliceNo") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if (vehicleVO.getVehicleType() == null || vehicleVO.getVehicleType().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formVehicleType") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}
		
		if (vehicleVO.getVehicleDesc() == null || vehicleVO.getVehicleDesc().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formVehicleDesc") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}		
		
/*		
		if (vehicleVO.getPoliceNo() == null || vehicleVO.getPoliceNo().isEmpty()) {

			addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:policeNo",
					facesUtils.getResourceBundleStringValue("formVehiclePoliceNo") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"),
					null);

			valid = false;

		}

		if (vehicleVO.getOutletId() == null) {

			addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:outletId",
					facesUtils.getResourceBundleStringValue("formVehicleOutlet") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"),
					null);

			valid = false;

		}

		if (vehicleVO.getVehicleType() == null || vehicleVO.getVehicleType().isEmpty()) {

			addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:vehicleType",
					facesUtils.getResourceBundleStringValue("formVehicleType") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"),
					null);

			valid = false;

		}

		if (vehicleVO.getVehicleDesc() == null || vehicleVO.getVehicleDesc().isEmpty()) {

			addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:vehicleDesc",
					facesUtils.getResourceBundleStringValue("formVehicleDesc") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"),
					null);

			valid = false;

		}*/

		return valid;

	}

	public void addFacesMsg(FacesMessage.Severity severity, String forComp, String msg, String detail) {
		FacesMessage message = new FacesMessage(severity, msg, detail);
		FacesContext.getCurrentInstance().addMessage(forComp, message);

	}

	public void save() {
		try {
			if (MODE_TYPE.equals("ADD")) {
				vehicleService.save(vehicleVO, userSession.getUserCode());

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
						null);
			} else if (MODE_TYPE.equals("EDIT")) {
				vehicleService.update(vehicleVO, userSession.getUserCode());

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
		this.vehicleVO = new VehicleVO();
		vehicleVO.setOutlet(outlet);

	}

	public void modeEdit(List<VehicleVO> vehicleList) {
		MODE_TYPE = "EDIT";
		for (int i = 0; i < vehicleList.size(); i++) {
			VehicleVO vehicleVOTemp = (VehicleVO) vehicleList.get(i);

			vehicleVO.setVehicleId(vehicleVOTemp.getVehicleId());

			vehicleVO.setOutlet(vehicleVOTemp.getOutlet());
			vehicleVO.setOutletId(vehicleVOTemp.getOutlet().getOutletId());
			vehicleVO.setPoliceNo(vehicleVOTemp.getPoliceNo());
			vehicleVO.setVehicleType(vehicleVOTemp.getVehicleType());
			vehicleVO.setVehicleDesc(vehicleVOTemp.getVehicleDesc());
			vehicleVO.setOccupyFlag(vehicleVOTemp.getOccupyFlag());
			vehicleVO.setActiveFlag(vehicleVOTemp.getActiveFlag());
			vehicleVO.setUpdateBy(userSession.getUserCode());
			vehicleVO.setUpdateOn(new Timestamp(System.currentTimeMillis()));

		}

	}

	public void changeCompanyToOutlet() {
		if (vehicleVO.getCompanyId() != null) {
			List<Outlet> outletList = outletService.findOutletByCompany(vehicleVO.getCompanyId());
			outletSelectItem = new ArrayList<SelectItem>();
			if (outletList.size() > 0) {
				for (Outlet out : outletList) {
					outletSelectItem.add(new SelectItem(out.getOutletId(), out.getOutletName()));
				}
			}

		}
	}

	public void clearAll() {
		vehicleVO = new VehicleVO();

	}

	public VehicleService getVehicleService() {
		return vehicleService;
	}

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public VehicleVO getVehicleVO() {
		return vehicleVO;
	}

	public void setVehicleVO(VehicleVO vehicleVO) {
		this.vehicleVO = vehicleVO;
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

	public List<SelectItem> getCitySelectItem() {
		return citySelectItem;
	}

	public void setCitySelectItem(List<SelectItem> citySelectItem) {
		this.citySelectItem = citySelectItem;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getGenderSelectItem() {
		return genderSelectItem;
	}

	public void setGenderSelectItem(List<SelectItem> genderSelectItem) {
		this.genderSelectItem = genderSelectItem;
	}

	public List<SelectItem> getReligionSelectItem() {
		return religionSelectItem;
	}

	public void setReligionSelectItem(List<SelectItem> religionSelectItem) {
		this.religionSelectItem = religionSelectItem;
	}

	public List<SelectItem> getMaritalStatSelectItem() {
		return maritalStatSelectItem;
	}

	public void setMaritalStatSelectItem(List<SelectItem> maritalStatSelectItem) {
		this.maritalStatSelectItem = maritalStatSelectItem;
	}

	public List<SelectItem> getVehicleStatSelectItem() {
		return vehicleStatSelectItem;
	}

	public void setVehicleStatSelectItem(List<SelectItem> vehicleStatSelectItem) {
		this.vehicleStatSelectItem = vehicleStatSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}

	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		VehicleInputBean.logger = logger;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public OutletEmpService getOutletEmpService() {
		return outletEmpService;
	}

	public void setOutletEmpService(OutletEmpService outletEmpService) {
		this.outletEmpService = outletEmpService;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public MbImageStreamer getMbImageStreamer() {
		return mbImageStreamer;
	}

	public void setMbImageStreamer(MbImageStreamer mbImageStreamer) {
		this.mbImageStreamer = mbImageStreamer;
	}

	public String getStreamUploadId() {
		return streamUploadId;
	}

	public void setStreamUploadId(String streamUploadId) {
		this.streamUploadId = streamUploadId;
	}

	public static String getUploadFunc() {
		return UPLOAD_FUNC;
	}

	public String getUploadFuncId() {
		return UPLOAD_FUNC;
	}

	public ProvinceService getProvinceService() {
		return provinceService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public List<SelectItem> getProvinceSelectItem() {
		return provinceSelectItem;
	}

	public void setProvinceSelectItem(List<SelectItem> provinceSelectItem) {
		this.provinceSelectItem = provinceSelectItem;
	}

	public List<SelectItem> getVehicleTypeSelectItem() {
		return vehicleTypeSelectItem;
	}

	public void setVehicleTypeSelectItem(List<SelectItem> vehicleTypeSelectItem) {
		this.vehicleTypeSelectItem = vehicleTypeSelectItem;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

}
