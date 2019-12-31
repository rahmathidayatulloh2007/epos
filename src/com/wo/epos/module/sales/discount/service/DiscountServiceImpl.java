package com.wo.epos.module.sales.discount.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.inv.category.dao.CategoryDAO;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.sales.discount.dao.DiscountDAO;
import com.wo.epos.module.sales.discount.model.Discount;
import com.wo.epos.module.sales.discount.model.DiscountDtl;
import com.wo.epos.module.sales.discount.vo.DiscountDtlVO;
import com.wo.epos.module.sales.discount.vo.DiscountVO;
import com.wo.epos.module.uam.company.dao.CompanyDAO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.dao.ParameterDAO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

@ManagedBean(name="discountService")
@ViewScoped
public class DiscountServiceImpl implements DiscountService, Serializable {
	
	private static final long serialVersionUID = 3870125958087235266L;

	@ManagedProperty(value="#{discountDAO}")
	private DiscountDAO discountDAO;
	
	@ManagedProperty(value="#{companyDAO}")
	private CompanyDAO companyDAO;
	
	@ManagedProperty(value="#{parameterDAO}")
	private ParameterDAO parameterDAO;
	
	@ManagedProperty(value="#{categoryDAO}")
	private CategoryDAO categoryDAO;

	public DiscountDAO getDiscountDAO() {
		return discountDAO;
	}

	public void setDiscountDAO(DiscountDAO discountDAO) {
		this.discountDAO = discountDAO;
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public ParameterDAO getParameterDAO() {
		return parameterDAO;
	}

	public void setParameterDAO(ParameterDAO parameterDAO) {
		this.parameterDAO = parameterDAO;
	}
	

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DiscountVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		return discountDAO.searchData(searchCriteria, first, pageSize, sortField, sortOrder);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		return discountDAO.searchCountData(searchCriteria);
	}

