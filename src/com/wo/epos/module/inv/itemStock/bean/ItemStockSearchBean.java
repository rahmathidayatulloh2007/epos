package com.wo.epos.module.inv.itemStock.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.wo.epos.module.inv.itemStock.vo.ItemStockVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.model.Outlet;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "itemStockSearchBean")
@ViewScoped
public class ItemStockSearchBean extends CommonBean implements Serializable {
		
	private static final long serialVersionUID = -5010765202025439622L;

	static Logger logger = Logger.getLogger(ItemStockSearchBean.class);
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{itemStockService}")
	private ItemStockService itemStockService;
	
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
			
	private ItemStockVO itemStockVOSearchDialog = new ItemStockVO();	

	private PagingTableModel<ItemStockVO> pagingItemStock;	
	
		
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> outletSelectItem = new ArrayList<SelectItem>();
	
		
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;	
	
	private Integer checkSearch;
	
	private Long companyId;
	
	private Long outletId;
	private Long sessionCompanyId;
	private Long sessionOutletId;
	
	private String doNo;
	private Date startDoDate;
	private Date endDoDate;
	private String transferTo;
	private String item;

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
				sessionCompanyId = userVO.getCompanyId();
				sessionOutletId = userVO.getOutletId();
			}

			itemStockVOSearchDialog = new ItemStockVO();
			pagingItemStock = new PagingTableModel(itemStockService, paging);

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

			if (companyId != null) {
				onChangeCompany();
			}

			disableSearchAll = false;

			checkSearch = 0;
			search();
		
	}
	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void search(){		
		//if(checkSearch == 0){	
			List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
			if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
				searchCriteria.add(new SearchValueObject("searchAll", searchAll));
			} else {
				searchCriteria.add(new SearchValueObject("searchAll", ""));
			}	
			
			if(getCompanyId() != null && getCompanyId()>0){
				searchCriteria.add(new SearchValueObject("companyId", ""+getCompanyId()));
			}
			
			if(getOutletId() != null && getOutletId()>0){
				searchCriteria.add(new SearchValueObject("outletId", ""+getOutletId()));
			}
			
			 disableSearchAll = false;
			 pagingItemStock.setSearchCriteria(searchCriteria);
		//}
		
	} 
	
	public void onChangeCompany(){
		List<Outlet> transferFromSelectList = outletService.findOutletByCompany(getCompanyId());
		outletSelectItem = new ArrayList<SelectItem>();
	    for(Outlet com : transferFromSelectList){
	    	outletSelectItem.add(new SelectItem(com.getOutletId(), com.getOutletName()));
	    }
		
	}

	
	public void clear(){
		searchAll = "";
		itemStockVOSearchDialog = new ItemStockVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		itemStockVOSearchDialog = new ItemStockVO();
		search();
	}
	
	
	public void exportToXls(){
		
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if (searchAll !=null && StringUtils.isNotBlank(searchAll)) {
			searchCriteria.add(new SearchValueObject("searchAll", searchAll));
		} else {
			searchCriteria.add(new SearchValueObject("searchAll", ""));
		}	
		
		if(getCompanyId() != null && getCompanyId()>0){
			searchCriteria.add(new SearchValueObject("companyId", ""+getCompanyId()));
		}
		
		if(getOutletId() != null && getOutletId()>0){
			searchCriteria.add(new SearchValueObject("outletId", ""+getOutletId()));
		}
		
		List<ItemStockVO> result = itemStockService.searchItemStock(searchCriteria);
		//List<ItemStockVO> result = new ArrayList<ItemStockVO>();
		
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
        		facesUtils.getResourceBundleStringValue("formItemStockCompany"),
        		facesUtils.getResourceBundleStringValue("formItemStockOutlet"),
        		facesUtils.getResourceBundleStringValue("formItemStockItemCode"),
        		facesUtils.getResourceBundleStringValue("formItemStockItemName"),
        		facesUtils.getResourceBundleStringValue("formItemStockCategory"),
        		facesUtils.getResourceBundleStringValue("formItemStockQty"),
        		facesUtils.getResourceBundleStringValue("formItemStockOutgoingQty"),
        		facesUtils.getResourceBundleStringValue("formItemStockIncomingQty"),
        		facesUtils.getResourceBundleStringValue("formItemStockUm")});
        
        for(int y=0;y<result.size();y++){
        	ItemStockVO vo = (ItemStockVO) result.get(y);
        	
	        dataXls.add( new Object[]{vo.getCompany().getCompanyName(),
	        						 vo.getOutlet().getOutletName(),
	        						 vo.getItem().getItemCode(),
	        						 vo.getItem().getItemName(),
	        						 vo.getItem().getCategory().getCategoryName(),
	        						 vo.getStockQty(),
	        						 vo.getOutgoingQty(),
	        						 vo.getIncomingQty(),
	        						 vo.getItem().getUm().getUmName()});
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
                    } 
                    else if (obj instanceof Boolean) 
                    {
                        cell.setCellValue((Boolean) obj);
                    } 
                    else if (obj instanceof String) 
                    {
                        cell.setCellValue((String) obj);
                    } 
                    else if (obj instanceof Double) 
                    {
                        cell.setCellValue((Double) obj);
                    }
                    else if (obj instanceof Long) 
                    {
                        cell.setCellValue((Long) obj);
                    }
                    else if (obj instanceof Integer) 
                    {
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
            response.setHeader("Content-Disposition", "inline; filename=\"" + "Item Stock.xls" + "\"");
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
		ItemStockSearchBean.logger = logger;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	

	public PagingTableModel<ItemStockVO> getPagingItemStock() {
		return pagingItemStock;
	}

	public void setPagingItemStock(PagingTableModel<ItemStockVO> pagingItemStock) {
		this.pagingItemStock = pagingItemStock;
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


	public Long getSessionCompanyId() {
		return sessionCompanyId;
	}


	public void setSessionCompanyId(Long sessionCompanyId) {
		this.sessionCompanyId = sessionCompanyId;
	}


	public Long getSessionOutletId() {
		return sessionOutletId;
	}


	public void setSessionOutletId(Long sessionOutletId) {
		this.sessionOutletId = sessionOutletId;
	}
	
	
	
}
