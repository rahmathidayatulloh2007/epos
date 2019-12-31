package com.wo.epos.module.sales.receipt.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.receipt.model.ReceiptDtl;
import com.wo.epos.module.sales.receipt.vo.ReceiptDtlVO;

public interface ReceiptDtlDAO  extends GenericDAO<ReceiptDtl, Long>, RetrieverDataPage<ReceiptDtlVO>{
	
}
