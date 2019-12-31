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
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.UploadFileVO;
import com.wo.epos.module.inv.category.model.Category;
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
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.service.DiscountService;
import com.wo.epos.module.sales.discount.vo.DiscountVO;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.model.InvoiceDtl;
import com.wo.epos.module.sales.invoice.service.InvoiceService;
import com.wo.epos.module.sales.register.service.RegisterService;
import com.wo.epos.module.sales.register.vo.RegisterVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;
import com.wo.epos.module.sales.salesOrder.service.SalesOrderService;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;

@ManagedBean(name = "salesOrderBistroInputBean")
@ViewScoped
public class SalesOrderBistroInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 7727870677960860773L;
	static Logger logger = Logger.getLogger(SalesOrderBistroInputBean.class);
	
	@ManagedProperty(value = "#{salesOrderService}")
	private SalesOrderService salesOrderService;

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
	
	@ManagedProperty(value = "#{systemPropertyService}")								
	private SystemPropertyService systemPropertyService;	

	private List<SalesOrderDtlVO> salesOrderDtlVOList = new ArrayList<SalesOrderDtlVO>();
	private List<CategoryVO> categoryVoList = new ArrayList<CategoryVO>();
	private List<ProductVO> productVoList = new ArrayList<ProductVO>();
	private List<EquipmentVO> equipmentVoList = new ArrayList<EquipmentVO>();
	private List<PaymentTypeVO> paymentTypeVoList = new ArrayList<PaymentTypeVO>();
	private List<PaymentTypeVO> dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
	private List<ParameterDtlVO> orderTypeList = new ArrayList<ParameterDtlVO>();
	private List<DiscountVO> discountVoList = new ArrayList<DiscountVO>();
	private List<DiscountDtl> discountDtlList = new ArrayList<DiscountDtl>();
	private List<SalesOrderVO> dataTableDialogTagihan = new ArrayList<SalesOrderVO>();
	private List<SalesOrderDtlVO> salesOrderDtlVOTagihanList = new ArrayList<SalesOrderDtlVO>();
	private List<SalesOrderDtlVO> selectedSalesOrderDtlVO;
	private List<SalesOrderVO> dataPaymentBillList = new ArrayList<SalesOrderVO>();
	
	private List<SelectItem> registerSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();	

	private SalesOrderVO salesOrderVoHeader = new SalesOrderVO();
	private SalesOrderDtlVO salesOrderDtlTableDel = new SalesOrderDtlVO();
	private SalesOrderDtlVO soDtlVoDialogProduct = new SalesOrderDtlVO();
	private CustomerVO customerVO = new CustomerVO();
	
	private SalesOrder salesOrderTagihan = new SalesOrder();
	private SystemProperty systemPropTaxType = new SystemProperty();

	private Integer indexRowOrder;
	private Integer indexRowCategory;

	private String streamUploadId;
	private String MODE_TYPE;
	
	private boolean disableButtonOrder;
	private boolean enableButtonOther;
	private boolean disableButtonOnRowClicked;;

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
		
		if(userSession.getOutletId() !=null){
		   equipmentVoList = equipmentService.searchDataEquipmentByParameterCodeAndOutletId(CommonConstants.EQUIPMENT_TABLE, userSession.getOutletId());		
		}else{
			equipmentVoList = new ArrayList<EquipmentVO>();
		}
		
		salesOrderDtlVOList = new ArrayList<SalesOrderDtlVO>();
		salesOrderDtlVOTagihanList = new ArrayList<SalesOrderDtlVO>();
		dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
		salesOrderVoHeader = new SalesOrderVO();
		salesOrderDtlTableDel = new SalesOrderDtlVO();
		salesOrderVoHeader.setItemAmount(new Long(0));
		salesOrderVoHeader.setSubTotal(new Double(0));
		salesOrderVoHeader.setSubDiskon(new Double(0));
		salesOrderVoHeader.setSoDate(new Date());
		salesOrderVoHeader.setTitelDiscount("Diskon");
		customerVO = new CustomerVO();
		systemPropTaxType = new SystemProperty();
		selectedSalesOrderDtlVO = new ArrayList<SalesOrderDtlVO>();
		
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
			systemPropTaxType  = systemPropertyService.searchSystemPropertyNameAndCompany(CommonConstants.DEFAULT_TAX_TYPE, userSession.getCompanyId());
			salesOrderVoHeader.setTaxTypeCode(systemPropTaxType.getSystemPropertyValue());
			if(systemPropTaxType.getSystemPropertyValue().equals(CommonConstants.TAX_EXCLUDE)){
				SystemProperty taxValue  = systemPropertyService.searchSystemPropertyNameAndCompany(CommonConstants.DEFAULT_TAX_VALUE, userSession.getCompanyId());
			    salesOrderVoHeader.setTaxValue(new Double(taxValue.getSystemPropertyValue()));
			}else{
				salesOrderVoHeader.setTaxTypeCode(systemPropTaxType.getSystemPropertyValue());
			}
		}
		
		
		
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
	}
	
	public void onRowSelect(SelectEvent event){
		disableButtonOnRowClicked = true;
	}
	
	public void onRowUnSelect(SelectEvent event){
		disableButtonOnRowClicked = false;
	}
	
	public boolean validateRegister(){
		boolean valid = true;
		if(salesOrderVoHeader.getRegisterId() == null){
			facesUtils
			.addFacesMessage(
					FacesMessage.SEVERITY_ERROR, " Register "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));					
					//facesUtils.getResourceBundleStringValue("formPoOutlet")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			
			valid = false;			
		}
				
		return valid;
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
				 salesOrderService.saveSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());	
			 
				 facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			 }
			 else if(MODE_TYPE.equals(CommonConstants.MODE_TYPE_EDIT))
			 {
				 salesOrderService.updateSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());
				 
				 facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
			                null);
			 }
			 
			 salesOrderService.flush();
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
			salesOrderService.rollback();
		}
	}
	
	public void saveSendKitchen(){
		try{  
			 if(MODE_TYPE.equals(CommonConstants.MODE_TYPE_ADD))
			 {
				 salesOrderVoHeader.setStatusCode(CommonConstants.SO_PROCEED);
				 salesOrderService.saveSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());
				 
				 facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
			                null);
			 }
			 else if(MODE_TYPE.equals(CommonConstants.MODE_TYPE_EDIT))
			 {
				 salesOrderService.updateSoDistro(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());
				 
				 facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
			                null);
			 }
			 
			 salesOrderService.flush();
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
			salesOrderService.rollback();
		}
	}
	
	private boolean validatePayment(){
		boolean valid = true;
		if(dataPaymentTypeVoList.size() == 0){
			facesUtils
			.addFacesMessage(
					FacesMessage.SEVERITY_ERROR, " Silahkan pilih tipe pembayaran ");					
					//facesUtils.getResourceBundleStringValue("formPoOutlet")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			
			valid = false;			
		}
		
		return valid;
	}
	
	public void savePayment(){
		try{  
			  if(validatePayment()){
				  if(salesOrderVoHeader.getStatusCode() !=null && !salesOrderVoHeader.getStatusCode().equals(""))
				  {
					  if(!salesOrderVoHeader.getStatusCode().equals(CommonConstants.SO_BILL))
					  {
						  salesOrderVoHeader.setStatusCode(CommonConstants.SO_PAYMENT);
						  salesOrderService.savePayment(salesOrderVoHeader, salesOrderDtlVOList, dataPaymentTypeVoList, userSession.getUserCode());
						  salesOrderService.flush();
						  buttonCancel();
					  }
					  else
					  {
						  salesOrderService.saveSoDistroPaymentBill(salesOrderVoHeader, dataPaymentTypeVoList, userSession.getUserCode());					  
						  List<Invoice> invList = invoiceService.searchDataInvoiceBySoIdAndStatus(salesOrderTagihan.getSoId(), CommonConstants.SOINVOICE_NEW);
						  if(invList.size() == 1){							  
							  salesOrderVoHeader.setSoId(salesOrderTagihan.getSoId());
							  salesOrderVoHeader.setStatusCode(CommonConstants.SO_PAYMENT);
							  salesOrderService.updateSoPayment(salesOrderVoHeader, userSession.getUserCode());
							  salesOrderService.flush();
							  buttonCancel();
						  }
						  else
						  {
							  salesOrderService.flush();
						  }
					  }
				  }
				  else
				  {
					  salesOrderService.savePayment(salesOrderVoHeader, salesOrderDtlVOList, dataPaymentTypeVoList, userSession.getUserCode());	
					  salesOrderService.flush();	
					  buttonCancel();
					  
					  
				  }
				  
				  facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
				  
				  RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').hide()");
			  }else{
				  RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').show()");
			  }
			 		 
		}catch(Exception ex){
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);
			salesOrderService.rollback();
		}
	}
	
	public void buttonSaveDialogTagihan(){
		try{  
			if(salesOrderDtlVOTagihanList.isEmpty())
			{
			  	 salesOrderVoHeader.setStatusCode(CommonConstants.SO_BILL);
		         salesOrderTagihan = salesOrderService.saveSoDistroBill(salesOrderVoHeader, salesOrderDtlVOList, dataTableDialogTagihan, userSession.getUserCode());
		         salesOrderService.flush();
		         RequestContext.getCurrentInstance().execute("PF('dialogTagihan').hide()");
		         
		         facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
			                null);
			}
			else
			{
				//facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("errorSaveDialogSo"));
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
						facesUtils.retrieveMessage("errorSaveDialogSo"), 
		                null);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);
			salesOrderService.rollback();
		}
   }
	
	public void buttonCancelDialogTagihan(){
		salesOrderVoHeader.setStatusCode("");
		dataTableDialogTagihan = new ArrayList<SalesOrderVO>();
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
		
	}
	
	public void buttonEditProduct(){
		soDtlVoDialogProduct = salesOrderDtlTableDel;
	}
	
	public void buttonSaveDialogProduct(){
		//salesOrderVoHeader.setSubTotal(new Double(0));
		for(int i=0; i<salesOrderDtlVOList.size();i++){
			   SalesOrderDtlVO salesOrdetEdit = (SalesOrderDtlVO)salesOrderDtlVOList.get(i);
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
		soDtlVoDialogProduct = new SalesOrderDtlVO();
		soDtlVoDialogProduct.setProductName("");
	}
	
	private void disableButton(){
		disableButtonOrder = true;
		enableButtonOther = false;
	}
	
	private void enableButton(){
		disableButtonOrder = false;
		enableButtonOther = true;
	}

	public boolean modeEdit(EquipmentVO equipDialog){
		boolean valid = false;
		if(equipDialog != null && equipDialog.getEquipmentId() !=null){
		    SalesOrderVO salesOrderVoEDit = salesOrderService.searchDataSoByEquipment(equipDialog.getEquipmentId(), equipDialog.getEquipmentStatusCode());
			if(salesOrderVoEDit !=null){
				salesOrderVoHeader = salesOrderVoEDit;
				salesOrderVoHeader.setTitelDiscount("Diskon");
				
				if(salesOrderVoHeader.getStatusCode().equals(CommonConstants.SO_BILL)){
					salesOrderTagihan = new SalesOrder();
					salesOrderTagihan.setSoId(salesOrderVoHeader.getSoId());
				}
				
				 for(int i=0; i<salesOrderVoHeader.getSalesOrderDtlVoList().size();i++){
					     SalesOrderDtlVO dtlVo = (SalesOrderDtlVO) salesOrderVoHeader.getSalesOrderDtlVoList().get(i);
					     salesOrderDtlVOList.add(dtlVo);
					     
					     MODE_TYPE = CommonConstants.MODE_TYPE_EDIT;
					     valid = true;
					     
					     if(salesOrderVoHeader.getTaxValue() !=null){
	       					 Double totalSub = salesOrderVoHeader.getSubTotal().doubleValue() - salesOrderVoHeader.getSubDiskon().doubleValue();
	       					 salesOrderVoHeader.setSubTaxValue(totalSub.doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue()/100));
	       				 }
				 }
				 
				 sumTotal();
				
			}	
		}
		
		return valid;
	}
	
	public void buttonOrder(Integer index, EquipmentVO equipDialog, ParameterDtlVO paramOrderType) {	
		if(modeEdit(equipDialog)){
		
		}else{
			if(equipDialog !=null){
				salesOrderVoHeader.setEquipmentId(equipDialog.getEquipmentId());
				salesOrderVoHeader.setEquipmentName(equipDialog.getEquipmentName());
			}
			salesOrderVoHeader.setSoTypeCode(paramOrderType.getParameterDtlCode());
			salesOrderVoHeader.setSoTypeName(paramOrderType.getParameterDtlName());
		}
		

		indexRowOrder = index;
		//categoryVoList = categoryService.searchAllCategoryParent(null);
		if(userSession.getCompanyId() !=null){
		    List<Category> categoryList = categoryService.searchCategoryCompanyByParentIsNotNull(userSession.getCompanyId());		
					
			for(int i=0; i<categoryList.size(); i++){
				Category category = (Category)categoryList.get(i);
				CategoryVO vo = new CategoryVO();
				
				vo.setCategoryId(category.getCategoryId());
				vo.setCompanyId(category.getCompany().getCompanyId());
				vo.setCategoryCode(category.getCategoryCode());
				vo.setCategoryName(category.getCategoryName());
				vo.setCategoryDesc(category.getCategoryDesc());
				vo.setCategoryLevel(category.getCategoryLevel());
				vo.setActiveFlag(category.getActiveFlag());
				
				categoryVoList.add(vo);
			}
		}
		
		enableButton();
		
	}

	public void buttonCategery(Integer index, CategoryVO categoryVO) {
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
				SalesOrderDtlVO order = (SalesOrderDtlVO) salesOrderDtlVOList.get(i);
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
					 
					 if(salesOrderVoHeader.getTaxValue() !=null){
       					 Double totalSub = salesOrderVoHeader.getSubTotal().doubleValue() - salesOrderVoHeader.getSubDiskon().doubleValue();
       					 salesOrderVoHeader.setSubTaxValue(totalSub.doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue()/100));
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
				
				if(salesOrderVoHeader.getTaxValue() !=null){
					 Double totalSub = salesOrderVoHeader.getSubTotal().doubleValue() - salesOrderVoHeader.getSubDiskon().doubleValue();
					 salesOrderVoHeader.setSubTaxValue(totalSub.doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue()/100));
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

			salesOrderVoHeader.setSubTotal(salesOrderVoHeader.getSubTotal().doubleValue() + order.getTotalPrice().doubleValue());
			if(salesOrderVoHeader.getTaxValue() !=null){
				salesOrderVoHeader.setSubTaxValue(salesOrderVoHeader.getSubTotal().doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue()/100));
			}
			
			salesOrderDtlVOList.add(order);
			salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
			sumTotal();
		}
	}
	
	public void sumTotal() {
		Double sumTotal = new Double(0);
		Double subTotal = salesOrderVoHeader.getSubTotal().doubleValue();
		Double subDiskon = salesOrderVoHeader.getSubDiskon().doubleValue();
		Double subTaxValue = new Double(0);
		if(salesOrderVoHeader.getSubTaxValue() !=null){		
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
	
	
	public void buttonPayment(){
		dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
		if(validateRegister()){
			if(salesOrderTagihan !=null && salesOrderTagihan.getSoId() !=null){
				dataPaymentBillList = new ArrayList<SalesOrderVO>();
				salesOrderVoHeader.setSumTotalPayment(null);
				salesOrderVoHeader.setPaymentAmount(null);
				List<Invoice> invoList = invoiceService.searchDataInvoiceBySoIdAndStatus(salesOrderTagihan.getSoId(), CommonConstants.SOINVOICE_NEW);
				for(int i=0; i<invoList.size(); i++){
					 Invoice inv = (Invoice)invoList.get(i);
					 SalesOrderVO soBill = new SalesOrderVO();
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
				}
			}else{
				salesOrderVoHeader.setSumTotalPayment(salesOrderVoHeader.getSumTotal());
			}
			
			RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').show()");
		}else{
			RequestContext.getCurrentInstance().execute("PF('dialogPembayaran').hide()");
		}
	}
	
	public void buttonDialogPaymentBill(SalesOrderVO salesOrderVo){
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
				 payTypeVO.setTotalPayment(null);
			     dataPaymentTypeVoList.add(payTypeVO);
			}
		    
		}else{
			payTypeVO.setTotalPayment(null);
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
    		   SalesOrderDtlVO soDtl = (SalesOrderDtlVO)salesOrderDtlVOList.get(i);
    		   
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
     		      SalesOrderDtlVO soDtl = (SalesOrderDtlVO)salesOrderDtlVOList.get(i);
     		      soDtl.setDiscPercent(new Double(0));
   		          totalDiscount = new Double(0);
    		}  
    	}    	
    	salesOrderVoHeader.setSubDiskon(totalDiscount);
    	sumTotal();
    }
    
    public void buttonDialogTagihan(){
    	salesOrderDtlVOTagihanList = new ArrayList<SalesOrderDtlVO>();
    	if(dataTableDialogTagihan.size() == 0){
	    	for(SalesOrderDtlVO dtl : salesOrderDtlVOList){
	    		salesOrderDtlVOTagihanList.add(dtl);
	    	}
    	}	
    }
    
    public void buttonAddTagihan(){
    	dataTableDialogTagihan.add(new SalesOrderVO());    	
    }
    
    public void buttonPindahTagihan(){
    	List<String> errors = new ArrayList<String>();
    	if(!dataTableDialogTagihan.isEmpty()){
	    	for(int i=0; i<dataTableDialogTagihan.size(); i++){
	    		  SalesOrderVO so = (SalesOrderVO)dataTableDialogTagihan.get(i);
	    		  
	    		  Double subTotal = new Double(0);
	    		  Double discount = new Double(0);
	    		  if(so.isCheckTagihan() == true){
	    			  for(int j=0; j<selectedSalesOrderDtlVO.size(); j++){    			  
		    			  SalesOrderDtlVO voDtl = (SalesOrderDtlVO)selectedSalesOrderDtlVO.get(j);
		    			  voDtl.setCheckTagihanDtl(false);
		    		      so.getTagihanList().add(voDtl);
		    		      
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
	    	}
	    	if(errors.size() == dataTableDialogTagihan.size()){
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Silahkan pilih tagihan");
			}
		}else{
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "Silahkan tambah tagihan"); 
		}
    	
    }
    
    public void buttonDelTagihan(){
    	for(int i=0; i<dataTableDialogTagihan.size(); i++){
  		      SalesOrderVO so = (SalesOrderVO)dataTableDialogTagihan.get(i);
	  		  if(so.isCheckTagihan() == true){
	  			   for(int j=0; j<so.getTagihanList().size(); j++){    			  
	    			   SalesOrderDtlVO voDtl = (SalesOrderDtlVO)so.getTagihanList().get(j);	    		      		    		      
	    		       salesOrderDtlVOTagihanList.add(voDtl);
	    		       
			       }
	  			  
	  			 dataTableDialogTagihan.remove(so);
	  		  }
    	}
    	
    }
    
    public void buttonHapusItemTagihan(){
    	for(int i=0; i<dataTableDialogTagihan.size(); i++){
		      SalesOrderVO so = (SalesOrderVO)dataTableDialogTagihan.get(i);
		      
		      Double subTotal = new Double(0);
	    	  Double discount = new Double(0);
	    	  if(so.isCheckTagihan() == true){
		  		  for(int j=0; j<so.getTagihanList().size(); j++){  	  			  
			    	    SalesOrderDtlVO voDtl = (SalesOrderDtlVO)so.getTagihanList().get(j);
			    	   
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
		SalesOrderBistroInputBean.logger = logger;
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
	
	public List<SalesOrderDtlVO> getSalesOrderDtlVOList() {
		return salesOrderDtlVOList;
	}

	public void setSalesOrderDtlVOList(List<SalesOrderDtlVO> salesOrderDtlVOList) {
		this.salesOrderDtlVOList = salesOrderDtlVOList;
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

	public SalesOrderVO getSalesOrderVoHeader() {
		return salesOrderVoHeader;
	}

	public void setSalesOrderVoHeader(SalesOrderVO salesOrderVoHeader) {
		this.salesOrderVoHeader = salesOrderVoHeader;
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

	public SalesOrderDtlVO getSalesOrderDtlTableDel() {
		return salesOrderDtlTableDel;
	}

	public void setSalesOrderDtlTableDel(SalesOrderDtlVO salesOrderDtlTableDel) {
		this.salesOrderDtlTableDel = salesOrderDtlTableDel;
	}

	public SalesOrderDtlVO getSoDtlVoDialogProduct() {
		return soDtlVoDialogProduct;
	}

	public void setSoDtlVoDialogProduct(SalesOrderDtlVO soDtlVoDialogProduct) {
		this.soDtlVoDialogProduct = soDtlVoDialogProduct;
	}

	public SalesOrderService getSalesOrderService() {
		return salesOrderService;
	}

	public void setSalesOrderService(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
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

	public List<SalesOrderVO> getDataTableDialogTagihan() {
		return dataTableDialogTagihan;
	}

	public void setDataTableDialogTagihan(List<SalesOrderVO> dataTableDialogTagihan) {
		this.dataTableDialogTagihan = dataTableDialogTagihan;
	}

	public List<SalesOrderDtlVO> getSelectedSalesOrderDtlVO() {
		return selectedSalesOrderDtlVO;
	}
	
	public void setSelectedSalesOrderDtlVO(
			List<SalesOrderDtlVO> selectedSalesOrderDtlVO) {
		this.selectedSalesOrderDtlVO = selectedSalesOrderDtlVO;
	}

	public List<SalesOrderDtlVO> getSalesOrderDtlVOTagihanList() {
		return salesOrderDtlVOTagihanList;
	}

	public void setSalesOrderDtlVOTagihanList(
			List<SalesOrderDtlVO> salesOrderDtlVOTagihanList) {
		this.salesOrderDtlVOTagihanList = salesOrderDtlVOTagihanList;
	}

	public SalesOrder getSalesOrderTagihan() {
		return salesOrderTagihan;
	}

	public void setSalesOrderTagihan(SalesOrder salesOrderTagihan) {
		this.salesOrderTagihan = salesOrderTagihan;
	}

	public InvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public List<SalesOrderVO> getDataPaymentBillList() {
		return dataPaymentBillList;
	}

	public void setDataPaymentBillList(List<SalesOrderVO> dataPaymentBillList) {
		this.dataPaymentBillList = dataPaymentBillList;
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

	public boolean isDisableButtonOnRowClicked() {
		return disableButtonOnRowClicked;
	}

	public void setDisableButtonOnRowClicked(boolean disableButtonOnRowClicked) {
		this.disableButtonOnRowClicked = disableButtonOnRowClicked;
	}

	
	

}
