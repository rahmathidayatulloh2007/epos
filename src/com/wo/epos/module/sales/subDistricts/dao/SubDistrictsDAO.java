package com.wo.epos.module.sales.subDistricts.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.subDistricts.model.SubDistricts;
import com.wo.epos.module.sales.subDistricts.vo.SubDistrictsVO;


public interface SubDistrictsDAO extends GenericDAO<SubDistricts, Long>, RetrieverDataPage<SubDistrictsVO> {
	
 
	public List<SubDistrictsVO> subDistrictsSearch();

	
}
