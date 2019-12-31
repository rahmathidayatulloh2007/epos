package com.wo.epos.module.uam.parameter.model;

import java.io.Serializable;


import com.wo.epos.common.model.BaseEntity;


public class ParameterDtl extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6722568395143105217L;
		
	private String parameterDtlCode;	
	private String parameterDtlName;
	private String parameterDtlDesc;
	
	private Parameter parameter;

	
	public String getParameterDtlCode() {
		return parameterDtlCode;
	}

	public void setParameterDtlCode(String parameterDtlCode) {
		this.parameterDtlCode = parameterDtlCode;
	}

	public String getParameterDtlName() {
		return parameterDtlName;
	}

	public void setParameterDtlName(String parameterDtlName) {
		this.parameterDtlName = parameterDtlName;
	}

	public String getParameterDtlDesc() {
		return parameterDtlDesc;
	}

	public void setParameterDtlDesc(String parameterDtlDesc) {
		this.parameterDtlDesc = parameterDtlDesc;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
}
