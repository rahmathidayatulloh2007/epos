package com.wo.epos.module.inv.customer.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.customer.dao.CustomerDAO;
import com.wo.epos.module.inv.customer.model.Customer;
import com.wo.epos.module.inv.customer.vo.CustomerVO;
import com.wo.epos.module.master.city.dao.CityDAO;
import com.wo.epos.module.master.city.model.City;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;


@ManagedBean(name = "customerService")
@ViewScoped
public class CustomerServiceImpl implements CustomerService, Serializable{

	private static final long serialVersionUID = 7730331326935516188L;
	
	@ManagedProperty(value="#{customerDAO}")
	private CustomerDAO customerDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{cityDAO}")
	private CityDAO cityDAO;
	
	@ManagedProperty(value="#{outletDAO}")
	private OutletDAO outletDAO;

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
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

	
	
	@Override
	public void save(CustomerVO customerVO, String user) {
		
		Company company = new Company();
		City city = new City();
		Customer customer = new Customer();
		
		customer.setCustomerId(customerVO.getCustomerId());
		customer.setCustomerCode(getCustomerCodeAuto());
		customer.setFullName(customerVO.getFullName());
		customer.setCompany(companyDAO.findById(customerVO.getCompany().getCompanyId()));
		customer.setCity(cityDAO.findById(customerVO.getCity().getCityId()));
		customer.setRegisterOutlet(outletDAO.findById(customerVO.getRegisterOutlet().getOutletId()));
		customer.setAddress(customerVO.getAddress());
		customer.setEmailAddress(customerVO.getEmailAddress());
		customer.setBirthDate(customerVO.getBirthDate());
		customer.setRegisterDate(new Date());
		customer.setTitle(customerVO.getTitle());
		customer.setPhoneNo(customerVO.getPhoneNo());
		customer.setActiveFlag(customerVO.getActiveFlag());
		customer.setCreateBy(user);
		customer.setCreateOn(new Timestamp(System.currentTimeMillis()));
	
		customerDAO.save(customer);
		customerDAO.flush();
		 
	}
	
	public String getCustomerCodeAuto(){
		String result ="";
		Customer customer = customerDAO.getLastCustomerCode();
		if(customer!=null && customer.getCustomerCode()!=null){
			Integer number = new Integer(customer.getCustomerCode().substring(1));
			if(number.intValue()<10){
				result = "C0000"+number.intValue();
			}
			else if(number.intValue()<100){
				result = "C000"+number.intValue();
			}
			else if(number.intValue()<1000){
				result = "C00"+number.intValue();
			}else if(number.intValue()<10000){
				result = "C0"+number.intValue();
			}else if(number.intValue()<100000){
				result = "C"+number.intValue();
			}
		}else{
			result ="C00001";
		}
		return result;
	}

	@Override
	public void update(CustomerVO customerVO, String user) {
		
		Company company = new Company();
		City city = new City();
		Customer customer = new Customer();		
        
		
		customer = customerDAO.findById(customerVO.getCustomerId());
       
		customer.setCustomerId(customerVO.getCustomerId());
		customer.setCustomerCode(customerVO.getCustomerCode());
		customer.setFullName(customerVO.getFullName());
		customer.setCompany(companyDAO.findById(customerVO.getCompany().getCompanyId()));
		customer.setCity(cityDAO.findById(customerVO.getCity().getCityId()));
		customer.setRegisterOutlet(outletDAO.findById(customerVO.getRegisterOutlet().getOutletId()));
		customer.setAddress(customerVO.getAddress());
		customer.setEmailAddress(customerVO.getEmailAddress());
		customer.setBirthDate(customerVO.getBirthDate());
		customer.setRegisterDate(new Date());
		customer.setTitle(customerVO.getTitle());
		customer.setPhoneNo(customerVO.getPhoneNo());
		customer.setActiveFlag(customerVO.getActiveFlag());
		customer.setUpdateBy(user);
		customer.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		customerDAO.update(customer);
		customerDAO.flush();
	}

	@Override
	public void delete(Long customerId) {
		
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		customerDAO.delete(customer);
		customerDAO.flush();
	}

	@Override
	public Customer findById(Long customerId) {
		
		return customerDAO.findById(customerId);
	}

	

	@SuppressWarnings("rawtypes")
	@Override
	public List<CustomerVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return customerDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return customerDAO.searchCountData(searchCriteria);
	}

	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
	}

	@Override
	public List<CustomerVO> searchCustomerList() {
		// TODO Auto-generated method stub
		return customerDAO.searchCustomerList();
	}
	
	

}
