package com.wo.epos.common.util;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.FacesContext;

//@ManagedBean(name = "themePreferences")
//@SessionScoped
public class ThemePreferences implements Serializable {
	private static final long serialVersionUID = 7675019899076507280L;
	private String theme = "base"; // default

	public String getTheme() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		if (params.containsKey("theme")) {
			theme = params.get("theme");
		}

		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
}
