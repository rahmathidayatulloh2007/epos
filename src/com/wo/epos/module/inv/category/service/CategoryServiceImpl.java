package com.wo.epos.module.inv.category.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.module.inv.category.dao.CategoryDAO;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.vo.CategoryVO;

@ManagedBean(name = "categoryService")
@ViewScoped
public class CategoryServiceImpl implements CategoryService, Serializable{

	private static final long serialVersionUID = 7730331326935516188L;
	
	@ManagedProperty(value="#{categoryDAO}")
	private CategoryDAO categoryDAO;
	
	public void deleteCategory(Long categoryId) throws SQLException {
		/*categoryDAO.deleteCategory(categoryId);*/
		Category deleteMenu = categoryDAO.findById(categoryId);
		categoryDAO.delete(deleteMenu);
		categoryDAO.flush();
	}

	public List<Category> getListCategoryByParentId(CategoryVO categoryVO, boolean getWithDetail, boolean orderByCode)
			throws SQLException {
		return categoryDAO.getListCategoryByParentId(categoryVO, getWithDetail, orderByCode);
	}
	
	public Category getCategory(CategoryVO categoryVo) throws SQLException {
		return categoryDAO.getCategory(categoryVo);
	}
	
	public void insertCategory(CategoryVO categoryVO, boolean isInsert, String user) throws SQLException {
		
//		categoryDAO.insertCategory(categoryVO, isInsert, user);
		
		Category parentCategory = null;
		if (categoryVO.getParentId() != null && categoryVO.getParentId() > 0) {
			parentCategory = categoryDAO.findById(categoryVO.getParentId());
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
			
			newCategory.setCreateBy(user);
			newCategory.setCreateOn(new Timestamp(System.currentTimeMillis()));
			
			categoryDAO.save(newCategory);
			categoryDAO.flush();
		} 
		else {
			Category editedCategory = categoryDAO.getById(categoryVO.getCategoryId());
			
			editedCategory.setCategoryCode(categoryVO.getCategoryCode());
			editedCategory.setCategoryName(categoryVO.getCategoryName());
			editedCategory.setCategoryDesc(categoryVO.getCategoryDesc());
			editedCategory.setCategoryLevel(categoryVO.getCategoryLevel());
			if(parentCategory !=null)
			editedCategory.setParent(parentCategory);

			editedCategory.setUpdateBy(user);
			editedCategory.setUpdateOn(new Timestamp(System.currentTimeMillis()));
			
			categoryDAO.update(editedCategory);
			categoryDAO.flush();
		}
		
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public Category findById(Long categoryId) {
		
		return categoryDAO.findById(categoryId);
	}

	@Override
	public List<Category> searchCategoryCompany(Long companyId) {
		return categoryDAO.searchCategoryCompany(companyId);
	}
	
	@Override
	public Category findByCode(String categoryCode) {		
		return categoryDAO.findByCode(categoryCode);
	}

	@Override
	public List<CategoryVO> searchAllCategoryParent(Long parentId) {
		return categoryDAO.searchAllCategoryParent(parentId);
	}

	@Override
	public List<Category> searchCategoryCompanyByParentIsNotNull(Long companyId) {
		return categoryDAO.searchCategoryCompanyByParentIsNotNull(companyId);
	}

}
