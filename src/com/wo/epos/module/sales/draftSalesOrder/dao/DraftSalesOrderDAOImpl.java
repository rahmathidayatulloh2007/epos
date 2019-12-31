package com.wo.epos.module.sales.draftSalesOrder.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.systemProperty.dao.SystemPropertyDAO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrder;
import com.wo.epos.module.sales.draftSalesOrder.model.DraftSalesOrderDtl;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderDtlVO;
import com.wo.epos.module.sales.draftSalesOrder.vo.DraftSalesOrderVO;

@ManagedBean(name = "draftSalesOrderDAO")
@ViewScoped
public class DraftSalesOrderDAOImpl extends GenericDAOHibernate<DraftSalesOrder, Long> implements DraftSalesOrderDAO, Serializable{

	private static final long serialVersionUID = 3958503960890547493L;

	@ManagedProperty(value="#{systemPropertyDAO}")
	private SystemPropertyDAO systemPropertyDAO;
	
	
	public SystemPropertyDAO getSystemPropertyDAO() {
		return systemPropertyDAO;
	}

	public void setSystemPropertyDAO(SystemPropertyDAO systemPropertyDAO) {
		this.systemPropertyDAO = systemPropertyDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DraftSalesOrderVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(DraftSalesOrder.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<DraftSalesOrderVO> voList = new ArrayList<DraftSalesOrderVO>();
		for(int i=0; i<criteria.list().size(); i++){
			DraftSalesOrder salesOrder = (DraftSalesOrder)criteria.list().get(i);
			DraftSalesOrderVO vo = new DraftSalesOrderVO();
			
			vo.setSoId(salesOrder.getSoId());
			vo.setSoNo(salesOrder.getSoNo());
			if(salesOrder.getCustomer()!=null){
			vo.setCustomerName(salesOrder.getCustomer().getCustomerName());
			}
			vo.setProductResume(salesOrder.getProductResume());
			vo.setSoDate(salesOrder.getSoDate());
			vo.setActiveFlag(salesOrder.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(DraftSalesOrder.class);
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
		if (searchCriteria != null) {
			for (SearchObject searchVal : searchCriteria) {
				if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
					 String sSplit = searchVal.getSearchValueAsString().trim();
						crit.add(Restrictions.or(
								Restrictions.ilike("soNo", sSplit,MatchMode.ANYWHERE),
								Restrictions.ilike("productResume", sSplit,MatchMode.ANYWHERE)));
				}	
		
				
			}
		}
		crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@Override
	public String searchSalesOrderMax(String year, String yearMonth,
			String yearMonthDate) {
		String numberSo = null;
			
		String sql = "SELECT MAX(SO_NO) FROM POS_DSO";
		
		if(year !=null && !year.equals("")){
			sql = sql + " WHERE SUBSTR(SO_NO,1,5)  = '"+year+"-' ";
		}else if(yearMonth !=null && !yearMonth.equals("")){
			sql = sql + " WHERE SUBSTR(SO_NO,1,7) = '"+yearMonth+"-' ";
		}else if(yearMonthDate !=null && !yearMonthDate.equals("")){
			sql = sql + " WHERE SUBSTR(SO_NO,1,9) = '"+yearMonthDate+"-' ";
		}
		
		Query query = getSession().createSQLQuery(sql);
		        
        if(query.list().size() > 0){
			if (query.uniqueResult() != null
					&& !query.uniqueResult().toString().equals("")) {
				numberSo = query.uniqueResult().toString();
        	} 
        }
			
		return numberSo;
	}
	
	@Override
	public String runningNumberSo(String systemPropertyName, Long companyId){
		String runningNumberSo = null;
		SystemProperty systemProperty = new SystemProperty();
		systemProperty =  systemPropertyDAO.searchSystemPropertyNameAndCompany(CommonConstants.SO_NUMBERFORMAT, companyId);
		
		if(systemProperty !=null){
			if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SONUMBER_YEARLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");				   
				String year =  sdfYear.format(new Date());
				String soNumber = searchSalesOrderMax(year, null, null);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					
					splitInt = splitInt + 1;
					
					if ((splitInt + "").length() == 5) {
						number = "" + (splitInt );
					} else if ((splitInt + "").length() == 4) {
						number = "0" + (splitInt );
					} else if ((splitInt + "").length() == 3) {
						number = "00" + (splitInt );
					} else if ((splitInt + "").length() == 2) {
						number = "000" + (splitInt );
					} else if((splitInt + "").length() == 1){
						number = "0000" + (splitInt );
					}
					
					runningNumberSo = year + "-" + number;
					
				} else {
					runningNumberSo = year + "-00001";
				}
				
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SONUMBER_MONTHLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMM");				   
				String yearMonth =  sdfYear.format(new Date());
				String soNumber = searchSalesOrderMax(null, yearMonth, null);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					
					Integer splitInt = Integer.parseInt(split[1]);
					splitInt = splitInt + 1;
					if ((splitInt + "").length() == 4) {
						number = "" + (splitInt );
					} else if ((splitInt + "").length() == 3) {
						number = "0" + (splitInt );
					} else if ((splitInt + "").length() == 2) {
						number = "00" + (splitInt);
					} else if((splitInt + "").length() == 1){
						number = "000" + (splitInt );
					}
					
					runningNumberSo = yearMonth + "-" + number;
					
				} else {
					runningNumberSo = yearMonth + "-0001";
				}
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SONUMBER_DAILY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMMdd");				   
				String yearMonthDate =  sdfYear.format(new Date());
				String soNumber = searchSalesOrderMax(null, null, yearMonthDate);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					splitInt = splitInt + 1;
					if ((splitInt + "").length() == 3) {
						number = "" + (splitInt );
					} else if ((splitInt + "").length() == 2) {
						number = "0" + (splitInt );
					} else if((splitInt + "").length() == 1){
						number = "00" + (splitInt );
					}
					
					runningNumberSo = yearMonthDate + "-" + number;
					
				} else {
					runningNumberSo = yearMonthDate + "-001";
				}
			}
		}
		
