package com.wo.epos.module.inv.transferItem.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.transferItem.model.TransferItem;
import com.wo.epos.module.inv.transferItem.vo.TransferItemVO;


public interface TransferItemDAO extends GenericDAO<TransferItem, Long>, RetrieverDataPage<TransferItemVO> {

	public TransferItem getLastDoId();
	@SuppressWarnings("rawtypes")
	public List<TransferItemVO> getTransferItemList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder);
	public List<TransferItemVO> findByOutlet(Long outletId);
	public TransferItem findByDoNo(String doNumber);
	public List<TransferItemVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId);
	public java.sql.Connection getConnection();
	
}