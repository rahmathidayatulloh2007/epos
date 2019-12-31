package com.wo.epos.module.inv.stockOpname.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.stockOpname.model.StockOpname;
import com.wo.epos.module.inv.stockOpname.vo.StockOpnameVO;

public interface StockOpnameService extends RetrieverDataPage<StockOpnameVO>{

	public void save(StockOpnameVO stockOpnameVO, String user);
	public void update(StockOpnameVO stockOpnameVO, String user);
	public void delete(Long stockOpnameId);
	public StockOpname findById(Long opnameId);
	public Boolean closeOpname(StockOpnameVO stockOpnameVO, String userCode);
	
}
