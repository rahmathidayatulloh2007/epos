package com.wo.epos.common.dao.tableOfValues;

import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.SQLQuery;

import com.wo.epos.common.dao.GenericDAOHibernate;

@ManagedBean(name="tableOfValuesDao")
@ViewScoped
public class TableOfValuesDAOImpl extends GenericDAOHibernate<Object, Long> implements TableOfValuesDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getListTableOfValues(String sql) throws SQLException {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		
		return sqlQuery.list();
	}

}
