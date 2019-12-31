package com.wo.epos.module.inv.rn.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.rn.model.Rn;
import com.wo.epos.module.inv.rn.vo.RnVO;

public interface RnDAO extends GenericDAO<Rn, Long>, RetrieverDataPage<RnVO> {
	
	public String searchRnNumberMax(String dateYearMonth);
	
}
