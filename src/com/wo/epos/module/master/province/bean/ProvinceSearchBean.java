package com.wo.epos.module.master.province.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.wo.epos.common.bean.CommonBean;
import com.wo.epos.common.paging.PagingTableModel;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.master.province.service.ProvinceService;
import com.wo.epos.module.master.province.vo.ProvinceVO;

@ManagedBean(name = "provinceSearchBean")
@ViewScoped
public class ProvinceSearchBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(ProvinceSearchBean.class);
	
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;
		
	private ProvinceVO provinceVOSearchDialog = new ProvinceVO();	

	private PagingTableModel<ProvinceVO> pagingProvince;	
	
	private List<ProvinceVO> provinceList = new ArrayList<ProvinceVO>();
	
	private List<ProvinceVO> selectedProvinces;
	
	private boolean disableSearchAll;
	private boolean selectAll;
	
	private String searchAll;	
			
	private Integer checkSearch;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		if(userSession != null){
		provinceVOSearchDialog = new ProvinceVO();
		
		pagingProvince = new PagingTableModel(provinceService, 10);
		//provinceList = provinceService.provinceSearch();	
		
		disableSearchAll = false;
		checkSearch = 0;
		}
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
			
			 disableSearchAll = false;
			 pagingProvince.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		
		if(provinceVOSearchDialog.getProvinceCode() !=null && !provinceVOSearchDialog.getProvinceCode().equals("")) {
			builder.append(facesUtils.getResourceBundleStringValue("formProvinceCode"));
			builder.append(":"+provinceVOSearchDialog.getProvinceCode()+",");
			searchCriteria.add(new SearchValueObject("provinceCode", provinceVOSearchDialog.getProvinceCode()));
		}
		
		if(provinceVOSearchDialog.getProvinceName() !=null && StringUtils.isNotBlank(provinceVOSearchDialog.getProvinceName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formUmName"));
			builder.append(":"+provinceVOSearchDialog.getProvinceName()+",");
			searchCriteria.add(new SearchValueObject("provinceName", provinceVOSearchDialog.getProvinceName()));
		}	
				
		if(builder.toString() !=null && !builder.toString().equals("")){
		    builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		}
		searchAll = builder.toString();
		
		pagingProvince.setSearchCriteria(searchCriteria);
		
	}
	
	public void clear(){
		searchAll = "";
		provinceVOSearchDialog = new ProvinceVO();		
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		provinceVOSearchDialog = new ProvinceVO();	
		search();
	}	
	
	public void modeDelete(List<ProvinceVO> provinceVOList){
		try
		{
			for(int i=0; i<provinceVOList.size(); i++)
			{
				ProvinceVO provinceVOTemp = (ProvinceVO)provinceVOList.get(i);
				
				provinceService.delete(provinceVOTemp.getProvinceId());
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
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formProvinceTitle")),
					null);	  
		}
	}
	
	public ProvinceService getProvinceService() {
		return provinceService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public PagingTableModel<ProvinceVO> getPagingProvince() {
		return pagingProvince;
	}

	public void setPagingProvince(PagingTableModel<ProvinceVO> pagingProvince) {
		this.pagingProvince = pagingProvince;
	}

	public List<ProvinceVO> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<ProvinceVO> provinceList) {
		this.provinceList = provinceList;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public List<ProvinceVO> getSelectedProvinces() {
		return selectedProvinces;
	}

	public void setSelectedProvinces(List<ProvinceVO> selectedProvinces) {
		this.selectedProvinces = selectedProvinces;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ProvinceSearchBean.logger = logger;
	}

	public Integer getCheckSearch() {
		return checkSearch;
	}

	public void setCheckSearch(Integer checkSearch) {
		this.checkSearch = checkSearch;
	}

	public boolean isDisableSearchAll() {
		return disableSearchAll;
	}

	public void setDisableSearchAll(boolean disableSearchAll) {
		this.disableSearchAll = disableSearchAll;
	}

	public ProvinceVO getProvinceVOSearchDialog() {
		return provinceVOSearchDialog;
	}

	public void setProvinceVOSearchDialog(ProvinceVO provinceVOSearchDialog) {
		this.provinceVOSearchDialog = provinceVOSearchDialog;
	}

	
	
	
}
