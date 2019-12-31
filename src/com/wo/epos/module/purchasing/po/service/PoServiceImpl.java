package com.wo.epos.module.purchasing.po.service;

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
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.purchasing.po.dao.PoDAO;
import com.wo.epos.module.purchasing.po.dao.PoDtlDAO;
import com.wo.epos.module.purchasing.po.model.Po;
import com.wo.epos.module.purchasing.po.model.PoDtl;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;
import com.wo.epos.module.purchasing.po.vo.PoVO;
import com.wo.epos.module.purchasing.supplier.service.SupplierService;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "poService")
@ViewScoped
public class PoServiceImpl implements PoService, Serializable {

	private static final long serialVersionUID = 2552043609009902234L;

	@ManagedProperty(value = "#{poDAO}")
	private PoDAO poDAO;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;

	@ManagedProperty(value = "#{poDtlDAO}")
	private PoDtlDAO poDtlDAO;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	public PoDAO getPoDAO() {
		return poDAO;
	}

	public void setPoDAO(PoDAO poDAO) {
		this.poDAO = poDAO;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	public PoDtlDAO getPoDtlDAO() {
		return poDtlDAO;
	}

	public void setPoDtlDAO(PoDtlDAO poDtlDAO) {
		this.poDtlDAO = poDtlDAO;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PoVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize, String sortField,
			boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return poDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return poDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(PoVO poVO, List<PoDtlVO> listPoDtlVO, String user) {
		String detailResume = "";
		try {
			Po po = new Po();
			po.setStatusCode(CommonConstants.PO_NEW);
			po.setStatus(parameterService.findByDetailId(CommonConstants.PO_NEW));

			if (poVO.getCompanyId() != null) {
				po.setCompanyId(poVO.getCompanyId());
				po.setCompany(companyService.findById(poVO.getCompanyId()));
			}

			if (poVO.getOutletId() != null) {
				po.setOutletId(poVO.getOutletId());
				po.setOutlet(outletService.findById(poVO.getOutletId()));
			}

			po.setSupplierId(poVO.getSupplierId());
			po.setSupplier(supplierService.findById(poVO.getSupplierId()));
			po.setPoNo(numberPo());
			po.setNotes(poVO.getNotes());
			po.setTaxTypeCode(poVO.getTaxTypeCode());
			po.setTaxType(parameterService.findByDetailId(poVO.getTaxTypeCode()));
			po.setPoDate((Date) poVO.getPoDate());
			if (!poVO.getTaxTypeCode().equals(CommonConstants.POTAXTYPE_WITHOUT)) {
				SystemProperty sysProp = systemPropertyService.searchSystemPropertyName(CommonConstants.TAX_VALUE);
				po.setTaxValue(new Double(sysProp.getSystemPropertyValue()));
			} else {
				po.setTaxValue(new Double(0));
			}

			List<PoDtl> listPoDtl = new ArrayList<PoDtl>();
			for (int i = 0; i < listPoDtlVO.size(); i++) {
				PoDtlVO dtlVO = (PoDtlVO) listPoDtlVO.get(i);

				PoDtl dtl = new PoDtl();
				dtl.setPo(po);
				dtl.setItemId(dtlVO.getItemId());
				// dtl.setOutletId(po.getOutletId());
				dtl.setOrderUm(dtlVO.getOrderUm());
				dtl.setOrderQty(dtlVO.getOrderQty());

				dtl.setUnitPrice(dtlVO.getUnitPrice());
				dtl.setDiscValue(dtlVO.getDiscValue());
				dtl.setDiscTypeCode(dtlVO.getDiscTypeCode());

				dtl.setDiscTypeCodeExt(dtlVO.getDiscTypeCode2());
				dtl.setDiscPercentExt(dtlVO.getDiscPercentExt());
				dtl.setDiscValueExt(dtlVO.getDiscValueExt());

				dtl.setDiscPercent(dtlVO.getDiscPercent());
				dtl.setReceiveQty(new Double(0));
				dtl.setLineNo(new Long(i + 1));
				dtl.setStatusCode(CommonConstants.PO_NEW);

				dtl.setActiveFlag(CommonConstants.Y);
				dtl.setCreateBy(user);
				dtl.setCreateOn(new Timestamp(System.currentTimeMillis()));

				String content = dtlVO.getItemName() + ": " + dtlVO.getOrderQty() + " " + dtlVO.getUmName();
				if (i == 0) {
					detailResume = detailResume + content;
				} else {
					detailResume = detailResume + ", " + content;
				}

				listPoDtl.add(dtl);

			}

			po.setListPoDetail(listPoDtl);
			po.setItemResume(detailResume);
			po.setActiveFlag(CommonConstants.Y);
			po.setCreateBy(user);
			po.setCreateOn(new Timestamp(System.currentTimeMillis()));

			poDAO.save(po);
			poDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			poDAO.rollback();
		}

	}

	@Override
	public void update(PoVO poVO, List<PoDtlVO> listPoDtlVO, String user) {
		String detailResume = "";
		try {
			Po po = new Po();
			po = poDAO.findById(poVO.getPoId());
			po.setOutletId(poVO.getOutletId());
			po.setOutlet(outletService.findById(poVO.getOutletId()));
			po.setSupplierId(poVO.getSupplierId());
			po.setSupplier(supplierService.findById(poVO.getSupplierId()));
			po.setPoNo(poVO.getPoNo());
			po.setNotes(poVO.getNotes());
			po.setCloseReason(poVO.getCloseReason());
			po.setTaxTypeCode(poVO.getTaxTypeCode());
			po.setTaxType(parameterService.findByDetailId(poVO.getTaxTypeCode()));
			po.setStatusCode(poVO.getStatusCode());
			po.setPoDate((Date) poVO.getPoDate());
			po.setActiveFlag(CommonConstants.Y);

			if (!poVO.getTaxTypeCode().equals(CommonConstants.POTAXTYPE_WITHOUT)) {
				SystemProperty sysProp = systemPropertyService.searchSystemPropertyName(CommonConstants.TAX_VALUE);
				po.setTaxValue(new Double(sysProp.getSystemPropertyValue()));
			}

			List<PoDtl> childListReal = po.getListPoDetail();
			if (listPoDtlVO != null) {
				PoDtl poDtl = null;
				PoDtl poDtlDatabase = null;
				PoDtlVO voPoDtl = null;
				boolean exist = false;

				/* untuk insert data baru dan update data lama */
				for (int x = 0; x < listPoDtlVO.size(); x++) {
					voPoDtl = (PoDtlVO) listPoDtlVO.get(x);
					
					String content = voPoDtl.getItemName() + ": " + voPoDtl.getOrderQty() + " "
							+ voPoDtl.getUmName();
					if (x == 0) {
						detailResume = detailResume + content;
					} else {
						detailResume = detailResume + ", " + content;
					}
					
					exist = false;
					for (int i = 0; i < childListReal.size(); i++) {
						poDtlDatabase = (PoDtl) childListReal.get(i);

						if (voPoDtl.getPoDtlId() != null && poDtlDatabase.getPoDtlId() != null
								&& poDtlDatabase.getPoDtlId().equals(voPoDtl.getPoDtlId())) {
							exist = true;
							break;
						}
					}

					if (exist) { /* update */
						poDtlDatabase.setItemId(voPoDtl.getItemId());
						// poDtlDatabase.setOutletId(voPoDtl.getOutletId());
						poDtlDatabase.setOrderUm(voPoDtl.getOrderUm());
						poDtlDatabase.setOrderQty(voPoDtl.getOrderQty());
						poDtlDatabase.setUnitPrice(voPoDtl.getUnitPrice());
						poDtlDatabase.setDiscValue(voPoDtl.getDiscValue());
						poDtlDatabase.setDiscTypeCode(voPoDtl.getDiscTypeCode());
						poDtlDatabase.setDiscPercent(voPoDtl.getDiscPercent());
						poDtlDatabase.setDiscTypeCodeExt(voPoDtl.getDiscTypeCode2());
						poDtlDatabase.setDiscPercentExt(voPoDtl.getDiscPercentExt());
						poDtlDatabase.setDiscValueExt(voPoDtl.getDiscValueExt());
						poDtlDatabase.setLineNo(new Long(x + 1));

						poDtlDatabase.setActiveFlag(CommonConstants.Y);
						poDtlDatabase.setUpdateBy(user);
						poDtlDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));

					} else {

						/* insert */
						poDtl = new PoDtl();
						poDtl.setPo(po);
						poDtl.setItemId(voPoDtl.getItemId());
						// poDtl.setOutletId(po.getOutletId());
						
						poDtl.setOrderUm(voPoDtl.getOrderUm());
						poDtl.setOrderQty(voPoDtl.getOrderQty());
						poDtl.setUnitPrice(voPoDtl.getUnitPrice());
						poDtl.setDiscValue(voPoDtl.getDiscValue());
						poDtl.setDiscTypeCode(voPoDtl.getDiscTypeCode());
						poDtl.setDiscPercent(voPoDtl.getDiscPercent());
						poDtl.setDiscTypeCodeExt(voPoDtl.getDiscTypeCode2());
						poDtl.setDiscPercentExt(voPoDtl.getDiscPercentExt());
						poDtl.setDiscValueExt(voPoDtl.getDiscValueExt());
						poDtl.setReceiveQty(new Double(0));
						poDtl.setLineNo(new Long(x + 1));
						poDtl.setStatusCode(CommonConstants.PO_NEW);

						poDtl.setActiveFlag(CommonConstants.Y);
						poDtl.setCreateBy(user);
						poDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));

						childListReal.add(poDtl);
					}
				}

				for (int i = 0; i < childListReal.size(); i++) {
					poDtlDatabase = (PoDtl) childListReal.get(i);

					for (int x = 0; x < listPoDtlVO.size(); x++) {
						voPoDtl = (PoDtlVO) listPoDtlVO.get(x);
						exist = false;

						if (voPoDtl.getPoDtlId() != null && poDtlDatabase.getPoDtlId() != null
								&& poDtlDatabase.getPoDtlId().equals(voPoDtl.getPoDtlId())) {
							exist = true;

							break;
						}
						
						else if(voPoDtl.getPoDtlId() != null && poDtlDatabase.getPoDtlId() == null){
							exist = true;

							break;
						}
						
					}

					if (!exist) { /* delete */
						i--;
						childListReal.remove(poDtlDatabase);
					}
				}

			}

