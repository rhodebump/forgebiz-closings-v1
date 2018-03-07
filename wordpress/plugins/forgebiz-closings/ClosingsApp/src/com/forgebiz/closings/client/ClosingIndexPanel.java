package com.forgebiz.closings.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClosingIndexPanel extends Composite {
	private static final MyBinder binder = (MyBinder) GWT.create(MyBinder.class);

	
	AsyncCallback newClosingCallback = new AsyncCallback() {
		public void onFailure(Throwable throwable) {
		}

		public void onSuccess(Object response) {
			GWT.log("newClosingCallback.onSuccess");
			ClosingSettings closingSettings = (ClosingSettings) response;
			CashPanel openCashPanel = new CashPanel();
			CashPanel closeCashPanel = new CashPanel();
			SalesPanel salesPanel = new SalesPanel(closingSettings);
			IncomePanel incomePanel = new IncomePanel(closingSettings);
			Button saveButton = new Button();

			DateBox closingDateBox = new DateBox();
			 ListBox locationListBox = new ListBox();
			ClosingPanel closingPanel = new ClosingPanel(closingsApp,closingSettings, openCashPanel, closeCashPanel, salesPanel,
					incomePanel, saveButton, closingDateBox, locationListBox);
			openCashPanel.setClosingPanel(closingPanel);
			closeCashPanel.setClosingPanel(closingPanel);
			RootPanel.get("closingsMain").clear();
			RootPanel.get("closingsMain").add(closingPanel);

		}
	};
	
	
	@UiField
	ClosingsApp closingsApp;
	


	@UiField
	DateBox startDatePicker = new DateBox();

	@UiField
	DateBox endDatePicker = new DateBox();

	@UiField(provided = true)
	ListBox locationListBox;
	
	@UiField
	Button searchButton = new Button("Search");
	public ClickHandler createNewClosingHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			// new closing panel
			// how to swap out the panel?
			closingsApp.fetchClosingSettings(newClosingCallback);

		}
	};
	
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
	
	
	public ClickHandler searchHandler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			GWT.log("searchHandler.onClick");
			
			String url = URL.encode("http://localhost:8080/wp-json/forgebiz-closings/v1/closing/search");
			url = url + "?location_id=" + locationListBox.getSelectedValue();
			url = url + "&start_date=" + startDatePicker.getValue().toString();
			url = url + "&end_date=" + endDatePicker.getValue().toString();
			
			GWT.log("url = " + url);
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
			ClosingsApp.setNonce(builder);
			try {
				builder.sendRequest(null, new RequestCallback() {
					public void onError(Request request, Throwable exception) {
						closingsApp.displayError("Couldn't retrieve JSON : "  + exception.getMessage());
					}

					public void onResponseReceived(Request request, Response response) {
						if (200 == response.getStatusCode()) {
							GWT.log("good result " + response.getStatusText());

							closingsApp.displayError(response.getText());
						} else {
							GWT.log("bad result " + response.getStatusCode());
							closingsApp.displayError("Couldn't retrieve JSON ("  + response.getStatusText() + ")");
						}
					}
				});
			} catch (RequestException e) {
				closingsApp.displayError("Couldn't retrieve JSON : " + e.getMessage());
			}
		}
	};

	public ClosingIndexPanel(ClosingsApp closingsApp) {
		initWidget((Widget) binder.createAndBindUi(this));
		this.startDatePicker.setFormat(
				new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));
		this.endDatePicker.setFormat(
				new DateBox.DefaultFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT)));

		this.searchButton.addClickHandler(this.searchHandler);
		
			Button createClosingButton = new Button("New Closing");

		createClosingButton.addClickHandler(this.createNewClosingHandler);

		closingsApp.fetchLocations(gotLocationsCallback);
		
	}

	@UiTemplate("ClosingIndexPanel.ui.xml")
	static abstract interface MyBinder extends UiBinder<Widget, ClosingIndexPanel> {
	}
}
