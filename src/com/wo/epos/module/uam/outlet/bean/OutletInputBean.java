package com.wo.epos.module.uam.outlet.bean;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "outletInputBean")
@ViewScoped
public class OutletInputBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = 4740244802528600L;
	
	static Logger logger = Logger.getLogger(OutletInputBean.class);
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
		
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{outletEmpService}")
	private OutletEmpService outletEmpService;
	
	private OutletVO outletVO = new OutletVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> picNameSelectItem = new ArrayList<SelectItem>();
	
	private String MODE_TYPE;
	
	private Long companyId;
	
	
	@PostConstruct
	public void postConstruct() {
		super.init();
		
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			HttpSession session = request.getSession(true);

			UserVO userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);

			if (userVO != null) {
				companyId = userVO.getCompanyId();
			}
			outletVO = new OutletVO();

			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));

			citySelectItem = new ArrayList<SelectItem>();
			List<CityVO> citySelectList = outletService.searchCityAll();
			for (CityVO vo : citySelectList) {
				citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
			}

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = outletService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}

			picNameSelectItem = new ArrayList<SelectItem>();

			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;
		
	}
	
	public boolean validateOutlet(){
		boolean valid = true;
		
		try {
			if (companyId == null && outletVO.getCompanyId() == null  ){

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesOutlet",
						facesUtils.getResourceBundleStringValue("formOutletCompany") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (outletVO.getOutletCode() == null ||  outletVO.getOutletCode().trim().isEmpty()
					&& MODE_TYPE == "ADD") {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesOutlet",
						facesUtils.getResourceBundleStringValue("formOutletCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (outletVO.getOutletName().trim().isEmpty() || outletVO.getOutletName() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesOutlet",
						facesUtils.getResourceBundleStringValue("formOutletName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (outletVO.getAddress() == null || outletVO.getAddress().trim().isEmpty()  ) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesOutlet",
						facesUtils.getResourceBundleStringValue("formOutletAddress") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}if (outletVO.getCityId() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesOutlet",
						facesUtils.getResourceBundleStringValue("formOutletCity") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}
		} catch (NumberFormatException ex) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesOutlet",
					facesUtils.getResourceBundleStringValue("errorMustBeNumber"));
			valid = false;
		}
		
		
		return valid;

	}
	
	
	
	public void save(){
		try 
		{
			if(MODE_TYPE.equals("ADD"))
			{
			    outletService.save(outletVO, userSession.getUserCode());
			    
			    facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				outletService.update(outletVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
			}
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
	
	public void modeAdd(){
		outletVO = new OutletVO();
		if(companyId !=null){
			outletVO.setCompanyId(companyId);
		}
	}
	
	public void modeEdit(List<OutletVO> outletList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<outletList.size(); i++){
			  OutletVO companyVOTemp = (OutletVO)outletList.get(i);
			  
			  outletVO.setOutletId(companyVOTemp.getOutletId());
			  outletVO.setOutletCode(companyVOTemp.getOutletCode());
			  outletVO.setOutletName(companyVOTemp.getOutletName());
			  outletVO.setCompanyId(companyVOTemp.getCompanyId());
			  outletVO.setCityId(companyVOTemp.getCityId());
			  outletVO.setProvinceName(companyVOTemp.getProvinceName());
			  outletVO.setAddress(companyVOTemp.getAddress());
			  outletVO.setPostalCode(companyVOTemp.getPostalCode());
			  //outletVO.setPicName(companyVOTemp.getPicName());
			  //outletVO.setPicPhoneno(companyVOTemp.getPicPhoneno());
			  outletVO.setActiveFlag(companyVOTemp.getActiveFlag());
			  outletVO.setCreateBy(userSession.getUserCode());
			  outletVO.setCreateOn(new Timestamp(System.currentTimeMillis()));	
			  
			  picNameSelectItem = new ArrayList<SelectItem>();
			  List<OutletEmpVO> outletEmp = outletEmpService.findOutletEmpByOutletIdList(companyVOTemp.getOutletId());
			  for(OutletEmpVO vo : outletEmp){		
				    picNameSelectItem.add(new SelectItem(vo.getEmployeeId(), vo.getEmployeeName()));
				    if(vo.getPicFlag().equals(CommonConstants.Y)){
				    	  outletVO.setPicEmployeeId(vo.getEmployeeId());
				    }
			  }
			  
		}
		
	}
	
	public void changeCityProvince(){
		City city = new City();
		city = cityService.findById(outletVO.getCityId());
		outletVO.setProvinceName(city.getProvince().getProvinceName());
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}
	

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public OutletVO getOutletVO() {
		return outletVO;
	}

	public void setOutletVO(OutletVO outletVO) {
		this.outletVO = outletVO;
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

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public OutletEmpService getOutletEmpService() {
		return outletEmpService;
	}

	public void setOutletEmpService(OutletEmpService outletEmpService) {
		this.outletEmpService = outletEmpService;
	}

	public List<SelectItem> getPicNameSelectItem() {
		return picNameSelectItem;
	}

	public void setPicNameSelectItem(List<SelectItem> picNameSelectItem) {
		this.picNameSelectItem = picNameSelectItem;
	}

	
	
	

}
