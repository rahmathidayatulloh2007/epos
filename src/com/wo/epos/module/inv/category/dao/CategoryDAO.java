package com.wo.epos.module.inv.category.dao;

import java.util.List;

import com.wo.epos.common.dao.GenericDAO;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.vo.CategoryVO;

public interface CategoryDAO extends GenericDAO<Category, Long>{
	/*void deleteCategory(Long menuId);*/

	List<Category> getListCategoryByParentId(CategoryVO categoryVO, boolean getWithDetail,
			boolean orderByCode);

	Category getCategory(CategoryVO categoryVo);
	public List<CategoryVO> searchCategoryList();
	public List<Category> searchCategoryCompany(Long companyId);
	public Category findByCode(String categoryCode);
	public List<CategoryVO> searchAllCategoryParent(Long parentId);
	public List<Category> searchCategoryCompanyByParentIsNotNull(Long companyId);

	/*void insertCategory(CategoryVO categoryVO, boolean isInsert, String user) throws SQLException;*/
}
