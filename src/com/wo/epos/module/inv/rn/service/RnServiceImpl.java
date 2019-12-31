package com.wo.epos.module.inv.rn.service;

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
import com.wo.epos.module.inv.DO.dao.DODAO;
import com.wo.epos.module.inv.DO.dao.DODtlDAO;
import com.wo.epos.module.inv.DO.model.DO;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;
import com.wo.epos.module.inv.rn.dao.RnDAO;
import com.wo.epos.module.inv.rn.dao.RnDtlDAO;
import com.wo.epos.module.inv.rn.model.Rn;
import com.wo.epos.module.inv.rn.model.RnDtl;
import com.wo.epos.module.inv.rn.vo.RnDtlVO;
import com.wo.epos.module.inv.rn.vo.RnVO;
import com.wo.epos.module.purchasing.po.dao.PoDAO;
import com.wo.epos.module.purchasing.po.dao.PoDtlDAO;
import com.wo.epos.module.purchasing.po.model.Po;
import com.wo.epos.module.purchasing.po.model.PoDtl;
import com.wo.epos.module.purchasing.supplier.dao.SupplierDAO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;

@ManagedBean(name = "rnService")
@ViewScoped
public class RnServiceImpl implements RnService, Serializable {

	private static final long serialVersionUID = -8916741536094254743L;

	@ManagedProperty(value = "#{rnDAO}")
	private RnDAO rnDAO;

	@ManagedProperty(value = "#{rnDtlDAO}")
	private RnDtlDAO rnDtlDAO;

	@ManagedProperty(value = "#{outletDAO}")
	private OutletDAO outletDAO;

	@ManagedProperty(value = "#{poDAO}")
	private PoDAO poDAO;

	@ManagedProperty(value = "#{poDtlDAO}")
	private PoDtlDAO poDtlDAO;

	@ManagedProperty(value = "#{supplierDAO}")
	private SupplierDAO supplierDAO;

	@ManagedProperty(value = "#{parameterDAO}")
	private ParameterDAO parameterDAO;

	@ManagedProperty(value = "#{itemStockDAO}")
	private ItemStockDAO itemStockDAO;

	@ManagedProperty(value = "#{DODAO}")
	private DODAO DODAO;

	@ManagedProperty(value = "#{DODtlDAO}")
	private DODtlDAO DODtlDAO;

	@ManagedProperty(value = "#{companyDAO}")
	private CompanyDAO companyDAO;

	public RnDAO getRnDAO() {
		return rnDAO;
	}

	public void setRnDAO(RnDAO rnDAO) {
		this.rnDAO = rnDAO;
	}

	public RnDtlDAO getRnDtlDAO() {
		return rnDtlDAO;
	}

