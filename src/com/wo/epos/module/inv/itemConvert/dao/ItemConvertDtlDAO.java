package com.wo.epos.module.inv.itemConvert.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemConvert.model.ItemConvertDtl;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertDtlVO;

public interface ItemConvertDtlDAO extends GenericDAO<ItemConvertDtl, Long>,
		RetrieverDataPage<ItemConvertDtlVO> {

	public List<ItemConvertDtlVO> getListItemConvertDtlVO(Long convertId);

}