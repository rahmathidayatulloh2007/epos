package com.wo.epos.module.master.province.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.province.dao.ProvinceDAO;
import com.wo.epos.module.master.province.model.Province;
import com.wo.epos.module.master.province.vo.ProvinceVO;

@ManagedBean(name="provinceService")
@ViewScoped
public class ProvinceServiceImpl implements ProvinceService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5927313745557525240L;
	@ManagedProperty(value="#{provinceDAO}")
	private ProvinceDAO provinceDAO;
	
		
	public ProvinceDAO getProvinceDAO() {
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	@Override	
	public void save(ProvinceVO provinceVo, String user) {
			
		Province province = new Province();
		
		province.setProvinceCode(provinceVo.getProvinceCode());
		province.setProvinceName(provinceVo.getProvinceName());
		province.setActiveFlag(provinceVo.getActiveFlag());
		province.setCreateBy(user);
		province.setCreateOn(new Timestamp(System.currentTimeMillis()));		
		
		
		provinceDAO.save(province);
		provinceDAO.flush();
		
		
	}

	@Override
	public void update(ProvinceVO provinceVo, String user) {
		
        Province province = new Province();
        
        province = provinceDAO.findById(provinceVo.getProvinceId());
		province.setProvinceCode(provinceVo.getProvinceCode());
		province.setProvinceName(provinceVo.getProvinceName());
		province.setActiveFlag(provinceVo.getActiveFlag());
		province.setUpdateBy(user);
		province.setUpdateOn(new Timestamp(System.currentTimeMillis()));		
		
		
		provinceDAO.update(province);
		provinceDAO.flush();
		
	}

	@Override
	public void delete(Long provinceId) {
		
		Province province = new Province();	        
	    
		province = provinceDAO.findById(provinceId);
		
		provinceDAO.delete(province);
		provinceDAO.flush();
		
	}

	
	@Override
	public Province findById(Long provinceId)
	{
		return provinceDAO.findById(provinceId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProvinceVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return provinceDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return provinceDAO.searchCountData(searchCriteria);
	}

	@Override
	public List<ProvinceVO> provinceSearch() {
		// TODO Auto-generated method stub
		return provinceDAO.provinceSearch();
	}
	
}
