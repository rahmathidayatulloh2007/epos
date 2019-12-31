package com.wo.epos.common.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class SearchValueObject <T> implements SearchObject<T>, Serializable {
	
	
	private static final long serialVersionUID = 5188751379913989486L;
	
	private String searchColumn;
	private T searchValueObject;
	
	public SearchValueObject(String searchColumn, T searchValueObject) {
		this.searchColumn = searchColumn;
		this.searchValueObject = searchValueObject;
	}
	
	@Override
	public String getSearchColumn() {		
		return searchColumn;
	}
	
	@Override
	public T getSearchValue() {
		return searchValueObject;
	}
	
	@Override
	public boolean isEmpty() {
		return StringUtils.isBlank(searchColumn);
	}

	@Override
	public String getSearchValueAsString() {
		
		return searchValueObject.toString();
	}

}
