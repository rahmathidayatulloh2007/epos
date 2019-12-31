package com.wo.epos.module.inv.itemConvert.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemConvert.model.ItemConvert;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertVO;

public interface ItemConvertDAO extends GenericDAO<ItemConvert, Long>,
		RetrieverDataPage<ItemConvertVO> {

	public String getConvertNoMax(String yearMonth);

	public ItemConvert findByConvertNo(String convertNo);

}