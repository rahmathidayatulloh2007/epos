package com.wo.epos.module.inv.transferItem.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.context.RequestContext;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.lov.bean.SelectorListener;
import com.wo.epos.common.lov.bean.SelectorModel.SelectorInfo;
import com.wo.epos.common.lov.util.StringUtils;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.service.ItemService;
import com.wo.epos.module.inv.transferItem.model.TransferItem;
import com.wo.epos.module.inv.transferItem.model.TransferItemDtl;
import com.wo.epos.module.inv.transferItem.service.TransferItemService;
import com.wo.epos.module.inv.transferItem.vo.TransferItemVO;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "transferItemInputBean")
@ViewScoped
public class TransferItemInputBean extends CommonBean implements  Serializable,
SelectorListener<Object>{
	
	private static final long serialVersionUID = -6329779815150150732L;
	static Logger logger = Logger.getLogger(TransferItemInputBean.class);
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{itemService}")
	private ItemService itemService;
		
	@ManagedProperty(value = "#{transferItemService}")
	private TransferItemService transferItemService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	
	private TransferItemVO transferItemVO;
	
	private TransferItemVO transferItemOri;
				
	private List<SelectItem> doTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> soIdSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> transferFromSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> transferToSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> deliveryTypeSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
    private TransferItemDtl transferItemDtl;
    
    private SelectorInfo selectorItem;
    
    private Integer selectedIndex;
    
    private Boolean flag;
    
    private Long companyId;
    
    private Long outletId;
    
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			HttpSession session = request.getSession(true);

			UserVO userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);

			if (userVO != null) {
				companyId = userVO.getCompanyId();
				outletId = userVO.getOutletId();
			}

			selectorItem = buildSelectorInfoItem();

			transferItemVO = new TransferItemVO();
			transferItemVO.setDoDate(new Date());

			/*
			 * doTypeSelectItem = new ArrayList<SelectItem>();
			 * doTypeSelectItem.add(new SelectItem("1", "Pesanan Pelanggan"));
			 * doTypeSelectItem.add(new SelectItem("2", "Perpindahan Barang"));
			 */

			soIdSelectItem = new ArrayList<SelectItem>();

			List<OutletVO> outletSelectList = outletService.searchOutletList();
			outletSelectItem = new ArrayList<SelectItem>();
			for (OutletVO com : outletSelectList) {
				outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
			}

			List<CompanyVO> companySelectList = outletService.searchCompanyList();
			companySelectItem = new ArrayList<SelectItem>();
			for (CompanyVO com : companySelectList) {
				companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
			}

			deliveryTypeSelectItem = new ArrayList<SelectItem>();

			List<ParameterDtl> deliveryTypeList = parameterService.parameterDtlList("DO_TYPE");
			deliveryTypeSelectItem = new ArrayList<SelectItem>();
			for (ParameterDtl com : deliveryTypeList) {
				deliveryTypeSelectItem.add(new SelectItem(com.getParameterDtlCode(), com.getParameterDtlName()));
			}

			/*
			 * List<Outlet> transferFromSelectList =
			 * outletService.findOutletByCompany(transferItemVO.getCompanyId());
			 * transferFromSelectItem = new ArrayList<SelectItem>(); for(Outlet
			 * com : transferFromSelectList){ transferFromSelectItem.add(new
			 * SelectItem(com.getOutletId(), com.getOutletName())); }
			 * 
			 * List<Outlet> transferToSelectList =
			 * outletService.findOutletByCompany(transferItemVO.getCompanyId());
			 * transferToSelectItem = new ArrayList<SelectItem>(); for(Outlet
			 * com : transferToSelectList){
			 * if(transferItemVO.getOutlet().getOutletId() !=
			 * com.getOutletId()){ transferToSelectItem.add(new
			 * SelectItem(com.getOutletId(), com.getOutletName())); } }
			 */

			MODE_TYPE = "ADD";
		}
	}
	
	
	
	public void updateSelectorItem(){
		
		selectorItem.setDbQuery(
		"	select ITEM_ID, ITEM_CODE, ITEM_NAME FROM POS_ITEM 	WHERE COMPANY_ID = '"+transferItemVO.getOutlet().getCompany().getCompanyId()+"' ");
		selectorItem.setDbQueryCount(
				"	SELECT count(1)	 FROM POS_ITEM	WHERE COMPANY_ID = '"+transferItemVO.getOutlet().getCompany().getCompanyId()+"' ");


	}
	
	public static SelectorInfo buildSelectorInfoItem() {
		
		
		SelectorInfo selectorInfoDlr = new SelectorInfo(
				" select ITEM_ID,ITEM_CODE, ITEM_NAME FROM POS_ITEM ",
				"	SELECT count(1)	FROM POS_ITEM	",
								Arrays.asList("Item Id","Item Code", "Item Name"),
								Arrays.asList("0","1","2"),
								false);
		return selectorInfoDlr;
	}
	
	public void addRow() {
		transferItemDtl = new TransferItemDtl();
		
    }
	
	public void addRow2() {
		transferItemDtl = new TransferItemDtl();
		Item item = new Item();
		Um um = new Um();
		item.setUm(um);
		transferItemDtl.setItem(item);
		
		if(transferItemVO.getListTIDtl()!=null){
			transferItemVO.getListTIDtl().add(transferItemDtl);
		}else{
			List list = new ArrayList();
			list.add(transferItemDtl);
			transferItemVO.setListTIDtl(list);
		}
		
		transferItemDtl.setCreateBy(userSession.getUserCode());
		transferItemDtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
    }
			
	public Boolean save(){
		
		Boolean flag=false;
		if(MODE_TYPE.equals("ADD")){
			if(!validate()){
		    transferItemService.save(transferItemVO, userSession.getUserCode());
			}else{
				flag = true;
			}
		    //transferItemService.save(transferItemVO, "admin");
		}else if(MODE_TYPE.equals("EDIT")){
			if(!validate()){
			transferItemService.update(transferItemVO, userSession.getUserCode(),transferItemOri);
			}else{
				flag = true;
			}
			//transferItemService.update(transferItemVO, "admin");
		}
		
		return flag;
	}
	
	public void onChangeDoType(){
		System.out.println("dotype=="+transferItemVO.getDoType());
		if(transferItemVO.getDoType()!=null && transferItemVO.getDoType().equals("DOTYPE_TRANSFERITEM")){
			flag= false;
		}else{
			flag= true;
		}
	}
	
	public void onChangeCompany(){
		List<Outlet> transferFromSelectList = outletService.findOutletByCompany(getCompanyId());
		outletSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : transferFromSelectList){
	    	outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    }
		
	}
	
	public Boolean validate(){
		Boolean result = false;
		if(getCompanyId() == null){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:companyId", 
	                facesUtils.getResourceBundleStringValue("formTransferCompany")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		if(transferItemVO.getOutlet().getOutletId() == null || transferItemVO.getOutlet().getOutletId()==0){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:companyId", 
	                facesUtils.getResourceBundleStringValue("formTransferCompany")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		if(transferItemVO.getDoDate() == null){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:transferItemDoDate", 
	                facesUtils.getResourceBundleStringValue("formTransferItemDoDate")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		if(transferItemVO.getDoType() == null || transferItemVO.getDoType().isEmpty()|| transferItemVO.getDoType().equals("0")){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:doType", 
	                facesUtils.getResourceBundleStringValue("formTransferItemDoType")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		if(transferItemVO.getTransferTo().getOutletId() == null){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:transferTo", 
	                facesUtils.getResourceBundleStringValue("formTransferItemTransferTo")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		if(transferItemVO.getNotes() == null || transferItemVO.getNotes().isEmpty()){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:transferItemNotes", 
	                facesUtils.getResourceBundleStringValue("formTransferItemNotes")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		if(transferItemVO.getListTIDtl() == null || transferItemVO.getListTIDtl().size() == 0){
			addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frm001:listDoDtl", 
	                facesUtils.getResourceBundleStringValue("formTransferItemCd")+" "+facesUtils.getResourceBundleStringValue("errorMustBeFilled"), 
	                null);
			result = true;
		}
		return result;
	}
	
	public void onChangeOutlet(){
		System.out.println("dotype=="+transferItemVO.getOutlet().getOutletId());
		
		Outlet outlet = outletService.findById(transferItemVO.getOutlet().getOutletId());
		System.out.println(outlet.getOutletName());
		System.out.println(outlet.getCompany().getCompanyId());
		
		transferItemVO.setTransferFrom(outlet);
		
		List<Outlet> transferFromSelectList = outletService.findOutletByCompany(outlet.getCompany().getCompanyId());
	    transferFromSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : transferFromSelectList){
	    	transferFromSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    }
	    
	    List<Outlet> transferToSelectList = outletService.findOutletByCompany(outlet.getCompany().getCompanyId());
	    transferToSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : transferToSelectList){
	    	if(transferItemVO.getOutlet().getOutletId().doubleValue() != com.getOutletId().doubleValue()){
	    	transferToSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    	}
	    }
	}
	
	public void modeAdd(){
		transferItemVO = new TransferItemVO();
		Outlet outlet = new Outlet();
		transferItemVO.setOutlet(outlet);
		Outlet outlet1 = new Outlet();
		transferItemVO.setTransferFrom(outlet1);
		Outlet outlet2 = new Outlet();
		transferItemVO.setTransferTo(outlet2);
		transferItemVO.setStatus("DO_NEW");
		
		if(outletId != null){
			
			transferItemVO.setOutlet(outletService.findById(outletId));
			transferItemVO.setTransferFrom(transferItemVO.getOutlet());
			transferItemVO.setDoNo(getSoNumber());
			transferItemVO.setCompanyId(transferItemVO.getOutlet().getCompany().getCompanyId());
			setCompanyId(transferItemVO.getOutlet().getCompany().getCompanyId());
			
			List<Outlet> transferFromSelectList = outletService.findOutletByCompany(transferItemVO.getOutlet().getCompany().getCompanyId());
		    transferFromSelectItem = new ArrayList<SelectItem>();
		    for(Outlet com : transferFromSelectList){
		    	transferFromSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
		    	System.out.println("com.getOutletName()=="+com.getOutletName());
		    }
		    
		    List<Outlet> transferToSelectList = outletService.findOutletByCompany(transferItemVO.getOutlet().getCompany().getCompanyId());
		    transferToSelectItem = new ArrayList<SelectItem>();
		    for(Outlet com : transferToSelectList){
		    	if(transferItemVO.getOutlet().getOutletId().doubleValue() != com.getOutletId().doubleValue()){
		    	transferToSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
		    	System.out.println("com.getOutletName()=="+com.getOutletName());
		    	}
		    }
		}
		
	}
	
	public String getSoNumber(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String result = "";
		TransferItem  transferItem = transferItemService.getLastDoId();
		Boolean isMonth = false;
		if(transferItem!=null && transferItem.getDoNo()!=null){
			if(transferItem.getDoNo().substring(0, 6).equals(sdf.format(new Date()))){
				isMonth = true;
			}
		}
		Long lastDoNo = new Long(0);
		if(isMonth){
		 lastDoNo = transferItem!=null && transferItem.getDoNo()!=null ? new Long(Integer.parseInt(transferItem.getDoNo().substring(7))): 0;
		}
		lastDoNo ++;
		
		if(lastDoNo <10){
			result = "000"+lastDoNo;
		}else if(lastDoNo <100){
			result = "00"+lastDoNo;
		}else if(lastDoNo <1000){
			result = "0"+lastDoNo;
		}else if(lastDoNo <1000){
			result = ""+lastDoNo;
		}
		result = sdf.format(new Date()) +"-"+ result;
		return result;
	}
		
	public void modeEdit(List<TransferItemVO> transferItemList){
		MODE_TYPE = "EDIT";
		
		for(int i=0; i<transferItemList.size(); i++){
			  TransferItemVO transferItemVOTemp = (TransferItemVO)transferItemList.get(i);
			  
			  //TransferItem transferItemVOTemp = transferItemService.findById(vo.getDoId());
			  transferItemOri = new TransferItemVO();
			  transferItemOri.setDoId(transferItemVOTemp.getDoId());
			  transferItemOri.setActiveFlag(transferItemVOTemp.getActiveFlag());
			  transferItemOri.setDoDate(transferItemVOTemp.getDoDate());
			  transferItemOri.setDoNo(transferItemVOTemp.getDoNo());
			  transferItemOri.setDoType(transferItemVOTemp.getDoType());
			  transferItemOri.setItemResume(transferItemVOTemp.getItemResume());
			  List list = new ArrayList();
			  for(int j=0;j<transferItemVOTemp.getListTIDtl().size();j++){
				  TransferItemDtl transferItemDtl = (TransferItemDtl)transferItemVOTemp.getListTIDtl().get(j);
				  TransferItemDtl transferItemDtl2 = new TransferItemDtl();
				  transferItemDtl2.setDeliveryQty(transferItemDtl.getDeliveryQty());
				  transferItemDtl2.setDeliveryUm(transferItemDtl.getDeliveryUm());
				  transferItemDtl2.setItem(transferItemDtl.getItem());
				 // transferItemDtl2.setOutlet(transferItemDtl.getOutlet());
				  transferItemDtl2.setActiveFlag(transferItemDtl.getActiveFlag());
				  transferItemDtl2.setTransferItem(transferItemDtl.getTransferItem());
				  transferItemDtl2.setDoDtlId(transferItemDtl.getDoDtlId());
				  list.add(transferItemDtl2);
			  }
			  transferItemOri.setListTIDtl(list);
			  transferItemOri.setNotes(transferItemVOTemp.getNotes());
			  transferItemOri.setOutlet(transferItemVOTemp.getOutlet());
			  transferItemOri.setSoId(transferItemVOTemp.getSoId());
			  transferItemOri.setStatus(transferItemVOTemp.getStatus());
			  transferItemOri.setTransferFrom(outletService.findById(transferItemVOTemp.getTransferFrom().getOutletId()));
			  transferItemOri.setTransferTo(outletService.findById(transferItemVOTemp.getTransferTo().getOutletId()));
			  
			  transferItemVO = new TransferItemVO();
			  transferItemVO.setDoId(transferItemVOTemp.getDoId());
			  transferItemVO.setActiveFlag(transferItemVOTemp.getActiveFlag());
			  transferItemVO.setDoDate(transferItemVOTemp.getDoDate());
			  transferItemVO.setDoNo(transferItemVOTemp.getDoNo());
			  transferItemVO.setDoType(transferItemVOTemp.getDoType());
			  transferItemVO.setItemResume(transferItemVOTemp.getItemResume());
			  System.out.println("transferItemVOTemp.getListTIDtl()=="+transferItemVOTemp.getListTIDtl().size());
			  if(transferItemVO.getListTIDtl()!=null){
				  transferItemVO.getListTIDtl().clear();
			  }
			  transferItemVO.setListTIDtl(transferItemVOTemp.getListTIDtl());
			  transferItemVO.setNotes(transferItemVOTemp.getNotes());
			  transferItemVO.setOutlet(transferItemVOTemp.getOutlet());
			  transferItemVO.setSoId(transferItemVOTemp.getSoId());
			  transferItemVO.setStatus(transferItemVOTemp.getStatus());
			  transferItemVO.setTransferFrom(transferItemVOTemp.getTransferFrom());
			  transferItemVO.setTransferTo(transferItemVOTemp.getTransferTo());
			  setCompanyId(transferItemVOTemp.getOutlet().getCompany().getCompanyId());
			  
			  System.out.println("transferItemVOTemp.getOutlet().getOutletName()=="+transferItemVOTemp.getOutlet().getOutletName());
			  System.out.println("transferItemVOTemp.getOutlet().getCompanyId()=="+transferItemVOTemp.getOutlet().getCompany().getCompanyId());
			  
			  
			  List<Outlet> transferFromSelectList = outletService.findOutletByCompany(companyId!=null?companyId:transferItemVOTemp.getOutlet().getCompany().getCompanyId());
			    transferFromSelectItem = new ArrayList<SelectItem>();
			    for(Outlet com : transferFromSelectList){
			    	transferFromSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
			    }
			    
			    List<Outlet> transferToSelectList = outletService.findOutletByCompany(companyId!=null?companyId:transferItemVOTemp.getOutlet().getCompany().getCompanyId());
			    transferToSelectItem = new ArrayList<SelectItem>();
			    for(Outlet com : transferToSelectList){
			    	if(transferItemVO.getOutlet().getOutletId().doubleValue() != com.getOutletId().doubleValue()){
			    	transferToSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
			    	}
			    }
			    
			    
			    
			
			    
		}
		
		
	}
	
	@Override
	public void itemSelected(String clientId, String widgetVar,
			Object selectedItem) {
		// TODO Auto-generated method stub
		Object[] obj = (Object[]) selectedItem;
		
		if (widgetVar.equals("selectorItem")) {
			TransferItemDtl temp = transferItemVO.getListTIDtl().get(selectedIndex);
			temp.setItem(itemService.findById(((java.math.BigDecimal)obj[0]).longValue()));
			temp.setDeliveryUm(temp.getItem().getUm());
			transferItemVO.getListTIDtl().set(selectedIndex, temp);
			updateComponent("frm001:listDoDtl:"
					+ selectedIndex + ":itemCode");
			updateComponent("frm001:listDoDtl:"
					+ selectedIndex + ":itemName");
			updateComponent("frm001:listDoDtl:"
					+ selectedIndex + ":um");
		} 
		
	}
	
	public void index(ActionEvent actionEvent) {
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("selectedIndex") != null) {
			setSelectedIndex(Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap().get("selectedIndex")));
		}
		
	}
	
	public void updateComponent(String componentId) {
		RequestContext.getCurrentInstance().update(componentId);
	}


	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		TransferItemInputBean.logger = logger;
	}

	
	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public List<SelectItem> getDoTypeSelectItem() {
		return doTypeSelectItem;
	}

	public void setDoTypeSelectItem(List<SelectItem> doTypeSelectItem) {
		this.doTypeSelectItem = doTypeSelectItem;
	}

	

	public TransferItemService getTransferItemService() {
		return transferItemService;
	}

	public void setTransferItemService(TransferItemService transferItemService) {
		this.transferItemService = transferItemService;
	}

	public TransferItemVO getTransferItemVO() {
		return transferItemVO;
	}

	public void setTransferItemVO(TransferItemVO transferItemVO) {
		this.transferItemVO = transferItemVO;
	}

	

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public TransferItemDtl getTransferItemDtl() {
		return transferItemDtl;
	}

	public void setTransferItemDtl(TransferItemDtl transferItemDtl) {
		this.transferItemDtl = transferItemDtl;
	}

	public SelectorInfo getSelectorItem() {
		return selectorItem;
	}

	public void setSelectorItem(SelectorInfo selectorItem) {
		this.selectorItem = selectorItem;
	}

	
	public Integer getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(Integer selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<SelectItem> getSoIdSelectItem() {
		return soIdSelectItem;
	}

	public void setSoIdSelectItem(List<SelectItem> soIdSelectItem) {
		this.soIdSelectItem = soIdSelectItem;
	}

	public List<SelectItem> getTransferFromSelectItem() {
		return transferFromSelectItem;
	}

	public void setTransferFromSelectItem(List<SelectItem> transferFromSelectItem) {
		this.transferFromSelectItem = transferFromSelectItem;
	}

	public List<SelectItem> getTransferToSelectItem() {
		return transferToSelectItem;
	}

	public void setTransferToSelectItem(List<SelectItem> transferToSelectItem) {
		this.transferToSelectItem = transferToSelectItem;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}

	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public List<SelectItem> getDeliveryTypeSelectItem() {
		return deliveryTypeSelectItem;
	}

	public void setDeliveryTypeSelectItem(List<SelectItem> deliveryTypeSelectItem) {
		this.deliveryTypeSelectItem = deliveryTypeSelectItem;
	}


	public Long getOutletId() {
		return outletId;
	}


	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}



	public TransferItemVO getTransferItemOri() {
		return transferItemOri;
	}



	public void setTransferItemOri(TransferItemVO transferItemOri) {
		this.transferItemOri = transferItemOri;
	}


	



	

	
	
	
	
	
	
}
