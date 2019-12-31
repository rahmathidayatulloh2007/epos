package com.wo.epos.common.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.wo.epos.common.util.FacesUtils;
import com.wo.epos.common.vo.UploadFileVO;

@ManagedBean(name = "mbImageStreamer")
@SessionScoped
public class MbImageStreamer implements Serializable {
    
	private static final long serialVersionUID = -5198978078419673368L;

	@ManagedProperty(value = "#{facesUtil}")
    private FacesUtils facesUtil;
    
    @ManagedProperty(value = "#{mbUploadSession}")
    private MbUploadSession mbUploadSession;
    
    private Map<String, List<UploadFileVO>> mapListUploadedFile = 
    		new HashMap<String, List<UploadFileVO>>();
    
    
//	private List<VoUploadFile> inputListUploadedFile = new ArrayList<VoUploadFile>();

	
	public void clearUploadList(String funcId) {
		List <UploadFileVO> list = getListFromMap(funcId);
		list.clear();
	}
	
	public List <UploadFileVO> getListFromMap(String funcId) {
		List <UploadFileVO> list = mapListUploadedFile.get(funcId);
		if (list == null) {
			list = new ArrayList<UploadFileVO>();
			mapListUploadedFile.put(funcId, list);
		}
		return list;
	}
	
	public void removeUpload(String funcId, String uploadId) {
		UploadFileVO fileToRemove = null;
		List <UploadFileVO> listUploadByFuncId = getListFromMap(funcId);
		for (UploadFileVO file : listUploadByFuncId) {
			if (StringUtils.equals(file.getUploadId(), uploadId)) {
				fileToRemove = file;
				break;
			}
		}
		if (fileToRemove != null) {
			listUploadByFuncId.remove(fileToRemove);
		}
	}

	public void clearUpload(String funcId) {
		UploadFileVO fileToRemove = null;
		List <UploadFileVO> listUploadByFuncId = getListFromMap(funcId);
		listUploadByFuncId.clear();
	}
	
    public StreamedContent getImage() throws IOException {
    	FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String uploadId = context.getExternalContext().getRequestParameterMap().get("uploadId");
            String funcId = context.getExternalContext().getRequestParameterMap().get("funcId");
            UploadFileVO upFile = getUploadFileById(funcId, uploadId);
            if (upFile != null) {
                return upFile.buildStreamedContent();
            } else {
            	return new DefaultStreamedContent();
            }
        }
    }
    
    private UploadFileVO getUploadFileById(String funcId, String uploadId) {
    	List <UploadFileVO> listUpload = getListFromMap(funcId);
    	for (UploadFileVO file : listUpload) {
    		if (StringUtils.equals(file.getUploadId(), uploadId)) {
    			return file;
    		}
    	}
    	return null;
    }

	public int getUploadFileIndexById(String funcId, String uploadId) {
		List<UploadFileVO> listUpload = getListFromMap(funcId);
		UploadFileVO file = null;
		for (int i = 0; i < listUpload.size(); i++) {
			file = listUpload.get(i);
			if (StringUtils.equals(file.getUploadId(), uploadId)) {
				return i;
			}
		}

		return -1;
	}
    
	public void updateListUploadFileFromSession() {
		String lastUploadId = facesUtil.retrieveRequestParam("lastUploadId");
		String funcId = facesUtil.retrieveRequestParam("funcId");
		
		UploadedFile lastUploadFile = mbUploadSession.getFile(
				//Constants.UPLOAD_FUNC_ID_ENTRY_AUDIT
				funcId, lastUploadId);
		mbUploadSession.removeFile(
				//Constants.UPLOAD_FUNC_ID_ENTRY_AUDIT
				funcId, lastUploadId);
		
		if (lastUploadFile != null) {
			addToListUpload(funcId, lastUploadId, lastUploadFile);
			
		}
		

	}
	
	public void updateListUploadFileFromSession1(String funcId, String lastUploadId) {
		
		UploadedFile lastUploadFile = mbUploadSession.getFile(
				//Constants.UPLOAD_FUNC_ID_ENTRY_AUDIT
				funcId, lastUploadId);
		mbUploadSession.removeFile(
				//Constants.UPLOAD_FUNC_ID_ENTRY_AUDIT
				funcId, lastUploadId);
		
		if (lastUploadFile != null) {
			addToListUpload(funcId, lastUploadId, lastUploadFile);
			
		}
		

	}
	
	private void addToListUpload(String funcId, String uploadId, UploadedFile file) {
		List <UploadFileVO> listUploadFile = getListFromMap(funcId);
		int index = getListUploadFileIndex(listUploadFile, uploadId);
		//if (!listContainsUploadId(listUploadFile, uploadId)) {
		System.out.println("index = " + index);
		System.out.println("uploadId = " + uploadId);
		if(index < 0) {
			UploadFileVO newUploadFile = new UploadFileVO(uploadId, file);
			listUploadFile.add(newUploadFile);
		} else {
			UploadFileVO voUploadFile = listUploadFile.get(index);
			voUploadFile.setFile(file);
		}
	}
	
	private boolean listContainsUploadId(List <UploadFileVO> listFile, String uploadId) {
		for (UploadFileVO uploadFile : listFile) {
			if (StringUtils.equals(uploadFile.getUploadId(), uploadId)) {
				return true;
			}
		}
		
		return false;
	}

	private int getListUploadFileIndex(List<UploadFileVO> listFile,
			String uploadId) {
		UploadFileVO uploadFile = null;
		for (int i = 0; i < listFile.size(); i++) {
			uploadFile = listFile.get(i);
			if (StringUtils.equals(uploadFile.getUploadId(), uploadId)) {
				return i;
			}
		}

		return -1;
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

	/**
	 * @return the mbUploadSession
	 */
	public MbUploadSession getMbUploadSession() {
		return mbUploadSession;
	}

	/**
	 * @param mbUploadSession the mbUploadSession to set
	 */
	public void setMbUploadSession(MbUploadSession mbUploadSession) {
		this.mbUploadSession = mbUploadSession;
	}
	
	public List <UploadFileVO> getInputListUploadedFile(String funcId) {
		return getListFromMap(funcId);
	}
	
	public void addUpload(String funcId, UploadFileVO uploadFile) {
		List <UploadFileVO> listUploadFile = getListFromMap(funcId);
		listUploadFile.add(uploadFile);
	}
	
	public void addUpload(String funcId, String uploadId, UploadedFile file) {
		addToListUpload(funcId, uploadId, file);
	}
	
	public String returnCurrentTime() {
		Long curr = System.currentTimeMillis();
		
		return curr.toString();
	}
}
