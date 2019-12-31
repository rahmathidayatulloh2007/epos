package com.wo.epos.module.uam.parameter.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

@ManagedBean(name="parameterService")
@ViewScoped
public class ParameterServiceImpl implements ParameterService, Serializable {

	private static final long serialVersionUID = -5927313745557525240L;
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
		
	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	@Override	
	public void save(ParameterVO parameterVo, String user) {
		String detailResume = "";
		Parameter parameter = new Parameter();
		
		parameter.setParameterCode(parameterVo.getParameterCode());
		parameter.setParameterName(parameterVo.getParameterName());
		parameter.setParameterDesc(parameterVo.getParameterDesc());			
		
		if(parameterVo.getListDetail()!=null && parameterVo.getListDetail().size()> 0) {
			for(int i=0 ; i < parameterVo.getListDetail().size() ; i++) {
				ParameterDtl parameterDtl = new ParameterDtl();
				ParameterDtlVO parameterDtlVo = parameterVo.getListDetail().get(i);
				parameterDtl.setParameter(parameter);
				parameterDtl.setParameterDtlCode(parameterDtlVo.getParameterDtlCode());
				parameterDtl.setParameterDtlName(parameterDtlVo.getParameterDtlName());				
				parameterDtl.setParameterDtlDesc(parameterDtlVo.getParameterDtlDesc());
				parameterDtl.setActiveFlag("Y");
				parameterDtl.setCreateBy(user);
				parameterDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));	
				
				String content = parameterDtlVo.getParameterDtlName() + ":" + parameterDtlVo.getParameterDtlDesc();
				
				if (i == 0) {
					detailResume = detailResume + content;
				} else {
					detailResume = detailResume + ", " + content;
				}
				
				parameter.getListDetail().add(parameterDtl);
			}
		}
		
		parameter.setDetailResume(detailResume);
		
		parameter.setActiveFlag(CommonConstants.Y);
		parameter.setCreateBy("Admin");
		parameter.setCreateOn(new Timestamp(System.currentTimeMillis()));		
		
		parameterDAO.save(parameter);
		parameterDAO.flush();		
	}

	@Override
	public void update(ParameterVO parameterVo, String user) {		
		String detailResume = "";
		
        Parameter paramHeader = null;
		paramHeader = parameterDAO.findById(parameterVo.getParameterCode());
		paramHeader.setParameterName(parameterVo.getParameterName());
		paramHeader.setParameterDesc(parameterVo.getParameterDesc());
		paramHeader.setActiveFlag(parameterVo.getActiveFlag());
	
		List<ParameterDtl> childListReal = paramHeader.getListDetail();
		if(parameterVo.getListDetail() != null) {
			ParameterDtl parameterDetail = null;
			ParameterDtl parameterDetailDatabase = null;
			ParameterDtlVO voParamDtl = null;
			boolean exist = false;
			
			/* untuk insert data baru dan update data lama */
			for(int x=0; x<parameterVo.getListDetail().size(); x++)
			{
				voParamDtl = (ParameterDtlVO) parameterVo.getListDetail().get(x);
				
				exist = false;
				for(int i=0; i<childListReal.size(); i++)
				{
					parameterDetailDatabase = (ParameterDtl) childListReal.get(i);
					
					if (parameterDetailDatabase.getParameterDtlCode().equals(voParamDtl.getParameterDtlCode())) {
						exist = true;
						break;
					}
				}
				
				if (exist)
				{	/* update */
					parameterDetailDatabase.setParameterDtlName(voParamDtl.getParameterDtlName());
					parameterDetailDatabase.setParameterDtlDesc(voParamDtl.getParameterDtlDesc());
					parameterDetailDatabase.setUpdateBy(user);
					parameterDetailDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				}
				else
				{	/* insert */
					parameterDetail = new ParameterDtl();
					parameterDetail.setParameter(paramHeader);
					parameterDetail.setParameterDtlCode(voParamDtl.getParameterDtlCode());
					parameterDetail.setParameterDtlName(voParamDtl.getParameterDtlName());
					parameterDetail.setParameterDtlDesc(voParamDtl.getParameterDtlDesc());
					parameterDetail.setActiveFlag("Y");
					parameterDetail.setCreateOn(new Timestamp(System.currentTimeMillis()));
					parameterDetail.setCreateBy(user);
					
					childListReal.add(parameterDetail);
				}
			}

			
			for(int i=0; i<childListReal.size(); i++)
			{
				parameterDetailDatabase = (ParameterDtl) childListReal.get(i);

				for(int x=0; x<parameterVo.getListDetail().size(); x++)
				{
					voParamDtl =(ParameterDtlVO) parameterVo.getListDetail().get(x);
					exist = false;
					
					if(parameterDetailDatabase.getParameterDtlCode() !=null) {
						if (parameterDetailDatabase.getParameterDtlCode().equals(voParamDtl.getParameterDtlCode())) {
							exist = true;
							
							String content = parameterDetailDatabase.getParameterDtlName() + ":" + parameterDetailDatabase.getParameterDtlDesc();
							if (i == 0) {
								detailResume = detailResume + content;
							} else {
								detailResume = detailResume + ", " + content;
							}
							
							break;
						}
					}
				}

				if (!exist)
				{	/* delete */
					i--;
					childListReal.remove(parameterDetailDatabase);
				}
			}
			
		}		
		
		paramHeader.setDetailResume(detailResume);
		parameterDAO.update(paramHeader);
		parameterDAO.flush();
        
       
	}

	@Override
	public void delete(String parameterCode) {
		
		Parameter parameter = new Parameter();	        
	    
		parameter = parameterDAO.findById(parameterCode);
		
		parameterDAO.delete(parameter);
		parameterDAO.flush();
		
	}

	
	@Override
	public Parameter findById(String parameterCode)
	{
		return parameterDAO.findById(parameterCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ParameterVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return parameterDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return parameterDAO.searchCountData(searchCriteria);
	}

	@Override
	public List<ParameterVO> parameterSearch() {
		// TODO Auto-generated method stub
		return parameterDAO.parameterSearch();
	}

	@Override
	public ParameterDtl findByDetailId(String id) {
		return parameterDAO.findByDetailId(id);
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		return parameterDAO.parameterDtlList(parameterCode);
	}
	
}




