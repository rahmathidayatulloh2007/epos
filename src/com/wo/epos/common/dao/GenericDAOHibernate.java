package com.wo.epos.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wo.epos.common.model.BaseEntity;

/**
 * @author Manimbul Simorangkir
 * @version 1.0
 * @created May, 2009
 */
public abstract class GenericDAOHibernate<T, ID extends Serializable>
		implements GenericDAO<T, ID> {
	public static final String LIKEALL_CHAR = "%";
	private Class<T> persistentClass;

	static Logger logger = Logger.getLogger(GenericDAOHibernate.class);

	@ManagedProperty(value = "#{configSessionFactory}")
	private SessionFactoryConfigBased sessionFactoryConfig;

	// private SessionFactory sessionFactory;

	public void setSessionFactoryConfig(
			SessionFactoryConfigBased sessionFactoryConfig) {
		this.sessionFactoryConfig = sessionFactoryConfig;
	}

	// public void setSessionFactory(SessionFactory sessionFactory)
	// {
	// this.sessionFactory = sessionFactory;
	// }

	@SuppressWarnings("unchecked")
	public GenericDAOHibernate() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {

		return sessionFactoryConfig.getSessionFactory().getCurrentSession();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void clear() {
		getSession().clear();
	}

	public void evict(T entity) {
		try {
			getSession().evict(entity);
		} catch (HibernateException e) {

			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void rollback() {
		if (getSession().getTransaction().isActive()) {
			getSession().getTransaction().rollback();
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T) getSession().load(getPersistentClass(), id);
	}

	@SuppressWarnings("unchecked")
	public T getById(ID id) {
		return (T) getSession().get(getPersistentClass(), id);
	}

	public void flush() {
		getSession().flush();
	}

	public T save(T entity) {
		try {
			getSession().save(entity);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return entity;
	}

	@SuppressWarnings("unchecked")
	public T merge(T entity) {
		try {
			return (T) getSession().merge(entity);
		} catch (HibernateException e) {

			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void update(T entity) {
		try {
			getSession().update(entity);
		} catch (HibernateException e) {

			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public void delete(T entity) {
		try {
			getSession().delete(entity);
		} catch (HibernateException e) {

			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public Date getCurrentTimestamp() {
		return new java.util.Date();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getLastNo(String columnName, String tableName, String code) {
		String result = null;
		try {
			String HQL_QUERY = "select max(" + columnName + ") from "
					+ tableName + " where " + columnName + " like '" + code
					+ "%'";
			List<String> queryList = getSession().createQuery(HQL_QUERY).list();

			if (queryList == null || queryList.size() == 0
					|| queryList.get(0) == null) {
				result = code + "1";
			} else {
				result = queryList.get(0);
				if (((Number) NumberFormat.getInstance().parse(result)) != null
						&& ((Number) NumberFormat.getInstance().parse(result))
								.intValue() > 0)
					result = code + String.valueOf(returnNextValue(result, 1));
				else
					result = code + "1";
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		return result;
	}

	public static int returnNextValue(String noteNo, int add) {
		int nextVal = Integer.parseInt(noteNo.replaceAll("[\\D]", "")) + add;
		return nextVal;
	}

	public static String zeroPad(String runNo) {
		for (int i = runNo.length(); i < 5; i++) {
			runNo = "0" + runNo;
		}
		return runNo;

	}

	protected void updateCreationInfo(BaseEntity baseEntity, String user) {
		//baseEntity.setCreateBy(String.valueOf(user));
		baseEntity.setCreateBy("Susanto");
		baseEntity.setCreateOn(new Timestamp(System.currentTimeMillis()));
		updateLastUpdateInfo(baseEntity, user);
	}

	protected void updateLastUpdateInfo(BaseEntity baseEntity, String user) {
		//baseEntity.setUpdateBy(String.valueOf(user));
		baseEntity.setUpdateBy("Susanto");
		baseEntity
				.setUpdateOn(new Timestamp(System.currentTimeMillis()));
	}

	public Date getDateFromDB() {
		SQLQuery sysdateQuery = getSession().createSQLQuery(
				"SELECT SYSDATE FROM DUAL");

		sysdateQuery.addScalar("SYSDATE", Hibernate.DATE);

		return (Date) sysdateQuery.uniqueResult();

	}

	protected Session openSession() {
		return sessionFactoryConfig.getSessionFactory().openSession();
	}
	
	// start added by robbin
	public Connection getConnection() {
		return getSession().connection();
	}
	
	public void commit() {
		getSession().getTransaction().commit();
	}
	
	public Transaction beginTransaction() {
		return getSession().beginTransaction();
	}
	//end added by robbin
	
}