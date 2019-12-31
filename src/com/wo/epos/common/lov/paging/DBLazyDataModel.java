/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.paging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.wo.epos.common.vo.SearchObject;

/**
 *
 * @author destri.hs
 */
public class DBLazyDataModel <E> extends LazyDataModel<E> {

    private static final long serialVersionUID = -5872869494149656422L;
    private RetrieverDataPage <E> retrieverData;
    private List<? extends SearchObject> searchCriteria;
    static Logger logger = Logger.getLogger(DBLazyDataModel.class);

    public DBLazyDataModel(            
            RetrieverDataPage <E> retrieverData, 
            List<? extends SearchObject> searchCriteria,
            int pageSize) {
        this.retrieverData = retrieverData;
        setPageSize(pageSize);
        setSearchCriteria(searchCriteria);
//        updateRowCount();
        
    }
    
    public void resetPage(String compId) {
        DataTable dataTable = (DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent(compId);
        dataTable.setFirst(0);
    }

    public DBLazyDataModel(RetrieverDataPage <E> retrieverData, int pageSize) {
        this(retrieverData, null, pageSize);
    }

    public void setSearchCriteria(List<? extends SearchObject> searchCriteria) {
        this.searchCriteria = searchCriteria;
//            updateRowCount();

    }

    public List<? extends SearchObject> getSearchCriteria() {
        return searchCriteria;
    }

    public void updateRowCount() {
        try {
            Long totalRowCount = retrieverData.searchCountData(searchCriteria);
            
            setRowCount(totalRowCount.intValue());
        } catch (Exception ex) {
            logger.debug("Exception while searching row count, use 0 as result", ex);
            setRowCount(0);
        }
    }
    
    public Integer findIndexOfInstanceFromLastQueryData(E obj) {
        Iterator <E> iter = iterator();
        for (int i = 0, sum = getPageSize() ; i < sum ; i++) {
            E item = iter.next();
            
            if (item == obj) {
                return i;
            }
        }
        
        return -1;
    }
    
    
    @Override
    public List<E> load(int first, int pageSize, String sortField,
    		SortOrder sortOrder, Map<String, Object> filters) {
    	// TODO Auto-generated method stub
    	 try {
      		updateRowCount();
      		
  			first = CustomDataTableRenderer.getAdjustedFirstData(first,
  					pageSize, getRowCount());
             List<E> results = retrieverData.searchData( 
                              searchCriteria, first, pageSize, sortField, sortOrder);

              setWrappedData(results);
              
              return results;
          } catch (Exception e) {
              logger.debug("Exception while trying search param detail, returning empty list", e);
              return new ArrayList<E>();
          }
    }
   

    

    public RetrieverDataPage<E> getRetrieverData() {
        return retrieverData;
    }
    
}