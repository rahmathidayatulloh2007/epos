package com.wo.epos.module.inv.um.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.um.dao.UmDAO;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;

@ManagedBean(name="umService")
@ViewScoped
public class UmServiceImpl implements UmService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{umDAO}")
	private UmDAO umDAO;
	
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public UmDAO getUmDAO() {
		return umDAO;
	}

	public void setUmDAO(UmDAO umDAO) {
		this.umDAO = umDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<UmVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return umDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return umDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(UmVO umVO, String user) {
		 Um um = new Um();		 
		 um.setUmName(umVO.getUmName());
		 um.setUmDesc(umVO.getUmDesc());
		 if(umVO.getCompanyId() !=null){
		   um.setCompanyId(umVO.getCompanyId());		 
		   Company company = companyDAO.findById(umVO.getCompanyId());
		   um.setCompany(company);		
		 }
		 
		 um.setActiveFlag(umVO.getActiveFlag());
		 um.setCreateBy(user);
		 um.setCreateOn(new Timestamp(System.currentTimeMillis()));
		 
		 umDAO.save(um);
		 umDAO.flush();
		 
	}

	@Override
	public void update(UmVO umVO, String user) {
		Um um = new Um();	
        
		um = umDAO.findById(umVO.getUmId());
		
		if(umVO.getCompanyId() !=null){
		   um.setCompanyId(umVO.getCompanyId());
		 
		   Company company = companyDAO.findById(umVO.getCompanyId());
		   um.setCompany(company);		
		}
		
		um.setUmName(umVO.getUmName());
		um.setUmDesc(umVO.getUmDesc());
		um.setActiveFlag(umVO.getActiveFlag());
		um.setUpdateBy(user);
		um.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
			
		umDAO.update(um);
		umDAO.flush();
		
	}

	@Override
	public void delete(Long umId) {
		Um um = new Um();
		um.setUmId(umId);
		
		umDAO.delete(um);
		umDAO.flush();
	}

	@Override
	public Um findById(Long umId) {
		return umDAO.findById(umId);
	}

	@Override
	public List<UmVO> searchUmList() {
		
		return umDAO.searchUmList();
	}

	@Override
	public List<UmVO> searchUmCompany(Long companyId) {
		return umDAO.searchUmCompany(companyId);
	}

	@Override
	public Um findByName(String umName) {
		return umDAO.findByName(umName);
	}
		
}
