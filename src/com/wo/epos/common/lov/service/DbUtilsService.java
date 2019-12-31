/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.service;

//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import com.wo.epos.common.lov.util.StringUtils;

//import javax.persistence.Id;

//import com.whiteopen.common.util.StringUtils;

/**
 *
 * @author destri.hs
 */
public interface DbUtilsService {
    Object hqlUniqueResult(String hql, Map <String, Object> params);
    Object sqlUniqueResult(String sql, Map <String, Object> params);

    List hqlResults(String hql, Integer first, Integer pageSize, Map <String, Object> params);
    List sqlResults(String sql, Integer first, Integer pageSize, Map <String, Object> params);
    
    String getStringDescFromEntity(Object entity, StringUtils stringUtils)
    		throws NoSuchMethodException, InvocationTargetException, IllegalAccessException ;
    ConvertedResult getStringDescFromEntityId(Class persistentClass, Object id, StringUtils stringUtils)
    		throws NoSuchMethodException, InvocationTargetException, IllegalAccessException ;
    

	Object getEntityId(Object entity) throws NoSuchMethodException,
		InvocationTargetException, IllegalAccessException;

    
    public static class ConvertedResult {
    	public Object entity;
    	public String entityDesc;
	}

}
