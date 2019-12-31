package com.wo.epos.module.inv.stockOpname.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.stockOpname.model.StockOpname;
import com.wo.epos.module.inv.stockOpname.vo.StockOpnameVO;
import com.wo.epos.module.master.systemProperty.dao.SystemPropertyDAO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;

@ManagedBean(name = "stockOpnameDAO")
@ViewScoped
public class StockOpnameDAOImpl extends GenericDAOHibernate<StockOpname, Long>
		implements StockOpnameDAO, Serializable {

	private static final long serialVersionUID = 3610742664929335358L;

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
	public List<StockOpnameVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {

		Criteria criteria = getSession().createCriteria(StockOpname.class);
		decorateCriteria(criteria, searchCriteria);

		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<StockOpnameVO> voList = new ArrayList<StockOpnameVO>();
		for (int i = 0; i < criteria.list().size(); i++) {
			StockOpname stockOpname = (StockOpname) criteria.list().get(i);
			StockOpnameVO vo = new StockOpnameVO();
			vo.setOpnameId(stockOpname.getOpnameId());
			vo.setCompany(stockOpname.getCompany());
			vo.setCompanyId(stockOpname.getCompany().getCompanyId());
			vo.setOutlet(stockOpname.getOutlet());
			vo.setOutletId(stockOpname.getOutlet().getOutletId());
			vo.setOpnameNo(stockOpname.getOpnameNo());
			vo.setOpnameDate(stockOpname.getOpnameDate());
			vo.setPeriod(stockOpname.getPeriod());

			String bulanInt = stockOpname.getPeriod().substring(0, 2);
			String tahunInt = stockOpname.getPeriod().substring(2, 6);

			vo.setPeriodBulan(bulanInt);
			vo.setPeriodTahun(tahunInt);

			vo.setNotes(stockOpname.getNotes());
			vo.setStatus(stockOpname.getStatus());
			vo.setParamStatus(stockOpname.getParamStatus());

			if (stockOpname.getStockOpnameDtl().size() > 0)
				vo.setStockOpnameDtl(stockOpname.getStockOpnameDtl());

			voList.add(vo);
		}

		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {

		Criteria criteria = getSession().createCriteria(StockOpname.class);
		decorateCriteria(criteria, searchCriteria);

		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}

	@Override
	public String runningNumberStockOpname(String systemPropertyName,
			Long companyId) {
		String runningNumberSo = null;
		SystemProperty systemProperty = new SystemProperty();
		systemProperty = systemPropertyDAO.searchSystemPropertyNameAndCompany(
				CommonConstants.STO_NUMBERFORMAT, companyId);

		if (systemProperty != null) {
			if (systemProperty.getSystemPropertyValue().equals(
					CommonConstants.STONUMBER_YEARLY)) {
				SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");
				String year = sdfYear.format(new Date());
				String soNumber = searchStockOpnameMax(year, null, null);

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
			} else if (systemProperty.getSystemPropertyValue().equals(
					CommonConstants.STONUMBER_MONTHLY)) {
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMM");
				String yearMonth = sdfYear.format(new Date());
				String soNumber = searchStockOpnameMax(null, yearMonth, null);

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
			} else if (systemProperty.getSystemPropertyValue().equals(
					CommonConstants.STONUMBER_DAILY)) {
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMMdd");
				String yearMonthDate = sdfYear.format(new Date());
				String soNumber = searchStockOpnameMax(null, null,
						yearMonthDate);

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
	public String searchStockOpnameMax(String year, String yearMonth,
			String yearMonthDate) {
		String numberSo = null;

		String sql = "SELECT MAX(OPNAME_NO) FROM POS_STOCK_OPNAME";

		if (year != null && !year.equals("")) {
			sql = sql + " WHERE SUBSTR(OPNAME_NO,1,5)  = '" + year + "-' ";
		} else if (yearMonth != null && !yearMonth.equals("")) {
			sql = sql + " WHERE SUBSTR(OPNAME_NO,1,7) = '" + yearMonth + "-' ";
		} else if (yearMonthDate != null && !yearMonthDate.equals("")) {
			sql = sql + " WHERE SUBSTR(OPNAME_NO,1,9) = '" + yearMonthDate
					+ "-' ";
		}

		Query query = getSession().createSQLQuery(sql);

		if (query.list().size() > 0) {
			if (query.uniqueResult() != null
					&& !query.uniqueResult().toString().equals("")) {
				numberSo = query.uniqueResult().toString();
			}
		}

		return numberSo;
	}

	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit,
			List<? extends SearchObject> searchCriteria) {
		crit.createAlias("outlet", "outlet");
		crit.createAlias("company", "company");
		crit.createAlias("paramStatus", "paramStatus");

		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
					String sSplit = searchVal.getSearchValueAsString().trim();

					crit.add(Restrictions.or(Restrictions.ilike("opnameNo",
							sSplit, MatchMode.ANYWHERE), Restrictions.or(
							Restrictions.ilike("notes", sSplit,
									MatchMode.ANYWHERE),
									Restrictions.ilike(
											"paramStatus.parameterDtlName",
											sSplit))));

				}

				if (searchVal.getSearchColumn().compareTo("opnameNo") == 0) {
					crit.add(Restrictions.ilike("opnameNo", searchVal
							.getSearchValueAsString().trim(),
							MatchMode.ANYWHERE));
				}
				if (searchVal.getSearchColumn().compareTo("opnameDateAwal") == 0) {
					crit.add(Restrictions.ge("opnameDate", searchVal
							.getSearchValue()));
				}
				if (searchVal.getSearchColumn().compareTo("opnameDateAkhir") == 0) {
					crit.add(Restrictions.le("opnameDate", searchVal
							.getSearchValue()));
				}
				if (searchVal.getSearchColumn().compareTo("periodTahun") == 0) {
					crit.add(Restrictions.like("period", "%" + searchVal
							.getSearchValueAsString().trim()));
				}
				if (searchVal.getSearchColumn().compareTo("periodBulan") == 0) {
					crit.add(Restrictions.like("period", searchVal
							.getSearchValueAsString().trim() + "%"));
				}
				if (searchVal.getSearchColumn().compareTo("deskripsi") == 0) {
					crit.add(Restrictions.ilike("notes", searchVal
							.getSearchValueAsString().trim(),MatchMode.ANYWHERE));
				}
				if (searchVal.getSearchColumn().compareTo("status") == 0) {
					crit.add(Restrictions.eq("status",
							searchVal.getSearchValue()));
				}
				if (searchVal.getSearchColumn().compareTo("outletId") == 0) {
					crit.add(Restrictions.eq("outlet.outletId",
							searchVal.getSearchValue()));
				}
				if (searchVal.getSearchColumn().compareTo("companyId") == 0) {
					crit.add(Restrictions.eq("outlet.company.companyId",
							searchVal.getSearchValue()));
				}

			}
		}
	}

}
