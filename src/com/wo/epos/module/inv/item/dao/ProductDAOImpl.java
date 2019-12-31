package com.wo.epos.module.inv.item.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.category.dao.CategoryDAO;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;

@ManagedBean(name = "productDAO")
@ViewScoped
public class ProductDAOImpl extends GenericDAOHibernate<Product, Long> 
implements ProductDAO, Serializable {

	private static final long serialVersionUID = 2395274711222464308L;

	@ManagedProperty(value="#{categoryDAO}")
	private CategoryDAO categoryDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProductVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Product.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<ProductVO> voList = new ArrayList<ProductVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Product product = (Product)criteria.list().get(i);
			  ProductVO vo = new ProductVO();
			  vo.setProductId(product.getProductId());
			  vo.setProductCode(product.getProductCode());
			  vo.setProductName(product.getProductName());
			  vo.setProductDesc(product.getProductDesc());
			  vo.setImageFile(product.getImageFile());
			  vo.setImageFilename(product.getImageFilename());
			  vo.setIngredientFlag(product.getIngredientFlag());
			  vo.setBarcode(product.getBarcode());
			  
			  if(product.getCompanyId() !=null){
				  vo.setCompanyId(product.getCompanyId());
				  Company com = companyDAO.findById(product.getCompanyId());
				  vo.setCompanyCode(com.getCompanyCode());
				  vo.setCompanyName(com.getCompanyName());
			  }
			  if(product.getCategoryId() !=null){
				  vo.setCategoryId(product.getCategoryId());
				  Category cate = categoryDAO.findById(vo.getCategoryId());
			      vo.setCategoryCode(cate.getCategoryCode());
			      vo.setCategoryName(cate.getCategoryName());
			  }
			  if(product.getUm() !=null){
				  vo.setUmId(product.getUm().getUmId());
				  vo.setUmName(product.getUm().getUmName());
			  }
			  if(product.getUm2() !=null){
				  vo.setUm2Id(product.getUm2().getUmId());
				  vo.setUm2Name(product.getUm2().getUmName());
			  }
			  
			  vo.setItemComposition(product.getItemComposition());
			  vo.setLaunchingDt(product.getLaunchingDt());
			  vo.setUnitCost(product.getUnitCost());
			  vo.setUnitPrice(product.getUnitPrice());
			  vo.setUnitPerUm2(product.getUnitPerUm2());
			  vo.setBufferStock(product.getBufferStock());
			  
			  vo.setActiveFlag(product.getActiveFlag());
			  			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Product.class);
		decorateCriteria(criteria, searchCriteria);
		 
		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}
	
	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit,
			List<? extends SearchObject> searchCriteria) {
		    crit.createAlias("um", "um");
		    crit.createAlias("company", "company");
		    crit.createAlias("category", "category");
		    		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if(searchVal.getSearchColumn().compareTo("companyLogin") == 0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));						
					}					
					
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
							 String sSplit = searchVal.getSearchValueAsString().trim();
								crit.add(Restrictions.or(
										Restrictions.ilike("productCode", sSplit, MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("productName", sSplit,MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("company.companyName", sSplit,MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("category.categoryName", sSplit,MatchMode.ANYWHERE),		
										Restrictions.or(Restrictions.ilike("um.umName", sSplit,MatchMode.ANYWHERE),	
										Restrictions.or(Restrictions.ilike("itemComposition", sSplit,MatchMode.ANYWHERE),			
										Restrictions.ilike("productDesc", sSplit,MatchMode.ANYWHERE))))))));
					}
					
					
					if(searchVal.getSearchColumn().compareTo("productCode")==0){
						crit.add(Restrictions.ilike("productCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}	
					
					if(searchVal.getSearchColumn().compareTo("productName")==0){
						crit.add(Restrictions.ilike("productName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}		
					
					if(searchVal.getSearchColumn().compareTo("productDesc")==0){
						crit.add(Restrictions.ilike("productDesc", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}	
					
					if(searchVal.getSearchColumn().compareTo("categoryId")==0){
						crit.add(Restrictions.eq("category.categoryId", searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("umId")==0){
						crit.add(Restrictions.eq("um.umId", searchVal.getSearchValue()));
					}
					
					if(searchVal.getSearchColumn().compareTo("itemComposition")==0){
						crit.add(Restrictions.ilike("itemComposition", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
										
				}
			}
			
			crit.add(Restrictions.eq("activeFlag", "Y"));
	}

	@Override
	public List<ProductVO> searchAllProductByCategoryList(Long categoryId) {
		Criteria criteria = getSession().createCriteria(Product.class);		
		criteria.add(Restrictions.eq("categoryId", categoryId));
		
		List<ProductVO> voList = new ArrayList<ProductVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Product product = (Product)criteria.list().get(i);
			  ProductVO vo = new ProductVO();
			  vo.setProductId(product.getProductId());
			  vo.setProductCode(product.getProductCode());
			  vo.setProductName(product.getProductName());
			  vo.setProductDesc(product.getProductDesc());
			  vo.setImageFile(product.getImageFile());
			  vo.setImageFilename(product.getImageFilename());
			  vo.setIngredientFlag(product.getIngredientFlag());
			  vo.setBarcode(product.getBarcode());
			  
			  if(product.getCompanyId() !=null){
				  vo.setCompanyId(product.getCompanyId());
				  Company com = companyDAO.findById(product.getCompanyId());
				  vo.setCompanyCode(com.getCompanyCode());
				  vo.setCompanyName(com.getCompanyName());
			  }
			  if(product.getCategoryId() !=null){
				  vo.setCategoryId(product.getCategoryId());
				  Category cate = categoryDAO.findById(vo.getCategoryId());
			      vo.setCategoryCode(cate.getCategoryCode());
			      vo.setCategoryName(cate.getCategoryName());
			  }
			  if(product.getUm() !=null){
				  vo.setUmId(product.getUm().getUmId());
				  vo.setUmName(product.getUm().getUmName());
			  }
			  
			  if(product.getUm2() !=null){
				  vo.setUm2Id(product.getUm2().getUmId());
				  vo.setUm2Name(product.getUm2().getUmName());
			  }
			  
			  vo.setItemComposition(product.getItemComposition());
			  vo.setLaunchingDt(product.getLaunchingDt());
			  vo.setUnitPerUm2(product.getUnitPerUm2());
			  vo.setUnitCost(product.getUnitCost());
			  vo.setUnitPrice(product.getUnitPrice());
			  vo.setBufferStock(product.getBufferStock());
			  
			  vo.setActiveFlag(product.getActiveFlag());			  
 	          /*if(product.getImageFile() !=null){
			       vo.setImageDisplay(new DefaultStreamedContent(new ByteArrayInputStream(product.getImageFile())));
			  }*/
 	          
			  voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public List<ProductVO> searchAllProductByCompanyId(Long companyId) {
		Criteria criteria = getSession().createCriteria(Product.class);		
		criteria.add(Restrictions.eq("companyId", companyId));
		
		List<ProductVO> voList = new ArrayList<ProductVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Product product = (Product)criteria.list().get(i);
			  ProductVO vo = new ProductVO();
			  vo.setProductId(product.getProductId());
			  vo.setProductCode(product.getProductCode());
			  vo.setProductName(product.getProductName());
			  vo.setProductDesc(product.getProductDesc());
			  vo.setImageFile(product.getImageFile());
			  vo.setImageFilename(product.getImageFilename());
			  vo.setIngredientFlag(product.getIngredientFlag());
			  vo.setBarcode(product.getBarcode());
			  
			  if(product.getCompanyId() !=null){
				  vo.setCompanyId(product.getCompanyId());
				  Company com = companyDAO.findById(product.getCompanyId());
				  vo.setCompanyCode(com.getCompanyCode());
				  vo.setCompanyName(com.getCompanyName());
			  }
			  if(product.getCategoryId() !=null){
				  vo.setCategoryId(product.getCategoryId());
				  Category cate = categoryDAO.findById(vo.getCategoryId());
			      vo.setCategoryCode(cate.getCategoryCode());
			      vo.setCategoryName(cate.getCategoryName());
			  }
			  if(product.getUm() !=null){
				  vo.setUmId(product.getUm().getUmId());
				  vo.setUmName(product.getUm().getUmName());
			  }
			  
			  if(product.getUm2() !=null){
				  vo.setUm2Id(product.getUm2().getUmId());
				  vo.setUm2Name(product.getUm2().getUmName());
			  }
			  
			  vo.setItemComposition(product.getItemComposition());
			  vo.setLaunchingDt(product.getLaunchingDt());
			  vo.setUnitCost(product.getUnitCost());
			  vo.setUnitPrice(product.getUnitPrice());
			  vo.setUnitPerUm2(product.getUnitPerUm2());
			  vo.setBufferStock(product.getBufferStock());
			  
			  vo.setActiveFlag(product.getActiveFlag());	
 	          
			  voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public ProductVO findByProductCodeAndCompanyId(String productCode, Long companyId) {
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		criteria.add(Restrictions.eq("productCode", productCode.trim()));
		
		ProductVO productVo = new ProductVO();
		for(int i=0; i<criteria.list().size(); i++){
			  Product product = (Product)criteria.list().get(i);
			 
			  productVo.setProductId(product.getProductId());
			  productVo.setProductCode(product.getProductCode());
			  productVo.setProductName(product.getProductName());
			  productVo.setProductDesc(product.getProductDesc());
			  productVo.setImageFile(product.getImageFile());
			  productVo.setImageFilename(product.getImageFilename());
			  productVo.setIngredientFlag(product.getIngredientFlag());
			  productVo.setBarcode(product.getBarcode());
			  
			  if(product.getCompanyId() !=null){
				  productVo.setCompanyId(product.getCompanyId());
				  Company com = companyDAO.findById(product.getCompanyId());
				  productVo.setCompanyCode(com.getCompanyCode());
				  productVo.setCompanyName(com.getCompanyName());
			  }
			  if(product.getCategoryId() !=null){
				  productVo.setCategoryId(product.getCategoryId());
				  Category cate = categoryDAO.findById(productVo.getCategoryId());
				  productVo.setCategoryCode(cate.getCategoryCode());
				  productVo.setCategoryName(cate.getCategoryName());
			  }
			  if(product.getUm() !=null){
				  productVo.setUmId(product.getUm().getUmId());
				  productVo.setUmName(product.getUm().getUmName());
			  }
			  
			  if(product.getUm2() !=null){
				  productVo.setUm2Id(product.getUm2().getUmId());
				  productVo.setUm2Name(product.getUm2().getUmName());
			  }
			  
			  productVo.setItemComposition(product.getItemComposition());
			  productVo.setLaunchingDt(product.getLaunchingDt());
			  productVo.setUnitCost(product.getUnitCost());
			  productVo.setUnitPrice(product.getUnitPrice());
			  productVo.setUnitPerUm2(product.getUnitPerUm2());
			  productVo.setBufferStock(product.getBufferStock());
			  
			  productVo.setActiveFlag(product.getActiveFlag());	
		}
		
		return productVo;
	}

	@Override
	public ProductVO findByProductCodeOrBarcodeAndCompanyId(String productCode,
			String barcode, Long companyId) {
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.add(Restrictions.eq("companyId", companyId));
		if(productCode !=null && !productCode.equals("")){			
		     criteria.add(Restrictions.eq("productCode", productCode.trim()));
		}else if(barcode !=null && !barcode.equals("")) {
			 criteria.add(Restrictions.eq("barcode", barcode.trim()));
		}
		
		ProductVO productVo = new ProductVO();
		for(int i=0; i<criteria.list().size(); i++){
			  Product product = (Product)criteria.list().get(i);
			 
			  productVo.setProductId(product.getProductId());
			  productVo.setProductCode(product.getProductCode());
			  productVo.setProductName(product.getProductName());
			  productVo.setProductDesc(product.getProductDesc());
			  productVo.setImageFile(product.getImageFile());
			  productVo.setImageFilename(product.getImageFilename());
			  productVo.setIngredientFlag(product.getIngredientFlag());
			  productVo.setBarcode(product.getBarcode());
			  
			  if(product.getCompanyId() !=null){
				  productVo.setCompanyId(product.getCompanyId());
				  Company com = companyDAO.findById(product.getCompanyId());
				  productVo.setCompanyCode(com.getCompanyCode());
				  productVo.setCompanyName(com.getCompanyName());
			  }
			  if(product.getCategoryId() !=null){
				  productVo.setCategoryId(product.getCategoryId());
				  Category cate = categoryDAO.findById(productVo.getCategoryId());
				  productVo.setCategoryCode(cate.getCategoryCode());
				  productVo.setCategoryName(cate.getCategoryName());
			  }
			  if(product.getUm() !=null){
				  productVo.setUmId(product.getUm().getUmId());
				  productVo.setUmName(product.getUm().getUmName());
			  }
			  
			  if(product.getUm2() !=null){
				  productVo.setUm2Id(product.getUm2().getUmId());
				  productVo.setUm2Name(product.getUm2().getUmName());
			  }
			  
			  productVo.setItemComposition(product.getItemComposition());
			  productVo.setLaunchingDt(product.getLaunchingDt());
			  productVo.setUnitCost(product.getUnitCost());
			  productVo.setUnitPrice(product.getUnitPrice());
			  productVo.setUnitPerUm2(product.getUnitPerUm2());
			  productVo.setBufferStock(product.getBufferStock());
			  
			  productVo.setActiveFlag(product.getActiveFlag());	
		}
		
		return productVo;
	}

	@Override
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Product.class);
		criteria.createAlias("product", "product",
				CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("product.productId",itemDiscountId));
		
		criteria.addOrder(Order.asc("product.productId"));
		
		return criteria.list();
	}

	@Override
	public ItemDiscount findByDetailId2(Long id) {
		Criteria criteria = getSession().createCriteria(Product.class);
		
		criteria.add(Restrictions.eq("ItemDiscountId",id));
		
		return (ItemDiscount)criteria.uniqueResult();
	}

}
