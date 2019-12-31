package com.wo.epos.module.inv.itemConvert.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemConvert.dao.ItemConvertDtlDAO;
import com.wo.epos.module.inv.itemConvert.model.ItemConvertDtl;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertDtlVO;

@ManagedBean(name = "itemConvertDtlService")
@ViewScoped
public class ItemConvertDtlServiceImpl implements ItemConvertDtlService,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5681968336220178587L;

	@ManagedProperty(value = "#{itemConvertDtlDAO}")
	private ItemConvertDtlDAO itemConvertDtlDAO;

	public ItemConvertDtlDAO getItemConvertDtlDAO() {
		return itemConvertDtlDAO;
	}

	public void setItemConvertDtlDAO(ItemConvertDtlDAO itemConvertDtlDAO) {
		this.itemConvertDtlDAO = itemConvertDtlDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemConvertDtlVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ItemConvertDtlVO voDtl, String user) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(ItemConvertDtlVO voDtl, String user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long convertDtlId) {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemConvertDtl findById(Long convertDtlId) {
		return itemConvertDtlDAO.findById(convertDtlId);
	}

	@Override
	public List<ItemConvertDtlVO> getListItemConvertDtlVO(Long convertId) {
		return itemConvertDtlDAO.getListItemConvertDtlVO(convertId);
	}

}