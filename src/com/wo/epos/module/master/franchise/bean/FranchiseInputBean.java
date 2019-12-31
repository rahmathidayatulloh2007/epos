package com.wo.epos.module.master.franchise.bean;

import java.io.IOException;
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

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.vo.UploadFileVO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.franchise.service.FranchiseService;
import com.wo.epos.module.master.franchise.vo.FranchiseVO;
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;

@ManagedBean(name = "franchiseInputBean")
@ViewScoped
public class FranchiseInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -1976327850226168845L;
	static Logger logger = Logger.getLogger(FranchiseInputBean.class);

	@ManagedProperty(value = "#{franchiseService}")
	private FranchiseService franchiseService;

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

	private FranchiseVO franchiseVO = new FranchiseVO();
	// private OutletEmpVO outletEmpVO = new OutletEmpVO();

	private boolean disableFlag;
	private boolean disableFlagAdd;
	// private boolean picFlagChecked;

	private String searchAll;

	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> genderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> religionSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> maritalStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> franchiseStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;

	private String streamUploadId;

	private UploadedFile uploadedFile;

	private static final String UPLOAD_FUNC = "FranchiseEDIT";

	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			franchiseVO = new FranchiseVO();
			Province province = new Province();
			City city = new City();
			city.setProvince(province);
			franchiseVO.setCity(city);

			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));

			citySelectItem = new ArrayList<SelectItem>();
			List<CityVO> citySelectList = franchiseService.searchCityAll();
			for (CityVO vo : citySelectList) {
				citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
			}

			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;
		}
	}

	public boolean validate() {
		boolean valid = true;
		

		if (franchiseVO.getFranchiseCode() == null || franchiseVO.getFranchiseCode().isEmpty()) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formFranchiseCode") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;

		}

		if (franchiseVO.getFranchiseName() == null || franchiseVO.getFranchiseName().isEmpty()) {
			
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formFranchiseName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;

		}

		if (franchiseVO.getPicName() == null || franchiseVO.getPicName().isEmpty()) {
			
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formFranchisePICName") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;

		}

		if (franchiseVO.getPicPhoneNo() == null || franchiseVO.getPicPhoneNo().isEmpty()) {
			
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formFranchisePICPhoneNo") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;

		}

		/*
		 * if (franchiseVO.getLogoFile() == null) {
		 * 
		 * addFacesMsg( FacesMessage.SEVERITY_ERROR, "frm001:upload", facesUtils
		 * .getResourceBundleStringValue("formFranchiseLogo") + " " + facesUtils
		 * .getResourceBundleStringValue("errorMustBeFilled"),null);
		 * 
		 * valid = false;
		 * 
		 * }
		 */

		if (franchiseVO.getAddress() == null || franchiseVO.getAddress().isEmpty()) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formFranchiseAddress") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			
			valid = false;

		}

		if (franchiseVO.getCityId() == null) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formFranchiseCity") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;

		}

		/*
		 * if (franchiseVO.getPostalCode() == null ||
		 * franchiseVO.getPostalCode().isEmpty()) {
		 * 
		 * addFacesMsg( FacesMessage.SEVERITY_ERROR, "frm001:postalCode",
		 * facesUtils .getResourceBundleStringValue("formFranchisePostalCode") +
		 * " " + facesUtils
		 * .getResourceBundleStringValue("errorMustBeFilled"),null);
		 * 
		 * valid = false;
		 * 
		 * }
		 */

		return valid;

	}

	public void onChangeCity() {
		City city = (City) cityService.findById(franchiseVO.getCityId());
		if (city != null) {
			franchiseVO.setProvinceName(city.getProvince().getProvinceName());
		}
	}

	public void addFacesMsg(FacesMessage.Severity severity, String forComp, String msg, String detail) {
		FacesMessage message = new FacesMessage(severity, msg, detail);
		FacesContext.getCurrentInstance().addMessage(forComp, message);

	}

	public void save() {
		try {
			if (MODE_TYPE.equals("ADD")) {
				franchiseService.save(franchiseVO, userSession.getUserCode());

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
						null);
			} else if (MODE_TYPE.equals("EDIT")) {
				franchiseService.update(franchiseVO, userSession.getUserCode());

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
		this.franchiseVO = new FranchiseVO();
		Province province = new Province();
		City city = new City();
		city.setProvince(province);
		this.franchiseVO.setCity(city);
		System.out.println("city==" + city);
	}

	public void modeEdit(List<FranchiseVO> franchiseList) {
		MODE_TYPE = "EDIT";
		for (int i = 0; i < franchiseList.size(); i++) {
			FranchiseVO franchiseVOTemp = (FranchiseVO) franchiseList.get(i);

			franchiseVO.setFranchiseId(franchiseVOTemp.getFranchiseId());
			franchiseVO.setFranchiseCode(franchiseVOTemp.getFranchiseCode());
			franchiseVO.setFranchiseName(franchiseVOTemp.getFranchiseName());
			franchiseVO.setCity(franchiseVOTemp.getCity());
			franchiseVO.setCityId(franchiseVOTemp.getCity().getCityId());
			franchiseVO.setProvinceName(franchiseVOTemp.getCity().getProvince().getProvinceName());
			franchiseVO.setAddress(franchiseVOTemp.getAddress());
			franchiseVO.setGroupId(franchiseVOTemp.getGroupId());
			franchiseVO.setLogoFile(franchiseVOTemp.getLogoFile());
			franchiseVO.setPicName(franchiseVOTemp.getPicName());
			franchiseVO.setPicPhoneNo(franchiseVOTemp.getPicPhoneNo());
			franchiseVO.setPostalCode(franchiseVOTemp.getPostalCode());
			franchiseVO.setActiveFlag(franchiseVOTemp.getLogoFileName());
			franchiseVO.setCreateBy(userSession.getUserCode());
			franchiseVO.setCreateOn(new Timestamp(System.currentTimeMillis()));

			if (franchiseVOTemp != null && franchiseVOTemp.getLogoFile() != null) {
				String uploadId = mbImageStreamer.returnCurrentTime();
				this.streamUploadId = uploadId;
				mbImageStreamer.clearUpload(getUploadFuncId());
				UploadFileVO file = new UploadFileVO(uploadId, franchiseVOTemp.getLogoFile());
				mbImageStreamer.addUpload(getUploadFuncId(), file);
				franchiseVO.setLogoFile(franchiseVOTemp.getLogoFile());
			} else {
				try {
					this.streamUploadId = null;
					// mbImageStreamer.clearUpload(getUploadFuncId());
				} catch (Exception e) {

				}
			}
		}

	}

	public void clearAll() {
		franchiseVO = new FranchiseVO();
		/*
		 * outletEmpVO = new OutletEmpVO(); picFlagChecked = false;
		 */
	}

	public void handleUploadedFile() throws IOException {

		// System.out.println("size=="+uploadedFile.getSize());
		// System.out.println("fileName=="+uploadedFile.getContentType());
		/*
		 * int maxFileSize= 100 * 1024; if(uploadedFile!=null &&
		 * uploadedFile.getSize() > maxFileSize){ facesUtils.addFacesMsg(
		 * FacesMessage.SEVERITY_ERROR, "frm001:upload",
		 * "Maximum File Size 100 kb", null); }else{
		 */
		if (uploadedFile != null && uploadedFile.getContentType().equals("image/jpeg")) {
			this.franchiseVO.setLogoFile(IOUtils.toByteArray(uploadedFile.getInputstream()));
			this.franchiseVO.setLogoFileName(uploadedFile.getFileName());

			String uploadId = mbImageStreamer.returnCurrentTime();
			this.streamUploadId = uploadId;

			mbImageStreamer.clearUpload(getUploadFuncId());
			mbImageStreamer.addUpload(getUploadFuncId(), uploadId, uploadedFile);
		} else {
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:upload", "Invalid File Type", null);
		}
		// }

	}

	public FranchiseService getFranchiseService() {
		return franchiseService;
	}

	public void setFranchiseService(FranchiseService franchiseService) {
		this.franchiseService = franchiseService;
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

	public FranchiseVO getFranchiseVO() {
		return franchiseVO;
	}

	public void setFranchiseVO(FranchiseVO franchiseVO) {
		this.franchiseVO = franchiseVO;
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

	public List<SelectItem> getFranchiseStatSelectItem() {
		return franchiseStatSelectItem;
	}

	public void setFranchiseStatSelectItem(List<SelectItem> franchiseStatSelectItem) {
		this.franchiseStatSelectItem = franchiseStatSelectItem;
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
		FranchiseInputBean.logger = logger;
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

}
