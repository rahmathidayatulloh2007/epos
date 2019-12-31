package com.wo.epos.module.uam.parameter.bean;

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
import com.wo.epos.common.util.FacesUtils;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.common.vo.SearchValueObject;
import com.wo.epos.module.uam.parameter.model.Parameter;
import com.wo.epos.module.uam.parameter.service.ParameterService;
import com.wo.epos.module.uam.parameter.vo.ParameterVO;

@ManagedBean(name = "parameterSearchBean")
@ViewScoped
public class ParameterSearchBean extends CommonBean implements Serializable{
	
	private static final long serialVersionUID = 8214384900518604439L;
	static Logger logger = Logger.getLogger(ParameterSearchBean.class);
	
	@ManagedProperty(value = "#{parameterService}")
	private ParameterService parameterService;
	
	private ParameterVO parameterVOSearchDialog = new ParameterVO();	

	private PagingTableModel<Parameter> pagingParameter;	
	
	private List<ParameterVO> parameterList = new ArrayList<ParameterVO>();
	
	private List<ParameterVO> selectedParameters;
	
	private boolean disableFlag;
	private boolean disableFlagAdd;	
	private boolean selectAll;
	private boolean disableSearchAll;
	
	private String searchAll;
	
	private Integer checkSearch;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void postConstruct(){
		super.init();
		parameterVOSearchDialog = new ParameterVO();
		
		pagingParameter = new PagingTableModel(parameterService, paging);
		
		disableFlag = false;
		disableFlagAdd = true;
		disableSearchAll = false;

		checkSearch = 0;
		
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
			 pagingParameter.setSearchCriteria(searchCriteria);
		}else{
			searchDialog();
		}
	}
	
	public void modeDelete(List<ParameterVO> parameterVOList){
		try
		{
			for(int i=0; i<parameterVOList.size(); i++)
			{
				ParameterVO parameterVOTemp = (ParameterVO)parameterVOList.get(i);
				
				parameterService.delete(parameterVOTemp.getParameterCode());
			}
			
			facesUtils.addFacesMsg(
				FacesMessage.SEVERITY_INFO, 
				"frm001:messages", 
                facesUtils.retrieveMessage("proses_common", facesUtils.retrieveMessage("common_msg_deleted")), 
                null);
		}
		catch(Exception ex){
			ex.printStackTrace();
			facesUtils.addFacesMsg(
					FacesMessage.SEVERITY_ERROR,
					"frm001:messages",
					facesUtils.retrieveMessage("errorProcessDeleteAlreadyUses", facesUtils.retrieveMessage("formParameterTitle")),
					null);	    
		}
	}
	
	public void selectAllData(javax.faces.event.AjaxBehaviorEvent e) {
        int total = 0;
        if (selectAll) {
            for (int i = 0; i < parameterList.size(); i++) {
                ((ParameterVO) parameterList.get(i)).setChecked(true);
                total++;
            }
        } else {
            for (int i = 0; i < parameterList.size(); i++) {
            	 ((ParameterVO) parameterList.get(i)).setChecked(false);
            }
        }
    }
	
	public void clear(){
		searchAll = "";
		parameterVOSearchDialog = new ParameterVO();
		checkSearch = 0;
		search();
	}
	
	public void clearDialog(){
		searchAll = "";
		checkSearch = 0;
		parameterVOSearchDialog = new ParameterVO();
		search();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void searchDialog(){
		checkSearch = 1;
		disableSearchAll = true;
		List<SearchObject> searchCriteria = new ArrayList<SearchObject>();
		StringBuilder builder = new StringBuilder();
	   
		if(parameterVOSearchDialog.getParameterCode() !=null && StringUtils.isNotBlank(parameterVOSearchDialog.getParameterCode())) {
			builder.append(facesUtils.getResourceBundleStringValue("formParameterCode"));
			builder.append(":"+parameterVOSearchDialog.getParameterCode()+",");
			searchCriteria.add(new SearchValueObject("parameterCode", parameterVOSearchDialog.getParameterCode()));
		}			
		if(parameterVOSearchDialog.getParameterName() !=null && StringUtils.isNotBlank(parameterVOSearchDialog.getParameterName())) {
			builder.append(facesUtils.getResourceBundleStringValue("formParameterName"));
			builder.append(":"+parameterVOSearchDialog.getParameterName()+",");
			searchCriteria.add(new SearchValueObject("parameterName", parameterVOSearchDialog.getParameterName()));
		}
		if(parameterVOSearchDialog.getParameterDesc() !=null && StringUtils.isNotBlank(parameterVOSearchDialog.getParameterDesc())) {
			builder.append(facesUtils.getResourceBundleStringValue("formParameterDescription"));
			builder.append(":"+parameterVOSearchDialog.getParameterDesc()+",");
			searchCriteria.add(new SearchValueObject("parameterDesc", parameterVOSearchDialog.getParameterDesc()));
		}
		if(parameterVOSearchDialog.getDetailResume() !=null && StringUtils.isNotBlank(parameterVOSearchDialog.getDetailResume())) {
			builder.append(facesUtils.getResourceBundleStringValue("formParameterDetail"));
			builder.append(":"+parameterVOSearchDialog.getDetailResume()+",");
			searchCriteria.add(new SearchValueObject("detailResume", parameterVOSearchDialog.getDetailResume()));
		}
		
		
		builder = builder.replace(builder.toString().lastIndexOf(","), builder.toString().lastIndexOf(",") + 1, "");
		searchAll = builder.toString();
		
		pagingParameter.setSearchCriteria(searchCriteria);
		
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

	public FacesUtils getFacesUtils() {
		return facesUtils;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public String getSearchAll() {
		return searchAll;
	}

	public void setSearchAll(String searchAll) {
		this.searchAll = searchAll;
	}

	public PagingTableModel<Parameter> getPagingParameter() {
		return pagingParameter;
	}

	public void setPagingParameter(PagingTableModel<Parameter> pagingParameter) {
		this.pagingParameter = pagingParameter;
	}

	public List<ParameterVO> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<ParameterVO> parameterList) {
		this.parameterList = parameterList;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public List<ParameterVO> getSelectedParameters() {
		return selectedParameters;
	}

	public void setSelectedParameters(List<ParameterVO> selectedParameters) {
		this.selectedParameters = selectedParameters;
	}

	public ParameterVO getParameterVOSearchDialog() {
		return parameterVOSearchDialog;
	}

	public void setParameterVOSearchDialog(ParameterVO parameterVOSearchDialog) {
		this.parameterVOSearchDialog = parameterVOSearchDialog;
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

	
	
	
}
