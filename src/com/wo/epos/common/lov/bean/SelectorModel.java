/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.bean;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;



import com.wo.epos.common.lov.converter.ItemToDisplayValueConverter;
import com.wo.epos.common.lov.paging.DBLazyDataModel;
import com.wo.epos.common.lov.paging.DefaultSearchObject;
import com.wo.epos.common.lov.paging.RetrieverDataPage;
import com.wo.epos.common.vo.SearchObject;



/**
 *
 * @author destri.hs
 */
public class SelectorModel {
    public static final String SEARCH_COL_QUERY_ALL = "ALL";
    public static final String SEARCH_COL_QUERY_ALL_COUNT = "ALL_COUNT";
    
    private CountLimit multipleCountLimit;
    private String jsOnSelected;
    private String jsFunctionOnSelected;
    private String userQueryString;
    private SelectorInfo selectorInfo;
    private String clientId;
    List<ColumnModel> columnModels;
    
    private DBLazyDataModel dataModel;

    public SelectorModel(            
            RetrieverDataPage retriever,
            SelectorInfo selectorInfo, 
            String clientId,
            int pageSize) {
        this(retriever, selectorInfo, clientId, pageSize, new CountLimit());
    }

    public SelectorModel(            
            RetrieverDataPage retriever,
            SelectorInfo selectorInfo, 
            String clientId,
            int pageSize,
            CountLimit countLimit) {
        this.multipleCountLimit = countLimit;
        this.selectorInfo = selectorInfo;
        this.clientId = clientId;
        this.dataModel = new DBLazyDataModel(retriever, 
                Arrays.asList(
                new DefaultSearchObject(SEARCH_COL_QUERY_ALL, selectorInfo.getQueryFilledWithUserQuery("")),
                new DefaultSearchObject(SEARCH_COL_QUERY_ALL_COUNT, selectorInfo.getQueryCountFilledWithUserQuery(""))                
                ),
                pageSize);
        initColumnModels();
    }
    
    private void initColumnModels() {
        List<ColumnModel> colModels = new ArrayList<ColumnModel>();
        
        List<String> colProps = selectorInfo.getColumnProp();
        List<String> colHeaders = selectorInfo.getColumns();
        for (int i = 0 ; i < colProps.size() ; i++) {
            if (colHeaders.size() >= i) {
                colModels.add(new ColumnModel(
                    colHeaders.get(i), colProps.get(i)));
            } else {
                colModels.add(new ColumnModel(
                    "Column_" + i, colProps.get(i)));
            }
        }
        
        columnModels = colModels;

    }
    
    public void search(String userQuery) {
        this.userQueryString = userQuery;
        dataModel.setSearchCriteria(
                Arrays.asList(
                buildQueryDataSearchObject(userQuery),
                buildQueryCountSearchObject(userQuery)));
    }
    
    public SearchObject buildQueryCountSearchObject(String userQuery) {
        return new DefaultSearchObject(
                    SEARCH_COL_QUERY_ALL_COUNT, 
                    selectorInfo.getQueryCountFilledWithUserQuery(userQuery)
                );
    }
    
    public SearchObject buildQueryDataSearchObject(String userQuery) {
        return new DefaultSearchObject(
                    SEARCH_COL_QUERY_ALL, 
                    selectorInfo.getQueryFilledWithUserQuery(userQuery)
                );
    }

    public List retrieveAllData() throws Exception {
        RetrieverDataPage retriever = dataModel.getRetrieverData();
        return retriever.searchData(Arrays.asList(
                buildQueryDataSearchObject(userQueryString),
                buildQueryCountSearchObject(userQueryString)), 
                0, -1, null, SortOrder.UNSORTED);
    }
    
    public List<ColumnModel> getColumnModels() {
        return columnModels;
    }
    
    

    public DBLazyDataModel getDataModel() {
        return dataModel;
    }

    public CountLimit getMultipleCountLimit() {
        return multipleCountLimit;
    }

    
    public String getUserQueryString() {
        return userQueryString;
    }

