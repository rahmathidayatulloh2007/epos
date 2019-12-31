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
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.master.equipment.dao.EquipmentDAO;
import com.wo.epos.module.master.equipment.model.Equipment;
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



@ManagedBean(name="draftSalesOrderService")
@ViewScoped
public class DraftSalesOrderServiceImpl implements DraftSalesOrderService, Serializable{
	
	private static final long serialVersionUID = 9176120095635185573L;

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

	@Override
	public void delete(Long salesOrderId) {		
		try{
			DraftSalesOrder salesOrderHeader = new DraftSalesOrder();
			salesOrderHeader = draftSalesOrderDAO.findById(salesOrderId);
			
			for(DraftSalesOrderDtl soDtl : salesOrderHeader.getSalesOrderDtlList()){
				DraftSalesOrderDtl salesOrderDtl = new DraftSalesOrderDtl();
				salesOrderDtl = draftSalesOrderDtlDAO.findById(soDtl.getSoDtlId());
				draftSalesOrderDtlDAO.delete(salesOrderDtl);			
			}
						
			draftSalesOrderDAO.delete(salesOrderHeader);
			draftSalesOrderDAO.flush();
			
		}catch(Exception ex){
			ex.printStackTrace();
			draftSalesOrderDAO.rollback();
		}
	}

	@Override
	public DraftSalesOrder findById(Long salesOrderId) {
		
		return draftSalesOrderDAO.findById(salesOrderId);
	}
	
	@Override
	public String searchSalesOrderMax(String year, String yearMonth, String yearMonthDate) {
		return draftSalesOrderDAO.searchSalesOrderMax(year, yearMonth, yearMonthDate);
	}
	
	@Override
	public DraftSalesOrderVO searchDataSoByEquipment(Long equipmentId,
			String equipmentStatus) {
		return draftSalesOrderDAO.searchDataSoByEquipment(equipmentId, equipmentStatus);
	}
	
