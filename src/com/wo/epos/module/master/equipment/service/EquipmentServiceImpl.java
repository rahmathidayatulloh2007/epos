package com.wo.epos.module.master.equipment.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.equipment.dao.EquipmentDAO;
import com.wo.epos.module.master.equipment.model.Equipment;
import com.wo.epos.module.master.equipment.vo.EquipmentVO;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;

@ManagedBean(name="equipmentService")
@ViewScoped
public class EquipmentServiceImpl implements EquipmentService, Serializable {

	private static final long serialVersionUID = 478992409929970399L;

	@ManagedProperty(value="#{equipmentDAO}")
	private EquipmentDAO equipmentDAO;
		
	@ManagedProperty(value="#{outletDAO}")
	private OutletDAO outletDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
		
	
	public EquipmentDAO getEquipmentDAO() {
		return equipmentDAO;
	}

	public void setEquipmentDAO(EquipmentDAO equipmentDAO) {
		this.equipmentDAO = equipmentDAO;
	}

	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public List<EquipmentVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		return equipmentDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		return equipmentDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(EquipmentVO equipmentVO, String user)  {
		try{
			
			Equipment equipment = new Equipment();
			if(equipmentVO.getOutletId() !=null){
			     equipment.setOutletId(equipmentVO.getOutletId());
			     equipment.setOutlet(outletDAO.findById(equipmentVO.getOutletId()));
			}
			equipment.setEquipmentCode(equipmentVO.getEquipmentCode());
			equipment.setEquipmentName(equipmentVO.getEquipmentName());
			equipment.setEquipmentTypeCode(equipmentVO.getEquipmentTypeCode());
			equipment.setEquipmentType(parameterDAO.findByDetailId(equipmentVO.getEquipmentTypeCode()));
			equipment.setEquipmentStatusCode(CommonConstants.EQUIPMENT_VACANT);
			equipment.setActiveFlag(CommonConstants.Y);
			equipment.setCreateBy(user);
			equipment.setCreateOn(new Timestamp(System.currentTimeMillis()));						
	
			equipmentDAO.save(equipment);
			equipmentDAO.flush();
	  
	  }catch(Exception ex){
		  ex.printStackTrace();
		  equipmentDAO.rollback();
	  }
		
	}

	@Override
	public void update(EquipmentVO equipmentVO, String user)  {
		try{
			
			Equipment equipment = new Equipment();
			equipment = equipmentDAO.findById(equipmentVO.getEquipmentId());
			if(equipmentVO.getOutletId() !=null){
				equipment.setOutletId(equipmentVO.getOutletId());
				equipment.setOutlet(outletDAO.findById(equipmentVO.getOutletId()));
			}
			equipment.setEquipmentName(equipmentVO.getEquipmentName());
			equipment.setEquipmentTypeCode(equipmentVO.getEquipmentTypeCode());
			equipment.setEquipmentType(parameterDAO.findByDetailId(equipmentVO.getEquipmentTypeCode()));								
			equipment.setActiveFlag(CommonConstants.Y);
			equipment.setUpdateBy(user);
			equipment.setUpdateOn(new Timestamp(System.currentTimeMillis()));						
	
			equipmentDAO.update(equipment);
			equipmentDAO.flush();
	  
	  }catch(Exception ex){
		  ex.printStackTrace();
		  equipmentDAO.rollback();
	  }
		
	}

	@Override
	public void delete(Long equipmentId) {
		Equipment equipDel = new Equipment();
		equipDel = equipmentDAO.findById(equipmentId);
				
		equipmentDAO.delete(equipDel);
		equipmentDAO.flush();
	}

	@Override
	public Equipment findById(Long equipmentId) {
		return equipmentDAO.findById(equipmentId);
	}

	@Override
	public List<EquipmentVO> searchDataEquipmentByParameterCode(
			String parameterCode) {
		return equipmentDAO.searchDataEquipmentByParameterCode(parameterCode);
	}
	
	@Override
	public List<EquipmentVO> searchDataEquipmentByParameterCodeAndOutletId(
			String parameterCode, Long outletId) {
		return equipmentDAO.searchDataEquipmentByParameterCodeAndOutletId(parameterCode, outletId);
	}
	
}	
