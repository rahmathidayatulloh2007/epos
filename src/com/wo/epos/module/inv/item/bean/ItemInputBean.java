package com.wo.epos.module.inv.item.bean;

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
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.bean.MbImageStreamer;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.util.InputEnableMap;
import com.wo.epos.common.vo.UploadFileVO;
import com.wo.epos.module.inv.category.model.Category;
import com.wo.epos.module.inv.category.service.CategoryService;
import com.wo.epos.module.inv.category.vo.CategoryVO;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.model.Product;
import com.wo.epos.module.inv.item.service.ItemService;
import com.wo.epos.module.inv.item.service.ProductBomService;
import com.wo.epos.module.inv.item.service.ProductService;
import com.wo.epos.module.inv.item.vo.ItemBomVO;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.inv.item.vo.ProductBomVO;
import com.wo.epos.module.inv.item.vo.ProductVO;
import com.wo.epos.module.inv.itemDiscount.model.ItemDiscount;
import com.wo.epos.module.inv.itemDiscount.service.ItemDiscountService;
import com.wo.epos.module.inv.itemDiscount.vo.ItemDiscountVO;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.inv.um.service.UmService;
import com.wo.epos.module.inv.um.vo.UmVO;
import com.wo.epos.module.purchasing.supplier.model.SupplierItem;
import com.wo.epos.module.purchasing.supplier.service.SupplierService;
import com.wo.epos.module.purchasing.supplier.vo.SupplierItemVO;
import com.wo.epos.module.purchasing.supplier.vo.SupplierVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;

import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.parameter.vo.ParameterDtlVO;

@ManagedBean(name = "itemInputBean")
@ViewScoped
public class ItemInputBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 5691372360609967430L;
	static Logger logger = Logger.getLogger(ItemInputBean.class);
	
	@ManagedProperty(value = "#{itemService}")
	private ItemService itemService;
	
	@ManagedProperty(value = "#{itemDiscountService}")
	private ItemDiscountService itemDiscountService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{umService}")
	private UmService umService;
	
	@ManagedProperty(value = "#{categoryService}")
	private CategoryService categoryService;
	
	@ManagedProperty(value = "#{productBomService}")
	private ProductBomService productBomService;
	
	@ManagedProperty(value = "#{productService}")
	private ProductService productService;
	
	@ManagedProperty(value = "#{supplierService}")
	private SupplierService supplierService;
	
	@ManagedProperty(value = "#{mbImageStreamer}")
	private MbImageStreamer mbImageStreamer;

	private ItemVO itemVO = new ItemVO();
	private List<SupplierItemVO> selectedSupplierItems;
	private List<SupplierItemVO> supplierItemList;
	private List<ProductBomVO> productBomList;
	private List<Long> supplierItemIdList = new ArrayList<Long>();
