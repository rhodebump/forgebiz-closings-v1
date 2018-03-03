package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class IncomePanel extends BaseIncomeSalesPanel {
	Label totalLabel = new Label("Gross Sales(Total A)");
	TextBox totalTextBox = new TextBox();

	Label cashTotalLabel = new Label("Cash (total closing cash from drawer with $100 subtracted)");
	TextBox cashTotalTextBox = new TextBox();

	public void setCashTotalCash(double d) {
		this.cashTotalTextBox.setValue(new Double(d).toString());
	}

	private ClosingPanel closingPanel;

	private void addKeyUpHandler(TextBox textBox) {
		textBox.addKeyUpHandler(this.handler);
	}

	public IncomePanel(ClosingSettings closingSettings) {
		add(this.cashTotalLabel);
		add(this.cashTotalTextBox);
		this.cashTotalTextBox.setEnabled(false);

		initControl(this.income1Label, this.income1TextBox, closingSettings.getShowIncome1(),
				closingSettings.getIncomeLabel1());
		initControl(this.income2Label, this.income1TextBox, closingSettings.getShowIncome2(),
				closingSettings.getIncomeLabel2());
		initControl(this.income3Label, this.income1TextBox, closingSettings.getShowIncome3(),
				closingSettings.getIncomeLabel3());
		initControl(this.income4Label, this.income1TextBox, closingSettings.getShowIncome4(),
				closingSettings.getIncomeLabel4());
		initControl(this.income5Label, this.income1TextBox, closingSettings.getShowIncome5(),
				closingSettings.getIncomeLabel5());
		initControl(this.income6Label, this.income1TextBox, closingSettings.getShowIncome6(),
				closingSettings.getIncomeLabel6());
		initControl(this.income7Label, this.income1TextBox, closingSettings.getShowIncome7(),
				closingSettings.getIncomeLabel7());
		initControl(this.income8Label, this.income1TextBox, closingSettings.getShowIncome8(),
				closingSettings.getIncomeLabel8());
		initControl(this.income9Label, this.income1TextBox, closingSettings.getShowIncome9(),
				closingSettings.getIncomeLabel9());

		add(this.totalLabel);
		add(this.totalTextBox);
		this.totalTextBox.setEnabled(false);
	}

	private Integer getIntegerValue(TextBox textBox) {
		try {
			return Integer.valueOf(Integer.parseInt(textBox.getValue()));
		} catch (Exception e) {
			GWT.log("returning 0 for " + textBox.getName());
		}
		return Integer.valueOf(0);
	}

	public void calculateAll() {
		GWT.log("calculateAll2");
		double total = 0.0D;
		total += getIntegerValue(this.income1TextBox).intValue();

		this.totalTextBox.setText(new Double(total).toString());
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}
}
