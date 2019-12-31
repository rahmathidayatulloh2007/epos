package com.wo.epos.common.constant;

public interface CommonConstants {

	final String FILE_SEPARATOR = System.getProperty("file.separator");

	final String BOOLEAN_FALSE = "false";

	final String BOOLEAN_TRUE = "true";

	/** Constant for login success message */
	final String MSG_LOGIN_SUCCESSFULL = "LOGIN_SUCCESSFULL";
	final String MSG_LOGIN_INVALID_CREDENTIALS = "INVALID CREDENTIALS";

	/** Constant for database access */
	final String UPDATE_PROCESS_INSERT = "INSERT ";

	final String UPDATE_PROCESS_UPDATE = "UPDATE ";

	final String UPDATE_PROCESS_DELETE = "DELETE ";

	final String SUBMIT_PROCESS_INSERT = "SUBMIT ";
	
	final String MODE_TYPE_SEARCH = "SEARCH";
	final String MODE_TYPE_ADD = "ADD";
	final String MODE_TYPE_EDIT = "EDIT";
	final String MODE_TYPE_VIEW = "VIEW";
	final String MODE_TYPE_MENU_ACCESS= "MENU_ACCESS";

	/** Constant for enabled flag */
	final String ENABLED_FLAG_TRUE = "Y";

	final String ENABLED_FLAG_FALSE = "N";

	final String N = "N";

	final String Y = "Y";

	final String SPACE = " ";

	/** Constant for date */
	final static String GLOBALDATEFORMAT = "dd/MM/yyyy";

	final static String INPUT_DATE_FORMAT = "dd-MMM-yyyy";

	final static String INPUT_DATE_TIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";

	final static String DB_DATE_FORMAT = "dd-MMM-yy";

	final static String DOCUMENT_DATE_FORMAT = "dd MMMM yyyy";

	final static String DB_DATE_FORMAT2 = "yyyy-MM-dd";

	final static String COMMONDATEFORMAT = "dd-MM-yyyy";

	final static String DOCUMENT_DATE_FORMAT_IND = "dd Bulan yyyy";

	final static String DOCUMENT_DATE_DAY_FORMAT_IND = "hari, dd Bulan yyyy";

	final static String DOCUMENT_DATE_FORMAT_HARI = "hari";

	final static String DOCUMENT_DATE_FORMAT_BULAN = "Bulan";

	final static String INPUT_DATE_LOCALE = "en";

	final static String INVALID_TIME_FORMAT_CONSTANT = "invalidTime";

	final static String DEFAULT_TIME_FORMAT = "00:00:00";

	final static String DB_ORCL_INPUT_DATE_FORMAT = "dd-Mon-yyyy";
	
	final static String INPUT_TIME_FORMAT = "HH:mm:ss";

	final static String INPUT_DATE_YEAR_FORMAT = "yyyy";

	/** Constant for session */
	final static String SESSION_PAGING_NUMBER = "SESSION_PAGING_NUMBER";

	final static String SESSION_PERSON = "SESSION_PERSON";
	
	final static String SESSION_EMPLOYEE = "SESSION_EMPLOYEE";
	
	final static String SESSION_USER = "SESSION_USER";
	
	final static String SESSION_MENU = "SESSION_MENU";

	/** Constant for math */
	final static String US_CURRENCY_PATTERN = "###,###,###,###,###.##";

	/** Constant for Parameter */
	final static String SYSTEM_PROPERTY_NO_LDAP_ADMIN_PASS = "NO_LDAP_ADMIN_PASS";
	final static String SYSTEM_PROPERTY_PAGINATION_ROWS = "PAGINATION_ROW";

	final static String SYSTEM_PROPERTY_EXTENDED_PAGINATION_ROWS = "EXTENDED_PAGINATION_ROW";

	final static int DEFAULT_PAGINATION_ROWS = 10;

	final static int DEFAULT_EXTENDED_PAGINATION_ROWS = 50;

	final static String CURR_USD = "USD";

	final static String CURR_IDR = "IDR";

	/** Constant for parameter method */
	final static String METHOD_PARAMETER = "method";

	/** Constant for administrator responsibility, executive and non-executive */
	final static String ADMINISTRATOR_RESPONSIBILITY = "administrator";

	final static String EXECUTIVE_RESPONSIBILITY = "executive";

	final static String MASS_RESPONSIBILITY = "mass";

	final static String BI_CHECKING = "BI_CHECKING";
	final static String REFERENCE_CHECKING = "REFERENCE_CHECKING";

	// start additional constant for Report
	final static String REPORT_GEN_STATUS_ON_PROGRESS = "ON_PROGRESS";
	final static String REPORT_GEN_STATUS_COMPLETE_SUCCESS = "COMPLETE";
	final static String REPORT_GEN_STATUS_COMPLETE_ERR = "ERROR";

	final static String SEPARATOR_DASH = "-";

	final static String FILE_TYPE_XLSX = ".xlsx";

