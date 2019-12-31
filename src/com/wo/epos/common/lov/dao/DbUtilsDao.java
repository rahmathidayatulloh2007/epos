/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.wo.epos.common.lov.service.DbUtilsService.ConvertedResult;
import com.wo.epos.common.lov.util.StringUtils;

/**
 *
 * @author destri.hs
 */
public interface DbUtilsDao { 
    int sqlExecute(String sql, Map <String, Object> params);
	
    Object hqlUniqueResult(String hql, Map <String, Object> params);
    Object sqlUniqueResult(String sql, Map <String, Object> params);

    List hqlResults(String hql, Integer first, Integer pageSize, Map <String, Object> params);
    List sqlResults(String sql, Integer first, Integer pageSize, Map <String, Object> params);

    public ConvertedResult getStringDescFromEntityId(Class persistentClass, Object id, StringUtils stringUtils) 
    		throws NoSuchMethodException, InvocationTargetException, IllegalAccessException ;

    public String getStringDescFromEntity(Object id, Object entity, StringUtils stringUtils) 
    		throws NoSuchMethodException, InvocationTargetException, IllegalAccessException ;    
}
