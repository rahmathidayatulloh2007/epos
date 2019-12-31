/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.dao;

import java.util.List;

import org.primefaces.model.SortOrder;

import com.wo.epos.common.vo.SearchObject;



/**
 *
 * @author destri.hs
 */
public interface SelectorDao {
    
    public List searchData(
            List <? extends SearchObject> searchCriteria, int first, int pageSize, String sortField, SortOrder sortOrder);
    public Long searchCountData(List <? extends SearchObject> searchCriteria);

    public List searchDataUsingNative(
            List <? extends SearchObject> searchCriteria, int first, int pageSize, String sortField, SortOrder sortOrder);
    public Long searchCountDataUsingNative(List <? extends SearchObject> searchCriteria);
}
