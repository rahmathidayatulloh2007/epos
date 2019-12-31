package com.wo.epos.module.purchasing.po.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.service.ItemService;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.purchasing.po.service.PoDtlService;
import com.wo.epos.module.purchasing.po.service.PoService;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;
import com.wo.epos.module.purchasing.po.vo.PoVO;
import com.wo.epos.module.purchasing.supplier.service.SupplierService;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;

@ManagedBean(name = "poInputBean")
@ViewScoped
public class PoInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -1256963229247693745L;

	static Logger logger = Logger.getLogger(PoInputBean.class);

	@ManagedProperty(value = "#{poService}")
	private PoService poService;

	@ManagedProperty(value = "#{poDtlService}")
	private PoDtlService poDtlService;

	@ManagedProperty(value = "#{itemService}")
	private ItemService itemService;

	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;

	private PagingTableModel<ItemVO> pagingItem;

	private PoVO poVO = new PoVO();
	private PoDtlVO poDtlVO = new PoDtlVO();
	private SystemProperty sysPropPpn = new SystemProperty();

	private List<PoDtlVO> listPoDtlVO = new ArrayList<PoDtlVO>();
	private List<SupplierVO> supplierDtlList = new ArrayList<SupplierVO>();
	private List<ItemVO> itemList = new ArrayList<ItemVO>();

	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean disableFlagDisc;

	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> taxSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> supplierSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> discountSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> discountSelectItem2 = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> itemSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> statusSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;
	private String searchAllDialog;
	private String completeSupplier;
	private String closeReasonDialog;
	private String poNoMesg;

	private Integer indexRowTable;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			poVO = new PoVO();
			poDtlVO = new PoDtlVO();
			listPoDtlVO = new ArrayList<PoDtlVO>();
			// supplierVO = new SupplierVO();

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			for (CompanyVO comVO : companySelectList) {
				companySelectItem.add(new SelectItem(comVO.getCompanyId(), comVO.getCompanyName()));
			}

			// Edited
			sysPropPpn = systemPropertyService.searchSystemPropertyName(CommonConstants.TAX_VALUE);
			taxSelectItem = new ArrayList<SelectItem>();
			List<ParameterDtl> paramDtlList = parameterService.parameterDtlList(CommonConstants.PO_TAXTYPE);
			for (ParameterDtl paramDtl : paramDtlList) {
				if (!paramDtl.getParameterDtlCode().equals(CommonConstants.POTAXTYPE_WITHOUT)) {
					taxSelectItem.add(new SelectItem(paramDtl.getParameterDtlCode(),
							paramDtl.getParameterDtlName() + " " + (sysPropPpn.getSystemPropertyValue() + "%")));
				} else {
					taxSelectItem.add(new SelectItem(paramDtl.getParameterDtlCode(), paramDtl.getParameterDtlName()));
				}
			}

			statusSelectItem = new ArrayList<SelectItem>();
			List<ParameterDtl> statusParamDtlList = parameterService.parameterDtlList(CommonConstants.PO_STATUS);
			for (ParameterDtl paramDtl : statusParamDtlList) {
				statusSelectItem.add(new SelectItem(paramDtl.getParameterDtlCode(), paramDtl.getParameterDtlName()));
			}

			supplierSelectItem = new ArrayList<SelectItem>();

			if (userSession.getCompanyId() != null) {
				itemSelectItem = new ArrayList<SelectItem>();
				List<ItemVO> itemSelectList = itemService.searchItemByCompanyList(userSession.getCompanyId());
				for (ItemVO itemVO : itemSelectList) {
					itemSelectItem.add(
							new SelectItem(itemVO.getItemId(), itemVO.getItemCode() + " - " + itemVO.getItemName()));
				}

				outletSelectItem = new ArrayList<SelectItem>();
				List<Outlet> outletSelectList = outletService.findOutletByCompany(userSession.getCompanyId());
				for (Outlet outVO : outletSelectList) {
					outletSelectItem.add(new SelectItem(outVO.getOutletId(), outVO.getOutletName()));
				}

				List<SupplierVO> supplierSelectList = supplierService
						.searchSupplierListByCompany(userSession.getCompanyId());
				for (SupplierVO supplierVO : supplierSelectList) {
					supplierSelectItem.add(new SelectItem(supplierVO.getSupplierId(),
							supplierVO.getSupplierCode() + " - " + supplierVO.getSupplierName()));
				}
			}

			discountSelectItem = new ArrayList<SelectItem>();
			List<ParameterDtl> paramDtl2List = parameterService.parameterDtlList(CommonConstants.DISC_TYPE);
			for (ParameterDtl paramDtl2 : paramDtl2List) {
				discountSelectItem
						.add(new SelectItem(paramDtl2.getParameterDtlCode(), paramDtl2.getParameterDtlName()));
			}
			
			discountSelectItem2 = new ArrayList<SelectItem>();
			List<ParameterDtl> paramDiscList = parameterService.parameterDtlList(CommonConstants.DISC_TYPE);
			for(ParameterDtl paramDtl : paramDiscList){
				discountSelectItem2.add(new SelectItem(paramDtl.getParameterDtlCode(),paramDtl.getParameterDtlName()));
			}
			

			pagingItem = new PagingTableModel(itemService, paging);

			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;
			disableFlagDisc = true;
		}
	}

	public void changeCompany() {
		
		itemSelectItem = new ArrayList<SelectItem>();
		List<ItemVO> itemSelectList = itemService.searchItemByCompanyList(poVO.getCompanyId());
		for (ItemVO itemVO : itemSelectList) {
			itemSelectItem.add(new SelectItem(itemVO.getItemId(), itemVO.getItemCode() + " - " + itemVO.getItemName()));
		}

		outletSelectItem = new ArrayList<SelectItem>();
		List<Outlet> outletSelectList = outletService.findOutletByCompany(poVO.getCompanyId());
		for (Outlet outVO : outletSelectList) {
			outletSelectItem.add(new SelectItem(outVO.getOutletId(), outVO.getOutletName()));
		}

		supplierSelectItem = new ArrayList<SelectItem>();
		List<SupplierVO> supplierSelectList = supplierService.searchSupplierListByCompany(poVO.getCompanyId());
		for (SupplierVO supplierVO : supplierSelectList) {
			supplierSelectItem.add(new SelectItem(supplierVO.getSupplierId(),
					supplierVO.getSupplierCode() + " - " + supplierVO.getSupplierName()));
		}

	}

	public void save() {
		try {

			if (userSession.getCompanyId() != null) {
				poVO.setCompanyId(userSession.getCompanyId());

				if (userSession.getOutletId() != null) {
					poVO.setOutletId(userSession.getOutletId());
				}
			}

			if (MODE_TYPE.equals("ADD")) {
				poNoMesg = poService.numberPo();
				
				poService.save(poVO, listPoDtlVO, userSession.getUserCode());
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, poNoMesg +" "+ facesUtils.retrieveMessage("common_msg_saved"));
			} else if (MODE_TYPE.equals("EDIT")) {
				poService.update(poVO, listPoDtlVO, userSession.getUserCode());
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,poVO.getPoNo()+ " " +
						facesUtils.retrieveMessage("common_msg_updated"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO,
					facesUtils.retrieveMessage("common_msg_save_failed") + ex.getMessage());
		}
	}
	public void modeAdd() {
		poVO = new PoVO();
		poDtlVO = new PoDtlVO();
		listPoDtlVO = new ArrayList<PoDtlVO>();
		completeSupplier = "";
	}

	public void modeEdit(List<PoVO> poList) {
		MODE_TYPE = "EDIT";
		for (int i = 0; i < poList.size(); i++) {
			PoVO poVOTemp = (PoVO) poList.get(i);
			poVO = poVOTemp;
		}

		completeSupplier(poVO.getSupplierCode() + " - " + poVO.getSupplierName());
		completeSupplier = poVO.getSupplierCode() + " - " + poVO.getSupplierName();

		listPoDtlVO = poDtlService.searchListPoDtlVO(poVO.getPoId());
		eventSubTotal();

	}

	public void addItem() {
		poDtlVO = new PoDtlVO();
		listPoDtlVO.add(poDtlVO);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void searchChooseItem() {
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		if (searchAllDialog != null && StringUtils.isNotBlank(searchAllDialog)) {
			searchCriteria.add(new SearchValueObject("searchAll", searchAllDialog));
		} else {
			searchCriteria.add(new SearchValueObject("searchAll", ""));
		}

		pagingItem.setSearchCriteria(searchCriteria);

	}

	public boolean validate() {
		boolean valid = true;

		if (poVO.getPoDate() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
					facesUtils.retrieveMessage("formPoDate") + " " + facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			valid = false;
		}

		if (poVO.getSupplierId() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formPoSupplier") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);
			valid = false;
		}

		if (poVO.getTaxTypeCode() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
					facesUtils.retrieveMessage("formPoTax") + " " + facesUtils.retrieveMessage("errorMustBeFilled"),
					null);
			valid = false;
		}

		if (listPoDtlVO.size() > 0) {
			for (int i = 0; i < listPoDtlVO.size(); i++) {
				PoDtlVO vo = (PoDtlVO) listPoDtlVO.get(i);

				if (vo.getItemName() == null || vo.getItemName().equals("")) {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPoItemName") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

					valid = false;
				}

				if (vo.getOrderQty() != null) {
					if (vo.getOrderQty() <= 0) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
								facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
										+ facesUtils.getResourceBundleStringValue("formPoErrMsgOrderQty"));
						valid = false;
					}
				} else {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPoQuantity") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
					valid = false;
				}

				if (vo.getUnitPrice() != null) {
					if (vo.getUnitPrice() < 0) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
								facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
										+ facesUtils.getResourceBundleStringValue("formPoErrMsgPrice"));
						valid = false;
					}
				} else {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPoPrice") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
					valid = false;

				}

