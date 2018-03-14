package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class ClosingHeader extends Header {
	
	
	
	public ClosingHeader(stirng type) {
		
	}
	
	      @Override
      public String getValue() {
        List<ContactInfo> items = dataGrid.getVisibleItems();
        if (items.size() == 0) {
          return "";
        } else {
          double totalSales1 = 0.0D;
          for (Closing item : items) {
            totalSales1 += item.getSales1();
          }
          return "Sales 1 Total: " + totalSales1;
        }
	
	/*
			    Header<String> sales1Header = new Header<String>(new TextCell()) {
      @Override
      public String getValue() {
        List<ContactInfo> items = dataGrid.getVisibleItems();
        if (items.size() == 0) {
          return "";
        } else {
          double totalSales1 = 0.0D;
          for (Closing item : items) {
            totalSales1 += item.getSales1();
          }
          return "Sales 1 Total: " + totalSales1;
        }
      }
    };
    */
}