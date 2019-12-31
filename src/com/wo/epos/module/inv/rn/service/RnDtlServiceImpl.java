package com.wo.epos.module.inv.rn.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.rn.dao.RnDtlDAO;
import com.wo.epos.module.inv.rn.model.RnDtl;
import com.wo.epos.module.inv.rn.vo.RnDtlVO;

@ManagedBean(name="rnDtlService")
@ViewScoped
public class RnDtlServiceImpl implements RnDtlService, Serializable{

	private static final long serialVersionUID = -8916741536094254743L;
	
	@ManagedProperty(value="#{rnDtlDAO}")
	private RnDtlDAO rnDtlDAO;
		
	public RnDtlDAO getRnDtlDAO() {
		return rnDtlDAO;
	}

	public void setRnDtlDAO(RnDtlDAO rnDtlDAO) {
		this.rnDtlDAO = rnDtlDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<RnDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		return rnDtlDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		return rnDtlDAO.searchCountData(searchCriteria);
	}

	

	@Override
	public void delete(Long rnDtlId) {
		RnDtl rnDtl = new RnDtl();
		rnDtl = findById(rnDtlId);		
				
		rnDtlDAO.delete(rnDtl);
		rnDtlDAO.flush();
				
		
	}

	@Override
	public RnDtl findById(Long rnDtlId) {
		return rnDtlDAO.findById(rnDtlId);
	}
	
	
}