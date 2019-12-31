package com.wo.epos.module.uam.parameter.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.dao.ParameterDtlDAO;
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

@ManagedBean(name="parameterDtlService")
@ViewScoped
public class ParameterDtlServiceImpl implements ParameterDtlService, Serializable {

	private static final long serialVersionUID = -5927313745557525240L;
	@ManagedProperty(value="#{parameterDtlDAO}")
	private ParameterDtlDAO parameterDtlDAO;

	public ParameterDtlDAO getParameterDtlDAO() {
		return parameterDtlDAO;
	}

	public void setParameterDtlDAO(ParameterDtlDAO parameterDtlDAO) {
		this.parameterDtlDAO = parameterDtlDAO;
	}

	
	
	@Override
	public ParameterDtl findById(String parameterCode)
	{
		return parameterDtlDAO.findById(parameterCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ParameterDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return parameterDtlDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return parameterDtlDAO.searchCountData(searchCriteria);
	}

	@Override
	public List<ParameterDtlVO> parameterSearch() {
		// TODO Auto-generated method stub
		return parameterDtlDAO.parameterSearch();
	}

	@Override
	public ParameterDtl findByDetailId(String id) {
		return parameterDtlDAO.findByDetailId(id);
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		return parameterDtlDAO.parameterDtlList(parameterCode);
	}
	
}
