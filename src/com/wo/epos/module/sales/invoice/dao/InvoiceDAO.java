package com.wo.epos.module.sales.invoice.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.vo.InvoiceVO;

public interface InvoiceDAO  extends GenericDAO<Invoice, Long>, RetrieverDataPage<InvoiceVO>{
	
	public String searchInvoiceNoMax(String year, String yearMonth, String yearMonthDate);	
	public String runningNumberInvoice(String systemPropertyName, Long companyId);
	public String runningNumberInvoiceTagihan(String systemPropertyName, String soInvNumber, Long companyId);
	
	public List<Invoice> searchDataInvoiceBySoId(Long salesOrderId);
	public List<Invoice> searchDataInvoiceBySoIdAndStatus(Long salesOrderId, String statusCode);
	
}
