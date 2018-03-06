package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CashPanel extends VerticalPanel {
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

	class MyHandler implements KeyUpHandler {
		MyHandler() {
		}

		public void onKeyUp(KeyUpEvent event) {
			CashPanel.this.closingPanel.calculateAll();
		}
	}

	MyHandler handler = new MyHandler();
	private ClosingPanel closingPanel;

	private void addKeyUpHandler(TextBox textBox) {
		textBox.addKeyUpHandler(this.handler);
	}

	public CashPanel() {
		add(this.open1CentLabel);
		add(this.open1Cent);
		addKeyUpHandler(this.open1Cent);

		add(this.open5CentsLabel);
		add(this.open5Cents);
		addKeyUpHandler(this.open5Cents);

		add(this.open10CentsLabel);
		add(this.open10Cents);
		addKeyUpHandler(this.open10Cents);

		add(this.open25CentsLabel);
		add(this.open25Cents);
		addKeyUpHandler(this.open25Cents);

		add(this.open1DollarLabel);
		add(this.open1Dollar);
		addKeyUpHandler(this.open1Dollar);

		add(this.open5DollarsLabel);
		add(this.open5Dollars);
		addKeyUpHandler(this.open5Dollars);

		add(this.open10DollarsLabel);
		add(this.open10Dollars);
		addKeyUpHandler(this.open10Dollars);

		add(this.open20DollarsLabel);
		add(this.open20Dollars);
		addKeyUpHandler(this.open20Dollars);

		add(this.open50DollarsLabel);
		add(this.open50Dollars);
		addKeyUpHandler(this.open50Dollars);

		add(this.open100DollarsLabel);
		add(this.open100Dollars);
		addKeyUpHandler(this.open100Dollars);

		add(this.totalLabel);
		add(this.totalTextBox);

		GWT.log("cashpanel #2");
	}



	public void calculateAll() {
		GWT.log("calculateAll2");
		double total = 0.0D;
		total += ClosingsApp.getIntValue(this.open1Cent).intValue();
		total += ClosingsApp.getIntValue(this.open5Cents).intValue();
		total += ClosingsApp.getIntValue(this.open10Cents).intValue();
		total += ClosingsApp.getIntValue(this.open25Cents).intValue();
		total += ClosingsApp.getIntValue(this.open1Dollar).intValue();
		total += ClosingsApp.getIntValue(this.open5Dollars).intValue();
		total += ClosingsApp.getIntValue(this.open10Dollars).intValue();
		total += ClosingsApp.getIntValue(this.open20Dollars).intValue();
		total += ClosingsApp.getIntValue(this.open50Dollars).intValue();
		total += ClosingsApp.getIntValue(this.open100Dollars).intValue();

		setCashTotal(total);
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}

	double cashTotal = 0.0D;

	public double getCashTotal() {
		return this.cashTotal;
	}

	private void setCashTotal(double cashTotal) {
		this.cashTotal = cashTotal;
		this.totalTextBox.setText(new Double(cashTotal).toString());
	}
}
