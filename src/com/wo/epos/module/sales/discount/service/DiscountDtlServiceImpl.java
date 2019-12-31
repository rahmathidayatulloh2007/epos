package com.wo.epos.module.sales.discount.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.sales.discount.dao.DiscountDAO;
import com.wo.epos.module.sales.discount.dao.DiscountDtlDAO;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.vo.DiscountDtlVO;

@ManagedBean(name="DiscountDtlService")
@ViewScoped
public class DiscountDtlServiceImpl implements DiscountDtlService, Serializable{

	private static final long serialVersionUID = -7078364079753719965L;
	
	@ManagedProperty(value="#{discountDtlDAO}")
	private DiscountDtlDAO discountDtlDAO;
	
	@ManagedProperty(value="#{discountDAO}")
	private DiscountDAO discountDAO;

	@Override
	public List<DiscountDtlVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DiscountDtlVO discountDtlVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(DiscountDtlVO discountDtlVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long discountDtlId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DiscountDtl findById(Long discountDtlId) {
		// TODO Auto-generated method stub
		return null;
	}

}
