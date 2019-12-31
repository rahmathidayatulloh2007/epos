package com.wo.epos.module.purchasing.po.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.purchasing.po.model.PoDtl;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;

@ManagedBean(name = "poDtlDAO")
@ViewScoped
public class PoDtlDAOImpl extends GenericDAOHibernate<PoDtl, Long> implements PoDtlDAO, Serializable {

	private static final long serialVersionUID = -1152817983116120534L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<PoDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
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

	@Override
	public List<PoDtlVO> searchListPoDtlVO(Long poId) {
		Criteria criteria = getSession().createCriteria(PoDtl.class);
		criteria.createAlias("po", "po");
		
		criteria.add(Restrictions.eq("po.poId", poId));
				
		List<PoDtlVO> voList = new ArrayList<PoDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  PoDtl poDtl = (PoDtl)criteria.list().get(i);
			  
			  PoDtlVO vo = new PoDtlVO();
			  
			  vo.setPoDtlId(poDtl.getPoDtlId());
			  vo.setLineNo(poDtl.getLineNo());
			  
			  if(poDtl.getPo() !=null){
			       vo.setPoId(poDtl.getPo().getPoId());
			     
			       if(poDtl.getPo().getOutlet() !=null){
					    vo.setOutletId(poDtl.getPo().getOutlet().getOutletId());
					    vo.setOutletName(poDtl.getPo().getOutlet().getOutletName());
				   }
			  }	  
			  
			  if(poDtl.getItem() !=null){
				  vo.setItemId(poDtl.getItemId());
				  vo.setItemCode(poDtl.getItem().getItemCode());
				  vo.setItemName(poDtl.getItem().getItemName());
				  vo.setCompleteItem(poDtl.getItem().getItemCode()+" - "+poDtl.getItem().getItemName());
				  if(poDtl.getItem().getUm() !=null){
				       vo.setUmId(poDtl.getItem().getUm().getUmId());
				       vo.setUmName(poDtl.getItem().getUm().getUmName());
				  }
			  }
			  
			  if(poDtl.getUnitPrice() !=null){
			      vo.setUnitPrice(poDtl.getUnitPrice().doubleValue());
			  }else{
				  vo.setUnitPrice(null);
			  }
			  
			  if(poDtl.getOrderQty() !=null){
			      vo.setOrderQty(poDtl.getOrderQty().doubleValue());
			  }else{
				  vo.setOrderQty(null);
			  }
			  
			  if(poDtl.getOrderUm() !=null){
			      vo.setOrderUm(new Long(poDtl.getOrderUm()+""));
			  }else{
				  vo.setOrderUm(null);
			  }
			  
			  vo.setDiscTypeCode(poDtl.getDiscTypeCode());
			  if(poDtl.getDiscValue() !=null){
			      vo.setDiscValue(poDtl.getDiscValue().doubleValue());
			  }else{
				  vo.setDiscValue(null);
			  }			  
			  
			  if(poDtl.getDiscPercent() !=null){
			      vo.setDiscPercent(poDtl.getDiscPercent().doubleValue());
			  }else{
				  vo.setDiscPercent(null);
			  }
			  

			  vo.setDiscTypeCode2(poDtl.getDiscTypeCodeExt());
			  
			  if(poDtl.getDiscValueExt() !=null){
			      vo.setDiscValueExt(poDtl.getDiscValueExt().doubleValue());
			  }else{
				  vo.setDiscValueExt(null);
			  }			  
			  
			  if(poDtl.getDiscPercentExt() !=null){
			      vo.setDiscPercentExt(poDtl.getDiscPercentExt().doubleValue());
			  }else{
				  vo.setDiscPercentExt(null);
			  }
			  		 
			  
			  voList.add(vo);
		}
		
		return voList;
	}
	
	
}