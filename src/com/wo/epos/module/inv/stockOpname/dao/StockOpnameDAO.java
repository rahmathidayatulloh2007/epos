package com.wo.epos.module.inv.stockOpname.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.stockOpname.model.StockOpname;
import com.wo.epos.module.inv.stockOpname.vo.StockOpnameVO;

public interface StockOpnameDAO extends GenericDAO<StockOpname, Long>, RetrieverDataPage<StockOpnameVO>{

	String searchStockOpnameMax(String year, String yearMonth,
			String yearMonthDate);

	String runningNumberStockOpname(String systemPropertyName, Long companyId);
	
	
}
