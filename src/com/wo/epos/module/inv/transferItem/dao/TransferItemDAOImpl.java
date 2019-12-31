package com.wo.epos.module.inv.transferItem.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.transferItem.model.TransferItem;
import com.wo.epos.module.inv.transferItem.vo.TransferItemVO;

@ManagedBean(name = "transferItemDAO")
@ViewScoped
public class TransferItemDAOImpl extends GenericDAOHibernate<TransferItem, Long> 
        implements TransferItemDAO, Serializable {

	private static final long serialVersionUID = 6573646331032730070L;

	
	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<TransferItemVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(TransferItem.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		System.out.println("query=="+criteria.toString());
		List<TransferItemVO> voList = new ArrayList<TransferItemVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  TransferItem transferItem = (TransferItem)criteria.list().get(i);
			  TransferItemVO vo = new TransferItemVO();
			  vo.setDoId(transferItem.getDoId());
			  vo.setOutlet(transferItem.getOutlet());
			  vo.setDoDate(transferItem.getDoDate());
			  vo.setDoNo(transferItem.getDoNo());
			  vo.setTransferFrom(transferItem.getTransferFrom());
			  vo.setTransferTo(transferItem.getTransferTo());
			  vo.setOutlet(transferItem.getOutlet());
			  vo.setDoType(transferItem.getDoType());
			  vo.setSoId(transferItem.getSoId());
			  vo.setItemResume(transferItem.getItemResume());
			  vo.setNotes(transferItem.getNotes());
			  vo.setStatus(transferItem.getStatus());
			  vo.setActiveFlag(transferItem.getActiveFlag());
			  vo.setListTIDtl(transferItem.getListTIDtl());	
			  System.out.println("size=="+transferItem.getListTIDtl().size());
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(TransferItem.class);
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
		    crit.createAlias("outlet", "outlet", Criteria.LEFT_JOIN);
		    crit.createAlias("transferTo", "transferTo", Criteria.LEFT_JOIN);
		    		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("doNo", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("outlet.outletName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("transferTo.outletName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("itemResume", sSplit,MatchMode.ANYWHERE)
									))
									));
					}
					
					if(searchVal.getSearchColumn().compareTo("outletId")==0){
						crit.add(Restrictions.eq("outlet.outletId", new Long(searchVal.getSearchValueAsString().trim())));
					}	
					
					if(searchVal.getSearchColumn().compareTo("doNo")==0){
						crit.add(Restrictions.ilike("doNo", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}		
					
					if(searchVal.getSearchColumn().compareTo("startDoDate")==0){
						crit.add(Restrictions.gt("doDate", (Date)searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("endDoDate")==0){
						crit.add(Restrictions.lt("doDate", (Date)searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("transferTo")==0){
						crit.add(Restrictions.eq("transferTo.outletName", searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("item")==0){
						crit.add(Restrictions.eq("itemResume", searchVal.getSearchValue()));
					}	
					
					
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TransferItemVO> getTransferItemList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder)
		 {
		Criteria criteria = getSession().createCriteria(TransferItem.class);
		decorateCriteria(criteria, searchCriteria);
		
		//criteria.setFirstResult(first);
		//criteria.setMaxResults(pageSize);
		
		System.out.println("query=="+criteria.toString());
		List<TransferItemVO> voList = new ArrayList<TransferItemVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  TransferItem transferItem = (TransferItem)criteria.list().get(i);
			  TransferItemVO vo = new TransferItemVO();
			  vo.setDoId(transferItem.getDoId());
			  vo.setOutlet(transferItem.getOutlet());
			  vo.setDoDate(transferItem.getDoDate());
			  vo.setDoNo(transferItem.getDoNo());
			  vo.setTransferFrom(transferItem.getTransferFrom());
			  vo.setTransferTo(transferItem.getTransferTo());
			  vo.setOutlet(transferItem.getOutlet());
			  vo.setDoType(transferItem.getDoType());
			  vo.setSoId(transferItem.getSoId());
			  vo.setItemResume(transferItem.getItemResume());
			  vo.setNotes(transferItem.getNotes());
			  vo.setStatus(transferItem.getStatus());
			  vo.setActiveFlag(transferItem.getActiveFlag());
			  vo.setListTIDtl(transferItem.getListTIDtl());	
			  System.out.println("size=="+transferItem.getListTIDtl().size());
			  voList.add(vo);
		}
		
		return voList;
	}
	
	public TransferItem getLastDoId() {
		
		Criteria criteria = getSession().createCriteria(TransferItem.class);
		criteria.addOrder(Order.desc("doNo"));
		
		//criteria.setFirstResult(1);
		criteria.setMaxResults(1);
		
		return (TransferItem)criteria.uniqueResult();
	}

	@Override
	public List<TransferItemVO> findByOutlet(Long outletId) {
		Criteria criteria = getSession().createCriteria(TransferItem.class);
		criteria.createAlias("outlet", "outlet", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("outlet.outletId", outletId));
		
		List<TransferItemVO> listTraItemVo = new ArrayList<TransferItemVO>();
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				  TransferItem item = (TransferItem)criteria.list().get(i);
				  TransferItemVO vo = new TransferItemVO();
				  vo.setDoId(item.getDoId());
				  vo.setOutlet(item.getOutlet());
				  vo.setDoNo(item.getDoNo());
				  
				  listTraItemVo.add(vo);
			}
		}
		
		return listTraItemVo;
	}

	@Override
	public TransferItem findByDoNo(String doNumber) {
		Criteria criteria = getSession().createCriteria(TransferItem.class);
		
		criteria.add(Restrictions.eq("doNo", doNumber));
		
		return (TransferItem) criteria.uniqueResult();
	}	
	
	@Override
	public List<TransferItemVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId) {
		Criteria criteria = getSession().createCriteria(TransferItem.class);
		criteria.createAlias("transferFrom", "transferFrom", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("transferTo", "transferTo", CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("status", CommonConstants.DO_NEW));
		criteria.add(Restrictions.eq("doType", CommonConstants.DELIVERY_TYPE_02));
		criteria.add(Restrictions.eq("transferFrom.outletId", outletFromId));
		criteria.add(Restrictions.eq("transferTo.outletId", outletToId));
		
		List<TransferItemVO> listTraItemVo = new ArrayList<TransferItemVO>();
		if(criteria.list().size() > 0){
			for(int i=0; i<criteria.list().size(); i++){
				  TransferItem item = (TransferItem)criteria.list().get(i);
				  TransferItemVO vo = new TransferItemVO();
				  vo.setDoId(item.getDoId());
				  vo.setOutlet(item.getOutlet());
				  vo.setDoNo(item.getDoNo());
				  vo.setTransferFrom(item.getTransferFrom());
				  vo.setTransferTo(item.getTransferTo());
				  vo.setDoType(item.getDoType());
				  vo.setStatus(item.getStatus());
				  
				  listTraItemVo.add(vo);
			}
		}
		
		return listTraItemVo;
	}
	
	public java.sql.Connection getConnection() {
        try {
        	Session sfi = (Session)getSession();
        	java.sql.Connection conn = sfi.connection();
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
	
	
	
}
	