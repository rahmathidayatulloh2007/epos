package com.wo.epos.module.sales.groupOutlet.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.groupOutlet.dao.GroupOutletDAO;
import com.wo.epos.module.sales.groupOutlet.model.GroupOutlet;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;


@ManagedBean(name="groupOutletService")
@ViewScoped
public class GroupOutletServiceImpl implements GroupOutletService, Serializable {
	
	private static final long serialVersionUID = -1280811241104006119L;

	@ManagedProperty(value="#{groupOutletDAO}")
	private GroupOutletDAO groupOutletDAO;

	public GroupOutletDAO getGroupOutletDAO() {
		return groupOutletDAO;
	}

	public void setGroupOutletDAO(GroupOutletDAO groupOutletDAO) {
		this.groupOutletDAO = groupOutletDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<GroupOutletVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return groupOutletDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return groupOutletDAO.searchCountData(searchCriteria);
	}

	@Override
	public GroupOutlet findById(Long groupOutletId) {
		// TODO Auto-generated method stub
		return groupOutletDAO.findById(groupOutletId);
	}
	
	
		
}
