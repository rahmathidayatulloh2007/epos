package com.wo.epos.module.sales.draftSalesOrder.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.item.dao.ProductDAO;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.master.equipment.dao.EquipmentDAO;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.master.systemProperty.dao.SystemPropertyDAO;
import com.wo.epos.module.sales.draftSalesOrder.dao.DraftSalesOrderDAO;
import com.wo.epos.module.sales.draftSalesOrder.dao.DraftSalesOrderDtlDAO;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrder;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrderDtl;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;
import com.wo.epos.module.sales.invoice.dao.InvoiceDAO;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.model.InvoiceDtl;
import com.wo.epos.module.sales.receipt.dao.ReceiptDAO;
import com.wo.epos.module.sales.receipt.model.Receipt;
import com.wo.epos.module.sales.receipt.model.ReceiptDtl;
import com.wo.epos.module.sales.register.dao.RegisterDAO;
import com.wo.epos.module.sales.register.dao.RegisterDtlDAO;
import com.wo.epos.module.sales.register.model.Register;
import com.wo.epos.module.sales.register.model.RegisterDtl;

@ManagedBean(name="draftSalesOrderDistributorService")
@ViewScoped
public class DraftSalesOrderDistributorServiceImpl implements DraftSalesOrderDistributorService, Serializable{
	
	private static final long serialVersionUID = -2039718923394019839L;

	@ManagedProperty(value="#{draftSalesOrderDAO}")
	private DraftSalesOrderDAO draftSalesOrderDAO;
	
	@ManagedProperty(value="#{draftSalesOrderDtlDAO}")
	private DraftSalesOrderDtlDAO draftSalesOrderDtlDAO;
	
	@ManagedProperty(value="#{systemPropertyDAO}")
	private SystemPropertyDAO systemPropertyDAO;
	
	@ManagedProperty(value="#{equipmentDAO}")
	private EquipmentDAO equipmentDAO;
	
	@ManagedProperty(value="#{invoiceDAO}")
	private InvoiceDAO invoiceDAO;
	
	@ManagedProperty(value="#{receiptDAO}")
	private ReceiptDAO receiptDAO;
	
	@ManagedProperty(value="#{registerDAO}")
	private RegisterDAO registerDAO;
	
	@ManagedProperty(value="#{registerDtlDAO}")
	private RegisterDtlDAO registerDtlDAO;
	
	@ManagedProperty(value="#{itemStockDAO}")
	private ItemStockDAO itemStockDAO;
	
	@ManagedProperty(value="#{productDAO}")
	private ProductDAO productDAO;
	
	

	public DraftSalesOrderDAO getDraftSalesOrderDAO() {
		return draftSalesOrderDAO;
	}

	public void setDraftSalesOrderDAO(DraftSalesOrderDAO draftSalesOrderDAO) {
		this.draftSalesOrderDAO = draftSalesOrderDAO;
	}

	public DraftSalesOrderDtlDAO getDraftSalesOrderDtlDAO() {
		return draftSalesOrderDtlDAO;
	}

	public void setDraftSalesOrderDtlDAO(DraftSalesOrderDtlDAO draftSalesOrderDtlDAO) {
		this.draftSalesOrderDtlDAO = draftSalesOrderDtlDAO;
	}

	public SystemPropertyDAO getSystemPropertyDAO() {
		return systemPropertyDAO;
	}

	public void setSystemPropertyDAO(SystemPropertyDAO systemPropertyDAO) {
		this.systemPropertyDAO = systemPropertyDAO;
	}

	public EquipmentDAO getEquipmentDAO() {
		return equipmentDAO;
	}

	public void setEquipmentDAO(EquipmentDAO equipmentDAO) {
		this.equipmentDAO = equipmentDAO;
	}	

	public InvoiceDAO getInvoiceDAO() {
		return invoiceDAO;
	}

