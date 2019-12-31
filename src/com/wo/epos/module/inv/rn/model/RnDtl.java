package com.wo.epos.module.inv.rn.model;

import java.io.Serializable;

import com.wo.epos.common.model.BaseEntity;
import com.wo.epos.module.inv.DO.model.DODtl;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.purchasing.po.model.PoDtl;

public class RnDtl extends BaseEntity implements Serializable {	
   
    private static final long serialVersionUID = -8200697631923746431L;
    
    private Long rnDtlId;
   // private Long outletId;
    private Long poDtlId;
    private Long doDtlId;
    private Long receiveUmId;
    
  //  private Outlet outlet;
    private Rn rn;
    private PoDtl poDtl;
    private DODtl DODtl;
    private Um receiveUm;
	
    private Integer lineNo;
    private Double receiveQty;
    
    
    
	public Long getRnDtlId() {
		return rnDtlId;
	}
	
	public void setRnDtlId(Long rnDtlId) {
		this.rnDtlId = rnDtlId;
	}
	
	public Long getPoDtlId() {
		return poDtlId;
	}
	
	public void setPoDtlId(Long poDtlId) {
		this.poDtlId = poDtlId;
	}
	
	public Long getDoDtlId() {
		return doDtlId;
	}
	
	public void setDoDtlId(Long doDtlId) {
		this.doDtlId = doDtlId;
	}
	
	public Long getReceiveUmId() {
		return receiveUmId;
	}
	
	public void setReceiveUmId(Long receiveUmId) {
		this.receiveUmId = receiveUmId;
	}
	
	public Rn getRn() {
		return rn;
	}
	
	public void setRn(Rn rn) {
		this.rn = rn;
	}
	
	public PoDtl getPoDtl() {
		return poDtl;
	}
	
	public void setPoDtl(PoDtl poDtl) {
		this.poDtl = poDtl;
	}
	
	
	

	public DODtl getDODtl() {
		return DODtl;
	}

	public void setDODtl(DODtl dODtl) {
		DODtl = dODtl;
	}

	public Um getReceiveUm() {
		return receiveUm;
	}
	
	public void setReceiveUm(Um receiveUm) {
		this.receiveUm = receiveUm;
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
	
}
