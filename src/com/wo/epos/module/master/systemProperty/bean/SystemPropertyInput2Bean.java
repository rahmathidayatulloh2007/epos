
package com.wo.epos.module.master.systemProperty.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.master.systemProperty.service.SystemPropertyService;
import com.wo.epos.module.master.systemProperty.vo.SystemPropertyVO;
import com.wo.epos.module.uam.company.model.Company;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.model.ParameterDtl;
import com.wo.epos.module.uam.parameter.service.ParameterService;

@ManagedBean(name = "systemPropertyInput2Bean")
@ViewScoped
public class SystemPropertyInput2Bean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 7113159650537924182L;

	static Logger logger = Logger.getLogger(SystemPropertyInput2Bean.class);
	
	@ManagedProperty(value = "#{systemPropertyService}")
	private SystemPropertyService systemPropertyService;
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
		
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	private SystemPropertyVO systemPropertyVO = new SystemPropertyVO();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;
	
	private String searchAll;
		
	private List<SelectItem> jnsUsahaSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> kdBrgOtomatisSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatKdBrgSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoPengirimanBrgSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoStockOpnameSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoPenerimaanBrgSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> kdProdOtomatisSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatKdProdSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoPenjualanSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoFakPenjualanSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoPenerimaanSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> pajakBarangPenjualanSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatnoPembelianBrgSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoFakPembelianSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> formatNoPembayaranSelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	
	private String businessType;
	private String invalidloginMax;
	private String automaticItemCode;
	private String formatItemCode;
	private String deliveryItemNoFormat;
	private String stockOpnameNoFormat;
	private String receiptItemNoFormat;	
	private String automaticProdCode;
	private String prodCodeFormat;
	private String salesNoFormat;
	private String invoiceNoFormat;
	private String receiptNoFormat;
	private String salesTax;
	private String deliveryCost;
	private String purchaceNoFormat;
	private String purchaceInvoiceNoFormat;
	private String paymentNoFormat;
	private String taxValue;
	private Long companyId;
	private String companyName;
	
    private String MODE_TYPE;
    
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		systemPropertyVO = new SystemPropertyVO();
				
		Parameter parameter = parameterService.findById("BUSINESS_TYPE");
		jnsUsahaSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			jnsUsahaSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		kdBrgOtomatisSelectItem = new ArrayList<SelectItem>();
		kdBrgOtomatisSelectItem.add(new SelectItem("Y","Y"));
		kdBrgOtomatisSelectItem.add(new SelectItem("N","N"));
		
		parameter = parameterService.findById("SKU_FORMAT_TYPE");
		formatKdBrgSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatKdBrgSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		
		parameter = parameterService.findById("DO_NUMBER_FORMAT");
		formatNoPengirimanBrgSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoPengirimanBrgSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("STO_NUMBER_FORMAT");
		formatNoStockOpnameSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoStockOpnameSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("RN_NUMBER_FORMAT");
		formatNoPenerimaanBrgSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoPenerimaanBrgSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		kdProdOtomatisSelectItem = new ArrayList<SelectItem>();
		kdProdOtomatisSelectItem.add(new SelectItem("Y","Y"));
		kdProdOtomatisSelectItem.add(new SelectItem("N","N"));
		
		
		parameter = parameterService.findById("PLU_FORMAT_TYPE");
		formatKdProdSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatKdProdSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("SO_NUMBER_FORMAT");
		formatNoPenjualanSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoPenjualanSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("SOINV_NUMBER_FORMAT");
		formatNoFakPenjualanSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoFakPenjualanSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("SORCPT_NUMBERFORMAT");
		formatNoPenerimaanSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoPenerimaanSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("TAX_TYPE");
		pajakBarangPenjualanSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			pajakBarangPenjualanSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("PO_NUMBER_FORMAT");
		formatnoPembelianBrgSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatnoPembelianBrgSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("PINV_NUMBER_FORMAT");
		formatNoFakPembelianSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoFakPembelianSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		parameter = parameterService.findById("PAYMENT_NUMBER_FORMAT");
		formatNoPembayaranSelectItem = new ArrayList<SelectItem>();		
		for(ParameterDtl vo : parameter.getListDetail()){		
			formatNoPembayaranSelectItem.add(new SelectItem(vo.getParameterDtlCode(), vo.getParameterDtlName()));
		}
		
		companySelectItem = new ArrayList<SelectItem>();		
		List<CompanyVO> companySelectList = companyService.searchCompanyList();
		for(CompanyVO vo : companySelectList){		
			companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
		}
		
		if(userSession.getCompanyId() !=null){
			companyId = userSession.getCompanyId();
			companyName = userSession.getCompanyName();
		} else {
			companyId = null;
		}
		
		setData();
	    	       		
		MODE_TYPE = "ADD";
		/*disableFlag = false;
		disableFlagAdd = true;*/			
		}
	}
	
	public void setData(){
//		Long companyId = null;		
		if(companyId != null){			
			SystemProperty systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("BUSINESS_TYPE", companyId);
			if(systemProperty!=null){
				businessType = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("MAX_INVALID_LOGIN", companyId);
			if(systemProperty!=null){
				invalidloginMax = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("SKU_GENFLAG", companyId);
			if(systemProperty!=null){
				automaticItemCode = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("SKU_GENFORMAT", companyId);
			if(systemProperty!=null){
				formatItemCode = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("DO_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				deliveryItemNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("STO_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				stockOpnameNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("RN_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				receiptItemNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("PLU_GENFLAG", companyId);
			if(systemProperty!=null){
				automaticProdCode = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("PLU_FORMAT_TYPE", companyId);
			if(systemProperty!=null){
				prodCodeFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("SO_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				salesNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("SOINV_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				invoiceNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("SORCPT_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				receiptNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("DEFAULT_TAX_TYPE", companyId);
			if(systemProperty!=null){
				salesTax = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("DELIVERY_COST", companyId);
			if(systemProperty!=null){
				deliveryCost = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("PO_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				purchaceNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("PINV_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				purchaceInvoiceNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("PAYMENT_NUMBERFORMAT", companyId);
			if(systemProperty!=null){
				paymentNoFormat = systemProperty.getSystemPropertyValue();
			}
			
			systemProperty = systemPropertyService
					.searchSystemPropertyNameAndCompany("TAX_VALUE", companyId);
			if(systemProperty!=null){
				taxValue = systemProperty.getSystemPropertyValue();
			}
		} else {
			businessType = null;
			invalidloginMax = null;
			automaticItemCode = null;
			formatItemCode = null;
			deliveryItemNoFormat = null;
			stockOpnameNoFormat = null;
			receiptItemNoFormat = null;
			automaticProdCode = null;
			prodCodeFormat = null;
			salesNoFormat = null;
			invoiceNoFormat = null;
			receiptNoFormat = null;
			salesTax = null;
			deliveryCost = null;
			purchaceNoFormat = null;
			purchaceInvoiceNoFormat = null;
			paymentNoFormat = null;
			taxValue = null;
		}
	}
	
	public boolean validate() {
		boolean valid = true;
		
		if (companyId == null){
			facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, facesUtils.retrieveMessage("formSystemPropertyCompany") + " "
					+ facesUtils.retrieveMessage("errorMustBeFilled"), null);

			valid = false;
		}
		
		return valid;
	}
	
	public void modeSave(){
		try{
			save();
			addFacesMsg(
	                FacesMessage.SEVERITY_INFO, 
	                "frm001:global", 
	                "Save Successfull", 
	                null);
		}catch(Exception  ex){
			
			System.out.println(ex);
			addFacesMsg(FacesMessage.SEVERITY_ERROR, "frm001:global",
					"Save Failed, " + ex.toString(), null);
		}
	}
	
	public void save() {
		try 
		{
			List<SystemPropertyVO> listSystemProperty = new ArrayList<SystemPropertyVO>();
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Tipe Usaha");
			systemPropertyVO.setSystemPropertyName("BUSINESS_TYPE");
			systemPropertyVO.setSystemPropertyValue(businessType);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Maksimal Salah Login");
			systemPropertyVO.setSystemPropertyName("MAX_INVALID_LOGIN");
			systemPropertyVO.setSystemPropertyValue(invalidloginMax);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Kode Barang Otomatis");
			systemPropertyVO.setSystemPropertyName("SKU_GENFLAG");
			systemPropertyVO.setSystemPropertyValue(automaticItemCode);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Kode Barang");
			systemPropertyVO.setSystemPropertyName("SKU_GENFORMAT");
			systemPropertyVO.setSystemPropertyValue(formatItemCode);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO
					.setSystemPropertyDesc("Format Nomor Pengiriman Barang");
			systemPropertyVO.setSystemPropertyName("DO_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(deliveryItemNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Nomor Stok Opname");
			systemPropertyVO.setSystemPropertyName("STO_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(stockOpnameNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO
					.setSystemPropertyDesc("Format Nomor Penerimaan Barang");
			systemPropertyVO.setSystemPropertyName("RN_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(receiptItemNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Kode Produk Otomatis");
			systemPropertyVO.setSystemPropertyName("PLU_GENFLAG");
			systemPropertyVO.setSystemPropertyValue(automaticProdCode);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Kode Produk");
			systemPropertyVO.setSystemPropertyName("PLU_FORMAT_TYPE");
			systemPropertyVO.setSystemPropertyValue(prodCodeFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Nomor Penjualan");
			systemPropertyVO.setSystemPropertyName("SO_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(salesNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Nomor Faktur Penjualan");
			systemPropertyVO.setSystemPropertyName("SOINV_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(invoiceNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Nomor Penerimaan");
			systemPropertyVO.setSystemPropertyName("SORCPT_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(receiptNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Jenis Pajak Penjualan");
			systemPropertyVO.setSystemPropertyName("DEFAULT_TAX_TYPE");
			systemPropertyVO.setSystemPropertyValue(salesTax);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Biaya Pengiriman");
			systemPropertyVO.setSystemPropertyName("DELIVERY_COST");
			systemPropertyVO.setSystemPropertyValue(deliveryCost);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Nomor Pembelian Barang");
			systemPropertyVO.setSystemPropertyName("PO_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(purchaceNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Nomor Faktur Pembelian");
			systemPropertyVO.setSystemPropertyName("PINV_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(purchaceInvoiceNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Format Nomor Pembayaran");
			systemPropertyVO.setSystemPropertyName("PAYMENT_NUMBERFORMAT");
			systemPropertyVO.setSystemPropertyValue(paymentNoFormat);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
			
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Nilai Pajak Penjualan");
			systemPropertyVO.setSystemPropertyName("DEFAULT_TAX_VALUE");
			systemPropertyVO.setSystemPropertyValue(taxValue);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
			
			systemPropertyVO = new SystemPropertyVO();
			systemPropertyVO.setCompanyId(companyId);
			systemPropertyVO.setSystemPropertyDesc("Nilai Pajak Penjualan");
			systemPropertyVO.setSystemPropertyName("TAX_VALUE");
			systemPropertyVO.setSystemPropertyValue(taxValue);
			systemPropertyVO.setActiveFlag("Y");
			listSystemProperty.add(systemPropertyVO);
	
	//		Long companyId = null;
	//		if(userSession.getCompanyId() !=null){
	//			companyId = userSession.getCompanyId();
	//		}
			
			for (int i = 0; i < listSystemProperty.size(); i++) {
				SystemPropertyVO systemPropertyVO = (SystemPropertyVO) listSystemProperty
						.get(i);
				SystemProperty systemProperty = systemPropertyService
						.searchSystemPropertyNameAndCompany(systemPropertyVO
								.getSystemPropertyName(), companyId);
	
				if (systemProperty == null) 
				{	
					systemPropertyVO.setCreateBy(userSession.getUserCode());
					systemPropertyVO.setCreateOn(new Timestamp(System.currentTimeMillis()));
					systemPropertyService.save(systemPropertyVO,
							userSession.getUserCode());
				} 
				else 
				{	
					systemPropertyVO.setSystemPropertyId(systemProperty.getSystemPropertyId());
					systemPropertyVO.setUpdateBy(userSession.getUserCode());
					systemPropertyVO.setUpdateOn(new Timestamp(System.currentTimeMillis()));
					systemPropertyService.update(systemPropertyVO,
							userSession.getUserCode());
				}
			}
			
			if(MODE_TYPE.equals("ADD"))
			{
				facesUtils.addFacesMsg(
						FacesMessage.SEVERITY_INFO, 
						"frm001:messages", 
		                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_saved")), 
		                null);
			}
			else if(MODE_TYPE.equals("EDIT")) 
			{
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
		companyId = null;
		setData();
	}
	
	public void modeEdit(List<CompanyVO> voList){
		MODE_TYPE = "EDIT";
		for(CompanyVO voTemp: voList){
			companyId = voTemp.getCompanyId();
			Company comp = companyService.findById(companyId);
			companyName = comp.getCompanyName();
			setData();
		}
	}	

	public void companyOnChange() {
		setData();
	}
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		SystemPropertyInputBean.logger = logger;
	}

	public SystemPropertyService getSystemPropertyService() {
		return systemPropertyService;
	}

	public void setSystemPropertyService(SystemPropertyService systemPropertyService) {
		this.systemPropertyService = systemPropertyService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public SystemPropertyVO getSystemPropertyVO() {
		return systemPropertyVO;
	}

	public void setSystemPropertyVO(SystemPropertyVO systemPropertyVO) {
		this.systemPropertyVO = systemPropertyVO;
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

	public List<SelectItem> getJnsUsahaSelectItem() {
		return jnsUsahaSelectItem;
	}

	public void setJnsUsahaSelectItem(List<SelectItem> jnsUsahaSelectItem) {
		this.jnsUsahaSelectItem = jnsUsahaSelectItem;
	}

	public List<SelectItem> getKdBrgOtomatisSelectItem() {
		return kdBrgOtomatisSelectItem;
	}

	public void setKdBrgOtomatisSelectItem(List<SelectItem> kdBrgOtomatisSelectItem) {
		this.kdBrgOtomatisSelectItem = kdBrgOtomatisSelectItem;
	}

	public List<SelectItem> getFormatKdBrgSelectItem() {
		return formatKdBrgSelectItem;
	}

	public void setFormatKdBrgSelectItem(List<SelectItem> formatKdBrgSelectItem) {
		this.formatKdBrgSelectItem = formatKdBrgSelectItem;
	}

	public List<SelectItem> getFormatNoPengirimanBrgSelectItem() {
		return formatNoPengirimanBrgSelectItem;
	}

	public void setFormatNoPengirimanBrgSelectItem(
			List<SelectItem> formatNoPengirimanBrgSelectItem) {
		this.formatNoPengirimanBrgSelectItem = formatNoPengirimanBrgSelectItem;
	}

	public List<SelectItem> getFormatNoStockOpnameSelectItem() {
		return formatNoStockOpnameSelectItem;
	}

	public void setFormatNoStockOpnameSelectItem(
			List<SelectItem> formatNoStockOpnameSelectItem) {
		this.formatNoStockOpnameSelectItem = formatNoStockOpnameSelectItem;
	}

	public List<SelectItem> getFormatNoPenerimaanBrgSelectItem() {
		return formatNoPenerimaanBrgSelectItem;
	}

	public void setFormatNoPenerimaanBrgSelectItem(
			List<SelectItem> formatNoPenerimaanBrgSelectItem) {
		this.formatNoPenerimaanBrgSelectItem = formatNoPenerimaanBrgSelectItem;
	}

	public List<SelectItem> getKdProdOtomatisSelectItem() {
		return kdProdOtomatisSelectItem;
	}

	public void setKdProdOtomatisSelectItem(
			List<SelectItem> kdProdOtomatisSelectItem) {
		this.kdProdOtomatisSelectItem = kdProdOtomatisSelectItem;
	}

	public List<SelectItem> getFormatKdProdSelectItem() {
		return formatKdProdSelectItem;
	}

	public void setFormatKdProdSelectItem(List<SelectItem> formatKdProdSelectItem) {
		this.formatKdProdSelectItem = formatKdProdSelectItem;
	}

	public List<SelectItem> getFormatNoPenjualanSelectItem() {
		return formatNoPenjualanSelectItem;
	}

	public void setFormatNoPenjualanSelectItem(
			List<SelectItem> formatNoPenjualanSelectItem) {
		this.formatNoPenjualanSelectItem = formatNoPenjualanSelectItem;
	}

	public List<SelectItem> getFormatNoFakPenjualanSelectItem() {
		return formatNoFakPenjualanSelectItem;
	}

	public void setFormatNoFakPenjualanSelectItem(
			List<SelectItem> formatNoFakPenjualanSelectItem) {
		this.formatNoFakPenjualanSelectItem = formatNoFakPenjualanSelectItem;
	}

	public List<SelectItem> getFormatNoPenerimaanSelectItem() {
		return formatNoPenerimaanSelectItem;
	}

	public void setFormatNoPenerimaanSelectItem(
			List<SelectItem> formatNoPenerimaanSelectItem) {
		this.formatNoPenerimaanSelectItem = formatNoPenerimaanSelectItem;
	}

	public List<SelectItem> getPajakBarangPenjualanSelectItem() {
		return pajakBarangPenjualanSelectItem;
	}

	public void setPajakBarangPenjualanSelectItem(
			List<SelectItem> pajakBarangPenjualanSelectItem) {
		this.pajakBarangPenjualanSelectItem = pajakBarangPenjualanSelectItem;
	}

	

	public List<SelectItem> getFormatnoPembelianBrgSelectItem() {
		return formatnoPembelianBrgSelectItem;
	}

	public void setFormatnoPembelianBrgSelectItem(
			List<SelectItem> formatnoPembelianBrgSelectItem) {
		this.formatnoPembelianBrgSelectItem = formatnoPembelianBrgSelectItem;
	}

	public List<SelectItem> getFormatNoFakPembelianSelectItem() {
		return formatNoFakPembelianSelectItem;
	}

	public void setFormatNoFakPembelianSelectItem(
			List<SelectItem> formatNoFakPembelianSelectItem) {
		this.formatNoFakPembelianSelectItem = formatNoFakPembelianSelectItem;
	}

	public List<SelectItem> getFormatNoPembayaranSelectItem() {
		return formatNoPembayaranSelectItem;
	}

	public void setFormatNoPembayaranSelectItem(
			List<SelectItem> formatNoPembayaranSelectItem) {
		this.formatNoPembayaranSelectItem = formatNoPembayaranSelectItem;
	}

	public String getMODE_TYPE() {
		return MODE_TYPE;
	}

	public void setMODE_TYPE(String mODE_TYPE) {
		MODE_TYPE = mODE_TYPE;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	

	
	public String getInvalidloginMax() {
		return invalidloginMax;
	}

	public void setInvalidloginMax(String invalidloginMax) {
		this.invalidloginMax = invalidloginMax;
	}

	public String getAutomaticItemCode() {
		return automaticItemCode;
	}

	public void setAutomaticItemCode(String automaticItemCode) {
		this.automaticItemCode = automaticItemCode;
	}

	public String getFormatItemCode() {
		return formatItemCode;
	}

	public void setFormatItemCode(String formatItemCode) {
		this.formatItemCode = formatItemCode;
	}

	public String getDeliveryItemNoFormat() {
		return deliveryItemNoFormat;
	}

	public void setDeliveryItemNoFormat(String deliveryItemNoFormat) {
		this.deliveryItemNoFormat = deliveryItemNoFormat;
	}

	public String getStockOpnameNoFormat() {
		return stockOpnameNoFormat;
	}

	public void setStockOpnameNoFormat(String stockOpnameNoFormat) {
		this.stockOpnameNoFormat = stockOpnameNoFormat;
	}

	public String getReceiptItemNoFormat() {
		return receiptItemNoFormat;
	}

	public void setReceiptItemNoFormat(String receiptItemNoFormat) {
		this.receiptItemNoFormat = receiptItemNoFormat;
	}

	public String getAutomaticProdCode() {
		return automaticProdCode;
	}

	public void setAutomaticProdCode(String automaticProdCode) {
		this.automaticProdCode = automaticProdCode;
	}

	public String getProdCodeFormat() {
		return prodCodeFormat;
	}

	public void setProdCodeFormat(String prodCodeFormat) {
		this.prodCodeFormat = prodCodeFormat;
	}

	public String getSalesNoFormat() {
		return salesNoFormat;
	}

	public void setSalesNoFormat(String salesNoFormat) {
		this.salesNoFormat = salesNoFormat;
	}

	public String getInvoiceNoFormat() {
		return invoiceNoFormat;
	}

	public void setInvoiceNoFormat(String invoiceNoFormat) {
		this.invoiceNoFormat = invoiceNoFormat;
	}

	public String getReceiptNoFormat() {
		return receiptNoFormat;
	}

	public void setReceiptNoFormat(String receiptNoFormat) {
		this.receiptNoFormat = receiptNoFormat;
	}

	public String getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}

	public String getPurchaceNoFormat() {
		return purchaceNoFormat;
	}

	public void setPurchaceNoFormat(String purchaceNoFormat) {
		this.purchaceNoFormat = purchaceNoFormat;
	}

	public String getPurchaceInvoiceNoFormat() {
		return purchaceInvoiceNoFormat;
	}

	public void setPurchaceInvoiceNoFormat(String purchaceInvoiceNoFormat) {
		this.purchaceInvoiceNoFormat = purchaceInvoiceNoFormat;
	}

	public String getPaymentNoFormat() {
		return paymentNoFormat;
	}

	public void setPaymentNoFormat(String paymentNoFormat) {
		this.paymentNoFormat = paymentNoFormat;
	}

	public String getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(String deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public String getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(String taxValue) {
		this.taxValue = taxValue;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
