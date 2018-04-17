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

	//this is a subtotal of income1/income9
	TextBox totalTextBox = new TextBox();
	
	//this is only present used for the cash that comes over as income on the income subpanel
	TextBox cashTotalTextBox = new TextBox();
	

	protected void initControl(Label label, TextBox textBox, int showControl, String labelText) {
		
		if (showControl == 1) {
			label.setText(labelText);
			add(NumberPanelHelper.getFlowPanel( label,  textBox));
			textBox.addKeyUpHandler(numberKeyUpHandler);
			
			
		}
	
	}


	NumberKeyUpHandler numberKeyUpHandler = new NumberKeyUpHandler();





	private double total =  0.0D;
	public double getTotal() {
		return total;
	}
	
	public void setTotal(Double total) {
		GWT.log("BaseIncomeSalesPanel.setTotal " + total.toString());
		this.total = total;
		totalTextBox.setValue(ClosingPanel.getCurrency(total));

	}
	public void calculateAll() {
		GWT.log("BaseIncomeSalesPanel calculateAll");
		double t = 0.0D;
		t += ClosingsApp.getDoubleValue(this.income1TextBox);
		GWT.log("income1TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income2TextBox);
		GWT.log("income2TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income3TextBox);
		GWT.log("income3TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income4TextBox);
		GWT.log("income4TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income5TextBox);
		GWT.log("income5TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income6TextBox);
		GWT.log("income6TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income7TextBox);
		GWT.log("income7TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income8TextBox);
		GWT.log("income8TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.income9TextBox);		
		GWT.log("income9TextBox " + t);
		t += ClosingsApp.getDoubleValue(this.cashTotalTextBox);				
		GWT.log("cashTotalTextBox " + t);
		
		setTotal(t);
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		numberKeyUpHandler.setClosingPanel(closingPanel);
	}
}
