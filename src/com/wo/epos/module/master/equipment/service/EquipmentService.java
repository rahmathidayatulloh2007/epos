package com.wo.epos.module.master.equipment.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.equipment.model.Equipment;
import com.wo.epos.module.master.equipment.vo.EquipmentVO;

public interface EquipmentService extends RetrieverDataPage<EquipmentVO> {

	public void save(EquipmentVO equipmentVO, String user);
	public void update(EquipmentVO equipmentVO, String user);
	public void delete(Long equipmentId);
	public Equipment findById(Long equipmentId);
	public List<EquipmentVO> searchDataEquipmentByParameterCode(String parameterCode);
	public List<EquipmentVO> searchDataEquipmentByParameterCodeAndOutletId(String parameterCode, Long outletId);
	
	
}	
	