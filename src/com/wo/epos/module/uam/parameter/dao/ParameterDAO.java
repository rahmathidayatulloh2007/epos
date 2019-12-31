package com.wo.epos.module.uam.parameter.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

public interface ParameterDAO extends GenericDAO<Parameter, String>, RetrieverDataPage<ParameterVO>{
	
	public List<ParameterVO>parameterSearch();

	public List<ParameterDtl> parameterDtlList(String parameterCode); 
	
	public ParameterDtl findByDetailId(String id);

}


