package com.wo.epos.module.inv.transferItem.bean;

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
import com.wo.epos.module.inv.transferItem.service.TransferItemService;
import com.wo.epos.module.inv.transferItem.vo.TransferItemVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "transferItemSearchBean")
@ViewScoped
public class TransferItemSearchBean extends CommonBean implements Serializable {
		
	private static final long serialVersionUID = -5010765202025439622L;

	static Logger logger = Logger.getLogger(TransferItemSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{transferItemService}")
	private TransferItemService transferItemService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{itemStockService}")
	private ItemStockService itemStockService;
			
	private TransferItemVO transferItemVOSearchDialog = new TransferItemVO();	

	private PagingTableModel<TransferItemVO> pagingTransferItem;	
	
	private List<TransferItemVO> selectedTransferItems;
		
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

			transferItemVOSearchDialog = new TransferItemVO();
			pagingTransferItem = new PagingTableModel(transferItemService, paging);

			List<CompanyVO> companySelectList = companyService.searchCompanyList();
			companySelectItem = new ArrayList<SelectItem>();
			for (CompanyVO com : companySelectList) {
				companySelectItem.add(new SelectItem(com.getCompanyId(), com.getCompanyName()));
			}

			List<OutletVO> outletSelectList = outletService.searchOutletList();
			outletSelectItem = new ArrayList<SelectItem>();
			for (OutletVO com : outletSelectList) {
				outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
			}

			disableSearchAll = false;

			checkSearch = 0;
			search();
		}
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
		JasperPrint print = JasperFillManager.fillReport(jr,param,transferItemService.getConnection());
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
			 pagingTransferItem.setSearchCriteria(searchCriteria);
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

	public void modeDelete(List<TransferItemVO> transferItemVOList){
		for(int i=0; i<transferItemVOList.size(); i++){
			TransferItemVO transferItemVoTemp = (TransferItemVO)transferItemVOList.get(i);
			if(transferItemVoTemp.getStatus()!=null && transferItemVoTemp.getStatus().equals("DO_NEW")){
				transferItemService.delete(transferItemVoTemp.getDoId(),userSession.getUserCode());
			}else{
				addFacesMsg(
		                FacesMessage.SEVERITY_ERROR, 
		                "frm001:searchAll", 
		                "Delete Failed, DO Status not equal 'DO_NEW' ", 
		                null);
			}
		}
	}
	
	public void clear(){
		searchAll = "";
		transferItemVOSearchDialog = new TransferItemVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		transferItemVOSearchDialog = new TransferItemVO();
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
		
		
		/*if(transferItemVOSearchDialog.getCompanyId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formTransferItemCompany"));
			builder.append(":"+companyService.findById(transferItemVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("companyId", transferItemVOSearchDialog.getCompanyId()));
		}
		
		if(transferItemVOSearchDialog.getTransferItemName() !=null && StringUtils.isNotBlank(transferItemVOSearchDialog.getTransferItemName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formTransferItemName"));
			builder.append(":"+transferItemVOSearchDialog.getTransferItemName()+",");
			searchCriteria.add(new SearchValueObject("transferItemName", transferItemVOSearchDialog.getTransferItemName()));
		}	
		
		if(transferItemVOSearchDialog.getTransferItemDesc() !=null && StringUtils.isNotBlank(transferItemVOSearchDialog.getTransferItemDesc())) {
			builder.append(facesUtils.getResourceBundleStringValue("formTransferItemDesc"));
			builder.append(":"+transferItemVOSearchDialog.getTransferItemDesc()+",");
			searchCriteria.add(new SearchValueObject("transferItemCode", transferItemVOSearchDialog.getTransferItemDesc()));
		}	
		
		if(builder.toString() !=null && !builder.toString().equals("")){
		    builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}*/
		//searchAll = builder.toString();
		
		pagingTransferItem.setSearchCriteria(searchCriteria);
		
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
		
		List<TransferItemVO> result = transferItemService.getTransferItemList(searchCriteria, 0, 0, null, false);
		
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
        		facesUtils.getResourceBundleStringValue("formTransferItemOutlet"),
        		facesUtils.getResourceBundleStringValue("formTransferItemDoNo"),
        		facesUtils.getResourceBundleStringValue("formTransferItemDoDate"),
        		facesUtils.getResourceBundleStringValue("formTransferItemDoType"),
        		facesUtils.getResourceBundleStringValue("formTransferItemSoNo"),
        		facesUtils.getResourceBundleStringValue("formTransferItemTransferFrom"),
        		facesUtils.getResourceBundleStringValue("formTransferItemTransferTo"),
        		facesUtils.getResourceBundleStringValue("formTransferItemItemResume")});
        
        for(int y=0;y<result.size();y++){
        	TransferItemVO vo = (TransferItemVO) result.get(y);
        	
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
		TransferItemSearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public TransferItemService getTransferItemService() {
		return transferItemService;
	}

	public void setTransferItemService(TransferItemService transferItemService) {
		this.transferItemService = transferItemService;
	}

	public TransferItemVO getTransferItemVOSearchDialog() {
		return transferItemVOSearchDialog;
	}

	public void setTransferItemVOSearchDialog(TransferItemVO transferItemVOSearchDialog) {
		this.transferItemVOSearchDialog = transferItemVOSearchDialog;
	}

	public PagingTableModel<TransferItemVO> getPagingTransferItem() {
		return pagingTransferItem;
	}

	public void setPagingTransferItem(PagingTableModel<TransferItemVO> pagingTransferItem) {
		this.pagingTransferItem = pagingTransferItem;
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

	public List<TransferItemVO> getSelectedTransferItems() {
		return selectedTransferItems;
	}

	public void setSelectedTransferItems(List<TransferItemVO> selectedTransferItems) {
		this.selectedTransferItems = selectedTransferItems;
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
