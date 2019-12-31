package com.wo.epos.module.master.franchise.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.city.dao.CityDAO;
import com.wo.epos.module.master.city.dao.FranchiseDAO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.franchise.model.Franchise;
import com.wo.epos.module.master.franchise.vo.FranchiseVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.outletEmp.dao.OutletEmpDAO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="franchiseService")
@ViewScoped
public class FranchiseServiceImpl implements FranchiseService, Serializable {

	private static final long serialVersionUID = 4359448049743461350L;

	@ManagedProperty(value="#{franchiseDAO}")
	private FranchiseDAO franchiseDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
	@ManagedProperty(value="#{outletEmpDAO}")
	private OutletEmpDAO outletEmpDAO;
	
	
	public FranchiseDAO getFranchiseDAO() {
		return franchiseDAO;
	}

	public void setFranchiseDAO(FranchiseDAO franchiseDAO) {
		this.franchiseDAO = franchiseDAO;
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
	public List<FranchiseVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return franchiseDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return franchiseDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(FranchiseVO franchiseVO, String user) {
		
		try{
		
		
		
		Franchise franchise = new Franchise();		
	
		franchise.setFranchiseCode(franchiseVO.getFranchiseCode());
		franchise.setFranchiseName(franchiseVO.getFranchiseName());
		City city = (City) cityDAO.findById(franchiseVO.getCityId());
		
		franchise.setCity(city);
		franchise.setAddress(franchiseVO.getAddress());
		franchise.setGroupId(franchiseVO.getGroupId());
		franchise.setLogoFile(franchiseVO.getLogoFile());
		franchise.setPicName(franchiseVO.getPicName());
		franchise.setPicPhoneNo(franchiseVO.getPicPhoneNo());
		franchise.setPostalCode(franchiseVO.getPostalCode());
		franchise.setLogoFile(franchiseVO.getLogoFile());
		franchise.setActiveFlag(franchiseVO.getActiveFlag());
		franchise.setCreateBy(user);
		franchise.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		franchiseDAO.save(franchise);
		
		franchiseDAO.flush();
		}catch(Exception e){
			franchiseDAO.rollback();
			System.out.println("err=="+e);
		}
		
	}

	@Override
	public void update(FranchiseVO franchiseVO, String user) {
	
		try{
		
		Franchise franchise = franchiseDAO.findById(franchiseVO.getFranchiseId());
		franchise.setFranchiseName(franchiseVO.getFranchiseName());
		
		franchise.setFranchiseCode(franchiseVO.getFranchiseCode());
		City city = (City) cityDAO.findById(franchiseVO.getCityId());
		
		franchise.setCity(city);
		//franchise.setCity(franchiseVO.getCity());
		franchise.setAddress(franchiseVO.getAddress());
		franchise.setGroupId(franchiseVO.getGroupId());
		franchise.setLogoFile(franchiseVO.getLogoFile());
		franchise.setPicName(franchiseVO.getPicName());
		franchise.setPicPhoneNo(franchiseVO.getPicPhoneNo());
		franchise.setPostalCode(franchiseVO.getPostalCode());
		franchise.setLogoFile(franchiseVO.getLogoFile());
		franchise.setActiveFlag(franchiseVO.getActiveFlag());
		franchise.setCreateBy(user);
		franchise.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		
		franchiseDAO.update(franchise);
		franchiseDAO.flush();
		}catch(Exception e){
			franchiseDAO.rollback();
			System.out.println(e);
		}
	}

	@Override
	public void delete(Long franchiseId) {
		
		Franchise franchise = new Franchise();
		franchise.setFranchiseId(franchiseId);
		franchiseDAO.delete(franchise);
		franchiseDAO.flush();
	}

	@Override
	public Franchise findById(Long franchiseId) {
		
		return franchiseDAO.findById(franchiseId);
	}

	@Override
	public List<CityVO> searchCityAll() {
		
		return cityDAO.citySearch();
	}

	
	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		
		return parameterDAO.parameterDtlList(parameterCode);
	}

	@Override
	public List<FranchiseVO> searchFranchiseList() {
		
		return franchiseDAO.searchFranchiseList();
	}
	

	@Override
	public Franchise findByNo(String franchiseNo) {
		return franchiseDAO.findByNo(franchiseNo);
	}

	

}
