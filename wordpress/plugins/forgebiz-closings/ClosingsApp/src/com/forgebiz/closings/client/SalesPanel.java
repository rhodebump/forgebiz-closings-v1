package com.forgebiz.closings.client;

import com.google.gwt.user.client.ui.Label;

public class SalesPanel extends BaseIncomeSalesPanel {
	Label totalLabel = new Label("Total (Total A)");



	public SalesPanel() {

	}
	
	public void setClosingSettings(ClosingSettings closingSettings) {
		initControl(income1Label, income1TextBox, closingSettings.getShowSales1(),
				closingSettings.getSalesLabel1());
		initControl(income2Label, income2TextBox, closingSettings.getShowSales2(),
				closingSettings.getSalesLabel2());
		initControl(income3Label, income3TextBox, closingSettings.getShowSales3(),
				closingSettings.getSalesLabel3());
		initControl(income4Label, income4TextBox, closingSettings.getShowSales4(),
				closingSettings.getSalesLabel4());
		initControl(income5Label, income5TextBox, closingSettings.getShowSales5(),
				closingSettings.getSalesLabel5());
		initControl(income6Label, income6TextBox, closingSettings.getShowSales6(),
				closingSettings.getSalesLabel6());
		initControl(income7Label, income7TextBox, closingSettings.getShowSales7(),
				closingSettings.getSalesLabel7());
		initControl(income8Label, income8TextBox, closingSettings.getShowSales8(),
				closingSettings.getSalesLabel8());
		initControl(income9Label, income9TextBox, closingSettings.getShowSales9(),
				closingSettings.getSalesLabel9());

		
		add(NumberPanelHelper.getBootStrapPanel( totalLabel,  totalTextBox));
					

		totalTextBox.setEnabled(false);
		
		
	}
	
	



}
