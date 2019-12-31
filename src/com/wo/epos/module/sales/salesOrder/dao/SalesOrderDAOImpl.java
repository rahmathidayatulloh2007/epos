package com.wo.epos.module.sales.salesOrder.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.systemProperty.dao.SystemPropertyDAO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderVO;

@ManagedBean(name = "salesOrderDAO")
@ViewScoped
public class SalesOrderDAOImpl extends GenericDAOHibernate<SalesOrder, Long> implements SalesOrderDAO, Serializable {

	private static final long serialVersionUID = 4616442762295183875L;

	@ManagedProperty(value = "#{systemPropertyDAO}")
	private SystemPropertyDAO systemPropertyDAO;

	public SystemPropertyDAO getSystemPropertyDAO() {
		return systemPropertyDAO;
	}

	public void setSystemPropertyDAO(SystemPropertyDAO systemPropertyDAO) {
		this.systemPropertyDAO = systemPropertyDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SalesOrderVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, boolean sortOrder) throws Exception {

		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		decorateCriteria(criteria, searchCriteria);

		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<SalesOrderVO> voList = new ArrayList<SalesOrderVO>();
		for (int i = 0; i < criteria.list().size(); i++) {
			SalesOrder salesOrder = (SalesOrder) criteria.list().get(i);
			SalesOrderVO vo = new SalesOrderVO();
			vo.setSoId(salesOrder.getSoId());
			vo.setSoNo(salesOrder.getSoNo());
			vo.setStatusName(salesOrder.getStatus().getParameterDtlName());
			vo.setCustomerName(salesOrder.getCustomer().getCustomerName());
			vo.setProductResume(salesOrder.getProductResume());
			vo.setSoDate(salesOrder.getSoDate());
			vo.setActiveFlag(salesOrder.getActiveFlag());

			voList.add(vo);
		}

		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {

		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		decorateCriteria(criteria, searchCriteria);

		Integer results = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}

	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit, List<? extends SearchObject> searchCriteria) {

		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
					String sSplit = searchVal.getSearchValueAsString().trim();
					crit.add(Restrictions.or(Restrictions.ilike("soNo", sSplit, MatchMode.ANYWHERE),
							Restrictions.ilike("productResume", sSplit, MatchMode.ANYWHERE)));
				}

			}
		}
		crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@Override
	public String searchSalesOrderMax(String year, String yearMonth, String yearMonthDate) {
		String numberSo = null;

		String sql = "SELECT MAX(SO_NO) FROM POS_SO";

		if (year != null && !year.equals("")) {
			sql = sql + " WHERE SUBSTR(SO_NO,1,5)  = '" + year + "-' ";
		} else if (yearMonth != null && !yearMonth.equals("")) {
			sql = sql + " WHERE SUBSTR(SO_NO,1,7) = '" + yearMonth + "-' ";
		} else if (yearMonthDate != null && !yearMonthDate.equals("")) {
			sql = sql + " WHERE SUBSTR(SO_NO,1,9) = '" + yearMonthDate + "-' ";
		}

		Query query = getSession().createSQLQuery(sql);

		if (query.list().size() > 0) {
			if (query.uniqueResult() != null && !query.uniqueResult().toString().equals("")) {
				numberSo = query.uniqueResult().toString();
			}
		}

		return numberSo;
	}

	@Override
	public String runningNumberSo(String systemPropertyName, Long companyId) {
		String runningNumberSo = null;
		SystemProperty systemProperty = new SystemProperty();
		systemProperty = systemPropertyDAO.searchSystemPropertyNameAndCompany(CommonConstants.SO_NUMBERFORMAT,
				companyId);

		if (systemProperty != null) {
			if (systemProperty.getSystemPropertyValue().equals(CommonConstants.SONUMBER_YEARLY)) {
				SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");
				String year = sdfYear.format(new Date());
				String soNumber = searchSalesOrderMax(year, null, null);

				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 5) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 4) {
						number = "0" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						number = "00" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "000" + (splitInt + 1);
					} else if ((splitInt + "").length() == 1) {
						number = "0000" + (splitInt + 1);
					}

					runningNumberSo = year + "-" + number;

				} else {
					runningNumberSo = year + "-00001";
				}
			} else if (systemProperty.getSystemPropertyValue().equals(CommonConstants.SONUMBER_MONTHLY)) {
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMM");
				String yearMonth = sdfYear.format(new Date());
				String soNumber = searchSalesOrderMax(null, yearMonth, null);

				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 4) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						number = "0" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "00" + (splitInt + 1);
					} else if ((splitInt + "").length() == 1) {
						number = "000" + (splitInt + 1);
					}

					runningNumberSo = yearMonth + "-" + number;

				} else {
					runningNumberSo = yearMonth + "-0001";
				}
			} else if (systemProperty.getSystemPropertyValue().equals(CommonConstants.SONUMBER_DAILY)) {
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMMdd");
				String yearMonthDate = sdfYear.format(new Date());
				String soNumber = searchSalesOrderMax(null, null, yearMonthDate);

				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 3) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "0" + (splitInt + 1);
					} else if ((splitInt + "").length() == 1) {
						number = "00" + (splitInt + 1);
					}

					runningNumberSo = yearMonthDate + "-" + number;

				} else {
					runningNumberSo = yearMonthDate + "-001";
				}
			}
		}

		return runningNumberSo;
	}

	@Override
	public SalesOrderVO searchDataSoByEquipment(Long equipmentId, String equipmentStatus) {
		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		criteria.createAlias("equipment", "equipment", CriteriaSpecification.INNER_JOIN);

		criteria.add(Restrictions.eq("equipment.equipmentId", equipmentId));
		criteria.add(Restrictions.eq("equipment.equipmentStatusCode", equipmentStatus));
		criteria.add(Restrictions.or(Restrictions.eq("statusCode", CommonConstants.SO_NEW),
				Restrictions.or(Restrictions.eq("statusCode", CommonConstants.SO_BILL),
						Restrictions.eq("statusCode", CommonConstants.SO_PROCEED))));

		SalesOrderVO salesOrderVoHeader = new SalesOrderVO();
		if (criteria.uniqueResult() != null) {
			SalesOrder salesOrder = (SalesOrder) criteria.uniqueResult();
			salesOrderVoHeader.setSoId(salesOrder.getSoId());
			salesOrderVoHeader.setOutletId(salesOrder.getOutletId());
			if (salesOrder.getOutlet() != null) {
				salesOrderVoHeader.setCompanyId(salesOrder.getOutlet().getCompany().getCompanyId());
			}
			salesOrderVoHeader.setEquipmentId(salesOrder.getEquipmentId());
			salesOrderVoHeader.setDeliveryCost(salesOrder.getDeliveryCost());
			salesOrderVoHeader.setItemAmount(salesOrder.getItemAmount());
			salesOrderVoHeader.setEquipmentName(salesOrder.getEquipment().getEquipmentName());
			salesOrderVoHeader.setSoNo(salesOrder.getSoNo());
			salesOrderVoHeader.setSoDate(salesOrder.getSoDate());
			salesOrderVoHeader.setNotes(salesOrder.getNotes());
			salesOrderVoHeader.setSoTypeCode(salesOrder.getSoTypeCode());
			salesOrderVoHeader.setSoTypeName(salesOrder.getSoType().getParameterDtlName());
			salesOrderVoHeader.setDeliveryStatusCode(salesOrder.getDeliveryStatusCode());
			salesOrderVoHeader.setTaxTypeCode(salesOrder.getTaxTypeCode());
			salesOrderVoHeader.setTaxValue(salesOrder.getTaxValue());
			salesOrderVoHeader.setProductResume(salesOrder.getProductResume());
			salesOrderVoHeader.setStatusCode(salesOrder.getStatusCode());
			salesOrderVoHeader.setSubDiskon(new Double(0));

			List<SalesOrderDtlVO> salesOrderDtlVoList = new ArrayList<SalesOrderDtlVO>();
			Double subTotal = new Double(0);
			for (int i = 0; i < salesOrder.getSalesOrderDtlList().size(); i++) {
				SalesOrderDtl dtl = (SalesOrderDtl) salesOrder.getSalesOrderDtlList().get(i);
				SalesOrderDtlVO soDtlVo = new SalesOrderDtlVO();
				soDtlVo.setSalesOrderId(dtl.getSalesOrder().getSoId());
				soDtlVo.setProductId(dtl.getProductId());
				soDtlVo.setProductName(dtl.getProduct().getProductName());
				soDtlVo.setLineNo(dtl.getLineNo());
				soDtlVo.setOrderQty(dtl.getOrderQty());
				soDtlVo.setOrderUm(dtl.getOrderUmId());
				soDtlVo.setUnitPrice(dtl.getUnitPrice());
				soDtlVo.setTotalPrice(dtl.getOrderQty().doubleValue() * dtl.getUnitPrice().doubleValue());
				soDtlVo.setDiscTypeCode(dtl.getDiscTypeCode());
				if (dtl.getDiscPercent() != null) {
					soDtlVo.setDiscPercent(dtl.getDiscPercent());
				} else {
					soDtlVo.setDiscPercent(new Double(0));
				}
				if (dtl.getDiscValue() != null) {
					soDtlVo.setDiscValue(dtl.getDiscValue());
				} else {
					soDtlVo.setDiscValue(new Double(0));
				}

				soDtlVo.setDeliveryStatusCode(dtl.getDeliveryStatusCode());
				soDtlVo.setPreparationSatusCode(dtl.getPreparationStatusCode());
				soDtlVo.setIngredientFlag(dtl.getProduct().getIngredientFlag());

				soDtlVo.setNotes(soDtlVo.getNotes());
				soDtlVo.setActiveFlag(CommonConstants.Y);

				subTotal = subTotal.doubleValue() + soDtlVo.getTotalPrice().doubleValue();

				salesOrderDtlVoList.add(soDtlVo);
			}

			salesOrderVoHeader.setSalesOrderDtlVoList(salesOrderDtlVoList);
			salesOrderVoHeader.setSubTotal(subTotal);

		} else {
			salesOrderVoHeader = null;
		}

		return salesOrderVoHeader;
	}

	@Override
	public List<SalesOrderVO> searchSalesOrderListCustomerId(Long customerId) {

		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		criteria.createAlias("customer", "customer", CriteriaSpecification.INNER_JOIN);

		if (customerId != null) {
			criteria.add(Restrictions.eq("customer.customerId", customerId));
		} else {
			criteria.add(Restrictions.isNull("customer.customerId"));
		}

		criteria.addOrder(Order.asc("soDate"));

		List<SalesOrderVO> SalesOrderVOList = new ArrayList<SalesOrderVO>();
		if (criteria != null) {
			for (int i = 0; i < criteria.list().size(); i++) {
				SalesOrder salesOrder = (SalesOrder) criteria.list().get(i);
				SalesOrderVO soVO = new SalesOrderVO();
				soVO.setSoId(salesOrder.getSoId());
				soVO.setSoNo(salesOrder.getSoNo());
				soVO.setCustomerId(salesOrder.getCustomerId());
				soVO.setCustomerName(salesOrder.getCustomer().getFullName());
				soVO.setSoDate(salesOrder.getSoDate());
				soVO.setStatusCode(salesOrder.getStatusCode());

				SalesOrderVOList.add(soVO);

			}
		}

		return SalesOrderVOList;
	}

	@Override
	public List<SalesOrderVO> searchSalesOrderListOutletId(Long outletId) {
		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		criteria.add(Restrictions.eq("outlet.outletId", outletId));

		criteria.addOrder(Order.asc("soId"));

		List<SalesOrderVO> voList = new ArrayList<SalesOrderVO>();
		for (int i = 0; i < criteria.list().size(); i++) {
			SalesOrder salesOrder = (SalesOrder) criteria.list().get(i);
			SalesOrder so = (SalesOrder) findById(salesOrder.getSoId());
			SalesOrderVO vo = new SalesOrderVO();

			vo.setSoId(so.getSoId());
			vo.setSoNo(so.getSoNo());
			vo.setCustomerId(so.getCustomerId());
			vo.setSoDate(so.getSoDate());
			vo.setStatusCode(so.getStatusCode());

			if (salesOrder.getCustomerId() != null) {
				vo.setCustomerId(so.getCustomer().getCustomerId());
				vo.setCustomerName(so.getCustomer().getFullName());
			}

			voList.add(vo);
		}

		return voList;
	}

	public List<SalesOrder> searchDataSalesOrderByOutlet(Long outletId) {
		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		criteria.createAlias("outlet", "outlet");
		criteria.add(Restrictions.eq("outlet.outletId", outletId));

		List<SalesOrder> salesOrderList = criteria.list();
		return salesOrderList;

	}

	public List<SalesOrder> findSalesOrderByCustomer(Long customerId) {
		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		criteria.createAlias("customer", "customer", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("customer.customerId", customerId));
		criteria.add(
				Restrictions.disjunction().add(Restrictions.eq("status.parameterDtlCode", CommonConstants.SO_PENDING))
						.add(Restrictions.eq("status.parameterDtlCode", CommonConstants.SO_PARTIAL)));
		criteria.addOrder(Order.asc("soNo"));

		return criteria.list();

	}

	@Override
	public SalesOrder findDtlById(Long id) {

		Criteria criteria = getSession().createCriteria(SalesOrder.class);

		criteria.add(Restrictions.eq("soId", id));

		return (SalesOrder) criteria.uniqueResult();

	}

}
