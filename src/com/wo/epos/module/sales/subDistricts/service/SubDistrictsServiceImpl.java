package com.wo.epos.module.sales.subDistricts.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.subDistricts.dao.SubDistrictsDAO;
import com.wo.epos.module.sales.subDistricts.vo.SubDistrictsVO;


@ManagedBean(name="subDistrictsService")
@ViewScoped
public class SubDistrictsServiceImpl implements SubDistrictsService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{subDistrictsDAO}")
	private SubDistrictsDAO subDistrictsDAO;

	
	
	public SubDistrictsDAO getSubDistrictsDAO() {
		return subDistrictsDAO;
	}

	public void setSubDistrictsDAO(SubDistrictsDAO subDistrictsDAO) {
		this.subDistrictsDAO = subDistrictsDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SubDistrictsVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return subDistrictsDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return subDistrictsDAO.searchCountData(searchCriteria);
	}
	
	
		
}
