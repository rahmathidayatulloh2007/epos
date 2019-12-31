package com.wo.epos.module.inv.transferItem.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.transferItem.model.TransferItemDtl;
import com.wo.epos.module.inv.transferItem.vo.TransferItemDtlVO;


public interface TransferItemDtlDAO extends GenericDAO<TransferItemDtl, Long>, RetrieverDataPage<TransferItemDtlVO> {

	

}