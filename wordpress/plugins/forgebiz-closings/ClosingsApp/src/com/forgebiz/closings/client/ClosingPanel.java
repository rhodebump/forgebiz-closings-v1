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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClosingPanel extends Composite {
	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	@UiField(provided = true)
	Button saveButton;

	private ClosingSettings closingSettings = null;
	@UiField(provided = true)
	CashPanel openCashPanel;
	@UiField(provided = true)
	CashPanel closeCashPanel;
	@UiField(provided = true)
	SalesPanel salesPanel;
	@UiField(provided = true)
	IncomePanel incomePanel;

	@UiField(provided = true)
	DateBox closingDateBox = new DateBox();

	ClosingsApp closingsApp = null;
	
	@UiField(provided = true)
	ListBox locationListBox;
	
	
	
	/*
	 *     ListBox lb = new ListBox();
    lb.addItem("foo");
    lb.addItem("bar");
    lb.addItem("baz");
    lb.addItem("toto");
    lb.addItem("tintin");

    // Make enough room for all five items (setting this value to 1 turns it
    // into a drop-down list).
    lb.setVisibleItemCount(5);
	 */
	AsyncCallback gotLocationsCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("openSettingCallback.onSuccess");
			
			JsArray<Location> records =(JsArray<Location>) response;
			for (int i = 0; i < records.length(); i++) {
				Location location = records.get(i);

				locationListBox.addItem(location.getLocationName(), new Integer(location.getId()).toString());
				
				
			}

		}
	};
	
	public ClosingPanel(ClosingsApp closingsApp, ClosingSettings closingSettings, CashPanel openCashPanel,
			CashPanel closeCashPanel, SalesPanel salesPanel, IncomePanel incomePanel, Button saveButton,
			DateBox closingDateBox, ListBox locationListBox) {
		this.closingsApp = closingsApp;
		this.closingSettings = closingSettings;
		this.openCashPanel = openCashPanel;
		this.closeCashPanel = closeCashPanel;
		this.salesPanel = salesPanel;
		this.incomePanel = incomePanel;
		this.saveButton = saveButton;
		this.saveButton.addClickHandler(this.saveHandler);
		initWidget((Widget) binder.createAndBindUi(this));
		GWT.log("closingpanel");
		closingsApp.fetchLocations(gotLocationsCallback);

	}

	public void calculateAll() {
		GWT.log("calculateAll");
		this.openCashPanel.calculateAll();
		double openCashTotal = this.openCashPanel.getCashTotal();
		GWT.log("openCashTotal " + openCashTotal);
		this.closeCashPanel.calculateAll();
		double closeCashTotal = this.closeCashPanel.getCashTotal();
		GWT.log("closeCashTotal " + closeCashTotal);
		double totalCash = closeCashTotal - openCashTotal;
		GWT.log("totalCash " + totalCash);

		this.incomePanel.setCashTotalCash(totalCash);
	}

	@UiTemplate("ClosingPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, ClosingPanel> {
	}
	
	

	public ClickHandler saveHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("saveHandler.onClick");
			Closing closing = (Closing) JavaScriptObject.createObject().cast();
			closing.setIncome1(ClosingsApp.getDoubleValue(incomePanel.income1TextBox));
			closing.setIncome2(ClosingsApp.getDoubleValue(incomePanel.income2TextBox));
			closing.setIncome3(ClosingsApp.getDoubleValue(incomePanel.income3TextBox));
			closing.setIncome4(ClosingsApp.getDoubleValue(incomePanel.income4TextBox));
			closing.setIncome5(ClosingsApp.getDoubleValue(incomePanel.income5TextBox));
			closing.setIncome6(ClosingsApp.getDoubleValue(incomePanel.income6TextBox));
			closing.setIncome7(ClosingsApp.getDoubleValue(incomePanel.income7TextBox));
			closing.setIncome8(ClosingsApp.getDoubleValue(incomePanel.income8TextBox));
			closing.setIncome9(ClosingsApp.getDoubleValue(incomePanel.income9TextBox));

			closing.setSales1(ClosingsApp.getDoubleValue(salesPanel.income1TextBox));
			closing.setSales2(ClosingsApp.getDoubleValue(salesPanel.income2TextBox));
			closing.setSales3(ClosingsApp.getDoubleValue(salesPanel.income3TextBox));
			closing.setSales4(ClosingsApp.getDoubleValue(salesPanel.income4TextBox));
			closing.setSales5(ClosingsApp.getDoubleValue(salesPanel.income5TextBox));
			closing.setSales6(ClosingsApp.getDoubleValue(salesPanel.income6TextBox));
			closing.setSales7(ClosingsApp.getDoubleValue(salesPanel.income7TextBox));
			closing.setSales8(ClosingsApp.getDoubleValue(salesPanel.income8TextBox));
			closing.setSales9(ClosingsApp.getDoubleValue(salesPanel.income9TextBox));

			saveClosing(closing);
		}
	};

	private void saveClosing(Closing closing) {
		String url = URL
				.encode("http://localhost:8080//wp-json/forgebiz-closings/v1/closing/" + closingSettings.getId());
		GWT.log("url = " + url);

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		ClosingsApp.setNonce(builder);
		builder.setHeader("Content-Type", "application/json");
		GWT.log(" JsonUtils.stringify1");
		String postData = JsonUtils.stringify(closing);
		GWT.log(" JsonUtils.stringify2");
		closingsApp.displayMessage("postData: " + postData);
		GWT.log("postData:" + postData);
		try {
			builder.sendRequest(postData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					closingsApp.displayError(
							"Couldn't retrieve JSON : http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings"
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("good result " + response.getStatusText());
						closingsApp.displayMessage(response.getText());
					} else {
						GWT.log("bad result " + response.getStatusCode());
						closingsApp.displayMessage(
								"Couldn't retrieve JSON (http://localhost:8080//wp-json/forgebiz-closings/v1/closing-settings"
										+ response.getStatusText() + ")");
					}
				}
			});
		} catch (RequestException e) {
			closingsApp.displayError("Couldn't retrieve JSON : " + e.getMessage());
		}
	}

}
