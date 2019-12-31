package com.wo.epos.module.inv.category.service;

import java.sql.SQLException;
import java.util.List;

import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.vo.CategoryVO;

public interface CategoryService {

	public void deleteCategory(Long categoryId) throws SQLException;

	public List<Category> getListCategoryByParentId(CategoryVO categoryVO, boolean getWithDetail, boolean orderByCode)
			throws SQLException;
	
	public Category getCategory(CategoryVO categoryVo) throws SQLException;

	public void insertCategory(CategoryVO categoryVO, boolean isInsert, String user) throws SQLException ;
	public Category findById(Long categoryId);
	public List<Category> searchCategoryCompany(Long companyId);
	public Category findByCode(String categoryCode);
	
	public List<CategoryVO> searchAllCategoryParent(Long parentId);
	public List<Category> searchCategoryCompanyByParentIsNotNull(Long companyId);
	
	
}
