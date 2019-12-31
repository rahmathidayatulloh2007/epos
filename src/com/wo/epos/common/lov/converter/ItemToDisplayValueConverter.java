package com.wo.epos.common.lov.converter;

public interface ItemToDisplayValueConverter <T> {	
	Object convertToDisplayValue(T item);
}
