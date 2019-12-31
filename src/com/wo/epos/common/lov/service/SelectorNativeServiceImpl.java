/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.service;



import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.SortOrder;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;*/

import com.wo.epos.common.lov.dao.SelectorDao;

/**
 *
 * @author destri.hs
 */
//@Service("selectorNativeService")
@ManagedBean(name="selectorNativeService")
@ViewScoped
public class SelectorNativeServiceImpl  implements SelectorNativeService {
    /*@Autowired
    @Qualifier("selectorDao")
    private SelectorDao selectorDao;*/
    
    @ManagedProperty(value="#{selectorDao}")
	private SelectorDao selectorDao;
    

    
    //@Override
    //@Transactional(readOnly=true)
    public List searchData(List searchCriteria, int first, int pageSize, String sortField, SortOrder sortOrder) throws Exception {
        return selectorDao.searchDataUsingNative(searchCriteria, first, pageSize, sortField, sortOrder);
    }

    //@Override
    //@Transactional(readOnly=true)
    public Long searchCountData(List  searchCriteria) throws Exception {
        return selectorDao.searchCountDataUsingNative(searchCriteria);
    }
    
    public void setSelectorDao(SelectorDao selectorDao) {
        this.selectorDao = selectorDao;
    }
    
}
