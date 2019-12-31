package com.wo.epos.common.model;

import java.io.Serializable;

public class ColumnModel implements Serializable {

	private static final long serialVersionUID = 4547851240813400773L;
	private String header;
	private String property;
	private int sequence;

	public ColumnModel(String header, String property) {
		this.header = header;
		this.property = property;
	}

	public ColumnModel(String header, String property, int sequence) {
		this.header = header;
		this.property = property;
		this.sequence = sequence;
	}

	public String getHeader() {
		return header;
	}

	public String getProperty() {
		return property;
	}

	public int getSequence() {
		return sequence;
	}
}