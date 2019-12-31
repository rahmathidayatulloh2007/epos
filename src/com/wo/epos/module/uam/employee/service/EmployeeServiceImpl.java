package com.wo.epos.module.uam.employee.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.city.dao.CityDAO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.employee.dao.EmployeeDAO;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.outletEmp.dao.OutletEmpDAO;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="employeeService")
@ViewScoped
public class EmployeeServiceImpl implements EmployeeService, Serializable {

	private static final long serialVersionUID = 4359448049743461350L;

	static Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
	
	@ManagedProperty(value="#{employeeDAO}")
	private EmployeeDAO employeeDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
	@ManagedProperty(value="#{outletEmpDAO}")
	private OutletEmpDAO outletEmpDAO;
	
	
	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}
	
	public OutletEmpDAO getOutletEmpDAO() {
		return outletEmpDAO;
	}

	public void setOutletEmpDAO(OutletEmpDAO outletEmpDAO) {
		this.outletEmpDAO = outletEmpDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<EmployeeVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return employeeDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return employeeDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(EmployeeVO employeeVO, String user) {
		
		try{
		Company company = new Company();
		City city = null;
		ParameterDtl parameterDtlGender = null;
		ParameterDtl parameterDtlReligion = null;
		ParameterDtl parameterDtlMaritalSts = null;
		ParameterDtl parameterDtlEmployeeSts = null;
		
		
		if (employeeVO.getCityId() != null) {
			city = cityDAO.findById(employeeVO.getCityId());
		}
		
		if (employeeVO.getGenderCode() != null) {
			parameterDtlGender = parameterDAO.findByDetailId(employeeVO.getGenderCode());
		}
		
		if (employeeVO.getReligionCode() != null) {
			parameterDtlReligion = parameterDAO.findByDetailId(employeeVO.getReligionCode());
		}
		
		if (employeeVO.getMaritalStatusCode() != null) {
			parameterDtlMaritalSts = parameterDAO.findByDetailId(employeeVO.getMaritalStatusCode());
		}
		
		if (employeeVO.getEmployeeStatusCode() != null) {
			parameterDtlEmployeeSts = parameterDAO.findByDetailId(employeeVO.getEmployeeStatusCode());
		}
		
		Employee employee = new Employee();		
	//	employee.setEmployeeId(employeeVO.getEmployeeId());
		employee.setEmployeeNo(employeeVO.getEmployeeNo());
		if(employeeVO.getCompanyId() !=null){
			company = companyDAO.findById(employeeVO.getCompanyId());
			employee.setCompany(company);
		}
		
		employee.setCity(city);
		employee.setFullName(employeeVO.getFullName());
		employee.setBirthPlace(employeeVO.getBirthPlace());
		employee.setAddress(employeeVO.getAddress());
		employee.setPostalCode(employeeVO.getPostalCode());
		employee.setHpNo(employeeVO.getHpNo());
		employee.setWorkEmail(employeeVO.getWorkEmail());		
		employee.setProfileImgname(employeeVO.getProfileImgname());		
		employee.setGender(parameterDtlGender);
		employee.setReligion(parameterDtlReligion);
		employee.setMaritalStatus(parameterDtlMaritalSts);
		employee.setEmployeeStatus(parameterDtlEmployeeSts);
		employee.setBirthDate(employeeVO.getBirthDate());
		employee.setJoinDt(employeeVO.getJoinDt());
		employee.setActiveFlag(employeeVO.getActiveFlag());
		employee.setCreateBy(user);
		employee.setCreateOn(new Timestamp(System.currentTimeMillis()));
		employee.setProfileImg(employeeVO.getProfileImg());
		employeeDAO.save(employee);
		
		//if(employeeVO.getOutletId() !=null){
			OutletEmp outletEmp = new OutletEmp();
			outletEmp.setOutletId(employeeVO.getOutletId());
			outletEmp.setCompanyId(employeeVO.getCompanyId());
			outletEmp.setEmployeeId(employee.getEmployeeId());
			outletEmp.setActiveFlag(CommonConstants.Y);
			if(employeeVO.isPicFlagChecked() == true){
				outletEmp.setPicFlag(CommonConstants.Y);
			}else{
				outletEmp.setPicFlag(CommonConstants.N);
			}
			outletEmp.setCreateBy(user);
			outletEmp.setCreateOn(new Timestamp(System.currentTimeMillis()));
			
			outletEmpDAO.save(outletEmp);
		//}
		
		
		
		
		employeeDAO.flush();
		//outletEmpDAO.flush();
		}catch(Exception e){
			employeeDAO.rollback();
			//outletEmpDAO.rollback();
			logger.error("Save Failed", e);
			System.out.println("err=="+e);
		}
		
	}

	@Override
	public void update(EmployeeVO employeeVO, String user) {
	try{
		Company company = new Company();
		City city = null;
		ParameterDtl parameterDtlGender = null;
		ParameterDtl parameterDtlReligion = null;
		ParameterDtl parameterDtlMaritalSts = null;
		ParameterDtl parameterDtlEmployeeSts = null;
		
		if (employeeVO.getCityId() != null) {
			city = cityDAO.findById(employeeVO.getCityId());
		}
		if (employeeVO.getGenderCode() != null) {
			parameterDtlGender = parameterDAO.findByDetailId(employeeVO.getGenderCode());
		}
		if (employeeVO.getReligionCode() != null) {
			parameterDtlReligion = parameterDAO.findByDetailId(employeeVO.getReligionCode());
		}
		if (employeeVO.getMaritalStatusCode() != null) {
			parameterDtlMaritalSts = parameterDAO.findByDetailId(employeeVO.getMaritalStatusCode());
		}
		if (employeeVO.getEmployeeStatusCode() != null) {
			parameterDtlEmployeeSts = parameterDAO.findByDetailId(employeeVO.getEmployeeStatusCode());
		}
		
		Employee employee = new Employee();	
		employee = employeeDAO.findById(employeeVO.getEmployeeId());
		employee.setEmployeeId(employeeVO.getEmployeeId());
		employee.setEmployeeNo(employeeVO.getEmployeeNo());
		
		if (employeeVO.getCompanyId() != null) {
			company = companyDAO.findById(employeeVO.getCompanyId());
			employee.setCompany(company);
		}
		
		employee.setCity(city);
		employee.setFullName(employeeVO.getFullName());
		employee.setBirthPlace(employeeVO.getBirthPlace());
		employee.setAddress(employeeVO.getAddress());
		employee.setPostalCode(employeeVO.getPostalCode());
		employee.setHpNo(employeeVO.getHpNo());
		employee.setWorkEmail(employeeVO.getWorkEmail());		
		employee.setProfileImgname(employeeVO.getProfileImgname());		
		employee.setGender(parameterDtlGender);
		employee.setReligion(parameterDtlReligion);
		employee.setMaritalStatus(parameterDtlMaritalSts);
		employee.setEmployeeStatus(parameterDtlEmployeeSts);
		employee.setBirthDate(employeeVO.getBirthDate());
		employee.setJoinDt(employeeVO.getJoinDt());
		employee.setActiveFlag(employeeVO.getActiveFlag());
		employee.setCreateBy(employeeVO.getCreateBy());
		employee.setCreateOn(employeeVO.getCreateOn());
		employee.setUpdateBy(user);
		employee.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		employee.setProfileImg(employeeVO.getProfileImg());
		
		//if(employeeVO.getOutletId() !=null){
			//OutletEmp outletEmp = new OutletEmp();
			OutletEmp outletEmp = outletEmpDAO.findOutletEmpByEmployeeId(employee.getEmployeeId());
			if(outletEmp!=null){
				outletEmp.setOutletId(employeeVO.getOutletId());
				outletEmp.setEmployeeId(employee.getEmployeeId());
				outletEmp.setCompanyId(employeeVO.getCompanyId());
				if(employeeVO.isPicFlagChecked() == true){
					outletEmp.setPicFlag(CommonConstants.Y);
				}else{
					outletEmp.setPicFlag(CommonConstants.N);
				}
				outletEmp.setActiveFlag(CommonConstants.Y);
				outletEmp.setUpdateBy(user);
				outletEmp.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				
				outletEmpDAO.update(outletEmp);
			}else{
				outletEmp = new OutletEmp();
				outletEmp.setOutletId(employeeVO.getOutletId());
				outletEmp.setCompanyId(employeeVO.getCompanyId());
				outletEmp.setEmployeeId(employee.getEmployeeId());
				outletEmp.setActiveFlag(CommonConstants.Y);
				if(employeeVO.isPicFlagChecked() == true){
					outletEmp.setPicFlag(CommonConstants.Y);
				}else{
					outletEmp.setPicFlag(CommonConstants.N);
				}
				outletEmp.setCreateBy(user);
				outletEmp.setCreateOn(new Timestamp(System.currentTimeMillis()));
				
				outletEmpDAO.save(outletEmp);
			}
		//}
		
		
		
		employeeDAO.update(employee);
		employeeDAO.flush();
	}catch(Exception e){
		employeeDAO.rollback();
		outletEmpDAO.rollback();
	}
	}

	@Override
	public void delete(Long employeeId) {
		//Employee employee = new Employee();
		//employee.setEmployeeId(employeeId);
		Employee employee = findById(employeeId);
		employeeDAO.delete(employee);
		employeeDAO.flush();
	}

	@Override
	public Employee findById(Long employeeId) {
		
		return employeeDAO.findById(employeeId);
	}

	@Override
	public List<CityVO> searchCityAll() {
		
		return cityDAO.citySearch();
	}

	@Override
	public List<CompanyVO> searchCompanyList() {
		
		return companyDAO.searchCompanyList();
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		
		return parameterDAO.parameterDtlList(parameterCode);
	}

	@Override
	public List<EmployeeVO> searchEmployeeList() {
		
		return employeeDAO.searchEmployeeList();
	}
	
	@Override
	public ParameterDtl findByDetailCode(String parameterDtlCode) {		
		
		return employeeDAO.findByDetailCode(parameterDtlCode);
	}

	@Override
	public Employee findByNo(String employeeNo) {
		return employeeDAO.findByNo(employeeNo);
	}

	@Override
	public List<EmployeeVO> searchEmployeeListByCompany(Long companyId) {
		return employeeDAO.searchEmployeeListByCompany(companyId);
	}

}
