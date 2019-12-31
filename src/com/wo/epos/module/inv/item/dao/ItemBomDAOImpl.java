package com.wo.epos.module.inv.item.dao;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;

@ManagedBean(name = "itemBomDAO")
@ViewScoped
public class ItemBomDAOImpl extends GenericDAOHibernate<Item, Long> implements
		ItemBomDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2941861197599253628L;

	@Override
	public Long getNextId() {
		Criteria criteria = getSession().createCriteria(SupplierItem.class)
				.setProjection(Projections.max("supplierItemId"));

		Long maxSupplierItemId = (Long) criteria.uniqueResult();

		if (maxSupplierItemId == null) {
			maxSupplierItemId = 1L;
		} else {
			maxSupplierItemId = maxSupplierItemId + 1;
		}

		return maxSupplierItemId;

	}
}
