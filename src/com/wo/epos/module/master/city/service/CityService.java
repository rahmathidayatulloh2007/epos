package com.wo.epos.module.master.city.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.model.Province;

public interface CityService extends RetrieverDataPage<CityVO>{

	public void save(CityVO cityVO, String user);
	public void update(CityVO cityVO, String user);
	public void delete(Long cityId);
	public City findById(Long cityId);
	
	public List<CityVO> citySearch();
	public List<Province> showProvince();
}
