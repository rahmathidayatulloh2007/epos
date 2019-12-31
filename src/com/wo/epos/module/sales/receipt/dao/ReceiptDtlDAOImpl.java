package com.wo.epos.module.sales.receipt.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.receipt.model.ReceiptDtl;
import com.wo.epos.module.sales.receipt.vo.ReceiptDtlVO;

@ManagedBean(name = "receiptDtlDAO")
@ViewScoped
public class ReceiptDtlDAOImpl extends GenericDAOHibernate<ReceiptDtl, Long> implements ReceiptDtlDAO, Serializable{
    
	private static final long serialVersionUID = 3295026854651070529L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ReceiptDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(ReceiptDtl.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<ReceiptDtlVO> voList = new ArrayList<ReceiptDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			ReceiptDtl receipt = (ReceiptDtl)criteria.list().get(i);
			ReceiptDtlVO vo = new ReceiptDtlVO();
			vo.setReceiptDtlId(receipt.getReceiptDtlId());
			vo.setActiveFlag(receipt.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(ReceiptDtl.class);
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
