package com.wo.epos.common.exception;

import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;

import com.wo.epos.common.util.FacesUtils;

public class ExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {

    private javax.faces.context.ExceptionHandlerFactory parent;

    public ExceptionHandlerFactory(javax.faces.context.ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new CustomExceptionHandler(result);
        return result;
    }
    
    public static void getSQLExceptionErrorMessage(final SQLException ex, FacesUtils facesUtils) {
		String errorMessage;
		final int errorCode = ex.getErrorCode();
		switch (Math.abs(errorCode)) {
			case 1 		: 	errorMessage = facesUtils.getResourceBundleStringValue("errorAlreadyExists"); //"Unique constraint violated. (Invalid data has been rejected)";
					 		break;
			case 600 	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBInternalError"); //"Internal error (contact support)";
							break;
			case 3113 	: 	errorMessage = facesUtils.getResourceBundleStringValue("errorDBEndOfFile"); //"End-of-file on communication channel (Network connection lost)";
							break;
			case 3114 	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBNotConnectedToOracle"); //"Not connected to ORACLE";
							break;
			case 942  	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTableNotExist"); //"Table or view does not exist";
							break;
			case 1017	: 	errorMessage = facesUtils.getResourceBundleStringValue("errorDBInvalidUserName"); //"Invalid Username/Password";
							break;
			case 1031	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBInsufficientPriviledges"); //"Insufficient privileges";
							break;
			case 1034	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBOracleDown"); //"Oracle not available (the database is down)";
							break;
			case 1403	:	errorMessage = facesUtils.getResourceBundleStringValue("errorNoDataFound"); //"No data found";
							break;
			case 12154	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTNSServiceName"); //"TNS:could not resolve service name";
							break;
			case 12203	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTNSUnableToConnect"); //"TNS:unable to connect to destination";
							break;
			case 12500	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTNSStartFail"); //"TNS:listener failed to start a dedicated server process";
							break;
			case 12545	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTNSLookupFail"); //"TNS:name lookup failure";
							break;
			case 12560	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTNSAdapterError"); //"TNS:protocol adapter error";
							break;
			case 54		:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBResourceBusy"); //"Resource busy and acquire with NOWAIT specified";
							break;
			case 12505	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTNSListener"); //"TNS:listener does not currently know of SID given in connect descriptor";
							break;
			case 1012	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBNotLoggedOn"); //"NOT_LOGGED_ON";
							break;
			case 51		:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBTimeout"); //"TIME_OUT_ON_RESOURCE";
							break;
			case 6502	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBValueError"); //"VALUE_ERROR";
							break;
			case 1400	: 	errorMessage = facesUtils.getResourceBundleStringValue("errorDBValueNull");; //"cannot insert NULL into column";
							break;
			case 2291	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBIntegrityParentNotFound");; //"integrity constraint <constraint name> violated - parent key not found";
							break;
			case 2292	:	errorMessage = facesUtils.getResourceBundleStringValue("errorDBIntegrityChildFound");; //"integrity constraint violated – child record found";
							break;
		    default 	: 	errorMessage = ex.getMessage();
		    				break;
		}
		facesUtils.addFacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage);
	}
}