package com.wo.epos.module.purchasing.supplier.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;

@ManagedBean(name = "supplierItemDAO")
@ViewScoped
public class SupplierItemDAOImpl  extends GenericDAOHibernate<SupplierItem, Long> 
implements SupplierItemDAO, Serializable {

	private static final long serialVersionUID = 2506534443798631402L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<SupplierItemVO> searchData(
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
