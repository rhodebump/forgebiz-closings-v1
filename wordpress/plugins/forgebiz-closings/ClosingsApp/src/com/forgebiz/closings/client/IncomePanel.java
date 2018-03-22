package com.forgebiz.closings.client;


import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class IncomePanel extends BaseIncomeSalesPanel {
	Label totalLabel = new Label("Total(Total B)");


	Label cashTotalLabel = new Label("Cash (Closing cash - Opening cash)");
	TextBox cashTotalTextBox = new TextBox();




	public void setClosingSettings(ClosingSettings closingSettings) {
		
		add(NumberPanelHelper.getBootStrapPanel(cashTotalLabel,cashTotalTextBox));

		this.cashTotalTextBox.setEnabled(false);

		initControl(income1Label, income1TextBox, closingSettings.getShowIncome1(),
				closingSettings.getIncomeLabel1());
		initControl(income2Label, income2TextBox, closingSettings.getShowIncome2(),
				closingSettings.getIncomeLabel2());
		initControl(income3Label, income3TextBox, closingSettings.getShowIncome3(),
				closingSettings.getIncomeLabel3());
		initControl(income4Label, income4TextBox, closingSettings.getShowIncome4(),
				closingSettings.getIncomeLabel4());
		initControl(income5Label, income5TextBox, closingSettings.getShowIncome5(),
				closingSettings.getIncomeLabel5());
		initControl(income6Label, income6TextBox, closingSettings.getShowIncome6(),
				closingSettings.getIncomeLabel6());
		initControl(income7Label, income7TextBox, closingSettings.getShowIncome7(),
				closingSettings.getIncomeLabel7());
		initControl(income8Label, income8TextBox, closingSettings.getShowIncome8(),
				closingSettings.getIncomeLabel8());
		initControl(income9Label, income9TextBox, closingSettings.getShowIncome9(),
				closingSettings.getIncomeLabel9());

		add(NumberPanelHelper.getBootStrapPanel( totalLabel,  totalTextBox));
		this.totalTextBox.setEnabled(false);
		
		
	}
	public IncomePanel() {
		super();

	}



}
