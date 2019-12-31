package com.wo.epos.module.inv.itemConvert.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemConvert.model.ItemConvertDtl;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertDtlVO;

public interface ItemConvertDtlService extends
		RetrieverDataPage<ItemConvertDtlVO> {

	public void save(ItemConvertDtlVO voDtl, String user);

	public void update(ItemConvertDtlVO voDtl, String user);

	public void delete(Long itemConvertDtlId);

	public ItemConvertDtl findById(Long itemConvertDtlId);

	public List<ItemConvertDtlVO> getListItemConvertDtlVO(Long convertId);

}