/*

Supplier supplier = new Supplier();

List<SupplierItem> childListReal = item.getListSupplierItem();

if(supplierItemList!=null) {
	SupplierItem supplierItem = null;
	SupplierItem supplierItemDatabase = null;
	SupplierItemVO supplierItemVO = null;
	boolean exist = false;
	
	for(int x=0 ; x < supplierItemList.size() ; x++) {
		
		supplierItemVO = (SupplierItemVO) supplierItemList.get(x);
		exist = false;
		for(int i=0; i<childListReal.size(); i++)
		{
			supplierItemDatabase = (SupplierItem) childListReal.get(i);
			
			
			if (Integer.parseInt(supplierItemDatabase.getSupplier().getSupplierId()+"") == Integer.parseInt(supplierItemVO.getSupplierId()+"")) {
				exist = true;
				break;
			}
		}
		
		if (exist)
		{	
			supplierItemDatabase.setItem(item);
			supplier = supplierDAO.findById(supplierItemVO.getSupplierId());
			supplierItemDatabase.setSupplier(supplier);
			supplierItemDatabase.setUpdateBy(user);
			supplierItemDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			
		}
		
		else
		{	
			supplierItem = new SupplierItem();
			supplierItem.setItem(item);
			supplier = supplierDAO.findById(supplierItemVO.getSupplierId());
			supplierItem.setSupplier(supplier);
			supplierItem.setActiveFlag("Y");
			supplierItem.setCreateOn(new Timestamp(System.currentTimeMillis()));
			supplierItem.setCreateBy(user);
			
			childListReal.add(supplierItem);
		}
		
		
	}
	
	for(int i=0; i<childListReal.size(); i++)
	{
		supplierItemDatabase = (SupplierItem) childListReal.get(i);

		for(int x=0; x<supplierItemList.size(); x++)
		{
			supplierItemVO =(SupplierItemVO) supplierItemList.get(x);
			exist = false;
			
			if(supplierItemDatabase.getSupplier().getSupplierId() !=null) {
				if (Integer.parseInt(supplierItemDatabase.getSupplier().getSupplierId()+"") == Integer.parseInt(supplierItemVO.getSupplierId()+"")){
					exist = true;
					
					break;
				}
			}
		}

		if (!exist)
		{	
			i--;
			childListReal.remove(supplierItemDatabase);
		}
	}
	
}*/


























