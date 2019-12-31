package com.wo.epos.module.inv.transferItem.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.transferItem.model.TransferItem;
import com.wo.epos.module.inv.transferItem.vo.TransferItemVO;

public interface TransferItemService extends RetrieverDataPage<TransferItemVO> {

	public void save(TransferItemVO transferItemVO, String user);
	public void update(TransferItemVO transferItemVO, String user,TransferItemVO transferItemVOOri);
	public void delete(Long transferItemId, String user);
	public TransferItem findById(Long transferItemId);
	public TransferItem findByDoNo(String doNumber);
	public List<TransferItemVO> findByOutlet(Long outletId);
	public List<TransferItemVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId);
	
	public List<TransferItemVO> getTransferItemList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder);
	public TransferItem getLastDoId();
	public java.sql.Connection getConnection();
	
	
}	
	