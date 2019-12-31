package com.wo.epos.common.dao;

import java.io.Serializable;
import java.util.Date;

import java.sql.Connection;
import org.hibernate.Transaction;

/**
 * @author Manimbul Simorangkir
 * @version 1.0
 * @created May, 2009
 */
public interface GenericDAO<T, ID extends Serializable> {
	T findById(ID id);

	T getById(ID id);

	T save(T entity);

	T merge(T entity);

	void update(T entity);

	void delete(T entity);

	void evict(T entity);

	void flush();

	void clear();

	void rollback();

	Date getDateFromDB();

	String getLastNo(String columnName, String tableName, String code);

	// start added by robbin
	void commit();
	Transaction beginTransaction();
	Connection getConnection();
	// end added by robbin
	
}