		return runningNumberSo;
	}

	@Override
	public DraftSalesOrderVO searchDataSoByEquipment(Long equipmentId,
			String equipmentStatus) {
		Criteria criteria = getSession().createCriteria(DraftSalesOrder.class);
		criteria.createAlias("equipment", "equipment", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("equipment.equipmentId", equipmentId));
		criteria.add(Restrictions.eq("equipment.equipmentStatusCode", equipmentStatus));
		criteria.add(Restrictions.or(Restrictions.eq("statusCode", CommonConstants.SO_NEW), 
				     Restrictions.or(Restrictions.eq("statusCode", CommonConstants.SO_BILL), 
				     Restrictions.eq("statusCode" , CommonConstants.SO_PROCEED))));
		
			
		DraftSalesOrderVO salesOrderVoHeader = new DraftSalesOrderVO();
		if(criteria.uniqueResult() !=null){
			DraftSalesOrder salesOrder = (DraftSalesOrder)criteria.uniqueResult();
			 salesOrderVoHeader.setSoId(salesOrder.getSoId());
			 salesOrderVoHeader.setOutletId(salesOrder.getOutletId());
			 if(salesOrder.getOutlet() !=null){
				 salesOrderVoHeader.setCompanyId(salesOrder.getOutlet().getCompany().getCompanyId());
			 }
			 salesOrderVoHeader.setEquipmentId(salesOrder.getEquipmentId());
			 salesOrderVoHeader.setDeliveryCost(salesOrder.getDeliveryCost());
			 salesOrderVoHeader.setItemAmount(salesOrder.getItemAmount());			 
			 salesOrderVoHeader.setEquipmentName(salesOrder.getEquipment().getEquipmentName());
			 salesOrderVoHeader.setSoNo(salesOrder.getSoNo());
			 salesOrderVoHeader.setSoDate(salesOrder.getSoDate());
			 salesOrderVoHeader.setNotes(salesOrder.getNotes()); 
			 salesOrderVoHeader.setSoTypeCode(salesOrder.getSoTypeCode());
			 salesOrderVoHeader.setSoTypeName(salesOrder.getSoType().getParameterDtlName());
			 salesOrderVoHeader.setDeliveryStatusCode(salesOrder.getDeliveryStatusCode());
			 salesOrderVoHeader.setTaxTypeCode(salesOrder.getTaxTypeCode());
			 salesOrderVoHeader.setTaxValue(salesOrder.getTaxValue());
			 salesOrderVoHeader.setProductResume(salesOrder.getProductResume());
			 salesOrderVoHeader.setStatusCode(salesOrder.getStatusCode());
			 salesOrderVoHeader.setSubDiskon(new Double(0));
			 
			 List<DraftSalesOrderDtlVO> salesOrderDtlVoList = new ArrayList<DraftSalesOrderDtlVO>();
			 Double subTotal = new Double(0);
			 for(int i=0; i<salesOrder.getSalesOrderDtlList().size();i++){
				 DraftSalesOrderDtl dtl = (DraftSalesOrderDtl)salesOrder.getSalesOrderDtlList().get(i);
				 DraftSalesOrderDtlVO soDtlVo = new DraftSalesOrderDtlVO();
					soDtlVo.setSalesOrderId(dtl.getDraftSalesOrder().getSoId());
					soDtlVo.setProductId(dtl.getProductId());
					soDtlVo.setProductName(dtl.getProduct().getProductName());
					soDtlVo.setLineNo(dtl.getLineNo());
					soDtlVo.setOrderQty(dtl.getOrderQty());
					soDtlVo.setOrderUm(dtl.getOrderUmId());
					soDtlVo.setUnitPrice(dtl.getUnitPrice());
					soDtlVo.setTotalPrice(dtl.getOrderQty().doubleValue() * dtl.getUnitPrice().doubleValue());
					soDtlVo.setDiscTypeCode(dtl.getDiscTypeCode());					
					if(dtl.getDiscPercent() !=null){
						soDtlVo.setDiscPercent(dtl.getDiscPercent());
					}else{
						soDtlVo.setDiscPercent(new Double(0));
					}
					if(dtl.getDiscValue() !=null){
					    soDtlVo.setDiscValue(dtl.getDiscValue());
					}else{
						soDtlVo.setDiscValue(new Double(0));
					}
					
					soDtlVo.setDeliveryStatusCode(dtl.getDeliveryStatusCode());
					soDtlVo.setPreparationSatusCode(dtl.getPreparationStatusCode());
					soDtlVo.setIngredientFlag(dtl.getProduct().getIngredientFlag());
					 
					soDtlVo.setNotes(soDtlVo.getNotes());
					soDtlVo.setActiveFlag(CommonConstants.Y);
					
					subTotal = subTotal.doubleValue() + soDtlVo.getTotalPrice().doubleValue();
					 
					salesOrderDtlVoList.add(soDtlVo);
			 }
			 
			 salesOrderVoHeader.setSalesOrderDtlVoList(salesOrderDtlVoList);
			 salesOrderVoHeader.setSubTotal(subTotal);
			 
		}else{
			salesOrderVoHeader = null;
		}
		
		return salesOrderVoHeader;
	}

	@Override
	public List<DraftSalesOrderVO> searchDraftSalesOrderList() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(DraftSalesOrder.class);
		criteria.add(Restrictions.eq("activeFlag", "Y"));
		criteria.addOrder(Order.asc("soId"));
		
		List<DraftSalesOrderVO> voList = new ArrayList<DraftSalesOrderVO>();	
		
		for(int i=0;i<criteria.list().size(); i++){
			 
			 DraftSalesOrder salesOrder = (DraftSalesOrder)criteria.list().get(i);
			 DraftSalesOrderVO  salesOrderVoHeader = new DraftSalesOrderVO();
			 
			 salesOrderVoHeader.setSoId(salesOrder.getSoId());
			 salesOrderVoHeader.setOutletId(salesOrder.getOutletId());
			 if(salesOrder.getOutlet() !=null){
				 salesOrderVoHeader.setCompanyId(salesOrder.getOutlet().getCompany().getCompanyId());
			 }
			 salesOrderVoHeader.setEquipmentId(salesOrder.getEquipmentId());
			 salesOrderVoHeader.setDeliveryCost(salesOrder.getDeliveryCost());
			 salesOrderVoHeader.setItemAmount(salesOrder.getItemAmount());			 
			 salesOrderVoHeader.setEquipmentName(salesOrder.getEquipment().getEquipmentName());
			 salesOrderVoHeader.setSoNo(salesOrder.getSoNo());
			 salesOrderVoHeader.setSoDate(salesOrder.getSoDate());
			 salesOrderVoHeader.setNotes(salesOrder.getNotes()); 
			 salesOrderVoHeader.setSoTypeCode(salesOrder.getSoTypeCode());
			 salesOrderVoHeader.setSoTypeName(salesOrder.getSoType().getParameterDtlName());
			 salesOrderVoHeader.setDeliveryStatusCode(salesOrder.getDeliveryStatusCode());
			 salesOrderVoHeader.setTaxTypeCode(salesOrder.getTaxTypeCode());
			 salesOrderVoHeader.setTaxValue(salesOrder.getTaxValue());
			 salesOrderVoHeader.setProductResume(salesOrder.getProductResume());
			 salesOrderVoHeader.setStatusCode(salesOrder.getStatusCode());
			 salesOrderVoHeader.setSubDiskon(new Double(0));
		
			 List<DraftSalesOrderDtlVO> salesOrderDtlVoList = new ArrayList<DraftSalesOrderDtlVO>();
			 Double subTotal = new Double(0);
			 for(int z=0; i<salesOrder.getSalesOrderDtlList().size();z++){
				 DraftSalesOrderDtl dtl = (DraftSalesOrderDtl)salesOrder.getSalesOrderDtlList().get(z);
				 DraftSalesOrderDtlVO soDtlVo = new DraftSalesOrderDtlVO();
					soDtlVo.setSalesOrderId(dtl.getDraftSalesOrder().getSoId());
					soDtlVo.setProductId(dtl.getProductId());
					soDtlVo.setProductName(dtl.getProduct().getProductName());
					soDtlVo.setLineNo(dtl.getLineNo());
					soDtlVo.setOrderQty(dtl.getOrderQty());
					soDtlVo.setOrderUm(dtl.getOrderUmId());
					soDtlVo.setUnitPrice(dtl.getUnitPrice());
					soDtlVo.setTotalPrice(dtl.getOrderQty().doubleValue() * dtl.getUnitPrice().doubleValue());
					soDtlVo.setDiscTypeCode(dtl.getDiscTypeCode());					
					if(dtl.getDiscPercent() !=null){
						soDtlVo.setDiscPercent(dtl.getDiscPercent());
					}else{
						soDtlVo.setDiscPercent(new Double(0));
					}
					if(dtl.getDiscValue() !=null){
					    soDtlVo.setDiscValue(dtl.getDiscValue());
					}else{
						soDtlVo.setDiscValue(new Double(0));
					}
					
					soDtlVo.setDeliveryStatusCode(dtl.getDeliveryStatusCode());
					soDtlVo.setPreparationSatusCode(dtl.getPreparationStatusCode());
					soDtlVo.setIngredientFlag(dtl.getProduct().getIngredientFlag());
					 
					soDtlVo.setNotes(soDtlVo.getNotes());
					soDtlVo.setActiveFlag(CommonConstants.Y);
					
					subTotal = subTotal.doubleValue() + soDtlVo.getTotalPrice().doubleValue();
					 
					salesOrderDtlVoList.add(soDtlVo);
			 }
			 
			 salesOrderVoHeader.setSalesOrderDtlVoList(salesOrderDtlVoList);
			 salesOrderVoHeader.setSubTotal(subTotal);
		}
		return voList;
		
		
	}
	
	@Override
	public List<DraftSalesOrderVO> searchSalesOrderListCustomerId(Long customerId) {

		Criteria criteria = getSession().createCriteria(DraftSalesOrder.class);
		criteria.createAlias("customer", "customer", CriteriaSpecification.INNER_JOIN);

		if (customerId != null) {
			criteria.add(Restrictions.eq("customer.customerId", customerId));
		} else {
			criteria.add(Restrictions.isNull("customer.customerId"));
		}

		criteria.addOrder(Order.asc("soDate"));

		List<DraftSalesOrderVO> SalesOrderVOList = new ArrayList<DraftSalesOrderVO>();
		if (criteria != null) {
			for (int i = 0; i < criteria.list().size(); i++) {
				DraftSalesOrder salesOrder = (DraftSalesOrder) criteria.list().get(i);
				DraftSalesOrderVO soVO = new DraftSalesOrderVO();
				soVO.setSoId(salesOrder.getSoId());
				soVO.setSoNo(salesOrder.getSoNo());
				soVO.setCustomerId(salesOrder.getCustomerId());
				soVO.setSoDate(salesOrder.getSoDate());
				soVO.setStatusCode(salesOrder.getStatusCode());

				SalesOrderVOList.add(soVO);

			}
		}

		return SalesOrderVOList;
	}

	@Override
	public List<DraftSalesOrder> findSalesOrderByCustomer(Long customerId) {
		Criteria criteria = getSession().createCriteria(DraftSalesOrder.class);
		criteria.createAlias("customer", "customer", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("customer.customerId", customerId));
		criteria.addOrder(Order.asc("soNo"));

		return criteria.list();
	}
	
	@Override
	public List<DraftSalesOrder> findSalesOrder() {
		Criteria criteria = getSession().createCriteria(DraftSalesOrder.class);

		criteria.addOrder(Order.asc("soNo"));

		return criteria.list();
	}
	
	
}
