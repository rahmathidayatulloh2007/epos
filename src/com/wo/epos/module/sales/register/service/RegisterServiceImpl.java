package com.wo.epos.module.sales.register.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.register.dao.RegisterDAO;
import com.wo.epos.module.sales.register.dao.RegisterDtlDAO;
import com.wo.epos.module.sales.register.model.Register;
import com.wo.epos.module.sales.register.model.RegisterDtl;
import com.wo.epos.module.sales.register.vo.RegisterDtlVO;
import com.wo.epos.module.sales.register.vo.RegisterVO;
import com.wo.epos.module.uam.employee.dao.EmployeeDAO;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;

@ManagedBean(name="registerService")
@ViewScoped
public class RegisterServiceImpl implements RegisterService, Serializable {

	
	private static final long serialVersionUID = 4393378202928966285L;

	@ManagedProperty(value="#{registerDAO}")
	private RegisterDAO registerDAO;
	
	@ManagedProperty(value="#{registerDtlDAO}")
	private RegisterDtlDAO registerDtlDAO;
	
	@ManagedProperty(value="#{outletDAO}")
	private OutletDAO outletDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
	@ManagedProperty(value="#{employeeDAO}")
	private EmployeeDAO employeeDAO;
	

	public RegisterDAO getRegisterDAO() {
		return registerDAO;
	}

	public void setRegisterDAO(RegisterDAO registerDAO) {
		this.registerDAO = registerDAO;
	}

	public RegisterDtlDAO getRegisterDtlDAO() {
		return registerDtlDAO;
	}

	public void setRegisterDtlDAO(RegisterDtlDAO registerDtlDAO) {
		this.registerDtlDAO = registerDtlDAO;
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

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RegisterVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		return registerDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		return registerDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(RegisterVO registerVO, List<RegisterDtlVO> registerDtlList,
			String user)  {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS");
			
			Register registerHeader = new Register();
			registerHeader.setOutletId(registerVO.getOutletId());
			registerHeader.setOutlet(outletDAO.findById(registerVO.getOutletId()));
			registerHeader.setStatusCode(CommonConstants.REGISTER_OPEN);
			registerHeader.setStatus(parameterDAO.findByDetailId(CommonConstants.REGISTER_OPEN));
			registerHeader.setCashierId(registerVO.getCashierId());
			registerHeader.setCashier(employeeDAO.findById(registerVO.getCashierId()));						
			
			String dateString = sdf.format(registerVO.getStartTime())+" "+registerVO.getTime()+":00.000000";
		    Date parsedDate = dateFormat.parse(dateString);
		    Timestamp timestamp = new Timestamp(parsedDate.getTime());
			registerHeader.setStartTime(timestamp);		
			registerHeader.setActiveFlag(CommonConstants.Y);
			registerHeader.setCreateBy(user);
			registerHeader.setCreateOn(new Timestamp(System.currentTimeMillis()));
			
			List<RegisterDtl> regDtlList = new ArrayList<RegisterDtl>();
			for(int i=0; i<registerDtlList.size(); i++){
				  RegisterDtlVO dtlVO = (RegisterDtlVO)registerDtlList.get(i);
				  RegisterDtl registerDtl = new RegisterDtl();
				  registerDtl.setRegister(registerHeader);
				  registerDtl.setPaymentMethodCode(dtlVO.getPaymentMethodCode());
				  registerDtl.setInitFund(dtlVO.getInitFund());
				  registerDtl.setActiveFlag(CommonConstants.Y);
				  registerDtl.setCreateBy(user);
				  registerDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
				  
				  regDtlList.add(registerDtl);
			}
			
			registerHeader.setRegisterDtlList(regDtlList);
			registerDAO.save(registerHeader);
			registerDAO.flush();
	  
	  }catch(ParseException exception){
		  exception.printStackTrace();
		  registerDAO.rollback();
	  }
		
	}

	@Override
	public void update(RegisterVO registerVO,
			List<RegisterDtlVO> registerDtlList, String user) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS");
			
			Register registerHeader = new Register();
			registerHeader = registerDAO.findById(registerVO.getRegId());
			registerHeader.setOutletId(registerVO.getOutletId());
			registerHeader.setOutlet(outletDAO.findById(registerVO.getOutletId()));
			registerHeader.setCashierId(registerVO.getCashierId());
			registerHeader.setCashier(employeeDAO.findById(registerVO.getCashierId()));						
			
			String dateString = sdf.format(registerVO.getStartTime())+" "+registerVO.getTime()+":00.000000";
		    Date parsedDate = dateFormat.parse(dateString);
		    Timestamp timestamp = new Timestamp(parsedDate.getTime());
			registerHeader.setStartTime(timestamp);			
			registerHeader.setUpdateBy(user);
			registerHeader.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			
			for(int i=0; i<registerDtlList.size(); i++){
				  RegisterDtlVO dtlVO = (RegisterDtlVO)registerDtlList.get(i);
				  RegisterDtl registerDtl = new RegisterDtl();
				  registerDtl = registerDtlDAO.getById(dtlVO.getRegDtlId());
				  registerDtl.setInitFund(dtlVO.getInitFund());
				  registerDtl.setUpdateBy(user);
				  registerDtl.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				  registerDtlDAO.update(registerDtl);
			}
			
			registerDAO.update(registerHeader);
			registerDAO.flush();
	  
	  }catch(ParseException exception){
		  exception.printStackTrace();
		  registerDAO.rollback();
	  }
		
	}

	@Override
	public void delete(Long regId) {
		Register regDel = new Register();
		regDel = registerDAO.findById(regId);
		
		for(RegisterDtl regDtl : regDel.getRegisterDtlList()){
			RegisterDtl regDtlDel = registerDtlDAO.findById(regDtl.getRegDtlId());
			registerDtlDAO.delete(regDtlDel);
		}
		
		registerDAO.delete(regDel);
		registerDAO.flush();
	}

	@Override
	public Register findById(Long regId) {
		return registerDAO.findById(regId);
	}

	@Override
	public List<RegisterDtlVO> searchRegisterDtlVoList(Long registerId) {
		return registerDtlDAO.searchRegisterDtlVoList(registerId);
	}

	@Override
	public List<RegisterVO> findRegisterByOutletId(Long outletId){
		return registerDAO.findRegisterByOutletId(outletId);
	}
	
	
}
