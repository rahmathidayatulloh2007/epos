package com.wo.epos.module.sales.product.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.event.SelectEvent;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.item.service.ProductService;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.service.UmService;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "productSearchBean")
@ViewScoped
public class ProductSearchBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 664461128245316928L;

	static Logger logger = Logger.getLogger(ProductSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
		
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{productService}")
	private ProductService productService;
	
	@ManagedProperty(value = "#{umService}")
	private UmService umService;
	
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	
		
	private ProductVO productVOSearchDialog = new ProductVO();	

	private PagingTableModel<ProductVO> pagingProduct;	
	
	private List<ProductVO> selectedProducts;
	
	private List<SelectItem> companyTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> umSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> categorySelectItem = new ArrayList<SelectItem>();
	
	private List<CompanyVO> companySelectList = new ArrayList<CompanyVO>();
	private List<Category> categorySelectList = new ArrayList<Category>();
	private List<UmVO> umSelectList = new ArrayList<UmVO>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;
	private String completeCompany;
    private String completeUm;
    private String completeCategory;
	
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			productVOSearchDialog = new ProductVO();
			pagingProduct = new PagingTableModel(productService, paging);
			if (userSession.getCompanyId() != null) {
				List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
				searchCriteria.add(new SearchValueObject("companyLogin", userSession.getCompanyId()));
				pagingProduct.setSearchCriteria(searchCriteria);

				initBean(userSession.getCompanyId());
			} else {
				companySelectList = new ArrayList<CompanyVO>();
				categorySelectList = new ArrayList<Category>();
				umSelectList = new ArrayList<UmVO>();
				initBean(null);
			}

			disableFlag = false;
			disableFlagAdd = true;
			disableSearchAll = false;

			checkSearch = 0;
		}
	}
	
	private void initBean(Long companyId){
		 companySelectList = companyService.searchCompanyList();		 
	     categorySelectList = categoryService.searchCategoryCompany(companyId);
	     umSelectList = umService.searchUmCompany(companyId);	        
	     /*List<CompanyVO> companySelectList = companyService.searchCompanyList();
	     companyTypeSelectItem = new ArrayList<SelectItem>();
	     for(CompanyVO com : companySelectList){
	    	  companyTypeSelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
	     }
	    
	     List<Category> categorySelectList = categoryService.searchCategoryCompany(companyId);
	     categorySelectItem = new ArrayList<SelectItem>();
	     for(Category category : categorySelectList){
	    	   categorySelectItem.add(new SelectItem(category.getCategoryId(), category.getCategoryName()));
	     }
	    
	     List<UmVO> umSelectList = umService.searchUmCompany(companyId);
	     umSelectItem = new ArrayList<SelectItem>();
	     for(UmVO vo : umSelectList){
	    	  umSelectItem.add(new SelectItem(vo.getUmId(), vo.getUmName()));
	     }*/
	}
	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search(){		
		if(checkSearch == 0){	
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if(userSession.getCompanyId() !=null){
			     searchCriteria.add(new SearchValueObject("companyLogin", userSession.getCompanyId()));
			}			
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}		
			
			 disableSearchAll = false;
			 pagingProduct.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 

	public void modeDelete(List<ProductVO> productVOList){
		try
		{
			for(int i=0; i<productVOList.size(); i++){
				ProductVO productTemp = (ProductVO)productVOList.get(i);			
				productService.delete(productTemp.getProductId());
			}
			
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_INFO, 
					"frm001:messages", 
		            facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
		            null);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formSystemPropertyTitle")),
					null);	  
		}	
	}
	
	public void clear(){
		searchAll = "";
		productVOSearchDialog = new ProductVO();
		checkSearch = 0;
		completeCategory = "";
		completeCompany = "";
	    completeUm = "";
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		productVOSearchDialog = new ProductVO();
		completeCategory = "";
		completeCompany = "";
	    completeUm = "";
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(userSession.getCompanyId() !=null){
		     searchCriteria.add(new SearchValueObject("companyLogin", userSession.getCompanyId()));
		}	
		
		if(completeCompany !=null && StringUtils.isNotBlank(completeCompany)) {
			String[] split = completeCompany.toString().split(" - ");
			Company comTemp = companyService.findByCode(split[0].toString().trim());
			builder.append(facesUtils.getResourceBundleStringValue("formProductCompany"));
			builder.append(":"+comTemp.getCompanyName().toString()+",");
			searchCriteria.add(new SearchValueObject("companyId", comTemp.getCompanyId()));
		}		
		if(productVOSearchDialog.getProductCode() !=null && StringUtils.isNotBlank(productVOSearchDialog.getProductCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formProductCode"));
			builder.append(":"+productVOSearchDialog.getProductCode()+",");
			searchCriteria.add(new SearchValueObject("productCode", productVOSearchDialog.getProductCode()));
		}
		if(productVOSearchDialog.getProductName() !=null && StringUtils.isNotBlank(productVOSearchDialog.getProductName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formProductName"));
			builder.append(":"+productVOSearchDialog.getProductName()+",");
			searchCriteria.add(new SearchValueObject("productName", productVOSearchDialog.getProductName()));
		}		
		if(completeCategory !=null && StringUtils.isNotBlank(completeCategory)) {
			String[] split = completeCategory.toString().split(" - ");
			Category cate = categoryService.findByCode(split[0].toString().trim());
			builder.append(facesUtils.getResourceBundleStringValue("formProductCategories"));
			builder.append(":"+cate.getCategoryName()+",");
			searchCriteria.add(new SearchValueObject("categoryId", cate.getCategoryId()));
		}
		if(completeUm !=null && StringUtils.isNotBlank(completeUm)) {
			Um umTemp = umService.findByName(completeUm.toString().trim());
			builder.append(facesUtils.getResourceBundleStringValue("formProductUm"));
			builder.append(":"+completeUm+",");
			searchCriteria.add(new SearchValueObject("umId", umTemp.getUmId()));
		}
		if(productVOSearchDialog.getItemComposition() !=null && StringUtils.isNotBlank(productVOSearchDialog.getItemComposition())) {
			builder.append(facesUtils.getResourceBundleStringValue("formProductComposition"));
			builder.append(":"+productVOSearchDialog.getItemComposition()+",");
			searchCriteria.add(new SearchValueObject("itemComposition", productVOSearchDialog.getItemComposition()));
		}
	
		
		if(!builder.toString().equals("")){
		     builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingProduct.setSearchCriteria(searchCriteria);
		
	}
	
	public void postProcessXls(Object document){
		HSSFWorkbook workbook = (HSSFWorkbook) document;
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow header = sheet.getRow(0); // get header
        HSSFFont headerRowCellFont = workbook.createFont(); // Create a new font in the workbook
            //headerRowCellFont.setFontName(MODE_ADD);
            //headerRowCellFont.Color = HSSFColor.BLACK.index;
        headerRowCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
           // headerRowCellFont.Boldweight = HSSFFont.BOLDWEIGHT_BOLD;
        HSSFCellStyle headerRowCellStyle = workbook.createCellStyle(); // Create a new style in the workbook
        headerRowCellStyle.setFont(headerRowCellFont);  // Attach the font to the Style
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++){
        	header.getCell(i).setCellStyle(headerRowCellStyle);
        }
	}
	
	public void changeCompany(){
		if(userSession.getCompanyId() !=null){
			initBean(userSession.getCompanyId());   		
		}else{
			initBean(productVOSearchDialog.getCompanyId());   
		}
	}
	
	
	public List<String> completeCompany(String outoCompleteText){	
		List<String> resultList = new ArrayList<String>();			
		for(CompanyVO vo : companySelectList){
			if(vo.getCompanyName().toUpperCase().contains(outoCompleteText.toUpperCase())){
				  resultList.add(vo.getCompanyCode()+" - "+vo.getCompanyName());
			}	  
		}
		
		return resultList;		
	}
	
	public void handleSelectCompany(SelectEvent event) {
        Object item = event.getObject(); 
        
        String[] split = item.toString().split("-");
        Company comTemp = companyService.findByCode(split[0].toString().trim());
        productVOSearchDialog.setCompanyId(comTemp.getCompanyId());
        
        changeCompany();
        
    }
	
	public List<String> completeUm(String outoCompleteText){	
		List<String> resultList = new ArrayList<String>();			
		for(UmVO vo : umSelectList){
			if(vo.getUmName().toUpperCase().contains(outoCompleteText.toUpperCase())){
				  resultList.add(vo.getUmName());
			}	  
		}
		
		return resultList;		
	}	
		
	public List<String> completeCategory(String outoCompleteText){	
		List<String> resultList = new ArrayList<String>();			
		for(Category category : categorySelectList){
			if(category.getCategoryCode().toUpperCase().contains(outoCompleteText.toUpperCase())){
				  resultList.add(category.getCategoryCode() +" - "+category.getCategoryName());
			}	  
		}
		
		return resultList;		
	}	
	


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ProductSearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public UmService getUmService() {
		return umService;
	}

	public void setUmService(UmService umService) {
		this.umService = umService;
	}

	public ProductVO getProductVOSearchDialog() {
		return productVOSearchDialog;
	}

	public void setProductVOSearchDialog(ProductVO productVOSearchDialog) {
		this.productVOSearchDialog = productVOSearchDialog;
	}

	public PagingTableModel<ProductVO> getPagingProduct() {
		return pagingProduct;
	}

	public void setPagingProduct(PagingTableModel<ProductVO> pagingProduct) {
		this.pagingProduct = pagingProduct;
	}

	public List<ProductVO> getSelectedProducts() {
		return selectedProducts;
	}

	public void setSelectedProducts(List<ProductVO> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}

	public List<SelectItem> getCompanyTypeSelectItem() {
		return companyTypeSelectItem;
	}

	public void setCompanyTypeSelectItem(List<SelectItem> companyTypeSelectItem) {
		this.companyTypeSelectItem = companyTypeSelectItem;
	}

	public List<SelectItem> getUmSelectItem() {
		return umSelectItem;
	}

	public void setUmSelectItem(List<SelectItem> umSelectItem) {
		this.umSelectItem = umSelectItem;
	}

	public List<SelectItem> getCategorySelectItem() {
		return categorySelectItem;
	}

	public void setCategorySelectItem(List<SelectItem> categorySelectItem) {
		this.categorySelectItem = categorySelectItem;
	}

	public boolean isDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}

	public boolean isDisableFlagAdd() {
		return disableFlagAdd;
	}

	public void setDisableFlagAdd(boolean disableFlagAdd) {
		this.disableFlagAdd = disableFlagAdd;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}


	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public List<CompanyVO> getCompanySelectList() {
		return companySelectList;
	}

	public void setCompanySelectList(List<CompanyVO> companySelectList) {
		this.companySelectList = companySelectList;
	}

	public List<Category> getCategorySelectList() {
		return categorySelectList;
	}

	public void setCategorySelectList(List<Category> categorySelectList) {
		this.categorySelectList = categorySelectList;
	}

	public List<UmVO> getUmSelectList() {
		return umSelectList;
	}

	public void setUmSelectList(List<UmVO> umSelectList) {
		this.umSelectList = umSelectList;
	}

	public String getCompleteCompany() {
		return completeCompany;
	}

	public void setCompleteCompany(String completeCompany) {
		this.completeCompany = completeCompany;
	}

	public String getCompleteUm() {
		return completeUm;
	}

	public void setCompleteUm(String completeUm) {
		this.completeUm = completeUm;
	}

	public String getCompleteCategory() {
		return completeCategory;
	}

	public void setCompleteCategory(String completeCategory) {
		this.completeCategory = completeCategory;
	}
	
			
}
