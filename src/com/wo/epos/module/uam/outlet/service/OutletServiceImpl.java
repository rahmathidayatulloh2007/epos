package com.wo.epos.module.uam.outlet.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.city.dao.CityDAO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.dao.OutletEmpDAO;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;

@ManagedBean(name = "outletService")
@ViewScoped
public class OutletServiceImpl implements OutletService, Serializable{

	private static final long serialVersionUID = 7730331326935516188L;
	
	@ManagedProperty(value="#{outletDAO}")
	private OutletDAO outletDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;
	
	@ManagedProperty(value="#{outletEmpDAO}")
	private OutletEmpDAO outletEmpDAO;

	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
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

	public OutletEmpDAO getOutletEmpDAO() {
		return outletEmpDAO;
	}

	public void setOutletEmpDAO(OutletEmpDAO outletEmpDAO) {
		this.outletEmpDAO = outletEmpDAO;
	}

	@Override
	public void save(OutletVO outletVO, String user) {
		try{
			Company company = new Company();
			City city = new City();
			Outlet outlet = new Outlet();
			
			city = cityDAO.findById(outletVO.getCityId());
					 
			outlet.setOutletId(outletVO.getOutletId());
			outlet.setOutletCode(outletVO.getOutletCode());
			outlet.setOutletName(outletVO.getOutletName());
	
			if(outletVO.getCompanyId() !=null){
				company = companyDAO.findById(outletVO.getCompanyId());
				if(company.getOutletQty() !=null){
					company.setOutletQty(company.getOutletQty() + 1);
				}else{
					company.setOutletQty(new Long(1));
				}						
				
				company.setUpdateBy(user);
				company.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				companyDAO.update(company);
				
				outlet.setCompany(company);
			}
			
			outlet.setCity(city);
			outlet.setAddress(outletVO.getAddress());
			outlet.setCityId(outletVO.getCityId());
			outlet.setPostalCode(outletVO.getPostalCode());
			//outlet.setPicName(outletVO.getPicName());
			//outlet.setPicPhoneno(outletVO.getPicPhoneno());
			outlet.setActiveFlag(outletVO.getActiveFlag());
			outlet.setCreateBy(user);
			outlet.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
			outletDAO.save(outlet);
			outletDAO.flush();
		}catch(Exception ex){
			ex.printStackTrace();
			outletDAO.rollback();
		}
		 
	}

	@Override
	public void update(OutletVO outletVO, String user) {
		try{
			Company company = new Company();
			City city = new City();
			Outlet outlet = new Outlet();		
	        
			//company = companyDAO.findById(outletVO.getCompanyId());
			city = cityDAO.findById(outletVO.getCityId());
			outlet = outletDAO.findById(outletVO.getOutletId());
	       
			outlet.setOutletId(outletVO.getOutletId());
			outlet.setOutletCode(outletVO.getOutletCode());
			outlet.setOutletName(outletVO.getOutletName());		
			
			if(outletVO.getCompanyId() !=null){
				company = companyDAO.findById(outletVO.getCompanyId());
				outlet.setCompany(company);
			}
			
			// update pic flag Outlet Emp
			if(outletVO.getPicEmployeeId() !=null){
				List<OutletEmpVO> outEmpList = outletEmpDAO.findOutletEmpByOutletIdList(outletVO.getOutletId());
				if(outEmpList.size() > 0){
					for(int i=0; i<outEmpList.size();i++){
						OutletEmpVO voEmp = (OutletEmpVO)outEmpList.get(i);
						OutletEmp outletEmpUpdate = outletEmpDAO.findById(voEmp.getOutletEmpId());
						if(Integer.parseInt(outletVO.getPicEmployeeId()+"") == Integer.parseInt(voEmp.getEmployeeId()+"")){
					    	 outletEmpUpdate.setPicFlag(CommonConstants.Y);
						}else{
							 outletEmpUpdate.setPicFlag(CommonConstants.N);
						}
						outletEmpUpdate.setUpdateBy(user);
						outletEmpUpdate.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						outletEmpDAO.update(outletEmpUpdate);
					}
				}			
			}
			
			outlet.setCity(city);
			outlet.setAddress(outletVO.getAddress());
			outlet.setPostalCode(outletVO.getPostalCode());
			//outlet.setPicName(outletVO.getPicName());
			//outlet.setPicPhoneno(outletVO.getPicPhoneno());
			outlet.setActiveFlag(outletVO.getActiveFlag());
			outlet.setUpdateBy(user);
			outlet.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			
			outletDAO.update(outlet);
			outletDAO.flush();
		}catch(Exception ex){
			ex.printStackTrace();
			outletDAO.rollback();
		}
	}

	@Override
	public void delete(Long outletId) {
		
		Outlet outlet = outletDAO.findById(outletId);
		outletDAO.delete(outlet);
		outletDAO.flush();
		
		Company company = companyDAO.findById(outlet.getCompany().getCompanyId());	
		company.setOutletQty(company.getOutletQty() - 1);	
		companyDAO.update(company);
		companyDAO.flush();
		
	}

	@Override
	public Outlet findById(Long outletId) {
		
		return outletDAO.findById(outletId);
	}

	@Override
	public List<OutletVO> searchOutletList() {
		
		return outletDAO.searchOutletList();
	}

	@Override
	public List<CityVO> searchCityAll() {
		
		return cityDAO.citySearch();
	}

	@Override
	public List<CompanyVO> searchCompanyList() {
		
		return companyDAO.searchCompanyList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<OutletVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return outletDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return outletDAO.searchCountData(searchCriteria);
	}
	
	public List<Outlet> findOutletByCompany(Long companyId){
		return outletDAO.findOutletByCompany(companyId);
	}

	@Override
	public Outlet findByOutletCode(String outletCode) {
		return outletDAO.findByOutletCode(outletCode);
	}
	
	public List<Outlet> searchDataOutletByCompany(Long companyId, Long outletId){
		return outletDAO.searchDataOutletByCompany(companyId, outletId);
	}

}
