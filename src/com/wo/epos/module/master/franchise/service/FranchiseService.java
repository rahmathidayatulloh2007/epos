package com.wo.epos.module.master.franchise.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.franchise.model.Franchise;
import com.wo.epos.module.master.franchise.vo.FranchiseVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

public interface FranchiseService extends RetrieverDataPage<FranchiseVO>{

	public void save(FranchiseVO franchiseVO, String user);
	public void update(FranchiseVO franchiseVO, String user);
	public void delete(Long franchiseId);
	public Franchise findById(Long franchiseId);
	public Franchise findByNo(String franchiseNo);
		
	public List<CityVO> searchCityAll();
	public List<ParameterDtl> parameterDtlList(String parameterCode);
	
	public List<FranchiseVO> searchFranchiseList();
}
