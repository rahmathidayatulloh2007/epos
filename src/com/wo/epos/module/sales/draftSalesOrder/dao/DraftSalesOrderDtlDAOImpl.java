package com.wo.epos.module.sales.draftSalesOrder.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrderDtl;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;


@ManagedBean(name = "draftSalesOrderDtlDAO")
@ViewScoped
public class DraftSalesOrderDtlDAOImpl extends GenericDAOHibernate<DraftSalesOrderDtl, Long> implements DraftSalesOrderDtlDAO, Serializable{

	private static final long serialVersionUID = 5199113980694053719L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<DraftSalesOrderDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(DraftSalesOrderDtl.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<DraftSalesOrderDtlVO> voList = new ArrayList<DraftSalesOrderDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			DraftSalesOrderDtl soDtl = (DraftSalesOrderDtl)criteria.list().get(i);
			DraftSalesOrderDtlVO vo = new DraftSalesOrderDtlVO();
			vo.setSoDtlId(soDtl.getSoDtlId());
			vo.setSalesOrderId(soDtl.getDraftSalesOrder().getSoId());
			vo.setSalesOrderNo(soDtl.getDraftSalesOrder().getSoNo());
			vo.setSalesOrderDate(soDtl.getDraftSalesOrder().getSoDate());
			vo.setEquipmentId(soDtl.getDraftSalesOrder().getEquipmentId());
			vo.setEquipmentName(soDtl.getDraftSalesOrder().getEquipment().getEquipmentName());
			vo.setProductId(soDtl.getProductId());
			vo.setProductName(soDtl.getProduct().getProductName());
			vo.setOrderQty(soDtl.getOrderQty());
		    vo.setOrderUm(soDtl.getOrderUmId());
			vo.setOrderUmName(soDtl.getOrderUm().getUmName());
			vo.setNotes(soDtl.getNotes());
			vo.setPreparationSatusCode(soDtl.getPreparationStatusCode());
			vo.setDeliveryStatusCode(soDtl.getDeliveryStatusCode());
			vo.setActiveFlag(soDtl.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(DraftSalesOrderDtl.class);
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
		crit.createAlias("salesOrder", "salesOrder");
	    
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
							
				if(searchVal.getSearchColumn().compareTo("preparationStatus")==0){
					crit.add(Restrictions.eq("preparationStatusCode", searchVal.getSearchValueAsString().trim()));
				}	
				if(searchVal.getSearchColumn().compareTo("salesOrderDate")==0){
					crit.add(Restrictions.eq("salesOrder.soDate", searchVal.getSearchValue()));
				}
				
			}
		}
	}

	@Override
	public List<DraftSalesOrderDtlVO> searchListSoDtlVO(Long soId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(DraftSalesOrderDtl.class);
		criteria.createAlias("draftSalesOrder","draftSalesOrder");
		criteria.add(Restrictions.eq("draftSalesOrder.soId", soId));
		
		List<DraftSalesOrderDtlVO> soDtlVOList = new ArrayList<DraftSalesOrderDtlVO>();
		for(int i=0 ; i<criteria.list().size();i++){
			
			DraftSalesOrderDtl salesOrderDtl = (DraftSalesOrderDtl) criteria.list().get(i);
			DraftSalesOrderDtlVO vo = new DraftSalesOrderDtlVO();
			//set data ke VO - belum kelar
			vo.setSoDtlId(salesOrderDtl.getSoDtlId());
			vo.setProductId(salesOrderDtl.getProductId());	
		    vo.setSalesOrderId(salesOrderDtl.getDraftSalesOrder().getSoId());
		    vo.setOrderUm(salesOrderDtl.getOrderUm().getUmId());
		    
		    vo.setProductName(salesOrderDtl.getProduct().getProductName());
		    vo.setSalesOrderNo(salesOrderDtl.getDraftSalesOrder().getSoNo()); 
		    vo.setDiscTypeCode(salesOrderDtl.getDiscType().getParameterDtlCode());
		    vo.setDiscTypeName(salesOrderDtl.getDiscType().getParameterDtlName());
		    vo.setDeliveryStatusCode(salesOrderDtl.getDeliveryStatus().getParameterDtlCode());
		    vo.setPreparationSatusCode(salesOrderDtl.getPreparationStatus().getParameterDtlName());
		    vo.setNotes(salesOrderDtl.getNotes());
		    vo.setOrderUmName(salesOrderDtl.getOrderUm().getUmName());
		    vo.setLineNo(salesOrderDtl.getLineNo());

		    vo.setOrderQty(salesOrderDtl.getOrderQty());
		    vo.setUnitPrice(salesOrderDtl.getProduct().getUnitPrice());
		    vo.setDiscPercent(salesOrderDtl.getDiscPercent());
		    vo.setDiscValue(salesOrderDtl.getDiscValue());
		    vo.setTotalPrice(salesOrderDtl.getUnitPrice()*salesOrderDtl.getOrderQty());
		  
		    vo.setTotalPriceDiscount(salesOrderDtl.getTotalPriceDiscount());
		    vo.setDiscount1(salesOrderDtl.getDiscount1());
		    vo.setDiscount2(salesOrderDtl.getDiscount2());
		    vo.setDiscount3(salesOrderDtl.getDiscount3());
		  
		    vo.setSalesOrderDate(salesOrderDtl.getDraftSalesOrder().getSoDate());
		    soDtlVOList.add(vo);
					
					
		}
		
		return soDtlVOList;
	}

	@Override
	public List<DraftSalesOrderDtl> searchListSoDtl(Long soId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(DraftSalesOrderDtl.class);
		criteria.createAlias("draftSalesOrder","draftSalesOrder");
		criteria.add(Restrictions.eq("draftSalesOrder.soId", soId));
		
		return criteria.list();
		
	}

}
