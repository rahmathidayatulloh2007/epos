package com.wo.epos.module.sales.districts.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.districts.dao.DistrictsDAO;
import com.wo.epos.module.sales.districts.model.Districts;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;


@ManagedBean(name="districtsService")
@ViewScoped
public class DistrictsServiceImpl implements DistrictsService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{districtsDAO}")
	private DistrictsDAO districtsDAO;


	
	public DistrictsDAO getDistrictsDAO() {
		return districtsDAO;
	}

	public void setDistrictsDAO(DistrictsDAO districtsDAO) {
		this.districtsDAO = districtsDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DistrictsVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return districtsDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return districtsDAO.searchCountData(searchCriteria);
	}

	@Override
	public Districts findById(Long districtsId) {
		// TODO Auto-generated method stub
		return districtsDAO.findById(districtsId);
	}
	
	
		
}
