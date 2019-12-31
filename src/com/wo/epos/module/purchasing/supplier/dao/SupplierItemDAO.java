package com.wo.epos.module.purchasing.supplier.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;

public interface SupplierItemDAO  extends GenericDAO<SupplierItem, Long>, RetrieverDataPage<SupplierItemVO> {

}
