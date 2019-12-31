package com.wo.epos.module.inv.rn.service;

import java.util.List;

import com.wo.epos.common.paging.RetrieverDataPage;
import com.wo.epos.module.inv.rn.model.Rn;
import com.wo.epos.module.inv.rn.vo.RnDtlVO;
import com.wo.epos.module.inv.rn.vo.RnVO;

public interface RnService extends RetrieverDataPage<RnVO>{
	
	public void savePo(RnVO rnVO, List<RnDtlVO> rnDtlVoPoList, String user);
	public void saveDo(RnVO rnVO, List<RnDtlVO> rnDtlVoPoList, String user);
	public void updatePo(RnVO rnVO, List<RnDtlVO> rnDtlVOList, String user);
	public void updateDo(RnVO rnVO, List<RnDtlVO> rnDtlVOList, String user);
	public void delete(Long rnId, String user);
	public Rn findById(Long rnId);

}
