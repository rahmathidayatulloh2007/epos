package com.wo.epos.module.master.province.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.master.province.vo.ProvinceVO;

public interface ProvinceService extends RetrieverDataPage<ProvinceVO> {

	public void save(ProvinceVO provinceVo, String user);
	public void update(ProvinceVO provinceVo, String user);
	public void delete(Long provinceId);
	public Province findById(Long provinceId);
	
	public List<ProvinceVO> provinceSearch();
	
	
}