	public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}
	
	public ReceiptDAO getReceiptDAO() {
		return receiptDAO;
	}

	public void setReceiptDAO(ReceiptDAO receiptDAO) {
		this.receiptDAO = receiptDAO;
	}	

	public RegisterDAO getRegisterDAO() {
		return registerDAO;
	}

	public void setRegisterDAO(RegisterDAO registerDAO) {
		this.registerDAO = registerDAO;
	}

	public RegisterDtlDAO getRegisterDtlDAO() {
		return registerDtlDAO;
	}

	public void setRegisterDtlDAO(RegisterDtlDAO registerDtlDAO) {
		this.registerDtlDAO = registerDtlDAO;
	}	

	public ItemStockDAO getItemStockDAO() {
		return itemStockDAO;
	}

	public void setItemStockDAO(ItemStockDAO itemStockDAO) {
		this.itemStockDAO = itemStockDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DraftSalesOrderVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return draftSalesOrderDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return draftSalesOrderDAO.searchCountData(searchCriteria);
	}
	
	@Override
	public void flush() {
		draftSalesOrderDAO.flush();		
	}
	
	@Override
	public void rollback() {
		draftSalesOrderDAO.rollback();		
	}
	
	private DraftSalesOrder saveSoDistro(DraftSalesOrderVO salesOrderVO,
			List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user) {
		
	   try{			
		   DraftSalesOrder soHeader = new DraftSalesOrder();
			soHeader.setOutletId(salesOrderVO.getOutletId());
			soHeader.setCustomerId(salesOrderVO.getCustomerId());			
			soHeader.setSoNo(draftSalesOrderDAO.runningNumberSo(CommonConstants.SO_NUMBERFORMAT, salesOrderVO.getCompanyId()));
			soHeader.setSoDate(salesOrderVO.getSoDate());
			soHeader.setSoTypeCode(salesOrderVO.getSoTypeCode());
			if(salesOrderVO.getSoTypeCode().equals(CommonConstants.SO_DELIVERY)){
				soHeader.setDeliveryStatusCode(CommonConstants.DELIVERY_HOLD);
				soHeader.setDeliveryCost(salesOrderVO.getDeliveryCost());
			}else{
				soHeader.setDeliveryStatusCode(CommonConstants.DELIVERY_NONE);
				soHeader.setDeliveryCost(null);
			}
			
			//SystemProperty sysTaxType =  systemPropertyDAO.searchSystemPropertyNameAndCompany(CommonConstants.DEFAULT_TAX_TYPE, salesOrderVO.getCompanyId());
			if(salesOrderVO.getTaxTypeCode().equals(CommonConstants.TAX_EXCLUDE)){
			   soHeader.setTaxTypeCode(salesOrderVO.getTaxTypeCode());
			   soHeader.setTaxValue(new Double(salesOrderVO.getTaxValue()));
			}else{
				soHeader.setTaxTypeCode(salesOrderVO.getTaxTypeCode());
			}
			  //SystemProperty sysTaxValue =  systemPropertyDAO.searchSystemPropertyNameAndCompany(CommonConstants.DEFAULT_TAX_VALUE, salesOrderVO.getCompanyId());
			
		
			soHeader.setItemAmount(new Long(salesOrderDtlVoList.size()));
			soHeader.setStatusCode(salesOrderVO.getStatusCode());
			soHeader.setActiveFlag(CommonConstants.Y);
						
			String detailResume = null;
			List<DraftSalesOrderDtl> soDetailList = new ArrayList<DraftSalesOrderDtl>();
			for(int i=0; i<salesOrderDtlVoList.size(); i++){
				DraftSalesOrderDtlVO soDtlVo = (DraftSalesOrderDtlVO)salesOrderDtlVoList.get(i);
				DraftSalesOrderDtl soDetail = new DraftSalesOrderDtl();
				 soDetail.setDraftSalesOrder(soHeader);
				 soDetail.setProductId(soDtlVo.getProductId());
				 soDetail.setLineNo(i+1);
				 soDetail.setOrderQty(soDtlVo.getOrderQty());
				 soDetail.setOrderUmId(soDtlVo.getOrderUm());
				 soDetail.setUnitPrice(soDtlVo.getUnitPrice());
				 soDetail.setTotalPriceDiscount(soDtlVo.getTotalPriceDiscount());
				 soDetail.setDiscount1(soDtlVo.getDiscount1());
				 soDetail.setDiscount2(soDtlVo.getDiscount2());
				 soDetail.setDiscount3(soDtlVo.getDiscount3());
				 if(soDtlVo.getDiscTypeCode() !=null && !soDtlVo.getDiscTypeName().equals("")){
					 soDetail.setDiscTypeCode(CommonConstants.DISCTYPE_PERCENT);
					 soDetail.setDiscPercent(soDtlVo.getDiscPercent());
				 }else {
					 soDetail.setDiscTypeCode(CommonConstants.DISCTYPE_WITHOUT);
					 soDetail.setDiscValue(null);
				 }
				
				 if(salesOrderVO.getSoTypeCode().equals(CommonConstants.SO_DELIVERY)){
					 soDetail.setDeliveryStatusCode(CommonConstants.DELIVERY_HOLD);
				 }else{
					 if(soDtlVo.getIngredientFlag() != null && soDtlVo.getIngredientFlag().equals(CommonConstants.Y)){
						 soDetail.setDeliveryStatusCode(CommonConstants.DELIVERY_HOLD);
					 }else{
						 soDetail.setDeliveryStatusCode(CommonConstants.DELIVERY_FINISH);
					 }
				 }
				 
				 if(soDtlVo.getIngredientFlag() != null && soDtlVo.getIngredientFlag().equals(CommonConstants.Y)){
					  soDetail.setPreparationStatusCode(CommonConstants.PREPARATION_HOLD);
				 }else{
					  soDetail.setPreparationStatusCode(CommonConstants.PREPARATION_NONE);
				 }
				 				 
				 soDetail.setNotes(soDtlVo.getNotes());
				 soDetail.setActiveFlag(CommonConstants.Y);
				 soDetail.setCreateBy(user);
				 soDetail.setCreateOn(new Timestamp(System.currentTimeMillis()));
				 
				 String content = soDtlVo.getProductName()+ ": " + soDtlVo.getOrderQty()+" "+soDtlVo.getOrderUmName();
				 if (i == 0) {
						detailResume = content;
				 } else {
				 		detailResume = detailResume + ", " + content;
				 }
				  
				 soDetailList.add(soDetail);
			}
			
			soHeader.setSalesOrderDtlList(soDetailList);
			soHeader.setProductResume(detailResume);
			soHeader.setCreateBy(user);
			soHeader.setCreateOn(new Timestamp(System.currentTimeMillis()));
			draftSalesOrderDAO.save(soHeader);
			//salesOrderDAO.flush();
			
			return soHeader;
			
	   }catch(Exception ex){
		   ex.printStackTrace();
		   draftSalesOrderDAO.rollback();	
	   }
	   
		return null;
	}
	
	@Override
	public void savePayment(DraftSalesOrderVO salesOrderVO,
			List<DraftSalesOrderDtlVO> salesOrderDtlVoList, List<PaymentTypeVO> paymentTypeVOList, String user) {
	  try{   
		  DraftSalesOrder soHeader = saveSoDistro(salesOrderVO, salesOrderDtlVoList, user);
		    insertSoInvoice(salesOrderVO, salesOrderDtlVoList, paymentTypeVOList, soHeader, user);
		   // updateEnquipment(salesOrderVO, user);
		   // updateItemStock(salesOrderVO, salesOrderDtlVoList, user);
		    
		    draftSalesOrderDAO.flush();    
	  }catch(Exception ex){
		  ex.printStackTrace();
		  draftSalesOrderDAO.rollback();
	  }
		
	}
	
	@Override
	public void saveDirect(DraftSalesOrderVO salesOrderVO,
			List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user) {
	  try{   
		    saveSoDistro(salesOrderVO, salesOrderDtlVoList, user);
		    //updateItemStock(salesOrderVO, salesOrderDtlVoList, user);
		    
		    draftSalesOrderDAO.flush();    
	  }catch(Exception ex){
		  ex.printStackTrace();
		  draftSalesOrderDAO.rollback();
	  }
		
	}
    
    private void insertSoInvoice(DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderDtlVO> salesOrderDtlVoList,
			  List<PaymentTypeVO> paymentTypeVOList, DraftSalesOrder salesOrder, String user){
		try{
	    	//Insert Invoice
			Invoice invoiceHeader = new Invoice();
			if(salesOrder !=null){
			  invoiceHeader.setSalesOrderId(salesOrder.getSoId());	
			}else{
				invoiceHeader.setSalesOrderId(salesOrderVO.getSoId());		
			}
			
			invoiceHeader.setOutletId(salesOrderVO.getOutletId());
			invoiceHeader.setSoInvNo(invoiceDAO.runningNumberInvoice(CommonConstants.SOINV_NUMBERFORMAT, salesOrderVO.getCompanyId()));
			invoiceHeader.setSoInvDate(salesOrderVO.getSoDate());
			invoiceHeader.setStatusCode(CommonConstants.SOINVOICE_PAYMENT);
			invoiceHeader.setActiveFlag(CommonConstants.Y);
			invoiceHeader.setProductResume(salesOrderVO.getProductResume());	
								
			String detailResume = null;
			List<InvoiceDtl> invoiceDtlList = new ArrayList<InvoiceDtl>();
			for(int i=0; i<salesOrderDtlVoList.size(); i++){
				DraftSalesOrderDtlVO soDtlVo = (DraftSalesOrderDtlVO)salesOrderDtlVoList.get(i);
				  
				  //Insert Invoice Detail
				  InvoiceDtl invDtl = new InvoiceDtl();
				  invDtl.setInvoice(invoiceHeader);
				  
				  for(int k=0; k<salesOrder.getSalesOrderDtlList().size(); k++){		
					  DraftSalesOrderDtl soDtl = (DraftSalesOrderDtl)salesOrder.getSalesOrderDtlList().get(k);
						 if(Integer.parseInt(soDtl.getProductId()+"") ==  Integer.parseInt(soDtlVo.getProductId()+"")){
							  invDtl.setSalesOrderDtlId(soDtl.getSoDtlId());
							  break;
						 }
				  }
				  				  
				  invDtl.setLineNo(i+1);
				  invDtl.setInvoiceQty(soDtlVo.getOrderQty());
				  invDtl.setActiveFlag(CommonConstants.Y);
				  invDtl.setCreateBy(user);
				  invDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
				  
				  String content = soDtlVo.getProductName()+ ": " + soDtlVo.getOrderQty()+" "+soDtlVo.getOrderUmName();
				  if (i == 0) {
						detailResume = content;
				  } else {
				 		detailResume = detailResume + ", " + content;
				  }
				  
				  invoiceDtlList.add(invDtl);			  
			}
			
			invoiceHeader.setProductResume(detailResume);
			invoiceHeader.setInvoiceDtlList(invoiceDtlList);
			invoiceHeader.setCreateBy(user);
			invoiceHeader.setCreateOn(new Timestamp(System.currentTimeMillis()));
			invoiceDAO.save(invoiceHeader);		
			/* End Insert Invoice */
			
			//Insert receipt Header
			Receipt receiptHeader = new Receipt();
			receiptHeader.setOutletId(salesOrderVO.getOutletId());
			receiptHeader.setReceiptNo(receiptDAO.runningNumberReceipt(CommonConstants.SORCPT_NUMBERFORMAT, salesOrderVO.getCompanyId()));
			receiptHeader.setReceiptDate(salesOrderVO.getSoDate());
			receiptHeader.setInvoice(invoiceHeader);
			receiptHeader.setNotes(salesOrderVO.getNotes());
			receiptHeader.setActiveFlag(CommonConstants.Y);
			receiptHeader.setReceiptAmount(salesOrderVO.getSumTotal());
			
			Double debitAmount = new Double(0);
			Double creditAmount = new Double(0);
			Double cashAmount = new Double(0); 
			List<ReceiptDtl> receiptDtlList = new ArrayList<ReceiptDtl>();
			for(int j=0; j<paymentTypeVOList.size();j++){
				  PaymentTypeVO paymentTypeVO = (PaymentTypeVO)paymentTypeVOList.get(j);
				  ReceiptDtl receiptDtl = new ReceiptDtl();
				  receiptDtl.setReceipt(receiptHeader);
				  receiptDtl.setPaymentTypeId(paymentTypeVO.getPaytypeId());
				  receiptDtl.setPaymentAmount(paymentTypeVO.getTotalPayment());
				  receiptDtl.setActiveFlag(CommonConstants.Y);
				  receiptDtl.setCreateBy(user);
				  receiptDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
				  
				  if(paymentTypeVO.getPaymentMethodCode().equals(CommonConstants.PAYMENT_CASH)){
					   cashAmount = cashAmount + paymentTypeVO.getTotalPayment();
				  }else if(paymentTypeVO.getPaymentMethodCode().equals(CommonConstants.PAYMENT_DEBIT)){
					  debitAmount = debitAmount + paymentTypeVO.getTotalPayment();
				  }else if(paymentTypeVO.getPaymentMethodCode().equals(CommonConstants.PAYMENT_CREDIT)){
					  creditAmount = creditAmount + paymentTypeVO.getTotalPayment();
				  }
				  
				  receiptDtlList.add(receiptDtl);
		    } 
			
			receiptHeader.setReceiptDtlList(receiptDtlList);
			receiptHeader.setCreateBy(user);
			receiptHeader.setCreateOn(new Timestamp(System.currentTimeMillis()));
			receiptDAO.save(receiptHeader);
			/*End Insert Receipt */
			
			/*Insert Update Register */
			Register registerHeader = new Register();
			registerHeader = registerDAO.findById(salesOrderVO.getRegisterId());		
			registerHeader.setUpdateBy(user);
			registerHeader.setUpdateOn(new Timestamp(System.currentTimeMillis()));	
			
			List<RegisterDtl> childRegister = registerHeader.getRegisterDtlList(); 
			for(RegisterDtl regDtl : childRegister){
				if(regDtl.getPaymentMethodCode().equals(CommonConstants.PAYMENT_CASH)){
					  regDtl.setTotalPayment(cashAmount);
				  }else if(regDtl.getPaymentMethodCode().equals(CommonConstants.PAYMENT_DEBIT)){
					  regDtl.setTotalPayment(debitAmount);
				  }else if(regDtl.getPaymentMethodCode().equals(CommonConstants.PAYMENT_CREDIT)){
					  regDtl.setTotalPayment(creditAmount);
				  }
				
				regDtl.setUpdateBy(user);
				regDtl.setUpdateOn(new Timestamp(System.currentTimeMillis()));	
			}
			
			registerDAO.update(registerHeader);		
			/* End Update Register*/
			
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}	    	
    }

	@Override
	public String runningNumberSo(String systemPropertyName, Long companyId) {
		// TODO Auto-generated method stub
		return draftSalesOrderDAO.runningNumberSo(systemPropertyName, companyId);
	}	

    /*private void updateEnquipment(SalesOrderVO salesOrderVO, String user){
    	Equipment equipment = new Equipment();
		equipment = equipmentDAO.findById(salesOrderVO.getEquipmentId());
		equipment.setEquipmentStatusCode(CommonConstants.EQUIPMENT_VACANT);
		equipment.setUpdateBy(user);
		equipment.setUpdateOn(new Timestamp(System.currentTimeMillis()));				
		equipmentDAO.update(equipment);
    }*/
           
/*	private void updateItemStock(DraftSalesOrderVO salesOrderVo, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user){    	
    	for(DraftSalesOrderDtlVO dtlVo : salesOrderDtlVoList){
    		Product product = new Product();
    		product = productDAO.findById(dtlVo.getProductId());
    		for(ProductBom pBom : product.getProductBomList()){
    			if(pBom.getItem() !=null && pBom.getItem().getItemId() !=null){
    				ItemStock itemStock = new ItemStock();
    		        itemStock = itemStockDAO.getItemStockByOutletIdAndItemId(salesOrderVo.getOutletId(), pBom.getItem().getItemId());
    		        Double qtySo = dtlVo.getOrderQty().doubleValue() * pBom.getItemQty().doubleValue();
    		        itemStock.setStockQty(itemStock.getStockQty() - qtySo.doubleValue());
    		        itemStock.setUpdateBy(user);
    		        itemStock.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
    		        itemStockDAO.update(itemStock);
    			}
    		     
    		}
    	}
    }*/
  	

}
