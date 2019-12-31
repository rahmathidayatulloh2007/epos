package com.wo.epos.module.inv.itemConvert.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemConvert.model.ItemConvertDtl;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertDtlVO;

@ManagedBean(name = "itemConvertDtlDAO")
@ViewScoped
public class ItemConvertDtlDAOImpl extends
		GenericDAOHibernate<ItemConvertDtl, Long> implements ItemConvertDtlDAO,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -864409590882312063L;

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
	public List<ItemConvertDtlVO> getListItemConvertDtlVO(Long convertId) {
		Criteria criteria = getSession().createCriteria(ItemConvertDtl.class);
		criteria.createAlias("itemConvert", "itemConvert");

		criteria.add(Restrictions.eq("itemConvert.convertId", convertId));

		List<ItemConvertDtlVO> voList = new ArrayList<ItemConvertDtlVO>();
		for (int i = 0; i < criteria.list().size(); i++) {
			ItemConvertDtl obj = (ItemConvertDtl) criteria.list().get(i);

			ItemConvertDtlVO vo = new ItemConvertDtlVO();

			vo.setConvertDtlId(obj.getConvertDtlId());
			if (obj.getItemConvert() != null) {
				vo.setConvertId(obj.getItemConvert().getConvertId());
			}
			if (obj.getItem() != null) {
				vo.setItemId(obj.getItem().getItemId());
				vo.setItemCode(obj.getItem().getItemCode());
				vo.setItemName(obj.getItem().getItemName());
			}
			if(obj.getItem() != null && obj.getItem().getUm() != null) {
				vo.setUmId( obj.getItem().getUm().getUmId());
				vo.setUmName( obj.getItem().getUm().getUmName());
			}
			vo.setItemQty(obj.getItemQty());
			vo.setStockQty(obj.getStockQty());
			vo.setRemainQty(obj.getRemainQty());

			voList.add(vo);
		}

		return voList;
	}

}