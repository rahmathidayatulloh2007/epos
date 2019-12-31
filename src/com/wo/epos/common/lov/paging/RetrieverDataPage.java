package com.wo.epos.common.lov.paging;

import java.sql.SQLException;
import java.util.List;
import org.primefaces.model.SortOrder;

import com.wo.epos.common.vo.SearchObject;

public interface RetrieverDataPage <E>{

    List<E> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
                    String sortField, SortOrder sortOrder) throws Exception ;

    Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception;
 
}
