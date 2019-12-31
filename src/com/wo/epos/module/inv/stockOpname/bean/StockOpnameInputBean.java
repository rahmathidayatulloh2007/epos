package com.wo.epos.module.inv.stockOpname.bean;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.wo.epos.module.inv.stockOpname.service.StockOpnameService;
import com.wo.epos.module.inv.stockOpname.vo.StockOpnameVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "stockOpnameInputBean")
@ViewScoped
public class StockOpnameInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 3246686603028391512L;

	static Logger logger = Logger.getLogger(StockOpnameInputBean.class);

	@ManagedProperty(value = "#{stockOpnameService}")
	private StockOpnameService stockOpnameService;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	@ManagedProperty(value = "#{outletEmpService}")
	private OutletEmpService outletEmpService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	private StockOpnameVO stockOpnameVO = new StockOpnameVO();
	// private OutletEmpVO outletEmpVO = new OutletEmpVO();

	private boolean disableFlag;
	private boolean disableFlagAdd;
	// private boolean picFlagChecked;

	private String searchAll;

	private List<SelectItem> opnameStatusSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> bulanSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> tahunSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;

	private String userPosition;

	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			stockOpnameVO = new StockOpnameVO();

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}

			List<ParameterDtl> stockOpnameSelectList = parameterService.parameterDtlList("STOCKOPNAME_STATUS");
			opnameStatusSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl dtl : stockOpnameSelectList) {
				opnameStatusSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
			}

			String[] months = new DateFormatSymbols().getMonths();
			for (int i = 0; i < months.length; i++) {
				bulanSelectItem.add(new SelectItem(String.valueOf(i).length() < 2 ? "0" + i : i, months[i]));
			}

			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			tahunSelectItem.add(new SelectItem(currentYear - 1, String.valueOf(currentYear - 1)));
			tahunSelectItem.add(new SelectItem(currentYear, String.valueOf(currentYear)));

			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;

			userPosition = getUserLevel();

			if (userPosition.equals(CommonConstants.COMPANY_LEVEL)) {

				List<Outlet> outletList = outletService.findOutletByCompany(userSession.getCompanyId());
				outletSelectItem = new ArrayList<SelectItem>();
				for (Outlet dtl : outletList) {
					outletSelectItem.add(new SelectItem(dtl.getOutletId(), dtl.getOutletName()));
				}
			} else if (userPosition.equals(CommonConstants.ADMIN_LEVEL)) {
				List<OutletVO> outletList = outletService.searchOutletList();
				outletSelectItem = new ArrayList<SelectItem>();
				for (OutletVO dtl : outletList) {
					outletSelectItem.add(new SelectItem(dtl.getOutletId(), dtl.getOutletName()));
				}
			}
		}
	}

	
	public boolean validateCompany() {
		boolean valid = true;
		if (userPosition.equals(CommonConstants.ADMIN_LEVEL)) {
			if (stockOpnameVO.getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesStockOpname",
						facesUtils.getResourceBundleStringValue("formStockOpnameCompany") + " "
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
/*	public boolean validateOutlet() {
		boolean valid = true;
		if (userPosition.equals(CommonConstants.ADMIN_LEVEL)|| userPosition.equals(CommonConstants.COMPANY_LEVEL) ) {
			if (stockOpnameVO.getOutletId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesStockOpname",
						facesUtils.getResourceBundleStringValue("formVehicleOutlet") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else {
				valid = true;
			}
		} else {
			valid = true;
		}
		
		return valid;
	}*/
	
	public boolean validate() {
		
		boolean valid = true;
		if (stockOpnameVO.getOpnameDate() == null) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesStockOpname",
					facesUtils.getResourceBundleStringValue("formStockOpnameTanggalPerhitungan") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		} if (stockOpnameVO.getPeriodBulan() == null || stockOpnameVO.getPeriodBulan().trim().isEmpty()) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesStockOpname",
					facesUtils.getResourceBundleStringValue("formStockOpnamePeriodePerhitungan") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		} if (stockOpnameVO.getPeriodTahun() == null || stockOpnameVO.getPeriodTahun().trim().isEmpty()) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesStockOpname",
					facesUtils.getResourceBundleStringValue("formStockOpnamePeriodePerhitungan") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		}

		return valid;

	}

	public void save() {
		try 
		{
			if (MODE_TYPE.equals("ADD")) 
			{
				stockOpnameService.save(stockOpnameVO,
						userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			} 
			else if (MODE_TYPE.equals("EDIT")) 
			{
				stockOpnameService.update(stockOpnameVO,
						userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);
		}

	}

	public void modeAdd() {
		this.stockOpnameVO = new StockOpnameVO();
		Outlet outlet = new Outlet();
		Company company = new Company();
		stockOpnameVO.setOutlet(outlet);
		stockOpnameVO.setCompany(company);
	}

	public void modeEdit(List<StockOpnameVO> stockOpnameVOList) {
		stockOpnameVO = new StockOpnameVO();
		MODE_TYPE = "EDIT";
		for (int i = 0; i < stockOpnameVOList.size(); i++) {
			stockOpnameVO = (StockOpnameVO) stockOpnameVOList.get(i);
		}

	}

	public void changeCompanyToOutlet() {
		if (stockOpnameVO.getCompanyId() != null) {
			List<Outlet> outletList = outletService
					.findOutletByCompany(stockOpnameVO.getCompanyId());
			outletSelectItem = new ArrayList<SelectItem>();
			if (outletList.size() > 0) {
				for (Outlet out : outletList) {
					outletSelectItem.add(new SelectItem(out.getOutletId(), out
							.getOutletName()));
				}
			}

		}
	}

	public void clearAll() {
		stockOpnameVO = new StockOpnameVO();

	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public StockOpnameVO getStockOpnameVO() {
		return stockOpnameVO;
	}

	public void setStockOpnameVO(StockOpnameVO stockOpnameVO) {
		this.stockOpnameVO = stockOpnameVO;
	}

	public List<SelectItem> getOpnameStatusSelectItem() {
		return opnameStatusSelectItem;
	}

	public void setOpnameStatusSelectItem(
			List<SelectItem> opnameStatusSelectItem) {
		this.opnameStatusSelectItem = opnameStatusSelectItem;
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
		StockOpnameInputBean.logger = logger;
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

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public List<SelectItem> getVehicleTypeSelectItem() {
		return opnameStatusSelectItem;
	}

	public void setVehicleTypeSelectItem(List<SelectItem> vehicleTypeSelectItem) {
		this.opnameStatusSelectItem = vehicleTypeSelectItem;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public StockOpnameService getStockOpnameService() {
		return stockOpnameService;
	}

	public void setStockOpnameService(StockOpnameService stockOpnameService) {
		this.stockOpnameService = stockOpnameService;
	}

	public List<SelectItem> getBulanSelectItem() {
		return bulanSelectItem;
	}

	public void setBulanSelectItem(List<SelectItem> bulanSelectItem) {
		this.bulanSelectItem = bulanSelectItem;
	}

	public List<SelectItem> getTahunSelectItem() {
		return tahunSelectItem;
	}

	public void setTahunSelectItem(List<SelectItem> tahunSelectItem) {
		this.tahunSelectItem = tahunSelectItem;
	}

}
