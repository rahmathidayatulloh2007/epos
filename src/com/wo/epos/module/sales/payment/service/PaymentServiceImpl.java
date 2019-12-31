package com.wo.epos.module.sales.payment.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.paymentType.dao.PaymentTypeDAO;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.sales.invoice.dao.InvoiceDAO;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.model.InvoiceDtl;
import com.wo.epos.module.sales.payment.vo.PaymentVO;
import com.wo.epos.module.sales.receipt.dao.ReceiptDAO;
import com.wo.epos.module.sales.receipt.model.Receipt;
import com.wo.epos.module.sales.receipt.model.ReceiptDtl;
import com.wo.epos.module.sales.register.dao.RegisterDAO;
import com.wo.epos.module.sales.salesOrder.dao.SalesOrderDAO;
import com.wo.epos.module.sales.salesOrder.dao.SalesOrderDtlDAO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

@ManagedBean(name = "paymentService")
@ViewScoped
public class PaymentServiceImpl implements PaymentService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6471467956765394179L;

	@ManagedProperty(value = "#{salesOrderDtlDAO}")
	private SalesOrderDtlDAO salesOrderDtlDAO;
	@ManagedProperty(value = "#{salesOrderDAO}")
	private SalesOrderDAO salesOrderDAO;
	@ManagedProperty(value = "#{parameterDAO}")
	private ParameterDAO parameterDAO;
	@ManagedProperty(value = "#{invoiceDAO}")
	private InvoiceDAO invoiceDAO;
	@ManagedProperty(value = "#{receiptDAO}")
	private ReceiptDAO receiptDAO;
	@ManagedProperty(value = "#{registerDAO}")
	private RegisterDAO registerDAO;
	@ManagedProperty(value = "#{paymentTypeDAO}")
	private PaymentTypeDAO paymentTypeDAO;

	public SalesOrderDtlDAO getSalesOrderDtlDAO() {
		return salesOrderDtlDAO;
	}

	public void setSalesOrderDtlDAO(SalesOrderDtlDAO salesOrderDtlDAO) {
		this.salesOrderDtlDAO = salesOrderDtlDAO;
	}

	@Override
	public List<SalesOrderDtlVO> searchListSoDtlVO(Long soId) {
		// TODO Auto-generated method stub
		return salesOrderDtlDAO.searchListSoDtlVO(soId);
	}

	public List<ParameterVO> parameterSearch() {
		return parameterDAO.parameterSearch();
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	public SalesOrderDAO getSalesOrderDAO() {
		return salesOrderDAO;
	}

	public void setSalesOrderDAO(SalesOrderDAO salesOrderDAO) {
		this.salesOrderDAO = salesOrderDAO;
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
	
	

	public PaymentTypeDAO getPaymentTypeDAO() {
		return paymentTypeDAO;
	}

	public void setPaymentTypeDAO(PaymentTypeDAO paymentTypeDAO) {
		this.paymentTypeDAO = paymentTypeDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PaymentVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SalesOrderDtl> searchListSoDtl(Long soId) {
		// TODO Auto-generated method stub
		return salesOrderDtlDAO.searchListSoDtl(soId);
	}

	@Override
	public List<ParameterDtl> parameterDtlList() {
		// TODO Auto-generated method stub
		return parameterDAO.parameterDtlList(CommonConstants.PAYMENT_METHOD);
	}

	@Override
	public void savePayment(PaymentVO paymentVO,List<PaymentTypeVO> paymentTypeVOs,String userCode) {
		// TODO Auto-generated method stub

		SalesOrder salesOrder = new SalesOrder();
		salesOrder = salesOrderDAO.findById(paymentVO.getSoId());

		if (salesOrder.getStatus() != null) {
			ParameterDtl status = parameterDAO.findByDetailId(paymentVO.getStatusCode());
			salesOrder.setStatus(status);
			salesOrder.setStatusCode(status.getParameterDtlCode());
			salesOrder.setUpdateBy(userCode);
			salesOrder.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			salesOrderDAO.update(salesOrder);
		}
		
		
		salesOrder = salesOrderDAO.findDtlById(paymentVO.getSoId());
		
		insertSoInvoice(salesOrder,paymentVO,paymentTypeVOs,userCode);
		salesOrderDAO.flush();
		
	}

	private void insertSoInvoice(SalesOrder salesOrder,PaymentVO paymentVO,
			List<PaymentTypeVO> paymentTypeVOs, String user) {
		try {
			// Insert Invoice
			Invoice invoiceHeader = new Invoice();
			invoiceHeader.setSalesOrderId(salesOrder.getSoId());

			invoiceHeader.setOutletId(salesOrder.getOutletId());
			invoiceHeader.setSoInvNo(
					invoiceDAO.runningNumberInvoice(CommonConstants.SOINV_NUMBERFORMAT, paymentVO.getCompanyId()));
			invoiceHeader.setSoInvDate(salesOrder.getSoDate());
			invoiceHeader.setStatusCode(CommonConstants.SOINVOICE_PAYMENT);
			invoiceHeader.setActiveFlag(CommonConstants.Y);
			invoiceHeader.setProductResume(salesOrder.getProductResume());

			String detailResume = null;
			List<InvoiceDtl> invoiceDtlList = new ArrayList<InvoiceDtl>();
			for (int i = 0; i < salesOrder.getSalesOrderDtlList().size(); i++) {
				SalesOrderDtl soDtl = salesOrder.getSalesOrderDtlList().get(i);

				// Insert Invoice Detail
				InvoiceDtl invDtl = new InvoiceDtl();
				invDtl.setInvoice(invoiceHeader);

				for (int k = 0; k < salesOrder.getSalesOrderDtlList().size(); k++) {
					soDtl = (SalesOrderDtl) salesOrder.getSalesOrderDtlList().get(k);
					if (Integer.parseInt(soDtl.getProduct().getProductId() + "") == Integer.parseInt(soDtl.getProduct().getProductId() + "")) {
						invDtl.setSalesOrderDtlId(soDtl.getSoDtlId());
						break;
					}
				}

				invDtl.setLineNo(i + 1);
				invDtl.setInvoiceQty(soDtl.getOrderQty());
				invDtl.setActiveFlag(CommonConstants.Y);
				invDtl.setCreateBy(user);
				invDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));

				String content = soDtl.getProduct().getProductName() + ": " + soDtl.getOrderQty() + " "
						+ soDtl.getOrderUm().getUmName();
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

			// Insert receipt Header
			Receipt receiptHeader = new Receipt();
			receiptHeader.setOutletId(paymentVO.getOutletId());
			receiptHeader.setReceiptNo(
					receiptDAO.runningNumberReceipt(CommonConstants.SORCPT_NUMBERFORMAT, paymentVO.getCompanyId()));
			receiptHeader.setReceiptDate(salesOrder.getSoDate());
			receiptHeader.setInvoice(invoiceHeader);
			receiptHeader.setNotes(salesOrder.getNotes());
			receiptHeader.setActiveFlag(CommonConstants.Y);
			receiptHeader.setReceiptAmount(paymentVO.getSumTotal());

			Double debitAmount = new Double(0);
			Double creditAmount = new Double(0);
			Double cashAmount = new Double(0);
			List<ReceiptDtl> receiptDtlList = new ArrayList<ReceiptDtl>();
			for (int j = 0; j < paymentTypeVOs.size(); j++) {
				PaymentTypeVO paymentTypeVO = (PaymentTypeVO) paymentTypeVOs.get(j);
				ReceiptDtl receiptDtl = new ReceiptDtl();
				receiptDtl.setReceipt(receiptHeader);
				receiptDtl.setPaymentTypeId(paymentTypeVO.getPaytypeId());
				receiptDtl.setPaymentAmount(paymentTypeVO.getPayValue());
				receiptDtl.setOrderName(paymentTypeVO.getPayName());
				receiptDtl.setTransferDate(paymentTypeVO.getTransferDate());

				
				if(paymentTypeVO.getPaymentMethodCode().equals(CommonConstants.PAYMENT_CEK)){
					receiptDtl.setChequeNumber(paymentTypeVO.getGiroNo());
					receiptDtl.setChequeCashDate(paymentTypeVO.getPayDate());
				}
				if(paymentTypeVO.getPaymentMethodCode().equals(CommonConstants.PAYMENT_TRANSFER)){
					receiptDtl.setAccountNumber(paymentTypeVO.getNoRek());
					receiptDtl.setUnderName(paymentTypeVO.getPayUnderName());					
					receiptDtl.setInvFile(paymentTypeVO.getInvFile());
					receiptDtl.setInvFileName(paymentTypeVO.getInvFileName());
					
				}
				
				cashAmount = cashAmount + paymentTypeVO.getPayValue();
				
				receiptDtl.setActiveFlag(CommonConstants.Y);
				receiptDtl.setCreateBy(user);
				receiptDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
				receiptDtlList.add(receiptDtl);
			}

			receiptHeader.setReceiptDtlList(receiptDtlList);
			receiptHeader.setCreateBy(user);
			receiptHeader.setCreateOn(new Timestamp(System.currentTimeMillis()));
			receiptDAO.save(receiptHeader);
			/* End Insert Receipt */

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<PaymentTypeVO> searchDataPaymentByPaymentMethod(String paymentMethodCode) {
		// TODO Auto-generated method stub
		return paymentTypeDAO.searchDataPaymentByPaymentMethod(paymentMethodCode);
	}

	@Override
	public List<PaymentTypeVO> searchDataPaymentType() {
		// TODO Auto-generated method stub
		return paymentTypeDAO.searchDataPaymentType();
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		// TODO Auto-generated method stub
		return parameterDAO.parameterDtlList(parameterCode);
	}
	
	

}
