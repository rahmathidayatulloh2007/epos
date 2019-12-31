package com.wo.epos.module.sales.salesOrder.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

public interface SalesOrderDtlService extends RetrieverDataPage<SalesOrderDtlVO> {
/*	public List<SalesOrderDtlVO> searchListSoDtlVO(Long soId);*/

	public List<SalesOrderDtl> searchListSoDtl(Long soId);
}