    public String getJsOnSelected() {
        return jsOnSelected;
    }

    public void setJsOnSelected(String jsOnSelected) {
        this.jsOnSelected = jsOnSelected;
    }

    public String getJsFunctionOnSelected() {
        return jsFunctionOnSelected;
    }

    public void setJsFunctionOnSelected(String jsFunctionOnSelected) {
        this.jsFunctionOnSelected = jsFunctionOnSelected;
    }
    
    public String constructJsFunctionParamAware(Object item) {
        if (StringUtils.isNotBlank(jsFunctionOnSelected)) {
            return selectorInfo.constructJsFunctionParamAware(jsFunctionOnSelected, item);
        } else {
            return "";
        }
    }
    
    public static class SelectorInfo implements Cloneable {
        private String dbQuery;
        private String dbQueryCount;
        private List <String> columns;
        private List <String> columnProp;
        private Boolean isHql;
        private SelectedItemConverter converter;
        private List <ItemToDisplayValueConverter> listOfConverter;

        public SelectorInfo(
                String dbQuery, 
                String dbQueryCount,
                List<String> columns, List<String> columnProp) {
            this(dbQuery, dbQueryCount, columns, columnProp, true);
        }
        
        public SelectorInfo(
                String dbQuery, 
                String dbQueryCount,
                List<String> columns, List<String> columnProp,
                Boolean isHql) {
            this(dbQuery, dbQueryCount, columns, columnProp, isHql, new DefaultSelectedItemConverter(columnProp));
        }

        public SelectorInfo(
                String dbQuery, 
                String dbQueryCount,
                List<String> columns, List<String> columnProp,
                Boolean isHql,
                List <ItemToDisplayValueConverter> listOfConverter) {
            this(dbQuery, dbQueryCount, columns, columnProp, isHql, listOfConverter, new DefaultSelectedItemConverter(columnProp));
        }

        public SelectorInfo(
                String dbQuery, 
                String dbQueryCount,
                List<String> columns, List<String> columnProp,
                Boolean isHql, 
                SelectedItemConverter converter) {
        	this(dbQuery, dbQueryCount, columns, columnProp, isHql, null, new DefaultSelectedItemConverter<Object>(columnProp));
        }

        public SelectorInfo(
                String dbQuery, 
                String dbQueryCount,
                List<String> columns, List<String> columnProp,
                Boolean isHql,
                List <ItemToDisplayValueConverter> listOfConverter,
                SelectedItemConverter converter) {
            this.dbQuery = dbQuery;
            this.dbQueryCount = dbQueryCount;
            this.columns = columns;
            this.columnProp = columnProp;
            this.isHql = isHql;
            this.converter = converter;
            this.listOfConverter = listOfConverter;
        }

        public List<ItemToDisplayValueConverter> getListOfConverter() {
			return listOfConverter;
		}
        
        public List<String> getColumns() {
            return columns;
        }

        public List<String> getColumnProp() {
            return columnProp;
        }

        public void setDbQuery(String dbQuery) {
            this.dbQuery = dbQuery;
        }

        public void setDbQueryCount(String dbQueryCount) {
            this.dbQueryCount = dbQueryCount;
        }

        
        public String getDbQuery() {
            return dbQuery;
        }

        public String getDbQueryCount() {
            return dbQueryCount;
        }

        public Boolean getIsHql() {
            return isHql;
        }
        
        String constructJsFunctionParamAware(String jsFunctionOnSelected, Object item) {
            StringBuilder sb = new StringBuilder();
            sb.append(jsFunctionOnSelected)
                    .append("(")
                    .append(converter.convertToJsParam(item))
                    .append(");");
            return sb.toString();            
        }
        
        public String getQueryFilledWithUserQuery(String userQuery) {
            return dbQuery.replace("{0}", userQuery);
        }
        public String getQueryCountFilledWithUserQuery(String userQuery) {
            return dbQueryCount.replace("{0}", userQuery);
        }

		public SelectorInfo clone() {
			try {
				return (SelectorInfo) super.clone();
			} catch(CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}
		}
    }
    
}
