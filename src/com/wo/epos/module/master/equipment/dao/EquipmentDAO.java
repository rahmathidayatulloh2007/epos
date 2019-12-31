package com.wo.epos.module.master.equipment.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.master.equipment.model.Equipment;
import com.wo.epos.module.master.equipment.vo.EquipmentVO;


public interface EquipmentDAO extends GenericDAO<Equipment, Long>, RetrieverDataPage<EquipmentVO> {
	
	public List<EquipmentVO> searchDataEquipmentByParameterCode(String parameterCode);
	public List<EquipmentVO> searchDataEquipmentByParameterCodeAndOutletId(String parameterCode, Long outletId);
	
}