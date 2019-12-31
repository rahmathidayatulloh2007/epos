package com.wo.epos.module.master.city.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.city.dao.CityDAO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.dao.ProvinceDAO;
import com.wo.epos.module.master.province.model.Province;

@ManagedBean(name = "cityService")
@ViewScoped
public class CityServiceImpl implements CityService, Serializable{

	private static final long serialVersionUID = -6455445345534945932L;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;	
	
	@ManagedProperty(value="#{provinceDAO}")
	private ProvinceDAO provinceDAO;	
	
	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}


	public ProvinceDAO getProvinceDAO() {
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	@Override
	public void save(CityVO cityVO, String user) {
		
		City city = new City();
		
		//city.setProvinceId(cityVO.getProvinceId());
		
		Province province = new Province();
		province = provinceDAO.findById(cityVO.getProvinceId());
		city.setProvince(province);
		
		city.setCityCode(cityVO.getCityCode());		
		city.setCityName(cityVO.getCityName());
		city.setActiveFlag(cityVO.getActiveFlag());
		city.setCreateBy(user);
		city.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		cityDAO.save(city);
		cityDAO.flush();
		
		
	}

	@Override
	public void update(CityVO cityVO, String user) {
				
		Province province = new Province();
		province = provinceDAO.findById(cityVO.getProvinceId());
		
		City city = new City();
		city = cityDAO.findById(cityVO.getCityId());
		city.setCityCode(cityVO.getCityCode());		
		city.setCityName(cityVO.getCityName());
		city.setProvince(province);
		city.setActiveFlag(cityVO.getActiveFlag());
		city.setUpdateBy(user);
		city.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		cityDAO.update(city);
		cityDAO.flush();
	}

	@Override
	public void delete(Long cityId) {
		
		City city = new City();
		city = cityDAO.findById(cityId);
		
		cityDAO.delete(city);
		cityDAO.flush();
	}

	@Override
	public City findById(Long cityId) {
		
		return cityDAO.findById(cityId);
	}

	@Override
	public List<CityVO> citySearch() {
		
		
		
		return cityDAO.citySearch();
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public List<CityVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return cityDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return cityDAO.searchCountData(searchCriteria);
	}
	
	public List<Province> showProvince()
	{
		return cityDAO.showProvince();
		
	}

}
