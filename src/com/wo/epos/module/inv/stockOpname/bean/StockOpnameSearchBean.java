package com.wo.epos.module.inv.stockOpname.bean;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.stockOpname.service.StockOpnameService;
import com.wo.epos.module.inv.stockOpname.vo.StockOpnameVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "stockOpnameSearchBean")
@ViewScoped
public class StockOpnameSearchBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 7504791363628659486L;

	static Logger logger = Logger.getLogger(StockOpnameSearchBean.class);

	@ManagedProperty(value = "#{stockOpnameService}")
	private StockOpnameService stockOpnameService;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	private StockOpnameVO stockOpnameVOSearchDialog = new StockOpnameVO();

	private PagingTableModel<StockOpnameVO> pagingStockOpname;

	private List<StockOpnameVO> stockOpnameList = new ArrayList<StockOpnameVO>();
	private List<StockOpnameVO> selectedStockOpname;

	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> opnameStatusSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> bulanSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> tahunSelectItem = new ArrayList<SelectItem>();

	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;

	private String searchAll;

	private Integer checkSearch;

	private String userPosition;

	private StockOpnameVO stockOpnameVO = new StockOpnameVO();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			stockOpnameVOSearchDialog = new StockOpnameVO();

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

			String[] months = new DateFormatSymbols().getShortMonths();
			for (int i = 0; i < months.length; i++) {
				bulanSelectItem
						.add(new SelectItem(String.valueOf(i).length() < 2 ? new String("0" + i) : i, months[i]));
			}

			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			tahunSelectItem.add(new SelectItem(currentYear - 1, String.valueOf(currentYear - 1)));
			tahunSelectItem.add(new SelectItem(currentYear, String.valueOf(currentYear)));

			pagingStockOpname = new PagingTableModel(stockOpnameService, paging);

			userPosition = getUserLevel();

			if (userPosition.equals(CommonConstants.ADMIN_LEVEL)
					|| userPosition.equals(CommonConstants.COMPANY_LEVEL)) {
				List<Outlet> outletList = outletService.findOutletByCompany(userSession.getCompanyId());
				outletSelectItem = new ArrayList<SelectItem>();
				for (Outlet dtl : outletList) {
					outletSelectItem.add(new SelectItem(dtl.getOutletId(), dtl.getOutletName()));
				}
			}

			disableFlag = false;
			disableFlagAdd = true;
			disableSearchAll = false;

			checkSearch = 0;

			search();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search() {
		if (checkSearch == 0) {
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if (userPosition.equals(CommonConstants.OUTLET_LEVEL)) {
				searchCriteria.add(new SearchValueObject("outlet", userSession
						.getOutletId()));
			} else if (userPosition.equals(CommonConstants.COMPANY_LEVEL)) {
				searchCriteria.add(new SearchValueObject("companyId",
						userSession.getCompanyId()));
			}

			if (searchAll != null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria
						.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}

			disableSearchAll = false;
			pagingStockOpname.setSearchCriteria(searchCriteria);
		} else {
			searchDialog();
		}
	}

	public void modeDelete(List<StockOpnameVO> stockOpnameVOList) {
		try
		{
			for (int i = 0; i < stockOpnameVOList.size(); i++) {
				StockOpnameVO stockOpnameVO = (StockOpnameVO) stockOpnameVOList
						.get(i);
	
				if (stockOpnameVO.getStatus().equals(
						CommonConstants.STOCKOPNAME_NEW))
					stockOpnameService.delete(stockOpnameVO.getOpnameId());
			}
			
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
                null);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_ERROR,
				"frm001:messages",
				facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formStockOpnameTitle")),
				null);	  
		}
	}

	public void modeClose(List<StockOpnameVO> stockOpnameVOList) {
		for (int i = 0; i < stockOpnameVOList.size(); i++) {
			stockOpnameVO = (StockOpnameVO) stockOpnameVOList.get(i);
		}
	}

	public void closeOpname() {
		try {
			if (stockOpnameService.closeOpname(stockOpnameVO,
					userSession.getUserCode())) {
				facesUtils
						.addFacesMessage(
								FacesMessage.SEVERITY_ERROR,
								facesUtils
										.getResourceBundleStringValue("formStockOpnameErrorNullOpnameQty"));
			} else {
				search();
			}
		} catch (Exception e) {
			e.printStackTrace();
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error :"
					+ e.getMessage());
		}
	}

	public void clear() {
		searchAll = "";
		stockOpnameVOSearchDialog = new StockOpnameVO();
		checkSearch = 0;
		search();
	}

	public void clearDialog() {
		searchAll = "";
		checkSearch = 0;
		stockOpnameVOSearchDialog = new StockOpnameVO();
		search();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog() {
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		if (userPosition.equals(CommonConstants.OUTLET_LEVEL)) {
			searchCriteria.add(new SearchValueObject("outlet", userSession
					.getOutletId()));
			searchCriteria.add(new SearchValueObject("companyId", userSession
					.getCompanyId()));
		} else if (userPosition.equals(CommonConstants.COMPANY_LEVEL)) {
			searchCriteria.add(new SearchValueObject("companyId", userSession
					.getCompanyId()));

			if (stockOpnameVOSearchDialog.getOutletId() != null) {
				builder.append(facesUtils
						.getResourceBundleStringValue("formEquipmentOutlet"));
				builder.append(":" + stockOpnameVOSearchDialog.getOutletId()
						+ ",");
				searchCriteria.add(new SearchValueObject("outlet",
						stockOpnameVOSearchDialog.getOutletId()));
			}
		} else if (userPosition.equals(CommonConstants.ADMIN_LEVEL)) {
			if (stockOpnameVOSearchDialog.getCompanyId() != null) {
				builder.append(facesUtils
						.getResourceBundleStringValue("formEquipmentCompany"));
				builder.append(":" + stockOpnameVOSearchDialog.getCompanyId()
						+ ",");
				searchCriteria.add(new SearchValueObject("companyId",
						stockOpnameVOSearchDialog.getCompanyId()));
			}

			if (stockOpnameVOSearchDialog.getOutletId() != null) {
				builder.append(facesUtils
						.getResourceBundleStringValue("formEquipmentOutlet"));
				builder.append(":" + stockOpnameVOSearchDialog.getOutletId()
						+ ",");
				searchCriteria.add(new SearchValueObject("outlet",
						stockOpnameVOSearchDialog.getOutletId()));
			}
		}

		if (stockOpnameVOSearchDialog.getOpnameNo() != null
				&& StringUtils.isNotBlank(stockOpnameVOSearchDialog
						.getOpnameNo())) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formStockOpnameNomorPerhitungan"));
			builder.append(":" + stockOpnameVOSearchDialog.getOpnameNo() + ",");
			searchCriteria.add(new SearchValueObject("opnameNo",
					stockOpnameVOSearchDialog.getOpnameNo()));
		}

		if (stockOpnameVOSearchDialog.getOpnameDateAwal() != null) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formStockOpnameTanggalPerhitungan"));
			builder.append(":"
					+ sdf.format(stockOpnameVOSearchDialog.getOpnameDateAwal())
					+ ",");
			searchCriteria.add(new SearchValueObject("opnameDateAwal",
					stockOpnameVOSearchDialog.getOpnameDateAwal()));
		}

		if (stockOpnameVOSearchDialog.getOpnameDateAkhir() != null) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formStockOpnameTanggalPerhitungan"));
			builder.append(":"
					+ sdf.format(stockOpnameVOSearchDialog.getOpnameDateAkhir())
					+ ",");
			searchCriteria.add(new SearchValueObject("opnameDateAkhir",
					stockOpnameVOSearchDialog.getOpnameDateAkhir()));
		}

		if (stockOpnameVOSearchDialog.getPeriodBulan() != null
				&& StringUtils.isNotBlank(stockOpnameVOSearchDialog
						.getPeriodBulan())) {
			String[] months = new DateFormatSymbols().getShortMonths();
			builder.append(facesUtils
					.getResourceBundleStringValue("formStockOpnamePeriodePerhitungan"));
			builder.append(":" + months[Integer.parseInt(stockOpnameVOSearchDialog.getPeriodBulan())]
					+ ",");
			searchCriteria.add(new SearchValueObject("periodBulan",
					stockOpnameVOSearchDialog.getPeriodBulan()));
		}

		if (stockOpnameVOSearchDialog.getPeriodTahun() != null
				&& StringUtils.isNotBlank(stockOpnameVOSearchDialog
						.getPeriodTahun())) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formStockOpnamePeriodePerhitungan"));
			builder.append(":" + stockOpnameVOSearchDialog.getPeriodTahun()
					+ ",");
			searchCriteria.add(new SearchValueObject("periodTahun",
					stockOpnameVOSearchDialog.getPeriodTahun()));
		}

		if (stockOpnameVOSearchDialog.getNotes() != null
				&& StringUtils.isNotBlank(stockOpnameVOSearchDialog.getNotes())) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formStockOpnameDeskripsi"));
			builder.append(":" + stockOpnameVOSearchDialog.getNotes() + ",");
			searchCriteria.add(new SearchValueObject("deskripsi",
					stockOpnameVOSearchDialog.getNotes()));
		}

		if (stockOpnameVOSearchDialog.getStatus() != null
				&& StringUtils
						.isNotBlank(stockOpnameVOSearchDialog.getStatus())) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formStockOpnameStatus"));
			builder.append(":"
					+ stockOpnameVOSearchDialog.getParamStatus()
							.getParameterDtlName() + ",");
			searchCriteria.add(new SearchValueObject("status",
					stockOpnameVOSearchDialog.getStatus()));
		}

		builder = builder.replace(builder.toString().lastIndexOf(","), builder
				.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();

		pagingStockOpname.setSearchCriteria(searchCriteria);
	}

	public void changeCompanyToOutlet() {
		if (stockOpnameVOSearchDialog.getCompanyId() != null) {
			List<Outlet> outletList = outletService
					.findOutletByCompany(stockOpnameVOSearchDialog
							.getCompanyId());
			outletSelectItem = new ArrayList<SelectItem>();
			if (outletList.size() > 0) {
				for (Outlet out : outletList) {
					outletSelectItem.add(new SelectItem(out.getOutletId(), out
							.getOutletName()));
				}
			}

		}
	}

	public StockOpnameService getStockOpnameService() {
		return stockOpnameService;
	}

	public void setStockOpnameService(StockOpnameService stockOpnameService) {
		this.stockOpnameService = stockOpnameService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public StockOpnameVO getStockOpnameVOSearchDialog() {
		return stockOpnameVOSearchDialog;
	}

	public void setStockOpnameVOSearchDialog(
			StockOpnameVO stockOpnameVOSearchDialog) {
		this.stockOpnameVOSearchDialog = stockOpnameVOSearchDialog;
	}

	public PagingTableModel<StockOpnameVO> getPagingStockOpname() {
		return pagingStockOpname;
	}

	public void setPagingStockOpname(
			PagingTableModel<StockOpnameVO> pagingStockOpname) {
		this.pagingStockOpname = pagingStockOpname;
	}

	public List<StockOpnameVO> getStockOpnameList() {
		return stockOpnameList;
	}

	public void setStockOpnameList(List<StockOpnameVO> stockOpnameList) {
		this.stockOpnameList = stockOpnameList;
	}

	public List<StockOpnameVO> getSelectedStockOpname() {
		return selectedStockOpname;
	}

	public void setSelectedStockOpname(List<StockOpnameVO> selectedStockOpname) {
		this.selectedStockOpname = selectedStockOpname;
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

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
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

	public StockOpnameVO getStockOpnameVO() {
		return stockOpnameVO;
	}

	public void setStockOpnameVO(StockOpnameVO stockOpnameVO) {
		this.stockOpnameVO = stockOpnameVO;
	}
}
