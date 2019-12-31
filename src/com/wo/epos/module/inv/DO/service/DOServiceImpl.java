package com.wo.epos.module.inv.DO.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.itemStock.dao.ItemStockDAO;
import com.wo.epos.module.inv.itemStock.model.ItemStock;
import com.wo.epos.module.inv.DO.dao.DODAO;
import com.wo.epos.module.inv.DO.model.DO;
import com.wo.epos.module.inv.DO.model.DODtl;
import com.wo.epos.module.inv.DO.vo.DOVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.outlet.dao.OutletDAO;

@ManagedBean(name="DOService")
@ViewScoped
public class DOServiceImpl implements DOService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{DODAO}")
	private DODAO DODAO;
	
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
	
	public DODAO getDODAO() {
		return DODAO;
	}

	public void setDODAO(DODAO DODAO) {
		this.DODAO = DODAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DOVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return DODAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return DODAO.searchCountData(searchCriteria);
	}
	
	@Override
	public String getDoNumber(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String result = "";
		DO  DO = DODAO.getLastDoId();
		Boolean isMonth = false;
		if(DO!=null && DO.getDoNo()!=null){
			if(DO.getDoNo().substring(0, 6).equals(sdf.format(new Date()))){
				isMonth = true;
			}
		}
		Long lastDoNo = new Long(0);
		if(isMonth){
		 lastDoNo = (DO!=null && DO.getDoNo()!=null ? new Long(Integer.parseInt(DO.getDoNo().substring(7))): 0);
		}
		lastDoNo ++;
		
		if(lastDoNo <10){
			result = "000"+lastDoNo;
		}else if(lastDoNo <100){
			result = "00"+lastDoNo;
		}else if(lastDoNo <1000){
			result = "0"+lastDoNo;
		}else if(lastDoNo <1000){
			result = ""+lastDoNo;
		}
		result = sdf.format(new Date()) +"-"+ result;
		return result;
	}

	@Override
	public void save(DOVO DOVO, String user) {
		
		try{
		 DO DO = new DO();	
		 DO.setCompany(companyDAO.findById(DOVO.getCompanyId()));
		 DO.setDoId(DOVO.getDoId());
		 //DO.setOutlet(DOVO.getOutlet());
		 DO.setDoDate(DOVO.getDoDate());
		 DO.setDoNo(getDoNumber());
		 

		 if(DOVO.getOutlet().getOutletId()!=null && DOVO.getOutlet().getOutletId() != 0){
			 DO.setOutlet(outletDAO.findById(DOVO.getOutlet().getOutletId()));
		 }else{
			 DO.setOutlet(null);
			 
		 }
		 
		 if(DOVO.getTransferFrom().getOutletId()!=null && DOVO.getTransferFrom().getOutletId() != 0){
			 DO.setTransferFrom(outletDAO.findById(DOVO.getTransferFrom().getOutletId()));
		 }else{
			 DO.setTransferFrom(null);
			 DOVO.setTransferFrom(null);
		 }
		 
		 if(DOVO.getTransferTo().getOutletId()!=null && DOVO.getTransferTo().getOutletId() != 0){
			 DO.setTransferTo(outletDAO.findById(DOVO.getTransferTo().getOutletId()));
		 }else{
			 DO.setTransferTo(null);
			 DOVO.setTransferTo(null);
		 }
		 
		 DO.setDoType(DOVO.getDoType());
		 DO.setSoId(DOVO.getSoId());
		 DO.setNotes(DOVO.getNotes());
		 DO.setStatus(DOVO.getStatus());
		 DO.setActiveFlag(CommonConstants.Y);
		 DO.setCreateBy(user);
		 DO.setCreateOn(new Timestamp(System.currentTimeMillis()));
		 
		 DO.setListDODtl(DOVO.getListDODtl());
		 
		 String itemResume = "";
		 for(int i=0;i<DO.getListDODtl().size();i++){
			 DODtl DODtl =(DODtl)DO.getListDODtl().get(i);
			// DODtl.setOutlet(DOVO.getOutlet());
			 DODtl.setDO(DO);
			 DODtl.setActiveFlag(CommonConstants.Y);
			 if(i>0){
				 itemResume = itemResume + " , "+ DODtl.getItem().getItemName() +" : "+DODtl.getDeliveryQty()+" "+DODtl.getDeliveryUm().getUmName() ;
			 }else{
				 itemResume = DODtl.getItem().getItemName() +" : "+DODtl.getDeliveryQty()+" "+DODtl.getDeliveryUm().getUmName();
			 }
			 
			 ItemStock ItemStockFrom = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferFrom()!=null?DOVO.getTransferFrom().getOutletId():null,DODtl.getItem().getItemId());
			 
			 ItemStock ItemStockTo = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferTo()!=null?DOVO.getTransferTo().getOutletId():null,DODtl.getItem().getItemId());
			 
			 if(ItemStockFrom!=null){
				 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty()!=null?ItemStockFrom.getOutgoingQty():0)+DODtl.getDeliveryQty().doubleValue());
				 ItemStockFrom.setStockQty((ItemStockFrom.getStockQty()!=null?ItemStockFrom.getStockQty():0)-DODtl.getDeliveryQty().doubleValue());
				 ItemStockFrom.setUpdateBy(user);
				 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				 itemStockDAO.update(ItemStockFrom);
			 }else{
				 ItemStockFrom = new ItemStock();
				 ItemStockFrom.setItem(DODtl.getItem());
				 ItemStockFrom.setItemId(DODtl.getItem().getItemId());
				 ItemStockFrom.setOutlet(DOVO.getTransferFrom());
				 ItemStockFrom.setOutletId(DOVO.getTransferFrom()!=null?DOVO.getTransferFrom().getOutletId():null);
				 ItemStockFrom.setStockQty(DODtl.getDeliveryQty().doubleValue());
				 ItemStockFrom.setOutgoingQty(DODtl.getDeliveryQty().doubleValue());
				 ItemStockFrom.setActiveFlag(CommonConstants.Y);
				 ItemStockFrom.setCreateBy(user);
				 ItemStockFrom.setCreateOn(new Timestamp(System.currentTimeMillis()));
				 ItemStockFrom.setCompanyId(DOVO.getCompanyId());
				 itemStockDAO.save(ItemStockFrom);
			 }
			 
			 if(ItemStockTo!=null){
				 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty()!=null?ItemStockTo.getIncomingQty():0)+DODtl.getDeliveryQty().doubleValue());
				 ItemStockTo.setStockQty((ItemStockTo.getStockQty()!=null?ItemStockTo.getStockQty():0)+DODtl.getDeliveryQty().doubleValue());
				 ItemStockTo.setUpdateBy(user);
				 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				 itemStockDAO.update(ItemStockTo);
			 }else{
				 ItemStockTo = new ItemStock();
				 ItemStockTo.setItem(DODtl.getItem());
				 ItemStockTo.setItemId(DODtl.getItem().getItemId());
				 ItemStockTo.setOutlet(DOVO.getTransferTo());
				 ItemStockTo.setOutletId(DOVO.getTransferTo()!=null?DOVO.getTransferTo().getOutletId():null);
				 ItemStockTo.setIncomingQty(DODtl.getDeliveryQty().doubleValue());
				 ItemStockTo.setStockQty(new Double(0));
				 ItemStockTo.setOutgoingQty(new Double(0));
				 ItemStockTo.setActiveFlag(CommonConstants.Y);
				 ItemStockTo.setCreateBy(user);
				 ItemStockTo.setCreateOn(new Timestamp(System.currentTimeMillis()));
				 ItemStockTo.setCompanyId(DOVO.getCompanyId());
				 itemStockDAO.save(ItemStockTo);
			 }
			 
			 
		 }
		 
		 DO.setItemResume(itemResume);
		 
		 DODAO.save(DO);
		 
		 itemStockDAO.flush();
		 DODAO.flush();
		}catch(Exception e){
			 itemStockDAO.rollback();
			 DODAO.rollback();
		}
		 
	}

	@Override
	public void update(DOVO DOVO, String user,DOVO DOVOOri) {
		
		try{
		DO DO = DODAO.findById(DOVO.getDoId());
		
		 DO.setDoId(DOVO.getDoId());
		 //DO.setOutlet(DOVO.getOutlet());
		 DO.setDoDate(DOVO.getDoDate());
		 DO.setDoNo(DOVO.getDoNo());
		 
		 if(DOVO.getOutlet().getOutletId()!=null && DOVO.getOutlet().getOutletId() != 0){
			 DO.setOutlet(outletDAO.findById(DOVO.getOutlet().getOutletId()));
		 }else{
			 DO.setOutlet(null);
			 
		 }
		 if(DOVO.getTransferFrom().getOutletId()!=null && DOVO.getTransferFrom().getOutletId() != 0){
			 DO.setTransferFrom(outletDAO.findById(DOVO.getTransferFrom().getOutletId()));
		 }else{
			 DO.setTransferFrom(null);
			 DOVO.setTransferFrom(null);
		 }
		 
		 if(DOVO.getTransferTo().getOutletId()!=null && DOVO.getTransferTo().getOutletId() != 0){
			 DO.setTransferTo(outletDAO.findById(DOVO.getTransferTo().getOutletId()));
		 }else{
			 DO.setTransferTo(null);
			 DOVO.setTransferTo(null);
		 }
		 
		 if(DOVOOri.getOutlet().getOutletId()!=null && DOVOOri.getOutlet().getOutletId() != 0){
			 
		 }else{
			 DOVOOri.setOutlet(null);
			 
		 }
		 if(DOVOOri.getTransferFrom().getOutletId()!=null && DOVOOri.getTransferFrom().getOutletId() != 0){
			
		 }else{
			 DOVOOri.setTransferFrom(null);
		 }
		 
		 if(DOVOOri.getTransferTo().getOutletId()!=null && DOVOOri.getTransferTo().getOutletId() != 0){
			 
		 }else{
			 DOVOOri.setTransferTo(null);
		 }
		 //DO.setTransferFrom(outletDAO.findById(DOVO.getTransferFrom().getOutletId()));
		 //DO.setTransferTo(outletDAO.findById(DOVO.getTransferTo().getOutletId()));
		 DO.setDoType(DOVO.getDoType());
		 DO.setSoId(DOVO.getSoId());
		 DO.setNotes(DOVO.getNotes());
		 DO.setActiveFlag("Y");
		 
		 DO.setUpdateBy(user);
		 DO.setUpdateOn(new Timestamp(System.currentTimeMillis()));	
		 
		Hashtable<Integer, DODtl> sourceDtl = new Hashtable<Integer,DODtl>();
		HashMap<Integer, DODtl>  mapDtlOri = new HashMap(sourceDtl);
		HashMap<Integer, DODtl>  mapDtlUpd = new HashMap(sourceDtl);
		
		for(int i=0;i<DO.getListDODtl().size();i++){
			DODtl DODtl =(DODtl)DO.getListDODtl().get(i);
			mapDtlOri.put(DODtl.getDoDtlId().intValue(), DODtl);
		}
		
		 for(int i=0; i<DOVO.getListDODtl().size();i++){
			 DODtl vo = (DODtl)DOVO.getListDODtl().get(i);
			 mapDtlUpd.put(vo.getDoDtlId()!=null?vo.getDoDtlId().intValue():null, vo);
			 DODtl dtlOri = mapDtlOri.get(vo.getDoDtlId()!=null?vo.getDoDtlId().intValue():null);
			 if(dtlOri!=null && dtlOri.getDoDtlId()!=null){// jika id update ada di list ori
				 DO.getListDODtl().get(DO.getListDODtl().indexOf(dtlOri)).setDeliveryQty(vo.getDeliveryQty());
				 DO.getListDODtl().get(DO.getListDODtl().indexOf(dtlOri)).setDeliveryUm(vo.getDeliveryUm());
				 DO.getListDODtl().get(DO.getListDODtl().indexOf(dtlOri)).setItem(vo.getItem());
				// DO.getListDODtl().get(DO.getListDODtl().indexOf(dtlOri)).setOutlet(vo.getOutlet());
			 }else{
				 DO.getListDODtl().add(vo);
			 }
		 }
		 
		 for(int i=0; i<DOVOOri.getListDODtl().size();i++){
			 DODtl vo = (DODtl)DOVOOri.getListDODtl().get(i);
			 DODtl dtlUpd = mapDtlUpd.get(vo.getDoDtlId().intValue());
			 if(dtlUpd == null){
				 DODtl dtlOri = mapDtlOri.get(vo.getDoDtlId().intValue());
				 DO.getListDODtl().remove(dtlOri);
			 }
		 }
		 
		 System.out.println("DO.getListDODtl() size=="+DO.getListDODtl().size());
		 
		 //DO.setListTIDtl(DOVO.getListDODtl());
		 
		 String itemResume = "";
		 for(int i=0;i<DO.getListDODtl().size();i++){
			 DODtl DODtl =(DODtl)DO.getListDODtl().get(i);
			// DODtl.setOutlet(DOVO.getOutlet());
			 DODtl.setDO(DO);
			 DODtl.setActiveFlag("Y");
			 if(i>0){
				 itemResume = itemResume + " , "+ DODtl.getItem().getItemName() +" : "+DODtl.getDeliveryQty()+" "+DODtl.getDeliveryUm().getUmName() ;
			 }else{
				 itemResume = DODtl.getItem().getItemName() +" : "+DODtl.getDeliveryQty()+" "+DODtl.getDeliveryUm().getUmName();
			 }
		 }
		 
		 DO.setItemResume(itemResume);
			
		// Jika Transfer To Ori == Transfer To Upd
		if((DOVO.getTransferTo() == null && DOVOOri.getTransferTo() == null) || ( DOVO.getTransferTo() != null && DOVOOri.getTransferTo() != null && DOVO.getTransferTo().getOutletId().intValue() == DOVOOri.getTransferTo().getOutletId().intValue())){
			 
				Hashtable<Integer, Long> source = new Hashtable<Integer,Long>();
				HashMap<Integer, Long>  mapOri = new HashMap(source);
				HashMap<Integer, Long>  mapUpd = new HashMap(source);
				
				for(int i=0;i<DOVOOri.getListDODtl().size();i++){
					DODtl DODtl2 =(DODtl)DOVOOri.getListDODtl().get(i);
					mapOri.put(DODtl2.getItem().getItemId().intValue(), DODtl2.getDeliveryQty());
				}
				
				for(int i=0;i<DOVO.getListDODtl().size();i++){
					DODtl DODtl =(DODtl)DOVO.getListDODtl().get(i);
					
					mapUpd.put(DODtl.getItem().getItemId().intValue(), DODtl.getDeliveryQty());
					
					ItemStock ItemStockFrom = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferFrom()!=null?DOVO.getTransferFrom().getOutletId():null,DODtl.getItem().getItemId());
					ItemStock ItemStockTo = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferTo()!=null?DOVO.getTransferTo().getOutletId():null,DODtl.getItem().getItemId());
					
					Long qtyOri = mapOri.get(DODtl.getItem().getItemId().intValue());
					
					if(qtyOri!=null && qtyOri.intValue()>0){// jika item update ada di list ori
						if(ItemStockFrom!=null){
							 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue() - qtyOri.doubleValue())+DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setStockQty((ItemStockFrom.getStockQty().doubleValue() + qtyOri.doubleValue())-DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setUpdateBy(user);
							 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockFrom);
						 }
						
						if(ItemStockTo!=null){
							 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue() - qtyOri.doubleValue())+DODtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setStockQty((ItemStockTo.getStockQty().doubleValue() - qtyOri.doubleValue())+DODtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setUpdateBy(user);
							 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockTo);
						 }
						
					}else{// jika item update tdk ada di list ori
						if(ItemStockFrom!=null){
							 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty()!=null?ItemStockFrom.getOutgoingQty():0)+DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setStockQty((ItemStockFrom.getStockQty()!=null?ItemStockFrom.getStockQty():0)-DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setUpdateBy(user);
							 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockFrom);
						 }else{
							 ItemStockFrom = new ItemStock();
							 ItemStockFrom.setItem(DODtl.getItem());
							 ItemStockFrom.setItemId(DODtl.getItem().getItemId());
							 ItemStockFrom.setOutlet(DOVO.getTransferFrom());
							 ItemStockFrom.setOutletId(DOVO.getTransferFrom()!=null?DOVO.getTransferFrom().getOutletId():null);
							 ItemStockFrom.setStockQty(DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setOutgoingQty(DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setActiveFlag(CommonConstants.Y);
							 ItemStockFrom.setCreateBy(user);
							 ItemStockFrom.setCreateOn(new Timestamp(System.currentTimeMillis()));
							 ItemStockFrom.setCompanyId(DOVO.getCompanyId());
							 itemStockDAO.save(ItemStockFrom);
						 }
						 
						 if(ItemStockTo!=null){
							 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty()!=null?ItemStockTo.getIncomingQty():0)+DODtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setStockQty((ItemStockTo.getStockQty()!=null?ItemStockTo.getStockQty():0)+DODtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setUpdateBy(user);
							 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockTo);
						 }else{
							 ItemStockTo = new ItemStock();
							 ItemStockTo.setItem(DODtl.getItem());
							 ItemStockTo.setItemId(DODtl.getItem().getItemId());
							 ItemStockTo.setOutlet(DOVO.getTransferTo());
							 ItemStockTo.setOutletId(DOVO.getTransferTo()!=null?DOVO.getTransferTo().getOutletId():null);
							 ItemStockTo.setIncomingQty(DODtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setStockQty(new Double(0));
							 ItemStockTo.setOutgoingQty(new Double(0));
							 ItemStockTo.setActiveFlag(CommonConstants.Y);
							 ItemStockTo.setCreateBy(user);
							 ItemStockTo.setCreateOn(new Timestamp(System.currentTimeMillis()));
							 ItemStockTo.setCompanyId(DOVO.getCompanyId());
							 itemStockDAO.save(ItemStockTo);
						 }
					}
				}
				
				for(int i=0;i<DOVOOri.getListDODtl().size();i++){
					DODtl DODtl =(DODtl)DOVOOri.getListDODtl().get(i);
					ItemStock ItemStockFrom = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferFrom()!=null?DOVO.getTransferFrom().getOutletId():null,DODtl.getItem().getItemId());
					ItemStock ItemStockTo = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferTo()!=null?DOVO.getTransferTo().getOutletId():null,DODtl.getItem().getItemId());
					
					Long qtyUpd = mapUpd.get(DODtl.getItem().getItemId().intValue());
					
					if(qtyUpd == null || qtyUpd.intValue() == 0){// jika item ori tdk ada di  List update
						if(ItemStockFrom!=null){
							 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setStockQty((ItemStockFrom.getStockQty().doubleValue()) + DODtl.getDeliveryQty().doubleValue());
							 ItemStockFrom.setUpdateBy(user);
							 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockFrom);
						 }
						
						if(ItemStockTo!=null){
							 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setStockQty((ItemStockTo.getStockQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
							 ItemStockTo.setUpdateBy(user);
							 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
							 itemStockDAO.update(ItemStockTo);
						 }
						
					}
				}
		}else{
			
			//kembalikan stock ke outlet sebelumnya
			for(int i=0;i<DOVOOri.getListDODtl().size();i++){
				DODtl DODtl =(DODtl)DOVOOri.getListDODtl().get(i);
				ItemStock ItemStockFrom = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVOOri.getTransferFrom()!=null?DOVOOri.getTransferFrom().getOutletId():null,DODtl.getItem().getItemId());
				ItemStock ItemStockTo = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVOOri.getTransferTo()!=null?DOVOOri.getTransferTo().getOutletId():null,DODtl.getItem().getItemId());
				
				if(ItemStockFrom!=null){
						 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setStockQty((ItemStockFrom.getStockQty().doubleValue()) + DODtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setUpdateBy(user);
						 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockFrom);
					 }
					
					if(ItemStockTo!=null){
						 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setStockQty((ItemStockTo.getStockQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setUpdateBy(user);
						 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockTo);
					 }
					
			
			}
			
			//kirim stock ke outlet tujuan baru
			for(int i=0;i<DOVO.getListDODtl().size();i++){
				DODtl DODtl =(DODtl)DOVO.getListDODtl().get(i);
				
				
				ItemStock ItemStockFrom = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferFrom()!=null?DOVO.getTransferFrom().getOutletId():null,DODtl.getItem().getItemId());
				ItemStock ItemStockTo = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DOVO.getCompanyId(),DOVO.getTransferTo()!=null?DOVO.getTransferTo().getOutletId():null,DODtl.getItem().getItemId());
				
					if(ItemStockFrom!=null){
						 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty()!=null?ItemStockFrom.getOutgoingQty():0)+DODtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setOutgoingQty((ItemStockFrom.getStockQty()!=null?ItemStockFrom.getStockQty():0)-DODtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setUpdateBy(user);
						 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockFrom);
					 }else{
						 ItemStockFrom = new ItemStock();
						 ItemStockFrom.setItem(DODtl.getItem());
						 ItemStockFrom.setItemId(DODtl.getItem().getItemId());
						 ItemStockFrom.setOutlet(DOVO.getTransferFrom());
						 ItemStockFrom.setOutletId(DOVO.getTransferFrom()!=null?DOVO.getTransferFrom().getOutletId():null);
						 ItemStockFrom.setStockQty(DODtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setOutgoingQty(DODtl.getDeliveryQty().doubleValue());
						 ItemStockFrom.setActiveFlag(CommonConstants.Y);
						 ItemStockFrom.setCreateBy(user);
						 ItemStockFrom.setCreateOn(new Timestamp(System.currentTimeMillis()));
						 ItemStockFrom.setCompanyId(DOVO.getCompanyId());
						 itemStockDAO.save(ItemStockFrom);
					 }
					 
					 if(ItemStockTo!=null){
						 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty()!=null?ItemStockTo.getIncomingQty():0)+DODtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setIncomingQty((ItemStockTo.getStockQty()!=null?ItemStockTo.getStockQty():0)+DODtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setUpdateBy(user);
						 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
						 itemStockDAO.update(ItemStockTo);
					 }else{
						 ItemStockTo = new ItemStock();
						 ItemStockTo.setItem(DODtl.getItem());
						 ItemStockTo.setItemId(DODtl.getItem().getItemId());
						 ItemStockTo.setOutlet(DOVO.getTransferTo());
						 ItemStockTo.setOutletId(DOVO.getTransferTo()!=null?DOVO.getTransferTo().getOutletId():null);
						 ItemStockTo.setIncomingQty(DODtl.getDeliveryQty().doubleValue());
						 ItemStockTo.setStockQty(new Double(0));
						 ItemStockTo.setOutgoingQty(new Double(0));
						 ItemStockTo.setActiveFlag(CommonConstants.Y);
						 ItemStockTo.setCreateBy(user);
						 ItemStockTo.setCreateOn(new Timestamp(System.currentTimeMillis()));
						 ItemStockTo.setCompanyId(DOVO.getCompanyId());
						 itemStockDAO.save(ItemStockTo);
					 }
				
			}
		}
		
		DODAO.update(DO);
		itemStockDAO.flush();
		DODAO.flush();
		
		}catch(Exception e){
			System.out.println("err=="+e);
			 itemStockDAO.rollback();
			 DODAO.rollback();
		}
		
	}

	@Override
	public void delete(Long DOId, String user) {
		try{
		DO DO = DODAO.findById(DOId);
		for(int i=0;i<DO.getListDODtl().size();i++){
			DODtl DODtl =(DODtl)DO.getListDODtl().get(i);
			ItemStock ItemStockFrom = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DO.getCompany().getCompanyId(),DO.getTransferFrom().getOutletId(),DODtl.getItem().getItemId());
			ItemStock ItemStockTo = itemStockDAO.getItemStockByCompanyIdOutletIdAndItemId(DO.getCompany().getCompanyId(),DO.getTransferTo().getOutletId(),DODtl.getItem().getItemId());
			
		
				if(ItemStockFrom!=null){
					 ItemStockFrom.setOutgoingQty((ItemStockFrom.getOutgoingQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
					 ItemStockFrom.setStockQty((ItemStockFrom.getStockQty().doubleValue())+DODtl.getDeliveryQty().doubleValue());
					 ItemStockFrom.setUpdateBy(user);
					 ItemStockFrom.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					 itemStockDAO.update(ItemStockFrom);
				 }
				
				if(ItemStockTo!=null){
					 ItemStockTo.setIncomingQty((ItemStockTo.getIncomingQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
					 ItemStockTo.setStockQty((ItemStockTo.getStockQty().doubleValue()) - DODtl.getDeliveryQty().doubleValue());
					 ItemStockTo.setUpdateBy(user);
					 ItemStockTo.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					 itemStockDAO.update(ItemStockTo);
				 }
				
			
		}
		DODAO.delete(DO);
		
		itemStockDAO.flush();
		DODAO.flush();
		}catch(Exception e){
			itemStockDAO.rollback();
			DODAO.rollback();
		}
		
	}
	
	public java.sql.Connection getConnection(){
		return DODAO.getConnection();
	}
	
	public DO getLastDoId(){
		return DODAO.getLastDoId();
	}

	@Override
	public DO findById(Long DOId) {
		return DODAO.findById(DOId);
	}

	public List<DOVO> getDOList(List searchCriteria, int first, int pageSize, String sortField, boolean sortOrder){
		return DODAO.getDOList(searchCriteria, first, pageSize, sortField, sortOrder);
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
	public List<DOVO> findByOutlet(Long outletId) {
		return DODAO.findByOutlet(outletId);
	}

	@Override
	public DO findByDoNo(String doNumber) {
		return DODAO.findByDoNo(doNumber);
	}

	@Override
	public List<DOVO> searchDataTransItemByOutlet(Long outletFromId, Long outletToId) {
		return DODAO.searchDataTransItemByOutlet(outletFromId, outletToId);
	}
	
	
		
}