	final static String SYSTEM_PROPERTY_FILE_PATH = "FILE_PATH";

	final static String MAP_KEY_CELL_FORMAT_DATA_DTL_NUMBER = "cellFormatNumber";
	final static String MAP_KEY_CELL_FORMAT_DATA_DTL_DATE = "cellFormatDate";
	final static String MAP_KEY_CELL_FORMAT_DATA_DTL_STRING = "cellFormatString";
	final static String MAP_KEY_CELL_FORMAT_HEADER_VALUE = "cellFormatHeaderValue";
	final static String MAP_KEY_CELL_FORMAT_HEADER_LABEL = "cellFormatHeaderLabel";
	final static String MAP_KEY_CELL_FORMAT_HEADER_TITLE = "cellFormatHeaderTitle";
	final static String MAP_KEY_CELL_FORMAT_COLUMN_HEADER = "cellFormatColumnHeader";
	final static String MAP_KEY_CELL_FORMAT_COLUMN_HEADER_NUMBER = "cellFormatColumnHeaderNumber";

	public static final String SEARCH_FILTER_BY_REPORT_CODE = "REPORT_CODE";
	public static final String SEARCH_FILTER_BY_NIK = "NIK";

	public static final String FND_LOOKUP_TYPE_REPORT_TYPE = "BTPN_CUSTOM_REPORT_WO";

	public static final String MAP_KEY_COLUMN_NAMES = "columnNames";
	public static final String MAP_KEY_COLUMN_NAME_ALIAS = "columnNameAlias";
	public static final String MAP_KEY_RESULTS = "results";

	public static final String ORGANIZATION_TYPE_DIREKTORAT = "DIREKTORAT";
	public static final String ORGANIZATION_TYPE_DIVISI = "DIVISI";

	public static final String CODE_BANK_BTPN_WOW = "201604";
	
