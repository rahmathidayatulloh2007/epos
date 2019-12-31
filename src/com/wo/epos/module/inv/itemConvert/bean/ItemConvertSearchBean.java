package com.wo.epos.module.inv.itemConvert.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.itemConvert.service.ItemConvertService;
import com.wo.epos.module.inv.itemConvert.vo.ItemConvertVO;

@ManagedBean(name = "itemConvertSearchBean")
@ViewScoped
public class ItemConvertSearchBean extends CommonBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3528141857228781815L;

	static Logger logger = Logger.getLogger(ItemConvertSearchBean.class);

	@ManagedProperty(value = "#{itemConvertService}")
	private ItemConvertService itemConvertService;

	private ItemConvertVO itemConvertVOSearchDialog = new ItemConvertVO();

	private PagingTableModel<ItemConvertVO> pagingItemConvert;

	private List<ItemConvertVO> selectedItemConvert;

	private boolean selectAll;
	private boolean disableSearchAll;

	private String searchAll;

	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			itemConvertVOSearchDialog = new ItemConvertVO();

			pagingItemConvert = new PagingTableModel(itemConvertService, paging);
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if (userSession.getCompanyId() != null) {
				searchCriteria.add(new SearchValueObject("company", userSession.getCompanyId()));

				// if (userSession.getOutletId() != null) {
				// searchCriteria.add(new SearchValueObject("outletLogin",
				// userSession.getOutletId()));
				// }

				pagingItemConvert.setSearchCriteria(searchCriteria);
			}

			disableSearchAll = false;

			checkSearch = 0;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search() {
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();

		if (checkSearch == 0) {
			if (searchAll != null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria
						.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}

			if (userSession.getCompanyId() != null) {
				searchCriteria.add(new SearchValueObject("company", userSession
						.getCompanyId()));

				// if (userSession.getOutletId() != null) {
				// searchCriteria.add(new SearchValueObject("outletLogin",
				// userSession.getOutletId()));
				// }

				// pagingItemConvert.setSearchCriteria(searchCriteria);
			}

			disableSearchAll = false;
			pagingItemConvert.setSearchCriteria(searchCriteria);
		} else {
			searchDialog();
		}

	}

	public void modeDelete(List<ItemConvertVO> voList) {
		for (int i = 0; i < voList.size(); i++) {
			ItemConvertVO vo = (ItemConvertVO) voList.get(i);

			itemConvertService.delete(vo.getConvertId());
		}
	}

	public void clear() {
		searchAll = "";
		itemConvertVOSearchDialog = new ItemConvertVO();
		checkSearch = 0;
		search();
	}

	public void clearDialog() {
		searchAll = "";
		checkSearch = 0;
		itemConvertVOSearchDialog = new ItemConvertVO();
		search();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog() {
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();

		if (userSession.getCompanyId() != null) {
			searchCriteria.add(new SearchValueObject("company", userSession
					.getCompanyId()));

			// if (userSession.getOutletId() != null) {
			// searchCriteria.add(new SearchValueObject("outletLogin",
			// userSession.getOutletId()));
			// }

			pagingItemConvert.setSearchCriteria(searchCriteria);
		}

		if (itemConvertVOSearchDialog.getConvertNo() != null
				&& StringUtils.isNotBlank(itemConvertVOSearchDialog
						.getConvertNo())) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formItemConvertConvertNo"));
			builder.append(":" + itemConvertVOSearchDialog.getConvertNo() + ",");
			searchCriteria.add(new SearchValueObject("convertNo",
					itemConvertVOSearchDialog.getConvertNo()));
		}

		if (itemConvertVOSearchDialog.getStartDate() != null
				&& itemConvertVOSearchDialog.getEndDate() != null) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":"
					+ sdf.format(itemConvertVOSearchDialog.getStartDate()));
			builder.append(" s/d "
					+ sdf.format(itemConvertVOSearchDialog.getEndDate()) + ",");
			searchCriteria.add(new SearchValueObject("startDate",
					itemConvertVOSearchDialog.getStartDate()));
			searchCriteria.add(new SearchValueObject("endDate",
					itemConvertVOSearchDialog.getEndDate()));
		} else if (itemConvertVOSearchDialog.getStartDate() == null
				&& itemConvertVOSearchDialog.getEndDate() != null) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":-");
			builder.append(" s/d "
					+ sdf.format(itemConvertVOSearchDialog.getEndDate()) + ",");
			searchCriteria.add(new SearchValueObject("endDate",
					itemConvertVOSearchDialog.getEndDate()));
		} else if (itemConvertVOSearchDialog.getStartDate() != null
				&& itemConvertVOSearchDialog.getEndDate() == null) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formCompanyReqisterOn"));
			builder.append(":"
					+ sdf.format(itemConvertVOSearchDialog.getStartDate()));
			builder.append(" s/d -" + ",");
		}

		if (itemConvertVOSearchDialog.getItemName() != null
				&& StringUtils.isNotBlank(itemConvertVOSearchDialog
						.getItemName())) {
			builder.append(facesUtils
					.getResourceBundleStringValue("formItemConvertItem"));
			builder.append(":" + itemConvertVOSearchDialog.getItemName() + ",");
			searchCriteria.add(new SearchValueObject("itemName",
					itemConvertVOSearchDialog.getItemName()));
		}

		if (!builder.toString().equals("")) {
			builder = builder.replace(builder.toString().lastIndexOf(","),
					builder.toString().lastIndexOf(",") + 1, "");
		}

		searchAll = builder.toString();

		pagingItemConvert.setSearchCriteria(searchCriteria);

	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ItemConvertSearchBean.logger = logger;
	}

	public ItemConvertService getItemConvertService() {
		return itemConvertService;
	}

	public void setItemConvertService(ItemConvertService itemConvertService) {
		this.itemConvertService = itemConvertService;
	}

	public ItemConvertVO getItemConvertVOSearchDialog() {
		return itemConvertVOSearchDialog;
	}

	public void setItemConvertVOSearchDialog(
			ItemConvertVO itemConvertVOSearchDialog) {
		this.itemConvertVOSearchDialog = itemConvertVOSearchDialog;
	}

	public PagingTableModel<ItemConvertVO> getPagingItemConvert() {
		return pagingItemConvert;
	}

	public void setPagingItemConvert(
			PagingTableModel<ItemConvertVO> pagingItemConvert) {
		this.pagingItemConvert = pagingItemConvert;
	}

	public List<ItemConvertVO> getSelectedItemConvert() {
		return selectedItemConvert;
	}

	public void setSelectedItemConvert(List<ItemConvertVO> selectedItemConvert) {
		this.selectedItemConvert = selectedItemConvert;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}

}