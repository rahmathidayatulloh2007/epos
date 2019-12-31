/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.bean;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.util.FacesUtils;

/**
 *
 * @author destri
 */
@ManagedBean(name = "mbUploadSession")
@SessionScoped
public class MbUploadSession {
    private static Logger logger = Logger.getLogger(MbUploadSession.class);
    
    @ManagedProperty(value = "#{facesUtil}")
    private FacesUtils facesUtil;
    
    
    private Map<String, String> mapLastUploadId = new HashMap<String, String>();
    private Map<String, Map<String, UploadedFile>> mapUpload = new HashMap<String, Map<String, UploadedFile>>();
    private UploadedFile file;
//    private String lastUploadId;
//    private StreamedContent vImageMobileStreamedContent;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void saveFile() {
    	String str = file.getFileName();
    	String ext = str.substring(str.lastIndexOf('.'), str.length());
    	if((ext.toLowerCase()).equals(".jpeg") || (ext.toLowerCase()).equals(".jpg") || (ext.toLowerCase()).equals(".doc") || (ext.toLowerCase()).equals(".docx") 
    			|| (ext.toLowerCase()).equals(".xls") || (ext.toLowerCase()).equals(".xlsx") || (ext.toLowerCase()).equals(".pdf")) {
    		String timestamp = facesUtil.retrieveRequestParam("timestamp");
    		String funcId = facesUtil.retrieveRequestParam("funcId");

    		Map<String, UploadedFile> map = getMapUpload(funcId);


    		map.put(timestamp, file);
    		mapLastUploadId.put(funcId, timestamp);
    	}else{
    		facesUtil.addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                "frmKpbEntry:fileUpload", 
	                "File tipe harus jpeg, jpg, doc (docx), xls (xlsx), pdf", 
	                null);
    	}
        //RequestContext.getCurrentInstance().execute("toogleConditionShowedEnableStatus();");
//        return "uploadFrame.xhtml";
    }
    
    public static double bytesToMeg(double bytes) {
		
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
    
    public void saveFile1(String inputId) {
    	if(file!=null) {
    	
	    	boolean valid = true;
	    	
	    	String str = file.getFileName();
	    	String ext = str.substring(str.lastIndexOf('.'), str.length());
	    	if((ext.toLowerCase()).equals(".jpeg") || (ext.toLowerCase()).equals(".jpg") || (ext.toLowerCase()).equals(".doc") || (ext.toLowerCase()).equals(".docx") 
	    			|| (ext.toLowerCase()).equals(".xls") || (ext.toLowerCase()).equals(".xlsx") || (ext.toLowerCase()).equals(".pdf")) {
	    		valid = true;
	    	}
	    	else {
	    		valid = false;
	    		
	    		facesUtil.addFacesMsg(
	                FacesMessage.SEVERITY_ERROR, 
	                inputId, 
	                "Tipe file harus dalam bentuk jpeg, jpg, doc (docx), xls (xlsx), pdf", 
	                null);
	    	}
	    	
	    	if(valid) {
	    		if(str.length()>100) {
	    			valid = false;
	    			
	    			facesUtil.addFacesMsg(
		                FacesMessage.SEVERITY_ERROR, 
		                inputId, 
		                "Filename maksimal 100 karakter", 
		                null);
	    		}
	    	}
	    	
	    	if(valid) {
	    		double megaByte = bytesToMeg(file.getSize());
	    		
	    		if(megaByte>5d) {
	    			valid = false;
	    			
	    			facesUtil.addFacesMsg(
		                FacesMessage.SEVERITY_ERROR, 
		                inputId, 
		                "Ukuran file total maksimal 5 MB", 
		                null);
	    		}
	    	}
	    	
	    	if(valid) {
		    	String timestamp = facesUtil.retrieveRequestParam("timestamp");
				String funcId = facesUtil.retrieveRequestParam("funcId");
		
				Map<String, UploadedFile> map = getMapUpload(funcId);
		
				map.put(timestamp, file);
				mapLastUploadId.put(funcId, timestamp);
	    	}	
    	}	
    }
    
    private Map<String, UploadedFile> getMapUpload(String funcId) {
    	Map<String, UploadedFile> map = mapUpload.get(funcId);
    	if (map == null) {
    		map = new HashMap<String, UploadedFile>();
    		mapUpload.put(funcId, map);
    	}
    	return map;
    }
    
    public Long timestamp() {
    	return System.currentTimeMillis();
    }
    
    public UploadedFile getFile(String funcId, String id) {
    	return getMapUpload(funcId).get(id);
    }

    public void removeFile(String funcId, String id) {
    	getMapUpload(funcId).remove(id);
    }

    public void clearAllUploadedFile(String funcId) {
    	getMapUpload(funcId).clear();
    	mapLastUploadId.remove(funcId);
    }
    
	/**
	 * @return the facesUtil
	 */
	public FacesUtils getFacesUtil() {
		return facesUtil;
	}

	/**
	 * @param facesUtil the facesUtil to set
	 */
	public void setFacesUtil(FacesUtils facesUtil) {
		this.facesUtil = facesUtil;
	}
    

	public String getLastUploadId(String funcId) {
		return mapLastUploadId.get(funcId);
	}
    
}
