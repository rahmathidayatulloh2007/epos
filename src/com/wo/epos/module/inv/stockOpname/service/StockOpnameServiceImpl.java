package com.wo.epos.module.inv.stockOpname.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.inv.stockOpname.dao.StockOpnameDAO;
import com.wo.epos.module.inv.stockOpname.model.StockOpname;
import com.wo.epos.module.inv.stockOpname.model.StockOpnameDtl;
import com.wo.epos.module.inv.stockOpname.vo.StockOpnameVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outletEmp.dao.OutletEmpDAO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;

@ManagedBean(name = "stockOpnameService")
@ViewScoped
public class StockOpnameServiceImpl implements StockOpnameService, Serializable {

	private static final long serialVersionUID = -8228066618717168955L;

	@ManagedProperty(value = "#{stockOpnameDAO}")
	private StockOpnameDAO stockOpnameDAO;

	@ManagedProperty(value = "#{companyDAO}")
	private CompanyDAO companyDAO;

	@ManagedProperty(value = "#{parameterDAO}")
	private ParameterDAO parameterDAO;

	@ManagedProperty(value = "#{outletDAO}")
	private OutletDAO outletDAO;

	@ManagedProperty(value = "#{outletEmpDAO}")
	private OutletEmpDAO outletEmpDAO;

	@ManagedProperty(value = "#{itemStockDAO}")
	private ItemStockDAO itemStockDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public List<StockOpnameVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {

		return stockOpnameDAO.searchData(searchCriteria, first, pageSize,
				sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {

		return stockOpnameDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(StockOpnameVO stockOpnameVO, String user) {

		try {
			StockOpname stockOpname = new StockOpname();

			stockOpname.setPeriod(stockOpnameVO.getPeriodBulan().concat(
					stockOpnameVO.getPeriodTahun()));
			stockOpname.setOpnameDate(stockOpnameVO.getOpnameDate());
			stockOpname.setOpnameNo(stockOpnameDAO.runningNumberStockOpname(
					CommonConstants.STO_NUMBERFORMAT,
					stockOpnameVO.getCompanyId()));

			// Get company
			Company company = companyDAO.findById(stockOpnameVO.getCompanyId());
			stockOpname.setCompany(company);

			// Get Outlet
			Outlet outlet = outletDAO.findById(stockOpnameVO.getOutletId());
			stockOpname.setOutlet(outlet);

			stockOpname.setNotes(stockOpnameVO.getNotes());
			stockOpname.setStatus(CommonConstants.STOCKOPNAME_NEW);

			stockOpname.setStockOpnameDtl(createDetailStockOpname(
					stockOpnameVO.getCompanyId(), stockOpnameVO.getOutletId(),
					stockOpname, user));
			stockOpname.setActiveFlag(CommonConstants.Y);
			stockOpname.setCreateBy(user);
			stockOpname.setCreateOn(new Timestamp(System.currentTimeMillis()));

			stockOpnameDAO.save(stockOpname);

			stockOpnameDAO.flush();
		} catch (Exception e) {
			e.printStackTrace();
			stockOpnameDAO.rollback();
		}

	}

	private List<StockOpnameDtl> createDetailStockOpname(Long companyId,
			Long outletId, StockOpname stockOpname, String user) {

		List<StockOpnameDtl> detailList = new ArrayList<StockOpnameDtl>();
		List<ItemStock> itemList = itemStockDAO
				.searchAllItemStockByCompanyIdOrOutleId(companyId, outletId);

		for (ItemStock vo : itemList) {
			StockOpnameDtl newEntity = new StockOpnameDtl();

			newEntity.setItem(vo.getItem());
			newEntity.setStockOpname(stockOpname);
			newEntity.setStockQty(vo.getStockQty());
			newEntity.setOpnameQty(null);
			newEntity.setActiveFlag(CommonConstants.Y);
			newEntity.setCreateBy(user);
			newEntity.setCreateOn(new Timestamp(System.currentTimeMillis()));

			detailList.add(newEntity);
		}

		return detailList;
	}

	@Override
	public void update(StockOpnameVO stockOpnameVO, String user) {

		try {

			StockOpname stockOpname = stockOpnameDAO.findById(stockOpnameVO
					.getOpnameId());

			stockOpname.setOpnameDate(stockOpnameVO.getOpnameDate());
			stockOpname.setPeriod(stockOpnameVO.getPeriodBulan().concat(
					stockOpnameVO.getPeriodTahun()));
			stockOpname.setNotes(stockOpnameVO.getNotes());

			for (StockOpnameDtl detailFromDB : stockOpname.getStockOpnameDtl()) {
				for (StockOpnameDtl detailFromVO : stockOpnameVO
						.getStockOpnameDtl()) {
					if (detailFromDB.getOpnameDtlId().equals(
							detailFromVO.getOpnameDtlId())) {
						detailFromDB.setOpnameQty(detailFromVO.getOpnameQty());
					}
				}
			}

			stockOpname.setUpdateBy(user);
			stockOpname.setUpdateOn(new Timestamp(System.currentTimeMillis()));

			stockOpnameDAO.update(stockOpname);
			stockOpnameDAO.flush();
		} catch (Exception e) {
			stockOpnameDAO.rollback();
			System.out.println(e);
		}

	}

	@Override
	public void delete(Long opnameId) {
		try {
			StockOpname stockOpname = stockOpnameDAO.findById(opnameId);
			stockOpnameDAO.delete(stockOpname);
			stockOpnameDAO.flush();
		} catch (Exception e) {
			stockOpnameDAO.rollback();
			System.out.println(e);
		}
	}

	@Override
	public Boolean closeOpname(StockOpnameVO stockOpnameVO, String user) {
		Boolean error = false;
		try {

			if (stockOpnameVO.getStockOpnameDtl().size() > 0) {
				for (StockOpnameDtl temp : stockOpnameVO.getStockOpnameDtl()) {
					if (temp.getOpnameQty() == null) {
						error = true;
						break;
					}
				}
			}

			if (!error) {
				StockOpname stockOpname = stockOpnameDAO.findById(stockOpnameVO
						.getOpnameId());
				stockOpname.setStatus(CommonConstants.STOCKOPNAME_CLOSED);
				stockOpname.setUpdateBy(user);
				stockOpname.setUpdateOn(new Timestamp(System
						.currentTimeMillis()));

				stockOpnameDAO.update(stockOpname);
				stockOpnameDAO.flush();
				error = false;
			}

		} catch (Exception e) {
			stockOpnameDAO.rollback();
			System.out.println(e);
		}

		return error;
	}

	@Override
	public StockOpname findById(Long vehicleId) {

		return null;
	}

	public StockOpnameDAO getStockOpnameDAO() {
		return stockOpnameDAO;
	}

	public void setStockOpnameDAO(StockOpnameDAO stockOpnameDAO) {
		this.stockOpnameDAO = stockOpnameDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
	}

	public OutletEmpDAO getOutletEmpDAO() {
		return outletEmpDAO;
	}

	public void setOutletEmpDAO(OutletEmpDAO outletEmpDAO) {
		this.outletEmpDAO = outletEmpDAO;
	}

	public ItemStockDAO getItemStockDAO() {
		return itemStockDAO;
	}

	public void setItemStockDAO(ItemStockDAO itemStockDAO) {
		this.itemStockDAO = itemStockDAO;
	}

}
