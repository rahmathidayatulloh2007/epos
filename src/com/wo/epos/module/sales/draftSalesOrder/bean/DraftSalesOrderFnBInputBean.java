package com.wo.epos.module.sales.draftSalesOrder.bean;

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
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.UploadFileVO;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.inv.customer.service.CustomerService;
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.inv.item.service.ProductService;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.equipment.service.EquipmentService;
import com.wo.epos.module.master.equipment.vo.EquipmentVO;
import com.wo.epos.module.master.paymentType.service.PaymentTypeService;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.service.DiscountService;
import com.wo.epos.module.sales.discount.vo.DiscountVO;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrder;
import com.wo.epos.module.sales.draftSalesOrder.service.DraftSalesOrderFnBService;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.model.InvoiceDtl;
import com.wo.epos.module.sales.invoice.service.InvoiceService;
import com.wo.epos.module.sales.register.service.RegisterService;
import com.wo.epos.module.sales.register.vo.RegisterVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;

@ManagedBean(name = "draftSalesOrderFnBInputBean")
@ViewScoped
public class DraftSalesOrderFnBInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -4535698702549175765L;

	static Logger logger = Logger.getLogger(DraftSalesOrderFnBInputBean.class);
	
	@ManagedProperty(value = "#{draftSalesOrderFnBService}")
	private DraftSalesOrderFnBService draftSalesOrderFnBService;

	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;

	@ManagedProperty(value = "#{productService}")
	private ProductService productService;

	@ManagedProperty(value = "#{equipmentService}")
	private EquipmentService equipmentService;

	@ManagedProperty(value = "#{mbImageStreamer}")
	private MbImageStreamer mbImageStreamer;
								
	@ManagedProperty(value = "#{paymentTypeService}")
	private PaymentTypeService paymentTypeService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
									
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{registerService}")
	private RegisterService registerService;
	
	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{discountService}")								
	private DiscountService discountService;	
	
	@ManagedProperty(value = "#{invoiceService}")								
	private InvoiceService invoiceService;	

	private List<DraftSalesOrderDtlVO> salesOrderDtlVOList = new ArrayList<DraftSalesOrderDtlVO>();
	private List<CategoryVO> categoryVoList = new ArrayList<CategoryVO>();
	private List<ProductVO> productVoList = new ArrayList<ProductVO>();
	private List<EquipmentVO> equipmentVoList = new ArrayList<EquipmentVO>();
	private List<PaymentTypeVO> paymentTypeVoList = new ArrayList<PaymentTypeVO>();
	private List<PaymentTypeVO> dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
	private List<ParameterDtlVO> orderTypeList = new ArrayList<ParameterDtlVO>();
	private List<DiscountVO> discountVoList = new ArrayList<DiscountVO>();
	private List<DiscountDtl> discountDtlList = new ArrayList<DiscountDtl>();
	private List<DraftSalesOrderVO> dataTableDialogTagihan = new ArrayList<DraftSalesOrderVO>();
	private List<DraftSalesOrderDtlVO> salesOrderDtlVOTagihanList = new ArrayList<DraftSalesOrderDtlVO>();
	private List<DraftSalesOrderDtlVO> selectedSalesOrderDtlVO;
	private List<DraftSalesOrderVO> dataPaymentBillList = new ArrayList<DraftSalesOrderVO>();
	
	private List<SelectItem> registerSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();	

	private DraftSalesOrderVO salesOrderVoHeader = new DraftSalesOrderVO();
	private DraftSalesOrderDtlVO salesOrderDtlTableDel = new DraftSalesOrderDtlVO();
	private DraftSalesOrderDtlVO soDtlVoDialogProduct = new DraftSalesOrderDtlVO();
	private CustomerVO customerVO = new CustomerVO();
	
	private DraftSalesOrder salesOrderTagihan = new DraftSalesOrder();

	private Integer indexRowOrder;
	private Integer indexRowCategory;

	private String streamUploadId;
	private String MODE_TYPE;
	
	private boolean disableButtonOrder;
	private boolean enableButtonOther;
	private boolean disableButtonOnRowClicked;
	private boolean enableButtonOnProductClicked;

	private boolean enableButtonTagihanOnProductClicked;

	@PostConstruct
	public void postConstruct() {
		super.init();
		initBean();
		orderType();
		citySelect();
		disableButton();
		
	}

	public void initBean(){
		categoryVoList = new ArrayList<CategoryVO>();
		productVoList = new ArrayList<ProductVO>();
		registerSelectItemList = new ArrayList<SelectItem>();
		equipmentVoList = equipmentService.searchDataEquipmentByParameterCode(CommonConstants.EQUIPMENT_TABLE);		
		salesOrderDtlVOList = new ArrayList<DraftSalesOrderDtlVO>();
		salesOrderDtlVOTagihanList = new ArrayList<DraftSalesOrderDtlVO>();
		dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
		salesOrderVoHeader = new DraftSalesOrderVO();
		salesOrderDtlTableDel = new DraftSalesOrderDtlVO();
		salesOrderVoHeader.setItemAmount(new Long(0));
		salesOrderVoHeader.setSubTotal(new Double(0));
		salesOrderVoHeader.setPaymentAmount(new Double(0));
		salesOrderVoHeader.setChangeAmount(new Double(0));
		salesOrderVoHeader.setSubDiskon(new Double(0));
		salesOrderVoHeader.setSoDate(new Date());
		salesOrderVoHeader.setTitelDiscount("Diskon");
		customerVO = new CustomerVO();
		selectedSalesOrderDtlVO = new ArrayList<DraftSalesOrderDtlVO>();
		
		if(userSession.getOutletId() !=null){
			Outlet outletLogin = outletService.findById(userSession.getOutletId());
			salesOrderVoHeader.setOutletId(userSession.getOutletId());
			if(outletLogin.getCompany() !=null){
				salesOrderVoHeader.setCompanyId(outletLogin.getCompany().getCompanyId());
			}
			
			Outlet outlet = outletService.findById(userSession.getOutletId());			
			paymentTypeVoList = paymentTypeService.searchDataPaymentByCompany(outlet.getCompany().getCompanyId());
			
			List<RegisterVO> registerVoList = registerService.findRegisterByOutletId(userSession.getOutletId());
			for(RegisterVO vo : registerVoList){
				registerSelectItemList.add(new SelectItem(vo.getRegId(), vo.getCashierName()));
			}
		}
		
		if(userSession.getCompanyId() !=null){
			discountVoList = discountService.searchDiscountByCompanyList(userSession.getCompanyId());
		}
		
		
		
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
	}
	
	private void orderType(){
		orderTypeList = new ArrayList<ParameterDtlVO>();
		List<ParameterDtl> paramTypeList = parameterService.parameterDtlList(CommonConstants.SO_TYPE);
		for(ParameterDtl dtl : paramTypeList){
			ParameterDtlVO paramVo = new ParameterDtlVO();
			paramVo.setParameterDtlCode(dtl.getParameterDtlCode());
			paramVo.setParameterDtlName(dtl.getParameterDtlName());
			paramVo.setParameterDtlDesc(dtl.getParameterDtlDesc());
			orderTypeList.add(paramVo);
		}
	}
	
	private void citySelect(){
		citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = outletService.searchCityAll();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}
	}
	
	
	public void saveTemporary(){
		try{  
			 if(MODE_TYPE.equals(CommonConstants.MODE_TYPE_ADD))
			 {
				 salesOrderVoHeader.setStatusCode(CommonConstants.SO_NEW);
				 draftSalesOrderFnBService.saveSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());
				 
				 facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
			                null);
			 }
			 else if(MODE_TYPE.equals(CommonConstants.MODE_TYPE_EDIT))
			 {
				 draftSalesOrderFnBService.updateSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());
				 
				 facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
			                null);
			 }
			 
			 draftSalesOrderFnBService.flush();
			 buttonCancel();
			 
		}catch(Exception ex){
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);
			draftSalesOrderFnBService.rollback();
		}
	}
	
	public void saveSendKitchen(){
		try{  
			 if(MODE_TYPE.equals(CommonConstants.MODE_TYPE_ADD))
			 {
				 salesOrderVoHeader.setStatusCode(CommonConstants.SO_PROCEED);
				 draftSalesOrderFnBService.saveSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());
				 
				 facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
			                null);
			 }
			 else if(MODE_TYPE.equals(CommonConstants.MODE_TYPE_EDIT))
			 {
				 draftSalesOrderFnBService.updateSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());
				 
				 facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
			                null);
			 }
			 
			 draftSalesOrderFnBService.flush();
		     buttonCancel();				 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);
			draftSalesOrderFnBService.rollback();
		}
	}
	
	public void savePayment(){
		if(dataPaymentTypeVoList.size() <= 0)
		{
			//facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Silahkan pilih tipe pembayaran");
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
					facesUtils.retrieveMessage("errorSaveFnBSo"), 
	                null);
			RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').show()");
		}
		else
		{
			try
			{  
				if(salesOrderVoHeader.getStatusCode() !=null && !salesOrderVoHeader.getStatusCode().equals("")){
					  if(!salesOrderVoHeader.getStatusCode().equals(CommonConstants.SO_BILL))
					  {
						  salesOrderVoHeader.setStatusCode(CommonConstants.SO_PAYMENT);
						  draftSalesOrderFnBService.savePayment(salesOrderVoHeader, salesOrderDtlVOList, dataPaymentTypeVoList, userSession.getUserCode());
						  buttonCancel();	
						  draftSalesOrderFnBService.flush();
					  }
					  else
					  {
						  draftSalesOrderFnBService.saveSoDistroPaymentBill(salesOrderVoHeader, dataPaymentTypeVoList, userSession.getUserCode());
						  draftSalesOrderFnBService.flush();
						  List<Invoice> invList = invoiceService.searchDataInvoiceBySoIdAndStatus(salesOrderTagihan.getSoId(), CommonConstants.SOINVOICE_NEW);
						  if(invList.size() == 0){
							  buttonCancel();
						  }
					  }
					  RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').hide()");
				  }
				  else
				  {
					  draftSalesOrderFnBService.savePayment(salesOrderVoHeader, salesOrderDtlVOList, dataPaymentTypeVoList, userSession.getUserCode());	
					  draftSalesOrderFnBService.flush();	
					  
					  buttonCancel();
					  RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').hide()");
				  }
				 
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				//facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error :" + ex.getMessage());
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_ERROR,
						"frm001:messages",
						"Operation Failed : " + ex.getMessage(),
						null);
				draftSalesOrderFnBService.rollback();
			}
		}
	}
	
	public void onRowSelect(SelectEvent event){
		disableButtonOnRowClicked = true;
	}
	
	public void onRowUnSelect(SelectEvent event){
		disableButtonOnRowClicked = false;
	}
	
	public void buttonSaveDialogTagihan(){
		try{  
			if(salesOrderDtlVOTagihanList.isEmpty())
			{
			  	 salesOrderVoHeader.setStatusCode(CommonConstants.SO_BILL);
		         salesOrderTagihan = draftSalesOrderFnBService.saveSoDistroBill(salesOrderVoHeader, salesOrderDtlVOList, dataTableDialogTagihan, userSession.getUserCode());
		         draftSalesOrderFnBService.flush();
		         RequestContext.getCurrentInstance().execute("PF('dialogTagihan').hide()");
		         
		         facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
			                null);
			}
			else
			{
				//facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Masukan sisa tagihan");
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
						facesUtils.retrieveMessage("errorSaveDialogSo"), 
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
			draftSalesOrderFnBService.rollback();
		}
   }
	
	public void buttonCancelDialogTagihan(){
		salesOrderVoHeader.setStatusCode("");
		dataTableDialogTagihan = new ArrayList<DraftSalesOrderVO>();
	}
	
	public void buttonCancel(){
		initBean();
		disableButton();
		orderType();
	}
	
	public void buttonDelete(){
		salesOrderDtlVOList.remove(salesOrderDtlTableDel);
		salesOrderVoHeader.setSubTotal(salesOrderVoHeader.getSubTotal()
				.doubleValue() - salesOrderDtlTableDel.getTotalPrice().doubleValue());
		salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
		sumTotal();
		disableButtonOnRowClicked = false;
		if(salesOrderDtlVOList.isEmpty()){
			enableButtonOnProductClicked = false;
		}else if(salesOrderDtlVOList.size() <=1){
			enableButtonTagihanOnProductClicked = false;
		}
	}
	
	public void buttonEditProduct(){
		soDtlVoDialogProduct = salesOrderDtlTableDel;
	}
	
	public void buttonSaveDialogProduct(){
		//salesOrderVoHeader.setSubTotal(new Double(0));
		for(int i=0; i<salesOrderDtlVOList.size();i++){
			DraftSalesOrderDtlVO salesOrdetEdit = (DraftSalesOrderDtlVO)salesOrderDtlVOList.get(i);
			   if(Integer.parseInt(soDtlVoDialogProduct.getProductId()+"") == Integer.parseInt(salesOrdetEdit.getProductId()+"")){
				   Double totalPriceBaru = soDtlVoDialogProduct.getOrderQty().doubleValue()	* soDtlVoDialogProduct.getUnitPrice().doubleValue();
				   Double totalPriceLama = salesOrdetEdit.getTotalPrice();
				   Double subTotalNew  = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceLama.doubleValue();
				   salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceBaru.doubleValue());			
				   
				   salesOrdetEdit = soDtlVoDialogProduct;
				   salesOrdetEdit.setTotalPrice(totalPriceBaru);	
				   
				   sumTotal();
				   break;
				   
			   }
			   
		}
	}	
	
	
	public void buttonCancelDialogProduct(){
		soDtlVoDialogProduct = new DraftSalesOrderDtlVO();
	}
	
	private void disableButton(){
		disableButtonOrder = true;
		enableButtonOther = false;
		disableButtonOnRowClicked = false;
		enableButtonOnProductClicked = false;
		enableButtonTagihanOnProductClicked = false;
	}
	
	private void enableButton(){
		disableButtonOrder = false;
		enableButtonOther = true;
	}

	public boolean modeEdit(EquipmentVO equipDialog){
		boolean valid = false;
		if(equipDialog.getEquipmentId() !=null){
			DraftSalesOrderVO salesOrderVoEDit = draftSalesOrderFnBService.searchDataSoByEquipment(equipDialog.getEquipmentId(), equipDialog.getEquipmentStatusCode());
			if(salesOrderVoEDit !=null){
				salesOrderVoHeader = salesOrderVoEDit;
				 for(int i=0; i<salesOrderVoHeader.getSalesOrderDtlVoList().size();i++){
					 DraftSalesOrderDtlVO dtlVo = (DraftSalesOrderDtlVO) salesOrderVoHeader.getSalesOrderDtlVoList().get(i);
					     salesOrderDtlVOList.add(dtlVo);
					     
					     MODE_TYPE = CommonConstants.MODE_TYPE_EDIT;
					     valid = true;
				 }
				 
				 sumTotal();
				
			}	
		}
		
		return valid;
	}
	
	public void buttonOrder(Integer index, EquipmentVO equipDialog, ParameterDtlVO paramOrderType) {	
		
			if(equipDialog !=null){
				salesOrderVoHeader.setEquipmentId(equipDialog.getEquipmentId());
				salesOrderVoHeader.setEquipmentName(equipDialog.getEquipmentName());
			}
			salesOrderVoHeader.setSoTypeCode(paramOrderType.getParameterDtlCode());
			salesOrderVoHeader.setSoTypeName(paramOrderType.getParameterDtlName());

		indexRowOrder = index;
		categoryVoList = categoryService.searchAllCategoryParent(null);
		enableButton();
		
	}

	public void buttonCategory(Integer index, CategoryVO categoryVO) {
		indexRowCategory = index;
		productVoList = productService
				.searchAllProductByCategoryList(categoryVO.getCategoryId());
		for (int i = 0; i < productVoList.size(); i++) {
			ProductVO prod = (ProductVO) productVoList.get(i);

			if (prod.getImageFile() != null) {
				String uploadId = mbImageStreamer.returnCurrentTime();
				this.streamUploadId = uploadId;
				mbImageStreamer.clearUpload(prod.getProductId() + "");
				UploadFileVO file = new UploadFileVO(uploadId,
						prod.getImageFile());
				mbImageStreamer.addUpload(prod.getProductId() + "", file);
			}
		}

	}

	public void selectProduct(ProductVO productVO) {
		if (salesOrderDtlVOList.size() > 0) {
			boolean checkData = false;
			for (int i = 0; i < salesOrderDtlVOList.size(); i++) {
				DraftSalesOrderDtlVO order = (DraftSalesOrderDtlVO) salesOrderDtlVOList.get(i);
				if (Integer.parseInt(order.getProductId() + "") == Integer.parseInt(productVO.getProductId() + "")) {
					 checkData = true;
					 
					 Double orderQty = order.getOrderQty().doubleValue() + 1;
					 Double totalPriceBaru = orderQty.doubleValue() * productVO.getUnitPrice().doubleValue();
					 Double totalPriceLama = order.getTotalPrice();
					 Double subTotalNew  = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceLama.doubleValue();
					 salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceBaru.doubleValue());		
					 
					 order.setOrderQty(orderQty);
					 order.setTotalPrice(order.getOrderQty().doubleValue()
							* productVO.getUnitPrice().doubleValue());
					 
					sumTotal();
					break;
				}
			}

			if (!checkData) {
				DraftSalesOrderDtlVO order = new DraftSalesOrderDtlVO();
				order.setProductId(productVO.getProductId());
				order.setProductName(productVO.getProductName());
				order.setIngredientFlag(productVO.getIngredientFlag());
				order.setUnitPrice(productVO.getUnitPrice());
				order.setOrderUm(productVO.getUmId());
				order.setOrderUmName(productVO.getUmName());
				order.setOrderQty(new Double(1));
				order.setTotalPrice(new Double(1)
						* productVO.getUnitPrice().doubleValue());

				salesOrderVoHeader.setSubTotal(salesOrderVoHeader.getSubTotal()
						.doubleValue() + order.getTotalPrice().doubleValue());
				
				order.setCategoryCode(productVO.getCategoryCode());
				Boolean flagDisc = false;
				if(discountDtlList !=null && discountDtlList.size() > 0){
					for(int i=0; i<discountDtlList.size(); i++){
						  DiscountDtl discDtl = (DiscountDtl)discountDtlList.get(i);
						  if(discDtl.getCategory() !=null){
				    		   if(productVO.getCategoryCode().equals(discDtl.getCategory().getCategoryCode())){
				    			    flagDisc = true;
                                    order.setDiscPercent(discDtl.getDiscount().getDiscountValue());
                                    Double totalDiskon = salesOrderVoHeader.getSubTotal().doubleValue() * (order.getDiscPercent().doubleValue()/100);
                                    salesOrderVoHeader.setSubDiskon(totalDiskon);
				    		   }
						  }	   
					}
				}	
				
				if(flagDisc == false){
					order.setDiscPercent(new Double(0));
				}
				
				salesOrderDtlVOList.add(order);
				salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
				sumTotal();
			}

		} else {
			DraftSalesOrderDtlVO order = new DraftSalesOrderDtlVO();
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

			salesOrderVoHeader.setSubTotal(salesOrderVoHeader.getSubTotal().doubleValue() + order.getTotalPrice().doubleValue());
			salesOrderDtlVOList.add(order);
			salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
			sumTotal();
		}
		
		enableButtonOnProductClicked = true;
		
		if (salesOrderDtlVOList.size() > 1) {
			enableButtonTagihanOnProductClicked = true;
		}else{
			enableButtonTagihanOnProductClicked = false;
		}
	}
	
	public void sumTotal() {
		Double sumTotal = new Double(0);
		Double subTotal = salesOrderVoHeader.getSubTotal().doubleValue();
		Double subDiskon = salesOrderVoHeader.getSubDiskon().doubleValue();

		if (salesOrderVoHeader.getOrderPrice() != null) {
			sumTotal = subTotal.doubleValue() - subDiskon.doubleValue()
					+ salesOrderVoHeader.getOrderPrice();
		} else {
			sumTotal = subTotal.doubleValue() - subDiskon.doubleValue();
		}

		salesOrderVoHeader.setSumTotal(sumTotal);
	}
	
	public void buttonPayment(){
		if(salesOrderVoHeader.getRegisterId() != null)
		{
			if(salesOrderTagihan !=null && salesOrderTagihan.getSoId() !=null){
				dataPaymentBillList = new ArrayList<DraftSalesOrderVO>();
				dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
				salesOrderVoHeader.setSumTotalPayment(null);
				salesOrderVoHeader.setPaymentAmount(null);
				salesOrderVoHeader.setChangeAmount(null);
				List<Invoice> invoList = invoiceService.searchDataInvoiceBySoId(salesOrderTagihan.getSoId());
				for(int i=0; i<invoList.size(); i++){
					 Invoice inv = (Invoice)invoList.get(i);
					 DraftSalesOrderVO soBill = new DraftSalesOrderVO();
					 Double subTotalBill = new Double(0);
					 for(int j=0; j<inv.getInvoiceDtlList().size(); j++){
						  InvoiceDtl invDtl = (InvoiceDtl)inv.getInvoiceDtlList().get(j);
						  
						  Double price = invDtl.getSalesOrderDtl().getUnitPrice();
						  Double qyt = invDtl.getSalesOrderDtl().getOrderQty();
						  Double diskon = invDtl.getSalesOrderDtl().getDiscPercent()!=null?invDtl.getSalesOrderDtl().getDiscPercent():0;
						  
						  Double total = qyt.doubleValue() * price.doubleValue();
						 
						  if(diskon > 0){
						       subTotalBill = subTotalBill.doubleValue() + (qyt.doubleValue() * price.doubleValue());
						  }else{
							  subTotalBill = subTotalBill.doubleValue() + (total.doubleValue() - (total.doubleValue() * diskon.doubleValue()/100));
						  }
					 }
					 
					 
					 soBill.setSubTotalBill(subTotalBill);
					 soBill.setInvoiceId(inv.getSoInvId());
					 soBill.setStatusInvoiceCode(inv.getStatusCode());
					 dataPaymentBillList.add(soBill);
					 
					 RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').show()");
				}
			}else{
				salesOrderVoHeader.setSumTotalPayment(salesOrderVoHeader.getSumTotal());
				RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').show()");
			}			
		}else{
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Silahkan pilih register id");
		}
		
	}
	
	public void buttonDialogPaymentBill(DraftSalesOrderVO salesOrderVo){
		  salesOrderVoHeader.setSumTotalPayment(salesOrderVo.getSubTotalBill());
		  salesOrderVoHeader.setInvoiceId(salesOrderVo.getInvoiceId());
	}
	
	public void buttonDialogPayment(PaymentTypeVO payTypeVO){
		boolean check = false;
		if(dataPaymentTypeVoList.size() > 0){
			for(int i=0; i<dataPaymentTypeVoList.size(); i++){
				PaymentTypeVO vo = (PaymentTypeVO)dataPaymentTypeVoList.get(i);
				if(Integer.parseInt(vo.getPaytypeId()+"") == Integer.parseInt(payTypeVO.getPaytypeId()+"")){
					check = true;
				}    
			}
			
			if(!check){
			     dataPaymentTypeVoList.add(payTypeVO);
			}
		    
		}else{
			dataPaymentTypeVoList.add(payTypeVO);
		}
	}
	
    public void changeTotalPayment(PaymentTypeVO payTypeVO){
    	Double totalPayment = new Double(0);
    	for(int i=0; i<dataPaymentTypeVoList.size(); i++){
    		PaymentTypeVO paymentVo = (PaymentTypeVO)dataPaymentTypeVoList.get(i);
    		if(paymentVo.getTotalPayment() !=null){
    		    totalPayment = totalPayment.doubleValue() + paymentVo.getTotalPayment().doubleValue();
    		}else{
    			totalPayment = totalPayment.doubleValue() + new Double(0);
    		}    		
    	}
    	
    	salesOrderVoHeader.setPaymentAmount(totalPayment.doubleValue());
    	Double totalChange = totalPayment.doubleValue() - salesOrderVoHeader.getSumTotalPayment().doubleValue();
    	salesOrderVoHeader.setChangeAmount(totalChange);
    }
    
    public void buttonDelTablePayment(PaymentTypeVO payTypeVO){
    	dataPaymentTypeVoList.remove(payTypeVO);
    }
    
    public void buttonCancelDialogCustomer(){
    	customerVO = new CustomerVO();
    }
	
    public void buttonSaveDialogCustomer(){
    	if(userSession.getCompanyId() !=null){
    		Company company = new Company();
    		company.setCompanyId(userSession.getCompanyId());
    		customerVO.setCompany(company);
    	}
    	
    	if(userSession.getOutletId() !=null){
    		Outlet outlet = new Outlet();
    		outlet.setOutletId(userSession.getOutletId());
    		customerVO.setRegisterOutlet(outlet);
    	}
    	
    	City city  = new City();
    	city.setCityId(customerVO.getCityId());
    	customerVO.setCity(city);
    			    	
    	customerService.save(customerVO, userSession.getUserCode());	
    }
    
    
    public void buttonDialogDisc(DiscountVO discountVO){
    	salesOrderVoHeader.setDiscountId(discountVO.getDiscountId());
    	salesOrderVoHeader.setDiscountName(discountVO.getDiscountName());
    	salesOrderVoHeader.setTitelDiscount(discountVO.getDiscountName());
    	
    	discountDtlList = discountService.searchDiscountDtlList(discountVO.getDiscountId());        
    	Double totalDiscount = new Double(0);
    	Boolean flagDisc = false;
    	for(int i=0; i<salesOrderDtlVOList.size(); i++){
    		DraftSalesOrderDtlVO soDtl = (DraftSalesOrderDtlVO)salesOrderDtlVOList.get(i);
    		   
    		   for(int j=0; j<discountDtlList.size(); j++){
    			   DiscountDtl discDtl = (DiscountDtl)discountDtlList.get(j);
    			   if(discDtl.getCategory() !=null){
		    		   if(soDtl.getCategoryCode().equals(discDtl.getCategory().getCategoryCode())){
		    			    flagDisc = true;
		    			    soDtl.setDiscPercent(discountVO.getDiscountValue());
		    		   } 
    			   }
    		   }
    		   
    		   Double totalPrice = soDtl.getOrderQty().doubleValue() * soDtl.getUnitPrice().doubleValue();
    		   totalDiscount = totalDiscount + totalPrice * (discountVO.getDiscountValue().doubleValue()/100);    		   
    	}
    	
    	if(flagDisc == false){
    		for(int i=0; i<salesOrderDtlVOList.size(); i++){
    			DraftSalesOrderDtlVO soDtl = (DraftSalesOrderDtlVO)salesOrderDtlVOList.get(i);
     		      soDtl.setDiscPercent(new Double(0));
   		          totalDiscount = new Double(0);
    		}  
    	}    	
    	salesOrderVoHeader.setSubDiskon(totalDiscount);
    	sumTotal();
    }
    
    public void buttonDialogTagihan(){
    	salesOrderDtlVOTagihanList.clear();
    	for(DraftSalesOrderDtlVO dtl : salesOrderDtlVOList){
    		salesOrderDtlVOTagihanList.add(dtl);
    	}  		
    }
    
    public void buttonAddTagihan(){
    	dataTableDialogTagihan.add(new DraftSalesOrderVO());    	
    }
    
    public void checkCheckbox(int indexTagihan){
    	for(int i=0; i<dataTableDialogTagihan.size(); i++){
    		if(i != indexTagihan)
    		{
    			DraftSalesOrderVO so = (DraftSalesOrderVO)dataTableDialogTagihan.get(i);
	  		  so.setCheckTagihan(false);
    		}
    	}
    }
    
    public void buttonPindahTagihan(){
    	List<String> errors = new ArrayList<String>();
    	if(!dataTableDialogTagihan.isEmpty())
    	{
    		int countMax = 0;
    		for(int i=0; i<dataTableDialogTagihan.size(); i++){
    			DraftSalesOrderVO so = (DraftSalesOrderVO)dataTableDialogTagihan.get(i);
	    		  if(so.isCheckTagihan() == true){
	    			  countMax++;
	    		  }
    		}
    		
    		// Tagihan maksimal 1
    		if(countMax > 1)
    		{
    			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Pilih tagihan maksimal 1");
    			return;
    		}
    			
    			
    		for(int i=0; i<dataTableDialogTagihan.size(); i++){
    			DraftSalesOrderVO so = (DraftSalesOrderVO)dataTableDialogTagihan.get(i);
	    		  
	    		  Double subTotal = new Double(0);
	    		  Double discount = new Double(0);
	    		  if(so.isCheckTagihan() == true){
	    			  for(int j=0; j<selectedSalesOrderDtlVO.size(); j++){    			  
	    				  DraftSalesOrderDtlVO voDtl = (DraftSalesOrderDtlVO)selectedSalesOrderDtlVO.get(j);
		    			  voDtl.setCheckTagihanDtl(false);
		    		      so.getTagihanList().add(voDtl);
		    		      
		    		     /* Double orderQty = order.getOrderQty().doubleValue() + 1;
						  Double totalPriceBaru = orderQty.doubleValue() * productVO.getUnitPrice().doubleValue();
						  Double totalPriceLama = order.getTotalPrice();
						  Double subTotalNew  = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceLama.doubleValue();*/
		    		      if(so.getSubTotal() !=null){
		    		           subTotal = subTotal.doubleValue() + voDtl.getTotalPrice().doubleValue() + so.getSubTotal().doubleValue();
		    		      }else{
		    		    	   subTotal = subTotal.doubleValue() + voDtl.getTotalPrice().doubleValue();
		    		      }
		    		      discount = subTotal * (voDtl.getDiscPercent().doubleValue() / 100);
		    		      
		    		      salesOrderDtlVOTagihanList.remove(voDtl);
	    			  }
	    			  
	    			  so.setSubTotal(subTotal);
	        		  so.setSubDiskon(discount);
	        		  so.setSumTotal(subTotal.doubleValue() - discount.doubleValue());
	    		  }
	    		  else{
	    			  errors.add("error");
	    			  //facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Silahkan pilih tagihan");
	    		  }
	    	}
    		
    		if(errors.size() == dataTableDialogTagihan.size())
    		{
    			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Silahkan pilih tagihan");
    		}
    	}
    	else{
    		facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Silahkan tambah tagihan"); 
    	}
    }
    
    public void buttonDelTagihan(){
    	for(int i=0; i<dataTableDialogTagihan.size(); i++){
    		DraftSalesOrderVO so = (DraftSalesOrderVO)dataTableDialogTagihan.get(i);
	  		  if(so.isCheckTagihan() == true){
	  			   for(int j=0; j<so.getTagihanList().size(); j++){    			  
	  				 DraftSalesOrderDtlVO voDtl = (DraftSalesOrderDtlVO)so.getTagihanList().get(j);	    		      		    		      
	    		       salesOrderDtlVOTagihanList.add(voDtl);
	    		       
			       }
	  			  
	  			 dataTableDialogTagihan.remove(so);
	  		  }
    	}
    	
    }
    
    public void buttonHapusItemTagihan(){
    	for(int i=0; i<dataTableDialogTagihan.size(); i++){
    		DraftSalesOrderVO so = (DraftSalesOrderVO)dataTableDialogTagihan.get(i);
		      
		      Double subTotal = new Double(0);
	    	  Double discount = new Double(0);
	    	  if(so.isCheckTagihan() == true){
		  		  for(int j=0; j<so.getTagihanList().size(); j++){  	  			  
		  			DraftSalesOrderDtlVO voDtl = (DraftSalesOrderDtlVO)so.getTagihanList().get(j);
			    	   
			    	    if(voDtl.isCheckTagihanDtl() == true){
			    		     salesOrderDtlVOTagihanList.add(voDtl);		
			    		     
			    		     if(so.getSubTotal() !=null){
			    		           subTotal = so.getSubTotal().doubleValue() - voDtl.getTotalPrice().doubleValue();
			    		      }
			    		     
			    		      discount = subTotal * (voDtl.getDiscPercent().doubleValue() / 100);
			    		     
			    		     so.getTagihanList().remove(voDtl);
		  			    }	  
		  		  }
		  		  
		  		so.setSubTotal(subTotal);
	  		    so.setSubDiskon(discount);
	  		    so.setSumTotal(subTotal.doubleValue() - discount.doubleValue());
	  		    
	    	  }	  
    	}
    }
        
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DraftSalesOrderFnBInputBean.logger = logger;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public List<CategoryVO> getCategoryVoList() {
		return categoryVoList;
	}

	public void setCategoryVoList(List<CategoryVO> categoryVoList) {
		this.categoryVoList = categoryVoList;
	}

	public List<ProductVO> getProductVoList() {
		return productVoList;
	}

	public void setProductVoList(List<ProductVO> productVoList) {
		this.productVoList = productVoList;
	}

	public List<EquipmentVO> getEquipmentVoList() {
		return equipmentVoList;
	}

	public void setEquipmentVoList(List<EquipmentVO> equipmentVoList) {
		this.equipmentVoList = equipmentVoList;
	}

	public EquipmentService getEquipmentService() {
		return equipmentService;
	}

	public void setEquipmentService(EquipmentService equipmentService) {
		this.equipmentService = equipmentService;
	}


	public Integer getIndexRowOrder() {
		return indexRowOrder;
	}

	public void setIndexRowOrder(Integer indexRowOrder) {
		this.indexRowOrder = indexRowOrder;
	}

	public Integer getIndexRowCategory() {
		return indexRowCategory;
	}

	public void setIndexRowCategory(Integer indexRowCategory) {
		this.indexRowCategory = indexRowCategory;
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

	public PaymentTypeService getPaymentTypeService() {
		return paymentTypeService;
	}

	public void setPaymentTypeService(PaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}

	public List<PaymentTypeVO> getPaymentTypeVoList() {
		return paymentTypeVoList;
	}

	public void setPaymentTypeVoList(List<PaymentTypeVO> paymentTypeVoList) {
		this.paymentTypeVoList = paymentTypeVoList;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public boolean isDisableButtonOrder() {
		return disableButtonOrder;
	}

	public void setDisableButtonOrder(boolean disableButtonOrder) {
		this.disableButtonOrder = disableButtonOrder;
	}

	public boolean isEnableButtonOther() {
		return enableButtonOther;
	}

	public void setEnableButtonOther(boolean enableButtonOther) {
		this.enableButtonOther = enableButtonOther;
	}


	public List<ParameterDtlVO> getOrderTypeList() {
		return orderTypeList;
	}

	public void setOrderTypeList(List<ParameterDtlVO> orderTypeList) {
		this.orderTypeList = orderTypeList;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public List<PaymentTypeVO> getDataPaymentTypeVoList() {
		return dataPaymentTypeVoList;
	}

	public void setDataPaymentTypeVoList(List<PaymentTypeVO> dataPaymentTypeVoList) {
		this.dataPaymentTypeVoList = dataPaymentTypeVoList;
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

	public CustomerVO getCustomerVO() {
		return customerVO;
	}

	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

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

	public List<DiscountVO> getDiscountVoList() {
		return discountVoList;
	}

	public void setDiscountVoList(List<DiscountVO> discountVoList) {
		this.discountVoList = discountVoList;
	}

	public DiscountService getDiscountService() {
		return discountService;
	}

	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public List<DiscountDtl> getDiscountDtlList() {
		return discountDtlList;
	}

	public void setDiscountDtlList(List<DiscountDtl> discountDtlList) {
		this.discountDtlList = discountDtlList;
	}

	
	public InvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public boolean isDisableButtonOnRowClicked() {
		return disableButtonOnRowClicked;
	}

	public void setDisableButtonOnRowClicked(boolean disableButtonOnRowClicked) {
		this.disableButtonOnRowClicked = disableButtonOnRowClicked;
	}

	public boolean isEnableButtonOnProductClicked() {
		return enableButtonOnProductClicked;
	}

	public void setEnableButtonOnProductClicked(boolean enableButtonOnProductClicked) {
		this.enableButtonOnProductClicked = enableButtonOnProductClicked;
	}

	public boolean isEnableButtonTagihanOnProductClicked() {
		return enableButtonTagihanOnProductClicked;
	}

	public void setEnableButtonTagihanOnProductClicked(
			boolean enableButtonTagihanOnProductClicked) {
		this.enableButtonTagihanOnProductClicked = enableButtonTagihanOnProductClicked;
	}

	public DraftSalesOrderFnBService getDraftSalesOrderFnBService() {
		return draftSalesOrderFnBService;
	}

	public void setDraftSalesOrderFnBService(DraftSalesOrderFnBService draftSalesOrderFnBService) {
		this.draftSalesOrderFnBService = draftSalesOrderFnBService;
	}

	public List<DraftSalesOrderDtlVO> getSalesOrderDtlVOList() {
		return salesOrderDtlVOList;
	}

	public void setSalesOrderDtlVOList(List<DraftSalesOrderDtlVO> salesOrderDtlVOList) {
		this.salesOrderDtlVOList = salesOrderDtlVOList;
	}

	public List<DraftSalesOrderVO> getDataTableDialogTagihan() {
		return dataTableDialogTagihan;
	}

	public void setDataTableDialogTagihan(List<DraftSalesOrderVO> dataTableDialogTagihan) {
		this.dataTableDialogTagihan = dataTableDialogTagihan;
	}

	public List<DraftSalesOrderDtlVO> getSalesOrderDtlVOTagihanList() {
		return salesOrderDtlVOTagihanList;
	}

	public void setSalesOrderDtlVOTagihanList(List<DraftSalesOrderDtlVO> salesOrderDtlVOTagihanList) {
		this.salesOrderDtlVOTagihanList = salesOrderDtlVOTagihanList;
	}

	public List<DraftSalesOrderDtlVO> getSelectedSalesOrderDtlVO() {
		return selectedSalesOrderDtlVO;
	}

	public void setSelectedSalesOrderDtlVO(List<DraftSalesOrderDtlVO> selectedSalesOrderDtlVO) {
		this.selectedSalesOrderDtlVO = selectedSalesOrderDtlVO;
	}

	public List<DraftSalesOrderVO> getDataPaymentBillList() {
		return dataPaymentBillList;
	}

	public void setDataPaymentBillList(List<DraftSalesOrderVO> dataPaymentBillList) {
		this.dataPaymentBillList = dataPaymentBillList;
	}

	public DraftSalesOrderVO getSalesOrderVoHeader() {
		return salesOrderVoHeader;
	}

	public void setSalesOrderVoHeader(DraftSalesOrderVO salesOrderVoHeader) {
		this.salesOrderVoHeader = salesOrderVoHeader;
	}

	public DraftSalesOrderDtlVO getSalesOrderDtlTableDel() {
		return salesOrderDtlTableDel;
	}

	public void setSalesOrderDtlTableDel(DraftSalesOrderDtlVO salesOrderDtlTableDel) {
		this.salesOrderDtlTableDel = salesOrderDtlTableDel;
	}

	public DraftSalesOrderDtlVO getSoDtlVoDialogProduct() {
		return soDtlVoDialogProduct;
	}

	public void setSoDtlVoDialogProduct(DraftSalesOrderDtlVO soDtlVoDialogProduct) {
		this.soDtlVoDialogProduct = soDtlVoDialogProduct;
	}

	public DraftSalesOrder getSalesOrderTagihan() {
		return salesOrderTagihan;
	}

	public void setSalesOrderTagihan(DraftSalesOrder salesOrderTagihan) {
		this.salesOrderTagihan = salesOrderTagihan;
	}

	
	
}
