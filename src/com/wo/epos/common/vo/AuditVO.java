package com.wo.epos.common.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.util.FacesUtils;

public class AuditVO implements Serializable {
	private static final long serialVersionUID = 4094847638141374725L;
	protected String table_name;
	protected String fields;
	protected String contents = "";
	protected Timestamp creation_date;
	protected String created_by;
	protected Timestamp last_update_date;
	protected String last_update_by;
	protected Integer deleted;
	protected String enabled_flag;
	protected boolean enabled_flag2;
	protected String enabled_flag_text;
	private Integer audit_no = new Integer(0);
	private FacesUtils fu = new FacesUtils();
	
	protected String author_address;
	// for getting local IP and MAC Address by Applet
	protected String local_ip;
	protected String mac_address;
	
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		try {
			Field[] fields = AuditVO.class.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
					sb.append(",").append(fields[i].getName()).append("=").append(fields[i].get(this));
			}
		} catch (IllegalAccessException iae) {}
		return sb.toString();
	}
	
	public Integer getDeleted() { return deleted; }
	public void setDeleted(Integer deleted) { this.deleted = deleted; }

	public String getContents() { return contents; }
	public void setContents(String contents) { this.contents = contents; }

	public String getFields() { return fields; }
	public void setFields(String fields) { this.fields = fields; }
	
    public void setAuditTrail() {//User user, String process) {
		setLast_update_by("");
		setCreated_by("");
		
        setAuthor_address("");
        setLocal_ip("");
        setMac_address("");
		/*
		setLastUpdBy(user.getUserCode());
		setCreatedBy(user.getUserCode());
		setLastUpdProcess(process);
		setOwner(user.getBranchCode());
		
        setAuthorAddress(user.getAuthorAddress());
        setLocalIp(user.getLocalIp());
        setMacAddress(user.getMacAddress());
        */
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String createdBy) {
		created_by = createdBy;
	}
	
	public Timestamp getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(Timestamp lastUpdateDate) {
		last_update_date = lastUpdateDate;
	}

	public String getLast_update_by() {
		return last_update_by;
	}

	public void setLast_update_by(String lastUpdateBy) {
		last_update_by = lastUpdateBy;
	}

	public Integer getAudit_no() {
		return audit_no;
	}

	public void setAudit_no(Integer auditNo) {
		audit_no = auditNo;
	}

	public String getAuthor_address() {
		return author_address;
	}

	public void setAuthor_address(String authorAddress) {
		author_address = authorAddress;
	}

	public String getLocal_ip() {
		return local_ip;
	}

	public void setLocal_ip(String localIp) {
		local_ip = localIp;
	}

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String macAddress) {
		mac_address = macAddress;
	}

	public Timestamp getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Timestamp creationDate) {
		creation_date = creationDate;
	}

	public String getEnabled_flag() {
		if (enabled_flag == null || enabled_flag.equals(""))
			enabled_flag = CommonConstants.ENABLED_FLAG_TRUE;
		
		return enabled_flag;
	}

	public void setEnabled_flag(String enabledFlag) {
		this.enabled_flag = enabledFlag;
		
		if (enabledFlag != null && !enabledFlag.equals("")) {
			if (enabledFlag.equals(CommonConstants.ENABLED_FLAG_TRUE) && !getEnabled_flag2()) {
				setEnabled_flag2(true);
			} else if (enabledFlag.equals(CommonConstants.ENABLED_FLAG_FALSE) && getEnabled_flag2()) {
				setEnabled_flag2(false);
			}
		} else {
			setEnabled_flag(CommonConstants.ENABLED_FLAG_TRUE);
		}
	}

	public boolean getEnabled_flag2() {
		return enabled_flag2;
	}

	public void setEnabled_flag2(boolean enabledFlag2) {
		enabled_flag2 = enabledFlag2;
		
		if(enabled_flag != null && !enabled_flag.equals("")) {
			if (enabled_flag2 && enabled_flag.equals(CommonConstants.ENABLED_FLAG_FALSE))
				setEnabled_flag(CommonConstants.ENABLED_FLAG_TRUE);
			else if (!enabled_flag2 && enabled_flag.equals(CommonConstants.ENABLED_FLAG_TRUE)) {
				setEnabled_flag(CommonConstants.ENABLED_FLAG_FALSE);
			}
		} else {
			if (enabled_flag2)
				setEnabled_flag(CommonConstants.ENABLED_FLAG_TRUE);
			else
				setEnabled_flag(CommonConstants.ENABLED_FLAG_FALSE);
		}
	}

	public String getEnabled_flag_text() {
		if (enabled_flag2)
			enabled_flag_text = fu.getResourceBundleStringValue("textEnabledFlagTrue");
		else 
			enabled_flag_text = fu.getResourceBundleStringValue("textEnabledFlagFalse");
		return enabled_flag_text;
	}

	public void setEnabled_flag_text(String enabled_flag_text) {
		this.enabled_flag_text = enabled_flag_text;
	}
	
}