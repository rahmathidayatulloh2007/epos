package com.wo.epos.common.paging;

import java.util.List;

import com.wo.epos.common.vo.SearchObject;

public interface RetrieverDataPage<E> {

	@SuppressWarnings("rawtypes")
	List<E> searchData(List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception;

	@SuppressWarnings("rawtypes")
	Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception;

}
