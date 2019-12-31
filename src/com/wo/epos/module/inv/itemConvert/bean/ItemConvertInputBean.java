package com.wo.epos.module.inv.itemConvert.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.service.ItemService;
import com.wo.epos.module.inv.item.vo.ItemBomVO;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.inv.itemConvert.service.ItemConvertDtlService;
import com.wo.epos.module.inv.itemConvert.service.ItemConvertService;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertDtlVO;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertVO;
import com.wo.epos.module.inv.itemStock.service.ItemStockService;
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "itemConvertInputBean")
@ViewScoped
public class ItemConvertInputBean extends CommonBean implements Serializable {

	private static final long serialVersionUID = -1256963229247693745L;

	static Logger logger = Logger.getLogger(ItemConvertInputBean.class);

	@ManagedProperty(value = "#{itemConvertService}")
	private ItemConvertService itemConvertService;

	@ManagedProperty(value = "#{itemConvertDtlService}")
	private ItemConvertDtlService itemConvertDtlService;

	@ManagedProperty(value = "#{itemService}")
	private ItemService itemService;

	@ManagedProperty(value = "#{itemStockService}")
	private ItemStockService itemStockService;

	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;

	private ItemConvertVO itemConvertVO = new ItemConvertVO();

	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> itemCompositeSelectItem = new ArrayList<SelectItem>();

	private String MODE_TYPE;
	private String searchAllDialog;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			itemConvertVO = new ItemConvertVO();
			itemConvertVO.setListItemConvertDtlVO(new ArrayList<ItemConvertDtlVO>());

			if (userSession.getCompanyId() != null) {
				itemConvertVO.setCompanyId(userSession.getCompanyId());
			}

			// set drop down list company
			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			for (CompanyVO comVO : companySelectList) {
				companySelectItem.add(new SelectItem(comVO.getCompanyId(), comVO.getCompanyName()));
			}

