package com.wo.epos.module.sales.groupOutlet.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.groupOutlet.model.GroupOutlet;
import com.wo.epos.module.sales.groupOutlet.vo.GroupOutletVO;


public interface GroupOutletDAO extends GenericDAO<GroupOutlet, Long>, RetrieverDataPage<GroupOutletVO> {
	
	public List<GroupOutletVO> groupOutletSearch();
	
}
