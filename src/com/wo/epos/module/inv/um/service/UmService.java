package com.wo.epos.module.inv.um.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.vo.UmVO;

public interface UmService extends RetrieverDataPage<UmVO> {

	public void save(UmVO umVO, String user);
	public void update(UmVO umVO, String user);
	public void delete(Long umId);
	public Um findById(Long umId);
	public Um findByName(String umName);
	
	public List<UmVO> searchUmList();
	public List<UmVO> searchUmCompany(Long companyId);
	
	
}	
	