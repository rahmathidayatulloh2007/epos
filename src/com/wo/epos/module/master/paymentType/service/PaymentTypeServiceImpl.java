package com.wo.epos.module.master.paymentType.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.paymentType.dao.PaymentTypeDAO;
import com.wo.epos.module.master.paymentType.model.PaymentType;
import com.wo.epos.module.master.paymentType.vo.PaymentTypeVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="paymentTypeService")
@ViewScoped
public class PaymentTypeServiceImpl implements PaymentTypeService, Serializable{
	
	private static final long serialVersionUID = 2451003240638479603L;

	@ManagedProperty(value="#{paymentTypeDAO}")
	private PaymentTypeDAO paymentTypeDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	

	public PaymentTypeDAO getPaymentTypeDAO() {
		return paymentTypeDAO;
	}

	public void setPaymentTypeDAO(PaymentTypeDAO paymentTypeDAO) {
		this.paymentTypeDAO = paymentTypeDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PaymentTypeVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return paymentTypeDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return paymentTypeDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(PaymentTypeVO paymentTypeVO, String user) {
		
		Company company = new Company();
		ParameterDtl paramDtlPayMethod = new ParameterDtl();
		
		company = companyDAO.findById(paymentTypeVO.getCompanyId());
		paramDtlPayMethod = parameterDAO.findByDetailId(paymentTypeVO.getPaymentMethodCode());
		
		PaymentType paymentType = new PaymentType();
		
		paymentType.setCompany(company);
		paymentType.setPaytypeCode(paymentTypeVO.getPaytypeCode());
		paymentType.setPaytypeName(paymentTypeVO.getPaytypeName());
		paymentType.setPaymentMethod(paramDtlPayMethod);
		
		if(paymentTypeVO.getPaytypeValue() != null){
			paymentType.setPaytypeValue(paymentTypeVO.getPaytypeValue());
		}
	
		paymentType.setActiveFlag(paymentTypeVO.getActiveFlag());
		paymentType.setCreateBy(user);
		paymentType.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		paymentTypeDAO.save(paymentType);
		paymentTypeDAO.flush();
		
	}

	@Override
	public void update(PaymentTypeVO paymentTypeVO, String user) {
		
		Company company = new Company();
		ParameterDtl paramDtlPayMethod = new ParameterDtl();
		
		company = companyDAO.findById(paymentTypeVO.getCompanyId());
		paramDtlPayMethod = parameterDAO.findByDetailId(paymentTypeVO.getPaymentMethodCode());
		
		PaymentType paymentType = new PaymentType();
		paymentType = paymentTypeDAO.findById(paymentTypeVO.getPaytypeId());
		paymentType.setCompany(company);
		paymentType.setPaytypeCode(paymentTypeVO.getPaytypeCode());
		paymentType.setPaytypeName(paymentTypeVO.getPaytypeName());
		paymentType.setPaymentMethod(paramDtlPayMethod);
		
		if(paymentTypeVO.getPaytypeValue() != null){
			paymentType.setPaytypeValue(paymentTypeVO.getPaytypeValue());
		}

		paymentType.setActiveFlag(paymentTypeVO.getActiveFlag());
		paymentType.setUpdateBy(user);
		paymentType.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		paymentTypeDAO.update(paymentType);
		paymentTypeDAO.flush();
		
	}

	@Override
	public void delete(Long paymentTypeId) {
		
		PaymentType paymentType = new PaymentType();
		paymentType.setPaytypeId(paymentTypeId);
		paymentTypeDAO.delete(paymentType);
		paymentTypeDAO.flush();
	}

	@Override
	public PaymentType findById(Long paymentTypeId) {
		
		return paymentTypeDAO.findById(paymentTypeId);
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
	public List<PaymentTypeVO> searchDataPaymentByCompany(Long companyId) {
		return paymentTypeDAO.searchDataPaymentByCompany(companyId);
	}

}
