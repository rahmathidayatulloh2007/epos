package com.wo.epos.module.sales.register.bean;

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
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.sales.register.service.RegisterService;
import com.wo.epos.module.sales.register.vo.RegisterVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.employee.model.Employee;
import com.wo.epos.module.uam.employee.service.EmployeeService;
import com.wo.epos.module.uam.employee.vo.EmployeeVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;
import com.wo.epos.module.uam.outletEmp.vo.OutletEmpVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "registerSearchBean")
@ViewScoped
public class RegisterSearchBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = -6359248128782696682L;

	static Logger logger = Logger.getLogger(RegisterSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{registerService}")
	private RegisterService registerService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{outletEmpService}")
	private OutletEmpService outletEmpService;
	
	@ManagedProperty(value = "#{employeeService}")
	private EmployeeService employeeService;
		
	private RegisterVO registerVOSearchDialog = new RegisterVO();	

	private PagingTableModel<RegisterVO> pagingRegister;	
	
	private List<RegisterVO> selectedRegisters;
	
	private List<CompanyVO> companySelectList = new ArrayList<CompanyVO>();
	private List<OutletVO> outletSelectList = new ArrayList<OutletVO>();
	private List<EmployeeVO> cashierVoList = new ArrayList<EmployeeVO>();
	
	private List<SelectItem> statusSelectItemList = new ArrayList<SelectItem>();

	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	private String completeCompany;
	private String completeOutlet;
	private String completeCashier;
	
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			registerVOSearchDialog = new RegisterVO();
			pagingRegister = new PagingTableModel(registerService, paging);

			companySelectList = companyService.searchCompanyList();
			outletSelectList = new ArrayList<OutletVO>();
			cashierVoList = new ArrayList<EmployeeVO>();

			statusSelectItemList = new ArrayList<SelectItem>();
			List<ParameterDtl> paramDtlList = parameterService.parameterDtlList(CommonConstants.REGISTER_STATUS);
			if (paramDtlList.size() > 0) {
				for (ParameterDtl dtl : paramDtlList) {
					statusSelectItemList.add(new SelectItem(dtl.getParameterDtlCode(), dtl.getParameterDtlName()));
				}
			}

			disableSearchAll = false;
			checkSearch = 0;
			initBean();
		}
	}
	
	public void initBean(){			
		if(userSession.getOutletId() !=null){
			 eventOutlet(userSession.getOutletId());
		}
	}
	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search(){		
		if(checkSearch == 0){	
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				if(userSession.getOutletId() !=null){
				   searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));
				}
				
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}		
			
			 disableSearchAll = false;
	         pagingRegister.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 
	
	public boolean validDelete(List<RegisterVO> registerVoList){
		boolean valid = true;
		for(int i=0; i<registerVoList.size(); i++){
			   RegisterVO regVoTemp = (RegisterVO)registerVoList.get(i);
			   
			   if(regVoTemp.getTotalPayment() != null){
					facesUtils.addFacesMessage(
       					FacesMessage.SEVERITY_ERROR,
       					"frm001:messagesTableRegister",					
       					facesUtils.getResourceBundleStringValue("textRow")+ " "+(i+1)+" "+
       					facesUtils.getResourceBundleStringValue("formRegisterMessagesDel"));
					
					valid = false;
				}
		}
		
		return valid;
		
	}

	public void modeDelete(List<RegisterVO> registerVoList)
	{
		try
		{
			for(int i=0; i<registerVoList.size(); i++){
				RegisterVO regVoTemp = (RegisterVO)registerVoList.get(i);
				registerService.delete(regVoTemp.getRegId());
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
		registerVOSearchDialog = new RegisterVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		registerVOSearchDialog = new RegisterVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		if(userSession.getOutletId() !=null){
			   searchCriteria.add(new SearchValueObject("outletLogin", userSession.getOutletId()));
		
		}
		StringBuilder builder = new StringBuilder();
		
		if(registerVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formRegisterCompany"));
			builder.append(":"+registerVOSearchDialog.getCompanyName()+", ");
			searchCriteria.add(new SearchValueObject("company", registerVOSearchDialog.getCompanyId() ));
		}	
		
		if(registerVOSearchDialog.getOutletId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formRegisterOutlet"));
			builder.append(":"+registerVOSearchDialog.getOutletName()+", ");
			searchCriteria.add(new SearchValueObject("outlet", registerVOSearchDialog.getOutletId() ));
		}
		
		if(registerVOSearchDialog.getStartDate() !=null && registerVOSearchDialog.getEndDate() !=null){
			builder.append(facesUtils.getResourceBundleStringValue("formRegisterStartTime"));
			builder.append(":"+sdf.format(registerVOSearchDialog.getStartDate()));
			builder.append(" s/d "+sdf.format(registerVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("startDate", registerVOSearchDialog.getStartDate()));
			searchCriteria.add(new SearchValueObject("endDate", registerVOSearchDialog.getEndDate()));
		}else if(registerVOSearchDialog.getStartDate() == null &&  registerVOSearchDialog.getEndDate() != null) {
			builder.append(facesUtils.getResourceBundleStringValue("formRegisterStartTime"));
			builder.append(":-");
			builder.append(" s/d "+sdf.format(registerVOSearchDialog.getEndDate())+",");
			searchCriteria.add(new SearchValueObject("endDate", registerVOSearchDialog.getEndDate()));
		}else if(registerVOSearchDialog.getStartDate() != null &&  registerVOSearchDialog.getEndDate() == null){
			builder.append(facesUtils.getResourceBundleStringValue("formRegisterStartTime"));
			builder.append(":"+sdf.format(registerVOSearchDialog.getStartDate()));
			builder.append(" s/d -"+",");
		}		
		
		if(registerVOSearchDialog.getCashierId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formRegisterCashier"));
			builder.append(":"+registerVOSearchDialog.getCashierName()+", ");
			searchCriteria.add(new SearchValueObject("outlet", registerVOSearchDialog.getCashierId()));
		}	
		
		if(registerVOSearchDialog.getStatusCode() !=null && StringUtils.isNotBlank(registerVOSearchDialog.getStatusCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formRegisterStatus"));
			builder.append(":"+(parameterService.findByDetailId(registerVOSearchDialog.getStatusCode())).getParameterDtlName()+", ");
			searchCriteria.add(new SearchValueObject("status", registerVOSearchDialog.getStatusCode()));
		}	
		
		if(!builder.toString().equals("")){
		     builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingRegister.setSearchCriteria(searchCriteria);
		
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
	
	private void eventCompany(Long companyId){
		List<Outlet> outList = outletService.findOutletByCompany(companyId);
		if(outList.size() > 0){
		    for(Outlet out : outList){
			     OutletVO outVo = new OutletVO();
			     outVo.setOutletId(out.getOutletId());
			     outVo.setOutletCode(out.getOutletCode());
			     outVo.setOutletName(out.getOutletName());			
			     
			     outletSelectList.add(outVo);
		   }
		}		
	}
	
	private void eventOutlet(Long outletId){
		List<OutletEmpVO> outEmpList = outletEmpService.findOutletEmpByOutletIdList(outletId);
	    for(OutletEmpVO outEmp : outEmpList){
	    	  EmployeeVO empVo = new EmployeeVO();
	    	  empVo.setEmployeeId(outEmp.getEmployeeId());
	    	  empVo.setEmployeeNo(outEmp.getEmployeeNo());
	    	  empVo.setFullName(outEmp.getEmployeeName());
	    	  cashierVoList.add(empVo);
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
        registerVOSearchDialog.setCompanyId(comTemp.getCompanyId());
        registerVOSearchDialog.setCompanyName(comTemp.getCompanyName());
        
        eventCompany(registerVOSearchDialog.getCompanyId());        
    }
		
	public List<String> completeOutlet(String outoCompleteText){	
		List<String> resultList = new ArrayList<String>();			
		for(OutletVO vo : outletSelectList){
			if(vo.getOutletName().toUpperCase().contains(outoCompleteText.toUpperCase())){
				  resultList.add(vo.getOutletCode()+" - "+vo.getOutletName());
			}	  
		}
		
		return resultList;		
	}	
	
	public void handleSelectOutlet(SelectEvent event) {
        Object item = event.getObject(); 
        
        String[] split = item.toString().split("-");
        Outlet outTemp = outletService.findByOutletCode(split[0].toString().trim());
        registerVOSearchDialog.setOutletId(outTemp.getOutletId());
        registerVOSearchDialog.setOutletName(outTemp.getOutletName());
        
        eventOutlet(registerVOSearchDialog.getOutletId());        
    }
	
	public List<String> completeCashier(String outoCompleteText){	
		List<String> resultList = new ArrayList<String>();			
		for(EmployeeVO vo : cashierVoList){
			if(vo.getFullName().toUpperCase().contains(outoCompleteText.toUpperCase())){
				  resultList.add(vo.getEmployeeNo()+" - "+vo.getFullName());
			}	  
		}
		
		return resultList;		
	}	
	
	public void handleSelectCashier(SelectEvent event) {
        Object item = event.getObject(); 
        
        String[] split = item.toString().split("-");
        Employee empTemp = employeeService.findByNo(split[0].toString().trim());
        registerVOSearchDialog.setCashierId(empTemp.getEmployeeId());    
        registerVOSearchDialog.setCashierName(empTemp.getFullName()); 
        
    }
	
	
	
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RegisterSearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public RegisterVO getRegisterVOSearchDialog() {
		return registerVOSearchDialog;
	}

	public void setRegisterVOSearchDialog(RegisterVO registerVOSearchDialog) {
		this.registerVOSearchDialog = registerVOSearchDialog;
	}

	public PagingTableModel<RegisterVO> getPagingRegister() {
		return pagingRegister;
	}

	public void setPagingRegister(PagingTableModel<RegisterVO> pagingRegister) {
		this.pagingRegister = pagingRegister;
	}

	public List<RegisterVO> getSelectedRegisters() {
		return selectedRegisters;
	}

	public void setSelectedRegisters(List<RegisterVO> selectedRegisters) {
		this.selectedRegisters = selectedRegisters;
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

	public String getCompleteCompany() {
		return completeCompany;
	}

	public void setCompleteCompany(String completeCompany) {
		this.completeCompany = completeCompany;
	}

	public String getCompleteOutlet() {
		return completeOutlet;
	}

	public void setCompleteOutlet(String completeOutlet) {
		this.completeOutlet = completeOutlet;
	}

	public String getCompleteCashier() {
		return completeCashier;
	}

	public void setCompleteCashier(String completeCashier) {
		this.completeCashier = completeCashier;
	}
	
	public OutletEmpService getOutletEmpService() {
		return outletEmpService;
	}

	public void setOutletEmpService(OutletEmpService outletEmpService) {
		this.outletEmpService = outletEmpService;
	}

	public List<CompanyVO> getCompanySelectList() {
		return companySelectList;
	}

	public void setCompanySelectList(List<CompanyVO> companySelectList) {
		this.companySelectList = companySelectList;
	}

	public List<OutletVO> getOutletSelectList() {
		return outletSelectList;
	}

	public void setOutletSelectList(List<OutletVO> outletSelectList) {
		this.outletSelectList = outletSelectList;
	}

	public List<EmployeeVO> getCashierVoList() {
		return cashierVoList;
	}

	public void setCashierVoList(List<EmployeeVO> cashierVoList) {
		this.cashierVoList = cashierVoList;
	}

	public List<SelectItem> getStatusSelectItemList() {
		return statusSelectItemList;
	}

	public void setStatusSelectItemList(List<SelectItem> statusSelectItemList) {
		this.statusSelectItemList = statusSelectItemList;
	}

	
}
