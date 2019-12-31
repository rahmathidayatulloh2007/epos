package com.wo.epos.module.sales.salesOrder.dao;

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
import com.wo.epos.module.sales.salesOrder.model.SalesOrderDtl;
import com.wo.epos.module.sales.salesOrder.vo.SalesOrderDtlVO;

@ManagedBean(name = "salesOrderDtlDAO")
@ViewScoped
public class SalesOrderDtlDAOImpl extends GenericDAOHibernate<SalesOrderDtl, Long> implements SalesOrderDtlDAO, Serializable{

	private static final long serialVersionUID = 5199113980694053719L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<SalesOrderDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(SalesOrderDtl.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<SalesOrderDtlVO> voList = new ArrayList<SalesOrderDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			SalesOrderDtl soDtl = (SalesOrderDtl)criteria.list().get(i);
			SalesOrderDtlVO vo = new SalesOrderDtlVO();
			vo.setSoDtlId(soDtl.getSoDtlId());
			vo.setSalesOrderId(soDtl.getSalesOrder().getSoId());
			vo.setSalesOrderNo(soDtl.getSalesOrder().getSoNo());
			vo.setSalesOrderDate(soDtl.getSalesOrder().getSoDate());
			vo.setEquipmentId(soDtl.getSalesOrder().getEquipmentId());
			vo.setEquipmentName(soDtl.getSalesOrder().getEquipment().getEquipmentName());
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
		
		Criteria criteria = getSession().createCriteria(SalesOrderDtl.class);
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
	public List<SalesOrderDtlVO> searchListSoDtlVO(Long soId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(SalesOrderDtl.class);
		criteria.createAlias("salesOrder","salesOrder");
		criteria.add(Restrictions.eq("salesOrder.soId", soId));
		
		List<SalesOrderDtlVO> soDtlVOList = new ArrayList<SalesOrderDtlVO>();
		for(int i=0 ; i<criteria.list().size();i++){
			
			SalesOrderDtl salesOrderDtl = (SalesOrderDtl) criteria.list().get(i);
			SalesOrderDtlVO vo = new SalesOrderDtlVO();
			//set data ke VO - belum kelar
			vo.setSoDtlId(salesOrderDtl.getSoDtlId());
			vo.setProductId(salesOrderDtl.getProductId());	
		    vo.setSalesOrderId(salesOrderDtl.getSalesOrder().getSoId());
		    vo.setOrderUm(salesOrderDtl.getOrderUm().getUmId());
		    
		    vo.setProductName(salesOrderDtl.getProduct().getProductName());
		    vo.setSalesOrderNo(salesOrderDtl.getSalesOrder().getSoNo()); 
		    vo.setDiscTypeCode(salesOrderDtl.getDiscType().getParameterDtlCode());
		    vo.setDiscTypeName(salesOrderDtl.getDiscType().getParameterDtlName());
		    vo.setDeliveryStatusCode(salesOrderDtl.getDeliveryStatus().getParameterDtlCode());
		    vo.setPreparationSatusCode(salesOrderDtl.getPreparationStatus().getParameterDtlName());
		    vo.setNotes(salesOrderDtl.getNotes());
		    vo.setOrderUmName(salesOrderDtl.getOrderUm().getUmName());
		    vo.setLineNo(salesOrderDtl.getLineNo());
		   
		    vo.setOrderQty(salesOrderDtl.getOrderQty());
		    vo.setUnitPrice(salesOrderDtl.getProduct().getUnitPrice());

		    vo.setDiscount1(salesOrderDtl.getDiscount1());
		    vo.setDiscount2(salesOrderDtl.getDiscount2());
		    vo.setDiscount3(salesOrderDtl.getDiscount3());
		    vo.setTotalPriceDiscount(salesOrderDtl.getTotalPriceDiscount());

		    vo.setDiscPercent(salesOrderDtl.getDiscPercent());
		    vo.setDiscValue(salesOrderDtl.getDiscValue());
		    vo.setTotalPrice(salesOrderDtl.getUnitPrice()*salesOrderDtl.getOrderQty());
		  
		  
		    vo.setSalesOrderDate(salesOrderDtl.getSalesOrder().getSoDate());
		    soDtlVOList.add(vo);
					
					
		}
		
		return soDtlVOList;
	}

	@Override
	public List<SalesOrderDtl> searchListSoDtl(Long soId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(SalesOrderDtl.class);
		criteria.createAlias("salesOrder","salesOrder");
		criteria.add(Restrictions.eq("salesOrder.soId", soId));
		
		return criteria.list();
		
	}


}
