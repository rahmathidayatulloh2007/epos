package com.wo.epos.module.sales.invoice.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.systemProperty.dao.SystemPropertyDAO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.invoice.vo.InvoiceVO;

@ManagedBean(name = "invoiceDAO")
@ViewScoped
public class InvoiceDAOImpl extends GenericDAOHibernate<Invoice, Long> implements InvoiceDAO, Serializable{

	private static final long serialVersionUID = 8111501853568620376L;

	@ManagedProperty(value="#{systemPropertyDAO}")
	private SystemPropertyDAO systemPropertyDAO;
		
	public SystemPropertyDAO getSystemPropertyDAO() {
		return systemPropertyDAO;
	}

	public void setSystemPropertyDAO(SystemPropertyDAO systemPropertyDAO) {
		this.systemPropertyDAO = systemPropertyDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<InvoiceVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Invoice.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<InvoiceVO> voList = new ArrayList<InvoiceVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Invoice inv = (Invoice)criteria.list().get(i);
			InvoiceVO vo = new InvoiceVO();
			vo.setSalesOrderId(inv.getSalesOrderId());
			vo.setActiveFlag(inv.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Invoice.class);
		decorateCriteria(criteria, searchCriteria);
		 
		Integer results = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();
		if (results == null) {
			results = 0;
		}
		return results.longValue();
	}
	
	@SuppressWarnings("rawtypes")
	private void decorateCriteria(Criteria crit,
			List<? extends SearchObject> searchCriteria) {
	}
	
	@Override
	public String searchInvoiceNoMax(String year, String yearMonth,
			String yearMonthDate) {
		String numberReceipt = null;
			
		String sql = "SELECT MAX(SO_INV_NO) FROM POS_SO_INV";
		
		if(year !=null && !year.equals("")){
			sql = sql + " WHERE SUBSTR(SO_INV_NO,1,5)  = '"+year+"-' ";
		}else if(yearMonth !=null && !yearMonth.equals("")){
			sql = sql + " WHERE SUBSTR(SO_INV_NO,1,7) = '"+yearMonth+"-' ";
		}else if(yearMonthDate !=null && !yearMonthDate.equals("")){
			sql = sql + " WHERE SUBSTR(SO_INV_NO,1,9) = '"+yearMonthDate+"-' ";
		}
		
		Query query = getSession().createSQLQuery(sql);
		        
        if(query.list().size() > 0){
			if (query.uniqueResult() != null
					&& !query.uniqueResult().toString().equals("")) {
				numberReceipt = query.uniqueResult().toString();
        	} 
        }
			
		return numberReceipt;
	}
	
	@Override
	public String runningNumberInvoice(String systemPropertyName, Long companyId){
		String runningNumberReceipt = null;
		SystemProperty systemProperty = new SystemProperty();
		systemProperty =  systemPropertyDAO.searchSystemPropertyNameAndCompany(systemPropertyName, companyId);
		
		if(systemProperty !=null){
			if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SOINVNUMBER_YEARLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");				   
				String year =  sdfYear.format(new Date());
				String soNumber = searchInvoiceNoMax(year, null, null);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 5) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 4) {
						number = "0" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						if(splitInt >= 999){
							 number = "0" + (splitInt + 1);
						 }else{
							 number = "00" + (splitInt + 1);
						 }	
					} else if ((splitInt + "").length() == 2) {
						 if(splitInt >= 99){
							 number = "00" + (splitInt + 1);
						 }else{
							 number = "000" + (splitInt + 1);
						 }	
					} else if((splitInt + "").length() == 1){
						if(splitInt >= 9)
						{
							number = "000" + (splitInt + 1);
						}
						else{
							number = "0000" + (splitInt + 1);
						}
					}
					
