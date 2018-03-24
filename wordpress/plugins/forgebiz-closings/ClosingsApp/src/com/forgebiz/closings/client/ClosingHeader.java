package com.forgebiz.closings.client;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextHeader;

public class ClosingHeader extends TextHeader {

	private CellTable table = null;

	private ColumnType columnType = null;

	public ClosingHeader(CellTable table, ColumnType columnType) {
		super(columnType.getName());
		this.table = table;
		this.columnType = columnType;
	}

	@Override
	public String getValue() {

		List<Closing> items = table.getVisibleItems();
		GWT.log("items.size()" + items.size());
		if (items.size() == 0) {
			return "";
		} else {
			Double totalSales1 = new Double(0.0D);

			for (Closing closing : items) {
				double d = columnType.getValue(closing);
				// for unknown reasons, GWT would always append the double as a string,
				// as a workaround, manually converting to a double
				Double d2 = Double.parseDouble(new Double(d).toString());

				totalSales1 = totalSales1 + d2;

				GWT.log("totalSales1=" + totalSales1);

			}
			return ClosingPanel.getCurrency(totalSales1);
			//return totalSales1.toString();
		}
	}

}