package com.wo.epos.module.purchasing.supplier.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.item.dao.ItemDAO;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.purchasing.supplier.dao.SupplierDAO;
import com.wo.epos.module.purchasing.supplier.dao.SupplierItemDAO;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;

@ManagedBean(name="supplierItemService")
@ViewScoped
public class SupplierItemServiceImpl implements SupplierItemService, Serializable  {
	
	private static final long serialVersionUID = 5004175711241088474L;
	
	@ManagedProperty(value="#{supplierItemDAO}")
	private SupplierItemDAO supplierItemDAO;
	
	@ManagedProperty(value="#{supplierDAO}")
	private SupplierDAO supplierDAO;
	
	@ManagedProperty(value="#{itemDAO}")
	private ItemDAO itemDAO;

	public SupplierItemDAO getSupplierItemDAO() {
		return supplierItemDAO;
	}

	public void setSupplierItemDAO(SupplierItemDAO supplierItemDAO) {
		this.supplierItemDAO = supplierItemDAO;
	}

	public SupplierDAO getSupplierDAO() {
		return supplierDAO;
	}

	public void setSupplierDAO(SupplierDAO supplierDAO) {
		this.supplierDAO = supplierDAO;
	}
	

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<SupplierItemVO> searchData(
			List<? extends SearchObject> searchCriteria, int first,
			int pageSize, String sortField, boolean sortOrder) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(SupplierItemVO supplierItemVO) {
		
		 Supplier supplier = new Supplier();
		 supplier = supplierDAO.findById(supplierItemVO.getSupplierId());
		 
		 Item item = new Item();
		 item = itemDAO.findById(supplierItemVO.getItemId());
		 
		 SupplierItem supplierItem = new SupplierItem();		 
		 supplierItem.setSupplierItemId(supplierItemVO.getSupplierItemId());
		 supplierItem.setSupplier(supplier);
		 supplierItem.setItem(item);
		 supplierItem.setActiveFlag(supplierItemVO.getActiveFlag());
		 supplierItem.setCreateBy(supplierItemVO.getCreateBy());
		 supplierItem.setCreateOn(supplierItemVO.getCreateOn());
		
		 
		 supplierItemDAO.save(supplierItem);
		 supplierItemDAO.flush();
	}

	@Override
	public void update(SupplierItemVO supplierItemVO) {
		 Supplier supplier = new Supplier();
		 supplier = supplierDAO.findById(supplierItemVO.getSupplierId());
		 
		 Item item = new Item();
		 item = itemDAO.findById(supplierItemVO.getItemId());
		 
		 SupplierItem supplierItem = new SupplierItem();
		 supplierItem = supplierItemDAO.findById(supplierItemVO.getSupplierItemId());
		 
		 supplierItem.setSupplierItemId(supplierItemVO.getSupplierItemId());
		 supplierItem.setSupplier(supplier);
		 supplierItem.setItem(item);
		 supplierItem.setActiveFlag(supplierItemVO.getActiveFlag());
		 supplierItem.setUpdateBy(supplierItemVO.getUpdateBy());
		 supplierItem.setUpdateOn(supplierItemVO.getUpdateOn());
		
		 
		 supplierItemDAO.update(supplierItem);
		 supplierItemDAO.flush();
		
	}

	@Override
	public void delete(Long supplierItemId) {
		
		SupplierItem supplierItem = new SupplierItem();
		supplierItem.setSupplierItemId(supplierItemId);
		
		supplierItemDAO.delete(supplierItem);
		supplierItemDAO.flush();
	}

	@Override
	public SupplierItem findById(Long supplierItemId) {
		
		return supplierItemDAO.findById(supplierItemId);
	}

}
