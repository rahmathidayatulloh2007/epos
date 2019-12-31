package com.wo.epos.module.inv.rn.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.rn.model.Rn;
import com.wo.epos.module.inv.rn.model.RnDtl;
import com.wo.epos.module.inv.rn.vo.RnDtlVO;

@ManagedBean(name = "rnDtlDAO")
@ViewScoped
public class RnDtlDAOImpl extends GenericDAOHibernate<RnDtl, Long> 
implements RnDtlDAO, Serializable{

   private static final long serialVersionUID = 5274411668156673620L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<RnDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Rn.class);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<RnDtlVO> voList = new ArrayList<RnDtlVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  RnDtl rnDtl = (RnDtl)criteria.list().get(i);
			  RnDtlVO vo = new RnDtlVO();
			  
			  vo.setRnDtlId(rnDtl.getRnDtlId());
			  
			  vo.setActiveFlag(rnDtl.getActiveFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Rn.class);
		 
		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}
	
	


}