//				for (int j = i + 1; j < listPoDtlVO.size(); j++) {
//					PoDtlVO voDuplicate = (PoDtlVO) listPoDtlVO.get(j);
//					if (vo.getItemId() != null && voDuplicate.getItemId() != null) {
//						if (Integer.parseInt(voDuplicate.getItemId() + "") == Integer.parseInt(vo.getItemId() + "")) {
//							facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
//									facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
//											+ vo.getItemName() + " "
//											+ facesUtils.getResourceBundleStringValue("errorDuplicateRow") + " "
//											+ (j + 1));
//
//							valid = false;
//						}
//					}
//				}
			}

		} else {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
					facesUtils.getResourceBundleStringValue("formPoErrMsgMinim"));

			valid = false;

		}

		return valid;

	}

	private void validateDisount(Integer indexRow) {
		if (listPoDtlVO.get(indexRow).getDiscPercent() >= 100) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messageDiscPercent",
					facesUtils.getResourceBundleStringValue("textRow") + " " + (indexRow + 1) + " "
							+ facesUtils.getResourceBundleStringValue("formPoValidationDiscount"));
		}
	}
	
//	public void eventSubTotal() {
//	Double subTotal = null;
//	Double totalTemp = null;
//	Double discount = null;
//	Double discountExt = null;
//	
//	for (int i = 0; i < listPoDtlVO.size(); i++) {
//		PoDtlVO vo = (PoDtlVO) listPoDtlVO.get(i);
//		if (vo.getOrderQty() != null && vo.getUnitPrice() != null) {
//			Double totalPrice = vo.getOrderQty().doubleValue() * vo.getUnitPrice();
//			vo.setTotalPo(totalPrice.doubleValue());
//			
//			if (vo.getDiscPercent() != null) {
//				vo.setDiscTypeCode(CommonConstants.DISCTYPE_PERCENT);
//				discount = (discount != null ? discount.doubleValue() : new Double(0))
//						+ (totalPrice * (vo.getDiscPercent().doubleValue() / 100));
//				totalTemp = totalPrice - discount;
//			
//				vo.setDiscValue(null);
//			}
//			else {
//				vo.setDiscValue(null);
//				vo.setDiscPercent(null);
//			}
//			
//			discount = handleDiscExt(vo, discountExt, totalTemp, discount);
//			subTotal = (subTotal != null ? subTotal.doubleValue() : new Double(0)) + vo.getTotalPo().doubleValue();
//
//		}
//
//		if (vo.getDiscPercent() != null) {
//			validateDisount(i);
//		}
//	}
//
//	if (subTotal != null) {
//		DecimalFormat df = new DecimalFormat("#.##");
//
//		if (subTotal != null)
//			poVO.setSubTotal(new BigDecimal(subTotal));
//		if (discount != null)
//			poVO.setDiscount(new BigDecimal(discount));
//
//		Double subtotalDiskon = (subTotal.doubleValue()
//				- (discount != null ? discount.doubleValue() : new Double(0)));
//		Double taxValue = (Double.parseDouble(sysPropPpn.getSystemPropertyValue()) / 100);
//		Double totalAll = null;
//		if (poVO.getTaxTypeCode().equals(CommonConstants.POTAXTYPE_WITH)) {
//			poVO.setPpn(new BigDecimal(
//					subtotalDiskon * (Double.parseDouble(sysPropPpn.getSystemPropertyValue()) / 100)));
//			totalAll = subtotalDiskon.doubleValue()
//					+ (poVO.getPpn() != null ? poVO.getPpn().doubleValue() : new Double(0));
//		} else {
//			poVO.setPpn(null);
//			totalAll = subtotalDiskon.doubleValue();
//		}
//		poVO.setTotal(new BigDecimal(totalAll.doubleValue()));
//	}
//}
//
//public Double handleDiscExt(PoDtlVO vo, Double discountExt,Double totalTemp, Double discount){
//
//	if(vo.getDiscPercentExt() != null){
//		vo.setDiscTypeCode2(CommonConstants.DISCTYPE_PERCENT);
//		discountExt = (discountExt != null ? discountExt.doubleValue() : new Double(0))
//				+ (totalTemp * (vo.getDiscPercentExt().doubleValue() / 100));
//		discount = discount + discountExt;
//	}else {
//		vo.setDiscValueExt(null);
//		vo.setDiscPercentExt(null);
//	}
//	
//	return discount;
//}
	
	public void eventSubTotal() {
	Double subTotal = null;
	Double totalTemp = null;
	Double discount = null;
	Double discountExt = null;
	Double discountTotal = 0d;
	
	for (int i = 0; i < listPoDtlVO.size(); i++) {
		
		PoDtlVO vo = (PoDtlVO) listPoDtlVO.get(i);
		if (vo.getOrderQty() != null && vo.getUnitPrice() != null) {
			Double totalPrice = vo.getOrderQty().doubleValue() * vo.getUnitPrice();
			totalTemp=0d;
			discount=0d;
			discountExt=0d;
			vo.setTotalPo(totalPrice.doubleValue());
			
			if (vo.getDiscPercent() != null) {
				vo.setDiscTypeCode(CommonConstants.DISCTYPE_PERCENT);
				discount = (discount != null ? discount.doubleValue() : new Double(0))
						+ (totalPrice * (vo.getDiscPercent().doubleValue() / 100));
				totalTemp = totalPrice - discount;
				vo.setTotalTemp(totalTemp);
				vo.setDiscValue(null);
			}
			else {
				vo.setDiscValue(null);
				vo.setDiscPercent(null);
			}
			
			if(vo.getDiscPercentExt() != null){
				vo.setDiscTypeCode2(CommonConstants.DISCTYPE_PERCENT);
				discountExt = (discountExt != null ? discountExt.doubleValue() : new Double(0))
						+ (totalTemp * (vo.getDiscPercentExt().doubleValue() / 100));
				discount = discount + discountExt;
			}else {
				vo.setDiscValueExt(null);
				vo.setDiscPercentExt(null);
			}
			
			discountTotal =discountTotal+discount;
			subTotal = (subTotal != null ? subTotal.doubleValue() : new Double(0)) + vo.getTotalPo().doubleValue();

		}

		if (vo.getDiscPercent() != null) {
			validateDisount(i);
		}
	}

	if (subTotal != null) {
		DecimalFormat df = new DecimalFormat("#.##");

		if (subTotal != null)
			poVO.setSubTotal(new BigDecimal(subTotal));
		if (discount != null)
			poVO.setDiscount(new BigDecimal(discountTotal));

		Double subtotalDiskon = (subTotal.doubleValue()
				- (discountTotal != null ? discountTotal.doubleValue() : new Double(0)));
		Double taxValue = (Double.parseDouble(sysPropPpn.getSystemPropertyValue()) / 100);
		Double totalAll = null;
		if (poVO.getTaxTypeCode().equals(CommonConstants.POTAXTYPE_WITH)) {
			poVO.setPpn(new BigDecimal(
					subtotalDiskon * (Double.parseDouble(sysPropPpn.getSystemPropertyValue()) / 100)));
			totalAll = subtotalDiskon.doubleValue()
					+ (poVO.getPpn() != null ? poVO.getPpn().doubleValue() : new Double(0));
		} else {
			poVO.setPpn(null);
			totalAll = subtotalDiskon.doubleValue();
		}
		poVO.setTotal(new BigDecimal(totalAll.doubleValue()));
	}
}

