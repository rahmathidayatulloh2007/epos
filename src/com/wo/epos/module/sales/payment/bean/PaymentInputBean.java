package com.wo.epos.module.sales.payment.bean;

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
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.util.InputEnableMap;
import com.wo.epos.common.vo.UploadFileVO;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.customer.service.CustomerService;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.sales.payment.service.PaymentService;
import com.wo.epos.module.sales.payment.vo.PaymentVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;
import com.wo.epos.module.sales.salesOrder.service.SalesOrderService;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name = "paymentInputBean")
@ViewScoped
public class PaymentInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -5945352149604532474L;

	static Logger logger = Logger.getLogger(PaymentInputBean.class);

	private static final String UPLOAD_FUNC = "INVOICE_TRANSFER";

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	@ManagedProperty(value = "#{salesOrderService}")
	private SalesOrderService salesOrderService;
	@ManagedProperty(value = "#{customerService2}")
	private CustomerService customerService2;
	@ManagedProperty(value = "#{paymentService}")
	private PaymentService paymentService;

	private SalesOrder salesOrder;

	private PaymentVO paymentVO;
	private PaymentTypeVO paymentTypeVO;
	private SalesOrderDtlVO soDtlVO;

	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> customerSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> salesOrderSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> paymentMethodSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> paymentTypeSelectItem = new ArrayList<SelectItem>();

	private List<Long> paymentTypeIdList = new ArrayList<Long>();

	private List<SalesOrderDtlVO> soDtlVOList = new ArrayList<SalesOrderDtlVO>();
	private List<PaymentTypeVO> paymentTypeVOs = new ArrayList<PaymentTypeVO>();

	private String MODE_TYPE;

	private Long soId;

	@ManagedProperty(value = "#{mbImageStreamer}")
	private MbImageStreamer mbImageStreamer;
	/* private static final long serialVersionUID = 4559151763066211481L; */
	private UploadedFile uploadedFile;
	private String streamUploadId;
	private InputEnableMap mapEnableInput = new InputEnableMap();

	@PostConstruct
	public void postConstruct() {
		super.init();
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		modeAdd();

		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for (CompanyVO comVO : companySelectList) {
			companySelectItem.add(new SelectItem(comVO.getCompanyId(), comVO.getCompanyName()));
		}

		customerSelectItem = new ArrayList<SelectItem>();
		List<CustomerSalesVO> customerSalesVOList = customerService2.searchCustomerList();
		for (CustomerSalesVO custVO : customerSalesVOList) {
			customerSelectItem.add(new SelectItem(custVO.getCustomerId(), custVO.getCustomerName()));
		}

		if (userSession.getCompanyId() != null) {
			paymentVO.setCompanyId(userSession.getCompanyId());
			paymentVO.setOutletId(userSession.getOutletId());
		}

		paymentMethodSelectItem = new ArrayList<SelectItem>();
		List<ParameterDtl> paymentMethods = paymentService.parameterDtlList();
		for (ParameterDtl dtl : paymentMethods) {
			paymentMethodSelectItem.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
		}/*
		 * paymentTypeSelectItem = new ArrayList<SelectItem>();
		 * List<PaymentTypeVO> paymentTypes =
		 * paymentService.searchDataPaymentType(); for (PaymentTypeVO dtl :
		 * paymentTypes) { paymentTypeSelectItem.add(new
		 * SelectItem(dtl.getPaytypeId(), dtl.getPaytypeName())); }
		 */

		if (paymentTypeVO.getInvFile() != null) {
			String uploadId = mbImageStreamer.returnCurrentTime();
			this.streamUploadId = uploadId;
			mbImageStreamer.clearUpload(getUploadFuncId());
			UploadFileVO file = new UploadFileVO(uploadId, paymentTypeVO.getInvFile());
			mbImageStreamer.addUpload(getUploadFuncId(), file);
		}

	}

	public void modeAdd() {
		paymentVO = new PaymentVO();
		paymentTypeVO = new PaymentTypeVO();
		salesOrder = new SalesOrder();
		soDtlVO = new SalesOrderDtlVO();
		paymentTypeVOs = new ArrayList<PaymentTypeVO>();
		salesOrderSelectItem = new ArrayList<SelectItem>();
		soDtlVOList = new ArrayList<SalesOrderDtlVO>();
	}

	public void changeCompany() {
		outletSelectItem = new ArrayList<SelectItem>();
		List<Outlet> outletSelectList = outletService.findOutletByCompany(paymentVO.getCompanyId());
		for (Outlet outVO : outletSelectList) {
			outletSelectItem.add(new SelectItem(outVO.getOutletId(), outVO.getOutletName()));
		}

	}

	public void changeCustomer() {
		salesOrderSelectItem.clear();
		List<SalesOrder> soSelectList = salesOrderService.findSalesOrderByCustomer(paymentVO.getCustomerId());
		for (SalesOrder soVO : soSelectList) {
			salesOrderSelectItem.add(new SelectItem(soVO.getSoId(), soVO.getSoNo() +" ( "+soVO.getStatus().getParameterDtlName()+" ) "));
		}
	}

	public void changeSo() {

		soDtlVOList = paymentService.searchListSoDtlVO(paymentVO.getSoId());
		Double subTotal = 0d;
		for (SalesOrderDtlVO vo : soDtlVOList) {
			subTotal += vo.getTotalPriceDiscount();
		}
		paymentVO.setSubTotal(subTotal);

		if (soDtlVO.getDiscValue() != null) {
			paymentVO.setSumTotal(paymentVO.getSubTotal() - soDtlVO.getDiscValue());

		} else {
			paymentVO.setSumTotal(paymentVO.getSubTotal());
		}

	}

	public void changePaymentMethod(int idx) {

		List<PaymentTypeVO> paymentTypeVOList = paymentService
				.searchDataPaymentByPaymentMethod(paymentTypeVOs.get(idx).getPaymentMethodCode());
		List paymentTypeSelectItemNew = new ArrayList<SelectItem>();
		for (PaymentTypeVO vo : paymentTypeVOList) {
			paymentTypeSelectItemNew.add(new SelectItem(vo.getPaytypeId(), vo.getPaytypeName()));
		}
		paymentTypeVOs.get(idx).setPaymentTypeSelectItem(paymentTypeSelectItemNew);

	}

	public void changeSubPayment() {
		Double paymentAmount = 0D;
		for (PaymentTypeVO vo : paymentTypeVOs) {
			if (vo.getPayValue() != null) {
				paymentAmount += vo.getPayValue();
			}
		}
		paymentVO.setPaymentAmount(paymentAmount);
		paymentVO.setChangeAmount(paymentVO.getPaymentAmount() - paymentVO.getSumTotal());

	}

	public boolean validatePaymentType() {
		boolean valid = true;

		if (paymentTypeVOs.size() > 0) {
			for (int i = 0; i < paymentTypeVOs.size(); i++) {

				PaymentTypeVO vo = (PaymentTypeVO) paymentTypeVOs.get(i);

				if (vo.getPaymentMethodCode() == null || vo.getPaymentMethodCode().equals("")) {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPaymentTypeMethod") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

					valid = false;
				}

				if (vo.getPaytypeId() == null) {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPaymentTypeTitle") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

					valid = false;
				}

				// Cek
				if (paymentTypeVO.getPaymentMethodCode().equals(CommonConstants.PAYMENT_CEK)) {
					if (vo.getGiroNo() == null) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
								facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
										+ facesUtils.getResourceBundleStringValue("formPaymentGiroNo") + " "
										+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

						valid = false;
					}

					if (vo.getPayDate() == null) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
								facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
										+ facesUtils.getResourceBundleStringValue("formPayDate") + " "
										+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

						valid = false;
					}
				}

				// Transfer
				if (paymentTypeVO.getPaymentMethodCode().equals(CommonConstants.PAYMENT_CEK)) {
					if (vo.getNoRek() == null) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
								facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
										+ facesUtils.getResourceBundleStringValue("formNoRek") + " "
										+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

						valid = false;
					}

					if (vo.getPayUnderName() == null || vo.getPayUnderName().trim().equals("")) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
								facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
										+ facesUtils.getResourceBundleStringValue("formPayUnderName") + " "
										+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

						valid = false;
					}

					if (vo.getUploadedFile() == null) {
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
								facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
										+ facesUtils.getResourceBundleStringValue("foumUploadedFile") + " "
										+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

						valid = false;
					}
				}

				if (vo.getPayValue() == null) {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPayValue") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));

					valid = false;
				}

				if (vo.getPayName() == null || vo.getPayName().trim().equals("")) {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPayValue") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
					valid = false;
				}

				else {
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
							facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
									+ facesUtils.getResourceBundleStringValue("formPaymentTypeTitle") + " "
									+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
					valid = false;
				}

			}

		} else {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
					facesUtils.getResourceBundleStringValue("formProductErrMsgMinim"));

			valid = false;

		}

		return valid;
	}

	public void reinit() {
		PaymentTypeVO vo = new PaymentTypeVO();
		paymentTypeVOs.add(vo);
	}

	public void deleteDetail(PaymentTypeVO paymentTypeVO) {

		if (paymentTypeVO.getPayValue() != null) {
			paymentVO.setPaymentAmount(paymentVO.getPaymentAmount() - paymentTypeVO.getPayValue());
			paymentVO.setChangeAmount(paymentVO.getPaymentAmount() - paymentVO.getSumTotal());
			paymentTypeVOs.remove(paymentTypeVO);
		} else {
			paymentTypeVOs.remove(paymentTypeVO);
		}

		System.out.println("paymentTypeVOs.size() ==============> " + paymentTypeVOs.size());
	}

	public void savePayment() {
		try {
			if(paymentVO.getChangeAmount() < 0){
				paymentVO.setStatusCode(CommonConstants.SO_PARTIAL);
			}
			else{
				paymentVO.setStatusCode(CommonConstants.SO_PAYMENT);
			}
			paymentService.savePayment(paymentVO, paymentTypeVOs, userSession.getUserCode());

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, " Save Berhasil ");

			modeAdd();

		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, " Save Gagal ");
			salesOrderService.rollback();
		}

	}

	public String getUploadFuncId() {
		return UPLOAD_FUNC;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PaymentInputBean.logger = logger;
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

	public SalesOrderService getSalesOrderService() {
		return salesOrderService;
	}

	public void setSalesOrderService(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}

	public CustomerService getCustomerService2() {
		return customerService2;
	}

	public void setCustomerService2(CustomerService customerService2) {
		this.customerService2 = customerService2;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	/*
	 * public SalesOrderDistributorInputBean getSalesOrderDistributorInputBean()
	 * { return salesOrderDistributorInputBean; }
	 * 
	 * public void
	 * setSalesOrderDistributorInputBean(SalesOrderDistributorInputBean
	 * salesOrderDistributorInputBean) { this.salesOrderDistributorInputBean =
	 * salesOrderDistributorInputBean; }
	 */
	public SalesOrder getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(SalesOrder salesOrder) {
		this.salesOrder = salesOrder;
	}

	public PaymentVO getPaymentVO() {
		return paymentVO;
	}

	public void setPaymentVO(PaymentVO paymentVO) {
		this.paymentVO = paymentVO;
	}

	public PaymentTypeVO getPaymentTypeVO() {
		return paymentTypeVO;
	}

	public void setPaymentTypeVO(PaymentTypeVO paymentTypeVO) {
		this.paymentTypeVO = paymentTypeVO;
	}

	public SalesOrderDtlVO getSoDtlVO() {
		return soDtlVO;
	}

	public void setSoDtlVO(SalesOrderDtlVO soDtlVO) {
		this.soDtlVO = soDtlVO;
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

	public List<SelectItem> getCustomerSelectItem() {
		return customerSelectItem;
	}

	public void setCustomerSelectItem(List<SelectItem> customerSelectItem) {
		this.customerSelectItem = customerSelectItem;
	}

	public List<SelectItem> getSalesOrderSelectItem() {
		return salesOrderSelectItem;
	}

	public void setSalesOrderSelectItem(List<SelectItem> salesOrderSelectItem) {
		this.salesOrderSelectItem = salesOrderSelectItem;
	}

	public List<SelectItem> getPaymentMethodSelectItem() {
		return paymentMethodSelectItem;
	}

	public void setPaymentMethodSelectItem(List<SelectItem> paymentMethodSelectItem) {
		this.paymentMethodSelectItem = paymentMethodSelectItem;
	}

	public List<SelectItem> getPaymentTypeSelectItem() {
		return paymentTypeSelectItem;
	}

	public void setPaymentTypeSelectItem(List<SelectItem> paymentTypeSelectItem) {
		this.paymentTypeSelectItem = paymentTypeSelectItem;
	}

	public List<Long> getPaymentTypeIdList() {
		return paymentTypeIdList;
	}

	public void setPaymentTypeIdList(List<Long> paymentTypeIdList) {
		this.paymentTypeIdList = paymentTypeIdList;
	}

	public List<SalesOrderDtlVO> getSoDtlVOList() {
		return soDtlVOList;
	}

	public void setSoDtlVOList(List<SalesOrderDtlVO> soDtlVOList) {
		this.soDtlVOList = soDtlVOList;
	}

	public List<PaymentTypeVO> getPaymentTypeVOs() {
		return paymentTypeVOs;
	}

	public void setPaymentTypeVOs(List<PaymentTypeVO> paymentTypeVOs) {
		this.paymentTypeVOs = paymentTypeVOs;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public Long getSoId() {
		return soId;
	}

	public void setSoId(Long soId) {
		this.soId = soId;
	}

	public MbImageStreamer getMbImageStreamer() {
		return mbImageStreamer;
	}

	public void setMbImageStreamer(MbImageStreamer mbImageStreamer) {
		this.mbImageStreamer = mbImageStreamer;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getStreamUploadId() {
		return streamUploadId;
	}

	public void setStreamUploadId(String streamUploadId) {
		this.streamUploadId = streamUploadId;
	}

	public InputEnableMap getMapEnableInput() {
		return mapEnableInput;
	}

	public void setMapEnableInput(InputEnableMap mapEnableInput) {
		this.mapEnableInput = mapEnableInput;
	}

	public static String getUploadFunc() {
		return UPLOAD_FUNC;
	}

}
