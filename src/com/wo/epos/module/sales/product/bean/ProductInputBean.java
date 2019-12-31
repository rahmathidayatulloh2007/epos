package com.wo.epos.module.sales.product.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.util.InputEnableMap;
import com.wo.epos.common.vo.UploadFileVO;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.service.ItemService;
import com.wo.epos.module.inv.item.service.ProductBomService;
import com.wo.epos.module.inv.item.service.ProductService;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.inv.item.vo.ProductBomVO;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.service.UmService;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "productInputBean")
@ViewScoped
public class ProductInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -5131337176553879805L;

	static Logger logger = Logger.getLogger(ProductInputBean.class);

	private static final String UPLOAD_FUNC = "SALES_PRODUCT_IMAGE";
	
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
	
	@ManagedProperty(value = "#{itemService}")
	private ItemService itemService;
	
	@ManagedProperty(value = "#{productBomService}")
	private ProductBomService productBomService;
	
	@ManagedProperty(value = "#{mbImageStreamer}")
	private MbImageStreamer mbImageStreamer;
	
	private List<ProductBomVO> productBomVoList = new ArrayList<ProductBomVO>();
	private List<CompanyVO> companySelectList = new ArrayList<CompanyVO>();
	private List<ItemVO> itemList = new ArrayList<ItemVO>(); 
	private List<Category> categorySelectList = new ArrayList<Category>();
	private List<UmVO> umSelectList = new ArrayList<UmVO>();
	private List<ItemDiscountVO> itemDiscountList;
	private ProductVO productVO = new ProductVO();
	private ProductBomVO productBomVO = new ProductBomVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
	
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> ingredientSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> umSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    private String completeCompany;
    private String completeUm;
    private String completeCategory;
    private String streamUploadId;
    
    private Integer indexRowTable;
    
    private UploadedFile uploadedFile;
    private InputEnableMap mapEnableInput = new InputEnableMap();
    
    
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			productVO = new ProductVO();
			if (userSession.getCompanyId() != null) {
				initBean(userSession.getCompanyId());
			} else {
				companySelectList = new ArrayList<CompanyVO>();
				categorySelectList = new ArrayList<Category>();
				umSelectList = new ArrayList<UmVO>();
				itemList = new ArrayList<ItemVO>();
				initBean(null);
			}
			MODE_TYPE = "ADD";
			disableFlag = false;
			disableFlagAdd = true;
		}
		
		
		umSelectItem = new ArrayList<SelectItem>();
		List<UmVO> umSelectList = itemService.searchUmList();
		for(UmVO vo : umSelectList){	
	    			umSelectItem.add(new SelectItem(vo.getUmId(), vo.getUmName()));
		}
		
	}
	
	private void initBean(Long companyId){
		 companySelectList = companyService.searchCompanyList();		 
	     categorySelectList = categoryService.searchCategoryCompanyByParentIsNotNull(companyId);
	     umSelectList = umService.searchUmCompany(companyId);	         
	     itemList = itemService.searchItemByCompanyList(companyId);
	     
	     activeSelectItem = new ArrayList<SelectItem>();
	     activeSelectItem.add(new SelectItem("Y","Y"));
	     activeSelectItem.add(new SelectItem("N","N"));
	     
	     ingredientSelectItem = new ArrayList<SelectItem>();
	     ingredientSelectItem.add(new SelectItem("Y","Y"));
	     ingredientSelectItem.add(new SelectItem("N","N"));
	     
	}
	
	public void changeCompany(){
		if(userSession.getCompanyId() !=null){
			initBean(userSession.getCompanyId());   		
		}else{
			initBean(productVO.getCompanyId());   
		}
	}
	

	
	public boolean validate(){
		boolean valid = true;
		if(productBomVoList.size() > 0){
			for(int i=0; i<productBomVoList.size(); i++){
				ProductBomVO vo = (ProductBomVO)productBomVoList.get(i);
				
				if(vo.getItemName() == null || vo.getItemName().equals("")){
					facesUtils
        			.addFacesMessage(
        					FacesMessage.SEVERITY_ERROR,
        					"frm001:messagesTable",					
        					facesUtils.getResourceBundleStringValue("textRow")+ " "+(i+1)+" "+
        					facesUtils.getResourceBundleStringValue("formProductItem")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
					
					valid = false;
				}
				
				if(vo.getItemQty() != null){
					if(vo.getItemQty() <=0){
						facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
								"frm001:messagesTable", facesUtils.getResourceBundleStringValue("textRow")+ " "
										+ (i + 1)+ " "+ facesUtils.getResourceBundleStringValue("formProductErrMsgQty"));
						valid = false;
					}
				}else{					
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
							"frm001:messagesTable", facesUtils.getResourceBundleStringValue("textRow")+ " "
									+ (i + 1)+ " "+ facesUtils.getResourceBundleStringValue("formProductQty")
									+ " "+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));			
			        valid = false;
				}
					
				
			    for(int j=i+1; j<productBomVoList.size(); j++){
					    ProductBomVO voDuplicate = (ProductBomVO)productBomVoList.get(j);	
		                if(vo.getItemName() !=null && voDuplicate.getItemName() !=null){ 
			                if(voDuplicate.getItemName().trim().equals(vo.getItemName().trim())){
							facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesTable",
									facesUtils.getResourceBundleStringValue("textRow") + " " + (i + 1) + " "
											+ vo.getItemName() + " "
											+ facesUtils.getResourceBundleStringValue("errorDuplicateRow") + " "
											+ (j + 1));

			        			valid = false;
			                } 
		               }
				 }
			}
			
		}else{
			facesUtils
			.addFacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messagesTable",					
					facesUtils.getResourceBundleStringValue("formProductErrMsgMinim"));
			
			valid = false;
	
		}
	
		
		
		return valid;

	}

	public boolean validateCompany() {
		boolean valid = true;
		if (userSession.getCompanyId() == null) {
			if (productVO.getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductCompany") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} else {
				valid = true;
			}
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	
	public boolean validateProduct(){
		boolean valid = true;
		
		try {
			if (productVO.getProductCode().trim().isEmpty() || productVO.getProductCode() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}   if (productVO.getProductName().trim().isEmpty() || productVO.getProductName() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getProductDesc().trim().isEmpty() || productVO.getProductDesc() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductDesc") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getUmId() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductUm") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}if (productVO.getUm2Id() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductUm") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}if (completeCategory == null || completeCategory == "") {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductCategories") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getLaunchingDt() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductLaunchingDate") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}
		
			if (productVO.getUnitPerUm2() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductUm") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getBufferStock() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductBufferStock") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getUnitCost() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductUnitCost") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getUnitPrice() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
						facesUtils.getResourceBundleStringValue("formProductUnitPrice") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}
		} catch (NumberFormatException ex) {
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesProduct",
					facesUtils.getResourceBundleStringValue("errorMustBeNumber"));
			valid = false;
		}
		
		
		return valid;

	}
	

	
	public boolean bufferStock(){
		boolean valid = true;	
		if(productBomVoList.size() > 0){
			for(int i=0; i<productBomVoList.size(); i++){
				ProductBomVO vo = (ProductBomVO)productBomVoList.get(i);
				
				if(vo.getItemQty() != null){
					if(vo.getItemQty() <= productVO.getBufferStock()){

					    facesUtils.addFacesMsg(
								FacesMessage.SEVERITY_WARN, 
								"frm001:messages", 
				                facesUtils.retrieveMessage("proses_commonBuffer","   "+productVO.getProductName() + " " + facesUtils.getResourceBundleStringValue("common_msg_buffer") + " " + 
								 vo.getItemName() ), 
				                null);
					    
						 valid = false;
					}
				}else{					
					facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR,
							"frm001:messagesTable", facesUtils.getResourceBundleStringValue("textRow")+ " "
									+ (i + 1)+ " "+ facesUtils.getResourceBundleStringValue("formProductQty")
									+ " "+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));			
			        valid = false;
				}	
			
			}
			
		}else{
			facesUtils
			.addFacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messagesTable",					
					facesUtils.getResourceBundleStringValue("formProductErrMsgMinim"));
			
			valid = false;
	
		}
		
	return valid;	

	}
	
	
	
	public void save()
	{
		try
		{
			if(userSession.getCompanyId() !=null )
			{
				productVO.setCompanyId(userSession.getCompanyId());
			}
			
			if(MODE_TYPE.equals("ADD")  )
			{
			    productService.save(productVO, productBomVoList,itemDiscountList, userSession.getUserCode());
			    bufferStock();
			    facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				productService.update(productVO, productBomVoList,itemDiscountList, userSession.getUserCode());
				bufferStock();
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_updated")), 
		                null);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					"Operation Failed : " + ex.getMessage(),
					null);	    
		}	
	}
	
	public void modeAdd(){
		MODE_TYPE = "ADD";
		
		productVO = new ProductVO();
		completeCategory = "";
		completeCompany = "";
	    completeUm = "";
	    productBomVoList = new ArrayList<ProductBomVO>();
	    
		List<ParameterDtl> list = parameterService.parameterDtlList(CommonConstants.CUSTOMER_TYPE);
		
		itemDiscountList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			ParameterDtl parameterDtl = list.get(i);
			ItemDiscountVO vo = new ItemDiscountVO();
			vo.setParameterDtl(parameterDtl);
			itemDiscountList.add(vo);
		}
	}
	
	
	public void modeEdit(List<ProductVO> productList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<productList.size(); i++){
			  ProductVO productTemp = (ProductVO)productList.get(i);
			  productVO = productTemp;	
			  
			  if(productTemp.getImageFile() !=null){
				   String uploadId = mbImageStreamer.returnCurrentTime();
				   this.streamUploadId = uploadId;
				   mbImageStreamer.clearUpload(getUploadFuncId());
				   UploadFileVO file = new UploadFileVO(uploadId,productTemp.getImageFile());
				   mbImageStreamer.addUpload(getUploadFuncId(), file);
			  } 
			  
			  completeCompany = productVO.getCompanyCode()+" - "+productVO.getCompanyName();
			  completeUm = productVO.getUmName();
			  completeCategory = productVO.getCategoryCode()+" - "+productVO.getCategoryName();
		}
		
		productBomVoList = productBomService.searchFindByProductId(productVO.getProductId());
		
		Product product = productService.findById(productVO.getProductId());
		itemDiscountList= new ArrayList<>();
		for(int i=0;i<product.getListItemDiscount().size();i++){
			ItemDiscount itemDiscount = product.getListItemDiscount().get(i);
			ItemDiscountVO vo = new ItemDiscountVO();
			vo.setItemDiscountId(itemDiscount.getItemDiscountId());
			vo.setDiscount1(itemDiscount.getDiscount1());
			vo.setDiscount2(itemDiscount.getDiscount2());
			vo.setDiscount3(itemDiscount.getDiscount3());
			vo.setParameterDtl(itemDiscount.getCustomerType());
			itemDiscountList.add(vo);
			
		}
	}
	
	public void addItem(){
		productBomVO = new ProductBomVO();
		productBomVoList.add(productBomVO);
	}
	
	public void indexDialogItem(Integer indexRow){
		this.indexRowTable = indexRow;
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
        productVO.setCompanyId(comTemp.getCompanyId());
        
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
	
	public void handleSelectUm(SelectEvent event) {
        Object item = event.getObject();   
        
        Um umTemp = umService.findByName(item.toString().trim());
        productVO.setUmId(umTemp.getUmId());
        productVO.setUmName(umTemp.getUmName());
        
        
    }
	public void onChangeuM(){
		
		Um um = (Um)umService.findById(productVO.getUmId());
		if(um!=null){
			productVO.setUmName(um.getUmName());
		}
	}
	public void handleSelectUm2(SelectEvent event) {
        Object item = event.getObject();   
        
        Um umTemp2 = umService.findByName(item.toString().trim());
        productVO.setUm2Id(umTemp2.getUmId());
        productVO.setUm2Name(umTemp2.getUmName());
        
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
	
	public void handleSelectCategory(SelectEvent event) {
        Object item = event.getObject();   
        
        String[] split = item.toString().split("-");
        Category cate = categoryService.findByCode(split[0].toString().trim());
        productVO.setCategoryId(cate.getCategoryId());
    }
	
	public List<String> completeItem(String outoCompleteText){	
		List<String> resultList = new ArrayList<String>();			
		for(ItemVO itemVo : itemList){
			if(itemVo.getItemName().toUpperCase().contains(outoCompleteText.toUpperCase())){
				  resultList.add(itemVo.getItemCode() +" - "+itemVo.getItemName());
			}	  
		}
		
		return resultList;		
	}	
	
	public void handleSelectItem(SelectEvent event) {
        Object item = event.getObject();        
        chooseItem(item.toString());
    }
	
	public void chooseItem(String item){
		String[] split = item.split("-");
		Item itemTemp = itemService.findByItemCode(split[0].toString().trim());
		
		for(int i=0; i<productBomVoList.size(); i++){
			ProductBomVO bomVo = (ProductBomVO)productBomVoList.get(i);
			  if(i == indexRowTable){
				  bomVo.setItemId(itemTemp.getItemId());
				  bomVo.setItemCode(itemTemp.getItemCode());
				  bomVo.setItemName(itemTemp.getItemName());
				  if(itemTemp.getUm() !=null){
					  bomVo.setUmId(itemTemp.getUm().getUmId());
					  bomVo.setUmName(itemTemp.getUm().getUmName());
				  }	
				  if(itemTemp.getUm() !=null){
					  bomVo.setUmId(itemTemp.getUm2().getUmId());
					  bomVo.setUmName(itemTemp.getUm2().getUmName());
				  }	
				  break;
			  }  
		}	
		
	}
	
	
	public void handleUploadedFile(ActionEvent event) throws IOException {
		if (checkUploadFileValid(uploadedFile)) {

			productVO.setImageFile(IOUtils.toByteArray(uploadedFile.getInputstream()));
			productVO.setImageFilename(uploadedFile.getFileName());
		
			String uploadId = mbImageStreamer.returnCurrentTime();
			this.streamUploadId = uploadId;
			mbImageStreamer.clearUpload(getUploadFuncId());
			mbImageStreamer.addUpload(getUploadFuncId(), uploadId, uploadedFile);
		}
		
	}
		
	private boolean checkUploadFileValid(UploadedFile fileUpload) throws IOException {
		boolean res = true;
		if (fileUpload == null) {
	        facesUtils.addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:upload", 
	                "Tidak ada file yang diupload", 
	                null);			
			
			res = false;
		} else {
			String filename = fileUpload.getFileName();
			String extAllowed[] = queryUploadExtensionAllowed();
			if (!fileUploadValid(filename, extAllowed)) {
				res = false;
		        facesUtils.addFacesMsg(
		                FacesMessage.SEVERITY_ERROR, 
		                "frm001:upload", 
		                "Tidak bisa upload file selain tipe " + Arrays.toString(extAllowed), 
		                null);			
			}		
			
			Long maxSize = queryUploadMaxSizeAllowed();
			if (!fileUploadSizeValid(fileUpload, maxSize)) {
				res = false;
		        facesUtils.addFacesMsg(
		                FacesMessage.SEVERITY_ERROR, 
		                "frm001:upload", 
		                "Ukuran file upload maks : [" + (maxSize/1024) + "] Kbytes", 
		                null);			
			}
			
		}
		
		
		return res;
	}

	private String[] queryUploadExtensionAllowed() {
		List <ParameterDtl> listQueryRes = 
				itemService.parameterDtlList(
						CommonConstants.SETTING_ITEM_UPLOAD_EXT);
		String res[] = new String[listQueryRes.size()];

		for (int i = 0 ; i < res.length ; i++) {
			res[i] = listQueryRes.get(i).getParameterDtlName();
		}
		return res;
	}
	
	private boolean fileUploadValid(String fileName, String [] extAllowed) {
		for (String ext : extAllowed) {
			if (StringUtils.endsWithIgnoreCase(fileName, "." + ext)) {
				return true;
			}			
		}
		return false;
	}
		
	private Long queryUploadMaxSizeAllowed() {
		List <ParameterDtl> listQueryRes = 
				itemService.parameterDtlList(
						CommonConstants.SETTING_ITEM_UPLOAD_SIZE);
		if (listQueryRes.size() > 0) {
			try {
				Long maxSize = 
						Long.parseLong(listQueryRes.get(0).getParameterDtlName()) * 1024;
				return maxSize;
			} catch (Exception ex) {
				logger.error("Error parsing max size upload allowed [" + listQueryRes.get(0).getParameterDtlName() + "]", ex);
				ex.printStackTrace();
			}
		}
		
		return 0l;
	}
		
	private boolean fileUploadSizeValid(UploadedFile file, Long maxSize) throws IOException {
		//int length = file.getContents().length;
		int length = IOUtils.toByteArray(file.getInputstream()).length;
		if (length > maxSize) {
			return false;
		} else {
			return true;
		}
	}
	
	public String getUploadFuncId() {
		return UPLOAD_FUNC;
	}	
	
	public boolean inputEnabled(String key) {
		return mapEnableInput.inputEnabled(key);
	}
	public boolean inputEnabled() {
		return mapEnableInput.inputEnabled();
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ProductInputBean.logger = logger;
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

	public ProductVO getProductVO() {
		return productVO;
	}

	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
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

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public List<ProductBomVO> getProductBomVoList() {
		return productBomVoList;
	}

	public void setProductBomVoList(List<ProductBomVO> productBomVoList) {
		this.productBomVoList = productBomVoList;
	}

	public ProductBomVO getProductBomVO() {
		return productBomVO;
	}

	public void setProductBomVO(ProductBomVO productBomVO) {
		this.productBomVO = productBomVO;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public List<ItemVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemVO> itemList) {
		this.itemList = itemList;
	}

	public Integer getIndexRowTable() {
		return indexRowTable;
	}

	public void setIndexRowTable(Integer indexRowTable) {
		this.indexRowTable = indexRowTable;
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

	public List<CompanyVO> getCompanySelectList() {
		return companySelectList;
	}

	public void setCompanySelectList(List<CompanyVO> companySelectList) {
		this.companySelectList = companySelectList;
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

	public ProductBomService getProductBomService() {
		return productBomService;
	}

	public void setProductBomService(ProductBomService productBomService) {
		this.productBomService = productBomService;
	}

	public List<SelectItem> getIngredientSelectItem() {
		return ingredientSelectItem;
	}

	public void setIngredientSelectItem(List<SelectItem> ingredientSelectItem) {
		this.ingredientSelectItem = ingredientSelectItem;
	}

	public MbImageStreamer getMbImageStreamer() {
		return mbImageStreamer;
	}

	public void setMbImageStreamer(MbImageStreamer mbImageStreamer) {
		this.mbImageStreamer = mbImageStreamer;
	}

	public String getStreamUploadId() {
		return streamUploadId;
	}

	public void setStreamUploadId(String streamUploadId) {
		this.streamUploadId = streamUploadId;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public InputEnableMap getMapEnableInput() {
		return mapEnableInput;
	}

	public void setMapEnableInput(InputEnableMap mapEnableInput) {
		this.mapEnableInput = mapEnableInput;
	}

	public static String getUploadFunc() {
		return UPLOAD_FUNC;
	}

	public List<ItemDiscountVO> getItemDiscountList() {
		return itemDiscountList;
	}

	public void setItemDiscountList(List<ItemDiscountVO> itemDiscountList) {
		this.itemDiscountList = itemDiscountList;
	}

	public List<SelectItem> getUmSelectItem() {
		return umSelectItem;
	}

	public void setUmSelectItem(List<SelectItem> umSelectItem) {
		this.umSelectItem = umSelectItem;
	}

	
}
