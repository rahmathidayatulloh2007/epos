package com.wo.epos.module.sales.salesOrder.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.salesOrder.dao.SalesOrderDtlDAO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

@ManagedBean(name = "salesOrderDtlService")
@ViewScoped
public class SalesOrderDtlServiceImpl implements SalesOrderDtlService, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9107896477419300737L;
	
	@ManagedProperty(value = "#{salesOrderDtlDAO}")
	private SalesOrderDtlDAO salesOrderDtlDAO;

	@Override
	public List<SalesOrderDtlVO> searchData(List<? extends SearchObject> searchCriteria, int first, int pageSize,
			String sortField, boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public List<SalesOrderDtlVO> searchListSoDtlVO(Long soId) {
		// TODO Auto-generated method stub
		return salesOrderDtlDAO.searchListSoDtlVO(soId);
	}*/

	@Override
	public List<SalesOrderDtl> searchListSoDtl(Long soId) {
		// TODO Auto-generated method stub
		return salesOrderDtlDAO.searchListSoDtl(soId);
	}

	public SalesOrderDtlDAO getSalesOrderDtlDAO() {
		return salesOrderDtlDAO;
	}

	public void setSalesOrderDtlDAO(SalesOrderDtlDAO salesOrderDtlDAO) {
		this.salesOrderDtlDAO = salesOrderDtlDAO;
	}
	
	

}
