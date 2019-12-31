/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.lov.bean;



import java.io.Serializable;
import java.util.*; 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.wo.epos.common.lov.bean.SelectorModel.SelectorInfo;
import com.wo.epos.common.lov.converter.ItemToDisplayValueConverter;
import com.wo.epos.common.lov.service.SelectorNativeService;
import com.wo.epos.common.lov.service.SelectorService;

/**
 *
 * @author destri.hs
 */
@ManagedBean(name = "mbLovCommon")
@ViewScoped
public class MbLovCommon implements Serializable {
	private static final long serialVersionUID = -4105506447875366650L;
	
	public static final String JS_ONSELECTED = "JS_ONSELECTED";
    public static final String JS_FUNCTION_ONSELECTED = "JS_FUNCTION_ONSELECTED";
    
    @ManagedProperty(value="#{selectorService}")
    private SelectorService selectorService;
    
    @ManagedProperty(value="#{selectorNativeService}")
    private SelectorNativeService selectorNativeService;
    
    
    //@ManagedProperty(value="#{facesUtil}")
    //private FacesUtil facesUtils;
    

    private Map <String, SelectorModel> mapModel = new HashMap<String, SelectorModel>();
    
    public String retrieveRequestParam(String key) {
        return FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get(key);
    }
    
    public void reset(String compClientId) {
        //js function first, and then js onselected event follow
        String jsOnSelected = retrieveRequestParam(JS_ONSELECTED);
        String jsFunctionOnSelected = retrieveRequestParam(JS_FUNCTION_ONSELECTED);
        
        
        
        SelectorModel model = mapModel.get(compClientId);
        model.setJsOnSelected(jsOnSelected);
        model.setJsFunctionOnSelected(jsFunctionOnSelected);
        model.search("");
        
        setToFirstPage(compClientId);
    }
    
    private void setToFirstPage(String compClientId) {
        DataTable tbl = (DataTable)FacesContext.getCurrentInstance().getViewRoot()
                .findComponent(compClientId)
                .findComponent("selectorForm")
                .findComponent("selectorTable");
        tbl.setFirst(0);
    }
    
    public void search () {
        String clientId = retrieveRequestParam("PARAM_CLIENT_ID");
        String userQuery = retrieveRequestParam("PARAM_SEARCH");
        
        reset(clientId);
        SelectorModel model = mapModel.get(clientId);
        model.search(userQuery);

    }
    
    public String getUserQueryString(String compClientId) {
        SelectorModel model = mapModel.get(compClientId);
        if (model != null) {
            return model.getUserQueryString();
        } else {
            return "";
        }
    }
    
    public SelectorModel getSelectorModel(
            String compClientId,
            SelectorModel.SelectorInfo selectorInfo) {
        SelectorModel model = mapModel.get(compClientId);
        if (model == null) {
            if (selectorInfo.getIsHql()) {
                model = new SelectorModel(selectorService, selectorInfo, compClientId, 10);                
            } else {
                model = new SelectorModel(selectorNativeService, selectorInfo, compClientId, 10);                
            }
            mapModel.put(compClientId, model);
        }
        return model;
    }
    
    public DataModel getDataModel(
            String compClientId,
            SelectorModel.SelectorInfo selectorInfo
            ) {
    	
        SelectorModel model = getSelectorModel(compClientId, selectorInfo);
        return model.getDataModel();
    }
    
    public List<ColumnModel> getColumnModel(String compClientId) {
        SelectorModel model = mapModel.get(compClientId);
        if (model != null) {
             return model.getColumnModels();
        } else {
            return new ArrayList<ColumnModel>();
        }        
    }
    
    public void select(
            String compClientId, 
            String widgetVar,
            SelectorListener beanListener,
            Object item,
            SelectorModel.SelectorInfo selectorInfo) {
//        System.out.println("select ");
        beanListener.itemSelected(compClientId, widgetVar, item);
        
        invokeJs(compClientId, selectorInfo, item);
    }
    
    private void invokeJs(String compClientId, SelectorModel.SelectorInfo selectorInfo, Object item) {
        String jsParamAware = getSelectorModel(compClientId, selectorInfo).constructJsFunctionParamAware(item);
        String jsOnSelected = getSelectorModel(compClientId, selectorInfo).getJsOnSelected();
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(jsParamAware)) {
            sb.append(jsParamAware);
        }
        if (StringUtils.isNotBlank(jsOnSelected)) {
            sb.append(jsOnSelected);
        }
        
        if (StringUtils.isNotBlank(sb.toString())) {
            RequestContext.getCurrentInstance().execute(sb.toString());            
        }        
    }
    
//    public void select(
//            String compClientId, 
//            String widgetVar,
//            SelectorListenerEventAware beanListener,
//            Object item) {
////        System.out.println("select event aware");
//        //beanListener.itemSelected(compClientId, widgetVar, item);
//    }

    public void setSelectorService(SelectorService selectorService) {
        this.selectorService = selectorService;
    }

   /* public void setFacesUtils(FacesUtil facesUtils) {
        this.facesUtils = facesUtils;
    }*/

    public boolean isNumeric(String prop) {
        return StringUtils.isNumeric(prop);
    }


    public Boolean alignLeft(Object value) {
        if (value instanceof Number) {
            return false;
        } else {
            return true;
        }
    }
    
    public Boolean alignRight(Object value) {
        if (value instanceof Number) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setSelectorNativeService(SelectorNativeService selectorNativeService) {
        this.selectorNativeService = selectorNativeService;
    }
    
    public void resetDbQuery(String compClientId) {
    	mapModel.put(compClientId, null);
    }
    
    public Object convertToDisplayValue(SelectorInfo selectorInfo, int idx, Object item) {
    	List <ItemToDisplayValueConverter> listOfConverter = 
    			selectorInfo.getListOfConverter();
    	
    	if (listOfConverter != null && listOfConverter.size() > idx) {
    		ItemToDisplayValueConverter conv = listOfConverter.get(idx);
    		if (conv != null) {
        		return conv.convertToDisplayValue(item);
    		} else {
    			return item;
    		}
    	} else {
    		return item;
    	}
    }

}
