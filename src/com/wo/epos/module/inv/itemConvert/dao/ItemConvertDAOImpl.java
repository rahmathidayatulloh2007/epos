package com.wo.epos.module.inv.itemConvert.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemConvert.model.ItemConvert;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertVO;

@ManagedBean(name = "itemConvertDAO")
@ViewScoped
public class ItemConvertDAOImpl extends GenericDAOHibernate<ItemConvert, Long>
		implements ItemConvertDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2722627961282563654L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemConvertVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		Criteria criteria = getSession().createCriteria(ItemConvert.class);
		decorateCriteria(criteria, searchCriteria);

		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		criteria.addOrder(Order.asc("convertNo"));

		List<ItemConvertVO> objList = new ArrayList<ItemConvertVO>();
		for (int i = 0; i < criteria.list().size(); i++) {
			ItemConvert obj = (ItemConvert) criteria.list().get(i);
			ItemConvertVO vo = new ItemConvertVO();

			vo.setConvertId(obj.getConvertId());
			if (obj.getCompany() != null) {
				vo.setCompanyId(obj.getCompany().getCompanyId());
				vo.setCompanyCode(obj.getCompany().getCompanyCode());
				vo.setCompanyName(obj.getCompany().getCompanyName());
			}
			// if (obj.getOutlet() != null) {
			// vo.setOutletId(obj.getOutlet().getOutletId());
			// vo.setOutletCode(obj.getOutlet().getOutletCode());
			// vo.setOutletName(obj.getOutlet().getOutletName());
			// }
			vo.setConvertNo(obj.getConvertNo());
			vo.setConvertDate(obj.getConvertDate());
			if (obj.getItem() != null) {
				vo.setItemId(obj.getItem().getItemId());
				vo.setItemCode(obj.getItem().getItemCode());
				vo.setItemName(obj.getItem().getItemName());
			}
			vo.setItemQty(obj.getItemQty());
			vo.setConvertDesc(obj.getConvertDesc());
			vo.setActiveFlag(obj.getActiveFlag());

			objList.add(vo);
		}

		return objList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(ItemConvert.class);
		decorateCriteria(criteria, searchCriteria);

		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}

	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit,
			List<? extends SearchObject> searchCriteria) {
		crit.createAlias("company", "company");
		// crit.createAlias("outlet", "outlet",
		// CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("item", "item");

		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
					String sSplit = searchVal.getSearchValueAsString().trim();
					crit.add(Restrictions.or(Restrictions.ilike(
							"company.companyName", sSplit, MatchMode.ANYWHERE),
							Restrictions.or(Restrictions.ilike("item.itemName",
									sSplit, MatchMode.ANYWHERE), Restrictions
									.ilike("convertNo", sSplit,
											MatchMode.ANYWHERE))));
				}

				if (searchVal.getSearchColumn().compareTo("company") == 0) {
					crit.add(Restrictions.eq("company.companyId",
							searchVal.getSearchValue()));
				}

				if (searchVal.getSearchColumn().compareTo("convertNo") == 0) {
					crit.add(Restrictions.ilike("convertNo", searchVal
							.getSearchValueAsString().trim(),
							MatchMode.ANYWHERE));
				}
				if (searchVal.getSearchColumn().compareTo("itemName") == 0) {
					crit.add(Restrictions.ilike("item.itemName", searchVal
							.getSearchValueAsString().trim(),
							MatchMode.ANYWHERE));
				}
				if (searchVal.getSearchColumn().compareTo("startDate") == 0) {
					crit.add(Restrictions.ge("convertDate",
							searchVal.getSearchValue()));
				}
				if (searchVal.getSearchColumn().compareTo("endDate") == 0) {
					crit.add(Restrictions.le("convertDate",
							searchVal.getSearchValue()));
				}
			}
		}
	}

	@Override
	public String getConvertNoMax(String yearMonth) {
		String convertNo = null;

		String sql = "SELECT MAX(CONVERT_NO) FROM POS_ITEM_CONVERT WHERE CONVERT_NO LIKE '%"
				+ yearMonth + "%' ";
		Query query = getSession().createSQLQuery(sql);

		if (query.list().size() > 0) {
			if (query.uniqueResult() != null
					&& !query.uniqueResult().toString().equals("")) {
				convertNo = query.uniqueResult().toString();
			}
		}

		return convertNo;
	}

	@Override
	public ItemConvert findByConvertNo(String convertNo) {
		Criteria criteria = getSession().createCriteria(ItemConvert.class);
		criteria.add(Restrictions.eq("convertNo", convertNo.trim()));

		return (ItemConvert) criteria.uniqueResult();
	}

}