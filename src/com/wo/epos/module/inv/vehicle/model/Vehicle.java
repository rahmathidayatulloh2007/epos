package com.wo.epos.module.inv.vehicle.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;


public class Vehicle extends BaseEntity implements Serializable {	
	
	private static final long serialVersionUID = -1212482090848119977L;

	private Long vehicleId;

	private Outlet outlet;
	private String policeNo;
	private String vehicleType;
	private ParameterDtl paramVehicleType;
	private String vehicleDesc;
	private String occupyFlag;
	
	public Long getVehicleId() {
		return vehicleId;
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
	public ParameterDtl getParamVehicleType() {
		return paramVehicleType;
	}
	public void setParamVehicleType(ParameterDtl paramVehicleType) {
		this.paramVehicleType = paramVehicleType;
	}
	
	
	
	
	
	
	

}
