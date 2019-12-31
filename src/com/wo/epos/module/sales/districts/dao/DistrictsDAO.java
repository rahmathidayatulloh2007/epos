package com.wo.epos.module.sales.districts.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.districts.model.Districts;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;


public interface DistrictsDAO extends GenericDAO<Districts, Long>, RetrieverDataPage<DistrictsVO> {
	
	public List<DistrictsVO> districtsSearch();
	
}
