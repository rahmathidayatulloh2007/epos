package com.wo.epos.module.sales.draftSalesOrder.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrderDtl;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

public interface DraftSalesOrderDtlDAO  extends GenericDAO<DraftSalesOrderDtl, Long>, RetrieverDataPage<DraftSalesOrderDtlVO>{

	public List<DraftSalesOrderDtlVO> searchListSoDtlVO(Long soId);
	public List<DraftSalesOrderDtl> searchListSoDtl(Long soId);
}
