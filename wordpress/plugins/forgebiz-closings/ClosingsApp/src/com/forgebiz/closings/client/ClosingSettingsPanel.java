package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBox;

public class ClosingSettingsPanel extends FlowPanel {




	SimpleCheckBox showSales1CheckBox = new SimpleCheckBox();
	TextBox sales_1_labelTextBox = new TextBox();

	SimpleCheckBox showSales2CheckBox = new SimpleCheckBox();
	TextBox sales_2_labelTextBox = new TextBox();

	SimpleCheckBox showSales3CheckBox = new SimpleCheckBox();
	TextBox sales_3_labelTextBox = new TextBox();

	SimpleCheckBox showSales4CheckBox = new SimpleCheckBox();
	TextBox sales_4_labelTextBox = new TextBox();

	SimpleCheckBox showSales5CheckBox = new SimpleCheckBox();
	TextBox sales_5_labelTextBox = new TextBox();

	SimpleCheckBox showSales6CheckBox = new SimpleCheckBox();
	TextBox sales_6_labelTextBox = new TextBox();

	SimpleCheckBox showSales7CheckBox = new SimpleCheckBox();
	TextBox sales_7_labelTextBox = new TextBox();

	SimpleCheckBox showSales8CheckBox = new SimpleCheckBox();
	TextBox sales_8_labelTextBox = new TextBox();
	SimpleCheckBox showSales9CheckBox = new SimpleCheckBox();
	TextBox sales_9_labelTextBox = new TextBox();

	SimpleCheckBox showIncome1CheckBox = new SimpleCheckBox();
	TextBox income_1_labelTextBox = new TextBox();

	SimpleCheckBox showIncome2CheckBox = new SimpleCheckBox();
	TextBox income_2_labelTextBox = new TextBox();

	SimpleCheckBox showIncome3CheckBox = new SimpleCheckBox();
	TextBox income_3_labelTextBox = new TextBox();

	SimpleCheckBox showIncome4CheckBox = new SimpleCheckBox();
	TextBox income_4_labelTextBox = new TextBox();

	SimpleCheckBox showIncome5CheckBox = new SimpleCheckBox();
	TextBox income_5_labelTextBox = new TextBox();

	SimpleCheckBox showIncome6CheckBox = new SimpleCheckBox();
	TextBox income_6_labelTextBox = new TextBox();

	SimpleCheckBox showIncome7CheckBox = new SimpleCheckBox();
	TextBox income_7_labelTextBox = new TextBox();

	SimpleCheckBox showIncome8CheckBox = new SimpleCheckBox();
	TextBox income_8_labelTextBox = new TextBox();
	SimpleCheckBox showIncome9CheckBox = new SimpleCheckBox();
	TextBox income_9_labelTextBox = new TextBox();

	Button saveButton = new Button("Save");
	Button cancelButton = new Button("Cancel");
	
	
	Button loadPresetButton = new Button("Load Settings for Pottery Store");

	
	
	ClosingSettings closingSettings = null;

	public ClickHandler saveHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("saveHandler.onClick");
			closingSettings
					.setSalesLabel1(sales_1_labelTextBox.getValue());
			closingSettings
					.setSalesLabel2(sales_2_labelTextBox.getValue());
			closingSettings
					.setSalesLabel3(sales_3_labelTextBox.getValue());
			closingSettings
					.setSalesLabel4(sales_4_labelTextBox.getValue());
			closingSettings
					.setSalesLabel5(sales_5_labelTextBox.getValue());
			closingSettings
					.setSalesLabel6(sales_6_labelTextBox.getValue());
			closingSettings
					.setSalesLabel7(sales_7_labelTextBox.getValue());
			closingSettings
					.setSalesLabel8(sales_8_labelTextBox.getValue());
			closingSettings
					.setSalesLabel9(sales_9_labelTextBox.getValue());
			