	public void setRnDtlDAO(RnDtlDAO rnDtlDAO) {
		this.rnDtlDAO = rnDtlDAO;
	}

	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
	}

	public PoDAO getPoDAO() {
		return poDAO;
	}

	public void setPoDAO(PoDAO poDAO) {
		this.poDAO = poDAO;
	}

	public SupplierDAO getSupplierDAO() {
		return supplierDAO;
	}

	public void setSupplierDAO(SupplierDAO supplierDAO) {
		this.supplierDAO = supplierDAO;
	}

	public PoDtlDAO getPoDtlDAO() {
		return poDtlDAO;
	}

	public void setPoDtlDAO(PoDtlDAO poDtlDAO) {
		this.poDtlDAO = poDtlDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	public ItemStockDAO getItemStockDAO() {
		return itemStockDAO;
	}

	public void setItemStockDAO(ItemStockDAO itemStockDAO) {
		this.itemStockDAO = itemStockDAO;
	}

	public DODAO getDODAO() {
		return DODAO;
	}

	public void setDODAO(DODAO DODAO) {
		this.DODAO = DODAO;
	}

	public DODtlDAO getDODtlDAO() {
		return DODtlDAO;
	}

	public void setDODtlDAO(DODtlDAO DODtlDAO) {
		this.DODtlDAO = DODtlDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RnVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize, String sortField,
			boolean sortOrder) throws Exception {
		return rnDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		return rnDAO.searchCountData(searchCriteria);
	}

	@Override
	public void savePo(RnVO rnVO, List<RnDtlVO> rnDtlVoPoList, String user) {
		try {
			Rn rn = new Rn();

			if (rnVO.getCompanyId() != null) {
				rn.setCompanyId(rnVO.getCompanyId());
				rn.setCompany(companyDAO.findById(rnVO.getCompanyId()));
			}

			if (rnVO.getOutletId() != null) {
				rn.setOutletId(rnVO.getOutletId());
				rn.setOutlet(outletDAO.findById(rnVO.getOutletId()));
			}

			rn.setRnNo(rnNumber());
			rn.setRnDate(rnVO.getRnDate());
			rn.setRnTypeCode(rnVO.getRnTypeCode());
			rn.setRnType(parameterDAO.findByDetailId(rnVO.getRnTypeCode()));
			if (rnVO.getPoId() != null) {
				rn.setPoId(rnVO.getPoId());
				rn.setPo(poDAO.findById(rnVO.getPoId()));
			}
			if (rnVO.getSupplierId() != null) {
				rn.setSupplierId(rnVO.getSupplierId());
				rn.setSupplier(supplierDAO.findById(rnVO.getSupplierId()));
			}
			rn.setSupplierDocNo(rnVO.getSupplierDocNo());
			rn.setSupplierDocDate(rnVO.getSupplierDocDate());
			rn.setNotes(rnVO.getNotes());
			rn.setActiveFlag(CommonConstants.Y);

			String statusPartial = "";
			String statusClose = "";
			String statusNew = "";
			String detailResume = "";
			String status = "";

			// List<RnDtl> rnDtlList = new ArrayList<RnDtl>();
			// for (int i = 0; i < rnDtlVoPoList.size(); i++) {
			// RnDtlVO dtlVo = (RnDtlVO) rnDtlVoPoList.get(i);
			//
			// if (dtlVo.getReceiveQty() != null) {
			// if (dtlVo.getReceiveQty() > 0) {
			// RnDtl rnDtl = new RnDtl();
			//
			// // rnDtl.setOutletId(rnVO.getOutletId());
			// rnDtl.setRn(rn);
			// rnDtl.setPoDtlId(dtlVo.getPoDtlVO().getPoDtlId());
			// rnDtl.setReceiveQty(dtlVo.getReceiveQty());
			// rnDtl.setReceiveUmId(dtlVo.getPoDtlVO().getUmId());
			// rnDtl.setLineNo(i + 1);
			// rnDtl.setActiveFlag(CommonConstants.Y);
			// rnDtl.setCreateBy(user);
			// rnDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
			//
			// String content = dtlVo.getPoDtlVO().getItemName() + ": " +
			// dtlVo.getReceiveQty() + " "
			// + dtlVo.getPoDtlVO().getUmName();
			// /*
			// * if (i == 0) { detailResume = detailResume + content;
			// * } else { detailResume = detailResume + ", " +
			// * content; }
			// */
			//
			// if (!detailResume.equals("")) {
			// detailResume = detailResume + ", " + content;
			// } else {
			// detailResume = content;
			// }
			//
			// rnDtlList.add(rnDtl);
			//
			// updateItemStockPO(rnVO, dtlVo, null, user, new Double(0),
			// CommonConstants.UPDATE); // Item
			// // Stock
			//
			// status = updatePoDetail(dtlVo, null, new Double(0), user,
			// CommonConstants.UPDATE); // Update
			// // PO
			// // Detail
			// }
			// }
			//
			// }	

			List<RnDtl> rnDtlList = new ArrayList<RnDtl>();
			for (int i = 0; i < rnDtlVoPoList.size(); i++) {
				RnDtlVO dtlVo = (RnDtlVO) rnDtlVoPoList.get(i);

				if (dtlVo.getReceiveQty() != null) {
					if (dtlVo.getReceiveQty() > 0) {
						RnDtl rnDtl = new RnDtl();

						// rnDtl.setOutletId(rnVO.getOutletId());
						rnDtl.setRn(rn);
						rnDtl.setPoDtlId(dtlVo.getPoDtlVO().getPoDtlId());
						rnDtl.setReceiveQty(dtlVo.getReceiveQty());
						rnDtl.setReceiveUmId(dtlVo.getPoDtlVO().getUmId());
						rnDtl.setLineNo(i + 1);
						rnDtl.setActiveFlag(CommonConstants.Y);
						rnDtl.setCreateBy(user);
						rnDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));

						String content = dtlVo.getPoDtlVO().getItemName() + ": " + dtlVo.getReceiveQty() + " "
								+ dtlVo.getPoDtlVO().getUmName();

						if (!detailResume.equals("")) {
							detailResume = detailResume + ", " + content;
						} else {
							detailResume = content;
						}

						rnDtlList.add(rnDtl);

						// updateItemStockPO(rnVO, dtlVo, null, user, new
						// Double(0),CommonConstants.UPDATE); // Item Stock

						status = updatePoDetail(dtlVo, null, new Double(0), user, CommonConstants.UPDATE); // Update
						// PO
						// Detail
					}
				}
			}
			
			List<RnDtlVO> rnDtlVOTempList = new ArrayList<RnDtlVO>();
			for (int i = 0; i < rnDtlVoPoList.size(); i++) {
				RnDtlVO dtlVo = (RnDtlVO) rnDtlVoPoList.get(i);

				if (rnDtlVOTempList.size() > 0) {

					Boolean empty = true;
					for (int j = 0; j < rnDtlVOTempList.size(); j++) {
						RnDtlVO voDiff = (RnDtlVO) rnDtlVOTempList.get(j);

						if (dtlVo.getPoDtlVO().getItemId().equals(voDiff.getPoDtlVO().getItemId())) {

							Double receiveQty = voDiff.getReceiveQty();

							/*
							 * rnDtlVOTempList.add(dtlVo);
							 * rnDtlVOTempList.remove(voDiff);
							 */

							voDiff.setReceiveQty(receiveQty + dtlVo.getReceiveQty());
							rnDtlVOTempList.set(j, voDiff);

							empty = false;
							break;
						}

					}
					if (empty) {
						rnDtlVOTempList.add(dtlVo);
					}

				} else {
					rnDtlVOTempList.add(dtlVo);
				}
			}
			
			for (RnDtlVO vo : rnDtlVOTempList) {
				updateItemStockPO(rnVO, vo, null, user, new Double(0), CommonConstants.UPDATE); // Item
			}

			Po po = new Po();
			po = poDAO.findById(rnVO.getPoId());

			for (int i = 0; i < po.getListPoDetail().size(); i++) {
				PoDtl poDtlStatus = (PoDtl) po.getListPoDetail().get(i);
				if (poDtlStatus.getStatusCode().equals(CommonConstants.PO_PARTIAL)) {
					statusPartial = CommonConstants.PO_PARTIAL;
				} else if (poDtlStatus.getStatusCode().equals(CommonConstants.PO_CLOSE)) {
					statusClose = CommonConstants.PO_CLOSE;
				} else if (poDtlStatus.getStatusCode().equals(CommonConstants.PO_NEW)) {
					statusNew = CommonConstants.PO_NEW;
				}
			}

			if (statusNew.equals("") && statusPartial.equals("")) {
				po.setStatusCode(CommonConstants.PO_CLOSE);
			} else if ((statusNew.equals("") && !statusPartial.equals(""))
					|| (statusNew.equals("") && !statusClose.equals(""))
					|| (!statusNew.equals("") && !statusPartial.equals(""))
					|| (!statusNew.equals("") && !statusClose.equals(""))) {
				po.setStatusCode(CommonConstants.PO_PARTIAL);
			} else {
				po.setStatusCode(CommonConstants.PO_NEW);
			}

			if (po.getStatusCode().equals(CommonConstants.PO_CLOSE)) {
				po.setCloseReason(CommonConstants.CLOSE_REASON);
			}

			po.setUpdateBy(user);
			po.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			poDAO.update(po);

			rn.setListRnDetail(rnDtlList);
			rn.setItemResume(detailResume);
			rn.setCreateBy(user);
			rn.setCreateOn(new Timestamp(System.currentTimeMillis()));
			rnDAO.save(rn);
			rnDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			rnDAO.rollback();
		}
	}

	/*
	 * List<RnDtlVO> rnDtlVODiffList = new ArrayList<RnDtlVO>(); for (int x = 0;
	 * x < rnDtlVoPoList.size(); x++) { if (rnDtlVODiffList.size() > 0) {
	 * 
	 * Boolean empty = true; for (int j = 0; j < rnDtlVODiffList.size(); j++) {
	 * RnDtlVO dtlVODiff = (RnDtlVO) rnDtlVODiffList.get(j);
	 * 
	 * if
	 * (dtlVo.getPoDtlVO().getItemId().equals(dtlVODiff.getPoDtlVO().getItemId()
	 * )) { dtlVODiff.setReceiveQty(dtlVODiff.getReceiveQty() +
	 * dtlVo.getReceiveQty()); rnDtlVODiffList.set(j, dtlVODiff); empty = false;
	 * break; }
	 * 
	 * } if (empty) { rnDtlVODiffList.add(dtlVo); }
	 * 
	 * } else { rnDtlVODiffList.add(dtlVo); }
	 * 
	 * for (RnDtlVO vo : rnDtlVODiffList) { updateItemStockPO(rnVO, vo, null,
	 * user, new Double(0), CommonConstants.UPDATE); // Item } }
	 */

	private String updatePoDetail(RnDtlVO rnDtlVo, RnDtl rnDtl, Double receiveQtyOri, String user, String check) {
		String status = "";

		PoDtl poDtl = new PoDtl();
		if (check.equals(CommonConstants.UPDATE)) {
			poDtl = poDtlDAO.findById(rnDtlVo.getPoDtlVO().getPoDtlId());
		} else if (check.equals(CommonConstants.DELETE)) {
			poDtl = poDtlDAO.findById(rnDtl.getPoDtl().getPoDtlId());
		}

		if (poDtl.getReceiveQty() != null) {
			if (check.equals(CommonConstants.UPDATE)) {
				if (receiveQtyOri > 0) {
					poDtl.setReceiveQty((poDtl.getReceiveQty().doubleValue() - receiveQtyOri.doubleValue())
							+ rnDtlVo.getReceiveQty().doubleValue());
				} else {
					poDtl.setReceiveQty(poDtl.getReceiveQty().doubleValue() + rnDtlVo.getReceiveQty().doubleValue());
				}
			} else if (check.equals(CommonConstants.DELETE)) {
				poDtl.setReceiveQty(poDtl.getReceiveQty().doubleValue() - rnDtl.getReceiveQty().doubleValue());
			}
		} else {
			poDtl.setReceiveQty(rnDtlVo.getReceiveQty().doubleValue());
		}

		if (poDtl.getReceiveQty() == 0) {
			poDtl.setStatusCode(CommonConstants.PO_NEW);
			status = CommonConstants.PO_NEW;
		} else if (poDtl.getReceiveQty() > 0 && poDtl.getReceiveQty() < poDtl.getOrderQty()) {
			poDtl.setStatusCode(CommonConstants.PO_PARTIAL);
			status = CommonConstants.PO_PARTIAL;
		} else if (poDtl.getReceiveQty().doubleValue() == poDtl.getOrderQty().doubleValue()) {
			poDtl.setStatusCode(CommonConstants.PO_CLOSE);
			status = CommonConstants.PO_CLOSE;
		}

		poDtl.setUpdateBy(user);
		poDtl.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		poDtlDAO.update(poDtl);

		return status;

	}

	private void updateItemStockPO(RnVO rnVO, RnDtlVO rnDtlVO, RnDtl rnDtl, String user, Double receiveQtyOri,
			String check) {
		ItemStockVO dataItemStockVO = new ItemStockVO();

		if (check.equals(CommonConstants.UPDATE)) {
			dataItemStockVO = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemIdVO(rnVO.getCompanyId(),
					rnVO.getOutletId(), rnDtlVO.getPoDtlVO()
							.getItemId()); /*
											 * itemStockDAO.dataItemStockItem(
											 * rnDtlVO.getPoDtlVO().getItemId())
											 * ;
											 */
		} else if (check.equals(CommonConstants.DELETE)) {
			dataItemStockVO = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemIdVO(rnVO.getCompanyId(),
					rnVO.getOutletId(), rnDtlVO.getPoDtlVO()
							.getItemId()); /*
											 * itemStockDAO.dataItemStockItem(
											 * rnDtl.getPoDtl().getItemId());
											 */
		}

		if (dataItemStockVO != null) {
			ItemStock itemStockUpdate = new ItemStock();
			itemStockUpdate = itemStockDAO.findById(dataItemStockVO.getItemStockId());
			if (check.equals("UPDATE")) {
				if (receiveQtyOri > 0) {
					itemStockUpdate
							.setStockQty((itemStockUpdate.getStockQty().doubleValue() - receiveQtyOri.doubleValue())
									+ rnDtlVO.getReceiveQty().doubleValue());
				} else {
					itemStockUpdate.setStockQty(itemStockUpdate.getStockQty() + rnDtlVO.getReceiveQty());
				}
			} else if (check.equals("DELETE")) {
				itemStockUpdate
						.setStockQty(itemStockUpdate.getStockQty().doubleValue() - rnDtl.getReceiveQty().doubleValue());
			}
			itemStockUpdate.setUpdateBy(user);
			itemStockUpdate.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			itemStockDAO.update(itemStockUpdate);
		} else {
			ItemStock itemStockInsert = new ItemStock();
			if (rnVO.getCompanyId() != null) {
				itemStockInsert.setCompanyId(rnVO.getCompanyId());
			}

			if (rnVO.getOutletId() != null) {
				itemStockInsert.setOutletId(rnVO.getOutletId());
			}

			itemStockInsert.setItemId(rnDtlVO.getPoDtlVO().getItemId());
			itemStockInsert.setStockQty(rnDtlVO.getReceiveQty().doubleValue());
			itemStockInsert.setIncomingQty(new Double(0));
			itemStockInsert.setOutgoingQty(new Double(0));
			if (rnDtlVO.getPoDtlVO().getReorderQtyItem() != null) {
				itemStockInsert.setReorderQty(rnDtlVO.getPoDtlVO().getReorderQtyItem().doubleValue());
			}
			itemStockInsert.setActiveFlag(CommonConstants.Y);
			itemStockInsert.setCreateBy(user);
			itemStockInsert.setCreateOn(new Timestamp(System.currentTimeMillis()));
			itemStockDAO.save(itemStockInsert);
		}
	}

	@Override
	public void updatePo(RnVO rnVO, List<RnDtlVO> rnDtlVOList, String user) {
		try {
			String detailResume = "";

			Rn rnHeader = null;
			rnHeader = rnDAO.findById(rnVO.getRnId());
			rnHeader.setRnDate(rnVO.getRnDate());
			rnHeader.setRnNo(rnVO.getRnNo());
			rnHeader.setSupplierDocNo(rnVO.getSupplierDocNo());
			rnHeader.setSupplierDocDate(rnVO.getSupplierDocDate());
			rnHeader.setNotes(rnVO.getNotes());
			rnHeader.setActiveFlag(CommonConstants.Y);

			List<RnDtl> childListReal = rnHeader.getListRnDetail();
			String statusPartial = "";
			String statusClose = "";
			String statusNew = "";

			if (rnDtlVOList != null) {
				RnDtl rnDetail = null;
				RnDtl rnDetailDatabase = null;
				RnDtlVO rnDtlVo = null;
				boolean exist = false;

				/* untuk insert data baru dan update data lama */
				for (int x = 0; x < rnDtlVOList.size(); x++) {
					rnDtlVo = (RnDtlVO) rnDtlVOList.get(x);

					Double receiveQtyOri = new Double(0);
					if (rnDtlVo.getReceiveQty() > 0) {
						exist = false;
						for (int i = 0; i < childListReal.size(); i++) {
							rnDetailDatabase = (RnDtl) childListReal.get(i);

							if (Integer.parseInt(rnDetailDatabase.getPoDtl().getItemId() + "") == Integer
									.parseInt(rnDtlVo.getPoDtlVO().getItemId() + "")) {
								exist = true;
								receiveQtyOri = rnDetailDatabase.getReceiveQty().doubleValue();
								break;
							}
						}

						if (exist) {

							/* update po detail */
							String status = updatePoDetail(rnDtlVo, null, receiveQtyOri, user, CommonConstants.UPDATE);
							if (status.equals(CommonConstants.PO_PARTIAL)) {
								statusPartial = status;
							} else if (status.equals(CommonConstants.PO_CLOSE)) {
								statusClose = status;
							} else if (status.equals(CommonConstants.PO_NEW)) {
								statusNew = status;
							}

							/* update item Stock */
							updateItemStockPO(rnVO, rnDtlVo, null, user, receiveQtyOri, CommonConstants.UPDATE);

							/* update Rn Detail */
							rnDetailDatabase.setReceiveQty(rnDtlVo.getReceiveQty());
							rnDetailDatabase.setUpdateBy(user);
							rnDetailDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						} else { /* insert */
							rnDetail = new RnDtl();
							// rnDetail.setOutletId(rnVO.getOutletId());
							rnDetail.setRn(rnHeader);
							rnDetail.setPoDtlId(rnDtlVo.getPoDtlVO().getPoDtlId());
							rnDetail.setReceiveQty(rnDtlVo.getReceiveQty());
							rnDetail.setReceiveUmId(rnDtlVo.getPoDtlVO().getUmId());
							rnDetail.setLineNo(x + 1);
							rnDetail.setActiveFlag(CommonConstants.Y);
							rnDetail.setCreateBy(user);
							rnDetail.setCreateOn(new Timestamp(System.currentTimeMillis()));
							childListReal.add(rnDetail);

							/* update po detail */
							String status = updatePoDetail(rnDtlVo, null, new Double(0), user, CommonConstants.UPDATE);
							if (status.equals(CommonConstants.PO_PARTIAL)) {
								statusPartial = status;
							} else if (status.equals(CommonConstants.PO_CLOSE)) {
								statusClose = status;
							} else if (status.equals(CommonConstants.PO_NEW)) {
								statusNew = status;
							}

							/* Item Stock */
							updateItemStockPO(rnVO, rnDtlVo, null, user, new Double(0), CommonConstants.UPDATE);

						}

					}
				}

				for (int i = 0; i < childListReal.size(); i++) {
					rnDetailDatabase = (RnDtl) childListReal.get(i);

					for (int x = 0; x < rnDtlVOList.size(); x++) {
						rnDtlVo = (RnDtlVO) rnDtlVOList.get(x);
						exist = false;

						if (Integer.parseInt(rnDetailDatabase.getPoDtl().getItemId() + "") == Integer
								.parseInt(rnDtlVo.getPoDtlVO().getItemId() + "")) {
							exist = true;

							String content = rnDtlVo.getPoDtlVO().getItemName() + ": " + rnDtlVo.getReceiveQty() + " "
									+ rnDtlVo.getPoDtlVO().getUmName();
							/*
							 * if (i == 0) { detailResume = detailResume +
							 * content; } else { detailResume = detailResume +
							 * ", " + content; }
							 */

							if (!detailResume.equals("")) {
								detailResume = detailResume + ", " + content;
							} else {
								detailResume = content;
							}

							break;
						}
					}

					if (!exist) { /* delete */

						/* update po detail */
						updatePoDetail(null, rnDetailDatabase, new Double(0), user, CommonConstants.UPDATE);

						/* update item stock */
						updateItemStockPO(rnVO, null, rnDetailDatabase, user, new Double(0), CommonConstants.UPDATE);

						i--;
						childListReal.remove(rnDetailDatabase);
					}
				}

			}

			Po po = new Po();
			po = poDAO.findById(rnVO.getPoId());
			for (int i = 0; i < po.getListPoDetail().size(); i++) {
				PoDtl poDtlStatus = (PoDtl) po.getListPoDetail().get(i);
				if (poDtlStatus.getStatusCode().equals(CommonConstants.PO_PARTIAL)) {
					statusPartial = CommonConstants.PO_PARTIAL;
				} else if (poDtlStatus.getStatusCode().equals(CommonConstants.PO_CLOSE)) {
					statusClose = CommonConstants.PO_CLOSE;
				} else if (poDtlStatus.getStatusCode().equals(CommonConstants.PO_NEW)) {
					statusNew = CommonConstants.PO_NEW;
				}
			}

			if (statusNew.equals("") && statusPartial.equals("")) {
				po.setStatusCode(CommonConstants.PO_CLOSE);
			} else if ((statusNew.equals("") && !statusPartial.equals(""))
					|| (statusNew.equals("") && !statusClose.equals(""))
					|| (!statusNew.equals("") && !statusPartial.equals(""))
					|| (!statusNew.equals("") && !statusClose.equals(""))) {
				po.setStatusCode(CommonConstants.PO_PARTIAL);
			} else {
				po.setStatusCode(CommonConstants.PO_NEW);
			}

			po.setUpdateBy(user);
			po.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			poDAO.update(po);

			rnHeader.setItemResume(detailResume);
			rnDAO.update(rnHeader);
			rnDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			rnDAO.rollback();
		}
	}

	@Override
	public void delete(Long rnId, String user) {
		try {
			Rn rn = new Rn();
			rn = findById(rnId);

			String statusParsial = "";
			String statusClose = "";
			String statusNew = "";
			for (int i = 0; i < rn.getListRnDetail().size(); i++) {
				RnDtl rnDtl = new RnDtl();
				rnDtl = rnDtlDAO.findById(rn.getListRnDetail().get(i).getRnDtlId());

				if (rnDtl.getRn().getRnType().equals(CommonConstants.RNTYPE_PURCHASEORDER)) {
					PoDtl poDtl = new PoDtl();
					poDtl = poDtlDAO.findById(rnDtl.getPoDtlId());
					poDtl.setUpdateBy(user);
					poDtl.setUpdateOn(new Timestamp(System.currentTimeMillis()));

					Double receiveQty = poDtl.getReceiveQty().doubleValue() - rnDtl.getReceiveQty().doubleValue();
					if (receiveQty == 0) {
						statusNew = CommonConstants.PO_NEW;
					} else if (receiveQty > 0 && receiveQty < poDtl.getOrderQty()) {
						statusParsial = CommonConstants.PO_PARTIAL;
					} else if (receiveQty == poDtl.getOrderQty()) {
						statusClose = CommonConstants.PO_CLOSE;
					}

					poDtl.setReceiveQty(receiveQty);
					poDtlDAO.update(poDtl);

					ItemStockVO dataItemStockVO = itemStockDAO
							.dataItemStockItem(rnDtl.getDODtl().getItem().getItemId());
					if (dataItemStockVO != null) {
						if (dataItemStockVO != null) {
							/* Update Item Stock */
							ItemStock itemStockUpdate = new ItemStock();
							itemStockUpdate = itemStockDAO.findById(dataItemStockVO.getItemStockId());
							itemStockUpdate.setStockQty(itemStockUpdate.getStockQty() - rnDtl.getReceiveQty());
							itemStockUpdate.setUpdateBy(user);
							itemStockUpdate.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							itemStockDAO.update(itemStockUpdate);
						}
					}

				} else if (rnDtl.getRn().getRnType().equals(CommonConstants.RNTYPE_TRANSFERITEM)) {
					ItemStockVO dataItemStockVO = itemStockDAO
							.dataItemStockItem(rnDtl.getDODtl().getItem().getItemId());
					if (dataItemStockVO != null) {
						if (Integer.parseInt(dataItemStockVO.getOutletId() + "") == Integer
								.parseInt(rn.getDO().getTransferFrom() + "")) {
							ItemStock itemStockOutletFrom = new ItemStock();
							itemStockOutletFrom = itemStockDAO.findById(dataItemStockVO.getItemStockId());
							itemStockOutletFrom.setOutgoingQty(itemStockOutletFrom.getOutgoingQty().doubleValue()
									+ rnDtl.getReceiveQty().doubleValue());
							itemStockOutletFrom.setStockQty(itemStockOutletFrom.getStockQty().doubleValue()
									+ rnDtl.getReceiveQty().doubleValue());
							itemStockOutletFrom.setUpdateBy(user);
							itemStockOutletFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							itemStockDAO.update(itemStockOutletFrom);
						}

						if (Integer.parseInt(dataItemStockVO.getOutletId() + "") == Integer
								.parseInt(rn.getDO().getTransferTo() + "")) {
							ItemStock itemStockOutletTo = new ItemStock();
							itemStockOutletTo = itemStockDAO.findById(dataItemStockVO.getItemStockId());
							itemStockOutletTo.setIncomingQty(itemStockOutletTo.getIncomingQty().doubleValue()
									+ rnDtl.getReceiveQty().doubleValue());
							itemStockOutletTo.setStockQty(itemStockOutletTo.getStockQty().doubleValue()
									- rnDtl.getReceiveQty().doubleValue());
							itemStockOutletTo.setUpdateBy(user);
							itemStockOutletTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							itemStockDAO.update(itemStockOutletTo);
						}
					}
				}

				if (rn.getRnType().equals(CommonConstants.RNTYPE_PURCHASEORDER)) {
					/* update POS_PO */
					Po po = new Po();
					po = poDAO.findById(rn.getPoId());
					if (statusParsial.equals(CommonConstants.PO_PARTIAL)) {
						po.setStatusCode(statusParsial);
					} else if (statusClose.equals(CommonConstants.PO_CLOSE)) {
						po.setStatusCode(statusClose);
					} else if (statusNew.equals(CommonConstants.PO_NEW)) {
						po.setStatusCode(statusNew);
					}
					po.setUpdateBy(user);
					po.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					poDAO.update(po);

				} else if (rn.getRnType().equals(CommonConstants.RNTYPE_TRANSFERITEM)) {
					/* update POS_DO */
					DO transItem = new DO();
					transItem = DODAO.findById(rn.getDoId());

					transItem.setStatus(CommonConstants.DO_NEW);
					transItem.setUpdateBy(user);
					transItem.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					DODAO.update(transItem);
				}

				rnDtlDAO.delete(rnDtl);

			}

			rnDAO.delete(rn);
			rnDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			rnDAO.rollback();
		}

	}

	@Override
	public Rn findById(Long rnId) {
		return rnDAO.findById(rnId);
	}

	private String rnNumber() {
		String rnNumber = null;
		SimpleDateFormat sdfYearMont = new SimpleDateFormat("YYYYMM");

		String dateYearMonth = sdfYearMont.format(new Date());
		String rnNumberDb = rnDAO.searchRnNumberMax(dateYearMonth);

		if (rnNumberDb != null && !rnNumberDb.equals("")) {
			String[] split = rnNumberDb.split("-");
			String number = null;
			Integer splitInt = Integer.parseInt(split[1]);
			if ((splitInt + "").length() == 4) {
				number = "" + (splitInt + 1);
			} else if ((splitInt + "").length() == 3) {
				number = "0" + (splitInt + 1);
			} else if ((splitInt + "").length() == 2) {
				number = "00" + (splitInt + 1);
			} else if ((splitInt + "").length() == 1) {
				if (splitInt >= 9) {
					number = "00" + (splitInt + 1);
				} else {
					number = "000" + (splitInt + 1);
				}
			}

			rnNumber = dateYearMonth + "-" + number;

		} else {
			rnNumber = dateYearMonth + "-0001";
		}

		return rnNumber;

	}

	@Override
	public void saveDo(RnVO rnVO, List<RnDtlVO> rnDtlVoPoList, String user) {
		try {
			Rn rn = new Rn();

			if (rnVO.getCompanyId() != null) {
				rn.setCompanyId(rnVO.getCompanyId());
				rn.setCompany(companyDAO.findById(rnVO.getCompanyId()));
			}

			if (rnVO.getOutletId() != null) {
				rn.setOutletId(rnVO.getOutletId());
				rn.setOutlet(outletDAO.findById(rnVO.getOutletId()));
			}

			rn.setRnNo(rnNumber());
			rn.setRnDate(rnVO.getRnDate());
			rn.setRnTypeCode(rnVO.getRnTypeCode());
			rn.setRnType(parameterDAO.findByDetailId(rnVO.getRnTypeCode()));
			if (rnVO.getOutletOriginId() != null) {
				rn.setOutletOriginId(rnVO.getOutletOriginId());
				rn.setOutletOrigin(outletDAO.findById(rnVO.getOutletOriginId()));
			}
			if (rnVO.getDoId() != null) {
				rn.setDoId(rnVO.getDoId());
				rn.setDO(DODAO.findById(rnVO.getDoId()));
			}
			rn.setNotes(rnVO.getNotes());
			rn.setActiveFlag(CommonConstants.Y);

			String detailResume = "";
			List<RnDtl> rnDtlList = new ArrayList<RnDtl>();
			for (int i = 0; i < rnDtlVoPoList.size(); i++) {
				RnDtlVO dtlVo = (RnDtlVO) rnDtlVoPoList.get(i);

				if (dtlVo.getReceiveQty() != null) {
					if (dtlVo.getReceiveQty() > 0) {
						RnDtl rnDtl = new RnDtl();

						// rnDtl.setOutletId(rnVO.getOutletId());
						rnDtl.setRn(rn);
						rnDtl.setDoDtlId(dtlVo.getDODtlVO().getDoDtlId());
						rnDtl.setLineNo(i + 1);
						rnDtl.setReceiveQty(dtlVo.getReceiveQty());
						rnDtl.setReceiveUmId(dtlVo.getDODtlVO().getUmId());
						rnDtl.setActiveFlag(CommonConstants.Y);
						rnDtl.setCreateBy(user);
						rnDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));

						String content = dtlVo.getDODtlVO().getItemName() + ": " + dtlVo.getReceiveQty() + " "
								+ dtlVo.getDODtlVO().getUmName();
						if (i == 0) {
							detailResume = detailResume + content;
						} else {
							detailResume = detailResume + ", " + content;
						}

						rnDtlList.add(rnDtl);

						/* update Item Stock */
						ItemStockVO dataItemStockVO = itemStockDAO.dataItemStockItem(dtlVo.getDODtlVO().getItemId());

						if (dataItemStockVO != null) {
							updateItemStockDo(rnVO, dtlVo, new Double(0), user);
						}
						/* update End Item Stock */
					}
				}

			}

			DO DO = new DO();
			DO = DODAO.findById(rnVO.getDoId());
			DO.setStatus(CommonConstants.DO_CLOSE);
			DO.setUpdateBy(user);
			DO.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			DODAO.update(DO);

			rn.setListRnDetail(rnDtlList);
			rn.setItemResume(detailResume);
			rn.setCreateBy(user);
			rn.setCreateOn(new Timestamp(System.currentTimeMillis()));
			rnDAO.save(rn);
			rnDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			rnDAO.rollback();
		}

	}

	private void updateItemStockDo(RnVO rnVO, RnDtlVO rnDtlVO, Double receiveQtyOri, String user) {

		ItemStockVO dataItemStockVO = itemStockDAO.dataItemStockItem(rnDtlVO.getDODtlVO().getItemId());
		if (dataItemStockVO != null) {
			if (Integer.parseInt(dataItemStockVO.getOutletId() + "") == Integer
					.parseInt(rnDtlVO.getDODtlVO().getOutletTransferFrom() + "")) {
				ItemStock itemStockOutletFrom = new ItemStock();
				itemStockOutletFrom = itemStockDAO.findById(dataItemStockVO.getItemStockId());
				if (receiveQtyOri > 0) {
					itemStockOutletFrom.setOutgoingQty(itemStockOutletFrom.getOutgoingQty().doubleValue()
							+ receiveQtyOri.doubleValue() - rnDtlVO.getReceiveQty().doubleValue());
					itemStockOutletFrom.setStockQty(itemStockOutletFrom.getStockQty().doubleValue()
							+ receiveQtyOri.doubleValue() - rnDtlVO.getReceiveQty().doubleValue());
				} else {
					itemStockOutletFrom.setOutgoingQty(
							itemStockOutletFrom.getOutgoingQty().doubleValue() - rnDtlVO.getReceiveQty().doubleValue());
					itemStockOutletFrom.setStockQty(
							itemStockOutletFrom.getStockQty().doubleValue() - rnDtlVO.getReceiveQty().doubleValue());
				}
				itemStockOutletFrom.setUpdateBy(user);
				itemStockOutletFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				itemStockDAO.update(itemStockOutletFrom);
			}

			if (Integer.parseInt(dataItemStockVO.getOutletId() + "") == Integer
					.parseInt(rnDtlVO.getDODtlVO().getOutletTransferTo() + "")) {
				ItemStock itemStockOutletTo = new ItemStock();
				itemStockOutletTo = itemStockDAO.findById(dataItemStockVO.getItemStockId());
				if (receiveQtyOri > 0) {
					itemStockOutletTo.setIncomingQty(itemStockOutletTo.getIncomingQty().doubleValue()
							+ receiveQtyOri.doubleValue() - rnDtlVO.getReceiveQty().doubleValue());
					itemStockOutletTo.setStockQty(itemStockOutletTo.getStockQty().doubleValue()
							- receiveQtyOri.doubleValue() + rnDtlVO.getReceiveQty().doubleValue());
				} else {
					itemStockOutletTo.setIncomingQty(
							itemStockOutletTo.getIncomingQty().doubleValue() - rnDtlVO.getReceiveQty().doubleValue());
					itemStockOutletTo.setStockQty(
							itemStockOutletTo.getStockQty().doubleValue() + rnDtlVO.getReceiveQty().doubleValue());
				}
				itemStockOutletTo.setUpdateBy(user);
				itemStockOutletTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				itemStockDAO.update(itemStockOutletTo);
			}
		}
	}

	@Override
	public void updateDo(RnVO rnVO, List<RnDtlVO> rnDtlVOList, String user) {
		try {
			String detailResume = "";

			Rn rnHeader = null;
			rnHeader = rnDAO.findById(rnVO.getRnId());
			rnHeader.setRnDate(rnVO.getRnDate());
			rnHeader.setNotes(rnVO.getNotes());
			rnHeader.setActiveFlag(CommonConstants.Y);

			List<RnDtl> childListReal = rnHeader.getListRnDetail();

			if (rnDtlVOList != null) {
				RnDtl rnDetailDatabase = null;
				RnDtlVO rnDtlVo = null;
				boolean exist = false;

				/* untuk insert data baru dan update data lama */
				for (int x = 0; x < rnDtlVOList.size(); x++) {
					rnDtlVo = (RnDtlVO) rnDtlVOList.get(x);

					Double receiveQtyOri = new Double(0);
					if (rnDtlVo.getReceiveQty() > 0) {
						exist = false;
						for (int i = 0; i < childListReal.size(); i++) {
							rnDetailDatabase = (RnDtl) childListReal.get(i);

							if (Integer.parseInt(rnDetailDatabase.getDODtl().getItem().getItemId() + "") == Integer
									.parseInt(rnDtlVo.getDODtlVO().getItemId() + "")) {
								exist = true;
								receiveQtyOri = rnDetailDatabase.getReceiveQty().doubleValue();
								break;
							}
						}

						if (exist) {
							/* update Rn Detail */
							rnDetailDatabase.setReceiveQty(rnDtlVo.getReceiveQty());
							rnDetailDatabase.setUpdateBy(user);
							rnDetailDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));

							updateItemStockDo(rnVO, rnDtlVo, receiveQtyOri, user);
						}
					}
				}
			}

			DO DO = new DO();
			DO = DODAO.findById(rnVO.getDoId());
			DO.setStatus(CommonConstants.DO_CLOSE);
			DO.setUpdateBy(user);
			DO.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			DODAO.update(DO);

			rnHeader.setItemResume(detailResume);
			rnDAO.update(rnHeader);
			rnDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			rnDAO.rollback();
		}
	}

}