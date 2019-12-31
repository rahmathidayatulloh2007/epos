package com.wo.epos.module.inv.item.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.category.dao.CategoryDAO;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.inv.item.dao.ItemBomDAO;
import com.wo.epos.module.inv.item.dao.ItemDAO;
import com.wo.epos.module.inv.item.dao.ProductBomDAO;
import com.wo.epos.module.inv.item.dao.ProductDAO;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.model.ItemBom;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ItemBomVO;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.inv.item.vo.ProductBomVO;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.dao.ItemDiscountDAO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.inv.um.dao.UmDAO;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.purchasing.supplier.dao.SupplierDAO;
import com.wo.epos.module.purchasing.supplier.dao.SupplierItemDAO;
import com.wo.epos.module.purchasing.supplier.model.Supplier;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="itemService")
@ViewScoped
public class ItemServiceImpl implements ItemService, Serializable{

	private static final long serialVersionUID = -4583797523100837915L;
	
	@ManagedProperty(value="#{itemDAO}")
	private ItemDAO itemDAO;

	@ManagedProperty(value="#{itemBomDAO}")
	private ItemBomDAO itemBomDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{categoryDAO}")
	private CategoryDAO categoryDAO;
	
	@ManagedProperty(value="#{umDAO}")
	private UmDAO umDAO;
	
	@ManagedProperty(value="#{supplierDAO}")
	private SupplierDAO supplierDAO;
	
	@ManagedProperty(value="#{productDAO}")
	private ProductDAO productDAO;
	
	@ManagedProperty(value="#{productBomDAO}")
	private ProductBomDAO productBomDAO;
	
	@ManagedProperty(value="#{supplierItemDAO}")
	private SupplierItemDAO supplierItemDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;

;
	
	@ManagedProperty(value="#{itemDiscountDAO}")
	private ItemDiscountDAO itemDiscountDAO;
	
	public ItemDiscountDAO getItemDiscountDAO() {
		return itemDiscountDAO;
	}

	public void setItemDiscountDAO(ItemDiscountDAO itemDiscountDAO) {
		this.itemDiscountDAO = itemDiscountDAO;
	}

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public ItemBomDAO getItemBomDAO() {
		return itemBomDAO;
	}

