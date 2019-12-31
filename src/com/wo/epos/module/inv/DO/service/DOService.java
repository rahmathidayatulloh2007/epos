package com.wo.epos.module.inv.DO.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.DO.model.DO;
import com.wo.epos.module.inv.DO.vo.DOVO;

public interface DOService extends RetrieverDataPage<DOVO> {

	public void save(DOVO DOVO, String user);
	public void update(DOVO DOVO, String user,DOVO DOVOOri);
	public void delete(Long DOId, String user);
	public DO findById(Long DOId);
	public DO findByDoNo(String doNumber);
	public List<DOVO> findByOutlet(Long outletId);
	public List<DOVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId);
	
	public List<DOVO> getDOList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder);
	public DO getLastDoId();
	public java.sql.Connection getConnection();
	public String getDoNumber();
}	
	