public Double handleDiscExt(PoDtlVO vo, Double discountExt,Double totalTemp, Double discount){

	if(vo.getDiscPercentExt() != null){
		vo.setDiscTypeCode2(CommonConstants.DISCTYPE_PERCENT);
		discountExt = (discountExt != null ? discountExt.doubleValue() : new Double(0))
				+ (totalTemp * (vo.getDiscPercentExt().doubleValue() / 100));
		discount = discount + discountExt;
	}else {
		vo.setDiscValueExt(null);
		vo.setDiscPercentExt(null);
	}
	
	return discount;
}
	
	public void eventDiscounTypeCode(Integer indexRow) {
		if (listPoDtlVO.get(indexRow).getDiscTypeCode() != null) {
			if (listPoDtlVO.get(indexRow).getDiscTypeCode().equals(CommonConstants.DISCTYPE_PERCENT)) {
				disableFlagDisc = false;
				listPoDtlVO.get(indexRow).setDiscValue(null);
			} else if (listPoDtlVO.get(indexRow).getDiscTypeCode().equals(CommonConstants.DISCTYPE_VALUE)) {
				disableFlagDisc = false;
				listPoDtlVO.get(indexRow).setDiscPercent(null);
			} else {
				disableFlagDisc = true;
				listPoDtlVO.get(indexRow).setDiscPercent(null);
				listPoDtlVO.get(indexRow).setDiscValue(null);
			}
		} else {
			disableFlagDisc = true;
			listPoDtlVO.get(indexRow).setDiscPercent(null);
			listPoDtlVO.get(indexRow).setDiscValue(null);
		}

		eventSubTotal();
	}
	
	public void eventDiscounTypeCode2(Integer indexRow) {
		if (listPoDtlVO.get(indexRow).getDiscTypeCode2() != null) {
			if (listPoDtlVO.get(indexRow).getDiscTypeCode2().equals(CommonConstants.DISCTYPE_PERCENT)) {
				disableFlagDisc = false;
				listPoDtlVO.get(indexRow).setDiscValueExt(null);
			} else if (listPoDtlVO.get(indexRow).getDiscTypeCode2().equals(CommonConstants.DISCTYPE_VALUE)) {
				disableFlagDisc = false;
				listPoDtlVO.get(indexRow).setDiscPercentExt(null);
			} else {
				disableFlagDisc = true;
				listPoDtlVO.get(indexRow).setDiscPercentExt(null);
				listPoDtlVO.get(indexRow).setDiscValueExt(null);
			}
		} else {
			disableFlagDisc = true;
			listPoDtlVO.get(indexRow).setDiscPercentExt(null);
			listPoDtlVO.get(indexRow).setDiscValueExt(null);
		}

		eventSubTotal();
	}

	public void eventDeletePo(PoDtlVO poDtlDeleteVO) {
		listPoDtlVO.remove(poDtlDeleteVO);
		eventSubTotal();

		if (listPoDtlVO.size() <= 0) {
			poVO.setSubTotal(null);
			poVO.setDiscount(null);
			poVO.setPpn(null);
			poVO.setTotal(null);
		}
	}

	public List<String> completeSupplier(String outoCompleteText) {
		List<String> resultList = new ArrayList<String>();
		for (SupplierVO supVo : supplierDtlList) {
			if (userSession.getCompanyId() == null) {
				if (supVo.getSupplierName().toUpperCase().contains(outoCompleteText.toUpperCase())) {
					resultList.add(supVo.getSupplierCode() + " - " + supVo.getSupplierName());
				}
			} else {
				if (Integer.parseInt(userSession.getCompanyId() + "") == Integer.parseInt(supVo.getCompanyId() + "")) {
					if (supVo.getSupplierName().toUpperCase().contains(outoCompleteText.toUpperCase())) {
						resultList.add(supVo.getSupplierCode() + " - " + supVo.getSupplierName());
					}
				}
			}
		}

		return resultList;

	}
	
	public void handleSelectItem(int indexRow) {
		
		for (PoDtlVO vo : listPoDtlVO) {
			vo = (PoDtlVO) listPoDtlVO.get(indexRow);
			Item itemTemp = itemService.findById(vo.getItemId());
			vo.setItemCode(itemTemp.getItemCode());
			vo.setItemName(itemTemp.getItemName());
			vo.setUnitPrice(itemTemp.getUnitPrice());
			if (itemTemp.getUm() != null) {
				vo.setUmId(itemTemp.getUm().getUmId());
				vo.setOrderUm(itemTemp.getUm().getUmId());
				vo.setUmName(itemTemp.getUm().getUmName());
			}

		}

	}
	
	public void changeStatusPoClose(){
		if(closeReasonDialog.trim().equals("") || closeReasonDialog.isEmpty()){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:closeReason",facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			poVO.setCloseReason(closeReasonDialog);
			RequestContext.getCurrentInstance().execute("PF('dialogReason').show()");
		}
		else{
			System.out.println(poVO.getStatusCode());
			ParameterDtl paramDtl = parameterService.findByDetailId(CommonConstants.PO_CLOSE);
			poVO.setStatusCode(paramDtl.getParameterDtlCode());
			poVO.setStatusName(paramDtl.getParameterDtlName());
			poVO.setCloseReason(closeReasonDialog);
			RequestContext.getCurrentInstance().execute("PF('dialogReason').hide()");
		}
	      
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PoInputBean.logger = logger;
	}

	public PoService getPoService() {
		return poService;
	}

	public void setPoService(PoService poService) {
		this.poService = poService;
	}

	public PoVO getPoVO() {
		return poVO;
	}

	public void setPoVO(PoVO poVO) {
		this.poVO = poVO;
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

	public List<SelectItem> getItemSelectItem() {
		return itemSelectItem;
	}

	public void setItemSelectItem(List<SelectItem> itemSelectItem) {
		this.itemSelectItem = itemSelectItem;
	}

	public List<SelectItem> getTaxSelectItem() {
		return taxSelectItem;
	}

	public void setTaxSelectItem(List<SelectItem> taxSelectItem) {
		this.taxSelectItem = taxSelectItem;
	}

	public List<SelectItem> getSupplierSelectItem() {
		return supplierSelectItem;
	}

	public void setSupplierSelectItem(List<SelectItem> supplierSelectItem) {
		this.supplierSelectItem = supplierSelectItem;
	}

	public PoDtlVO getPoDtlVO() {
		return poDtlVO;
	}

	public void setPoDtlVO(PoDtlVO poDtlVO) {
		this.poDtlVO = poDtlVO;
	}

	public List<PoDtlVO> getListPoDtlVO() {
		return listPoDtlVO;
	}

	public void setListPoDtlVO(List<PoDtlVO> listPoDtlVO) {
		this.listPoDtlVO = listPoDtlVO;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public String getSearchAllDialog() {
		return searchAllDialog;
	}

	public void setSearchAllDialog(String searchAllDialog) {
		this.searchAllDialog = searchAllDialog;
	}

	public Integer getIndexRowTable() {
		return indexRowTable;
	}

	public void setIndexRowTable(Integer indexRowTable) {
		this.indexRowTable = indexRowTable;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
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

	public List<SelectItem> getDiscountSelectItem() {
		return discountSelectItem;
	}

	public void setDiscountSelectItem(List<SelectItem> discountSelectItem) {
		this.discountSelectItem = discountSelectItem;
	}

	public PoDtlService getPoDtlService() {
		return poDtlService;
	}

	public void setPoDtlService(PoDtlService poDtlService) {
		this.poDtlService = poDtlService;
	}

	public boolean isDisableFlagDisc() {
		return disableFlagDisc;
	}

	public void setDisableFlagDisc(boolean disableFlagDisc) {
		this.disableFlagDisc = disableFlagDisc;
	}

	public PagingTableModel<ItemVO> getPagingItem() {
		return pagingItem;
	}

	public void setPagingItem(PagingTableModel<ItemVO> pagingItem) {
		this.pagingItem = pagingItem;
	}

	public List<SupplierVO> getSupplierDtlList() {
		return supplierDtlList;
	}

	public void setSupplierDtlList(List<SupplierVO> supplierDtlList) {
		this.supplierDtlList = supplierDtlList;
	}

	public String getCompleteSupplier() {
		return completeSupplier;
	}

	public void setCompleteSupplier(String completeSupplier) {
		this.completeSupplier = completeSupplier;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	public SystemProperty getSysPropPpn() {
		return sysPropPpn;
	}

	public void setSysPropPpn(SystemProperty sysPropPpn) {
		this.sysPropPpn = sysPropPpn;
	}

	public List<ItemVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemVO> itemList) {
		this.itemList = itemList;
	}

	public List<SelectItem> getStatusSelectItem() {
		return statusSelectItem;
	}

	public void setStatusSelectItem(List<SelectItem> statusSelectItem) {
		this.statusSelectItem = statusSelectItem;
	}

	public List<SelectItem> getDiscountSelectItem2() {
		return discountSelectItem2;
	}

	public void setDiscountSelectItem2(List<SelectItem> discountSelectItem2) {
		this.discountSelectItem2 = discountSelectItem2;
	}

	public String getCloseReasonDialog() {
		return closeReasonDialog;
	}

	public void setCloseReasonDialog(String closeReasonDialog) {
		this.closeReasonDialog = closeReasonDialog;
	}

	public String getPoNoMesg() {
		return poNoMesg;
	}

	public void setPoNoMesg(String poNoMesg) {
		this.poNoMesg = poNoMesg;
	}
	
	

}
