package com.wo.epos.module.inv.DO.bean;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.inv.itemStock.service.ItemStockService;
import com.wo.epos.module.inv.DO.service.DOService;
import com.wo.epos.module.inv.DO.vo.DOVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "DOSearchBean")
@ViewScoped
public class DOSearchBean extends CommonBean implements Serializable {
		
	private static final long serialVersionUID = -5010765202025439622L;

	static Logger logger = Logger.getLogger(DOSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{DOService}")
	private DOService DOService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{itemStockService}")
	private ItemStockService itemStockService;
			
	private DOVO DOVOSearchDialog = new DOVO();	

	private PagingTableModel<DOVO> pagingDO;	
	
	private List<DOVO> selectedDOs;
		
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	
		
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;
	
	private Long companyId;
	
	private Long outletId;
	private String doNo;
	private Date startDoDate;
	private Date endDoDate;
	private String transferTo;
	private String item;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		HttpSession session = request.getSession(true);

		UserVO userVO = (UserVO) session
				.getAttribute(CommonConstants.SESSION_USER);

		if (userVO != null) {
			companyId = userVO.getCompanyId();
			outletId = userVO.getOutletId();
		}
				 
		DOVOSearchDialog = new DOVO();
		pagingDO = new PagingTableModel(DOService, paging);
		
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
	    companySelectItem = new ArrayList<SelectItem>();
	    for(CompanyVO com : companySelectList){
	    	companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
	    }
	    
	    List<OutletVO> outletSelectList = outletService.searchOutletList();
	    outletSelectItem = new ArrayList<SelectItem>();
	    for(OutletVO com : outletSelectList){
	    	outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    }
	    
	    
	    
	    disableSearchAll = false;
		
		checkSearch = 0;
		search();
	}
	
	
	
