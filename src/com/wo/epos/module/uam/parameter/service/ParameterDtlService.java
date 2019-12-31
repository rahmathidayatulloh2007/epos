package com.wo.epos.module.uam.parameter.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;


public interface ParameterDtlService extends RetrieverDataPage<ParameterDtlVO> {


	public ParameterDtl findById(String parameterCode);
	
	public ParameterDtl findByDetailId(String id);
	
	public List<ParameterDtlVO> parameterSearch();
	public List<ParameterDtl> parameterDtlList(String parameterCode); 
	
	
}
