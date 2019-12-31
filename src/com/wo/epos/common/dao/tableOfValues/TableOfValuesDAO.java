package com.wo.epos.common.dao.tableOfValues;

import java.sql.SQLException;
import java.util.List;

import com.wo.epos.common.dao.GenericDAO;

public interface TableOfValuesDAO extends GenericDAO<Object, Long>{
	public List<Object[]> getListTableOfValues(String sql) throws SQLException ;

}