			// set drop down list item composite
			if (!isAdminMode()) {
				itemCompositeSelectItem = new ArrayList<SelectItem>();
				List<ItemVO> itemVoList = itemService.searchItemCompositeListByCompany(itemConvertVO.getCompanyId());
				for (ItemVO vo : itemVoList) {
					itemCompositeSelectItem.add(new SelectItem(vo.getItemId(), vo.getItemName()));
				}
			}
		}
	}

	public void modeAdd() {
		MODE_TYPE = CommonConstants.MODE_TYPE_ADD;
		itemConvertVO = new ItemConvertVO();
		itemConvertVO.setConvertDate(new Date());

		if (userSession.getCompanyId() != null) {
			itemConvertVO.setCompanyId(userSession.getCompanyId());
		}

		itemConvertVO.getListItemConvertDtlVO().clear();
	}

	// public void modeEdit(List<ItemConvertVO> voList) {
	// MODE_TYPE = CommonConstants.MODE_TYPE_EDIT;
	// for (int i = 0; i < voList.size(); i++) {
	// itemConvertVO = (ItemConvertVO) voList.get(i);
	// }
	//
	// List<ItemConvertDtlVO> listItemConvertDtlVO = itemConvertDtlService
	// .getListItemConvertDtlVO(itemConvertVO.getConvertId());
	// itemConvertVO.setListItemConvertDtlVO(listItemConvertDtlVO);
	// }

	public void modeView(List<ItemConvertVO> voList) {
		MODE_TYPE = CommonConstants.MODE_TYPE_VIEW;
		for (int i = 0; i < voList.size(); i++) {
			itemConvertVO = (ItemConvertVO) voList.get(i);
		}

		List<ItemConvertDtlVO> listItemConvertDtlVO = itemConvertDtlService
				.getListItemConvertDtlVO(itemConvertVO.getConvertId());
		itemConvertVO.setListItemConvertDtlVO(listItemConvertDtlVO);
	}

	public void companyOnChange() {
		// clear value
		itemConvertVO.setItemId(null);
		itemConvertVO.setItemName(null);

		// clear detail list
		itemConvertVO.getListItemConvertDtlVO().clear();

		// update drop down value item composite
		if (itemCompositeSelectItem != null) {
			itemCompositeSelectItem.clear();
		} else {
			itemCompositeSelectItem = new ArrayList<SelectItem>();
		}
		List<ItemVO> itemVoList = itemService
				.searchItemCompositeListByCompany(itemConvertVO.getCompanyId());
		for (ItemVO vo : itemVoList) {
			itemCompositeSelectItem.add(new SelectItem(vo.getItemId(), vo
					.getItemName()));
		}
	}

	public void itemQtyOnChange() {
		this.itemCompositeOnChange();
	}

	public void itemCompositeOnChange() {
		List<ItemBomVO> itemBomVoList = itemService
				.searchItemBomList(itemConvertVO.getItemId());

		Double amount = itemConvertVO.getItemQty() != null ? itemConvertVO
				.getItemQty() : 0;
		// update list detail
		itemConvertVO.getListItemConvertDtlVO().clear();
		for (ItemBomVO itemBomVo : itemBomVoList) {
			ItemConvertDtlVO voDtl = new ItemConvertDtlVO();

			voDtl.setItemId(itemBomVo.getItemCompositionId());
			voDtl.setItemCode(itemBomVo.getItemCompositionCode());
			voDtl.setItemName(itemBomVo.getItemCompositionName());
			voDtl.setItemQty(itemBomVo.getItemQty() * amount);

			Item item = itemService.findById(voDtl.getItemId());
			if (item != null && item.getUm() != null) {
				voDtl.setUmId(item.getUm().getUmId());
				voDtl.setUmName(item.getUm().getUmName());
			}

			ItemStockVO itemStockVo = itemStockService
					.getItemStockByCompanyIdOutletIdAndItemId(
							itemConvertVO.getCompanyId(), null,
							voDtl.getItemId());

			if (itemStockVo != null) {
				voDtl.setStockQty(itemStockVo.getStockQty());
				voDtl.setRemainQty(voDtl.getStockQty() - voDtl.getItemQty());
			} else {
				voDtl.setStockQty((double) 0);
				voDtl.setRemainQty(0 - voDtl.getItemQty());
			}

			itemConvertVO.getListItemConvertDtlVO().add(voDtl);
		}
	}

	// public void itemCompositeOnSelected() {
	// List<ItemBomVO> itemBomVoList = itemService
	// .searchItemBomList(itemConvertVO.getItemId());
	//
	// // update list detail
	// itemConvertVO.getListItemConvertDtlVO().clear();
	// for (ItemBomVO itemBomVo : itemBomVoList) {
	// ItemConvertDtlVO voDtl = new ItemConvertDtlVO();
	//
	// voDtl.setItemId(itemBomVo.getItemCompositionId());
	// voDtl.setItemCode(itemBomVo.getItemCompositionCode());
	// voDtl.setItemName(itemBomVo.getItemCompositionName());
	// voDtl.setItemQty(itemBomVo.getItemQty());
	//
	// Item item = itemService.findById(voDtl.getItemId());
	// if (item != null && item.getUm() != null) {
	// voDtl.setUmId(item.getUm().getUmId());
	// voDtl.setUmName(item.getUm().getUmName());
	// }
	//
	// ItemStockVO itemStockVo = itemStockService
	// .getItemStockByCompanyIdOutletIdAndItemId(
	// itemConvertVO.getCompanyId(), null,
	// voDtl.getItemId());
	//
	// if (itemStockVo != null) {
	// voDtl.setStockQty(itemStockVo.getStockQty());
	// voDtl.setRemainQty(voDtl.getStockQty() - voDtl.getItemQty());
	// } else {
	// voDtl.setStockQty((double) 0);
	// voDtl.setRemainQty(0 - voDtl.getItemQty());
	// }
	//
	// itemConvertVO.getListItemConvertDtlVO().add(voDtl);
	// }
	// }

	// public List<SelectItem> itemCompositeOnComplete(String autoCompleteText)
	// {
	// List<SelectItem> resultList = new ArrayList<SelectItem>();
	// for (SelectItem obj : itemCompositeSelectItem) {
	// if (obj.getLabel().toUpperCase()
	// .contains(autoCompleteText.toUpperCase())) {
	// resultList.add(obj);
	// }
	// }
	//
	// return resultList;
	// }

	public void save() {
		try 
		{
			if (MODE_TYPE.equals(CommonConstants.MODE_TYPE_ADD)) 
			{
				itemConvertService.save(itemConvertVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			} 
			else if (MODE_TYPE.equals(CommonConstants.MODE_TYPE_EDIT)) 
			{
				itemConvertService.update(itemConvertVO, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);	  
		}	
	}

	public boolean validateCompany() {
		boolean valid = true;
		if (userSession.getCompanyId() == null) {
			if (itemConvertVO.getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItemConvert",
						facesUtils.getResourceBundleStringValue("formItemConvertCompany") + " "
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
	
	
	public boolean validate() {
		boolean valid = true;

		List<ItemConvertDtlVO> voDtlList = itemConvertVO
				.getListItemConvertDtlVO();
		if (voDtlList != null) {
			for (int i = 0; i < voDtlList.size(); i++) {
				ItemConvertDtlVO voDtl = voDtlList.get(i);

				ItemStockVO itemStockVo = itemStockService
						.getItemStockByCompanyIdOutletIdAndItemId(
								itemConvertVO.getCompanyId(), null,
								voDtl.getItemId());

				if (itemStockVo != null) {
					voDtl.setStockQty(itemStockVo.getStockQty());
					voDtl.setRemainQty(voDtl.getStockQty() - voDtl.getItemQty());
				} else {
					voDtl.setStockQty((double) 0);
					voDtl.setRemainQty(0 - voDtl.getItemQty());
				}

				// validate remain qty should not less than zero
				if (voDtl.getRemainQty().doubleValue() < 0) {
					facesUtils
							.addFacesMessage(
									"frm001:dataTableDtl",
									FacesMessage.SEVERITY_ERROR,
									null,
									String.format(
											"%s %s",
											voDtl.getItemName(),
											facesUtils
													.getResourceBundleStringValue("formItemConvertErrMsgRemainLessThanZero")));

					valid = false;
				}
			}
		}

		if (itemConvertVO.getItemId() == null) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItemConvert",
					facesUtils.getResourceBundleStringValue("formItemConvertItemComposite") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		} if (itemConvertVO.getItemQty()== null) {

			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItemConvert",
					facesUtils.getResourceBundleStringValue("formItemConvertItemQty") + " "
							+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
			valid = false;

		} 
		
		return valid;
	}

	public boolean isAdminMode() {
		return userSession.getCompanyId() == null;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ItemConvertInputBean.logger = logger;
	}

	public ItemConvertService getItemConvertService() {
		return itemConvertService;
	}

	public void setItemConvertService(ItemConvertService itemConvertService) {
		this.itemConvertService = itemConvertService;
	}

	public ItemConvertVO getItemConvertVO() {
		return itemConvertVO;
	}

	public void setItemConvertVO(ItemConvertVO itemConvertVO) {
		this.itemConvertVO = itemConvertVO;
	}

	public ItemConvertDtlService getItemConvertDtlService() {
		return itemConvertDtlService;
	}

	public void setItemConvertDtlService(
			ItemConvertDtlService itemConvertDtlService) {
		this.itemConvertDtlService = itemConvertDtlService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public ItemStockService getItemStockService() {
		return itemStockService;
	}

	public void setItemStockService(ItemStockService itemStockService) {
		this.itemStockService = itemStockService;
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

	public List<SelectItem> getItemCompositeSelectItem() {
		return itemCompositeSelectItem;
	}

	public void setItemCompositeSelectItem(
			List<SelectItem> itemCompositeSelectItem) {
		this.itemCompositeSelectItem = itemCompositeSelectItem;
	}

	public String getSearchAllDialog() {
		return searchAllDialog;
	}

	public void setSearchAllDialog(String searchAllDialog) {
		this.searchAllDialog = searchAllDialog;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

}