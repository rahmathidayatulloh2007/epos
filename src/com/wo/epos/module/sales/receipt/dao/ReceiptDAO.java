package com.wo.epos.module.sales.receipt.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.receipt.model.Receipt;
import com.wo.epos.module.sales.receipt.vo.ReceiptVO;

public interface ReceiptDAO  extends GenericDAO<Receipt, Long>, RetrieverDataPage<ReceiptVO> {
	
	public String searchReceiptNoMax(String year, String yearMonth, String yearMonthDate);
	public String runningNumberReceipt(String systemPropertyName, Long companyId);
	
	public List<Receipt> findReceiptByInvoiceId(Long invoiceId);
	
}
