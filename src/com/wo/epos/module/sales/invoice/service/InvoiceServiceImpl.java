package com.wo.epos.module.sales.invoice.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.invoice.dao.InvoiceDAO;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.vo.InvoiceVO;

@ManagedBean(name="invoiceService")
@ViewScoped
public class InvoiceServiceImpl implements InvoiceService, Serializable {

	private static final long serialVersionUID = 5562470585670930106L;
	
	@ManagedProperty(value="#{invoiceDAO}")
	private InvoiceDAO invoiceDAO;
		
	
	public InvoiceDAO getInvoiceDAO() {
		return invoiceDAO;
	}

	public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
		this.invoiceDAO = invoiceDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<InvoiceVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		return invoiceDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		return invoiceDAO.searchCountData(searchCriteria);
	}

	@Override
	public List<Invoice> searchDataInvoiceBySoId(Long salesOrderId) {
		return invoiceDAO.searchDataInvoiceBySoId(salesOrderId);
	}

	@Override
	public List<Invoice> searchDataInvoiceBySoIdAndStatus(Long salesOrderId,
			String statusCode) {
		return invoiceDAO.searchDataInvoiceBySoIdAndStatus(salesOrderId, statusCode);
	}
	
	
}