			po.setItemResume(detailResume);
			poDAO.update(po);
			poDAO.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			poDAO.rollback();
		}

	}

	// public String generateDtlResume(PoDtlVO voPoDtl, int i, String content,
	// String detailResumeOld){
	//
	// String detailResume = "";
	//
	// if (i == 0) {
	// detailResume = detailResumeOld + content;
	// } else {
	// detailResume = detailResumeOld + ", " + content;
	// }
	// return detailResume;
	// }

	@Override
	public void delete(Long poId) {
		Po po = new Po();
		po = poDAO.findById(poId);
		PoDtl poDtl = null;
		for (int i = 0; i < po.getListPoDetail().size(); i++) {
			poDtl = new PoDtl();
			poDtl = poDtlDAO.findById(po.getListPoDetail().get(i).getPoDtlId());
			poDtlDAO.delete(poDtl);
		}

		poDAO.delete(po);
		poDAO.flush();
	}

	@Override
	public Po findById(Long poId) {
		return poDAO.findById(poId);
	}

	@Override
	public String numberPo() {
		String numberPo = null;
		SimpleDateFormat sdfYearMont = new SimpleDateFormat("YYYYMM");

		String dateYearMonth = sdfYearMont.format(new Date());
		String poNumber = searchPoMax(dateYearMonth);

		if (poNumber != null && !poNumber.equals("")) {
			String[] split = poNumber.split("-");
			String number = null;

			Integer splitInt = Integer.parseInt(split[1]);

			splitInt = splitInt + 1;

			if ((splitInt + "").length() == 4) {
				number = "" + (splitInt);
			} else if ((splitInt + "").length() == 3) {
				number = "0" + (splitInt);
			} else if ((splitInt + "").length() == 2) {
				number = "00" + (splitInt);
			} else if ((splitInt + "").length() == 1) {

				number = "000" + (splitInt);
			}

			numberPo = dateYearMonth + "-" + number;

		} else {
			numberPo = dateYearMonth + "-0001";
		}

		return numberPo;

	}

	@Override
	public String searchPoMax(String yearMonth) {
		return poDAO.searchPoMax(yearMonth);
	}

	@Override
	public List<PoVO> searchPoNumber(Long supplierId) {
		return poDAO.searchPoNumber(supplierId);
	}

	@Override
	public Po findByPoNumber(String poNumber) {
		return poDAO.findByPoNumber(poNumber);
	}

}
