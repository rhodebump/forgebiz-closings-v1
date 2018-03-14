package com.forgebiz.closings.client;

import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextHeader;

public class ClosingHeader extends TextHeader {

	private CellTable table = null;

	private ColumnType ct = null;
	
	private double getValue(Closing closing) {
		/*
		if (ct == ColumnType.SALES_1) {
			return closing.getSales1();
		}else 		if (ct == ColumnType.SALES_2) {
			return closing.getSales2();
		}
		*/
		
		return 1.25D;
	}
	
	public ClosingHeader(String colName, CellTable table,ColumnType ct) {
		super(colName);
		this.table = table;
		this.ct = ct;
	}

	@Override
	public String getValue() {

		List<Closing> items = table.getVisibleItems();
		GWT.log("items.size()" + items.size());
		if (items.size() == 0) {
			return "";
		} else {
			double totalSales1 = 0.0D;
			for (Closing closing : items) {
				totalSales1 = totalSales1 +  getValue(closing);
				GWT.log("totalSales1=" + totalSales1);
			}
			return "" + totalSales1;
		}
	}

	/*
	 * Header<String> sales1Header = new Header<String>(new TextCell()) {
	 * 
	 * @Override public String getValue() { List<ContactInfo> items =
	 * dataGrid.getVisibleItems(); if (items.size() == 0) { return ""; } else {
	 * double totalSales1 = 0.0D; for (Closing item : items) { totalSales1 +=
	 * item.getSales1(); } return "Sales 1 Total: " + totalSales1; } } };
	 */
}