	@Override
	public DraftSalesOrder saveSoDistro(DraftSalesOrderVO salesOrderVO,
			List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user) {
		
	   try{			
		   DraftSalesOrder soHeader = new DraftSalesOrder();
			soHeader.setOutletId(salesOrderVO.getOutletId());
			if(salesOrderVO.getEquipmentId() !=null){
			    soHeader.setEquipmentId(salesOrderVO.getEquipmentId());
			   
			    //Update Equipment
				Equipment equipment = new Equipment();
				equipment = equipmentDAO.findById(salesOrderVO.getEquipmentId());
				equipment.setEquipmentStatusCode(CommonConstants.EQUIPMENT_OCCUPY);
				equipment.setUpdateBy(user);
				equipment.setUpdateOn(new Timestamp(System.currentTimeMillis()));				
				equipmentDAO.update(equipment);
				
			}
			
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
					 if(soDtlVo.getIngredientFlag().equals(CommonConstants.Y)){
						 soDetail.setDeliveryStatusCode(CommonConstants.DELIVERY_HOLD);
					 }else{
						 soDetail.setDeliveryStatusCode(CommonConstants.DELIVERY_FINISH);
					 }
				 }
				 
				 if(soDtlVo.getIngredientFlag().equals(CommonConstants.Y)){
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
	public void updateSoDistro(DraftSalesOrderVO salesOrderVO,
			List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user) {
		
        String detailResume = "";
		
        DraftSalesOrder soHeader = new DraftSalesOrder();
        soHeader = draftSalesOrderDAO.findById(salesOrderVO.getSoId());
        
        if(salesOrderVO.getTaxTypeCode().equals(CommonConstants.TAX_EXCLUDE)){
		   soHeader.setTaxTypeCode(salesOrderVO.getTaxTypeCode());
		   soHeader.setTaxValue(new Double(salesOrderVO.getTaxValue()));
		}else{
			soHeader.setTaxTypeCode(salesOrderVO.getTaxTypeCode());
		}		 
		 
        Long itemAmount = new Long(0);
		List<DraftSalesOrderDtl> childListReal = soHeader.getSalesOrderDtlList();
		if(salesOrderDtlVoList != null) {
			DraftSalesOrderDtl salesOrderDtl = null;
			DraftSalesOrderDtl salesOrderDtlDatabase = null;
			DraftSalesOrderDtlVO salesOrderDtlVo = null;
			boolean exist = false;
			
			/* untuk insert data baru dan update data lama */
			for(int x=0; x<salesOrderDtlVoList.size(); x++)
			{
				salesOrderDtlVo = (DraftSalesOrderDtlVO) salesOrderDtlVoList.get(x);
				
				exist = false;
				for(int i=0; i<childListReal.size(); i++)
				{
					salesOrderDtlDatabase = (DraftSalesOrderDtl) childListReal.get(i);
					
					if (Integer.parseInt(salesOrderDtlDatabase.getProductId()+"") == Integer.parseInt(salesOrderDtlVo.getProductId()+"")) {
						exist = true;
						break;
					}
				}
				
				if (exist)
				{	/* update */
					salesOrderDtlDatabase.setOrderQty(salesOrderDtlVo.getOrderQty());
					salesOrderDtlDatabase.setNotes(salesOrderDtlVo.getNotes());
					salesOrderDtlDatabase.setUpdateBy(user);
					salesOrderDtlDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				}
				else
				{	/* insert */
					salesOrderDtl = new DraftSalesOrderDtl();
					salesOrderDtl.setDraftSalesOrder(soHeader);
					salesOrderDtl.setProductId(salesOrderDtlVo.getProductId());
					salesOrderDtl.setLineNo(x+1);
					salesOrderDtl.setOrderQty(salesOrderDtlVo.getOrderQty());
					salesOrderDtl.setOrderUmId(salesOrderDtlVo.getOrderUm());
					salesOrderDtl.setUnitPrice(salesOrderDtlVo.getUnitPrice());
					salesOrderDtl.setTotalPriceDiscount(salesOrderDtlVo.getTotalPriceDiscount());
					salesOrderDtl.setDiscount1(salesOrderDtlVo.getDiscount1());
					salesOrderDtl.setDiscount2(salesOrderDtlVo.getDiscount2());
					salesOrderDtl.setDiscount3(salesOrderDtlVo.getDiscount3());
					if(salesOrderDtlVo.getDiscTypeCode() !=null && !salesOrderDtlVo.getDiscTypeName().equals("")){
						salesOrderDtl.setDiscTypeCode(CommonConstants.DISCTYPE_PERCENT);
						salesOrderDtl.setDiscPercent(salesOrderDtlVo.getDiscPercent());
					}else {
						salesOrderDtl.setDiscTypeCode(CommonConstants.DISCTYPE_WITHOUT);
						salesOrderDtl.setDiscValue(null);
					}
					
					if(salesOrderVO.getSoTypeCode().equals(CommonConstants.SO_DELIVERY)){
						salesOrderDtl.setDeliveryStatusCode(CommonConstants.DELIVERY_HOLD);
					 }else{
						 if(salesOrderDtlVo.getIngredientFlag().equals(CommonConstants.Y)){
							 salesOrderDtl.setDeliveryStatusCode(CommonConstants.DELIVERY_HOLD);
						 }else{
							 salesOrderDtl.setDeliveryStatusCode(CommonConstants.DELIVERY_FINISH);
						 }
					 }
					
					if(salesOrderDtlVo.getIngredientFlag().equals(CommonConstants.Y)){
						 salesOrderDtl.setPreparationStatusCode(CommonConstants.PREPARATION_HOLD);
					 }else{
						 salesOrderDtl.setPreparationStatusCode(CommonConstants.PREPARATION_NONE);
					 }
				
					
					salesOrderDtl.setNotes(salesOrderDtlVo.getNotes());
					salesOrderDtl.setActiveFlag(CommonConstants.Y);
					salesOrderDtl.setCreateBy(user);
					salesOrderDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));			
					salesOrderDtl.setCreateBy(user);
					
					childListReal.add(salesOrderDtl);
				}
			}

			
			for(int i=0; i<childListReal.size(); i++)
			{
				salesOrderDtlDatabase = (DraftSalesOrderDtl) childListReal.get(i);

				for(int x=0; x<salesOrderDtlVoList.size(); x++)
				{
					salesOrderDtlVo =(DraftSalesOrderDtlVO) salesOrderDtlVoList.get(x);
					exist = false;
					
					if (Integer.parseInt(salesOrderDtlDatabase.getProductId()+"") == Integer.parseInt(salesOrderDtlVo.getProductId()+"")) {
							exist = true;
							
							String content = salesOrderDtlVo.getProductName()+ ": " + salesOrderDtlVo.getOrderQty()+" "+salesOrderDtlVo.getOrderUmName();
							if (i == 0) {
									detailResume = content;
							} else {
							 		detailResume = detailResume + ", " + content;
							}
							
							itemAmount = new Long(i+1);
							
							break;
						}
					}
				

				if (!exist)
				{	/* delete */
					i--;
					childListReal.remove(salesOrderDtlDatabase);
				}
			}
			
		}		
		
		soHeader.setItemAmount(itemAmount);
		soHeader.setProductResume(detailResume);
		soHeader.setUpdateBy(user);
		soHeader.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		draftSalesOrderDAO.update(soHeader);
		//salesOrderDAO.flush();		
	}

