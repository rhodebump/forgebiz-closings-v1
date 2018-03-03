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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ClosingSettingsPanel extends VerticalPanel {
	Label errorLabel;

	private void displayMessage(String error) {
		this.errorLabel.setText(this.errorLabel.getText() + " " + error);
	}

	private void displayError(String error) {
		displayMessage(error);
	}

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
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel1(ClosingSettingsPanel.this.sales_1_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel2(ClosingSettingsPanel.this.sales_2_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel3(ClosingSettingsPanel.this.sales_3_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel4(ClosingSettingsPanel.this.sales_4_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel5(ClosingSettingsPanel.this.sales_5_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel6(ClosingSettingsPanel.this.sales_6_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel7(ClosingSettingsPanel.this.sales_7_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel8(ClosingSettingsPanel.this.sales_8_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setSalesLabel9(ClosingSettingsPanel.this.sales_9_labelTextBox.getValue());

			ClosingSettingsPanel.this.closingSettings
					.setShowSales1(ClosingSettingsPanel.this.showSales1CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales2(ClosingSettingsPanel.this.showSales2CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales3(ClosingSettingsPanel.this.showSales3CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales4(ClosingSettingsPanel.this.showSales4CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales5(ClosingSettingsPanel.this.showSales5CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales6(ClosingSettingsPanel.this.showSales6CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales7(ClosingSettingsPanel.this.showSales7CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales8(ClosingSettingsPanel.this.showSales8CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowSales9(ClosingSettingsPanel.this.showSales9CheckBox.getValue().booleanValue());

			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel1(ClosingSettingsPanel.this.income_1_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel2(ClosingSettingsPanel.this.income_2_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel3(ClosingSettingsPanel.this.income_3_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel4(ClosingSettingsPanel.this.income_4_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel5(ClosingSettingsPanel.this.income_5_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel6(ClosingSettingsPanel.this.income_6_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel7(ClosingSettingsPanel.this.income_7_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel8(ClosingSettingsPanel.this.income_8_labelTextBox.getValue());
			ClosingSettingsPanel.this.closingSettings
					.setIncomeLabel9(ClosingSettingsPanel.this.income_9_labelTextBox.getValue());

			ClosingSettingsPanel.this.closingSettings
					.setShowIncome1(ClosingSettingsPanel.this.showIncome1CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome2(ClosingSettingsPanel.this.showIncome2CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome3(ClosingSettingsPanel.this.showIncome3CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome4(ClosingSettingsPanel.this.showIncome4CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome5(ClosingSettingsPanel.this.showIncome5CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome6(ClosingSettingsPanel.this.showIncome6CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome7(ClosingSettingsPanel.this.showIncome7CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome8(ClosingSettingsPanel.this.showIncome8CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.closingSettings
					.setShowIncome9(ClosingSettingsPanel.this.showIncome9CheckBox.getValue().booleanValue());
			ClosingSettingsPanel.this.saveClosingSettings(ClosingSettingsPanel.this.closingSettings);
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

	private static final String JSON_URL2 = "http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings";

	private void saveClosingSettings(ClosingSettings closingLabel) {
		String url = URL
				.encode("http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings/" + closingLabel.getId());
		GWT.log("url = " + url);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		String postData = JsonUtils.stringify(closingLabel);
		displayMessage("postData: " + postData);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					ClosingSettingsPanel.this.displayError(
							"Couldn't retrieve JSON : http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings"
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						ClosingSettingsPanel.this.displayMessage("response=" + response.getText());
					} else {
						GWT.log("bad result " + response.getStatusCode());
						ClosingSettingsPanel.this.displayMessage(
								"Couldn't retrieve JSON (http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings"
										+ response.getStatusText() + ")");
					}
				}
			});
		} catch (RequestException e) {
			displayError("Couldn't retrieve JSON : " + e.getMessage());
		}
	}

	private void initControl(SimpleCheckBox checkbox, TextBox textbox, String labelText, boolean display) {
		checkbox.setValue(Boolean.valueOf(display));

		add(checkbox);
		textbox.setValue(labelText);
		add(textbox);
	}

	public ClosingSettingsPanel(ClosingSettings closingSetting) {
		this.closingSettings = this.closingSettings;
		initControl(this.showSales1CheckBox, this.sales_1_labelTextBox, closingSetting.getSalesLabel1(),
				closingSetting.getShowSales1());
		initControl(this.showSales2CheckBox, this.sales_2_labelTextBox, closingSetting.getSalesLabel2(),
				closingSetting.getShowSales2());
		initControl(this.showSales3CheckBox, this.sales_3_labelTextBox, closingSetting.getSalesLabel3(),
				closingSetting.getShowSales3());
		initControl(this.showSales4CheckBox, this.sales_4_labelTextBox, closingSetting.getSalesLabel4(),
				closingSetting.getShowSales4());
		initControl(this.showSales5CheckBox, this.sales_5_labelTextBox, closingSetting.getSalesLabel5(),
				closingSetting.getShowSales5());
		initControl(this.showSales6CheckBox, this.sales_6_labelTextBox, closingSetting.getSalesLabel6(),
				closingSetting.getShowSales6());
		initControl(this.showSales7CheckBox, this.sales_7_labelTextBox, closingSetting.getSalesLabel7(),
				closingSetting.getShowSales7());
		initControl(this.showSales8CheckBox, this.sales_8_labelTextBox, closingSetting.getSalesLabel8(),
				closingSetting.getShowSales8());
		initControl(this.showSales9CheckBox, this.sales_9_labelTextBox, closingSetting.getSalesLabel9(),
				closingSetting.getShowSales9());

		initControl(this.showIncome1CheckBox, this.income_1_labelTextBox, closingSetting.getIncomeLabel1(),
				closingSetting.getShowIncome1());
		initControl(this.showIncome2CheckBox, this.income_2_labelTextBox, closingSetting.getIncomeLabel2(),
				closingSetting.getShowIncome2());
		initControl(this.showIncome3CheckBox, this.income_3_labelTextBox, closingSetting.getIncomeLabel3(),
				closingSetting.getShowIncome3());
		initControl(this.showIncome4CheckBox, this.income_4_labelTextBox, closingSetting.getIncomeLabel4(),
				closingSetting.getShowIncome4());
		initControl(this.showIncome5CheckBox, this.income_5_labelTextBox, closingSetting.getIncomeLabel5(),
				closingSetting.getShowIncome5());
		initControl(this.showIncome6CheckBox, this.income_6_labelTextBox, closingSetting.getIncomeLabel6(),
				closingSetting.getShowIncome6());
		initControl(this.showIncome7CheckBox, this.income_7_labelTextBox, closingSetting.getIncomeLabel7(),
				closingSetting.getShowIncome7());
		initControl(this.showIncome8CheckBox, this.income_8_labelTextBox, closingSetting.getIncomeLabel8(),
				closingSetting.getShowIncome8());
		initControl(this.showIncome9CheckBox, this.income_9_labelTextBox, closingSetting.getIncomeLabel9(),
				closingSetting.getShowIncome9());

		add(this.saveButton);
		this.saveButton.addClickHandler(this.saveHandler);

		add(this.loadPresetButton);
		this.loadPresetButton.addClickHandler(this.loadPresetHandler);
	}
}
