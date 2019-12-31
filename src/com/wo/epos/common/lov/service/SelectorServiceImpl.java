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

import com.wo.epos.common.lov.dao.SelectorDao;

/**
 *
 * @author destri.hs
 */
//@Service("selectorService")
@ManagedBean(name="selectorService")
@ViewScoped
public class SelectorServiceImpl implements SelectorService {
    /*@Autowired
    @Qualifier("selectorDao")
    private SelectorDao selectorDao;*/
    
    @ManagedProperty(value="#{selectorDao}")
	private SelectorDao selectorDao;

    
    //@Override
    //@Transactional(readOnly=true)
    public List searchData(List searchCriteria, int first, int pageSize, String sortField, SortOrder sortOrder) throws Exception {
        return selectorDao.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
    }

    //@Override
    //@Transactional(readOnly=true)
    public Long searchCountData(List  searchCriteria) throws Exception {
        return selectorDao.searchCountData(searchCriteria);
    }
    
    public void setSelectorDao(SelectorDao selectorDao) {
        this.selectorDao = selectorDao;
    }
    
        
}
