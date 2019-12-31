package com.wo.epos.module.inv.transferItem.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.transferItem.model.TransferItemDtl;
import com.wo.epos.module.inv.transferItem.vo.TransferItemDtlVO;

@ManagedBean(name = "transferItemDtlDAO")
@ViewScoped
public class TransferItemDtlDAOImpl extends GenericDAOHibernate<TransferItemDtl, Long> 
        implements TransferItemDtlDAO, Serializable {
	
	private static final long serialVersionUID = 5546585637379065768L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<TransferItemDtlVO> searchData(
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