package com.wo.epos.module.inv.category.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.NavigationConstants;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;

@ManagedBean(name = "categorySearchBean")
@ViewScoped
public class CategorySearchBean extends CommonBean implements Serializable{

private static final long serialVersionUID = -743646042554424313L;
	
	static Logger logger = Logger.getLogger(CategorySearchBean.class);
	
	// Add Service
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	// Add Object Class
	private CategoryVO categoryVO = new CategoryVO();
	
	// Add Object Search
	private Long companyId;
	private String companyName;
	
	// Add Object Form
	private TreeNode root;
	public String defaultTitle;
	private String categoryTitle;
	private Long category_parent_id;
	private String parent_category_code;
	private String parent_category_name;
	private Long update_category_parent_id;
	private Integer category_parent_level;
//	private Long prev_parent_id;
//	private Integer prev_parent_level;
//	private String prev_category_title;
	private boolean categoryUpdate;
	private Integer category_level;
	private Long category_id;
	private String category_code;
	private String category_name;
	private String category_description;
	private boolean isRowEmpty = true;
	private String dispatchAction;
	private boolean isRoot = true;
	private Long del_category_id;
	
	// Add Object List
	private List<Category> categoryList = new ArrayList<Category>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
    
//	private PagingTableModel<CategoryVO> pagingCategory;
	
	@PostConstruct
	public void postConstruct() {
    	super.init();
		
    	logger.debug("Constructor CategoryBean");
    	
    	defaultTitle = facesUtils.getResourceBundleStringValue("formCategoryTitle");
    	categoryTitle = defaultTitle;
    	companyId = getCompanyIdFromUser();
    	
    	try {
			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			for(CompanyVO vo : companySelectList){		
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}
			
			categoryUpdate = false;
			
			searchCategory();
    	} catch (SQLException ex) {
			getSQLExceptionErrorMessage(ex);
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
		}	
		
    }
    
    public void searchCategory() throws ClassCastException, SQLException, Exception {
		logger.debug("searchCategory");
		try {
			
			categoryVO = new CategoryVO();
			Long categoryId = null;
			Integer categoryLevel = null;
			String tempCategoryTitle = "";
			String tempParent_category_code = "";
			Long tempCompanyId = new Long(0);
			
			if (facesUtils.getParameter("categoryParam") != null) {
				String stringForCategory = facesUtils.getParameter("categoryParam");
				tempCategoryTitle = stringForCategory.split(";") [0];
				categoryId = new Long(stringForCategory.split(";")[1]);
				categoryLevel = new Integer(stringForCategory.split(";")[2]);
				tempParent_category_code = stringForCategory.split(";")[3];
				tempCompanyId = new Long(stringForCategory.split(";")[4]);
			}
			
			if (companyId != null && !"".equals(companyId)) {
				if(companyId != 0) {
					Company company = companyService.findById(companyId);
					categoryVO.setCompany(company);
					categoryVO.setCompanyId(companyId);
				} else {
					categoryVO.setCompany(null);
					categoryVO.setCompanyId(null);
				}				
			}

			
//			if (categoryId != null && categoryLevel != null) {
//				categoryVO.setParentId(categoryId);
//				/*setPrev_parent_id(getCategory_parent_id());
//				setPrev_parent_level(getCategory_parent_level());
//				setPrev_category_title(getCategoryTitle());*/
//				setCategoryTitle(tempCategoryTitle);
//				setCategory_parent_id(categoryId);
//				setParent_category_code(tempParent_category_code);
//				setCategory_parent_level(categoryLevel);
////				categoryVO.setCompanyId(tempCompanyId);
//				
//				Company company = companyService.findById(tempCompanyId);
//				categoryVO.setCompany(company);
//				
//				companyId = tempCompanyId;
//			} 
//			else {
//				setCategoryTitle(defaultTitle);
//				setCategory_parent_id(new Long(0));
//				setParent_category_code(null);
//				setCategory_parent_level(new Integer(0));
//				/*setPrev_parent_id(new Long(0));
//				setPrev_parent_level(new Integer(0));
//				setPrev_category_title("");*/
//				categoryVO.setCompanyId(companyId);
//			}
			
			searchCategoryAndPopulateTree(categoryVO, true);
			
		} catch (ClassCastException ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error get request attribute"+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw ex;
		} catch (SQLException ex) {
			getSQLExceptionErrorMessage(ex);
			logger.error(ex.getMessage(), ex);
			throw ex;
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw ex;
		} finally {
			if (getCategoryList() != null && getCategoryList().size() > 0) setIsRowEmpty(false);
			else setIsRowEmpty(true);
			
			if (getCategory_parent_level() != null && getCategory_parent_level().intValue() <= 0) setIsRoot(true);
			else setIsRoot(false);
		}
    }
    
