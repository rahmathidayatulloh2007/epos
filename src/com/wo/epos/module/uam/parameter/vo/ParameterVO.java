package com.wo.epos.module.uam.parameter.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;

public class ParameterVO extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 4432099191460050723L;
	
	private String parameterCode;	
	private String parameterName;
	private String parameterDesc;
	private String detailResume;
	
	private List<ParameterDtlVO> listDetail = new ArrayList<ParameterDtlVO>();
	
	private boolean checked;
	
	public ParameterVO()
	{
		
	}
	
	public String getParameterCode() {
		return parameterCode;
	}
	
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getParameterDesc() {
		return parameterDesc;
	}

	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

	public String getDetailResume() {
		return detailResume;
	}

	public void setDetailResume(String detailResume) {
		this.detailResume = detailResume;
	}

	public List<ParameterDtlVO> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<ParameterDtlVO> listDetail) {
		this.listDetail = listDetail;
	}
	
}
