package com.wo.epos.common.lov.paging;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.datatable.DataTableRenderer;

public class CustomDataTableRenderer extends DataTableRenderer {
	static Logger logger = Logger.getLogger(CustomDataTableRenderer.class);

	
	protected void encodePaginatorMarkup(FacesContext facesContext,
			DataTable dataTable, String arg2, String arg3, String arg4)
			throws IOException {
//		logger.info("using CustomDataTableRenderer");
		this.adjustFirstValue(dataTable);
		//super.encodePaginatorMarkup(facesContext, dataTable, arg2, arg3, arg4);
		encodePaginatorMarkup(facesContext, dataTable, arg2, arg3, arg4);
	}

	private void adjustFirstValue(DataTable dataTable) {
		int first = dataTable.getFirst();
		int rowCount = dataTable.getRowCount();
		int pageSize = dataTable.getRowsToRender();
		first = getAdjustedFirstData(first, pageSize, rowCount);
		dataTable.setFirst(first);
	}

	public static int getAdjustedFirstData(int first, int pageSize, int rowCount) {
		while (first >= rowCount && first > 0) {
			first -= pageSize;
		}

		return first;
	}

}