	public static final String GENDER = "GENDER";
	public static final String CUSTOMER_TYPE = "CUSTOMER_TYPE";
	public static final String RELIGION = "RELIGION";
	public static final String MARITAL_STATUS = "MARITAL_STATUS";
	public static final String EMPLOYEE_STATUS = "EMPLOYEE_STATUS";
	public static final String COMPANY_TYPE = "COMPANY_TYPE";
	public static final String COMPANY_FRANCHISE = "COMPANY_FRANCHISE";
	public static final String DISC_TYPE = "DISC_TYPE";
	public static final String DISCTYPE_WITHOUT = "DISCTYPE_WITHOUT";
	public static final String DISCTYPE_PERCENT = "DISCTYPE_PERCENT";
	public static final String DISCTYPE_VALUE = "DISCTYPE_VALUE";	
	public static final String PO_TAXTYPE = "PO_TAXTYPE";
	public static final String POTAXTYPE_WITHOUT = "POTAXTYPE_WITHOUT";
	public static final String POTAXTYPE_INCLUDE = "POTAXTYPE_INCLUDE";
	public static final String POTAXTYPE_WITH = "POTAXTYPE_WITH";	
	public static final String PO_STATUS = "PO_STATUS";
	public static final String PO_NEW = "PO_NEW";
	public static final String PO_PARTIAL = "PO_PARTIAL";
	public static final String PO_CLOSE = "PO_CLOSE";
	public static final String TAX_VALUE = "TAX_VALUE";
	public static final String RN_TYPE = "RN_TYPE";
	public static final String RNTYPE_PURCHASEORDER = "RNTYPE_PURCHASEORDER";
	public static final String RNTYPE_TRANSFERITEM = "RNTYPE_TRANSFERITEM";
	public static final String DOTYPE_TRANSFERITEM = "DOTYPE_TRANSFERITEM";
	public static final String DO_NEW = "DO_NEW";
	public static final String DO_CLOSE = "DO_CLOSE";
	public static final String DELIVERY_TYPE_01 = "DELIVERY_TYPE_01";
	public static final String DELIVERY_TYPE_02 = "DELIVERY_TYPE_02";
	public static final String INSERT = "INSERT";
	public static final String UPDATE = "UPDATE";
	public static final String DELETE = "DELETE";
	public static final String SETTING_ITEM_UPLOAD_EXT 	= "IMAGE_EXT";
	public static final String SETTING_ITEM_UPLOAD_SIZE = "IMAGE_SIZE";
	public static final String REGISTER_STATUS = "REGISTER_STATUS";
	public static final String REGISTER_OPEN = "REGISTER_OPEN";
	public static final String PAYMENT_STATUS = "PAYMENT_STATUS";
	public static final String PAYMENT_CASH = "PAYMENT_CASH";
	public static final String PAYMENT_CEK = "PAYMENT_CEK";
	public static final String PAYMENT_TRANSFER = "PAYMENT_TRANSFER";
	public static final String PAYMENT_DEBIT = "PAYMENT_DEBIT";
	public static final String PAYMENT_CREDIT = "PAYMENT_CREDIT";
	public static final String EQUIPMENT_TYPE = "EQUIPMENT_TYPE";
	public static final String EQUIPMENT_TABLE = "EQUIPMENT_TABLE";
	public static final String EQUIPMENT_ORDERTAKER = "EQUIPMENT_ORDERTAKER";
	public static final String EQUIPMENT_CASHIER = "EQUIPMENT_CASHIER";
	public static final String PAYMENT_METHOD = "PAYMENT_METHOD";
	public static final String EQUIPMENT_STATUS = "EQUIPMENT_STATUS";
	public static final String EQUIPMENT_VACANT = "EQUIPMENT_VACANT";
	public static final String EQUIPMENT_OCCUPY = "EQUIPMENT_OCCUPY";
	public static final String EQUIPMENT_RESERVE = "EQUIPMENT_RESERVE";
	public static final String SO_NUMBERFORMAT = "SO_NUMBERFORMAT";
	public static final String SONUMBER_YEARLY = "SONUMBER_YEARLY";
	public static final String SONUMBER_MONTHLY = "SONUMBER_MONTHLY";
	public static final String SONUMBER_DAILY = "SONUMBER_DAILY";
	public static final String DEFAULT_TAX_TYPE = "DEFAULT_TAX_TYPE";
	public static final String TAX_INCLUDE = "TAX_INCLUDE";
	public static final String TAX_EXCLUDE = "TAX_EXCLUDE";  
	public static final String TAX_NONE = "TAX_NONE";
	public static final String DEFAULT_TAX_VALUE = "DEFAULT_TAX_VALUE";
	public static final String BUSINESS_TYPE = "BUSINESS_TYPE";
	public static final String BUSINESS_BISTRO = "BUSINESS_BISTRO";
	public static final String SO_NEW = "SO_NEW";
	public static final String SO_PROCEED = "SO_PROCEED";
	public static final String SO_PAYMENT = "SO_PAYMENT";
	public static final String SO_PENDING = "SO_PENDING";
	public static final String SO_PARTIAL = "SO_PARTIAL";
	public static final String SO_BILL = "SO_BILL";
	public static final String SO_TYPE = "SO_TYPE";
	public static final String SO_DINE_IN = "SO_DINE_IN";
	public static final String SO_DELIVERY = "SO_DELIVERY";
	public static final String SO_PICKUP = "SO_PICKUP";
	public static final String SORCPT_NUMBERFORMAT = "SORCPT_NUMBERFORMAT";
	public static final String SORCPTNUMBER_YEARLY = "SORCPTNUMBER_YEARLY";
	public static final String SORCPTNUMBER_MONTHLY = "SORCPTNUMBER_MONTHLY";
	public static final String SORCPTNUMBER_DAILY = "SORCPTNUMBER_DAILY";
	public static final String SOINV_NUMBERFORMAT = "SOINV_NUMBERFORMAT";
	public static final String SOINVNUMBER_YEARLY = "SOINVNUMBER_YEARLY";
	public static final String SOINVNUMBER_MONTHLY = "SOINVNUMBER_MONTHLY";
	public static final String SOINVNUMBER_DAILY = "SOINVNUMBER_DAILY";
	public static final String DISCOUNT_PROVIDER = "DISCOUNT_PROVIDER";
	public static final String DISCOUNT_CATEGORY = "DISCOUNT_CATEGORY";
	public static final String DELIVERY_HOLD = "DELIVERY_HOLD";
	public static final String DELIVERY_NONE = "DELIVERY_NONE";
	public static final String DELIVERY_FINISH = "DELIVERY_FINISH";
	public static final String PREPARATION_HOLD = "PREPARATION_HOLD";
	public static final String PREPARATION_NONE = "PREPARATION_NONE";
	public static final String PREPARATION_FINISH = "PREPARATION_FINISH";	
	public static final String SOINVOICE_PAYMENT = "SOINVOICE_PAYMENT";
	public static final String SOINVOICE_NEW = "SOINVOICE_NEW";

	// end additional constant for Report
	
	// constant user level
	public static final String ADMIN_LEVEL = "ADMIN_LEVEL";
	public static final String COMPANY_LEVEL = "COMPANY_LEVEL";
	public static final String OUTLET_LEVEL = "OUTLET_LEVEL";
	
	// constant stock opname
	public static final String STOCKOPNAME_NEW = "STOCKOPNAME_NEW";
	public static final String STOCKOPNAME_CLOSED = "STOCKOPNAME_CLOSED";
	public static final String STO_NUMBERFORMAT = "STO_NUMBERFORMAT";
	public static final String STONUMBER_YEARLY = "STONUMBER_YEARLY";
	public static final String STONUMBER_MONTHLY = "STONUMBER_MONTHLY";
	public static final String STONUMBER_DAILY = "STONUMBER_DAILY";
	
	// constant stock opname
	public static final String CLOSE_REASON = "Barang Sudah Diterima Semua";

}