package com.wo.epos.module.sales.invoice.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.invoice.model.InvoiceDtl;
import com.wo.epos.module.sales.invoice.vo.InvoiceDtlVO;

public interface InvoiceDtlDAO  extends GenericDAO<InvoiceDtl, Long>, RetrieverDataPage<InvoiceDtlVO>{
	
}
