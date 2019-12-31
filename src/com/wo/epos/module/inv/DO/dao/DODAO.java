package com.wo.epos.module.inv.DO.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.DO.model.DO;
import com.wo.epos.module.inv.DO.vo.DOVO;


public interface DODAO extends GenericDAO<DO, Long>, RetrieverDataPage<DOVO> {

	public DO getLastDoId();
	@SuppressWarnings("rawtypes")
	public List<DOVO> getDOList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder);
	public List<DOVO> findByOutlet(Long outletId);
	public DO findByDoNo(String doNumber);
	public List<DOVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId);
	public java.sql.Connection getConnection();
	
}