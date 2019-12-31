package com.wo.epos.module.uam.parameter.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;

public class ParameterDtlVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4432099191460050723L;
	
	private String parameterDtlCode;	
	private String parameterDtlName;
	private String parameterDtlDesc;
	private String flag;
	
	private ParameterVO parameterVO;
	
	private boolean checked;
	
	public ParameterDtlVO()
	{
		
	}

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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public ParameterVO getParameterVO() {
		return parameterVO;
	}

	public void setParameterVO(ParameterVO parameterVO) {
		this.parameterVO = parameterVO;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


}
