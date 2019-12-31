package com.wo.epos.module.uam.employee.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.UploadFileVO;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.employee.service.EmployeeService;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "employeeInputBean")
@ViewScoped
public class EmployeeInputBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -1976327850226168845L;
	static Logger logger = Logger.getLogger(EmployeeInputBean.class);
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService employeeService;
	
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
	
	private EmployeeVO employeeVO = new EmployeeVO();
	//private OutletEmpVO outletEmpVO = new OutletEmpVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean adminMode;
	//private boolean picFlagChecked;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> genderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> religionSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> maritalStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> employeeStatSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
    private String streamUploadId;
    
    private UploadedFile uploadedFile;
    
    private static final String UPLOAD_FUNC = "EmployeeEDIT";
    
    @PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			/*
			 * HttpServletRequest request = (HttpServletRequest) FacesContext
			 * .getCurrentInstance().getExternalContext().getRequest();
			 * 
			 * HttpSession session = request.getSession(true);
			 * 
			 * UserVO userVO = (UserVO) session
			 * .getAttribute(CommonConstants.SESSION_USER);
			 * 
			 * if (userVO != null) { companyId = userVO.getCompanyId(); outletId
			 * = userVO.getOutletId(); }
			 */

			employeeVO = new EmployeeVO();

			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));

			List<CompanyVO> companyUserList = employeeService.searchCompanyList();
			for (int i = 0; i < companyUserList.size(); i++)
				if (userSession.getCompanyId() != null) {
					if (userSession.getCompanyId().equals(companyUserList.get(i).getCompanyId())) {
						adminMode = false;
						break;
					}
				} else {
					adminMode = true;
				}

			citySelectItem = new ArrayList<SelectItem>();
			List<CityVO> citySelectList = employeeService.searchCityAll();
			for (CityVO vo : citySelectList) {
				citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
			}

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = employeeService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}

			List<ParameterDtl> genderSelectList = employeeService.parameterDtlList(CommonConstants.GENDER);
			genderSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : genderSelectList) {
				genderSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			List<ParameterDtl> religionSelectList = employeeService.parameterDtlList(CommonConstants.RELIGION);
			religionSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : religionSelectList) {
				religionSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			List<ParameterDtl> maritalStatSelectList = employeeService.parameterDtlList(CommonConstants.MARITAL_STATUS);
			maritalStatSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : maritalStatSelectList) {
				maritalStatSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			List<ParameterDtl> employeeStatSelectList = employeeService
					.parameterDtlList(CommonConstants.EMPLOYEE_STATUS);
			employeeStatSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : employeeStatSelectList) {
				employeeStatSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			List<OutletVO> outletSelectList = outletService.searchOutletList();
			outletSelectItem = new ArrayList<SelectItem>();
			for (OutletVO outEmp : outletSelectList) {
				if (adminMode == false) {
					if (outEmp.getCompanyId().equals(userSession.getCompanyId())) {
						outletSelectItem.add(new SelectItem(outEmp.getOutletId(), outEmp.getOutletName()));
					}
				} else {
					outletSelectItem.add(new SelectItem(outEmp.getOutletId(), outEmp.getOutletName()));
				}
			}

			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;
		}
	}
    
    public boolean validateCompany() {
		boolean valid = true;
		if (userSession.getCompanyId() == null) {
			if (employeeVO.getCompanyId() == null && MODE_TYPE == "ADD") {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formProductCompany") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else {
				valid = true;
			}
		} else {
			valid = true;
		}
		
		return valid;
	}
    
    
    
    public boolean validateEmployee() {
		boolean valid = true;
		
		
			if (employeeVO.getOutletId() == null && MODE_TYPE == "ADD") {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeOutlet") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}  if (employeeVO.getEmployeeNo() == null || employeeVO.getEmployeeNo().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeNo") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}  if (employeeVO.getFullName() == null || employeeVO.getFullName().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeFullName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getBirthPlace() == null || employeeVO.getBirthPlace().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeBirthPlace") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getBirthDate() == null ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeBirthDate") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getGenderCode()== null || employeeVO.getGenderCode().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeGender") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getReligionCode()== null || employeeVO.getReligionCode().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeReligion") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getMaritalStatusCode()== null || employeeVO.getMaritalStatusCode().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeMaritalStatus") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getEmployeeStatusCode() == null || employeeVO.getEmployeeStatusCode().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeStatus") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getJoinDt() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeJoinDt") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getAddress() == null || employeeVO.getAddress().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeAddress") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getCityId() == null ) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeCity") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getPostalCode() == null || employeeVO.getPostalCode().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeePostalCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getHpNo()== null || employeeVO.getHpNo().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeHpNo") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (employeeVO.getWorkEmail() == null || employeeVO.getWorkEmail().trim().isEmpty()) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesEmployee",
						facesUtils.getResourceBundleStringValue("formEmployeeWorkEmail") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}
		
		return valid;
	}
    
    
    
	public boolean validate() {
		boolean valid = true;

		/*if (userSession.getCompanyId() != null) {
			employeeVO.setCompanyId(userSession.getCompanyId());
		}*/

		// if (employeeVO.getCompanyId() == null) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:companyId",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeCompany")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }

		/*if (employeeVO.getEmployeeNo() == null
				|| employeeVO.getEmployeeNo().isEmpty()) {

			addFacesMsg(
							FacesMessage.SEVERITY_ERROR,
							"frm001:employeeNo",
							facesUtils
									.getResourceBundleStringValue("formEmployeeNo")
									+ " "
									+ facesUtils
											.getResourceBundleStringValue("errorMustBeFilled"),null);

			valid = false;

		}
		
		if (employeeVO.getFullName() == null
				|| employeeVO.getFullName().isEmpty()) {

			addFacesMsg(
							FacesMessage.SEVERITY_ERROR,
							"frm001:fullName",
							facesUtils
									.getResourceBundleStringValue("formEmployeeFullName")
									+ " "
									+ facesUtils
											.getResourceBundleStringValue("errorMustBeFilled"),null);

			valid = false;

		}*/
		
		// if (employeeVO.getBirthPlace() == null
		// || employeeVO.getBirthPlace().isEmpty()) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:birthPlace",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeBirthPlace")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }
		 int year = Calendar.getInstance().get(Calendar.YEAR);
		 int month = Calendar.getInstance().get(Calendar.MONTH);
		 int day = Calendar.getInstance().get(Calendar.DATE);
		 Date maxBirthDate = new GregorianCalendar(year+10, month, day).getTime();
		 
		 DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		 String dateString = sdf.format(maxBirthDate);
		 
		 if (employeeVO.getBirthDate()!=null && employeeVO.getBirthDate().after(maxBirthDate)) {
			 addFacesMsg(
			 FacesMessage.SEVERITY_ERROR,
			 "frm001:birthDate",
			 facesUtils.getResourceBundleStringValue("formEmployeeBirthDate")
			 + " "
			 + facesUtils.getResourceBundleStringValue("errorDateRange")
			 + " "
			 + dateString,null);
		
			 valid = false;
		
		 }
		
		// if (employeeVO.getGenderCode() == null
		// || employeeVO.getGenderCode().isEmpty()) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:gender",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeGender")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }
		//
		// if (employeeVO.getReligionCode() == null
		// || employeeVO.getReligionCode().isEmpty()) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:religion",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeReligion")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }
		//
		// if (employeeVO.getMaritalStatusCode() == null
		// || employeeVO.getMaritalStatusCode().isEmpty()) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:maritalStatus",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeMaritalStatus")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }
		//
		// if (employeeVO.getAddress() == null
		// || employeeVO.getAddress().isEmpty()) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:address",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeAddress")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }
		//
		// if (employeeVO.getCityId() == null) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:cityId",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeCity")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }
		 Date dt = new Date();
		 dateString = sdf.format(dt);
		 if (employeeVO.getJoinDt()!=null && employeeVO.getJoinDt().after(dt)) {
		
			 addFacesMsg(
					 FacesMessage.SEVERITY_ERROR,
					 "frm001:joinDt",
					 facesUtils.getResourceBundleStringValue("formEmployeeJoinDt")
					 + " "
					 + facesUtils.getResourceBundleStringValue("errorDateRange")
					 + " "
					 + dateString,null);
			
			 valid = false;
		 }
		
		// if (employeeVO.getEmployeeStatusCode() == null ||
		// employeeVO.getEmployeeStatusCode().isEmpty()) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:employeeStatus",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeStatus")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }
		//
		// if (employeeVO.getEmployeeStatusCode() == null ||
		// employeeVO.getEmployeeStatusCode().isEmpty()) {
		//
		// addFacesMsg(
		// FacesMessage.SEVERITY_ERROR,
		// "frm001:employeeStatus",
		// facesUtils
		// .getResourceBundleStringValue("formEmployeeStatus")
		// + " "
		// + facesUtils
		// .getResourceBundleStringValue("errorMustBeFilled"),null);
		//
		// valid = false;
		//
		// }

		return valid;

	}
	
	public void onChangeCompany(){
		List<Outlet> transferFromSelectList = outletService.findOutletByCompany(employeeVO.getCompanyId());
		outletSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : transferFromSelectList){
	    	outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    }
		
	}
	
	public void addFacesMsg(
            FacesMessage.Severity severity, String forComp, String msg, String detail) {
        FacesMessage message = new FacesMessage(
                                        severity,
                                        msg,
                                        detail);
        FacesContext.getCurrentInstance().addMessage(forComp, message);
        
    }
    
    public void save(){
    	try 
		{
	    	
				if(MODE_TYPE.equals("ADD"))
				{
				    employeeService.save(employeeVO, userSession.getUserCode());
				    
				    facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:growlMessage", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
			                null);
				}
				else if(MODE_TYPE.equals("EDIT"))
				{
					employeeService.update(employeeVO, userSession.getUserCode());
					
					facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:growlMessage", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
			                null);
				}
	    	
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:growlMessage",
					"Operation Failed : " + ex.getMessage(),
					null);	    
		}	
	}
	
	public void modeAdd(){
		employeeVO = new EmployeeVO();

	}
	
	public void modeEdit(List<EmployeeVO> employeeList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<employeeList.size(); i++){
			EmployeeVO employeeVOTemp = (EmployeeVO)employeeList.get(i);
			OutletEmp outletEmp = outletEmpService.findOutletEmpByEmployeeId(employeeVOTemp.getEmployeeId());
			
			
			if(outletEmp!=null && outletEmp.getOutletId()!=null){
		    employeeVO.setOutletId(outletEmp.getOutletId());
			employeeVO.setOutletName(outletService.findById(outletEmp.getOutletId()).getOutletName());	
			}
			if(outletEmp!=null && outletEmp.getPicFlag() != null && outletEmp.getPicFlag().equals("Y")){
				employeeVO.setPicFlagChecked(true);
			}
			employeeVO.setEmployeeId(employeeVOTemp.getEmployeeId());
			employeeVO.setEmployeeNo(employeeVOTemp.getEmployeeNo());
			employeeVO.setCompanyId(employeeVOTemp.getCompanyId());
			employeeVO.setCompanyName(employeeVOTemp.getCompanyName());
			employeeVO.setCityId(employeeVOTemp.getCityId());
			employeeVO.setCityName(employeeVOTemp.getCityName());
			employeeVO.setFullName(employeeVOTemp.getFullName());
			employeeVO.setBirthPlace(employeeVOTemp.getBirthPlace());
			employeeVO.setAddress(employeeVOTemp.getAddress());
			employeeVO.setPostalCode(employeeVOTemp.getPostalCode());
			employeeVO.setHpNo(employeeVOTemp.getHpNo());
			employeeVO.setWorkEmail(employeeVOTemp.getWorkEmail());		
			employeeVO.setProfileImgname(employeeVOTemp.getProfileImgname());		
			employeeVO.setGenderCode(employeeVOTemp.getGenderCode());
			employeeVO.setGenderName(employeeVOTemp.getGenderName());
			employeeVO.setReligionCode(employeeVOTemp.getReligionCode());
			employeeVO.setReligionName(employeeVOTemp.getReligionName());
			employeeVO.setMaritalStatusCode(employeeVOTemp.getMaritalStatusCode());
			employeeVO.setMaritalStatusName(employeeVOTemp.getMaritalStatusName());
			employeeVO.setEmployeeStatusCode(employeeVOTemp.getEmployeeStatusCode());
			employeeVO.setEmployeeStatusName(employeeVOTemp.getEmployeeStatusName());
			employeeVO.setBirthDate(employeeVOTemp.getBirthDate());
			employeeVO.setJoinDt(employeeVOTemp.getJoinDt());
			employeeVO.setActiveFlag(employeeVOTemp.getActiveFlag());	
			employeeVO.setCreateBy(userSession.getUserCode());
			employeeVO.setCreateOn(new Timestamp(System.currentTimeMillis()));
			
			if(employeeVOTemp!=null && employeeVOTemp.getProfileImg() != null){
				String uploadId = mbImageStreamer.returnCurrentTime();
				this.streamUploadId = uploadId;
				mbImageStreamer.clearUpload(getUploadFuncId());
				UploadFileVO file = new UploadFileVO(uploadId,employeeVOTemp.getProfileImg());
				mbImageStreamer.addUpload(getUploadFuncId(), file);
				employeeVO.setProfileImg(employeeVOTemp.getProfileImg());
			}else{
				try{
					this.streamUploadId = null;
//					mbImageStreamer.clearUpload(getUploadFuncId());
				}catch(Exception e){
					
				}
			}
		}
		
	}
	
	public void clearAll(){
		employeeVO = new EmployeeVO();
		this.streamUploadId = null;
		/*outletEmpVO = new OutletEmpVO();
		picFlagChecked = false;*/
	}
	
	public void handleUploadedFile() throws IOException {
		
		//System.out.println("size=="+uploadedFile.getSize());
		//System.out.println("fileName=="+uploadedFile.getContentType());
		/*int maxFileSize= 100 * 1024;
		if(uploadedFile!=null && uploadedFile.getSize() > maxFileSize){
			facesUtils.addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:upload", 
	                "Maximum File Size 100 kb", 
	                null);
		}else{*/
			if(uploadedFile!=null && uploadedFile.getContentType().equals("image/jpeg") ){
				this.employeeVO.setProfileImg(IOUtils.toByteArray(uploadedFile.getInputstream()));
				this.employeeVO.setProfileImgname(uploadedFile.getFileName());
				
				String uploadId = mbImageStreamer.returnCurrentTime();
				this.streamUploadId = uploadId;
				
				mbImageStreamer.clearUpload(getUploadFuncId());
				mbImageStreamer.addUpload(getUploadFuncId(), uploadId, uploadedFile);
			}else{
				facesUtils.addFacesMsg(
		                FacesMessage.SEVERITY_ERROR, 
		                "frm001:upload", 
		                "Invalid File Type", 
		                null);
			}
		//}
		
		
	}
	
	public void handleFileUpload(FileUploadEvent event) throws IOException {

	    UploadedFile file = event.getFile();
	    
	    //byte[] foto = IOUtils.toByteArray(file.getInputstream());
	    
	    if(file!=null && file.getContentType().equals("image/jpeg") ){
			this.employeeVO.setProfileImg(IOUtils.toByteArray(file.getInputstream()));
			
			String uploadId = mbImageStreamer.returnCurrentTime();
			this.streamUploadId = uploadId;
			
			mbImageStreamer.clearUpload(getUploadFuncId());
			mbImageStreamer.addUpload(getUploadFuncId(), uploadId, uploadedFile);
		}else{
			facesUtils.addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:upload", 
	                "Invalid File Type", 
	                null);
		}
	    
	 //application code
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
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

	public EmployeeVO getEmployeeVO() {
		return employeeVO;
	}

	public void setEmployeeVO(EmployeeVO employeeVO) {
		this.employeeVO = employeeVO;
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

	public List<SelectItem> getEmployeeStatSelectItem() {
		return employeeStatSelectItem;
	}

	public void setEmployeeStatSelectItem(List<SelectItem> employeeStatSelectItem) {
		this.employeeStatSelectItem = employeeStatSelectItem;
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
		EmployeeInputBean.logger = logger;
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

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}	
	
}
