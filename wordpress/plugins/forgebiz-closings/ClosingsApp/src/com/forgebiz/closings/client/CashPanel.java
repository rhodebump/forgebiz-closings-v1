package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class CashPanel extends FlowPanel {
	Label open1CentLabel = new Label(".01");
	TextBox open1Cent = new TextBox();

	Label open5CentsLabel = new Label(".05");
	TextBox open5Cents = new TextBox();

	Label open10CentsLabel = new Label(".10");
	TextBox open10Cents = new TextBox();

	Label open25CentsLabel = new Label(".25");
	TextBox open25Cents = new TextBox();
	Label open1DollarLabel = new Label("$1");
	TextBox open1Dollar = new TextBox();

	Label open5DollarsLabel = new Label("$5");
	TextBox open5Dollars = new TextBox();

	Label open10DollarsLabel = new Label("$10");
	TextBox open10Dollars = new TextBox();

	Label open20DollarsLabel = new Label("$20");
	TextBox open20Dollars = new TextBox();

	Label open50DollarsLabel = new Label("$50");
	TextBox open50Dollars = new TextBox();

	Label open100DollarsLabel = new Label("$100");
	TextBox open100Dollars = new TextBox();

	Label totalLabel = new Label("Total");
	TextBox totalTextBox = new TextBox();

	NumberKeyUpHandler numberKeyUpHandler = new NumberKeyUpHandler();


	private void addFormGroup(Label label, TextBox textBox) {
		
		add(NumberPanelHelper.getFlowPanel( label,  textBox));
		textBox.addKeyUpHandler(numberKeyUpHandler);
	}

	public CashPanel() {

		addFormGroup(open1CentLabel, open1Cent);

		addFormGroup(open5CentsLabel, open5Cents);

		addFormGroup(open10CentsLabel, open10Cents);

		addFormGroup(open25CentsLabel, open25Cents);

		addFormGroup(open1DollarLabel, open1Dollar);

		addFormGroup(open5DollarsLabel, open5Dollars);

		addFormGroup(open10DollarsLabel, open10Dollars);

		addFormGroup(open20DollarsLabel, open20Dollars);
		addFormGroup(open50DollarsLabel, open50Dollars);

		addFormGroup(open100DollarsLabel, open100Dollars);
		addFormGroup(totalLabel, totalTextBox);

		totalTextBox.setEnabled(false);


	}

	public void calculateAll() {
		GWT.log("calculateAll2");
		double total = 0.0D;
		total += ClosingsApp.getDoubleValue(this.open1Cent);
		total += ClosingsApp.getDoubleValue(this.open5Cents);
		total += ClosingsApp.getDoubleValue(this.open10Cents);
		total += ClosingsApp.getDoubleValue(this.open25Cents);
		total += ClosingsApp.getDoubleValue(this.open1Dollar);
		total += ClosingsApp.getDoubleValue(this.open5Dollars);
		total += ClosingsApp.getDoubleValue(this.open10Dollars);
		total += ClosingsApp.getDoubleValue(this.open20Dollars);
		total += ClosingsApp.getDoubleValue(this.open50Dollars);
		total += ClosingsApp.getDoubleValue(this.open100Dollars);

		setCashTotal(total);
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		numberKeyUpHandler.setClosingPanel(closingPanel);
	}

	private Double cashTotal = 0.0D;

	public double getCashTotal() {
		return this.cashTotal;
	}

	public void setCashTotal(Double cashTotal) {
		this.cashTotal = cashTotal;
		this.totalTextBox.setValue(ClosingPanel.getCurrency(cashTotal));
	}
}
