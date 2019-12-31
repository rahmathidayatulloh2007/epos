package com.wo.epos.module.inv.itemConvert.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.item.dao.ItemDAO;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.itemConvert.dao.ItemConvertDAO;
import com.wo.epos.module.inv.itemConvert.dao.ItemConvertDtlDAO;
import com.wo.epos.module.inv.itemConvert.model.ItemConvert;
import com.wo.epos.module.inv.itemConvert.model.ItemConvertDtl;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertDtlVO;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertVO;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;

@ManagedBean(name = "itemConvertService")
@ViewScoped
public class ItemConvertServiceImpl implements ItemConvertService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 273785126147821124L;

	@ManagedProperty(value = "#{companyDAO}")
	private CompanyDAO companyDAO;

	@ManagedProperty(value = "#{itemDAO}")
	private ItemDAO itemDAO;

	@ManagedProperty(value = "#{itemStockDAO}")
	private ItemStockDAO itemStockDAO;

	@ManagedProperty(value = "#{itemConvertDAO}")
	private ItemConvertDAO itemConvertDAO;

	@ManagedProperty(value = "#{itemConvertDtlDAO}")
	private ItemConvertDtlDAO itemConvertDtlDAO;

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public ItemConvertDAO getItemConvertDAO() {
		return itemConvertDAO;
	}

	public void setItemConvertDAO(ItemConvertDAO itemConvertDAO) {
		this.itemConvertDAO = itemConvertDAO;
	}

	public ItemConvertDtlDAO getItemConvertDtlDAO() {
		return itemConvertDtlDAO;
	}

	public void setItemConvertDtlDAO(ItemConvertDtlDAO itemConvertDtlDAO) {
		this.itemConvertDtlDAO = itemConvertDtlDAO;
	}

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public ItemStockDAO getItemStockDAO() {
		return itemStockDAO;
	}

	public void setItemStockDAO(ItemStockDAO itemStockDAO) {
		this.itemStockDAO = itemStockDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemConvertVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return itemConvertDAO.searchData(searchCriteria, first, pageSize,
				sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return itemConvertDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(ItemConvertVO vo, String user) {
		try {
			ItemConvert obj = new ItemConvert();

			Company company = companyDAO.findById(vo.getCompanyId());
			obj.setCompany(company);

			obj.setConvertNo(this.generateConvertNo());
			obj.setConvertDate(new Date());

			Item item = itemDAO.findById(vo.getItemId());
			obj.setItem(item);

			obj.setItemQty(vo.getItemQty());
			obj.setConvertDesc(vo.getConvertDesc());

			obj.setActiveFlag(CommonConstants.Y);
			obj.setCreateBy(user);
			obj.setCreateOn(new Timestamp(System.currentTimeMillis()));

			// add stock composite [start]
			ItemStock itemStock = itemStockDAO
					.getItemStockByCompanyIdOutletIdAndItemId(obj.getCompany()
							.getCompanyId(), null, obj.getItem().getItemId());
			if (itemStock != null) {
				itemStock.setStockQty(itemStock.getStockQty()
						+ obj.getItemQty());
			} else {
				itemStock = new ItemStock();

				itemStock.setCompany(obj.getCompany());
				itemStock.setCompanyId(obj.getCompany().getCompanyId());
				itemStock.setOutlet(null);
				itemStock.setOutletId(null);
				itemStock.setItem(obj.getItem());
				itemStock.setItemId(obj.getItem().getItemId());

				itemStock.setStockQty(obj.getItemQty());
				itemStock.setOutgoingQty((double) 0);
				itemStock.setIncomingQty((double) 0);
				itemStock.setReorderQty((double) 0);
				itemStock.setAveragePrice((double) 0);

				itemStock.setActiveFlag(CommonConstants.Y);
				itemStock.setCreateBy(user);
				itemStock
						.setCreateOn(new Timestamp(System.currentTimeMillis()));

				itemStockDAO.save(itemStock);
			}
			// add stock composite [end]

			if (vo.getListItemConvertDtlVO() != null) {
				List<ItemConvertDtl> listItemConvertDtl = new ArrayList<ItemConvertDtl>();
				for (int i = 0; i < vo.getListItemConvertDtlVO().size(); i++) {
					ItemConvertDtlVO voDtl = (ItemConvertDtlVO) vo
							.getListItemConvertDtlVO().get(i);

					ItemConvertDtl objDtl = new ItemConvertDtl();

					objDtl.setItemConvert(obj);

					Item item2 = itemDAO.getById(voDtl.getItemId());
					objDtl.setItem(item2);

					objDtl.setItemQty(voDtl.getItemQty());
					objDtl.setStockQty(voDtl.getStockQty());
					objDtl.setRemainQty(voDtl.getRemainQty());

					objDtl.setActiveFlag(CommonConstants.Y);
					objDtl.setCreateBy(user);
					objDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));

					listItemConvertDtl.add(objDtl);

					// subtrack stock composition [start]
					ItemStock itemStock2 = itemStockDAO
							.getItemStockByCompanyIdOutletIdAndItemId(obj
									.getCompany().getCompanyId(), null, objDtl
									.getItem().getItemId());
					itemStock2.setStockQty(itemStock2.getStockQty()
							- objDtl.getItemQty());
					// subtrack stock composition [end]
				}

				obj.setListItemConvertDtl(listItemConvertDtl);
			}

			itemConvertDAO.save(obj);

			itemConvertDAO.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			itemConvertDAO.rollback();
		}
	}

	@Override
	public void update(ItemConvertVO vo, String user) {
		try {
			ItemConvert obj = new ItemConvert();

			Company company = new Company();
			company.setCompanyId(vo.getCompanyId());
			obj.setCompany(company);

			obj.setConvertNo(this.generateConvertNo());
			obj.setConvertDate(vo.getConvertDate());

			Item item = new Item();
			item.setItemId(vo.getItemId());
			obj.setItem(item);

			obj.setItemQty(vo.getItemQty());
			obj.setConvertDesc(vo.getConvertDesc());

			obj.setActiveFlag(CommonConstants.Y);
			obj.setUpdateBy(user);
			obj.setUpdateOn(new Timestamp(System.currentTimeMillis()));

			List<ItemConvertDtl> objDtlListDb = obj.getListItemConvertDtl();
			ItemConvertDtl objDtl = null;
			ItemConvertDtl objDtlDb = null;
			ItemConvertDtlVO voDtl = null;
			boolean exist = false;
			if (vo.getListItemConvertDtlVO() != null) {
				/* untuk insert data baru dan update data lama */
				for (int x = 0; x < vo.getListItemConvertDtlVO().size(); x++) {
					voDtl = (ItemConvertDtlVO) vo.getListItemConvertDtlVO()
							.get(x);

					exist = false;
					for (int i = 0; i < objDtlListDb.size(); i++) {
						objDtlDb = (ItemConvertDtl) objDtlListDb.get(i);
						if (objDtlDb.getItem().getItemId()
								.equals(voDtl.getItemId())) {
							exist = true;
							break;
						}
					}

					if (exist) {
						// update
						objDtlDb.setItemConvert(obj);

						Item item2 = new Item();
						item2.setItemId(voDtl.getItemId());
						objDtlDb.setItem(item2);

						objDtlDb.setItemQty(voDtl.getItemQty());
						objDtlDb.setStockQty(voDtl.getStockQty());
						objDtlDb.setRemainQty(voDtl.getRemainQty());

						objDtlDb.setActiveFlag(CommonConstants.Y);
						objDtlDb.setUpdateBy(user);
						objDtlDb.setUpdateOn(new Timestamp(System
								.currentTimeMillis()));
					} else {
						// insert
						objDtl = new ItemConvertDtl();

						objDtl.setItemConvert(obj);

						Item item2 = new Item();
						item2.setItemId(voDtl.getItemId());
						objDtl.setItem(item2);

						objDtl.setItemQty(voDtl.getItemQty());
						objDtl.setStockQty(voDtl.getStockQty());
						objDtl.setRemainQty(voDtl.getRemainQty());

						objDtl.setActiveFlag(CommonConstants.Y);
						objDtl.setCreateBy(user);
						objDtl.setCreateOn(new Timestamp(System
								.currentTimeMillis()));

						objDtlListDb.add(objDtl);
					}
				}
			}

			for (int i = 0; i < objDtlListDb.size(); i++) {
				objDtlDb = (ItemConvertDtl) objDtlListDb.get(i);

				exist = false;
				for (int x = 0; x < vo.getListItemConvertDtlVO().size(); x++) {
					voDtl = (ItemConvertDtlVO) vo.getListItemConvertDtlVO()
							.get(x);

					if (objDtlDb.getItem().getItemId() != null
							&& voDtl.getItemId() != null) {
						if (objDtlDb.getItem().getItemId()
								.equals(voDtl.getItemId())) {
							exist = true;

							break;
						}
					}
				}

				if (!exist) {
					// delete
					i--;
					objDtlListDb.remove(objDtlDb);
				}
			}

			itemConvertDAO.update(obj);
			itemConvertDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			itemConvertDAO.rollback();
		}
	}

	@Override
	public void delete(Long convertId) {
		ItemConvert obj = new ItemConvert();
		obj = itemConvertDAO.findById(convertId);
		ItemConvertDtl itemConvertDtl = null;
		for (int i = 0; i < obj.getListItemConvertDtl().size(); i++) {
			itemConvertDtl = new ItemConvertDtl();
			itemConvertDtl = itemConvertDtlDAO.findById(obj
					.getListItemConvertDtl().get(i).getConvertDtlId());
			itemConvertDtlDAO.delete(itemConvertDtl);
		}

		itemConvertDAO.delete(obj);
		itemConvertDAO.flush();
	}

	@Override
	public ItemConvert findById(Long convertId) {
		return itemConvertDAO.findById(convertId);
	}

	@Override
	public ItemConvert findByConvertNo(String convertNo) {
		return itemConvertDAO.findByConvertNo(convertNo);
	}

	private String generateConvertNo() {
		String convertNo = null;
		SimpleDateFormat sdfYearMont = new SimpleDateFormat("YYYYMM");

		String dateYearMonth = sdfYearMont.format(new Date());
		String convertNoLast = this.getConvertNoMax(dateYearMonth);

		if (convertNoLast != null && !convertNoLast.equals("")) {
			String[] split = convertNoLast.split("-");
			String number = null;
			Integer splitInt = Integer.parseInt(split[1]);

			number = String.format("%04d", new Object[] { splitInt + 1 });
			// if ((splitInt + "").length() == 4) {
			// number = "" + (splitInt + 1);
			// } else if ((splitInt + "").length() == 3) {
			// number = "0" + (splitInt + 1);
			// } else if ((splitInt + "").length() == 2) {
			// number = "00" + (splitInt + 1);
			// } else if ((splitInt + "").length() == 1) {
			// number = "000" + (splitInt + 1);
			// }

			convertNo = dateYearMonth + "-" + number;
		} else {
			convertNo = dateYearMonth + "-0001";
		}

		return convertNo;
	}

	@Override
	public String getConvertNoMax(String yearMonth) {
		return itemConvertDAO.getConvertNoMax(yearMonth);
	}
}
