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

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.module.inv.item.vo.ItemVO;
import com.wo.epos.module.sales.register.service.RegisterService;
import com.wo.epos.module.sales.register.vo.RegisterDtlVO;
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

@ManagedBean(name = "registerInputBean")
@ViewScoped
public class RegisterInputBean extends CommonBean implements Serializable{
	
    private static final long serialVersionUID = -4970325761212607721L;
	static Logger logger = Logger.getLogger(RegisterInputBean.class);
	
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
	
	private RegisterVO registerVO = new RegisterVO();
	
	private List<RegisterDtlVO> registerDtlVOList = new ArrayList<RegisterDtlVO>();
	private List<CompanyVO> companySelectList = new ArrayList<CompanyVO>();
	private List<OutletVO> outletSelectList = new ArrayList<OutletVO>();
	private List<EmployeeVO> cashierVoList = new ArrayList<EmployeeVO>();
	private List<SelectItem> cashierSelectItem = new ArrayList<SelectItem>();
			
    private String MODE_TYPE;
    private String completeCompany;
    private String completeOutlet;
    private String completeCashier;
    
	@PostConstruct
	public void postConstruct() {
		super.init();
		if (userSession != null) {
			registerVO = new RegisterVO();
			companySelectList = companyService.searchCompanyList();
			registerDtlVOList = new ArrayList<RegisterDtlVO>();
			outletSelectList = new ArrayList<OutletVO>();

			MODE_TYPE = "ADD";

			if (userSession.getCompanyId() != null) {
				cashierSelectItem = new ArrayList<SelectItem>();
				List<EmployeeVO> cashierSelectList = employeeService
						.searchEmployeeListByCompany(userSession.getCompanyId());
				for (EmployeeVO employeeVO : cashierSelectList) {
					cashierSelectItem.add(new SelectItem(employeeVO.getEmployeeId(),
							employeeVO.getEmployeeNo() + " - " + employeeVO.getFullName()));
				}

			}
		}

	}
			
	public void save()
	{
		try
		{
			if(userSession.getOutletId() !=null){
				registerVO.setOutletId(userSession.getOutletId());
			}
			
			if(MODE_TYPE.equals("ADD"))
			{
				registerService.save(registerVO, registerDtlVOList, userSession.getUserCode());
				
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT"))
			{
				registerService.update(registerVO, registerDtlVOList, userSession.getUserCode());
				
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
	
	public void modeEdit(List<RegisterVO> registerList){
		MODE_TYPE = "EDIT";
		for(int i=0; i<registerList.size(); i++){
			  RegisterVO regisVo = (RegisterVO)registerList.get(i);
			  registerVO = regisVo;		
			  completeCashier = registerVO.getCashierCode()+" - "+registerVO.getCashierName();
			  completeOutlet = registerVO.getOutletCode()+" - "+registerVO.getOutletName();
			  completeCompany = registerVO.getCompanyCode()+" - "+registerVO.getCompanyName();
		}
		
		registerDtlVOList = registerService.searchRegisterDtlVoList(registerVO.getRegId());
		
	}
	
	public void modeAdd(){
		MODE_TYPE = "ADD";
		
		registerVO = new RegisterVO();
		registerDtlVOList = new ArrayList<RegisterDtlVO>();
		List<ParameterDtl> paramDtlList = parameterService.parameterDtlList(CommonConstants.PAYMENT_METHOD);
		if(paramDtlList.size() > 0){
			for(ParameterDtl dtl:paramDtlList){
			       RegisterDtlVO regDtlVo = new RegisterDtlVO();
			       regDtlVo.setPaymentMethodCode(dtl.getParameterDtlCode());
			       regDtlVo.setPaymentMethodName(dtl.getParameterDtlName());
			       regDtlVo.setInitFund(new Double(0));
			       
			       if(!dtl.getParameterDtlCode().equals(CommonConstants.PAYMENT_CASH)){
			    	   regDtlVo.setDisableInitFund(true);
			       }else{
			    	   regDtlVo.setDisableInitFund(false);
			       }
			       
			       registerDtlVOList.add(regDtlVo);
			}
		}
		
		initBean();
	}
	
	public void initBean(){			
		if(userSession.getOutletId() !=null){
			 eventOutlet(userSession.getOutletId());
		}else{
			if(userSession.getCompanyId() !=null){
			   eventCompany(userSession.getCompanyId());
			}
		}
	}
	
	private void eventCompany(Long companyId){
		outletSelectList = new ArrayList<OutletVO>();
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
		cashierVoList = new ArrayList<EmployeeVO>();
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
        registerVO.setCompanyId(comTemp.getCompanyId());
        
        eventCompany(registerVO.getCompanyId());        
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
        registerVO.setOutletId(outTemp.getOutletId());
        
        eventOutlet(registerVO.getOutletId());        
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
        registerVO.setCashierId(empTemp.getEmployeeId());
        
    }
	
	
	

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RegisterInputBean.logger = logger;
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

	public RegisterVO getRegisterVO() {
		return registerVO;
	}

	public void setRegisterVO(RegisterVO registerVO) {
		this.registerVO = registerVO;
	}

	public List<RegisterDtlVO> getRegisterDtlVOList() {
		return registerDtlVOList;
	}

	public void setRegisterDtlVOList(List<RegisterDtlVO> registerDtlVOList) {
		this.registerDtlVOList = registerDtlVOList;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
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

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public List<EmployeeVO> getCashierVoList() {
		return cashierVoList;
	}

	public void setCashierVoList(List<EmployeeVO> cashierVoList) {
		this.cashierVoList = cashierVoList;
	}

	public List<SelectItem> getCashierSelectItem() {
		return cashierSelectItem;
	}

	public void setCashierSelectItem(List<SelectItem> cashierSelectItem) {
		this.cashierSelectItem = cashierSelectItem;
	}
	
	
}
