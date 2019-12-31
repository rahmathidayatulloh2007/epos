package com.wo.epos.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputEnableMap {
	private Map <String, Boolean> mapEnable = new HashMap<String, Boolean>();
	private Boolean defaultEnabled = true;
	
	public Boolean inputEnabled() {
		return defaultEnabled;
	}

	public Boolean inputEnabled(String key) {
		Boolean mapBool = mapEnable.get(key);
		if (mapBool != null) {
			return mapBool;
		} else {
			return defaultEnabled;
		}
	}
	
	public void setDefaultEnabled(Boolean defaultEnabled) {
		this.defaultEnabled = defaultEnabled;
	}
	
	public void putMapEnable(String key, Boolean bool) {
		mapEnable.put(key, bool);
	}
	
	public void resetAllKeysInMapEnable(Boolean bool) {
		List <String> listKey = new ArrayList<String>(mapEnable.keySet());
		for (String key : listKey) {
			mapEnable.put(key, bool);
		}
	}
}
