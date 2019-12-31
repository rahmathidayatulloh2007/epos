package com.wo.epos.module.inv.itemConvert.service;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.itemConvert.model.ItemConvert;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertVO;

public interface ItemConvertService extends RetrieverDataPage<ItemConvertVO> {

	public void save(ItemConvertVO itemConvertVO, String user);

	public void update(ItemConvertVO itemConvertVO, String user);

	public void delete(Long convertId);

	public ItemConvert findById(Long convertId);

	public ItemConvert findByConvertNo(String convertNo);

	public String getConvertNoMax(String yearMonth);
}
