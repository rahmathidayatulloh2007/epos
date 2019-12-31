package com.wo.epos.module.sales.invoice.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.vo.InvoiceVO;

public interface InvoiceService extends RetrieverDataPage<InvoiceVO> {

	public List<Invoice> searchDataInvoiceBySoId(Long salesOrderId);
	public List<Invoice> searchDataInvoiceBySoIdAndStatus(Long salesOrderId, String statusCode);
	
}	
	