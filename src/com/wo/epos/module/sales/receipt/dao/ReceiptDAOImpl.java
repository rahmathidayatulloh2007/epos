package com.wo.epos.module.sales.receipt.dao;

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
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.mapping.Join;

import com.wo.epos.common.constant.CommonConstants;
import com.wo.epos.common.dao.GenericDAOHibernate;
import com.wo.epos.common.vo.SearchObject;
import com.wo.epos.module.master.systemProperty.dao.SystemPropertyDAO;
import com.wo.epos.module.master.systemProperty.model.SystemProperty;
import com.wo.epos.module.sales.invoice.model.Invoice;
import com.wo.epos.module.sales.receipt.model.Receipt;
import com.wo.epos.module.sales.receipt.vo.ReceiptVO;

@ManagedBean(name = "receiptDAO")
@ViewScoped
public class ReceiptDAOImpl extends GenericDAOHibernate<Receipt, Long> implements ReceiptDAO, Serializable{
    
	private static final long serialVersionUID = 3295026854651070529L;
	
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
	public List<ReceiptVO> searchData(List<? extends SearchObject> searchCriteria,
			int first, int pageSize, String sortField, boolean sortOrder)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Receipt.class);
		decorateCriteria(criteria, searchCriteria);
		
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		List<ReceiptVO> voList = new ArrayList<ReceiptVO>();
		for(int i=0; i<criteria.list().size(); i++){
			Receipt receipt = (Receipt)criteria.list().get(i);
			ReceiptVO vo = new ReceiptVO();
			vo.setReceiptId(receipt.getReceiptId());
			vo.setActiveFlag(receipt.getActiveFlag());
			
			voList.add(vo);
		}
		
		return voList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long searchCountData(List<? extends SearchObject> searchCriteria)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(Receipt.class);
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
	public String searchReceiptNoMax(String year, String yearMonth,
			String yearMonthDate) {
		String numberReceipt = null;
			
		String sql = "SELECT MAX(RECEIPT_NO) FROM POS_SALES_RECEIPT";
		
		if(year !=null && !year.equals("")){
			sql = sql + " WHERE SUBSTR(RECEIPT_NO,1,5)  = '"+year+"-' ";
		}else if(yearMonth !=null && !yearMonth.equals("")){
			sql = sql + " WHERE SUBSTR(RECEIPT_NO,1,7) = '"+yearMonth+"-' ";
		}else if(yearMonthDate !=null && !yearMonthDate.equals("")){
			sql = sql + " WHERE SUBSTR(RECEIPT_NO,1,9) = '"+yearMonthDate+"-' ";
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
	public String runningNumberReceipt(String systemPropertyName, Long companyId){
		String runningNumberReceipt = null;
		SystemProperty systemProperty = new SystemProperty();
		systemProperty =  systemPropertyDAO.searchSystemPropertyNameAndCompany(systemPropertyName, companyId);
		
		if(systemProperty !=null){
			if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SORCPTNUMBER_YEARLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("YYYY");				   
				String year =  sdfYear.format(new Date());
				String soNumber = searchReceiptNoMax(year, null, null);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 5) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 4) {
						number = "0" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						number = "00" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "000" + (splitInt + 1);
					} else if((splitInt + "").length() == 1){
						if(splitInt >= 9){
							number = "000" + (splitInt + 1);
						}else{
							number = "0000" + (splitInt + 1);
						}
					}
					
					runningNumberReceipt = year + "-" + number;
					
				} else {
					runningNumberReceipt = year + "-00001";
				}
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SORCPTNUMBER_MONTHLY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMM");				   
				String yearMonth =  sdfYear.format(new Date());
				String soNumber = searchReceiptNoMax(null, yearMonth, null);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 4) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 3) {
						number = "0" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "00" + (splitInt + 1);
					} else if((splitInt + "").length() == 1){
						number = "000" + (splitInt + 1);
					}
					
					runningNumberReceipt = yearMonth + "-" + number;
					
				} else {
					runningNumberReceipt = yearMonth + "-0001";
				}
			}else if(systemProperty.getSystemPropertyValue().equals(CommonConstants.SORCPTNUMBER_DAILY)){
				SimpleDateFormat sdfYear = new SimpleDateFormat("yyyyMMdd");				   
				String yearMonthDate =  sdfYear.format(new Date());
				String soNumber = searchReceiptNoMax(null, null, yearMonthDate);
				
				if (soNumber != null && !soNumber.equals("")) {
					String[] split = soNumber.split("-");
					String number = null;
					Integer splitInt = Integer.parseInt(split[1]);
					if ((splitInt + "").length() == 3) {
						number = "" + (splitInt + 1);
					} else if ((splitInt + "").length() == 2) {
						number = "0" + (splitInt + 1);
					} else if((splitInt + "").length() == 1){
						number = "00" + (splitInt + 1);
					}
					
					runningNumberReceipt = yearMonthDate + "-" + number;
					
				} else {
					runningNumberReceipt = yearMonthDate + "-001";
				}
			}
		}
		
		return runningNumberReceipt;
	}

	@Override
	public List<Receipt> findReceiptByInvoiceId(Long invoiceId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Receipt.class);
		
		criteria.add(Restrictions.eq("invoiceId", invoiceId));

		return criteria.list();
	}
	

}
