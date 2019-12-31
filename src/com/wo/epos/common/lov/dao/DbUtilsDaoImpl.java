/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.dao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.lov.service.DbUtilsService.ConvertedResult;
import com.wo.epos.common.lov.util.StringUtils;


/**
 *
 * @author destri.hs
 */ 

@ManagedBean(name = "dbUtilsDao")
@ViewScoped
public class DbUtilsDaoImpl 
    extends GenericDAOHibernate<Object, Serializable> implements DbUtilsDao {
    private static Logger logger = Logger.getLogger(DbUtilsDaoImpl.class);

    @Override
    public int sqlExecute(String sql, Map<String, Object> params) {
		SQLQuery query = getSession().createSQLQuery(
				sql
				);
		
        for (Map.Entry<String, Object> entry : params.entrySet()) {            
            query.setParameter(entry.getKey(), entry.getValue());            
        }
		
		return query.executeUpdate();				
    	
    }
    
    @Override
    public List hqlResults(String hql, 
            Integer first, Integer pageSize,
            Map <String, Object> params) {
        
        Query query = getSession().createQuery(hql);
        
        for (Map.Entry<String, Object> entry : params.entrySet()) {            
            query.setParameter(entry.getKey(), entry.getValue());            
        }
        
        if (first != null) {
            query.setFirstResult(first);
        }
        if (pageSize != null) {
            query.setMaxResults(pageSize);            
        }
        
        try {
            List result = 
                    query.list();         
            return result;
            
        } catch (Exception ex) {
            logger.error("Error querying hql data [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying hql data [" + ex.getMessage() + "]", ex);
        }
    }

    @Override
    public List sqlResults(String sql, Integer first, Integer pageSize, Map <String, Object> params) {
        
        
        SQLQuery query = getSession().createSQLQuery(sql);
        if (first != null) {
            query.setFirstResult(first);            
        }
        if (pageSize != null) {
            query.setMaxResults(pageSize);            
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {            
            query.setParameter(entry.getKey(), entry.getValue());            
        }
        
        try {
            return query.list(); 
        } catch (Exception ex) {
            logger.error("Error querying search sql data [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying sql data [" + ex.getMessage() + "]", ex);
        }
        
    }

    
    @Override
    public Object hqlUniqueResult(String hql, Map <String, Object> params) {        
        Query query = getSession().createQuery(hql);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());            
        }
 
        try {
            Number res = (Number) query.uniqueResult();        
            return res.longValue();
        } catch (Exception ex) {
            logger.error("Error querying unique result hql [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying unique result hql [" + ex.getMessage() + "]", ex);
        }
    }

    @Override
    public Object sqlUniqueResult(String sql, Map <String, Object> params) {
        
        SQLQuery query = getSession().createSQLQuery(sql);
        
        for (Map.Entry<String, Object> entry : params.entrySet()) {            
            query.setParameter(entry.getKey(), entry.getValue());            
        }
 
        try {
            Object res = query.uniqueResult();
            if (res instanceof Number) {
                return ((Number)res).longValue();
            } else {
                return res;
            }
        } catch (Exception ex) {
            logger.error("Error querying unique result sql [" + ex.getMessage() + "]", ex);
            throw new RuntimeException("Error querying unique result sql [" + ex.getMessage() + "]", ex);
        }
        
    }

    public ConvertedResult getStringDescFromEntityId(Class persistentClass, Object id, StringUtils stringUtils) 
    		throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		if (id != null) {
			Session session = getSession();
			Object dbEntity = session.get(persistentClass, (Serializable) id);
//			session.refresh(dbEntity);
			try {
				String result = stringUtils
						.convertEntityToString(dbEntity, dbEntity);
				if (dbEntity != null) {
					session.evict(dbEntity);
				}
				ConvertedResult cres = new ConvertedResult();
				cres.entity = dbEntity;
				cres.entityDesc = result;
				return cres;
				
			} catch (Exception ex) {
				if (dbEntity != null) {
					session.evict(dbEntity);
				}
				
				throw new RuntimeException(ex);
			}

		} else {
			return null;
		}
    }

    @Override
    public String getStringDescFromEntity(Object entityId, Object entity, StringUtils stringUtils) 
    		throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    	
		if (entity != null) {
			Session session = getSession();
			Object dbEntity = session.get(entity.getClass(), (Serializable) entityId);
//			Object mergedEditedEntity = (Object) session.merge(entity);
//			session.setReadOnly(mergedEditedEntity, true);
			try {
				String strEditedEntity = stringUtils
						.convertEntityToString(entity, dbEntity);
				
				session.evict(entity);
				session.evict(dbEntity);
				return strEditedEntity;
				
			} catch (Exception ex) {
				if (dbEntity != null) {
					session.evict(dbEntity); 
				}
				if (entity != null) {
					session.evict(entity);
				}
				
				throw new RuntimeException(ex);
			}
		} else {
			return null;
		}
    	
    }
    

    
}