//	private List<Long> itemBomIdDeletedList = new ArrayList<Long>();
	private List<ProductVO> productList;
	private List<ItemBomVO> itemBomList;
	private List<ItemDiscountVO> itemDiscountList;

	private SupplierItemVO supplierItemVO = new SupplierItemVO();
	private ProductVO productVO = new ProductVO();
	private ProductBomVO productBomVO = new ProductBomVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	private boolean adminMode;
	private boolean sellChecked;
	
	private String searchAll;
	private PagingTableModel<ParameterDtlVO> pagingParameterDtl;	
	private List<ParameterDtlVO> listParameterDtl;
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> umSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> supplierSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> categorySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> activeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> compositeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> itemNonCompositeSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    private String streamUploadId;
    private static final String UPLOAD_FUNC = "SDM001EDIT";
    private UploadedFile uploadedFile;
    private InputEnableMap mapEnableInput = new InputEnableMap();
    
    private Long clickCount;
    
    private String text;
    
    
    @PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){

			
			//------------------------------------------------------------------------------
			
	
			
			//-----------------------ambil dan set pram ditail berdasarkan customerType---------
			
			itemVO = new ItemVO();
			supplierItemVO = new SupplierItemVO();
			supplierItemList = new ArrayList<SupplierItemVO>();
			productBomList = new ArrayList<ProductBomVO>();
			productList = new ArrayList<ProductVO>();		
			itemBomList = new ArrayList<ItemBomVO>();
			
			
			activeSelectItem = new ArrayList<SelectItem>();
			activeSelectItem.add(new SelectItem("Y", "Y"));
			activeSelectItem.add(new SelectItem("N", "N"));

			compositeSelectItem = new ArrayList<SelectItem>();
			compositeSelectItem.add(new SelectItem("Y", "Y"));
			compositeSelectItem.add(new SelectItem("N", "N"));
			
			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = itemService.searchCompanyList();
			for(CompanyVO vo : companySelectList){		
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}
			
			List<CompanyVO> companyUserList = itemService.searchCompanyList();
			for(int i = 0; i < companyUserList.size(); i++)
			if(userSession.getCompanyId()!=null){
				if(userSession.getCompanyId().equals(companyUserList.get(i).getCompanyId())){
					adminMode = false;
					break;
				}
			}
			else
			{
				adminMode = true;
			}
			
			umSelectItem = new ArrayList<SelectItem>();
			List<UmVO> umSelectList = itemService.searchUmList();
			for(UmVO vo : umSelectList){	
				if(adminMode == false)
		    	{
		    		if(vo.getCompanyId().equals(userSession.getCompanyId())){
		    			umSelectItem.add(new SelectItem(vo.getUmId(), vo.getUmName()));
		    		}
		    	}
//				else
//		    	{
//		    		umSelectItem.add(new SelectItem(vo.getUmId(), vo.getUmName()));
//		    	}
			}
			
			supplierSelectItem = new ArrayList<SelectItem>();
			List<SupplierVO> supplierSelectList = itemService.searchSupplierList();
			for(SupplierVO vo : supplierSelectList){		
				if(adminMode == false)
		    	{
		    		if(vo.getCompanyId().equals(userSession.getCompanyId())){
		    			supplierSelectItem.add(new SelectItem(vo.getSupplierId(), vo.getSupplierName()));
		    		}
		    	}
//				else
//		    	{
//		    		supplierSelectItem.add(new SelectItem(vo.getSupplierId(), vo.getSupplierName()));
//		    	}
			}
			
			categorySelectItem = new ArrayList<SelectItem>();
			List<CategoryVO> categorySelectList = itemService.searchCategoryList();
			
			for(CategoryVO vo : categorySelectList){		
				if(adminMode == false)
		    	{
		    		if(vo.getCompanyId().equals(userSession.getCompanyId())){
		    			
		    			if(vo.getCategoryLevel()==2){
		    				
		    				categorySelectItem.add(new SelectItem(vo.getCategoryId(), vo.getCategoryName()));
		    			}
		    		}
		    	}
//				else
//		    	{
//		    		categorySelectItem.add(new SelectItem(vo.getCategoryId(), vo.getCategoryName()));
//		    	}
			}
			
			itemNonCompositeSelectItem = new ArrayList<SelectItem>();
			List<ItemVO> itemNonCompositeSelectList = itemService
					.searchItemNonCompositeList();
			for (ItemVO vo : itemNonCompositeSelectList) {
				if (adminMode == false) {
					if (vo.getCompanyId().equals(userSession.getCompanyId())) {
						itemNonCompositeSelectItem.add(new SelectItem(vo
								.getItemId(), vo.getItemName()));
					}
				} 
//				else 
//				{
//					itemNonCompositeSelectItem.add(new SelectItem(vo.getItemId(),
//							vo.getItemName()));
//				}
			}
			
			MODE_TYPE = "ADD";
			
			disableFlag = false;
			disableFlagAdd = true;
		}
		
		
	}
    
    
   public void handleKeyEvent() {
	   itemVO.getUmId().toString();
   }
    
    public void listOnChangeByCompany(Long companyId) 
    {
    	umSelectItem = new ArrayList<SelectItem>();
		List<UmVO> umSelectList = umService.searchUmCompany(companyId);
		for(UmVO vo : umSelectList){	
			umSelectItem.add(new SelectItem(vo.getUmId(), vo.getUmName()));
		}
		
		supplierSelectItem = new ArrayList<SelectItem>();
		List<SupplierVO> supplierSelectList = supplierService.searchSupplierListByCompany(companyId);
		for(SupplierVO vo : supplierSelectList){		
			supplierSelectItem.add(new SelectItem(vo.getSupplierId(), vo.getSupplierName()));
		}
		
		categorySelectItem = new ArrayList<SelectItem>();
		List<Category> categorySelectList = categoryService.searchCategoryCompany(companyId);
		for(Category category : categorySelectList){
			if(category.getCategoryLevel()==2){
			categorySelectItem.add(new SelectItem(category.getCategoryId(), category.getCategoryName()));
		}}
		
		itemNonCompositeSelectItem = new ArrayList<SelectItem>();
		List<ItemVO> itemNonCompositeSelectList = itemService
				.searchItemNonCompositeListByCompany(companyId);
		for (ItemVO vo : itemNonCompositeSelectList) {
			itemNonCompositeSelectItem.add(new SelectItem(vo.getItemId(), vo.getItemName()));
		}
    }
    
	public void companyOnChange() {
		listOnChangeByCompany(itemVO.getCompanyId());
		
		// update drop down item non composite
//		List<ItemVO> voList = itemService
//				.searchItemNonCompositeListByCompany(itemVO.getCompanyId());
//
//		itemNonCompositeSelectItem.clear();
//		for (ItemVO vo : voList) {
//			itemNonCompositeSelectItem.add(new SelectItem(vo.getItemId(), vo
//					.getItemName()));
//		}

		// clear itemBomList
//		itemBomList.clear();
	}
    
	public boolean validasi() {
		boolean valid = true;
		if (supplierItemList.size() > 0) {
			for (int i = 0; i < supplierItemList.size(); i++) {
				// validate Supplier Item should not be empty
				if (supplierItemList.get(i).getSupplierId() == null) {
					facesUtils
							.addFacesMessage(
									"frm001:supplierItemTable",
									FacesMessage.SEVERITY_ERROR,
									null,
									facesUtils
											.getResourceBundleStringValue("textRow")
											+ " "
											+ (i + 1)
											+ " "
											+ facesUtils
													.getResourceBundleStringValue("formSupplierItemTitle")
											+ " "
											+ facesUtils
													.getResourceBundleStringValue("errorMustBeFilled"));

					valid = false;
				}

				// validate should not duplicate
				if (valid) {
					for (int j = i; j > 0; j--) {
						if (supplierItemList
								.get(i)
								.getSupplierId()
								.equals(supplierItemList.get(j - 1)
										.getSupplierId())) {
							valid = false;
							facesUtils
									.addFacesMessage(
											"frm001:supplierItemTable",
											FacesMessage.SEVERITY_ERROR,
											null,
											facesUtils
													.getResourceBundleStringValue("textRow")
													+ " "
													+ (i + 1)
													+ " "
													+ supplierService
															.findById(
																	supplierItemList
																			.get(i)
																			.getSupplierId())
															.getSupplierName()
													+ " "
													+ facesUtils
															.getResourceBundleStringValue("errorDuplicateRow")
													+ " " + (j));
						}
					}
				}
			}
		}

		// validate ItemBom [start]
		if (CommonConstants.Y.equals(itemVO.getCompositeFlag())
				&& (itemBomList == null || itemBomList.isEmpty())) {
			valid = false;
			facesUtils
					.addFacesMessage(
							"frm001:itemBomTable",
							FacesMessage.SEVERITY_ERROR,
							null,
							facesUtils
									.getResourceBundleStringValue("formItemErrMsgMinimItemBom"));
		} else if (itemBomList != null && !itemBomList.isEmpty()) {
			for (int i = 0; i < itemBomList.size(); i++) {
				ItemBomVO vo = itemBomList.get(i);
				// validate Item should not be empty
				if (vo.getItemCompositionId() == null) {
					facesUtils
							.addFacesMessage(
									"frm001:itemBomTable",
									FacesMessage.SEVERITY_ERROR,
									null,
									facesUtils
											.getResourceBundleStringValue("textRow")
											+ " "
											+ (i + 1)
											+ " "
											+ facesUtils
													.getResourceBundleStringValue("formItemBom")
											+ " "
											+ facesUtils
													.getResourceBundleStringValue("errorMustBeFilled"));

					valid = false;
				}

				// validate Item Qty should not be empty
				if (vo.getItemQty() == null || vo.getItemQty() == 0) {
					facesUtils
							.addFacesMessage(
									"frm001:itemBomTable",
									FacesMessage.SEVERITY_ERROR,
									null,
									facesUtils
											.getResourceBundleStringValue("textRow")
											+ " "
											+ (i + 1)
											+ " "
											+ facesUtils
													.getResourceBundleStringValue("formItemQty")
											+ " "
											+ facesUtils
													.getResourceBundleStringValue("errorMustBeFilled"));

					valid = false;
				}

				// validate should not duplicate
				if (valid) {
					for (int j = i + 1; j < itemBomList.size(); j++) {
						ItemBomVO vo2 = itemBomList.get(j);
						if (vo.getItemCompositionId().equals(
								vo2.getItemCompositionId())) {

							facesUtils
									.addFacesMessage(
											"frm001:itemBomTable",
											FacesMessage.SEVERITY_ERROR,
											null,
											facesUtils
													.getResourceBundleStringValue("textRow")
													+ " "
													+ (i + 1)
													+ " "
													+ vo.getItemCompositionName()
													+ " "
													+ facesUtils
															.getResourceBundleStringValue("errorDuplicateRow")
													+ " " + (j + 1));

							valid = false;
						}
					}
				}
			}
		}
		// validate ItemBom [end]

		return valid;
	}
	
	public boolean validateCompany(){
		boolean valid = true;
		if (adminMode == true) {
			if (itemVO.getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formItemCompany") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}else{
				valid = true;
			}
		}else{
			valid = true;
		}
		return valid;
	}
	
	
	public boolean validateItem(){
		boolean valid = true;
		
		try {
			if (itemVO.getItemCode().trim().isEmpty() || itemVO.getItemCode() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formItemCode") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} if (itemVO.getItemName().trim().isEmpty() || itemVO.getItemName() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formItemName") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (itemVO.getItemDesc().trim().isEmpty() || itemVO.getItemDesc() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formItemDesc") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (itemVO.getUmId() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formProductUm") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (itemVO.getUnitPerUm2() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formProductUm") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (itemVO.getUm2Id() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formProductUm") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (itemVO.getCategoryId() == null) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formItemCategory") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (itemVO.getUnitCost() == null && sellChecked==false) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formProductUnitCost") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (itemVO.getUnitPrice() == null && sellChecked==false) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formProductUnitPrice") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getLaunchingDt() == null && sellChecked==true && MODE_TYPE.equals("ADD")) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formProductLaunchingDate") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getUnitCost() == null && sellChecked==true && MODE_TYPE.equals("ADD")) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
						facesUtils.getResourceBundleStringValue("formProductUnitCost") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (productVO.getUnitPrice() == null && sellChecked==true && MODE_TYPE.equals("ADD")) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesItem",
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
	
    public void save(){
    	try 
    	{
	    	productVO.setSellProduct(sellChecked);     
	    	
	    	if(userSession.getCompanyId() !=null)
	    	{
	    		itemVO.setCompanyId(userSession.getCompanyId());
	    	}
	        	
	    	if(MODE_TYPE.equals("ADD"))
	    	{
	    	    itemService.save(itemVO, supplierItemList, productVO, productBomVO, itemBomList,itemDiscountList, userSession.getUserCode());
	    	    
	    	    facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
	    	}
	    	else if(MODE_TYPE.equals("EDIT"))
	    	{
	    		itemService.update(itemVO, supplierItemList, productVO, productBomVO, itemBomList,itemDiscountList, userSession.getUserCode());
	    		
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
		itemVO = new ItemVO();
		itemVO.setCompositeFlag(CommonConstants.N);
		supplierItemList = new ArrayList<SupplierItemVO>();
		itemBomList = new ArrayList<ItemBomVO>();
		
		List<ParameterDtl> list = parameterService.parameterDtlList(CommonConstants.CUSTOMER_TYPE);
		
		itemDiscountList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			ParameterDtl parameterDtl = list.get(i);
			ItemDiscountVO vo = new ItemDiscountVO();
			vo.setParameterDtl(parameterDtl);
			itemDiscountList.add(vo);
		}
		
	}
    
    public void modeEdit(List<ItemVO> itemList){
		MODE_TYPE = "EDIT";
		
		for(int i=0; i<itemList.size(); i++){
						
			ItemVO itemVOTemp = (ItemVO)itemList.get(i);
			
			itemVO.setItemId(itemVOTemp.getItemId());
			itemVO.setCompanyId(itemVOTemp.getCompanyId());
			itemVO.setCompanyName(itemVOTemp.getCompanyName());
			itemVO.setItemCode(itemVOTemp.getItemCode());
			itemVO.setItemName(itemVOTemp.getItemName());
			itemVO.setItemDesc(itemVOTemp.getItemDesc());
			itemVO.setUmId(itemVOTemp.getUmId());
			itemVO.setUmName(itemVOTemp.getUmName());
			itemVO.setUm2Id(itemVOTemp.getUm2Id());
			itemVO.setUm2Name(itemVOTemp.getUm2Name());
	
			itemVO.setUnitPerUm2(itemVOTemp.getUnitPerUm2());
			itemVO.setUnitCost(itemVOTemp.getUnitCost());
			itemVO.setUnitPrice(itemVOTemp.getUnitPrice());
			itemVO.setImageFile(itemVOTemp.getImageFile());
			itemVO.setImageFilename(itemVOTemp.getImageFilename());
			itemVO.setCategoryId(itemVOTemp.getCategoryId());
			itemVO.setCategoryName(itemVOTemp.getCategoryName());
			itemVO.setActiveFlag(itemVOTemp.getActiveFlag());
			itemVO.setBarcode(itemVOTemp.getBarcode());
			
			if (itemVOTemp.getCompositeFlag() != null
					&& CommonConstants.Y.equals(itemVOTemp.getCompositeFlag())) {
				itemVO.setCompositeFlag(CommonConstants.Y);
			} else {
				itemVO.setCompositeFlag(CommonConstants.N);
			}
			
			companyOnChange();
			
			if(itemVOTemp.getImageFile() !=null){
				String uploadId = mbImageStreamer.returnCurrentTime();
				this.streamUploadId = uploadId;
				mbImageStreamer.clearUpload(getUploadFuncId());
				UploadFileVO file = new UploadFileVO(uploadId,itemVOTemp.getImageFile());
				mbImageStreamer.addUpload(getUploadFuncId(), file);
			}
			
			List<ProductBomVO> productBomListTemp = productBomService.findBomByItemId(itemVO.getItemId());
			if(productBomListTemp != null && !productBomListTemp.isEmpty()){
				sellChecked = true;
				for(int j=0; j<productBomListTemp.size();j++){
					ProductBomVO productBomTemp = productBomListTemp.get(j);
					ProductBomVO vo = new ProductBomVO();
					
					vo.setBomId(productBomTemp.getBomId());
					vo.setProductId(productBomTemp.getProductId());
					vo.setItemId(productBomTemp.getItemId());
					vo.setItemQty(productBomTemp.getItemQty());
					vo.setActiveFlag(productBomTemp.getActiveFlag());				
					productBomList.add(vo);
				}
			
				for(int j=0; j<productBomList.size();j++){
					Product prodTemp = productService.findById(productBomList.get(j).getProductId());
					ProductVO prodVO = new ProductVO();
					prodVO.setProductId(prodTemp.getProductId());
					prodVO.setCompanyId(prodTemp.getCompanyId());
					prodVO.setProductCode(prodTemp.getProductCode());
					prodVO.setProductName(prodTemp.getProductName());
					prodVO.setProductDesc(prodTemp.getProductDesc());
					prodVO.setImageFile(prodTemp.getImageFile());
					prodVO.setImageFilename(prodTemp.getImageFilename());
					prodVO.setCategoryId(prodTemp.getCategoryId());
					prodVO.setLaunchingDt(prodTemp.getLaunchingDt());
					prodVO.setUnitCost(prodTemp.getUnitCost());
					prodVO.setUnitPrice(prodTemp.getUnitPrice());
					prodVO.setUnitPerUm2(prodTemp.getUnitPerUm2());

					prodVO.setActiveFlag("Y");
					productList.add(prodVO);
				}
			}	
			

		
		}
		
		Item item = itemService.findById(itemVO.getItemId());
		itemDiscountList= new ArrayList<>();
		for(int i=0;i<item.getListItemDiscount().size();i++){
			ItemDiscount itemDiscount = item.getListItemDiscount().get(i);
			ItemDiscountVO vo = new ItemDiscountVO();
			vo.setItemDiscountId(itemDiscount.getItemDiscountId());
			vo.setDiscount1(itemDiscount.getDiscount1());
			vo.setDiscount2(itemDiscount.getDiscount2());
			vo.setDiscount3(itemDiscount.getDiscount3());
			vo.setParameterDtl(itemDiscount.getCustomerType());
			itemDiscountList.add(vo);
			
		}
		
		supplierItemList = new ArrayList<SupplierItemVO>();
		List<SupplierItem> suppItemList = itemService.searchSupplierItemList(itemVO.getItemId());
		for(int j=0; j<suppItemList.size();j++){
			SupplierItem supplierItem = (SupplierItem) suppItemList.get(j);
			
			SupplierItemVO sup = new SupplierItemVO();
			
			sup.setSupplierItemId(supplierItem.getSupplierItemId());
			sup.setSupplierId(supplierItem.getSupplier().getSupplierId());
			sup.setItemId(supplierItem.getItem().getItemId());
			sup.setActiveFlag(supplierItem.getActiveFlag());
			
			supplierItemList.add(sup);
		}
		
		itemBomList = itemService.searchItemBomList(itemVO.getItemId());
	}
    
    public void reinit() {
    	
		supplierItemVO = new SupplierItemVO();
		supplierItemList.add(supplierItemVO);		
    }
    public void deleteDetail(SupplierItemVO supplierItemVO)
    {
    	supplierItemIdList.add(supplierItemVO.getSupplierItemId());
    }
	
	public void clearAll(){
		itemVO = new ItemVO();
		productVO = new ProductVO();
		productBomVO = new ProductBomVO();
		//itemVO.setListSupplierItem(new ArrayList<SupplierItemVO>());
		supplierItemList = new ArrayList<SupplierItemVO>();
		productBomList = new ArrayList<ProductBomVO>();
		productList = new ArrayList<ProductVO>();
		sellChecked = false;
	}
	
	public void addItemBom() {
		ItemBomVO itemBomVO = new ItemBomVO();
		itemBomList.add(itemBomVO);		
	}
	
	public void delItemBom(ItemBomVO itemBomVO) {
//		itemBomList.remove(itemBomVO);
//		itemBomIdDeletedList.add(itemBomVO.getBomId());
	}
	
	public void onChangeItemBomItem(ItemBomVO itemBomVO) {
		if (itemBomVO.getItemCompositionId() != null) {
			Item item = itemService.findById(itemBomVO.getItemCompositionId());
			if (item != null) {
				itemBomVO.setItemCompositionName(item.getItemName());
			}
		}
	}

	public void onChangeCompositeFlag() {
		if (itemVO != null) {
			if (CommonConstants.Y.equals(itemVO.getCompositeFlag())) {
				// do nothing
			} else {
				itemBomList = new ArrayList<ItemBomVO>();
			}
		}
	}
	
	public void onChangeuM(){
		
		Um um = (Um)umService.findById(itemVO.getUmId());
		if(um!=null){
			itemVO.setUmName(um.getUmName());
		}
	}
	
	public void handleUploadedFile(ActionEvent event) throws IOException {
	//	byte[] content = IOUtils.toByteArray(uploadedFile.getInputstream());
		if (checkUploadFileValid(uploadedFile)) {
			//itemVO.setImageFile(uploadedFile.getContents());
			
			itemVO.setImageFile(IOUtils.toByteArray(uploadedFile.getInputstream()));
			itemVO.setImageFilename(uploadedFile.getFileName());
			System.out.println("isi itemVO imagefile = " + itemVO.getImageFile());
			
			String uploadId = mbImageStreamer.returnCurrentTime();
			this.streamUploadId = uploadId;
			mbImageStreamer.clearUpload(getUploadFuncId());
			mbImageStreamer.addUpload(getUploadFuncId(), uploadId, uploadedFile);
		}
		
	}
	
	/**
	 * 
	 * @param fileUpload
	 * @return
	 * untuk check upload file valid
	 * @throws IOException 
	 * 
	 */
	// expect uploadedFile exist, not null. if null, show err message
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
	
	/**
	 * 
	 * @return
	 * query untuk upload extension allowed
	 * 
	 */
	private String[] queryUploadExtensionAllowed() {
		/*List <Ahmipb2bDtlsettings> listQueryRes = 
				ahmipb2bDtlsettingsService.getSettingByRsetVid(
						Constants.SETTING_RSETVID_UPLOAD_EXT);*/
		List <ParameterDtl> listQueryRes = 
				itemService.parameterDtlList(
						CommonConstants.SETTING_ITEM_UPLOAD_EXT);
		String res[] = new String[listQueryRes.size()];

		for (int i = 0 ; i < res.length ; i++) {
			res[i] = listQueryRes.get(i).getParameterDtlName();
		}
		return res;
	}
	
	/**
	 * 
	 * @param fileName
	 * @param extAllowed
	 * @return
	 * validasi file upload
	 * 
	 */
	private boolean fileUploadValid(String fileName, String [] extAllowed) {
		for (String ext : extAllowed) {
			if (StringUtils.endsWithIgnoreCase(fileName, "." + ext)) {
				return true;
			}			
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 * query untuk check max size upload
	 * 
	 */
	private Long queryUploadMaxSizeAllowed() {
		/*List <Ahmipb2bDtlsettings> listQueryRes = 
				ahmipb2bDtlsettingsService.getSettingByRsetVid(
						Constants.SETTING_RSETVID_UPLOAD_SIZE);*/
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
	
	/**
	 * @param file
	 * @param maxSize
	 * @return
	 * check size file upload valid
	 * @throws IOException 
	 * 
	 */
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

	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public UmService getUmService() {
		return umService;
	}

	public void setUmService(UmService umService) {
		this.umService = umService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public ItemVO getItemVO() {
		return itemVO;
	}

	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}

	public List<SupplierItemVO> getSelectedSupplierItems() {
		return selectedSupplierItems;
	}

	public void setSelectedSupplierItems(List<SupplierItemVO> selectedSupplierItems) {
		this.selectedSupplierItems = selectedSupplierItems;
	}

	public List<SupplierItemVO> getSupplierItemList() {
		return supplierItemList;
	}

	public void setSupplierItemList(List<SupplierItemVO> supplierItemList) {
		this.supplierItemList = supplierItemList;
	}

	public SupplierItemVO getSupplierItemVO() {
		return supplierItemVO;
	}

	public void setSupplierItemVO(SupplierItemVO supplierItemVO) {
		this.supplierItemVO = supplierItemVO;
	}

	public ProductVO getProductVO() {
		return productVO;
	}

	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}

	public ProductBomVO getProductBomVO() {
		return productBomVO;
	}

	public void setProductBomVO(ProductBomVO productBomVO) {
		this.productBomVO = productBomVO;
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

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

	public boolean isSellChecked() {
		return sellChecked;
	}

	public void setSellChecked(boolean sellChecked) {
		this.sellChecked = sellChecked;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getUmSelectItem() {
		return umSelectItem;
	}

	public void setUmSelectItem(List<SelectItem> umSelectItem) {
		this.umSelectItem = umSelectItem;
	}

	public List<SelectItem> getSupplierSelectItem() {
		return supplierSelectItem;
	}

	public void setSupplierSelectItem(List<SelectItem> supplierSelectItem) {
		this.supplierSelectItem = supplierSelectItem;
	}

	public List<SelectItem> getCategorySelectItem() {
		return categorySelectItem;
	}

	public void setCategorySelectItem(List<SelectItem> categorySelectItem) {
		this.categorySelectItem = categorySelectItem;
	}

	public List<SelectItem> getActiveSelectItem() {
		return activeSelectItem;
	}

	public void setActiveSelectItem(List<SelectItem> activeSelectItem) {
		this.activeSelectItem = activeSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public ProductBomService getProductBomService() {
		return productBomService;
	}

	public void setProductBomService(ProductBomService productBomService) {
		this.productBomService = productBomService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public List<Long> getSupplierItemIdList() {
		return supplierItemIdList;
	}

	public void setSupplierItemIdList(List<Long> supplierItemIdList) {
		this.supplierItemIdList = supplierItemIdList;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
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

	public MbImageStreamer getMbImageStreamer() {
		return mbImageStreamer;
	}

	public void setMbImageStreamer(MbImageStreamer mbImageStreamer) {
		this.mbImageStreamer = mbImageStreamer;
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

	public List<ProductBomVO> getProductBomList() {
		return productBomList;
	}

	public void setProductBomList(List<ProductBomVO> productBomList) {
		this.productBomList = productBomList;
	}

	public List<ProductVO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductVO> productList) {
		this.productList = productList;
	}

	public List<SelectItem> getItemNonCompositeSelectItem() {
		return itemNonCompositeSelectItem;
	}

	public void setItemNonCompositeSelectItem(
			List<SelectItem> itemNonCompositeSelectItem) {
		this.itemNonCompositeSelectItem = itemNonCompositeSelectItem;
	}

	public List<ItemBomVO> getItemBomList() {
		return itemBomList;
	}

	public void setItemBomList(List<ItemBomVO> itemBomList) {
		this.itemBomList = itemBomList;
	}

	public List<SelectItem> getCompositeSelectItem() {
		return compositeSelectItem;
	}

	public void setCompositeSelectItem(List<SelectItem> compositeSelectItem) {
		this.compositeSelectItem = compositeSelectItem;
	}

	public boolean isShowItemBomList() {
		return itemVO != null
				&& CommonConstants.Y.equals(itemVO.getCompositeFlag());
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PagingTableModel<ParameterDtlVO> getPagingParameterDtl() {
		return pagingParameterDtl;
	}

	public void setPagingParameterDtl(PagingTableModel<ParameterDtlVO> pagingParameterDtl) {
		this.pagingParameterDtl = pagingParameterDtl;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public List<ParameterDtlVO> getListParameterDtl() {
		return listParameterDtl;
	}

	public void setListParameterDtl(List<ParameterDtlVO> listParameterDtl) {
		this.listParameterDtl = listParameterDtl;
	}
	
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


	public List<ItemDiscountVO> getItemDiscountList() {
		return itemDiscountList;
	}


	public void setItemDiscountList(List<ItemDiscountVO> itemDiscountList) {
		this.itemDiscountList = itemDiscountList;
	}


	public ItemDiscountService getItemDiscountService() {
		return itemDiscountService;
	}


	public void setItemDiscountService(ItemDiscountService itemDiscountService) {
		this.itemDiscountService = itemDiscountService;
	}
    
	
	
//	public List<Long> getItemBomIdDeletedList() {
//		return itemBomIdDeletedList;
//	}
//
//	public void setItemBomIdDeletedList(List<Long> itemBomIdDeletedList) {
//		this.itemBomIdDeletedList = itemBomIdDeletedList;
//	}
}
