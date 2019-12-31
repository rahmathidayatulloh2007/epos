package com.wo.epos.module.uam.outlet.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.master.city.service.CityService;
import com.wo.epos.module.master.city.vo.CityVO;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;
import com.wo.epos.module.uam.company.service.CompanyService;
import com.wo.epos.module.uam.company.vo.CompanyVO;
import com.wo.epos.module.uam.outlet.service.OutletService;
import com.wo.epos.module.uam.outlet.vo.OutletVO;
import com.wo.epos.module.uam.outletEmp.model.OutletEmp;
import com.wo.epos.module.uam.outletEmp.service.OutletEmpService;
import com.wo.epos.module.uam.user.vo.UserVO;

@ManagedBean(name = "outletSearchBean")
@ViewScoped
public class OutletSearchBean extends CommonBean implements Serializable{

	private static final long serialVersionUID = -743646042554424313L;
	
	static Logger logger = Logger.getLogger(OutletSearchBean.class);
	
	@ManagedProperty(value = "#{outletService}")
	private OutletService outletService;
	
	@ManagedProperty(value = "#{outletEmpService}")
	private OutletEmpService outletEmpService;
	
	@ManagedProperty(value = "#{companyService}")
	private CompanyService companyService;
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
	
	@ManagedProperty(value = "#{cityService}")
	private CityService cityService;
	
	private OutletVO outletVOSearchDialog = new OutletVO();
	
	private PagingTableModel<OutletVO> pagingOutlet;
	
	private List<OutletVO> outletList = new ArrayList<OutletVO>();
	private List<OutletVO> selectedOutlet;
	
	private List<SelectItem> citySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> companySelectItem = new ArrayList<SelectItem>();
	private List<SelectItem> provinceSelectItem = new ArrayList<SelectItem>();
	
	private boolean disableFlag;
	private boolean disableFlagAdd;	
	private boolean selectAll;
	private boolean disableSearchAll;

	private String searchAll;
	
	private Integer checkSearch;
	
