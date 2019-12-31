package com.wo.epos.module.sales.register.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.sales.register.model.Register;
import com.wo.epos.module.sales.register.vo.RegisterDtlVO;
import com.wo.epos.module.sales.register.vo.RegisterVO;

public interface RegisterService extends RetrieverDataPage<RegisterVO> {

	public void save(RegisterVO registerVO, List<RegisterDtlVO> registerDtlList, String user);
	public void update(RegisterVO registerVO, List<RegisterDtlVO> registerDtlList, String user);
	public void delete(Long regId);
	public Register findById(Long regId);
	public List<RegisterDtlVO> searchRegisterDtlVoList(Long registerId);
	public List<RegisterVO> findRegisterByOutletId(Long outletId);
	
}	
	