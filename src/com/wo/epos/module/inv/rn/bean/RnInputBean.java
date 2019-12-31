package com.wo.epos.module.inv.rn.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.rn.model.Rn;
import com.wo.epos.module.inv.rn.model.RnDtl;
import com.wo.epos.module.inv.rn.service.RnService;
import com.wo.epos.module.inv.rn.vo.RnDtlVO;
import com.wo.epos.module.inv.rn.vo.RnVO;
import com.wo.epos.module.inv.DO.model.DO;
import com.wo.epos.module.inv.DO.model.DODtl;
import com.wo.epos.module.inv.DO.service.DOService;
import com.wo.epos.module.inv.DO.vo.DODtlVO;
import com.wo.epos.module.inv.DO.vo.DOVO;
import com.wo.epos.module.purchasing.po.model.Po;
import com.wo.epos.module.purchasing.po.model.PoDtl;
import com.wo.epos.module.purchasing.po.service.PoService;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;
import com.wo.epos.module.purchasing.po.vo.PoVO;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.service.SupplierService;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "rnInputBean")
@ViewScoped
public class RnInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -6329779815150150732L;
	static Logger logger = Logger.getLogger(RnInputBean.class);

	@ManagedProperty(value = "#{rnService}")
	private RnService rnService;

	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;

	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;

	@ManagedProperty(value = "#{poService}")
	private PoService poService;

	@ManagedProperty(value = "#{DOService}")
	private DOService DOService;

	private RnVO rnVO = new RnVO();

	private List<RnDtlVO> rnDtlVOList = new ArrayList<RnDtlVO>();
	// private List<SupplierVO> supplierDtlList = new ArrayList<SupplierVO>();
	private ArrayList<SelectItem> supplierDtlList = new ArrayList<SelectItem>();
	// private List<PoVO> poNumberList = new ArrayList<PoVO>();
	private ArrayList<SelectItem> poNumberList = new ArrayList<SelectItem>();
	private List<RnDtlVO> rnDtlVoPoList = new ArrayList<RnDtlVO>();
	private List<Outlet> outletOriginList = new ArrayList<Outlet>();
	private List<DOVO> senderNumberList = new ArrayList<DOVO>();

	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> rnTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletOriginSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> senderNumberSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;
	private String completeSupplier;
	private String completePoNumber;
	/* private String completeOutletOrigin; */
	private String completeSenderNumber;

	private boolean purchaseOrderBoolean;
	private boolean DOBoolean;

	@PostConstruct
	public void postConstruct() {
		super.init();
		rnVO = new RnVO();

		companySelectItem = new ArrayList<SelectItem>();
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for (CompanyVO comVO : companySelectList) {
			companySelectItem.add(new SelectItem(comVO.getCompanyId(), comVO.getCompanyName()));
		}

		if (userSession.getCompanyId() != null) {
			outletSelectItem = new ArrayList<SelectItem>();
			List<Outlet> outletSelectList = outletService.findOutletByCompany(userSession.getCompanyId());
			for (Outlet outVO : outletSelectList) {
				outletSelectItem.add(new SelectItem(outVO.getOutletId(), outVO.getOutletName()));
			}
		}

		rnTypeSelectItem = new ArrayList<SelectItem>();
		List<ParameterDtl> paramDetlVoList = parameterService.parameterDtlList(CommonConstants.RN_TYPE);
		for (ParameterDtl param : paramDetlVoList) {
			rnTypeSelectItem.add(new SelectItem(param.getParameterDtlCode(), param.getParameterDtlName()));
		}

		poNumberList = new ArrayList<SelectItem>();

		// supplierDtlList = new ArrayList<SupplierVO>();
		// if(userSession.getCompanyId() !=null){
		// supplierDtlList =
		// supplierService.searchSupplierListByCompany(userSession.getCompanyId());
		// }
		// supplierDtlList = new ArrayList<SelectItem>();
		// if(userSession.getCompanyId() != null){
		// List<SupplierVO> supplierList =
		// supplierService.searchSupplierListByCompany(userSession.getCompanyId());
		// for(SupplierVO supplierVO : supplierList)
		// {
		// supplierDtlList.add(new SelectItem(supplierVO.getSupplierId(),
		// supplierVO.getSupplierCode() + " - " +
		// supplierVO.getSupplierName()));
		// }
		// }

		outletOriginList = new ArrayList<Outlet>();

		MODE_TYPE = "ADD";

		purchaseOrderBoolean = false;
		DOBoolean = false;

	}

	public void changeCompany() {
		if (rnVO.getCompanyId() != null) {
			outletSelectItem = new ArrayList<SelectItem>();
			List<Outlet> outletSelectList = outletService.findOutletByCompany(rnVO.getCompanyId());
			for (Outlet outVO : outletSelectList) {
				outletSelectItem.add(new SelectItem(outVO.getOutletId(), outVO.getOutletName()));
			}

			if (rnVO.getCompanyId() != null) {
				supplierDtlList = new ArrayList<SelectItem>();
				// supplierDtlList =
				// supplierService.searchSupplierListByCompany(rnVO.getCompanyId());
				List<SupplierVO> supplierList = supplierService.searchSupplierListByCompany(rnVO.getCompanyId());
				for (SupplierVO supplierVO : supplierList) {
					supplierDtlList.add(new SelectItem(supplierVO.getSupplierId(),
							supplierVO.getSupplierCode() + " - " + supplierVO.getSupplierName()));
				}
			}
		}
	}

	public boolean validate() {
		boolean valid = true;

		if (rnDtlVoPoList != null) {
			for (int i = 0; i < rnDtlVoPoList.size(); i++) {
				RnDtlVO rnDtlVo = rnDtlVoPoList.get(i);
				if (rnDtlVo.getPoDtlVO() != null) {
					Double totalAccepted = rnDtlVo.getReceiveQty() + rnDtlVo.poDtlVO.getReceiveQty();
					if (totalAccepted > rnDtlVo.poDtlVO.getOrderQty()) {

						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
								facesUtils.getResourceBundleStringValue("formRnRaccepted") + " "
										+ facesUtils.getResourceBundleStringValue("errorRnReceiveQty"));
						valid = false;

						/*
						 * facesUtils.addFacesMsg( FacesMessage.SEVERITY_ERROR,
						 * "frm001:dataTablePo:"+i+":receiveQty",
						 * facesUtils.getResourceBundleStringValue(
						 * "formRnRaccepted") + " " +
						 * facesUtils.getResourceBundleStringValue(
						 * "errorRnReceiveQty"), null); valid = false;
						 */
					}
				}

				if (rnDtlVo.getDODtlVO() != null) {
					/*
					 * Double totalAccepted = rnDtlVo.getReceiveQty() +
					 * rnDtlVo.getReceiveQty();
					 */
					if (rnDtlVo.DODtlVO.getDeliveryQty() > rnDtlVo.getReceiveQty()) {

						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
								facesUtils.getResourceBundleStringValue("formRnSum") + " "
										+ facesUtils.getResourceBundleStringValue("formRnRaccepted") + " "
										+ facesUtils.getResourceBundleStringValue("errorRnReceiveQty"));
						valid = false;
					}
				}

			}
		}

		return valid;
	}

	public boolean validateCompany() {
		boolean valid = true;
		if (userSession.getCompanyId() == null) {
			if (rnVO.getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
						facesUtils.getResourceBundleStringValue("formProductCompany") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else {
				valid = true;
			}
		} else {
			valid = true;
		}

		return valid;
	}
	/*
	 * public boolean validateOutlet() { boolean valid = true; if
	 * (userSession.getOutletId()== null) { if (rnVO.getOutletId() == null) {
	 * 
	 * facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "frm001:messagesRn",
	 * facesUtils.getResourceBundleStringValue("formOutletTitle") + " " +
	 * facesUtils.getResourceBundleStringValue("errorMustBeFilled")); valid =
	 * false;
	 * 
	 * } else { valid = true; } } else { valid = true; }
	 * 
	 * return valid; }
	 */

	public boolean validateRn() {
		boolean valid = true;

		if (rnVO.getRnDate() == null) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
					facesUtils.getResourceBundleStringValue("formRnDate") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		}
		if (rnVO.getRnTypeCode() == null || rnVO.getRnTypeCode().trim().isEmpty()) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
					facesUtils.getResourceBundleStringValue("formRnType") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		}

		return valid;
	}

	public boolean purchaseOrder() {
		boolean valid = true;
		if (purchaseOrderBoolean == true) {
			if (rnVO.getSupplierId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
						facesUtils.getResourceBundleStringValue("formRnSupplier") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}
			if (rnVO.getPoId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
						facesUtils.getResourceBundleStringValue("formRnPoNumber") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}

		} else {
			valid = true;
		}

		return valid;
	}

	public boolean DO() {
		boolean valid = true;
		if (DOBoolean == true) {
			if (rnVO.getOutletOriginId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
						facesUtils.getResourceBundleStringValue("formRnOutletOrigin") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}
			if (rnVO.getDoNo() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesRn",
						facesUtils.getResourceBundleStringValue("formRnSenderNumber") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}
		} else {
			valid = true;
		}

		return valid;
	}

	public boolean save() {
		boolean valid = false;

		try {
			if (validate()) {
				if (userSession.getCompanyId() != null) {
					rnVO.setCompanyId(userSession.getCompanyId());

					if (userSession.getOutletId() != null) {
						rnVO.setOutletId(userSession.getOutletId());
					}
				}

				if (MODE_TYPE.equals("ADD")) {
					if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_PURCHASEORDER)) {
						rnService.savePo(rnVO, rnDtlVoPoList, userSession.getUserCode());
					} else if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_TRANSFERITEM)) {
						rnService.saveDo(rnVO, rnDtlVoPoList, userSession.getUserCode());
					}

					facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages",
							facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")),
							null);
				} else if (MODE_TYPE.equals("EDIT")) {
					if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_PURCHASEORDER)) {
						rnService.updatePo(rnVO, rnDtlVOList, userSession.getUserCode());
					} else if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_TRANSFERITEM)) {
						rnService.updateDo(rnVO, rnDtlVoPoList, userSession.getUserCode());
					}

					facesUtils.addFacesMsg(FacesMessage.SEVERITY_INFO, "frm001:messages", facesUtils
							.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), null);
				}

				valid = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:messages",
					"Operation Failed : " + ex.getMessage(), null);
		}

		return valid;
	}

	public void modeAdd() {
		rnVO = new RnVO();
		supplierDtlList = new ArrayList<SelectItem>();
		if (userSession.getCompanyId() != null) {
			List<SupplierVO> supplierList = supplierService.searchSupplierListByCompany(userSession.getCompanyId());
			for (SupplierVO supplierVO : supplierList) {
				supplierDtlList.add(new SelectItem(supplierVO.getSupplierId(),
						supplierVO.getSupplierCode() + " - " + supplierVO.getSupplierName()));
			}
		}
		poNumberList = new ArrayList<SelectItem>();
		clearTablePo();
		clearTableTanfer();
		disableFlag();
		// rnDtlVoPoList = new ArrayList<RnDtlVO>();
	}

	public void modeEdit(List<RnVO> rnList) {
		try {
			MODE_TYPE = "EDIT";
			for (int i = 0; i < rnList.size(); i++) {
				RnVO rnVOTemp = (RnVO) rnList.get(i);
				rnVO = rnVOTemp;
			}

			Rn rnEdit = new Rn();
			rnEdit = rnService.findById(rnVO.getRnId());
			if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_PURCHASEORDER)) {
				// completeSupplier = rnVO.getSupplierCode() + " - " +
				// rnVO.getSupplierName();
				// completePoNumber = rnVO.getPoNumber();
				rnVO.setSupplierId(rnEdit.getSupplierId());
				purchaseOrderBoolean = true;
				DOBoolean = false;
			} else if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_TRANSFERITEM))

			{
				/*
				 * completeOutletOrigin = rnVO.getOutletOriginCode() + " - " +
				 * rnVO.getOutletOriginName(); completeSenderNumber =
				 * rnVO.getDOName();
				 */
				purchaseOrderBoolean = false;
				DOBoolean = true;
			}

			rnDtlVoPoList.clear();
			for (int j = 0; j < rnEdit.getListRnDetail().size(); j++) {
				RnDtl rnDtlEdit = (RnDtl) rnEdit.getListRnDetail().get(j);
				RnDtlVO rnDtlVo = new RnDtlVO();
				PoDtlVO poVO = new PoDtlVO();

				if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_PURCHASEORDER)) {
					poVO.setPoDtlId(rnDtlEdit.getPoDtl().getPoDtlId());
					poVO.setPoId(rnDtlEdit.getPoDtl().getPo().getPoId());
					if (rnDtlEdit.getPoDtl().getItem() != null) {
						poVO.setItemId(rnDtlEdit.getPoDtl().getItemId());
						poVO.setItemCode(rnDtlEdit.getPoDtl().getItem().getItemCode());
						poVO.setItemName(rnDtlEdit.getPoDtl().getItem().getItemName());
						poVO.setReorderQtyItem(rnDtlEdit.getPoDtl().getItem().getReorderQty());

						if (rnDtlEdit.getReceiveUm() != null) {
							poVO.setUmId(rnDtlEdit.getReceiveUm().getUmId());
							poVO.setUmName(rnDtlEdit.getReceiveUm().getUmName());
						}
					}

					if (rnDtlEdit.getPoDtl().getOrderQty() != null) {
						poVO.setOrderQty(rnDtlEdit.getPoDtl().getOrderQty());
					}
					if (rnDtlEdit.getPoDtl().getReceiveQty() != null) {
						poVO.setReceiveQty(rnDtlEdit.getPoDtl().getReceiveQty());
					}
					if (rnDtlEdit.getPoDtl().getUnitPrice() != null) {
						poVO.setUnitPrice(rnDtlEdit.getPoDtl().getUnitPrice());
					}

					rnDtlVo.setPoDtlVO(poVO);
				} else if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_TRANSFERITEM)) {
					DODtlVO voDtl = new DODtlVO();

					if (rnDtlEdit.getDODtl() != null) {
						if (rnDtlEdit.getDODtl().getItem() != null) {
							voDtl.setDoDtlId(rnDtlEdit.getDoDtlId());
							voDtl.setItemId(rnDtlEdit.getDODtl().getItem().getItemId());
							voDtl.setItemCode(rnDtlEdit.getDODtl().getItem().getItemCode());
							voDtl.setItemName(rnDtlEdit.getDODtl().getItem().getItemName());
						}

						if (rnDtlEdit.getDODtl().getDeliveryUm() != null) {
							voDtl.setUmId(rnDtlEdit.getDODtl().getDeliveryUm().getUmId());
							voDtl.setUmName(rnDtlEdit.getDODtl().getDeliveryUm().getUmName());
						}

						voDtl.setDoId(rnDtlEdit.getDODtl().getDO().getDoId());
						voDtl.setOutletTransferFrom(rnDtlEdit.getDODtl().getDO().getTransferFrom().getOutletId());
						voDtl.setOutletTransferTo(rnDtlEdit.getDODtl().getDO().getTransferTo().getOutletId());
					}

					if (rnDtlEdit.getDODtl().getDeliveryQty() != null) {
						voDtl.setDeliveryQty(rnDtlEdit.getDODtl().getDeliveryQty());
					}

					rnDtlVo.setDODtlVO(voDtl);
				}

				rnDtlVo.setRnDtlId(rnDtlEdit.getRnDtlId());
				rnDtlVo.setRnId(rnDtlEdit.getRn().getRnId());
				rnDtlVo.setReceiveQty(rnDtlEdit.getReceiveQty());

				rnDtlVoPoList.add(rnDtlVo);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void clearTablePo() {
		// poDtlVoList = new ArrayList<PoDtlVO>();
		rnDtlVoPoList = new ArrayList<RnDtlVO>();
		// completeSupplier = "";
		// completePoNumber = "";
		// rnVO.setSupplierDocNo("");
		// rnVO.setSupplierDocDate(null);
		// rnVO.setNotes("");
		purchaseOrderBoolean = false;
		DOBoolean = true;
	}

	private void clearTableTanfer() {
		outletOriginList = new ArrayList<Outlet>();
		/* completeOutletOrigin = ""; */
		completeSenderNumber = "";
		purchaseOrderBoolean = true;
		DOBoolean = false;
	}

	private void disableFlag() {
		purchaseOrderBoolean = false;
		DOBoolean = false;
	}

	public void changeRnType() {
		if (rnVO.getRnTypeCode() != null && !rnVO.getRnTypeCode().equals("")) {
			if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_PURCHASEORDER)) {
				clearTableTanfer();
			} else if (rnVO.getRnTypeCode().equals(CommonConstants.RNTYPE_TRANSFERITEM)) {
				clearTablePo();
				outletOriginList = new ArrayList<Outlet>();
				if (userSession.getCompanyId() != null) {
					if (userSession.getOutletId() != null) {
						outletOriginList = outletService.searchDataOutletByCompany(userSession.getCompanyId(),
								userSession.getOutletId());
					} else {
						outletOriginList = outletService.findOutletByCompany(userSession.getCompanyId());
					}
				} else {
					if (rnVO.getOutletId() != null) {
						outletOriginList = outletService.searchDataOutletByCompany(rnVO.getCompanyId(),
								rnVO.getOutletId());
					} else {
						outletOriginList = outletService.findOutletByCompany(rnVO.getCompanyId());
					}
				}

				outletOriginSelectItem = new ArrayList<SelectItem>();
				for (Outlet ou : outletOriginList) {
					outletOriginSelectItem.add(new SelectItem(ou.getOutletId(), ou.getOutletName()));
				}
				senderNumberSelectItem = new ArrayList<SelectItem>();
			}
		} else {
			clearTableTanfer();
			clearTablePo();
			disableFlag();
		}
	}

	// public List<String> completeSupplier(String outoCompleteText){
	// List<String> resultList = new ArrayList<String>();
	// for(SupplierVO supVo : supplierDtlList){
	// if(userSession.getCompanyId() == null){
	// if(supVo.getSupplierName().toUpperCase().contains(outoCompleteText.toUpperCase())){
	// resultList.add(supVo.getSupplierCode() +" - "+supVo.getSupplierName());
	// }
	// }else{
	// if(Integer.parseInt(userSession.getCompanyId()+"") ==
	// Integer.parseInt(supVo.getCompanyId()+"")){
	// if(supVo.getSupplierName().toUpperCase().contains(outoCompleteText.toUpperCase())){
	// resultList.add(supVo.getSupplierCode() +" - "+supVo.getSupplierName());
	// }
	// }
	// }
	//
	//
	// }
	//
	// return resultList;
	// }

	// public void handleSelectSupplier(SelectEvent event) {
	public void handleSelectSupplier() {
		// Object item = event.getObject();
		String param = facesUtils.retrieveRequestParam("supplier");

		// String[] splitSupplier = param.toString().split(" - ");
		if (param != null && !param.isEmpty()) {
			/*
			 * Supplier supp = supplierService.findById(new Long(param));
			 * 
			 * if(supp.getSupplierId() !=null){ List<PoVO> poNumberList =
			 * poService.searchPoNumber(supp.getSupplierId()); for(PoVO poVO :
			 * poNumberList) { this.poNumberList.add(new
			 * SelectItem(poVO.getPoId(), poVO.getPoNo())); } } rnDtlVoPoList =
			 * new ArrayList<RnDtlVO>();
			 */
			this.poNumberList = new ArrayList<SelectItem>();
			Supplier supp = supplierService.findById(new Long(param));

			if (supp.getSupplierId() != null) {
				List<PoVO> poNumberList = poService.searchPoNumber(supp.getSupplierId());
				for (PoVO poVO : poNumberList) {
					this.poNumberList.add(new SelectItem(poVO.getPoId(), poVO.getPoNo()));
				}
			}
			rnDtlVoPoList = new ArrayList<RnDtlVO>();

		} else {
			rnVO.setPoId(null);
			this.poNumberList = new ArrayList<SelectItem>();
			rnDtlVoPoList = new ArrayList<RnDtlVO>();
		}
	}

	/*
	 * public List<String> completePoNumber(String outoCompleteText){
	 * List<String> resultList = new ArrayList<String>(); for(PoVO poVo :
	 * poNumberList){
	 * if(poVo.getPoNo().toUpperCase().contains(outoCompleteText.toUpperCase()))
	 * { resultList.add(poVo.getPoNo()); } }
	 * 
	 * return resultList; }
	 */

	public void handleSelectPoNumber() {
		// Object item = event.getObject();
		// poDtlVoList = new ArrayList<PoDtlVO>();

		String param = facesUtils.retrieveRequestParam("poNumber");
		if (param != null && !param.isEmpty()) {
			rnDtlVoPoList = new ArrayList<RnDtlVO>();

			Po po = poService.findById(new Long(param));
			rnVO.setPoId(po.getPoId());

			if (po != null) {
				for (PoDtl poDetail : po.getListPoDetail()) {
					PoDtlVO vo = new PoDtlVO();
					RnDtlVO rnDtlVo = new RnDtlVO();

					vo.setPoDtlId(poDetail.getPoDtlId());
					vo.setPoId(poDetail.getPo().getPoId());
					if (poDetail.getItem() != null) {
						vo.setItemId(poDetail.getItemId());
						vo.setItemCode(poDetail.getItem().getItemCode());
						vo.setItemName(poDetail.getItem().getItemName());
						vo.setReorderQtyItem(poDetail.getItem().getReorderQty());

						if (poDetail.getItem().getUm() != null) {
							vo.setUmId(poDetail.getItem().getUm().getUmId());
							vo.setUmName(poDetail.getItem().getUm().getUmName());
						}
					}

					if (poDetail.getOrderQty() != null) {
						vo.setOrderQty(poDetail.getOrderQty());
					}
					if (poDetail.getReceiveQty() != null) {
						vo.setReceiveQty(poDetail.getReceiveQty());
					}
					if (poDetail.getUnitPrice() != null) {
						vo.setUnitPrice(poDetail.getUnitPrice());
					}

					vo.setStatusCode(poDetail.getStatusCode());

					rnDtlVo.setPoDtlVO(vo);
					rnDtlVoPoList.add(rnDtlVo);
				}
			}
		}
	}

	/*
	 * public List<String> completeOutletOrigin(String outoCompleteText) {
	 * List<String> resultList = new ArrayList<String>(); for (Outlet out :
	 * outletOriginList) { if
	 * (out.getOutletName().toUpperCase().contains(outoCompleteText.toUpperCase(
	 * ))) { resultList.add(out.getOutletCode() + " - " + out.getOutletName());
	 * } }
	 * 
	 * return resultList; }
	 */

	// public void handleSelectOutletOrigin(SelectEvent event) {
	// Object item = event.getObject();
	//
	// rnDtlVoPoList = new ArrayList<RnDtlVO>();
	// String[] splitItem = item.toString().split(" - ");
	//
	// Outlet outletOr =
	// outletService.findByOutletCode(splitItem[0].toString().trim());
	//
	// if (outletOr != null) {
	// if (userSession.getOutletId() != null) {
	// senderNumberList =
	// DOService.searchDataTransItemByOutlet(userSession.getOutletId(),
	// outletOr.getOutletId());
	// } else {
	// senderNumberList =
	// DOService.searchDataTransItemByOutlet(rnVO.getOutletId(),
	// outletOr.getOutletId());
	// }
	//
	// rnVO.setOutletOriginId(outletOr.getOutletId());
	// }
	//
	// completeSenderNumber = "";
	// rnDtlVoPoList = new ArrayList<RnDtlVO>();
	// }
	//
	// public void handleSelectSenderNumber(SelectEvent event) {
	// Object item = event.getObject();
	//
	// DO transItem = DOService.findByDoNo(item.toString().trim());
	// rnDtlVoPoList = new ArrayList<RnDtlVO>();
	//
	// rnVO.setDoId(transItem.getDoId());
	// if (transItem.getListDODtl().size() > 0) {
	// for (DODtl dtl : transItem.getListDODtl()) {
	// RnDtlVO rnDtlVo = new RnDtlVO();
	// DODtlVO voDtl = new DODtlVO();
	//
	// voDtl.setDoDtlId(dtl.getDoDtlId());
	// voDtl.setItemId(dtl.getItem().getItemId());
	// voDtl.setItemCode(dtl.getItem().getItemCode());
	// voDtl.setItemName(dtl.getItem().getItemName());
	// voDtl.setUmId(dtl.getDeliveryUm().getUmId());
	// voDtl.setUmName(dtl.getDeliveryUm().getUmName());
	// voDtl.setDeliveryQty(dtl.getDeliveryQty());
	// voDtl.setDoId(dtl.getDO().getDoId());
	// voDtl.setOutletTransferFrom(dtl.getDO().getTransferFrom().getOutletId());
	// voDtl.setOutletTransferTo(dtl.getDO().getTransferTo().getOutletId());
	//
	// rnDtlVo.setDODtlVO(voDtl);
	// rnDtlVoPoList.add(rnDtlVo);
	// }
	//
	// }
	//
	// }

	public void handleSelectOutletOrigin() {

		senderNumberSelectItem = new ArrayList<SelectItem>();

		if (rnVO.getOutletOriginId() != null) {
			if (userSession.getOutletId() != null) {
				senderNumberList = DOService.searchDataTransItemByOutlet(rnVO.getOutletOriginId(),
						userSession.getOutletId());
			} else {
				senderNumberList = DOService.searchDataTransItemByOutlet(rnVO.getOutletOriginId(), rnVO.getOutletId());
			}
		}
		for (DOVO vo : senderNumberList) {
			senderNumberSelectItem.add(new SelectItem(vo.getDoNo(), vo.getDoNo()));
		}

		completeSenderNumber = "";
		rnDtlVoPoList = new ArrayList<RnDtlVO>();
	}

	public void handleSelectSenderNumber() {

		DO transItem = DOService.findByDoNo(rnVO.getDoNo());
		rnDtlVoPoList = new ArrayList<RnDtlVO>();
		if (transItem.getDoId() != null) {
			rnVO.setDoId(transItem.getDoId());
		}
		if (transItem.getListDODtl().size() > 0) {
			for (DODtl dtl : transItem.getListDODtl()) {
				RnDtlVO rnDtlVo = new RnDtlVO();
				DODtlVO voDtl = new DODtlVO();

				voDtl.setDoDtlId(dtl.getDoDtlId());
				voDtl.setItemId(dtl.getItem().getItemId());
				voDtl.setItemCode(dtl.getItem().getItemCode());
				voDtl.setItemName(dtl.getItem().getItemName());
				voDtl.setUmId(dtl.getDeliveryUm().getUmId());
				voDtl.setUmName(dtl.getDeliveryUm().getUmName());
				voDtl.setDeliveryQty(dtl.getDeliveryQty());
				voDtl.setDoId(dtl.getDO().getDoId());
				voDtl.setOutletTransferFrom(dtl.getDO().getTransferFrom().getOutletId());
				voDtl.setOutletTransferTo(dtl.getDO().getTransferTo().getOutletId());

				rnDtlVo.setDODtlVO(voDtl);
				rnDtlVoPoList.add(rnDtlVo);
			}

		}

	}

	public List<String> completeSenderNumber(String outoCompleteText) {
		List<String> resultList = new ArrayList<String>();
		for (DOVO itemVo : senderNumberList) {
			if (itemVo.getDoNo().toUpperCase().contains(outoCompleteText.toUpperCase())) {
				resultList.add(itemVo.getDoNo());
			}
		}

		return resultList;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RnInputBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public RnService getRnService() {
		return rnService;
	}

	public void setRnService(RnService rnService) {
		this.rnService = rnService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}

	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}

	public List<SelectItem> getRnTypeSelectItem() {
		return rnTypeSelectItem;
	}

	public void setRnTypeSelectItem(List<SelectItem> rnTypeSelectItem) {
		this.rnTypeSelectItem = rnTypeSelectItem;
	}

	public RnVO getRnVO() {
		return rnVO;
	}

	public void setRnVO(RnVO rnVO) {
		this.rnVO = rnVO;
	}

	public List<RnDtlVO> getRnDtlVOList() {
		return rnDtlVOList;
	}

	public void setRnDtlVOList(List<RnDtlVO> rnDtlVOList) {
		this.rnDtlVOList = rnDtlVOList;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public ArrayList<SelectItem> getSupplierDtlList() {
		return supplierDtlList;
	}

	public void setSupplierDtlList(ArrayList<SelectItem> supplierDtlList) {
		this.supplierDtlList = supplierDtlList;
	}

	public ArrayList<SelectItem> getPoNumberList() {
		return poNumberList;
	}

	public void setPoNumberList(ArrayList<SelectItem> poNumberList) {
		this.poNumberList = poNumberList;
	}

	public String getCompleteSupplier() {
		return completeSupplier;
	}

	public void setCompleteSupplier(String completeSupplier) {
		this.completeSupplier = completeSupplier;
	}

	public PoService getPoService() {
		return poService;
	}

	public void setPoService(PoService poService) {
		this.poService = poService;
	}

	public String getCompletePoNumber() {
		return completePoNumber;
	}

	public void setCompletePoNumber(String completePoNumber) {
		this.completePoNumber = completePoNumber;
	}

	public boolean isPurchaseOrderBoolean() {
		return purchaseOrderBoolean;
	}

	public void setPurchaseOrderBoolean(boolean purchaseOrderBoolean) {
		this.purchaseOrderBoolean = purchaseOrderBoolean;
	}

	public boolean isDOBoolean() {
		return DOBoolean;
	}

	public void setDOBoolean(boolean DOBoolean) {
		this.DOBoolean = DOBoolean;
	}

	/*
	 * public String getCompleteOutletOrigin() { return completeOutletOrigin; }
	 * 
	 * public void setCompleteOutletOrigin(String completeOutletOrigin) {
	 * this.completeOutletOrigin = completeOutletOrigin; }
	 */

	public String getCompleteSenderNumber() {
		return completeSenderNumber;
	}

	public void setCompleteSenderNumber(String completeSenderNumber) {
		this.completeSenderNumber = completeSenderNumber;
	}

	public List<RnDtlVO> getRnDtlVoPoList() {
		return rnDtlVoPoList;
	}

	public void setRnDtlVoPoList(List<RnDtlVO> rnDtlVoPoList) {
		this.rnDtlVoPoList = rnDtlVoPoList;
	}

	public List<Outlet> getOutletOriginList() {
		return outletOriginList;
	}

	public void setOutletOriginList(List<Outlet> outletOriginList) {
		this.outletOriginList = outletOriginList;
	}

	public DOService getDOService() {
		return DOService;
	}

	public void setDOService(DOService DOService) {
		this.DOService = DOService;
	}

	public List<DOVO> getSenderNumberList() {
		return senderNumberList;
	}

	public void setSenderNumberList(List<DOVO> senderNumberList) {
		this.senderNumberList = senderNumberList;
	}

	public List<SelectItem> getOutletOriginSelectItem() {
		return outletOriginSelectItem;
	}

	public void setOutletOriginSelectItem(List<SelectItem> outletOriginSelectItem) {
		this.outletOriginSelectItem = outletOriginSelectItem;
	}

	public List<SelectItem> getSenderNumberSelectItem() {
		return senderNumberSelectItem;
	}

	public void setSenderNumberSelectItem(List<SelectItem> senderNumberSelectItem) {
		this.senderNumberSelectItem = senderNumberSelectItem;
	}

}
