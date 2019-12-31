package com.wo.epos.module.inv.item.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.model.ItemBom;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.model.ProductBom;
import com.wo.epos.module.inv.item.vo.ItemBomVO;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;

@ManagedBean(name = "itemDAO")
@ViewScoped
public class ItemDAOImpl extends GenericDAOHibernate<Item, Long> 
implements ItemDAO, Serializable{

	private static final long serialVersionUID = -2613073755499205529L;

	@SuppressWarnings("rawtypes")
	@Override
	public List<ItemVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		Criteria criteria = getSession().createCriteria(Item.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		
		List<ItemVO> voList = new ArrayList<ItemVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Item item = (Item)criteria.list().get(i);
			  ItemVO vo = new ItemVO();
			  
			  vo.setItemId(item.getItemId());
			  vo.setItemCode(item.getItemCode());
			  vo.setItemName(item.getItemName());
			  vo.setItemDesc(item.getItemDesc());
			  vo.setCompanyId(item.getCompany().getCompanyId());
			  vo.setCompanyName(item.getCompany().getCompanyName());		
			  vo.setCategoryId(item.getCategory().getCategoryId());
			  vo.setCategoryName(item.getCategory().getCategoryName());
			  vo.setUmId(item.getUm().getUmId());
			  vo.setUmName(item.getUm().getUmName());
			  vo.setUm2Id(item.getUm2()!=null?item.getUm2().getUmId():null);
			  vo.setUm2Name(item.getUm2()!=null?item.getUm2().getUmName():null);
			  vo.setUnitPerUm2(item.getUnitPerUm2());
			  vo.setUnitCost(item.getUnitCost());
			  vo.setUnitPrice(item.getUnitPrice());

			  if(item.getImageFile() !=null){
			     vo.setImageFile(item.getImageFile());
			  }
			  if(item.getImageFilename() !=null){
				 vo.setImageFilename(item.getImageFilename());
			  }
			  vo.setActiveFlag(item.getActiveFlag());
			  vo.setBarcode(item.getBarcode());
			  vo.setCompositeFlag(item.getCompositeFlag());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Item.class);
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
		    crit.createAlias("company", "company");
		    crit.createAlias("category", "category");
		    crit.createAlias("um", "um");
		    
			if (searchCriteria != null) {
				for (SearchObject searchVal : searchCriteria) {
					if (searchVal.getSearchColumn().compareTo("searchAll") == 0) {
						 String sSplit = searchVal.getSearchValueAsString().trim();
							crit.add(Restrictions.or(
									Restrictions.ilike("company.companyName", sSplit, MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemCode", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemName", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("itemDesc", sSplit,MatchMode.ANYWHERE),
									Restrictions.or(Restrictions.ilike("category.categoryName", sSplit,MatchMode.ANYWHERE),
									Restrictions.ilike("um.umName", sSplit,MatchMode.ANYWHERE)))))));
					}
					
					if(searchVal.getSearchColumn().compareTo("companyId")==0){
						crit.add(Restrictions.eq("company.companyId", searchVal.getSearchValue()));
					}
					if(searchVal.getSearchColumn().compareTo("itemCode")==0){
						crit.add(Restrictions.ilike("itemCode", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("itemName")==0){
						crit.add(Restrictions.ilike("itemName", searchVal.getSearchValueAsString().trim(), MatchMode.ANYWHERE));
					}
					if(searchVal.getSearchColumn().compareTo("category")==0){
						crit.add(Restrictions.eq("category.categoryId", searchVal.getSearchValue()));
					}						
					if(searchVal.getSearchColumn().compareTo("um")==0){
						crit.add(Restrictions.eq("um.umId", searchVal.getSearchValue()));
					}
				
				}
			}
	}

	@Override
	public List<ItemVO> searchItemList() {
		
		Criteria criteria = getSession().createCriteria(Item.class);
		
		List<ItemVO> voList = new ArrayList<ItemVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Item item = (Item)criteria.list().get(i);
			  ItemVO vo = new ItemVO();
			  
			  vo.setItemId(item.getItemId());
			  vo.setItemCode(item.getItemCode());
			  vo.setItemName(item.getItemName());
			  vo.setItemDesc(item.getItemDesc());
			  vo.setCompanyId(item.getCompany().getCompanyId());
			  vo.setCompanyName(item.getCompany().getCompanyName());		
			  vo.setCategoryId(item.getCategory().getCategoryId());
			  vo.setCategoryName(item.getCategory().getCategoryName());
			  vo.setUmId(item.getUm().getUmId());
			  vo.setUmName(item.getUm().getUmName());
			  vo.setUm2Id(item.getUm2().getUmId());
			  vo.setUm2Name(item.getUm2().getUmName());
			  vo.setUnitPerUm2(item.getUnitPerUm2());
			  vo.setUnitCost(item.getUnitCost());
			  vo.setUnitPrice(item.getUnitPrice()); 
			  
			  vo.setActiveFlag(item.getActiveFlag());
			  vo.setBarcode(item.getBarcode());
			  
			  voList.add(vo);
		}
		
		return voList;
	}

	@Override
	public List<ItemVO> searchItemList(String compositeFlag,
			boolean filterCompany, Long companyId) {
		Criteria criteria = getSession().createCriteria(Item.class);
		criteria.createAlias("company", "company");

		criteria.add(Restrictions.eq("compositeFlag", compositeFlag));
		criteria.add(Restrictions.eq("activeFlag", CommonConstants.Y));

		if (filterCompany) {
			if (companyId != null) {
				criteria.add(Restrictions.eq("company.companyId", companyId));
			} else {
				criteria.add(Restrictions.isNull("company.companyId"));
			}
		}

		List<ItemVO> voList = new ArrayList<ItemVO>();

		for (int i = 0; i < criteria.list().size(); i++) {
			Item item = (Item) criteria.list().get(i);
			ItemVO vo = this.convertToVO(item);

			voList.add(vo);
		}

		return voList;
	}

	private ItemVO convertToVO(Item obj) {
		ItemVO vo = new ItemVO();
		vo.setItemId(obj.getItemId());
		vo.setItemCode(obj.getItemCode());
		vo.setItemName(obj.getItemName());
		vo.setItemDesc(obj.getItemDesc());
		vo.setCompanyId(obj.getCompany().getCompanyId());
		vo.setCompanyName(obj.getCompany().getCompanyName());
		vo.setCategoryId(obj.getCategory().getCategoryId());
		vo.setCategoryName(obj.getCategory().getCategoryName());
		vo.setUmId(obj.getUm().getUmId());
		vo.setUmName(obj.getUm().getUmName());
		vo.setUm2Id(obj.getUm().getUmId());
		vo.setUm2Name(obj.getUm().getUmName());
		vo.setUnitPerUm2(obj.getUnitPerUm2());
		vo.setUnitCost(obj.getUnitCost());
		vo.setUnitPrice(obj.getUnitPrice());
/*		vo.setItemDiscountId(obj.getItemDiscountId().getItemDiscountId());
		vo.setCustomerType(obj.getItemDiscountId().getCustomerType().getParameterDtlName());
		*/  
		vo.setActiveFlag(obj.getActiveFlag());
		vo.setBarcode(obj.getBarcode());

		return vo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SupplierItem> searchSupplierItemList(Long itemId) {
		
		Criteria criteria = getSession().createCriteria(SupplierItem.class);
		criteria.createAlias("item", "item",
				CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("item.itemId",itemId));
		
		criteria.addOrder(Order.asc("item.itemId"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemDiscount> searchItemDiscountList(Long itemDiscountId) {
		Criteria criteria = getSession().createCriteria(ItemDiscount.class);
		criteria.createAlias("item", "item",
				CriteriaSpecification.INNER_JOIN);
		
		criteria.add(Restrictions.eq("item.itemId",itemDiscountId));
		
		criteria.addOrder(Order.asc("item.itemId"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ItemBomVO> searchItemBomList(Long itemId) {

		Criteria criteria = getSession().createCriteria(ItemBom.class);
		criteria.createAlias("item", "item", CriteriaSpecification.INNER_JOIN);

		criteria.add(Restrictions.eq("item.itemId", itemId));

		criteria.addOrder(Order.asc("item.itemId"));

		List<ItemBom> itemBomList = criteria.list();
		List<ItemBomVO> itemBomVoList = new ArrayList<ItemBomVO>();
		for (ItemBom obj : itemBomList) {
			ItemBomVO vo = new ItemBomVO();

			vo.setBomId(obj.getBomId());
			if (obj.getItem() != null) {
				vo.setItemCompositionId(obj.getItemComposition().getItemId());
				vo.setItemCompositionCode(obj.getItemComposition().getItemCode());
				vo.setItemCompositionName(obj.getItemComposition().getItemName());
			}
			vo.setItemQty(obj.getItemQty());
			
			itemBomVoList.add(vo);
		}

		return itemBomVoList;
	}
	
	@Override
	public SupplierItem findByDetailId(Long id) {
		Criteria criteria = getSession().createCriteria(SupplierItem.class);
		
		criteria.add(Restrictions.eq("supplierItemId",id));
		
		return (SupplierItem)criteria.uniqueResult();
	}
	
	public Product findByProductId(Long id) {
		Criteria criteria = getSession().createCriteria(Product.class);
		
		criteria.add(Restrictions.eq("productId",id));
		
		return (Product)criteria.uniqueResult();
	}	
	
	public ProductBom findByProductBomId(Long id) {
		Criteria criteria = getSession().createCriteria(ProductBom.class);
		
		criteria.add(Restrictions.eq("bomId",id));
		
		return (ProductBom)criteria.uniqueResult();
	}

	@Override
	public List<ItemVO> searchItemDiloagPoList(String search) {
		
		Criteria criteria = getSession().createCriteria(Item.class);
		criteria.createAlias("um", "um");
		
		if(search !=null && !search.equals("")){		
			criteria.add(Restrictions.or(Restrictions.ilike("itemCode", search, MatchMode.ANYWHERE),
										 Restrictions.or(Restrictions.ilike("itemName", search, MatchMode.ANYWHERE),
										 Restrictions.ilike("um.umName", search, MatchMode.ANYWHERE))));
		}
			
		criteria.addOrder(Order.asc("itemCode"));
		
		List<ItemVO> voList = new ArrayList<ItemVO>();
		for(int i=0; i<criteria.list().size(); i++){
			  Item item = (Item)criteria.list().get(i);
			  ItemVO vo = new ItemVO();
			  
			  vo.setItemId(item.getItemId());
			  vo.setItemCode(item.getItemCode());
			  vo.setItemName(item.getItemName());
			  vo.setCompanyId(item.getCompany().getCompanyId());
			  vo.setCompanyName(item.getCompany().getCompanyName());		
			  vo.setCategoryId(item.getCategory().getCategoryId());
			  vo.setCategoryName(item.getCategory().getCategoryName());
			  vo.setUmId(item.getUm().getUmId());
			  vo.setUmName(item.getUm().getUmName());
			  vo.setUm2Id(item.getUm2().getUmId());
			  vo.setUm2Name(item.getUm2().getUmName());
			  vo.setUnitPerUm2(item.getUnitPerUm2());
			  vo.setUnitCost(item.getUnitCost());
			  vo.setUnitPrice(item.getUnitPrice());
			  vo.setActiveFlag(item.getActiveFlag());
			  vo.setBarcode(item.getBarcode());
			  
			  voList.add(vo);
		}
		
		return voList;
	}
	
	public Long getNextId()
	{
		Criteria criteria = getSession().createCriteria(SupplierItem.class).
							setProjection(Projections.max("supplierItemId"));
		
		Long maxSupplierItemId = (Long)criteria.uniqueResult();
		
		if (maxSupplierItemId == null){ 
			maxSupplierItemId = 1L; 
		} else { 
			maxSupplierItemId = maxSupplierItemId + 1; 
		}
		
		return maxSupplierItemId;
		
	}

	@Override
	public Item findByItemCode(String itemCode) {
		Criteria criteria = getSession().createCriteria(Item.class);
		criteria.add(Restrictions.eq("itemCode", itemCode.trim()));
		
		criteria.addOrder(Order.asc("itemCode"));
		
		return (Item) criteria.uniqueResult();
	}
	
	@Override
	public List<ItemVO> searchItemByCompanyList(Long companyId) {
		
		Criteria criteria = getSession().createCriteria(Item.class);
		criteria.createAlias("company", "company", CriteriaSpecification.INNER_JOIN);
		
		if(companyId !=null){
			criteria.add(Restrictions.eq("company.companyId",companyId));
		}else{
			criteria.add(Restrictions.isNull("company.companyId"));
		}

		criteria.addOrder(Order.asc("itemCode"));
		
		List<ItemVO> itemVoList = new ArrayList<ItemVO>();
		if(criteria !=null){
			for(int i=0; i<criteria.list().size(); i++){
				Item item = (Item)criteria.list().get(i);
				ItemVO itemVo = new ItemVO();
				itemVo.setItemId(item.getItemId());
				itemVo.setItemCode(item.getItemCode());
				itemVo.setItemName(item.getItemName());
				itemVo.setItemDesc(item.getItemDesc());
				itemVo.setBarcode(item.getBarcode());
				
				itemVoList.add(itemVo);
			
			}
		}
		
		
		return itemVoList;
	}



	@Override
	public ItemDiscount findByDetailId2(Long id) {
		Criteria criteria = getSession().createCriteria(ItemDiscount.class);
		
		criteria.add(Restrictions.eq("ItemDiscountId",id));
		
		return (ItemDiscount)criteria.uniqueResult();
	}

}