	private Long companyId;
    
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
			}

			outletVOSearchDialog = new OutletVO();

			citySelectItem = new ArrayList<SelectItem>();
			List<CityVO> citySelectList = outletService.searchCityAll();
			for (CityVO vo : citySelectList) {
				citySelectItem.add(new SelectItem(vo.getCityId(), vo.getCityName()));
			}

			companySelectItem = new ArrayList<SelectItem>();
			List<CompanyVO> companySelectList = outletService.searchCompanyList();
			for (CompanyVO vo : companySelectList) {
				companySelectItem.add(new SelectItem(vo.getCompanyId(), vo.getCompanyName()));
			}

			List<ProvinceVO> provinceSelectList = provinceService.provinceSearch();
			provinceSelectItem = new ArrayList<SelectItem>();
			for (ProvinceVO vo : provinceSelectList) {
				provinceSelectItem.add(new SelectItem(vo.getProvinceId(), vo.getProvinceName()));
			}

			pagingOutlet = new PagingTableModel(outletService, paging);
			disableFlag = false;
			disableFlagAdd = true;
			disableSearchAll = false;

			checkSearch = 0;
			search();
		
	}
    
    public String getOutletEmpById(Long id,String data){
    	String result = null;
    	OutletEmp outletEmp = outletEmpService.findOutletEmpByOutletId(id);
    	//System.out.println("outletEmp=="+outletEmp);
    	//System.out.println("outletEmp.getOutletEmpId()=="+outletEmp.getOutletEmpId());
    	if(outletEmp!=null && outletEmp.getOutletEmpId()!=null){
	    	if(data.equals("ContactName")){
	    		result = outletEmp.getEmployee().getFullName();
	    	}
	    	if(data.equals("Telp")){
	    		result = outletEmp.getEmployee().getHpNo();
	    	}
    	}
    	return result;
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
			
			if(companyId!=null){
			searchCriteria.add(new SearchValueObject("company", companyId));
			}
			
			disableSearchAll = false;
			pagingOutlet.setSearchCriteria(searchCriteria);
			//outletList = outletService.searchOutletList();
    	}else{
    		searchDialog();
    	}
    }
    
    public void modeDelete(List<OutletVO> outletVOList){
    	try
		{
	    	for(int i=0; i<outletVOList.size(); i++){
	    		OutletVO outletVOTemp = outletVOList.get(i);
	    		outletService.delete(outletVOTemp.getOutletId());
	    	}
	    	
	    	facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
                null);
	    	
		} catch (Exception ex) {
			ex.printStackTrace();
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_ERROR,
				"frm001:messages",
				facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formOutletTitle")),
				null);
		}	
    }
    
    public void clear(){
		searchAll = "";
		outletVOSearchDialog = new OutletVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		outletVOSearchDialog = new OutletVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(companyId!= null){
			outletVOSearchDialog.setCompanyId(companyId);
		}
		
		if(outletVOSearchDialog.getCompanyId() !=null ) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyName"));
			builder.append(":"+companyService.findById(outletVOSearchDialog.getCompanyId()).getCompanyName()+",");
			searchCriteria.add(new SearchValueObject("company", outletVOSearchDialog.getCompanyId()));
		}
		if(outletVOSearchDialog.getOutletName() !=null && StringUtils.isNotBlank(outletVOSearchDialog.getOutletName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formOutletName"));
			builder.append(":"+outletVOSearchDialog.getOutletName()+",");
			searchCriteria.add(new SearchValueObject("outletName", outletVOSearchDialog.getOutletName()));
		}
		
		if(outletVOSearchDialog.getAddress() !=null && StringUtils.isNotBlank(outletVOSearchDialog.getAddress())) {
			builder.append(facesUtils.getResourceBundleStringValue("formOutletAddress"));
			builder.append(":"+outletVOSearchDialog.getAddress()+",");
			searchCriteria.add(new SearchValueObject("address", outletVOSearchDialog.getAddress()));
		}
		if(outletVOSearchDialog.getProvinceId() !=null) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyProvince"));
			builder.append(":"+provinceService.findById(outletVOSearchDialog.getProvinceId()).getProvinceName()+",");
			searchCriteria.add(new SearchValueObject("province", outletVOSearchDialog.getProvinceId()));
		}
		if(outletVOSearchDialog.getCityId() !=null) {
			System.out.println("outletVOSearchDialog.getCityId() =" +outletVOSearchDialog.getCityId() );
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyCity"));
			builder.append(":"+cityService.findById(outletVOSearchDialog.getCityId()).getCityName()+",");
			System.out.println("cityService.findById(outletVOSearchDialog.getCityId()).getCityName() =" +cityService.findById(outletVOSearchDialog.getCityId()).getCityName() );
			searchCriteria.add(new SearchValueObject("city", outletVOSearchDialog.getCityId()));
		}
		if(outletVOSearchDialog.getPicName() !=null && StringUtils.isNotBlank(outletVOSearchDialog.getPicName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyPicName"));
			builder.append(":"+outletVOSearchDialog.getPicName()+",");
			searchCriteria.add(new SearchValueObject("picName", outletVOSearchDialog.getPicName()));
		}
		if(outletVOSearchDialog.getPicPhoneno() !=null && StringUtils.isNotBlank(outletVOSearchDialog.getPicPhoneno())) {
			builder.append(facesUtils.getResourceBundleStringValue("formCompanyPicPhoneno"));
			builder.append(":"+outletVOSearchDialog.getPicPhoneno()+",");
			searchCriteria.add(new SearchValueObject("picPhone", outletVOSearchDialog.getPicPhoneno()));
		}
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingOutlet.setSearchCriteria(searchCriteria);
		
	}

	public OutletService getOutletService() {
		return outletService;
	}

	public void setOutletService(OutletService outletService) {
		this.outletService = outletService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public ProvinceService getProvinceService() {
		return provinceService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public OutletVO getOutletVOSearchDialog() {
		return outletVOSearchDialog;
	}

	public void setOutletVOSearchDialog(OutletVO outletVOSearchDialog) {
		this.outletVOSearchDialog = outletVOSearchDialog;
	}

	public PagingTableModel<OutletVO> getPagingOutlet() {
		return pagingOutlet;
	}

	public void setPagingOutlet(PagingTableModel<OutletVO> pagingOutlet) {
		this.pagingOutlet = pagingOutlet;
	}

	public List<OutletVO> getOutletList() {
		return outletList;
	}

	public void setOutletList(List<OutletVO> outletList) {
		this.outletList = outletList;
	}

	public List<OutletVO> getSelectedOutlet() {
		return selectedOutlet;
	}

	public void setSelectedOutlet(List<OutletVO> selectedOutlet) {
		this.selectedOutlet = selectedOutlet;
	}

	public List<SelectItem> getCitySelectItem() {
		return citySelectItem;
	}

	public void setCitySelectItem(List<SelectItem> citySelectItem) {
		this.citySelectItem = citySelectItem;
	}

	public List<SelectItem> getCompanySelectItem() {
		return companySelectItem;
	}

	public void setCompanySelectItem(List<SelectItem> companySelectItem) {
		this.companySelectItem = companySelectItem;
	}

	public List<SelectItem> getProvinceSelectItem() {
		return provinceSelectItem;
	}

	public void setProvinceSelectItem(List<SelectItem> provinceSelectItem) {
		this.provinceSelectItem = provinceSelectItem;
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

	public OutletEmpService getOutletEmpService() {
		return outletEmpService;
	}

	public void setOutletEmpService(OutletEmpService outletEmpService) {
		this.outletEmpService = outletEmpService;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	
	
	

}
