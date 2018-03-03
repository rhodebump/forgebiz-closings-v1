package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BaseIncomeSalesPanel extends VerticalPanel {
	Label income1Label = new Label();
	TextBox income1TextBox = new TextBox();
	Label income2Label = new Label();
	TextBox income2TextBox = new TextBox();
	Label income3Label = new Label();
	TextBox income3TextBox = new TextBox();
	Label income4Label = new Label();
	TextBox income4TextBox = new TextBox();
	Label income5Label = new Label();
	TextBox income5TextBox = new TextBox();
	Label income6Label = new Label();
	TextBox income6TextBox = new TextBox();
	Label income7Label = new Label();
	TextBox income7TextBox = new TextBox();
	Label income8Label = new Label();
	TextBox income8TextBox = new TextBox();
	Label income9Label = new Label();
	TextBox income9TextBox = new TextBox();

	public void setCashTotalCash(double d) {
	}

	protected void initControl(Label label, TextBox textBox, boolean showControl, String labelText) {
		if (showControl) {
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
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		this.closingPanel = closingPanel;
	}
}
