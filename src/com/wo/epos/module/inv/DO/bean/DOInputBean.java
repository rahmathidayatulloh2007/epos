package com.wo.epos.module.inv.DO.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.lov.bean.SelectorListener;
import com.wo.epos.common.lov.bean.SelectorModel.SelectorInfo;
import com.wo.epos.module.inv.DO.model.DODtl;
import com.wo.epos.module.inv.DO.service.DOService;
import com.wo.epos.module.inv.DO.vo.DOVO;
import com.wo.epos.module.inv.item.model.Item;
import com.wo.epos.module.inv.item.service.ItemService;
import com.wo.epos.module.inv.um.model.Um;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "DOInputBean")
@ViewScoped
public class DOInputBean extends CommonBean implements  Serializable,
SelectorListener<Object>{
	
	private static final long serialVersionUID = -6329779815150150732L;
	static Logger logger = Logger.getLogger(DOInputBean.class);
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{itemService}")
	private ItemService itemService;
		
	@ManagedProperty(value = "#{DOService}")
	private DOService DOService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	
	private DOVO DOVO;
	
	private DOVO DOOri;
				
	private List<SelectItem> doTypeSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> soIdSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> transferFromSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> transferToSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> deliveryTypeSelectItem = new ArrayList<SelectItem>();
	
    private String MODE_TYPE;
    
    private DODtl DODtl;
    
    private SelectorInfo selectorItem;
    
    private Integer selectedIndex;
    
    private Boolean flag;
    
    private Long companyId;
    
    private Long outletId;
    
    private String doNoMesg;
    
	@PostConstruct
	public void postConstruct() {
		super.init();
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			HttpSession session = request.getSession(true);

			UserVO userVO = (UserVO) session.getAttribute(CommonConstants.SESSION_USER);

			if (userVO != null) {
				companyId = userVO.getCompanyId();
				outletId = userVO.getOutletId();
			}

			selectorItem = buildSelectorInfoItem();

			DOVO = new DOVO();
			DOVO.setDoDate(new Date());

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
			 * outletService.findOutletByCompany(DOVO.getCompanyId());
			 * transferFromSelectItem = new ArrayList<SelectItem>(); for(Outlet
			 * com : transferFromSelectList){ transferFromSelectItem.add(new
			 * SelectItem(com.getOutletId(), com.getOutletName())); }
			 * 
			 * List<Outlet> transferToSelectList =
			 * outletService.findOutletByCompany(DOVO.getCompanyId());
			 * transferToSelectItem = new ArrayList<SelectItem>(); for(Outlet
			 * com : transferToSelectList){ if(DOVO.getOutlet().getOutletId() !=
			 * com.getOutletId()){ transferToSelectItem.add(new
			 * SelectItem(com.getOutletId(), com.getOutletName())); } }
			 */

			MODE_TYPE = "ADD";
		
	}
	
	
	
	public void updateSelectorItem(){
		
		selectorItem.setDbQuery(
		"	select ITEM_ID, ITEM_CODE, ITEM_NAME FROM POS_ITEM 	WHERE COMPANY_ID = '"+DOVO.getOutlet().getCompany().getCompanyId()+"' ");
		selectorItem.setDbQueryCount(
				"	SELECT count(1)	 FROM POS_ITEM	WHERE COMPANY_ID = '"+DOVO.getOutlet().getCompany().getCompanyId()+"' ");


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
		DODtl = new DODtl();
		
    }
	
	public void addRow2() {
		DODtl = new DODtl();
		Item item = new Item();
		Um um = new Um();
		item.setUm(um);
		DODtl.setItem(item);
		
		if(DOVO.getListDODtl()!=null){
			DOVO.getListDODtl().add(DODtl);
		}else{
			List list = new ArrayList();
			list.add(DODtl);
			DOVO.setListDODtl(list);
		}
		
		DODtl.setCreateBy(userSession.getUserCode());
		DODtl.setCreateOn(new Timestamp(System.currentTimeMillis()));
    }
			
	public Boolean save(){
		
		Boolean flag=false;
		
		try 
		{
			if(MODE_TYPE.equals("ADD"))
			{
				if(validate())
				{
					
					doNoMesg = DOService.getDoNumber();
					DOService.save(DOVO, userSession.getUserCode());
					
					facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", doNoMesg+" "+facesUtils.retrieveMessage("common_msg_saved")), 
			                null);
				}
				else
				{
					flag = true;
				}
			    //DOService.save(DOVO, "admin");
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				if(!validate())
				{
					
					DOService.update(DOVO, userSession.getUserCode(),DOOri);
					
					facesUtils.addFacesMsg(
							FacesMessage.SEVERITY_INFO, 
							"frm001:messages", 
			                facesUtils.retrieveMessage("proses_common", DOVO.getDoNo() + " " + facesUtils.retrieveMessage("common_msg_updated")), 
			                null);
				}
				else
				{
					flag = true;
				}
				//DOService.update(DOVO, "admin");
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
		
		return flag;
	}
	public boolean validateCompany() {
		boolean valid = true;
		if (companyId == null) {
			if (DOVO.getCompanyId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
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
	
/*	public boolean validateOutlet() {
		boolean valid = true;
		if (outletId == null) {
			if (DOVO.getOutlet().getOutletId() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDOOutlet") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			} 
		}
		
		return valid;
	}*/
	
	
	public boolean validate(){
		boolean valid = true;
		
			if (DOVO.getDoDate() == null) {

				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDODoDate") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;

			}  if (DOVO.getDoType() == null || DOVO.getDoType().isEmpty()|| DOVO.getDoType().equals("0")) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDODoType") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} 

		
		return valid;

	}
	
	public boolean validatePesanperpindahan(){
		boolean valid = true;
		if(!flag){
			
			if(DOVO.getTransferFrom().getOutletId() == null){
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDOTransferFrom") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if(DOVO.getTransferTo().getOutletId() == null){
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDOTransferTo") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
				
			} if (DOVO.getListDODtl() == null || DOVO.getListDODtl().size() == 0) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDOCd") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}
			
			
		}else{
			
			if(DOVO.getSoId() == null ||DOVO.getSoId().trim().isEmpty() ){
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDOSoNo") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			} if (DOVO.getListDODtl() == null || DOVO.getListDODtl().size() == 0) {
				facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, "frm001:messagesDo",
						facesUtils.getResourceBundleStringValue("formDOCd") + " "
								+ facesUtils.getResourceBundleStringValue("errorMustBeFilled"));
				valid = false;
			}
	
		}

		return valid;
	}
	
	
	
	
	public void onChangeOutlet(){
		System.out.println("dotype=="+DOVO.getOutlet().getOutletId());
		
		if(DOVO.getOutlet().getOutletId() == null){
			Outlet outlet = new Outlet();
			outlet.setOutletId(new Long(1));
			DOVO.setTransferFrom(outlet);
			transferToSelectItem.clear();
			transferToSelectItem.addAll(outletSelectItem);
		}else{
			Outlet outlet = outletService.findById(DOVO.getOutlet().getOutletId());
			//System.out.println(outlet.getOutletName());
			//System.out.println(outlet.getCompany().getCompanyId());
			
			DOVO.setTransferFrom(outlet);
			
			List<Outlet> transferFromSelectList = outletService.findOutletByCompany(outlet.getCompany().getCompanyId());
		    transferFromSelectItem = new ArrayList<SelectItem>();
		    for(Outlet com : transferFromSelectList){
		    	transferFromSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
		    }
		    
		    List<Outlet> transferToSelectList = outletService.findOutletByCompany(outlet.getCompany().getCompanyId());
		    transferToSelectItem = new ArrayList<SelectItem>();
		    for(Outlet com : transferToSelectList)
		    {
		    	if(DOVO.getOutlet().getOutletId().doubleValue() != com.getOutletId().doubleValue())
		    	{
		    		transferToSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
		    	}
		    }
		}
	}
	
	public void modeAdd(){
		DOVO = new DOVO();
		Outlet outlet = new Outlet();
		DOVO.setOutlet(outlet);
		Outlet outlet1 = new Outlet();
		DOVO.setTransferFrom(outlet1);
		Outlet outlet2 = new Outlet();
		DOVO.setTransferTo(outlet2);
		DOVO.setStatus("DO_NEW");
		
		if(companyId!=null){
		DOVO.setCompanyId(companyId);
		
		List<Outlet> outletSelectList = outletService.findOutletByCompany(DOVO.getCompanyId());
	    outletSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : outletSelectList){
	    	outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    	
	    }
	    
		}
		
		
		if(outletId != null){
			
			DOVO.setOutlet(outletService.findById(outletId));
			DOVO.setTransferFrom(DOVO.getOutlet());
			
			DOVO.setCompanyId(DOVO.getOutlet().getCompany().getCompanyId());
			setCompanyId(DOVO.getOutlet().getCompany().getCompanyId());
			
			List<Outlet> transferFromSelectList = outletService.findOutletByCompany(DOVO.getOutlet().getCompany().getCompanyId());
		    transferFromSelectItem = new ArrayList<SelectItem>();
		    for(Outlet com : transferFromSelectList){
		    	transferFromSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
		    	//System.out.println("com.getOutletName()=="+com.getOutletName());
		    }
		    
		    List<Outlet> transferToSelectList = outletService.findOutletByCompany(DOVO.getOutlet().getCompany().getCompanyId());
		    transferToSelectItem = new ArrayList<SelectItem>();
		    for(Outlet com : transferToSelectList){
		    	if(DOVO.getOutlet().getOutletId().doubleValue() != com.getOutletId().doubleValue()){
		    	transferToSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
		    	//System.out.println("com.getOutletName()=="+com.getOutletName());
		    	}
		    }
		}
		
		DOVO.setDoNo("-");
		
	}
	
	public void onChangeDoType(){
		if(DOVO.getDoType()!=null && DOVO.getDoType().equals("DOTYPE_TRANSFERITEM"))
		{
			flag = false;
		}
		else
		{
			flag = true;
		}
	}
	
	public void changeCompanyToOutlet() {
		if (DOVO.getCompanyId() != null) 
		{
			List<Outlet> outletList = outletService.findOutletByCompany(DOVO.getCompanyId());
			outletSelectItem = new ArrayList<SelectItem>();
			transferToSelectItem = new ArrayList<SelectItem>();
			if (outletList.size() > 0) 
			{
				for (Outlet out : outletList) 
				{
					outletSelectItem.add(new SelectItem(out.getOutletId(), out.getOutletName()));
					transferToSelectItem.add(new SelectItem(out.getOutletId(), out.getOutletName()));
				}
			}

		}
	}
	
	/*public void onChangeCompany(){
		List<Outlet> transferFromSelectList = outletService.findOutletByCompany(DOVO.getCompanyId());
		outletSelectItem = new ArrayList<SelectItem>();
		transferToSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : transferFromSelectList){
	    	outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    	transferToSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    }
		
	}*/
		
	public void modeEdit(List<DOVO> DOList){
		MODE_TYPE = "EDIT";
		
		for(int i=0; i<DOList.size(); i++){
			  DOVO DOVOTemp = (DOVO)DOList.get(i);
			  
			  //DO DOVOTemp = DOService.findById(vo.getDoId());
			  DOOri = new DOVO();
			  DOOri.setDoId(DOVOTemp.getDoId());
			  DOOri.setActiveFlag(DOVOTemp.getActiveFlag());
			  DOOri.setDoDate(DOVOTemp.getDoDate());
			  DOOri.setDoNo(DOVOTemp.getDoNo());
			  DOOri.setDoType(DOVOTemp.getDoType());
			  DOOri.setItemResume(DOVOTemp.getItemResume());
			  List list = new ArrayList();
			  for(int j=0;j<DOVOTemp.getListDODtl().size();j++){
				  DODtl DODtl = (DODtl)DOVOTemp.getListDODtl().get(j);
				  DODtl DODtl2 = new DODtl();
				  DODtl2.setDeliveryQty(DODtl.getDeliveryQty());
				  DODtl2.setDeliveryUm(DODtl.getDeliveryUm());
				  DODtl2.setItem(DODtl.getItem());
				 // DODtl2.setOutlet(DODtl.getOutlet());
				  DODtl2.setActiveFlag(DODtl.getActiveFlag());
				  DODtl2.setDO(DODtl.getDO());
				  DODtl2.setDoDtlId(DODtl.getDoDtlId());
				  list.add(DODtl2);
			  }
			  DOOri.setListDODtl(list);
			  DOOri.setNotes(DOVOTemp.getNotes());
			  //DOOri.setOutlet(DOVOTemp.getOutlet());
			  DOOri.setSoId(DOVOTemp.getSoId());
			  DOOri.setStatus(DOVOTemp.getStatus());
			  
			  if(DOVOTemp.getOutlet()!=null){
				  DOOri.setOutlet(outletService.findById(DOVOTemp.getOutlet().getOutletId()));
			  }else{
					  Outlet outlet = new Outlet();
					  DOOri.setOutlet(outlet);
			  }
			  if(DOVOTemp.getTransferFrom()!=null){
			  DOOri.setTransferFrom(outletService.findById(DOVOTemp.getTransferFrom().getOutletId()));
			  }else{
				  Outlet outlet = new Outlet();
				  DOOri.setTransferFrom(outlet);
			  }
			  if(DOVOTemp.getTransferTo()!=null){
				  DOOri.setTransferTo(outletService.findById(DOVOTemp.getTransferTo().getOutletId()));
			  }else{
				  Outlet outlet = new Outlet();
				  DOOri.setTransferTo(outlet);
			  }
			  
			  
			  DOVO = new DOVO();
			  DOVO.setDoId(DOVOTemp.getDoId());
			  DOVO.setActiveFlag(DOVOTemp.getActiveFlag());
			  DOVO.setDoDate(DOVOTemp.getDoDate());
			  DOVO.setDoNo(DOVOTemp.getDoNo());
			  DOVO.setDoType(DOVOTemp.getDoType());
			  DOVO.setItemResume(DOVOTemp.getItemResume());
			  System.out.println("DOVOTemp.getListDODtl()=="+DOVOTemp.getListDODtl().size());
			  if(DOVO.getListDODtl()!=null){
				  DOVO.getListDODtl().clear();
			  }
			  DOVO.setListDODtl(DOVOTemp.getListDODtl());
			  DOVO.setNotes(DOVOTemp.getNotes());
			  DOVO.setOutlet(DOVOTemp.getOutlet());
			  DOVO.setSoId(DOVOTemp.getSoId());
			  DOVO.setStatus(DOVOTemp.getStatus());
			  DOVO.setTransferFrom(DOVOTemp.getTransferFrom());
			  DOVO.setTransferTo(DOVOTemp.getTransferTo());
			  //setCompanyId(DOVOTemp.getCompanyId());
			  DOVO.setCompanyId(DOVOTemp.getCompanyId());
			  
			  if(DOVOTemp.getOutlet()!=null){
				  DOVO.setOutlet(outletService.findById(DOVOTemp.getOutlet().getOutletId()));
			  }else{
					  Outlet outlet = new Outlet();
					  DOVO.setOutlet(outlet);
			  }
			  if(DOVOTemp.getTransferFrom()!=null){
				  DOVO.setTransferFrom(outletService.findById(DOVOTemp.getTransferFrom().getOutletId()));
			  }else{
				  Outlet outlet = new Outlet();
				  DOVO.setTransferFrom(outlet);
			  }
			  if(DOVOTemp.getTransferTo()!=null){
				  DOVO.setTransferTo(outletService.findById(DOVOTemp.getTransferTo().getOutletId()));
			  }else{
				  Outlet outlet = new Outlet();
				  DOVO.setTransferTo(outlet);
			  }
			  //setCompanyId(DOVOTemp.getOutlet().getCompany().getCompanyId());
			  
			  //System.out.println("DOVOTemp.getOutlet().getOutletName()=="+DOVOTemp.getOutlet().getOutletName());
			  //System.out.println("DOVOTemp.getOutlet().getCompanyId()=="+DOVOTemp.getOutlet().getCompany().getCompanyId());
			  
			  
			  List<Outlet> transferFromSelectList = outletService.findOutletByCompany(DOVOTemp.getCompanyId()!=null?DOVOTemp.getCompanyId():DOVOTemp.getOutlet().getCompany().getCompanyId());
			    transferFromSelectItem = new ArrayList<SelectItem>();
			    for(Outlet com : transferFromSelectList){
			    	transferFromSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
			    }
			    
			    List<Outlet> transferToSelectList = outletService.findOutletByCompany(DOVOTemp.getCompanyId()!=null?DOVOTemp.getCompanyId():DOVOTemp.getOutlet().getCompany().getCompanyId());
			    transferToSelectItem = new ArrayList<SelectItem>();
			    for(Outlet com : transferToSelectList){
			    	if(DOVO.getOutlet()!=null && DOVO.getOutlet().getOutletId()!=null){
				    	if(DOVO.getOutlet().getOutletId().doubleValue() != com.getOutletId().doubleValue()){
				    	transferToSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
				    	}
			    	}else{
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
			DODtl temp = DOVO.getListDODtl().get(selectedIndex);
			temp.setItem(itemService.findById(((java.math.BigDecimal)obj[0]).longValue()));
			temp.setDeliveryUm(temp.getItem().getUm());
			DOVO.getListDODtl().set(selectedIndex, temp);
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
		DOInputBean.logger = logger;
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

	

	public DOService getDOService() {
		return DOService;
	}

	public void setDOService(DOService DOService) {
		this.DOService = DOService;
	}

	public DOVO getDOVO() {
		return DOVO;
	}

	public void setDOVO(DOVO DOVO) {
		this.DOVO = DOVO;
	}

	

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public DODtl getDODtl() {
		return DODtl;
	}

	public void setDODtl(DODtl DODtl) {
		this.DODtl = DODtl;
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



	public DOVO getDOOri() {
		return DOOri;
	}



	public void setDOOri(DOVO DOOri) {
		this.DOOri = DOOri;
	}



	public String getDoNoMesg() {
		return doNoMesg;
	}



	public void setDoNoMesg(String doNoMesg) {
		this.doNoMesg = doNoMesg;
	}

}
