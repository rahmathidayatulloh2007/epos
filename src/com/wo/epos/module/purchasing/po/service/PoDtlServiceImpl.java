package com.wo.epos.module.purchasing.po.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.purchasing.po.dao.PoDtlDAO;
import com.wo.epos.module.purchasing.po.model.PoDtl;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;

@ManagedBean(name="poDtlService")
@ViewScoped
public class PoDtlServiceImpl implements PoDtlService, Serializable {

	private static final long serialVersionUID = 4867243729271664961L;
	
	@ManagedProperty(value="#{poDtlDAO}")
	private PoDtlDAO poDtlDAO;
	
	
	public PoDtlDAO getPoDtlDAO() {
		return poDtlDAO;
	}

	public void setPoDtlDAO(PoDtlDAO poDtlDAO) {
		this.poDtlDAO = poDtlDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PoDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(PoDtlVO poDtlVO, String user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PoDtlVO poDtlVO, String user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long poDtlId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PoDtl findById(Long poDtlId) {
		return poDtlDAO.findById(poDtlId);
	}

	@Override
	public List<PoDtlVO> searchListPoDtlVO(Long poId) {
		return poDtlDAO.searchListPoDtlVO(poId);
	}
	
	
}