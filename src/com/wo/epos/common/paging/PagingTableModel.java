package com.wo.epos.common.paging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.wo.epos.common.vo.SearchObject;

public class PagingTableModel<E> extends LazyDataModel<E> {

	private static final long serialVersionUID = -5872869494149656422L;
	private RetrieverDataPage<E> retrieverData;
	@SuppressWarnings("rawtypes")
	private List<? extends SearchObject> searchCriteria;
	static Logger logger = Logger.getLogger(PagingTableModel.class);

	public PagingTableModel(RetrieverDataPage<E> retrieverData, int pageSize) {
		this.retrieverData = retrieverData;
		setPageSize(pageSize);
		updateRowCount();
	}

	@SuppressWarnings("rawtypes")
	public void setSearchCriteria(List<? extends SearchObject> searchCriteria) {
		this.searchCriteria = searchCriteria;
		updateRowCount();

	}

	@SuppressWarnings("rawtypes")
	public List<? extends SearchObject> getSearchCriteria() {
		return searchCriteria;
	}

	@Override
	public E getRowData(String rowKey) {
		/*
		 * for(E obj : this.retrieverData) { if(obj.getModel().equals(rowKey))
		 * return car; }
		 */
		return null;
	}

	public void updateRowCount() {
		/*if (searchCriteria == null)
			setRowCount(0);
		else {*/
			try {

				Long totalRowCount = retrieverData
						.searchCountData(searchCriteria);

				setRowCount(totalRowCount.intValue());
			} catch (Exception ex) {
				logger.debug(
						"Exception while searching row count, use 0 as result",
						ex);
				setRowCount(0);
			}
		//}

	}

	@Override
	public List<E> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		List<E> results = new ArrayList<E>();
		try {
			if (StringUtils.isNotEmpty(sortField)) {
				if (sortOrder.name().equalsIgnoreCase("ASCENDING"))
					results = retrieverData.searchData(searchCriteria, first,
							pageSize, sortField, true);
				else
					results = retrieverData.searchData(searchCriteria, first,
							pageSize, sortField, false);
			} else {
				results = retrieverData.searchData(searchCriteria, first,
						pageSize, sortField, false);
			}

			if (results != null && results.size() == 0 && first >= 10) {
				return this.load(0, pageSize, sortField, sortOrder, filters);
			}

			// filter - filter has already been on Hibernate level so the page
			// level filter is only
			// for retrieved data filter
			if (filters != null && !filters.isEmpty()) {
				for (E obj : results) {
					boolean match = true;

					for (Iterator<String> it = filters.keySet().iterator(); it
							.hasNext();) {
						try {
							String filterProperty = it.next();
							String filterValue = (String) filters
									.get(filterProperty);
							String fieldValue = String.valueOf(obj.getClass()
									.getField(filterProperty).get(obj));

							if (filterValue == null
									|| fieldValue.startsWith(filterValue)) {
								match = true;
							} else {
								match = false;
								break;
							}
						} catch (Exception e) {
							match = false;
						}
					}

					if (!match) {
						// remove from list if not found
						results.remove(obj);
					}
				}
			}

			// sort
			// sort has been done in the query, so this sort is redundant
			if (sortField != null && results != null && sortOrder != null) {
				// Collections.sort(results, new LazySorter<E>(sortField,
				// sortOrder));
			}

			setWrappedData(results);
			updateRowCount();

			if (results != null) {
				// rowCount
		int dataSize = results.size();

				// paginate
				if (dataSize > pageSize) {
					try {
						return results.subList(first, first + pageSize);
					} catch (IndexOutOfBoundsException e) {
						return results.subList(first, first
								+ (dataSize % pageSize));
					}
				} else {
					return results;
				}
			}
			return results;
		} catch (Exception e) {
			logger.debug(
					"Exception while trying search param detail, returning empty list",
					e);
			return new ArrayList<E>();
		}
	}
}
