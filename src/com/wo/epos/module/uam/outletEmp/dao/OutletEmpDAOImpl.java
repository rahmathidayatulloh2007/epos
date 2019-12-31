package com.wo.epos.module.uam.outletEmp.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;
import com.wo.epos.module.uam.role.dao.RoleDAO;
import com.wo.epos.module.uam.role.vo.RoleVO;
import com.wo.epos.module.uam.user.dao.UserDAO;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "outletEmpDAO")
@ViewScoped
public class OutletEmpDAOImpl extends GenericDAOHibernate<OutletEmp, Long> implements OutletEmpDAO, Serializable{

	private static final long serialVersionUID = 2779455998225585896L;
	
	@ManagedProperty(value="#{userDAO}")
	private UserDAO userDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public List<OutletEmpVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {		
		
		Criteria criteria = getSession().createCriteria(OutletEmp.class);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<OutletEmpVO> voList = new ArrayList<OutletEmpVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  OutletEmp outlet = (OutletEmp)criteria.list().get(i);
			  OutletEmpVO outletVO = new OutletEmpVO();
			  
			  outletVO.setOutletEmpId(outlet.getOutletEmpId());
			  if(outlet.getOutlet() !=null){
				  outletVO.setOutletId(outlet.getOutletId());
				  outletVO.setOutletName(outlet.getOutlet().getOutletName());
			  }
			  if(outlet.getEmployee() !=null){
				  outletVO.setEmployeeId(outlet.getEmployeeId());
				  outletVO.setEmployeeName(outlet.getEmployee().getFullName());
			  }			  		
			 
			  outletVO.setActiveFlag(outlet.getActiveFlag());
			  
			  voList.add(outletVO);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Outlet.class);
		 
		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}
	
	

	@Override
	public List<OutletEmpVO> searchOutletEmpList() {
		
       Criteria criteria = getSession().createCriteria(OutletEmp.class);
		
		List<OutletEmpVO> voList = new ArrayList<OutletEmpVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  OutletEmp outlet = (OutletEmp)criteria.list().get(i);
			  OutletEmpVO outletVO = new OutletEmpVO();
			  
			  outletVO.setOutletEmpId(outlet.getOutletEmpId());
			  if(outlet.getOutlet() !=null){
				  outletVO.setOutletId(outlet.getOutletId());
				  outletVO.setOutletName(outlet.getOutlet().getOutletName());
			  }
			  if(outlet.getEmployee() !=null){
				  outletVO.setEmployeeId(outlet.getEmployeeId());
				  outletVO.setEmployeeName(outlet.getEmployee().getFullName());
			  }			  		
			 
			  outletVO.setActiveFlag(outlet.getActiveFlag());
			  
			  voList.add(outletVO);
		}
		
		return voList;
	}	
	
	public OutletEmp findOutletEmpByOutletId(Long outletId){
		 Criteria criteria = getSession().createCriteria(OutletEmp.class);
		 criteria.add(Restrictions.eq("outlet.outletId", outletId));
		 criteria.setMaxResults(1);
		 
		 return (OutletEmp)criteria.uniqueResult();
	}
	
	public List<OutletEmpVO> findOutletEmpByOutletIdList(Long outletId){
		 Criteria criteria = getSession().createCriteria(OutletEmp.class);
		 criteria.add(Restrictions.eq("outlet.outletId", outletId));
		 
		 List<OutletEmpVO> result = new  ArrayList<OutletEmpVO>();
		 
		 for(int i=0;i<criteria.list().size();i++){
			 OutletEmp oe = (OutletEmp)criteria.list().get(i);
			 OutletEmpVO vo = new OutletEmpVO();
			 vo.setEmployeeId(oe.getEmployee().getEmployeeId());
			 vo.setEmployeeName(oe.getEmployee().getFullName());
			 vo.setOutletEmpId(oe.getOutletEmpId());
			 vo.setOutletId(oe.getOutletId());
			 vo.setOutletName(oe.getOutlet().getOutletName());
			 vo.setPicFlag(oe.getPicFlag());
			 vo.setEmployeeNo(oe.getEmployee().getEmployeeNo());
			 
			 UserVO userVO = userDAO.dataUserEmp(oe.getEmployee().getEmployeeId());
			 if(userVO != null){
				 vo.setPosition(userVO.getRoleName());
			 }
			 result.add(vo);
		 }
		 
		 return result;
	}
	
	public OutletEmp findOutletEmpByEmployeeId(Long employeeId){
		 Criteria criteria = getSession().createCriteria(OutletEmp.class);
		 criteria.add(Restrictions.eq("employeeId", employeeId));
		 criteria.setMaxResults(1);
		 
		 return (OutletEmp)criteria.uniqueResult();
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}		
}
