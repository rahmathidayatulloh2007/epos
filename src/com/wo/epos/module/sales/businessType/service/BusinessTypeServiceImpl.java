package com.wo.epos.module.sales.businessType.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.businessType.dao.BusinessTypeDAO;
import com.wo.epos.module.sales.businessType.model.BusinessType;
import com.wo.epos.module.sales.businessType.vo.BusinessTypeVO;
import com.wo.epos.module.sales.districts.dao.DistrictsDAO;
import com.wo.epos.module.sales.districts.vo.DistrictsVO;


@ManagedBean(name="businessTypeService")
@ViewScoped
public class BusinessTypeServiceImpl implements BusinessTypeService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{businessTypeDAO}")
	private BusinessTypeDAO businessTypeDAO;

	public BusinessTypeDAO getBusinessTypeDAO() {
		return businessTypeDAO;
	}

	public void setBusinessTypeDAO(BusinessTypeDAO businessTypeDAO) {
		this.businessTypeDAO = businessTypeDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<BusinessTypeVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return businessTypeDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return businessTypeDAO.searchCountData(searchCriteria);
	}

	@Override
	public BusinessType findById(Long businessTypeId) {
		// TODO Auto-generated method stub
		return businessTypeDAO.findById(businessTypeId);
	}
	
	
		
}
