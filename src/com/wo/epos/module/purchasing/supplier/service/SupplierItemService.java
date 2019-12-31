package com.wo.epos.module.purchasing.supplier.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;

public interface SupplierItemService  extends RetrieverDataPage<SupplierItemVO>{
	
	public void save(SupplierItemVO supplierItemVO);
	public void update(SupplierItemVO supplierItemVO);
	public void delete(Long supplierItemId);
	public SupplierItem findById(Long supplierItemId);

}
