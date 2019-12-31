package com.wo.epos.module.uam.outlet.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.vo.OutletVO;

public interface OutletService extends RetrieverDataPage<OutletVO>{

	public void save(OutletVO outletVO, String user);
	public void update(OutletVO outletVO, String user);
	public void delete(Long outletId);
	public Outlet findById(Long outletId);
	public Outlet findByOutletCode(String outletCode);
	
	public List<OutletVO> searchOutletList();
	public List<CityVO> searchCityAll();
	public List<CompanyVO> searchCompanyList();	
	public List<Outlet> findOutletByCompany(Long companyId);
	public List<Outlet> searchDataOutletByCompany(Long companyId, Long outletId);
}
