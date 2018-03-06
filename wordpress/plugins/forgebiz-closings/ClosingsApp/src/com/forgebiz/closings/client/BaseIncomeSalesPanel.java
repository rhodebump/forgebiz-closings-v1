package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BaseIncomeSalesPanel extends VerticalPanel {
	Label income1Label = new Label();
	public TextBox income1TextBox = new TextBox();
	Label income2Label = new Label();
	public TextBox income2TextBox = new TextBox();
	Label income3Label = new Label();
	public TextBox income3TextBox = new TextBox();
	Label income4Label = new Label();
	public TextBox income4TextBox = new TextBox();
	Label income5Label = new Label();
	public TextBox income5TextBox = new TextBox();
	Label income6Label = new Label();
	public TextBox income6TextBox = new TextBox();
	Label income7Label = new Label();
	public TextBox income7TextBox = new TextBox();
	Label income8Label = new Label();
	public TextBox income8TextBox = new TextBox();
	Label income9Label = new Label();
	public  TextBox income9TextBox = new TextBox();

	public void setCashTotalCash(double d) {
	}

	protected void initControl(Label label, TextBox textBox, boolean showControl, String labelText) {
		
		if (showControl != false) {
			label.setText(labelText);
			add(label);
			add(textBox);
			addKeyUpHandler(textBox);
		}
	}

	class MyHandler implements KeyUpHandler {
		MyHandler() {
		}

		public void onKeyUp(KeyUpEvent event) {
			BaseIncomeSalesPanel.this.closingPanel.calculateAll();
		}
	}

	MyHandler handler = new MyHandler();
	private ClosingPanel closingPanel;

	private void addKeyUpHandler(TextBox textBox) {
		textBox.addKeyUpHandler(this.handler);
	}



	public void calculateAll() {
		GWT.log("calculateAll2");
		double total = 0.0D;
		total += getIntegerValue(this.income1TextBox).intValue();
		total += getIntegerValue(this.income2TextBox).intValue();
		total += getIntegerValue(this.income3TextBox).intValue();
		total += getIntegerValue(this.income4TextBox).intValue();
		total += getIntegerValue(this.income5TextBox).intValue();
		total += getIntegerValue(this.income6TextBox).intValue();
		total += getIntegerValue(this.income7TextBox).intValue();
		total += getIntegerValue(this.income8TextBox).intValue();
		total += getIntegerValue(this.income9TextBox).intValue();		
		setTotal(total);
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}
}
