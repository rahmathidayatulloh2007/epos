/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wo.epos.common.paging;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

public class LazySorter<E> implements Comparator<E> {

	private String sortField;

	private SortOrder sortOrder;

	public LazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@SuppressWarnings("all")
	public int compare(E obj1, E obj2) {
		try {
			Object value1 = obj1.getClass().getField(this.sortField).get(obj1);
			Object value2 = obj2.getClass().getField(this.sortField).get(obj2);

			int value = ((Comparable) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}