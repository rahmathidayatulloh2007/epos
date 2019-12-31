package com.wo.epos.module.inv.item.dao;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.module.inv.item.model.Item;

public interface ItemBomDAO extends GenericDAO<Item, Long> {
	public Long getNextId();
}