	@Override
	public void save(DiscountVO discountVO, List<DiscountDtlVO> discountDtlList, String user) {
		
		Company company = new Company();
		ParameterDtl discountProvider = new ParameterDtl();
		ParameterDtl discountCategory = new ParameterDtl();
		Discount discount = new Discount();
		Category category = new Category();
		
		company = companyDAO.findById(discountVO.getCompanyId());
		discountProvider = parameterDAO.findByDetailId(discountVO.getDiscountProviderCode());
		discountCategory = parameterDAO.findByDetailId(discountVO.getDiscountCategoryCode());
		
		discount.setCompany(company);
		discount.setDiscountCode(discountVO.getDiscountCode());
		discount.setDiscountName(discountVO.getDiscountName());
		discount.setDiscountValue(discountVO.getDiscountValue());
		discount.setDiscountProvider(discountProvider);
		discount.setDiscountCategory(discountCategory);
		discount.setCategoryResume(discountVO.getCategoryResume());
		
		List<DiscountDtl> dtlList = new ArrayList<DiscountDtl>();
		if(discountDtlList.size()> 0) {
			for(int i=0 ; i < discountDtlList.size() ; i++) {
				category = categoryDAO.findById(discountDtlList.get(i).getCategoryId());
				DiscountDtl discountDtl = new DiscountDtl();
				DiscountDtlVO discountDtlVO = discountDtlList.get(i);
				
				discountDtl.setDiscount(discount);
				discountDtl.setCategory(category);
				discountDtl.setDiscountDtlId(discountDtlVO.getDiscountDtlId());
				discountDtl.setActiveFlag("Y");
				discountDtl.setCreateBy(user);
				discountDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));					
				
				dtlList.add(discountDtl);
			}
		}
		
		discount.setListDiscountDtl(dtlList);
		discount.setActiveFlag(discountVO.getActiveFlag());
		discount.setCreateBy(user);
		discount.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		discountDAO.save(discount);
		discountDAO.flush();
		
	}

	@Override
	public void update(DiscountVO discountVO, List<DiscountDtlVO> discountDtlList, String user) {
		Company company = new Company();
		ParameterDtl discountProvider = new ParameterDtl();
		ParameterDtl discountCategory = new ParameterDtl();
		Discount discount = new Discount();
		
		company = companyDAO.findById(discountVO.getCompanyId());
		discountProvider = parameterDAO.findByDetailId(discountVO.getDiscountProviderCode());
		discountCategory = parameterDAO.findByDetailId(discountVO.getDiscountCategoryCode());
		
		discount = discountDAO.findById(discountVO.getDiscountId());
		discount.setCompany(company);
		discount.setDiscountCode(discountVO.getDiscountCode());
		discount.setDiscountName(discountVO.getDiscountName());
		discount.setDiscountValue(discountVO.getDiscountValue());
		discount.setDiscountProvider(discountProvider);
		discount.setDiscountCategory(discountCategory);
		discount.setCategoryResume(discountVO.getCategoryResume());
		discount.setActiveFlag(discountVO.getActiveFlag());
		discount.setCreateBy(user);
		discount.setCreateOn(new Timestamp(System.currentTimeMillis()));
		
		Category category = new Category();
		
		List<DiscountDtl> childListReal = discount.getListDiscountDtl();
		
		if(discountDtlList!=null) {
			DiscountDtl discountDtl = null;
			DiscountDtl discountDtlDatabase = null;
			DiscountDtlVO discountDtlVO = null;
			boolean exist = false;
			
			for(int x=0 ; x < discountDtlList.size() ; x++) {
				
				discountDtlVO = (DiscountDtlVO) discountDtlList.get(x);
				exist = false;
				for(int i=0; i<childListReal.size(); i++)
				{
					discountDtlDatabase = (DiscountDtl) childListReal.get(i);
					
					
					if (Integer.parseInt(discountDtlDatabase.getCategory().getCategoryId()+"") == Integer.parseInt(discountDtlVO.getCategoryId()+"")) {
						exist = true;
						break;
					}
				}
				
				if (exist)
				{	
					discountDtlDatabase.setDiscount(discount);
					category = categoryDAO.findById(discountDtlVO.getCategoryId());
					discountDtlDatabase.setCategory(category);
					discountDtlDatabase.setUpdateBy(user);
					discountDtlDatabase.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					
				}
				
				else
				{	
					discountDtl = new DiscountDtl();
					discountDtl.setDiscount(discount);
					category = categoryDAO.findById(discountDtlVO.getCategoryId());
					discountDtl.setCategory(category);
					discountDtl.setActiveFlag("Y");
					discountDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
					discountDtl.setCreateBy(user);
					
					childListReal.add(discountDtl);
				}
				
				
			}
			
			for(int i=0; i<childListReal.size(); i++)
			{
				discountDtlDatabase = (DiscountDtl) childListReal.get(i);

				for(int x=0; x<discountDtlList.size(); x++)
				{
					discountDtlVO =(DiscountDtlVO) discountDtlList.get(x);
					exist = false;
					
					if(discountDtlDatabase.getCategory().getCategoryId() !=null) {
						if (Integer.parseInt(discountDtlDatabase.getCategory().getCategoryId()+"") == Integer.parseInt(discountDtlVO.getCategoryId()+"")){
							exist = true;
							
							break;
						}
					}
				}

				if (!exist)
				{	
					i--;
					childListReal.remove(discountDtlDatabase);
				}
			}
			
		}
		
		discountDAO.update(discount);
		discountDAO.flush();
		
	}

	@Override
	public void delete(Long discountId) {
		
		Discount discount = new Discount();	        
	    
		discount = discountDAO.findById(discountId);
		
		discountDAO.delete(discount);
		discountDAO.flush();
	}

	@Override
	public Discount findById(Long discountId) {
		
		return discountDAO.findById(discountId);
	}

	@Override
	public List<CompanyVO> searchCompanyList() {
		
		return companyDAO.searchCompanyList();
	}

	@Override
	public List<ParameterDtl> parameterDtlList(String parameterCode) {
		
		return parameterDAO.parameterDtlList(parameterCode);
	}

	@Override
	public List<CategoryVO> searchCategoryList() {
		
		return categoryDAO.searchCategoryList();
	}

	@Override
	public List<DiscountDtl> searchDiscountDtlList(Long discountId) {
		
		return discountDAO.searchDiscountDtlList(discountId);
	}

	@Override
	public List<DiscountVO> searchDiscountByCompanyList(Long companyId) {
		return discountDAO.searchDiscountByCompanyList(companyId);
	}

}
