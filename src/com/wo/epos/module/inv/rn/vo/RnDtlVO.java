package com.wo.epos.module.inv.rn.vo;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.DO.vo.DODtlVO;
import com.wo.epos.module.purchasing.po.vo.PoDtlVO;

public class RnDtlVO extends BaseEntity implements Serializable {	
   
    private static final long serialVersionUID = 2194698843765858142L;
	
    private Long rnDtlId;
    private Long rnId;
    
 //   private String poDtlName;
	    
    private Integer lineNo;
    private Double receiveQty;
    
    public PoDtlVO poDtlVO;
    public DODtlVO DODtlVO;
    
    
	public Long getRnDtlId() {
		return rnDtlId;
	}
	
	public void setRnDtlId(Long rnDtlId) {
		this.rnDtlId = rnDtlId;
	}
	
	public Long getRnId() {
		return rnId;
	}
	
	public void setRnId(Long rnId) {
		this.rnId = rnId;
	}
	
	public Integer getLineNo() {
		return lineNo;
	}
	
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	
	public Double getReceiveQty() {
		return receiveQty;
	}
	
	public void setReceiveQty(Double receiveQty) {
		this.receiveQty = receiveQty;
	}

	public PoDtlVO getPoDtlVO() {
		return poDtlVO;
	}

	public void setPoDtlVO(PoDtlVO poDtlVO) {
		this.poDtlVO = poDtlVO;
	}

	public DODtlVO getDODtlVO() {
		return DODtlVO;
	}

	public void setDODtlVO(DODtlVO dODtlVO) {
		DODtlVO = dODtlVO;
	}

	

	
	
	
    
}
