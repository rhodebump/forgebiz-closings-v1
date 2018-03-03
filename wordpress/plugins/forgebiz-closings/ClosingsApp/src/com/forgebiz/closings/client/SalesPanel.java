package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class SalesPanel extends BaseIncomeSalesPanel {
	Label totalLabel = new Label("Total (Total B)");
	TextBox totalTextBox = new TextBox();

	private ClosingPanel closingPanel;

	public SalesPanel() {
	}

	public SalesPanel(ClosingSettings closingSettings) {
		initControl(this.income1Label, this.income1TextBox, closingSettings.getShowSales1(),
				closingSettings.getSalesLabel1());
		initControl(this.income2Label, this.income1TextBox, closingSettings.getShowSales2(),
				closingSettings.getSalesLabel2());
		initControl(this.income3Label, this.income1TextBox, closingSettings.getShowSales3(),
				closingSettings.getSalesLabel3());
		initControl(this.income4Label, this.income1TextBox, closingSettings.getShowSales4(),
				closingSettings.getSalesLabel4());
		initControl(this.income5Label, this.income1TextBox, closingSettings.getShowSales5(),
				closingSettings.getSalesLabel5());
		initControl(this.income6Label, this.income1TextBox, closingSettings.getShowSales6(),
				closingSettings.getSalesLabel6());
		initControl(this.income7Label, this.income1TextBox, closingSettings.getShowSales7(),
				closingSettings.getSalesLabel7());
		initControl(this.income8Label, this.income1TextBox, closingSettings.getShowSales8(),
				closingSettings.getSalesLabel8());
		initControl(this.income9Label, this.income1TextBox, closingSettings.getShowSales9(),
				closingSettings.getSalesLabel9());

		add(this.totalLabel);
		add(this.totalTextBox);
		this.totalTextBox.setEnabled(false);
	}

	public void calculateAll() {
		GWT.log("calculateAll2");
		double total = 0.0D;

		this.totalTextBox.setText(new Double(total).toString());
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}
}
