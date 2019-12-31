package com.wo.epos.module.inv.vehicle.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class VehicleVO extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -1212482090848119977L;

	private Long vehicleId;
	private Long companyId;
	
	private Outlet outlet;
	private Long outletId;
	private String outletName;
	private String policeNo;
	private String vehicleType;
	private ParameterDtl paramVehicleType;
	private String vehicleDesc;
	private String occupyFlag;
	private String companyName;
	
	public Long getVehicleId() {
		return vehicleId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Outlet getOutlet() {
		return outlet;
	}
	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}
	public String getPoliceNo() {
		return policeNo;
	}
	public void setPoliceNo(String policeNo) {
		this.policeNo = policeNo;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getVehicleDesc() {
		return vehicleDesc;
	}
	public void setVehicleDesc(String vehicleDesc) {
		this.vehicleDesc = vehicleDesc;
	}
	
	public String getOccupyFlag() {
		return occupyFlag;
	}
	public void setOccupyFlag(String occupyFlag) {
		this.occupyFlag = occupyFlag;
	}
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public ParameterDtl getParamVehicleType() {
		return paramVehicleType;
	}
	public void setParamVehicleType(ParameterDtl paramVehicleType) {
		this.paramVehicleType = paramVehicleType;
	}
	
	
	

}
