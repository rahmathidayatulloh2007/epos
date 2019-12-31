package com.wo.epos.module.inv.itemDiscount.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemDiscount.dao.ItemDiscountDAO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;

@ManagedBean(name="itemDiscountService")
@ViewScoped
public class ItemDiscountServiceImpl implements ItemDiscountService, Serializable{

	private static final long serialVersionUID = -4583797523100837915L;
	
	@ManagedProperty(value="#{itemDiscountDAO}")
	private ItemDiscountDAO itemDicountDAO;


	public ItemDiscountDAO getItemDicountDAO() {
		return itemDicountDAO;
	}

	public void setItemDicountDAO(ItemDiscountDAO itemDicountDAO) {
		this.itemDicountDAO = itemDicountDAO;
	}

	@Override
	public List<ItemDiscountVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return itemDicountDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return itemDicountDAO.searchCountData(searchCriteria);
	}

	@Override
	public List<ItemDiscountVO> searchItemList() {
		// TODO Auto-generated method stub
		return itemDicountDAO.searchItemList();
	}

	@Override
	public ItemDiscount findById(Long itemDiscountId) {
		// TODO Auto-generated method stub
		return itemDicountDAO.findById(itemDiscountId);
	}


	

}
