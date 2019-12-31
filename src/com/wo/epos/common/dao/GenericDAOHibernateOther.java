package com.wo.epos.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
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
public abstract class GenericDAOHibernateOther<T, ID extends Serializable>
		implements GenericDAO<T, ID> {
	public static final String LIKEALL_CHAR = "%";
	private Class<T> persistentClass;

	static Logger logger = Logger.getLogger(GenericDAOHibernateOther.class);

	private SessionFactoryConfigBased sessionFactoryConfig = new SessionFactoryConfigBased();

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
	public GenericDAOHibernateOther() {
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
		getSession().setFlushMode(FlushMode.MANUAL);
		getSession().beginTransaction();
		T t = (T) getSession().load(getPersistentClass(), id);
		getSession().close();
		return t;
	}

	@SuppressWarnings("unchecked")
	public T getById(ID id) {
		getSession().setFlushMode(FlushMode.MANUAL);
		getSession().beginTransaction();
		T t = (T) getSession().get(getPersistentClass(), id);
		getSession().close();

		return t;
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
		return (T) getSession().merge(entity);
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

	@Override
	public String getLastNo(String columnName, String tableName, String code) {
		throw new RuntimeException("Not Implemented");
	}

	public static String returnNextValue(String noteNo, int add) {
		String nextVal = String.valueOf(Integer.valueOf(noteNo.substring(2, 7))
				.intValue() + add);
		return zeroPad(nextVal);
	}

	public static String zeroPad(String runNo) {
		for (int i = runNo.length(); i < 5; i++) {
			runNo = "0" + runNo;
		}
		return runNo;

	}

	protected void updateCreationInfo(BaseEntity baseEntity, String user) {
		baseEntity.setCreateBy(String.valueOf(user));
		baseEntity.setCreateOn(new Timestamp(System.currentTimeMillis()));
		updateLastUpdateInfo(baseEntity, user);
	}

	protected void updateLastUpdateInfo(BaseEntity baseEntity, String user) {
		baseEntity.setUpdateBy(String.valueOf(user));
		baseEntity
				.setUpdateOn(new Timestamp(System.currentTimeMillis()));
	}

	public Date getDateFromDB() {
		SQLQuery sysdateQuery = getSession().createSQLQuery(
				"SELECT SYSDATE FROM DUAL");

		sysdateQuery.addScalar("SYSDATE", Hibernate.DATE);

		return (Date) sysdateQuery.uniqueResult();

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