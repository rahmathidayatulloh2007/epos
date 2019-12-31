package com.wo.epos.module.sales.salesOrder.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
/*import com.wo.epos.module.inv.customer.service.CustomerService;*/
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.service.ProductService;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemStock.service.ItemStockService;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.paymentType.service.PaymentTypeService;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.sales.register.service.RegisterService;
import com.wo.epos.module.sales.register.vo.RegisterVO;
import com.wo.epos.module.sales.salesOrder.service.SalesOrderRetailService;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;
import com.wo.epos.module.sales.customer.model.CustomerSales;
import com.wo.epos.module.sales.customer.service.CustomerService;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "salesOrderRetailInputBean")
@ViewScoped
public class SalesOrderRetailInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 7843026683560754008L;

	static Logger logger = Logger.getLogger(SalesOrderRetailInputBean.class);

	@ManagedProperty(value = "#{registerService}")
	private RegisterService registerService;

	@ManagedProperty(value = "#{productService}")
	private ProductService productService;

	/*
	 * @ManagedProperty(value = "#{customerService}") private CustomerService
	 * customerService;
	 */

	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;

	@ManagedProperty(value = "#{customerService2}")
	private CustomerService customerService2;

	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;

	@ManagedProperty(value = "#{salesOrderRetailService}")
	private SalesOrderRetailService salesOrderRetailService;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	@ManagedProperty(value = "#{paymentTypeService}")
	private PaymentTypeService paymentTypeService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	@ManagedProperty(value = "#{itemStockService}")
	private ItemStockService itemStockService;

	private List<SalesOrderDtlVO> salesOrderDtlVOList = new ArrayList<SalesOrderDtlVO>();
	private List<PaymentTypeVO> paymentTypeVoList = new ArrayList<PaymentTypeVO>();
	private List<ProductVO> productVoList = new ArrayList<ProductVO>();
	private List<PaymentTypeVO> dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();

	private List<SelectItem> registerSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> customerSelectItem = new ArrayList<SelectItem>();

	private SalesOrderVO salesOrderVoHeader = new SalesOrderVO();
	private CustomerVO customerVO = new CustomerVO();
	private SalesOrderDtlVO salesOrderDtlTableDel = new SalesOrderDtlVO();
	private SalesOrderDtlVO soDtlVoDialogProduct = new SalesOrderDtlVO();

	private SystemProperty systemPropTaxType = new SystemProperty();

	private CustomerSales customerSales = new CustomerSales();
	private String MODE_TYPE;
	private String completeSearchProduct;

	private boolean disableButtonOnRowClicked;
	private boolean viewCustomer;
	private String ppn;

	@PostConstruct
	public void postConstruct() {
		super.init();
		initBean();

	}

	public void initBean() {
		registerSelectItemList = new ArrayList<SelectItem>();
		customerSelectItem = new ArrayList<SelectItem>();
		salesOrderDtlVOList = new ArrayList<SalesOrderDtlVO>();
		dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
		paymentTypeVoList = new ArrayList<PaymentTypeVO>();
		salesOrderDtlTableDel = new SalesOrderDtlVO();
		completeSearchProduct = "";
		salesOrderVoHeader = new SalesOrderVO();
		salesOrderVoHeader.setSoDate(new Date());
		salesOrderVoHeader.setTitelDiscount("Diskon");
		salesOrderVoHeader.setItemAmount(new Long(0));
		salesOrderVoHeader.setSubTotal(new Double(0));
		salesOrderVoHeader.setSubDiskon(new Double(0));

		ParameterDtl paramDtl = parameterService.findByDetailId(CommonConstants.SO_PICKUP);
		salesOrderVoHeader.setStatusCode(paramDtl.getParameterDtlCode());
		salesOrderVoHeader.setStatusName(paramDtl.getParameterDtlName());

		if (userSession.getOutletId() != null) {
			List<RegisterVO> registerVoList = registerService.findRegisterByOutletId(userSession.getOutletId());
			for (RegisterVO vo : registerVoList) {
				registerSelectItemList.add(new SelectItem(vo.getRegId(), vo.getCashierName()));
			}

			Outlet outletLogin = outletService.findById(userSession.getOutletId());
			salesOrderVoHeader.setOutletId(userSession.getOutletId());
			if (outletLogin.getCompany() != null) {
				salesOrderVoHeader.setCompanyId(outletLogin.getCompany().getCompanyId());
			}

			Outlet outlet = outletService.findById(userSession.getOutletId());
			paymentTypeVoList = paymentTypeService.searchDataPaymentByCompany(outlet.getCompany().getCompanyId());

		}

		if (userSession.getCompanyId() != null) {
			productVoList = productService.searchAllProductByCompanyId(userSession.getCompanyId());

			systemPropTaxType = systemPropertyService
					.searchSystemPropertyNameAndCompany(CommonConstants.DEFAULT_TAX_TYPE, userSession.getCompanyId());
			salesOrderVoHeader.setTaxTypeCode(systemPropTaxType.getSystemPropertyValue());
			if (systemPropTaxType.getSystemPropertyValue().equals(CommonConstants.TAX_EXCLUDE)) {
				SystemProperty taxValue = systemPropertyService.searchSystemPropertyNameAndCompany(
						CommonConstants.DEFAULT_TAX_VALUE, userSession.getCompanyId());
				salesOrderVoHeader.setTaxValue(new Double(taxValue.getSystemPropertyValue()));
			} else {
				salesOrderVoHeader.setTaxTypeCode(systemPropTaxType.getSystemPropertyValue());
			}

		}

		citySelectItem = new ArrayList<SelectItem>();
		List<CityVO> citySelectList = cityService.citySearch();
		for (CityVO vo : citySelectList) {
			citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}

		customerSelectItem = new ArrayList<SelectItem>();
		List<CustomerSalesVO> customerSelectList = customerService2.searchCustomerList();
		for (CustomerSalesVO vo : customerSelectList) {
			customerSelectItem.add(new SelectItem(vo.getCustomerId(), vo.getCustomerName()));
		}

		ppn = null;

	}

	private boolean validatePayment() {
		boolean valid = true;
		if (dataPaymentTypeVoList.size() == 0) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, " Silahkan pilih tipe pembayaran ");
			// facesUtils.getResourceBundleStringValue("formPoOutlet")+"
			// "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

			valid = false;
		}

		if (salesOrderDtlVOList.size() == 0) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, " Item harus di isi ");
			// facesUtils.getResourceBundleStringValue("formPoOutlet")+"
			// "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

			valid = false;
		} else {
			for (SalesOrderDtlVO salesOrderDtlVo : salesOrderDtlVOList) {
				Product product = new Product();
				product = productService.findById(salesOrderDtlVo.getProductId());
				for (ProductBom pBom : product.getProductBomList()) {

					ItemStockVO itemStockVO = itemStockService.getItemStockByCompanyIdOutletIdAndItemId(
							userSession.getCompanyId(), userSession.getOutletId(), pBom.getItem().getItemId());
					if (salesOrderDtlVo.getOrderQty() > itemStockVO.getStockQty()) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
								"Jumlah Order Qty lebih dari Stock yang ada");
						// facesUtils.getResourceBundleStringValue("formPoOutlet")+"
						// "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

						valid = false;
						break;
					}
				}
			}
		}
		return valid;
	}

	private boolean validateSaveDirect() {
		boolean valid = true;
		if (salesOrderDtlVOList.size() == 0) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, " Item harus di isi ");
			// facesUtils.getResourceBundleStringValue("formPoOutlet")+"
			// "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

			valid = false;
		} else {
			for (SalesOrderDtlVO salesOrderDtlVo : salesOrderDtlVOList) {
				Product product = new Product();
				product = productService.findById(salesOrderDtlVo.getProductId());
				for (ProductBom pBom : product.getProductBomList()) {

					ItemStockVO itemStockVO = itemStockService.getItemStockByCompanyIdOutletIdAndItemId(
							userSession.getCompanyId(), userSession.getOutletId(), pBom.getItem().getItemId());
					if (salesOrderDtlVo.getOrderQty() > itemStockVO.getStockQty()) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
								"Jumlah Order Qty lebih dari Stock yang ada. Sisa Stock: " + itemStockVO.getStockQty());
						// facesUtils.getResourceBundleStringValue("formPoOutlet")+"
						// "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

						valid = false;
						break;
					}
				}
			}
		}
		return valid;
	}

	public void saveDiect() {
		try {
			if (validateRegister()) {
				if (validateSaveDirect()) {
					salesOrderVoHeader.setStatusCode(CommonConstants.SO_PAYMENT);
					salesOrderVoHeader.setSoTypeCode(CommonConstants.SO_PICKUP);
					salesOrderRetailService.saveDirect(salesOrderVoHeader, salesOrderDtlVOList,
							userSession.getUserCode());

					facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, " Save Berhasil ");
					initBean();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			salesOrderRetailService.rollback();
		}
	}

	public void savePayment() {
		try {
			if (validatePayment()) {
				salesOrderVoHeader.setStatusCode(CommonConstants.SO_PAYMENT);
				salesOrderVoHeader.setSoTypeCode(CommonConstants.SO_PICKUP);
				salesOrderRetailService.savePayment(salesOrderVoHeader, salesOrderDtlVOList, dataPaymentTypeVoList,
						userSession.getUserCode());
				buttonCancel();
				RequestContext.getCurrentInstance().execute("PF('dialogPembayaranRetail').hide()");

				facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
						facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
						null);
			} else {
				RequestContext.getCurrentInstance().execute("PF('dialogPembayaranRetail').show()");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			salesOrderRetailService.rollback();
		}
	}

	public void cancelPayment() {
		dataPaymentTypeVoList.clear();
		salesOrderVoHeader.setChangeAmount(null);
		salesOrderVoHeader.setPaymentAmount(null);
		RequestContext.getCurrentInstance().execute("PF('dialogPembayaranRetail').hide()");

	}

	public List<String> completeSearchProduct(String outoCompleteText) {
		List<String> resultList = new ArrayList<String>();
		for (ProductVO proVo : productVoList) {
			if (proVo.getProductName().toUpperCase().contains(outoCompleteText.toUpperCase())
					|| proVo.getProductCode().toUpperCase().contains(outoCompleteText.toUpperCase())) {
				resultList.add(proVo.getProductCode() + " - " + proVo.getProductName());
			}
		}

		return resultList;
	}

	public void handleSelectProduct(SelectEvent event) {
		Object object = event.getObject();

		chooseProduct(object.toString(), null);
		this.completeSearchProduct = "";
	}

	public void handleKeyPressProduct() {
		if (completeSearchProduct != null && !completeSearchProduct.equals("")) {
			chooseProduct(null, completeSearchProduct);
		}

		this.completeSearchProduct = "";
	}

	private void chooseProduct(String productCode, String barcode) {

		if (userSession.getCompanyId() != null) {
			ProductVO productVO = new ProductVO();
			if (productCode != null && !productCode.equals("")) {
				String[] split = productCode.split("-");
				productVO = productService.findByProductCodeOrBarcodeAndCompanyId(split[0].toString(), null,
						userSession.getCompanyId());
			} else if (barcode != null && !barcode.equals("")) {
				productVO = productService.findByProductCodeOrBarcodeAndCompanyId(null, barcode,
						userSession.getCompanyId());
			}

			if (salesOrderDtlVOList.size() > 0) {
				boolean checkData = false;
				for (int i = 0; i < salesOrderDtlVOList.size(); i++) {
					SalesOrderDtlVO order = (SalesOrderDtlVO) salesOrderDtlVOList.get(i);
					if (Integer.parseInt(order.getProductId() + "") == Integer
							.parseInt(productVO.getProductId() + "")) {
						checkData = true;

						Double orderQty = order.getOrderQty().doubleValue() + 1;
						Double totalPriceBaru = orderQty.doubleValue() * productVO.getUnitPrice().doubleValue();
						Double totalPriceLama = order.getTotalPrice();
						Double subTotalNew = salesOrderVoHeader.getSubTotal().doubleValue()
								- totalPriceLama.doubleValue();
						salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceBaru.doubleValue());

						order.setOrderQty(orderQty);
						order.setTotalPrice(order.getOrderQty().doubleValue() * productVO.getUnitPrice().doubleValue());

						if (salesOrderVoHeader.getTaxValue() != null) {
							Double totalSub = salesOrderVoHeader.getSubTotal().doubleValue()
									- salesOrderVoHeader.getSubDiskon().doubleValue();
							salesOrderVoHeader.setSubTaxValue(
									totalSub.doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue() / 100));
						}

						sumTotal();
						break;
					}
				}

				if (!checkData) {
					SalesOrderDtlVO order = new SalesOrderDtlVO();
					order.setProductId(productVO.getProductId());
					order.setProductName(productVO.getProductName());
					order.setIngredientFlag(productVO.getIngredientFlag());
					order.setUnitPrice(productVO.getUnitPrice());
					order.setOrderUm(productVO.getUmId());
					order.setOrderUmName(productVO.getUmName());
					order.setOrderQty(new Double(1));
					order.setTotalPrice(new Double(1) * productVO.getUnitPrice().doubleValue());

					salesOrderVoHeader.setSubTotal(
							salesOrderVoHeader.getSubTotal().doubleValue() + order.getTotalPrice().doubleValue());

					order.setCategoryCode(productVO.getCategoryCode());
					order.setDiscPercent(new Double(0));

					if (salesOrderVoHeader.getTaxValue() != null) {
						Double totalSub = salesOrderVoHeader.getSubTotal().doubleValue()
								- salesOrderVoHeader.getSubDiskon().doubleValue();
						salesOrderVoHeader.setSubTaxValue(
								totalSub.doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue() / 100));
					}

					salesOrderDtlVOList.add(order);
					salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
					sumTotal();
				}

			} else {
				SalesOrderDtlVO order = new SalesOrderDtlVO();
				order.setProductId(productVO.getProductId());
				order.setProductName(productVO.getProductName());
				order.setIngredientFlag(productVO.getIngredientFlag());
				order.setUnitPrice(productVO.getUnitPrice());
				order.setOrderUm(productVO.getUmId());
				order.setOrderUmName(productVO.getUmName());
				order.setDiscPercent(new Double(0));
				order.setOrderQty(new Double(1));
				order.setTotalPrice(new Double(1) * productVO.getUnitPrice().doubleValue());
				order.setCategoryCode(productVO.getCategoryCode());

				salesOrderVoHeader.setSubTotal(
						salesOrderVoHeader.getSubTotal().doubleValue() + order.getTotalPrice().doubleValue());
				if (salesOrderVoHeader.getTaxValue() != null) {
					salesOrderVoHeader.setSubTaxValue(salesOrderVoHeader.getSubTotal().doubleValue()
							* (salesOrderVoHeader.getTaxValue().doubleValue() / 100));
				}

				salesOrderDtlVOList.add(order);
				salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
				sumTotal();
			}
		}

		/*
		 * if(userSession.getCompanyId() !=null){ String[] split =
		 * productCode.split("-"); ProductVO vo =
		 * productService.findByProductCodeAndCompanyId(split[0].toString(),
		 * userSession.getCompanyId()); SalesOrderDtlVO order = new
		 * SalesOrderDtlVO();
		 * 
		 * order.setProductId(vo.getProductId());
		 * order.setProductName(vo.getProductName());
		 * order.setIngredientFlag(vo.getIngredientFlag());
		 * order.setUnitPrice(vo.getUnitPrice());
		 * order.setOrderUm(vo.getUmId()); order.setOrderUmName(vo.getUmName());
		 * order.setDiscPercent(new Double(0)); order.setOrderQty(new
		 * Double(1)); order.setTotalPrice(new Double(1) *
		 * vo.getUnitPrice().doubleValue());
		 * order.setCategoryCode(vo.getCategoryCode());
		 * 
		 * salesOrderDtlVOList.add(order); }
		 */

	}

	public void sumTotal() {
		Double sumTotal = new Double(0);
		Double subTotal = salesOrderVoHeader.getSubTotal().doubleValue();
		Double subDiskon = salesOrderVoHeader.getSubDiskon().doubleValue();
		Double subTaxValue = new Double(0);
		if (salesOrderVoHeader.getSubTaxValue() != null) {
			subTaxValue = salesOrderVoHeader.getSubTaxValue().doubleValue();
		}

		if (salesOrderVoHeader.getDeliveryCost() != null) {
			sumTotal = subTotal.doubleValue() - subDiskon.doubleValue() + subTaxValue.doubleValue()
					+ salesOrderVoHeader.getDeliveryCost();
		} else {
			sumTotal = (subTotal.doubleValue() - subDiskon.doubleValue()) + subTaxValue.doubleValue();
		}

		salesOrderVoHeader.setSumTotal(sumTotal);
	}

	public void changeQtyProduct(SalesOrderDtlVO salesOrderDtlVO) {
		for (int i = 0; i < salesOrderDtlVOList.size(); i++) {
			SalesOrderDtlVO salesOrdetEdit = (SalesOrderDtlVO) salesOrderDtlVOList.get(i);
			if (Integer.parseInt(salesOrderDtlVO.getProductId() + "") == Integer
					.parseInt(salesOrdetEdit.getProductId() + "")) {
				Double totalPriceBaru = salesOrderDtlVO.getOrderQty().doubleValue()
						* salesOrderDtlVO.getUnitPrice().doubleValue();
				Double totalPriceLama = salesOrdetEdit.getTotalPrice();
				Double subTotalNew = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceLama.doubleValue();
				salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceBaru.doubleValue());

				salesOrdetEdit = salesOrderDtlVO;
				salesOrdetEdit.setTotalPrice(totalPriceBaru);
				sumTotal();
				break;

			}
		}

	}

	public void buttonCancel() {
		initBean();
	}

	public void viewDetailCustomer() {
		if (salesOrderVoHeader.getCustomerId() == null || salesOrderVoHeader.getCustomerId().toString().equals("")) {
			viewCustomer = false;
			ppn = null;
		} else {
			viewCustomer = true;
			customerSales = customerService2.findById(salesOrderVoHeader.getCustomerId());

			if (customerSales.getNpwpNo() != null) {
				ppn = "PPN";
			} else {
				ppn = null;
			}
		}
	}

	public void buttonDelete() {

		salesOrderDtlVOList.remove(salesOrderDtlTableDel);
		salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
		disableButtonOnRowClicked = false;

	}

	public void buttonEditProduct() {
		soDtlVoDialogProduct = salesOrderDtlTableDel;
	}

	public void onRowSelect(SelectEvent event) {
		disableButtonOnRowClicked = true;
	}

	public void onRowUnSelect(SelectEvent event) {
		disableButtonOnRowClicked = false;
	}

	public void buttonCancelDialogProduct() {
		soDtlVoDialogProduct = new SalesOrderDtlVO();
	}

	public void buttonSaveDialogProduct() {
		for (int i = 0; i < salesOrderDtlVOList.size(); i++) {
			SalesOrderDtlVO salesOrdetEdit = (SalesOrderDtlVO) salesOrderDtlVOList.get(i);
			if (Integer.parseInt(soDtlVoDialogProduct.getProductId() + "") == Integer
					.parseInt(salesOrdetEdit.getProductId() + "")) {
				Double totalPriceBaru = soDtlVoDialogProduct.getOrderQty().doubleValue()
						* soDtlVoDialogProduct.getUnitPrice().doubleValue();
				Double totalPriceLama = salesOrdetEdit.getTotalPrice();
				Double subTotalNew = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceLama.doubleValue();
				salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceBaru.doubleValue());

				salesOrdetEdit = soDtlVoDialogProduct;
				salesOrdetEdit.setTotalPrice(totalPriceBaru);
				sumTotal();
				break;

			}
		}
	}

	public void buttonDelTablePayment(PaymentTypeVO payTypeVO) {
		dataPaymentTypeVoList.remove(payTypeVO);
	}

	public void buttonCancelDialogCustomer() {
		customerVO = new CustomerVO();
	}

	/*
	 * public void buttonSaveDialogCustomer(){ if(userSession.getCompanyId()
	 * !=null){ Company company = new Company();
	 * company.setCompanyId(userSession.getCompanyId());
	 * customerVO.setCompany(company); }
	 * 
	 * if(userSession.getOutletId() !=null){ Outlet outlet = new Outlet();
	 * outlet.setOutletId(userSession.getOutletId());
	 * customerVO.setRegisterOutlet(outlet); }
	 * 
	 * City city = new City(); city.setCityId(customerVO.getCityId());
	 * customerVO.setCity(city);
	 * 
	 * customerService.save(customerVO, userSession.getUserCode()); }
	 */

	public boolean validateRegister() {
		boolean valid = true;
		if (salesOrderVoHeader.getRegisterId() == null) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
					" Register " + facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			// facesUtils.getResourceBundleStringValue("formPoOutlet")+"
			// "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

			valid = false;
		}

		return valid;
	}

	public void buttonPayment() {
		dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
		if (validateRegister()) {
			salesOrderVoHeader.setSumTotalPayment(salesOrderVoHeader.getSumTotal());

			RequestContext.getCurrentInstance().execute("PF('dialogPembayaranRetail').show()");
		} else {
			RequestContext.getCurrentInstance().execute("PF('dialogPembayaranRetail').hide()");
		}
	}

	public void buttonDialogPayment(PaymentTypeVO payTypeVO) {
		boolean check = false;
		if (dataPaymentTypeVoList.size() > 0) {
			for (int i = 0; i < dataPaymentTypeVoList.size(); i++) {
				PaymentTypeVO vo = (PaymentTypeVO) dataPaymentTypeVoList.get(i);
				if (Integer.parseInt(vo.getPaytypeId() + "") == Integer.parseInt(payTypeVO.getPaytypeId() + "")) {
					check = true;
				}
			}

			if (!check) {
				payTypeVO.setTotalPayment(null);
				dataPaymentTypeVoList.add(payTypeVO);
			}

		} else {
			payTypeVO.setTotalPayment(null);
			dataPaymentTypeVoList.add(payTypeVO);
		}
	}

	public void changeTotalPayment(PaymentTypeVO payTypeVO) {
		Double totalPayment = new Double(0);
		for (int i = 0; i < dataPaymentTypeVoList.size(); i++) {
			PaymentTypeVO paymentVo = (PaymentTypeVO) dataPaymentTypeVoList.get(i);
			if (paymentVo.getTotalPayment() != null) {
				totalPayment = totalPayment.doubleValue() + paymentVo.getTotalPayment().doubleValue();
			} else {
				totalPayment = totalPayment.doubleValue() + new Double(0);
			}
		}

		salesOrderVoHeader.setPaymentAmount(totalPayment.doubleValue());
		Double totalChange = totalPayment.doubleValue() - salesOrderVoHeader.getSumTotalPayment().doubleValue();
		salesOrderVoHeader.setChangeAmount(totalChange);
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SalesOrderBean.logger = logger;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public List<SelectItem> getRegisterSelectItemList() {
		return registerSelectItemList;
	}

	public void setRegisterSelectItemList(List<SelectItem> registerSelectItemList) {
		this.registerSelectItemList = registerSelectItemList;
	}

	public SalesOrderVO getSalesOrderVoHeader() {
		return salesOrderVoHeader;
	}

	public void setSalesOrderVoHeader(SalesOrderVO salesOrderVoHeader) {
		this.salesOrderVoHeader = salesOrderVoHeader;
	}

	public List<SalesOrderDtlVO> getSalesOrderDtlVOList() {
		return salesOrderDtlVOList;
	}

	public void setSalesOrderDtlVOList(List<SalesOrderDtlVO> salesOrderDtlVOList) {
		this.salesOrderDtlVOList = salesOrderDtlVOList;
	}

	public String getCompleteSearchProduct() {
		return completeSearchProduct;
	}

	public void setCompleteSearchProduct(String completeSearchProduct) {
		this.completeSearchProduct = completeSearchProduct;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<ProductVO> getProductVoList() {
		return productVoList;
	}

	public void setProductVoList(List<ProductVO> productVoList) {
		this.productVoList = productVoList;
	}

	/*
	 * public CustomerService getCustomerService() { return customerService; }
	 * 
	 * public void setCustomerService(CustomerService customerService) {
	 * this.customerService = customerService; }
	 */

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public List<SelectItem> getCitySelectItem() {
		return citySelectItem;
	}

	public void setCitySelectItem(List<SelectItem> citySelectItem) {
		this.citySelectItem = citySelectItem;
	}

	public CustomerVO getCustomerVO() {
		return customerVO;
	}

	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
	}

	public SalesOrderDtlVO getSalesOrderDtlTableDel() {
		return salesOrderDtlTableDel;
	}

	public void setSalesOrderDtlTableDel(SalesOrderDtlVO salesOrderDtlTableDel) {
		this.salesOrderDtlTableDel = salesOrderDtlTableDel;
	}

	public boolean isDisableButtonOnRowClicked() {
		return disableButtonOnRowClicked;
	}

	public void setDisableButtonOnRowClicked(boolean disableButtonOnRowClicked) {
		this.disableButtonOnRowClicked = disableButtonOnRowClicked;
	}

	public SalesOrderDtlVO getSoDtlVoDialogProduct() {
		return soDtlVoDialogProduct;
	}

	public void setSoDtlVoDialogProduct(SalesOrderDtlVO soDtlVoDialogProduct) {
		this.soDtlVoDialogProduct = soDtlVoDialogProduct;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	public SystemProperty getSystemPropTaxType() {
		return systemPropTaxType;
	}

	public void setSystemPropTaxType(SystemProperty systemPropTaxType) {
		this.systemPropTaxType = systemPropTaxType;
	}

	public SalesOrderRetailService getSalesOrderRetailService() {
		return salesOrderRetailService;
	}

	public void setSalesOrderRetailService(SalesOrderRetailService salesOrderRetailService) {
		this.salesOrderRetailService = salesOrderRetailService;
	}

	public List<PaymentTypeVO> getDataPaymentTypeVoList() {
		return dataPaymentTypeVoList;
	}

	public void setDataPaymentTypeVoList(List<PaymentTypeVO> dataPaymentTypeVoList) {
		this.dataPaymentTypeVoList = dataPaymentTypeVoList;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public List<PaymentTypeVO> getPaymentTypeVoList() {
		return paymentTypeVoList;
	}

	public void setPaymentTypeVoList(List<PaymentTypeVO> paymentTypeVoList) {
		this.paymentTypeVoList = paymentTypeVoList;
	}

	public PaymentTypeService getPaymentTypeService() {
		return paymentTypeService;
	}

	public void setPaymentTypeService(PaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public List<SelectItem> getCustomerSelectItem() {
		return customerSelectItem;
	}

	public void setCustomerSelectItem(List<SelectItem> customerSelectItem) {
		this.customerSelectItem = customerSelectItem;
	}

	public CustomerService getCustomerService2() {
		return customerService2;
	}

	public void setCustomerService2(CustomerService customerService2) {
		this.customerService2 = customerService2;
	}

	public boolean isViewCustomer() {
		return viewCustomer;
	}

	public void setViewCustomer(boolean viewCustomer) {
		this.viewCustomer = viewCustomer;
	}

	public CustomerSales getCustomerSales() {
		return customerSales;
	}

	public void setCustomerSales(CustomerSales customerSales) {
		this.customerSales = customerSales;
	}

	public String getPpn() {
		return ppn;
	}

	public void setPpn(String ppn) {
		this.ppn = ppn;
	}

	public ItemStockService getItemStockService() {
		return itemStockService;
	}

	public void setItemStockService(ItemStockService itemStockService) {
		this.itemStockService = itemStockService;
	}

}
