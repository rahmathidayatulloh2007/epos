package com.wo.epos.module.sales.salesOrder.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

public interface SalesOrderDtlDAO  extends GenericDAO<SalesOrderDtl, Long>, RetrieverDataPage<SalesOrderDtlVO>{
	public List<SalesOrderDtlVO> searchListSoDtlVO(Long soId);
	public List<SalesOrderDtl> searchListSoDtl(Long soId);
	
}
