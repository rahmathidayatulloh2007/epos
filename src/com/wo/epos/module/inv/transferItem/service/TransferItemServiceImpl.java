package com.wo.epos.module.inv.transferItem.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.inv.transferItem.dao.TransferItemDAO;
import com.wo.epos.module.inv.transferItem.model.TransferItem;
import com.wo.epos.module.inv.transferItem.model.TransferItemDtl;
import com.wo.epos.module.inv.transferItem.vo.TransferItemVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;

@ManagedBean(name="transferItemService")
@ViewScoped
public class TransferItemServiceImpl implements TransferItemService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{transferItemDAO}")
	private TransferItemDAO transferItemDAO;
	
	@ManagedProperty(value="#{itemStockDAO}")
	private ItemStockDAO itemStockDAO;
	
	@ManagedProperty(value="#{outletDAO}")
	private OutletDAO outletDAO;
	
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public TransferItemDAO getTransferItemDAO() {
		return transferItemDAO;
	}

	public void setTransferItemDAO(TransferItemDAO transferItemDAO) {
		this.transferItemDAO = transferItemDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TransferItemVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return transferItemDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return transferItemDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(TransferItemVO transferItemVO, String user) {
		
		try{
		 TransferItem transferItem = new TransferItem();	
		 
		 transferItem.setDoId(transferItemVO.getDoId());
		 transferItem.setOutlet(transferItemVO.getOutlet());
		 transferItem.setDoDate(transferItemVO.getDoDate());
		 transferItem.setDoNo(transferItemVO.getDoNo());
		 transferItem.setTransferFrom(outletDAO.findById(transferItemVO.getTransferFrom().getOutletId()));
		 transferItem.setTransferTo(outletDAO.findById(transferItemVO.getTransferTo().getOutletId()));
		 transferItem.setDoType(transferItemVO.getDoType());
		 transferItem.setSoId(transferItemVO.getSoId());
		 transferItem.setNotes(transferItemVO.getNotes());
		 transferItem.setStatus(transferItemVO.getStatus());
		 transferItem.setActiveFlag("Y");
		 transferItem.setCreateBy(user);
		 transferItem.setCreateOn(new Timestamp(System.currentTimeMillis()));
		 
		 transferItem.setListTIDtl(transferItemVO.getListTIDtl());
		 
		 String itemResume = "";
		 for(int i=0;i<transferItem.getListTIDtl().size();i++){
			 TransferItemDtl transferItemDtl =(TransferItemDtl)transferItem.getListTIDtl().get(i);
			// transferItemDtl.setOutlet(transferItemVO.getOutlet());
			 transferItemDtl.setTransferItem(transferItem);
			 transferItemDtl.setActiveFlag("Y");
			 if(i>0){
				 itemResume = itemResume + " , "+ transferItemDtl.getItem().getItemName() +" : "+transferItemDtl.getDeliveryQty()+" "+transferItemDtl.getDeliveryUm().getUmName() ;
			 }else{
				 itemResume = transferItemDtl.getItem().getItemName() +" : "+transferItemDtl.getDeliveryQty()+" "+transferItemDtl.getDeliveryUm().getUmName();
			 }
			 
			 ItemStock ItemStockFrom = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferFrom().getOutletId(),transferItemDtl.getItem().getItemId());
			 
			 ItemStock ItemStockTo = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferTo().getOutletId(),transferItemDtl.getItem().getItemId());
			 
			 if(ItemStockFrom!=null){
				 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty()!=null?ItemStockFrom.getOutgoingQty():0)+transferItemDtl.getDeliveryQty().doubleValue());
				 ItemStockFrom.setUpdateBy(user);
				 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				 itemStockDAO.update(ItemStockFrom);
			 }else{
				 ItemStockFrom = new ItemStock();
				 ItemStockFrom.setItem(transferItemDtl.getItem());
				 ItemStockFrom.setItemId(transferItemDtl.getItem().getItemId());
				 ItemStockFrom.setOutlet(transferItemVO.getTransferFrom());
				 ItemStockFrom.setOutletId(transferItemVO.getTransferFrom().getOutletId());
				 ItemStockFrom.setStockQty(transferItemDtl.getDeliveryQty().doubleValue());
				 ItemStockFrom.setOutgoingQty(transferItemDtl.getDeliveryQty().doubleValue());
				 ItemStockFrom.setActiveFlag("Y");
				 ItemStockFrom.setCreateBy(user);
				 ItemStockFrom.setCreateOn(new Timestamp(System.currentTimeMillis()));
				 itemStockDAO.save(ItemStockFrom);
			 }
			 
			 if(ItemStockTo!=null){
				 ItemStockTo.setIncomingQty((ItemStockFrom.getIncomingQty()!=null?ItemStockFrom.getIncomingQty():0)+transferItemDtl.getDeliveryQty().doubleValue());
				 ItemStockTo.setUpdateBy(user);
				 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				 itemStockDAO.update(ItemStockTo);
			 }else{
				 ItemStockTo = new ItemStock();
				 ItemStockTo.setItem(transferItemDtl.getItem());
				 ItemStockTo.setItemId(transferItemDtl.getItem().getItemId());
				 ItemStockTo.setOutlet(transferItemVO.getTransferTo());
				 ItemStockTo.setOutletId(transferItemVO.getTransferTo().getOutletId());
				 ItemStockTo.setIncomingQty(transferItemDtl.getDeliveryQty().doubleValue());
				 ItemStockTo.setStockQty(new Double(0));
				 ItemStockTo.setOutgoingQty(new Double(0));
				 ItemStockTo.setActiveFlag("Y");
				 ItemStockTo.setCreateBy(user);
				 ItemStockTo.setCreateOn(new Timestamp(System.currentTimeMillis()));
				 itemStockDAO.save(ItemStockTo);
			 }
			 
			 
		 }
		 
		 transferItem.setItemResume(itemResume);
		 
		 transferItemDAO.save(transferItem);
		 
		 itemStockDAO.flush();
		 transferItemDAO.flush();
		}catch(Exception e){
			 itemStockDAO.rollback();
			 transferItemDAO.rollback();
		}
		 
	}

	@Override
	public void update(TransferItemVO transferItemVO, String user,TransferItemVO transferItemVOOri) {
		
		try{
		TransferItem transferItem = transferItemDAO.findById(transferItemVO.getDoId());
		
		 transferItem.setDoId(transferItemVO.getDoId());
		 transferItem.setOutlet(transferItemVO.getOutlet());
		 transferItem.setDoDate(transferItemVO.getDoDate());
		 transferItem.setDoNo(transferItemVO.getDoNo());
		 transferItem.setTransferFrom(outletDAO.findById(transferItemVO.getTransferFrom().getOutletId()));
		 transferItem.setTransferTo(outletDAO.findById(transferItemVO.getTransferTo().getOutletId()));
		 transferItem.setDoType(transferItemVO.getDoType());
		 transferItem.setSoId(transferItemVO.getSoId());
		 transferItem.setNotes(transferItemVO.getNotes());
		 transferItem.setActiveFlag("Y");
		 
		 transferItem.setUpdateBy(user);
		 transferItem.setUpdateOn(new Timestamp(System.currentTimeMillis()));	
		 
		Hashtable<Integer, TransferItemDtl> sourceDtl = new Hashtable<Integer,TransferItemDtl>();
		HashMap<Integer, TransferItemDtl>  mapDtlOri = new HashMap(sourceDtl);
		HashMap<Integer, TransferItemDtl>  mapDtlUpd = new HashMap(sourceDtl);
		
		for(int i=0;i<transferItem.getListTIDtl().size();i++){
			TransferItemDtl transferItemDtl =(TransferItemDtl)transferItem.getListTIDtl().get(i);
			mapDtlOri.put(transferItemDtl.getDoDtlId().intValue(), transferItemDtl);
		}
		
		 for(int i=0; i<transferItemVO.getListTIDtl().size();i++){
			 TransferItemDtl vo = (TransferItemDtl)transferItemVO.getListTIDtl().get(i);
			 mapDtlUpd.put(vo.getDoDtlId()!=null?vo.getDoDtlId().intValue():null, vo);
			 TransferItemDtl dtlOri = mapDtlOri.get(vo.getDoDtlId()!=null?vo.getDoDtlId().intValue():null);
			 if(dtlOri!=null && dtlOri.getDoDtlId()!=null){// jika id update ada di list ori
				 transferItem.getListTIDtl().get(transferItem.getListTIDtl().indexOf(dtlOri)).setDeliveryQty(vo.getDeliveryQty());
				 transferItem.getListTIDtl().get(transferItem.getListTIDtl().indexOf(dtlOri)).setDeliveryUm(vo.getDeliveryUm());
				 transferItem.getListTIDtl().get(transferItem.getListTIDtl().indexOf(dtlOri)).setItem(vo.getItem());
				// transferItem.getListTIDtl().get(transferItem.getListTIDtl().indexOf(dtlOri)).setOutlet(vo.getOutlet());
			 }else{
				 transferItem.getListTIDtl().add(vo);
			 }
		 }
		 
		 for(int i=0; i<transferItemVOOri.getListTIDtl().size();i++){
			 TransferItemDtl vo = (TransferItemDtl)transferItemVOOri.getListTIDtl().get(i);
			 TransferItemDtl dtlUpd = mapDtlUpd.get(vo.getDoDtlId().intValue());
			 if(dtlUpd == null){
				 TransferItemDtl dtlOri = mapDtlOri.get(vo.getDoDtlId().intValue());
				 transferItem.getListTIDtl().remove(dtlOri);
			 }
		 }
		 
		 System.out.println("transferItem.getListTIDtl() size=="+transferItem.getListTIDtl().size());
		 
		 //transferItem.setListTIDtl(transferItemVO.getListTIDtl());
		 
		 String itemResume = "";
		 for(int i=0;i<transferItem.getListTIDtl().size();i++){
			 TransferItemDtl transferItemDtl =(TransferItemDtl)transferItem.getListTIDtl().get(i);
			// transferItemDtl.setOutlet(transferItemVO.getOutlet());
			 transferItemDtl.setTransferItem(transferItem);
			 transferItemDtl.setActiveFlag("Y");
			 if(i>0){
				 itemResume = itemResume + " , "+ transferItemDtl.getItem().getItemName() +" : "+transferItemDtl.getDeliveryQty()+" "+transferItemDtl.getDeliveryUm().getUmName() ;
			 }else{
				 itemResume = transferItemDtl.getItem().getItemName() +" : "+transferItemDtl.getDeliveryQty()+" "+transferItemDtl.getDeliveryUm().getUmName();
			 }
		 }
		 
		 transferItem.setItemResume(itemResume);
			
		// Jika Transfer To Ori == Transfer To Upd
		if(transferItemVO.getTransferTo().getOutletId().intValue() == transferItemVOOri.getTransferTo().getOutletId().intValue()){
			 
				Hashtable<Integer, Long> source = new Hashtable<Integer,Long>();
				HashMap<Integer, Long>  mapOri = new HashMap(source);
				HashMap<Integer, Long>  mapUpd = new HashMap(source);
				
				for(int i=0;i<transferItemVOOri.getListTIDtl().size();i++){
					TransferItemDtl transferItemDtl2 =(TransferItemDtl)transferItemVOOri.getListTIDtl().get(i);
					mapOri.put(transferItemDtl2.getItem().getItemId().intValue(), transferItemDtl2.getDeliveryQty());
				}
				
				for(int i=0;i<transferItemVO.getListTIDtl().size();i++){
					TransferItemDtl transferItemDtl =(TransferItemDtl)transferItemVO.getListTIDtl().get(i);
					
					mapUpd.put(transferItemDtl.getItem().getItemId().intValue(), transferItemDtl.getDeliveryQty());
					
					ItemStock ItemStockFrom = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferFrom().getOutletId(),transferItemDtl.getItem().getItemId());
					ItemStock ItemStockTo = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferTo().getOutletId(),transferItemDtl.getItem().getItemId());
					
					Long qtyOri = mapOri.get(transferItemDtl.getItem().getItemId().intValue());
					
					if(qtyOri!=null && qtyOri.intValue()>0){// jika item update ada di list ori
						if(ItemStockFrom!=null){
							 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue() - qtyOri.doubleValue())+transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setUpdateBy(user);
							 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockFrom);
						 }
						
						if(ItemStockTo!=null){
							 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue() - qtyOri.doubleValue())+transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setUpdateBy(user);
							 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockTo);
						 }
						
					}else{// jika item update tdk ada di list ori
						if(ItemStockFrom!=null){
							 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty()!=null?ItemStockFrom.getOutgoingQty():0)+transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setUpdateBy(user);
							 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockFrom);
						 }else{
							 ItemStockFrom = new ItemStock();
							 ItemStockFrom.setItem(transferItemDtl.getItem());
							 ItemStockFrom.setItemId(transferItemDtl.getItem().getItemId());
							 ItemStockFrom.setOutlet(transferItemVO.getTransferFrom());
							 ItemStockFrom.setOutletId(transferItemVO.getTransferFrom().getOutletId());
							 ItemStockFrom.setStockQty(transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setOutgoingQty(transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setActiveFlag("Y");
							 ItemStockFrom.setCreateBy(user);
							 ItemStockFrom.setCreateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.save(ItemStockFrom);
						 }
						 
						 if(ItemStockTo!=null){
							 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty()!=null?ItemStockTo.getIncomingQty():0)+transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setUpdateBy(user);
							 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockTo);
						 }else{
							 ItemStockTo = new ItemStock();
							 ItemStockTo.setItem(transferItemDtl.getItem());
							 ItemStockTo.setItemId(transferItemDtl.getItem().getItemId());
							 ItemStockTo.setOutlet(transferItemVO.getTransferTo());
							 ItemStockTo.setOutletId(transferItemVO.getTransferTo().getOutletId());
							 ItemStockTo.setIncomingQty(transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setStockQty(new Double(0));
							 ItemStockTo.setOutgoingQty(new Double(0));
							 ItemStockTo.setActiveFlag("Y");
							 ItemStockTo.setCreateBy(user);
							 ItemStockTo.setCreateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.save(ItemStockTo);
						 }
					}
				}
				
				for(int i=0;i<transferItemVOOri.getListTIDtl().size();i++){
					TransferItemDtl transferItemDtl =(TransferItemDtl)transferItemVOOri.getListTIDtl().get(i);
					ItemStock ItemStockFrom = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferFrom().getOutletId(),transferItemDtl.getItem().getItemId());
					ItemStock ItemStockTo = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferTo().getOutletId(),transferItemDtl.getItem().getItemId());
					
					Long qtyUpd = mapUpd.get(transferItemDtl.getItem().getItemId().intValue());
					
					if(qtyUpd == null || qtyUpd.intValue() == 0){// jika item ori tdk ada di  List update
						if(ItemStockFrom!=null){
							 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue()) - transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setUpdateBy(user);
							 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockFrom);
						 }
						
						if(ItemStockTo!=null){
							 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue()) - transferItemDtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setUpdateBy(user);
							 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockTo);
						 }
						
					}
				}
		}else{
			
			//kembalikan stock ke outlet sebelumnya
			for(int i=0;i<transferItemVOOri.getListTIDtl().size();i++){
				TransferItemDtl transferItemDtl =(TransferItemDtl)transferItemVOOri.getListTIDtl().get(i);
				ItemStock ItemStockFrom = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVOOri.getTransferFrom().getOutletId(),transferItemDtl.getItem().getItemId());
				ItemStock ItemStockTo = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVOOri.getTransferTo().getOutletId(),transferItemDtl.getItem().getItemId());
				
				if(ItemStockFrom!=null){
						 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue()) - transferItemDtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setUpdateBy(user);
						 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockFrom);
					 }
					
					if(ItemStockTo!=null){
						 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue()) - transferItemDtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setUpdateBy(user);
						 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockTo);
					 }
					
			
			}
			
			//kirim stock ke outlet tujuan baru
			for(int i=0;i<transferItemVO.getListTIDtl().size();i++){
				TransferItemDtl transferItemDtl =(TransferItemDtl)transferItemVO.getListTIDtl().get(i);
				
				
				ItemStock ItemStockFrom = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferFrom().getOutletId(),transferItemDtl.getItem().getItemId());
				ItemStock ItemStockTo = itemStockDAO.getItemStockByOutletIdAndItemId(transferItemVO.getTransferTo().getOutletId(),transferItemDtl.getItem().getItemId());
				
					if(ItemStockFrom!=null){
						 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty()!=null?ItemStockFrom.getOutgoingQty():0)+transferItemDtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setUpdateBy(user);
						 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockFrom);
					 }else{
						 ItemStockFrom = new ItemStock();
						 ItemStockFrom.setItem(transferItemDtl.getItem());
						 ItemStockFrom.setItemId(transferItemDtl.getItem().getItemId());
						 ItemStockFrom.setOutlet(transferItemVO.getTransferFrom());
						 ItemStockFrom.setOutletId(transferItemVO.getTransferFrom().getOutletId());
						 ItemStockFrom.setStockQty(transferItemDtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setOutgoingQty(transferItemDtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setActiveFlag("Y");
						 ItemStockFrom.setCreateBy(user);
						 ItemStockFrom.setCreateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.save(ItemStockFrom);
					 }
					 
					 if(ItemStockTo!=null){
						 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty()!=null?ItemStockTo.getIncomingQty():0)+transferItemDtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setUpdateBy(user);
						 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockTo);
					 }else{
						 ItemStockTo = new ItemStock();
						 ItemStockTo.setItem(transferItemDtl.getItem());
						 ItemStockTo.setItemId(transferItemDtl.getItem().getItemId());
						 ItemStockTo.setOutlet(transferItemVO.getTransferTo());
						 ItemStockTo.setOutletId(transferItemVO.getTransferTo().getOutletId());
						 ItemStockTo.setIncomingQty(transferItemDtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setStockQty(new Double(0));
						 ItemStockTo.setOutgoingQty(new Double(0));
						 ItemStockTo.setActiveFlag("Y");
						 ItemStockTo.setCreateBy(user);
						 ItemStockTo.setCreateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.save(ItemStockTo);
					 }
				
			}
		}
		
		transferItemDAO.update(transferItem);
		itemStockDAO.flush();
		transferItemDAO.flush();
		
		}catch(Exception e){
			 itemStockDAO.rollback();
			 transferItemDAO.rollback();
		}
		
	}

	@Override
	public void delete(Long transferItemId, String user) {
		try{
		TransferItem transferItem = transferItemDAO.findById(transferItemId);
		for(int i=0;i<transferItem.getListTIDtl().size();i++){
			TransferItemDtl transferItemDtl =(TransferItemDtl)transferItem.getListTIDtl().get(i);
			ItemStock ItemStockFrom = itemStockDAO.getItemStockByOutletIdAndItemId(transferItem.getTransferFrom().getOutletId(),transferItemDtl.getItem().getItemId());
			ItemStock ItemStockTo = itemStockDAO.getItemStockByOutletIdAndItemId(transferItem.getTransferTo().getOutletId(),transferItemDtl.getItem().getItemId());
			
		
				if(ItemStockFrom!=null){
					 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue()) - transferItemDtl.getDeliveryQty().doubleValue());
					 ItemStockFrom.setUpdateBy(user);
					 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					 itemStockDAO.update(ItemStockFrom);
				 }
				
				if(ItemStockTo!=null){
					 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue()) - transferItemDtl.getDeliveryQty().doubleValue());
					 ItemStockTo.setUpdateBy(user);
					 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					 itemStockDAO.update(ItemStockTo);
				 }
				
			
		}
		transferItemDAO.delete(transferItem);
		
		itemStockDAO.flush();
		transferItemDAO.flush();
		}catch(Exception e){
			itemStockDAO.rollback();
			transferItemDAO.rollback();
		}
		
	}
	
	public java.sql.Connection getConnection(){
		return transferItemDAO.getConnection();
	}
	
	public TransferItem getLastDoId(){
		return transferItemDAO.getLastDoId();
	}

	@Override
	public TransferItem findById(Long transferItemId) {
		return transferItemDAO.findById(transferItemId);
	}

	public List<TransferItemVO> getTransferItemList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder){
		return transferItemDAO.getTransferItemList(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	public OutletDAO getOutletDAO() {
		return outletDAO;
	}

	public void setOutletDAO(OutletDAO outletDAO) {
		this.outletDAO = outletDAO;
	}

	public ItemStockDAO getItemStockDAO() {
		return itemStockDAO;
	}

	public void setItemStockDAO(ItemStockDAO itemStockDAO) {
		this.itemStockDAO = itemStockDAO;
	}
	
	@Override
	public List<TransferItemVO> findByOutlet(Long outletId) {
		return transferItemDAO.findByOutlet(outletId);
	}

	@Override
	public TransferItem findByDoNo(String doNumber) {
		return transferItemDAO.findByDoNo(doNumber);
	}

	@Override
	public List<TransferItemVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId) {
		return transferItemDAO.searchDataTransItemByOutlet(outletFromId, outletToId);
	}
	
	
		
}
