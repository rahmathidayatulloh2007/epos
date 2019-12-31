package com.wo.epos.module.uam.parameter.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

public interface ParameterService extends RetrieverDataPage<ParameterVO> {

	public void save(ParameterVO parameterVo, String user);
	public void update(ParameterVO parameterVo, String user);
	public void delete(String parameterCode);
	public Parameter findById(String parameterCode);
	
	public ParameterDtl findByDetailId(String id);
	
	public List<ParameterVO> parameterSearch();
	public List<ParameterDtl> parameterDtlList(String parameterCode); 
	
	
}
