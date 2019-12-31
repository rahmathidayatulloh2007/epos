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
import com.wo.epos.common.constant.CommonConstants;
/*import com.wo.epos.module.inv.customer.service.CustomerService;*/
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.service.ProductService;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.service.ItemDiscountService;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.inv.itemStock.service.ItemStockService;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.paymentType.service.PaymentTypeService;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.sales.customer.model.CustomerSales;
import com.wo.epos.module.sales.customer.service.CustomerService;
import com.wo.epos.module.sales.customer.vo.CustomerSalesVO;
import com.wo.epos.module.sales.draftSalesOrder.service.DraftSalesOrderDistributorService;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;
import com.wo.epos.module.sales.register.service.RegisterService;
import com.wo.epos.module.sales.register.vo.RegisterVO;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "draftSalesOrderDistributorInputBean")
@ViewScoped
public class DraftSalesOrderDistributorInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = 7843026683560754008L;

	static Logger logger = Logger.getLogger(DraftSalesOrderDistributorInputBean.class);
	
	@ManagedProperty(value = "#{registerService}")
	private RegisterService registerService;
	
	@ManagedProperty(value = "#{productService}")
	private ProductService productService;
	
	/*@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;	*/
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	@ManagedProperty(value = "#{customerService2}")
	private CustomerService customerService2;
	
	@ManagedProperty(value = "#{systemPropertyService}")								
	private SystemPropertyService systemPropertyService;
	
	@ManagedProperty(value = "#{draftSalesOrderDistributorService}")								
	private DraftSalesOrderDistributorService draftSalesOrderDistributorService;
	
	@ManagedProperty(value = "#{outletService}")								
	private OutletService outletService;
	
	@ManagedProperty(value = "#{paymentTypeService}")								
	private PaymentTypeService paymentTypeService;
	
	@ManagedProperty(value = "#{parameterService}")								
	private ParameterService parameterService;

	@ManagedProperty(value = "#{itemStockService}")								
	private ItemStockService itemStockService;
	
	@ManagedProperty(value = "#{itemDiscountService}")
	private ItemDiscountService itemDiscountService;
	
	private List<SelectItem> choseeSoSelectItem = new ArrayList<SelectItem>();
	
	private List<DraftSalesOrderDtlVO> salesOrderDtlVOList = new ArrayList<DraftSalesOrderDtlVO>();
	private List<PaymentTypeVO> paymentTypeVoList = new ArrayList<PaymentTypeVO>();
	private List<ProductVO> productVoList = new ArrayList<ProductVO>();
	private List<PaymentTypeVO> dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
	
	private List<SelectItem> registerSelectItemList = new ArrayList<SelectItem>();
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> customerSelectItem = new ArrayList<SelectItem>();
	
	private DraftSalesOrderVO salesOrderVoHeader = new DraftSalesOrderVO();
	private CustomerVO customerVO = new CustomerVO();
	private DraftSalesOrderDtlVO salesOrderDtlTableDel = new DraftSalesOrderDtlVO();
	private DraftSalesOrderDtlVO soDtlVoDialogProduct = new DraftSalesOrderDtlVO();
	private List<ItemDiscountVO> itemDiscountList;
	private SystemProperty systemPropTaxType = new SystemProperty();
	private double sumTotalNoPpn = new Double(0);			
	private CustomerSales customerSales = new CustomerSales();
	private String MODE_TYPE;
	private String completeSearchProductDistributor;
	private String souNmber;
	private boolean disableButtonOnRowClicked;
	private boolean viewCustomer;
	private boolean disableSeacrh;
	private String ppn;
	private boolean ppnFlag;
	
	@PostConstruct
	public void postConstruct(){
		super.init();

		initBean();
		
	}
	
	public void initBean(){
		MODE_TYPE = "ADD";
		registerSelectItemList = new ArrayList<SelectItem>();
		customerSelectItem = new ArrayList<SelectItem>();
		salesOrderDtlVOList = new ArrayList<DraftSalesOrderDtlVO>();
		dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
		paymentTypeVoList = new ArrayList<PaymentTypeVO>();
		salesOrderDtlTableDel = new DraftSalesOrderDtlVO();
		completeSearchProductDistributor = "";
		
		salesOrderVoHeader = new DraftSalesOrderVO();		
		salesOrderVoHeader.setSoDate(new Date());
		salesOrderVoHeader.setTitelDiscount("Diskon");
		salesOrderVoHeader.setItemAmount(new Long(0));
		salesOrderVoHeader.setSubTotal(new Double(0));
		salesOrderVoHeader.setSubDiskon(new Double(0));
		salesOrderVoHeader.setRegisterName(userSession.getEmployeeName());

			
		if(userSession.getOutletId() !=null){						
			List<RegisterVO> registerVoList = registerService.findRegisterByOutletId(userSession.getOutletId());
			for(RegisterVO vo : registerVoList){
				registerSelectItemList.add(new SelectItem(vo.getRegId(), vo.getCashierName()));
			}
			
			Outlet outletLogin = outletService.findById(userSession.getOutletId());
			salesOrderVoHeader.setOutletId(userSession.getOutletId());
			if(outletLogin.getCompany() !=null){
				salesOrderVoHeader.setCompanyId(outletLogin.getCompany().getCompanyId());
			}
			
			Outlet outlet = outletService.findById(userSession.getOutletId());			
			paymentTypeVoList = paymentTypeService.searchDataPaymentByCompany(outlet.getCompany().getCompanyId());
			
		}
		
		if(userSession.getCompanyId() !=null){
			  productVoList = productService.searchAllProductByCompanyId(userSession.getCompanyId());
		      
			  systemPropTaxType  = systemPropertyService.searchSystemPropertyNameAndCompany(CommonConstants.DEFAULT_TAX_TYPE, userSession.getCompanyId());
			  salesOrderVoHeader.setTaxTypeCode(systemPropTaxType.getSystemPropertyValue());
			  if(systemPropTaxType.getSystemPropertyValue().equals(CommonConstants.TAX_EXCLUDE)){
				   SystemProperty taxValue  = systemPropertyService.searchSystemPropertyNameAndCompany(CommonConstants.DEFAULT_TAX_VALUE, userSession.getCompanyId());
			       salesOrderVoHeader.setTaxValue(new Double(taxValue.getSystemPropertyValue()));
			  }else{
				   salesOrderVoHeader.setTaxTypeCode(systemPropTaxType.getSystemPropertyValue());
			  }
		
		}
		
		citySelectItem = new ArrayList<SelectItem>();		
		List<CityVO> citySelectList = cityService.citySearch();
		for(CityVO vo : citySelectList){		
		   citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
		}	
		
		customerSelectItem = new ArrayList<SelectItem>();
		List<CustomerSalesVO> customerSelectList = customerService2.searchCustomerList();
		for(CustomerSalesVO vo : customerSelectList){
			customerSelectItem.add(new SelectItem(vo.getCustomerId(), vo.getCustomerName()));
			
		}
		
		choseeSoSelectItem = new ArrayList<SelectItem>();
		choseeSoSelectItem.add(new SelectItem("PPN", "PPN"));
		choseeSoSelectItem.add(new SelectItem("Non PPN", "Non PPN"));
		
		ppn = null;
		disableSeacrh = false;
	}
	
	public void changePPN(){
		if(salesOrderVoHeader.getPpn().equals("PPN")){
			ppnFlag = true;
			sumTotal();
		}else if(salesOrderVoHeader.getPpn().equals("Non PPN")){
			ppnFlag = false;
			sumTotal();
		}
	}
	
	public void modeAdd(){

		initBean();
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
		
		if(salesOrderDtlVOList.size() == 0){
			facesUtils
			.addFacesMessage(
					FacesMessage.SEVERITY_ERROR, " Item harus di isi ");					
					//facesUtils.getResourceBundleStringValue("formPoOutlet")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			
			valid = false;			
		} /*else {
			for (DraftSalesOrderDtlVO salesOrderDtlVo: salesOrderDtlVOList) {
				Product product = new Product();
	    		product = productService.findById(salesOrderDtlVo.getProductId());
	    		for(ProductBom pBom : product.getProductBomList()){

					ItemStockVO itemStockVO = itemStockService.getItemStockByCompanyIdOutletIdAndItemId(userSession.getCompanyId(), userSession.getOutletId(), pBom.getItem().getItemId());
					if (salesOrderDtlVo.getOrderQty() > itemStockVO.getStockQty() ) {
						facesUtils
						.addFacesMessage(
								FacesMessage.SEVERITY_ERROR, "Jumlah Order Qty lebih dari Stock yang ada");					
								//facesUtils.getResourceBundleStringValue("formPoOutlet")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
						
						valid = false;		
						break;
					}	
	    		}	
			}
		}*/
		return valid;
	}
	
	public boolean validateSaveDirect(){
		boolean valid = true;
		
		if(salesOrderVoHeader.getCustomerId()==null){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesSOin",
					facesUtils.getResourceBundleStringValue("formCustomerTitle") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;
		}
		
		if(salesOrderDtlVOList.size() == 0){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesSOin",
					facesUtils.getResourceBundleStringValue("formProductTitle") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;			
		} 
		return valid;
	}
	
	public void saveDiect(){
		try{  
			
				
				
					if(userSession.getCompanyId() !=null){
					souNmber= draftSalesOrderDistributorService.runningNumberSo(CommonConstants.SORCPT_NUMBERFORMAT, userSession.getCompanyId());
					}else{
						souNmber= draftSalesOrderDistributorService.runningNumberSo(CommonConstants.SORCPT_NUMBERFORMAT, salesOrderVoHeader.getCompanyId());
					}
					  salesOrderVoHeader.setStatusCode(CommonConstants.SO_PAYMENT);
					  salesOrderVoHeader.setSoTypeCode(CommonConstants.SO_PICKUP);
					  draftSalesOrderDistributorService.saveDirect(salesOrderVoHeader, salesOrderDtlVOList, userSession.getUserCode());				 					  
				      
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_INFO, "frm001:messagesSO",
								facesUtils.getResourceBundleStringValue("common_msg_saved") + " "
										+ facesUtils.getResourceBundleStringValue("formDrafSalesOrderNomor")+" "+ 
										souNmber);	
					 
				  
					 		 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			draftSalesOrderDistributorService.rollback();
		}
	}
	
	public void savePayment(){
		try{  
			  if(validatePayment())
			  {
				  salesOrderVoHeader.setStatusCode(CommonConstants.SO_PAYMENT);
				  salesOrderVoHeader.setSoTypeCode(CommonConstants.SO_PICKUP);
				  draftSalesOrderDistributorService.savePayment(salesOrderVoHeader, salesOrderDtlVOList, dataPaymentTypeVoList, userSession.getUserCode());				 
				  buttonCancel();					  
			      RequestContext.getCurrentInstance().execute("PF('dialogPembayaranDistributor').hide()");
			      
			      facesUtils
					.addFacesMessage(
							FacesMessage.SEVERITY_INFO, " Save Berhasil ");
			  }
			  else
			  {
				  RequestContext.getCurrentInstance().execute("PF('dialogPembayaranDistributor').show()");
			  }
			 		 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			draftSalesOrderDistributorService.rollback();
		}
	}
	
	public List<String> completeSearchProductDistributor(String outoCompleteText){
		List<String> resultList = new ArrayList<String>();			
		for(ProductVO proVo : productVoList){
			if(proVo.getProductName().toUpperCase().contains(outoCompleteText.toUpperCase()) 
					|| proVo.getProductCode().toUpperCase().contains(outoCompleteText.toUpperCase())){
			      resultList.add(proVo.getProductCode() +" - "+proVo.getProductName());
			}	
		}
		
		return resultList;
	}
	
	public void handleSelectProduct(SelectEvent event) {
        Object object = event.getObject();
        
        chooseProduct(object.toString(), null);
        this.completeSearchProductDistributor = "";
    }
	
	public void handleKeyPressProduct() {
        if(completeSearchProductDistributor !=null && !completeSearchProductDistributor.equals("")){
        	 chooseProduct(null, completeSearchProductDistributor);
        }
        
        this.completeSearchProductDistributor = "";
    }
	
	private void chooseProduct(String productCode, String barcode){
		
		if(userSession.getCompanyId() !=null){
			ProductVO productVO = new ProductVO();
			if(productCode !=null && !productCode.equals("")){
				String[] split = productCode.split("-");
				productVO = productService.findByProductCodeOrBarcodeAndCompanyId(split[0].toString(), null, userSession.getCompanyId());	
			}else if(barcode !=null && !barcode.equals("")){
				productVO = productService.findByProductCodeOrBarcodeAndCompanyId(null, barcode, userSession.getCompanyId());
			}
			
			if (salesOrderDtlVOList.size() > 0) {
				boolean checkData = false;
				for (int i = 0; i < salesOrderDtlVOList.size(); i++) {
					DraftSalesOrderDtlVO order = (DraftSalesOrderDtlVO) salesOrderDtlVOList.get(i);
					if (Integer.parseInt(order.getProductId() + "") == Integer.parseInt(productVO.getProductId() + "")) {
						 checkData = true;
						 
							customerSales = customerService2.findById(salesOrderVoHeader.getCustomerId());
							Product product = productService.findById(productVO.getProductId());
							itemDiscountList= new ArrayList<>();
							for(int x=0;x<product.getListItemDiscount().size();x++){
								ItemDiscount itemDiscount = product.getListItemDiscount().get(x);
							if(customerSales.getCustomerType().getParameterDtlCode()== itemDiscount.getCustomerType().getParameterDtlCode()){
								
								
								if(itemDiscount.getDiscount1()== null){
									order.setDiscount1(0);
								}else{
									order.setDiscount1(itemDiscount.getDiscount1());
								}
								
								
								if(itemDiscount.getDiscount2()== null){
									order.setDiscount2(0);
								}else{
									order.setDiscount2(itemDiscount.getDiscount2());
								}
								
								if(itemDiscount.getDiscount3()== null){
									order.setDiscount3(0);
								}else{
									order.setDiscount3(itemDiscount.getDiscount3());
								}
								order.setCustomerType(itemDiscount.getCustomerType().getParameterDtlName());
							}
							
							}
							
							double up =productVO.getUnitPrice();
							double d1 =order.getDiscount1().doubleValue()/100;
							double d2 =order.getDiscount2().doubleValue()/100;
							double d3 =order.getDiscount3().doubleValue()/100;
							double q =order.getOrderQty();
							double hasil1 = (up * q)-((up * q)* d1);
							double hasil2 = hasil1 - (hasil1 * d2);
							double hasil3 = hasil2 - (hasil2 * d3);
							order.setTotalPriceDiscount(hasil3);
						 
/*							 Double orderQty = order.getOrderQty().doubleValue() + 1;
							 Double totalPriceBaru = orderQty.doubleValue() * productVO.getUnitPrice().doubleValue();
							 Double totalPriceLama = order.getTotalPrice();
							 Double subTotalNew  = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceLama.doubleValue();
							 salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceBaru.doubleValue());		
							 
							 order.setOrderQty(orderQty);
							 order.setTotalPrice(order.getOrderQty().doubleValue()
									* productVO.getUnitPrice().doubleValue());*/
						 
							 Double orderQty = order.getOrderQty().doubleValue() + 1;
							 Double totalPriceBaru = orderQty.doubleValue() * productVO.getUnitPrice().doubleValue();
							 Double totalPriceLama = order.getTotalPriceDiscount();
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
					
					salesOrderVoHeader.setSumTotal(0.0);
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
	
					/*
					 * salesOrderVoHeader.setSubTotal(salesOrderVoHeader.
					 * getSubTotal() .doubleValue() +
					 * order.getTotalPrice().doubleValue());
					 */


					order.setCategoryCode(productVO.getCategoryCode());
					order.setDiscPercent(new Double(0));
					
					if(salesOrderVoHeader.getTaxValue() !=null){
						 Double totalSub = salesOrderVoHeader.getSubTotal().doubleValue() - salesOrderVoHeader.getSubDiskon().doubleValue();
						 salesOrderVoHeader.setSubTaxValue(totalSub.doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue()/100));
					}					
					
					customerSales = customerService2.findById(salesOrderVoHeader.getCustomerId());
					Product product = productService.findById(productVO.getProductId());
					itemDiscountList= new ArrayList<>();
					for(int i=0;i<product.getListItemDiscount().size();i++){
						ItemDiscount itemDiscount = product.getListItemDiscount().get(i);
					if(customerSales.getCustomerType().getParameterDtlCode()== itemDiscount.getCustomerType().getParameterDtlCode()){
						
						
						if(itemDiscount.getDiscount1()== null){
							order.setDiscount1(0);
						}else{
							order.setDiscount1(itemDiscount.getDiscount1());
						}
						
						
						if(itemDiscount.getDiscount2()== null){
							order.setDiscount2(0);
						}else{
							order.setDiscount2(itemDiscount.getDiscount2());
						}
						
						if(itemDiscount.getDiscount3()== null){
							order.setDiscount3(0);
						}else{
							order.setDiscount3(itemDiscount.getDiscount3());
						}
						order.setCustomerType(itemDiscount.getCustomerType().getParameterDtlName());
					}
					
					}
					
					double up =productVO.getUnitPrice();
					double d1 =order.getDiscount1().doubleValue()/100;
					double d2 =order.getDiscount2().doubleValue()/100;
					double d3 =order.getDiscount3().doubleValue()/100;
					double q =order.getOrderQty();
					double hasil1 = (up * q)-((up * q)* d1);
					double hasil2 = hasil1 - (hasil1 * d2);
					double hasil3 = hasil2 - (hasil2 * d3);
					order.setTotalPriceDiscount(hasil3);
					salesOrderVoHeader.setSubTotal(salesOrderVoHeader.getSubTotal().doubleValue()
							+ order.getTotalPriceDiscount().doubleValue());
					
					salesOrderDtlVOList.add(order);
					salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
					sumTotal();
				}
	
			} else {

				salesOrderVoHeader.setSubTotal(0.0);
				salesOrderVoHeader.setSumTotal(0.0);
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
				
				customerSales = customerService2.findById(salesOrderVoHeader.getCustomerId());
				Product product = productService.findById(productVO.getProductId());
				itemDiscountList= new ArrayList<>();
				for(int i=0;i<product.getListItemDiscount().size();i++){
					ItemDiscount itemDiscount = product.getListItemDiscount().get(i);
				if(customerSales.getCustomerType().getParameterDtlCode()== itemDiscount.getCustomerType().getParameterDtlCode()){
					
					
					if(itemDiscount.getDiscount1()== null){
						order.setDiscount1(0);
					}else{
						order.setDiscount1(itemDiscount.getDiscount1());
					}
					
					
					if(itemDiscount.getDiscount2()== null){
						order.setDiscount2(0);
					}else{
						order.setDiscount2(itemDiscount.getDiscount2());
					}
					
					if(itemDiscount.getDiscount3()== null){
						order.setDiscount3(0);
					}else{
						order.setDiscount3(itemDiscount.getDiscount3());
					}
					order.setCustomerType(itemDiscount.getCustomerType().getParameterDtlName());
				}
				
				}
				
				double up =productVO.getUnitPrice();
				double d1 =order.getDiscount1().doubleValue()/100;
				double d2 =order.getDiscount2().doubleValue()/100;
				double d3 =order.getDiscount3().doubleValue()/100;
				double q =order.getOrderQty();
				double hasil1 = (up * q)-((up * q)* d1);
				double hasil2 = hasil1 - (hasil1 * d2);
				double hasil3 = hasil2 - (hasil2 * d3);
				order.setTotalPriceDiscount(hasil3);
				
				/*				salesOrderVoHeader.setSubTotal(salesOrderVoHeader.getSubTotal().doubleValue() + order.getTotalPrice().doubleValue());*/
				salesOrderVoHeader.setSubTotal(salesOrderVoHeader.getSubTotal().doubleValue() + order.getTotalPriceDiscount().doubleValue());
				
				if(salesOrderVoHeader.getTaxValue() !=null){
					salesOrderVoHeader.setSubTaxValue(salesOrderVoHeader.getSubTotal().doubleValue() * (salesOrderVoHeader.getTaxValue().doubleValue()/100));
				}
				
				salesOrderDtlVOList.add(order);
				salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
				sumTotal();
			}
		}				
	}
	
	public void sumTotal() {
		sumTotalNoPpn = new Double(0);
		Double sumTotalPpn = new Double(0);
		Double ppn = 0.1;
		Double sumTotal = new Double(0);
		Double subTotal = salesOrderVoHeader.getSubTotal().doubleValue();
		Double subDiskon = salesOrderVoHeader.getSubDiskon().doubleValue();
		Double subTaxValue = new Double(0);
		if(salesOrderVoHeader.getSubTaxValue() !=null){		
			subTaxValue = salesOrderVoHeader.getSubTaxValue().doubleValue();
		}
		
		/*
		 * if (salesOrderVoHeader.getDeliveryCost() != null) { sumTotal =
		 * subTotal.doubleValue() - subDiskon.doubleValue() +
		 * subTaxValue.doubleValue() + salesOrderVoHeader.getDeliveryCost();
		 * 
		 * if (ppnFlag = true) { sumTotalNoPpn = sumTotal * ppn; sumTotalPpn =
		 * sumTotal - sumTotalNoPpn; }
		 * 
		 * }
		 */
		
		sumTotal = (subTotal.doubleValue() - subDiskon.doubleValue()) + subTaxValue.doubleValue();

		if (ppnFlag = true && salesOrderVoHeader.getPpn().equals("PPN")) {
			sumTotalNoPpn = sumTotal * ppn;
			sumTotalPpn = sumTotal + sumTotalNoPpn;
			salesOrderVoHeader.setSumTotal(sumTotalPpn);
		} else  {
			salesOrderVoHeader.setSumTotal(sumTotal);
		}
	}
	
	public void changeQtyProduct(DraftSalesOrderDtlVO salesOrderDtlVO){		
		for(int i=0; i<salesOrderDtlVOList.size();i++){
			DraftSalesOrderDtlVO salesOrdetEdit = (DraftSalesOrderDtlVO)salesOrderDtlVOList.get(i);
			   if(Integer.parseInt(salesOrderDtlVO.getProductId()+"") == Integer.parseInt(salesOrdetEdit.getProductId()+"")){
				   Double totalPriceBaru = salesOrderDtlVO.getOrderQty().doubleValue()	* salesOrderDtlVO.getUnitPrice().doubleValue();
				   Double totalPriceLama = salesOrdetEdit.getTotalPrice();
/*				   Double subTotalNew  = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceLama.doubleValue();
				   salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceBaru.doubleValue());	*/		
				   
				   salesOrdetEdit = salesOrderDtlVO;
				   salesOrdetEdit.setTotalPrice(totalPriceBaru);
				   
				   
					Double up =salesOrderDtlVO.getUnitPrice();
					Double d1 =salesOrderDtlVO.getDiscount1().doubleValue()/100;
					Double d2 =salesOrderDtlVO.getDiscount2().doubleValue()/100;
					Double d3 =salesOrderDtlVO.getDiscount3().doubleValue()/100;
					Double q =salesOrderDtlVO.getOrderQty();
					Double hasil1 = (up * q)-((up * q)* d1);
					Double hasil2 = hasil1 - (hasil1 * d2);
					Double hasil3 = hasil2 - (hasil2 * d3);
					
				   Double totalPriceDiscountBaru = hasil3;
				   Double totalPriceDiscountLama = salesOrdetEdit.getTotalPriceDiscount();
				   Double subTotalNew  = salesOrderVoHeader.getSubTotal().doubleValue() - totalPriceDiscountLama.doubleValue();
				   salesOrderVoHeader.setSubTotal(subTotalNew.doubleValue() + totalPriceDiscountBaru.doubleValue());	
				   salesOrdetEdit = salesOrderDtlVO;
				   salesOrdetEdit.setTotalPriceDiscount(totalPriceDiscountBaru);
				      
				   sumTotal();
				   break;
				   
			   }			   
		}
	    	
	}	
	
	public void buttonCancel(){
		initBean();
		salesOrderVoHeader.setSumTotal(0.0);
		salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
	}
	
	public void viewDetailCustomer(){
		
		if(salesOrderVoHeader.getCustomerId() == null || salesOrderVoHeader.getCustomerId().toString().equals("")){
			viewCustomer = false; 
			ppn = null;
			disableSeacrh = false;
		}
		else{
			viewCustomer = true;
			disableSeacrh = true;
			customerSales = customerService2.findById(salesOrderVoHeader.getCustomerId());
			
			customerSales.getCustomerType().getParameterDtlCode();
			
			if(customerSales.getNpwpNo() != null){
				ppn = "PPN";
			} else {
				ppn = null;
				disableSeacrh = false;
			}
		}
	}
	public void hapusUpdate(){
		double up =salesOrderDtlTableDel.getUnitPrice();
		double d1 =salesOrderDtlTableDel.getDiscount1().doubleValue()/100;
		double d2 =salesOrderDtlTableDel.getDiscount2().doubleValue()/100;
		double d3 =salesOrderDtlTableDel.getDiscount3().doubleValue()/100;
		double q =salesOrderDtlTableDel.getOrderQty();
		double hasil1 = (up * q)-((up * q)* d1);
		double hasil2 = hasil1 - (hasil1 * d2);
		double hasil3 = hasil2 - (hasil2 * d3);
		salesOrderDtlTableDel.setTotalPriceDiscount(hasil3);
		
		sumTotalNoPpn = new Double(0);
		Double sumTotalPpn = new Double(0);
		Double ppn = 0.1;
		
		if (ppnFlag = true && salesOrderVoHeader.getPpn().equals("PPN")) {
			sumTotalNoPpn = hasil3 * ppn;
			sumTotalPpn = hasil3 + sumTotalNoPpn;
			salesOrderDtlTableDel.setSumTotal(sumTotalPpn);
		} else  {
			salesOrderDtlTableDel.setSumTotal(hasil3);
		}
		
		salesOrderVoHeader.setSumTotal(salesOrderVoHeader.getSumTotal()
				.doubleValue() - salesOrderDtlTableDel.getSumTotal());
		
	}
	
	public void buttonDelete(){
		salesOrderDtlVOList.remove(salesOrderDtlTableDel);
		hapusUpdate();
		salesOrderVoHeader.setItemAmount(new Long(salesOrderDtlVOList.size()));
		disableButtonOnRowClicked = false;		
	}
	
	public void buttonEditProduct(){
		soDtlVoDialogProduct = salesOrderDtlTableDel;
	}
	
	public void onRowSelect(SelectEvent event){
		disableButtonOnRowClicked = true;
	}
	
	public void onRowUnSelect(SelectEvent event){
		disableButtonOnRowClicked = false;
	}	
	
	public void buttonCancelDialogProduct(){
		soDtlVoDialogProduct = new DraftSalesOrderDtlVO();
	}
	
	public void buttonSaveDialogProduct(){		
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
	
	
	public void buttonDelTablePayment(PaymentTypeVO payTypeVO){
    	dataPaymentTypeVoList.remove(payTypeVO);
    }
	
	public void buttonCancelDialogCustomer(){
    	customerVO = new CustomerVO();
    }
    
/*    public boolean validateRegister(){
		boolean valid = true;
		if(salesOrderVoHeader.getRegisterId() == null){
			facesUtils
			.addFacesMessage(
					FacesMessage.SEVERITY_ERROR, " Register "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));		
			
			valid = false;			
		}
				
		return valid;
	}*/
    
    public void buttonPayment(){
		dataPaymentTypeVoList = new ArrayList<PaymentTypeVO>();
		
			salesOrderVoHeader.setSumTotalPayment(salesOrderVoHeader.getSumTotal());
			
			RequestContext.getCurrentInstance().execute("PF('dialogPembayaranDistributor').show()");
/*		}else{
			RequestContext.getCurrentInstance().execute("PF('dialogPembayaranDistributor').hide()");
		}*/
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
    
	
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DraftSalesOrderBean.logger = logger;
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


	public String getCompleteSearchProductDistributor() {
		return completeSearchProductDistributor;
	}

	public void setCompleteSearchProductDistributor(String completeSearchProductDistributor) {
		this.completeSearchProductDistributor = completeSearchProductDistributor;
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

	/*public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}*/

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


	public boolean isDisableButtonOnRowClicked() {
		return disableButtonOnRowClicked;
	}

	public void setDisableButtonOnRowClicked(boolean disableButtonOnRowClicked) {
		this.disableButtonOnRowClicked = disableButtonOnRowClicked;
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

	public List<ItemDiscountVO> getItemDiscountList() {
		return itemDiscountList;
	}

	public void setItemDiscountList(List<ItemDiscountVO> itemDiscountList) {
		this.itemDiscountList = itemDiscountList;
	}

	public ItemDiscountService getItemDiscountService() {
		return itemDiscountService;
	}

	public void setItemDiscountService(ItemDiscountService itemDiscountService) {
		this.itemDiscountService = itemDiscountService;
	}

	public boolean isDisableSeacrh() {
		return disableSeacrh;
	}

	public void setDisableSeacrh(boolean disableSeacrh) {
		this.disableSeacrh = disableSeacrh;
	}

	public DraftSalesOrderDistributorService getDraftSalesOrderDistributorService() {
		return draftSalesOrderDistributorService;
	}

	public void setDraftSalesOrderDistributorService(DraftSalesOrderDistributorService draftSalesOrderDistributorService) {
		this.draftSalesOrderDistributorService = draftSalesOrderDistributorService;
	}

	public List<DraftSalesOrderDtlVO> getSalesOrderDtlVOList() {
		return salesOrderDtlVOList;
	}

	public void setSalesOrderDtlVOList(List<DraftSalesOrderDtlVO> salesOrderDtlVOList) {
		this.salesOrderDtlVOList = salesOrderDtlVOList;
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

	public String getSouNmber() {
		return souNmber;
	}

	public void setSouNmber(String souNmber) {
		this.souNmber = souNmber;
	}

	public List<SelectItem> getChoseeSoSelectItem() {
		return choseeSoSelectItem;
	}

	public void setChoseeSoSelectItem(List<SelectItem> choseeSoSelectItem) {
		this.choseeSoSelectItem = choseeSoSelectItem;
	}

	public boolean isPpnFlag() {
		return ppnFlag;
	}

	public void setPpnFlag(boolean ppnFlag) {
		this.ppnFlag = ppnFlag;
	}

	public double getSumTotalNoPpn() {
		return sumTotalNoPpn;
	}

	public void setSumTotalNoPpn(double sumTotalNoPpn) {
		this.sumTotalNoPpn = sumTotalNoPpn;
	}
	
	
	
}