			closingSettings
					.setShowSales1(getInt(showSales1CheckBox));
			closingSettings
					.setShowSales2(getInt(showSales2CheckBox));
			closingSettings
					.setShowSales3(getInt(showSales3CheckBox));
			closingSettings
					.setShowSales4(getInt(showSales4CheckBox));
			closingSettings
					.setShowSales5(getInt(showSales5CheckBox));
			closingSettings
					.setShowSales6(getInt(showSales6CheckBox));
			closingSettings
					.setShowSales7(getInt(showSales7CheckBox));
			closingSettings
					.setShowSales8(getInt(showSales8CheckBox));
			closingSettings
					.setShowSales9(getInt(showSales9CheckBox));
			closingSettings
					.setIncomeLabel1(income_1_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel2(income_2_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel3(income_3_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel4(income_4_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel5(income_5_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel6(income_6_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel7(income_7_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel8(income_8_labelTextBox.getValue());
			closingSettings
					.setIncomeLabel9(income_9_labelTextBox.getValue());
			closingSettings
					.setShowIncome1(getInt(showIncome1CheckBox));
			closingSettings
					.setShowIncome2(getInt(showIncome2CheckBox));

			closingSettings
					.setShowIncome3(getInt(showIncome3CheckBox));

			closingSettings
					.setShowIncome4(getInt(showIncome4CheckBox));
			closingSettings
					.setShowIncome5(getInt(showIncome5CheckBox));

			closingSettings
					.setShowIncome6(getInt(showIncome6CheckBox));

			closingSettings
					.setShowIncome7(getInt(showIncome7CheckBox));

			closingSettings
					.setShowIncome8(getInt(showIncome8CheckBox));

			closingSettings
					.setShowIncome9(getInt(showIncome9CheckBox));
			saveClosingSettings();
		}
	};
	private int getInt(SimpleCheckBox cb) {
		if (cb.getValue().booleanValue()) {
			return 1;
		}else {
			return 0;
		}
	}

	public ClickHandler loadPresetHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			ClosingSettingsPanel.this.showSales1CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_1_labelTextBox.setValue("Bisque Sales");

			ClosingSettingsPanel.this.showSales2CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_2_labelTextBox.setValue("Paint Time");

			ClosingSettingsPanel.this.showSales3CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_3_labelTextBox.setValue("Children's Party");

			ClosingSettingsPanel.this.showSales4CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_4_labelTextBox.setValue("Adult Party");

			ClosingSettingsPanel.this.showSales5CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_5_labelTextBox.setValue("Party Deposit");

			ClosingSettingsPanel.this.showSales6CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_6_labelTextBox.setValue("Gift Certificates Sold");

			ClosingSettingsPanel.this.showSales7CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_7_labelTextBox.setValue("Credit Card Tips");

			ClosingSettingsPanel.this.showSales8CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.sales_8_labelTextBox.setValue("Sales Tax");

			ClosingSettingsPanel.this.showSales9CheckBox.setValue(Boolean.valueOf(false));

			ClosingSettingsPanel.this.showIncome1CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.income_1_labelTextBox.setValue("Debits");

			ClosingSettingsPanel.this.showIncome2CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.income_2_labelTextBox.setValue("Checks");

			ClosingSettingsPanel.this.showIncome3CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.income_3_labelTextBox.setValue("Credit Card");

			ClosingSettingsPanel.this.showIncome4CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.income_4_labelTextBox.setValue("Coupon");

			ClosingSettingsPanel.this.showIncome5CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.income_5_labelTextBox.setValue("Receipts");

			ClosingSettingsPanel.this.showIncome6CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.income_6_labelTextBox.setValue("Gift Certificates Redeemed");

			ClosingSettingsPanel.this.showIncome7CheckBox.setValue(Boolean.valueOf(true));
			ClosingSettingsPanel.this.income_7_labelTextBox.setValue("Deposit Redeemed");

			ClosingSettingsPanel.this.showIncome8CheckBox.setValue(Boolean.valueOf(false));

			ClosingSettingsPanel.this.showIncome9CheckBox.setValue(Boolean.valueOf(false));
		}
	};



	private void saveClosingSettings() {
		String base = ClosingsApp.getURL("/closing-settings/" + closingSettings.getId());
		String url = URL
				.encode(base);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		String postData = JsonUtils.stringify(closingSettings);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					ClosingsApp.getInstance().displayError(
							"Could not save settings:"
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());						
						ClosingsApp.getInstance().clearMain();
						ClosingsApp.getInstance().displayMessage("Settings successfully saved");
						
						
					} else {
						GWT.log("bad result " + response.getStatusCode());
						ClosingsApp.getInstance().displayMessage(response.getText());
					}
				}
			});
		} catch (RequestException e) {
			ClosingsApp.getInstance().displayError("Could not save settings:" + e.getMessage());
		}
	}

	public static FlowPanel getFlowPanel(Label label, TextBox textBox) {
		FlowPanel fp = new FlowPanel();
		fp.setStyleName("form-group");
		fp.add(label);
		label.setStyleName("control-label");
		fp.add(textBox);
		textBox.setStyleName("form-control");
		//add(fp);

		Label fp2 = new Label();
		fp2.setText("That's not a number, please fix it.");
		fp2.setStyleName("invalid-feedback");

		fp.add(fp2);
		Label fp3 = new Label();
		fp3.setText("Looks good!");
		fp3.setStyleName("valid-feedback");

		fp.add(fp3);

		//addKeyUpHandler(fp, textBox);
		return fp;
	}
	
	

	private void initControl(SimpleCheckBox checkbox, TextBox textBox, String labelText, int display,String prompt) {
		
		textBox.setValue(labelText);
		
		Label textboxLabel = new Label("Label for " + prompt);
		FlowPanel fp = new FlowPanel();
		fp.setStyleName("form-group");
		fp.add(textboxLabel);
		textboxLabel.setStyleName("control-label");
		fp.add(textBox);
		textBox.setStyleName("form-control");
		
		add(fp);
		if (display == 1) {
			checkbox.setValue(true);
		
		} else {
			checkbox.setValue(false);
		}
		

		Label checkboxLabel = new Label("Display?");
		
		FlowPanel fp2 = new FlowPanel();
		fp2.setStyleName("form-group");
		fp2.add(checkboxLabel);
		checkboxLabel.setStyleName("control-label");
		fp2.add(checkbox);
		checkbox.setStyleName("form-control");
		
		add(fp2);
		
		



	}
	
	public ClickHandler cancelHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			ClosingsApp.getInstance().clearMain();

		}
	};
	public ClosingSettingsPanel(ClosingSettings closingSettings) {

		
		this.closingSettings = closingSettings;
		initControl(this.showSales1CheckBox, this.sales_1_labelTextBox, closingSettings.getSalesLabel1(),
				closingSettings.getShowSales1(), "Sales #1");
		
		initControl(this.showSales2CheckBox, this.sales_2_labelTextBox, closingSettings.getSalesLabel2(),
				closingSettings.getShowSales2(), "Sales #2");
		
		
		initControl(this.showSales3CheckBox, this.sales_3_labelTextBox, closingSettings.getSalesLabel3(),
				closingSettings.getShowSales3(), "Sales #3");
		
		initControl(this.showSales4CheckBox, this.sales_4_labelTextBox, closingSettings.getSalesLabel4(),
				closingSettings.getShowSales4(), "Sales #4");
		
		
		
		initControl(this.showSales5CheckBox, this.sales_5_labelTextBox, closingSettings.getSalesLabel5(), closingSettings.getShowSales5(), "Sales #5");
		
		initControl(this.showSales6CheckBox, this.sales_6_labelTextBox, closingSettings.getSalesLabel6(),
				closingSettings.getShowSales6(), "Sales #6");
		
		initControl(this.showSales7CheckBox, this.sales_7_labelTextBox, closingSettings.getSalesLabel7(),
				closingSettings.getShowSales7(), "Sales #7");
		
		initControl(this.showSales8CheckBox, this.sales_8_labelTextBox, closingSettings.getSalesLabel8(),
				closingSettings.getShowSales8(), "Sales #8");
		
		initControl(this.showSales9CheckBox, this.sales_9_labelTextBox, closingSettings.getSalesLabel9(),
				closingSettings.getShowSales9(), "Sales #9");

		initControl(this.showIncome1CheckBox, this.income_1_labelTextBox, closingSettings.getIncomeLabel1(),
				closingSettings.getShowIncome1(),"Income #1");
		
		initControl(this.showIncome2CheckBox, this.income_2_labelTextBox, closingSettings.getIncomeLabel2(),
				closingSettings.getShowIncome2(),"Income #2");
		
		initControl(this.showIncome3CheckBox, this.income_3_labelTextBox, closingSettings.getIncomeLabel3(),
				closingSettings.getShowIncome3(),"Income #3");
		initControl(this.showIncome4CheckBox, this.income_4_labelTextBox, closingSettings.getIncomeLabel4(),
				closingSettings.getShowIncome4(),"Income #4");
		initControl(this.showIncome5CheckBox, this.income_5_labelTextBox, closingSettings.getIncomeLabel5(),
				closingSettings.getShowIncome5(),"Income #5");
		initControl(this.showIncome6CheckBox, this.income_6_labelTextBox, closingSettings.getIncomeLabel6(),
				closingSettings.getShowIncome6(),"Income #6");
		initControl(this.showIncome7CheckBox, this.income_7_labelTextBox, closingSettings.getIncomeLabel7(),
				closingSettings.getShowIncome7(),"Income #7");
		

		initControl(this.showIncome8CheckBox, this.income_8_labelTextBox, closingSettings.getIncomeLabel8(),
				closingSettings.getShowIncome8(),"Income #8");


		initControl(this.showIncome9CheckBox, this.income_9_labelTextBox, closingSettings.getIncomeLabel9(),
				closingSettings.getShowIncome9(),"Income #9");


		saveButton.setStyleName("btn btn-primary");
		cancelButton.setStyleName("btn btn-primary");
		loadPresetButton.setStyleName("btn btn-primary");
		

		FlowPanel fp = new FlowPanel();
		fp.setStyleName("row");
		add(fp);
		fp.add(saveButton);
		fp.add(cancelButton);
		fp.add(loadPresetButton);
		
		cancelButton.addClickHandler(cancelHandler);		
		saveButton.addClickHandler(this.saveHandler);
		loadPresetButton.addClickHandler(this.loadPresetHandler);
	}
}
