package com.wo.epos.module.inv.rn.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.rn.model.RnDtl;
import com.wo.epos.module.inv.rn.vo.RnDtlVO;

public interface RnDtlDAO extends GenericDAO<RnDtl, Long>, RetrieverDataPage<RnDtlVO> {
	

}