    public void searchCategoryAndPopulateTree (CategoryVO categoryVO, boolean getWithDetail) throws ClassCastException, SQLException, Exception {
    	setCategoryList(categoryService.getListCategoryByParentId(categoryVO, getWithDetail, true));
		
		root = new DefaultTreeNode("root", null);
		populateTree(getCategoryList(), root);
		
	}
    
    private void populateTree(List<Category> categoryListRaw, TreeNode rootNode) {
		for (int index = 0; (categoryListRaw != null && index < categoryListRaw.size()); index++) {
			Category category = (Category) categoryListRaw.get(index);
			TreeNode treeNode = new DefaultTreeNode(category , rootNode);
			populateTree(category.getChildCategoryList(), treeNode);
		}
	}
    
    public void addCategory(ActionEvent event) throws Exception {
		logger.debug("CategoryBean addCategory");
		try {
			
			if(getCompanyIdFromUser() !=null) {
				companyId = getCompanyIdFromUser();
			}
			
			if(companyId !=null && Integer.parseInt(companyId+"") > 0){
				setDispatchAction(NavigationConstants.MODE_INSERT);
				setCategoryUpdate(true);
				
				//setParent_category_code(category_code);
				setUpdate_category_parent_id(getCategory_parent_id());
				setParent_category_name(categoryTitle);
				
				Company company = companyService.findById(companyId);
				if(company!=null && Integer.parseInt(companyId+"") > 0)
					setCompanyName(company.getCompanyName());
				else
					setCompanyName(null);
				
				
				setCategory_id(new Long(0));
				setCategory_code(null);
				setCategory_name(null);
				setCategory_description(null);
				setCategory_level(category_parent_level.intValue() + 1);
			}else{
				facesUtils.addFacesMessage(
						FacesMessage.SEVERITY_WARN,
						facesUtils.getResourceBundleStringValue("formUmCompany")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"),
						"");
			}
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
		}
	}
    
    public void saveCategory(ActionEvent event) throws NullPointerException, SQLException, Exception {
		try {
			logger.debug("MenuEditBean saveMenu");
			
			CategoryVO categoryVO = new CategoryVO();
			
			if(companyId !=null){
				Company company = companyService.findById(companyId);
				categoryVO.setCompany(company);
			}
			categoryVO.setCategoryId(category_id);
			categoryVO.setCategoryCode(category_code);
			categoryVO.setCategoryName(category_name);
			categoryVO.setParentId(update_category_parent_id);
			categoryVO.setCategoryDesc(category_description);
			categoryVO.setCategoryLevel(category_level);
			
			if (getDispatchAction() != null && getDispatchAction().equals(NavigationConstants.MODE_INSERT))
				categoryService.insertCategory(categoryVO, true, userSession.getUserCode());
			else if (getDispatchAction() != null && getDispatchAction().equals(NavigationConstants.MODE_UPDATE)) {
				categoryVO.setEnabledFlag(enabledFlag);
				categoryService.insertCategory(categoryVO, false, userSession.getUserCode());
			}
			
			categoryVO.setParentId(category_parent_id);
			
			searchCategoryAndPopulateTree(categoryVO, true);
			
			setCategoryUpdate(false);
			
		} catch (NullPointerException ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage("menu_action", errorMessage);
		} catch (SQLException ex) {
			getSQLExceptionErrorMessage(ex);
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
		} finally {
			if (getCategoryList() != null && getCategoryList().size() > 0) setIsRowEmpty(false);
			else setIsRowEmpty(true);
			
			if (getCategory_parent_level() != null && getCategory_parent_level().intValue() <= 0) setIsRoot(true);
			else setIsRoot(false);
		} 
    }
    
