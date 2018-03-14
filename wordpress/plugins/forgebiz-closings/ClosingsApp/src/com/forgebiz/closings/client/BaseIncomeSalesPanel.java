package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class BaseIncomeSalesPanel extends FlowPanel {
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

	TextBox totalTextBox = new TextBox();
	
	
	public void setCashTotalCash(double d) {
	}

	protected void initControl(Label label, TextBox textBox, boolean showControl, String labelText) {
		
		if (showControl != false) {
			label.setText(labelText);
			label.removeStyleName(".gwt-Label");
			//add(label);
			//add(textBox);
			//numberKeyUpHandler.addKeyUpHandler("todo", textBox);
			textBox.setStyleName("form-control");
			addFormGroup(label,textBox);
		}
	
	}
	private void addFormGroup(Label label, TextBox textBox) {
		FlowPanel fp = new FlowPanel();
		fp.setStyleName("form-group");
		fp.add(label);
		fp.add(textBox);
		textBox.setStyleName("form-control");
		add(fp);
		
		numberKeyUpHandler.addKeyUpHandler(textBox,fp);
	}

	NumberKeyUpHandler numberKeyUpHandler = new NumberKeyUpHandler();





	private double total =  0.0D;
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		totalTextBox.setValue(ClosingPanel.getCurrency(total));
		this.total = total;
	}
	public void calculateAll() {
		GWT.log("calculateAll2");
		double t = 0.0D;
		t += ClosingsApp.getIntValue(this.income1TextBox);
		t += ClosingsApp.getIntValue(this.income2TextBox);
		t += ClosingsApp.getIntValue(this.income3TextBox);
		t += ClosingsApp.getIntValue(this.income4TextBox);
		t += ClosingsApp.getIntValue(this.income5TextBox);
		t += ClosingsApp.getIntValue(this.income6TextBox);
		t += ClosingsApp.getIntValue(this.income7TextBox);
		t += ClosingsApp.getIntValue(this.income8TextBox);
		t += ClosingsApp.getIntValue(this.income9TextBox);		
		setTotal(t);
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		numberKeyUpHandler.setClosingPanel(closingPanel);
	}
}
