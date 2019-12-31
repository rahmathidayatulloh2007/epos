package com.wo.epos.common.lov.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultDateConverter implements ItemToDisplayValueConverter<Date> {
	//klo cuma dipanggil dari xhtml, mustinya sequential, gk perlu thread safe
	private SimpleDateFormat sdf;
	
	public DefaultDateConverter() {
		sdf = new SimpleDateFormat("dd-MMM-yyyy");
	}
	
	public DefaultDateConverter(String pattern) {
		sdf = new SimpleDateFormat(pattern); 
	}

	@Override
	public Object convertToDisplayValue(Date item) {
		if (item != null) {
			return sdf.format(item);
		} else {
			return "";
		}
	}

}
