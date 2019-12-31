package com.wo.epos.common.vo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

public class UploadFileVO {
	private String uploadId;
	private UploadedFile file;
	private byte[] contents;
	//private DefaultStreamedContent streamedContent;
	private String fileName;
	private String createBy;
	private Date createDate;
	
	private String absoluteSaveFilePath;
	
	public UploadFileVO(String uploadId, UploadedFile file) {
		super();
		this.uploadId = uploadId;
		this.file = file;
	}

	public UploadFileVO(String uploadId, byte[] contents) {
		super();
		this.uploadId = uploadId;
		this.contents = contents;
	}
	
	public UploadFileVO(String uploadId, byte[] contents, String fileName, String createBy, Date createDate) {
		super();
		this.uploadId = uploadId;
		this.contents = contents;
		this.fileName = fileName;
		this.createBy = createBy;
		this.createDate = createDate;
	}



	/**
	 * @return the uploadId
	 */
	public String getUploadId() {
		return uploadId;
	}




	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}




	/**
	 * @return the streamedContent
	 */
	public DefaultStreamedContent buildStreamedContent() {
		try {
			if (file != null) {
				return new DefaultStreamedContent(file.getInputstream());
			} else if (contents != null){
				return new DefaultStreamedContent(new ByteArrayInputStream(contents));
			} else {
				return new DefaultStreamedContent();
			}
		} catch (IOException e) {
			e.printStackTrace();
			
			return new DefaultStreamedContent();
		}

	}




	/**
	 * @return the absoluteSaveFilePath
	 */
	public String getAbsoluteSaveFilePath() {
		return absoluteSaveFilePath;
	}




	/**
	 * @param absoluteSaveFilePath the absoluteSaveFilePath to set
	 */
	public void setAbsoluteSaveFilePath(String absoluteSaveFilePath) {
		this.absoluteSaveFilePath = absoluteSaveFilePath;
	}




	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
