package com.wo.epos.module.master.province.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.master.province.vo.ProvinceVO;

public interface ProvinceDAO extends GenericDAO<Province, Long>, RetrieverDataPage<ProvinceVO>{
	
	public List<ProvinceVO>provinceSearch();
}
