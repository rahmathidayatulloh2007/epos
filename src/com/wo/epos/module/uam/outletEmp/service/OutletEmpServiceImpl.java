package com.wo.epos.module.uam.outletEmp.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.outletEmp.dao.OutletEmpDAO;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;

@ManagedBean(name = "outletEmpService")
@ViewScoped
public class OutletEmpServiceImpl implements OutletEmpService, Serializable{

	private static final long serialVersionUID = -5498581254487000814L;

	@ManagedProperty(value="#{outletEmpDAO}")
	private OutletEmpDAO outletEmpDAO;
	
		public OutletEmpDAO getOutletEmpDAO() {
		return outletEmpDAO;
	}

	public void setOutletEmpDAO(OutletEmpDAO outletEmpDAO) {
		this.outletEmpDAO = outletEmpDAO;
	}

	@Override
	public void save(OutletEmpVO outletEmpVO, String user) {
		
		OutletEmp outletEmp = new OutletEmp();
		
		outletEmp.setOutletId(outletEmpVO.getOutletId());
		outletEmp.setEmployeeId(outletEmpVO.getOutletEmpId());
		
		outletEmp.setActiveFlag("Y");
		outletEmp.setCreateBy(user);
		outletEmp.setCreateOn(new Timestamp(System.currentTimeMillis()));
	
		outletEmpDAO.save(outletEmp);
		outletEmpDAO.flush();
		 
	}

	@Override
	public void update(OutletEmpVO outletEmpVO, String user) {
		
        OutletEmp outletEmp = new OutletEmp();
        outletEmp= outletEmpDAO.findById(outletEmpVO.getOutletEmpId());
		
		outletEmp.setOutletId(outletEmpVO.getOutletId());
		outletEmp.setEmployeeId(outletEmpVO.getOutletEmpId());
		
		outletEmp.setUpdateBy(user);
		outletEmp.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		outletEmpDAO.update(outletEmp);
		outletEmpDAO.flush();
	}

	@Override
	public void delete(Long outletEmpId) {
		
		OutletEmp outletEmp = new OutletEmp();
		outletEmp.setOutletEmpId(outletEmpId);
		outletEmpDAO.delete(outletEmp);
		outletEmpDAO.flush();
	}

	@Override
	public OutletEmp findById(Long outletEmpId) {
		
		return outletEmpDAO.findById(outletEmpId);
	}

	@Override
	public List<OutletEmpVO> searchOutletEmpList() {
		
		return outletEmpDAO.searchOutletEmpList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<OutletEmpVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return outletEmpDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return outletEmpDAO.searchCountData(searchCriteria);
	}
	
	public OutletEmp findOutletEmpByOutletId(Long outletId){
		return outletEmpDAO.findOutletEmpByOutletId(outletId);
	}
	
	public List<OutletEmpVO> findOutletEmpByOutletIdList(Long outletId){
		return outletEmpDAO.findOutletEmpByOutletIdList(outletId);
	}
	
	public OutletEmp findOutletEmpByEmployeeId(Long employeeId){
		return outletEmpDAO.findOutletEmpByEmployeeId(employeeId);
	}

}