	public void setItemBomDAO(ItemBomDAO itemBomDAO) {
		this.itemBomDAO = itemBomDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public UmDAO getUmDAO() {
		return umDAO;
	}

	public void setUmDAO(UmDAO umDAO) {
		this.umDAO = umDAO;
	}
	

	public SupplierDAO getSupplierDAO() {
		return supplierDAO;
	}

	public void setSupplierDAO(SupplierDAO supplierDAO) {
		this.supplierDAO = supplierDAO;
	}
	

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public ProductBomDAO getProductBomDAO() {
		return productBomDAO;
	}

	public void setProductBomDAO(ProductBomDAO productBomDAO) {
		this.productBomDAO = productBomDAO;
	}

	public SupplierItemDAO getSupplierItemDAO() {
		return supplierItemDAO;
	}

	public void setSupplierItemDAO(SupplierItemDAO supplierItemDAO) {
		this.supplierItemDAO = supplierItemDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return itemDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return itemDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(ItemVO itemVO, List<SupplierItemVO> supplierItemList, ProductVO productVO, 
			ProductBomVO productBomVO, List<ItemBomVO> itemBomList,List<ItemDiscountVO> itemDiscountList, String user) {
		
		Company company = new Company();
		Category category = new Category();
		Um um = new Um();
		Um um2 = new Um();
		Item item = new Item();
		 
		Product product = new Product();
		ProductBom productBom = new ProductBom();
		Supplier supplier = new Supplier();

		company = companyDAO.findById(itemVO.getCompanyId());
		category = categoryDAO.findById(itemVO.getCategoryId());
		um = umDAO.findById(itemVO.getUmId());
		um2 = umDAO.findById(itemVO.getUm2Id());
		//item.setItemId(itemVO.getItemId());
		item.setCompany(company);
		item.setItemCode(itemVO.getItemCode());
		item.setItemName(itemVO.getItemName());
		item.setItemDesc(itemVO.getItemDesc());
		item.setUm(um);
		item.setUm2(um2);
		item.setUnitPerUm2(itemVO.getUnitPerUm2());
		item.setUnitCost(itemVO.getUnitCost());
		item.setUnitPrice(itemVO.getUnitPrice());
		  
		item.setCategory(category);
		item.setStockQty(itemVO.getStockQty());
		if(itemVO.getReorderQty() !=null){
		   item.setReorderQty(new Long(0));
		}
		item.setAveragePrice(itemVO.getAveragePrice());
		item.setImageFile(itemVO.getImageFile());
		item.setImageFilename(itemVO.getImageFilename());
		item.setBarcode(itemVO.getBarcode());
		item.setCompositeFlag(itemVO.getCompositeFlag());
		
		List<SupplierItem> itemSuppList = new ArrayList<SupplierItem>();
		if(supplierItemList.size()> 0) {
			for(int i=0 ; i < supplierItemList.size() ; i++) {
				supplier = supplierDAO.findById(supplierItemList.get(i).getSupplierId());
				SupplierItem supplierItem = new SupplierItem();
				SupplierItemVO supplierItemVO = supplierItemList.get(i);
				
				supplierItem.setItem(item);
				supplierItem.setSupplier(supplier);
				supplierItem.setSupplierItemId(supplierItemVO.getSupplierItemId());
				supplierItem.setActiveFlag("Y");
				supplierItem.setCreateBy("Admin");
				supplierItem.setCreateOn(new Timestamp(System.currentTimeMillis()));					
				
				itemSuppList.add(supplierItem);
			}
			
		}
			List<ItemDiscount> itemDiscountSuppList = new ArrayList<ItemDiscount>();
			if(itemDiscountList.size()> 0) {
				for(int i=0 ; i < itemDiscountList.size() ; i++) {
					
					ItemDiscount itemDiscount = new ItemDiscount();
					ItemDiscountVO itemDiscountVO = itemDiscountList.get(i);
					ParameterDtl customerType = itemDiscountVO.getParameterDtl();
					itemDiscount.setItemId(item);
					itemDiscount.setCustomerType(customerType);
					itemDiscount.setDiscount1(itemDiscountVO.getDiscount1());
					itemDiscount.setDiscount2(itemDiscountVO.getDiscount2());
					itemDiscount.setDiscount3(itemDiscountVO.getDiscount3());
					itemDiscount.setActiveFlag("Y");
					itemDiscount.setCreateBy("Admin");
					itemDiscount.setCreateOn(new Timestamp(System.currentTimeMillis()));					
					
					itemDiscountSuppList.add(itemDiscount);
				}
		}
		
		// insert ItemBom [start]
		List<ItemBom> itmBommList = new ArrayList<ItemBom>();
		if (itemBomList.size() > 0) {
			for (int i = 0; i < itemBomList.size(); i++) {
				ItemBomVO itemBomVo = itemBomList.get(i);
				ItemBom itemBom = new ItemBom();

				Item itemComposition = new Item();
				itemComposition.setItemId(itemBomVo.getItemCompositionId());				
				itemBom.setItemComposition(itemComposition);

				itemBom.setItem(item);

				itemBom.setItemQty(itemBomVo.getItemQty());

				itemBom.setActiveFlag("Y");
				itemBom.setCreateBy(user);
				itemBom.setCreateOn(new Timestamp(System.currentTimeMillis()));

				itmBommList.add(itemBom);
			}
		}
		// insert ItemBom [end]
		item.setListItemDiscount(itemDiscountSuppList);
		item.setListSupplierItem(itemSuppList);
		item.setListItemBom(itmBommList);
		item.setActiveFlag(itemVO.getActiveFlag());
		item.setCreateBy(user);
		item.setCreateOn(new Timestamp(System.currentTimeMillis()));
				
		itemDAO.save(item);
		
		if(productVO.getSellProduct() == true)
		{
			//product.setProductId(productVO.getProductId());
			product.setCompanyId(item.getCompany().getCompanyId());
			product.setProductCode(item.getItemCode());
			product.setProductName(item.getItemName());
			product.setProductDesc(item.getItemDesc());
			product.setUm(item.getUm());
			product.setUm2(item.getUm2());
			product.setUnitPerUm2(item.getUnitPerUm2());
			product.setUnitCost(item.getUnitCost());
			product.setUnitPrice(item.getUnitPrice());
			
			product.setImageFile(item.getImageFile());
			product.setImageFilename(item.getImageFilename());
			product.setCategoryId(item.getCategory().getCategoryId());
			product.setLaunchingDt(productVO.getLaunchingDt());
			product.setUnitCost(productVO.getUnitCost());
			product.setUnitPrice(productVO.getUnitPrice());
			product.setActiveFlag("Y");
			product.setCreateBy(user);
			product.setCreateOn(new Timestamp(System.currentTimeMillis()));	
			
			productDAO.save(product);
			
			productBom.setProduct(product);
			productBom.setItem(item);
			productBom.setItemQty(new Double(1));
			productBom.setActiveFlag("Y");
			productBom.setCreateBy(user);
			productBom.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
			productBomDAO.save(productBom);
			
			
		}
		
		itemDAO.flush();
	
	}

	@Override
	public void update(ItemVO itemVO, List<SupplierItemVO> supplierItemList, ProductVO productVO, 
			ProductBomVO productBomVO, List<ItemBomVO> itemBomList,List<ItemDiscountVO> itemDiscountList, String user) {
		
		Company company = new Company();
		Category category = new Category();
		Um um = new Um();
		Um um2 = new Um();
		Product product = new Product();
		ProductBom productBom = new ProductBom();
			
		company = companyDAO.findById(itemVO.getCompanyId());
		category = categoryDAO.findById(itemVO.getCategoryId());
		um2 = umDAO.findById(itemVO.getUm2Id());
		um = umDAO.findById(itemVO.getUmId());
		
		Item item = new Item();
		item = itemDAO.findById(itemVO.getItemId());
		
		item.setCompany(company);
		item.setItemCode(itemVO.getItemCode());
		item.setItemName(itemVO.getItemName());
		item.setItemDesc(itemVO.getItemDesc());
		item.setUm(um);
		
		item.setUm2(um2);
		item.setUnitPerUm2(itemVO.getUnitPerUm2());
		item.setUnitCost(itemVO.getUnitCost());
		item.setUnitPrice(itemVO.getUnitPrice());
		
		item.setCategory(category);
		item.setStockQty(itemVO.getStockQty());
		item.setReorderQty(itemVO.getReorderQty());
		item.setAveragePrice(itemVO.getAveragePrice());
		item.setImageFile(itemVO.getImageFile());
		item.setImageFilename(itemVO.getImageFilename());
		item.setBarcode(itemVO.getBarcode());
		item.setCompositeFlag(itemVO.getCompositeFlag());
		
		//---------------------------------------------------------------------
				List<ItemDiscount> ItemDiscountDbList = item.getListItemDiscount();
				if (itemDiscountList != null) {
					ItemDiscount itemDiscount = null;
					ItemDiscount itemDiscountDb = null;
					ItemDiscountVO itemDiscountVO = null;
					boolean exist = false;

					for (int x = 0; x < itemDiscountList.size(); x++) {

						itemDiscountVO = (ItemDiscountVO) itemDiscountList.get(x);
						exist = false;
						for (int i = 0; i < ItemDiscountDbList.size(); i++) {
							itemDiscountDb = (ItemDiscount) ItemDiscountDbList.get(i);

							if (itemDiscountDb.getItemDiscountId().longValue() == itemDiscountVO.getItemDiscountId().longValue()) {
								exist = true;
								break;
							}
						}

						if (exist) {
							/* update */
							itemDiscountDb.setItemId(item);
							itemDiscountDb.setCustomerType(itemDiscountVO.getParameterDtl());
							itemDiscountDb.setDiscount1(itemDiscountVO.getDiscount1());
							itemDiscountDb.setDiscount2(itemDiscountVO.getDiscount2());
							itemDiscountDb.setDiscount3(itemDiscountVO.getDiscount3());
							itemDiscountDb.setUpdateBy(user);
							itemDiscountDb.setUpdateOn(new Timestamp(System.currentTimeMillis()));

						}

						else {
							/* insert */
							itemDiscount = new ItemDiscount();
							itemDiscount.setItemId(item);
							itemDiscount.setCustomerType(itemDiscountVO.getParameterDtl());
							itemDiscount.setDiscount1(itemDiscountVO.getDiscount1());
							itemDiscount.setDiscount2(itemDiscountVO.getDiscount2());
							itemDiscount.setDiscount3(itemDiscountVO.getDiscount3());
							itemDiscount.setActiveFlag("Y");
							itemDiscount.setCreateOn(new Timestamp(System.currentTimeMillis()));
							itemDiscount.setCreateBy(user);

							ItemDiscountDbList.add(itemDiscount);
						}

					}
					// delete ItemDiscount
					for (int i = 0; i < ItemDiscountDbList.size(); i++) {
						itemDiscountDb = (ItemDiscount) ItemDiscountDbList.get(i);

						for (int x = 0; x < itemVO.getListItemDiscount().size(); x++) {
							itemDiscountVO = (ItemDiscountVO) itemVO.getListItemDiscount().get(x);
							exist = false;

							if (itemDiscountDb.getItemDiscountId().longValue() == itemDiscountVO.getItemDiscountId().longValue()) {
								exist = true;
								break;
							}

							if (!exist) {
								i--;
								ItemDiscountDbList.remove(itemDiscountDb);
							}
						}
					}

				}
				
				//--------------------------------------------------------------------------		
				
		
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
			
		}
		
		
		
		
		
		
		// insert/update/delete ItemBom [start]
		if (itemBomList != null) {
			ItemBom itemBom = null;
			ItemBom itemBomDb = null;
			ItemBomVO itemBomVO = null;
			boolean exist = false;

			List<ItemBom> itemBomDbList = item.getListItemBom();

			for (int x = 0; x < itemBomList.size(); x++) {
				itemBomVO = (ItemBomVO) itemBomList.get(x);
				exist = false;

				for (int i = 0; i < itemBomDbList.size(); i++) {
					itemBomDb = (ItemBom) itemBomDbList.get(i);

					if (itemBomDb.getItemComposition().getItemId().longValue() == itemBomVO
							.getItemCompositionId().longValue()) {
						exist = true;
						break;
					}
				}

				if (exist) {
					// update ItemBom
					// Item itemComposition = new Item();
					// itemComposition.setItemId(itemBomVO.getItemId());
					// itemBomDb.setItemComposition(itemComposition);
					//
					// itemBomDb.setItem(item);

					itemBomDb.setItemQty(itemBomVO.getItemQty());
					itemBomDb.setUpdateBy(user);
					itemBomDb.setUpdateOn(new Timestamp(System
							.currentTimeMillis()));

				} else {
					// add ItemBom
					itemBom = new ItemBom();

					Item itemComposition = new Item();
					itemComposition.setItemId(itemBomVO.getItemCompositionId());
					itemBom.setItemComposition(itemComposition);

					itemBom.setItem(item);

					itemBom.setItemQty(itemBomVO.getItemQty());

					itemBom.setActiveFlag("Y");
					itemBom.setCreateBy(user);
					itemBom.setCreateOn(new Timestamp(System
							.currentTimeMillis()));

					itemBomDbList.add(itemBom);
				}
			}

			// delete ItemBom
			for (int i = 0; i < itemBomDbList.size(); i++) {
				itemBomDb = (ItemBom) itemBomDbList.get(i);

				for (int x = 0; x < itemBomList.size(); x++) {
					itemBomVO = (ItemBomVO) itemBomList.get(x);
					exist = false;

					if (itemBomDb.getItemComposition().getItemId().longValue() == itemBomVO
							.getItemCompositionId().longValue()) {
						exist = true;

						break;
					}
				}

				if (!exist) {
					i--;
					itemBomDbList.remove(itemBomDb);
				}
			}
		}
		// insert/update/delete ItemBom [end]
		
		item.setActiveFlag(itemVO.getActiveFlag());
		item.setUpdateBy(user);
		item.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		
		itemDAO.update(item);
		
		
		if(productVO.getSellProduct() == true)
		{
			product = productDAO.findById(productVO.getProductId());
			
			product.setCompanyId(item.getCompany().getCompanyId());
			product.setProductCode(item.getItemCode());
			product.setProductName(item.getItemName());
			product.setProductDesc(item.getItemDesc());
			product.setImageFile(item.getImageFile());
			product.setImageFilename(item.getImageFilename());
			product.setCategoryId(item.getCategory().getCategoryId());
			product.setLaunchingDt(productVO.getLaunchingDt());
			product.setUnitCost(productVO.getUnitCost());
			product.setUnitPrice(productVO.getUnitPrice());
			product.setUnitPerUm2(productVO.getUnitPerUm2());
			
/*			product.setDiscount1(productVO.getDiscount1());
			product.setDiscount2(productVO.getDiscount2());
			product.setDiscount3(productVO.getDiscount3());
			*/
			product.setActiveFlag(productVO.getActiveFlag());
			product.setUpdateBy("Admin");
			product.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			productDAO.update(product);
			
			
			productBom.setBomId(productBomVO.getBomId());
			productBom.setProduct(product);
			productBom.setItem(item);
			productBom.setItemQty(new Double(1));
			productBom.setActiveFlag(productBomVO.getActiveFlag());
			productBom.setCreateBy("Admin");
			productBom.setCreateOn(new Timestamp(System.currentTimeMillis()));
			
			productBomDAO.update(productBom);
			
			
		}
		
		itemDAO.flush();
		
	}

	@Override
	public void delete(Long itemId) {
		
		Item item = new Item();	        
	    
		item = itemDAO.findById(itemId);
		
		itemDAO.delete(item);
		itemDAO.flush();
	}

	@Override
	public Item findById(Long itemId) {
		
		return itemDAO.findById(itemId);
	}

	@Override
	public SupplierItem findByDetailId(Long id) {
		
		return itemDAO.findByDetailId(id);
	}

	@Override
	public List<SupplierItem> searchSupplierItemList(Long itemId) {
		
		return itemDAO.searchSupplierItemList(itemId);
	}

	@Override
	public List<ItemBomVO> searchItemBomList(Long itemId) {
		
		return itemDAO.searchItemBomList(itemId);
	}
	
	@Override
	public List<ItemVO> searchItemList() {
		
		return itemDAO.searchItemList();
	}

	@Override
	public List<ItemVO> searchItemNonCompositeListByCompany(Long companyId) {
		return itemDAO.searchItemList(CommonConstants.N, true, companyId);
	}

	@Override
	public List<ItemVO> searchItemCompositeListByCompany(Long companyId) {
		return itemDAO.searchItemList(CommonConstants.Y, true, companyId);
	}

	@Override
	public List<ItemVO> searchItemNonCompositeList() {
		return itemDAO.searchItemList(CommonConstants.N, false, null);
	}

	@Override
	public List<ItemVO> searchItemCompositeList() {
		return itemDAO.searchItemList(CommonConstants.Y, false, null);
	}

	@Override
	public List<ItemVO> searchItemList(String compositeFlag,
			boolean filterCompany, Long companyId) {
		return itemDAO.searchItemList(compositeFlag, filterCompany, companyId);
	}
	
	@Override
	public List<CompanyVO> searchCompanyList() {
		
		return companyDAO.searchCompanyList();
	}

	@Override
	public List<UmVO> searchUmList() {
		
		return umDAO.searchUmList();
	}

	@Override
	public List<SupplierVO> searchSupplierList() {
		
		return supplierDAO.searchSupplierList();
	}
	
	@Override
	public List<CategoryVO> searchCategoryList() {
		
		return categoryDAO.searchCategoryList();
	}	
	
	@Override
	public List<ItemVO> searchItemDialogPoList(String search) {
		
		return itemDAO.searchItemDiloagPoList(search);		               
	}

	@Override
	public Long getNextId() {
		
		return itemDAO.getNextId();
	}

	@Override
	public Item findByItemCode(String itemCode) {
		return itemDAO.findByItemCode(itemCode);
	}

	@Override
	public List<ItemVO> searchItemByCompanyList(Long companyId) {
		return itemDAO.searchItemByCompanyList(companyId);
	}			
	
	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		
		return parameterDAO.parameterDtlList(parameterCode);
	}

	@Override
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemDiscountList(itemDiscountId);
	}

}
