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
import com.wo.epos.module.inv.item.dao.ItemDAO;
import com.wo.epos.module.inv.item.dao.ProductBomDAO;
import com.wo.epos.module.inv.item.dao.ProductDAO;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ProductBomVO;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.inv.um.dao.UmDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="productService")
@ViewScoped
public class ProductServiceImpl  implements ProductService, Serializable {

	private static final long serialVersionUID = -300856395269222004L;
	
	@ManagedProperty(value="#{productDAO}")
	private ProductDAO productDAO;
	
	@ManagedProperty(value="#{productBomDAO}")
	private ProductBomDAO productBomDAO;
	
	@ManagedProperty(value="#{umDAO}")
	private UmDAO umDAO;
	
	@ManagedProperty(value="#{itemDAO}")
	private ItemDAO itemDAO;
	
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public UmDAO getUmDAO() {
		return umDAO;
	}

	public void setUmDAO(UmDAO umDAO) {
		this.umDAO = umDAO;
	}

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
	
	public ProductBomDAO getProductBomDAO() {
		return productBomDAO;
	}

	public void setProductBomDAO(ProductBomDAO productBomDAO) {
		this.productBomDAO = productBomDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProductVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return productDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return productDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(ProductVO productVO, List<ProductBomVO> productBomVoList,List<ItemDiscountVO> itemDiscountList, String user) {
		 Product productHeader = new Product();
		 productHeader.setCompanyId(productVO.getCompanyId());
		 productHeader.setProductCode(productVO.getProductCode());
		 productHeader.setProductName(productVO.getProductName());
		 productHeader.setProductDesc(productVO.getProductDesc());
		 productHeader.setUm(umDAO.findById(productVO.getUmId()));
		 productHeader.setUm2(umDAO.findById(productVO.getUm2Id()));
		 productHeader.setUnitPerUm2(productVO.getUnitPerUm2());
		 productHeader.setCategoryId(productVO.getCategoryId());
		 productHeader.setLaunchingDt(productVO.getLaunchingDt());
		 productHeader.setUnitCost(productVO.getUnitCost());
		 productHeader.setUnitPrice(productVO.getUnitPrice());		 
		 productHeader.setActiveFlag(productVO.getActiveFlag());
		 productHeader.setIngredientFlag(productVO.getIngredientFlag());
		 productHeader.setImageFile(productVO.getImageFile());
		 productHeader.setImageFilename(productVO.getImageFilename());
		 productHeader.setBarcode(productVO.getBarcode());
		 productHeader.setBufferStock(productVO.getBufferStock());
		 
		 String detailResume = "";
		 List<ProductBom> productBomList = new ArrayList<ProductBom>();
		 if(productBomVoList.size() > 0){
			 for(int i=0; i<productBomVoList.size(); i++){
				  ProductBomVO bomVo = (ProductBomVO)productBomVoList.get(i);
				  ProductBom bom = new ProductBom();				  
				  
				  bom.setProduct(productHeader);
				  bom.setItem(itemDAO.findById(bomVo.getItemId()));				  
				  bom.setItemQty(bomVo.getItemQty());
				  bom.setActiveFlag(CommonConstants.Y);
				  bom.setCreateBy(user);
				  bom.setCreateOn(new Timestamp(System.currentTimeMillis()));
				  
				  String content = bomVo.getItemName()+ ": " + bomVo.getItemQty()+" "+bomVo.getUmName();
				  if (i == 0) {
						detailResume = detailResume + content;
				  } else {
				 		detailResume = detailResume + ", " + content;
				  }
				  
				  productBomList.add(bom);
			 }
		 }

			List<ItemDiscount> itemDiscountSuppList = new ArrayList<ItemDiscount>();
			if(itemDiscountList.size()> 0) {
				for(int i=0 ; i < itemDiscountList.size() ; i++) {
					
					ItemDiscount itemDiscount = new ItemDiscount();
					ItemDiscountVO itemDiscountVO = itemDiscountList.get(i);
					ParameterDtl customerType = itemDiscountVO.getParameterDtl();
					itemDiscount.setProductId(productHeader);
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
		 
		 productHeader.setListItemDiscount(itemDiscountSuppList);	
		 productHeader.setProductBomList(productBomList);
		 productHeader.setItemComposition(detailResume);
		 productHeader.setCreateBy(user);
		 productHeader.setCreateOn(new Timestamp(System.currentTimeMillis()));
		 
		 productDAO.save(productHeader);
		 productDAO.flush();
		 
	}

	@Override
	public void update(ProductVO productVO, List<ProductBomVO> productBomVoList,List<ItemDiscountVO> itemDiscountList, String user) {
        String detailResume = "";
		
        Product productHeader = null;
        productHeader = productDAO.findById(productVO.getProductId());
		productHeader.setCompanyId(productVO.getCompanyId());
		productHeader.setProductName(productVO.getProductName());
		productHeader.setProductDesc(productVO.getProductDesc());
		productHeader.setUm(umDAO.findById(productVO.getUmId()));
	    productHeader.setUm2(umDAO.findById(productVO.getUm2Id()));
		productHeader.setUnitPerUm2(productVO.getUnitPerUm2());
		productHeader.setCategoryId(productVO.getCategoryId());
		productHeader.setLaunchingDt(productVO.getLaunchingDt());
		productHeader.setUnitCost(productVO.getUnitCost());
		productHeader.setUnitPrice(productVO.getUnitPrice());		 
		productHeader.setActiveFlag(productVO.getActiveFlag());
		productHeader.setIngredientFlag(productVO.getIngredientFlag());
		productHeader.setImageFile(productVO.getImageFile());
		productHeader.setImageFilename(productVO.getImageFilename());
		productHeader.setBarcode(productVO.getBarcode());
		productHeader.setBufferStock(productVO.getBufferStock());
		
		List<ProductBom> childListReal = productHeader.getProductBomList();
		if(productBomVoList != null) {
			ProductBom productBom = null;
			ProductBom productBomDatabase = null;
			ProductBomVO productBomVO = null;
			boolean exist = false;
			
			/* untuk insert data baru dan update data lama */
			for(int x=0; x<productBomVoList.size(); x++)
			{
				productBomVO = (ProductBomVO) productBomVoList.get(x);
				
				exist = false;
				for(int i=0; i<childListReal.size(); i++)
				{
					productBomDatabase = (ProductBom) childListReal.get(i);
					
					if (Integer.parseInt(productBomDatabase.getItem().getItemId()+"") == Integer.parseInt(productBomVO.getItemId()+"")){
						exist = true;
						break;
					}
				}
				
				if (exist)
				{	/* update */
					productBomDatabase.setItem(itemDAO.findById(productBomVO.getItemId()));				  
					productBomDatabase.setItemQty(productBomVO.getItemQty());
					productBomDatabase.setActiveFlag(CommonConstants.Y);
					productBomDatabase.setUpdateBy(user);
					productBomDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));
				}
				else
				{	/* insert */
					productBom = new ProductBom();		  
					productBom.setProduct(productHeader);
					productBom.setItem(itemDAO.findById(productBomVO.getItemId()));				  
					productBom.setItemQty(productBomVO.getItemQty());
					productBom.setActiveFlag(CommonConstants.Y);
					productBom.setCreateBy(user);
					productBom.setCreateOn(new Timestamp(System.currentTimeMillis()));					  
					
					childListReal.add(productBom);
				}
			}

			
			for(int i=0; i<childListReal.size(); i++)
			{
				productBomDatabase = (ProductBom) childListReal.get(i);

				for(int x=0; x<productBomVoList.size(); x++)
				{
					productBomVO = (ProductBomVO) productBomVoList.get(x);
					exist = false;
					
					if (Integer.parseInt(productBomDatabase.getItem().getItemId()+"") == Integer.parseInt(productBomVO.getItemId()+"")){
						   exist = true;
							
						   String content = productBomVO.getItemName()+ ": " + productBomVO.getItemQty()+" "+productBomVO.getUmName();
							  if (i == 0) {
									detailResume = detailResume + content;
							  } else {
							 		detailResume = detailResume + ", " + content;
							  }
							
						   break;
				    }
				 }

				if (!exist)
				{	/* delete */
					i--;
					childListReal.remove(productBomDatabase);
				}
			}
			
		}		
		
		List<ItemDiscount> ItemDiscountDbList = productHeader.getListItemDiscount();
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
					itemDiscountDb.setProductId(productHeader);
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
					itemDiscountDb.setProductId(productHeader);
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

				for (int x = 0; x < productVO.getListItemDiscount().size(); x++) {
					itemDiscountVO = (ItemDiscountVO) productVO.getListItemDiscount().get(x);
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
		
		
		
		
		productHeader.setItemComposition(detailResume);
		productHeader.setUpdateBy(user);
		productHeader.setUpdateOn(new Timestamp(System.currentTimeMillis()));
		productDAO.update(productHeader);
		productDAO.flush();
		
	}

	@Override
	public void delete(Long productId) {
		
		Product product = new Product();
		product = findById(productId);
		
		for(ProductBom bom :product.getProductBomList()){
			  ProductBom productBom =  productBomDAO.findById(bom.getBomId());			   
			  productBomDAO.delete(productBom);
		}		
		
		productDAO.delete(product);
		productDAO.flush();
	}

	@Override
	public Product findById(Long productId) {
		
		return productDAO.findById(productId);
	}

	@Override
	public List<ProductVO> searchAllProductByCategoryList(Long categoryId) {
		return productDAO.searchAllProductByCategoryList(categoryId);
	}

	@Override
	public List<ProductVO> searchAllProductByCompanyId(Long companyId) {
		return productDAO.searchAllProductByCompanyId(companyId);
	}
	
	@Override
	public ProductVO findByProductCodeAndCompanyId(String productCode, Long companyId) {
		return productDAO.findByProductCodeAndCompanyId(productCode, companyId);
	}
	
	@Override
	public ProductVO findByProductCodeOrBarcodeAndCompanyId(String productCode, String barcode, Long companyId) {
		return productDAO.findByProductCodeOrBarcodeAndCompanyId(productCode, barcode, companyId);
	}

	@Override
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId) {
		// TODO Auto-generated method stub
		return null;
	}

}
