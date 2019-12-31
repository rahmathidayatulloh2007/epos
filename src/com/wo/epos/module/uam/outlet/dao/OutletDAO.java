package com.wo.epos.module.uam.outlet.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.vo.OutletVO;

public interface OutletDAO extends GenericDAO<Outlet, Long>, RetrieverDataPage<OutletVO>{

	public List<OutletVO> searchOutletList();
	public List<Outlet> findOutletByCompany(Long companyId);
	public Outlet findByOutletCode(String outletCode);
	public List<Outlet> searchDataOutletByCompany(Long companyId, Long outletId);
	
}
