package com.wo.epos.module.inv.DO.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.DO.model.DODtl;
import com.wo.epos.module.inv.DO.vo.DODtlVO;

@ManagedBean(name = "DODtlDAO")
@ViewScoped
public class DODtlDAOImpl extends GenericDAOHibernate<DODtl, Long> 
        implements DODtlDAO, Serializable {
	
	private static final long serialVersionUID = 5546585637379065768L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<DODtlVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
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


}