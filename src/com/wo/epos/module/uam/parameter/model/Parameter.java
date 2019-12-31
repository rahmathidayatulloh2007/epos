package com.wo.epos.module.uam.parameter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wo.epos.common.model.BaseEntity;


public class Parameter extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6722568395143105217L;
	
	private String parameterCode;	
	private String parameterName;
	private String parameterDesc;
	private String detailResume;
	
	private List<ParameterDtl> listDetail = new ArrayList<ParameterDtl>();
	
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
	public List<ParameterDtl> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<ParameterDtl> listDetail) {
		this.listDetail = listDetail;
	}
	
}
