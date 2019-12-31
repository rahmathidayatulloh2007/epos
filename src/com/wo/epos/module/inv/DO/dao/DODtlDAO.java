package com.wo.epos.module.inv.DO.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.DO.model.DODtl;
import com.wo.epos.module.inv.DO.vo.DODtlVO;


public interface DODtlDAO extends GenericDAO<DODtl, Long>, RetrieverDataPage<DODtlVO> {

	

}