    public void changeCompany() {
    	try {
			searchCategory();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public void updateCategory(ActionEvent event) throws Exception {
		logger.debug("CategoryEditBean updateCategory");
		try {
			setDispatchAction(NavigationConstants.MODE_UPDATE);
			
			setCategoryUpdate(true);
			
			Long attribCategoryId = (Long) facesUtils.getAttribute(event, "attribCategoryId");
			
			CategoryVO categoryVo = new CategoryVO();
			categoryVo.setCategoryId(attribCategoryId);
			Category categoryQuery = categoryService.getCategory(categoryVo);
			setCategoryToForm(categoryQuery);
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			
		}
	}
    
    public void setCategoryToForm(Category categoryVO) {
    	
    	if(categoryVO.getCompany().getCompanyId() != null) {
    		Company company = companyService.findById(categoryVO.getCompany().getCompanyId());
			setCompanyName(company.getCompanyName());
    	}	
		else {
			setCompanyName(null);
		}	
		setCategory_id(categoryVO.getCategoryId());
		setCategory_name(categoryVO.getCategoryName());
		setCategory_code(categoryVO.getCategoryCode());
		setCategory_description(categoryVO.getCategoryDesc());
		setCategory_level(categoryVO.getCategoryLevel());
		if (categoryVO.getParent() != null) {
			setUpdate_category_parent_id(categoryVO.getParent().getCategoryId());
			setParent_category_code(categoryVO.getParent().getCategoryCode());
			setParent_category_name(categoryVO.getParent().getCategoryName());
		}
		else {
			setParent_category_code(null);
			setParent_category_name(defaultTitle);
		}
		//setEnabledFlag(categoryVO.getEnabled_flag());
	}
    
    
    public void modeDelete(Category vo){
		try
		{
			categoryService.deleteCategory(vo.getCategoryId());
			
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
	                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
	                null);
		}
		catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formUmTitle")),
					null);
		}
	}
    
    public void deleteCategory(ActionEvent actionEvent) throws ClassCastException, SQLException, Exception {
		logger.debug("deleteCategory");
		try {
			Long attribCategoryId = (Long) facesUtils.getAttribute(actionEvent, "attribCategoryId");
			
//			categoryService.deleteCategory(getDel_category_id());
			categoryService.deleteCategory(attribCategoryId);
			
			CategoryVO newCategoryVO = new CategoryVO();
			newCategoryVO.setParentId(getCategory_parent_id());
			searchCategoryAndPopulateTree(newCategoryVO, true);
			
		} catch (SQLException ex) {
			getSQLExceptionErrorMessage(ex);
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			FacesMessage errorMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Operation Failed : "+ ex.getMessage(),
					ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(null, errorMessage);
			logger.error(ex.getMessage(), ex);
			throw ex;
		}  finally {
			if (getCategoryList() != null && getCategoryList().size() > 0) setIsRowEmpty(false);
			else setIsRowEmpty(true);
			
			if (getCategory_parent_level() != null && getCategory_parent_level().intValue() <= 0) setIsRoot(true);
			else setIsRoot(false);
		}
    }
    
    // TODO get from user login
    public Long getCompanyIdFromUser() {
    	return userSession.getCompanyId();
    }
    
    public void cancelCategory(ActionEvent ae) {
		setCategoryUpdate(false);
	}
    
	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public CategoryVO getCategoryVO() {
		return categoryVO;
	}

	public void setCategoryVO(CategoryVO categoryVO) {
		this.categoryVO = categoryVO;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Long getCategory_parent_id() {
		return category_parent_id;
	}

	public void setCategory_parent_id(Long category_parent_id) {
		this.category_parent_id = category_parent_id;
	}

	public Long getUpdate_category_parent_id() {
		return update_category_parent_id;
	}

	public void setUpdate_category_parent_id(Long update_category_parent_id) {
		this.update_category_parent_id = update_category_parent_id;
	}

	public Integer getCategory_parent_level() {
		return category_parent_level;
	}

	public void setCategory_parent_level(Integer category_parent_level) {
		this.category_parent_level = category_parent_level;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getDefaultTitle() {
		return defaultTitle;
	}

	public void setDefaultTitle(String defaultTitle) {
		this.defaultTitle = defaultTitle;
	}

	public boolean isRowEmpty() {
		return isRowEmpty;
	}

	public void setIsRowEmpty(boolean isRowEmpty) {
		this.isRowEmpty = isRowEmpty;
	}

	public String getDispatchAction() {
		return dispatchAction;
	}

	public void setDispatchAction(String dispatchAction) {
		this.dispatchAction = dispatchAction;
	}

	public boolean isCategoryUpdate() {
		return categoryUpdate;
	}

	public void setCategoryUpdate(boolean categoryUpdate) {
		this.categoryUpdate = categoryUpdate;
	}
	
	public boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public Integer getCategory_level() {
		return category_level;
	}

	public void setCategory_level(Integer category_level) {
		this.category_level = category_level;
	}

	public String getParent_category_name() {
		return parent_category_name;
	}

	public void setParent_category_name(String parent_category_name) {
		this.parent_category_name = parent_category_name;
	}

	public String getParent_category_code() {
		return parent_category_code;
	}

	public void setParent_category_code(String parent_category_code) {
		this.parent_category_code = parent_category_code;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getCategory_description() {
		return category_description;
	}

	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}

	public boolean getIsRowEmpty() {
		return isRowEmpty;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCategory_code() {
		return category_code;
	}

	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}

	public Long getDel_category_id() {
		return del_category_id;
	}

	public void setDel_category_id(Long del_category_id) {
		this.del_category_id = del_category_id;
	}

}