	public  void print() throws JRException {
		// JasperPrint jasperPrint = getJasperPrint();
		String selectedPrinter = null;

		PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		JasperReport jr;
		HttpServletRequest servletRequest = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
		jr = (JasperReport) JRLoader.loadObjectFromFile(servletRequest.getRealPath("/resources/report/report1.jasper"));
		Map<String, Object> param = new HashMap<String, Object>();
		Locale locale = new Locale("id", "ID");
		param.put(JRParameter.REPORT_LOCALE, locale);
		JasperPrint print = JasperFillManager.fillReport(jr,param,DOService.getConnection());
		if (printService != null) {
			selectedPrinter = printService.getName();
			try {
				printerJob.setPrintService(printService);
				boolean printSucceed =
						 JasperPrintManager.printReport(print, false);
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

		/*PrinterJob printerJob = PrinterJob.getPrinterJob();
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null,
				null);
		PrintService selectedService = null;

		if (services.length != 0 || services != null) {
			for (PrintService service : services) {
				String existingPrinter = service.getName().toLowerCase();
				if (existingPrinter.equals(selectedPrinter)) {
					selectedService = service;
					break;
				}
			}

			if (selectedService != null) {
				try {
					printerJob.setPrintService(selectedService);
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// boolean printSucceed =
				// JasperPrintManager.printReport(mainPrint, false);
			}
		}*/

	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search(){		
		if(checkSearch == 0){	
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}		
			
			if(getOutletId() != null){
				searchCriteria.add(new SearchValueObject("outletId", ""+getOutletId()));
			}
			
			 disableSearchAll = false;
			 pagingDO.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	} 
	
	public String getDeliveryTypeNameById(String paramDtlId){
		String result = null;
		ParameterDtl dtl = parameterService.findByDetailId(paramDtlId);
		result = dtl.getParameterDtlName();
		return result;
		
	}

	public void modeDelete(List<DOVO> DOVOList){
		try
		{
			for(int i=0; i<DOVOList.size(); i++){
				DOVO DOVoTemp = (DOVO)DOVOList.get(i);
				if(DOVoTemp.getStatus()!=null && DOVoTemp.getStatus().equals("DO_NEW"))
				{
					DOService.delete(DOVoTemp.getDoId(),userSession.getUserCode());
				}
				else
				{
					addFacesMsg(
			                FacesMessage.SEVERITY_ERROR, 
			                "frm001:messages", 
			                "Operation Failed : DO status is not 'NEW'", 
			                null);
				}
			}
			
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formDOTitle")),
					null);
		}	
	}
	
	public void clear(){
		searchAll = "";
		DOVOSearchDialog = new DOVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		DOVOSearchDialog = new DOVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(getOutletId() != null){
			searchCriteria.add(new SearchValueObject("outletId", ""+getOutletId()));
		}
		
		if(getDoNo() != null && !getDoNo().isEmpty()){
			searchCriteria.add(new SearchValueObject("doNo", getDoNo()));
		}
		
		if(getStartDoDate() != null){
			searchCriteria.add(new SearchValueObject("startDoDate", getStartDoDate()));
		}
		
		if(getEndDoDate() != null){
			searchCriteria.add(new SearchValueObject("endDoDate", getEndDoDate()));
		}
		
		if(getTransferTo() != null && !getTransferTo().isEmpty()){
			searchCriteria.add(new SearchValueObject("transferTo", getTransferTo()));
		}
		
		if(getItem() != null && !getItem().isEmpty()){
			searchCriteria.add(new SearchValueObject("item", getItem()));
		}
		
		
		/*if(DOVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formDOCompany"));
			builder.append(":"+companyService.findById(DOVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyId", DOVOSearchDialog.getCompanyId()));
		}
		
		if(DOVOSearchDialog.getDOName() !=null && StringUtils.isNotBlank(DOVOSearchDialog.getDOName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formDOName"));
			builder.append(":"+DOVOSearchDialog.getDOName()+",");
			searchCriteria.add(new SearchValueObject("DOName", DOVOSearchDialog.getDOName()));
		}	
		
		if(DOVOSearchDialog.getDODesc() !=null && StringUtils.isNotBlank(DOVOSearchDialog.getDODesc())) {
			builder.append(facesUtils.getResourceBundleStringValue("formDODesc"));
			builder.append(":"+DOVOSearchDialog.getDODesc()+",");
			searchCriteria.add(new SearchValueObject("DOCode", DOVOSearchDialog.getDODesc()));
		}	
		
		if(builder.toString() !=null && !builder.toString().equals("")){
		    builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}*/
		//searchAll = builder.toString();
		
		pagingDO.setSearchCriteria(searchCriteria);
		
	}
	
	public void exportToXls(){
		
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(searchAll!= null && !searchAll.isEmpty()){
			searchCriteria.add(new SearchValueObject("searchAll", ""+searchAll));
		}
		
		if(getOutletId() != null){
			searchCriteria.add(new SearchValueObject("outletId", ""+getOutletId()));
		}
		
		if(getDoNo() != null && !getDoNo().isEmpty()){
			searchCriteria.add(new SearchValueObject("doNo", getDoNo()));
		}
		
		if(getStartDoDate() != null){
			searchCriteria.add(new SearchValueObject("startDoDate", getStartDoDate()));
		}
		
		if(getEndDoDate() != null){
			searchCriteria.add(new SearchValueObject("endDoDate", getEndDoDate()));
		}
		
		if(getTransferTo() != null && !getTransferTo().isEmpty()){
			searchCriteria.add(new SearchValueObject("transferTo", getTransferTo()));
		}
		
		if(getItem() != null && !getItem().isEmpty()){
			searchCriteria.add(new SearchValueObject("item", getItem()));
		}
		
		List<DOVO> result = DOService.getDOList(searchCriteria, 0, 0, null, false);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample Sheet");
        HSSFFont headerRowCellFont = workbook.createFont(); // Create a new font in the workbook
            //headerRowCellFont.setFontName(MODE_ADD);
            //headerRowCellFont.Color = HSSFColor.BLACK.index;
        headerRowCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
           // headerRowCellFont.Boldweight = HSSFFont.BOLDWEIGHT_BOLD;
        HSSFCellStyle headerRowCellStyle = workbook.createCellStyle(); // Create a new style in the workbook
        headerRowCellStyle.setFont(headerRowCellFont);  // Attach the font to the Style
        
        
        List<Object[]> dataXls = new ArrayList<Object[]>();
        dataXls.add( new Object[]{
        		facesUtils.getResourceBundleStringValue("formDOOutlet"),
        		facesUtils.getResourceBundleStringValue("formDODoNo"),
        		facesUtils.getResourceBundleStringValue("formDODoDate"),
        		facesUtils.getResourceBundleStringValue("formDODoType"),
        		facesUtils.getResourceBundleStringValue("formDOSoNo"),
        		facesUtils.getResourceBundleStringValue("formDOTransferFrom"),
        		facesUtils.getResourceBundleStringValue("formDOTransferTo"),
        		facesUtils.getResourceBundleStringValue("formDOItemResume")});
        
        for(int y=0;y<result.size();y++){
        	DOVO vo = (DOVO) result.get(y);
        	
	        dataXls.add( new Object[]{vo.getOutlet().getOutletName(),
	        						 vo.getDoNo(),
	        						 vo.getDoDate(),
	        						 getDeliveryTypeNameById(vo.getDoType()),
	        						 vo.getSoId(),
	        						 vo.getTransferFrom().getOutletName(),
	        						 vo.getTransferTo().getOutletName(),
	        						 vo.getItemResume()});
        }
        
        int rownum = 0;
        for (int i=0;i<dataXls.size();i++) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = dataXls.get(i);
            int cellnum = 0;
            if(i==0){
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof Date) {
                        cell.setCellValue((Date) obj);  
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    }else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }

                    //Do Work
                    cell.setCellStyle(headerRowCellStyle); // Assign style to all the cells in the header
                   
                }
            }
            else{
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof Date) {
                        cell.setCellValue(sdf.format((Date) obj));  
                    } else if (obj instanceof Boolean) {
                        cell.setCellValue((Boolean) obj);
                    } else if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }else if (obj instanceof Long) {
                        cell.setCellValue((Long) obj);
                    }else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }

                }
            }
        }
        
        FacesContext fc = FacesContext.getCurrentInstance();

            final HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setBufferSize(1024);
            //response.setContentType("text/comma-separated-values");
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "inline; filename=\"" + "DO.xls" + "\"");
            try {
                OutputStream output = response.getOutputStream();
                workbook.write(output);
               /* for (String s : strings) {
                    output.write(s.getBytes());
                }*/
                output.flush();
                output.close();
            } catch (IOException ex) {
               System.out.println(ex);
            }

            fc.responseComplete();
    }

	

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DOSearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public DOService getDOService() {
		return DOService;
	}

	public void setDOService(DOService DOService) {
		this.DOService = DOService;
	}

	public DOVO getDOVOSearchDialog() {
		return DOVOSearchDialog;
	}

	public void setDOVOSearchDialog(DOVO DOVOSearchDialog) {
		this.DOVOSearchDialog = DOVOSearchDialog;
	}

	public PagingTableModel<DOVO> getPagingDO() {
		return pagingDO;
	}

	public void setPagingDO(PagingTableModel<DOVO> pagingDO) {
		this.pagingDO = pagingDO;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
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

	public List<DOVO> getSelectedDOs() {
		return selectedDOs;
	}

	public void setSelectedDOs(List<DOVO> selectedDOs) {
		this.selectedDOs = selectedDOs;
	}


	public ParameterService getParameterService() {
		return parameterService;
	}


	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public Long getOutletId() {
		return outletId;
	}


	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}


	public List<SelectItem> getOutletSelectItem() {
		return outletSelectItem;
	}


	public void setOutletSelectItem(List<SelectItem> outletSelectItem) {
		this.outletSelectItem = outletSelectItem;
	}


	public OutletService getOutletService() {
		return outletService;
	}


	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}


	public String getDoNo() {
		return doNo;
	}


	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}


	public Date getStartDoDate() {
		return startDoDate;
	}


	public void setStartDoDate(Date startDoDate) {
		this.startDoDate = startDoDate;
	}


	public Date getEndDoDate() {
		return endDoDate;
	}


	public void setEndDoDate(Date endDoDate) {
		this.endDoDate = endDoDate;
	}


	public String getTransferTo() {
		return transferTo;
	}


	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}


	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public ItemStockService getItemStockService() {
		return itemStockService;
	}


	public void setItemStockService(ItemStockService itemStockService) {
		this.itemStockService = itemStockService;
	}
	
	
	
}
