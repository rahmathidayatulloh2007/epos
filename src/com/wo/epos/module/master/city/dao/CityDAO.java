package com.wo.epos.module.master.city.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.model.Province;

public interface CityDAO extends GenericDAO<City, Long>, RetrieverDataPage<CityVO>{

	public List<CityVO> citySearch();
	public List<Province> showProvince();
}
