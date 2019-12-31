package com.wo.epos.module.inv.category.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.vo.CategoryVO;

@ManagedBean(name = "categoryDAO")
@ViewScoped
public class CategoryDAOImpl extends GenericDAOHibernate<Category, Long> implements CategoryDAO, java.io.Serializable {
	
	private static final long serialVersionUID = 5365714797160282048L;
	static Logger logger = Logger.getLogger(CategoryDAOImpl.class);

	/*@Override
	public void deleteCategory(Long categoryId) {
		Category deleteMenu = findById(categoryId);
		delete(deleteMenu);
		flush();
	}*/
	
	@Override
	public List<Category> getListCategoryByParentId(CategoryVO categoryVO, boolean getWithDetail, boolean orderByCode) {
		if (categoryVO.getParentId() == null || categoryVO.getParentId().longValue() == 0) {
			return searchParentIsNull(categoryVO.getCompanyId());
		} else {
			return searchByParentId(categoryVO.getParentId(), categoryVO.getCompany().getCompanyId());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private List<Category> searchParentIsNull(Long companyId) {
		Criteria criteria = getSession().createCriteria(Category.class);
		
		criteria.createAlias("company", "company");
		criteria.add(Restrictions.isNull("parent"));
		if(companyId!=null)
			criteria.add(Restrictions.eq("company.companyId", companyId));
		criteria.addOrder(Order.asc("categoryLevel"));
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	private List<Category> searchByParentId(Long parentId, Long companyId) {
		Criteria criteria = getSession().createCriteria(Category.class);
		
		criteria.createAlias("parent", "parent");
		if(companyId!=null)
			criteria.add(Restrictions.eq("company.companyId", companyId));
		criteria.add(Restrictions.eq("parent.categoryId", parentId));
		criteria.addOrder(Order.asc("categoryLevel"));
		
		return criteria.list();
	}
	
	@Override
	public Category getCategory(CategoryVO categoryVo) {
		Category res = getById(categoryVo.getCategoryId());
		if (res == null) {
			res = new Category(); 
		}
		return res;
	}

	@Override
	public List<CategoryVO> searchCategoryList() {
		Criteria criteria = getSession().createCriteria(Category.class);
		
		List<CategoryVO> voList = new ArrayList<CategoryVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Category category = (Category)criteria.list().get(i);
			CategoryVO vo = new CategoryVO();
			
			vo.setCategoryId(category.getCategoryId());
			vo.setCompanyId(category.getCompany().getCompanyId());
			vo.setCategoryCode(category.getCategoryCode());
			vo.setCategoryName(category.getCategoryName());
			vo.setCategoryDesc(category.getCategoryDesc());
			vo.setCategoryLevel(category.getCategoryLevel());
			vo.setActiveFlag(category.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> searchCategoryCompany(Long companyId) {
		Criteria criteria = getSession().createCriteria(Category.class);		
		criteria.createAlias("company", "company", CriteriaSpecification.INNER_JOIN);
		if(companyId!=null)
			criteria.add(Restrictions.eq("company.companyId", companyId));
		
		criteria.addOrder(Order.asc("categoryLevel"));
		
		return criteria.list();
	}

	@Override
	public Category findByCode(String categoryCode) {
		Criteria criteria = getSession().createCriteria(Category.class);	
		
		criteria.add(Restrictions.eq("categoryCode", categoryCode.trim()));		
		criteria.addOrder(Order.asc("categoryCode"));
		
		return (Category) criteria.uniqueResult();
	}

	@Override
	public List<CategoryVO> searchAllCategoryParent(Long parentId) {
		Criteria criteria = getSession().createCriteria(Category.class);
		criteria.createAlias("parent", "parent", CriteriaSpecification.INNER_JOIN);
		
		if(parentId !=null){
		   criteria.add(Restrictions.eq("parent.categoryId", parentId));
		}
		
		List<CategoryVO> voList = new ArrayList<CategoryVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Category category = (Category)criteria.list().get(i);
			CategoryVO vo = new CategoryVO();
			
			vo.setCategoryId(category.getCategoryId());
			vo.setCompanyId(category.getCompany().getCompanyId());
			vo.setCategoryCode(category.getCategoryCode());
			vo.setCategoryName(category.getCategoryName());
			vo.setCategoryDesc(category.getCategoryDesc());
			vo.setCategoryLevel(category.getCategoryLevel());
			vo.setActiveFlag(category.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> searchCategoryCompanyByParentIsNotNull(Long companyId) {
		Criteria criteria = getSession().createCriteria(Category.class);		
		criteria.createAlias("company", "company", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("parent", "parent", CriteriaSpecification.INNER_JOIN);
		
		if(companyId!=null){
			criteria.add(Restrictions.eq("company.companyId", companyId));
		}else{
			criteria.add(Restrictions.isNull("company.companyId"));
		}
		
		criteria.add(Restrictions.isNotNull("parent.categoryId"));
		criteria.addOrder(Order.asc("categoryLevel"));
		
		return criteria.list();
	}
	
	/*@Override
	public void insertCategory(CategoryVO categoryVO, boolean isInsert, String user) throws SQLException {
		Category parentCategory = null;
		if (categoryVO.getParentId() != null && categoryVO.getParentId() > 0) {
			parentCategory = findById(categoryVO.getParentId());
		}
		
		if (isInsert) {
			Category newCategory = new Category();
			newCategory.setCompany(categoryVO.getCompany());
			newCategory.setCategoryCode(categoryVO.getCategoryCode());
			newCategory.setCategoryName(categoryVO.getCategoryName());
			newCategory.setCategoryDesc(categoryVO.getCategoryDesc());
			newCategory.setCategoryLevel(categoryVO.getCategoryLevel());
			if(parentCategory!=null)
				newCategory.setParent(parentCategory);
			updateCreationInfo(newCategory, user);
			save(newCategory);
			flush();			
		} 
		else {
			Category editedCategory = getById(categoryVO.getCategoryId());
			
			editedCategory.setCategoryCode(categoryVO.getCategoryCode());
			editedCategory.setCategoryName(categoryVO.getCategoryName());
			editedCategory.setCategoryDesc(categoryVO.getCategoryDesc());
			editedCategory.setCategoryLevel(categoryVO.getCategoryLevel());
			editedCategory.setParent(parentCategory);

			updateLastUpdateInfo(editedCategory, user);
			update(editedCategory);
			flush();
		}
		
	}*/
}
