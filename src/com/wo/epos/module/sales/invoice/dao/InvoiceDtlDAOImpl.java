package com.wo.epos.module.sales.invoice.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.invoice.model.InvoiceDtl;
import com.wo.epos.module.sales.invoice.vo.InvoiceDtlVO;
import com.wo.epos.module.sales.salesOrder.model.SalesOrder;

@ManagedBean(name = "invoiceDtlDAO")
@ViewScoped
public class InvoiceDtlDAOImpl extends GenericDAOHibernate<InvoiceDtl, Long> implements InvoiceDtlDAO, Serializable{

	private static final long serialVersionUID = 8111501853568620376L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<InvoiceDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(InvoiceDtl.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<InvoiceDtlVO> voList = new ArrayList<InvoiceDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			InvoiceDtl inv = (InvoiceDtl)criteria.list().get(i);
			InvoiceDtlVO vo = new InvoiceDtlVO();
			vo.setSoInvDtlId(inv.getSoInvDtlId());
			vo.setActiveFlag(inv.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(SalesOrder.class);
		decorateCriteria(criteria, searchCriteria);
		 
		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}
	
	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit,
			List<? extends SearchObject> searchCriteria) {
	}

	

}