	@Override
	public void savePayment(DraftSalesOrderVO salesOrderVO,
			List<DraftSalesOrderDtlVO> salesOrderDtlVoList, List<PaymentTypeVO> paymentTypeVOList, String user) {
	  try{   
		    if(salesOrderVO.getStatusCode() !=null && !salesOrderVO.getStatusCode().equals("")){
		    	DraftSalesOrder soHeader = updateSoPayment(salesOrderVO, user);
		    	insertSoInvoice(salesOrderVO, salesOrderDtlVoList, paymentTypeVOList, soHeader, user);
		    	updateItemStock(salesOrderVO, salesOrderDtlVoList, user);
				
		    }else{	
		    	DraftSalesOrder soHeader = saveSoDistro(salesOrderVO, salesOrderDtlVoList, user);
		    	insertSoInvoice(salesOrderVO, salesOrderDtlVoList, paymentTypeVOList, soHeader, user);
		    	updateEnquipment(salesOrderVO, user);
		    	updateItemStock(salesOrderVO, salesOrderDtlVoList, user);
		    }		    
	  }catch(Exception ex){
		  ex.printStackTrace();
		  draftSalesOrderDAO.rollback();
	  }
		
	}
	
	@Override
    public DraftSalesOrder updateSoPayment(DraftSalesOrderVO salesOrderVO, String user){
		DraftSalesOrder soHeader = new DraftSalesOrder();
		soHeader = draftSalesOrderDAO.findById(salesOrderVO.getSoId());
		soHeader.setStatusCode(salesOrderVO.getStatusCode());
		if(salesOrderVO.getEquipmentId() !=null){ 
		    //Update Equipment
			Equipment equipment = new Equipment();
			equipment = equipmentDAO.findById(salesOrderVO.getEquipmentId());
			equipment.setEquipmentStatusCode(CommonConstants.EQUIPMENT_VACANT);
			equipment.setUpdateBy(user);
			equipment.setUpdateOn(new Timestamp(System.currentTimeMillis()));				
			equipmentDAO.update(equipment);
		}
		
		soHeader.setUpdateBy(user);
		soHeader.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
		draftSalesOrderDAO.update(soHeader);
	    
		return soHeader;
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
	public DraftSalesOrder saveSoDistroBill(DraftSalesOrderVO salesOrderVO,
			List<DraftSalesOrderDtlVO> salesOrderDtlVoList,
			List<DraftSalesOrderVO> tagihanList, String user) {
		try{
			if(salesOrderVO.getSoId() == null){
				DraftSalesOrder salesOrder = saveSoDistro(salesOrderVO, salesOrderDtlVoList, user);		
				DraftSalesOrder salesOrder2 = insertInvoiceBill(salesOrder, salesOrderVO, tagihanList, user);
				  
				 return salesOrder2;				 
			}else{
			   updateSoDistro(salesOrderVO, salesOrderDtlVoList, user);
			   DraftSalesOrder salesOrder2 = insertInvoiceBill(null, salesOrderVO, tagihanList, user); 
			   
			   return salesOrder2;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
		
	}
	
	private DraftSalesOrder insertInvoiceBill(DraftSalesOrder salesOrder, DraftSalesOrderVO salesOrderVO, List<DraftSalesOrderVO> tagihanList, String user){
		  // Long soDtlId = null;
		   if(salesOrder == null){
			   salesOrder = findById(salesOrderVO.getSoId());
			   //soDtlId = salesOrder.getSalesOrderDtlList().get(0).getSoDtlId();
		   }
		   String soInvNo = "";
		   for(int i=0; i<tagihanList.size(); i++){
			   DraftSalesOrderVO soHeaderVo = (DraftSalesOrderVO)tagihanList.get(i);
			    
			    Invoice invoiceHeader = new Invoice();
			    invoiceHeader.setSalesOrderId(salesOrder.getSoId());
				invoiceHeader.setOutletId(salesOrder.getOutletId());
				if(i == 0){
				   invoiceHeader.setSoInvNo(invoiceDAO.runningNumberInvoice(CommonConstants.SOINV_NUMBERFORMAT, salesOrderVO.getCompanyId()));
				   soInvNo =  invoiceHeader.getSoInvNo();
				}else{
					if(soInvNo !=null && !soInvNo.equals("")){
					    invoiceHeader.setSoInvNo(invoiceDAO.runningNumberInvoiceTagihan(CommonConstants.SOINV_NUMBERFORMAT, soInvNo, salesOrderVO.getCompanyId()));
					}else{
						invoiceHeader.setSoInvNo(invoiceDAO.runningNumberInvoice(CommonConstants.SOINV_NUMBERFORMAT, salesOrderVO.getCompanyId()));
					}
					
				}
				   
				invoiceHeader.setSoInvDate(salesOrder.getSoDate());
				invoiceHeader.setStatusCode(CommonConstants.SOINVOICE_NEW);
				invoiceHeader.setActiveFlag(CommonConstants.Y);
				
				String detailResume = "";
				List<InvoiceDtl> invoiceDtlList = new ArrayList<InvoiceDtl>();
			    for(int j=0; j<soHeaderVo.getTagihanList().size(); j++){
			    	DraftSalesOrderDtlVO soDtlVo = (DraftSalesOrderDtlVO)soHeaderVo.getTagihanList().get(j);
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
					  invoiceDtlList.add(invDtl);	
					
					  String content = soDtlVo.getProductName()+ ": " + soDtlVo.getOrderQty()+" "+soDtlVo.getOrderUmName();
					  if (j == 0) {
							detailResume = content;
					  } else {
					  		detailResume = detailResume + ", " + content;
					  }
							
			    }
			    
			    invoiceHeader.setInvoiceDtlList(invoiceDtlList);
			    invoiceHeader.setProductResume(detailResume);
			    invoiceHeader.setCreateBy(user);
				invoiceHeader.setCreateOn(new Timestamp(System.currentTimeMillis()));
			    invoiceDAO.save(invoiceHeader);
			    
		   }
		   
		   return salesOrder;
	}

	@Override
	public void saveSoDistroPaymentBill(DraftSalesOrderVO salesOrderVO, List<PaymentTypeVO> paymentTypeVOList, String user) {
		
		try{
			//Update Invoice
			Invoice invoiceHeader = new Invoice();
			invoiceHeader =  invoiceDAO.findById(salesOrderVO.getInvoiceId());	
			invoiceHeader.setStatusCode(CommonConstants.SOINVOICE_PAYMENT);
			invoiceHeader.setUpdateBy(user);
			invoiceHeader.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			invoiceDAO.update(invoiceHeader);
			
			//Insert receipt Header
			Receipt receiptHeader = new Receipt();
			receiptHeader.setOutletId(salesOrderVO.getOutletId());
			receiptHeader.setReceiptNo(receiptDAO.runningNumberReceipt(CommonConstants.SORCPT_NUMBERFORMAT, salesOrderVO.getCompanyId()));
			receiptHeader.setReceiptDate(salesOrderVO.getSoDate());		
			receiptHeader.setInvoice(invoiceHeader);
			receiptHeader.setNotes(salesOrderVO.getNotes());
			receiptHeader.setActiveFlag(CommonConstants.Y);
			receiptHeader.setReceiptAmount(salesOrderVO.getSumTotal());
			receiptHeader.setRegisterId(salesOrderVO.getRegisterId());
			
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

    private void updateEnquipment(DraftSalesOrderVO salesOrderVO, String user){
    	Equipment equipment = new Equipment();
		equipment = equipmentDAO.findById(salesOrderVO.getEquipmentId());
		equipment.setEquipmentStatusCode(CommonConstants.EQUIPMENT_VACANT);
		equipment.setUpdateBy(user);
		equipment.setUpdateOn(new Timestamp(System.currentTimeMillis()));				
		equipmentDAO.update(equipment);
    }
    
    
   
	private void updateItemStock(DraftSalesOrderVO salesOrderVo, List<DraftSalesOrderDtlVO> salesOrderDtlVoList, String user){    	
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
    }
	
	private String soInvoiceNumberTagihan(String soInvNumber){
		String runnigNumber = "";
		
		if(soInvNumber !=null && !soInvNumber.equals("")){
			String[] split = soInvNumber.split("-");
			String number = null;
			Integer splitInt = Integer.parseInt(split[1]);
			
			if ((splitInt + "").length() == 5) {
				number = "" + (splitInt + 1);
			} else if ((splitInt + "").length() == 4) {
				number = "0" + (splitInt + 1);
			} else if ((splitInt + "").length() == 3) {
				if(splitInt >= 999){
					 number = "0" + (splitInt + 1);
				 }else{
					 number = "00" + (splitInt + 1);
				 }							
			} else if ((splitInt + "").length() == 2) {
				 if(splitInt >= 99){
					 number = "00" + (splitInt + 1);
				 }else{
					 number = "000" + (splitInt + 1);
				 }	
			} else if((splitInt + "").length() == 1){
				if(splitInt >= 9)
				{
					number = "000" + (splitInt + 1);
				}
				else{
					number = "0000" + (splitInt + 1);
				}
			}
			runnigNumber =  split[0] +"-"+number;
		}		
		
		
		return runnigNumber;
	}

	@Override
	public List<DraftSalesOrderVO> searchDraftSalesOrderList() {
		// TODO Auto-generated method stub
		return draftSalesOrderDAO.searchDraftSalesOrderList();
	}

	@Override
	public List<DraftSalesOrderVO> searchSalesOrderListCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return draftSalesOrderDAO.searchSalesOrderListCustomerId(customerId);
	}

	@Override
	public List<DraftSalesOrder> findSalesOrderByCustomer(Long customerId) {
		// TODO Auto-generated method stub
		return draftSalesOrderDAO.findSalesOrderByCustomer(customerId);
	}

	@Override
	public List<DraftSalesOrder> findSalesOrder() {
		// TODO Auto-generated method stub
		return draftSalesOrderDAO.findSalesOrder();
	}	
  	

}