					runningNumberReceipt = year + "-" + number;
					
				} else {
					runningNumberReceipt = year + "-00001";
				}
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SOINVNUMBER_MONTHLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMM");				   
				String yearMonth =  sdfYear.format(new Date());
				String soNumber = searchInvoiceNoMax(null, yearMonth, null);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 4) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						if(splitInt >= 999){
							 number = ""+ (splitInt + 1);
						 }else{
							 number = "0" + (splitInt + 1);
						 }	
					} else if ((splitInt + "").length() == 2) {
						 if(splitInt >= 99){
							 number = "0" + (splitInt + 1);
						 }else{
							 number = "00" + (splitInt + 1);
						 }	
					} else if((splitInt + "").length() == 1){
						 if(splitInt >= 9){
							 number = "00" + (splitInt + 1);
						 }else{
							 number = "000" + (splitInt + 1);
						 }	
					}
					
					runningNumberReceipt = yearMonth + "-" + number;
					
				} else {
					runningNumberReceipt = yearMonth + "-0001";
				}
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SOINVNUMBER_DAILY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMMdd");				   
				String yearMonthDate =  sdfYear.format(new Date());
				String soNumber = searchInvoiceNoMax(null, null, yearMonthDate);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 3) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "0" + (splitInt + 1);
					} else if((splitInt + "").length() == 1){
						 if(splitInt >= 9){
							 number = "0" + (splitInt + 1);
						 }else{
							 number = "00" + (splitInt + 1);
						 }	
					}
					
					runningNumberReceipt = yearMonthDate + "-" + number;
					
				} else {
					runningNumberReceipt = yearMonthDate + "-001";
				}
			}
		}
		
		return runningNumberReceipt;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> searchDataInvoiceBySoId(Long salesOrderId) {
		
		Criteria criteria = getSession().createCriteria(Invoice.class);
		
		criteria.add(Restrictions.eq("salesOrderId", salesOrderId));
		
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> searchDataInvoiceBySoIdAndStatus(Long salesOrderId,
			String statusCode) {
        Criteria criteria = getSession().createCriteria(Invoice.class);
		
		criteria.add(Restrictions.eq("salesOrderId", salesOrderId));
		criteria.add(Restrictions.eq("statusCode", statusCode.trim()));
		
		return criteria.list();
	}
	
	
	@Override
	public String runningNumberInvoiceTagihan(String systemPropertyName, String soInvNumber, Long companyId){
		String runningNumberReceipt = null;
		SystemProperty systemProperty = new SystemProperty();
		systemProperty =  systemPropertyDAO.searchSystemPropertyNameAndCompany(systemPropertyName, companyId);
		
		if(systemProperty !=null){
			if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SOINVNUMBER_YEARLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");				   
				String year =  sdfYear.format(new Date());
				
				if (soInvNumber != null && !soInvNumber.equals("")) {
					String[] split = soInvNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 5) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 4) {
						number = "0" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						if(splitInt >= 999){
							 number = "0" + (splitInt + 1);
						 }else{
							 number = "00" + (splitInt + 1);
						 }	
					} else if ((splitInt + "").length() == 2) {
						 if(splitInt >= 99){
							 number = "00" + (splitInt + 1);
						 }else{
							 number = "000" + (splitInt + 1);
						 }	
					} else if((splitInt + "").length() == 1){
						if(splitInt >= 9)
						{
							number = "000" + (splitInt + 1);
						}
						else{
							number = "0000" + (splitInt + 1);
						}
					}
					
					runningNumberReceipt = year + "-" + number;
					
				} else {
					runningNumberReceipt = year + "-00001";
				}
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SOINVNUMBER_MONTHLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMM");				   
				String yearMonth =  sdfYear.format(new Date());
				
				if (soInvNumber != null && !soInvNumber.equals("")) {
					String[] split = soInvNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 4) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						if(splitInt >= 999){
							 number = ""+ (splitInt + 1);
						 }else{
							 number = "0" + (splitInt + 1);
						 }	
					} else if ((splitInt + "").length() == 2) {
						 if(splitInt >= 99){
							 number = "0" + (splitInt + 1);
						 }else{
							 number = "00" + (splitInt + 1);
						 }	
					} else if((splitInt + "").length() == 1){
						 if(splitInt >= 9){
							 number = "00" + (splitInt + 1);
						 }else{
							 number = "000" + (splitInt + 1);
						 }	
					}
					
					runningNumberReceipt = yearMonth + "-" + number;
					
				} else {
					runningNumberReceipt = yearMonth + "-0001";
				}
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SOINVNUMBER_DAILY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMMdd");				   
				String yearMonthDate =  sdfYear.format(new Date());
				
				if (soInvNumber != null && !soInvNumber.equals("")) {
					String[] split = soInvNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 3) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "0" + (splitInt + 1);
					} else if((splitInt + "").length() == 1){
						 if(splitInt >= 9){
							 number = "0" + (splitInt + 1);
						 }else{
							 number = "00" + (splitInt + 1);
						 }	
					}
					
					runningNumberReceipt = yearMonthDate + "-" + number;
					
				} else {
					runningNumberReceipt = yearMonthDate + "-001";
				}
			}
		}
		
		return runningNumberReceipt;
	}

	

}
