/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.primefaces.model.SortOrder;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.lov.bean.SelectorModel;
import com.wo.epos.common.vo.SearchObject;


/**
 *
 * @author destri.hs
 */
@ManagedBean(name = "selectorDao")
@ViewScoped
public class SelectorDaoImpl extends GenericDAOHibernate<Object, Serializable> 
    implements SelectorDao {
    private static Logger logger = Logger.getLogger(SelectorDaoImpl.class);
    
    @Override
    public List searchData(List <? extends SearchObject> searchCriteria, int first, int pageSize, String sortField, SortOrder sortOrder) {
        String hqlStr = "";
        for (SearchObject so : searchCriteria) {
            if (StringUtils.equals(so.getSearchColumn(), SelectorModel.SEARCH_COL_QUERY_ALL)) {
                hqlStr = so.getSearchValueAsString();
            }
        }        
        
        Query query = getSession().createQuery(hqlStr);
        if (first >= 0 && pageSize > 0) {
            query.setFirstResult(first);
            query.setMaxResults(pageSize);            
        } else {
            //won't page anything, return all
        }
        try {
            List result = 
                    query.list();         
            return result;
            
        } catch (Exception ex) {
            logger.error("Error querying search data lov [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying search data lov [" + ex.getMessage() + "]", ex);
        }
    }

    @Override
    public Long searchCountData(List <? extends SearchObject> searchCriteria) {
        String hqlStr = "";
        for (SearchObject so : searchCriteria) {
            if (StringUtils.equals(so.getSearchColumn(), SelectorModel.SEARCH_COL_QUERY_ALL_COUNT)) {
                hqlStr = so.getSearchValueAsString();
            }
        }
        
        Query query = getSession().createQuery(hqlStr);
 
        try {
            Number res = (Number) query.uniqueResult();        
            return res.longValue();
        } catch (Exception ex) {
            logger.error("Error querying search count data lov [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying search count data lov [" + ex.getMessage() + "]", ex);
        }
    }
    
    @Override
    public List searchDataUsingNative(List <? extends SearchObject> searchCriteria, int first, int pageSize, String sortField, SortOrder sortOrder) {
        String nativeQueryStr = "";
        for (SearchObject so : searchCriteria) {
            if (StringUtils.equals(so.getSearchColumn(), SelectorModel.SEARCH_COL_QUERY_ALL)) {
                nativeQueryStr = so.getSearchValueAsString();
            }
        }        
        
        SQLQuery query = getSession().createSQLQuery(nativeQueryStr);
        if (first >= 0 && pageSize > 0) {
            query.setFirstResult(first);
            query.setMaxResults(pageSize);            
        } else {
            //won't page anything, return all
        }
        
        try {
            return query.list(); 
        } catch (Exception ex) {
            logger.error("Error querying search data native lov [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying search data native lov [" + ex.getMessage() + "]", ex);
        }
    }

    @Override
    public Long searchCountDataUsingNative(List <? extends SearchObject> searchCriteria) {
        String nativeQueryStr = "";
        for (SearchObject so : searchCriteria) {
            if (StringUtils.equals(so.getSearchColumn(), SelectorModel.SEARCH_COL_QUERY_ALL_COUNT)) {
                nativeQueryStr = so.getSearchValueAsString();
            }
        }
        
        SQLQuery query = getSession().createSQLQuery(nativeQueryStr);
 
        try {
            Number res = (Number) query.uniqueResult();        
            return res.longValue();
        } catch (Exception ex) {
            logger.error("Error querying search count data native lov [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying search count data native lov [" + ex.getMessage() + "]", ex);
        }
    }
    
}
