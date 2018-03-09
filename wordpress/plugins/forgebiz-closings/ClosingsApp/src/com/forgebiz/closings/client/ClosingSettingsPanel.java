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
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClosingSettingsPanel extends VerticalPanel {




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
			
			GWT.log("saveHandler.onClick2");
			closingSettings
					.setShowSales1(showSales1CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales2(showSales2CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales3(showSales3CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales4(showSales4CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales5(showSales5CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales6(showSales6CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales7(showSales7CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales8(showSales8CheckBox.getValue().booleanValue());
			closingSettings
					.setShowSales9(showSales9CheckBox.getValue().booleanValue());
			GWT.log("saveHandler.onClick3");
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
			GWT.log("saveHandler.onClick4");
			closingSettings
					.setShowIncome1(showIncome1CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome2(showIncome2CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome3(showIncome3CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome4(showIncome4CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome5(showIncome5CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome6(showIncome6CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome7(showIncome7CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome8(showIncome8CheckBox.getValue().booleanValue());
			closingSettings
					.setShowIncome9(showIncome9CheckBox.getValue().booleanValue());
			GWT.log("saveHandler.onClick5");
			saveClosingSettings(closingSettings);
		}
	};

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



	private void saveClosingSettings(ClosingSettings closingSettings) {
		String base = ClosingsApp.getURL("/closing-settings/" + closingSettings.getId());
		String url = URL
				.encode(base);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		String postData = JsonUtils.stringify(closingSettings);
		//closingsApp.displayMessage("postData: " + postData);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					closingsApp.displayError(
							"Could not save settings:"
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						closingsApp.displayMessage("Settings saved");
					} else {
						GWT.log("bad result " + response.getStatusCode());
						closingsApp.displayMessage(response.getText());
					}
				}
			});
		} catch (RequestException e) {
			closingsApp.displayError("Could not save settings:" + e.getMessage());
		}
	}


	private void initControl(SimpleCheckBox checkbox, TextBox textbox, String labelText, boolean display,String prompt) {
		
		DecoratorPanel decPanel = new DecoratorPanel();
		add(decPanel);
		VerticalPanel vp = new VerticalPanel();
		decPanel.add(vp);
		if (display == false) {
			checkbox.setValue(false);
		} else {
			checkbox.setValue(true);
		}
		
		Label checkboxLabel = new Label("Display " + prompt);
		vp.add(checkboxLabel);
		vp.add(checkbox);
		Label textboxLabel = new Label("Label for " + prompt);
		vp.add(textboxLabel);
		//textbox.setValue(labelText);
		textbox.setValue(labelText);
		vp.add(textbox);
	}
	
	private ClosingsApp closingsApp = null;
	public ClosingSettingsPanel(ClosingsApp closingsApp,ClosingSettings closingSettings) {
		this.closingsApp = closingsApp;
		this.closingSettings = closingSettings;
		initControl(this.showSales1CheckBox, this.sales_1_labelTextBox, closingSettings.getSalesLabel1(),
				closingSettings.getShowSales1(), "Sales #1");
		initControl(this.showSales2CheckBox, this.sales_2_labelTextBox, closingSettings.getSalesLabel2(),
				closingSettings.getShowSales2(), "Sales #2");
		initControl(this.showSales3CheckBox, this.sales_3_labelTextBox, closingSettings.getSalesLabel3(),
				closingSettings.getShowSales3(), "Sales #3");
		initControl(this.showSales4CheckBox, this.sales_4_labelTextBox, closingSettings.getSalesLabel4(),
				closingSettings.getShowSales4(), "Sales #4");
		initControl(this.showSales5CheckBox, this.sales_5_labelTextBox, closingSettings.getSalesLabel5(),
				closingSettings.getShowSales5(), "Sales #5");
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
				closingSettings.getShowIncome3(),"Income #4");
		initControl(this.showIncome4CheckBox, this.income_4_labelTextBox, closingSettings.getIncomeLabel4(),
				closingSettings.getShowIncome4(),"Income #5");
		initControl(this.showIncome5CheckBox, this.income_5_labelTextBox, closingSettings.getIncomeLabel5(),
				closingSettings.getShowIncome5(),"Income #6");
		initControl(this.showIncome6CheckBox, this.income_6_labelTextBox, closingSettings.getIncomeLabel6(),
				closingSettings.getShowIncome6(),"Income #7");
		initControl(this.showIncome7CheckBox, this.income_7_labelTextBox, closingSettings.getIncomeLabel7(),
				closingSettings.getShowIncome7(),"Income #8");
		initControl(this.showIncome8CheckBox, this.income_8_labelTextBox, closingSettings.getIncomeLabel8(),
				closingSettings.getShowIncome8(),"Income #9");
		initControl(this.showIncome9CheckBox, this.income_9_labelTextBox, closingSettings.getIncomeLabel9(),
				closingSettings.getShowIncome9(),"Income #9");

		add(this.saveButton);
		this.saveButton.addClickHandler(this.saveHandler);

		add(this.loadPresetButton);
		this.loadPresetButton.addClickHandler(this.loadPresetHandler);
	}
}
