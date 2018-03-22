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
	
	

	protected void initControl(Label label, TextBox textBox, boolean showControl, String labelText) {
		
		if (showControl != false) {
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
		this.total = total;
		totalTextBox.setValue(ClosingPanel.getCurrency(total));

	}
	public void calculateAll() {
		GWT.log("calculateAll2");
		double t = 0.0D;
		t += ClosingsApp.getDoubleValue(this.income1TextBox);
		t += ClosingsApp.getDoubleValue(this.income2TextBox);
		t += ClosingsApp.getDoubleValue(this.income3TextBox);
		t += ClosingsApp.getDoubleValue(this.income4TextBox);
		t += ClosingsApp.getDoubleValue(this.income5TextBox);
		t += ClosingsApp.getDoubleValue(this.income6TextBox);
		t += ClosingsApp.getDoubleValue(this.income7TextBox);
		t += ClosingsApp.getDoubleValue(this.income8TextBox);
		t += ClosingsApp.getDoubleValue(this.income9TextBox);		
		setTotal(t);
	}

	public void setClosingPanel(ClosingPanel closingPanel) {
		numberKeyUpHandler.setClosingPanel(closingPanel);
	}
}
