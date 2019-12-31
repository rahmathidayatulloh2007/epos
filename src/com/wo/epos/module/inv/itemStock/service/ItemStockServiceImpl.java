package com.wo.epos.module.inv.itemStock.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;
import com.wo.epos.module.inv.um.dao.UmDAO;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;

@ManagedBean(name="itemStockService")
@ViewScoped
public class ItemStockServiceImpl implements ItemStockService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{itemStockDAO}")
	private ItemStockDAO itemStockDAO;

	public ItemStockDAO getItemStockDAO() {
		return itemStockDAO;
	}

	public void setItemStockDAO(ItemStockDAO itemStockDAO) {
		this.itemStockDAO = itemStockDAO;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemStockVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return itemStockDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return itemStockDAO.searchCountData(searchCriteria);
	}
	
	public List<ItemStockVO> searchItemStock(List searchCriteria){
		
		return itemStockDAO.searchItemStock(searchCriteria);
	}

	public ItemStockVO getItemStockByCompanyIdOutletIdAndItemId(Long companyId, Long outletId, Long itemId) {
		return itemStockDAO.getItemStockByCompanyIdOutletIdAndItemIdVO(companyId, outletId, itemId);
	}
		
}
